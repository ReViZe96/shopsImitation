package com.springboot.practise.services;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import com.springboot.practise.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void save(Users us) {
        usersRepository.save(us);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Users findByNameSeriaAndNumber(String name, Integer seria, Integer number) {
        return usersRepository.findByNameSeriaAndNumber(name, seria, number);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Users findById(Long id) {
        Optional<Users> users = usersRepository.findById(id);
        Users user = users.get();
        return user;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Users> findByName(String name) {
        return usersRepository.findByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String name) {
        List<Users> user = usersRepository.findByName(name);
        usersRepository.deleteAll(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setNameById(Long id, String name) {
        usersRepository.setNameById(id, name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setSeriaById(Long id, Integer seria) {
        usersRepository.setSeriaById(id, seria);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setNumberById(Long id, Integer number) {
        usersRepository.setNumberById(id, number);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setAgeById(Long id, Integer age) {
        usersRepository.setAgeById(id, age);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveItems(Users user, Items item) {
        user.addItem(item);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Items> showItems(Users user) {
        return usersRepository.getItems(user.getId());
    }
}