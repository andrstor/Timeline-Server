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

import java.util.HashSet;
import java.util.Set;

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
public class User {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String username;
    
    @Persistent
    private Set<String> memberOfGroups;
	
	public User() {}
	
	public User(String username) {
		this.username = username;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
	@XmlTransient
	public Set<String> getMemberOfGroups() {
		if(memberOfGroups==null){
			memberOfGroups = new HashSet<String>();
		}
		return memberOfGroups;
	}

	public void setMemberOfGroups(Set<String> memberOfGroups) {
		this.memberOfGroups = memberOfGroups;
	}
	
	public boolean isMemberOfGroup(Group groupToCheckMembership){
		for (String groupId : memberOfGroups) {
			if(groupToCheckMembership.getId().equals(groupId))
				return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return username;
	}
}
