package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.Bookings;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.exception.NotFoundException;
import com.travelbnb.payloads.BookingPayload;
import com.travelbnb.repository.BookingsRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private final PropertyRepository propertyRepository;
    private final BookingsRepository bookingsRepository;

    public BookingServiceImpl(PropertyRepository propertyRepository, BookingsRepository bookingsRepository) {
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public BookingPayload createBookings(long porpertyId, AppUserEntity user, BookingPayload bookingPayload) {
        Optional<PropertyEntity> opPropertyId = propertyRepository.findById(porpertyId);
        if (opPropertyId.isEmpty()) {
            throw new NotFoundException("Property not found with ID: " + porpertyId);
        }
        PropertyEntity property = opPropertyId.get();
        if(user == null){
            throw new NotFoundException("User not found");
        }
        bookingPayload.setAppUserEntity(user);
        bookingPayload.setPropertyEntity(property);

//        Logical Implementation
        int nightlyPrice = property.getNightlyPrice();
        int totalPrice = nightlyPrice * bookingPayload.getTotalNights();
        int gstAmount = (totalPrice*18)/100;
        int finalPrice = totalPrice+gstAmount;
        bookingPayload.setPrice(finalPrice);


        Bookings book = payloadToEntity(bookingPayload);
        Bookings saved = bookingsRepository.save(book);
        BookingPayload booking = entityToPayload(saved);
        return booking;
    }
    // Conversion Payload To Entity
    private Bookings payloadToEntity(BookingPayload bpd){
        Bookings book = new Bookings();
        book.setEmail(bpd.getEmail());
        book.setName(bpd.getName());
        book.setMobile(bpd.getMobile());
        book.setPrice(bpd.getPrice());
        book.setTotalNights(bpd.getTotalNights());
        book.setPropertyEntity(bpd.getPropertyEntity());
        book.setAppUserEntity(bpd.getAppUserEntity());

        return book;
    }
    //    conversion Entity To Payload
   private BookingPayload entityToPayload(Bookings book){
        BookingPayload bpd = new BookingPayload();
        bpd.setId(book.getId());
        bpd.setEmail(book.getEmail());
        bpd.setName(book.getName());
        bpd.setMobile(book.getMobile());
        bpd.setPrice(book.getPrice());
        bpd.setTotalNights(book.getTotalNights());
        bpd.setPropertyEntity(book.getPropertyEntity());
        bpd.setAppUserEntity(book.getAppUserEntity());

        return bpd;
   }
}
