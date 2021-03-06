package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Luminous.DefaultMod.makeCardPath;


public class Balance_Death_Scythe extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Balance_Death_Scythe.class.getSimpleName());
    public static final String IMG = makeCardPath("Death_Scythe.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    private static final int DAMAGE = 7;
    private static final int LIMIT = 7;
    // /STAT DECLARATION/


    public Balance_Death_Scythe() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.magicNumber = 5;
        baseMagicNumber = this.magicNumber;
        this.defaultSecondMagicNumber = 10;
        defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
            //int count = (monster.maxHealth - monster.currentHealth) / defaultSecondMagicNumber;
            int count = (this.damage * 2) / defaultSecondMagicNumber;
            for (int i=0; i < count; i++){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(
                        monster,new DamageInfo(p, magicNumber, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
