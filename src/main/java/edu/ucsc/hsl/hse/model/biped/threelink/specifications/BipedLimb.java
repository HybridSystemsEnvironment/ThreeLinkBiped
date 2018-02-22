package edu.ucsc.hsl.hse.model.biped.threelink.specifications;

public enum BipedLimb
{
	SWING_LEG(
		"Swing Leg"),
	PLANTED_LEG(
		"Planted Leg"),
	TORSO(
		"Torso");

	public final String name;

	private BipedLimb(String new_name)
	{
		name = new_name;
	}
}