package com.fabula.timeline.service;

import java.util.Collections;
import java.util.List;

import com.fabula.timeline.service.model.Event;
import com.fabula.timeline.service.model.Mood;

public class Helpers {

	public static Mood getAverageMood(List<Event> moodEvents){
		double[] sum = new double[2];
		double[] average = new double[2];
		
		Collections.sort(moodEvents, Collections.reverseOrder());
		
		List<Event> lastEvents;
		
		if(moodEvents.size()<10){
			lastEvents = moodEvents.subList(0, moodEvents.size());
		}else{
			lastEvents = moodEvents.subList(0, 10);
		}
		
		
		for (Event moodEvent : lastEvents) {
			sum[0] = sum[0]+ moodEvent.getValence();
			sum[1] = sum[1]+ moodEvent.getArousal();
		}
		
		average[0] = ((float)sum[0]/(float)lastEvents.size());
		average[1] = ((float)sum[1]/(float)lastEvents.size());
	
		Mood moodAverage = new Mood(average[0], average[1]);
		return moodAverage;
	}
	
	
	
}
