
public class MergeSort {
	static int copy[];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] test_array = {4,1,3,9,3,5,345,6,45,34,5,34,234,6,5476,435,34,323,23,2,32,3,52,4,4,4,5,5,6,4,34,3,2,2,4,5};
		copy = test_array.clone();

		mergeSort(test_array, 0, test_array.length - 1);
		for (int i : test_array){
			System.out.println(i + " " );
		}
	}
	
	public static void mergeSort(int[] array, int startIndex, int endIndex) {

		if (startIndex < endIndex) {
			int middle = startIndex + (endIndex - startIndex)/2;
			mergeSort(array,startIndex,middle);
			mergeSort(array,middle + 1,endIndex);
			merge(array, startIndex,middle,endIndex);
			for (int i = startIndex; i <=endIndex; i++ ) {
				copy[i] = array[i];
			}
		}
	}
	
	public static void merge(int[] array, int startIndex, int middle,int endIndex) {

		int firstArrayIndex = startIndex;
		int secondArrayIndex = middle + 1; //where the second array starts
		int newIndex = startIndex; //this index will keep track of our assignments
		while (firstArrayIndex <= middle && secondArrayIndex <= endIndex) {
			if (copy[firstArrayIndex] <= copy[secondArrayIndex]){
				array[newIndex] = copy[firstArrayIndex];
				firstArrayIndex++;
			} else {
				array[newIndex] = copy[secondArrayIndex];
				secondArrayIndex++;
			}
			newIndex++;
		}
		while (firstArrayIndex <= middle){ //copy the rest of the second index
			array[newIndex] = copy[firstArrayIndex];
			firstArrayIndex++;
			newIndex++;
		}
		while (secondArrayIndex <= endIndex){ //copy the rest of the second index
			array[newIndex] = copy[secondArrayIndex];
			secondArrayIndex++;
			newIndex++;
		}
		
	}
}
