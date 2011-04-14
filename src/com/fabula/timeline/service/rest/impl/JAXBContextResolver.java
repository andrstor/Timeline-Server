package com.fabula.timeline.service.rest.impl;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.fabula.timeline.service.model.Emotion;
import com.fabula.timeline.service.model.Event;
import com.fabula.timeline.service.model.EventItem;
import com.fabula.timeline.service.model.Experience;
import com.fabula.timeline.service.model.Experiences;
import com.fabula.timeline.service.model.Group;
import com.fabula.timeline.service.model.Groups;
import com.fabula.timeline.service.model.User;
import com.fabula.timeline.service.model.Users;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private JAXBContext context;
    @SuppressWarnings("unchecked")
	private Class[] types = {Experiences.class, Experience.class, Event.class, EventItem.class, Emotion.class, Users.class, User.class, Groups.class, Group.class};

    public JAXBContextResolver() throws Exception {
    	
    	JSONConfiguration.MappedBuilder builder = JSONConfiguration.mapped();
    	builder.arrays("experiences");
    	builder.arrays("events");
    	builder.arrays("eventItems");
    	builder.arrays("emotionList");
//    	builder.arrays("tags");
    	builder.arrays("users");
    	builder.arrays("members");
    	builder.arrays("groups");
    	

    	JSONConfiguration configuration = builder.build();
    	this.context = new JSONJAXBContext(configuration,types);
    }
 
    @SuppressWarnings("unchecked")
	@Override
     public JAXBContext getContext(Class<?> objectType) {
         for (Class type : types) {
             if (type == objectType) {
                 return context;
             }
         }
         return null;
    }
 }