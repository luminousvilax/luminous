package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractDynamicCard;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Luminous.DefaultMod.makeCardPath;

public class Light_Spectral_Light extends AbstractMagicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Light_Spectral_Light.class.getSimpleName());
    public static final String IMG = makeCardPath("Spectral_Light.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = -1;
    private static final int DAMAGE = 7;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /STAT DECLARATION/

    public Light_Spectral_Light() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = EnergyPanel.totalCount;
        if (this.energyOnUse < count){
            this.energyOnUse = count;
        }
        if (upgraded){
            count++;
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new WhirlwindEffect(), 0.2f));
        for (int i = 0; i < count; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(
                            p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn,
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        p.energy.use(EnergyPanel.totalCount);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}