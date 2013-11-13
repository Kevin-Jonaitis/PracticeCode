
public class Stack {
	int[] array;
	int top;
	
	public Stack(int size) {
		array = new int[size];
		top = -1;
	}
	
	
	public int pop() throws Exception{
		if (top == -1) {
			throw new Exception("Stack is empty");
		} else { 
			top--;
			return array[top+1];
		}
		
	}
	
	public void push(int value) throws Exception {
		if (top == array.length - 1) {
			throw new Exception("Stack is full");
		} else {
			top++;
			array[top] = value;
		}
	}
	

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
