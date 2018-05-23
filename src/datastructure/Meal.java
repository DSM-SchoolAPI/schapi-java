package datastructure;

import java.util.ArrayList;

public class Meal {
    public ArrayList<String> breakfast;
    public ArrayList<String> lunch;
    public ArrayList<String> dinner;

    public Meal() {
        this.breakfast = this.lunch = this.dinner = new ArrayList<>();
    }
}
