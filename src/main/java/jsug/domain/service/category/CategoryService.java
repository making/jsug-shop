package jsug.domain.service.category;

import jsug.domain.model.Category;
import jsug.domain.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Cacheable("category")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
