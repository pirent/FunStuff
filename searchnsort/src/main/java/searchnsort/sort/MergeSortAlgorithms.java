package searchnsort.sort;


public class MergeSortAlgorithms extends AbstractSortAlgorithm {

	public MergeSortAlgorithms(int[] data) {
		super(data);
	}

	public void sort() {
		mergeSort(0, data.length - 1);
	}

	private void mergeSort(int low, int high) {
		if (high - low < 1) {
			return;
		}
		
		final int mid = (low + high) / 2;
		mergeSort(low, mid);
		mergeSort(mid + 1, high);
		
		merge(low, mid, mid+1, high);
	}

	private void merge(int low1, int high1, int low2, int high2) {
		int[] sortedArray = new int[data.length];
		int current = low1;
		
		for (int i = low1, j = low2; i <= high1; i++) {
			while (j <= high2) {
				if (data[i] < data[j]) {
					sortedArray[current++] = data[i];
					break;
				} else {
					sortedArray[current++] = data[j++];
				}
			}
		}
		
		// Copy values back into original array
		for (int i = low1; i < high2; i++) {
			data[i] = sortedArray[i];
		}
	}

}
