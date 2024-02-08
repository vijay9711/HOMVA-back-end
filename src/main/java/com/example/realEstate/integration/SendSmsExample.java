package com.example.realEstate.integration;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SendSmsExample {
    // Your Twilio Account SID and Auth Token from the Twilio Console
    public static final String ACCOUNT_SID = "AC7ff8e62214f7f9e664e219ca47b1eefc";
    public static final String AUTH_TOKEN = "7f57045b4c54b2ed3f78ae7a8f348f10";

    public void sendSMS() {
        // Initialize Twilio client with your credentials
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Sender's phone number (Twilio phone number)
        String fromPhoneNumber = "+18885862085";

        // Recipient's phone number
        String toPhoneNumber = "+17203695630"; // Replace with the recipient's phone number

        // Message to be sent
        String messageBody = "Hello from HOMVA!";

        // Send SMS message
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }
}
