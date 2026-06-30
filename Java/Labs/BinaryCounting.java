
public class BinaryCounting {

    public static void main(String[] args) {
        String text = toBytes("A is for Alpha; B is for Bravo; C is for Charlie.").replace(" ", "");
        String prevText = text;
        String binaryToRemove = "0";

        while (true) {
            for (int i = 0; i < text.length() - binaryToRemove.length(); i++) {
                if (text.substring(i, i + binaryToRemove.length()).equals(binaryToRemove)) {
                    text = text.substring(0, i) + text.substring(i + binaryToRemove.length());
                    break;
                }
            }
            for (int i = text.length() - binaryToRemove.length(); i >= 0; i--) {
                if (text.substring(i, i + binaryToRemove.length()).equals(binaryToRemove)) {
                    text = text.substring(0, i) + text.substring(i + binaryToRemove.length());
                    break;
                }
            }
            if (text.equals(prevText)) {
                break;
            } else {
                prevText = text;
                binaryToRemove = incrementBinary(binaryToRemove);
            }
        }
        
        text = binaryToOctal(text);
        prevText = text;   


        String octalToRemove = "0";

        while (true) {
            for (int i = 0; i < text.length() - octalToRemove.length(); i++) {
                if (text.substring(i, i + octalToRemove.length()).equals(octalToRemove)) {
                    text = text.substring(0, i) + text.substring(i + octalToRemove.length());
                    break;
                }
            }
            for (int i = text.length() - octalToRemove.length(); i >= 0; i--) {
                if (text.substring(i, i + octalToRemove.length()).equals(octalToRemove)) {
                    text = text.substring(0, i) + text.substring(i + octalToRemove.length());
                    break;
                }
            }
            if (text.equals(prevText)) {
                break;
            } else {
                prevText = text;
                octalToRemove = incrementOctal(octalToRemove);
            }
        }

        System.out.println(octalToDecimal(octalToRemove)-1);
    }

    static String toBytes(String text) {

        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            String bin = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(bin).append(" ");
        }

        return binary.toString();
    }

    static String incrementBinary(String binary) {
        StringBuilder sb = new StringBuilder(binary);
        int i = sb.length() - 1;

        while (i >= 0 && sb.charAt(i) == '1') {
            sb.setCharAt(i, '0');
            i--;
        }

        if (i >= 0) {
            sb.setCharAt(i, '1');
        } else {
            sb.insert(0, '1');
        }

        return sb.toString();
    }

    static String incrementOctal(String octal) {
        StringBuilder sb = new StringBuilder(octal);
        int i = sb.length() - 1;

        while (i >= 0 && sb.charAt(i) == '7') {
            sb.setCharAt(i, '0');
            i--;
        }

        if (i >= 0) {
            sb.setCharAt(i, (char) (sb.charAt(i) + 1));
        } else {
            sb.insert(0, '1');
        }

        return sb.toString();
    }

    static String binaryToOctal(String text) {
        while (text.length() % 3 != 0) {
            text = "0" + text;
        }

        StringBuilder octal = new StringBuilder();
        for (int i = 0; i < text.length(); i += 3) {
            octal.append(Integer.parseInt(text.substring(i, i + 3), 2));
        }

        while (octal.charAt(0) == '0') {
            octal.deleteCharAt(0);
        }
        return octal.toString();
    }

    static int octalToDecimal(String octal) {
        return Integer.parseInt(octal, 8);
    }
}
