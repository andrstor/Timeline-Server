package com.fabula.timeline.service.rest.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fabula.timeline.service.model.Event;
import com.fabula.timeline.service.model.Experience;
import com.fabula.timeline.service.model.Experiences;
import com.google.appengine.api.datastore.KeyFactory;


@Path("/")
public class TimelineResource {
//	private static final NucleusLogger log =
//	      Log4JLogger.getLoggerInstance(null);
 
// @GET
// @Produces("application/xml")
// @Path("/employee/{employeeEmail}/") 
// public Employee getEmployee( @PathParam ("employeeEmail") String email) {
// //dummy code
// Employee emp = new Employee();
// emp.setEmail(email);
// emp.setFirstName("John");
// emp.setLastName("Doe");
// return emp;
// } 
// 
// @PUT
// @Consumes("application/xml")
// @Produces("application/xml")
// @Path("/employee/") 
// public Employee putEmployee(Employee emp) {
// //dummy code
// return emp;
// } 
	
 /*
  *  ONE EVENT
  */
	 
 
@PUT
 @Consumes({"application/json", "application/xml"})
 @Produces({"application/json", "application/xml"})
 @Path("/event/") 
 public Event putEvent(Event event) {
	 PersistenceManager pm = PMF.get().getPersistenceManager(); 
     try {
    	 //koble til experience (forutsetter at denne er syncet før)
    	 String k = KeyFactory.createKeyString(Experience.class.getSimpleName(), event.getExperienceid());
         Experience exp = pm.getObjectById(Experience.class, k);
         
         Event ev =  exp.getEventByID(event.getId());
    	 if(ev==null){
    		 exp.getEvents().add(event);
    	 }else{
             ev.setEmotionList(event.getEmotionList());
             ev.setEventItems(event.getEventItems());
             event = ev;
    	 }
    	 
    	
    	 pm.makePersistent(exp);
    	 pm.makePersistent(event);
     } finally {
         pm.close();
     }

	 System.out.println("Event: "+event.toString());
	 return event;
 } 
 
/*
 *  ALL EVENTS
 */
	 

@GET
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
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
 
 /*
  *  ONE EXPERIENCE
  */
 
 @PUT
 @Consumes({"application/json", "application/xml"})
 @Produces({"application/json", "application/xml"})
 @Path("/experience/") 
 public Experience putExperience(Experience experience) {
     PersistenceManager pm = PMF.get().getPersistenceManager();
     try {
         pm.makePersistent(experience);
     } finally {
         pm.close();
     }

	 System.out.println("Experience: "+experience.toString());
 return experience;
 } 
 
 /*
  * EXPERIENCES
  */
 
 @SuppressWarnings("unchecked")
 @GET
 @Produces({"application/json", "application/xml"})
 @Path("/experiences/") 
 public String GetExperiences() {
     PersistenceManager pm = PMF.get().getPersistenceManager();
     Query q = pm.newQuery("select from " + Experience.class.getName());
     List<Experience> exps = (List) q.execute();
     pm.close();
	
//     log.debug("DB inneholder "+experiences.size()+" experiencer, med "+experiences.get(0).getEvents().size()+" events");
     
     return ("DB inneholder "+exps.size()+" experiencer");
 } 
 
 @PUT
 @Consumes({"application/json", "application/xml"})
 @Produces({"application/json", "application/xml"})
 @Path("/experiences/") 
 public Experiences putExperiences(Experiences experiences) {
     PersistenceManager pm = PMF.get().getPersistenceManager();
     try {
    	 for (Experience experience : experiences.getExperiences()) {
        	 try {
                 pm.makePersistent(experience);
              }catch (Exception e) {
				// TODO: handle exception
			}
    	}
	} finally {
		pm.close();
	}
    
    System.out.println("Experiences: "+experiences.toString());
    
 return experiences;
 } 
 
}

