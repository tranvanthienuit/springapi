package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.Entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

    @Query("select u from Blog u where u.blogId=:blogId")
    Blog findBlogByBlogId(String blogId);
}
