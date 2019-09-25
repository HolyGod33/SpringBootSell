package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 17:11 2019/9/4
 */
@Component
public interface ProductCategoryMapper {
    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);



    @Select("select * from product_category where category_type=#{categoryType}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
    })
    List<ProductCategory> findByCategoryName(String CategoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    //将指定type类型的记录中的categoryName字段修改为参数中的字段
    int updateByCategoryType(@Param("categoryName") String categoryName,@Param("categoryType") Integer categoryType);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
        //将指定type类型的记录中的categoryName字段修改为参数中的字段
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);


    ProductCategory selectByCategoryType(Integer categoryType);

}
