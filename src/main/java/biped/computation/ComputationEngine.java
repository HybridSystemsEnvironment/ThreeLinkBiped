
package biped.computation;

public class ComputationEngine {

	public static double computeStepDetectH(biped.hybridsystem.State biped, biped.hybridsystem.Parameters parameters) {

		double hVal = parameters.stepAngle - biped.plantedLegAngle;
		return hVal;
	}
}
