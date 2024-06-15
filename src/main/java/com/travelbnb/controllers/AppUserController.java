package com.travelbnb.controllers;

import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.JWTTokenPayload;
import com.travelbnb.payloads.LoginPayload;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appUser")
public class AppUserController {


    private AppUserService appUserService;
    private AppUserRepository appUserRepository;
    @Autowired
    public AppUserController(AppUserService appUserService,AppUserRepository appUserRepository)
    {
        this.appUserService=appUserService;
        this.appUserRepository=appUserRepository;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUsers(@RequestBody AppUserPayload appUserPayload)
    {
        if(appUserRepository.existsByEmail(appUserPayload.getEmail()))
        {
            return new ResponseEntity<>("Email Already Exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(appUserRepository.existsByUsername(appUserPayload.getUsername()))
        {
            return new ResponseEntity<>("UserName Already Exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        appUserPayload.setPassword(BCrypt.hashpw(appUserPayload.getPassword(),BCrypt.gensalt(5)));
        AppUserPayload aupl = appUserService.addUsers(appUserPayload);
        return new ResponseEntity<>(aupl, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginPayload loginPayload)
    {
        String token = appUserService.verifyLogin(loginPayload);
        if(token!=null){
            JWTTokenPayload jwtTokenPayload = new JWTTokenPayload();
            jwtTokenPayload.setType("JWT Token");
            jwtTokenPayload.setToken(token);
            return new ResponseEntity<>(jwtTokenPayload,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.OK);
    }
}
