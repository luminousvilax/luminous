package Luminous.actions;
import Luminous.powers.MagicPowerSystem;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
        int index = MagicPower.indexOf(PowerID);
        for (int i = 0; i != index && i < MagicPower.size(); i++){
            if (player.hasPower(MagicPower.get(i))){
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

    public static boolean canGainLightPower(AbstractCreature player){
        if (!player.hasPower(BalancePower.POWER_ID) && !player.hasPower(DarkPower.POWER_ID)
            && getPowerAmtAction.main(LightPower.POWER_ID) < MagicPowerSystem.Balance_AMT){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean canGainDarkPower(AbstractCreature player){
        if (!player.hasPower(BalancePower.POWER_ID) && !player.hasPower(LightPower.POWER_ID)
            && getPowerAmtAction.main(DarkPower.POWER_ID) < MagicPowerSystem.Balance_AMT){
            return true;
        }
        else {
            return false;
        }
    }

}
