package Crowd_Pressure_AGH.model;

/** The class representing a single point which is being used to create walls, setting up destination and start*/
public class Position {
	double x;
	double y;

	public Position(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public String toString(){
//		return new String("[ X=" + this.getX() + ", Y=" + this.getY() + " ]"); // old style
		return new String("[" + this.getX() + " , " + this.getY() + "]"); // new style
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
