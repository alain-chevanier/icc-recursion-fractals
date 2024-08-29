package unam.ciencias.icc;

import java.awt.Graphics;

public interface ShapeRenderer<T extends Shape> {
  void render(T shape, Point upperLeftCorner, Graphics graphics);
}
