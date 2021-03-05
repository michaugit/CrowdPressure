package Crowd_Pressure_AGH;

import Crowd_Pressure_AGH.MusicPlayer.MusicPlayer;
import Crowd_Pressure_AGH.builders.PeopleFactory;
import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.map.PrimitiveWall;
import Crowd_Pressure_AGH.model.map.Wall;
import Crowd_Pressure_AGH.model.person.GroupOfPeople;
import Crowd_Pressure_AGH.model.person.Person;
import Crowd_Pressure_AGH.model.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/** The class to handle window application */
public class GUIController implements Initializable {
    private static final double COLOR_OPACITY = 1.0;
    private static final double COLOR_BLUE = 0.0;
    private int fps;
    private SimulationTypes simulationType;
    private int scaleValue;
    private Position destination;
    private Crowd_Pressure_AGH.model.map.Map map;
    private Timeline simLoop;
    private GraphicsContext gc;
    private Engine engine;
    private ArrayList<Double> wallPos = new ArrayList<>(2);
    private PeopleFactory peopleFactory;
    private TreeMap<String, GroupOfPeople> groupsOfPeople;

    //connection with FXML objects
    @FXML
    public MenuItem menuNew;
    @FXML
    public MenuItem menuQuit;
    @FXML
    public Button btnPauseStart;
    @FXML
    public Button btnNextStep;
    @FXML
    public ComboBox<String> cbAction;
    @FXML
    public ComboBox<String> cbSym;
    @FXML
    public TextField fpsInput;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Canvas canvas;
    @FXML
    public Button btnPlus;
    @FXML
    public Button btnMinus;
    @FXML
    public Label lblInfo;
    @FXML
    public ComboBox<String> cbSelectGroup;
    @FXML
    public CheckBox musicCheckBox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        peopleFactory = new PeopleFactory();
        groupsOfPeople = new TreeMap<>();

        simulationType = DefaultConfig.SYMULATION_TYPE;
        engine = SimulationInitializer.createEngine(simulationType, peopleFactory);
        map = new Crowd_Pressure_AGH.model.map.Map(engine.getEnvironment().getMap().getWalls());

        wallPos.clear();
        destination = DefaultConfig.DEFAULT_DESTINATION_POSITION;
        scaleValue = DefaultConfig.SCALE_VALUE;
        fps = DefaultConfig.INITIAL_FPS;

        cbAction.getItems().removeAll(cbAction.getItems());
        cbAction.getItems().addAll("Add Wall", "Add Person", "Add Destination", "Add another Group");
        cbAction.getSelectionModel().select("Add Person");
        TerminalPrinter.print("# Add Person #"); //to log file
        lblInfo.setText("Click to add a person");

        cbSym.getItems().removeAll(cbSym.getItems());

        for(SimulationTypes s : SimulationTypes.values()){
            cbSym.getItems().add(s.toString());
        }
        cbSym.getSelectionModel().select(simulationType.toString());

        btnPauseStart.setText("Start");

        setFpsInputListener();
        setCbSymListener();
        setCanvasListener();
        setCbActionListener();

        drawCanvasSimulation();
        drawPeople(engine.getEnvironment().getPeople());

        updateGroupList();
        drawDestination();

        initializeMusicPlayer(false); /** ON/OFF MusicPlayer: true-> music as default is on   |  false-> music as default is off  */
    }

    @FXML
    public void chooseAction() {
        TerminalPrinter.print("# " + cbAction.getValue() + " #");
    }

    @FXML
    public void chooseSym() {
        TerminalPrinter.print("# MAP SELECTED: " + cbSym.getValue() + " #");
    }

    @FXML
    public void pauseStartSim() {
        switch (simLoop.getStatus()) {
            case RUNNING:
                simLoop.pause();
                MusicPlayer.stop();
                btnPauseStart.setText("Start");
                btnNextStep.setDisable(false);
                break;
            case PAUSED:
            case STOPPED:
                simLoop.play();
                MusicPlayer.start();
                btnPauseStart.setText("Pause");
                btnNextStep.setDisable(true);
                break;
        }
    }

    @FXML
    public void getNextStep() {
        Duration duration = Duration.millis(1000 / (float) fps);
        KeyFrame frame = getNextFrame(duration);
        Timeline loop = new Timeline();
        int cycleCount = 1;
        loop.setCycleCount(cycleCount);
        loop.getKeyFrames().add(frame);
        loop.play();
    }

