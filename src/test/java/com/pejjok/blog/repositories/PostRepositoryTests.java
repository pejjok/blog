package com.pejjok.blog.repositories;

import com.pejjok.blog.TestDataUtils;
import com.pejjok.blog.domain.entities.CategoryEntity;
import com.pejjok.blog.domain.entities.PostEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostRepositoryTests {
    private final PostRepository underTest;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostRepositoryTests(PostRepository underTest,UserRepository userRepository,CategoryRepository categoryRepository) {
        this.underTest = underTest;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void testThatTagCanBeCreatedAndReturned(){
        //Create Post
        UserEntity user = TestDataUtils.createTestUserEntityA();
        CategoryEntity category = TestDataUtils.createTestCategoryEntityA();
        PostEntity postEntity = TestDataUtils.createTestPostEntityA(user,category);
        userRepository.save(user);
        categoryRepository.save(category);
        underTest.save(postEntity);

        // Find Created Post
        Optional<PostEntity> result = underTest.findById(postEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(postEntity);
    }

    @Test
    public void testThatMultipleTagsCanBeCreatedAndReturned(){
        // Create Posts
        UserEntity user = TestDataUtils.createTestUserEntityA();
        CategoryEntity category = TestDataUtils.createTestCategoryEntityA();
        PostEntity postEntityA = TestDataUtils.createTestPostEntityA(user,category);
        PostEntity postEntityB = TestDataUtils.createTestPostEntityB(user,category);
        PostEntity postEntityC = TestDataUtils.createTestPostEntityC(user,category);
        userRepository.save(user);
        categoryRepository.save(category);
        underTest.save(postEntityA);
        underTest.save(postEntityB);
        underTest.save(postEntityC);

        //Find All Posts
        List<PostEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).containsExactly(postEntityA,postEntityB,postEntityC);
    }


    @Test
    public void testThatTagCanBeUpdated(){
        // Create Post
        UserEntity user = TestDataUtils.createTestUserEntityA();
        CategoryEntity category = TestDataUtils.createTestCategoryEntityA();
        PostEntity postEntity = TestDataUtils.createTestPostEntityA(user,category);
        userRepository.save(user);
        categoryRepository.save(category);
        underTest.save(postEntity);

        // Update Post
        postEntity.setTitle("updated");
        underTest.save(postEntity);

        // Find updated Post
        Optional<PostEntity> result = underTest.findById(postEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(postEntity.getId());
        assertThat(result.get().getTitle()).isEqualTo("updated");
    }

    @Test
    public void testThatTagCanBeDeleted(){
        // Create Post
        UserEntity user = TestDataUtils.createTestUserEntityA();
        CategoryEntity category = TestDataUtils.createTestCategoryEntityA();
        PostEntity postEntity = TestDataUtils.createTestPostEntityA(user,category);
        userRepository.save(user);
        categoryRepository.save(category);
        underTest.save(postEntity);

        // Delete Post
        underTest.deleteById(postEntity.getId());

        // Find deleted Post
        Optional<PostEntity> result = underTest.findById(postEntity.getId());

        assertThat(result).isNotPresent();
    }
}
