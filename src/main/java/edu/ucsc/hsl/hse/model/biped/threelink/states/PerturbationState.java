package edu.ucsc.hsl.hse.model.biped.threelink.states;

import edu.ucsc.cross.hse.core.modelling.DataStructure;

public class PerturbationState extends DataStructure
{

	public double perturbationAngle;

	public PerturbationState(Double perturbation_angle)
	{
		perturbationAngle = perturbation_angle;
	}
}
