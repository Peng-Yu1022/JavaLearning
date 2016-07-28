package demos;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

import processing.core.PApplet;

public class LifeExpectancyExercise extends PApplet{
	
	UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		
		// to make the map interactive;
		MapUtils.createDefaultEventDispatcher(this, map);
		
		lifeExpByCountry = loadLifExpectancyFromCSV("data/LifeExpectancyWorldBank.csv");
		
		countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	public void draw(){
		map.draw();
	}
	
	private void shadeCountries(){
		for (Marker maker : countryMakers){
			String countryId = marker.getId();
			
			if (lifeExpMap.containsKey(countryId)){
				float lifeExp = lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}
	
	private Map<String, Float> loadLifExpectancyFromCSV(String fileName){
		
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		
		String[] rows = loadStrings(fileName);
		for (String row : rows){
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")){
				lifeExpMap.put(columns[4],columns[5]);
			}
		}	
		
		return lifeExpMap;
		
	}

}
