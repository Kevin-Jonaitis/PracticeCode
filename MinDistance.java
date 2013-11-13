
public class MinDistance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[] arr1 = { 1,5,15,16,27,49,70 };
		int[] arr2 = { 8,9,11,24,32,33,34 };
		
		MinDistance(arr1,arr2);
	}

	private static void MinDistance(int[] arr1, int[] arr2) {
		int pointer_a = 0;
		int pointer_b = 0;
		int min = Integer.MAX_VALUE;
		while (pointer_a < arr1.length && pointer_b < arr2.length) {
			min = Math.min(min, Math.abs(arr1[pointer_a] - arr2[pointer_b]));
			if (arr1[pointer_a] < arr2[pointer_b])
				pointer_a++;
			else
				pointer_b++;
		}
		System.out.println(min);
	}

}
