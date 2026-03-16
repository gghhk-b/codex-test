package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 标记为控制器：用于返回页面视图
public class HomeController {

    @GetMapping("/") // 访问根路径时触发
    public String home(Model model) {
        // 向页面传递一个简单消息，供 Thymeleaf 展示
        model.addAttribute("message", "恭喜你，已经完成 Controller 到 HTML 的打通！");

        // 返回逻辑视图名：index（不写 .html）
        return "index";
    }
}
