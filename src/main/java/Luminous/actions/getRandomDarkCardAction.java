package Luminous.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class getRandomDarkCardAction {

    public static AbstractCard main(){
        ArrayList<AbstractCard> tmp = new ArrayList();
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while(var4.hasNext()) {
            AbstractCard c = (AbstractCard)var4.next();
            if (juageMagicCardAction.isDarkCard(c)) {
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
