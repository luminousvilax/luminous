package Luminous.actions;

import java.util.Random;

public class luckTestAction {
    public static boolean main(double sucRate){
        Random random = new Random();
        double result = random.nextDouble();
        if ( sucRate > 0 && result <= sucRate){
            return true;
        }
        else {
            return false;
        }
    }
}
