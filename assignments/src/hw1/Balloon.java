package hw1;

public class Balloon {
	/**
	 * the change in the temperature, 
	 * used to find the change in balloonTemp. 
	 */
	private double deltaT;
	/**
	 * the rate at which fuel is burned for the hot air balloon, 
	 * used to compute burnRate.
	 */
	private double burnRate;
	/**
	 * the amount of fuel that has been burned, 
	 * used to compute fuelLevel.
	 */
	private double fuelBurned;
	/**
	 * the amount of fuel left, 
	 * indicates the amount of fuel remaining. 
	 */
	private double fuelLevel;
	/**
	 * the starting outside temperature, 
	 * airTemp and balloonTemp defaults to this when reset. 
	 */
	private double originalOutsideTemp;
	/**
	 * current air temperature, 
	 * used to compute airDensity, balloonDensity, and deltaT. 
	 */
	private double airTemp;
	/**
	 * the current density of the air. 
	 */
	private double airDensity;
	/**
	 * the current air density of the balloon. 
	 */
	private double balloonDensity;
	/**
	 * the mass of the balloon, used to find the current 
	 * force of gravity on the balloon as well as the net acceleration. 
	 */
	private double balloonMass;
	/**
	 * the current length of the tether. Used to find the 
	 * amount of tether left after the balloon increases altitude. 
	 */
	private double tetherLength;
	/**
	 * temperature of the balloon. 
	 */
	private double balloonTemp;

	/**
	 * current velocity of balloon, 
	 * used to compute the next altitude as the balloon rises. 
	 */
	private double velocity;
	/**
	 * altitude of the balloon, 
	 * used to compute the new altitude through being added to velocity. 
	 */
	private double altitude;
	/**
	 * the next calculated value of the balloon's altitude. 
	 */
	private double nextAltitude;
	/**
	 * the direction of the balloon in degrees. 
	 */
	private double windDirection;
	/**
	 * the starting direction of the balloon, 
	 * used once the program is reset to give a starting angle for the balloon. 
	 */
	private double originalWindDirection;
	/**
	 * computes the current force 
	 * felt by the balloon given the gravity constant * mass of balloon. 
	 */
	private double forceGravity;
	/**
	 * the net force on the balloon,
	 *  otherwise known as the sum of all forces forces which create one resultant force. 
	 */
	private double forceNet;
	/**
	 * the net acceleration the balloon is experiencing. 
	 * Found using forceNet / balloonMass, and used to compute velocity. 
	 */
	private double accelerationNet;
	/**
	 * the current force of lift on the balloon, 
	 * calculated using volume * density difference * gravity constant. 
	 */
	private double forceLift;
	/**
	 * the total time passed, used to compute many things in the program, 
	 * such as everything in the update() method as well as seconds and minutes. 
	 */
	private long timeTotal;

	

	/**
	 * Heat loss factor.
	 */
	private static final double HEAT_LOSS = 0.1;
	/**
	 * The volume of air in the balloon in m^3
	 */
	private static final double VOLUME_IN_AIR_BALLOON = 61234;
	/**
	 * Acceleration due to gravity in m/s^2
	 */
	private static final double GRAVITY = 9.81;
	/**
	 * The gas constant in J/kgK
	 */
	private static final double GAS_CONSTANT = 287.05;
	/**
	 * Standard pressure in hPa
	 */
	private static final double STANDARD_PRESSURE = 1013.25;
	/**
	 * Kelvins at 0 degrees C
	 */
	private static final double KELVIN_AT_ZERO_DEGREES = 273.15;
	
/**
 * Constructs a new balloon simulation. 
 * @param airTemp: outside air temp. 
 * @param windDirection: degree value of wind's direction. 
 */
	public Balloon(double airTemp, double windDirection) {


		originalOutsideTemp = airTemp;
		originalWindDirection = windDirection;	
		reset();

	}
	/**
	 * gets remaining fuel which can be used to heat the balloon's air. 
	 * @returns the current fuel level, which reduces as more fuel is burned. 
	 */
	public double getFuelRemaining() {
		
		return fuelLevel;
	}
	
	/**
	 * sets remaining fuel which can be used to heat the balloon's air.
	 * @param fuel 
	 */
	public void setFuelRemaning(double fuel) {
		
		fuelLevel += fuel;
		
	}
	
