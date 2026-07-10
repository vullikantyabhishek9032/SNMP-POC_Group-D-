package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.RecommendationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecommendationMail(RecommendationDTO recommendationDTO) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("sirasanisivakrishna26@gmail.com");
        message.setFrom("sirasanisivakrishna26@gmail.com");
        message.setSubject(
                "Plan Recommendation");

        message.setText("Customer : "+recommendationDTO.getCustomerId()
                        + "\nCurrent Plan : "+recommendationDTO.getCurrentPlan()
                        + "\nRecommended Plan : "+recommendationDTO.getRecommendedPlan()
                        + "\nBenefits : "+recommendationDTO.getBenefits() );

        mailSender.send(message);

        System.out.println("EMAIL SENT SUCCESSFULLY");
    }
}