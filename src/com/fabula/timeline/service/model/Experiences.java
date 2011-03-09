package com.fabula.timeline.service.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper classs for XML-serialization
 * 
 * 
 * @author andekr
 *
 */
@XmlRootElement(name="timeline")
public class Experiences {



	
	private ArrayList<Experience> experiences;
	
	/**
	 * No-arg constructor for JAXB
	 */
	public Experiences(){}
	
	public Experiences(ArrayList<Experience> experiences) {
		this.experiences = experiences;
	}

	@XmlElementWrapper(name="experiences")
	@XmlElements(value = { @XmlElement(name="experience", type=Experience.class) })
	public ArrayList<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(ArrayList<Experience> experiences) {
		this.experiences = experiences;
	}
	
	@Override
	public String toString() {
		return "Experiences innholder "+getExperiences().size()+" experiencer";
	}
	
	
}
