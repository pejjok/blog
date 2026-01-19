package com.pejjok.blog.repositories;

import com.pejjok.blog.TestDataUtils;
import com.pejjok.blog.domain.entities.TagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagRepositoryTests{
    private final TagRepository underTest;

    @Autowired
    public TagRepositoryTests(TagRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatTagCanBeCreatedAndReturned(){
        //Create Tag
        TagEntity tagEntity = TestDataUtils.createTestTagEntityA();
        underTest.save(tagEntity);

        // Find Created Tag
        Optional<TagEntity> result = underTest.findById(tagEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tagEntity);
    }

    @Test
    public void testThatMultipleTagsCanBeCreatedAndReturned(){
        // Create Tags
        TagEntity tagEntityA = TestDataUtils.createTestTagEntityA();
        TagEntity tagEntityB = TestDataUtils.createTestTagEntityB();
        TagEntity tagEntityC = TestDataUtils.createTestTagEntityC();
        underTest.save(tagEntityA);
        underTest.save(tagEntityB);
        underTest.save(tagEntityC);

        //Find All Tags
        List<TagEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).containsExactly(tagEntityA,tagEntityB,tagEntityC);
    }


    @Test
    public void testThatTagCanBeUpdated(){
        // Create Tag
        TagEntity tagEntityA = TestDataUtils.createTestTagEntityA();
        underTest.save(tagEntityA);

        // Update Tag
        tagEntityA.setName("updated");
        underTest.save(tagEntityA);

        // Find updated Tag
        Optional<TagEntity> result = underTest.findById(tagEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(tagEntityA.getId());
        assertThat(result.get().getName()).isEqualTo("updated");
    }

    @Test
    public void testThatTagCanBeDeleted(){
        // Create Category
        TagEntity tagEntityA = TestDataUtils.createTestTagEntityA();
        underTest.save(tagEntityA);

        // Delete Category
        underTest.deleteById(tagEntityA.getId());

        // Find deleted Category
        Optional<TagEntity> result = underTest.findById(tagEntityA.getId());

        assertThat(result).isNotPresent();
    }
}
