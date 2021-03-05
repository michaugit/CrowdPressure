package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Position;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.model.person.StaticInfo;
import Crowd_Pressure_AGH.model.person.VariableInfo;
import org.junit.Test;

import java.util.List;


public class PersonCrossPointsTest {

    @Test
    public void calculateCrossPointUnderSqrtTest() {
        StaticInfo staticInfo0 = new StaticInfo(0, 5, 5, 0.24, 5, 5);
        VariableInfo variableInfo0 = new VariableInfo(0.2, new Position(100, 100),
                new Position(6, 5));
        PersonInformation main = new PersonInformation(staticInfo0, variableInfo0);

        StaticInfo staticInfo1 = new StaticInfo(1, 640, 5, 0.24, 5, 5);
        VariableInfo variableInfo1 = new VariableInfo(0.2, new Position(100, 100),
                new Position(11, 8));
        PersonInformation neighborInformation = new PersonInformation(staticInfo1, variableInfo1);
        PersonCrossPoints pcp = new PersonCrossPoints(0.25, main);

        double result = pcp.calculateCrossPointCoordUnderSqrt(
                neighborInformation.getVariableInfo().getPosition(),
                neighborInformation.getStaticInfo().getRadius());
        assertEquals(223.55, result, 0.1);
    }

}