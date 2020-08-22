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
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static Luminous.DefaultMod.makeCardPath;


public class Light_Reflection extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Light_Reflection.class.getSimpleName());
    public static final String IMG = makeCardPath("Reflection.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int SECOND_MAGIC = 2;
    private static final int DAMAGE = 6;

    // /STAT DECLARATION/


    public Light_Reflection() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int decreaseDamage = magicNumber;
        if (upgraded){
            decreaseDamage = 0;
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING
        ));
        for (int i = 0; i < defaultSecondMagicNumber; i++){
            AbstractMonster monster = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(monster.hb.cX, monster.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(
                    monster, new DamageInfo(p, damage - decreaseDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING
            ));
            if (!upgraded){
                decreaseDamage += magicNumber;
            }
        }
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
