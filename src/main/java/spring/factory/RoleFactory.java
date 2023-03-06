package spring.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Role;
import spring.repository.RoleRepository;

@Service
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
