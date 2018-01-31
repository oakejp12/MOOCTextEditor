/**
 *
 */
package spelling;

import jdk.jfr.events.ThrowablesEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000;

    Dictionary dict;

    public NearbyWords (Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Return the list of Strings that are one modification away
     * from the input string.
     * @param s The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly )  {
        List<String> retList = new ArrayList<>();
        insertions(s, retList, wordsOnly);
        substitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }


    /**
     * Add to the currentList Strings that are one character mutation away from the input string.
     *
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for(int index = 0; index < s.length(); index++){
            for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {

                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuilder sb = new StringBuilder(s);
                sb.setCharAt(index, (char)charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if(!currentList.contains(sb.toString()) && (!wordsOnly||dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away from the input string.
     *
     * @param s the original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly ) {

        // for each letter in s and for all possible insertion characters
        for(int index = 0; index <= s.length(); index++){
            for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {

                // Use StringBuilder for an easy interface to permuting the letters in the String
                StringBuilder begSubStr = new StringBuilder(s.substring(0, index));
                StringBuilder endSubStr = new StringBuilder(s.substring(index, s.length()));

                StringBuilder sb = begSubStr.append((char) charCode).append(endSubStr);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if(!currentList.contains(sb.toString()) && (!wordsOnly||dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /** Add to the currentList Strings that are one character deletion away
     * from the input string.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly ) {

        // for each letter in the s and for all possible replacement characters
        for(int index = 0; index < s.length(); index++){
            char[] temp = s.toCharArray();
            temp[index] = '\0';

            String str = new String(temp);

            // if the item isn't in the list, isn't the original string, and
            // (if wordsOnly is true) is a real word, add to the list
            if(!currentList.contains(str) && (!wordsOnly||dict.isWord(str)) && !s.equals(str)) {
                currentList.add(str);
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away from the input string.
     *
     * @param word The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {

        // initial variables
        List<String> queue = new LinkedList<>();     // String to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same
        // string multiple times
        List<String> retList = new LinkedList<>();   // words to return

        // insert first node
        queue.add(word);
        visited.add(word);

        while (!queue.isEmpty() && retList.size() < numSuggestions) {
            String popped = queue.remove(0);

            List<String> explore = distanceOne(popped, true);

            explore.forEach((str) -> {
                if (!visited.contains(str)) {
                    visited.add(str);
                    queue.add(str);

                    if (dict.isWord(str)) retList.add(str);
                }
            });
        }


        return retList;

    }

    public static void main(String[] args) {
        String word = "he";

        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \""+word+"\" are:");
        System.out.println(l+"\n");

        word = "tailo";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \""+word+"\" are:");
        System.out.println(suggest);
    }

}
