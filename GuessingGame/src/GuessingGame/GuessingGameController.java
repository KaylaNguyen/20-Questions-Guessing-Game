package GuessingGame;

import IO.CommutativeExpressionReader;
import cs201bintree.*;

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
							"Oops... I guessed wrong! Play unlimited edition to correct me!");
			// restart game
			view.restartGame();
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
}
