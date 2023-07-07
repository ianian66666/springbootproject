package com.ian.mapper;

import com.ian.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("INSERT INTO  category(type, name, sort,create_time, update_time, create_user, update_user) values " +
            "(#{type},#{name},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(Category category);

    @Select("SELECT *from category order by  sort asc")
    List<Category> list();

    @Delete("DELETE from category where id=#{id}")
    void delete(Integer id);

    @Update("update category set name=#{name},sort=#{sort},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void update(Category category);

    @Select("select *from category where type=#{type} order by sort asc ,update_time desc")
    List<Category> listBytype(Category category);
}
