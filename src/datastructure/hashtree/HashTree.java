package datastructure.hashtree;

/**
 * 
 * 哈希表
 * 
 * 理想的情况是希望不经过任何比较，一次存取便能得到所查的记录，那就必须在记的存储位置和它的关键字之间建立一个确定的对应关系f，
 * 使每个关键字和一个唯一的存储位置相对应。因而在查找时，只要根据这个对应关系f找到给定值K的像f(K)。由此，不需要进行比较便可直接取得所查记录。在此，
 * 我们称这个对应关系为哈希（Hash）函数，按这个思想建立的表为哈希表。
 * 
 * 在哈希表中对于不同的关键字可能得到同一哈希地址，这种现象称做冲突。在一般情况下，冲突只能尽可能地减少，而不能完全避免。
 * 因为哈希函数是从关键字集合到地址集合的映像。通常关键字的集合比较大，它的元素包括所有可能的关键字，而地址集合的元素仅为哈希表中的地址值。在一般情况下，
 * 哈希函数是一个压缩映像函数，这就不可避免的要产生冲突。
 * 
 * 哈希树(HashTree)算法就是要提供一种在理论上和实际应用中均能有效地处理冲突的方法。一般的哈希(Hash)算法都是O(1)的，而且基本是以空间换时间
 * 。这很容易导致对存储空间无限制的需求。本文中哈希树(HashTree)算法在实际操作中使用了一些技巧使得对空间的需求控制在一定范围内。
 * 即空间需求仅和所需要存储的对象个数有关，不会无限制地“膨胀”下去。
 * 
 * 
 * 哈希树的理论基础
 * 
 * 【质数分辨定理】 简单地说就是：n个不同的质数可以“分辨”的连续整数的个数和他们的乘积相等。“分辨”就是指这些连续的整数不可能有完全相同的余数序列。
 * 
 * 我们选择质数分辨算法来建立一棵哈希树。
 * 选择从2开始的连续质数来建立一个十层的哈希树。第一层结点为根结点，根结点下有2个结点；第二层的每个结点下有3个结点；依此类推，
 * 即每层结点的子节点数目为连续的质数。到第十层，每个结点下有29个结点。 同一结点中的子结点，从左到右代表不同的余数结果。
 * 例如：第二层结点下有三个子节点。那么从左到右分别代表：除3余0，除3余1，除3余2.
 */
public class HashTree<V> {

	private int[] primeNums = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	private transient Node<V> root;

	public HashTree() {
		root = new Node<V>(0, 0, null, makeChildren(0));
	}

	public V add(V value) {
		int hash = hash(value);
		Node<V> node = root;

		for (int i = 0; i < 10; i++) {
			int mod = hash % primeNums[i];
			if (node.getChildren()[mod] == null) {
				node.getChildren()[mod] = new Node<V>(i + 1, mod, value, makeChildren(i + 1));
				return value;
			} else if (node.getChildren()[mod].getValue() == null) {
				node.getChildren()[mod].setValue(value);
				return value;
			} else if (node.getChildren()[mod].getValue().equals(value)) {
				return value;
			} else {
				node = node.getChildren()[mod];
			}
		}
		return null;
	}

	public V get(V value) {
		int hash = hash(value);
		Node<V> node = root;

		for (int i = 0; i < 10; i++) {
			int mod = hash % primeNums[i];
			if (node.getChildren()[mod] == null) {
				return null;
			} else if (node.getChildren()[mod].getValue() == null) {
				node = node.getChildren()[mod];
			} else if (node.getChildren()[mod].getValue().equals(value)) {
				return value;
			} else {
				node = node.getChildren()[mod];
			}
		}
		return null;
	}

	public V remove(V value) {
		int hash = hash(value);
		Node<V> node = root;

		for (int i = 0; i < 10; i++) {
			int mod = hash % primeNums[i];
			if (node.getChildren()[mod] == null) {
				return null;
			} else if (node.getChildren()[mod].getValue() == null) {
				node = node.getChildren()[mod];
			} else if (node.getChildren()[mod].getValue().equals(value)) {
				node.getChildren()[mod].setValue(null);
				return value;
			} else {
				node = node.getChildren()[mod];
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	Node<V>[] makeChildren(int primeNumsIndex) {
		return (Node<V>[]) new Node[primeNums[primeNumsIndex]];
	}

	static final class Node<V> {
		int floor;
		int index;
		V value;
		Node<V>[] children;

		public Node(int floor, int index, V value, Node<V>[] children) {
			this.floor = floor;
			this.index = index;
			this.value = value;
			this.children = children;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getFloor() {
			return floor;
		}

		public void setFloor(int floor) {
			this.floor = floor;
		}

		public Node<V>[] getChildren() {
			return children;
		}

		public void setChildren(Node<V>[] children) {
			this.children = children;
		}

	}

	final int hash(Object key) {
		int h;
		// int hh = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
		int hh = (h = key.hashCode()) ^ (h >>> 16);
		return Math.abs(hh);
	}
}
