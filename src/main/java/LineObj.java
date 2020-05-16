import processing.core.PApplet;
import processing.core.PVector;

public class LineObj extends ObjClass implements Obj{
    //static PApplet p;
    PVector centerPosition = new PVector();
    float angle;
    PVector end1, end2;
    float len = 600;

    public LineObj(){

    }

    /**
     *
     * @param centerAngle angle of center of the line in polar coordinates
     * @param dist distance of center of the line in polar coordinates
     * @param angle angle which line is rotated relative to agent
     */
    public LineObj(float centerAngle, float dist, float angle){
        float xPos = (float) (Math.cos(Math.toRadians(centerAngle))*dist);
        float yPos = (float) (Math.sin(Math.toRadians(centerAngle))*dist);

        centerPosition.set(xPos, yPos);
        this.angle = angle;
        calculateEdgePoints();
    }

    public LineObj(PVector start, float angle){
        end1 = start;
        end2 = new PVector(end1.x + p.cos(p.radians(angle))*len, end1.y + p.sin(p.radians(angle))*len);
    }

    public void calculateEdgePoints(){
        end1 = new PVector(centerPosition.x - p.cos(p.radians(180-angle))*(len/2),
               centerPosition.y + p.sin(p.radians(180-angle))*(len/2));

        end2 = new PVector(centerPosition.x - p.cos(p.radians(angle))*(len/2),
                centerPosition.y - p.sin(p.radians(angle))*(len/2));
    }

    public void show(int... rgb){
        p.stroke(0);
        p.line(end1.x, end1.y, end2.x, end2.y);
    }

    public Obj randomize(float centerAngleMin, float centerAngleMax, float distMin, float distMax, float angleMin, float angleMax){
        float randomCenterAngle = p.random(centerAngleMin, centerAngleMax);
        float randomDist = p.random(distMin, distMax);
        float randomAngle = p.random(angleMin, angleMax);
        float xPos = (float) (Math.cos(Math.toRadians(randomCenterAngle))*randomDist);
        float yPos = (float) (Math.sin(Math.toRadians(randomCenterAngle))*randomDist);
        centerPosition.set(xPos, yPos);
        this.angle = randomAngle;
        calculateEdgePoints();

        return this;
    }

    @Override
    public String features() {
        return " ";
    }

    @Override
    public String featuresExtended() {
        return " ";
    }

    @Override
    public void manipulate(float mouseX, float mouseY, int scroll, PVector rayStartingPoint) {

    }
}
