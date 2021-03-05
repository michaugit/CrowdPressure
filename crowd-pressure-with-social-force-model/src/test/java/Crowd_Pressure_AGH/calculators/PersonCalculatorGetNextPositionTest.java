package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import Crowd_Pressure_AGH.calculators.figures.Vector;
import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Position;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.model.person.StaticInfo;
import Crowd_Pressure_AGH.model.person.VariableInfo;
import org.junit.Test;

public class PersonCalculatorGetNextPositionTest {

    @Test
    public void getNextPositionTestR() throws AngleOutOfRangeException {

        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(0);
        main.getVariableInfo().setDesiredSpeed(new Vector(0, 2));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(2.0, result.getX(), 0.01);
        assertEquals(0.0, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestL() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(1);
        main.getVariableInfo().setDesiredSpeed(new Vector(1, 2));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(-2.0, result.getX(), 0.01);
        assertEquals(0.0, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestTL() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(0.75);
        main.getVariableInfo().setDesiredSpeed(new Vector(0.75, 5));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(-3.535, result.getX(), 0.01);
        assertEquals(3.535, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestT() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(0.5);
        main.getVariableInfo().setDesiredSpeed(new Vector(0.5, 2));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(0.0, result.getX(), 0.01);
        assertEquals(2.0, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestTR() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(0.25);
        main.getVariableInfo().setDesiredSpeed(new Vector(0.25, 5));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(3.535, result.getX(), 0.01);
        assertEquals(3.535, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestBL() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(1.25);
        main.getVariableInfo().setDesiredSpeed(new Vector(1.25, 5));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(-3.535, result.getX(), 0.01);
        assertEquals(-3.535, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestB() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(1.5);
        main.getVariableInfo().setDesiredSpeed(new Vector(1.5, 2));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();
        assertEquals(0.0, result.getX(), 0.01);
        assertEquals(-2.0, result.getY(), 0.01);
    }

    @Test
    public void getNextPositionTestBR() throws AngleOutOfRangeException {
        StaticInfo staticInfo0 = new StaticInfo(0, 1, 10, 0.1, 1, 1);
        VariableInfo variableInfo0 = new VariableInfo(0.25, new Position(1, 1),
                new Position(0, 0));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        main.getVariableInfo().setDesiredDirection(1.75);
        main.getVariableInfo().setDesiredSpeed(new Vector(1.75, 5));
        PersonCalculator pc = new PersonCalculator(main, null);
        Position result = pc.getNextPosition();

        assertEquals(3.535, result.getX(), 0.01);
        assertEquals(-3.535, result.getY(), 0.01);
    }
}
