// Alejandro Mijares
// February 19, 2023
// Panther ID: 3145563
// Program Version: 1.0
// Java Version: 8
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class TrebuchetTester {
    /**
     * Main function that opens the source file and calls the parsing function
     * If loop checks for file names that have been passed. If none are found, the default file is parsed.
     * @param args Can accept a String array of file names to parse
     * @throws IOException will be thrown in case of error in file manipulation
     */
    public static void main(String[] args)throws IOException {
        if (args.length > 0){
            for (int i=0; i < args.length; i++){
                File fileName = new File(args[i]);
                Scanner inFile = new Scanner(fileName);
                processFile(inFile);
            }
        }
        else{
            File fileName = new File("catapult - test data.txt");
            Scanner inFile = new Scanner(fileName);
            processFile(inFile);
        }
    }

    /**
     * File parsing function which contains the logic for traversing the given file format.
     * Trebuchet count                    2
     * (Quantity) Initial Velocities     (5) 10 20 30 40 50
     * (Quantity) Launch Angles          (8) 10 20 30 40 50 60 70 80
     * Min/Max Distance of Target        100 300
     * (Quantity) Initial Velocities     8 10 15 20 25 30 35 40 45
     * (Quantity) Launch Angles          8 10 15 20 25 30 35 40 45
     * Min/Max Distance of Target        10 1000
     * @param inFile Source file in above format
     */
    private static void processFile(Scanner inFile){
        while (inFile.hasNext()){
            int indexOfTrebuchets = inFile.nextInt();
            for (int i=0; i < indexOfTrebuchets; i++){
                Trebuchet trebuchet = new Trebuchet();

                trebuchet.setSpeedCount(inFile.nextInt());
                for (int j=0; j < trebuchet.getSpeedCount(); j++){
                    trebuchet.addSpeed(j, inFile.nextInt());
                }

                trebuchet.setAngleCount(inFile.nextInt());
                for (int j=0; j < trebuchet.getAngleCount(); j++){
                    trebuchet.addAngle(j, inFile.nextInt());
                }

                trebuchet.setMinDistance(inFile.nextInt());
                trebuchet.setMaxDistance(inFile.nextInt());
                trebuchet.calculateAllTrajectories();
                printTrebuchetStats(trebuchet);
            }
        }
    }

    /**
     * Prints trebuchet statistics to the console
     * @param trebuchet Object created from given parameters in source file
     */
    private static void printTrebuchetStats(Trebuchet trebuchet){
        System.out.println("                                    Projectile Table");
        System.out.println("--------------------------------");
        System.out.println("Note: Angles between 180 and 360 will fire into the ground, and have a distance of 0");
        System.out.println("Support for floating or flying trebuchets is under consideration for the next patch\n");
        System.out.print("Speeds || Angles");

        for (int i=0; i<trebuchet.getAngleCount();i++){
            System.out.printf("        %2d deg", trebuchet.getAngle(i));
        }
        System.out.print("\n");

        for (int i=0; i<trebuchet.getSpeedCount();i++){
            System.out.printf("%2d m/s          ",trebuchet.getSpeed(i));
            for (int j=0; j<trebuchet.getTrajectoriesColumnWidth(i);j++){
                System.out.printf("%14.3f", trebuchet.getTrajectory(i,j));
            }
            System.out.print("\n");
        }

        System.out.println("--------------------------------");
        System.out.printf("Best Trajectory values for a target [%2d:%2d] meters away:%n [Distance m (Angle deg, Speed m/s)]%n",trebuchet.getMinDistance(), trebuchet.getMaxDistance());
        if (trebuchet.getFeasibleTrajectoryCount() > 0) {
            for (int i = 0; i < trebuchet.getFeasibleTrajectoryCount(); i++) {
                System.out.printf("%2d", trebuchet.getFeasibleTrajectories(i));
                System.out.printf("(%2d,%2d) ", trebuchet.getFeasibleAngle(i), trebuchet.getFeasibleSpeed(i));
                if (i>1 && i%10 == 0){
                    System.out.print("\n");
                }
            }
            System.out.print("\n");
        }
        else {
            System.out.println("We cannot recommend firing on the target with the given parameters.");
        }
        System.out.println("--------------------------------\n\n");
    }
}