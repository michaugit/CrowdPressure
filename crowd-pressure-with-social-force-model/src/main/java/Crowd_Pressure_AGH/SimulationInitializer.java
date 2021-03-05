package Crowd_Pressure_AGH;

import java.util.ArrayList;
import java.util.List;

import Crowd_Pressure_AGH.builders.MapFactory;
import Crowd_Pressure_AGH.builders.PeopleFactory;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.person.Person;

/** The class which initializes simulation */
public class SimulationInitializer {
	//creating engine
	static public Engine createEngine(SimulationTypes sym, PeopleFactory peopleFactory) {
		//creating map
		MapFactory mapFactory = new MapFactory();
		Map map = mapFactory.getMap(sym);

		List<Person> people = new ArrayList<>();
		//creating environment for people and map
		Environment environment = new Environment(people, map);
		
		//creating people
		peopleFactory.addPeople(environment,sym);

		return new Engine(environment);
	}

}
