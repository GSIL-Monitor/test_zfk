package datastructure;

import java.util.HashSet;
import java.util.UUID;

import datastructure.trietree.TrieTree;

public class TrieTreeTest {
	public static void main(String[] args) {
		TrieTree tt = new TrieTree();
		HashSet<String> hs = new HashSet<String>();
		long t1 = System.currentTimeMillis();
		// 初始化
		for (int i = 0; i < 100000; i++) {
			tt.add(UUID.randomUUID().toString().substring(0, 10));
		}
		for (int i = 1000; i < 10000; i++) {
			System.out.println(tt.contain(UUID.randomUUID().toString().substring(0, 10)));
		}
		long t2 = System.currentTimeMillis();

		for (int i = 0; i < 1000000; i++) {
			// hs.add(UUID.randomUUID().toString().substring(0, 10));
		}
		long t3 = System.currentTimeMillis();

		System.out.println(t2 - t1);
		System.out.println(t3 - t2);
	}
}
