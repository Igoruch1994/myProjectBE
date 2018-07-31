package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.LoginDTO;
import project.dto.ResponseDTO;
import project.entity.User;
import project.service.AuthService;
import project.service.UserService;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getById")
    public User greeting(@RequestParam(value="id") final long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}