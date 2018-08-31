
package biped.app;

import java.io.File;

import com.be3short.io.xml.XMLParser;

import biped.reference.control.PlantFlowController;
import biped.reference.control.ReferenceJumpController;
import biped.reference.control.VirtualFlowController;
import biped.reference.control.VirtualJumpController;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.CollectionFile;
import edu.ucsc.cross.hse.core.file.FileBrowser;

/**
 * A content loader
 */
public class SystemLoader {

	public biped.parameters.base.State plantState;

	public biped.parameters.base.State virtualState;

	public perturbation.hybridsystem.Parameters perturbationParameters;

	public biped.parameters.base.Parameters bipedParameters;

	public static void main(String args[]) {

		SystemLoader loader = new SystemLoader();
		CollectionFile file = new CollectionFile(loader);
		file.saveToFile(FileBrowser.save(), false);
	}

	public SystemLoader() {

		plantState = new biped.parameters.base.State();
		virtualState = new biped.parameters.base.State();
		perturbationParameters = new perturbation.hybridsystem.Parameters(null, 0.0, false);
		bipedParameters = new biped.parameters.base.Parameters();
	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment environment) {

		loadEnvironmentContent(environment, FileBrowser.load());

	}

	/**
	 * Load the environment contents
	 * 
	 * @param env
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment env, File file) {

		CollectionFile cfile = CollectionFile.loadFromFile(file);
		SystemLoader loader = cfile.getObject(SystemLoader.class);
		if (loader != null) {
			HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters> plant = loadPlant(env,
					loader);
			HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters> virtual = loadVirtual(
					env, loader);
			HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters> reference = loadReference(
					env, loader);
			plant.getParams().virtual.setOutput(virtual);
			virtual.getParams().plant.setOutput(plant);
			virtual.getParams().reference.setOutput(reference);
			env.getSystems().add(plant, virtual, reference);
		}
		System.out.println(XMLParser.serializeObject(env));
		// System.exit(0);

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters> loadPlant(
			HSEnvironment environment, SystemLoader loader) {

		biped.plant.hybridsystem.Parameters params = new biped.plant.hybridsystem.Parameters(loader.bipedParameters);
		biped.parameters.base.State state = biped.parameters.base.State.getState(loader.plantState);

		PlantFlowController controller = new PlantFlowController(params);
		HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters> plant = ContentFactory
				.createRealSystem(state, params, controller);

		return plant;

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters> loadVirtual(
			HSEnvironment environment, SystemLoader loader) {

		biped.virtual.hybridsystem.Parameters params = new biped.virtual.hybridsystem.Parameters(
				loader.bipedParameters);
		biped.parameters.virtual.State state = new biped.parameters.virtual.State(loader.virtualState,
				params.bipedParams);

		// Initialize the virtual flow controller
		VirtualFlowController virtualFlowControl = new VirtualFlowController(params.bipedParams);
		// Initialize the virtual jump controller
		VirtualJumpController virtualJumpControl = new VirtualJumpController(params);

		HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters> plant = ContentFactory
				.createVirtualSystem(state, params, virtualFlowControl, virtualJumpControl);

		return plant;

	}

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters> loadReference(
			HSEnvironment environment, SystemLoader loader) {

		biped.parameters.virtual.Parameters params = new biped.parameters.virtual.Parameters(loader.bipedParameters);
		biped.parameters.virtual.State state = new biped.parameters.virtual.State(
				biped.parameters.base.State.getState(params.equilibParams.initialState), params);
		// Initialize the reference flow controller
		VirtualFlowController referenceFlowControl = new VirtualFlowController(params);
		// Initialize the reference jump controller
		ReferenceJumpController referenceJumpControl = new ReferenceJumpController(params);

		HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters> plant = ContentFactory
				.createReferenceSystem(state, params, referenceFlowControl, referenceJumpControl);

		return plant;

	}

}
