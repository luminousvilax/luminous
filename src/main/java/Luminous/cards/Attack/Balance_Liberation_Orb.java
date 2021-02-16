package Luminous.cards.Attack;

import Luminous.DefaultMod;
import Luminous.actions.ChanvokeAction;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.orbs.DarkOrb;
import Luminous.orbs.LightOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import Luminous.actions.luckTestAction;

import static Luminous.DefaultMod.makeCardPath;


public class Balance_Liberation_Orb extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Balance_Liberation_Orb.class.getSimpleName());
    public static final String IMG = makeCardPath("Liberation_Orbs.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 4;

    private static final int AMOUNT = 10;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = -1;

    // /STAT DECLARATION/


    public Balance_Liberation_Orb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        LightOrb.PEREFFECT = this.magicNumber;
        DarkOrb.PEREFFECT = this.magicNumber;

        addToBot(new ChanvokeAction(AMOUNT));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
