package com.kaishengit.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by hoyt on 2017/10/27.
 */

public class ProductMapperTestCase {

    private static SqlSession sqlSession;
    private static ProductMapper productMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        productMapper = sqlSession.getMapper(ProductMapper.class);
    }

    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void insertTest() {
        Product product = new Product();
        product.setProductName("iphone6s");
        product.setProductInventory(100);

        productMapper.insert(product);
        sqlSession.commit();
    }

    @Test
    public void findById() {
        Product product = productMapper.selectByPrimaryKey(1);
        System.out.println(product);
    }

    @Test
    public void findByProductNumTest() {
        ProductExample productExample =new ProductExample();
        productExample.createCriteria().andProductInventoryEqualTo(100).andProductNameLike("%中兴%");

        List<Product> productList = productMapper.selectByExample(productExample);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void findAllTest() {
        List<Product> productList = productMapper.selectByExample(new ProductExample());
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void deleteByExampleTest() {
        ProductExample productExample =new ProductExample();
        productExample = new ProductExample();
        productExample.createCriteria().andProductInventoryEqualTo(40);
        productMapper.deleteByExample(productExample);
        sqlSession.commit();
    }

    @Test
    public void deleteByPrimaryTest() {
        productMapper.deleteByPrimaryKey(7);
        sqlSession.commit();
    }

    @Test
    public void findByOrTest() {
        ProductExample productExample = new ProductExample();
        productExample.or().andProductNameLike("%小米%");
        productExample.or().andProductNameLike("%华为%");
        productExample.setOrderByClause("id desc");

        List<Product> productList = productMapper.selectByExample(productExample);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void pageTest() {
        //PageHelper.startPage(2,5);
        PageHelper.offsetPage(5,5);
        ProductExample productExample = new ProductExample();
        List<Product> productList = productMapper.selectByExample(productExample);
        for (Product product : productList) {
            System.out.println(product);
        }
        //转化为PageInfo对象
        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        System.out.println("page--->" + pageInfo.getPages());

    }

    //制定分页参数
    @Test
    public void page() {
        List<Product> productList = productMapper.page(3,3);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

}
