package datastructure.trietree;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * 又称单词查找树，Trie树，是一种树形结构，是一种哈希树的变种。典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），
 * 所以经常被搜索引擎系统用于文本词频统计。它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。
 * 将给定的字符串建立为空根数，然后在节点中做相应记录，如到当前前缀串为止的子串。
 * 
 * Tire用途
 * Tire树核心思想是空间换取时间，利用字符串的公共前缀来节省查询时间，常用于统计与排序大量字符串。其查询的时间复杂度是O（L），只与待查询串的长度相关。
 * 所以其有广泛的应用，下边简单介绍下Tire树的用途
 * 
 * Tire用于统计： 题目：给你100000个长度不超过10的单词。对于每一个单词，我们要判断他出没出现过，如果出现了，求第一次出现在第几个位置。
 * 
 * 解法 ：从第一个单词开始构造Tire树，Tire树包含两字段，字符与位置，对于非结尾字符，其位置标0，结尾字符，标注在100000个单词词表中的位置。
 * 对词表建造Tire树，对每个词检索到词尾，若词尾的数字字段>0,表示单词已经在之前出现过，否则建立结尾字符标志，下次出现即可得知其首次出现位置，
 * 便利词表即可依次计算出每个单词首次出现位置复杂度为O（N×L）L为最长单词长度，N为词表大小
 * 
 * Tire用于排序 题目：对10000个英文名按字典顺序排序 解法：建造Tire树，先序便利即可得到结果。
 */
public class TrieTree {

	private Node root;

	public TrieTree() {
		root = new Node(false, '0');
	}

	public String add(String value) {
		if (value == null) {
			return null;
		}
		Node node = root;

		char[] chars = value.toCharArray();
		// 1、遍历所有字符
		for (int i = 0; i < chars.length; i++) {
			boolean isExist = false;
			char charr = chars[i];
			// 当前节点下所有子节点
			ArrayList<Node> chilren = node.getChildren();
			// 遍历所有子节点
			for (int j = 0; j < chilren.size(); j++) {
				// 当前是否字符存在
				if (charr == chilren.get(j).getValue()) {
					isExist = true;
					// 如果是最后一个字符，标记结束
					if (i == chars.length - 1) {
						chilren.get(j).setStr(true);
					}
					// 下一轮指向子节点
					node = chilren.get(j);
					break;
				}
			}
			// 当前字符不存在，加入当前节点的子节点
			if (!isExist) {
				if (i == chars.length - 1) {
					Node newNode = new Node(true, charr);
					chilren.add(newNode);
					node = newNode;
				} else {
					Node newNode = new Node(false, charr);
					chilren.add(newNode);
					node = newNode;
				}
			}
		}
		return value;
	}

	public boolean contain(String value) {
		if (value == null) {
			return false;
		}
		Node node = root;

		char[] chars = value.toCharArray();
		// 1、遍历所有字符
		for (int i = 0; i < chars.length; i++) {
			boolean isExist = false;
			char charr = chars[i];
			// 当前节点下所有子节点
			ArrayList<Node> chilren = node.getChildren();
			// 遍历所有子节点
			for (int j = 0; j < chilren.size(); j++) {
				// 当前字符存在
				if (charr == chilren.get(j).getValue()) {
					isExist = true;
					// 如果是最后一个字符，且有结束标志，则存在
					if (i == chars.length - 1 && chilren.get(j).isStr) {
						return true;
					} else {
						node = chilren.get(j);
					}
					break;
				}
			}
			// 任何中间字符不存在，则没有当前字符串
			if (!isExist) {
				return false;
			}
		}
		return false;
	}

	class Node {
		// 标记该结点处是否构成单词
		boolean isStr;
		char value;
		ArrayList<Node> children = new ArrayList<Node>();

		public Node(boolean isStr, char value) {
			super();
			this.isStr = isStr;
			this.value = value;
		}

		public boolean isStr() {
			return isStr;
		}

		public void setStr(boolean isStr) {
			this.isStr = isStr;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}

		public ArrayList<Node> getChildren() {
			return children;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Node [isStr=").append(isStr).append(", value=").append(value).append(", children=")
					.append(children).append("]");
			return builder.toString();
		}

	}

}
