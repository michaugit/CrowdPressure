package Crowd_Pressure_AGH.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import Crowd_Pressure_AGH.model.map.PrimitiveWall;
import Crowd_Pressure_AGH.model.map.Wall;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.calculators.figures.LinePointAngle;
import Crowd_Pressure_AGH.calculators.figures.LineTwoPoints;
import Crowd_Pressure_AGH.model.Position;

/** The class calculating wall cross points with person */
public class WallCrossPoint {
	private Double alpha;
	private Position personPosition;

	public WallCrossPoint(Double alpha, PersonInformation personInformation) {
		this.alpha = alpha;
		this.personPosition = personInformation.getVariableInfo().getPosition();
	}

	public List<Position> getWallCrossPoints(Wall w) {
		if (w instanceof PrimitiveWall) {
			PrimitiveWall sw = (PrimitiveWall) w;
			Optional<Position> op = GeometricCalculator.crossPointTwoLines.apply(
					new LinePointAngle(personPosition, alpha),
					new LineTwoPoints(sw.getStartPosition(), sw.getEndPosition()));
			if (op.isPresent()) {
				return Arrays.asList(op.get());
			} else {
				return new ArrayList<>();
			}
		}

		return null;
	}

}
