package com.springboot.practise.services;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import com.springboot.practise.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    private ItemsRepository itemsRepository;

    @Autowired
    public void setItemsRepository(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void save(Items it) {
        itemsRepository.save(it);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Items> getAll() {
        return itemsRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Items findByNameDescriptionAndPrice(String name, String description, Integer price) {
        return itemsRepository.findByNameDescriptionAndPrice(name, description, price);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Items> findByName(String name) {
        return itemsRepository.findByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Items findById(Long id) {
        Optional<Items> items = itemsRepository.findById(id);
        Items item = items.get();
        return item;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String name) {
        List<Items> items = itemsRepository.findByName(name);
        itemsRepository.deleteAll(items);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        itemsRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setNameById(Long id, String name) {
        itemsRepository.setNameById(id, name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDescriptionById(Long id, String description) {
        itemsRepository.setDescriptionById(id, description);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setPriceById(Long id, Integer price) {
        itemsRepository.setPriceById(id, price);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Users> showUsers(Items item) {
        return itemsRepository.getUsers(item.getId());
    }
}