//    @FXML
//    public void enlargeView() {
//        scaleValue = scaleValue + 2;
//        clearCanvas();
//        drawCanvasSimulation();
//        drawDestination();
//    }
//
//    @FXML
//    public void lessenView() {
//        scaleValue = scaleValue >= 4 ? scaleValue - 2 : scaleValue;
//        clearCanvas();
//        drawCanvasSimulation();
//        drawDestination();
//    }

    @FXML
    public void exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnPauseStart.getScene().getWindow();
            stage.close();
        } else {
            e.consume();
        }
    }

    /** The function which does a hard reset */
    @FXML
    public void clearSym(ActionEvent e) {
        Parent root2 = null;
        try {
            root2 = FXMLLoader.load(Main.class.getResource("/App.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        btnPauseStart.getScene().setRoot(root2);
}

    @FXML
    public void restartSelectedSym(ActionEvent e){
        changeSymType(simulationType);
        drawDestination();
        MusicPlayer.stop();
    }

    private void drawCanvasSimulation() {
        initializeCanvas();
        buildAndSetUpSimulationLoop(this.fps);

        drawMap(map);
        drawPeople(engine.getEnvironment().getPeople());

    }

    private void initializeCanvas() {
        canvas.setHeight(760);
        canvas.setWidth(1400);
        gc = canvas.getGraphicsContext2D();
        canvas.setLayoutY(-50);
        canvas.setLayoutX(-10);
        canvas.setScaleX(1);
        canvas.setScaleY(-1);
    }

    private void setCanvasListener() {
        canvas.setOnMouseClicked(event -> {
            double posX = event.getX();
            double posY = event.getY();

            System.out.println(posX + " x " + posY);

            switch (cbAction.getSelectionModel().getSelectedIndex()) {
                case 0:
                    if (wallPos.isEmpty()) {
                        wallPos.add(0, posX);
                        wallPos.add(1, posY);
                    }
                    double posX1 = wallPos.get(0);
                    double posY1 = wallPos.get(1);
                    if ((posX1 != posX) || (posY1 != posY)) {
                        setWall(new Position(descale(posX1), descale(posY1)), new Position(descale(posX), descale(posY)));
                    } else {
                        System.out.println("Need another click to create wall...");
                    }
                    break;
                case 1:
                    setPerson(posX, posY);
                    break;
                case 2:
                    setDestination(posX, posY);

                    break;
            }
        });
    }

    private void setWall(Position pos1, Position pos2) {
        Wall newWall = new PrimitiveWall(pos1, pos2);
        List<Wall> currentWalls = map.getWalls();
        currentWalls.add(newWall);
        map = new Crowd_Pressure_AGH.model.map.Map(currentWalls);
        drawMap(map);
        TerminalPrinter.print("Wall created: start = "+ pos1.toString() + " end = " + pos2.toString());
        wallPos.clear();
    }

    private void setPerson(double posX, double posY) {
        gc.setFill(Color.BLACK);
        gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);

        String selectedGroup=cbSelectGroup.getSelectionModel().getSelectedItem();
        GroupOfPeople group= groupsOfPeople.get(selectedGroup);

        Person newP= peopleFactory.addPersonToGroup(engine.getEnvironment(), new Position(descale(posX), descale(posY)), group);

//        TerminalPrinter.print(selectedGroup + ": new Person created: (ID, Position, Destination): " +
//                        "ID = " + newP.getPersonInformation().getStaticInfo().getId() + ", " +
//                        newP.getPersonInformation().getVariableInfo().getPosition() + ", " +
//                        newP.getPersonInformation().getVariableInfo().getDestinationPoint()
//        );
        /** new style */
                TerminalPrinter.print(selectedGroup + ": new Person created: ( ID = " + newP.getPersonInformation().getStaticInfo().getId()+
                        ", Position = " + newP.getPersonInformation().getVariableInfo().getPosition() +
                        ", Destination =  " +  newP.getPersonInformation().getVariableInfo().getDestinationPoint() + " )");

    }

    private void setDestination(double posX, double posY) {
        gc.fillArc(posX, posY, 5, 5, 0, 360, ArcType.OPEN);

        String selectedGroup=cbSelectGroup.getSelectionModel().getSelectedItem();
        GroupOfPeople group= groupsOfPeople.get(selectedGroup);
        group.setDestination(new Position(descale(posX), descale(posY)));
        TerminalPrinter.print("Destination of group " + group.getID() + " set on: " + group.getDestination().toString() );
    }

    private void setCbActionListener() {
        cbAction.valueProperty().addListener((ov, old_val, new_val) -> {
            if (new_val.equals("Add Person")) {
                lblInfo.setText("Click to add a person");
            } else if(new_val.equals("Add Wall")){
                lblInfo.setText("Two clicks in different positions add a wall");
            } else if(new_val.equals("Add Destination")){
                lblInfo.setText("Click to add a Destination Point to selected group");
            } else if(new_val.equals("Add another Group")){
                addNewGroup(null);
                updateGroupList();
            }
        });
    }

    private void setFpsInputListener(){
        fpsInput.setOnAction(e -> {
            String newValue=fpsInput.getText();

            if (!newValue.toString().matches("\\d*")) {
                fpsInput.setText(newValue.toString().replaceAll("[^\\d]", ""));
            }
            if (fpsInput.getText().equals("")){
                fpsInput.setText( String.valueOf(DefaultConfig.INITIAL_FPS));
            }

            int intValue=Integer.parseInt(fpsInput.getText());

            if(intValue < 5 || intValue > 200){
                fpsInput.setText( String.valueOf(DefaultConfig.INITIAL_FPS));
            }

            fps = Integer.parseInt(fpsInput.getText());
            changeFps(fps);
        });

        fpsInput.focusedProperty().addListener((ov, old_val, new_val) -> {
            if(new_val==false){
                String newValue=fpsInput.getText();

                if (!newValue.toString().matches("\\d*")) {
                    fpsInput.setText(newValue.toString().replaceAll("[^\\d]", ""));
                }
                if (fpsInput.getText().equals("")){
                    fpsInput.setText( String.valueOf(DefaultConfig.INITIAL_FPS));
                }
                int intValue=Integer.parseInt(fpsInput.getText());

                if(intValue < 5 || intValue > 200){
                    fpsInput.setText( String.valueOf(DefaultConfig.INITIAL_FPS));
                }


                fps = Integer.parseInt(fpsInput.getText());
                changeFps(fps);
            }
        });
    }

    private void changeFps(int fps) {
        Animation.Status status = simLoop.getStatus();
        simLoop.stop();
        simLoop.getKeyFrames().clear();
        buildAndSetUpSimulationLoop(fps);
        if (status == Animation.Status.RUNNING) {
            simLoop.play();
        }
    }

    private void setCbSymListener() {
        cbSym.valueProperty().addListener((ov, old_val, new_val) -> {
            for (SimulationTypes s : SimulationTypes.values()) {
                if (s.toString().equals(new_val)) {
                    changeSymType(s);
                    drawDestination();
                    peopleFactory = new PeopleFactory();
                    break;
                }
            }
        });
    }

    private void setMusicCheckBoxListener(){
        musicCheckBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            if(new_val==true){
                MusicPlayer.turnOnPlayer();
                if(btnPauseStart.getText().equals("Pause")){
                    MusicPlayer.start();
                }
            }
            else if (new_val==false){
                MusicPlayer.stop();
                MusicPlayer.turnOffPlayer();
            }

        });
    }

    private void changeSymType(SimulationTypes symType) {
        simLoop.pause();
        MusicPlayer.stop();
        btnPauseStart.setText("Start");
        btnNextStep.setDisable(false);

        this.simulationType = symType;
        engine = SimulationInitializer.createEngine(this.simulationType, peopleFactory);
        map = engine.getEnvironment().getMap();
        List<Person> people = engine.getEnvironment().getPeople();
        if (people.size() > 0) {
            destination = engine.getEnvironment().getPeople().get(0).getPersonInformation().getVariableInfo().getDestinationPoint();
        } else {
            destination = DefaultConfig.DEFAULT_DESTINATION_POSITION;
        }
        clearCanvas();
        drawCanvasSimulation();
        updateGroupList();
    }

    private void buildAndSetUpSimulationLoop(int fps) {
        Duration duration = Duration.millis(1000 / (float) fps);
        KeyFrame frame = getNextFrame(duration);
        simLoop = new Timeline();
        int cycleCount = Animation.INDEFINITE;
        simLoop.setCycleCount(cycleCount);
        simLoop.getKeyFrames().add(frame);
    }

    private KeyFrame getNextFrame(Duration duration) {
        return new KeyFrame(duration, e -> {
            try {
                clearCanvas();
                drawMap(map);
                drawPeople(engine.getEnvironment().getPeople());
                drawDestination();
                engine.nextState();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void drawMap(Map map) {
        for (Wall w : map.getWalls()) {
            Position start = ((PrimitiveWall) w).getStartPosition();
            Position end = ((PrimitiveWall) w).getEndPosition();
            gc.strokeLine(scale(start.getX()), scale(start.getY()), scale(end.getX()), scale(end.getY()));
        }
    }

    private double descale(double value) {
        if (value == 0)
            return 0;
        return value / scaleValue;
    }

    private double scale(double value) {
        return value * scaleValue;
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }

    private void drawPeople(List<Person> people) {
        for (Person p : people) {
            Color personColor = getPersonColor(
                    p.getPersonInformation().getVariableInfo().getCrowdPressure()
            );

            gc.setFill(personColor);
            double x = scale(p.getPersonInformation().getVariableInfo().getPosition().getX());
            double y = scale(p.getPersonInformation().getVariableInfo().getPosition().getY());
            double radius = scale(p.getPersonInformation().getStaticInfo().getRadius());
            if(DefaultConfig.SHOW_VISION_RADIUS){
                double vision = scale(p.getPersonInformation().getStaticInfo().getHorizontDistance());
                gc.strokeOval(x - vision, y - vision, vision * 2, vision * 2);
            }

            gc.fillArc(x-5, y-5, 10, 10, 0, 360, ArcType.OPEN);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    private Color getPersonColor(double socialForce) {
        double maxForceToPrint = 76;
        double numerator;

        if(socialForce > maxForceToPrint){
            numerator=maxForceToPrint;
        }else{
            numerator=socialForce;
        }

        double x= numerator / maxForceToPrint;

        double red = (x > 0.5 ? 1.0 : 2 * x / 1.0);
        double green = (x > 0.5 ? 1 - 2 * (x - 0.5) / 1.0 : 1.0);

        return new Color(red, green, COLOR_BLUE, COLOR_OPACITY);
    }

    private void drawDestination() {
        double a = -1024;
        double b = -1024;
        for(java.util.Map.Entry<String, GroupOfPeople> g : groupsOfPeople.entrySet()) {

            double x = scale(g.getValue().getDestination().getX());
            double y = scale(g.getValue().getDestination().getY());
            a = x;
            b = y;
            gc.setFill(Color.BLUE);
            gc.fillArc(x, y, 10, 10, 0, 360, ArcType.ROUND);
        }
    }

    private void updateGroupList(){
        cbSelectGroup.getItems().removeAll(cbSelectGroup.getItems());
        groupsOfPeople.clear();

        if(engine.getEnvironment().getAllGroups().isEmpty()) {
            addNewGroup(null);
        }

        int iter = 1;
        for (GroupOfPeople g : engine.getEnvironment().getAllGroups()) {
            groupsOfPeople.put(new String("Group " + iter), g);
            cbSelectGroup.getItems().add("Group " + iter);
            iter++;
        }

        cbSelectGroup.getSelectionModel().select(0);
    }

    private void addNewGroup(Position destination){
        if(destination == null){
            destination= DefaultConfig.DEFAULT_DESTINATION_POSITION;
        }
        engine.getEnvironment().addGroup(new GroupOfPeople(destination));
    }

    private void initializeMusicPlayer(Boolean modeON){
        MusicPlayer.setMusic("/dejavu_meme.mp3");

        musicCheckBox.setVisible(true);
        setMusicCheckBoxListener();

        if(modeON) {
            musicCheckBox.setSelected(true);
            MusicPlayer.turnOnPlayer();
        }
        else if(!modeON){
            musicCheckBox.setSelected(false);
            MusicPlayer.turnOffPlayer();
        }
    }

}
