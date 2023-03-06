package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    List<User> userList;
    int count;
}
