package GuessingGame;

// jar file
import cs201bintree.*;
import IO.CommutativeExpressionWriter;
import IO.CommutativeExpressionReader;

/**
 * Controller class that controls the game logic
 */
public class GuessingGameController {
	private DefaultBinTree<String> tree;
	private DefaultBinTreeNode<String> currentNode;
	private GuessingGameView view;

	public GuessingGameController(GuessingGameView view) {
		this.view = view;
		// initialize tree and node
		tree = new DefaultBinTree<String>();
		tree = (DefaultBinTree<String>) CommutativeExpressionReader
				.readCommutativeExpr("supportFiles/textFiles/TravelFile.xml");
		currentNode = (DefaultBinTreeNode<String>) tree.getRoot();
	}

	/**
	 * @return the currentNode
	 */
	public DefaultBinTreeNode<String> getCurrentNode() {
		return currentNode;
	}

	/**
	 * @return the tree
	 */
	public DefaultBinTree<String> getTree() {
		return tree;
	}

	/**
	 * Method called when user chose yes
	 */
	public void choseYes() {
		// check if the current node's child is a leaf first
		// get to the question "Are you thinking of..."
		if (currentNode.getLeftChild() == null
				|| currentNode.getRightChild() == null) {
			// if left node is not null
			if(currentNode.getLeftChild() != null)
				// move to the answer
				currentNode = (DefaultBinTreeNode<String>) currentNode.getLeftChild();
			else
				// move to the answer
				currentNode = (DefaultBinTreeNode<String>) currentNode.getRightChild();
			// set text to be the answer
			view.getQuestion().setText(
					"So it's " + ((String) currentNode.getData()).substring(2)
							+ " indeed!");
			view.restartGame();
		}
		// if current node's child is not a leaf
		else {
			// see the 1st char of the answers
			char ans = ((String) currentNode.getLeftChild().getData())
					.charAt(0);
			// if the left child is the yes answer
			if (Character.toString(ans).equals("Y"))
				// move to next question
				currentNode = (DefaultBinTreeNode<String>) currentNode.getLeftChild();
			else {
				// move to next question
				currentNode = (DefaultBinTreeNode<String>) currentNode.getRightChild();
			}
			// set text to be the next question
			view.getQuestion().setText(
					((String) currentNode.getData()).substring(2));
		}
	}

	/**
	 * Method called when user chose no
	 */
	public void choseNo() {
		// check if the current node's child is a leaf first
		// get to the question "Are you thinking of..."
		if (currentNode.getLeftChild() == null
				|| currentNode.getRightChild() == null) {
			// display text
			view.getQuestion()
					.setText(
							"Oops... I guessed wrong! Please tell me what's in your mind!");
			// prompt form to take user's input
			view.prompForm();
		}

		// if current node's child is not a leaf
		else {
			// see the 1st char of the answers
			char ans = ((String) currentNode.getLeftChild().getData())
					.charAt(0);
			// if the left child is the yes answer
			if (Character.toString(ans).equals("N")) {
				// move to next question
				currentNode = (DefaultBinTreeNode<String>) currentNode.getLeftChild();
			} else {
				// move to next question
				currentNode = (DefaultBinTreeNode<String>) currentNode.getRightChild();
			}
			// set text to be the next question
			view.getQuestion().setText(
					((String) currentNode.getData()).substring(2));
		}
	}

	/**
	 * Method called to update tree and xml file
	 */
	public void updateTree(boolean chooseYes, String question, String answer) {
		// add the question to be the current node's child
		// if left child is null
		if (currentNode.getLeftChild() == null) {
			currentNode.setLeftChild(new DefaultBinTreeNode<String>("N:" + question));
			// current node is now the child
			currentNode = (DefaultBinTreeNode<String>) currentNode.getLeftChild();
		} 
		// if right child is null
		else {
			currentNode.setRightChild(new DefaultBinTreeNode<String>("N:" + question));
			// current node is now the child
			currentNode = (DefaultBinTreeNode<String>) currentNode.getRightChild();
		}

		// if user chose yes as the answer
		if (chooseYes) {
			// if left child is null
			if (currentNode.getLeftChild() == null)
				// set left child to be the answer with the flag Y
				currentNode.setLeftChild(new DefaultBinTreeNode<String>("Y:" + answer));
			// if right child is null
			else
				// set right child to be the answer with the flag Y
				currentNode.setRightChild(new DefaultBinTreeNode<String>("Y:" + answer));
		}
		else{
				// if left child is null
				if (currentNode.getLeftChild() == null)
					// set left child to be the answer with the flag N
					currentNode.setLeftChild(new DefaultBinTreeNode<String>("N:" + answer));
				// if right child is null
				else
					// set right child to be the answer with the flag N
					currentNode.setRightChild(new DefaultBinTreeNode<String>("N:" + answer));
			}
	
		// update the xml file and update the tree
		CommutativeExpressionWriter.writeCommutativeExpr( (BinTree<String>) getTree(), "supportFiles/textFiles/TravelFile.xml");
	
		// set question text
		view.getQuestion().setText("Let's try again!");
		// call view to restart game
		view.restartGame();
	}
}
