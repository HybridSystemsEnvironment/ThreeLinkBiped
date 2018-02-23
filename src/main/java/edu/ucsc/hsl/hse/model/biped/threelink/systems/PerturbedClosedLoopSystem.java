package edu.ucsc.hsl.hse.model.biped.threelink.systems;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.BipedComputer;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedTrackingController;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedVirtualControl;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.PerturbationParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.PerturbedClosedLoopBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;

public class PerturbedClosedLoopSystem extends HybridSystem<PerturbedClosedLoopBipedState>
{

	public PerturbationParameters perturbParams;
	public BipedParameters params;
	public VirtualBipedState referenceState;
	public BipedVirtualControl controller;
	public BipedTrackingController realControl;

	public PerturbedClosedLoopSystem(PerturbedClosedLoopBipedState state, VirtualBipedState reference,
	BipedParameters params, BipedVirtualControl virtual_control, BipedTrackingController real_control,
	PerturbationParameters p_params)
	{
		super(state);
		this.referenceState = reference;
		this.params = params;
		this.controller = virtual_control;
		this.realControl = real_control;
		this.perturbParams = p_params;
		prepare();
	}

	@Override
	public boolean C(PerturbedClosedLoopBipedState x)
	{
		return true;
	}

	@Override
	public void F(PerturbedClosedLoopBipedState x, PerturbedClosedLoopBipedState x_dot)
	{
		BipedComputer.computeChangeBetweenImpact(x.realBiped, x_dot.realBiped, params, realControl.getInputTorques());
		if (!BipedComputer.isImpactOccurring(x.virtualBiped, params, this.getComponents().getState().perturbation))
		{
			BipedComputer.computeChangeBetweenImpact(x.virtualBiped, x_dot.virtualBiped, params,
			controller.getInputTorques());
		}
		x_dot.virtualBiped.trajTimer = 1.0;
	}

	@Override
	public boolean D(PerturbedClosedLoopBipedState x)
	{
		// TODO Auto-generated method stub
		return BipedComputer.isImpactOccurring(x.realBiped, params, getComponents().getState().perturbation);
	}

	@Override
	public void G(PerturbedClosedLoopBipedState x, PerturbedClosedLoopBipedState x_plus)
	{
		if (BipedComputer.isImpactOccurring(x.realBiped, params, getComponents().getState().perturbation))
		{
			BipedComputer.computeChangeAtImpact(x.realBiped, x_plus.realBiped, params);
			BipedComputer.computeChangeAtImpact(x.realBiped, x_plus.virtualBiped, params);

			x_plus.virtualBiped.trajTimer = 0.0;
			x_plus.virtualBiped.trajParams = controller.recomputeTrajectoryParams(x_plus.virtualBiped);
			x_plus.perturbation.perturbationAngle = params.stepAngle * (Math.random() - Math.random())
			* this.perturbParams.perturbationPercentage;
		}
	}

	private void prepare()
	{
		controller.computeEquilibriumStates();
		getComponents().getState().virtualBiped.trajParams = controller
		.recomputeTrajectoryParams(getComponents().getState().virtualBiped,
		params.getStepTime() * getComponents().getState().virtualBiped.trajTimer);
		getComponents().getState().virtualBiped.trajTimer = 0.0;

	}

}
