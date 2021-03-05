package Crowd_Pressure_AGH.model.person;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.Position;
import Crowd_Pressure_AGH.model.map.Map;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersonTest {


    @Test
    public void prepareNextStepTest() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 5, 0.24, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.2, new Position(5, 5), new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        Person person = new Person(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));

        person.prepareNextStep(1);
        assertEquals(4.05, person.getPersonInformation().getVariableInfo().getNextPosition().getX(),0.01);
        assertEquals(2.94, person.getPersonInformation().getVariableInfo().getNextPosition().getY(),0.01);
    }

    @Test
    public void prepareNextStepTest2() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 5, 0.24, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.8, new Position(5, 5), new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        Person person = new Person(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));

    }

    @Test
    public void nextStepTest() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 5, 0.44, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.2, new Position(5, 5), new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        Person person = new Person(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));

        for (int i = 0; i < 10; i++) {
            person.prepareNextStep(i);
            person.nextStep();

        }
    }

    @Test
    public void nextStepTest2() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 5, 0.44, 10, 1);
        VariableInfo variableInfo0 = new VariableInfo(1.75, new Position(5, 5), new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        Person person = new Person(main, new Environment(new ArrayList<>(), new Map(new ArrayList<>())));

        for (int i = 0; i < 10; i++) {
            person.prepareNextStep(i);
            person.nextStep();
        }
    }
}
