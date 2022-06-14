package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Category;
import spring.Repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void removeCategoriesByCategoryId(String categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        categoryRepository.delete(category);
    }

    public Category findByCategoryId(String categoryid) {
        return categoryRepository.findByCategoryId(categoryid);
    }

    public Page<Category> getAllCate(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
