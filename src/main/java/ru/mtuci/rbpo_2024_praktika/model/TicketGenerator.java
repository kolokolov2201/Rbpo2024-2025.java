package ru.mtuci.rbpo_2024_praktika.model;

import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;
import java.util.Date;
//TOD: 1. computeHmac - отсутствует механизм генерации цифровой подписи. Вы генерируете просто хэш. Как клиент проверит его?
@Component
public class TicketGenerator {

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public TicketGenerator() throws Exception {
        generateKeyPair();
    }

    private void generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public String createDigitalSignature(License license, Device device) {
        try {
            String rawData = assembleRawData(license, device);
            return signData(rawData);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании цифровой подписи", e);
        }
    }

    private String assembleRawData(License license, Device device) {
        return license.getCode() + license.getApplicationUser() + device.getId() + license.getEndingDate();
    }

    private String signData(String data) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    public Ticket generateTicket(License license, Device device) {
        Ticket ticket = new Ticket();
        ticket.setServerDate(new Date());
        ticket.setLifetime(calculateLifetime(license));
        ticket.setFirstActivationDate(license.getFirstActivationDate());
        ticket.setEndingDate(license.getEndingDate());
        ticket.setUserId(getUserId(device));
        ticket.setDeviceId(device.getId());
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
