
/* ===========================================================
 * HSE : The Hybrid Systems Environment
 * ===========================================================
 *
 * MIT License
 * 
 * Copyright (c) 2018 HybridSystemsEnvironment
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.

 *
 * ------------------------------------------------
 * HybridSys.java
 * ------------------------------------------------
 *
 * Original Author:  Brendan Short
 * Contributor(s):   
 *
 * Changes:
 * --------
 * 23-August-2018 : Version 1 (BS);
 * 
 */

package biped.app;

import edu.ucsc.cross.hse.core.hybridsystem.input.FlowMap;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowSet;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpMap;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpSet;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * Generalized hybrid system with dynamics given by externally defined mappings
 * and sets.
 * 
 * @author Brendan Short
 *
 * @param <X>
 *            class type of hybrid system state
 */
public class HybridSystem<X extends DataStructure, P extends DataStructure>
		extends edu.ucsc.cross.hse.core.modeling.HybridSystem<X> {

	/**
	 * Externally defined flow map
	 */
	private FlowMap<X, P> flowMap;

	/**
	 * Externally defined jump map
	 */
	private JumpMap<X, P> jumpMap;

	/**
	 * Externally defined flow set
	 */
	private FlowSet<X, P> flowSet;

	/**
	 * Externally defined jump set
	 */
	private JumpSet<X, P> jumpSet;

	private P params;

	/**
	 * Constructor for generalized hybrid system
	 * 
	 * @param state
	 *            system state
	 * @param flow_map
	 *            flow map definition
	 * @param jump_map
	 *            jump map definition
	 * @param flow_set
	 *            flow set definition
	 * @param jump_set
	 *            jump set definition
	 * @param parameters
	 *            list of any components (parameters, inputs, networks etc)
	 */
	public HybridSystem(X state, P params, FlowMap<X, P> flow_map, JumpMap<X, P> jump_map, FlowSet<X, P> flow_set,
			JumpSet<X, P> jump_set, Object... components) {

		super(state, components);
		this.params = params;
		flowMap = flow_map;
		jumpMap = jump_map;
		flowSet = flow_set;
		jumpSet = jump_set;
	}

	/**
	 * Computes the flow set
	 */
	@Override
	public boolean C(X x) {

		return flowSet.C(x, params);
	}

	/**
	 * Computes the jump set
	 */
	@Override
	public boolean D(X x) {

		return jumpSet.D(x, params);
	}

	/**
	 * Computes the flow map
	 */
	@Override
	public void F(X x, X x_dot) {

		flowMap.F(x, x_dot, params);
	}

	/**
	 * Computes the jump map
	 */
	@Override
	public void G(X x, X x_plus) {

		jumpMap.G(x, x_plus, params);

	}

	/**
	 * @param flowMap
	 *            the flowMap to set
	 */
	public void setFlowMap(FlowMap<X, P> flowMap) {

		this.flowMap = flowMap;
	}

	/**
	 * @param jumpMap
	 *            the jumpMap to set
	 */
	public void setJumpMap(JumpMap<X, P> jumpMap) {

		this.jumpMap = jumpMap;
	}

	/**
	 * @param flowSet
	 *            the flowSet to set
	 */
	public void setFlowSet(FlowSet<X, P> flowSet) {

		this.flowSet = flowSet;
	}

	/**
	 * @param jumpSet
	 *            the jumpSet to set
	 */
	public void setJumpSet(JumpSet<X, P> jumpSet) {

		this.jumpSet = jumpSet;
	}

	/**
	 * @return the flowMap
	 */
	public FlowMap<X, P> getFlowMap() {

		return flowMap;
	}

	/**
	 * @return the jumpMap
	 */
	public JumpMap<X, P> getJumpMap() {

		return jumpMap;
	}

	/**
	 * @return the flowSet
	 */
	public FlowSet<X, P> getFlowSet() {

		return flowSet;
	}

	/**
	 * @return the jumpSet
	 */
	public JumpSet<X, P> getJumpSet() {

		return jumpSet;
	}

	/**
	 * @return the params
	 */
	public P getParams() {

		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(P params) {

		this.params = params;
	}

}
