package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import Crowd_Pressure_AGH.SimulationTypes;
import Crowd_Pressure_AGH.builders.MapFactory;
import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.Position;
import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.person.Person;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.model.person.StaticInfo;
import Crowd_Pressure_AGH.model.person.VariableInfo;
import org.junit.Test;

public class CollisionDistanceTest {

    @Test
    public void getCollistionDistanceValueTest() throws AngleOutOfRangeException {
        List<Person> people = new ArrayList<>();
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap(SimulationTypes.SYM_EMPTY);
        Environment environment = new Environment(people, map);
        StaticInfo staticInfo1 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo1 = new VariableInfo(1, new Position(1, 1), new Position(11, 8));
        StaticInfo staticInfo2 = new StaticInfo(2, 640, 1, 1, 1, 1);
        VariableInfo variableInfo2 = new VariableInfo(1, new Position(1, 1), new Position(7, 15));
        people.addAll(Arrays.asList(
                new Person(new PersonInformation(staticInfo1, variableInfo1), environment),
                new Person(new PersonInformation(staticInfo2, variableInfo2), environment)));

        StaticInfo staticInfo0 = new StaticInfo(0, 1, 1, 0.24, 5, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(6, 5));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        CollisionDistance cd = new CollisionDistance(environment);
        double result = cd.getCollistionDistanceValue(0.25, main).getMinimumDistance();

        assertEquals(4.24, result, 0.1);
    }
}