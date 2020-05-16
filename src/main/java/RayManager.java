import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class RayManager {
    static PApplet p;
    ArrayList<Ray> rays = new ArrayList<>();
    PVector startingPoint;
    float degreesToCover;
    float distOfSigh;
    int numRays;
    boolean isBeingManipulated = false;

    /**
     *
     * @param startingPoint starting point for rays
     * @param numRays number of rays
     * @param degreesToCover degrees to cover with rays in degrees
     * @param distOfSigh distance of sigh for each ray
     */
    public RayManager(PVector startingPoint, int numRays, float degreesToCover, float distOfSigh) {
        this.startingPoint = startingPoint;
        this.numRays = numRays;
        this.degreesToCover = degreesToCover;
        this.distOfSigh = distOfSigh;

        createRays();
    }

    /**
     * Create rays at initialization or if starting point has changed
     */
    public void createRays(){
        rays.clear();
        for (int i = 0; i < numRays; i++) {
            float angle = -degreesToCover/2f + (degreesToCover/numRays)*i;
            PVector rayStop = new PVector(startingPoint.x + cos(radians(angle))* distOfSigh, startingPoint.y + sin(radians(angle))* distOfSigh);
            rays.add(new Ray(startingPoint.x, startingPoint.y, rayStop.x, rayStop.y, distOfSigh));
        }
    }

    /**
     *
     * @param mouseX mouse position X
     * @param mouseY mouse position Y
     *  If mouse is above starting point and button is pressed, update starting point to be mouse position;
     *  generate new set of rays;
     *  display circle as GUI element
     */
    public void updateStartingPoint(float mouseX, float mouseY){
        int grabyCircleDiameter = 20;
        p.fill(0);

        boolean buttonPressed = p.mousePressed;
        if(mouseX < startingPoint.x + 10 && mouseX > startingPoint.x - 10 && mouseY < startingPoint.y + 10 && mouseY > startingPoint.y - 10){
            if(buttonPressed){
                isBeingManipulated = true;
            }
            grabyCircleDiameter = 30;
        }

        if(buttonPressed == false){
            isBeingManipulated = false;
        }

        if(isBeingManipulated){
            startingPoint.set(mouseX, mouseY);
            createRays();
        }

        p.stroke(0);
        p.ellipse(0, 0, grabyCircleDiameter, grabyCircleDiameter);
    }

    public void show(){
        p.strokeWeight(1);
        p.stroke(180);

        for(Ray ray:rays){
            p.line(ray.start.x, ray.start.y, ray.contactPoint.x, ray.contactPoint.y);
        }
    }

    /**
     *
     * @param object examined object
     * @return distance for points of intersection for each ray
     */
    public float[] calculateInterSections(Obj object){
        float[] rayCastingData = new float[rays.size()];
        for (int i = 0; i < rays.size(); i++) {
            rayCastingData[i] = rays.get(i).computeIntersection(object);
        }
        return rayCastingData;
    }


}
