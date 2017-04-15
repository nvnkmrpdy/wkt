/**
 * 
 */
package com.sinergise.io.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sinergise.geometry.Geometry;
import com.sinergise.io.WKTReader;
import com.sinergise.io.WKTWriter;

public class WktTest {
	
	static WKTReader reader;
	static WKTWriter writer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reader = new WKTReader();
		writer = new WKTWriter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		String wkt1 = "POINT EMPTY";
		String wkt2 = "POINT (26 36)";
		String wkt3 = "LINESTRING EMPTY";
		String wkt4 = "LINESTRING (54 23, 12 28, 30 30, 54 23)";
		String wkt5 = "MULTIPOINT EMPTY";
		String wkt6 = "MULTIPOINT ((26 31), (25 35), (26 36))";
		String wkt7 = "MULTILINESTRING EMPTY";
		String wkt8 = "MULTILINESTRING ((54 23, 12 28, 30 30, 54 23), (55 26, 17 24, 36 33, 56 22))";
		String wkt9 = "POLYGON EMPTY";
		String wkt10 = "POLYGON ((30 10, 10 30, 40 40, 30 10))";
		String wkt11 = "POLYGON ((30 10, 10 30, 40 40, 30 10), (54 23, 12 28, 30 30, 54 23), (55 26, 17 24, 36 33, 55 26))";
		String wkt12 = "MULTIPOLYGON EMPTY";
		String wkt13 = "MULTIPOLYGON (((30 10, 10 30, 40 40, 30 10)), ((32 12, 12 32, 42 42, 32 12), (54 23, 12 28, 30 30, 54 23), (55 26, 17 24, 36 33, 55 26)), ((34 15, 14 35, 44 45, 34 15), (53 25, 32 48, 30 40, 53 25), (34 43, 22 58, 30 50, 34 43)))";
		String wkt14 = "GEOMETRYCOLLECTION EMPTY";
		String wkt15 = "GEOMETRYCOLLECTION (POINT (4 6), LINESTRING (4 6, 7 10), MULTIPOINT ((26 31), (25 35), (26 36)), MULTIPOLYGON (((30 10, 10 30, 40 40, 30 10)), ((32 12, 12 32, 42 42, 32 12), (54 23, 12 28, 30 30, 54 23), (55 26, 17 24, 36 33, 55 26)), ((34 15, 14 35, 44 45, 34 15), (53 25, 32 48, 30 40, 53 25), (34 43, 22 58, 30 50, 34 43))))";
		
		Geometry g1 = reader.read(wkt1);
		Geometry g2 = reader.read(wkt2);
		Geometry g3 = reader.read(wkt3);
		Geometry g4 = reader.read(wkt4);
		Geometry g5 = reader.read(wkt5);
		Geometry g6 = reader.read(wkt6);
		Geometry g7 = reader.read(wkt7);
		Geometry g8 = reader.read(wkt8);
		Geometry g9 = reader.read(wkt9);
		Geometry g10 = reader.read(wkt10);
		Geometry g11 = reader.read(wkt11);
		Geometry g12 = reader.read(wkt12);
		Geometry g13 = reader.read(wkt13);
		Geometry g14 = reader.read(wkt14);
		Geometry g15 = reader.read(wkt15);
		
		String s1 = writer.write(g1);
		String s2 = writer.write(g2);
		String s3 = writer.write(g3);
		String s4 = writer.write(g4);
		String s5 = writer.write(g5);
		String s6 = writer.write(g6);
		String s7 = writer.write(g7);
		String s8 = writer.write(g8);
		String s9 = writer.write(g9);
		String s10 = writer.write(g10);
		String s11 = writer.write(g11);
		String s12 = writer.write(g12);
		String s13 = writer.write(g13);
		String s14 = writer.write(g14);
		String s15 = writer.write(g15);
		
		assertTrue(wkt1.equals(s1));
		assertTrue(wkt2.equals(s2));
		assertTrue(wkt3.equals(s3));
		assertTrue(wkt4.equals(s4));
		assertTrue(wkt5.equals(s5));
		assertTrue(wkt6.equals(s6));
		assertTrue(wkt7.equals(s7));
		assertTrue(wkt8.equals(s8));
		assertTrue(wkt9.equals(s9));
		assertTrue(wkt10.equals(s10));
		assertTrue(wkt11.equals(s11));
		assertTrue(wkt12.equals(s12));
		assertTrue(wkt13.equals(s13));
		assertTrue(wkt14.equals(s14));
		assertTrue(wkt15.equals(s15));
		
	}

}
