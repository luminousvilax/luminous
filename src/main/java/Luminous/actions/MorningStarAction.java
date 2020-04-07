package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

public class MorningStarAction extends AbstractGameAction {
    private DamageInfo damageInfo;
    private int drawAmount;

    public MorningStarAction(AbstractCreature target, DamageInfo damageInfo, int drawAmount) {
        this.damageInfo = damageInfo;
        setValues(target, damageInfo);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1f;
        this.drawAmount = drawAmount;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new VerticalImpactEffect(this.target.hb.cX + this.target.hb.width / 4.0F, this.target.hb.cY - this.target.hb.height / 4.0F));
            this.target.damage(this.damageInfo);
            if (this.target.isDying || this.target.currentHealth <= 0) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                AbstractDungeon.player.draw(drawAmount);
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }
}
