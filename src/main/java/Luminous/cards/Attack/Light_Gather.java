package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Luminous.DefaultMod.makeCardPath;

public class Light_Gather extends AbstractMagicCard {


    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID(Light_Gather.class.getSimpleName());
    public static final String IMG = makeCardPath("Gather.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final float BLOCK_RATE = 0.5f;

    // /STAT DECLARATION/


    public Light_Gather() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
        ));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                p, p, (int)(damage * BLOCK_RATE)
        ));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
