package Luminous.relics;

import Luminous.DefaultMod;
import Luminous.powers.BlackBlessingPower;
import Luminous.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import Luminous.actions.getPowerAmtAction;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Luminous.DefaultMod.makeRelicOutlinePath;
import static Luminous.DefaultMod.makeRelicPath;

public class BlackBlessingRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID(BlackBlessingRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static boolean isAvailable;

    public BlackBlessingRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        isAvailable = true;
    }

    @Override
    public void atTurnStart() {
        if (isAvailable && getPowerAmtAction.main(BlackBlessingPower.POWER_ID) < 3){
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player,
                    new BlackBlessingPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1
            ));
            if (getPowerAmtAction.main(BlackBlessingPower.POWER_ID) == 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player, 1),1
                ));
            }
        }
        isAvailable = true;
    }

    @Override
    public void atBattleStart() {
        isAvailable = true;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0){
            isAvailable = false;
        }
        return damageAmount;
    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
