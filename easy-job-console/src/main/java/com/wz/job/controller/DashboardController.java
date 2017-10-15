package com.wz.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>控制主页</p>
 * @author wangzi
 * Created by wangzi on 2017-09-24.
 */
@Controller
public class DashboardController {
    @RequestMapping("/")
    public String home(){
        return "index";
    }
}
