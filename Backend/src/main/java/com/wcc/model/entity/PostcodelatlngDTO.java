package com.wcc.model.entity;

import lombok.Data;

@Data
public class PostcodelatlngDTO {
    private Long idComment;
    private String postcode;
    private Double latitude;
    private Double longitude;
}