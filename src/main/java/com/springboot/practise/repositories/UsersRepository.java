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
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("select us from Users us where us.name= :name and us.seria= :seria and us.number= :number")
    Users findByNameSeriaAndNumber(@Param("name") String name, @Param("seria") Integer seria, @Param("number") Integer number);

    @Query("select us from Users us where us.name= :name")
    List<Users> findByName(@Param("name") String name);

    @Modifying
    @Query("update Users us set us.name= :name where us.id= :id")
    void setNameById(@Param("id") Long id, @Param("name") String name);

    @Modifying(flushAutomatically = true)
    @Query("update Users us set us.seria= :seria where us.id= :id")
    void setSeriaById(@Param("id") Long id, @Param("seria") Integer seria);

    @Modifying(flushAutomatically = true)
    @Query("update Users us set us.number= :number where us.id= :id")
    void setNumberById(@Param("id") Long id, @Param("number") Integer number);

    @Modifying
    @Query("update Users us set us.age= :age where us.id= :id")
    void setAgeById(@Param("id") Long id, @Param("age") Integer age);

    @Query("select us.items from Users us where us.id= :id")
    List<Items> getItems(@Param("id") Long id);
}