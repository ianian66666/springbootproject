package com.ian.controller;

import com.ian.entity.AddressBook;
import com.ian.entity.Result;
import com.ian.service.AddressBookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增默認地址
     *
     * @param request
     * @param addressBook
     * @return
     */

    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody AddressBook addressBook) {
        addressBookService.save(request, addressBook);
        return Result.success("新增成功");
    }

    /**
     * 設置默認地址
     *
     * @param request
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public Result<AddressBook> setDefault(HttpServletRequest request, @RequestBody AddressBook addressBook) {
        AddressBook addressBook1 = addressBookService.setDefault(request, addressBook);
        return Result.success(addressBook1);
    }

    /**
     * 依據id查詢地址
     *
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public Result<AddressBook> seletById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.seletById(id);
        if ((addressBook != null)) {
            return Result.success(addressBook);
        } else {
            return Result.error("沒有找到該對象");
        }
    }

    /**
     * 查詢某個用戶的默認地址
     *
     * @param request
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> selectDefault(HttpServletRequest request) {
        AddressBook addressBook = addressBookService.selectDefault(request);
        if ((addressBook != null)) {
            return Result.success(addressBook);
        } else {
            return Result.error("沒有找到該對象");
        }
    }

    /**
     * 查詢某個用戶的全部地址
     * @param request
     * @param addressBook
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(HttpServletRequest request,AddressBook addressBook) {
        List<AddressBook> addressBookList=   addressBookService.list(request,addressBook);
        return Result.success(addressBookList);
    }
}

