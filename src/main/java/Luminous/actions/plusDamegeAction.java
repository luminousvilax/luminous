package Luminous.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cards.CardQueueItem;

public class plusDamegeAction {
    public static void main(AbstractCard card, AbstractMonster m, double dmgRate){
        AbstractCard tmp = card.makeStatEquivalentCopy();
        tmp.baseDamage *= dmgRate;
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
}
