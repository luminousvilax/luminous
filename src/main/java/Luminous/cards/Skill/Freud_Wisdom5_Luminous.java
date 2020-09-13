package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.FreudAddCardAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.BossDamagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom5_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom5_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Freud5.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;
    private static final int AMOUNT = 6;

    private static final int UPGRADE_AMOUNT = 2;


    // /STAT DECLARATION/
    public Freud_Wisdom5_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = AMOUNT;
        setDisplayRarity(CardRarity.RARE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new BossDamagePower(p, p, magicNumber), magicNumber
        ));

        AbstractDungeon.actionManager.addToBottom(new FreudAddCardAction(new Freud_Wisdom6_Luminous(), this.timesUpgraded, 6));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
            initializeDescription();
        }
    }
}
