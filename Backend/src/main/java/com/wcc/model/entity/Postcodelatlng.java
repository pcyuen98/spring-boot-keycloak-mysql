package com.wcc.model.entity;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private Integer idComment;

	@NotNull(message = "Postcode is required")
	@Size(max = 20, message = "Postcode must be less than 20 characters")
	@Column(name = "postcode", nullable = false)
	private String postcode;

	@NotNull(message = "Latitude is required")
	@Column(name = "latitude", nullable = false)
	private Double latitude;

	@NotNull(message = "Longitude is required")
	@Column(name = "longitude", nullable = false)
	private Double longitude;
	
	@OneToMany(mappedBy = "postcodelatlng", fetch = FetchType.EAGER)
	private List<PostcodeDetails> postcodeDetails = new ArrayList<>();

	@Override
	public String toString() {
	    return "Postcodelatlng{" +
	            "idComment=" + idComment +
	            ", postcode='" + postcode + '\'' +
	            ", latitude=" + latitude +
	            ", longitude=" + longitude +
	            ", postcodeDetailsSize=" + (postcodeDetails != null ? postcodeDetails.size() : 0) +
	            '}';
	}
}
