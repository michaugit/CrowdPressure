package Crowd_Pressure_AGH.calculators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.map.PrimitiveWall;
import Crowd_Pressure_AGH.model.map.Wall;
import Crowd_Pressure_AGH.model.person.Person;
import Crowd_Pressure_AGH.model.person.PersonInformation;
import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.calculators.figures.LineTwoPoints;
import Crowd_Pressure_AGH.calculators.figures.Vector;
import Crowd_Pressure_AGH.calculators.figures.VectorXY;

/** The class providing Social Force calculator */
public class SocialForce {
	private Environment environment;

	public SocialForce(Environment environment) {
		this.environment = environment;
	}

	public List<Vector> getForceNeighbours(PersonInformation personInformation) {
		List<Vector> forces = new ArrayList<>();
		for (Person p : environment.getPeople()) {
			if (p.getPersonInformation().getStaticInfo().getId() != personInformation
					.getStaticInfo().getId()) {
				Optional<Vector> oForce = calculateForce(personInformation, p.getPersonInformation());
				if (oForce.isPresent()) {
					forces.add(oForce.get());
				}
			}
		}
		return forces;
	}

	private Optional<Vector> calculateForce(PersonInformation personInfo, PersonInformation neighborInfo) {
		
		Double radiusSum = personInfo.getStaticInfo().getRadius()
				+ neighborInfo.getStaticInfo().getRadius();
		
//		TerminalPrinter.print("POS" + Arrays.asList(personInfo.getVariableInfo().getPosition(),
//				neighborInfo.getVariableInfo().getPosition()));
		Double distance = GeometricCalculator.distance.apply(personInfo.getVariableInfo().getPosition(),
				neighborInfo.getVariableInfo().getPosition());

		Double force = (radiusSum - distance);	
//		TerminalPrinter.print(personInfo.getStaticInfo().getId()+" RS, D, F" + Arrays.asList(radiusSum, distance, force));
		force = force * DefaultConfig.K_PARAMETER;
		if (force > 0) {
			VectorXY direction = GeometricCalculator.subtractVectors(
					new VectorXY(personInfo.getVariableInfo().getPosition()),
					new VectorXY(neighborInfo.getVariableInfo().getPosition()));
			Vector forceVector = GeometricCalculator.changeVector(direction);
			forceVector.setValue(force);
			return Optional.of(forceVector);
		}
		return Optional.empty();
	}

	public List<Vector> getForceWalls(PersonInformation personInformation) {
		List<Vector> forces = new ArrayList<>();
		for (Wall w : environment.getMap().getWalls()) {
			if (w instanceof PrimitiveWall) {
				PrimitiveWall sw = (PrimitiveWall) w;
				Optional<Vector> oForce = calculateForce(personInformation, sw);
				if (oForce.isPresent()) {
					forces.add(oForce.get());
				}
			}
		}
		return forces;
	}

	private Optional<Vector> calculateForce(PersonInformation personInfo, PrimitiveWall wall) {
		Optional<Vector> optionalVector = GeometricCalculator.vectorStraightPoint.apply(
				personInfo.getVariableInfo().getPosition(),
				new LineTwoPoints(wall.getStartPosition(), wall.getEndPosition()));

		if (optionalVector.isPresent()) {
			Vector forceVector = optionalVector.get();
			Double force = personInfo.getStaticInfo().getRadius() - forceVector.getValue();
			if (force > 0) {
				force = force * DefaultConfig.K_PARAMETER;
				forceVector.setValue(force);

				return Optional.of(forceVector);
			}
		}
		return Optional.empty();
	}

}
