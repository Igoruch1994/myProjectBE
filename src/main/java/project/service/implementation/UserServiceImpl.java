package project.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.config.orika.OrikaBeanMapper;
import project.dto.UserDTO;
import project.entity.User;
import project.repository.UserRepository;
import project.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrikaBeanMapper orikaBeanMapper;

    private static final Logger LOGGER = Logger.getLogger(AuthServiceImpl.class);

    @Override
    public List<UserDTO> getAllUsers() {
        LOGGER.info("Get All Users ");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        LOGGER.error("User " + userName);
        return userRepository.findAll().stream()
                .map(u -> orikaBeanMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(final long id) {
        final User user = userRepository.findOne(id);
        return Objects.nonNull(user) ? UserDTO.toDTO(user) : null;
    }
}
