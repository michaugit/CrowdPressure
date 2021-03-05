package Crowd_Pressure_AGH;

import java.util.Iterator;

import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.person.Person;

/** The class containing simulation engine that performs and prepares the following simulation steps */
public class Engine {
	private int step;

	private Environment environment;

	public Engine(Environment environment) {
		this.environment = environment;
		step = 0;
	}

	public void nextState() throws AngleOutOfRangeException {
		for (Person p : environment.getPeople()) {
			if (p.getPersonInformation().getVariableInfo().isFinished()) {
				TerminalPrinter.print("PERSON: " + p.getPersonInformation().getStaticInfo().getId() +
						" FINISHED ON STEP: " + step +
						"  MAX CROWD PRESSURE: " + String.format("%.2f",p.getPersonInformation().getVariableInfo().getMaxCrowdPressure()));
				continue;
			}
			p.prepareNextStep(step);
		}

		for (Iterator<Person> iterator = environment.getPeople().iterator(); iterator.hasNext();) {
			Person p = iterator.next();
			if (p.getPersonInformation().getVariableInfo().isFinished()) {
				iterator.remove();
			} else {
				p.nextStep();
				step++;
			}
		}
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
