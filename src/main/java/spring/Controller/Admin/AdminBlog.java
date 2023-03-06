package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Blog;
import spring.Entity.User;
import spring.Sercurity.userDetail;
import spring.factory.BlogFactory;
import spring.factory.UserFactory;

import java.time.LocalDate;

@RestController
@RequestMapping("api/admin/blog")
public class AdminBlog {
    @Autowired
    BlogFactory blogFactory;
    @Autowired
    UserFactory userFactory;


    @PostMapping("/create")
    public ResponseEntity<?> saveBlog(@RequestBody Blog blog) throws Exception {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(user1.getUserId());
        blog.setUser(user);
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        blog.setDayAdd(date);
        blogFactory.saveBlog(blog);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@RequestBody @PathVariable(name = "id") String blogId) {
        blogFactory.findAndDeleteBlog(blogId);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateBlog(@RequestBody Blog blog) {
        Blog blog1 = blogFactory.findBlog(blog.getBlogId());
        if (blog.getContent() != null)
            blog1.setContent(blog.getContent());
        if (blog.getContext() != null)
            blog1.setContext(blog.getContext());
        if (blog.getTitle() != null)
            blog1.setTitle(blog.getTitle());
        if (blog.getImage() != null)
            blog1.setImage(blog.getImage());
        blogFactory.saveBlog(blog1);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }

}
