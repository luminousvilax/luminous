package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.actions.MagicPowerAction;
import Luminous.cards.AbstractDynamicCard;
import Luminous.characters.luminous;
import Luminous.powers.BalancePower;
import Luminous.powers.DarkPower;
import Luminous.powers.LightPower;
import Luminous.powers.MagicPowerSystem;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import Luminous.actions.getPowerAmtAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Luminous.DefaultMod.makeCardPath;

public class Equalize_Luminous extends AbstractDynamicCard {


    public static final String ID = DefaultMod.makeID(Equalize_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;


    // /STAT DECLARATION/
    public Equalize_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(BalancePower.POWER_ID)){
            int amount = MagicPowerSystem.Balance_TURN - p.getPower(BalancePower.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    p, p, new BalancePower(p, amount), amount
            ));
        }
        else {
            if (p.hasPower(LightPower.POWER_ID)) {
                MagicPowerAction.toBalance(LightPower.POWER_ID, MagicPowerSystem.Balance_TURN);
                MagicPowerSystem.LightThrough = true;
            }
            else if (p.hasPower(DarkPower.POWER_ID)) {
                MagicPowerAction.toBalance(DarkPower.POWER_ID, MagicPowerSystem.Balance_TURN);
                MagicPowerSystem.DarkThrough = true;
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        p, p, new BalancePower(p, MagicPowerSystem.Balance_TURN), MagicPowerSystem.Balance_TURN
                ));
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
