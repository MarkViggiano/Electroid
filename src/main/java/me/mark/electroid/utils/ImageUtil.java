package me.mark.electroid.utils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {

  private String path;
  private BufferedImage image;
  private final int subImageWidth;
  private final int subImageHeight;

  public ImageUtil(String path, int subImageWidth, int subImageHeight) {
    this.path = path;
    this.subImageWidth = subImageWidth;
    this.subImageHeight = subImageHeight;
    try {
      this.image = ImageIO.read(getClass().getResourceAsStream(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getSubImageHeight() {
    return subImageHeight;
  }

  public int getSubImageWidth() {
    return subImageWidth;
  }

  public Image getImageFromPosition(int x, int y) {
    return getImage().getSubimage(x * getSubImageWidth(), y * getSubImageHeight(), 50, 50);
  }

  public BufferedImage getImage() {
    return image;
  }

}
