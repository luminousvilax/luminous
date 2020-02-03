package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.actions.*;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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


public class DarkPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("DarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int Left_AMT = MagicPowerSystem.Balance_AMT;

    public DarkPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("DarkPower.png"));

        updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)  {
        if (MagicPowerAction.DarkPowerAccess(card)){
            //flash();
            if (luckTestAction.main(0.5)){
                if (juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Balance)){
                    MagicPowerAction.DarkPowerAction(MagicPowerSystem.Magic_Balance);
                }
                else if(juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Dark)){
                    MagicPowerAction.DarkPowerAction(MagicPowerSystem.Magic_Dark);
                }
            }
            Left_AMT = MagicPowerSystem.Balance_AMT  - getPowerAmtAction.main(DarkPower.POWER_ID);
            updateDescription();
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + Left_AMT + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + Left_AMT + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkPower(owner, amount);
    }
}
