package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.actions.MagicPowerAction;
import Luminous.actions.getPowerAmtAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class DarkCrescendoPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(DarkCrescendoPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int ReceiveStrengthAmount = 1;
    private static final int COUNTPOINT = 3;
    private static int MagicPowerAmount = 0;

    public DarkCrescendoPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        MagicPowerAmount = 0;

        this.img = ImageMaster.loadImage(makePowerPath("DarkCrescendo.png"));

        updateDescription();
    }



    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        ReceiveStrengthAmount *= this.amount;
        if (MagicPowerAction.whenApplyMagicPower(power.ID)){
            if (power.ID.equals(BalancePower.POWER_ID)){
                flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        owner, owner, new StrengthPower(owner, ReceiveStrengthAmount), ReceiveStrengthAmount));
            }
            else {
                MagicPowerAmount++;
                if (MagicPowerAmount == COUNTPOINT){
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                            owner, owner, new StrengthPower(owner, ReceiveStrengthAmount), ReceiveStrengthAmount));
                    MagicPowerAmount = 0;
                }
            }
        }
        updateDescription();
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ COUNTPOINT + DESCRIPTIONS[1] + ReceiveStrengthAmount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkCrescendoPower(owner, source, amount);
    }
}
