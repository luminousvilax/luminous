package Luminous;

import Luminous.cards.Attack.*;
import Luminous.cards.Power.*;
import Luminous.cards.Skill.*;
import Luminous.relics.BlackBlessingRelic;
import Luminous.relics.MirrorRelic;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Luminous.cards.*;
import Luminous.characters.luminous;
import Luminous.events.IdentityCrisisEvent;
import Luminous.potions.PlaceholderPotion;
import Luminous.util.IDCheckDontTouchPls;
import Luminous.util.TextureLoader;
import Luminous.variables.DefaultCustomVariable;
import Luminous.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "LuminousClass" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "LuminousClass:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "LuminousClass". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class DefaultMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Luminous";
    private static final String AUTHOR = "Vilax"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color LUMINOUS_BLUE = CardHelper.getColor(3.0f,232.0f,252.0f); //color changed, lazy to change name
    //public static final Color LIGHT = CardHelper.getColor(64.0f, 70.0f, 70.0f);
//    public static final Color DARK = CardHelper.getColor(133,12,40);
//    public static final Color BALANCE = CardHelper.getColor(197, 211, 217);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "LuminousResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "LuminousResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "LuminousResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "LuminousResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "LuminousResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "LuminousResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "LuminousResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "LuminousResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "LuminousResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "LuminousResources/images/charSelect/LuminousButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "LuminousResources/images/charSelect/LuminousBG.jpg";
    public static final String THE_DEFAULT_SHOULDER_1 = "LuminousResources/images/char/LuminousCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "LuminousResources/images/char/LuminousCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "LuminousResources/images/char/LuminousCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "LuminousResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "LuminousResources/images/char/LuminousCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "LuminousResources/images/char/LuminousCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/sounds/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_luminous, INITIALIZE =================
    
    public DefaultMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("Luminous");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename LuminousResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of LuminousClass with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than LuminousClass. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + luminous.Enums.COLOR_luminous.toString());
        logger.info("==================正在注入新卡片信息=====================");
        BaseMod.addColor(luminous.Enums.COLOR_luminous, LUMINOUS_BLUE, LUMINOUS_BLUE, LUMINOUS_BLUE,
                LUMINOUS_BLUE, LUMINOUS_BLUE, LUMINOUS_BLUE, LUMINOUS_BLUE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        
        logger.info("Done creating the color");
        logger.info("================成功注入新卡片信息==================");
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DefaultMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_luminous, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + luminous.Enums.LuminousClass.toString());
        
        BaseMod.addCharacter(new luminous("the Default", luminous.Enums.LuminousClass),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, luminous.Enums.LuminousClass);
        
        receiveEditPotions();
        logger.info("Added " + luminous.Enums.LuminousClass.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.LuminousClass".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, luminous.Enums.LuminousClass);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new MirrorRelic(), luminous.Enums.COLOR_luminous);
        BaseMod.addRelicToCustomPool(new BlackBlessingRelic(), luminous.Enums.COLOR_luminous);

        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards

        //Attack
        BaseMod.addCard(new Balance_Aether_Conduit());
        BaseMod.addCard(new Balance_Baptism());
        BaseMod.addCard(new Balance_Death_Scythe());
        BaseMod.addCard(new Balance_Ender());
        BaseMod.addCard(new Dark_Abyssal_Drop());
        BaseMod.addCard(new Dark_Apocalypse());
        BaseMod.addCard(new Dark_Arcane_Pitch());
        BaseMod.addCard(new Dark_Dim_Erosion());
        BaseMod.addCard(new Dark_Extract());
        BaseMod.addCard(new Dark_Moonlight_Spear());
        BaseMod.addCard(new Dark_Morning_Star());
        BaseMod.addCard(new Dark_Pressure_Void());
        BaseMod.addCard(new Dark_Raid());
        BaseMod.addCard(new Light_Disperse());
        BaseMod.addCard(new Light_Flash_Shower());
        BaseMod.addCard(new Light_Gather());
        BaseMod.addCard(new Light_Glare());
        BaseMod.addCard(new Light_Ray_of_Redemption());
        BaseMod.addCard(new Light_Reflection());
        BaseMod.addCard(new Light_Spectral_Light());
        BaseMod.addCard(new Light_Sylvan_Lance());
        BaseMod.addCard(new Light_Track());

        //Skill
        BaseMod.addCard(new Accumulation_Luminous());
        BaseMod.addCard(new Arcane_Rage_Luminous());
        BaseMod.addCard(new Armageddon_Luminous());
        BaseMod.addCard(new Blinding_Pillar_Luminous());
        BaseMod.addCard(new Chaos_Field_Luminous());
        BaseMod.addCard(new Chase_Spell_Luminous());
        BaseMod.addCard(new Concentrate_Luminous());
        BaseMod.addCard(new Darks_Surge_Luminous());
        BaseMod.addCard(new Date_of_Light_Luminous());
        BaseMod.addCard(new Defend_Luminous());
        BaseMod.addCard(new Equalize_Luminous());
        BaseMod.addCard(new Erosion_Luminous());
        BaseMod.addCard(new Fast_Spell_Luminous());
        BaseMod.addCard(new Flash_Blink_Luminous());
        BaseMod.addCard(new Freud_Wisdom1_Luminous());    // They are derivatives
            BaseMod.addCard(new Freud_Wisdom2_Luminous());
            BaseMod.addCard(new Freud_Wisdom3_Luminous());
            BaseMod.addCard(new Freud_Wisdom4_Luminous());
            BaseMod.addCard(new Freud_Wisdom5_Luminous());
            BaseMod.addCard(new Freud_Wisdom6_Luminous());
        BaseMod.addCard(new Hero_Will_Luminous());
        BaseMod.addCard(new Lights_Protection_Luminous());
        BaseMod.addCard(new Magic_Guard_Luminous());
        BaseMod.addCard(new Mana_Well_Luminous());
        BaseMod.addCard(new Mirror_Force_Luminous());
        BaseMod.addCard(new Phantom_Treasure_Luminous());
        BaseMod.addCard(new Reverse_Spell_Luminous());
        BaseMod.addCard(new Shadow_Shell_Luminous());
        BaseMod.addCard(new Switch_Luminous());
        BaseMod.addCard(new Sympathy_Luminous());
        BaseMod.addCard(new Tune_Luminous());

        //Power
        BaseMod.addCard(new Abyss_Heart_Luminous());
        BaseMod.addCard(new Dark_Crescendo_Luminous());
        BaseMod.addCard(new Darkness_Mastery_Luminous());
        BaseMod.addCard(new Dusk_Guard_Luminous());
        BaseMod.addCard(new Ethereal_Form_Luminous());
        BaseMod.addCard(new Lania_Blessing_Luminous());
        BaseMod.addCard(new Light_Wash_Luminous());
        BaseMod.addCard(new Lunar_Tide_Luminous());
        BaseMod.addCard(new Mana_Overload_Luminous());
        BaseMod.addCard(new Photic_Meditation_Luminous());
        
        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards

        //Attack_Unlock
        UnlockTracker.unlockCard(Balance_Aether_Conduit.ID);
        UnlockTracker.unlockCard(Balance_Baptism.ID);
        UnlockTracker.unlockCard(Balance_Death_Scythe.ID);
        UnlockTracker.unlockCard(Balance_Ender.ID);
        UnlockTracker.unlockCard(Dark_Abyssal_Drop.ID);
        UnlockTracker.unlockCard(Dark_Apocalypse.ID);
        UnlockTracker.unlockCard(Dark_Arcane_Pitch.ID);
        UnlockTracker.unlockCard(Dark_Dim_Erosion.ID);
        UnlockTracker.unlockCard(Dark_Extract.ID);
        UnlockTracker.unlockCard(Dark_Moonlight_Spear.ID);
        UnlockTracker.unlockCard(Dark_Morning_Star.ID);
        UnlockTracker.unlockCard(Dark_Pressure_Void.ID);
        UnlockTracker.unlockCard(Dark_Raid.ID);
        UnlockTracker.unlockCard(Light_Disperse.ID);
        UnlockTracker.unlockCard(Light_Flash_Shower.ID);
        UnlockTracker.unlockCard(Light_Gather.ID);
        UnlockTracker.unlockCard(Light_Glare.ID);
        UnlockTracker.unlockCard(Light_Ray_of_Redemption.ID);
        UnlockTracker.unlockCard(Light_Reflection.ID);
        UnlockTracker.unlockCard(Light_Spectral_Light.ID);
        UnlockTracker.unlockCard(Light_Sylvan_Lance.ID);
        UnlockTracker.unlockCard(Light_Track.ID);

        //Skill_Unlock
        UnlockTracker.addCard(Accumulation_Luminous.ID);
        UnlockTracker.unlockCard(Arcane_Rage_Luminous.ID);
        UnlockTracker.unlockCard(Armageddon_Luminous.ID);
        UnlockTracker.unlockCard(Blinding_Pillar_Luminous.ID);
        UnlockTracker.unlockCard(Chaos_Field_Luminous.ID);
        UnlockTracker.unlockCard(Chase_Spell_Luminous.ID);
        UnlockTracker.unlockCard(Concentrate_Luminous.ID);
        UnlockTracker.unlockCard(Darks_Surge_Luminous.ID);
        UnlockTracker.unlockCard(Date_of_Light_Luminous.ID);
        UnlockTracker.unlockCard(Defend_Luminous.ID);
        UnlockTracker.unlockCard(Equalize_Luminous.ID);
        UnlockTracker.unlockCard(Erosion_Luminous.ID);
        UnlockTracker.unlockCard(Fast_Spell_Luminous.ID);
        UnlockTracker.unlockCard(Flash_Blink_Luminous.ID);
        UnlockTracker.unlockCard(Freud_Wisdom1_Luminous.ID);
            UnlockTracker.unlockCard(Freud_Wisdom2_Luminous.ID);
            UnlockTracker.unlockCard(Freud_Wisdom3_Luminous.ID);
            UnlockTracker.unlockCard(Freud_Wisdom4_Luminous.ID);
            UnlockTracker.unlockCard(Freud_Wisdom5_Luminous.ID);
            UnlockTracker.unlockCard(Freud_Wisdom6_Luminous.ID);
        UnlockTracker.unlockCard(Hero_Will_Luminous.ID);
        UnlockTracker.unlockCard(Lights_Protection_Luminous.ID);
        UnlockTracker.unlockCard(Magic_Guard_Luminous.ID);
        UnlockTracker.unlockCard(Mana_Well_Luminous.ID);
        UnlockTracker.unlockCard(Mirror_Force_Luminous.ID);
        UnlockTracker.unlockCard(Phantom_Treasure_Luminous.ID);
        UnlockTracker.unlockCard(Reverse_Spell_Luminous.ID);
        UnlockTracker.unlockCard(Shadow_Shell_Luminous.ID);
        UnlockTracker.unlockCard(Switch_Luminous.ID);
        UnlockTracker.unlockCard(Sympathy_Luminous.ID);
        UnlockTracker.unlockCard(Tune_Luminous.ID);

        //Power_Unlock
        UnlockTracker.unlockCard(Abyss_Heart_Luminous.ID);
        UnlockTracker.unlockCard(Dark_Crescendo_Luminous.ID);
        UnlockTracker.unlockCard(Darkness_Mastery_Luminous.ID);
        UnlockTracker.unlockCard(Dusk_Guard_Luminous.ID);
        UnlockTracker.unlockCard(Ethereal_Form_Luminous.ID);
        UnlockTracker.unlockCard(Lania_Blessing_Luminous.ID);
        UnlockTracker.unlockCard(Light_Wash_Luminous.ID);
        UnlockTracker.unlockCard(Lunar_Tide_Luminous.ID);
        UnlockTracker.unlockCard(Mana_Overload_Luminous.ID);
        UnlockTracker.unlockCard(Photic_Meditation_Luminous.ID);


        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        String language = null;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
                break;
        }

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/"+ language + "/DefaultMod-Orb-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        String language = null;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
                break;
        }

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/"+ language +"/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
