package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.*;

import static Luminous.DefaultMod.makePowerPath;


public class LightPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("LightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int Left_AMT = MagicPowerSystem.Balance_AMT;

    public LightPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("LightPower.png"));

        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)  {
        if (MagicPowerAction.LightPowerAccess(card)){
            //DefaultMod.logger.info("==================== "+card.cardID+" 可以应用光明加成====================");
            Left_AMT = MagicPowerSystem.Balance_AMT  - getPowerAmtAction.main(LightPower.POWER_ID);
            updateDescription();
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (MagicPowerAction.LightPowerAccess(card)) {
            if (luckTestAction.main(0.5)) {
                //flash();
                damage += (int)(card.baseDamage * 0.5);
            }
        }
        return this.atDamageGive(damage, type);
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
        return new LightPower(owner, amount);
    }
}
