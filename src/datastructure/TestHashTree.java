package datastructure;

import datastructure.hashtree.HashTree;

public class TestHashTree {
	public static void main(String[] args) {
		HashTree<String> ht = new HashTree<String>();
		for(int i=0;i<1000;i++){
			ht.add("v_"+i);
		}
		System.out.println(ht.get("v_999"));
		System.out.println(ht.get("v_9999"));
		System.out.println(ht.remove("v_999"));
		System.out.println(ht.get("v_999"));
		System.out.println(ht.add("v_999"));
		System.out.println(ht.get("v_999"));
	}
}
