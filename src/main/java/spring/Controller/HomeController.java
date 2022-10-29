package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.*;
import spring.Entity.Model.*;
import spring.JWT.JwtTokenProvider;
import spring.Sercurity.userDetail;
import spring.Service.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/app")
public class HomeController {
    @Autowired
    BookService booksService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    //    @Autowired
//    TokenService tokenService;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    RatingService ratingService;
    @Autowired
    BlogService blogService;

    @GetMapping(value = {"/home/page/{number}"})
    public ResponseEntity<BookReturn> home(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        BookReturn bookReturn = new BookReturn();


        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        //lấy tất cả các sách và số lưởng tổng
        Pageable pageable = PageRequest.of(page, 12);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(booksService.getAllBook().size());

        // lấy sách dựa trên những phiếu mượn sách trước
        Pageable pageable1 = PageRequest.of(0,12);
        List<Book> bookOrder = orderssDeSevice.getBookFromBorrDe(pageable1);

        // lấy sách dựa trên số sách mà khách hàng đã mượn

        // lấy sách dựa trên số sao đánh giá cao nhất
        List<Book> bookRating = booksService.getBookByRating(pageable1);

        bookReturn.setBookList(bookList);
        bookReturn.setBookOder(bookOrder);
        bookReturn.setBookRating(bookRating);
        return new ResponseEntity<>(bookReturn, HttpStatus.OK);
//        }


    }


    @GetMapping(value = {"library/page/{number}"})
    public ResponseEntity<BookReturn> shop(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        BookReturn bookReturn = new BookReturn();


        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        //lấy tất cả các sách và số lưởng tổng
        Pageable pageable = PageRequest.of(page, 12);
        Page<Book> bookPage = booksService.getAllBooks(pageable);
        List<Book> bookPageContent = bookPage.getContent();
        bookList.setBookList(bookPageContent);
        bookList.setCount(booksService.getAllBook().size());

        // lấy sách dựa trên những phiếu mượn sách trước
        Pageable pageable1 = PageRequest.of(0, 12);
        List<Book> bookOrder = orderssDeSevice.getBookFromBorrDe(pageable1);

        // lấy sách dựa trên số sao đánh giá cao nhất
        List<Book> bookRating = booksService.getBookByRating(pageable1);

        bookReturn.setBookList(bookList);
        bookReturn.setBookOder(bookOrder);
        bookReturn.setBookRating(bookRating);

        return new ResponseEntity<>(bookReturn, HttpStatus.OK);
//        }

    }


    @GetMapping(value = {"category/getDetail/{id}"})
    public ResponseEntity<?> getCategoryBook(@RequestBody @PathVariable(value = "id", required = false) String CategoryId) throws Exception {
        if (CategoryId == null) {
            return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
        } else {
            Category categoryList = categoryService.findByCategoryId(CategoryId);
            if (categoryList == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }


    @PostMapping("login")
    public LoginResponse getlogin(@RequestBody LoginReQuest loginReQuest) throws Exception {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginReQuest.getNameUser(),
                loginReQuest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userDetail user = (userDetail) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getUsername());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId(), user.getUsername());
        User user1 = userService.findUserByUserId(user.getUserId());
        return new LoginResponse(accessToken, refreshToken);
    }

    @PostMapping("register")
    public ResponseEntity<?> getregister(@RequestBody User user) throws Exception {
        String username;
        if (userService.findUserName(user.getNameUser()) == null) {
            username = "";
        } else {
            username = userService.findUserName(user.getNameUser()).getNameUser();
        }
        if (user.getNameUser().equals(username)) {
            return new ResponseEntity<>("account exist", HttpStatus.OK);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = user.getRole();
        if (role == null)
            role = roleService.findRoleByName("USER");
        user.setRole(role);
        LocalDate ldate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(ldate);
        user.setDayAdd(date);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("category/list")
    public ResponseEntity<CateList> getAllCategory() {
        CateList cateList = new CateList();
        cateList.setCategoryList(categoryService.getAllCategory());
        cateList.setCount(categoryService.getAllCategory().size());
        return new ResponseEntity<>(cateList, HttpStatus.OK);
    }

    @GetMapping("blog/list")
    public ResponseEntity<List<Blog>> findAllBlog() {
        return new ResponseEntity<>(blogService.findAllBlog(), HttpStatus.OK);
    }
}
