package com.fabula.timeline.service.model;

import java.util.LinkedList;
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
@XmlRootElement
public class Groups {

	private List<Group> groups;
	
	/**
	 * No-arg constructor for JAXB
	 */
	public Groups(){
		this.groups = new LinkedList<Group>();
	}
	
	public Groups(List<Group> groups) {
		this.groups = groups;
	}

	@XmlElements(value = { @XmlElement(type=Group.class) })
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public void addGroup(Group groupToAdd){
		this.groups.add(groupToAdd);
	}
	
	@Override
	public String toString() {
		return "Groups innholder "+getGroups().size()+" grupper";
	}
	
	
}
