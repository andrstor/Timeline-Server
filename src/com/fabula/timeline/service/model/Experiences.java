/*******************************************************************************
 * Copyright (c) 2011 Andreas Storlien and Anders Kristiansen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Andreas Storlien and Anders Kristiansen - initial API and implementation
 ******************************************************************************/
package com.fabula.timeline.service.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper classs for XML/JSON-serialization
 * 
 * 
 * @author andekr
 *
 */
@XmlRootElement
public class Experiences {

	private List<Experience> experiences;
	
	/**
	 * No-arg constructor for JAXB
	 */
	public Experiences(){
		this.experiences = new LinkedList<Experience>();
	}
	
	public Experiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	@XmlElements(value = { @XmlElement(type=Experience.class) })
	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}
	
	@Override
	public String toString() {
		return "Experiences innholder "+getExperiences().size()+" experiencer";
	}
	
	
}
