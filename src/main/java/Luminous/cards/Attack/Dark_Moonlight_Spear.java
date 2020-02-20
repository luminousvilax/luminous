package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.PressureVoidPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import Luminous.actions.getPowerAmtAction;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.core.pattern.HighlightConverter;

import javax.swing.text.Highlighter;

import static Luminous.DefaultMod.makeCardPath;


public class Dark_Moonlight_Spear extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Dark_Moonlight_Spear.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 2;

    private static final int DAMAGE = 15;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    // /STAT DECLARATION/


    public Dark_Moonlight_Spear() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                m, p, new VulnerablePower(m, magicNumber, false), magicNumber
        ));
        if (m.hasPower(PressureVoidPower.POWER_ID)){
//            int pressureAmount = m.getPower(PressureVoidPower.POWER_ID).amount;
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
//                    m, p, new PressureVoidPower(m, p, pressureAmount), pressureAmount
//            ));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    m, p, new WeakPower(m, magicNumber, false), magicNumber
            ));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }

}
