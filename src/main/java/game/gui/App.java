package game.gui;

import game.SimulationEngine;
import game.WorldMap;
import game.utils.Vector2D;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.TreeSet;

public class App extends Application{
    private WorldMap map;
    private boolean started;
    private  int size;
    private SimulationEngine engine;
    private Thread engineThread;
    private GridPane gridPane;
    private Stage stage;
    private HBox hbox;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.size = Integer.parseInt(args[0]);
        started =false;
        this.map = new WorldMap(size);
        this.engine = new SimulationEngine(map);
        this.engineThread = new Thread(engine);
        engine.setMoveDelay(300);
        engine.setApp(this);
    }
    public void start(Stage primaryStage) {
        this.stage=primaryStage;
        setConfiguration();
    }
    private void setConfiguration(){
        drawMap();
        setHBox();
        VBox vbox = new VBox(hbox, gridPane);
        Scene scene = new Scene(vbox, 640, 660);
        this.stage.setScene(scene);
        this.stage.show();
        gridPane.setOnMouseClicked((EventHandler<Event>) event -> {
            MouseEvent event1 = (MouseEvent) event;
            Vector2D v = new Vector2D((int)(event1.getX()*size/640.0),(int)(event1.getY()*size/640.0));
            map.addCell(v);
            updateConfig();
        });
        stage.setOnCloseRequest(event -> engine.endSimulation());
    }
    public void update(){
        Platform.runLater(() -> {
            drawMap();
            setHBox();
            VBox vbox = new VBox(hbox, gridPane);
            Scene scene = new Scene(vbox, 640, 660);
            this.stage.setScene(scene);
            stage.setOnCloseRequest(event -> engine.endSimulation());
        });
    }
    private void updateConfig(){
        drawMap();
        setHBox();
        VBox vbox = new VBox(hbox, gridPane);
        Scene scene = new Scene(vbox, 640, 660);
        this.stage.setScene(scene);
        gridPane.setOnMouseClicked((EventHandler<Event>) event -> {
            MouseEvent event1 = (MouseEvent) event;
            Vector2D v = new Vector2D((int)(event1.getX()*size/640.0),(int)(event1.getY()*size/640.0));
            map.addCell(v);
            updateConfig();
        });
        stage.setOnCloseRequest(event -> engine.endSimulation());
    }
    private void drawMap() {
        gridPane = new GridPane();
        HBox cell;
        RowConstraints rc = new RowConstraints();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(640.0 / size);
        rc.setPercentHeight(640.0 / size);
        for(int i =0;i<size;i++){
            gridPane.getRowConstraints().add(rc);
            gridPane.getColumnConstraints().add(cc);
            for(int j = 0; j<size; j++){
                Vector2D v = new Vector2D(i,j);
                cell = cellBox(v);
                gridPane.add(cell,i,j);
            }
        }
    }
    private HBox cellBox(Vector2D cell){
        HBox hbox =new HBox();
        hbox.setPrefSize(640.0/size,640.0/size);
        TreeSet<Vector2D> cells = map.getCells();
        if(cells.contains(cell)){
            hbox.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            return hbox;
        }
        hbox.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        return hbox;
    }
    private void setHBox(){
        Button startButton = new Button("Run");
        Button stopButton = new Button("Stop");
        hbox=new HBox(startButton,stopButton);
        hbox.setAlignment(Pos.CENTER_LEFT);
        startButton.setOnAction(event -> {
            if(!started){
                engineThread.start();
                started = true;
            }
            else
                engine.unfreeze();
        });
        stopButton.setOnAction(event -> {
            engine.freeze();
            setConfiguration();
        });
    }
}