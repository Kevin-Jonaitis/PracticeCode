import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class StackTest {

	
	//pop when empty
	//push when full
	//normal pop
	//normal push
	
	@Test
	public void testEmptyPop() {
		Stack s = new Stack(2);
		try {
			s.pop();
			fail();
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Stack is empty");
		}
	}
	
	@Test
	public void testFullPush() {
		Stack s = new Stack(2);
		try {
			s.push(2);
			s.push(3);

		} catch (Exception e1) {
			fail();
		}
		try {
			s.push(3);
			fail();
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Stack is full");
		}
	}
	
	@Test
	public void testNormal() {
		Stack s = new Stack(2);
		try {
			s.push(2);
			Assert.assertEquals(2, s.pop());
			s.push(1);
			s.push(3);
			s.pop();
			Assert.assertEquals(1, s.pop());

		} catch (Exception e) {
			fail();
		}
	}
}
