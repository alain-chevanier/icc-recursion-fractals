package unam.ciencias.icc;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageGenerator<T> {
  BufferedImage paint(T shape);
  BufferedImage paint(List<T> shape);
}
