package com.tree;

/**
 * 平衡二叉树
 * 
 * @author luqijia
 *
 * @param <AnyType>
 */
public class BalanceTree<AnyType extends Comparable<? super AnyType>> {
	/**
	 * Avl树的节点声明
	 * 
	 * @author luqijia
	 *
	 * @param <AnyType>
	 */
	private static class AvlNode<AnyType> {
		AnyType element;
		AvlNode<AnyType> left;
		AvlNode<AnyType> right;
		int height;

		AvlNode(AnyType theElement) {
			this(theElement, null, null);
		}

		AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
			element = theElement;
			lt = left;
			rt = right;
			height = 0;
		}
	}

	private AvlNode<AnyType> root;

	public BalanceTree() {
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

	private boolean contains(AnyType x, AvlNode<AnyType> t) {
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
	private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
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
	private AvlNode<AnyType> findMax(AvlNode<AnyType> t) {
		if (t != null) {
			while (t.right != null) {
				t = t.right;
			}
		}
		return t;
	}

	private int height(AvlNode<AnyType> t) {
		return t == null ? -1 : t.height;
	}

	// 插入元素
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {

		if (t == null) {
			return new AvlNode<>(x);
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = insert(x, t.left);
		} else if (compareResult > 0) {
			t.right = insert(x, t.right);
		} else {
			// 与该该元素一样，不 做任何事
		}
		return balance(t);
	}

	private static final int ALLOWED_IMBALANCE = 1;

	/**
	 * 使树处于平衡状态
	 * 
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
		if (t == null) {
			return t;
		}
		if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
			if (height(t.left.left) >= height(t.left.right)) {
				t = rotateWithLeftChild(t); // 单旋转 把左边的树变为右边的树
			} else {
				t = doubleWithLeftChild(t); // 双旋转
			}
		} else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
			if (height(t.right.right) >= height(t.right.left)) {
				t = rotateWithRightChild(t); // 单旋转 把右边的树变为左边的树
			} else {
				t = doubleWithRightChild(t); // 双旋转
			}
		}
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	/**
	 * 参照93页图4-40
	 * @param k1
	 * @return
	 */
	private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
		AvlNode<AnyType> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;
		return k2;
	}

	private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> t) {
		t.right = rotateWithLeftChild(t.right);
		return rotateWithRightChild(t);

	}

	/**
	 * 单旋转 用左子级旋转二叉树节点
	 * 
	 * 对于AVL树，这是案例1的单个旋转
	 * 
	 * 更新高度，然后返回新根
	 * 参照93页图4-40
	 * 
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {

		AvlNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	/**
	 * 双旋转
	 * 
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> t) {
		t.left = rotateWithRightChild(t.left);
		return rotateWithLeftChild(t);
	}

	// 删除元素
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * AVL树的删除
	 * 
	 * @param x
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
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
		return balance(t);
	}

	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}

	private void printTree(AvlNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
	
	public static void main(String[] args) {
		BalanceTree<Integer> balanceTree = new BalanceTree<Integer>();
		balanceTree.insert(10);
		balanceTree.insert(30);
		balanceTree.insert(65);
		balanceTree.insert(50);
		balanceTree.insert(90);
		balanceTree.insert(40);
		balanceTree.printTree();
	}
}
