package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class LunarTidePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(LunarTidePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int hpamount = 0;
    private static final float HPRATE = 0.7f;
    private static final int STRAMOUNT = 3;
    private static boolean isGainStr = false;

    public LunarTidePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("LunarTide.png"));

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        this.hpamount = (int)(AbstractDungeon.player.maxHealth * HPRATE);
        if (AbstractDungeon.player.currentHealth >= this.hpamount) {
            if (isGainStr) {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(
                        AbstractDungeon.player, AbstractDungeon.player, StrengthPower.POWER_ID, STRAMOUNT
                ));
                isGainStr = false;
            }
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
        else if (!isGainStr) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRAMOUNT), STRAMOUNT
            ));
            isGainStr = true;
        }
        updateDescription();
    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+ hpamount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LunarTidePower(owner, source, amount);
    }
}
