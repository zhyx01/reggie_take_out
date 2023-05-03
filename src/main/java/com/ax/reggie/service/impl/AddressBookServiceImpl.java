package com.ax.reggie.service.impl;

import com.ax.reggie.entity.AddressBook;
import com.ax.reggie.mapper.AddressBookMapper;
import com.ax.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
