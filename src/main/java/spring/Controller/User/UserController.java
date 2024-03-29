package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Orderss;
import spring.Entity.OrderssDetail;
import spring.Entity.User;
import spring.JWT.JwtTokenProvider;
import spring.Sercurity.userDetail;
import spring.factory.OrderssDeFactory;
import spring.factory.OrderssFactory;
import spring.factory.UserFactory;
import spring.model.Mail;
import spring.repository.MailService;
import spring.repository.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"api/app"})
public class UserController {
    @Autowired
    UserFactory userFactory;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    OrderssFactory orderssFactory;
    @Autowired
    OrderssDeFactory orderssDeFactory;

    @PostMapping(value = {"update/profile"})
    public ResponseEntity<User> editInfo(@RequestBody(required = false) User user) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = userFactory.findUserByUserId(user.getUserId());
        if (user.getFullName() != null) {
            user1.setFullName(user.getFullName());
//            userService.editUserFullname(user.getFullName(), userDetail.getUserId());
        }
        if (user.getUsername() != null) {
            user1.setUsername(user.getUsername());
//            userService.editUserName(user.getNameUser(), userDetail.getUserId());
        }
        if (user.getAddress() != null) {
            user1.setAddress(user.getAddress());
//            userService.editUserAdress(user.getAddress(), userDetail.getUserId());
        }
        if (user.getTelephone() != null) {
            user1.setTelephone(user.getTelephone());
//            userService.editUserTelephone(user.getTelephone(), userDetail.getUserId());
        }
        if (user.getEmail() != null) {
            user1.setEmail(user.getEmail());
//            userService.editUserEmail(user.getEmail(), userDetail.getUserId());
        }
        if (user.getSex() != null) {
            user1.setSex(user.getSex());
//            userService.editUserSex(user.getSex(), userDetail.getUserId());
        }
        userFactory.saveUser(user1);
        user1 = userFactory.findUserByUserId(user.getUserId());
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @PostMapping(value = {"update/avatar"})
    public ResponseEntity<User> editImg(@RequestBody Map<String, Object> image) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(userDetail.getUserId());
        user.setImage(image.get("image").toString());
        userFactory.saveUser(user);
//        userService.editImage(getImageByte(), user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = {"update/password"})
    public ResponseEntity<?> editPassword(@RequestBody Map<String, Object> password) {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(userDetail.getUserId());
        if (passwordEncoder.matches(password.get("oldPassword").toString(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password.get("newPassword").toString()));
            userFactory.saveUser(user);
//            userService.editUserPass(passwordEncoder.encode(newPassword), user.getUserId());
            return new ResponseEntity<>("thanh cong roi ban ey", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mat khau cu sai", HttpStatus.OK);
        }
    }

    public byte[] getImageByte() {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(userDetail.getUserId());
        if (user.getImage() == null)
            return null;
        return user.getImage().getBytes();
    }

    @PostMapping(value = {"forget/password"})
    public ResponseEntity<?> forgetPass(@RequestBody Map<String, Object> email) {
        String Email = email.get("email").toString();
        if (mailService.checkMail(Email)) {
            User user = userRepository.findByEmail(Email);
            String token = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getUsername());
            Mail mail = new Mail();
            mail.setMailFrom("uitsneaker@gmail.com");
            mail.setMailTo(Email);
            mail.setMailSubject("Quên password");
            mail.setMailContent("<h1>Reset Password</h1></br></br>\n" +
                    "<h2>Xin chào quý khách mật khẩu của bạn đang được reset.</br>\n" +
                    "\tHãy nhấp vào link dưới đây để cài đặt mật khẩu lại. Cảm ơn quý khách\n</h2>\n" +
//                    "<h3>Link: </h3>" + "<a href=https://uitbook.netlify.app/cai-dat-mat-khau-moi/"+Email+"/" + token + ">" + email + "</a>");
                    "<h3>Link: </h3>" + "<a href=http://localhost:3000/cai-dat-mat-khau-moi/" + Email + "/" + token + ">" + email + "</a>");

            mailService.sendEmail(mail);
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("không có mail nào trong tài khoản", HttpStatus.OK);
    }

    @PostMapping(value = {"reset/password/{token}"})
    public ResponseEntity<?> setPassword(@PathVariable("token") String token, @RequestBody Map<String, Object> emailAndPass) {
        if (jwtTokenProvider.validateToken(token)) {
            userFactory.setPassword(passwordEncoder.encode(emailAndPass.get("password").toString()), emailAndPass.get("email").toString());
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("error", HttpStatus.OK);
    }

    @GetMapping(value = {"profile"})
    public ResponseEntity<User> getUser() throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userFactory.findUserByUserId(userDetail.getUserId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("user/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable(name = "email") String email) {
        if (email != null)
            return new ResponseEntity<>(userFactory.findUser(email), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"order"})
    public ResponseEntity<List<Orderss>> findOrderss(@RequestBody Map<String, Object> keysearch) {
        if (orderssFactory.findOrder(keysearch.get("keysearch").toString()).size() == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<Orderss> orderssList = orderssFactory.findOrder(keysearch.get("keysearch").toString());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"order-detail/{id}"})
    public ResponseEntity<?> findOrderDe(@PathVariable("id") String orderDeId) {
        if (orderDeId == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<OrderssDetail> orderssDetail = orderssDeFactory.findOrderssDe(orderDeId);
            return new ResponseEntity<>(orderssDetail, HttpStatus.OK);
        }
    }
}
