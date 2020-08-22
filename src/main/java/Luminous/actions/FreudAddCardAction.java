package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FreudAddCardAction extends AbstractGameAction {
    private AbstractCard card;
    private int timesToUpgrade;
    private int times;

    public FreudAddCardAction(AbstractCard card, int times, int timesToUpgrade) {
        this.card = card;
        this.timesToUpgrade = timesToUpgrade;
        this.times = times;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.times >= this.timesToUpgrade) {
                card.upgrade();
            }
            card.timesUpgraded = times;
            card.current_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.current_y = Settings.HEIGHT / 2.0F;
            card.angle = 0.0f;
            AbstractDungeon.player.hand.moveToDeck(card, false);

            this.isDone = true;
        }
    }
}
