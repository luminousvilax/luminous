package Luminous.actions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class getPowerAmtAction {
    public static int main(String powerId) {
        if (AbstractDungeon.player.hasPower(powerId))
            return (AbstractDungeon.player.getPower(powerId)).amount;
        return 0;
    }
}
