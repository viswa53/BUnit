package com.bunit.resource;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	public static void main(String[] args) {
		
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(10);
		integers.set(0, 20);
		
		System.out.println(integers);
	}
}
