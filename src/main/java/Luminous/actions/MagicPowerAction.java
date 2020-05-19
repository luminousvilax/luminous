package Luminous.actions;
import Luminous.DefaultMod;
import Luminous.cards.Power.Darkness_Mastery_Luminous;
import Luminous.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Arrays;

public class MagicPowerAction {

    private static ArrayList<String> MagicPower = new ArrayList<>(Arrays.asList(LightPower.POWER_ID, DarkPower.POWER_ID, BalancePower.POWER_ID));

    public static boolean canGainMagicPower(AbstractCreature player, String PowerID){
        for (String magicPower: MagicPower){
            if (PowerID.equals(BalancePower.POWER_ID)){
                break;
            }
            if (player.hasPower(magicPower) && !magicPower.equals(PowerID)){
                return false;
            }
        }
        if (PowerID.equals(BalancePower.POWER_ID)){
            return true;
        }
        else
            return (getPowerAmtAction.main(PowerID) < MagicPowerSystem.Balance_AMT);
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
            return !card.purgeOnUse;
        }
        else {
            return false;
        }
    }

    public static boolean DarkPowerAccess(AbstractCard card){
        if (card.type == AbstractCard.CardType.ATTACK && (
                juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Dark) ||
                        juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Balance) )){
            if (card.freeToPlayOnce && !card.purgeOnUse){
                return true;
            }
            else
                return !(card.isCostModifiedForTurn && card.costForTurn < card.cost);
        }
        else {
            return false;
        }
    }

    public static boolean whenApplyMagicPower(String PowerID){
        return (PowerID.equals(LightPower.POWER_ID) || PowerID.equals(DarkPower.POWER_ID) || PowerID.equals(BalancePower.POWER_ID));
    }

    public static void toBalance(String currentPower, int turns) {
        turns += getPowerAmtAction.main(DarknessMasteryPower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new BalancePower(AbstractDungeon.player, turns), turns));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                currentPower));
    }
}
