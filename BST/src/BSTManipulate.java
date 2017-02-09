import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BSTManipulate extends JFrame{
	
	private JPanel controlPanel;	
	private JTextField addField;
	private JTextField deleteField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton searchButton;
	private JButton printButton;
	private JButton rebalanceButton;
	private JButton clearButton;
	private JTextArea outputArea;
	
	private BSTNode root = null;
	private BSTNode dNode = null;
	private BSTNode dPNode = null;
	private Stack<Integer> searchStack = new Stack<Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSTManipulate bstGUI = new BSTManipulate();
		bstGUI.setVisible(true);
		bstGUI.setSize(800, 600);
		bstGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BSTManipulate(){
		super("Binary Search Tree Manipulate GUI");
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 4, 5, 5));
		addField = new JTextField();
		controlPanel.add(addField);
		addButton = new JButton("Add Node");
		controlPanel.add(addButton);
		deleteField = new JTextField();
		controlPanel.add(deleteField);
		deleteButton = new JButton("Delete Node");
		controlPanel.add(deleteButton);
		searchButton = new JButton("Search Value");
		controlPanel.add(searchButton);
		printButton = new JButton("Print Tree in ...");
		controlPanel.add(printButton);
		rebalanceButton = new JButton("Rebalance Tree");
		controlPanel.add(rebalanceButton);
		clearButton = new JButton("Clear Tree");
		controlPanel.add(clearButton);
		add(controlPanel, BorderLayout.NORTH);
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		add(outputArea, BorderLayout.CENTER);
		
		ButtonHandler ButtonHandler = new ButtonHandler();
		addButton.addActionListener(ButtonHandler);
		deleteButton.addActionListener(ButtonHandler);
		searchButton.addActionListener(ButtonHandler);
		printButton.addActionListener(ButtonHandler);
		rebalanceButton.addActionListener(ButtonHandler);
		clearButton.addActionListener(ButtonHandler);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			if(event.getSource().equals(addButton)){
				
				try{
					int inputValue = Integer.parseInt(addField.getText().trim());
					if(root == null){
						root = new BSTNode(inputValue);
						outputArea.append("Value has been inserted : "+String.valueOf(inputValue)+"\n");
					}else{
						if(search(inputValue)){
							JOptionPane.showMessageDialog(BSTManipulate.this, "this number is already in binary search tree", "Alert", JOptionPane.ERROR_MESSAGE);
						}else{
							insert(inputValue);
							outputArea.append("Value has been inserted : "+String.valueOf(inputValue)+"\n");
						}
					}
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(BSTManipulate.this, "InputValue is connot format to Integer", "Alert", JOptionPane.ERROR_MESSAGE);
				}catch(Exception e){
					e.printStackTrace();
				}
				addField.setText("");
				addField.requestFocus();
				
			}else if(event.getSource().equals(deleteButton)){
				
				if(root == null){
					JOptionPane.showMessageDialog(BSTManipulate.this, "The tree is empty!", "Alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try{
					int inputValue = Integer.parseInt(deleteField.getText().trim());
					outputArea.append("Attempt to delete the Value : "+inputValue+"\n");
					if(search(inputValue)){
						delete();
						outputArea.append("Node : "+inputValue+" has been deleted\n");
					}else{
						outputArea.append("The Node : "+inputValue+" is not exists\n");
					}
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(BSTManipulate.this, "InputValue is connot format to Integer", "Alert", JOptionPane.ERROR_MESSAGE);
				}catch(Exception e){
					e.printStackTrace();
				}
				deleteField.setText("");
				deleteField.requestFocus();
				
			}else if(event.getSource().equals(searchButton)){
				
				if(root == null){
					JOptionPane.showMessageDialog(BSTManipulate.this, "The tree is empty!", "Alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try{
					int inputValue = Integer.parseInt(JOptionPane.showInputDialog(BSTManipulate.this, "Enter the value you want to search", "Search", JOptionPane.QUESTION_MESSAGE).trim());
					outputArea.append("Attempt to find the Value : "+inputValue+"\n");
					if(search(inputValue)){
						outputArea.append("Find the Node : "+inputValue+"  It's Parent is: ");
						while(!searchStack.isEmpty()){
							outputArea.append("-->"+searchStack.pop());
						}
						outputArea.append("-->null\n");
					}else{
						outputArea.append("Can't find the Node : "+inputValue+"\n");
					}
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(BSTManipulate.this, "InputValue is connot format to Integer", "Alert", JOptionPane.ERROR_MESSAGE);
				}catch(Exception e){
					System.out.println("cancel the search function");
				}
				
			}else if(event.getSource().equals(printButton)){
				
				if(root == null){
					JOptionPane.showMessageDialog(BSTManipulate.this, "The tree is empty!", "Alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String[] traversalList = { "PreOrder", "InOrder", "PostOrder", "LevelOrder" };
				String traversalWay = (String) JOptionPane.showInputDialog(BSTManipulate.this, "How to Traversal the tree?", "Input", JOptionPane.INFORMATION_MESSAGE, null, traversalList, traversalList[0]);
				try{
					switch(traversalWay){
					case "PreOrder":
						outputArea.append("Print Tree in PreOrder : ");
						preOrderPrint(root);
						outputArea.append("\n");
						break;
					case "InOrder":
						outputArea.append("Print Tree in InOrder : ");
						inOrderPrint(root);
						outputArea.append("\n");
						break;
					case "PostOrder":
						outputArea.append("Print Tree in PostOrder : ");
						postOrderPrint(root);
						outputArea.append("\n");
						break;
					case "LevelOrder":
						outputArea.append("Print Tree in LevelOrder : ");
						levelOrderPrint(root);
						outputArea.append("\n");
						break;	
					default:
					}
				}catch(Exception e){
					System.out.println("cancel the print function");
				}
				
			}else if(event.getSource().equals(rebalanceButton)){
				
			}else if(event.getSource().equals(clearButton)){
				
				root = null;
				JOptionPane.showMessageDialog(BSTManipulate.this, "The tree has been cleared", "Clear", JOptionPane.INFORMATION_MESSAGE);
				outputArea.append("The tree has been cleared\n");
				outputArea.append("-------------------------------------\n");
				
			}
		}
		
	}
	
	public boolean search(int value){
		BSTNode current = root;
		searchStack = new Stack<Integer>();
		dNode = null;
		dPNode = null;
		while(true){
			if(current.getValue() == value){
				dNode = current;
				return true;
			}
			if(value < current.getValue()){
				if(current.getLeftNode() == null){
					return false;
				}else{
					searchStack.push(current.getValue());
					dPNode = current;
					current = current.getLeftNode();
				}
			}else if(value > current.getValue()){
				if(current.getRightNode() == null){
					return false;
				}else{
					searchStack.push(current.getValue());
					dPNode = current;
					current = current.getRightNode();
				}
			}
		}
	}
	
	public void insert(int value){
		BSTNode current = root;
		while(true){
			if(value < current.getValue()){
				if(current.getLeftNode() == null){
					current.setLeftNode(new BSTNode(value));
					return;
				}else{
					current = current.getLeftNode();
				}
			}else if(value > current.getValue()){
				if(current.getRightNode() == null){
					current.setRightNode(new BSTNode(value));
					return;
				}else{
					current = current.getRightNode();
				}
			}
		}
	}
	
	public void delete(){
		if(dNode.getLeftNode() == null && dNode.getRightNode() == null){
			if(dPNode != null){
				if(dPNode.getLeftNode().getValue() == dNode.getValue()){
					dPNode.setLeftNode(null);
				}else{
					dPNode.setRightNode(null);
				}
			}else{
				root = null;
			}
		}else if(dNode.getLeftNode() == null){
			
		}else if(dNode.getRightNode() == null){
			
		}else{
			
		}
	}
	
	public void preOrderPrint(BSTNode node){
		outputArea.append(String.valueOf(node.getValue())+" ");
		if(node.getLeftNode() != null){
			preOrderPrint(node.getLeftNode());
		}
		if(node.getRightNode() != null){
			preOrderPrint(node.getRightNode());
		}
	}
	
	public void inOrderPrint(BSTNode node){
		if(node.getLeftNode() != null){
			inOrderPrint(node.getLeftNode());
		}
		outputArea.append(String.valueOf(node.getValue())+" ");
		if(node.getRightNode() != null){
			inOrderPrint(node.getRightNode());
		}
	}
	
	public void postOrderPrint(BSTNode node){
		if(node.getLeftNode() != null){
			postOrderPrint(node.getLeftNode());
		}
		if(node.getRightNode() != null){
			postOrderPrint(node.getRightNode());
		}
		outputArea.append(String.valueOf(node.getValue())+" ");
	}
	
	public void levelOrderPrint(BSTNode node){
		
	}

}
