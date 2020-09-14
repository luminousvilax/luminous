package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractDynamicCard;
import Luminous.characters.luminous;
import Luminous.powers.BalancePower;
import Luminous.powers.DateOfLightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Luminous.DefaultMod.makeCardPath;

public class Date_of_Light_Luminous extends AbstractDynamicCard {


    public static final String ID = DefaultMod.makeID(Date_of_Light_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Date_of_Light.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String CANT_USE_MESSAGE = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 0;
    private static final int AMOUNT = 3;

    // /STAT DECLARATION/


    public Date_of_Light_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = DateOfLightPower.dmgAmount;
        exhaust = true;
        cantUseMessage = CANT_USE_MESSAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new DateOfLightPower(p, p, magicNumber), magicNumber
        ));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m) && AbstractDungeon.player.hasPower(BalancePower.POWER_ID) ;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
