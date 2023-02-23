package me.mark.electroid.gui.mainmenu;

import com.megaboost.Game;
import com.megaboost.GameState;
import com.megaboost.inputs.MouseClickType;
import com.megaboost.loading.LoadingScreen;
import me.mark.electroid.Electroid;
import me.mark.electroid.loading.start.CreatePlayer;
import me.mark.electroid.loading.start.CreateWorld;

import java.util.Arrays;

public class NewCircuitButton extends MainMenuButton {

  public NewCircuitButton() {
    super("New Circuit", (int) (Electroid.getInstance().getGame().getHeight() * 0.58));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    Electroid electroid = Electroid.getInstance();
    Game game = electroid.getGame();
    game.setGameState(GameState.PLAYING);
    new LoadingScreen(game, Arrays.asList(new CreateWorld(), new CreatePlayer()));
    electroid.getMainMenu().setShown(false);
  }
}
