package datastructure;

import java.util.HashSet;

import datastructure.hashtree.HashTree;

public class TestHashTree {
	public static void main(String[] args) {
		HashTree<String> ht = new HashTree<String>();
		HashSet<String> hs = new HashSet<String>();
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ht.add("ht_" + i);
			// ht.contain("ht_"+i);
		}
		long t2 = System.currentTimeMillis();

		for (int i = 0; i < 1000000; i++) {
			hs.add("ht_" + i);
		}
		long t3 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		System.out.println(t3 - t2);

		// System.out.println(ht.get("v_999"));
		// System.out.println(ht.get("v_9999"));
		// System.out.println(ht.remove("v_999"));
		// System.out.println(ht.get("v_999"));
		// System.out.println(ht.add("v_999"));
		// System.out.println(ht.get("v_999"));
	}
}
