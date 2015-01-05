package searchnsort.sort;

public enum SortAlgorithmType {
	SELECTION_SORT(1),
	INSERTION_SORT(2),
	MERGE_SORT(3);

	private int value;

	private SortAlgorithmType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static SortAlgorithmType convert(int value) {
		switch (value) {
		case 1:
			return SELECTION_SORT;
		case 2:
			return INSERTION_SORT;
		case 3:
			return MERGE_SORT;
		default:
			throw new IllegalArgumentException("Invalid sort algorithm value: " + value);
		}
	}
}
