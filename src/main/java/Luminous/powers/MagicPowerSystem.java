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

import static Luminous.DefaultMod.makePowerPath;


public class MagicPowerSystem extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = DefaultMod.makeID("MagicPowerSystem");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int Balance_AMT = 9;
    public static final int Balance_TURN = 2;
    private static final int BaseMagicAmount = 1;
    public static final int MagicAmountAtBalance = 10;
    public static boolean LightThrough = false;
    public static boolean DarkThrough = false;
    public static final String Magic_Light = "Light";
    public static final String Magic_Dark = "Dark";
    public static final String Magic_Balance = "Balance";

    public MagicPowerSystem(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;
        LightThrough = false;
        DarkThrough = false;

        this.img = ImageMaster.loadImage(makePowerPath("Mirror.png"));

        updateDescription();
    }


    public void onAfterCardPlayed(final AbstractCard card) {
        int MagicAmount = BaseMagicAmount + getPowerAmtAction.main(ArcanePitchPower.POWER_ID);
        if (card.type != AbstractCard.CardType.ATTACK)
            return;
        if ((!card.purgeOnUse && juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Light)) ||
                        AbstractDungeon.player.hasPower(SympathyPower.POWER_ID)){
           if (MagicPowerAction.canGainMagicPower(AbstractDungeon.player, LightPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player,
                        new LightPower(AbstractDungeon.player, MagicAmount),MagicAmount
                ));
            }
            if (getPowerAmtAction.main(LightPower.POWER_ID) >= Balance_AMT){
                MagicPowerAction.toBalance(LightPower.POWER_ID, Balance_TURN);
                LightThrough = true;
            }
        }
        if (juageMagicCardAction.isMagicCard(card, MagicPowerSystem.Magic_Dark) ||
                        AbstractDungeon.player.hasPower(SympathyPower.POWER_ID)){
            if (MagicPowerAction.canGainMagicPower(AbstractDungeon.player, DarkPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player,
                        new DarkPower(AbstractDungeon.player, MagicAmount),MagicAmount));
            }
            if (getPowerAmtAction.main(DarkPower.POWER_ID) >= Balance_AMT){
                MagicPowerAction.toBalance(DarkPower.POWER_ID, Balance_TURN);
                DarkThrough = true;
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
