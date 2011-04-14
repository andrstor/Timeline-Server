package com.fabula.timeline.service.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
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
public class Group {
	
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
    @Persistent
    @Extension(vendorName="datanucleus",value="true", key = "gae.pk-name")
	private String id;
    
    @Persistent
	private String name;
    
    @Persistent
    private Set<String> membersInGroup;
    
    @NotPersistent
	private List<User> members;
    
    public Group (){
    	members = new ArrayList<User>();
    }
	
	public Group(String name, List<User> members) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.members = members;
	}
	
	public Group(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		members = new ArrayList<User>();
	}
	
	public Group(String id, String name) {
		this.id = id;
		this.name = name;
		members = new ArrayList<User>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
	@XmlElements(value = { @XmlElement(name="members", type=User.class) })
	public List<User> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}
	
	public void addMember(User user) {
		members.add(user);
	}
	
	public void removeMember(User user) {
		members.remove(user);
	}
	
	public User getMember(int index) {
		return members.get(index);
	}
	
	public boolean hasMember(User user){
		for (String memberId : membersInGroup) {
			if(user.getEncodedKey().equals(memberId))
				return true;
		}
		
		return false;
	}
	
	@XmlTransient
	public Set<String> getMembersInGroup() {
		if(membersInGroup==null){
			membersInGroup = new HashSet<String>();
		}
		
		return membersInGroup;
	}

	public void setMembersInGroup(Set<String> membersInGroup) {
		this.membersInGroup = membersInGroup;
	}

	@Override
	public String toString() {
		  StringBuffer sb = new StringBuffer();
	        sb.append("Group : ").append(this.name+"\n");
	        sb.append("Members: ").append(membersInGroup.size()+"\n\n");
//	        for (User user : members) {
//	        	 sb.append("User: ").append(user+"\n");
//			}
	        
	        return sb.toString();
	}
	

}
