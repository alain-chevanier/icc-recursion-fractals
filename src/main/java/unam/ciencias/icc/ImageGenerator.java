package unam.ciencias.icc;

import java.awt.image.BufferedImage;

public interface  ImageGenerator<T> {
  BufferedImage paint(T object);
