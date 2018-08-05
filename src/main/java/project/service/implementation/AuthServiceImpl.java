package project.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import project.service.EmailService;
import project.util.Checker;
import project.util.EmailGenerator;

import java.util.Objects;

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

    @Autowired
    private EmailService emailService;

    @Value("${email.forgot.password.template}")
    private String SUCCESS_PASSWORD_RESET;

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
    @Override
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

    public ResponseEntity<ResponseDTO> sendChangePasswordMail(final String email) {
        final User user = userRepository.findUserByEmail(email);

       /* if (Objects.isNull(user)) {
            return  createFailureResponse("User with such email does not exist !");
        }*/

        /*String randomKey = RandomStringUtils.randomNumeric(20);
        user.setTemporaryData(randomKey);
        userRepository.save(user);
        */
        final String body = generateMailBodyToForgotPasswordRequest(user);

        emailService.sendHtmlEmail(email,"Change password", body);
        return createSuccessResponse("Success. Check your mail");
    }

    private ResponseEntity<ResponseDTO> checkCredentials(final RegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm()))
            return createFailureResponse("Passwords not match", HttpStatus.BAD_REQUEST);
        if (userRepository.findUserByEmail(dto.getEmail()) != null)
            return createFailureResponse("Email already used", HttpStatus.BAD_REQUEST);
        else return createSuccessResponse();
    }

    private String generateMailBodyToForgotPasswordRequest(final User user) {
        return EmailGenerator
                .generateConfirmationMail("testUser", "test",
                        "key", "rootPath", SUCCESS_PASSWORD_RESET,
                        "Change Password");
    }

}
