package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.LoginPayload;

import java.util.List;

public interface AppUserService {
    AppUserPayload addUsers(AppUserPayload appUserPayload);

    String verifyLogin(LoginPayload loginPayload);

    List<AppUserPayload> getAllUsers(int pageSize, int pageNo,String sortBy, String sortDir);

    AppUserEntity updateUserDetails(long userId, AppUserPayload appUserPayload);

    void deleteUser(long userId);
}
