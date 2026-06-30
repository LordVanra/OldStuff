import java.util.ArrayList;
import java.util.Scanner;

public class bases {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int b = sc.nextInt();
        String s = sc.nextLine();

        ArrayList<Integer> s_arr = new ArrayList<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);

            int value;
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } 
            else {
                value = 10 + (c - 'A');
            }

            s_arr.add(value);
        }

        ArrayList<ArrayList<Integer>> arr = nextN(n, b, s_arr);
        for (ArrayList<Integer> a : arr) {
            for (int i : a) {
                System.out.print(i);
            }
            System.out.println();
        }

        System.out.println(mostCommonDigit(arr));
        sc.close();
    }

    public static int mostCommonDigit(ArrayList<ArrayList<Integer>> arr) {
        int[] freq = new int[16];  

        for (ArrayList<Integer> list : arr) {
            for (int d : list) {
                freq[d]++;
            }
        }
        int bestCount = 0;

        for (int i = 0; i < 16; i++) {
            if (freq[i] > bestCount) {
                bestCount = freq[i];
            }
        }

        return bestCount;
    }


    public static ArrayList<ArrayList<Integer>> nextN(int n, int b, ArrayList<Integer> s) {
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < n; i++){
            arr.add(new ArrayList<>(s));
            s.set(0, s.get(0)+1);
            int j = 0;
            while(s.get(j) == b) {
                s.set(j, 0);
                try {
                    s.set(j+1, s.get(j+1)+1);
                } catch (IndexOutOfBoundsException e) {
                    s.add(1);
                }
                j++;
            }
        }
        return arr;
    }
}
