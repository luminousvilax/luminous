package Luminous.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class ReverseCardAction extends AbstractGameAction {
    private String cantUseMessage;

    public ReverseCardAction(String cantUseMessage) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.cantUseMessage = cantUseMessage;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.discardPile.isEmpty() || AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F,this.cantUseMessage , true));
                this.isDone = true;
                return;
            }

            if (!AbstractDungeon.player.discardPile.isEmpty() && !AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard discard = AbstractDungeon.player.discardPile.getTopCard();
                AbstractDungeon.player.discardPile.group.remove(discard);
                AbstractDungeon.getCurrRoom().souls.remove(discard);

                AbstractCard drawcard = AbstractDungeon.player.drawPile.getTopCard();

                AbstractDungeon.player.limbo.group.add(discard);
                discard.current_y = -200.0F * Settings.scale;
                discard.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                discard.target_y = (float)Settings.HEIGHT / 2.0F;
                discard.targetAngle = 0.0F;
                discard.lighten(false);
                discard.drawScale = 0.12F;
                discard.targetDrawScale = 0.75F;
                discard.applyPowers();
                this.addToTop(new UnlimboAction(discard));
                AbstractDungeon.player.drawPile.addToTop(discard);

                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(drawcard, AbstractDungeon.player.drawPile));

                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }

            this.isDone = true;
        }
    }
}
