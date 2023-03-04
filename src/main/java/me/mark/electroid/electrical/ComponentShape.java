package me.mark.electroid.electrical;

import java.util.Arrays;

public record ComponentShape(int[] blockMap) {

  public int[] getBlockMap() {
    return blockMap;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ComponentShape that = (ComponentShape) o;
    return Arrays.equals(getBlockMap(), that.getBlockMap());
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(blockMap);
  }
}
