package game.utils;
import java.util.Objects;
public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public boolean precedes(Vector2D other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2D other) {
        return this.x >= other.x && this.y >= other.y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2D vector2D = (Vector2D) other;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}