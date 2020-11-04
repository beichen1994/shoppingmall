package com.wyq.tmall.config;
 
import com.wyq.tmall.interceptor.LoginInterceptor;
import com.wyq.tmall.interceptor.OtherInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
@Configuration
class WebMvcConfigurer extends WebMvcConfigurerAdapter{
     
	@Bean
    public OtherInterceptor getOtherIntercepter() {
        return new OtherInterceptor();
    }
	
    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }
     
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginIntercepter())
        .addPathPatterns("/**");      
    }
}