package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class plusDamageAction {
    public static void main(AbstractCard card, AbstractMonster m, double dmgRate){
        AbstractCard tmp = card.makeStatEquivalentCopy();
        tmp.baseDamage = (int)(tmp.baseDamage * dmgRate - getPowerAmtAction.main(StrengthPower.POWER_ID)) ;
        if (tmp.baseMagicNumber > 0){
            tmp.baseMagicNumber *= dmgRate;
        }
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = Settings.HEIGHT / 2.0F;
        tmp.freeToPlayOnce = true;
        tmp.calculateCardDamage(m);
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m));
    }

    public static void dmpPlus(AbstractCard card, AbstractMonster m, double dmgRate){
        int dmg = card.damage;
        int magicNumber = card.magicNumber;
        dmg *= dmgRate;
        magicNumber *= dmgRate;
        if (card.target == AbstractCard.CardTarget.ALL_ENEMY){
            if (magicNumber > 0){
                for (int i=0; i<magicNumber; i++){
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                            AbstractDungeon.player, DamageInfo.createDamageMatrix(dmg, true), DamageInfo.DamageType.NORMAL,
                            AbstractGameAction.AttackEffect.FIRE
                    ));
                }
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                        AbstractDungeon.player, DamageInfo.createDamageMatrix(dmg, true), DamageInfo.DamageType.NORMAL,
                        AbstractGameAction.AttackEffect.FIRE
                ));
            }
        }
        else {
            if (magicNumber > 0){
                for(int i=0; i<magicNumber; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(AbstractDungeon.player, dmg, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(
                        m, new DamageInfo(AbstractDungeon.player, dmg, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

}
