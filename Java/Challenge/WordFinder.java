
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class WordFinder {

    private ArrayList<String> words;

    /**
     * Constructs a WordFinder object with an empty list of words
     */
    public WordFinder() {
        words = new ArrayList<String>();
    }

    /**
     * Loads a list of words from a given text file
     *
     * @param source the name of the file as an absolute path (ex. "C:\\CS\
     * \Challenges\\WordSearchChallenge\\words.txt")
     */
    public void load(String source) {
// Construct the Scanner and File objects for reading
        try {
            File inputFile = new File(source);
            Scanner in = new Scanner(inputFile);
// Read the input file
            while (in.hasNextLine()) {
                String line = in.nextLine();
                words.add(line);
            }
            in.close();
        } catch (FileNotFoundException fileEx) {
//this.source = null;
            fileEx.printStackTrace();
        }
    }

    /**
     * Loads a list of words from a given file that is selected from a pop-up
     * dialog window
     */
    public void pick() {
        JFileChooser chooser = new JFileChooser(".");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            load(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Prints the words in the list
     */
    public void printWords() {
        for (String word : words) {
            //System.out.println(word);
        }
    }

    /**
     * Finds the total number of words in the list
     *
     * @return the total number of words in the list, or 0 if there are no words
     */
    public int totalWords() {
        int i = 0;
        for (String word : words) {
            i++;
        }
        return i;
    }

    /**
     * Finds the total number of words that start with a given character
     *
     * @param letter the start character
     * @return the total number of words that start with the given character, or
     * 0 if there are no words
     */
    public int startsWith(char letter) {
        int[] chars = new int[26];
        for(String word : words) {
            chars[word.charAt(0) - 'A']++;
        }
        return chars[letter - 'A'];
    }

    /**
     * Finds the total number of words that end with a given character
     *
     * @param letter the end character
     * @return the total number of words that end with the given character, or 0
     * if there are no words
     */
    public int endsWith(char letter) {
        int[] chars = new int[26];
        for(String word : words) {
            chars[word.charAt(word.length() - 1) - 'A']++;
        }
        return chars[letter - 'A'];
    }

    /**
     * Finds the total number of words that are a given length
     *
     * @param length the given length of the word
     * @return the total number of words that are a given length, or 0 if there
     * are no words
     */
    public int lettersLong(int length) {
        int i = 0;
        for(String word : words) {
            if(word.length() == length) {
                i++;
            }
        }
        return i;
    }

    /**
     * Finds the length of the longest word in the list
     *
     * @return the length of the longest word in the list, or 0 if there are no
     * words
     */
    public int longestLength() {
        int i = 0;
        int i_max = 0;
        for(String word : words) {
            i = word.length();
            if(i > i_max) {
                i_max = i;
            }
        }
        return i_max;
    }

    /**
     * Finds the total number of words that start with a given character, and
     * end with a given character
     *
     * @param start the start character
     * @param end the end character
     * @return the total number of words that start with a given character, and
     * end with the given character, or 0 if there are no words
     */
    public int startsWithAndEndsWith(char start, char end) {
        int i = 0;
        for(String word : words) {
            if(word.charAt(0) == start && word.charAt(word.length() - 1) == end) {
                i++;
            }
        }
        return i;
    }

    /**
     * Finds the total number of words that contain an exact given substring
     *
     * @param sequence the exact given substring
     * @return the total number of words that contain an exact given substring,
     * or 0 if there are no words
     */
    public int containsExact(String sequence) {
        int c = 0;
        for(String word : words) {
            for(int i = 0; i < word.length()-sequence.length(); i++) {
                if(word.substring(i, i+sequence.length()).equals(sequence)) {
                    c++;
                }
            }
        }
        return c;
    }

    /**
     * Finds the total number of words that start with a given substring
     *
     * @param sequence the substring
     * @return the total number of words that start with a given substring, or 0
     * if there are no words
     */
    public int startsWith(String sequence) {
        int c = 0;
        for(String word : words) {
            if(word.substring(0, sequence.length()).equals(sequence)) {
                c++;
            }
        }
        return c;
    }

    /**
     * Finds the total number of words that contain at least one of the given
     * set of characters at least once
     *
     * @param chars the given set of characters to check for
     * @return the total number of words that contain at least one of the given
     * set characters at least once, or 0 if there are no words
     */
    public int matchesAnyOf(String chars) {
        int c = 0;
        for(String word : words) {
            badloop:
            for(int i = 0; i < word.length(); i++) {
                for (int j = 0; j < chars.length(); j++) {
                    if(word.charAt(i) == chars.charAt(j)) {
                        c++;
                        break badloop;
                    }
                }
            }
        }
        return c;
    }

    /**
     * Finds the total number of words that have at least two adjacent letters
     * that are the same (ex. ATTUNE, FOAMLESS, TEEPEE)
     *
     * @return the total number of words that have at least two adjacent letters
     * that are the same, or 0 if there are no words
     */
    public int hasDoubleLetters() {
        int c = 0;
        for(String word : words) {
            for(int i = 0; i < word.length() - 1;i++){
                if(word.charAt(i) == word.charAt(i+1)) {
                    c++;
                    break;
                }
                
            }
        }
        return c;
    }

    /**
     * Finds the total number of words that contain all of the given set of
     * characters at least once in any order (not including repeats)
     *
     * @param chars the given set of characters to check for
     * @return the total number of words that contain all of the given set of
     * characters at least once in any order, or 0 if there are no words
     */
    public int matchesAll(String chars) {
        String chars_init = chars;
        int c = 0;
        for(String word : words) {
            for(int i = 0; i < word.length();i++){  
                for (int j = 0; j < chars.length(); j++) {
                    if(word.charAt(i) == chars.charAt(j)) {
                        chars = chars.substring(0, j) + chars.substring(j + 1);
                    }
                }
            }
            if(chars.length() == 0) {
                c++;
            }
            chars = chars_init;
        }
        return c;
    }

    /**
     * Finds the total number of words that do not contain any of the given set
     * of characters
     *
     * @param chars the given set of characters to check for
     * @return the total number of words that do not contain any of the given
     * set of characters, or 0 if there are no words
     */
    public int doesNotMatchAny(String chars) {
       int c = 0;
        for(String word : words) {
            outerLoop:
            for(int i = 0; i < word.length();i++){  
                for (int j = 0; j < chars.length(); j++) {
                    if(word.charAt(i) == chars.charAt(j)) {
                        c--;
                        break outerLoop;
                    }
                }
            }
            c++;
        }
        return c;
    }

    /**
     * Finds the total number of words that contain all of a set of given
     * characters at least once, and do not contain another given set of
     * characters
     *
     * @param wantThese the given set of characters (at least once) to include
     * @param dontWantThese the given set of characters to not include
     * @return the total number of words that contain all of a set of given
     * characters at least once, and do not contain another given set of
     * characters, or 0 if there are no words
     */
    public int containsThisAndNotThat(String wantThese, String dontWantThese) {
        String chars_init = wantThese;
        int c = 0;
        for(String word : words) {
            outerLoop:
            for(int i = 0; i < word.length();i++){  
                for (int j = 0; j < wantThese.length(); j++) {
                    if(word.charAt(i) == wantThese.charAt(j)) {
                        wantThese = wantThese.substring(0, j) + wantThese.substring(j + 1);
                    }
                }
                if(wantThese.length() == 0) {
                    for(int j = 0; j < dontWantThese.length(); j++) {
                        if(word.charAt(i) == dontWantThese.charAt(j)) {
                            c--;
                            break outerLoop;
                        }
                    }
                }
            }
            if(wantThese.length() == 0) {
                c++;
            }
            wantThese = chars_init;
        }
        return c;
    }

    /**
     * Finds the total number of words that contain exactly one vowel 'A', 'E',
     * 'I', 'O', 'U' (not including Y)
     *
     * @return the total number of words that contain exactly one vowel 'A',
     * 'E', 'I', 'O', 'U' (not including Y), or 0 if there are no words
     */
    public int containsExactlyOneVowel() {
       int c = 0;
       int vowels = 0;
        for(String word : words) {
            vowels = 0;
            for(int i = 0; i < word.length(); i++) {
                if(word.charAt(i) == 'A' || word.charAt(i) == 'E' || word.charAt(i) == 'I' || word.charAt(i) == 'O' || word.charAt(i) == 'U') {
                    vowels++;
                }
            }
            if (vowels == 1) {
                c++;
            }
        }
        return c;
    }

    /**
     * Finds the total number of words that start with a given character,
     * contains all of the characters, at least once, from a given set, and does
     * not contain any of a given set of characters
     *
     * @param start the start character
     * @param contains the given set of characters to include
     * @param doesNotContain the given set of characters to not include
     * @return the total number of words that start with a given character,
     * contains all of the characters, at least once, from a given set, and does
     * not contain any of a given set of characters, or 0 if there are no words
     */
    public int startsWithAndContains(char start, String wantThese, String dontWantThese) {
        String chars_init = wantThese;
        int c = 0;
        for(String word : words) {
            if (word.charAt(0) == start) {

            
            outerLoop:
            for(int i = 0; i < word.length();i++){  
                for (int j = 0; j < wantThese.length(); j++) {
                    if(word.charAt(i) == wantThese.charAt(j)) {
                        wantThese = wantThese.substring(0, j) + wantThese.substring(j + 1);
                    }
                }
                if(wantThese.length() == 0) {
                    for(int j = 0; j < dontWantThese.length(); j++) {
                        if(word.charAt(i) == dontWantThese.charAt(j)) {
                            c--;
                            break outerLoop;
                        }
                    }
                }
            }
            if(wantThese.length() == 0) {
                c++;
            }
            wantThese = chars_init;
        }
        }
        return c;
    }
}
