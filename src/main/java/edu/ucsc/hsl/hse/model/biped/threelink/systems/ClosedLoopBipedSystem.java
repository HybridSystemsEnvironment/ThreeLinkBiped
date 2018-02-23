package edu.ucsc.hsl.hse.model.biped.threelink.systems;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.BipedComputer;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedTrackingController;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedVirtualControl;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;

public class ClosedLoopBipedSystem extends HybridSystem<ClosedLoopBipedState>
{

	public BipedParameters params;
	public VirtualBipedState referenceState;
	public BipedVirtualControl controller;
	public BipedTrackingController realControl;

	public ClosedLoopBipedSystem(ClosedLoopBipedState state, VirtualBipedState reference, BipedParameters params,
	BipedVirtualControl virtual_control, BipedTrackingController real_control)
	{
		super(state);
		this.referenceState = reference;
		this.params = params;
		this.controller = virtual_control;
		this.realControl = real_control;
		initialize();
	}

	@Override
	public boolean C(ClosedLoopBipedState x)
	{
		return true;
	}

	@Override
	public void F(ClosedLoopBipedState x, ClosedLoopBipedState x_dot)
	{
		BipedComputer.computeChangeBetweenImpact(x.realBiped, x_dot.realBiped, params, realControl.getInputTorques());
		if (!BipedComputer.isImpactOccurring(x.virtualBiped, params))
		{
			BipedComputer.computeChangeBetweenImpact(x.virtualBiped, x_dot.virtualBiped, params,
			controller.getInputTorques());
		}
		x_dot.virtualBiped.trajTimer = 1.0;
	}

	@Override
	public boolean D(ClosedLoopBipedState x)
	{
		return BipedComputer.isImpactOccurring(x.realBiped, params);
	}

	@Override
	public void G(ClosedLoopBipedState x, ClosedLoopBipedState x_plus)
	{
		BipedComputer.computeChangeAtImpact(x.realBiped, x_plus.realBiped, params);
		BipedComputer.computeChangeAtImpact(x.realBiped, x_plus.virtualBiped, params);

		x_plus.virtualBiped.trajTimer = 0.0;
		x_plus.virtualBiped.trajParams = controller.recomputeTrajectoryParams(x_plus.virtualBiped);
	}

	public void initialize()
	{
		controller.computeEquilibriumStates();

		getComponents().getState().virtualBiped.trajParams = controller.recomputeTrajectoryParams(
		getComponents().getState().virtualBiped, params.getStepTime() * (.2 + 1.7 * Math.random()));
		getComponents().getState().virtualBiped.trajTimer = 0.0;

	}

}
