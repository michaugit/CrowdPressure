package Crowd_Pressure_AGH.model.person;

import Crowd_Pressure_AGH.model.Position;
import java.util.ArrayList;
import java.util.List;

/** The class that connects people into groups with a common destination */
public class GroupOfPeople {
    static Integer idGen = 0;
    private Integer groupID;
    private Position destinationPoint;
    private ArrayList<Person> peopleInGroup;

    public GroupOfPeople(Position destinationPkt){
        this.groupID=idGen;
        idGen++;
        this.destinationPoint=destinationPkt;
        peopleInGroup = new ArrayList<>();
    }

    public void addPerson(Person p){
        this.updateDestinationPoint(p);
        peopleInGroup.add(p);
    }

    public void removePerson(Person p){
        peopleInGroup.remove(p);
    }

    private void updateDestinationPoint(Person p){
        p.getPersonInformation().getVariableInfo().getDestinationPoint().setX(destinationPoint.getX());
        p.getPersonInformation().getVariableInfo().getDestinationPoint().setY(destinationPoint.getY());
    }

    private void updateDestinationPointAll(){
        for(Person p : peopleInGroup){
            updateDestinationPoint(p);
        }
    }

    public List<Person> getAllPeopleAsList(){
        return peopleInGroup;
    }

    public Position getDestination(){
        return destinationPoint;
    }

    public void setDestination(Position newDestination){
        this.destinationPoint=newDestination;
        updateDestinationPointAll();
    }

    public Integer getID(){
        return groupID;
    }
}
