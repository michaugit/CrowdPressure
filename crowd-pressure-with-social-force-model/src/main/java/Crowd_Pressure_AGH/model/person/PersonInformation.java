package Crowd_Pressure_AGH.model.person;

/**
 * The class containing all information about an object (person)
 *
 * -person 									i
 * -person radius 							r_i = m_i / 320
 * -mass of person							m_1 				60-100kg
 * -position 								x_i
 * -speed 									v_i
 * -comfortable walking speed 				v_i0
 * -destination point						O_i
 * -direction 								alpha is in [-phi, phi]
 * -desired directions 						alpha_des
 * -destination point (vision center)		alpha_0
 * -desired walking speed 					v_des
 * -reflecting collision in direction alpha	f(alpha)
 * -vision field (left and right) 			phi			 
 * -horizon distance						H_i
 * -relaxation time 						tau 				0.5 sek
 * -maximum distance						d_max = H_i
 * -distance								d(alpha)
 *
 * 
 * d(alpha) = (d_max)^2 + (f(alpha))^2 - 2 * d_max * f(alpha) * cos(alpha_0 - alpha) 
 * 
 *
 * -distance between person i and the first obstacle in direction alpha_des at time t		 t_h
 *
 *
 * -v_des(t) = min( v_i0, d_h/theta)
 */

public class PersonInformation {
	private StaticInfo staticInfo;
	private VariableInfo variableInfo;

	public PersonInformation(StaticInfo staticInfo,
							 VariableInfo variableInfo) {
		super();
		this.staticInfo = staticInfo;
		this.variableInfo = variableInfo;
	}

	public StaticInfo getStaticInfo() {
		return staticInfo;
	}

	public void setStaticInfo(StaticInfo staticInfo) {
		this.staticInfo = staticInfo;
	}

	public VariableInfo getVariableInfo() {
		return variableInfo;
	}

	@Override
	public String toString() {
		String staticInfo="Mass: " + this.staticInfo.getMass() + "\n" +
				"Radius: " + this.staticInfo.getRadius()+  "\n" +
				"Comfortable speed: " + this.staticInfo.getComfortableSpeed() +  "\n" +
				"Vision angle: " + this.staticInfo.getVisionAngle() +  "\n" +
				"Horizont distance: " + this.staticInfo.getHorizontDistance() +  "\n" +
				"Relaxation time: " + this.staticInfo.getRelaxationTime() +  "\n";

		String variableInfo="VisionCenter: " + this.variableInfo.getVisionCenter() +  "\n" +
				"DestinationAngle: " + this.variableInfo.getDestinationAngle() +  "\n" +
				"DestinationPoint: " + this.variableInfo.getDestinationPoint() +  "\n" +
				"Position: " + this.variableInfo.getPosition() +  "\n" +
				"NextPosition: " + this.variableInfo.getNextPosition() +  "\n" +
				"DesiredDirection: " + this.variableInfo.getDesiredDirection() +  "\n" +
				"CrowdPressure: " + this.variableInfo.getCrowdPressure() +  "\n";

		return "#Static info: \n" + staticInfo + "\n#Variable information: \n" + variableInfo ;
	}

	public void setVariableInfo(VariableInfo variableInfo) {
		this.variableInfo = variableInfo;
	}
}
