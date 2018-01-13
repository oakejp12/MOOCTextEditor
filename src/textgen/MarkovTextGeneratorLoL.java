package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator)
    {
        wordList = new LinkedList<>();
        starter = "";
        rnGenerator = generator;
    }


    /** Train the generator by adding the sourceText */
    @Override
    public void train(String sourceText) {

        // Call getTokens on the text to preserve separate strings that are either words.
        // Ignore everything that is not a word.
        List<String> tokens = getTokens(sourceText);

        starter = tokens.get(0);

        int wordIndex = 0;
        for (String token : tokens) {
            if (isWord(token)) {

                ListNode node;

                int indexOfWord = getWordIndex(token);

                if (indexOfWord < 0) {
                    // Word wasn't found in current storage so create a new one
                    node = new ListNode(token);
                    wordList.add(node);
                } else {
                    // Find the ListNode in the word list so we can add to it...
                    node = wordList.get(indexOfWord);
                }

                // Retrieve the next node word and add it to the current node's list of words...
                String nextWord = (wordIndex < tokens.size() - 1) ? tokens.get(wordIndex + 1) : null;
                if (nextWord != null) {
                    node.addNextWord(nextWord);
                }
            } else {
                System.err.println("ERROR: Captured a non-word: " + token);
            }
            wordIndex++;
        }

        // Retrieve last word and set its next word to the starter word
        String lastWord = tokens.get(tokens.size() - 1);
        int indexOfWord = getWordIndex(lastWord);
        ListNode lastWordNode = wordList.get(indexOfWord);
        lastWordNode.addNextWord(starter);
    }

    /**
     * Generate the number of words requested.
     *
     * @param numWords the number of words to generate
     * @return generated text
     */
    @Override
    public String generateText(int numWords) {
        String generatedText = starter;

        // Start from the "starting" word and branch out
        int startingNodeIndex = getWordIndex(starter);
        ListNode node = wordList.get(startingNodeIndex);

        // For so many words - 1 (since we already have a starting word)
        // Branch out with random word
        for (int i = 0; i < numWords - 1; ++i) {
            String wordToAdd = node.getRandomNextWord(rnGenerator);
            generatedText += " " + wordToAdd;

            node = wordList.get(getWordIndex(wordToAdd));
        }


        return generatedText;
    }


    // Can be helpful for debugging
    @Override
    public String toString()
    {
        String toReturn = "";
        for (ListNode n : wordList)
        {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /** Retrain the generator from scratch on the source text */
    @Override
    public void retrain(String sourceText) {
        // Scratch out saved data and run train() again...
        wordList.clear();
        starter = "";
        train(sourceText);
    }

    // NOTE: Add any private helper methods you need here.

    /**
     * Find the first occurrence of a word in the list of words already processed and
     * returns the index of where it was found.
     *
     * @param word word to search for in the wordList
     * @return index of the word if found, else -1
     */
    private int getWordIndex(String word) {

        if (wordList.isEmpty()) return -1;

        int currentIndex = 0;
        ListNode currentNode = wordList.get(currentIndex);
        while (!(currentNode.getWord().equals(word))) {
            currentIndex++;

            if (currentIndex == (wordList.size())) return -1;

            currentNode = wordList.get(currentIndex);
        }

        return currentIndex;
    }

    /** Returns the tokens that match the regex pattern from the document
     * text string.
     *
     * @param sourceText Text being trained on and used to split apart to retrieve words
     * @return A List of tokens from the document text that match the regex
     *   pattern
     */
    private List<String> getTokens(String sourceText)
    {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern tokSplitter = Pattern.compile("[a-zA-Z']+");
        Matcher m = tokSplitter.matcher(sourceText);

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    /**
     * Take a string that either contains only alphabetic characters,
     * or only sentence-ending punctuation.  Return true if the string
     * contains only alphabetic characters, and false if it contains
     * end of sentence punctuation.
     *
     * @param tok The string to check
     * @return true if tok is a word, false if it is punctuation.
     */
    private boolean isWord(String tok)
    {
        // Note: This is a fast way of checking whether a string is a word
        // You probably don't want to change it.
        return !(tok.contains("!") || tok.contains(".") || tok.contains("?"));
    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     * @param args
     */
    public static void main(String[] args)
    {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, "+
                "You say stop, and I say go, go, go, "+
                "Oh no. You say goodbye and I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "I say high, you say low, "+
                "You say why, and I say I don't know. "+
                "Oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "Why, why, why, why, why, why, "+
                "Do you say goodbye. "+
                "Oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "You say yes, I say no, "+
                "You say stop and I say go, go, go. "+
                "Oh, oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word)
    {
        this.word = word;
        nextWords = new LinkedList<>();
    }

    public String getWord()
    {
        return word;
    }

    public void addNextWord(String nextWord)
    {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator)
    {
        if (nextWords.size() > 0) {
            return nextWords.get(generator.nextInt(nextWords.size()));
        }

        return "";
    }

    public String toString()
    {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


