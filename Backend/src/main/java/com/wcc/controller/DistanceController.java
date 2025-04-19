package com.wcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.service.PostService;
import com.wcc.utility_classes.Distance;

@RestController
@RequestMapping("/wcc/distance")
public class DistanceController {

	@Autowired
	PostService postService;
	
	@GetMapping("/postcode/all")
	public String findAll() {
		return postService.findAll().toString();
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get")
	public double getDistance(@RequestParam("source") String source, @RequestParam("dest") String dest) {

		Postcodelatlng postcodelatlngSource = postService.findByPostcode(source);

		Postcodelatlng postcodelatlngDest = postService.findByPostcode(dest);

		System.out.println("postcodelatlng:=" + postcodelatlngSource);

		double distance = Distance.calculateDistance(postcodelatlngSource.getLatitude(),
				postcodelatlngSource.getLongitude(), postcodelatlngDest.getLatitude(),
				postcodelatlngDest.getLongitude());
		return distance;
	}

}
