package com.example.farm.service;


import com.example.farm.mapper.CollectedProductMapper;
import com.example.farm.model.dto.FarmCollectedProductDTO;
import com.example.farm.model.request.admin.GetCollectedProductsFarmRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService{
    private static final String CRON = "0 00 18 * * ?"; // Отправление данных о собранных товарах в 18:00 каждый день
    private static final String adminEmail = "astravsu@gmail.com"; // Адрес почты, на который отправляются данные

    private final CollectedProductMapper collectedProductMapper;
    private final CollectedProductService collectedProductService;
    private final JavaMailSender emailSender;

    @Scheduled(cron = CRON)
    public void sendMail() {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        List<FarmCollectedProductDTO> collectedProducts =
                collectedProductMapper.fromEntityToFarmCollectedProductDTO(
                        collectedProductService.getCollectedProductsByPeriod(
                                new GetCollectedProductsFarmRequest("day", currentDay)
                        )
                );
        String subject = "Статистика фермы за день";
        String text = toConvenientForm(collectedProducts);
        send(adminEmail, subject, text);
    }

    public void send(String to, String subject, String text) {
        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setFrom("Farm-service");
            helper.setSubject(subject);
            helper.setText(text);
            this.emailSender.send(message);
            log.info("Mail has been sended.");
        } catch (MessagingException messagingException) {
            throw new RuntimeException(messagingException);
        }
    }

    private String toConvenientForm(List<FarmCollectedProductDTO> collectedProducts) {
        StringBuilder text = new StringBuilder("Total collected in a day:\n-------------------\n");
        for (FarmCollectedProductDTO collectedProductDTO: collectedProducts) {
            text.append(collectedProductDTO.toString());
            text.append("-------------------\n");
        }
        return String.valueOf(text);
    }
}
