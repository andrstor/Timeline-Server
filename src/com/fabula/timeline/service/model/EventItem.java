package com.fabula.timeline.service.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@PersistenceCapable
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class EventItem {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;

    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String id;
	@Persistent
	private String creator;
	@Persistent
	private String pictureFilename;
	@Persistent
	private String noteTitle;
	@Persistent
	private String noteText;
	@Persistent
	private String className;
	
	public EventItem() {}
	
	public EventItem(String id, String user, String pictureFilename, String noteTitle, String noteText) {
		this.id = id;
		this.creator = user;
		this.pictureFilename = pictureFilename;
		this.noteText = noteText;
		this.noteTitle = noteTitle;
	}
	
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String username) {
		this.creator = username;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
		
	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
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
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		  StringBuffer sb = new StringBuffer();
	        sb.append("EventItem ID: ").append(getId()+"\n");
	        sb.append("EventItem title: ").append(getNoteTitle()+"\n");
	        sb.append("EventItem text: ").append(getNoteText()+"\n");
	       
	    return sb.toString();
	}

}
