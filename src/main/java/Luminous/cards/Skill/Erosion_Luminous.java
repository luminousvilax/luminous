package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractDynamicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Luminous.DefaultMod.makeCardPath;

public class Erosion_Luminous extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Erosion_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Erosion_Luminous.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;

    private final int AMOUNT = 1;
    private final int LOSE_BLOCK_AMOUT = 2;


    public Erosion_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = LOSE_BLOCK_AMOUT;
        this.isEthereal = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       int count = p.currentBlock / defaultSecondMagicNumber;
       AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(p, p));
       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
               p, p, new StrengthPower(p, magicNumber * count), magicNumber * count
       ));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}