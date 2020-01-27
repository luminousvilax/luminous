package Luminous.powers;

import Luminous.DefaultMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Luminous.actions.getPowerAmtAction;
import Luminous.actions.juageMagicCardAction;
import Luminous.actions.MagicPowerAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makePowerPath;


public class MagicPowerSystem extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("MagicPowerSystem");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int Balance_AMT = 2;
    private static final int Balance_TURN = 3;
    public static boolean LightThrough = false;
    public static boolean DarkThrough = false;

    public MagicPowerSystem(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.img = ImageMaster.loadImage(makePowerPath("Mirror.png"));

        updateDescription();
    }


    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)

    public void onAfterCardPlayed(final AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK && juageMagicCardAction.isLightCard(card)){
           if (MagicPowerAction.canGainLightPower(AbstractDungeon.player)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                         new LightPower(AbstractDungeon.player, 1),1));
            }
            if (getPowerAmtAction.main(LightPower.POWER_ID) >= MagicPowerSystem.Balance_AMT){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new BalancePower(AbstractDungeon.player, Balance_TURN),Balance_TURN));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        LightPower.POWER_ID));
                MagicPowerSystem.LightThrough = true;
            }
        }

        if (card.type == AbstractCard.CardType.ATTACK && juageMagicCardAction.isDarkCard(card)){
            if (MagicPowerAction.canGainDarkPower(AbstractDungeon.player)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new DarkPower(AbstractDungeon.player, 1),1));
            }
            if (getPowerAmtAction.main(DarkPower.POWER_ID) >= MagicPowerSystem.Balance_AMT){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new BalancePower(AbstractDungeon.player, Balance_TURN),Balance_TURN));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player, DarkPower.POWER_ID));
                MagicPowerSystem.DarkThrough = true;
            }
        }
    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MagicPowerSystem(owner, amount);
    }

}
