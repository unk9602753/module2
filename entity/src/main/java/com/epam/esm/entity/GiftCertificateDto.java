package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createDate;
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;

    public GiftCertificateDto(GiftCertificate giftCertificate) {
        this.id = giftCertificate.getId();
        this.name = giftCertificate.getName();
        this.description = giftCertificate.getDescription();
        this.price = giftCertificate.getPrice();
        this.duration = giftCertificate.getDuration();
        this.createDate = giftCertificate.getCreateDate();
        this.lastUpdateDate = giftCertificate.getLastUpdateDate();
        this.tags = new ArrayList<>();
    }
}
