package com.imooc.controller;

import com.imooc.Service.CategoryService;
import com.imooc.Service.ProductService;
import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author JXUT CXY
 * @Description 买家信息
 * @Date: created in 21:16 2018/10/20
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123",unless = "#result.getCode() != 0")
    //注解常用配置  condition是条件成立时才使用缓存 如果不成立不缓存 unless是如果不的意思 所以正常的条件要反一下
    public ResultVO list(){
        //1.查询所有上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //2.查询类目(一次查询)
        List<Integer> categoryTypeList=new ArrayList<>();
        //lambda方法
        categoryTypeList=productInfoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList= categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼接
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo: productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO. setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