	/**
	 * gets balloon's mass (m)
	 * @return the current mass of the balloon. 
	 */
	public double getBalloonMass() {
		
		return balloonMass;
	}
	
	/**
	 * sets balloon's mass (m)
	 * @param mass
	 */
	public void setBalloonMass(double mass) {
		
		balloonMass = mass;
	}
	
	/**
	 * gets outside air temp
	 * @return current air temp
	 */
	public double getOutsideAirTemp() {
		
		return airTemp;
	}
	
	/**
	 * sets outside air temp
	 * @param temp
	 */
	public void setOutsideAirTemp(double temp) {
		
		airTemp = temp;
	}
	
	/**
	 * gets fuel burn rate(B)
	 * @return the current rate at which the fuel is being burned. 
	 */
	public double getFuelBurnRate() {
	
		return burnRate;
	}
	/**
	 * sets the fuel burn rate(B)
	 * @param rate
	 */
	public void setFuelBurnRate(double rate) {
		
		burnRate = rate;
	}
	/**
	 * gets the balloon temperature (Tballoon)
	 * @return current balloon temp
	 */
	public double getBalloonTemp() {
		
		return balloonTemp;
	}
	/**
	 * sets the balloon temperature (Tballoon)
	 * @param temp
	 */
	public void setBalloonTemp(double temp) {
		
		balloonTemp = temp;
	}
	/**
	 * gets the balloon velocity
	 * @return velocty
	 */
	public double getVelocity() {
		
		return velocity;
	}
	/**
	 * gets the balloon altitude
	 * @return altitude
	 */
	public double getAltitude() {
		
		return altitude;
	}
	
	/**
	 * gets tether length
	 * @return tether length
	 */
	public double getTetherLength() {
		
		return tetherLength;
	}
	
	/** 
	 * gets tether length minus altitude
	 * @return tether length available to be used
	 */
	public double getTetherRemaining() {
		
		return tetherLength - altitude;
	}
	/**
	 * sets the length of the tether
	 * @param length
	 */
	public void setTetherLength(double length) {
		
		tetherLength = length;
	}
	/**
	 * gets the wind direction in degrees between 0 and 360
	 * @return the current wind direction
	 */
	public double getWindDirection() {
		
		return windDirection;
	}
	
	/**
	 * updates the wind direction by adding the given value onto the current wind direction, which will remain between 0 & 360. 
	 * @param deg
	 */
	public void changeWindDirection(double deg) {
		
		windDirection = (deg + windDirection + 360) % 360;
	}
	
	/**
	 * gets the number of minutes that have passed in the simulation
	 * @return time in minutes
	 */
	public long getMinutes() {
		
		return timeTotal / 60;
	}
	
	/**
	 * gets the number of seconds that have passed, will be between 0 and 59. 
	 * @return time in seconds
	 */
	public long getSeconds() {
		
		return timeTotal % 60;
	}
	
	/**
	 * updates the status of the balloon every one second. 
	 */
	public void update() {
		
		timeTotal++;
		
		fuelBurned = Math.min(fuelLevel, burnRate);
		fuelLevel -= fuelBurned;

		deltaT = fuelBurned + (airTemp - balloonTemp) * HEAT_LOSS;
		balloonTemp += deltaT;
		
		airDensity = STANDARD_PRESSURE / (GAS_CONSTANT * (airTemp + KELVIN_AT_ZERO_DEGREES));
		balloonDensity = STANDARD_PRESSURE / (GAS_CONSTANT * (balloonTemp + KELVIN_AT_ZERO_DEGREES));
		
		forceLift = VOLUME_IN_AIR_BALLOON * (airDensity - balloonDensity) * GRAVITY;
		forceGravity = GRAVITY * balloonMass;
		forceNet = forceLift - forceGravity;	
		accelerationNet = forceNet / balloonMass;
		
		velocity += accelerationNet;
		nextAltitude = altitude + velocity;
		altitude = Math.min(Math.max(nextAltitude, 0), tetherLength);
	}
	
	/**
	 * resets the simulation to the beginning. 
	 */
	public void reset() {
		
		burnRate = 0;
		airTemp = originalOutsideTemp;
		balloonTemp = originalOutsideTemp;
		windDirection = originalWindDirection;
		balloonMass = 0;
		altitude = 0;
		fuelLevel = 0;
		timeTotal = 0;
		velocity = 0;
		
	}
}
