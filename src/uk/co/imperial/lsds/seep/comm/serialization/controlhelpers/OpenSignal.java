/*******************************************************************************
 * Copyright (c) 2013 Raul Castro Fernandez (Ra).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ra - Design and initial implementation
 ******************************************************************************/
package uk.co.imperial.lsds.seep.comm.serialization.controlhelpers;

public class OpenSignal {

	private int opId;
	
	public OpenSignal(){}
	
	public OpenSignal(int opId){
		this.opId = opId;
	}
	
	public int getOpId() {
		return opId;
	}
	public void setOpId(int opId) {
		this.opId = opId;
	}
}