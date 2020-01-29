package Luminous.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import Luminous.powers.MagicPowerSystem;

import java.util.regex.*;

public class juageMagicCardAction {
    private static final String patternLight = "^Luminous:Light_.*";
    private static final String patternDark = "^Luminous:Dark_.*";
    private static final String patternBalance = "^Luminous:Balance_.*";

    public static boolean isMagicCard(AbstractCard card, String Magic){
        switch (Magic){
            case MagicPowerSystem.Magic_Light:
                if (Pattern.matches(patternLight, card.cardID) ){
                    return true;
                }
                else {
                    return false;
                }

            case MagicPowerSystem.Magic_Dark:
                if (Pattern.matches(patternDark, card.cardID)){
                    return true;
                }
                else{
                    return false;
                }

            case MagicPowerSystem.Magic_Balance:
                if (Pattern.matches(patternBalance, card.cardID)){
                    return true;
                }
                else {
                    return false;
                }

            default:
                return false;
        }
    }

}
