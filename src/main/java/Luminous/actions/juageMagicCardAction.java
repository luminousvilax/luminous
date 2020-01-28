package Luminous.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.regex.*;

public class juageMagicCardAction {
    private static final String patternLight = "^Luminous:Light_.*";
    private static final String patternDark = "^Luminous:Dark_.*";
    private static final String patternBalance = "^Luminous:Balance_.*";

    public static boolean isLightCard(AbstractCard card){
        if (Pattern.matches(patternLight, card.cardID) ){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isDarkCard(AbstractCard card){
        if (Pattern.matches(patternDark, card.cardID)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isBalanceCard(AbstractCard card){
        if (Pattern.matches(patternBalance, card.cardID)){
            return true;
        }
        else {
            return false;
        }
    }

}
