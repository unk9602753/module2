package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotNull(message = "exception.name.not.be.null")
    @Size(min = 1,max = 40,message = "exception.name.out.of.range")
    private String name;
    @NotNull(message = "exception.description.not.be.null")
    @Size(min = 1,max = 40,message = "exception.description.out.of.range")
    private String description;
    @Min(value = 1,message = "exception.price.min.out.of.range")
    @Max(value = 10000, message = "exception.price.max.out.of.range")
    private double price;
    @Min(value = 5,message = "exception.duration.min.out.of.range")
    @Max(value = 365, message = "exception.duration.max.out.of.range")
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
