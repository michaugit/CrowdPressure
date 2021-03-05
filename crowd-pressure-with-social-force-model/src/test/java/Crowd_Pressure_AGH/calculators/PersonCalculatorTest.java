package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.DirectionInfo;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.Position;
import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.map.PrimitiveWall;
import Crowd_Pressure_AGH.model.person.Person;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.model.person.StaticInfo;
import Crowd_Pressure_AGH.model.person.VariableInfo;
import org.junit.Test;

public class PersonCalculatorTest {

    @Test
    public void getDestinationDistanceFunctionValuesTest() throws AngleOutOfRangeException {
        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people, new Map(new ArrayList<>()));
        StaticInfo staticInfo1 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo1 = new VariableInfo(1, new Position(1, 1), new Position(11, 8));
        StaticInfo staticInfo2 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo2 = new VariableInfo(1, new Position(1, 1), new Position(7, 15));
        people.addAll(Arrays.asList(
                new Person(new PersonInformation(staticInfo1, variableInfo1), environment),
                new Person(new PersonInformation(staticInfo2, variableInfo2), environment)));

        StaticInfo staticInfo0 = new StaticInfo(0, 1, 1, 0.24, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(14, 11),
                new Position(6, 5));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        PersonCalculator pc = new PersonCalculator(main, environment);

        List<DirectionInfo> ddfv = pc.getDestinationDistanceFunctionValues();
    }

    @Test
    public void getDesireDirectionTest() throws AngleOutOfRangeException {

        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people, new Map(new ArrayList<>()));
        StaticInfo staticInfo1 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo1 = new VariableInfo(1, new Position(1, 1), new Position(11, 8));
        StaticInfo staticInfo2 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo2 = new VariableInfo(1, new Position(1, 1), new Position(7, 15));
        people.addAll(Arrays.asList(
                new Person(new PersonInformation(staticInfo1, variableInfo1), environment),
                new Person(new PersonInformation(staticInfo2, variableInfo2), environment)));

        StaticInfo staticInfo0 = new StaticInfo(0, 1, 1, 0.24, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(14, 17),
                new Position(6, 5));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        // cod.i(pedestrianInformation);
        PersonCalculator pc = new PersonCalculator(main, environment);

        DirectionInfo dd = pc.getDirectionInfo();
        assertEquals(0.370, dd.getAlpha(), 0.01);
    }

    @Test
    public void getDesireDirectionTest2() throws AngleOutOfRangeException {
        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people, new Map(new ArrayList<>()));
        StaticInfo staticInfo1 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo1 = new VariableInfo(1, new Position(1, 1), new Position(11, 8));
        StaticInfo staticInfo2 = new StaticInfo(1, 640, 1, 1, 1, 1);
        VariableInfo variableInfo2 = new VariableInfo(1, new Position(1, 1), new Position(7, 15));
        people.addAll(Arrays.asList(
                new Person(new PersonInformation(staticInfo1, variableInfo1), environment),
                new Person(new PersonInformation(staticInfo2, variableInfo2), environment)));

        StaticInfo staticInfo0 = new StaticInfo(0, 1, 1, 0.24, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(14, 11),
                new Position(6, 5));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        PersonCalculator pc = new PersonCalculator(main, environment);

        DirectionInfo dd = pc.getDirectionInfo();
    }

    @Test
    public void getDesireDirectionTest3() throws AngleOutOfRangeException {
        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people,
                new Map(Arrays.asList(new PrimitiveWall(new Position(5, 2), new Position(2.5, 4.5)))));

        StaticInfo staticInfo0 = new StaticInfo(0, 640, 5, 0.4, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.20, new Position(5, 5),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        PersonCalculator pc = new PersonCalculator(main, environment);

        DirectionInfo dd = pc.getDirectionInfo();
    }

}