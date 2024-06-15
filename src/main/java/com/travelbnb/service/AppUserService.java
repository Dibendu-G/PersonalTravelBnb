package com.travelbnb.service;

import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.LoginPayload;

public interface AppUserService {
    AppUserPayload addUsers(AppUserPayload appUserPayload);

    String verifyLogin(LoginPayload loginPayload);
}
