package lesson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"","/"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String loginPage(){
        return "login";
    }
}
