package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Luminous.DefaultMod.makeCardPath;


public class Accumulation_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Accumulation_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Accumulation.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String CANT_USE_MESSAGE = cardStrings.EXTENDED_DESCRIPTION[0];

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 0;

    private static final int AMOUNT = 0;
    private static final int UPGRADE_AMOUNT = 1;
    // /STAT DECLARATION/


    public Accumulation_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        cantUseMessage = CANT_USE_MESSAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int energy = EnergyPanel.getCurrentEnergy() + magicNumber;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new EnergizedPower(p, energy), energy
        ));
        if (!upgraded) {
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return (EnergyPanel.getCurrentEnergy() != 0) || this.upgraded;
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
