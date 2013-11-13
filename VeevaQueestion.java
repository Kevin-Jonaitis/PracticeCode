import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class VeevaQueestion {
	
	enum Gender {MALE, FEMALE};

	
	class Person { 
		Person mother;
		Person father;
		List<Person> children;
		int age;
		Gender gender;
	
		/**
		 * Method that, given a Person p, find the oldest child. We allow the father and mother to remarry, so they COULD have different children, hence combining mother and father.
		 * We are also adding their children to a SET of children(set because we don't want to add the same children twice).
		 * @param p
		 * @return
		 */
		public List<Person> findOldestSister(Person p) {
			List<Person> winners = new ArrayList<Person>();
			int maxAge = Integer.MIN_VALUE;
			Person mother = p.mother;
			Person father = p.father;
			
			
			Set<Person> allChildren = new HashSet<Person>();
			allChildren.addAll(mother.children);
			allChildren.addAll(father.children);
			
			for (Person test : allChildren) {  
				if (!test.equals(p) && test.gender == Gender.FEMALE) { //we don't want to look at ourselves!
					if (test.age > maxAge) { //we have a new maxAge, so reset the array, and add the new person
						winners = new ArrayList<Person>();
						winners.add(test);
					} else if (test.age == maxAge) { //the max age is equal to this person, so add them to the array
						winners.add(test);
					}
				}	
			}
			return winners;
		}
		
		
		
		/**
		 * The method we are given
		 */
		
		public List<Person> findOldestAncestor(Person p) {
			List<Person> oldest = findOldestAncestorHelper(p);
			if (oldest == null) //We only have one node, so no ancestors
				oldest = new ArrayList<Person>();
				oldest.add(p);
			return oldest;
			
		}
		/**
		 * This is a recursive algorithm that finds the max age of the father and mother of a person, and
		 * returns that maxAge. It runs in O(n) time and O(n) space.
		 * The clever part of this is we can look at any returned person in List<Person> from a function return find the maxAge for that list(because they should all be the same)
		 * So basically we're returning maxAge and List<Person> in one return statement
		 * 
		 * @param p
		 * @return
		 */
		public List<Person> findOldestAncestorHelper(Person p) {
			List<Person> father = null;
			List<Person> mother = null;
			List<Person> returnList = null;
			
			//This is the recursion parth
			if (p.father != null)
				father = findOldestAncestor(p);
			if (p.mother != null)
				mother = findOldestAncestor(p);
				
			
			//base case
			if (father != null && mother == null)
				return father;
			else if (father == null && mother != null)
				return mother;
			else if (father == null && mother == null)
				return returnList;
			
			
			//Logic on father versus mother if both non-null
			if (father.get(0).age > mother.get(0).age)
				returnList = father;
			else if (mother.get(0).age > father.get(0).age)
				returnList = mother;
			else if (father.get(0).age == mother.get(0).age) {
				returnList.addAll(father);
				returnList.addAll(mother);
			}
			
			return returnList;
	
		}
		
		
		
		
		
		
		/**
		 * Given a person, we want to find their oldest ancestor. This could be multiple people, so we are returning a list of persons.
		 * This is the solution if "ancestor" means highest level in the tree. I think it's quite elegent, actually, 
		 * and was what I thought was the original problem.
		 * This uses an interesting technique of "level-by-level" breadth first search. That is, we search whole levels at at time, and return that level
		 * It's kind of a blend of iterative deepening and breadth-first-search.
		 * It checks to see that both loops are executing, and when one stops executing, that means we have a list that is empty and one thats not
		 * so return the non-empty one, which is the top-level of the tree.
		 * Runs in O(n) time and O(n) space
		 * @param p
		 * @return
		 */
		public List<Person> findOldestAncestorLevel(Person p) {
			Queue<Person> currentLevel = new LinkedList<Person>();
			Queue<Person> nextLevel = new LinkedList<Person>();
			currentLevel.add(p);
	
			while (!currentLevel.isEmpty()) {
				
				boolean whileOne = false;
				boolean whileTwo = false;
				while (whileOne && whileTwo) {
					whileOne = false;
					whileTwo = false;
					if (currentLevel.peek().father != null)
							nextLevel.add(currentLevel.remove().father);
					if (currentLevel.peek().mother != null)
						nextLevel.add(currentLevel.remove().mother);
					whileOne = true;
				}
				
				while (!nextLevel.isEmpty()) {
					if (nextLevel.peek().father != null)
						currentLevel.add(nextLevel.remove().father);
					if (nextLevel.peek().mother != null)
						currentLevel.add(nextLevel.remove().mother);
					whileTwo = true;
				}
			}
			if(nextLevel.isEmpty())
				return (List<Person>) currentLevel;
			else
				return (List<Person>) nextLevel;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
