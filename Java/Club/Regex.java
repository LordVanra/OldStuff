import java.util.Scanner;

enum State {
    START,
    DIGIT_1, // [3].14
    DIGIT_2, // 3[.14]
    END,
    FAIL
}

public class Regex {
    private String value;
    private State state;
    private int index;

    public Regex(String value) {
        this.value = value;
        this.state = State.START;
        this.index = 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Regex regex = new Regex(input);
        regex.run();
        scanner.close();    }

    public void run() {
        while (state != State.END && state != State.FAIL) {
            switch (state) {
                case START:
                    if (Character.isDigit(value.charAt(index))) {
                        state = State.DIGIT_1;
                    }
                    else {
                        state = State.FAIL;
                    }
                case DIGIT_1:
                    if (value.charAt(index) == '.') {
                        state = State.DIGIT_2;
                    }
                    else if (Character.isDigit(value.charAt(index))) {
                        state = State.DIGIT_1;
                    }
                    else {
                        state = State.FAIL;
                    }
                case DIGIT_2:
                    if (index == value.length()) {
                        state = State.END;
                    }
                    else if (Character.isDigit(value.charAt(index))) {
                        state = State.DIGIT_2;
                    }
                    else {
                        state = State.FAIL;
                    }
                case FAIL:
                    break;
                case END:
                    break;
            }
            index++;
        }
    }
}