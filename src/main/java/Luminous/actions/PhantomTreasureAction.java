package Luminous.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class PhantomTreasureAction extends AbstractGameAction {
    private String TEXT;
    private boolean upgraded;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private int dupeAmount = 1;
    private int selectAmount = 1;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();

    public PhantomTreasureAction(AbstractCreature source, int amount, boolean upgraded, String TEXT) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = DURATION_PER_CARD;
        this.p = AbstractDungeon.player;
        this.selectAmount = amount;
        this.TEXT = TEXT;
        this.upgraded = upgraded;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        int i;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!this.isCopyable(c)) {
                    this.cannotDuplicate.add(c);
                }
            }

            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                var1 = this.p.hand.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (this.isCopyable(c)) {
                        for(i = 0; i < this.dupeAmount; ++i) {
                            AbstractCard card = c.makeStatEquivalentCopy();
                            if (this.upgraded) {
                                card.freeToPlayOnce = true;
                            }
                            this.addToTop(new MakeTempCardInHandAction(card));
                        }

                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT, selectAmount, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                for(i = 0; i < this.dupeAmount; ++i) {
                    AbstractCard card = this.p.hand.getTopCard();
                    if (this.upgraded) {
                        card.freeToPlayOnce = true;
                    }
                    this.addToTop(new MakeTempCardInHandAction(card));
                }

                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                this.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));

                for(i = 0; i < this.dupeAmount; ++i) {
                    AbstractCard card = c.makeStatEquivalentCopy();
                    if (this.upgraded) {
                        card.freeToPlayOnce = true;
                    }
                    this.addToTop(new MakeTempCardInHandAction(card));
                }
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        Iterator var1 = this.cannotDuplicate.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    private boolean isCopyable(AbstractCard card) {
        return card.type.equals(CardType.ATTACK);
    }

}

