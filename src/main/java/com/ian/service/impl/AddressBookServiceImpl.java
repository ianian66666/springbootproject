package com.ian.service.impl;

import com.ian.entity.AddressBook;
import com.ian.entity.Result;
import com.ian.mapper.AddressBookMapper;
import com.ian.service.AddressBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void save(HttpServletRequest request, AddressBook addressBook) {
        Long empId =(Long) request.getSession().getAttribute("employee");
        addressBook.setUserId(empId);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setCreateUser(empId);
        addressBookMapper.save(addressBook);

    }

    @Override
    public AddressBook setDefault(HttpServletRequest request, AddressBook addressBook) {
        //先將全部isdefault資料庫字斷都設為0 update address_book set is_default =0 where user_id =#{userId}
        Long empId = (Long) request.getSession().getAttribute("employee");
            addressBook.setUserId(empId);
            addressBook.setIsDefault(0);
            addressBookMapper.setDefault(addressBook);

        //再將要改的默認地址設為1
        addressBook.setIsDefault(1);
        addressBookMapper.setDefaultForOne(addressBook);
        return addressBook;
    }

    @Override
    public AddressBook seletById(Long id) {
        AddressBook addressBook=   addressBookMapper.seletById(id);
            return addressBook;
    }

    @Override
    public AddressBook selectDefault(HttpServletRequest request) {
        AddressBook addressBook = new AddressBook();
        Long empId = (Long) request.getSession().getAttribute("employee");
        addressBook.setUserId(empId);
        addressBook.setIsDefault(1);
      AddressBook addressResult =  addressBookMapper.selectDefault(addressBook);
          return addressResult;
      }

    @Override
    public List<AddressBook> list(HttpServletRequest request, AddressBook addressBook) {
        Long empId = (Long) request.getSession().getAttribute("employee");
                addressBook.setUserId(empId);
        List<AddressBook> addressBookList =addressBookMapper.list(addressBook);
        return addressBookList;
    }

}
