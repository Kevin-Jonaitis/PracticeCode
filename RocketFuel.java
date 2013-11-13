import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * 
 * SEE INPUT TESTS IN FOLDER
 * Question 1 / 1 (TreeFlatten)
Imagine you are implementing some boolean expression logic, and for the purpose of debugging in the future, you want to write a program to provide an intuitive text printout of each expression. More specifically, suppose you start with strings that represent boolean expressions containing binary operators AND and OR. Each of the two arguments to an operator may either be a string of lower-case letters or a recursive call to AND or OR. For example, AND(OR(foo,bar),baz) represents "foo or bar, and also baz".

 

Your program should read one line from standard input, interpret this line of text as a boolean expression, and print out a tree-like representation of this boolean expression (see examples for the format). Moreover, this tree-like representation should flatten contiguous recursive calls to AND and OR when doing so does not change the logic of the function (see examples). The top-down ordering of the leaves of the printed trees should be the same as their left-right order in the input string.

 

You should strive for an algorithm whose running time is O(n + m), where n is the length of the input string and m is the number of characters printed in the output. Assume the length of the input string could be up to millions of characters.

 

Keep in mind the following:

 

1. Correctly printing the output per the problem specification is most important. Your output should exactly match the examples below.

 

2. Once you have a correct solution, try to ensure that it will run on as large examples as possible for standard JVM settings. As mentioned above, note what the breaking points of your code are.

 

3. The code should be as clean and easy to understand as possible (even without comments). Imagine that this code will be part of a larger code base, and will have to be read and maintained by other engineers.

 

4. The above three points are most important, but given that your solution satisfies them, solving the problem faster is better, so submit your solution as soon as your are confident that it is correct, fast, and clean.

 

Input constraints: None. Your solution should work on as large inputs as possible without errors or timeouts. If your solution passes the sample test cases, it will likely be fast enough for full credit.

 

Notes: The input will be given in one line, which will be terminated by a newline ('\n'). The last line of the output should end with a newline, and there should be no trailing empty newline characters.

 

Examples:

 

input:

AND(a,b)

output:

AND
 |
 +-a
 |
 +-b

 

(arguments can be more than one character)
input:

OR(foo,bar)

output:

OR
 |
 +-foo
 |
 +-bar



(output should flatten boolean operators when doing so cannot change the value of the root expression)
input:

AND(a,AND(b,c))

output:

AND
 |
 +-a
 |
 +-b
 |
 +-c

 

input:

OR(OR(a,b),c)

output:

OR
 |
 +-a
 |
 +-b
 |
 +-c

 

input:

AND(OR(a,b),OR(c,d))

output:

AND
 |
 +-OR
 |  |
 |  +-a
 |  |
 |  +-b
 |
 +-OR
    |
    +-c
    |
    +-d
 * @author Kevin
 *
 */

public class RocketFuel {
	
	int leftElementCounter = -1;
	Tree tree = new Tree(null);
	int maxCol = Integer.MIN_VALUE;
	int maxRow = Integer.MIN_VALUE;
	char[][] output;

	public class Tree {
	    public Node root;

	    public Tree(String rootData) {
	        root = new Node(null,null);
	        root.data = rootData;
	        root.children = new ArrayList<Node>();
	    }

	    public class Node {
	        public String data;
	        public int depth;
	        public int row;
	        public int column;
	        public  Node parent;
	        public  ArrayList<Node> children;
	        
		    public Node addChild(String s) {
		    	Node n = new Node(this,s);
		    	this.children.add(n);
		    	return n;
		    }
	        
	        public Node(Node p,String s) {
	        	this.parent = p;
	        	this.data = s;
	        	children = new ArrayList<Node>();
	        }
	    }
	}
	
	public void readIn(Scanner in) {
		String input = in.nextLine();
		Tree.Node pointer = tree.root;
		int counter = 0;
		while (counter <= input.length() - 1) {
			if ((counter + 4 <= input.length() - 1) && input.subSequence(counter, counter + 4).equals("AND(")) {
				// Add AND to the tree, go down one level
				if(tree.root.data == null) {
					tree.root.data = "AND";
				} else {
						pointer = pointer.addChild("AND");
				}
				counter = counter + 4;
				
			} else if ((counter + 3 <= input.length() - 1) && input.subSequence(counter, counter + 3).equals("OR(")) {
				
				if(tree.root.data == null) {
					tree.root.data = "OR";
				} else {
						pointer = pointer.addChild("OR");
				}
				counter = counter + 3;
				// Add Or to the tree, go down one level
				
			} else if(input.charAt(counter) == ',') {
				counter++;
				continue; //simply advance the code
			} else if(input.charAt(counter) == ')') {
				counter++;
				if (pointer.parent != null && pointer.parent.data.equals(pointer.data)) { //if the pointer and parent have same AND or OR, push the children up to the parent
					for (Tree.Node n : pointer.children) {
						pointer.parent.children.add(n);
					}
					pointer.parent.children.remove(pointer);
				}
				pointer = pointer.parent;
			} else {
				String varName = "";
				while (input.charAt(counter) != ',' && input.charAt(counter) != ')') {
					varName = varName + input.charAt(counter);
					counter++;
				}
				pointer.addChild(varName);
			}
		}	
	}
	
	public RocketFuel(Scanner in) {
		readIn(in);
		printOut();
	}
	public void printOut() {
		
		dfs(tree.root, 0);
		output = new char[maxRow+ 1][maxCol+ 1];
		dfs_output(tree.root);
		readRows();
	}

	private void readRows() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i <= maxRow; i++) {
			for (int j = 0; j <= maxCol; j++){
					sb.append(output[i][j]);
			}
			System.out.println(sb.toString().replaceAll("\\s+$", ""));
			sb =  new StringBuffer();

		}
		
	}

	private void dfs_output(Tree.Node n) {
		int last_row = 0;
		if (n.children.size() > 0)
			last_row = n.children.get(n.children.size() - 1).row; //the last child		
		for (int i = n.row + 1; i < last_row; i++) {		
			output[i][n.column + 1] = '|';
		}
		for (int i = 0; i < n.data.length(); i++){
				output[n.row][n.column + i] = n.data.charAt(i);
			}
			if(n.column != 0){
				output[n.row][n.column- 1] = '-';
				output[n.row][n.column- 2] = '+';
				
			
		}
		
		for (Tree.Node test : n.children) {
			dfs_output(test);
		}
	}

	public void dfs(Tree.Node n, int depth) {
		leftElementCounter++;
		n.row = 2 * leftElementCounter;
		n.column = depth * 3;
		if (n.row > maxRow)
			maxRow = n.row;
		if((n.column + n.data.length()) > maxCol)
			maxCol = n.column + + n.data.length();
		for (Tree.Node test : n.children) {
			dfs(test, (depth + 1));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.print("Incorrect number of args");
		} else {

			String filename = args[0];
			int i = 0;
			try {
				Scanner in = new Scanner(new FileReader(filename));
				new RocketFuel(in);

			} catch (FileNotFoundException e) {
				System.err.print("File not found: " + filename);
			}
		}
	}
}
