package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import Luminous.actions.getPowerAmtAction;

import static Luminous.DefaultMod.makeCardPath;


public class Balance_Aether_Conduit extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Balance_Aether_Conduit.class.getSimpleName());
    public static final String IMG = makeCardPath("Aether_Conduit.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final int COST = 2;

    private static final int DAMAGE = 10;
    private static final int UPGRADED_PLUS_DAMAGE = 5;

    // /STAT DECLARATION/


    public Balance_Aether_Conduit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 3;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int magicPowerAmount = getPowerAmtAction.magicPower();
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new DieDieDieEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                m, new DamageInfo(p, this.damage + magicPowerAmount * this.magicNumber, damageTypeForTurn),AbstractGameAction.AttackEffect.SLASH_HEAVY
        ));
    }

    public void applyPowers(){
        super.applyPowers();
        int newVal = this.magicNumber;
        int magicPowerAmount = getPowerAmtAction.magicPower();
        this.defaultSecondMagicNumber = 0;
        this.defaultBaseSecondMagicNumber = 0;
        if (magicPowerAmount > 0){
            for (int i = 0; i < magicPowerAmount; i++){
                this.defaultBaseSecondMagicNumber += newVal;
            }
        }
        if (this.defaultBaseSecondMagicNumber > 0){
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

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
