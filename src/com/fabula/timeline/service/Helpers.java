package com.fabula.timeline.service;

import java.util.List;

import com.fabula.timeline.service.model.Event;

public class Helpers {

	public static int getAverageMood(List<Event> moodEvents){
		int sum = 0;
		
		for (Event moodEvent : moodEvents) {
			sum+=moodEvent.getMood();
		}
		float floatAverage = ((float)sum/(float)moodEvents.size());
	
		return Math.round(floatAverage);
	}
	
	
	
}
