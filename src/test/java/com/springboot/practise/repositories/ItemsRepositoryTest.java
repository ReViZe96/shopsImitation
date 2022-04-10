package com.springboot.practise.repositories;

import com.springboot.practise.PractiseApplication;
import com.springboot.practise.entities.Items;
import com.springboot.practise.entities.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = PractiseApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemsRepositoryTest {

    private TestEntityManager testEntityManager;

    @Autowired
    public void setTestEntityManager(TestEntityManager testEntityManager) {
        this.testEntityManager = testEntityManager;
    }

    private ItemsRepository itemsRepository;

    @Autowired
    public void setItemsRepository(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    private Items testItemFirst;
    private Items testItemSecond;
    private Items testItemThird;
    private List<Users> testUsersList;

    @BeforeEach
    public void createTestDatas() {
        Users firstUser = new Users("firstUser", 1111, 111111, 11);
        testEntityManager.persist(firstUser);
        Users secondUser = new Users("secondUser", 2222, 222222, 22);
        testEntityManager.persist(secondUser);
        testUsersList = new ArrayList<>();
        testUsersList.add(firstUser);
        testUsersList.add(secondUser);
        testItemFirst = new Items("small gun", "forTest", 1000, testUsersList);
        testEntityManager.persist(testItemFirst);
        testItemSecond = new Items("big gun", "forTest", 2000);
        testEntityManager.persist(testItemSecond);
        testItemThird = new Items("small gun", "forTestToo", 1500);
        testEntityManager.persist(testItemThird);
    }

    @Test
    public void getUsersTest() {
        Long testItemsId = (Long) (testEntityManager.getId(testItemFirst));
        List<Users> actual = itemsRepository.getUsers(testItemsId);
        Assertions.assertEquals(testUsersList, actual);
    }

    @Test
    public void findByNameDescriptionAndPriceTest() {
        Items actual = itemsRepository.findByNameDescriptionAndPrice("small gun", "forTestToo", 1500);
        Assertions.assertEquals(testItemThird, actual);
    }

    @Test
    public void setPriceByIdTest() {
        Long testItemsId = (Long) (testEntityManager.getId(testItemSecond));
        itemsRepository.setPriceById(testItemsId, 5000);
        testEntityManager.refresh(testItemSecond);
        Items actual = itemsRepository.findById(testItemsId).get();
        Assertions.assertEquals(5000, actual.getPrice());
    }
}