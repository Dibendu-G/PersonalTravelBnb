package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.LoginPayload;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService{


    private JWTService jwtService;
    private AppUserRepository appUserRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public AppUserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository,
                              PropertyRepository propertyRepository)
    {
        this.jwtService = jwtService;
        this.appUserRepository=appUserRepository;
        this.propertyRepository = propertyRepository;
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

    @Override
    public List<AppUserPayload> getAllUsers(int pageSize, int pageNo,String sortBy, String sortDir) {
        Pageable pageable;
        if (sortDir.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        Page<AppUserEntity> userPage = appUserRepository.findAll(pageable);
        List<AppUserPayload> userPayloads = userPage.getContent().stream()
                .map(this::EntityToPayloads)
                .collect(Collectors.toList());

        return userPayloads;
    }

    @Override
    public AppUserEntity updateUserDetails(long userId, AppUserPayload appUserPayload) {
        Optional<AppUserEntity> byId = appUserRepository.findById(userId);
        AppUserEntity aue = byId.get();
        aue.setName(appUserPayload.getName());
        aue.setEmail(appUserPayload.getEmail());
        aue.setUsername(appUserPayload.getUsername());

        return appUserRepository.save(aue);
    }

    @Override
    public void deleteUser(long userId) {
        if (appUserRepository.existsById(userId)) {
            appUserRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    //    Conversion PayloadsToEntity
    AppUserEntity PayloadsToEntity(AppUserPayload appUserPayload)
    {
        AppUserEntity aue = new AppUserEntity();
        aue.setName(appUserPayload.getName());
        aue.setEmail(appUserPayload.getEmail());
        aue.setUsername(appUserPayload.getUsername());
        aue.setRole(appUserPayload.getRole());
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
        apl.setRole(ape.getRole());
        apl.setPassword(ape.getPassword());

        return apl;
    }
}
