package module3;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module3HomeWork extends PApplet{
    private UnfoldingMap map;
    private Map<String,Float> lifeExpByCountry;
    List<Feature> countries = new ArrayList<Feature>();
    List<Marker> countryMarkers = new ArrayList<Marker>();
    public void setup() {
        size(800,600,OPENGL);
        map = new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this,map);
        lifeExpByCountry = loadLifeExpectancyFromCSV("data/LifeExpectancyWorldBankModule3.csv");
        countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
        countryMarkers = MapUtils.createSimpleMarkers(countries);

        map.addMarkers(countryMarkers);
        sharedCountries();

    }


    public void draw() {
        background(10);
        map.draw();

    }

    private Map<String,Float> loadLifeExpectancyFromCSV(String fileName){
        Map<String,Float> lifeExpMap = new HashMap<String, Float>();
        String[] rows = loadStrings(fileName);
        for (String row: rows){
            String[] columns = row.split(",");
            try{
                float value = Float.parseFloat(columns[5]);
                lifeExpMap.put(columns[4],value);
            }catch (Exception e){                }
        }
        return lifeExpMap;
    }
    private void sharedCountries(){
        for (Marker marker : countryMarkers){
            String countryID = marker.getId();
            if (lifeExpByCountry.containsKey(countryID)){
                float lifeExp = lifeExpByCountry.get(countryID);
                int colorLevel = (int) map(lifeExp,40,90,10,255);
                marker.setColor(color(255-colorLevel,100,colorLevel));
            }else {
                marker.setColor(color(150,150,150));
            }
        }

    }


}
