package Crowd_Pressure_AGH;

import java.util.function.Function;

import Crowd_Pressure_AGH.calculators.DestinationDistance;
import Crowd_Pressure_AGH.model.DestinationDistanceCalculatorInfo;
import Crowd_Pressure_AGH.model.Position;

/** The class containing default configuration constants in Simulation */
public class DefaultConfig {

    //PERSON
    public static final double DEFAULT_PERSON_MASS = 70.0;
    public static final double DEFAULT_PERSON_COMFORTABLE_SPEED = 0.14;
    public static final double DEFAULT_PERSON_VISION_ANGLE = 0.38;
    public static final double DEFAULT_PERSON_HORIZON_DISTANCE = 40;
    public static final double DEFAULT_PERSON_RELAXATION_TIME = 1;
    public static final double MASS_RADIUS_RATIO = 60.0;
    public static final double MAX_ACCELERATION_VALUE = 0.5;
    public static final Position DEFAULT_DESTINATION_POSITION = new Position(70, 37);

    //OTHER
    public static final double ANGLE_GRANULATION = 0.12;
    public static final double START_ANGLE = 0.0;
    public static final double END_ANGLE = 2.0;
    public static final double MAX_DISTANCE_TO_GOAL = 1.8;
    public static final double K_PARAMETER = 3 * Math.pow(10, 3); // 5 * Math.pow(10, 3)
    public static final double PRECISION_OF_CALCULATIONS = 0.001;
    public static final Function<DestinationDistanceCalculatorInfo, Double> DESTINATION_DISTANCE_FUNCTION = DestinationDistance.originalDDFunction;
    public static final boolean FORCES = true;
    public static final int PERCENT_PROBABILITY_OF_CHANGING_VISION_CENTER = 5;
    public static final int PERCENT_PROBABILITY_OF_COMPUTE_NOT_FOR_WALLS = 5;
    public static final boolean CHANGE_VISION_CENTER_RANDOM = true;
    public static double DESTINATION_ANGLE_RANGE = 2 * 0.1;
    public static final double  WALL_DISTANCE_TO_CHANGE_DIRECTION = 4.0;

    //SIMULATION
    public static final SimulationTypes SYMULATION_TYPE = SimulationTypes.SYM_ONE_WALL_TWO_GROUPS;
    public static final int SCALE_VALUE = 10;
    public static final boolean SHOW_VISION_RADIUS = false;
    public static final int INITIAL_FPS = 70;
}
