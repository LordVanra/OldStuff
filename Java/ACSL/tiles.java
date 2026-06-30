import java.util.Arrays;
import java.util.Scanner;

public class tiles {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String start = sc.nextLine();
        String hand = sc.nextLine();
        String draw = sc.nextLine();

        while (start.length() < 4){
            start += 0;
        }

        String[] startArr = start.split("");
        String[] handArr = hand.split(" ");
        String[] drawArr = draw.split(" ");

        
        int[] rows = Arrays.stream(startArr).mapToInt(Integer::parseInt).toArray();
        int[] hands = Arrays.stream(handArr).mapToInt(Integer::parseInt).toArray();
        int[] draws = Arrays.stream(drawArr).mapToInt(Integer::parseInt).toArray();

        chooseNextMove(rows, hands);


    }
    public static int chooseNextMove(int[] rows, int[] hands){
        for (int hand:hands){
            for(int row:rows){
                if (hand == row / 10 || hand == row % 10){
                    System.out.println(hand);
                    System.out.println(row);
                    return hand;
                }
            }
        }
        return -1;
    }
}