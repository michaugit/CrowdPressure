package Crowd_Pressure_AGH.calculators;

import Crowd_Pressure_AGH.TerminalPrinter;

import java.util.function.BiFunction;

/** The class calculating crowd pressure value from forces values */
public class CrowdPressure {
	private static final double MIN_ACCELERATION_VALUE = 0.0;
	private static final double MULTIPLE = 3;
//	private static final double MAX_CROWD_PRESSURE = 1.0;
	private static final double MAX_CROWD_PRESSURE = 1000.0;

	private static final BiFunction<Double, Double, Double> multipleByFunction = (Double ac, Double multi) -> {
		return ac * multi;
	};

	public double getCrowdPressure(double forcesValue) {
//		TerminalPrinter.print("FV: " + forcesValue );
		double crowdPressure = 0.0;

		if (forcesValue > MIN_ACCELERATION_VALUE) {
			crowdPressure = multipleByFunction.apply(forcesValue, MULTIPLE);
		} else { 
			crowdPressure = 0.0;
		}
		if (crowdPressure > MAX_CROWD_PRESSURE) {
			crowdPressure = MAX_CROWD_PRESSURE;
		}
//		TerminalPrinter.print("AFT FV: " + Arrays.asList(forcesValue,crowdPressure) );
//		TerminalPrinter.print("ACC, CP " + Arrays.asList(forcesValue,crowdPressure));

		return crowdPressure;
	}

}
