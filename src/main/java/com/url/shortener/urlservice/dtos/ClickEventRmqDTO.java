package com.url.shortener.urlservice.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClickEventRmqDTO {
    private Long urlMappingId;
    private LocalDateTime clickDate;

}
