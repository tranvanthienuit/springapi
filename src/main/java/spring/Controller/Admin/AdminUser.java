package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Role;
import spring.Entity.User;
import spring.factory.RoleFactory;
import spring.factory.UserFactory;

import java.util.Map;

@RestController
public class AdminUser {
    @Autowired
    UserFactory userFactory;
    @Autowired
    RoleFactory roleFactory;


    @PostMapping(value = {"/api/admin/{id}"})
    public ResponseEntity<?> editeRole(@PathVariable("id") String userId, @RequestBody Map<String, Object> roleName) {
        User user = userFactory.findUserByUserId(userId);

        Role role = roleFactory.findRoleByName(roleName.get("roleName").toString());
        user.setRole(role);
        userFactory.saveUser(user);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
