package project.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.config.orika.OrikaBeanMapper;
import project.dto.LoginDTO;
import project.dto.RegistrationDTO;
import project.dto.ResponseDTO;
import project.entity.User;
import project.repository.UserRepository;
import project.service.AuthService;
import project.util.Checker;

import static project.dto.ResponseDTO.createFailureResponse;
import static project.dto.ResponseDTO.createSuccessResponse;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrikaBeanMapper orikaBeanMapper;

    @Override
    public ResponseEntity<ResponseDTO> login(final LoginDTO loginDTO) {
        LOGGER.info("Sign in request: " + loginDTO.getEmail());
        if (!Checker.checkParamsOnNull(loginDTO)) {
            return createFailureResponse("Some fields is empty", HttpStatus.BAD_REQUEST);
        }
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail().trim(), loginDTO.getPassword().trim());

        try {
            final Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (final AuthenticationException e) {
            LOGGER.error("Failed to login: " + loginDTO.getEmail(), e);
            throw new BadCredentialsException("Incorrect login or password");
        }
        LOGGER.info("Login was successful: " + userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getEmail());
        return createSuccessResponse("Login was successful");
    }

    @Transactional
    public ResponseEntity<ResponseDTO> signUp(final RegistrationDTO registration) {
        LOGGER.info("Sign up request: " + registration.getEmail());
        if (!Checker.checkParamsOnNull(registration)) {
            LOGGER.error("Some field is empty");
            return createFailureResponse("Some field is empty", HttpStatus.BAD_REQUEST);
        }

        final User user;
        final ResponseEntity<ResponseDTO> response = checkCredentials(registration);
        if (!response.getBody().isSuccess()) {
            LOGGER.error("Bad request params: " + registration.getEmail());
            return response;
        } else {
            user = orikaBeanMapper.map(registration, User.class);
            userRepository.save(user);
            login(new LoginDTO(registration.getEmail(), registration.getPassword()));
            LOGGER.info("User was successful created: " + user);
            return createSuccessResponse("User was successfully created", HttpStatus.OK);
        }
    }

    private ResponseEntity<ResponseDTO> checkCredentials(RegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm()))
            return createFailureResponse("Passwords not match", HttpStatus.BAD_REQUEST);
        if (userRepository.findUserByEmail(dto.getEmail()) != null)
            return createFailureResponse("Email already used", HttpStatus.BAD_REQUEST);
        else return createSuccessResponse();
    }

}
