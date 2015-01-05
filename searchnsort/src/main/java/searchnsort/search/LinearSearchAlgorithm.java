package searchnsort.search;

public class LinearSearchAlgorithm implements SearchAlgorithm{

	public int search(int[] data, int searchKey) {
		int position = -1;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i] == searchKey) {
				position = i;
				break;
			}
		}
		
		return position;
	}

}
