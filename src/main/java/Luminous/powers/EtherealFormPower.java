package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.actions.MagicPowerAction;
import Luminous.actions.getPowerAmtAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class EtherealFormPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(EtherealFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int ApplyTurns = 3;
    private boolean justApplied;

    public EtherealFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;
        justApplied = true;

        this.img = ImageMaster.loadImage(makePowerPath("EtherealForm.png"));

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new EtherealFormPower(p, p, 1), 1));
        if (this.amount == ApplyTurns){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(
                    p, p, this.ID, ApplyTurns - 1
            ));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    p, p, new IntangiblePlayerPower(p, 1), 1
            ));
            this.justApplied = true;
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
       if (power.ID.equals(this.ID) && this.amount == ApplyTurns - 1){
           AbstractPlayer p = AbstractDungeon.player;
           AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(
                   p, p, this.ID, ApplyTurns - 1
           ));
           AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                   p, p, new IntangiblePlayerPower(p, 1), 1
           ));
           this.justApplied = true;
       }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + ApplyTurns + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new EtherealFormPower(owner, source, amount);
    }
}
