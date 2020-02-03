package Luminous.actions;
import Luminous.DefaultMod;
import Luminous.powers.MagicPowerSystem;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import Luminous.powers.BalancePower;
import Luminous.powers.LightPower;
import Luminous.powers.DarkPower;

import java.util.ArrayList;
import java.util.Arrays;

public class MagicPowerAction {

    private static ArrayList<String> MagicPower = new ArrayList<>(Arrays.asList(LightPower.POWER_ID, DarkPower.POWER_ID, BalancePower.POWER_ID));

    public static boolean canGainMagicPower(AbstractCreature player, String PowerID){
        for (String magicPower: MagicPower){
            if (PowerID == BalancePower.POWER_ID){
                break;
            }
            if (player.hasPower(magicPower) && magicPower != PowerID){
                return false;
            }
        }
        if (PowerID == BalancePower.POWER_ID){
            return true;
        }
        else if (getPowerAmtAction.main(PowerID) < MagicPowerSystem.Balance_AMT){
            return true;
        }
        else
            return false;
    }

    public static void DarkPowerAction(String Magic){
        AbstractCard randomCard;
        if ((randomCard = getRandomMagicCardAction.main(Magic)) != null){
            randomCard.setCostForTurn(randomCard.costForTurn - 1);
        }
        else {
            randomCard =  AbstractDungeon.player.hand.getRandomCard( true);
            randomCard.setCostForTurn(randomCard.costForTurn - 1);
        }
    }

    public static boolean LightPowerAccess(AbstractCard card){
        if (card.type == AbstractCard.CardType.ATTACK &&
                (juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Light) ||
                juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Balance))){
            if (!card.purgeOnUse){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public static boolean DarkPowerAccess(AbstractCard card){
        if (card.type == AbstractCard.CardType.ATTACK ){
            if (card.freeToPlayOnce && !card.purgeOnUse){
                return true;
            }
            else if (card.isCostModifiedForTurn && card.costForTurn < card.cost){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

}
