package searchnsort.sort;

public abstract class AbstractSortAlgorithm implements SortAlgorithm {
	protected int[] data;
	
	public AbstractSortAlgorithm(int[] data) {
		this.data = data;
	}
}
