package game;

import game.utils.CompareVector2D;
import game.utils.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class WorldMap {
    protected TreeSet<Vector2D> cells = new TreeSet<>(new CompareVector2D());
    protected int width,height;
    public WorldMap(int width,int height){
        this.width = width;
        this.height = height;
    }
    public HashMap<Vector2D, Integer> getCellsDensity(){
        HashMap<Vector2D, Integer> cellDensity= new HashMap<>();
        ArrayList<Vector2D> neighbourhood;
        int n;
        for(Vector2D cell: cells){
            neighbourhood = cell.getNeighbourhood();
            for(Vector2D neighbour: neighbourhood){
                if(neighbour.precedes(new Vector2D(width,height))&&neighbour.follows(new Vector2D(0,0))){
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
        for(Vector2D position:density.keySet()){
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
}

