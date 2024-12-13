package ru.mtuci.rbpo_2024_praktika.request;

public class LicenseRequest {
    private Long productId;
    private Long ownerId;
    private Long licenseTypeId;
    private Integer deviceCount;

    // Getters and setters  (Геттеры и сеттеры)
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public Long getLicenseTypeId() { return licenseTypeId; }
    public void setLicenseTypeId(Long licenseTypeId) { this.licenseTypeId = licenseTypeId; }

    public Integer getDeviceCount() { return deviceCount; }
    public void setDeviceCount(Integer maxDevices) { this.deviceCount = deviceCount; }
}
