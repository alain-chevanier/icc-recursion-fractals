package unam.ciencias.icc;

public interface ProximityComparator<T> {
  boolean test(T a, T b, float epsilon);
}
