package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.MagicPowerAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static Luminous.DefaultMod.makeCardPath;


public class Switch_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Switch_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Switch.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String upgradeDescription = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;

    private static final int AMOUNT = 5;
    // /STAT DECLARATION/


    public Switch_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MagicPowerAction.toSwitch(magicNumber, 1);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = upgradeDescription;
            initializeDescription();
        }
    }
}
