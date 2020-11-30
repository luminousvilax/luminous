package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.actions.getPowerAmtAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.EnderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;

import static Luminous.DefaultMod.makeCardPath;


public class Balance_Baptism extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Balance_Baptism.class.getSimpleName());
    public static final String IMG = makeCardPath("Baptism.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String CANTUSEMESSAGE = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final int COST = 1;

    private static final int DAMAGE = 10;
    private static final int UPGRADED_PLUS_DAMAGE = 2;

    // /STAT DECLARATION/


    public Balance_Baptism() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        exhaust = true;
        cantUseMessage = CANTUSEMESSAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int atkAmount = getPowerAmtAction.main(EnderPower.POWER_ID) * 2 + 1;
        for (int i = 1; i <= atkAmount; i++){
            switch (i) {
                case 1: case 4:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                    ));
                    break;
                case 2: case 5:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL
                    ));
                    break;
                case 3: case 6:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                    ));
                    break;
                case 7:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY
                    ));
                    break;
                default:
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE
                    ));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, EnderPower.POWER_ID));
    }

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        return super.canUse(p, m) && AbstractDungeon.player.hasPower(EnderPower.POWER_ID) ;
//    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
