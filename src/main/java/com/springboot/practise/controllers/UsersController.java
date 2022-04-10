package com.springboot.practise.controllers;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import com.springboot.practise.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
@Api(tags = "Контроллеры для покупателей")
public class UsersController {
    private UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiOperation(value = "Вывод списка всех зарегистрированных покупателей")
    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    public String showAll(Model model) {
        List<Users> allUsers = usersService.getAll();
        if (allUsers.isEmpty()) {
            return "noUsers";
        } else {
            model.addAttribute("allUsers", allUsers);
            return "usersList";
        }
    }

    @ApiOperation(value = "Вывод страницы конкретного покупателя")
    @RequestMapping(value = "/showCurrent/{id}", method = RequestMethod.GET)
    public String showCurrent(@ApiParam("ID покупателя") @PathVariable(value = "id") Long id, Model model) {
        Users user = usersService.findById(id);
        model.addAttribute("user", user);
        return "currentUser";
    }

    @ApiOperation(value = "Вывод страницы с формой для добавления нового покупателя")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getForm() {
        return "addUser";
    }

    @ApiOperation(value = "Добавление нового покупателя")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveUser(@ApiParam("ФИО") @RequestParam String name,
                           @ApiParam("Серия паспорта") @RequestParam Integer seria,
                           @ApiParam("Номер паспорта") @RequestParam Integer number,
                           @ApiParam("Возраст") @RequestParam Integer age) {
        Users comparing = usersService.findByNameSeriaAndNumber(name, seria, number);
        if (!(comparing == null)) {
            return "userExist";
        } else {
            Users user = new Users(name, seria, number, age);
            usersService.save(user);
            return "successAddUser";
        }
    }

    @ApiOperation(value = "Вывод страницы с формой для удаления")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String getDelForm() {
        return "deleteUser";
    }

    @ApiOperation(value = "Удаление")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@ApiParam("ФИО") @RequestParam String name, Model model) {
        List<Users> comparing = usersService.findByName(name);
        if (comparing.isEmpty()) {
            return "userNotExist";
        } else {
            usersService.delete(name);
            return "successDeleteUser";
        }
    }

    @ApiOperation(value = "Вывод страницы с формой для поиска")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String getFindForm() {
        return "findUser";
    }

    @ApiOperation(value = "Поиск и вывод результатов")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findUser(@ApiParam("ФИО") @RequestParam String name, Model model) {
        List<Users> foundUsers = usersService.findByName(name);
        if (!(foundUsers.isEmpty())) {
            model.addAttribute("foundUsers", foundUsers);
            return "foundUsers";
        } else {
            return "userNotExist";
        }
    }

    @ApiOperation(value = "Удаление конкретного покупателя из списка")
    @RequestMapping(value = "/deleteCurrent/{id}", method = RequestMethod.POST)
    public String deleteCurr(@ApiParam("ID") @PathVariable(value = "id") Long id) {
        usersService.deleteById(id);
        return "redirect:/users/showAll";
    }

    @ApiOperation(value = "Изменение ФИО конкретного покупателя из списка")
    @RequestMapping(value = "/editName/{id}", method = RequestMethod.POST)
    public String editCurr(@ApiParam("ID") @PathVariable(value = "id") Long id,
                           @ApiParam("Новые ФИО") @RequestParam(value = "name") String name) {
        usersService.setNameById(id, name);
        return "redirect:/users/showAll";
    }

    @ApiOperation(value = "Изменение серии паспорта конкретного покупателя из списка")
    @RequestMapping(value = "/editSeria/{id}", method = RequestMethod.POST)
    public String editSeria(@ApiParam("ID") @PathVariable(value = "id") Long id,
                            @ApiParam("Новая серия паспорта") @RequestParam(value = "seria") Integer seria) {
        usersService.setSeriaById(id, seria);
        return "redirect:/users/showAll";
    }

    @ApiOperation(value = "Изменение номера паспорта конкретного покупателя из списка")
    @RequestMapping(value = "/editNumber/{id}", method = RequestMethod.POST)
    public String editNumber(@ApiParam("ID") @PathVariable(value = "id") Long id,
                             @ApiParam("Новая серия паспорта") @RequestParam(value = "number") Integer number) {
        usersService.setNumberById(id, number);
        return "redirect:/users/showAll";
    }

    @ApiOperation(value = "Изменение возраста конкретного покупателя из списка")
    @RequestMapping(value = "/editAge/{id}", method = RequestMethod.POST)
    public String editAge(@ApiParam("ID") @PathVariable(value = "id") Long id,
                          @ApiParam("Новый возраст") @RequestParam(value = "age") Integer age) {
        usersService.setAgeById(id, age);
        return "redirect:/users/showAll";
    }

    @ApiOperation(value = "Вывод страницы с формой для поиска товаров, приобретенных конкретным пользователем")
    @RequestMapping(value = "/showItByUs", method = RequestMethod.GET)
    public String getShowItByUs() {
        return "itemsByUsers";
    }

    @ApiOperation(value = "Вывод списка покупок, конкретного покупателя")
    @RequestMapping(value = "/showItByUs", method = RequestMethod.POST)
    public String showItbyUs(@ApiParam("ФИО покупателя") @RequestParam String usersName,
                             @ApiParam("Серия паспорта покупателя") @RequestParam Integer usersSeria,
                             @ApiParam("Номер паспорта покупателя") @RequestParam Integer usersNumber, Model model) {
        Users user = usersService.findByNameSeriaAndNumber(usersName, usersSeria, usersNumber);
        if (!(user == null)) {
            List<Items> items = usersService.showItems(user);
            model.addAttribute("items", items);
            model.addAttribute("usersName", user.getName());
            return "itemsByUsers";
        } else {
            return "userNotExist";
        }
    }
}