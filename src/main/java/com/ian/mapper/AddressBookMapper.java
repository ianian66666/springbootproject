package com.ian.mapper;

import com.ian.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    @Insert("INSERT INTO address_book(user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code," +
            " district_name, detail, label, is_default,create_time, update_time, create_user, update_user) values (" +
            "#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode}," +
            "#{districtName},#{detail},#{label},#{isDefault},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(AddressBook addressBook);

    @Update("update address_book set  is_default=#{isDefault} where user_id=#{userId}")
    void setDefault(AddressBook addressBook);
    @Update("update address_book set  is_default=#{isDefault} where id=#{id}")
    void setDefaultForOne(AddressBook addressBook);

    @Select("SELECT *from address_book where id=#{id}")
    AddressBook seletById(Long id);

    @Select("select *from  address_book where user_id=#{userId} and is_default=#{isDefault}")
    AddressBook selectDefault(AddressBook addressBook);
@Select("select *from address_book where user_id=#{userId} order by update_time desc")
    List<AddressBook> list(AddressBook addressBook);
}
