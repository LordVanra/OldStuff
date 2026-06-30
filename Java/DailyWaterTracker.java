
public class DailyWaterTracker {
    private int goal;
    private int currentAmount;

    public DailyWaterTracker(int goal) {
        this.goal = goal;
        this.currentAmount = 0;
    }

    public void addWater(int amount) {
        if (amount > 0) {
            currentAmount += amount;
        }
    }

    public int getTotal() {
        return currentAmount;
    }

    public boolean goalMet() {
        return currentAmount >= goal;
    }

    public void reset() {
        currentAmount = 0;
    }
}