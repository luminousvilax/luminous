package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.actions.*;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Luminous.DefaultMod.makePowerPath;


public class BalancePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("BalancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int Left_AMT = 0;
    private boolean justApplied = false;

    public BalancePower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;
        justApplied = true;

        this.img = ImageMaster.loadImage(makePowerPath("BalancePower.png"));

        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)  {
        if (MagicPowerAction.LightPowerAccess(card)){
            plusDamageAction.main(card, m, 0.5);

        }
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action){
        if (MagicPowerAction.DarkPowerAccess(card)){
            if (juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Balance)){
                MagicPowerAction.DarkPowerAction(MagicPowerSystem.Magic_Balance);
            }
            else if(juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Dark)){
                MagicPowerAction.DarkPowerAction(MagicPowerSystem.Magic_Dark);
            }
        }
    }

    public void atEndOfRound(){
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, BalancePower.POWER_ID));
        }
        else {
            if (this.amount == 1){
                if (MagicPowerSystem.LightThrough){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                            new DarkPower(owner, 1),1));
                    MagicPowerSystem.LightThrough = false;
                }
                else if (MagicPowerSystem.DarkThrough){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                            new LightPower(owner, 1),1));
                    MagicPowerSystem.DarkThrough = false;
                }
            }
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner,
                    BalancePower.POWER_ID,1));
        }
        //effect here
        updateDescription();

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BalancePower(owner, amount);
    }
}
