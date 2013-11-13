import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 

/** 
 * We do not test file not found and incorrect file arg lengths because these are trivial test cases
 * That would require sophisticated set up and tear down to test
 * They are caught using exceptions in the file
 * 
 * The rest of the test cases try valid and invalid paths, as well and invalid input in the file
**/
public class ArrayHopperTest {

	public static void main(String[] args) {
		new ArrayHopperTest();
		
	}
	
	public ArrayHopperTest() {
		setUpStreams();
		testInvalidNumbers();
		cleanUpStreams();
		
		setUpStreams();
		testValidPath();
		cleanUpStreams();
		
		
		setUpStreams();
		testInvalidPath();
		cleanUpStreams();
		
		
		setUpStreams();
		testInvalidPathTwo();
		cleanUpStreams();
		
		
		setUpStreams();
		testInvalidPathTwo();
		cleanUpStreams();
		
		
		setUpStreams();
		testEmptyFile();
		cleanUpStreams();
		
	}
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testInvalidNumbers() {
		String data = "1\n2\nabd\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner scanner = new Scanner(System.in);


		new ArrayHopper(scanner);
		assertEquals("Number not in correct format", errContent.toString());
	}
	
	@Test
	public void testValidPath() {
		String data = "5\n6\n0\n4\n2\n4\n1\n0\n0\n4";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner scanner = new Scanner(System.in);


		new ArrayHopper(scanner);
		assertEquals("0, 5, 9, out", outContent.toString());
	}
	
	
	@Test
	public void testInvalidPath() {
		String data = "5\n6\n0\n4\n2\n4\n1\n0\n0\n0";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner scanner = new Scanner(System.in);


		new ArrayHopper(scanner);
		assertEquals("failure", outContent.toString());
	}
	
	@Test
	public void testInvalidPathTwo() {
		String data = "0\n100";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner scanner = new Scanner(System.in);


		new ArrayHopper(scanner);
		assertEquals("failure", outContent.toString());
	}
	
	@Test
	public void testEmptyFile() {
		String data = "";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		Scanner scanner = new Scanner(System.in);


		new ArrayHopper(scanner);
		assertEquals("failure", outContent.toString());
	}


}
