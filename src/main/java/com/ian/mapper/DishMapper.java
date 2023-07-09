package com.ian.mapper;

import com.ian.dto.DishDto;
import com.ian.entity.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(*) from dish where category_id=#{id}")
    int flagDish(Integer id);
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into dish(name, category_id, price, code, image, description, status,create_time, update_time, create_user, update_user) " +
            "values (#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void saveDish(DishDto dishDto);

    List<DishDto>  list(String name);


    @Select("select * from dish where id=#{id};")
    Dish selectById(Integer id);

    @Update("update dish set name=#{name},category_id =#{categoryId},price=#{price},image=#{image}," +
            "description=#{description}, update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void updateById(DishDto dishDto);

    void delete(Long[] ids);

    void updateStatus(Dish dish, Long[] ids);

    @Select("SELECT * from dish where category_id=#{categoryId} and status =#{status} order by sort asc ,update_time desc")
    List<Dish> listByCategory(Dish dish);

    int selectByStatus(Integer status, Long[] ids);
}
