package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Luminous.actions.reduceCardCostAction;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom1_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom1_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;

    private static final int EFFECT_CARDS = 2;
    private static final int UPGRADE_EFFECT_CARDS = 3;
    private static final int COST_DOWN = 1;

    // /STAT DECLARATION/
    public Freud_Wisdom1_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = EFFECT_CARDS;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = COST_DOWN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.HAND);
        for (int i = 0; i < magicNumber; i++) {
            cardGroup.group.add(AbstractDungeon.player.hand.getRandomCard(true));
        }
        AbstractDungeon.actionManager.addToBottom(new reduceCardCostAction(cardGroup, defaultSecondMagicNumber));

        Freud_Wisdom2_Luminous freud_wisdom2 = new Freud_Wisdom2_Luminous();
        if (upgraded){
            freud_wisdom2.upgrade();
        }
        AbstractDungeon.player.hand.addToHand(freud_wisdom2);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_EFFECT_CARDS);
            initializeDescription();
        }
    }
}
