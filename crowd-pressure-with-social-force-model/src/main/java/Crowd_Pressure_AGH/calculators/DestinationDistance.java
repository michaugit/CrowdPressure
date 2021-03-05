package Crowd_Pressure_AGH.calculators;

import java.util.function.Function;
import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.model.DestinationDistanceCalculatorInfo;

/** The class calculating the distance from destination */
public class DestinationDistance {
	public static Function<DestinationDistanceCalculatorInfo, Double> originalDDFunction = (
			DestinationDistanceCalculatorInfo info) -> {
		double a = info.getAlpha();
		double da = info.getDestinationAngle();
		double hd = info.getHorizontDistance();
		double cdv = info.getCollisionDistanceValue();
		return hd * hd + cdv * cdv - 2 * hd * cdv * Math.cos(da * Math.PI - a * Math.PI); //WZÓR
	};

	public static Function<DestinationDistanceCalculatorInfo, Double> modifiedDDFunction = (
			DestinationDistanceCalculatorInfo info) -> {
		double a = info.getAlpha();
		double da = info.getDestinationAngle();
		double hd = info.getHorizontDistance();
		double cdv = info.getCollisionDistanceValue();

		double lParameter = 0.0;
		if (cdv < hd) {
			lParameter = hd - cdv;
			lParameter = lParameter * 100;
		}
		double result = hd * hd + cdv * cdv - 2 * hd * cdv * Math.cos(da * Math.PI - a * Math.PI) + lParameter;
//		 TerminalPrinter.print("alpha, L, cdv, hd, result " + Arrays.asList(a,lParameter,cdv,hd,
//		 result));
		return result;
	};

	public double getDestinationDistanceFunction(double alpha, double destinationAngle, double horizontDistance,
			Double collisionDistanceValue) {

		DestinationDistanceCalculatorInfo ddci = new DestinationDistanceCalculatorInfo(alpha, destinationAngle,
				horizontDistance, collisionDistanceValue);

		return DefaultConfig.DESTINATION_DISTANCE_FUNCTION.apply(ddci);
	}

}
