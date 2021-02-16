package Luminous.actions;

import Luminous.orbs.DarkOrb;
import Luminous.orbs.LightOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Lightning;

public class ChanvokeAction extends AbstractGameAction {
    private int amount;

    public ChanvokeAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (int i = 0;i < this.amount;i++) {
                AbstractDungeon.player.increaseMaxOrbSlots(1, false);
                if (luckTestAction.main(0.5)) {
                    addToBot((AbstractGameAction)new ChannelAction((AbstractOrb) new LightOrb(), false));
                }
                else {
                    addToBot((AbstractGameAction)new ChannelAction((AbstractOrb) new DarkOrb(), false));
                }
            }

            for (int i = 0;i < this.amount;i++) {
                addToBot((AbstractGameAction)new AnimateOrbAction(1));
                addToBot((AbstractGameAction)new EvokeOrbAction(1));
                addToBot((AbstractGameAction)new DecreaseMaxOrbAction(1));
            }

            this.isDone = true;
        }
    }
}
