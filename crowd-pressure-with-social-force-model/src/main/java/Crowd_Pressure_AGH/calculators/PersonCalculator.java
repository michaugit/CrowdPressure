package Crowd_Pressure_AGH.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.DirectionInfo;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.MinimumDistance;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.calculators.figures.Vector;
import Crowd_Pressure_AGH.calculators.figures.VectorXY;
import Crowd_Pressure_AGH.model.Position;

/** The class calculating and storing information about an object (person) */
public class PersonCalculator {
    private static final Random rand = new Random();
    private static final int RANDOM_POOL = 10000;
    private PersonInformation personInformation;
    private DestinationDistance destinationDistanceCal;
    private CollisionDistance collisionDistanceCal;
    private CrowdPressure crowdPressureCal;
    private SocialForce socialForceCal;

    public PersonCalculator(PersonInformation personInformation, Environment environment) {
        this.personInformation = personInformation;
        this.destinationDistanceCal = new DestinationDistance();
        this.collisionDistanceCal = new CollisionDistance(environment);
        this.crowdPressureCal = new CrowdPressure();
        this.socialForceCal = new SocialForce(environment);
    }

    public DirectionInfo getDirectionInfo() throws AngleOutOfRangeException {
        List<DirectionInfo> directionInfos = getDestinationDistanceFunctionValues();

        Double destinationAngle = GeometricCalculator.calculateAngle.apply(
                personInformation.getVariableInfo().getNextPosition(),
                personInformation.getVariableInfo().getDestinationPoint()
        );

        List<DirectionInfo> freeDestinationDirections = getNoNotMovingObstaclesDirections(directionInfos.stream().filter(di -> {
                    return GeometricCalculator.angleDiff.apply(di.getAlpha(), destinationAngle) < DefaultConfig.DESTINATION_ANGLE_RANGE;
                }
        ).collect(Collectors.toList()));

        List<DirectionInfo> hasNotNotMovingObstacles = getNoNotMovingObstaclesDirections(directionInfos);

        if (!freeDestinationDirections.isEmpty()) {
//            TerminalPrinter.print(personInformation.getStaticInfo().getId() + " X "
//                    + personInformation.getVariableInfo().getVisionCenter());
            return getMinimumDestinationDistance(freeDestinationDirections);
        } else if (hasNotNotMovingObstacles.isEmpty()) {
//            TerminalPrinter.print(personInformation.getStaticInfo().getId() + " A "
//                    + personInformation.getVariableInfo().getVisionCenter());
            if (getTrueInProbability(DefaultConfig.PERCENT_PROBABILITY_OF_CHANGING_VISION_CENTER)) {
                return changeVisionCenter(directionInfos);
            } else {
                return getMinimumDestinationDistance(directionInfos);
            }
        } else if (hasNotNotMovingObstacles.size() < directionInfos.size()) {
            DirectionInfo minDist = getMinimumDestinationDistance(directionInfos);

            Optional<Double> notMovingObstacle = minDist.getCollisionDistance().getNotMovingObstacle();
            if (notMovingObstacle.isPresent()) {
                if (notMovingObstacle.get() < DefaultConfig.WALL_DISTANCE_TO_CHANGE_DIRECTION) {
//                    TerminalPrinter.print(personInformation.getStaticInfo().getId() + " Z "
//                            + personInformation.getVariableInfo().getVisionCenter());
                    return getMinimumDestinationDistance(hasNotNotMovingObstacles);
                }
            }

            if (getTrueInProbability(DefaultConfig.PERCENT_PROBABILITY_OF_COMPUTE_NOT_FOR_WALLS)) {
                return minDist;
            } else {
                return getMinimumDestinationDistance(directionInfos);
            }
        } else {
//            TerminalPrinter.print(personInformation.getStaticInfo().getId() + " C " + personInformation.getVariableInfo().getVisionCenter());
            return getMinimumDestinationDistance(directionInfos);
        }
    }

