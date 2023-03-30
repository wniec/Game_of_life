package game;

import game.utils.CompareVector2D;
import game.utils.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class WorldMap {
    protected TreeSet<Vector2D> cells = new TreeSet<>(new CompareVector2D());
    protected int size;
    public WorldMap(int size){
        this.size = size;
    }
    public HashMap<Vector2D, Integer> getCellsDensity(){
        HashMap<Vector2D, Integer> cellDensity= new HashMap<>();
        ArrayList<Vector2D> neighbourhood;
        int n;
        for(Vector2D cell: cells){
            neighbourhood = Neighbourhood(cell);
            for(Vector2D neighbour: neighbourhood){
                if(neighbour.precedes(new Vector2D(size,size))&&neighbour.follows(new Vector2D(0,0))){
                    if(cellDensity.containsKey(neighbour)) {
                        n = cellDensity.get(neighbour) + 1;
                        cellDensity.remove(neighbour);
                        cellDensity.put(neighbour, n);
                    }
                    else{
                        cellDensity.put(neighbour,1);
                    }
                }
            }
        }
        return cellDensity;
    }
    public ArrayList<Vector2D> getDeadCells(HashMap<Vector2D, Integer> density){
        ArrayList<Vector2D> dead = new ArrayList<>();
        for(Vector2D position:cells){
            if(!density.containsKey(position)){
                dead.add(position);
                continue;
            }
            int neigbours = density.get(position);
            if((neigbours <2 || neigbours >3)&&cells.contains(position)){
                dead.add(position);
            }
        }
        return dead;
    }
    public ArrayList<Vector2D> getNewCells(HashMap<Vector2D, Integer> density){
        ArrayList<Vector2D> newCells = new ArrayList<>();
        for(Vector2D position:density.keySet()){
            int neigbours = density.get(position);
            if(neigbours ==3&&!cells.contains(position)){
                newCells.add(position);
            }
        }
        return newCells;
    }
    public void update(ArrayList<Vector2D> deadCells,ArrayList<Vector2D>newCells){
        for(Vector2D cell : deadCells){
            cells.remove(cell);
        }
        cells.addAll(newCells);
    }

    public TreeSet<Vector2D> getCells() {
        return cells;
    }

    public void addCell(Vector2D cell) {
        this.cells.add(cell);
    }
    private ArrayList<Vector2D> Neighbourhood(Vector2D vector){
        ArrayList<Vector2D> neighbourhood = new ArrayList<>();
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                Vector2D v = new Vector2D((vector.x + i)%size, (vector.y + j)%size);
                neighbourhood.add(v);
            }
        }
        return neighbourhood;
    }
}

