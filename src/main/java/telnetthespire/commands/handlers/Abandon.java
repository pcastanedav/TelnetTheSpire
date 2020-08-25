package telnetthespire.commands.handlers;

/*
@Name("abandon")
class Abandon extends Command {

    static {
        Executor.registerHandler(new Abandon());
    }

    @Override
    public boolean isAvailable() {
        return !isInDungeon()
            && CardCrawlGame.characterManager.anySaveFileExists();
    }

    @Override
    public boolean execute(Parameters parameters) {

        CardCrawlGame.chosenCharacter = (CardCrawlGame.characterManager.loadChosenCharacter()).chosenClass;

        AbstractPlayer player = AbstractDungeon.player;
        AbstractPlayer.PlayerClass pClass = player.chosenClass;

        Command.logger.info("Abandoning run with " + pClass.name());

        SaveFile file = SaveAndContinue.loadSaveFile(pClass);

        if (Settings.isStandardRun()) {
            CardCrawlGame.playerPref.putInteger(pClass.name() + "_SPIRITS", file.floor_num >= 16 ? 1 : 0);
            CardCrawlGame.playerPref.flush();
        }

        SaveAndContinue.deleteSave(player);

        if (!file.is_ascension_mode)
            StatsScreen.incrementDeath(player.getCharStat());

        CardCrawlGame.mainMenuScreen.abandonedRun = true;
        CardCrawlGame.mainMenuScreen.isSettingsUp = true;
        TelnetTheSpire.mustSendGameState = true;

        return false;
    }
}

 */