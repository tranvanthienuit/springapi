package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.UserList;
import spring.Service.RoleService;
import spring.Service.UserService;

import java.util.List;

@RestController
public class User {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @DeleteMapping(value = {"/admin/xoa-user/{userId}", "/admin/xoa-user","/seller/xoa-user/{userId}", "/seller/xoa-user"})
    public ResponseEntity<String> removeUser(@PathVariable(value = "userId", required = false) String userId) throws Exception {
        if (userService.findUserByUserId(userId) != null) {
            if (!userService.countUser().equals(1)) {
                userService.removeUserByUserId(userId);
                return new ResponseEntity<>("successful",HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-user/{page}", "/admin/xem-tat-ca-user","/seller/xem-tat-ca-user/{page}", "/seller/xem-tat-ca-user"})
    public ResponseEntity<UserList> getAllUser(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        UserList userList = new UserList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<spring.Entity.Model.User> userPage = userService.getAllUser(pageable);
        List<spring.Entity.Model.User> userPageContent = userPage.getContent();
        if (userPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            userList.setUserList(userPageContent);
            userList.setCount(userService.getAllUsers().size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

}