    private List<DirectionInfo> getNoNotMovingObstaclesDirections(List<DirectionInfo> directionInfos) {
        return directionInfos.stream()
                .filter(di -> !di.getCollisionDistance().getNotMovingObstacle().isPresent())
                .collect(Collectors.toList());
    }

    private boolean getTrueInProbability(int probablity) {
        int random = rand.nextInt(100);
        boolean computeNotForWalls = random < probablity;
        return computeNotForWalls;
    }

    private DirectionInfo changeVisionCenter(List<DirectionInfo> directionInfos) {
        if (DefaultConfig.CHANGE_VISION_CENTER_RANDOM) {
            return getRandomDirectionInfo();
        }
        return getMaximumCollisionDistance(directionInfos);
    }

    private DirectionInfo getRandomDirectionInfo() {
        double alpha = (double) rand.nextInt(RANDOM_POOL) / ((double) (RANDOM_POOL / 2));
        // TerminalPrinter.print("alpha: " + alpha);
        return new DirectionInfo(alpha, new MinimumDistance(Double.MAX_VALUE, Optional.empty()),
                Double.MIN_VALUE);
    }

    public DirectionInfo getMaximumCollisionDistance(List<DirectionInfo> directionInfos) {
        DirectionInfo max = Collections.max(directionInfos, new Comparator<DirectionInfo>() {
            @Override
            public int compare(DirectionInfo di1, DirectionInfo di2) {
                return di1.getCollisionDistance().getNotMovingObstacle().get()
                        .compareTo(di2.getCollisionDistance().getNotMovingObstacle().get());
            }
        });
        return max;
    }

    public DirectionInfo getMinimumDestinationDistance(List<DirectionInfo> directionInfos) {
        DirectionInfo min = Collections.min(directionInfos, new Comparator<DirectionInfo>() {
            @Override
            public int compare(DirectionInfo di1, DirectionInfo di2) {
                return di1.getDestinationDistance().compareTo(di2.getDestinationDistance());
            }
        });
        return min;
    }

    List<DirectionInfo> getDestinationDistanceFunctionValues() throws AngleOutOfRangeException {
        List<DirectionInfo> directionInfos = new ArrayList<>();
        double start = personInformation.getVariableInfo().getVisionCenter()
                - personInformation.getStaticInfo().getVisionAngle();
        double step = DefaultConfig.ANGLE_GRANULATION;
        double end = personInformation.getVariableInfo().getVisionCenter()
                + personInformation.getStaticInfo().getVisionAngle();
        double alpha;
        for (Double i = start; i <= end; i = i + step) {
            alpha = getAlpha(i);
            MinimumDistance collisionDistanceValue = collisionDistanceCal.getCollistionDistanceValue(alpha,
                    personInformation);
            double destinationDistanceValue = destinationDistanceCal.getDestinationDistanceFunction(alpha,
                    personInformation.getVariableInfo().getDestinationAngle(),
                    personInformation.getStaticInfo().getHorizontDistance(),
                    collisionDistanceValue.getMinimumDistance());
            directionInfos.add(new DirectionInfo(alpha, collisionDistanceValue, destinationDistanceValue));
        }
        // TerminalPrinter.print("DIRECTIONS: ",directionInfos.stream().filter(d ->
        // d.getCollisionDistance()<10.0).collect(Collectors.toList()));
        return directionInfos;
    }

    private double getAlpha(Double i) {
        double alpha;
        if (i < DefaultConfig.START_ANGLE) {
            alpha = 2 + i;
        } else if (i > DefaultConfig.END_ANGLE) {
            alpha = i - 2;
        } else {
            alpha = i;
        }
        return alpha;
    }

