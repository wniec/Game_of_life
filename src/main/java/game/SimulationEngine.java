package game;


import game.gui.App;
import game.utils.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public class SimulationEngine implements Runnable{
    private int moveDelay;
    private final WorldMap map;
    private boolean isRunning =false;
    private App app;
    private boolean endSim=false;
    public SimulationEngine(WorldMap map){
        this.map=map;
    }
    @Override
    public void run(){
        this.isRunning=true;
        while (!endSim) {
            if(isRunning){
                runRound();
            }
            try {
                Thread.sleep(moveDelay);
            }catch(InterruptedException interruption){
                System.out.println("INTERRUPTION");
            }
        }
    }
    public void freeze(){
        this.isRunning=false;
    }
    public void unfreeze(){
        this.isRunning=true;
    }
    public void endSimulation() {
        this.endSim = true;
    }
    public void runRound() {
        HashMap<Vector2D,Integer> cellDensity = map.getCellsDensity();
        ArrayList<Vector2D> newCells = map.getNewCells(cellDensity);
        ArrayList<Vector2D> deadCells = map.getDeadCells(cellDensity);
        map.update(deadCells,newCells);
        app.update();
    }
    public void setMoveDelay(int delay){
        this.moveDelay=delay;
    }
    public void setApp(App app) {
        this.app=app;
    }
}
