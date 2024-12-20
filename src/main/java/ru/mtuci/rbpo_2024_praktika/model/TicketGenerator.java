package ru.mtuci.rbpo_2024_praktika.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class TicketGenerator {

    private static final String HMAC_SHA256 = "HmacSHA256";
    @Value("${jwt.secret}")
    private String secretKey;

    private String createDigitalSignature(License license, Device device) {
        String rawData = assembleRawData(license, device);
        return computeHmac(rawData);
    }

    private String assembleRawData(License license, Device device) {
        return license.getCode() + license.getApplicationUser() + device.getId() + license.getEndingDate();
    }

    private String computeHmac(String data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации цифровой подписи", e);
        }
    }

    public Ticket generateTicket(License license, Device device) {
        Ticket ticket = new Ticket();
        ticket.setServerDate(new Date());
        ticket.setLifetime(calculateLifetime(license));
        ticket.setFirstActivationDate(license.getFirstActivationDate());
        ticket.setEndingDate(license.getEndingDate());
        ticket.setUserId(getUserId(device));
        ticket.setDeviceId(device.getMacAddress());
        ticket.setBlocked(getBlockedStatus(license));
        ticket.setDigitalSignature(createDigitalSignature(license, device));
        return ticket;
    }

    private long calculateLifetime(License license) {
        return license.getLicenseType().getDefaultDuration().longValue() * 7 * 24 * 60 * 60;
    }

    private Long getUserId(Device device) {
        return device.getApplicationUser() != null ? device.getApplicationUser().getId() : null;
    }

    private String getBlockedStatus(License license) {
        return license.getBlocked() != null ? license.getBlocked().toString() : "null";
    }
}
