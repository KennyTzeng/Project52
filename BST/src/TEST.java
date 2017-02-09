
public class TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSTNode root = new BSTNode(5);
		root.setLeftNode(new BSTNode(3));
		root.getLeftNode().setLeftNode(new BSTNode(2));
		root.getLeftNode().setRightNode(new BSTNode(4));
		BSTNode t = root.getLeftNode();
		// BSTNode rep = new BSTNode(8);
		t.setValue(8);
		BSTNode g = t.getLeftNode();
		g = new BSTNode(55);
//		root.setRightNode(new BSTNode(7));
//		root.getRightNode().setRightNode(new BSTNode(9));
//		root.setRightNode(null);
//		System.out.println(root.getRightNode().getRightNode().getValue());
		System.out.println(root.getLeftNode().getLeftNode().getValue());
	}

}
