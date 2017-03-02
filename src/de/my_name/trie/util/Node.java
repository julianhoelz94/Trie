package de.my_name.trie.util;

/**
 * Created by my_name on 24/10/2016.
 */
public class Node {

    private Integer points;
    private Node[] children;
    private Node parent;
    private char ch;

    private static final int SIZE_ALPHABET = 26;
    private static final int ASCII_CODE_OF_LOWERCASE_LETTER_A = 97;
    private static final char ROOT_CHAR = '+';

    /**
     * Creates a new Object of the type Node. Can be used to set a node
     * without a parent, e.g. the root of the Trie
     */
    public Node() {
        this.children = new Node[SIZE_ALPHABET];
        this.ch = ROOT_CHAR;
        this.parent = null;
    }

    /**
     * Creates a new object of the type node and sets ch as the prefix and
     * the parent as the parent or previous node of the created node.
     *
     * @param ch     is going to be the prefix that is saved in the node.
     * @param parent is going to be the parent of the new node.
     */
    public Node(char ch, Node parent) {
        if (parent == null) {
            throw new IllegalArgumentException();
        }
        this.children = new Node[SIZE_ALPHABET];
        this.ch = ch;
        this.parent = parent;
        parent.setChild(ch, this);
    }

    /**
     * Private method to change the child of a node.
     *
     * @param ch    Child has the character ch.
     * @param child is the node which becomes child of this node.
     */
    private void setChild(char ch, Node child) {
        this.children[getIndex(ch)] = child;
    }

    /**
     * Method to receive the child of the node which is saved for the letter ch.
     *
     * @param ch the letter that is requested.
     * @return gives back the child node for the letter ch.
     */
    public Node getChild(char ch) {
        return this.children[getIndex(ch)];
    }

    /**
     * Determines if a specific object is part of the prefix tree.
     *
     * @param key searches for the object, which holds the name key.
     * @return Returns the found node which holds the value of the searched
     * object. Returns null if object is not found.
     * @throws IllegalArgumentException if the string key does not consist of
     *                                  lower case letters.
     */
    public Node find(String key) throws IllegalArgumentException {
        char[] charedKey = key.toCharArray();
        if (!hasCorrectForm(charedKey)) {
            throw new IllegalArgumentException();
        }
        Node currentNode = this.getChild(charedKey[0]);
        for (int i = 1; i < charedKey.length; ++i) {
            if (currentNode != null) {
                currentNode = currentNode.getChild(charedKey[i]);
            } else {
                return null;
            }
        }
        return currentNode;
    }

    /**
     * Removes this node from the trie and cleans up the rest of the trie.
     */
    public void remove() {
        if (this.parent != null) {
            this.points = null;
            this.cleanup();

        }
    }

    private void cleanup() {
        if (!this.hasChildren() && this.points == null) {
            this.parent.setChild(this.ch, null);
        }
        if (this.parent.points == null) {
            this.parent.remove();
        }
    }

    /**
     * This method tests if the the this node has children.
     *
     * @return Returns false if the current node does not have children.
     */
    public boolean hasChildren() {
        boolean hasChildren = false;
        for (Node child : this.children) {
            if (child != null) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }

    /**
     * This method converts the node and its children into a string parsing
     * the trie in a DFS order.
     *
     * @return Returns a string representation of this node and its
     * children.
     */
    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append(ch);
        if (points != null) {
            buildString.append('[');
            buildString.append(points.toString());
            buildString.append(']');
        }
        if (hasChildren()) {
            buildString.append('(');
            for (Node child : children) {
                if (child != null) {
                    buildString.append(child.toString());
                }
            }
            buildString.append(')');
        }
        return buildString.toString();
    }

    /**
     * Setter method of the variable points.
     *
     * @param points The integer that is being saved in this node. Is set to
     *               null if nothing is saved in the node. If a node is set
     *               to null, the trie should be cleaned up at this node.
     *               Otherwise a possible empty arm is created.
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Getter method of the variable points.
     *
     * @return returns the integer object which is saved in the node. Returns
     * null if no Integer is saved in the Node.
     */
    public Integer getPoints() {
        return this.points;
    }


    /**
     * This method maps the chars ch to a numerical value of the type int.
     * Hereby it assumes that the letter already is of the form a to z.
     *
     * @param ch Must be a non-capitalized letter of the English alphabet.
     * @return Returns a int value of the argument letter starting with a => 0
     * and ending with z => 25.
     */
    private static int getIndex(char ch) {
        int index;
        index = ((int) ch) - ASCII_CODE_OF_LOWERCASE_LETTER_A;
        return index;
    }

    private static boolean hasCorrectForm(char[] charedKey) {
        boolean correct = true;
        for (char ch : charedKey) {
            if (Character.getType(ch) != Character.LOWERCASE_LETTER) {
                correct = false;
            }
        }
        return correct;
    }
}
