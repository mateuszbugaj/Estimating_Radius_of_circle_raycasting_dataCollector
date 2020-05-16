import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    public void settings(){
        size(800,900);
    }

    Shape[] shapes;
    int scrollVariable = 0;
    RayManager rayManager;
    NeuralNetwork nn;
    Obj object;

    public void setup(){
        ObjClass.p = this;
        RayManager.p = this;
        textSize(20);

        nn = new NeuralNetwork("radius_position_nn_a.h5");
        rayManager = new RayManager(new PVector(0, 0), 200, 120, 500);
        shapes = new Shape[]{Shape.Circle};
        object = shapes[new Random().nextInt(shapes.length)].Object(0, 500, 500);

        /*
        Function for collecting data
         */
//        collectData(80_000, "circle_radius_and_pos", Shape.Circle);
    }

    /**
     * Main logic
     * Calculating contact points and generating predictions as well as manipulating object and rays
     */
    public void draw(){
        background(255);
        pushMatrix();
        translate(0, height/2f);

        object.manipulate(mouseX , mouseY-height/2f, scrollVariable, rayManager.startingPoint);

        scrollVariable = 0;

        float[] rayCastingData = rayManager.calculateInterSections(object);
        double[] output = nn.predict(rayCastingData);
        CircleObj predictedCircle = new CircleObj((float) output[0], (float) output[1], (float) output[2]);

        rayManager.show();
        object.show(60, 92, 29); // green-ish original object

        translate(rayManager.startingPoint.x, rayManager.startingPoint.y);
        predictedCircle.show(128, 23, 23); // red-ish predicted object

        rayManager.updateStartingPoint(mouseX, mouseY-height/2f);
        popMatrix();

        fill(0);
        text(object.featuresExtended(), 10, 40);
        text(String.format("Center angle: %.2f | distance: %.2f | diameter: %.2f - PREDICTION", (float) output[0], (float) output[1], (float) output[2]), 10, 60);
    }


    /**
     *
     * @param numEntries number of entries to collect in file
     * @param fileName name of the file where collected entries will be stored
     * @param shapes set of shapes available for random generating
     *  Set up logger and generate new object from available set for every entry;
     *  perform ray-casting, create entry line and log data
     */
    public void collectData(int numEntries, String fileName, Shape... shapes){
        System.out.println("Generating random objects between: " + Arrays.toString(shapes) + " " + numEntries + " times ");
        Logger logger = new Logger(fileName, numEntries).notifyAfterEvery(1000).setActive(true);
        logger.clearFile();



        for (int entryIndex = 0; entryIndex < numEntries; entryIndex++) {
            Obj object = shapes[new Random().nextInt(shapes.length)].Object();
            String entry = object.features();

            for (Ray ray : rayManager.rays) {
                ray.computeIntersection(object);
                entry = entry.concat(" " + ray.distOfIntersection);
            }

            entry = entry.concat("\n");
            logger.addNewEntry(entry);
        }
    }


    public void mouseWheel(MouseEvent event) {
        scrollVariable = event.getCount();
    }

}


