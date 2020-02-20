package Luminous.actions;

import Luminous.powers.MagicPowerSystem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import Luminous.powers.LightPower;
import Luminous.powers.DarkPower;
import Luminous.powers.BalancePower;

public class getPowerAmtAction {

    public static int main(String powerId) {
        if (AbstractDungeon.player.hasPower(powerId))
            return (AbstractDungeon.player.getPower(powerId)).amount;
        return 0;
    }

    public static int magicPower(){
        if (AbstractDungeon.player.hasPower(LightPower.POWER_ID)){
            return (AbstractDungeon.player.getPower(LightPower.POWER_ID)).amount;
        }
        else if (AbstractDungeon.player.hasPower(DarkPower.POWER_ID)){
            return (AbstractDungeon.player.getPower(DarkPower.POWER_ID)).amount;
        }
        else if (AbstractDungeon.player.hasPower(BalancePower.POWER_ID)){
            return MagicPowerSystem.MagicAmountAtBalance;
        }
        return 0;
    }

}
