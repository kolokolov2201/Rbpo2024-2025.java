package ru.mtuci.rbpo_2024_praktika.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.Ticket;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TicketService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private String generateDigitalSignature(License license, Device device) {
        try {
            String rawData = String.format("%s%s%s%s",
                    license.getCode(),
                    license.getApplicationUser() != null ? license.getApplicationUser().getId() : "",
                    device.getId(),
                    license.getEndingDate() != null ? license.getEndingDate().toString() : "");

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(rawData.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generating digital signature: Algorithm not found or invalid key", e);
        }
    }

    public Ticket createTicket(License license, Device device) {
        if (license == null || device == null) {
            throw new IllegalArgumentException("License and device cannot be null.");
        }
        Ticket ticket = new Ticket();
        ticket.setServerDate(new Date());
        ticket.setLifetime(license.getLicenseType().getDefaultDuration().longValue() * 30 * 24 * 60 * 60);
        ticket.setFirstActivationDate(license.getFirstActivationDate());
        ticket.setEndingDate(license.getEndingDate());
        ticket.setUserId(device.getApplicationUser() != null ? device.getApplicationUser().getId() : null);
        ticket.setDeviceId(device.getMacAddress());
        ticket.setBlocked(license.getBlocked() != null ? license.getBlocked().toString() : "false");
        ticket.setDigitalSignature(generateDigitalSignature(license, device));
        return ticket;
    }
}