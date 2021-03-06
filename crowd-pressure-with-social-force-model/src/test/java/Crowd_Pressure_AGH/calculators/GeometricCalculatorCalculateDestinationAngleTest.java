package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import Crowd_Pressure_AGH.model.Position;
import org.junit.Test;

public class GeometricCalculatorCalculateDestinationAngleTest {

    @Test
    public void calculateDestinationAngleTest() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(6, 5), new Position(14, 11));
        assertEquals(0.204, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTest2() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(-4.960573506572389, 0.6266661678215222), new Position(5, 5));
        assertEquals(0.131, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestRT() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(3, 3));
        assertEquals(0.25, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestT() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(2, 3));
        assertEquals(0.5, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestLT() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(1, 3));
        assertEquals(0.75, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestL() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(1, 2));
        assertEquals(1.0, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestR() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(3, 2));
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestLB() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(1, 1));
        assertEquals(1.25, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestB() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(2, 1));
        assertEquals(1.5, result, 0.01);
    }

    @Test
    public void calculateDestinationAngleTestRB() {
        double result = GeometricCalculator.calculateAngle.apply(new Position(2, 2), new Position(3, 1));
        assertEquals(1.75, result, 0.01);
    }
}