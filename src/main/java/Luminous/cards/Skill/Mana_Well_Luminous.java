package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static Luminous.DefaultMod.makeCardPath;


public class Mana_Well_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Mana_Well_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String upgradeDescription = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 0;

    private static final int AMOUNT = 2;
    private static final int PlusAmount = 1;
    // /STAT DECLARATION/


    public Mana_Well_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = AMOUNT;
        baseMagicNumber = this.magicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(PlusAmount);
            rawDescription = upgradeDescription;
            initializeDescription();
        }
    }
}
