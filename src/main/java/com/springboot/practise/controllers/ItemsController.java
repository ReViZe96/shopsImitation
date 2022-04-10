package com.springboot.practise.controllers;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import com.springboot.practise.services.ItemsService;
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
@RequestMapping("/items")
@Api(tags = "Контроллеры для товаров")
public class ItemsController {
    private ItemsService itemsService;

    @Autowired
    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @ApiOperation(value = "Вывод списка всех имеющихся товаров")
    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    public String showAll(Model model) {
        List<Items> allItems = itemsService.getAll();
        if (allItems.isEmpty()) {
            return "noItems";
        } else {
            model.addAttribute("allItems", allItems);
            return "itemsList";
        }
    }

    @ApiOperation(value = "Вывод страницы конкретного товара")
    @RequestMapping(value = "/showCurrent/{id}")
    public String showCurrent(@ApiParam("ID товара") @PathVariable(value = "id") Long id, Model model) {
        Items item = itemsService.findById(id);
        model.addAttribute("item", item);
        return "currentItem";
    }

    @ApiOperation(value = "Вывод страницы с формой для добавления нового товара")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getSaveForm() {
        return "addItem";
    }

    @ApiOperation(value = "Добавление нового товара")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveItem(@ApiParam("Название") @RequestParam String name,
                           @ApiParam("Описание") @RequestParam String description,
                           @ApiParam("Цена") @RequestParam Integer price) {
        Items comparing = itemsService.findByNameDescriptionAndPrice(name, description, price);
        if (!(comparing == null)) {
            return "itemExist";
        } else {
            Items item = new Items(name, description, price);
            itemsService.save(item);
            return "successAddItem";
        }
    }

    @ApiOperation(value = "Вывод страницы с формой для удаления")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String getDelForm() {
        return "deleteItem";
    }

    @ApiOperation(value = "Удаление")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteItem(@ApiParam("Название") @RequestParam String name) {
        List<Items> comparing = itemsService.findByName(name);
        if (comparing.isEmpty()) {
            return "itemNotExist";
        } else {
            itemsService.delete(name);
            return "successDeleteItem";
        }
    }

    @ApiOperation(value = "Вывод страницы с формой для поиска")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String getFindForm() {
        return "findItem";
    }

    @ApiOperation(value = "Поиск и вывод результатов")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findItem(@ApiParam("Название") @RequestParam(value = "name") String name, Model model) {
        List<Items> foundItems = itemsService.findByName(name);
        if (!(foundItems.isEmpty())) {
            model.addAttribute("foundItems", foundItems);
            return "foundItems";
        } else {
            return "itemNotExist";
        }
    }

    @ApiOperation(value = "Удаление конкретного товара из списка")
    @RequestMapping(value = "/deleteCurrent/{id}", method = RequestMethod.POST)
    public String deleteCurr(@ApiParam("ID") @PathVariable(value = "id") Long id) {
        itemsService.deleteById(id);
        return "redirect:/items/showAll";
    }

    @ApiOperation(value = "Изменение названия конкретного товара из списка")
    @RequestMapping(value = "/editName/{id}", method = RequestMethod.POST)
    public String editName(@ApiParam("ID") @PathVariable(value = "id") Long id,
                           @ApiParam("Новое название") @RequestParam(value = "name") String name) {
        itemsService.setNameById(id, name);
        return "redirect:/items/showAll";
    }

    @ApiOperation(value = "Изменение описания конкретного товара из списка")
    @RequestMapping(value = "/editDescription/{id}", method = RequestMethod.POST)
    public String editDescription(@ApiParam("ID") @PathVariable(value = "id") Long id,
                                  @ApiParam("Новое описание") @RequestParam(value = "description") String description) {
        itemsService.setDescriptionById(id, description);
        return "redirect:/items/showAll";
    }

    @ApiOperation(value = "Изменение цены конкретного товара из списка")
    @RequestMapping(value = "/editPrice/{id}", method = RequestMethod.POST)
    public String editPrice(@ApiParam("ID") @PathVariable(value = "id") Long id,
                            @ApiParam("Новая цена") @RequestParam(value = "price") Integer price) {
        itemsService.setPriceById(id, price);
        return "redirect:/items/showAll";
    }

    @ApiOperation(value = "Вывод страницы с формой для поиска покупателей, приобревших конкретный товар")
    @RequestMapping(value = "/showUsByIt", method = RequestMethod.GET)
    public String getPageUsByIt() {
        return "usersByItems";
    }

    @ApiOperation(value = "Вывод списка покупателей, приобревших конкретный товар")
    @RequestMapping(value = "/showUsByIt", method = RequestMethod.POST)
    public String showUsByIt(@ApiParam("Название товара") @RequestParam String name,
                             @ApiParam("Описание товара") @RequestParam String description,
                             @ApiParam("Цена товара") @RequestParam Integer price, Model model) {
        Items item = itemsService.findByNameDescriptionAndPrice(name, description, price);
        if (!(item == null)) {
            List<Users> users = itemsService.showUsers(item);
            model.addAttribute("users", users);
            model.addAttribute("itemsName", item.getName());
            return "usersByItems";
        } else {
            return "itemNotExist";
        }
    }
}