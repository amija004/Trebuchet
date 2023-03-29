// Alejandro Mijares
// February 19, 2023
// Panther ID: 3145563
// Program Version: 1.0
// Java Version: 8
import java.util.ArrayList;

/**
 * Trebuchet object which is created with initial velocities, angles, and target distances
 * Calculates viable strike trajectories for the given target
 */
public class Trebuchet {
    /**
     * 2D array of possible trajectories
     */
    private double[][] trajectories;
    /**
     * 1D arraylist of trajectories that land within the compound walls
     */
    private ArrayList<Long> feasibleTrajectories;
    /**
     * 1D arraylist of angles and speeds that generate a feasibleTrajectory
     */
    private ArrayList<int[]> feasibleAnglesAndSpeeds;
    /**
     * 1D array of speeds
     */
    private int[] speeds;
    /**
     * 1D array of angles
     */
    private int[] angles;
    /**
     * Nearest compound wall
     */
    private int minDistance;
    /**
     * Farthest compound wall
     */
    private int maxDistance;

//-------------------------------------------------------------------------------------------------------------
    /**
     * Initializes array of length speedCount
     * @param speedCount Quantity of initial velocities within input file
     */
    public void setSpeedCount(int speedCount) {
        speeds = new int[speedCount];
    }
    /**
     * Gets length of speed array
     * @return speeds.length
     */
    public int getSpeedCount() {
        return speeds.length;
    }

    /**
     * Adds angle to angles array
     * @param index insertion point of new angle
     * @param angle value of angle in degrees
     */
    public void addAngle(int index, int angle) {
        angles[index] = angle;
    }

    /**
     * Gets length of angle array
     * @return angles.length
     */
    public int getAngleCount() {
        return angles.length;
    }

    /**
     * Adds initial velocity to speeds array
     * @param index insertion point of new initial velocity
     * @param speed value of initial velocity in meters per second
     */
    public void addSpeed(int index, int speed) {
        speeds[index] = speed;
    }

    /**
     * Initializes array of length angleCount
     * @param angleCount Quantity of angles
     */
    public void setAngleCount(int angleCount) {
        angles = new int[angleCount];
    }

    /**
     * Sets the distance from the trebuchet to the nearest compound wall
     * @param newMinDistance Minimum viable distance for trajectory
     */
    public void setMinDistance(int newMinDistance) {
        minDistance = newMinDistance;
    }

    /**
     * Sets the distance from the trebuchet to the farthest compound wall
     * @param newMaxDistance Maximum viable distance for trajectory
     */
    public void setMaxDistance(int newMaxDistance) {
        maxDistance = newMaxDistance;
    }

    /**
     * Gets the distance from the trebuchet to the nearest compound wall
     * @return MinDistance
     */
    public int getMinDistance() {
        return minDistance;
    }

    /**
     * Gets the distance from the trebuchet to the farthest compound wall
     * @return MaxDistance
     */
    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * Gets the length of an array at a specific index from the 2D array Trajectories
     * @param index Speed
     * @return Quantity of angles for the requested speed
     */
    public int getTrajectoriesColumnWidth(int index) {
        return trajectories[index].length;
    }

    /**
     * Returns the calculated trajectory from the 2D array Trajectories
     * @param row Speed in meters per second
     * @param col Angle in degrees
     * @return Trajectory in meters
     */
    public double getTrajectory(int row, int col){
        return trajectories[row][col];
    }

    /**
     * Returns the speed at a given index
     * @param index position of requested speed in the array
     * @return speed in meters per second
     */
    public int getSpeed(int index) {
        return speeds[index];
    }

    /**
     * Returns the angle at a given index
     * @param index position of requested angle in the array
     * @return angle in degrees
     */
    public int getAngle(int index) {
        return angles[index];
    }

    /**
     * Feasible trajectories are stored as arrays of length 2 in a 1 dimensional arraylist feasibleAnglesAndSpeeds.
     * Example: [[Angle,Speed],[Angle,Speed],...]
     * @param index location of [Angle, Speed] pair
     * @return [Angle, Speed]
     */
    public Long getFeasibleTrajectories(int index) {
        return feasibleTrajectories.get(index);
    }

    /**
     * Returns the feasible angle at a given index
     * @param index position of requested angle in the feasible array
     * @return angle in degrees
     */
    public int getFeasibleAngle(int index) {
        return feasibleAnglesAndSpeeds.get(index)[0];
    }

    /**
     * Returns the feasible speed at a given index
     * @param index position of requested speed in the feasible array
     * @return speed in meters per second
     */
    public int getFeasibleSpeed(int index) {
        return feasibleAnglesAndSpeeds.get(index)[1];
    }

    /**
     * Returns the count of feasible trajectory pairs
     * @return quantity of feasible trajectories
     */
    public int getFeasibleTrajectoryCount(){
        return feasibleTrajectories.size();
    }

    /**
     * Initialization of 2D trajectory array is performed by this function once the array sizes are known
     */
    private void initializeTrajectories() {
        trajectories = new double[speeds.length][angles.length];
        feasibleTrajectories = new ArrayList<>();
        feasibleAnglesAndSpeeds = new ArrayList<>();
    }

//-------------------------------------------------------------------------------------------------------------
    /**
    * This method calculates the distance a projectile will travel for a given angle and initial velocity
    * @param angle This is the angle in degrees at which the trebuchet will fire
    * @param speed This is the initial velocity in meters per second of the projectile
    * @return This is the distance in meters the projectile will travel
     */
    private double calculateTrajectory(int angle, int speed){
        double radian = Math.toRadians(angle); // Pi/180 Radians = 1 Degree
        double trajectory = 0;// Distance is defaulted to 0
        if (0 <= angle && angle <= 180) { // Trajectory will only be calculated if trebuchet is shooting into air.
            trajectory = ((speed * speed) * Math.sin(2 * radian)) / 9.8; // (V_0^2 * sin(2 * Angle))/ gravity
        }
        return trajectory;
    }

    /**
    *This method passes through all launch vectors, calling CalculateTrajectory to determine the distance, and storing
    * each calculation a 2D trajectories array, as well as in feasibleTrajectories if the distance traveled is within
    * the compound walls. It takes no input and returns no output, instead it modifies the class variables directly.
     */
    public void calculateAllTrajectories(){
        initializeTrajectories();
        for (int i=0; i < speeds.length; i++){
            for (int j=0; j < angles.length; j++){
                trajectories[i][j] = calculateTrajectory(angles[j], speeds[i]);
                if (minDistance < trajectories[i][j] && trajectories[i][j] < maxDistance){
                    feasibleTrajectories.add(Math.round(trajectories[i][j]));
                    int[] anglesAndSpeed = {angles[j], speeds[i]};
                    feasibleAnglesAndSpeeds.add(anglesAndSpeed);
                }
            }
        }
    }
}
