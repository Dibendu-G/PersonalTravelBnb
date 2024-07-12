package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.Bookings;
import com.travelbnb.payloads.BookingPayload;

import java.util.List;

public interface BookingService {
    BookingPayload createBookings(long porpertyId, AppUserEntity user, BookingPayload bookingPayload);

    List<BookingPayload> getAllBookings(int pageSize, int pageNo, String sortBy, String sortDir);

    Bookings updateBookingDetails(long bookingId, BookingPayload bookingPayload);

    void deleteUser(long bookingId);
}
