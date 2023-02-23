package me.mark.electroid.gui;

import com.megaboost.position.Location;
import me.mark.electroid.Electroid;

public interface CenteredComponent {

  Location getLocation();
  int getWidth();
  int getHeight();

  default void centerHorizontally() {
    getLocation().setX((Electroid.getInstance().getGame().getGameWidth()/2) - (getWidth()/2));
  }

  default void centerVertically() {
    getLocation().setY((Electroid.getInstance().getGame().getGameHeight()/2) - (getHeight()/2));
  }

}
