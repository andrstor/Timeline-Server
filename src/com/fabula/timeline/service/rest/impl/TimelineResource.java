package com.fabula.timeline.service.rest.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Transactional;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fabula.timeline.service.Helpers;
import com.fabula.timeline.service.model.Event;
import com.fabula.timeline.service.model.Experience;
import com.fabula.timeline.service.model.Experiences;
import com.fabula.timeline.service.model.Group;
import com.fabula.timeline.service.model.Groups;
import com.fabula.timeline.service.model.Mood;
import com.fabula.timeline.service.model.User;
import com.fabula.timeline.service.model.Users;
import com.google.appengine.api.datastore.KeyFactory;


/**
 * REST services for Fabula Timeline Android Application.
 * 
 * 
 * @author andekr
 * @author andrstor
 *
 */
@Path("/")
public class TimelineResource {
	
	//EVENT RESOURCES
	 /*
	  *  ONE EVENT
	  */
 
	/**
	 * PUT
	 * <br>
	 * Store or update one Event
	 * <br>
	 * <b>URL: /event/</b>
	 * <br> 
	 * Consumes a complete event as JSON or XML and stores/updates in Google App Engine.
	 * Requires that the "father-experience" is already registered.
	 * <br>
	 * Example of JSON:
	 * <br>
	 * <code>
	 * {"eventItems":[
					{"className":"SimpleNote",
					"creator":"anderskri@gmail.com",
					"id":"516733c5-ef9e-4324-bd93-ee204a582036",
					"noteText":"Examplenote","noteTitle":"Example"}
					],
				"className":"Event",
				"creator":"anderskri@gmail.com",
				"experienceid":"a6a18076-9b96-4952-8bb6-cf56fee7b759",
				"id":"7f8189b9-11cf-4b8a-9456-29d9359f6dad",
				"latitude":63.40958633333333,
				"longitude":10.431676433333333,
				"valence":0.0,
				"arousal":0.0,
				"datetimemillis":1304362625287,
				"shared":true,
				"average":false
				} 

	 * </code>
	 * 
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Path("/event/")
	public void putEvent(Event event) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			//Connect to Experience. Requires the experience to be present in Google App Engine.
			String k = KeyFactory.createKeyString(Experience.class
					.getSimpleName(), event.getExperienceid());
			Experience exp = pm.getObjectById(Experience.class, k);

			Event ev = exp.getEventByID(event.getId());
			if (ev == null) {
				exp.getEvents().add(event);
			} else {
				ev.setEmotionList(event.getEmotionList());
				ev.setEventItems(event.getEventItems());
				ev.setShared(event.isShared());
				event = ev;
			}

			pm.makePersistent(exp);
			pm.makePersistent(event);
		} finally {
			pm.close();
		}
	}

	/*
	 *  ALL EVENTS
	 */
		 

	/**
	 * GET
	 * <br>
	 * Get events as String (Mainly for testing purposes)
	 * <br>
	 * <b>URL: /events/</b>
	 * <br>
	 * Produces toString of all events as String
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/events/")
	public String getEvents() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select from " + Event.class.getName());
		List<Event> evs = (List) q.execute();

		String events = "";

		for (Event event : evs) {
			events += event.toString();
		}

		return events;
	}
	
	// EXPERIENCES

	/*
	 * ONE EXPERIENCE
	 */

