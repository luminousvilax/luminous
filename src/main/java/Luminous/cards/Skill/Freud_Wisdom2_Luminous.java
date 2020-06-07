package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;

import static Luminous.DefaultMod.makeCardPath;


public class Freud_Wisdom2_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Freud_Wisdom2_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int BLOCK_RETURN = 6;
    private static final int UPGRADE_BLOCK_RETURN = 2;


    // /STAT DECLARATION/
    public Freud_Wisdom2_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        baseMagicNumber = magicNumber = BLOCK_RETURN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new PlatedArmorPower(p, magicNumber), magicNumber
        ));

        Freud_Wisdom3_Luminous freud_wisdom3 = new Freud_Wisdom3_Luminous();
        if (upgraded) {
            freud_wisdom3.upgrade();
        }
        AbstractDungeon.player.drawPile.addToTop(freud_wisdom3);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BLOCK_RETURN);
            initializeDescription();
        }
    }
}
