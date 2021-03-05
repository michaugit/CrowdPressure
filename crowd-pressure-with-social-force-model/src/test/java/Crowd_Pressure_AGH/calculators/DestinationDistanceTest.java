package Crowd_Pressure_AGH.calculators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DestinationDistanceTest {

    @Test
    public void getDestinationDistanceFunctionTest() {
        DestinationDistance destinationDistance = new DestinationDistance();
        double result = destinationDistance.getDestinationDistanceFunction(10, 11, 10.0, 2.0);

        assertEquals(144.0, result, 0.01);
    }
}