	/**
	 * PUT
	 * <br>
	 * Store or update one Experience
	 * <br>
	 * <b>URL: /experience/</b>
	 * <br>
	 * Consumes a complete experience as JSON or XML and stores/updates in Google App Engine.
	 * Also stores all children
	 * <br>
	 * Example of JSON:
	 * <br>
	 * <code>
	 * {
   "creator":"anderskri@gmail.com",
   "events":[
      {
         "className":"MoodEvent",
         "creator":"anderskri@gmail.com",
         "datetimemillis":"1301556003782",
         "experienceid":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
         "id":"43799bf7-53fa-4acc-8045-0d8a0f29ea8b",
         "latitude":"63.41652448",
         "longitude":"10.4027416",
         "valence":"1.0",
         "arousal":"1.0",
         "shared":"false",
         "average":false
      },
      {
         "className":"MoodEvent",
         "creator":"andrstor87@gmail.com",
         "datetimemillis":"1301556030401",
         "experienceid":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
         "id":"9689012d-520b-4bf1-b19f-06c1072f9f8b",
         "latitude":"63.4165383",
         "longitude":"10.40269734",
         "valence":"0.0",
         "arousal":"1.0",
         "shared":"false",
         "average":false
      },
      {
         "className":"Event",
         "creator":"andrstor87@gmail.com",
         "datetimemillis":"1301556205024",
         "emotionList":[
            {
               "emotionType":"LIKE",
               "emotionid":"22d08b27-0266-4561-8a16-96b86b099aa9"
            }
         ],
         "eventItems":[
            {
               "className":"SimplePicture",
               "creator":"andrstor87@gmail.com",
               "filename":"-1779601815.jpg",
               "id":"91e0b5ba-a384-40b4-88ac-edecf8c28e91"
            },
            {
               "className":"SimpleNote",
               "creator":"andrstor87@gmail.com",
               "id":"9b3dd9a7-ac7e-44e2-a62d-e6191c2a3aff",
               "noteText":"The most productive in the world.",
               "noteTitle":"My workplace"
            }
         ],
         "experienceid":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
         "id":"b05348b2-d827-4192-b153-c283ac251586",
         "latitude":"63.41654401",
         "longitude":"10.4027174",
         "valence":"0.0",
         "arousal":"0.0",
         "shared":"true",
         "average":false
      },
      {
         "className":"Event",
         "creator":"anderskri@gmail.com",
         "datetimemillis":"1301564716999",
         "eventItems":[
            {
               "className":"SimpleNote",
               "creator":"test@timelineapp.no",
               "id":"5c58f9ae-3f6a-4a79-b09e-a3ad9b58d26b",
               "noteText":"Finally  Fabula is on Timeline!",
               "noteTitle":"Fabula on Timeline"
            }
         ],
         "experienceid":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
         "id":"adf77be1-fc8d-4a5b-b465-16c4d09a68f6",
         "latitude":"63.41654447",
         "longitude":"10.402711",
         "valence":"0.0",
         "arousal":"0.0",
         "shared":"true",
         "average":false
      },
      {
         "className":"Event",
         "creator":"test@timelineapp.no",
         "datetimemillis":"1301567596334",
         "eventItems":[
            {
               "className":"SimplePicture",
               "creator":"test@timelineapp.no",
               "filename":"1689419062.mp4",
               "id":"5fc3463d-31ef-42a4-8b5e-3ae5a9a58f57"
            }
         ],
         "experienceid":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
         "id":"76d46599-eda8-487d-a829-b5517bc6ab43",
         "latitude":"0.0",
         "longitude":"0.0",
         "valence":"0.0",
         "arousal":"0.0",
         "shared":"true",
         "average":false
      }
   ],
   "id":"4cfb71dc-30f6-47e5-829a-a21e08e568b9",
   "shared":"true",
   "sharingGroup":"4bbd7541-5c49-45e5-87c9-d75ac1e3a36a",
   "title":"Fabula"
}
	 * </code>
	 * 
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Path("/experience/")
	public void putExperience(Experience experience) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(experience);
		} finally {
			pm.close();
		}

		System.out.println("Experience: " + experience.toString());
	}
 
	/*
	 * EXPERIENCES
	 */

