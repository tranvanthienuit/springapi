package spring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Roles")
@Data
@RequiredArgsConstructor
public class Role {
    //Role: id, name
    @Id
    @Column(name = "RoleId")
    private String roleId;
    @Column(name = "NameRole")
    private String nameRole;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;
}