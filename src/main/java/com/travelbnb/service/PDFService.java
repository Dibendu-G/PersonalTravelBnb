package com.travelbnb.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelbnb.payloads.BookingPayload;
import com.travelbnb.payloads.PropertyPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {


    private static final String PDF_DIRECTORY = "/path/to/your/pdf/directory/";


    public boolean generatePDF(String fileName, BookingPayload payload){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            // Create a table with 3 columns
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); // Make the table take up the whole page width

            // Add table headers
            PdfPCell cell = new PdfPCell(new Phrase("Booking Confirmation", font));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            // Add data to the table
//            table.addCell(new Phrase("Booking ID:", font));
//            table.addCell(new Phrase(String.valueOf(payload.getId()), font));
            table.addCell(new Phrase("Guest Name:", font));
            table.addCell(new Phrase(payload.getName(), font));
            table.addCell(new Phrase("Email Id:", font));
            table.addCell(new Phrase(payload.getEmail(), font));
//            table.addCell(new Phrase("Price per Night:", font));
//            table.addCell(new Phrase(String.valueOf(propertyPayload.getNightlyPrice()), font));
            table.addCell(new Phrase("Total Nights:", font));
            table.addCell(new Phrase(String.valueOf(payload.getTotalNights()), font));
            table.addCell(new Phrase("Total Price:", font));
            table.addCell(new Phrase(String.valueOf(payload.getPrice()), font));

            // Add the table to the document
            document.add(table);

            document.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
