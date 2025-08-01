package com.scaler.emailservicecapstone.dto;

import java.time.LocalDateTime;

public class OtpAckDto {

    private String otpValue;
    private boolean delivered;
    private String deliveryTarget;
    private String deliveryType; // SMS or EMAIL
    private String deliveryStatus; // e.g., SUCCESS or FAILED
    private LocalDateTime deliveryTimestamp;

    public String getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(String otpValue) {
        this.otpValue = otpValue;
    }

    public LocalDateTime getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public void setDeliveryTimestamp(LocalDateTime deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getDeliveryTarget() {
        return deliveryTarget;
    }

    public void setDeliveryTarget(String deliveryTarget) {
        this.deliveryTarget = deliveryTarget;
    }
}
