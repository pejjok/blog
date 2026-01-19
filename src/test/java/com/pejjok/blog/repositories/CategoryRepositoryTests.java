package com.pejjok.blog.repositories;

import com.pejjok.blog.TestDataUtils;
import com.pejjok.blog.domain.entities.CategoryEntity;
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
public class CategoryRepositoryTests {
    private final CategoryRepository underTest;

    @Autowired
    public CategoryRepositoryTests(CategoryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatCategoryCanBeCreatedAndReturned(){
        //Create Category
        CategoryEntity categoryEntity = TestDataUtils.createTestCategoryEntityA();
        underTest.save(categoryEntity);

        // Find Created Category
        Optional<CategoryEntity> result = underTest.findById(categoryEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(categoryEntity);
    }

    @Test
    public void testThatMultipleCategoriesCanBeCreatedAndReturned(){
        // Create Categories
        CategoryEntity categoryEntityA = TestDataUtils.createTestCategoryEntityA();
        CategoryEntity categoryEntityB = TestDataUtils.createTestCategoryEntityB();
        CategoryEntity categoryEntityC = TestDataUtils.createTestCategoryEntityC();
        underTest.save(categoryEntityA);
        underTest.save(categoryEntityB);
        underTest.save(categoryEntityC);

        //Find All Categories
        List<CategoryEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).containsExactly(categoryEntityA,categoryEntityB,categoryEntityC);
    }


    @Test
    public void testThatCategoryCanBeUpdated(){
        // Create Category
        CategoryEntity categoryEntity = TestDataUtils.createTestCategoryEntityA();
        underTest.save(categoryEntity);

        // Update Category
        categoryEntity.setName("updated");
        underTest.save(categoryEntity);

        // Find updated Category
        Optional<CategoryEntity> result = underTest.findById(categoryEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(categoryEntity.getId());
        assertThat(result.get().getName()).isEqualTo("updated");
    }

    @Test
    public void testThatCategoryCanBeDeleted(){
        // Create Category
        CategoryEntity categoryEntity = TestDataUtils.createTestCategoryEntityA();
        underTest.save(categoryEntity);

        // Delete Category
        underTest.deleteById(categoryEntity.getId());

        // Find deleted Category
        Optional<CategoryEntity> result = underTest.findById(categoryEntity.getId());

        assertThat(result).isNotPresent();
    }

}
