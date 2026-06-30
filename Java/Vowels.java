import java.util.ArrayList;

public class Vowels {
    public static void main(String[] args)
    {
        int[] vowels = countVowels("Hello Computer Science students; here is your programming problem. HAPPY CODING!");
        // Print the integer array for display purposes.
        // System.out.println(Arrays.toString(vowels));
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Computer");
        list.add("Science");
        list.add ("Science");
        RemoveDuplicates(list);
        for(String i : list)
        {
            System.out.println(i);
        }
    }

    // Accepts a String as a parameter and returns an array of integers
    // representing the counts of each vowel in the String. The array
    // returned by the method should hold 5 elements: the first is the count
    // of A’s, the second is the count of E’s, the third I’s, the fourth
    // O’s, and the fifth U’s. The String may contain both uppercase and
    // lowercase letters (case insensitive).
    public static int[] countVowels(String str) {
        int[] vowels = new int[5];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'A' || c == 'a') {
                vowels[0]++;
            } 
            else if (c == 'E' || c == 'e') {
                vowels[1]++;
            } 
            else if (c == 'I' || c == 'i') {
                vowels[2]++;
            } 
            else if (c == 'O' || c == 'o') {
                vowels[3]++;
            } 
            else if (c == 'U' || c == 'u') {
                vowels[4]++;
            }
        }
        return vowels;
    }

    public static void RemoveDuplicates(ArrayList<String> list){
        
        for(int i = 0; i < list.size() - 1; i++){
            while(i < list.size() - 1 && list.get(i).equals(list.get(i + 1))){
                list.remove(i + 1);
            }
        }
    }
}
