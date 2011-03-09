package com.fabula.timeline.service.rest.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fabula.timeline.service.model.Event;
import com.fabula.timeline.service.model.Experience;
import com.fabula.timeline.service.model.Experiences;

@Path("/")
public class TimelineResource {
	private static final Logger log =
	      Logger.getLogger(TimelineResource.class.getName());
 
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
	 
 
 @SuppressWarnings("unchecked")
@PUT
 @Consumes("application/xml")
 @Produces("application/xml")
 @Path("/event/") 
 public Event putEvent(Event event) {
	 PersistenceManager pm = PMF.get().getPersistenceManager(); 
	 Event e = null;
     try {
    	 String query = "select from " + Event.class.getName()+ " where id=='"+event.getId()+"'";
    	 List<Event> results = (List<Event>) pm.newQuery(query).execute();
    	 if(results.size()==0){
    		 e = event;
    		 //koble til experience
    		 String q2 = "select from " + Experience.class.getName()+ " where id=='"+e.getExperienceid()+"'";
        	 List<Experience> r2 = (List<Experience>) pm.newQuery(q2).execute();
        	 if(r2.size()>0){
        		 Experience exp = r2.get(0);
        		 exp.getEvents().add(e);
        		 pm.makePersistent(exp);
        	 }
        		 
    	 }else{
    		 e= results.get(0);
             
             e.setEmotionList(event.getEmotionItems());
             e.setEventItems(event.getEventItems());
    	 }
    	 
         
         pm.makePersistent(e);
     } finally {
         pm.close();
     }

     log.info("Event: "+event.getEventItems().size());
	 System.out.println("Event: "+e.toString());
 return e;
 } 
 
 
 /*
  *  ONE EXPERIENCE
  */
 
 @PUT
 @Consumes("application/xml")
 @Produces("application/xml")
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
 @Produces("text/plain")
 @Path("/experiences/") 
 public String GetExperiences() {
     PersistenceManager pm = PMF.get().getPersistenceManager();
     String query = "select from " + Experience.class.getName();
     List<Experience> experiences = (List<Experience>) pm.newQuery(query).execute();

	
 return "DB inneholder "+experiences.size()+" experiencer, med "+experiences.get(0).getEvents().size()+" eventItems";
 } 
 
 @PUT
 @Consumes("application/xml")
 @Produces("application/xml")
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

