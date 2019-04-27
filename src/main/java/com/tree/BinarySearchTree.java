package com.tree;

/**
 * 二叉查找树
 * 
 * @author luqijia
 *
 * @param <AnyType>
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	/**
	 * BinaryNode类（节点类）
	 * 
	 * @author luqijia
	 *
	 * @param <AnyType>
	 */
	private static class BinaryNode<AnyType> {
		AnyType element;
		BinaryNode<AnyType> left;
		BinaryNode<AnyType> right;

		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}
	}

	private BinaryNode<AnyType> root;

	public BinarySearchTree() {
		root = null;
	}

	public void makeEmpty() {
		root = null;
	}

	// 是否为空
	public boolean isEmpty() {
		return root == null;
	}

	// 包含元素
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null) {
			return false;
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			return contains(x, t.left);
		} else if (compareResult > 0) {
			return contains(x, t.right);
		} else {
			return true; // Math
		}
	}

	// 查找最小值
	public AnyType findMin() {
		if (isEmpty()) {
			System.out.println("tree is empty");
		}
		return findMin(root).element;
	}

	// finMin的递归实现
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null) {
			return null;
		} else if (t.left == null) {
			return t;
		}
		return findMin(t.left);
	}

	// 查找最大值
	public AnyType findMax() {
		if (isEmpty()) {
			System.out.println("tree is empty");
		}
		return findMax(root).element;
	}

	// finMax的非递归实现
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null) {
			while (t.right != null) {
				t = t.right;
			}
		}
		return t;
	}

	// 插入元素
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {

		if (t == null) {
			return new BinaryNode<>(x);
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = insert(x, t.left);
		} else if (compareResult > 0) {
			t.right = insert(x, t.right);
		} else {
			// 与该该元素一样，不 做任何事
		}
		return t;
	}

	// 删除元素
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null) {
			return t;
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = remove(x, t.left);
		} else if (compareResult > 0) {
			t.right = remove(x, t.right);
		} else if (t.left != null && t.right != null) { // 有两个孩子节点
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else {
			t = (t.left != null) ? t.left : t.right;
		}
		return t;
	}

	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}

	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		binarySearchTree.insert(20);
		binarySearchTree.insert(10);
		binarySearchTree.insert(42);
		binarySearchTree.insert(12);
		binarySearchTree.insert(15);
		binarySearchTree.remove(20);
		binarySearchTree.printTree();
	}
}
