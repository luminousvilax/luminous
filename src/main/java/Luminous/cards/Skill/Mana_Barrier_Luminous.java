package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.MagicPowerAction;
import Luminous.cards.AbstractDynamicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import Luminous.actions.getPowerAmtAction;

import static Luminous.DefaultMod.makeCardPath;

public class Mana_Barrier_Luminous extends AbstractDynamicCard {


    public static final String ID = DefaultMod.makeID(Mana_Barrier_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Mana_Barrier.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String CANT_USE_MESSAGE = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;


    // /STAT DECLARATION/


    public Mana_Barrier_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        cantUseMessage = CANT_USE_MESSAGE;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (MagicPowerAction.costMagicPower(magicNumber)) {
            addToBot(new ApplyPowerAction(
                    p, p, new BufferPower(p, 1), 1
            ));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return getPowerAmtAction.magicPower() > magicNumber;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
