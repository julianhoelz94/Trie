package de.my_name.trie.util;

/**
 * Created by my_name on 24/10/2016.
 */
public class Trie {

    private final Node root;

    /**
     * Creates a new object of the type Trie and sets the root as an empty
     * Node Object.
     */
    public Trie() {
        this.root = new Node();
    }


    /**
     * Adds a new item to the Trie.
     *
     * @param key adds the new item under the name of the key. The key must
     *            be definite.
     * @param points Assigns the Integer argument point to the new item.
     * @return Returns
     * @throws IllegalArgumentException if the key is not of the requested
     * form or if points is null.
     */
    public boolean add(String key, Integer points) throws
            IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        char[] charedKey = key.toCharArray();
        if (!hasCorrectForm(charedKey) || points == null) {
            throw new IllegalArgumentException();
        }
        Node currentNode = root;
        for (char ch : charedKey) {
            Node child = currentNode.getChild(ch);
            if (child == null) {
                currentNode = new Node(ch, currentNode);
            } else {
                currentNode = child;
            }
        }
        if (currentNode.getPoints() != null) {
            return false;
        }
        currentNode.setPoints(points);
        return true;
    }

    /**
     * Removes an item from the Trie. Cleans the Trie afterwards.
     *
     * @param key searches for the item with this key and removes it from the
     *            Trie.
     * @return Returns true if the Object was removed, false if it could not
     * be found.
     * @throws IllegalArgumentException if key does not have the correct form.
     */
    public boolean remove(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node nodeToRemove = root.find(key);
        if (nodeToRemove == null || nodeToRemove.getPoints() == null) {
            return false;
        }
        nodeToRemove.remove();
        return true;
    }

    /**
     * Changes the value of a item that is already in the Trie.
     *
     * @param key is the definite key of the item that is being changed.
     * @param points is the new Integer object that is being saved to key.
     * @return Returns true if the object was changed, false if it could not
     * be found.
     * @throws IllegalArgumentException if points is null or if the key is
     * not of the correct form.
     */
    public boolean change(String key, Integer points) throws
            IllegalArgumentException {
        if (points == null || key == null) {
            throw new IllegalArgumentException();
        }
        Node nodeToChange = root.find(key);
        if (nodeToChange == null || nodeToChange.getPoints() == null) {
            return false;
        }
        nodeToChange.setPoints(points);
        return true;
    }

    /**
     * Searches for the an item in the Trie and returns its Integer object.
     *
     * @param key Searches for the argument key.
     * @return Returns the Integer object of the found item, null if the
     * object could not be found.
     * @throws IllegalArgumentException if the key is not in the correct form.
     */
    public Integer getPoints(String key) throws IllegalArgumentException {
        Node nodeToRead = root.find(key);
        if (nodeToRead == null) {
            return null;
        }
        return nodeToRead.getPoints();
    }

    /**
     * This Method converts the Trie into a String parsing
     * the Trie in a DFS order.
     *
     * @return Returns a String representation of the Tire.
     */
    @Override
    public String toString() {
        return root.toString();
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
