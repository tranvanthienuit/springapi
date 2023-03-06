package spring.Sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.Entity.User;
import spring.factory.UserFactory;

@Service
public class userServiceDetail implements UserDetailsService {
    @Autowired
    UserFactory userFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFactory.findUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDetail.createUserDetail(user);
    }

}