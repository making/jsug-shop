package jsug.domain.repository.category;

import jsug.domain.model.Category;
import jsug.domain.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {
        "/sql/drop-tables.sql",
        "/db/migration/V1__create-schema.sql",
        "/sql/insert-category.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testFindAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories, hasSize(4));
        assertThat(categories.get(0).getCategoryName(), is("本"));
        assertThat(categories.get(1).getCategoryName(), is("音楽"));
        assertThat(categories.get(2).getCategoryName(), is("家電"));
        assertThat(categories.get(3).getCategoryName(), is("パソコン"));
    }
}