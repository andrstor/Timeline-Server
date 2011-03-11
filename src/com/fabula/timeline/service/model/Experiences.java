package com.fabula.timeline.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
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



	
	private List<Experience> experiences;
	
	/**
	 * No-arg constructor for JAXB
	 */
	public Experiences(){}
	
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
