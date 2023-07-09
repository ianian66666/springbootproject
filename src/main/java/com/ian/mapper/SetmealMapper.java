package com.ian.mapper;

import com.ian.dto.SetmealDto;
import com.ian.entity.Setmeal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("SELECT count(*) from setmeal where category_id=#{id}")
    int flagDish(Integer id);

    @Options(useGeneratedKeys = true , keyProperty = "id")
    @Insert("INSERT INTO setmeal(category_id, name, price, status,description, image, create_time, update_time, create_user, update_user) values " +
            "(#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(SetmealDto setmealDto);

    List<SetmealDto> list(String name);

    int selectStatus( Integer status, Long[] ids);

    void deleteByIds(Long[] ids);

    @Select("select  * from  setmeal where id=#{id}")
    Setmeal selectById(Long id);
@Update("update setmeal set name=#{name},category_id=#{categoryId},price=#{price},description=#{description}," +
        "image=#{image},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void updateWithSteDish(SetmealDto setmealDto);

    void updateStatusByIds(Setmeal setmeal, Long[] ids);
}
