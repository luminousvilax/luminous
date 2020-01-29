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
            if (juageMagicCardAction.isMagicCard(c,Magic)) {
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
}
