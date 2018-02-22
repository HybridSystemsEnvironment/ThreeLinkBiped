package edu.ucsc.hsl.hse.model.biped.threelink.parameters;

public class PerturbationParameters
{

	public Double perturbationPercentage;

	public PerturbationParameters(Double percentage)
	{
		this.perturbationPercentage = percentage;
	}

	public PerturbationParameters()
	{
		this.perturbationPercentage = .3;
	}
}
