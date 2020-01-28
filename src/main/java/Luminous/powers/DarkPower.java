package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.juageMagicCardAction;
import Luminous.actions.luckTestAction;
import Luminous.actions.reduceCardCostAction;
import Luminous.actions.getRandomDarkCardAction;

import static Luminous.DefaultMod.makePowerPath;


public class DarkPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("DarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int Left_AMT = 0;

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

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)  {
        DefaultMod.logger.info("====================DarkPower正常触发=========================");
        if (card.type == AbstractCard.CardType.ATTACK && !card.purgeOnUse && juageMagicCardAction.isDarkCard(card) ){
            //flash();
            AbstractCard randomCard;
            if (luckTestAction.main(0.5)){
                if ((randomCard = getRandomDarkCardAction.main()) != null){
                    randomCard.setCostForTurn(randomCard.costForTurn - 1);
                }
                else {
                    randomCard =  AbstractDungeon.player.hand.getRandomCard( true);
                    randomCard.setCostForTurn(randomCard.costForTurn - 1);
                }
                DefaultMod.logger.info("=================触发了Dark效果==================");
            }
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkPower(owner, amount);
    }
}
