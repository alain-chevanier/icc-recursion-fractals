package unam.ciencias.icc;

import java.awt.Graphics2D;

public interface ShapeRenderer<T extends Shape> {
  void render(T shape, Point2D upperLeftCorner, Graphics2D graphics);
}
