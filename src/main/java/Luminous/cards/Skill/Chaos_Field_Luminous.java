package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractMagicCard;
import Luminous.characters.luminous;
import Luminous.powers.MagicPowerSystem;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Luminous.actions.getRandomMagicCardAction;

import java.util.ArrayList;
import java.util.Iterator;

import static Luminous.DefaultMod.makeCardPath;


public class Chaos_Field_Luminous extends AbstractMagicCard {

    public static final String ID = DefaultMod.makeID(Chaos_Field_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Chaos_Field.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 2;

    private static final int AMOUNT = 1;
    private static final int UPGRADE_AMOUNT = 1;
    // /STAT DECLARATION/


    public Chaos_Field_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList();
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard card = getRandomMagicCardAction.fromPool(MagicPowerSystem.Magic_Balance);
            card.setCostForTurn(0);
            card.purgeOnUse = true;
            list.add(card);
        }
        Iterator var = list.iterator();
        while (var.hasNext()) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard)var.next(), true));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
            initializeDescription();
        }
    }
}
