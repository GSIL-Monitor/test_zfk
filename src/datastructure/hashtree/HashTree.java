package datastructure.hashtree;

/**
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
