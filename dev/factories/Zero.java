
package edu.ucsc.hsl.hse.model.biped.threelink.factories;

public class Zero {

	public static double threshold = .005;

	public static boolean equal(double value) {

		return (Math.abs(value) < threshold);
	}

	public static boolean equal(double value, double tolerance) {

		return (Math.abs(value) < tolerance);
	}
}
