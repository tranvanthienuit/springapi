package spring.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Model.User;
import spring.Sercurity.userDetail;
import spring.Service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sua-thong-tin")
    public ResponseEntity<String> editInfo(@RequestBody(required = false) User user) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = userService.findUserByUserId(userDetail.getUserId());
        String info=null;
        if(user.getFullName()!=null){
            userService.editUserFullname(user.getFullName(), user.getUserId());
            info = user.getFullName();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        if (user.getNameUser() != null) {
            userService.editUserName(user.getNameUser(), user1.getUserId());
            info = user.getNameUser();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        if (user.getAddress() != null) {
            userService.editUserAdress(user.getAddress(), user1.getUserId());
            info = user.getAddress();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        if (user.getTelephone() != null) {
            userService.editUserTelephone(user.getTelephone(), user1.getUserId());
            info = user.getTelephone();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        if (user.getEmail() != null) {
            userService.editUserEmail(user.getEmail(), user1.getUserId());
            info = user.getEmail();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        if (user.getSex() != null) {
            userService.editUserSex(user.getSex(), user1.getUserId());
            info = user.getSex();
            return new ResponseEntity<>(info,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cap-nhat-anh")
    public ResponseEntity<User> editImg(@RequestBody String image) throws Exception {
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        byte[] newImage = image.getBytes();
        userService.editImage(newImage, user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/sua-mat-khau/{old-password}/{new-password}")
    public ResponseEntity<?> editPassword(@RequestBody @PathVariable("old-password") String oldPassword,@RequestBody @PathVariable("new-password") String newPassword){
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        if (!oldPassword.equals(user.getPassword())){
            return new ResponseEntity<>("mat khau cu sai",HttpStatus.BAD_REQUEST);}
        else {
            userService.editUserPass(newPassword,user.getUserId());
            return new ResponseEntity<>("thanh cong roi ban ey",HttpStatus.OK);
        }
    }
}
