package com.wyq.tmall.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.wyq.tmall.pojo.User;
import com.wyq.tmall.service.UserService;
import com.wyq.tmall.util.Page;
 
@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
  
    //只有查询功能
    /*
     1、用户的增加，是交由前台注册行为产生的
	 2、删除不提供（用户信息是最重要的资料）
	 3、修改不提供，应该由前台用户自己完成
     */
    
    @RequestMapping("admin_user_list")
    //获取分页对象
    public String list(Model model, Page page){
    	
    	//设置分页信息
        PageHelper.offsetPage(page.getStart(),page.getCount());
 
        //查询用户集合
        List<User> us= userService.list();
 
        // 通过PageInfo获取总数，并设置在page对象上
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);
 
        //把用户集合设置到model的"us"属性上
        model.addAttribute("us", us);
        
        //把分页对象设置到model的"page"属性上
        model.addAttribute("page", page);
 
        //服务端跳转到admin/listUser.jsp页面
        return "admin/listUser";
    }
 
}