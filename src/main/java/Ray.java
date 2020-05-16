import processing.core.PApplet;
import processing.core.PVector;

public class Ray {
    static PApplet p;
    PVector start, stop;
    PVector contactPoint;
    float distOfIntersection;
    float distOfSigh;

    public Ray(float x1, float y1, float x2, float y2, float distOfSigh){
        start = new PVector(x1, y1);
        stop = new PVector(x2, y2);
        this.distOfSigh = distOfSigh;
        contactPoint = stop;
    }


    /**
     *
     * @param object examined object
     * @return distance for point of intersection
     *  use correct method to calculate intersection based on type of the object
     */
    public float computeIntersection(Obj object){
        if(object instanceof CircleObj){
            computeIntersection((CircleObj) object);
        } else if(object instanceof LineObj){
            computeIntersection((LineObj) object);
        } else if(object instanceof Corner){
            computeIntersection((Corner) object);
        }

        return distOfIntersection;
    }

    public void computeIntersection(CircleObj circle){
        double baX = stop.x - start.x;
        double baY = stop.y - start.y;
        double caX = circle.position.x - start.x;
        double caY = circle.position.y - start.y;
        double radius = circle.diameter/2;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;
        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            // no contact
            contactPoint = stop;
            distOfIntersection = distOfSigh;
            return;
        }

        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        PVector p1 = new PVector((float) (start.x - baX * abScalingFactor1), (float) (start.y - baY * abScalingFactor1));
        if (disc == 0) {
            contactPoint = p1;
            if(contactPoint.dist(start) > distOfSigh){
                // no contact
                contactPoint = stop;
                distOfIntersection = distOfSigh;
            }
        }
        PVector p2 = new PVector((float) (start.x - baX * abScalingFactor2) , (float) (start.y - baY * abScalingFactor2));
        if(p2.dist(start) > p1.dist(start)){
            contactPoint = p1;
        } else contactPoint = p2;

        if(contactPoint.dist(start) > distOfSigh || contactPoint.dist(stop) > distOfSigh){
            // no contact
            contactPoint = stop;
            distOfIntersection = distOfSigh;
        }

        distOfIntersection = start.dist(contactPoint);
    }

    public void computeIntersection(LineObj line){
        contactPoint = stop;
        distOfIntersection = distOfSigh;

        float x1 = line.end1.x;
        float y1 = line.end1.y;
        float x2 = line.end2.x;
        float y2 = line.end2.y;
        float x3 = start.x;
        float y3 = start.y;
        float x4 = start.x + stop.x;
        float y4 = start.y + stop.y;

        float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (den == 0) {
            // no contact
            return;
        }

        float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;

        if (t >= 0 && t <= 1 && u >= 0) {
            PVector pt = new PVector();
            pt.x = x1 + t * (x2 - x1);
            pt.y = y1 + t * (y2 - y1);
            contactPoint = pt;
            if(contactPoint.dist(start) > distOfSigh){
                contactPoint = stop;
            }
            distOfIntersection = start.dist(contactPoint);

        } else {
            // no contact
            return;
        }
    }

    public void computeIntersection(Corner corner){
        float minDist = distOfSigh;
        PVector contactPoint = new PVector();
        computeIntersection(corner.line1);
        if(distOfIntersection <minDist){
            minDist = distOfIntersection;
            contactPoint = this.contactPoint;
        }

        computeIntersection(corner.line2);
        if(distOfIntersection <minDist){
            minDist = distOfIntersection;
            contactPoint = this.contactPoint;
        }

        distOfIntersection = minDist;

        if(distOfIntersection == distOfSigh){
            contactPoint = stop;
        }

        this.contactPoint = contactPoint;

    }

    public void computeIntersection(){
        contactPoint = stop;
        distOfIntersection = distOfSigh;
    }


}
