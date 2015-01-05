package searchnsort.search;

public enum SearchAlgorithmType {
	LINEAR_SEARCH(1), BINARY_SEARCH(2);

	private final int value;

	private SearchAlgorithmType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static SearchAlgorithmType convert(int value) {
		switch (value) {
		case 1:
			return LINEAR_SEARCH;
		case 2:
			return BINARY_SEARCH;
		default:
			throw new IllegalArgumentException(
					"Not a valid search algorithm choice: " + value);
		}
	}
}
