package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.PressureVoidPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static Luminous.DefaultMod.makeCardPath;


public class Dark_Void_Devour extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Dark_Void_Devour.class.getSimpleName());
    public static final String IMG = makeCardPath("Void_Devour.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 2;
    private static final int MAGIC = 5;
    private static final int SECOND_MAGIC = 3;

    // /STAT DECLARATION/


    public Dark_Void_Devour() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster.hasPower(PressureVoidPower.POWER_ID)) {
                addToBot(new DamageAction(
                        monster, new DamageInfo(p, monster.getPower(PressureVoidPower.POWER_ID).amount * magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE
                ));
            }
            else {
                addToBot(new ApplyPowerAction(monster, p, new PressureVoidPower(monster, p, defaultSecondMagicNumber), defaultSecondMagicNumber));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            this.exhaust = false;
            initializeDescription();
        }
    }
}
