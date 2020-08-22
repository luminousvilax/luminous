package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

import static Luminous.DefaultMod.makePowerPath;


public class AbyssHeartPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(AbyssHeartPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbyssHeartPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("AbyssHeart.png"));

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        Iterator var1 = owner.powers.iterator();

        while (var1.hasNext()) {
            AbstractPower p = (AbstractPower) var1.next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
                        owner, source, new StrengthPower(owner, this.amount),this.amount
                ));
                break;
            }
        }
    }


    // Update the description when you apply this power.
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AbyssHeartPower(owner, source, amount);
    }
}
