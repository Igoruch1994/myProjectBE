package project.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<UserDTO> getAllUsers() {
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