    public Position getNextPosition() throws AngleOutOfRangeException {
        Vector velocity = personInformation.getVariableInfo().getDesiredSpeed();
        Vector acceleration = personInformation.getVariableInfo().getDesiredAcceleration();

        Vector finalForce = velocity;

        if (DefaultConfig.FORCES) {

            finalForce = GeometricCalculator.addVectors(velocity, acceleration);
        }

        // TerminalPrinter.print(personInformation.getStaticInfo().getId()+" VELO ACC FF:
        // ",Arrays.asList(velocity,acceleration, finalForce));

        VectorXY shift = GeometricCalculator.changeVector(finalForce);

        double x = personInformation.getVariableInfo().getPosition().getX();
        double y = personInformation.getVariableInfo().getPosition().getY();

        return new Position(x + shift.getX(), y + shift.getY());
    }

    public Vector getDesireVelocity(double collisionDistance, double alpha, int i) {
        double result = personInformation.getStaticInfo().getComfortableSpeed();
        // TerminalPrinter.print("v: "+i,Arrays.asList(goalVelocity(),
        // collisionVelocity(collisionDistance), result));
        Vector v = new Vector(alpha, Arrays.asList(goalVelocity(), collisionVelocity(collisionDistance), result)
                .stream().mapToDouble(d -> d).min().getAsDouble());
        return v;
    }

    private double collisionVelocity(double collisionDistance) {
        return collisionDistance / personInformation.getStaticInfo().getRelaxationTime();
    }

    private double goalVelocity() {
        double distance = GeometricCalculator.distance.apply(
                personInformation.getVariableInfo().getPosition(),
                personInformation.getVariableInfo().getDestinationPoint());
        return distance / personInformation.getStaticInfo().getRelaxationTime();
    }

    public List<Vector> getForces() throws AngleOutOfRangeException {

        List<Vector> neighborsForces = socialForceCal.getForceNeighbours(personInformation);
        List<Vector> wallForces = socialForceCal.getForceWalls(personInformation);

        Vector nForce = getForcesSum(neighborsForces);
        nForce.setValue(nForce.getValue() / personInformation.getStaticInfo().getMass());

        Vector wForce = getForcesSum(wallForces);
        wForce.setValue(wForce.getValue() / personInformation.getStaticInfo().getMass());
        return Arrays.asList(nForce, wForce);
    }

    public double getForcesValue(List<Vector> forces) {
        double forceValue = 0;
        for (Vector force : forces) {
            forceValue = forceValue + force.getValue();
        }
        return forceValue;
    }

    public Vector getDesireAcceleration(Vector vdes, List<Vector> forces) throws AngleOutOfRangeException {
        Vector velocity = personInformation.getVariableInfo().getDesiredSpeed();
        //TerminalPrinter.print("VDES, VELOCITY",Arrays.asList(vdes,velocity));
        Vector acceleration = GeometricCalculator.subtractVectors(vdes, velocity);

        acceleration.setValue(acceleration.getValue() / personInformation.getStaticInfo().getRelaxationTime());
        for (Vector force : forces) {
            acceleration = GeometricCalculator.addVectors(acceleration, force);

        }

        if (acceleration.getValue() > velocity.getValue()) {
            acceleration.setValue(velocity.getValue());
        }

        if (DefaultConfig.MAX_ACCELERATION_VALUE < acceleration.getValue()) {
            acceleration.setValue(DefaultConfig.MAX_ACCELERATION_VALUE);
        }

        return acceleration;
    }

    private Vector getForcesSum(List<Vector> forces) throws AngleOutOfRangeException {
        Vector force = new Vector(Double.NaN, 0.0);
        // TerminalPrinter.print(forces);
        for (Vector f : forces) {
            // TerminalPrinter.print("FORCES",crowdPressureCalforces);
            force = GeometricCalculator.addVectors(force, f);
        }
        return force;
    }

    public double getCrowdPressure(double forcesValue) {
        // TerminalPrinter.print(personInformation.getStaticInfo().getId());
        return crowdPressureCal.getCrowdPressure(forcesValue);
    }

}
