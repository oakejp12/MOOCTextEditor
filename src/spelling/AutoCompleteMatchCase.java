package spelling;

import java.util.*;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;

    public AutoCompleteMatchCase() {
        root = new TrieNode();
        size = 0;
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     *
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        word = word.toLowerCase();

        // Don't add the word if it's already there
        if (isWord(word)) return false;

        TrieNode curr = root;

        for (int i = 0; i < word.length(); i++) {
            Character character = word.charAt(i);

            if (!((curr.getValidNextCharacters()).contains(character))) {
                curr.insert(character);
            }

            if (i == word.length() - 1) {
                curr = curr.getChild(character);
                curr.setEndsWord(true);
                size++;
                return true;
            }

            curr = curr.getChild(character);
        }


        return false;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }


    /** Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week. */
    @Override
    public boolean isWord(String s) {
        s = s.toLowerCase();

        // No words have been added
        if (size < 1) return false;

        // Since root node is the empty string, start at it's first child
        // that corresponds to the first character of the provided argument
        TrieNode curr = root;

        for (int i = 0; i < s.length(); i++) {
            Character character = s.charAt(i);

            // If the next character to inspect isn't in the set of next valid characters
            // the word doesn't exist...
            if (!(curr.getValidNextCharacters()).contains(character)) {
                return false;
            }

            curr = curr.getChild(character);

            if (curr.endsWord() && curr.getText().equalsIgnoreCase(s))
                return true;
        }

        return false;
    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     *
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     *
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        //    Create a list of completions to return (initially empty)
        //    While the queue is not empty and you don't have enough completions:
        //       remove the first Node from the queue
        //       If it is a word, add it to the completions list
        //       Add all of its child nodes to the back of the queue
        // Return the list of completions

        List<String> completions = new ArrayList<>();

        TrieNode root = getStem(prefix);

        if (root != null) {
            Queue<TrieNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                TrieNode curr = queue.remove();

                if (curr.endsWord()) {
                    completions.add(curr.getText());
                }

                for (Character c : curr.getValidNextCharacters()) queue.add(curr.getChild(c));
            }
        }

        int endIndex = (numCompletions >= completions.size()) ? completions.size() : numCompletions;

        return completions.subList(0, endIndex);
    }

    /**
     * Find the stem in the trie relating to the provided prefix. This method is
     * similar in implementation to isWord, but it returns the TrieNode associated
     * with the stem provided which may not necessarily be an end word.
     *
     * @param prefix    stem to search for
     * @return          TrieNode corresponding to the stem
     */
    private TrieNode getStem(String prefix) {
        prefix = prefix.toLowerCase(); // Just in case, ya know...

        if (prefix.isEmpty()) return this.root;

        TrieNode curr = root;

        for (int i = 0; i < prefix.length(); i++) {
            Character character = prefix.charAt(i);

            // If the next character to inspect isn't in the set of next valid characters
            // the word doesn't exist...
            if (!(curr.getValidNextCharacters()).contains(character)) return null;

            curr = curr.getChild(character);

            if (curr.getText().equalsIgnoreCase(prefix)) return curr;
        }

        return null;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /** Do a pre-order traversal from this node down */
    private void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }



}