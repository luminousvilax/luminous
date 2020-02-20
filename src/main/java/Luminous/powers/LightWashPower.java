package Luminous.powers;

import Luminous.DefaultMod;
import Luminous.cards.Power.Light_Wash_Luminous;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.getPowerAmtAction;

import static Luminous.DefaultMod.makePowerPath;

//Ignore Block of monsters when attack

public class LightWashPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(LightWashPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int IgnoreBlockAmount = 0;
    private static int IgnoreBlockBaseAmount = 6;

    public LightWashPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        IgnoreBlockAmount = IgnoreBlockBaseAmount * this.amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("LightWash.png"));

        updateDescription();
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        IgnoreBlockAmount = IgnoreBlockBaseAmount * this.amount;
        updateDescription();
        if (card.type == AbstractCard.CardType.ATTACK){
            if (card.target == AbstractCard.CardTarget.ENEMY){
                if (m.currentBlock <= IgnoreBlockAmount){
                    card.damageTypeForTurn = DamageInfo.DamageType.HP_LOSS;
                }
            }
            else {
                for(AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
                    if (monster.currentBlock <= IgnoreBlockAmount){
                        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(monster, AbstractDungeon.player));
                    }
                }
            }
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0]+ IgnoreBlockAmount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LightWashPower(owner, source, amount);
    }
}
