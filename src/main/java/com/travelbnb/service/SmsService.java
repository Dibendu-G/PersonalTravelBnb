package com.travelbnb.service;

import com.travelbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public void sendSms(String to, String message) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                message
        ).create();
    }
}

