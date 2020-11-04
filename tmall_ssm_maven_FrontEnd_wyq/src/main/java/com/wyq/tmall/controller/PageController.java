package com.wyq.tmall.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
//准备一个专门的PageController类，专门做服务端跳转。 
//比如访问registerPage，就会服务端跳转到WEB-INF/jsp/fore/register.jsp 去，这样就解决了无法被访问的问题。


@Controller
@RequestMapping("")
public class PageController {
	
    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }
    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }
    @RequestMapping("forealipay")
    public String alipay(){
        return "fore/alipay";
    }
}