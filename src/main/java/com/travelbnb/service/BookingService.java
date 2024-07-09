package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.payloads.BookingPayload;

public interface BookingService {
    BookingPayload createBookings(long porpertyId, AppUserEntity user, BookingPayload bookingPayload);
}
