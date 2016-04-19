package generics;

import java.util.ArrayList;
import java.util.List;

public class ListMaker<T> {

	public List<T> create () {
		return new ArrayList<T>();
	}
	
	public static void main(String[] args) {
		ListMaker<String> listMaker = new ListMaker<String>();
		System.out.println(listMaker.create());
	}
}
