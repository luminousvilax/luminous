package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.PressureVoidPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Luminous.DefaultMod.makeCardPath;


public class Dark_Apocalypse extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Dark_Apocalypse.class.getSimpleName());
    public static final String IMG = makeCardPath("Apocalypse.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int MAGIC = 2;
    private static final int SECOND_MAGIC = 3;
    private static final int DAMAGE = 6;

    // /STAT DECLARATION/


    public Dark_Apocalypse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < defaultSecondMagicNumber; i++){
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                    p, DamageInfo.createDamageMatrix(damage, true), damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE
            ));
        }
        if (upgraded){
            for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters){
                if (!monster.isDeadOrEscaped()){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                            monster, p, new PressureVoidPower(monster, p, magicNumber), magicNumber
                    ));
                }
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
