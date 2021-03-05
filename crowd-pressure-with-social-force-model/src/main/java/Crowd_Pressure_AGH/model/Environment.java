package Crowd_Pressure_AGH.model;

import java.util.ArrayList;
import java.util.List;

import Crowd_Pressure_AGH.model.map.Map;
import Crowd_Pressure_AGH.model.person.GroupOfPeople;
import Crowd_Pressure_AGH.model.person.Person;

/** The class representing the environment which contains list of people groups and Map */
public class Environment {
	private List<Person> people;
	private ArrayList<GroupOfPeople> groups;
	private Map map;

	public Environment(List<Person> people, Map map) {
		super();
		this.people = people;
		this.map = map;
		groups = new ArrayList<GroupOfPeople>();
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Environment(Map map) {
		super();
		this.groups = new ArrayList<>();
		this.people =  new ArrayList<>();
		this.map = map;
	}

	public void addGroup(GroupOfPeople g){
		people.addAll(g.getAllPeopleAsList());
		groups.add(g);
	}

	public void addPersonToGroup(GroupOfPeople group, Person person){
		group.addPerson(person);
		people.add(person);
	}

	public void removeGroup(GroupOfPeople g){
		people.removeAll(g.getAllPeopleAsList());
		groups.remove(g);
	}

	public GroupOfPeople getGroupByID(Integer ID){
		for(GroupOfPeople g : groups){
			if( g.getID().equals(ID)) {
				return g;
			}
		}
		return null;
	}

	public ArrayList<GroupOfPeople> getAllGroups(){
		return  groups;
	}

}
