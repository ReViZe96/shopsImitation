package com.springboot.practise.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(tags = "Контроллер главной страницы")
public class MainPageController {
    @ApiOperation(value = "Вывод главной страницы")
    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String showMainPage() {
        return "mainPage";
    }
}