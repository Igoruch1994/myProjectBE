package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String testService() {
        return "Hello from ES2!. Jenkins works well";
    }

}
