public class WordFinderTester {

    public static void main(String[] args) {
        WordFinder wf = new WordFinder();
        System.out.println("LOADING FILE ... ");
// Loads a list of words from a given text file as an absolute path
        wf.load("C:\\users\\arnav\\Downloads\\words.txt");
// OR
// Loads a list of words from a given file that is selected from a pop-up dialog window //wf.pick();
                // Prints the words in the list
        wf.printWords();
        System.out.println(wf.totalWords());
        System.out.println(wf.startsWith('M'));
        System.out.println(wf.endsWith('A'));
        System.out.println(wf.lettersLong(13));
        System.out.println(wf.longestLength());
        System.out.println(wf.startsWithAndEndsWith('C', 'S'));
        System.out.println(wf.containsExact("GEO"));
        System.out.println(wf.startsWith("ST"));
        System.out.println(wf.matchesAnyOf("WPI"));
        System.out.println(wf.hasDoubleLetters());
        System.out.println(wf.matchesAll("COMPUTER"));
        System.out.println(wf.doesNotMatchAny("RSTLN"));
        System.out.println(wf.containsThisAndNotThat("AEIOU","HTML"));
        System.out.println(wf.containsExactlyOneVowel());
        System.out.println(wf.startsWithAndContains('H', "CODE", "BINARY"));
    }
}
