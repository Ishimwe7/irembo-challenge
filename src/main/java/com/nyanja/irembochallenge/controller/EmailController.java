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
            String idOrPassport = "";
            if ("Rwandan".equalsIgnoreCase(request.getApplicantCitizenship())) {
                idOrPassport = "<p><strong>Identification document Number:</strong> " + request.getIdNumber() + "</p>";
            } else if ("Foreigner".equalsIgnoreCase(request.getApplicantCitizenship())) {
                idOrPassport = "<p><strong>Passport Number:</strong> " + request.getPassportNumber() + "</p>";
            }

            String emailContent = "<h3>Thank you for submitting your Import Permit Application!</h3>" +
                    "<p>Dear " + request.getOtherNames() + " " + request.getSurname() + ",</p>" +
                    "<p>We have received your application details. Below are your submitted details:</p>" +
                    "<p><strong>Citizenship:</strong> " + request.getApplicantCitizenship() + "</p>" +
                    idOrPassport +
                    "<p><strong>Phone Number:</strong> " + request.getPhoneNumber()+ "</p>" +
                    "<p><strong>Address:</strong> " + request.getOwnerAddress() + "</p>" +
                    "<p><strong>Business Type:</strong> " + request.getBusinessType() + "</p>" +
                    "<p><strong>Company:</strong> " + request.getCompanyName() + "</p>" +
                    "<p><strong>Business Address:</strong> " + request.getCompanyAddress()+ "</p>" +
                    "<p><strong>Registration Date:</strong> " + request.getRegDate() + "</p>" +
                    "<p><strong>TIN Number:</strong> " + request.getTinNumber() + "</p>" +
                    "<p><strong>Product Information:</strong></p>" +
                    "<ul>" +
                    "<li><strong>Product Name:</strong> " + request.getProductName() + "</li>" +
                    "<li><strong>Products Description:</strong> " + request.getDescription() + "</li>" +
                    "<li><strong>Quantity:</strong> " + request.getQuantity() + " " + request.getUnit() + "</li>" +
                    "</ul>" +
                    "<p>If any of the details above are incorrect, please contact us immediately.</p>" +
                    "<p>Thank you for using our service. We will process your application as soon as possible.</p>";

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


