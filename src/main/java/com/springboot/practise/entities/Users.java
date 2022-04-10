package com.springboot.practise.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    public Users() {
    }

    public Users(String name, Integer seria, Integer number, Integer age) {
        this.name = name;
        this.seria = seria;
        this.number = number;
        this.age = age;
    }

    public Users(String name, Integer seria, Integer number, Integer age, List<Items> items) {
        this.name = name;
        this.seria = seria;
        this.number = number;
        this.age = age;
        this.items = items;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "passport_seria")
    private Integer seria;

    @Column(name = "passport_number")
    private Integer number;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_items",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private List<Items> items = new ArrayList<>();

    //добавление каждому товару пользователя
    @PrePersist
    public void addUser() {
        items.forEach(items -> items.getUsers().add(this));
    }

    //удаление у каждого товара пользователя
    @PreRemove
    public void removeUser() {
        items.forEach(items -> items.getUsers().remove(this));
    }

    public void addItem(Items item) {
        items.add(item);
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
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

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}