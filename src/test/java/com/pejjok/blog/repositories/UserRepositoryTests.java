package com.pejjok.blog.repositories;

import com.pejjok.blog.TestDataUtils;
import com.pejjok.blog.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTests {

    private final UserRepository underTest;

    @Autowired
    public UserRepositoryTests(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndReturned(){
        //Create User
        UserEntity userEntity = TestDataUtils.createTestUserEntityA();
        underTest.save(userEntity);

        // Find Created User
        Optional<UserEntity> result = underTest.findById(userEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userEntity);
    }

    @Test
    public void testThatMultipleUserCanBeCreatedAndReturned(){
        // Create Users
        UserEntity userEntityA = TestDataUtils.createTestUserEntityA();
        UserEntity userEntityB = TestDataUtils.createTestUserEntityB();
        UserEntity userEntityC = TestDataUtils.createTestUserEntityC();
        underTest.save(userEntityA);
        underTest.save(userEntityB);
        underTest.save(userEntityC);

        //Find All Users
        List<UserEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).containsExactly(userEntityA,userEntityB,userEntityC);
    }


    @Test
    public void testThatUserCanBeUpdated(){
        // Create User
        UserEntity userEntity = TestDataUtils.createTestUserEntityA();
        underTest.save(userEntity);

        // Update User
        userEntity.setName("updated");
        underTest.save(userEntity);

        // Find updated User
        Optional<UserEntity> result = underTest.findById(userEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userEntity.getId());
        assertThat(result.get().getName()).isEqualTo("updated");
    }

    @Test
    public void testThatUserCanBeDeleted(){
        // Create User
        UserEntity userEntity = TestDataUtils.createTestUserEntityA();
        underTest.save(userEntity);

        // Delete User
        underTest.deleteById(userEntity.getId());

        // Find deleted User
        Optional<UserEntity> result = underTest.findById(userEntity.getId());

        assertThat(result).isNotPresent();
    }

}
