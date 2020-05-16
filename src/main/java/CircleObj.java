import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

public class CircleObj extends ObjClass implements Obj{
    //static PApplet p;
    PVector position = new PVector();
    float diameter;
    float centerAngle;
    float dist;

    boolean isBeingManipulated = false;

    public CircleObj(){

    }

    /**
     *
     * @param pos position on cartesian coordinates
     * @param d diameter
     */
    public CircleObj(PVector pos, float d){
        position.set(pos);
        diameter = d;
    }

    /**
     *
     * @param angle angle in polar coordinates
     * @param dist dist in polar coordinates
     * @param d diameter
     */
    public CircleObj(float angle, float dist, float d){

        // calculate cartesian coordinates for displaying and computing purposes
        float xPos = (float) (Math.cos(Math.toRadians(angle))*dist);
        float yPos = (float) (Math.sin(Math.toRadians(angle))*dist);
        centerAngle = angle;
        position.set(xPos, yPos);
        diameter = d;
        this.dist = dist;
    }

    /**
     *
     * @param rgb color of stroke
     */
    public void show(int... rgb){
        p.noFill();
        p.strokeWeight(3);
        p.stroke(rgb[0], rgb[1], rgb[2]);
        p.ellipse(position.x, position.y, diameter, diameter);
        //p.line(0,0, position.x, position.y);
    }


    public Obj randomize(float centerAngleMin, float centerAngleMax, float diameterMin, float diameterMax, float distMin, float distMax){
        float randomCenterAngle = p.random(centerAngleMin, centerAngleMax);
        float randomDiameter = p.random(diameterMin, diameterMax);
        float randomRadius = randomDiameter/2;
        float randomDist = p.random(distMin+randomRadius, distMax+randomRadius);
        float xPos = (float) (Math.cos(Math.toRadians(randomCenterAngle))*randomDist);
        float yPos = (float) (Math.sin(Math.toRadians(randomCenterAngle))*randomDist);
        position.set(xPos, yPos);
        diameter = randomDiameter;
        centerAngle = randomCenterAngle;
        dist = randomDist;

        return this;
    }

    @Override
    public String features() {
        return  centerAngle + " " + dist + " " + diameter;
    }

    @Override
    public String featuresExtended() {
        return  String.format("Center angle: %.2f | distance: %.2f | diameter: %.2f", centerAngle, dist, diameter);
    }

    /**
     *
     * @param mouseX mouse position X
     * @param mouseY mouse position Y
     * @param scroll scroll variable
     * @param rayStartingPoint starting point of rays
     *  If mouse is above starting point and button is pressed, update starting point to be mouse position;
     *  update center angle and distance;
     *  display circle as GUI element
     */
    @Override
    public void manipulate(float mouseX, float mouseY, int scroll, PVector rayStartingPoint) {
        int grabyCircleDiameter = 30;
        p.fill(0);

        boolean buttonPressed = p.mousePressed;
        if(mouseX < position.x + 15 && mouseX > position.x - 15 && mouseY < position.y + 15 && mouseY > position.y - 15){
            if(buttonPressed){
                isBeingManipulated = true;
            }
            diameter += scroll*20;
            grabyCircleDiameter = 50;
        }

        if(buttonPressed == false){
            isBeingManipulated = false;
        }

        if(isBeingManipulated){
            position.set(mouseX, mouseY);

        }

        p.stroke(0);
        p.ellipse(position.x, position.y, grabyCircleDiameter, grabyCircleDiameter);

        centerAngle = p.atan2(rayStartingPoint.y - position.y, rayStartingPoint.x - position.x);
        centerAngle = p.degrees(centerAngle - (centerAngle<0?-p.PI:p.PI));
        dist = rayStartingPoint.dist(position);

    }

}
