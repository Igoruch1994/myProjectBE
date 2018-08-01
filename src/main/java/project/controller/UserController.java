package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.LoginDTO;
import project.dto.ResponseDTO;
import project.dto.UserDTO;
import project.service.AuthService;
import project.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getById")
    public UserDTO greeting(@RequestParam(value="id") final long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}