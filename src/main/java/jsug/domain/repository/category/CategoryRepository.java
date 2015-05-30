package jsug.domain.repository.category;

import jsug.domain.model.Category;
import jsug.domain.repository.SqlFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    SqlFinder sql;

    public List<Category> findAll() {
        return jdbcTemplate.query(sql.get("sql/category/findAll.sql"),
                (rs, i) -> Category.builder()
                        .categoryId(rs.getInt("category_id"))
                        .categoryName(rs.getString("category_name"))
                        .build());
    }
}
