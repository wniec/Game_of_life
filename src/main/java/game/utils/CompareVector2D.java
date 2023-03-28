package game.utils;
import java.util.Comparator;
public class CompareVector2D implements Comparator<Vector2D>{
    @Override
    public int compare(Vector2D position1, Vector2D position2 ){
        if (position1.equals(position2))
            return 0;
        if (position1.x>position2.x)
            return 1;
        else if (position1.x<position2.x)
            return -1;
        else if (position1.y>position2.y)
            return 1;
        else if (position1.y<position2.y)
            return -1;
        else
            return 1;
    }
}
