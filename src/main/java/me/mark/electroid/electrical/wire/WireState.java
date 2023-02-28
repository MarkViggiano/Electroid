package me.mark.electroid.electrical.wire;

import java.awt.Image;

public class WireState {

  private final Image asset;
  private final int rotation;

  public WireState(Image asset, int rotation) {
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
