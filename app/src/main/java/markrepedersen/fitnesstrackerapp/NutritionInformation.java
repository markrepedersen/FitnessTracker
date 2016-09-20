package markrepedersen.fitnesstrackerapp;

/**
 * Created by mark on 16-09-19.
 */
public class NutritionInformation {
    private String food;
    private int calories;
    private int lipids;
    private int carbs;
    private int protein;

    public NutritionInformation() {
    }

    public NutritionInformation(String food, int calories, int lipids, int carbs, int protein) {
        this.food = food;
        this.calories = calories;
        this.lipids = lipids;
        this.carbs = carbs;
        this.protein = protein;
    }

    public String getFood() {
        return food;
    }

    public int getCalories() {
        return calories;
    }

    public int getLipids() {
        return lipids;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setLipids(int lipids) {
        this.lipids = lipids;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
}
