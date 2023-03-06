package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Book;
import spring.Entity.Comment;
import spring.Entity.User;
import spring.Sercurity.userDetail;
import spring.factory.BookFactory;
import spring.factory.CommentFactory;
import spring.factory.UserFactory;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/user/comment")
public class UserComment {
    @Autowired
    CommentFactory commentFactory;
    @Autowired
    UserFactory userFactory;
    @Autowired
    BookFactory bookFactory;

    @PostMapping("create/{bookId}")
    public ResponseEntity<?> saveComment(@PathVariable(name = "bookId") String bookId, @RequestBody Comment comment) {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(user1.getUserId());
        comment.setUser(user);
        comment.setBook(bookFactory.findBookByBookId(bookId));
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        comment.setDayAdd(date);
        Book book = bookFactory.findBookByBookId(bookId);
        book.setCmt(true);
        bookFactory.saveBook(book);
        commentFactory.saveComment(comment);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @PostMapping(value = "delete")
    public ResponseEntity<?> deleteComment(@RequestBody Map<String, Object> comment) {
        userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(user1.getUserId());
        commentFactory.deleteUserAndComment(user.getUserId(), comment.get("commentId").toString());
        Book book = bookFactory.findBookByBookId(comment.get("bookId").toString());
        book.setCmt(false);
        bookFactory.saveBook(book);
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") String commentId, @RequestBody Map<String, Object> content) {
        commentFactory.updateComment(commentId, content.get("content").toString());
        return new ResponseEntity<>("successfull", HttpStatus.OK);
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<?> commentBook(@PathVariable(name = "bookId") String bookId) {
        return new ResponseEntity<>(commentFactory.findAllByBookId(bookId), HttpStatus.OK);
    }
}
