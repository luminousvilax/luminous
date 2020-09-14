package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.FreudAddCardAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Luminous.actions.reduceCardCostAction;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom1_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom1_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Freud1.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;

    private static final int EFFECT_CARDS = 2;
    private static final int UPGRADE_EFFECT_CARDS = 1;
    private static final int COST_DOWN = 1;

    // /STAT DECLARATION/
    public Freud_Wisdom1_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = EFFECT_CARDS;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = COST_DOWN;
        this.timesUpgraded = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.HAND);
        CardGroup hand = new CardGroup(CardGroup.CardGroupType.HAND);
        for (AbstractCard card: AbstractDungeon.player.hand.group) {
            hand.addToTop(card);
        }
        hand.removeCard(this.cardID);
        int maxHand = hand.size();
        //DefaultMod.logger.info("=========弗里德手牌 "+ hand.group.toString() + " ===========");
        while (hand.size() > 0 && cardGroup.size() < magicNumber && cardGroup.size() < maxHand) {
            AbstractCard randomCard = hand.getRandomCard(false);
            //DefaultMod.logger.info("=========弗里德1随机到了手牌 "+ randomCard.name + " ===========");
            hand.removeCard(randomCard);
            if (randomCard.costForTurn > 0) {
                cardGroup.group.add(randomCard);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new reduceCardCostAction(cardGroup, defaultSecondMagicNumber));

        AbstractDungeon.actionManager.addToBottom(new FreudAddCardAction(new Freud_Wisdom2_Luminous(), this.timesUpgraded, 2));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPGRADE_EFFECT_CARDS);
            initializeDescription();
        }
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    public boolean canUpgrade() {
        return this.timesUpgraded <= 6;
    }
}
