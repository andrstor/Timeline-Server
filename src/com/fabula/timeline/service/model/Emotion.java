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
public class Emotion {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    
    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String emotionid;
    @Persistent
	private EmotionEnum emotionType;
	
	public Emotion (){}

	public Emotion(String emotionid, EmotionEnum emotionType) {
		this.emotionid = emotionid;
		this.emotionType = emotionType;
	}

	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getEmotionid() {
		return emotionid;
	}

	public void setEmotionid(String emotionid) {
		this.emotionid = emotionid;
	}

	public EmotionEnum getEmotionType() {
		return emotionType;
	}

	public void setEmotionType(EmotionEnum emotionType) {
		this.emotionType = emotionType;
	}
	
	public enum EmotionEnum{
	LIKE("LIKE"), 
	COOL("COOL"), 
	DISLIKE("DISLIKE"),
	SAD("SAD");

	private final String value;
	
	
	EmotionEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getName(){
		return name();
	}
	
    public static EmotionEnum fromValue(String v) {
        for (EmotionEnum c: EmotionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	}
	
 
}
