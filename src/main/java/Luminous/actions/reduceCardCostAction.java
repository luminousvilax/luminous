package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class reduceCardCostAction extends AbstractGameAction {

    private int amout = 0;

    public reduceCardCostAction(int amout){
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.amount = amount;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            c.setCostForTurn(c.costForTurn-this.amount);
        this.isDone = true;
    }

}
