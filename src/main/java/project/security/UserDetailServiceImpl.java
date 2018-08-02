package project.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import project.entity.enums.RoleType;
import project.repository.UserRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        LOGGER.info("Init parameters method loadUserByUsername: "  + email);

        project.entity.User user = userRepository.findUserByEmail(email);
        org.springframework.security.core.userdetails.User springUser;
        if (user == null || !Objects.equals(user.getRole(), RoleType.ROLE_ADMIN)) {
            throw new BadCredentialsException(email);
        }
        final RoleType role = user.getRole();
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        springUser = new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
        return springUser;
    }
}
