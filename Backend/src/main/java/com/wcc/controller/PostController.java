package com.wcc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcc.model.bean.DistanceUnit;
import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.service.PostService;
import com.wcc.utility_classes.DistanceUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/wcc/distance")
@Tag(name = "Post Controller", description = "Endpoints for calculating distances between postcodes")
public class PostController {

	@Autowired
	PostService postService;

	@Operation(summary = "Testing Services only", description = "For Testing without token access")
	@GetMapping("/postcode/all")
	public String findAll() {
		return postService.findAll().toString();
	}

	@PostMapping("/postcode/save")
	public Postcodelatlng save(@Valid @RequestBody Postcodelatlng postcodelatlng) throws Exception {
		
		return postService.save(postcodelatlng);
	}

	@Operation(summary = "Calculate distance between two postcodes", description = "Calculates the distance in kilometers between two location  postcodes based on their lat/lng values")
	//@PreAuthorize("isAuthenticated()")
	@GetMapping("/get")
	public com.wcc.model.bean.Distance getDistance(@RequestParam("source") String source, @RequestParam("dest") String dest) {

		Postcodelatlng postcodelatlngSource = postService.findByPostcode(source);

		Postcodelatlng postcodelatlngDest = postService.findByPostcode(dest);

		System.out.println("postcodelatlng:=" + postcodelatlngSource);

		double distanceInKM = DistanceUtil.calculateDistance(postcodelatlngSource.getLatitude(),
				postcodelatlngSource.getLongitude(), postcodelatlngDest.getLatitude(),
				postcodelatlngDest.getLongitude());
		
		com.wcc.model.bean.Distance distance = new com.wcc.model.bean.Distance();
		distance.setDistance(distanceInKM);
		distance.setUnit(DistanceUnit.KM);
		return distance;
	}

}
