package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.getPowerAmtAction;

import static Luminous.DefaultMod.makePowerPath;

public class TrackPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(TrackPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int DMG_LIMIT = 10;
    private int dmgOfTurn;

    public TrackPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.img = ImageMaster.loadImage(makePowerPath("Track.png"));

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                this.source, this.source, this.amount
        ));
        if (AbstractDungeon.player.hasPower(CurePower.POWER_ID)) {
            addToBot(new HealAction(this.source, this.source, (this.amount / 2) * getPowerAmtAction.main(CurePower.POWER_ID)));
        }
        this.dmgOfTurn += damageAmount;
        return damageAmount;
    }

    @Override
    public void atEndOfRound(){
        if (this.dmgOfTurn < this.amount * 3) {
//            addToBot(new ReducePowerAction(owner, owner, TrackPower.POWER_ID, this.amount / 2));
//        }
//        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, TrackPower.POWER_ID));
        }
        this.dmgOfTurn = 0;
        updateDescription();
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + this.amount  + DESCRIPTIONS[1] + this.amount * 3 + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrackPower(owner, source, amount);
    }
}
