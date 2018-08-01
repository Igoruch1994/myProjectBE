package project.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dto.UserDTO;
import project.repository.UserRepository;
import project.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(final long id) {
        return UserDTO.toDTO(userRepository.findOne(id));
    }
}
