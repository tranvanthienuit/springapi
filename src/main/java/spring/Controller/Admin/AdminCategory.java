package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.CateList;
import spring.Entity.Model.Categories;
import spring.Service.CategoryService;

import java.util.List;


@RestController
public class AdminCategory {
    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "/admin/luu-loai-sach")
    public ResponseEntity<String> saveCategory(@RequestBody Categories categories) throws Exception {
        categoryService.saveCategory(categories);
        return new ResponseEntity<>("successful",HttpStatus.OK);
    }

    @DeleteMapping(value = {"/admin/xoa-loai-sach/{idCategory}", "/admin/xoa-loai-sach"})
    public ResponseEntity<String> removeCategory(@PathVariable(value = "idCategory", required = false) String categoryId) throws Exception {
        if (categoryService.findByCategoryId(categoryId) != null) {
            categoryService.removeCategoriesByCategoryId(categoryId);
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-loai-sach/{page}", "/admin/xem-tat-ca-loai-sach"})
    public ResponseEntity<CateList> getAllCate(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        CateList cateList = new CateList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<Categories> categoriesList = categoryService.getAllCate(pageable);
        List<Categories> categoriesListContent = categoriesList.getContent();
        if (categoriesListContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            cateList.setCategoriesList(categoriesListContent);
            cateList.setCount(categoryService.getAllCategory().size());
            return new ResponseEntity<>(cateList, HttpStatus.OK);
        }
    }
    @GetMapping("/admin/category")
    public ResponseEntity<CateList> getAllCategory(){
        CateList cateList = new CateList() ;
        cateList.setCategoriesList(categoryService.getAllCategory());
        cateList.setCount(categoryService.getAllCategory().size());
        return new ResponseEntity<>(cateList,HttpStatus.OK);
    }

}
