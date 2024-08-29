package unam.ciencias.icc;

public enum ImageFormat {
  PNG("PNG");

  private final String value;

  ImageFormat(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