	/**
	 * GET
	 * <br>
	 * Gets all experiences of a user
	 * <br>
	 * <b>URL: /experiences/{username}</b>
	 * <br>
	 * Takes username of the user to get experiences
	 * Produces the all experiences shared with the user as JSON or XML
	 * 
	 * 
	 * 	
	 * @param username The username of the user to get shared experiences from
	 * @return list of experiences wrapped in a Experiences object.
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/experiences/{username}")
	@Transactional
	public Experiences GetSharedExperiences(@PathParam("username") String username) {
		Experiences exps = getExperiences(username);
		Experiences sharedExps = new Experiences();

			User user = getUser(username);
			for (Experience experience : exps.getExperiences()) {
				if (experience.isShared() && experience.hasMember(user)) {
					for (Event event : experience.getEvents()) {
						if (!event.isShared()) {
							experience.getEvents().remove(event);
						}
					}
					sharedExps.getExperiences().add(experience);
				}
			}

		return sharedExps;
	}
	
	/**
	 * Helper method to retrieve all experiences of a user
	 * <br>
	 * 
	 * @param username The username of the user to get experiences from
	 * @return list of experiences wrapped in a Experiences object.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Experiences getExperiences(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Experiences exps = new Experiences();
		List<Experience> experiencesFromServer = null;

		try {
			Query q = pm.newQuery("select from " + Experience.class.getName());
			experiencesFromServer = (List) q.execute();
			for (Experience experience : experiencesFromServer) {
				pm.detachCopy(experience);
					pm.detachCopyAll(experience.getEvents());
					for (Event event : experience.getEvents()) {
						pm.detachCopyAll(event.getEventItems());
						pm.detachCopyAll(event.getEmotionList());
					}
			}
		} finally {
			pm.close();
		}

		exps.setExperiences(experiencesFromServer);

		return exps;
	}
	
	/**
	 * Helper method to retrieve all shared experiences of a user as well as the MoodEvents of the user.
	 * <br>
	 * This is a helper method used by the Timeline Activity Report Resource.
	 * 
	 * @param username The username of the user to get experiences from
	 * @return list of experiences wrapped in a Experiences object.
	 */
	@Transactional
	public Experiences GetAllExperiencesWithSharedEventsAndUsersMoodEvent(String username) {
		Experiences exps = getExperiences(username);
		Experiences sharedExps = new Experiences();

		User user = getUser(username);
		for (Experience experience : exps.getExperiences()) {
			if (experience.isShared() && experience.hasMember(user)) {
				for (Event event : experience.getEvents()) {
					if ((event.getClassName().equals("MoodEvent") && !event.getCreator().equals(username)) || !event.isShared()) {
						experience.getEvents().remove(event);
					}
				}
				sharedExps.getExperiences().add(experience);
			} 

		}

	return sharedExps;
	}
	
