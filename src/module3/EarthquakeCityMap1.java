package module3;

//Java utilities libraries

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//import java.util.Collections;
//import java.util.Comparator;
//Processing library
//Unfolding libraries
//Parsing library



/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap1 extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)

		/*Location valLoc0 = new Location(-38.14F, -73.03F);
		Location valLoc1 = new Location(61.02F, -147.65F);
		Location valLoc2 = new Location(3.30F, 95.78F);
		Location valLoc3 = new Location(38.322F, 142.36F);
		Location valLoc4 = new Location(52.76F, 160.06F);
		PointFeature valEq0 = new PointFeature(valLoc0);
		valEq0.addProperty("title", "0");
		valEq0.addProperty("magnitude", "0");
		valEq0.addProperty("date", "0");
		valEq0.addProperty("year", "0");
		PointFeature valEq1 = new PointFeature(valLoc1);
		valEq1.addProperty("title", "1");
		valEq1.addProperty("magnitude", "1");
		valEq1.addProperty("date", "1");
		valEq1.addProperty("year", "1");
		PointFeature valEq2 = new PointFeature(valLoc2);
		valEq2.addProperty("title", "2");
		valEq2.addProperty("magnitude", "2");
		valEq2.addProperty("date", "2");
		valEq2.addProperty("year", "2");
		PointFeature valEq3 = new PointFeature(valLoc3);
		valEq3.addProperty("title", "3");
		valEq3.addProperty("magnitude", "3");
		valEq3.addProperty("date", "3");
		valEq3.addProperty("year", "3");
		PointFeature valEq4 = new PointFeature(valLoc4);
		valEq4.addProperty("title", "4");
		valEq4.addProperty("magnitude", "4");
		valEq4.addProperty("date", "4");
		valEq4.addProperty("year", "4");

		earthquakes.add(valEq0);
		earthquakes.add(valEq1);
		earthquakes.add(valEq2);
		earthquakes.add(valEq3);
		earthquakes.add(valEq4);*/

		for (PointFeature eq: earthquakes){
			markers.add(createMarker(eq));
		}



		// Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/

	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
		
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    if (mag<=4.0f) {
			marker.setRadius(5.0f);
			marker.setColor(Color.BLUE.getRGB());
		}else if(mag>4.0f & mag<=4.9f){
			marker.setRadius(10.0f);
			marker.setColor(Color.YELLOW.getRGB());
		}else if(mag<4.9f){
			marker.setRadius(15.0f);
			marker.setColor(color(200,200,0));
		}
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{
		rectMode(CORNER);  // Default rectMode is CORNER
		fill(100);  // Set fill to white
		quad(10, 50, 10, 400, 200, 400, 200, 50);
		ellipseMode(CENTER);  // Set ellipseMode to CENTER
		fill(Color.BLUE.getRGB());  // Set fill to gray60, 150
		ellipse(40,70,20,20);

		ellipseMode(CENTER);  // Set ellipseMode to CENTER60, 50,
		fill(Color.YELLOW.getRGB());  // Set fill to gray
		ellipse(40,120,20,20);

		ellipseMode(CENTER);  // Set ellipseMode to CENTER
		fill(Color.RED.getRGB());  // Set fill to gray
		ellipse(40,170,20,20);
		textSize(32);
		text("SDFSDFASDFASDFASDF ASDFASDFSDF", 70, 80);

	}
}
