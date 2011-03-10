package com.fabula.timeline.service.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class EventItem {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

    @Persistent
	private String id;
	@Persistent
	private String theclass;

	@Persistent
	private String username;
	@Persistent
	private String pictureFilename;
	@Persistent
	private String noteTitle;
	@Persistent
	private String noteText;
	
	public EventItem() {}
	
	public EventItem(String id, String user, String theclass, String pictureFilename, String noteTitle, String noteText) {
		this.id = id;
		this.username = user;
		this.theclass = theclass;
		this.pictureFilename = pictureFilename;
		this.noteText = noteText;
		this.noteTitle = noteTitle;
	}
	
	@XmlAttribute(name="class")
	public String getTheclass() {
		return theclass;
	}

	public void setTheclass(String theclass) {
		this.theclass = theclass;
	}
	
	@XmlAttribute
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
        this.key = key;
    }
	public String getPictureFilename() {
		return pictureFilename;
	}

	public void setPictureFilename(String pictureFilename) {
		this.pictureFilename = pictureFilename;
	}
	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	@Override
	public String toString() {
		return "EventItem: "+getId();
	}

}
