package me.mark.electroid.electrical;

import java.awt.Image;

public class ComponentState {

  private final Image asset;
  private final int rotation;

  public ComponentState(Image asset, int rotation) {
    this.asset = asset;
    this.rotation = rotation;
  }

  public Image getAsset() {
    return asset;
  }

  public int getRotation() {
    return rotation;
  }
}
