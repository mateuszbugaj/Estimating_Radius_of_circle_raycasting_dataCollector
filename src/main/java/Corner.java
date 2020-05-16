import processing.core.PApplet;
import processing.core.PVector;

public class Corner extends ObjClass implements Obj{
    //static PApplet p;
    PVector centerPosition = new PVector();
    float angleBetweenLines;
    float relativeAngle;
    LineObj line1, line2;

    public Corner(){

    }

    public Corner(float centerAngle, float dist, float angleBetweenLines, float relativeAngle){
        float xPos = (float) (Math.cos(Math.toRadians(centerAngle))*dist);
        float yPos = (float) (Math.sin(Math.toRadians(centerAngle))*dist);
        centerPosition.set(xPos, yPos);
        this.angleBetweenLines = angleBetweenLines;
        this.relativeAngle = relativeAngle;

        line1 = new LineObj(centerPosition, (angleBetweenLines/2) + relativeAngle);
        line2 = new LineObj(centerPosition, (-angleBetweenLines/2) + relativeAngle);

    }

    public void show(int... rgb){
        line1.show();
        line2.show();
    }

    public Obj randomize(float centerAngleMin, float centerAngleMax, float distMin, float distMax, float relativeAngleMin, float relativeAngleMax){
        float randomCenterAngle = p.random(centerAngleMin, centerAngleMax);
        float randomDist = p.random(distMin, distMax);float xPos = (float) (Math.cos(Math.toRadians(randomCenterAngle))*randomDist);
        float yPos = (float) (Math.sin(Math.toRadians(randomCenterAngle))*randomDist);
        centerPosition.set(xPos, yPos);
        this.angleBetweenLines = p.random(20, 120);
        this.relativeAngle = p.random(relativeAngleMin, relativeAngleMax);
        line1 = new LineObj(centerPosition, (angleBetweenLines/2) + relativeAngle);
        line2 = new LineObj(centerPosition, (-angleBetweenLines/2) + relativeAngle);

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
