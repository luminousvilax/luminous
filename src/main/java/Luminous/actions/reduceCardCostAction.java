package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class reduceCardCostAction extends AbstractGameAction {

    private int amount = 0;
    private CardGroup cardGroup;

    public reduceCardCostAction(CardGroup group, int amount){
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.amount = amount;
        this.cardGroup = group;
    }

    public void update() {
        for (AbstractCard c : cardGroup.group)
            c.setCostForTurn(c.costForTurn-this.amount);
        this.isDone = true;
    }

}
