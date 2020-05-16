import processing.core.PApplet;
import processing.core.PVector;

public interface Obj {
    public void show(int... rgb);
    public Obj randomize(float centerAngleMin, float centerAngleMax, float centerDistMin, float centerDistMax, float v5, float v6);
    public String features();
    public String featuresExtended();
    public void manipulate(float mouseX, float mouseY, int scroll, PVector rayStartingPoint);
}
