package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom4_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom4_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int STRENGTH = 1;
    private static final int DEXTERITY = 1;
    private static final int UPGRADE_STRENGTH = 1;
    private static final int UPGRADE_DEXTERITY = 1;


    // /STAT DECLARATION/
    public Freud_Wisdom4_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = STRENGTH;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = DEXTERITY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new StrengthPower(p, magicNumber), magicNumber
        ));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new DexterityPower(p, defaultSecondMagicNumber), defaultSecondMagicNumber
        ));

        Freud_Wisdom5_Luminous freud_wisdom5 = new Freud_Wisdom5_Luminous();
        if (upgraded){
            freud_wisdom5.upgrade();
        }
        AbstractDungeon.player.drawPile.addToTop(freud_wisdom5);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_STRENGTH);
            upgradeDefaultSecondMagicNumber(UPGRADE_DEXTERITY);
            initializeDescription();
        }
    }
}
