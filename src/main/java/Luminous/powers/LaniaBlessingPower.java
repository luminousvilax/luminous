package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Random;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class LaniaBlessingPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(LaniaBlessingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int HealAmount = 5;
    private static int BlockAmount = 10;
    private static int StrengthAmount = 2;

    public LaniaBlessingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("LaniaBlessing.png"));

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        AbstractCreature p = AbstractDungeon.player;
        Random random = new Random();
        double lucky = random.nextDouble();
        if (lucky <= 0.33) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(
                p, p, HealAmount
            ));
        }
        else if (lucky <= 0.67) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                    p, p, BlockAmount
            ));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    p, p, new StrengthPower(p, StrengthAmount), StrengthAmount
            ));
        }
    }



    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ HealAmount + DESCRIPTIONS[1] + BlockAmount + DESCRIPTIONS[2] + StrengthAmount + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LaniaBlessingPower(owner, source, amount);
    }
}
