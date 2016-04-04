package generics;

import static generics.watercolors.Watercolors.*;
import generics.watercolors.Watercolors;

import java.util.EnumSet;
import java.util.Set;

import util.Sets;

public class WatercolorSet {
	public static void main(String[] args) {
		EnumSet<Watercolors> set1 = EnumSet.range(BRILLIANT_RED, VIRIDIAN_HUE);
		EnumSet<Watercolors> set2 = EnumSet.range(CERULEAN_BLUE_HUE, BURNT_UMBER);
		
		System.out.println("Set 1: " + set1);
		System.out.println("Set 2: " + set2);
		System.out.println("Union : " + Sets.union(set1, set2));
		
		Set<Watercolors> intersection = Sets.intersection(set1, set2);
		System.out.println("Intersection: " + intersection);
		
		System.out.println("Different between set1 and intersection: " + Sets.difference(set1, intersection));
		System.out.println("Different between set2 and intersection: " + Sets.difference(set2, intersection));
		
		System.out.println("Complement: " + Sets.complement(set1, set2));
	}
}
