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
class UsersRepositoryTest {

    private TestEntityManager testEntityManager;

    @Autowired
    public void setTestEntityManager(TestEntityManager testEntityManager) {
        this.testEntityManager = testEntityManager;
    }

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private Users testUserFirst;
    private Users testUserSecond;
    private Users testUserThird;
    private List<Items> testItemsList;

    @BeforeEach
    public void createTestDatas() {
        Items firstItems = new Items("firstItem", "forTest", 100);
        testEntityManager.persist(firstItems);
        Items secondItem = new Items("secondItem", "forTest", 200);
        testEntityManager.persist(secondItem);
        testItemsList = new ArrayList<>();
        testItemsList.add(firstItems);
        testItemsList.add(secondItem);
        testUserFirst = new Users("John Dow", 1111, 111111, 11, testItemsList);
        testEntityManager.persist(testUserFirst);
        testUserSecond = new Users("Not a John", 2222, 222222, 22);
        testEntityManager.persist(testUserSecond);
        testUserThird = new Users("John Dow", 1111, 111111, 12);
        testEntityManager.persist(testUserThird);
    }

    @Test
    public void getItemsTest() {
        Long testUsersId = (Long) (testEntityManager.getId(testUserFirst));
        List<Items> actual = usersRepository.getItems(testUsersId);
        Assertions.assertEquals(testItemsList, actual);
    }

    @Test
    public void findByNameTest() {
        List<Users> actual = usersRepository.findByName("John Dow");
        List<Users> expected = new ArrayList<>();
        expected.add(testUserFirst);
        expected.add(testUserThird);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void setNameByIdTest() {
        Long testUsersId = (Long) (testEntityManager.getId(testUserSecond));
        usersRepository.setNameById(testUsersId, "Jane Dow");
        testEntityManager.refresh(testUserSecond);
        Users actual = usersRepository.findById(testUsersId).get();
        Assertions.assertEquals("Jane Dow", actual.getName());
    }
}