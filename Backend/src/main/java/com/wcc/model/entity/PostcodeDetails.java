package com.wcc.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "postcodedetails")
public class PostcodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Description is required")
    @Column(name = "description", nullable = false)
    private String description;

    // Use EAGER loading for demonstration here
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postcode_id")
    private Postcodelatlng postcodelatlng;
}
