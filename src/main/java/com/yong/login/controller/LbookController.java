package com.yong.login.controller;


import com.yong.login.entity.AdminLogin;
import com.yong.login.entity.Lbook;
import com.yong.login.entity.R;
import com.yong.login.service.LbookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yong
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/lbook")
public class LbookController {

    @Autowired
    private LbookService lbookService;


    @ApiOperation(value = "查询所有图书信息")
    @GetMapping("/getbooks/{page}/{limit}")
    public R getBooks(@PathVariable Long page,@PathVariable Long limit){
        return lbookService.getBooks(page, limit);
    }

    @ApiOperation(value = "根据id修改图书信息")
    @GetMapping("/updatebooks")
    public R updateBooks(Lbook lbook){
        return lbookService.saveBooks(lbook);
    }

    @ApiOperation(value = "根据id删除图书信息")
    @GetMapping("/deletebooks/{id}")
    public R deleteBooks(Integer id){
        return lbookService.deleteBooks(id);
    }

    @ApiOperation(value = "添加图书")
    @GetMapping("/addbooks")
    public R addBooks(Lbook lbook){
        return lbookService.addBooks(lbook);
    }

    @ApiOperation(value = "图书详细信息")
    @GetMapping("/bookInformation/{id}")
    public R bookInformation(Integer id){
        return lbookService.bookInformation(id);
    }
}

