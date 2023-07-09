package com.ian.service;

import com.ian.entity.AddressBook;
import com.ian.entity.Result;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressBookService {
    void save(HttpServletRequest request, AddressBook addressBook);

    AddressBook setDefault(HttpServletRequest request, AddressBook addressBook);

    AddressBook seletById(Long id);

    AddressBook selectDefault(HttpServletRequest request);

    List<AddressBook> list(HttpServletRequest request, AddressBook addressBook);
}
