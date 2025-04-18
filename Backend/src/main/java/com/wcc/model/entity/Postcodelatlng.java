package com.wcc.model.entity;

import java.io.Serial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "postcodelatlng")
public class Postcodelatlng {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idComment;

	@Column(name = "postcode")
	private String postcode;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude", nullable = false)
	private Double longitude;

}
