package com.fabula.timeline.service.model;

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


@PersistenceCapable(detachable="true")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Event implements Comparable<Event>{
	
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
	private List<Emotion> emotionList;
	@Persistent
	private String latitude;
	@Persistent
	private String longitude;
	@Persistent
	private boolean shared;
	@Persistent
	private double valence;
	@Persistent
	private double arousal;
	@Persistent
	private String className;
	@Persistent
	private String creator;

	
	public Event(String id, String experienceid, long datetimeinmillis, List<EventItem> eventItems, String latitude, String longitude, boolean shared) {
		this.id = id;
		this.experienceid = experienceid;
		this.datetimemillis = datetimeinmillis;
		this.eventItems = eventItems;
		this.latitude = latitude;
		this.longitude = longitude;
		this.shared = shared;
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
	
	@XmlElements(value = { @XmlElement(name="eventItems",type=EventItem.class) })
	public List<EventItem> getEventItems() {
        return this.eventItems;
	}
	
	public void setEventItems(List<EventItem> eventItems) {
		this.eventItems = eventItems;
	}
	
	@XmlElements(value = { @XmlElement(type=Emotion.class) })
	public List<Emotion> getEmotionList() {
        return this.emotionList;
	}
	
	public void setEmotionList(List<Emotion> emotionList) {
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

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		     StringBuffer sb = new StringBuffer();
		    sb.append("<td valign='top'>");
	        sb.append("Event created by: ").append(getCreator()+"<br />");
	        sb.append("Date: ").append(new Date(getDatetimemillis())+"<br />");
	        sb.append("Position: ").append(getLatitude()+","+getLongitude()+"<br />");
	        
	        if(getClassName().equals("Event")){
	        	if(emotionList.size()>0){
		        	sb.append("Emotions connected to this event: ");
		        	for (Emotion emo : emotionList) {
			        	sb.append("Emotion: ").append(emo.getEmotionType().getName()+"<br />");
					}
		        }
		  
		        if(eventItems.size()>0){
		            sb.append("The event contains : ").append(eventItems.size()+" items<br />");
			        for (EventItem eit : eventItems) {
			        	 sb.append(eit.toString());
					}
		        }
	        }else if(getClassName().equals("MoodEvent")){
	        	sb.append("Mood: Valence:"+getValence()+", arousal:"+getArousal());
	        }
	        
	        
	        sb.append("</td>");
	        return sb.toString();
	}

	@Override
	public int compareTo(Event other) {
		Date thisDate = new Date(datetimemillis);
		Date otherDate = new Date(other.getDatetimemillis());
		return thisDate.compareTo(otherDate);
	}
	

	
}
