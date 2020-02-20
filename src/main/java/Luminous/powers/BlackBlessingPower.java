package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class BlackBlessingPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(BlackBlessingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int[] ReceiveStrength;

    public BlackBlessingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        ReceiveStrength = new int[]{1, 3, 6};

        this.img = ImageMaster.loadImage(makePowerPath("BlackBlessing.png"));

        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(this.ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    owner, owner, new StrengthPower(owner, this.amount+1), this.amount+1
            ));
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentBlock && !owner.hasPower(BufferPower.POWER_ID)){
            damageAmount -= (damageAmount - owner.currentBlock) / 2;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, StrengthPower.POWER_ID, this.amount));
            if (this.amount == 0){
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner,this.owner, this.ID));
            }
        }
        return damageAmount;
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ DESCRIPTIONS[1] + ReceiveStrength[0] + DESCRIPTIONS[4] +
                          DESCRIPTIONS[2]+ ReceiveStrength[1] + DESCRIPTIONS[4] +
                          DESCRIPTIONS[3]+ ReceiveStrength[2] +DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BlackBlessingPower(owner, source, amount);
    }
}
