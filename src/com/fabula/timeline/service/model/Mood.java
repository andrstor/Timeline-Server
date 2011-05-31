package com.fabula.timeline.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Mood {
	
	private double valence;
	private double arousal;
	
	public Mood(double valence, double arousal) {
		this.valence = valence;
		this.arousal = arousal;
	}
	
	public Mood() {
	}


	public double getValence() {
		return valence;
	}

	public void setValence(double valence) {
		this.valence = valence;
	}

	public double getArousal() {
		return arousal;
	}

	public void setArousal(double arousal) {
		this.arousal = arousal;
	}
	
	
	
	

}
