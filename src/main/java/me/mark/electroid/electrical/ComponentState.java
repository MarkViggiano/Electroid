package me.mark.electroid.electrical;

import java.awt.Image;

public record ComponentState(Image asset, int rotation) {

  public Image getAsset() {
    return asset;
  }

  public int getRotation() {
    return rotation;
  }
}
