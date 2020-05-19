package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.powers.FreudInvinciblePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.InvinciblePower;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom6_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom6_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int AMOUNT = 2;

    private static final int UPGRADE_AMOUNT = 1;


    // /STAT DECLARATION/
    public Freud_Wisdom6_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new FreudInvinciblePower(p, p, magicNumber), magicNumber
        ));
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