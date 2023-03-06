package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Category;
import spring.factory.BookFactory;
import spring.factory.CategoryFactory;


@RestController
@RequestMapping("/api/admin/category")
public class AdminCategory {
    @Autowired
    CategoryFactory categoryFactory;
    @Autowired
    BookFactory bookFactory;

    @PostMapping(value = "/create")
    public ResponseEntity<String> saveCategory(@RequestBody Category category) throws Exception {
        categoryFactory.saveCategory(category);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public ResponseEntity<String> removeCategory(@PathVariable(value = "id", required = false) String categoryId) throws Exception {
        if (categoryFactory.findByCategoryId(categoryId) != null) {
//            bookService.removeBookByCategory(categoryId);
            categoryFactory.removeByCategoryId(categoryId);
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> editeCategory(@RequestBody Category category) {
        Category categories = categoryFactory.findByCategoryId(category.getCategoryId());
        categories.setNameCate(category.getNameCate());
        categoryFactory.saveCategory(categories);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

}