	/**
	 * GET
	 * <br>
	 * Gets all experiences in server
	 * <br>
	 * <b>URL: /experiences/</b>
	 * <br>
	 * Gets all the experiences stored. <i>Mainly for testing and administrative purposes.</i>
	 * Produces the all experiences as JSON or XML
	 * 
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/experiences/")
	@Transactional
	public Experiences GetAllExperiences() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Extent<Experience> extent = pm.getExtent(Experience.class, false);

		Experiences experiences = new Experiences();
		try {
			for (Experience experience : extent) {
				experiences.getExperiences().add(experience);
			}

		} finally {
			extent.closeAll();
		}

		return experiences;
	}
 
	
	/**
	 * PUT
	 * <br>
	 * Stores/updates all experiences in server
	 * 
	 * <b>URL: /experiences/</b>
	 * <br>
	 * Stores all the experiences sent in.
	 * Produces the all experiences as JSON or XML
	 * 
	 * @param experiences {@link Experiences} to insert
	 * 
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Path("/experiences/")
	public void putExperiences(Experiences experiences) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (Experience experience : experiences.getExperiences()) {
				try {
					pm.makePersistent(experience);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			pm.close();
		}
    
    System.out.println("Experiences: "+experiences.toString());
    
 } 
 
	// USERS

	/**
	 * PUT
	 * <br>
	 * Registers a user
	 * <br>
	 * <b>URL: /user/</b>
	 * <br>
	 * Consumes a JSON or XML with the user information. Currently only a username.
	 * <br>
	 * Example JSON:
	 * <br>
	 * <code>
	 * {"username":"yourusername@mail.com"}
	 * </code>
	 * 
	 * 
	 * @param user {@link User} to insert
	 * 
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Path("/user/")
	public void putUser(User user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			try {
				pm.makePersistent(user);
				//Add group to user if user is in group(user might be deleted, and want to re-register
				Groups groups = getGroups();
				for (Group group : groups.getGroups()) {
					if(group.hasMember(user)){
						String k = KeyFactory.createKeyString(Group.class
								.getSimpleName(), group.getId());
						group = pm.getObjectById(Group.class, k);
						user.getMemberOfGroups().add(k);
					}
				}

				pm.makePersistent(user);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

	}
 
	/**
	 * GET
	 * <br>
	 * Gets the user with the username sent as parameter
	 * <br>
	 * <b>URL: /user/{username}</b>
	 * <br>
	 * Produces JSON or XML with user information.
	 * 
	 * @param username of user to get
	 * @return {@link User} the user with the username sent as parameter
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/user/{username}/")
	public User getUser(@PathParam("username") String username) {
		User user = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			try {
				String k = KeyFactory.createKeyString(User.class
						.getSimpleName(), username);
				user = pm.getObjectById(User.class, k);
				System.out.println(user);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		return user;
	}
 
	/**
	 * GET
	 * <br>
	 * Gets all the users
	 * <br>
	 * <b>URL: /users/ </b>
	 * 
	 * Produces JSON or XML with all users.
	 * <br>
	 * Example of returning JSON:
	 * <br>
	 * <code>
	 * {"users":[{"username":"user1@gmail.com"},{"username":"user2@gmail.com"},{"username":"test@timelineapp.no"}]}
	 * </code>
	 * 
	 * @return {@link Users} the users
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/users/")
	public Users getUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List userList = null;
		try {
			try {
				Query q = pm.newQuery("select from " + User.class.getName());
				userList = (List) q.execute();
				System.out.println(userList.size());

			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		Users users = new Users(userList);

		return users;
	}
 
 //GROUPS
 
	/**
	 * GET
	 * <br>
	 * Gets the group with the id sent as parameter
	 * <br>
	 * <b>URL: /group/{groupid}</b>
	 * <br>
	 * Produces JSON or XML with group information.
	 * 
	 * @param groupid of group to get
	 * @return {@link Group} the group with the id sent as parameter
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/group/{groupid}")
	public Group getGroup(@PathParam("groupid") String groupid) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Group group = null;
		try {
			try {
				String k = KeyFactory.createKeyString(Group.class
						.getSimpleName(), groupid);
				group = pm.getObjectById(Group.class, k);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		return group;
	}
 
	/**
	 * PUT
	 * <br>
	 * Registers a group
	 * <br>
	 * <b>URL: /group/</b>
	 * <br>
	 * Consumes a JSON or XML with the group information. The JSON should contain at least one member(the creator).
	 * 
	 * 
	 * @param group The {@link Group} to register
	 * 
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Path("/group/")
	public void putGroup(Group group) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = null;
		try {
			try {
				pm.makePersistent(group);
				user = getUser(group.getMembers().get(0).getUsername());
				addUserToGroup(group.getId(), user.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}
	}
 
	/**
	 * DELETE
	 * <br>
	 * Deletes a group
	 * <br>
	 * <b>URL: /group/{groupid}</b>
	 * <br>
	 * Takes a group id as parameter. Deletes the group with the given id.
	 * Returns a boolean as String of the status of the delete. Not implemented yet.
	 * 
	 * @param groupid groupid of group to delete
	 * 
	 */
	@DELETE
	@Produces("text/plain")
	@Path("/group/{groupid}/")
	public void removeGroup(@PathParam("groupid") String groupid) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Group group = null;
		try {
			try {
				String k = KeyFactory.createKeyString(Group.class
						.getSimpleName(), groupid);
				group = pm.getObjectById(Group.class, k);

				pm.deletePersistent(group);
				// TODO: set success

			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

	}
 
	/**
	 * GET
	 * <br>
	 * Gets the groups the user is member of
	 * <br>
	 * <b>URL: /groups/{username}</b>
	 * <br>
	 * Produces JSON or XML with list of groups the user is member of.
	 * 
	 * 
	 * @param username of user to get groups from.
	 * @return {@link Groups} the user is member of.
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/groups/{username}")
	@Transactional
	public Groups getGroupsOfUser(@PathParam("username") String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Extent<Group> extent = pm.getExtent(Group.class, false);
		Groups groups = new Groups();
		User user = null;
		try {
			try {
				user = getUser(username);
				List<Group> tempGroups = new ArrayList<Group>();
				System.out.println(tempGroups);
				for (Group tgroup : extent) {
					Group group = pm.detachCopy(tgroup);
					System.out.println(group);
					if (group.hasMember(user)) {
						groups.addGroup(group);
						for (String member : group.getMembersInGroup()) {
							System.out.println(member);
							group.addMember(pm
									.getObjectById(User.class, member));
							System.out.println(group);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			extent.closeAll();
		}

		return groups;
	}
	
	/**
	 * GET
	 * <br>
	 * Gets all the groups
	 * <br>
	 * <b> URL: /groups/</b>
	 * <br>
	 * Produces JSON or XML with list of all groups
	 * 
	 * 
	 * @return {@link Groups} the user is member of.
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/groups/")
	@Transactional
	public Groups getGroups() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Extent<Group> extent = pm.getExtent(Group.class, false);
		Groups groups = new Groups();
		try {
			try {
				List<Group> tempGroups = new ArrayList<Group>();
				System.out.println(tempGroups);
				for (Group tgroup : extent) {
					Group group = pm.detachCopy(tgroup);
						groups.addGroup(group);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			extent.closeAll();
		}

		return groups;
	}
 
	/**
	 * 
	 * GET
	 * <br>
	 * Gets if user is member in a group. 
	 * <i>For testing purposes.</i>
	 * <br>
	 * <b>URL: /group/{groupid}/{username}/</b>
	 * 
	 * @param groupid
	 * @param username
	 * @return String true if user is in group
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/group/{groupid}/{username}")
	public String isUserInGroup(@PathParam("groupid") String groupid,
			@PathParam("username") String username) {

		boolean groupHasMember = false;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			try {
				User user = pm.getObjectById(User.class, username);
				String k = KeyFactory.createKeyString(Group.class
						.getSimpleName(), groupid);
				Group group = pm.getObjectById(Group.class, k);

				 groupHasMember = group.hasMember(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		return "Group has member: " + groupHasMember;

	}
 
 
	/**
	 * 
	 * PUT
	 * <br>
	 * Adds a user to a group
	 * <br>
	 * <b>URL: /group/{groupid}/user/{username}/</b>
	 * <br>
	 * TODO: Register user if not registered	 
	 *
	 * 
	 * @param groupid Id of group to add user to
	 * @param username username of already registered user
	 */
	@PUT
	@Produces( { "application/json", "application/xml" })
	@Path("/group/{groupid}/user/{username}/")
	public void addUserToGroup(@PathParam("groupid") String groupid,
			@PathParam("username") String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = null;
		Group group = null;
		try {
			try {
				user = getUser(username);
				String k = KeyFactory.createKeyString(Group.class
						.getSimpleName(), groupid);
				group = pm.getObjectById(Group.class, k);
				user.getMemberOfGroups().add(k);
				group.getMembersInGroup().add(user.getEncodedKey());

				pm.makePersistent(group);
				pm.makePersistent(user);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

	}
 
	/**
	 * DELETE
	 * <br>
	 * Removes a user form a group
	 * <br>
	 * <b> URL: /group/{groupid}/user/{username}/</b>
	 * 
	 * @param groupid id of group to remove user from
	 * @param username of user to remove
	 */
	@DELETE
	@Produces("text/plain")
	@Path("/group/{groupid}/user/{username}/")
	public void removeUserFromGroup(@PathParam("groupid") String groupid,
			@PathParam("username") String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = null;
		Group group = null;
		try {
			try {
				user = getUser(username);
				String k = KeyFactory.createKeyString(Group.class.getSimpleName(), groupid);
				group = pm.getObjectById(Group.class, k);
				boolean groupDeletedFromUser = user.getMemberOfGroups().remove(k);
				boolean userDeletedFromGroup = group.getMembersInGroup()
						.remove(user.getEncodedKey());

				if (groupDeletedFromUser && userDeletedFromGroup) {
					pm.makePersistent(group);
					pm.makePersistent(user);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}
	}
 
 
 //MOOD
 
	/**
	 * GET
	 * <br>
	 * Gets the average mood of an experience by ID
	 * <br>
	 * <b>URL: /mood/id/{experienceid}/</b>
	 * <br>
	 * Produces a JSON String with the average mood including valence and arousal.<br>
	 * Produces a Mood as JSON or XML. Example of JSON: <br>
	 * <code>
	 * {"valence":"0.1", "arousal":"0.4"}
	 * </code>
	 * <br>
	 * This is the preferred method to fetch the average mood.
	 * 
	 * @param experienceid Experience id of the experience to get average mood from.
	 * @return averageMood The average {@link Mood} of the {@link Experience}
	 * 
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/mood/id/{experienceid}/")
	public Mood getAverageMoodByID(@PathParam("experienceid") String experienceid) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Mood moodAverage = null;
		Experience experience = null;
		try {
			try {
				String k = KeyFactory.createKeyString(Experience.class.getSimpleName(), experienceid);
				experience = pm.getObjectById(Experience.class, k);
				System.out.println(experience);
				moodAverage = Helpers.getAverageMood(experience.getMoodEvents());
				               
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		return moodAverage;
	}
 
	/**
	 * GET
	 * <br>
	 * Gets the average mood of an experience by title
	 * <br>
	 * <i>Note: Not as accurate as ID, and is case sensitive. Merely for testing.</i>
	 * <br>
	 * 
	 * <b>URL: /mood/title/{title}/</b>
	 * 
	 * @param title title of the experience to get average mood from.
	 * @return averageMood The average {@link Mood} of the {@link Experience}
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Produces( { "application/json", "application/xml" })
	@Path("/mood/title/{title}/")
	public Mood getAverageMoodByName(@PathParam("title") String title) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Mood moodAverage = null;
		List<Experience> experiences = new ArrayList<Experience>();
		try {
			try {
				Query q = pm.newQuery("select from "
						+ Experience.class.getName() + 
						" where title=='"+ title + "'");
				experiences = (List) q.execute();
				System.out.println(experiences.get(0));
				moodAverage = Helpers.getAverageMood(experiences.get(0).getMoodEvents());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			pm.close();
		}

		return moodAverage;
	}
	
	/**
	 * A test method to satisfy input format(csv) for the Nabaztag rabbit.
	 * 
	 * 
	 * @param title
	 */
	@GET
	@Produces("text/plain")
	@Path("rabbit/mood/title/{title}/")
	public String getRabbitAverageMoodByName(@PathParam("title") String title) {
		Mood moodAverage = getAverageMoodByName(title);

		return moodAverage.getValence()+","+moodAverage.getArousal();

	}
 
	/**
	 * PUT
	 * <br>
	 * Inserts a mood to an experience only by parameters,
	 * and no content needed.
	 * <br>
	 * <i>Uses experience title to identify experience. Merely for testing.<br>
	 * Should use the method taking in mood as JSON, not in parameters.</i>
	 * <br>
	 * 
	 * <b>URL: /mood/title/{title}/{username}/{moodX}/{moodY}</b>
	 * 
	 * @param title of experience to insert mood
	 * @param username of user to set as creator
	 * @param valence Mood valence to insert. Double from 0 to 1. 
	 * @param arousal Mood arousal to insert. Double from 0 to 1. 
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Produces( { "application/json", "application/xml" })
	@Path("/mood/title/{title}/{username}/{valence}/{arousal}")
	public void putMoodByName(@PathParam("title") String title,
			@PathParam("username") String username,
			@PathParam("valence") double valence, @PathParam("arousal") double arousal) {
		
		if(valence>=0 && valence <=1 && arousal>=0 && arousal<=1){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			User user = getUser(username);
			Query q = pm.newQuery("select from " + Experience.class.getName()
					+ " where title=='" + title + "'");
			List<Experience> experiences = (List) q.execute();
			Experience experience = experiences.get(0);
			Event newMoodEvent = new Event();
			newMoodEvent.setId(UUID.randomUUID().toString());
			newMoodEvent.setExperienceid(experience.getId());
			newMoodEvent.setClassName("MoodEvent");
			newMoodEvent.setCreator(user.getUsername());
			newMoodEvent.setDatetimemillis(new Date().getTime());
			newMoodEvent.setShared(false);
			newMoodEvent.setLatitude("0");
			newMoodEvent.setLongitude("0");
			newMoodEvent.setValence(valence);
			newMoodEvent.setArousal(arousal);
	
			try {
				try {
					experience.getEvents().add(newMoodEvent);
					pm.makePersistent(experience);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} finally {
				pm.close();
			}
		}else{
			//Do not insert
		}

	}
 
	 
	/**
	 * PUT
	 * <br>
	 * Inserts a mood to an experience only by parameters,
	 * and no content needed
	 * <br>
	 * Uses experience id to identify experience
	 * <br>
	 * <i>Merely for testing.Should use the method taking in mood as JSON, not in parameters.</i>
	 * <br>
	 * 
	 * <br>
	 * <b>URL: /mood/id/{id}/{username}/{moodX}/{moodY}</b>
	 * 
	 * @param id of experience to insert mood
	 * @param username of user to set as creator
	 * @param valence Mood valence to insert. Double from 0 to 1. 
	 * @param arousal Mood arousal to insert. Double from 0 to 1. 
	 */
	@PUT
	@Produces( { "application/json", "application/xml" })
	@Path("/mood/id/{id}/{username}/{valence}/{arousal}")
	public void putMoodByID(@PathParam("id") String id,
			@PathParam("username") String username,
			@PathParam("valence") double valence, @PathParam("arousal") double arousal) {
		if(valence>=0 && valence <=1 && arousal>=0 && arousal<=1){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			User user = getUser(username);
			String k = KeyFactory.createKeyString(Experience.class.getSimpleName(),
					id);
			Experience experience = pm.getObjectById(Experience.class, k);
			Event newMoodEvent = new Event();
			newMoodEvent.setId(UUID.randomUUID().toString());
			newMoodEvent.setExperienceid(id);
			newMoodEvent.setClassName("MoodEvent");
			newMoodEvent.setCreator(user.getUsername());
			newMoodEvent.setDatetimemillis(new Date().getTime());
			newMoodEvent.setShared(false);
			newMoodEvent.setLatitude("0");
			newMoodEvent.setLongitude("0");
			newMoodEvent.setValence(valence);
			newMoodEvent.setArousal(arousal);
	
	
			try {
				try {
					experience.getEvents().add(newMoodEvent);
					pm.makePersistent(experience);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} finally {
				pm.close();
			}
		}else{
			//Do not insert
		}
			

	}
	
	/**
	 * PUT
	 * <br>
	 * Inserts a mood to an experience
	 * <br>
	 * Uses experience id to identify experience
	 * 
	 * <br>
	 * <b>URL: /mood/id/{id}/{username}/{moodX}/{moodY}</b>
	 * <br>
	 * Consumes a Mood as JSON or XML. Example of JSON: <br>
	 * <code>
	 * {"valence":"0.1", "arousal":"0.4"}
	 * </code>
	 * 
	 * @param id of experience to insert mood
	 * @param username of user to set as creator
	 * @param mood The {@link Mood} to insert.
	 */
	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	@Path("/mood/id/{id}/{username}/")
	public void putMood(Mood mood, @PathParam("username") String username, @PathParam("id") String id) {
		if(mood.getValence()>=0 && mood.getValence() <=1 && mood.getArousal()>=0 & mood.getArousal()<=1){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			User user = getUser(username);
			String k = KeyFactory.createKeyString(Experience.class.getSimpleName(),
					id);
			Experience experience = pm.getObjectById(Experience.class, k);
			Event newMoodEvent = new Event();
			newMoodEvent.setId(UUID.randomUUID().toString());
			newMoodEvent.setExperienceid(id);
			newMoodEvent.setClassName("MoodEvent");
			newMoodEvent.setCreator(user.getUsername());
			newMoodEvent.setDatetimemillis(new Date().getTime());
			newMoodEvent.setShared(false);
			newMoodEvent.setLatitude("0");
			newMoodEvent.setLongitude("0");
			newMoodEvent.setValence(mood.getValence());
			newMoodEvent.setArousal(mood.getValence());
	
	
			try {
				try {
					experience.getEvents().add(newMoodEvent);
					pm.makePersistent(experience);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} finally {
				pm.close();
			}
		}else{
			//Do not insert
		}
	}
	
	/**
	 * The resource to send Timeline Activity Report.
	 * <br>
	 * Create a HTML e-mail containing the activity of the user sent as parameter.
	 * <br>
	 * Iterates all shared experiences, and adds them to the e-mail using the objects toString methods.
	 * <br>
	 * Sends the e-mail to the users e-mail(which is the username).
	 * 
	 * @param username of the user to create and send activity report to.
	 */
	@GET
	@Path("/status/{username}/")
	public void sendTimelineActivityReport(@PathParam("username") String username) {
		  Properties props = new Properties();
	      Session session = Session.getDefaultInstance(props, null);

	      Experiences exps = GetAllExperiencesWithSharedEventsAndUsersMoodEvent(username);

	      try {
	          Message msg = new MimeMessage(session);
	          msg.setFrom(new InternetAddress("timelineapplication@gmail.com", "Timeline Admin"));
	          msg.addRecipient(Message.RecipientType.TO,
	                           new InternetAddress(username, "Timeline User"));
	          
	          SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
	          
			 msg.setSubject("Your Timeline activity per "+format.format(new Date()));
			 String msgBody = "<html><body><img src='http://folk.ntnu.no/andekr/upload/header.png' alt='header'/><br />";
	          msgBody += "<h1>Your Timeline activity report:</h1><br /><br />";
	          msgBody += "<table border='1' rules='none' frame='box' cellpadding='10' width='800px'>";
	          for (Experience experience : exps.getExperiences()) {
        		 msgBody += experience.toString();
	          }
	          msgBody += "</table>";
	          msgBody +="</body></html>";
	         
	          Multipart mp = new MimeMultipart();

	          MimeBodyPart htmlPart = new MimeBodyPart();
	          htmlPart.setContent(msgBody, "text/html");
	          mp.addBodyPart(htmlPart);
	          
	          msg.setContent(mp);
	          
	          Transport.send(msg);

	      } catch (AddressException e) {
	          // ...
	      } catch (MessagingException e) {
	          // ...
	      } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}

