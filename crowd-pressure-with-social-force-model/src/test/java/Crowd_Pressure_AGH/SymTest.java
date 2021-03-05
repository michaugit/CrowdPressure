package Crowd_Pressure_AGH;

import java.util.ArrayList;
import java.util.List;

import Crowd_Pressure_AGH.builders.MapFactory;
import Crowd_Pressure_AGH.builders.PeopleFactory;
import Crowd_Pressure_AGH.exceptions.AngleOutOfRangeException;
import Crowd_Pressure_AGH.model.Environment;
import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.person.Person;

import org.junit.Test;

public class SymTest {
    private static final int STEPS = 10;

    @Test
    public void test() throws AngleOutOfRangeException {
        // create map
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap(SimulationTypes.SYM_ONE_WALL_TWO_GROUPS);
        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people, map);
        PeopleFactory PeopleBuilder = new PeopleFactory();
        PeopleBuilder.addPeople(environment, SimulationTypes.SYM_ONE_WALL_TWO_GROUPS);
        Engine engine = new Engine(environment);
        int i = STEPS;
        while (i > 0) {
            engine.nextState();
            i--;
        }
    }

    @Test
    public void test2() throws AngleOutOfRangeException {
        // create map
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.getMap(SimulationTypes.SYM_ROOM_WITHOUT_OBSTACLE);
        List<Person> people = new ArrayList<>();
        Environment environment = new Environment(people, map);
        PeopleFactory PeopleBuilder = new PeopleFactory();
        PeopleBuilder.addPeople(environment, SimulationTypes.SYM_ROOM_WITHOUT_OBSTACLE);
        Engine engine = new Engine(environment);
        int i = STEPS;
        while (i > 0) {
            engine.nextState();
            i--;
        }
    }
}