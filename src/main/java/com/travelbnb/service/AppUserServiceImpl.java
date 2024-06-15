package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.LoginPayload;
import com.travelbnb.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{


    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository)
    {
        this.jwtService = jwtService;
        this.appUserRepository=appUserRepository;
    }
    @Override
    public AppUserPayload addUsers(AppUserPayload appUserPayload) {
        AppUserEntity ape = PayloadsToEntity(appUserPayload);
        AppUserEntity saved = appUserRepository.save(ape);
        AppUserPayload apl = EntityToPayloads(saved);

        return apl;
    }

    @Override
    public String verifyLogin(LoginPayload loginPayload) {
        Optional<AppUserEntity> opUserName = appUserRepository.findByUsername(loginPayload.getUsername());
        if(opUserName.isPresent())
        {
            AppUserEntity appUser = opUserName.get();
          if(BCrypt.checkpw(loginPayload.getPassword(),appUser.getPassword())){
             String token = jwtService.generateToken(appUser);
             return token;
          }
        }
        return null;
    }

    //    Conversion PayloadsToEntity
    AppUserEntity PayloadsToEntity(AppUserPayload appUserPayload)
    {
        AppUserEntity aue = new AppUserEntity();
        aue.setName(appUserPayload.getName());
        aue.setEmail(appUserPayload.getEmail());
        aue.setUsername(appUserPayload.getUsername());
        aue.setPassword(appUserPayload.getPassword());

        return aue;
    }

//    Conversion EntityToPayloads
    AppUserPayload EntityToPayloads(AppUserEntity ape)
    {
        AppUserPayload apl = new AppUserPayload();
        apl.setId(ape.getId());
        apl.setName(ape.getName());
        apl.setEmail(ape.getEmail());
        apl.setUsername(ape.getUsername());
        apl.setPassword(ape.getPassword());

        return apl;
    }
}
