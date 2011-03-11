package com.fabula.timeline.service.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author andekr
 *
 */
@PersistenceCapable
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Experience {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;

    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String id;
	@Persistent
	private String title;
	@Persistent
	private boolean shared;
	@Persistent
	private String creator;
	@Persistent
	private List<Event> events;
	

	public Experience() {}
	
	public Experience(String id, String title, boolean shared, String username, ArrayList<Event> events) {
		this.id = id;
		this.title = title;
		this.shared = shared;
		this.creator = username;
		this.events = events;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreator() {
		return creator;
	}
	
	
	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	
	@XmlElements(value = { @XmlElement(type=Event.class) })
	public List<Event> getEvents() {
		if (events == null) {
			events = new ArrayList<Event>();
        }
        return this.events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	
	@Override
	public String toString() {
		     StringBuffer sb = new StringBuffer();
	        sb.append("Exp ID: ").append(getId()+"\n");
	        sb.append("Exp name: ").append(getTitle()+"\n");
	        sb.append("Exp creator: ").append(getCreator()+"\n");
	        sb.append("Antall events: ").append(events.size()+"\n\n");
	        for (Event e : events) {
	        	 sb.append("Events: ").append(e.toString()+"\n");
			}
	       
	        return sb.toString();
	}

	public Event getEventByID(String id2) {
		for (Event event : getEvents()) {
			if(event.getId().equals(id2))
				return event;
		}

		return null;
	}
}
