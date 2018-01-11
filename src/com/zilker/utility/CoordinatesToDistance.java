package com.zilker.utility;

public class CoordinatesToDistance {
	
	
	
	// Calculate the distance between user and the grocery shop in Kilometers.
	public static int distanceInKilometers(double userLatitude, double groceryLatitude, double groceryLongitude,
			double userLongitude) {
		double theta = userLongitude - groceryLongitude;
		double dist = Math.sin(degreeToRadians(userLatitude)) * Math.sin(degreeToRadians(groceryLatitude))
				+ Math.cos(degreeToRadians(userLatitude)) * Math.cos(degreeToRadians(groceryLatitude))
						* Math.cos(degreeToRadians(theta));
		dist = Math.acos(dist);
		dist = radiansToDegree(dist);
		dist = dist * 60 * 1.1515;

		dist = dist * 1.609344;

		return (int) dist;
	}

	// This function converts decimal degrees to radians

	private static double degreeToRadians(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double radiansToDegree(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	
}
