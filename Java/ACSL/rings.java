import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
public class rings {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int players = sc.nextInt();
        Integer[] scores = new Integer[players];
        System.out.println(players);
        sc.nextLine();
        for(int i = 0; i < players; i++){
            scores[i] = parsePlayer(sc.nextLine() + ' ');
            System.out.println(scores[i]);
        }
        Integer[] sortedScores = scores.clone();
        Arrays.sort(sortedScores, Collections.reverseOrder());
        for (Integer sortedScore : sortedScores) {
            for (int j = 0; j < scores.length; j++) {
                if (Objects.equals(scores[j], sortedScore)) {
                    System.out.println((j+1) + "-" + scores[j]);
                }
            }
        }
        sc.close();

    }
    public static int parsePlayer(String scoring){
        int runningTotal = 0;
        int ballTotal = 0;
        for(int i = 0; i < scoring.length(); i++){
            if(scoring.charAt(i) == 'A' || scoring.charAt(i) == 'R'){
                ballTotal += 1;
            }
            if(scoring.charAt(i) == 'O' || scoring.charAt(i) == 'G'){
                ballTotal += 3;
            }
            if(scoring.charAt(i) == 'B'){
                ballTotal += 6;
            }
            if(scoring.charAt(i) == '+'){
                ballTotal += 2;
            }
            if(i > 0 && scoring.charAt(i) != ' ' && scoring.charAt(i-1) != ' ' && scoring.charAt(i) != '+'){
                ballTotal += 1;
            }
            if(scoring.charAt(i) == ' '){
                runningTotal += ballTotal;
                ballTotal = 0;
            }
        }
        return runningTotal;
    }
}