package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.getPowerAmtAction;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makePowerPath;

//Pay additional cost to deal more damage.

public class ManaOverloadPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(ManaOverloadPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int BaseDamage = 6;
    private static final int COST = 1;

    public ManaOverloadPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("ManaOverload.png"));

        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            for (int i = 0; i < this.amount; i++) {
                if (AbstractDungeon.player.energy.energy >= COST && action.target != null && !action.target.isDeadOrEscaped()) {
                    AbstractDungeon.player.loseEnergy(COST);
                    int Damage = BaseDamage + getPowerAmtAction.main(StrengthPower.POWER_ID);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            action.target, new DamageInfo(owner, Damage, action.damageType), AbstractGameAction.AttackEffect.BLUNT_HEAVY
                    ));
                }
            }
        }
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+ (BaseDamage + getPowerAmtAction.main(StrengthPower.POWER_ID)) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ManaOverloadPower(owner, source, amount);
    }
}
