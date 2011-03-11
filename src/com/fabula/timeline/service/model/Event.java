package com.fabula.timeline.service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@PersistenceCapable
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Event {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;

    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String id;
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String experienceid;
	@Persistent
	private long datetimemillis;
	@Persistent
	private List<EventItem> eventItems;
	@Persistent
	private List<String> emotionList;
	@Persistent
	private String latitude;
	@Persistent
	private String longitude;

	
	public Event(String id, String experienceid, long datetimeinmillis, ArrayList<EventItem> eventItems, String latitude, String longitude) {
		this.id = id;
		this.experienceid = experienceid;
		this.datetimemillis = datetimeinmillis;
		this.eventItems = eventItems;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Event(){}

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

	public String getExperienceid() {
		return experienceid;
	}


	public void setExperienceid(String experienceid) {
		this.experienceid = experienceid;
	}

	public long getDatetimemillis() {
		return datetimemillis;
	}


	public void setDatetimemillis(long datetimemillis) {
		this.datetimemillis = datetimemillis;
	}
	
	@XmlElements(value = { @XmlElement(type=EventItem.class) })
	public List<EventItem> getEventItems() {
		if (eventItems == null) {
			eventItems = new ArrayList<EventItem>();
        }
        return this.eventItems;
	}
	
	public void setEventItems(List<EventItem> eventItems) {
		this.eventItems = eventItems;
	}
	
	@XmlElements(value = { @XmlElement(type=String.class) })
	public List<String> getEmotionList() {
		if (emotionList == null) {
			emotionList = new ArrayList<String>();
        }
        return this.emotionList;
	}
	
	public void setEmotionList(List<String> emotionList) {
		this.emotionList = emotionList;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		     StringBuffer sb = new StringBuffer();
	        sb.append("Event ID: ").append(getId()+"\n");
	        sb.append("Exp ID: ").append(getExperienceid()+"\n");
	        sb.append("Date: ").append(new Date(getDatetimemillis())+"\n");
	        sb.append("LatLng: ").append(getLatitude()+","+getLongitude()+"\n");
	        sb.append("Antall items: ").append(eventItems.size()+"\n");
	        for (EventItem eit : eventItems) {
	        	 sb.append("EventItem: ").append(eit.toString()+"\n");
			}
	        for (String emo : emotionList) {
	        	sb.append("Emotion: ").append(emo+"\n");
			}
	       
	        return sb.toString();
	}
	

	
}
