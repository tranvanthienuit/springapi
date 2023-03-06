package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.factory.RoleFactory;
import spring.factory.UserFactory;
import spring.model.UserList;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/seller/user", "/api/admin/user"})
public class User {
    @Autowired
    UserFactory userFactory;
    @Autowired
    RoleFactory roleFactory;


    @DeleteMapping(value = {"delete/{id}}"})
    public ResponseEntity<String> removeUser(@PathVariable(value = "id", required = false) String userId) throws Exception {
        if (userFactory.findUserByUserId(userId) != null) {
            if (!userFactory.countUser().equals(1)) {
                userFactory.removeUserByUserId(userId);
                return new ResponseEntity<>("successful", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"page/{number}"})
    public ResponseEntity<UserList> getAllUser(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        UserList userList = new UserList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<spring.Entity.User> userPage = userFactory.getAllUser(pageable);
        List<spring.Entity.User> userPageContent = userPage.getContent();
        if (userPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            userList.setUserList(userPageContent);
            userList.setCount(userFactory.getAllUsers().size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"search"})
    public ResponseEntity<?> findUser(@RequestBody Map<String, Object> keyword) {
        if (keyword != null)
            return new ResponseEntity<>(userFactory.findUser(keyword.get("keyword").toString()), HttpStatus.OK);
        return new ResponseEntity<>("Không có người dùng nào cả", HttpStatus.OK);
    }
}
