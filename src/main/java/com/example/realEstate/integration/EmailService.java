package com.example.realEstate.integration;

import java.io.IOException;



public interface EmailService {
    void sendSignupEmail(String recipientEmail);

    void sendOfferSubmissionEmail(String email);
}
