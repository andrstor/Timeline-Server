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

import com.fabula.timeline.service.rest.impl.TimelineResource;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable(detachable="true")
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
	private List<Event> events;
	@Persistent
	private String sharingGroup;;
	

	public Experience() {}
	
	public Experience(String id, String title, boolean shared, String username, List<Event> events, String sharingGroup) {
		this.id = id;
		this.title = title;
		this.shared = shared;
		this.creator = username;
		this.events = events;
		this.sharingGroup = sharingGroup;
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
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
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
	
	@XmlElements(value = { @XmlElement(name="events", type=Event.class) })
	public List<Event> getEvents() {
        return this.events;
	}
	public void setEvents(List<Event> events) {
		if(events.size()>0)
			this.events = events;
	}
		
	public String getSharingGroup() {
		return sharingGroup;
	}

	public void setSharingGroup(String sharingGroup) {
		this.sharingGroup = sharingGroup;
	}
	
	public List<Event> getMoodEvents() {
		ArrayList<Event> moodEvents = new ArrayList<Event>();
		
		for (Event moodEvent : getEvents()) {
			if(moodEvent.getClassName().equals("MoodEvent"))
				moodEvents.add(moodEvent);
		}
		
		
        return moodEvents;
	}
	

	@Override
	public String toString() {
		     StringBuffer sb = new StringBuffer();
		    sb.append("<tr><td colspan ='3'>");
	        sb.append("<h3>Experience: ").append(getTitle()+"</h3><br />");
	        sb.append("Created by: ").append(getCreator()+"<br />");
	        if(isShared()){
	        TimelineResource res = new TimelineResource();
	        	sb.append("The experience is shared with: ").append(res.getGroup(getSharingGroup()).getName()+"<br />");
	        }
	        sb.append("</td></tr>");
	        if(events.size()>0){
	        sb.append("<tr><td colspan ='3'>");
	        sb.append("<h2><b><font size=12>"+events.size()+"</font></b> events:</h2>");
	        sb.append("</td></tr>");
	        int coloumnCounter = 0; 
	        sb.append("<tr>");
	        for (Event e : events) {
	        	if(coloumnCounter<3){
		        		sb.append(e.toString());
	        		coloumnCounter++;
	        	}else{
	        			sb.append("</tr><tr>");
		        		sb.append(e.toString());
	        		coloumnCounter=1;
	        	}
	        	
			}
	        //To make the table "complete"
	        for (int i = coloumnCounter; i < 3; i++) {
	        	 sb.append("<td/>");
			}
	        
	        sb.append("</tr>");
	        }
	        sb.append("<tr><td colspan ='3'>");
	        sb.append("<hr>");
	        sb.append("</td></tr>");
	        
	        return sb.toString();
	}

	public Event getEventByID(String id2) {
		for (Event event : getEvents()) {
			if(event.getId().equals(id2))
				return event;
		}

		return null;
	}

	public boolean hasMember(User user) {
		for (String groupID : user.getMemberOfGroups()) {
			String k = KeyFactory.createKeyString(Group.class.getSimpleName(), sharingGroup);
			if(k.equals(groupID))
				return true;
		}
			
		return false;
	}
}
