package com.sinergise.io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.LineString;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiPolygon;
import com.sinergise.geometry.Point;
import com.sinergise.geometry.Polygon;

/**
 * Reads the string WKT representation of a Geometry and constructs a Geometry instance
 *
 */
public class WKTReader {
	
	/**
	 * Transforms the input WKT-formatted String into Geometry object
	 * @param wktString
	 * @return {@link Geometry}
	 */
	public Geometry read(String wktString) {
		if (wktString == null) {
			return null;
		}
		
		if (wktString.startsWith("POINT")) {
			return readPoint(wktString);
		}
		
		if (wktString.startsWith("LINESTRING")) {
			return readLineString(wktString);
		}
		
		if (wktString.startsWith("POLYGON")) {
			return readPolygon(wktString);
		}
		
		if (wktString.startsWith("MULTIPOINT")) {
			return readMultiPoint(wktString);
		}
		
		if (wktString.startsWith("MULTILINESTRING")) {
			return readMultiLineString(wktString);
		}
		
		if (wktString.startsWith("MULTIPOLYGON")) {
			return readMultiPolygon(wktString);
		}
		
		if (wktString.startsWith("GEOMETRYCOLLECTION")) {
			return readGeometryCollection(wktString);
		}
		
		System.err.println("Invalid Geometry type");
		return null;
	}
	
	/**
	 * Reads the string WKT representation of a Point and constructs a Point instance
	 * @param wktString
	 * @return {@link Point}
	 */
	private Point readPoint(String wktString) {
		Pattern wktPattern = Pattern.compile("([\\d]+)\\s([\\d]+)");
		Matcher matcher = wktPattern.matcher(wktString);
		Point point = null;
		// Has valid x,y
		if (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			double y = Double.parseDouble(matcher.group(2));
			point = new Point(x, y);
		} else {
			point = new Point();
		}
		return point;
	}
	
	/**
	 * Reads the string WKT representation of a Point and constructs a Point instance
	 * @param wktString
	 * @return {@link Point}
	 */
	private MultiPoint readMultiPoint(String wktString) {
		Pattern wktPattern = Pattern.compile("(\\([\\d]+)\\s([\\d]+\\))");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<Point> points = new ArrayList<Point>();
		// For each Point, get the Point geometry instance
		while (matcher.find()) {
			points.add(readPoint(matcher.group()));
		} 
		
		// Empty 
		if (points.isEmpty()) {
			return new MultiPoint();
		}
		// Return the MultiPoint geometry instance
		Point pointsArr[] = new Point[points.size()];
		points.toArray(pointsArr);
		return new MultiPoint(pointsArr);
	}
	
	/**
	 * Reads the string WKT representation of a LineString and constructs a LineString instance
	 * @param wktString
	 * @return {@link LineString}
	 */
	private LineString readLineString(String wktString) {
		Pattern wktPattern = Pattern.compile("([\\d]+)\\s([\\d]+)");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<Double> coordsList = new ArrayList<Double>();
		// For each coord
		while (matcher.find()) {
			coordsList.add(Double.parseDouble(matcher.group(1)));
			coordsList.add(Double.parseDouble(matcher.group(2)));
		}
		// Empty
		if (coordsList.isEmpty()) {
			return new LineString();
		}
		
		// Return the LineString geometry instance
		double[] coords = new double[coordsList.size()];
		int i = 0;
		for (Double coord: coordsList) {
			coords[i++] = coord;
		}
		return new LineString(coords);
	}
	
	/**
	 * Reads the string WKT representation of a MultiLineString and constructs a MultiLineString instance
	 * @param wktString
	 * @return {@link MultiLineString}
	 */
	private MultiLineString readMultiLineString(String wktString) {
		Pattern wktPattern = Pattern.compile("(\\([\\d\\s,]+\\))");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<LineString> lineStrings = new ArrayList<LineString>();
		// For each LineString, get the LineString geometry instance
		while (matcher.find()) {
			lineStrings.add(readLineString(matcher.group()));
		} 
		
		// Empty
		if (lineStrings.isEmpty()) {
			return new MultiLineString();
		}
		// Return the MultiLineString geometry instance
		LineString lineStringsArr[] = new LineString[lineStrings.size()];
		lineStrings.toArray(lineStringsArr);
		return new MultiLineString(lineStringsArr);
	}
	
	/**
	 * Reads the string WKT representation of a Polygon and constructs a Polygon instance
	 * @param wktString
	 * @return {@link Polygon}
	 */
	private Polygon readPolygon(String wktString) {
		Pattern wktPattern = Pattern.compile("(\\([\\d\\s,]+\\))");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<LineString> lineStrings = new ArrayList<LineString>();
		// For each LineString, get the LineString geometry instance
		while (matcher.find()) {
			lineStrings.add(readLineString(matcher.group()));
		}
		
		// Empty
		if (lineStrings.isEmpty()) {
			return new Polygon();
		}
		
		// Outer Polygon
		LineString outer = lineStrings.remove(0);
		// Holes
		LineString holes[] = new LineString[lineStrings.size()];
		lineStrings.toArray(holes);
		// Return the Polygon geometry instance
		return new Polygon(outer, holes);
	}
	
	/**
	 * Reads the string WKT representation of a MultiPolygon and constructs a MultiPolygon instance
	 * @param wktString
	 * @return {@link MultiPolygon}
	 */
	private MultiPolygon readMultiPolygon(String wktString) {
		Pattern wktPattern = Pattern.compile("((\\([\\d\\s,]+\\))(?:,\\s(\\([\\d\\s,]+\\)))*)");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<Polygon> polygons = new ArrayList<Polygon>();
		// For each Polygon, get the Polygon geometry instance
		while (matcher.find()) {
			polygons.add(readPolygon(matcher.group()));
		} 
		
		// Empty
		if (polygons.isEmpty()) {
			return new MultiPolygon();
		}
		// Return the MultiPolygon geometry instance
		Polygon polygonsArr[] = new Polygon[polygons.size()];
		polygons.toArray(polygonsArr);
		return new MultiPolygon(polygonsArr);
	}
	
	/**
	 * Reads the string WKT representation of a GeometryCollection and constructs a GeometryCollection instance
	 * @param wktString
	 * @return {@link GeometryCollection}
	 */
	private GeometryCollection<Geometry> readGeometryCollection(String wktString) {
		Pattern wktPattern = Pattern.compile("[A-Z]+\\s\\([0-9\\s,)(]+\\)");
		Matcher matcher = wktPattern.matcher(wktString);
		
		List<Geometry> geometries = new ArrayList<Geometry>();
		// For each Geometry present, get the Geometry instance
		while (matcher.find()) {
			geometries.add(read(matcher.group()));
		} 
		
		return new GeometryCollection<Geometry>(geometries);
	}

}