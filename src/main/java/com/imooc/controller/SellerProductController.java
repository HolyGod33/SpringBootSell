package com.imooc.controller;

import com.imooc.Service.CategoryService;
import com.imooc.Service.ProductService;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:44 2019/8/13
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);

        Page<ProductInfo> productInfoPage = productService.findAll(request);

        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size", size);

        return new ModelAndView("product/list",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onsale(@RequestParam("productId") String productId,
                               Map<String,Object> map) {
        ProductInfo productInfo = new ProductInfo();

        try {
            productInfo=productService.onsale(productId);
        }catch (SellException e) {
            log.error("[卖家端上架] {}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offsale(@RequestParam("productId") String productId,
                               Map<String,Object> map) {
        ProductInfo productInfo = new ProductInfo();

        try {
            productInfo=productService.offsale(productId);
        }catch (SellException e) {
            log.error("[卖家端上架] {}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId" , required = false)String productId,
                       Map<String , Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
            return new ModelAndView("product/index", map);
        }
    @PostMapping("/save")
//    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult,
                             Map<String,Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo =new ProductInfo();

        try {
            //为空就是新增商品
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo = productService.findOne(form.getProductId());
            }else{
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form,productInfo);

            productService.save(productInfo);
        }catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }
}
