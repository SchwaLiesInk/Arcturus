/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Gerwyn Jones
 */
class DetectorResult {

    Point at;
    Length dif;
    MapElement obj;
    CollisionDetector objPart;
    CollisionDetector withPart;
    MapElement with;
    DetectorResult next;

    DetectorResult(float atx, float aty, Length dif, MapElement obj, CollisionDetector objPart, MapElement with, CollisionDetector withPart) {
        this.dif = dif;
        this.at = new Point(atx, aty);
        this.obj = obj;
        this.objPart = objPart;
        this.with = with;
        this.withPart = withPart;
    }
}

class MoveResult {

    Acceleration acc;
    AngularAcceleration aa;
    MapElement obj;
    MoveResult next;
    boolean update;
    boolean waiting;

    protected MoveResult() {
        this.aa = new AngularAcceleration(0);
        this.acc = new Acceleration(new Vector(0, 0));
        obj = null;
    }

    MoveResult(float a, float ax, float ay, MapElement obj) {
        this.aa = new AngularAcceleration(a);
        this.acc = new Acceleration(new Vector(ax, ay));
        this.obj = obj;
    }

    boolean Update() {
        return update;
    }
}

public class CollisionDetector extends Point implements InterAction {

    public static final short STATE_FREE = 0;
    public static final short STATE_COLLIDING = 1;
    public static final short STATE_HIT = 2;
    public static final short STATE_COLLIDED = 3;
    public static final short STATE_WAS_HIT = 4;
//////////////////////////////////////
    private short state = 0;
    public short lastState = 0;
    short index;
    Point collisionPoint;
    Vector at;
    Length radius;
    Mass massPoint;

    ///////////////////////////////// 
    public CollisionDetector() {
        this.x = 0;
        this.y = 0;
        this.r = 1;
    }

    public CollisionDetector(Point at) {
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = at.r;
    }

    public CollisionDetector(int x, int y, int zone) {
        this.x = x;
        this.y = y;
        this.r = zone;
    }

    public CollisionDetector(double x, double y, double zone) {
        this.x = (x);
        this.y = (y);
        this.r = (int) (zone);
    }

    public CollisionDetector(float mass, int i, float x, float y, int r) {
        index = (short) i;
        this.collisionPoint = new Point(x, y);
        at = new Vector(x, y);
        massPoint = new Mass(this.collisionPoint, mass, r);
        radius = new Length(r);
    }

    public CollisionDetector(Point p, float mass) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        this.r = p.r;
        at = new Vector(x, y);
        massPoint = new Mass(p, mass, (float) r);
        radius = new Length((float) r);
    }

    public CollisionDetector(Point p, float mass, float rad) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        this.r = rad;
        at = new Vector(x, y);
        massPoint = new Mass(p, mass, rad);
        radius = new Length(rad);
    }

///////////////////////////////////////// COMPARABLE
    public boolean equals(Object obj) {

        return false;
    }

    public int compareTo(Object obj) {
        return 0;
    }
//////////////////////////////////////////////

    public static short stateLogic(short states) {
//this.lastState=states;
        if (states == STATE_FREE) {
            return STATE_FREE;
        }
        if (states == STATE_WAS_HIT) {
            return STATE_FREE;
        } else {
            return states++;
        }
        //return this.state;
    }

    public short stateLogic() {
        this.lastState = state;
        if (this.state == STATE_FREE) {
            return STATE_FREE;
        }
        if (this.state == STATE_WAS_HIT) {
            return STATE_FREE;
        } else {
            return this.state++;
        }
        //return this.state;
    }

    public static CollisionDetector getCollisionDetector(Point at) {
        return new CollisionDetector(at);
    }

    public MouseArea collisionToMouseArea() {
        return new MouseArea((int) (this.x - this.r), (int) (this.y - this.r), (int) (this.r), (int) (this.r), "NEW");
    }

    public static MouseArea collisionToMouseArea(CollisionDetector col) {
        return new MouseArea((int) (col.x - col.r), (int) (col.y - col.r), (int) (col.r), (int) (col.r), "NEW");
    }

    public void setup(int state) {
    }

    public void setup() {
    }

    public double getScale() {
        return 0;
    }

    public double getRange() {
        return 0;
    }

    public double getVeiw() {
        return 0;
    }

    public double[][] getVeiwMatrix() {
        return new double[4][4];
    }
