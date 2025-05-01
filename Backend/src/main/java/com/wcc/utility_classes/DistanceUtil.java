package com.wcc.utility_classes;

/**
 * Calculates the distance in kilometers between two points on the Earth's surface
 * given their latitude and longitude coordinates.
 * <p>
 * This method uses the Haversine formula to account for the curvature of the Earth.
 *
 * @param latitude  Latitude of the first point in decimal degrees.
 * @param longitude Longitude of the first point in decimal degrees.
 * @param latitude2 Latitude of the second point in decimal degrees.
 * @param longitude2 Longitude of the second point in decimal degrees.
 * @return The distance between the two points in kilometers.
 * @throws IllegalArgumentException if any of the input values are not valid
 * (e.g., if latitudes are outside the range
 * [-90, 90] or longitudes are outside the
 * range [-180, 180]).
 */
public class DistanceUtil {

	private final static double EARTH_RADIUS = 6371; // radius in kilometers

	  private DistanceUtil() {
		    throw new IllegalStateException("Utility class");
		  }
	  
	public static double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
		// Using Haversine formula! See Wikipedia;
		double lon1Radians = Math.toRadians(longitude);
		double lon2Radians = Math.toRadians(longitude2);
		double lat1Radians = Math.toRadians(latitude);
		double lat2Radians = Math.toRadians(latitude2);
		double a = haversine(lat1Radians, lat2Radians)
				+ Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (EARTH_RADIUS * c);
	}

	private static double haversine(double deg1, double deg2) {
		return square(Math.sin((deg1 - deg2) / 2.0));
	}

	private static double square(double x) {
		return x * x;
	}
}
