package com.imooc.controller;

import com.imooc.Service.CategoryService;
import com.imooc.dataobject.ProductCategory;
import com.imooc.exception.SellException;
import com.imooc.form.CategoryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 13:24 2019/8/21
 */
@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);

    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId" , required = false) Integer categoryId,Map<String,Object> map
                              ){
        ProductCategory productCategory = new ProductCategory();
        if (categoryId!=null) {
            productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/index",map);
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        }catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
