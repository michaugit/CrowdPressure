package Crowd_Pressure_AGH.builders;

import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.person.*;

import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.SimulationTypes;
import Crowd_Pressure_AGH.model.Position;

import java.util.Random;

/** The class creating people to environment */
public class PeopleFactory {
    private int id;

    public PeopleFactory(){
        id = 0;
    }

    public void addPeople(Environment environment, SimulationTypes sym) {
        if (sym.equals(SimulationTypes.SYM_ONE_WALL_TWO_GROUPS)){
            addPeopleOneWallTwoGroups(environment);
        } else if (sym.equals(SimulationTypes.SYM_ROOM_WITHOUT_OBSTACLE)){
            addPeopleToRoom(environment);
        } else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_VERTICAL_OBSTACLE)){
            addPeopleToRoom(environment);
        } else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_HORIZONTAL_OBSTACLE)) {
            addPeopleToRoom(environment);
        } else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_CORRIDOR)){
            addPeopleToRoom(environment);
        } else if (sym.equals(SimulationTypes.SYM_ROOM_WITH_CORRIDOR_AND_COLUMN)){
            addPeopleToRoom(environment);
        } else if (sym.equals(SimulationTypes.SYM_THREE_GROUPS)){
            addThreeGroups(environment);
        }

    }

    private void addPeopleOneWallTwoGroups(Environment environment) {
        Position destinationPointL = new Position(30, 40);
        Position destinationPointR = new Position(110, 40);
        int groupCount = 20;

        environment.addGroup(createGroup(environment, destinationPointL, destinationPointR, groupCount));
        environment.addGroup(createGroup(environment, destinationPointR, destinationPointL, groupCount));
    }

    private void addPeopleToRoom(Environment environment){
        Position startPoint = new Position(25,40);
        Position destinationPoint = new Position(110,40);
        int groupCount = 50;

        environment.addGroup(createGroup(environment,startPoint,destinationPoint,groupCount));
    }

    private void addThreeGroups(Environment environment){
        Position startPointGroup1 = new Position(35,45);
        Position destinationPointGroup1 = new Position(107,10);

        Position startPointGroup2 = new Position(105,45);
        Position destinationPointGroup2 = new Position(32,10);

        Position startPointGroup3 = new Position(70,15);
        Position destinationPointGroup3 = new Position(70,65);

        int groupCount = 15;

        environment.addGroup(createGroup(environment,startPointGroup1,destinationPointGroup1,groupCount));
        environment.addGroup(createGroup(environment,startPointGroup2,destinationPointGroup2,groupCount));
        environment.addGroup(createGroup(environment,startPointGroup3,destinationPointGroup3,groupCount));

    }

    private Person createPerson (int id, Environment environment, Position destinationPoint, Position position){

        StaticInfo staticInfo = new StaticInfo(id, DefaultConfig.DEFAULT_PERSON_MASS,
                DefaultConfig.DEFAULT_PERSON_COMFORTABLE_SPEED, DefaultConfig.DEFAULT_PERSON_VISION_ANGLE,
                DefaultConfig.DEFAULT_PERSON_HORIZON_DISTANCE, DefaultConfig.DEFAULT_PERSON_RELAXATION_TIME);

        VariableInfo variableInfo = new VariableInfo(destinationPoint, position);

        PersonInformation personInformation = new PersonInformation(staticInfo, variableInfo);

        return new Person(personInformation, environment);
    }

    private Person createPerson (int id, Environment environment, double visionCenter, Position destinationPoint, Position position){

        StaticInfo staticInfo = new StaticInfo(id, DefaultConfig.DEFAULT_PERSON_MASS,
                DefaultConfig.DEFAULT_PERSON_COMFORTABLE_SPEED, DefaultConfig.DEFAULT_PERSON_VISION_ANGLE,
                DefaultConfig.DEFAULT_PERSON_HORIZON_DISTANCE, DefaultConfig.DEFAULT_PERSON_RELAXATION_TIME);

        VariableInfo variableInfo = new VariableInfo(visionCenter, destinationPoint, position);

        PersonInformation personInformation = new PersonInformation(staticInfo, variableInfo);

        return new Person(personInformation, environment);
    }

    private GroupOfPeople createGroup (Environment environment, Position position, Position destinationPoint, int groupCount){
        GroupOfPeople group = new GroupOfPeople(destinationPoint);
        Random r = new Random();
        for (int i = 0; i < groupCount; i++) {
            int rx = r.nextInt(10) - 5;
            int ry = r.nextInt(10) - 5;

            Position positionR = new Position(position.getX() + rx, position.getY() + ry);
            group.addPerson(createPerson(id++, environment, group.getDestination(), positionR));
        }

        return group;
    }

    public Person addPerson (Environment environment, Position position, Position destinationPoint){
        if (destinationPoint == null) {
            destinationPoint = DefaultConfig.DEFAULT_DESTINATION_POSITION;
        }
        GroupOfPeople g = new GroupOfPeople(destinationPoint);
        Person newP = createPerson(id++, environment, destinationPoint, position);
        g.addPerson(newP);
        environment.addGroup(g);

        return newP;
    }

    public Person addPersonToGroup (Environment environment, Position position, GroupOfPeople group){
        Person newP = createPerson(id++, environment, group.getDestination(), position);
        environment.addPersonToGroup(group, newP);

        return newP;
    }

}
