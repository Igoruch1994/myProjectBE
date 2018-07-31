package project.service;

import org.springframework.http.ResponseEntity;
import project.dto.LoginDTO;
import project.dto.ResponseDTO;

public interface AuthService {

    ResponseEntity<ResponseDTO> login(LoginDTO loginDTO);

}
