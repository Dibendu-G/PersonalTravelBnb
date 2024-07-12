package com.travelbnb.controllers;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.Bookings;
import com.travelbnb.payloads.BookingPayload;
import com.travelbnb.service.BookingService;
import com.travelbnb.service.BucketService;
import com.travelbnb.service.PDFService;
import com.travelbnb.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PDFService pdfService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private SmsService smsService;

    public BookingController(BookingService bookingService, PDFService pdfService, BucketService bucketService, SmsService smsService) {
        this.bookingService = bookingService;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.smsService = smsService;
    }

    //    Applying Pagination and Sorting

    @GetMapping
    public ResponseEntity<List<BookingPayload>> getAllBookings(@RequestParam(name="pageSize",defaultValue = "5", required = false)int pageSize,
                                                            @RequestParam(name="pageNo",defaultValue ="0",required = false)int pageNo,
                                                            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
                                                            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<BookingPayload> allBookingsDetails = bookingService.getAllBookings(pageSize,pageNo,sortBy,sortDir);

        return new ResponseEntity<>(allBookingsDetails,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestParam long porpertyId,
                                                        @AuthenticationPrincipal AppUserEntity user,
                                                        @RequestBody BookingPayload bookingPayload)throws IOException {
            BookingPayload crtBook = bookingService.createBookings(porpertyId,user,bookingPayload);



        boolean b = pdfService.generatePDF("C://test//" + "booking-confirmation-id" + crtBook.getId() + ".pdf", bookingPayload);
        if(b){

            MultipartFile file = BookingController.convert("C://test//"+"booking-confirmation-id" + crtBook.getId() + ".pdf");
            String uploadedFileUrl = bucketService.uploadFile(file, "YourBucketName");
//            System.out.println(uploadedFileUrl);
            smsService.sendSms(crtBook.getMobile(),"Hotel booking confirmed and SMS sent successfully! Check the Link for Your Invoice: "+uploadedFileUrl);

        }else{
            return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
            return new ResponseEntity<>(crtBook, HttpStatus.CREATED);


    }

    //    Updating

    @PutMapping("/{bookingId}")
    public ResponseEntity<Bookings> updateUser(@PathVariable long bookingId,
                                                    @RequestBody BookingPayload bookingPayload)
    {
        Bookings bookings = bookingService.updateBookingDetails(bookingId,bookingPayload);
        return new ResponseEntity<>(bookings,HttpStatus.OK);
    }

    //    Deleting

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable long bookingId) {
        try {
            bookingService.deleteUser(bookingId);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        } catch (RuntimeException e)
        {
            return new  ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }


    public static MultipartFile convert(String filePath) throws IOException {
        File file = new File(filePath);

        byte[] fileContent = Files.readAllBytes(file.toPath());

        Resource resource = new ByteArrayResource(fileContent);

        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

                Files.write(dest.toPath(), fileContent);
            }
        };
        return multipartFile;
    }
}
