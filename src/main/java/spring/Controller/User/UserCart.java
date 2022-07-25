package spring.Controller.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.*;
import spring.Entity.Model.*;
import spring.Repository.MailService;
import spring.Sercurity.userDetail;
import spring.Service.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
public class UserCart {
    @Autowired
    BookService bookService;
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    RoleService roleService;

    @PostMapping(value = {"/user/mua-sach", "/mua-sach"})
    public ResponseEntity<List<CartBook>> Orderss(@RequestBody Cart objectCart) throws Exception {
        List<CartBook> cart = objectCart.getCartBooks();

        User user = new User();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(auth.getName(), "anonymousUser")) {
            userDetail user1 = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userService.findUserByUserId(user1.getUserId());
        } else {
            user = objectCart.getUser();
        }
        if (user.getUserId() == null) {
            Role role = roleService.findRoleByName("USER");
            user.setRole(role);
        }
        user.setAddress(objectCart.getUser().getAddress());
        user.setTelephone(objectCart.getUser().getTelephone());

        LocalDate ldate = LocalDate.now();
        Date date = Date.valueOf(ldate);
        Orderss orderss = new Orderss();
        orderss.setOrderssDate(date);
        orderss.setUser(user);
        Integer totalBook = 0;
        Double totalPrice = 0.0;


        for (CartBook cartBook : cart) {
            totalBook = totalBook + cartBook.getQuantity();
            totalPrice = totalPrice + cartBook.getTotal();
            OrderssDetail orderssDetail = new OrderssDetail();
            for (Book book1 : bookService.getAllBook()) {
                // tìm từng cuốn sách
                Book book = bookService.findBookByBookId(cartBook.getBooks());
                //so sánh có trong list sach không
                if (book.getBookId().equals(book1.getBookId())) {
                    //nếu sách nhiều hơn mua thì lấy sách trừ mua
                    if (book1.getCount() - cartBook.getQuantity() >= 0) {
                        orderssDetail.setCount(cartBook.getQuantity());
                        orderssDetail.setTotal((Double) cartBook.getTotal());
                        bookService.findBookAndUpdate(book1.getCount() - cartBook.getQuantity(), book.getBookId());
                    } else {
                        bookService.findBookAndUpdate(0, book.getBookId());
                        orderssDetail.setTotal((Double) cartBook.getTotal());
                        orderssDetail.setCount(book1.getCount());
                    }
                }
            }
            sendEmail sendEmail = new sendEmail(cart,user,totalPrice,totalBook);
            Thread thread = new Thread(sendEmail);
            thread.start();

            orderss.setTotalBook(totalBook);
            orderss.setStatus("chưa giao hàng");
            orderss.setPay(objectCart.getPay());
            orderss.setTelephone(user.getTelephone());
            orderss.setAddress(user.getAddress());
            orderss.setNameUser(user.getNameUser());
            orderss.setFullName(user.getFullName());
            orderss.setTotalPrice(totalPrice);
            orderssSevice.saveOrderss(orderss);


            orderssDetail.setOrderss(orderss);
            Book book = bookService.findBookByBookId(cartBook.getBooks());
            orderssDetail.setBook(book);
            orderssDeSevice.saveOrderssDe(orderssDetail);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @Data
    @AllArgsConstructor
    private class sendEmail implements Runnable{
        private List<CartBook> cart;
        private User user;
        private Double totalPrice;
        private Integer totalBook;


        @Override
        public void run() {
            try {
                Mail mail = new Mail();
                mail.setMailFrom("uitsneaker@gmail.com");
                mail.setMailTo(user.getEmail());
                mail.setMailSubject("THÔNG TIN HÓA ĐƠN");
                String html = "    <style>\n" +
                        ".styled-table {\n" +
                        "border-collapse: collapse;\n" +
                        "margin: 25px 0;\n" +
                        "font-size: 0.9em;\n" +
                        "font-family: sans-serif;\n" +
                        "width: 100%;\n" +
                        "box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);\n" +
                        "}\n" +
                        ".styled-table thead tr {\n" +
                        "background-color: #81afaf;\n" +
                        "color: #ffffff;\n" +
                        "text-align: left;\n" +
                        "}\n" +
                        ".styled-table th,\n" +
                        ".styled-table td {\n" +
                        "text-align: center;" +
                        "padding: 12px 15px;\n" +
                        "size: fixed;\n" +
                        "}\n" +
                        ".styled-table tbody tr {\n" +
                        "    border-bottom: 1px solid #dddddd;\n" +
                        "}\n" +
                        "\n" +
                        ".styled-table tbody tr:nth-of-type(even) {\n" +
                        "    background-color: #f3f3f3;\n" +
                        "}\n" +
                        "\n" +
                        ".styled-table tbody tr:last-of-type {\n" +
                        "    border-bottom: 2px solid #009879;\n" +
                        "}\n" +
                        ".styled-table tbody tr.active-row {\n" +
                        "    font-weight: bold;\n" +
                        "    color: #009879;\n" +
                        "}\n" +
                        "    </style>" + "<h2>TÊN CỦA KHÁCH HÀNG: " + user.getFullName() + "</h2></br>" +
                        "<table class=\"styled-table\">\n" +
                        "    <thead>\n" +
                        "        <tr>\n" +
                        "            <th>Tên Sách</th>\n" +
                        "            <th>Số Lượng</th>\n" +
                        "            <th>Giá</th>\n" +
                        "        </tr>\n" +
                        "    </thead><tbody>";
                String table = "";
                for (CartBook cartBook : cart) {
                    Book book = bookService.findBookByBookId(cartBook.getBooks());
                    table = table + "<tr>\n" +
                            "<td>" + book.getNameBook() + "</td>\n" +
                            "<td>" + cartBook.getQuantity() + "</td>\n" +
                            "<td>" + cartBook.getTotal() + "</td>\n" +
                            "</tr>";
                }
                html = html + table + " </tbody>\n" +
                        "</table>" + "</br><h3>TỔNG GIÁ TIỀN CỦA BẠN LÀ : " + totalPrice + "</h3></br><h3>TỔNG SỐ SÁCH BẠN ĐÃ MUA : " + totalBook + "</h3>";
                mail.setMailContent(html);
                mailService.sendEmail(mail);
            } catch (Exception e){
                System.out.println("Exception is caught");
            }
        }
    }
}
