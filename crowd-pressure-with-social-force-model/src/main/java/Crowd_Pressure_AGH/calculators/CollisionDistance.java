package Crowd_Pressure_AGH.calculators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.MinimumDistance;
import Crowd_Pressure_AGH.model.map.Wall;
import Crowd_Pressure_AGH.model.person.Person;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.model.Position;

/** The class calculating the distance from various objects */
public class CollisionDistance {
	private Environment environment;

	public CollisionDistance(Environment environment) {
		this.environment = environment;
	}

	public MinimumDistance getCollistionDistanceValue(Double alpha, PersonInformation personInformation)
			throws AngleOutOfRangeException {
		Double horizontDistance = personInformation.getStaticInfo().getHorizontDistance();
		int id = personInformation.getStaticInfo().getId();
		if (environment == null) {
			return new MinimumDistance(horizontDistance, Optional.empty());
		}
		//TerminalPrinter.print(personInformation.toString());
		PersonCrossPoints personCrossPoints = new PersonCrossPoints(alpha, personInformation);
		WallCrossPoint wallCrossPoint = new WallCrossPoint(alpha, personInformation);

		Position personPosition = personInformation.getVariableInfo().getPosition();

		Optional<Double> nDistance = neighboursDistance(personCrossPoints, personPosition, id);
		Optional<Double> wDistance = getWallDistance(wallCrossPoint, personPosition);

		MinimumDistance minimumDistance = getminimumDistance(horizontDistance, nDistance, wDistance);

		return minimumDistance;
	}

	private MinimumDistance getminimumDistance(Double horizontDistance, Optional<Double> nDistance,
			Optional<Double> wDistance) {
		Double minDistanceValue = horizontDistance;
		for (Optional<Double> d : Arrays.asList(nDistance, wDistance)) {
			if (d.isPresent()) {
				minDistanceValue = minDistanceValue < d.get() ? minDistanceValue : d.get();
			}
		}
		if (wDistance.isPresent()) {
			if (wDistance.get() < horizontDistance) {
				return new MinimumDistance(minDistanceValue, wDistance);
			}
		}

		return new MinimumDistance(minDistanceValue, Optional.empty());
	}

	private Optional<Double> getWallDistance(WallCrossPoint wallCrossPoint, Position personPosition) {
		Optional<Double> optionalGlobalMin = Optional.empty();
		for (Wall w : environment.getMap().getWalls()) {
			optionalGlobalMin = resolveMinimumOfOptionals(optionalGlobalMin,
					getMinDistance(personPosition, wallCrossPoint.getWallCrossPoints(w)));
		}

		return optionalGlobalMin;
	}

	private Optional<Double> neighboursDistance(PersonCrossPoints personCrossPointsCal,
												Position personPosition, int id) throws AngleOutOfRangeException {

		Optional<Double> optionalGlobalMin = Optional.empty();

		for (Person p : environment.getPeople()) {
			if (p.getPersonInformation().getStaticInfo().getId() != id) {
				optionalGlobalMin = resolveMinimumOfOptionals(optionalGlobalMin, getMinDistance(personPosition,
						personCrossPointsCal.getNeighborAllCrossPoints(p.getPersonInformation())));
			}
		}

		return optionalGlobalMin;
	}

	private Optional<Double> resolveMinimumOfOptionals(Optional<Double> optionalGlobalMin,
			Optional<Double> optionalActualMin) {
		if (optionalGlobalMin.isPresent()) {
			if (optionalActualMin.isPresent()) {
				Double actualMin = optionalActualMin.get();
				if (actualMin < optionalGlobalMin.get()) {
					optionalGlobalMin = Optional.of(actualMin);
				}
			}
		} else {
			optionalGlobalMin = optionalActualMin;
		}

		return optionalGlobalMin;
	}

	private Optional<Double> getMinDistance(Position personPosition, List<Position> crossPoints) {
		OptionalDouble om = crossPoints.stream()
				.mapToDouble(cp -> GeometricCalculator.distance.apply(personPosition, cp)).min();
		if (om.isPresent()) {
			return Optional.of(om.getAsDouble());
		}

		return Optional.empty();
	}

}
