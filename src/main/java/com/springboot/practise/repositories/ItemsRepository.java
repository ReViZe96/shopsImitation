package com.springboot.practise.repositories;

import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("select it from Items it where it.name= :name and it.price= :price and it.description= :description")
    Items findByNameDescriptionAndPrice(@Param("name") String name, @Param("description") String description, @Param("price") Integer price);

    @Query("select it from Items it where it.name= :name")
    List<Items> findByName(@Param("name") String name);

    @Modifying
    @Query("update Items it set it.name= :name where it.id= :id")
    void setNameById(@Param("id") Long id, @Param("name") String name);

    @Modifying(flushAutomatically = true)
    @Query("update Items it set it.description= :description where it.id= :id")
    void setDescriptionById(@Param("id") Long id, @Param("description") String description);

    @Modifying
    @Query("update Items it set it.price= :price where it.id= :id")
    void setPriceById(@Param("id") Long id, @Param("price") Integer price);

    @Query("select it.users from Items it where it.id= :id")
    List<Users> getUsers(@Param("id") Long id);
}