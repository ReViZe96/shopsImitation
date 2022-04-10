package com.springboot.practise.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Items {

    public Items() {
    }

    public Items(String name, String description, Integer price) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Items(String name, String description, Integer price, List<Users> users) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "items", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})

    private List<Users> users = new ArrayList<>();

    //добавление каждому пользователю товара
    @PrePersist
    public void addItem() {
        users.forEach(users -> users.getItems().add(this));
    }

    //удаление у каждого пользователя товара
    @PreRemove
    public void removeItem() {
        users.forEach(users -> users.getItems().remove(this));
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}