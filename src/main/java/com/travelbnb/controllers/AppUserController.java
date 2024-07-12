package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.JWTTokenPayload;
import com.travelbnb.payloads.LoginPayload;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    Updating

    @PutMapping("/{userId}")
    public ResponseEntity<AppUserEntity> updateUser(@PathVariable long userId,
                                                    @RequestBody AppUserPayload appUserPayload)
    {
        AppUserEntity app = appUserService.updateUserDetails(userId,appUserPayload);
        return new ResponseEntity<>(app,HttpStatus.OK);
    }

//    Deleting

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        try {
            appUserService.deleteUser(userId);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        } catch (RuntimeException e)
        {
            return new  ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
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

//    Applying Pagination and Sorting
    @GetMapping
    public ResponseEntity<List<AppUserPayload>> getAllUsers(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<AppUserPayload> allUserDetails = appUserService.getAllUsers(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allUserDetails,HttpStatus.OK);
    }

}
