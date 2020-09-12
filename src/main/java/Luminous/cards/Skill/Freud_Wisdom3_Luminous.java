package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.FreudAddCardAction;
import Luminous.cards.AbstractMagicCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom3_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom3_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Freud3.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int REGEN = 4;
    private static final int UPGRADE_REGEN = 2;


    // /STAT DECLARATION/
    public Freud_Wisdom3_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = REGEN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
//                p, p, new RegenPower(p, magicNumber), magicNumber
//        ));
        AbstractDungeon.actionManager.addToBottom(new HealAction(
                p, p, magicNumber
        ));

        AbstractDungeon.actionManager.addToBottom(new FreudAddCardAction(new Freud_Wisdom4_Luminous(), this.timesUpgraded, 4));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REGEN);
            initializeDescription();
        }
    }
}
