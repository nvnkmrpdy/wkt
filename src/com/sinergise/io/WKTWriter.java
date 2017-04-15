package com.sinergise.io;

import com.sinergise.geometry.Geometry;
import com.sinergise.geometry.GeometryCollection;
import com.sinergise.geometry.LineString;
import com.sinergise.geometry.MultiLineString;
import com.sinergise.geometry.MultiPoint;
import com.sinergise.geometry.MultiPolygon;
import com.sinergise.geometry.Point;
import com.sinergise.geometry.Polygon;

/**
 * Transforms the input Geometry object into WKT-formatted string
 */
public class WKTWriter {
	
	final String EMPTY = "EMPTY";
	final String OPEN_PARANTHESIS = "(";
	final String CLOSE_PARANTHESIS = ")";
	final String COMMA_SPACE = ", ";
	final String SPACE = " ";
	
	/**
	 * Transforms the input Geometry object into WKT-formatted string
	 */
	@SuppressWarnings("unchecked")
	public String write(Geometry geom) {
		
		if (geom == null) {
			return null;
		}
		
		if (geom instanceof Point) {
			return "POINT " + writePoint((Point) geom);
		}
		
		if (geom instanceof LineString) {
			return "LINESTRING " + writeLineString((LineString) geom);
		}
		
		if (geom instanceof Polygon) {
			return "POLYGON " + writePolygon((Polygon) geom);
		}
		
		if (geom instanceof MultiPoint) {
			return "MULTIPOINT " + writeMultiPoint((MultiPoint) geom);
		}
		
		if (geom instanceof MultiLineString) {
			return "MULTILINESTRING " + writeMultiLineString((MultiLineString) geom);
		}
		
		if (geom instanceof MultiPolygon) {
			return "MULTIPOLYGON " + writeMultiPolygon((MultiPolygon) geom);
		}
		
		if (geom instanceof GeometryCollection) {
			return "GEOMETRYCOLLECTION " + writeGeometryCollection((GeometryCollection<Geometry>) geom);
		}
		
		System.err.println("Invalid Geometry type");
		return null;
	}
	
	/**
	 * Transforms the input Point object into WKT-formatted string
	 * @param point
	 * @return String
	 */
	private String writePoint(Point point) {
		if (point == null) {
			return null;
		}
		
		if (point.isEmpty()) {
			return EMPTY;
		}
		
		return wrapInParanthesis((int)point.getX() + SPACE + (int)point.getY()); 
	}
	
	/**
	 * Transforms the input MultiPoint object into WKT-formatted string
	 * @param multiPoint
	 * @return String
	 */
	private String writeMultiPoint(MultiPoint multiPoint) {
		if (multiPoint == null) {
			return null;
		}
		int count = multiPoint.size();
		
		if (count == 0) {
			return EMPTY;
		}
		
		// For each Point
		String points[] = new String[count];
		int i = 0;
		for(Point point: multiPoint) {
			points[i++] = writePoint(point);
		}
		
		return wrapInParanthesis(String.join(COMMA_SPACE, points));
	}
	
	/**
	 * Transforms the input LineString object into WKT-formatted string
	 * @param lineString
	 * @return String
	 */
	private String writeLineString(LineString lineString) {
		
		if (lineString.isEmpty()) {
			return EMPTY;
		}
		
		// Gather the coords
		int len = lineString.getNumCoords();
		String coordsStr[] = new String[len];
		for (int i = 0; i < len; i++) {
			coordsStr[i] = (int)lineString.getX(i) + SPACE + (int)lineString.getY(i);
		}
		
		return wrapInParanthesis(String.join(COMMA_SPACE, coordsStr)); 
	}
	
	/**
	 * Transforms the input MultiLineString object into WKT-formatted string
	 * @param multiLineString
	 * @return String
	 */
	private String writeMultiLineString(MultiLineString multiLineString) {
		if (multiLineString == null) {
			return null;
		}
		
		int count = multiLineString.size();
		if (count == 0) {
			return EMPTY;
		}
		
		// For each LineString
		String lineStrings[] = new String[count];
		int i = 0;
		for(LineString lineString: multiLineString) {
			lineStrings[i++] = writeLineString(lineString);
		}
		
		return wrapInParanthesis(String.join(COMMA_SPACE, lineStrings));
	}
	
	/**
	 * Transforms the input Polygon object into WKT-formatted string
	 * @param polygon
	 * @return String
	 */
	private String writePolygon(Polygon polygon) {
		if (polygon == null) {
			return null;
		}
		
		if (polygon.isEmpty()) {
			return EMPTY;
		}
		
		StringBuilder polygonStr = new StringBuilder();
		polygonStr.append(OPEN_PARANTHESIS);
		
		// Outer
		polygonStr.append(writeLineString(polygon.getOuter()));
		
		// Holes
		int holesCount = polygon.getNumHoles();
		if (holesCount > 0) {
			String holesRepresentation[] = new String[holesCount];
			// For each hole
			for (int i=0; i < holesCount; i++) {
				holesRepresentation[i] = writeLineString(polygon.getHole(i));
			}
			
			polygonStr.append(COMMA_SPACE).append(String.join(COMMA_SPACE, holesRepresentation));
		}
		polygonStr.append(CLOSE_PARANTHESIS);
		
		return polygonStr.toString();
	}

	/**
	 * Transforms the input MultiPolygon object into WKT-formatted string
	 * @param multiPolygon
	 * @return String
	 */
	private String writeMultiPolygon(MultiPolygon multiPolygon) {
		if (multiPolygon == null) {
			return null;
		}
		
		int count = multiPolygon.size();
		if (count == 0) {
			return EMPTY;
		}
		
		// For each Polygon
		String polygons[] = new String[count];
		int i = 0;
		for(Polygon polygon: multiPolygon) {
			polygons[i++] = writePolygon(polygon);
		}
		
		return wrapInParanthesis(String.join(COMMA_SPACE, polygons));
	}
	
	/**
	 * Transforms the input GeometryCollection object into WKT-formatted string
	 * @param geometryCollection
	 * @return String
	 */
	private String writeGeometryCollection(GeometryCollection<Geometry> geometryCollection) {
		if (geometryCollection == null) {
			return null;
		}
		
		int count = geometryCollection.size();
		if (count == 0) {
			return EMPTY;
		}
		
		// For each Geometry
		String geometries[] = new String[count];
		int i = 0;
		for (Geometry geometry: geometryCollection) {
			geometries[i++] = write(geometry);
		}
		
		return wrapInParanthesis(String.join(COMMA_SPACE, geometries));
	}
	
	/**
	 * Wraps the input string in paranthesis
	 * @param str
	 * @return String
	 */
	private String wrapInParanthesis(String str) {
		return OPEN_PARANTHESIS + str + CLOSE_PARANTHESIS;
	}
	
}