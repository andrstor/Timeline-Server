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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

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
    private Key key;

    @Persistent
	private String id;
	@Persistent
	private String title;
	@Persistent
	private boolean shared;
	@Persistent
	private String username;
	@Persistent
	private List<Event> events;
	

	public Experience() {}
	
	public Experience(String id, String title, boolean shared, String username, ArrayList<Event> events) {
		this.id = id;
		this.title = title;
		this.shared = shared;
		this.username = username;
		this.events = events;
	}
	
	@XmlAttribute
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlAttribute
	public String getUsername() {
		return username;
	}
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
        this.key = key;
    }
	
	public void setUsername(String username) {
		this.username = username;
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
	
	@XmlElementWrapper(name="Events")
	@XmlElements(value = { @XmlElement(name="event", type=Event.class) })
	public List<Event> getEvents() {
		if (events == null) {
			events = new ArrayList<Event>();
        }
        return this.events;
	}
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	
	@Override
	public String toString() {
		     StringBuffer sb = new StringBuffer();
	        sb.append("Exp ID: ").append(getId()+"\n");
	        sb.append("Exp name: ").append(getTitle()+"\n");
	        sb.append("Exp creator: ").append(getUsername()+"\n");
	        sb.append("Antall events: ").append(events.size()+"\n\n");
	        for (Event e : events) {
	        	 sb.append("Events: ").append(e.toString()+"\n");
			}
	       
	        return sb.toString();
	}
}
