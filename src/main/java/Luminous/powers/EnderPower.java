package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.cards.Attack.Balance_Baptism;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.cards.Attack.Balance_Ender;

import static Luminous.DefaultMod.makePowerPath;

//Gain Block at end of round, as the amount of your MagicPower.

public class EnderPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(EnderPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EnderPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("Ender.png"));

        updateDescription();
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(this.ID) && this.amount == Balance_Ender.BapNumber - 1) {
            AbstractCard card = new Balance_Baptism();
            card.freeToPlayOnce = true;
            card.purgeOnUse = true;
            AbstractDungeon.player.hand.addToHand(card);
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + Balance_Ender.BapNumber + DESCRIPTIONS[1] ;
    }

    @Override
    public AbstractPower makeCopy() {
        return new EnderPower(owner, source, amount);
    }
}
