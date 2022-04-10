package com.springboot.practise.controllers;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import com.springboot.practise.services.ItemsService;
import com.springboot.practise.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/buy")
@Api(tags = "Контроллеры для совершения покупки")
public class BuyController {
    private ItemsService itemsService;

    @Autowired
    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    private UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiOperation(value = "Вывод страницы с формой для совершения покупки")
    @RequestMapping(value = "/make", method = RequestMethod.GET)
    public String getPageForBuy() {
        return "makeBuy";
    }

    @ApiOperation(value = "Совершение покупки")
    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public String makeBuy(@ApiParam("Имя пользователя") @RequestParam String usersName,
                          @ApiParam("Cерия паспорта пользователя") @RequestParam Integer usersSeria,
                          @ApiParam("Номер паспорта пользователя") @RequestParam Integer usersNumber,
                          @ApiParam("Название товара") @RequestParam String itemsName,
                          @ApiParam("Описание товара") @RequestParam String itemsDescription,
                          @ApiParam("Стоимость товара") @RequestParam Integer itemsPrice) {
        Users user = usersService.findByNameSeriaAndNumber(usersName, usersSeria, usersNumber);
        Items item = itemsService.findByNameDescriptionAndPrice(itemsName, itemsDescription, itemsPrice);
        if ((!(item == null)) && (!(user == null))) {
            usersService.saveItems(user, item);
            return "successBuy";
        } else {
            return "failedBuy";
        }
    }
}