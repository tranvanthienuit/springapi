package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Role;
import spring.Entity.Model.User;
import spring.Service.RoleService;
import spring.Service.UserService;

@RestController
public class AdminUser {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @PostMapping("/admin/{userId}")
    public ResponseEntity<?> editeRole(@PathVariable("userId") String userId, @RequestBody String roleName) {
        User user = userService.findUserByUserId(userId);
        Role role = roleService.fineRoleByName(roleName);
        user.setRole(role);
        userService.saveUser(user);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
