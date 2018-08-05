package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ResponseDTO;
import project.service.AuthService;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/reset")
    public ResponseEntity<ResponseDTO> sendEmail() {
        return authService.sendChangePasswordMail("igor.bardyuk@sombrainc.com");
    }

}
