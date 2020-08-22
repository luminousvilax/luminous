package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.EnderPower;
import Luminous.util.SoundEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

import static Luminous.DefaultMod.makeCardPath;


public class Balance_Ender extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Balance_Ender.class.getSimpleName());
    public static final String IMG = makeCardPath("Ender.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADED_PLUS_DAMAGE = 4;

    public static final int BapNumber = 7;

    // /STAT DECLARATION/


    public Balance_Ender() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = BapNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int plusDamage = 0;
        if (m.type == AbstractMonster.EnemyType.BOSS || m.type == AbstractMonster.EnemyType.ELITE) {
            plusDamage = (int)(baseDamage * 0.5);
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction((String) SoundEffect.Ender.getKey()));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new GoldenSlashEffect(m.hb.cX, m.hb.cY, true), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                m, new DamageInfo(p, damage + plusDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE
        ));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new EnderPower(p, p, magicNumber), magicNumber
        ));
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
