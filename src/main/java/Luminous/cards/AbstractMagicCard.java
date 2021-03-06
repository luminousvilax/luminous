package Luminous.cards;

import Luminous.actions.MagicPowerAction;
import Luminous.powers.LightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractMagicCard extends AbstractDefaultCard {


    public AbstractMagicCard(final String id,
                             final String img,
                             final int cost,
                             final CardType type,
                             final CardColor color,
                             final CardRarity rarity,
                             final CardTarget target
                             ) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}
