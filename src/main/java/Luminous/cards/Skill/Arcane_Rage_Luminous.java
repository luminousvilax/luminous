package Luminous.cards.Skill;

import Luminous.DefaultMod;
import Luminous.cards.AbstractDynamicCard;
import Luminous.characters.luminous;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Luminous.DefaultMod.makeCardPath;

public class Arcane_Rage_Luminous extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Arcane_Rage_Luminous.class.getSimpleName());
    public static final String IMG = makeCardPath("Arcane_Rage_Luminous.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = luminous.Enums.COLOR_luminous;

    private static final int COST = 0;

    private int AMOUNT = 3;
    private static final int UPGRADE_AMOUNT = 2;
    private static final int ENERGY_AMOUNT = 2;
    private static final int Vulnerable_AMOUNT = 2;


    public Arcane_Rage_Luminous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentDrawPileCardAmount = p.drawPile.group.size();
        p.gainEnergy(ENERGY_AMOUNT);
        if (currentDrawPileCardAmount < magicNumber){
            for (int i = 0; i < magicNumber - currentDrawPileCardAmount; i++){
                AbstractCard card = p.discardPile.getRandomCard(true);
                p.discardPile.group.remove(card);
                p.drawPile.group.add(card);
            }
        }
        p.draw(magicNumber);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p,p, new VulnerablePower(p, Vulnerable_AMOUNT, false)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_AMOUNT);
            this.AMOUNT = UPGRADE_AMOUNT;
            initializeDescription();
        }
    }
}