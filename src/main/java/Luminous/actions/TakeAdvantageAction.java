package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class TakeAdvantageAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractMonster m;
    private AbstractGameAction abstractGameAction;
    private String cantUseMessage;

    public TakeAdvantageAction(AbstractPlayer p, AbstractMonster m, AbstractGameAction abstractGameAction, String cantUseMessage) {
        setValues((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player);
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.m = m;
        this.p = p;
        this.cantUseMessage = cantUseMessage;
        this.abstractGameAction = abstractGameAction;
    }

    public void update() {
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            AbstractDungeon.actionManager.addToBottom(abstractGameAction);
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F,this.cantUseMessage , true));
        }
        this.isDone = true;
    }
}
