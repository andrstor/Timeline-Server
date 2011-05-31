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
public class Users {

	private List<User> users;
	
	/**
	 * No-arg constructor for JAXB
	 */
	public Users(){
		this.users = new LinkedList<User>();
	}
	
	public Users(List<User> users) {
		this.users = users;
	}

	@XmlElements(value = { @XmlElement(type=User.class) })
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	@Override
	public String toString() {
		return "Users contains "+getUsers().size()+" users";
	}
	
	
}
