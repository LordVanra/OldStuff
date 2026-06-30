
public class TimeDisplay {
    public static void main(String[] args) {
        System.out.println("Time Given in Milliseconds: ");
        long time = -123456789l;
        if(time<0){
            throw new IllegalArgumentException("Time cannot be negative");
        }
        displayTimeGivenMilliSec(123456789);
    }

    public static void displayTimeGivenMilliSec(long timeinMilliSecs){
        int hours = (int) (timeinMilliSecs / 3600000);
        int minutes = (int) (timeinMilliSecs/ 60000) - hours * 60;
        int seconds = (int) (timeinMilliSecs/ 1000) - hours * 3600 - minutes * 60;
        int milliseconds = (int) (timeinMilliSecs) - hours * 3600000 - minutes * 60000 - seconds * 1000;
        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("Seconds: " + seconds);
        System.out.println("Milliseconds: " + milliseconds);
    }
}