package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@NoArgsConstructor
@Data
public class Ticket {

    private Date serverDate;
    private Long lifetime;
    private Date firstActivationDate;
    private Date endingDate;
    private Long userId;
    private String deviceId;
    private String blocked;
    private String digitalSignature;
    @JsonIgnore
    private License license;
    @JsonIgnore
    private Device device;

    private static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateDigitalSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        String data = serverDate.getTime() + lifetime + firstActivationDate.getTime() + endingDate.getTime() + userId + deviceId + blocked;

        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] hmacSha256 = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacSha256);
    }

    public boolean verifyDigitalSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        if (digitalSignature == null || digitalSignature.isEmpty()) {
            return false;
        }
        String expectedSignature = generateDigitalSignature();
        return expectedSignature.equals(this.digitalSignature);
    }


    public static class Builder {
        private Date serverDate;
        private Date firstActivationDate;
        private Long userId;
        private String deviceId;
        private String blocked;
        private License license;
        private Device device;


        public Builder serverDate(Date serverDate) {
            this.serverDate = serverDate;
            return this;
        }

        public Builder firstActivationDate(Date firstActivationDate) {
            this.firstActivationDate = firstActivationDate;
            return this;
        }


        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder blocked(String blocked) {
            this.blocked = blocked;
            return this;
        }

        public Builder license(License license) {
            this.license = license;
            return this;
        }

        public Builder device(Device device) {
            this.device = device;
            return this;
        }

        public Ticket build() throws NoSuchAlgorithmException, InvalidKeyException {
            Ticket ticket = new Ticket();
            ticket.setServerDate(serverDate);
            ticket.setFirstActivationDate(firstActivationDate);

            Instant instant = firstActivationDate.toInstant();
            Instant endingInstant = instant.plus(7, ChronoUnit.DAYS);

            ticket.setLifetime(ChronoUnit.SECONDS.between(instant, endingInstant));
            ticket.setEndingDate(Date.from(endingInstant));


            ticket.setUserId(userId);
            ticket.setDeviceId(deviceId);
            ticket.setBlocked(blocked);
            ticket.setLicense(license);
            ticket.setDevice(device);
            ticket.setDigitalSignature(ticket.generateDigitalSignature());
            return ticket;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}