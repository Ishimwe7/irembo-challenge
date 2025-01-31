package com.nyanja.irembochallenge.controller;

import com.nyanja.irembochallenge.EmailRequest;
import com.nyanja.irembochallenge.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailController {

    @Autowired
    private  EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        try {
            if(request == null || request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "No Email Found!"));
            }

            // Use string concatenation instead of template literals
            String emailContent = "<h3>Import Permit Details</h3>" +
                    "<p><strong>Applicant:</strong> " + request.getOtherNames() + " " + request.getSurname() + "</p>" +
                    "<p><strong>Citizenship:</strong> " + request.getApplicantCitizenship() + "</p>" +
                    "<p><strong>Nationality:</strong> " + request.getNationality() + "</p>" +
                    "<p><strong>Company:</strong> " + request.getCompanyName() + "</p>" +
                    "<p><strong>TIN Number:</strong> " + request.getTinNumber() + "</p>" +
                    "<p><strong>Product Name:</strong> " + request.getProductName() + "</p>" +
                    "<p><strong>Description:</strong> " + request.getDescription() + "</p>" +
                    "<p><strong>Quantity:</strong> " + request.getQuantity() + " " + request.getUnit() + "</p>";

            // Call the email service with the formatted content
            emailService.sendEmail(request.getEmail(), "Import Permit Application", emailContent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Internal Server error!"));
        }
    }

}


