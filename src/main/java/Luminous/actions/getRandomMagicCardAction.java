package Luminous.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class getRandomMagicCardAction {

    public static AbstractCard main(String Magic){
        ArrayList<AbstractCard> tmp = new ArrayList();
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while(var4.hasNext()) {
            AbstractCard c = (AbstractCard)var4.next();
            if (juageMagicCardAction.isMagicCard(c,Magic) && c.costForTurn > 0) {
                tmp.add(c);
            }
        }

        if (tmp.isEmpty()) {
            return null;
        } else {
            Collections.sort(tmp);
            return (AbstractCard)tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
        }
    }

    public static AbstractCard fromPool(String Magic) {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var2 = AbstractDungeon.srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == AbstractCard.CardType.ATTACK && juageMagicCardAction.isMagicCard(c, Magic) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = AbstractDungeon.srcUncommonCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == AbstractCard.CardType.ATTACK && juageMagicCardAction.isMagicCard(c, Magic) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = AbstractDungeon.srcRareCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == AbstractCard.CardType.ATTACK && juageMagicCardAction.isMagicCard(c, Magic) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));

    }

}
