package Crowd_Pressure_AGH.model.person;

import java.util.List;

import Crowd_Pressure_AGH.model.DirectionInfo;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.DefaultConfig;
import Crowd_Pressure_AGH.calculators.GeometricCalculator;
import Crowd_Pressure_AGH.calculators.PersonCalculator;
import Crowd_Pressure_AGH.calculators.figures.Vector;
import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;

/** The class representing a single object (person) in Simulation */
public class Person {
	private PersonInformation personInformation;
	private Environment environment;
	private PersonCalculator personCalculator;

	public Person(PersonInformation personInformation, Environment environment) {
		this.environment = environment;
		this.personInformation = personInformation;
		this.personCalculator = new PersonCalculator(personInformation, environment);
	}

	public void prepareNextStep(int step) throws AngleOutOfRangeException {
		DirectionInfo desiredDirectionInfo = personCalculator.getDirectionInfo();
		Vector desiredVelocity = personCalculator.getDesireVelocity(desiredDirectionInfo.getCollisionDistance().getMinimumDistance(),
				desiredDirectionInfo.getAlpha(), personInformation.getStaticInfo().getId());
		// TerminalPrinter.print(desiredVelocity);

		List<Vector> forces = personCalculator.getForces();
		double forcesValue = personCalculator.getForcesValue(forces);
		Vector desiredAcceleration = personCalculator.getDesireAcceleration(desiredVelocity, forces);
		personInformation.getVariableInfo().setDesiredDirection(desiredDirectionInfo.getAlpha());
		personInformation.getVariableInfo().setDesiredSpeed(desiredVelocity);
		personInformation.getVariableInfo().setDesiredAcceleration(desiredAcceleration);
		personInformation.getVariableInfo().setNextPosition(personCalculator.getNextPosition());
		personInformation.getVariableInfo()
				.setDestinationAngle(GeometricCalculator.calculateAngle.apply(
						personInformation.getVariableInfo().getNextPosition(),
						personInformation.getVariableInfo().getDestinationPoint()));


		/** In order to avoid storing wrong values of social force at start simulation */
		double crowdPressure = personCalculator.getCrowdPressure(forcesValue);
		if(step < 3000 && crowdPressure > 200){
			crowdPressure=0;
		}

		personInformation.getVariableInfo().setCrowdPressure(crowdPressure);

	}

	public void nextStep() {
		// TerminalPrinter.print("pos",personInformation.getVariableInfo().getPosition());
		// TerminalPrinter.print("next",personInformation.getVariableInfo().getNextPosition());
		personInformation.getVariableInfo()
				.setPosition(personInformation.getVariableInfo().getNextPosition());
		personInformation.getVariableInfo()
				.setVisionCenter(personInformation.getVariableInfo().getDesiredDirection());

		personInformation.getVariableInfo()
				.setFinished(GeometricCalculator.isBigger.apply(
						GeometricCalculator.distance.apply(personInformation.getVariableInfo().getPosition(),
								personInformation.getVariableInfo().getDestinationPoint()),
						DefaultConfig.MAX_DISTANCE_TO_GOAL));

		// TerminalPrinter.print("af pos",personInformation.getVariableInfo().getPosition());
		// TerminalPrinter.print("af
		// next",personInformation.getVariableInfo().getNextPosition());
	}

	public PersonInformation getPersonInformation() {
		return personInformation;
	}

	public void setPersonInformation(PersonInformation personInformation) {
		this.personInformation = personInformation;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