/////////////////////////////////////////////////////

    public boolean testSphereZone(CollisionDetector test) {
        if (Collision(r, test, test.r)) {
            this.state = this.STATE_HIT;
            return true;
        } else {
            this.stateLogic();
            return false;
        }
    }

    public boolean testRoundZone(CollisionDetector test) {
        if (Collision(r, test, test.r)) {
            this.state = this.STATE_HIT;
            return true;
        } else {
            this.stateLogic();
            return false;
        }

    }

    public boolean testCircleZone(CollisionDetector test) {
        if (Collision(r, test, test.r)) {
            this.state = this.STATE_HIT;
            return true;
        } else {
            this.stateLogic();
            return false;
        }

    }

    public boolean testSquareZone(CollisionDetector test) {
        if (test.Collides2D(this, r + test.r)) {
            this.state = this.STATE_HIT;
            return true;
        } else {
            this.stateLogic();
            return false;
        }

    }

    public boolean testBounceZone(CollisionDetector test) {
        if (Collision(r, test, test.r)) {
            this.state = this.STATE_HIT;
            return true;
        } else {
            this.stateLogic();
            return false;
        }
    }

// <editor-fold defaultstate="collapsed" desc="Interfaces">
    public void active() {
        switch (this.state) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
            }
            default: {
                this.stateLogic();
                break;
            }
        }
    }

    public void reset() {
        return;
    }

    public void active(short stat) {
        //this.state=stat;
        switch (stat) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {//  break;
            }
            default: {
                this.stateLogic();
                break;
            }
        }
    }

    public void reset(short state) {
        this.state = state;
    }

// public void reset(SpaceTime st,CollisionDetector detect){}
// public void active(SpaceTime st,CollisionDetector detect){}
    //public final void start(){this.run();}
    public void print() {
        System.out.println(this.toString());
        //OVERIDE

    }

    public void paint(Graphics2D g) {

        //OVERIDE
    }
    /*public void run(){
     try{
     this.active();
     this.sleep(1);
     }catch(Exception e){System.out.println("EX"+e);this.reset();return;}
     }*/
    ////////////////////////////
    /////////////////////////////////////

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    public void print(CollisionDetector collision, CollisionDetector collide) {
    }

    //
    public void active(CollisionDetector collision) {
    }

    public void reset(CollisionDetector collision) {
    }

    //
    public void active(short state, CollisionDetector collision) {
    }

    public void reset(short state, CollisionDetector collision) {
    }

    //
    public void mouseAction(MouseEvent me) {
    }

    public void collision(CollisionDetector cd) {

        switch (this.state) {
            case 0: {

                switch (cd.state) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (cd.state) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (cd.state) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (cd.state) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                switch (cd.state) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }

    }

    DetectorResult Detect(MapElement me, MapElement with) {
        if (me != null) {
            DetectorResult result = me.effect;
            DetectorResult detected = result;
            float cx1 = (float) (this.at.x + with.x);
            float cy1 = (float) (this.at.y + with.y);
            float r1 = this.radius.Value() * this.radius.Value();
            //System.out.println((me.detector==null)+" "+me.getClass().toString());
            for (int c = 0; c < me.detector.length; c++) {
                float r2 = me.detector[c].radius.Value() * me.detector[c].radius.Value();
                float x1 = (float) (me.x + me.detector[c].at.x) - cx1;
                float y1 = (float) (me.y + me.detector[c].at.y) - cy1;
                float cd1 = (x1 * x1) + (y1 * y1);
                float dis = r1 + r2;
                float cd = 0;
                float x = 0;
                float y = 0;
                if (cd1 < dis) {
                    x = x1;
                    y = y1;
                    cd = cd1;
                }
                if (x != 0 && y != 0) {
                    Length dif = new Length((float) ((1 + Math.sqrt(dis - cd))));
                    //System.out.println(dif);
                    if (result == null) {
                        //float s=(float)(Math.sqrt(cd));
                        result = new DetectorResult(x, y, dif, me, me.detector[c], with, this);
                        detected = result;

                    } else {
                        while (detected.next != null) {
                            detected = detected.next;
                        }
                        //float s=(float)(Math.sqrt(cd));
                        detected.next = new DetectorResult(x, y, dif, me, me.detector[c], with, this);
                        detected = detected.next;
                    }
                }
            }

            return result;
        } else {
            return null;
        }
    } // </editor-fold>
}
