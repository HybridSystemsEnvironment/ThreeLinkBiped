package edu.ucsc.hsl.hse.model.biped.threelink.factories;

import edu.ucsc.cross.hse.core.variable.RandomVariable;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedReferenceControl;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedTrackingController;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedVirtualControl;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.PerturbationParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.ClosedLoopBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.PerturbationState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.PerturbedClosedLoopBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ClosedLoopBipedSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.PerturbedClosedLoopSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ReferenceSystem;

public class BipedModelFactory
{

	public static PerturbedClosedLoopSystem getClosedLoopPerturbed(BipedState plant_state,
	VirtualBipedState virtual_state, ReferenceSystem reference, double perturbation_percentage,
	BipedParameters parameters)
	{
		PerturbationParameters pParams = new PerturbationParameters(perturbation_percentage);
		PerturbationState ps = new PerturbationState(
		(Math.random() * pParams.perturbationPercentage) * parameters.stepAngle);
		BipedVirtualControl vc = new BipedVirtualControl(plant_state, virtual_state,
		reference.getComponents().getState(), parameters);
		BipedTrackingController tc = new BipedTrackingController(plant_state, virtual_state, vc, parameters);
		PerturbedClosedLoopBipedState perturbedState = new PerturbedClosedLoopBipedState(plant_state, virtual_state,
		ps);
		PerturbedClosedLoopSystem sys = new PerturbedClosedLoopSystem(perturbedState,
		reference.getComponents().getState(), parameters, vc, tc, pParams);
		return sys;
	}

	public static ClosedLoopBipedSystem getClosedLoopUnperturbed(BipedState plant_state,
	VirtualBipedState virtual_state, ReferenceSystem reference, BipedParameters parameters)
	{
		BipedVirtualControl vc = new BipedVirtualControl(plant_state, virtual_state,
		reference.getComponents().getState(), parameters);
		BipedTrackingController tc = new BipedTrackingController(plant_state, virtual_state, vc, parameters);
		ClosedLoopBipedSystem sys = new ClosedLoopBipedSystem(new ClosedLoopBipedState(plant_state, virtual_state),
		reference.getComponents().getState(), parameters, vc, tc);
		return sys;
	}

	public static ReferenceSystem getReferenceSystem(VirtualBipedState reference_state, BipedParameters parameters)
	{
		BipedReferenceControl controller = new BipedReferenceControl(reference_state, parameters);
		ReferenceSystem reference = new ReferenceSystem(reference_state, parameters, controller);
		reference.initialize();
		return reference;
	}

	public static VirtualBipedState getFixedVirtualBipedState(Double planted_angle, Double swing_angle,
	Double torso_angle, Double planted_velocity, Double swing_velocity, Double torso_velocity)
	{
		return new VirtualBipedState(planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity,
		torso_velocity);
	}

	public static VirtualBipedState getRandomVirtualBipedState(Double planted_angle_min, Double planted_angle_max,
	Double swing_angle_min, Double swing_angle_max, Double torso_angle_min, Double torso_angle_max,
	Double planted_velocity_min, Double planted_velocity_max, Double swing_velocity_min, Double swing_velocity_max,
	Double torso_velocity_min, Double torso_velocity_max)
	{
		BipedState random = getRandomBipedState(planted_angle_min, planted_angle_max, swing_angle_min, swing_angle_max,
		torso_angle_min, torso_angle_max, planted_velocity_min, planted_velocity_max, swing_velocity_min,
		swing_velocity_max, torso_velocity_min, torso_velocity_max);
		VirtualBipedState virtRandom = new VirtualBipedState(random);
		return virtRandom;
	}

	public static BipedState getFixedBipedState(Double planted_angle, Double swing_angle, Double torso_angle,
	Double planted_velocity, Double swing_velocity, Double torso_velocity)
	{
		return new BipedState(planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity,
		torso_velocity);
	}

	public static BipedState getRandomBipedState(Double planted_angle_min, Double planted_angle_max,
	Double swing_angle_min, Double swing_angle_max, Double torso_angle_min, Double torso_angle_max,
	Double planted_velocity_min, Double planted_velocity_max, Double swing_velocity_min, Double swing_velocity_max,
	Double torso_velocity_min, Double torso_velocity_max)
	{
		BipedState random = new BipedState(RandomVariable.generate(planted_angle_min, planted_angle_max),
		RandomVariable.generate(swing_angle_min, swing_angle_max),
		RandomVariable.generate(torso_angle_min, torso_angle_max),
		RandomVariable.generate(planted_velocity_min, planted_velocity_max),
		RandomVariable.generate(swing_velocity_min, swing_velocity_max),
		RandomVariable.generate(torso_velocity_min, torso_velocity_max));
		return random;
	}

}
