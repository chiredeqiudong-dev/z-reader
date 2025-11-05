package cn.turingzy.zreader.web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zy
 * @date 2025/11/5
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

}
