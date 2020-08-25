package telnetthespire.commands.handlers;

/*
@Name("click")
class Click extends Command {

    @Override
    public boolean isAvailable() {
        return isInDungeon();
    }

    @Override
    public boolean execute(Parameters parameters) throws InvalidCommandException {
        Parameters.ClickParams click = parameters.toClickParams();
        Gdx.input.setCursorPosition((int) (click.x * Settings.scale), (int) (click.y * Settings.scale));
        InputHelper.updateFirst();
        if (click.button.equals("LEFT")) {
            InputHelper.justClickedLeft = true;
            InputHelper.isMouseDown = true;
            ReflectionHacks.setPrivateStatic(InputHelper.class, "isPrevMouseDown", true);
        } else if (click.button.equals("RIGHT")) {
            InputHelper.justClickedRight = true;
            InputHelper.isMouseDown_R = true;
            ReflectionHacks.setPrivateStatic(InputHelper.class, "isPrevMouseDown_R", true);
        }
        GameStateListener.setTimeout(click.timeout);
        return true;
    }
}
*/