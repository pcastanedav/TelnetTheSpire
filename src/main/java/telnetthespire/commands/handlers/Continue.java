package telnetthespire.commands.handlers;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import telnetthespire.GameStateListener;
import telnetthespire.commands.arguments.NoArguments;
import telnetthespire.commands.parsers.CommandParser;

import java.util.ArrayList;

public class Continue extends CommandHandler<NoArguments> {

    public Continue(CommandParser parser) {
        super(parser);
    }

    @Override
    public boolean execute() {
        int monstersSlain, elites1Slain, elites2Slain, elites3Slain, goldGained, champion, perfect, mysteryMachine;
        Boolean combo, overkill;
        float playtime;
        CardCrawlGame.loadingSave = true;
        CardCrawlGame.chosenCharacter = (CardCrawlGame.characterManager.loadChosenCharacter()).chosenClass;
        CardCrawlGame.mainMenuScreen.isFadingOut = true;
        CardCrawlGame.mainMenuScreen.fadeOutMusic();
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        ModHelper.setModsFalse();
        AbstractPlayer p = AbstractDungeon.player;
        SaveFile saveFile = SaveAndContinue.loadSaveFile(p.chosenClass);
        AbstractDungeon.loading_post_combat = false;
        Settings.seed = Long.valueOf(saveFile.seed);
        Settings.isFinalActAvailable = saveFile.is_final_act_on;
        Settings.hasRubyKey = saveFile.has_ruby_key;
        Settings.hasEmeraldKey = saveFile.has_emerald_key;
        Settings.hasSapphireKey = saveFile.has_sapphire_key;
        Settings.isDailyRun = saveFile.is_daily;
        if (Settings.isDailyRun)
            Settings.dailyDate = saveFile.daily_date;
        Settings.specialSeed = Long.valueOf(saveFile.special_seed);
        Settings.seedSet = saveFile.seed_set;
        Settings.isTrial = saveFile.is_trial;
        if (Settings.isTrial) {
            ModHelper.setTodaysMods(Settings.seed.longValue(), AbstractDungeon.player.chosenClass);
            AbstractPlayer.customMods = saveFile.custom_mods;
        } else if (Settings.isDailyRun) {
            ModHelper.setTodaysMods(Settings.specialSeed.longValue(), AbstractDungeon.player.chosenClass);
        }
        AbstractPlayer.customMods = saveFile.custom_mods;
        if (AbstractPlayer.customMods == null)
            AbstractPlayer.customMods = new ArrayList();
        p.currentHealth = saveFile.current_health;
        p.maxHealth = saveFile.max_health;
        p.gold = saveFile.gold;
        p.displayGold = p.gold;
        p.masterHandSize = saveFile.hand_size;
        p.potionSlots = saveFile.potion_slots;
        if (p.potionSlots == 0)
            p.potionSlots = 3;
        p.potions.clear();
        for (int i = 0; i < p.potionSlots; i++)
            p.potions.add(new PotionSlot(i));
        p.masterMaxOrbs = saveFile.max_orbs;
        p.energy = new EnergyManager(saveFile.red + saveFile.green + saveFile.blue);
        monstersSlain = saveFile.monsters_killed;
        elites1Slain = saveFile.elites1_killed;
        elites2Slain = saveFile.elites2_killed;
        elites3Slain = saveFile.elites3_killed;
        goldGained = saveFile.gold_gained;
        champion = saveFile.champions;
        perfect = saveFile.perfect;
        combo = saveFile.combo;
        overkill = saveFile.overkill;
        mysteryMachine = saveFile.mystery_machine;
        playtime = (float)saveFile.play_time;
        AbstractDungeon.ascensionLevel = saveFile.ascension_level;
        AbstractDungeon.isAscensionMode = saveFile.is_ascension_mode;
        p.masterDeck.clear();
        for (CardSave s : saveFile.cards) {
            logger.info(s.id + ", " + s.upgrades);
            p.masterDeck.addToTop(CardLibrary.getCopy(s.id, s.upgrades, s.misc));
        }
        Settings.isEndless = saveFile.is_endless_mode;
        int index = 0;
        p.blights.clear();
        if (saveFile.blights != null) {
            for (String b : saveFile.blights) {
                AbstractBlight blight = BlightHelper.getBlight(b);
                if (blight != null) {
                    int incrementAmount = ((Integer)saveFile.endless_increments.get(index)).intValue();
                    for (int j = 0; j < incrementAmount; j++)
                        blight.incrementUp();
                    blight.setIncrement(incrementAmount);
                    blight.instantObtain(AbstractDungeon.player, index, false);
                }
                index++;
            }
            if (saveFile.blight_counters != null) {
                index = 0;
                for (Integer integer : saveFile.blight_counters) {
                    ((AbstractBlight)p.blights.get(index)).setCounter(integer.intValue());
                    ((AbstractBlight)p.blights.get(index)).updateDescription(p.chosenClass);
                    index++;
                }
            }
        }
        p.relics.clear();
        index = 0;
        for (String s : saveFile.relics) {
            AbstractRelic r = RelicLibrary.getRelic(s).makeCopy();
            r.instantObtain(p, index, false);
            if (index < saveFile.relic_counters.size())
                r.setCounter(((Integer)saveFile.relic_counters.get(index)).intValue());
            r.updateDescription(p.chosenClass);
            index++;
        }
        index = 0;
        for (String s : saveFile.potions) {
            AbstractPotion potion = PotionHelper.getPotion(s);
            if (potion != null)
                AbstractDungeon.player.obtainPotion(index, potion);
            index++;
        }
        AbstractCard tmpCard = null;
        if (saveFile.bottled_flame != null) {
            for (AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
                if (abstractCard.cardID.equals(saveFile.bottled_flame)) {
                    tmpCard = abstractCard;
                    if (abstractCard.timesUpgraded == saveFile.bottled_flame_upgrade && abstractCard.misc == saveFile.bottled_flame_misc)
                        break;
                }
            }
            if (tmpCard != null) {
                tmpCard.inBottleFlame = true;
                ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).card = tmpCard;
                ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).setDescriptionAfterLoading();
            }
        }
        tmpCard = null;
        if (saveFile.bottled_lightning != null) {
            for (AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
                if (abstractCard.cardID.equals(saveFile.bottled_lightning)) {
                    tmpCard = abstractCard;
                    if (abstractCard.timesUpgraded == saveFile.bottled_lightning_upgrade && abstractCard.misc == saveFile.bottled_lightning_misc)
                        break;
                }
            }
            if (tmpCard != null) {
                tmpCard.inBottleLightning = true;
                ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).card = tmpCard;
                ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).setDescriptionAfterLoading();
            }
        }
        tmpCard = null;
        if (saveFile.bottled_tornado != null) {
            for (AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
                if (abstractCard.cardID.equals(saveFile.bottled_tornado)) {
                    tmpCard = abstractCard;
                    if (abstractCard.timesUpgraded == saveFile.bottled_tornado_upgrade && abstractCard.misc == saveFile.bottled_tornado_misc)
                        break;
                }
            }
            if (tmpCard != null) {
                tmpCard.inBottleTornado = true;
                ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).card = tmpCard;
                ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).setDescriptionAfterLoading();
            }
        }
        if (saveFile.daily_mods != null && saveFile.daily_mods.size() > 0)
            ModHelper.setMods(saveFile.daily_mods);
        MetricData metricData = new MetricData();
        metricData.clearData();
        metricData.campfire_rested = saveFile.metric_campfire_rested;
        metricData.campfire_upgraded = saveFile.metric_campfire_upgraded;
        metricData.purchased_purges = saveFile.metric_purchased_purges;
        metricData.potions_floor_spawned = saveFile.metric_potions_floor_spawned;
        metricData.current_hp_per_floor = saveFile.metric_current_hp_per_floor;
        metricData.max_hp_per_floor = saveFile.metric_max_hp_per_floor;
        metricData.gold_per_floor = saveFile.metric_gold_per_floor;
        metricData.path_per_floor = saveFile.metric_path_per_floor;
        metricData.path_taken = saveFile.metric_path_taken;
        metricData.items_purchased = saveFile.metric_items_purchased;
        metricData.items_purged = saveFile.metric_items_purged;
        metricData.card_choices = saveFile.metric_card_choices;
        metricData.event_choices = saveFile.metric_event_choices;
        metricData.damage_taken = saveFile.metric_damage_taken;
        metricData.boss_relics = saveFile.metric_boss_relics;
        if (saveFile.metric_potions_obtained != null)
            metricData.potions_obtained = saveFile.metric_potions_obtained;
        if (saveFile.metric_relics_obtained != null)
            metricData.relics_obtained = saveFile.metric_relics_obtained;
        if (saveFile.metric_campfire_choices != null)
            metricData.campfire_choices = saveFile.metric_campfire_choices;
        if (saveFile.metric_item_purchase_floors != null)
            metricData.item_purchase_floors = saveFile.metric_item_purchase_floors;
        if (saveFile.metric_items_purged_floors != null)
            metricData.items_purged_floors = saveFile.metric_items_purged_floors;
        if (saveFile.neow_bonus != null)
            metricData.neowBonus = saveFile.neow_bonus;
        if (saveFile.neow_cost != null)
            metricData.neowCost = saveFile.neow_cost;
        GameStateListener.resetStateVariables();

        return false;
    }
}
