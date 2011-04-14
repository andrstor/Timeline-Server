package com.fabula.timeline.service.rest.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fabula.timeline.service.model.User;
import com.google.appengine.api.datastore.KeyFactory;


/**
 * REST services for account management Fabula Timeline Android Application.
 * 
 * JUST FOR TESTS!
 * 
 * @author andekr
 * @author andrstor
 *
 */
//@Path("/")
public class AccountResource {
	
	@PUT
	@Path("/mail/")
	public void sendEmail() {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String token = "sndjgfnso1515313";
        
        String msgBody = "Thank you for installing the Timelineapplication!\n\n" +
        		" Please click this link to confirm your email address: http://reflectapp.com/account/confirm/"+
        		token;

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("anderskri@gmail.com", "Timelineapplication"));
            InternetAddress [] a = new InternetAddress[]{ new InternetAddress("no-reply@gmail.com", "Timelineapplication")};
            msg.setReplyTo(a);
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("anderskri@gmail.com", "Mr. User"));
            msg.setSubject("Welcome to Timelineapplication");
            msg.setText(msgBody);
            Transport.send(msg);
    
        } catch (AddressException e) {
        	e.printStackTrace();
        } catch (MessagingException e) {
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
	
	// USERS

	@PUT
	@Consumes( { "application/json", "application/xml" })
	@Produces( { "application/json", "application/xml" })
	@Path("/confirm/{username}/{token}")
	public User confirmUser(@PathParam("username") String username, @PathParam("token") String token) {
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
 
}

