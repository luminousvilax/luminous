package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.TakeAdvantageAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Luminous.DefaultMod.makeCardPath;


public class Blinding_Pillar_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Blinding_Pillar_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Blinding_Pillar.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;
    private static final String cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final int COST = 1;

    private static final int AMOUNT = 2;
    private static final int PlusAmount = 1;
    // /STAT DECLARATION/


    public Blinding_Pillar_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TakeAdvantageAction(
                p, m, new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber), cantUseMessage
        ));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(PlusAmount);
            initializeDescription();
        }
    }
}
