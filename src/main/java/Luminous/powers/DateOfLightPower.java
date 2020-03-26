package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static Luminous.DefaultMod.makePowerPath;

//At end of turn, Deal damage to All enemies.

public class DateOfLightPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(DateOfLightPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int dmgAmount = 12;

    public DateOfLightPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.img = ImageMaster.loadImage(makePowerPath("DateOfLight.png"));

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead){
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new LightningEffect(monster.hb.cX, monster.hb.cY), 0.1f));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(
                        monster, new DamageInfo(AbstractDungeon.player, dmgAmount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE
                ));
            }
        }
        if (this.amount == 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ dmgAmount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DateOfLightPower(owner, source, amount);
    }
}
