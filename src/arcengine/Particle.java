/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Gerwyn Jones
 */
class ParticleDisplacer extends Point implements VectorStateFunction {

    ParticleDisplacer() {
        r = 10;
    }

    @Override
    public Vector fv(double x, double y, double z, double w) {
        return new Vector(x * r * Math.sin(w), y, z * r * Math.cos(w));
    }

}

class ParticleIterator implements OneStateFunction {

    @Override
    public double f(double x) {
        return (x / Particle.b);
    }
}

class ParticleResult implements OneStateFunction {

    double dim = 4.0;
    double entropy = 4.5;

    ParticleResult(double d, double en) {
        dim = d;
        entropy = en;
    }

    @Override
    public double f(double x) {
        return entropy / (x * dim / 3.0);
    }
}

public abstract class Particle extends MapElement implements OneStateFunction {

    static int b = 150;
    static int PARTICLES = 100;
    Time entropy;
    double stage = 0;
    double from = 0;
    double to = 0;
    double stagePoint = 0;
    //
    Vector direct;
    Vector velocity;
    //
    double splash = 3;
    double anglular = 0;
    double decay = 0.1;
    double gravity = 0.01;
    double spread = 1;
    double travelled = 0;
    double travels = b;
    double scale = 0.01;
    //
    double a = 1;//potential energy start (symetrical)
    double normal = -10;//normalizing constant(fire) chaos if negative -e(air preasure)
    //
    Point startingPoint;
    ParticleIterator iterate = new ParticleIterator();
    ParticleResult result;
    ParticleDisplacer placer = new ParticleDisplacer();
    NumberSequance ns;
    IntegralOperation in;
    //

    DetectorResult Detect(MapElement me) {

        return null;
    }

    @Override
    GameObjectType Type() {
        return GameObjectType.PARTICLE;
    }

    boolean RunSimulation() {
        return false;
    }

    void Collision(DetectorResult result, float dx, float dy) {

    }

    void paint(Graphics g, int osx, int osy) {
    }

    Particle(Point start, float m, float spd) {
        super();
        speed = new Speed(spd);
        this.x = start.x;
        this.y = start.y;
        this.z = start.z;
        this.r = start.r;
        this.mass = new Mass(start, m, (float) start.r);
        direct = new Vector(Random.NextDouble() - Random.NextDouble(), Random.NextDouble() - Random.NextDouble(), Random.NextDouble() - Random.NextDouble());
        direct.Normalize();
        velocity = new Vector();
        ns = new NumberSequance(new Double[]{
            (double) 0.0
        }
        );
        result = new ParticleResult(1 + (1 + Random.Next(4)) * Random.NextDouble(), 1 + (1 + Random.Next(5)) * Random.NextDouble());

        this.startingPoint = start;//.AsPoint(r);
        in = new IntegralOperation(a, b + 0.2, normal);
        in.SetVariables(ns);
        in.OperateUsing((OneStateFunction) this);//scale function
        in.iterate = iterate;//scaler
        in.resultant = result;
        splash = 1;

    }

    double Move(double t, double lim, double dampen, double spin) {
        Matrix rot = new Matrix();
        rot = rot.RotateY(spin * t);
        anglular += spin * t;
        direct = rot.Mult(rot, direct);
        //
        double acc = ac(t);
        double acv = scale * acc / mass.Value() * t;
        Vector i = direct.Scaled(acv * speed.Value());
        Vector g = new Vector(0, scale, 0).Scaled(gravity * t);
        Vector p = new Vector(0, -scale, 0).Scaled(acv * splash * t);
        velocity = velocity.Add(velocity, i).AsVector();
        velocity = velocity.Add(velocity, g).AsVector();
        velocity = velocity.Add(velocity, p).AsVector();
        if (placer != null) {
            velocity = velocity.Add(velocity, placer.fv(acv, i.y, acv, anglular)).AsVector();
        }
        velocity = velocity.Scaled(1.0 - dampen);
        Vector displace = velocity.Scaled(t);
        //
        Point at = (Add(this, displace)).AsPoint(this.r - travelled * decay * t);
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = at.r;
        //System.out.println(this.x+" "+this.y+" "+this.z+" "+this.r);
        //
        double r = local.Length();
        double v = velocity.Length();
        splash += spread / (gravity * mass.Value() / this.r);//
        //
        travelled += v * t;
        if (travelled > travels || r > lim || this.r < 1.0) {
            direct = new Vector(Random.NextDouble() - Random.NextDouble(), Random.NextDouble() - Random.NextDouble(), Random.NextDouble() - Random.NextDouble());
            direct.Normalize();
            velocity = new Vector();
            this.x = startingPoint.x;
            this.y = startingPoint.y;
            this.z = startingPoint.z;
            this.r = startingPoint.r;
            in.ResetState();
            splash = 1;
            travelled = 0;
        }
        return r;
    }

    @Override
    public double f(double x) {
        return (1.0 / x - x * (1.0 - 1.0 / b));
    }

    double ac(double t) {

        if (stagePoint == 0) {
            in.LoopSum(t);
            to = in.results.First();
            stage = from;
        }
        if (stagePoint < 1.0 + t) {
            stage = Maths.lerp(stagePoint, from, to);
            stagePoint += t;
        } else {
            from = to;
            stagePoint = 0;
        }
        if (in.results.First() != null) {
            return in.results.First() * energy;
        } else {
            return 0;
        }
    }

    void Print() {

        int i = 0;
        do {
            double t = 0.1;//(double)(i+1)/(b+(1+i));
            in.LoopSum(t);//energy system
            double sys = (double) (i / b);
            System.out.println(sys + " Result " + in.a + " << \t" + in.results.ToString(10));
            if (stagePoint == 0) {
                to = in.results.First();
                stage = from;
            }
            while (stagePoint < 1.0 + t) {
                //System.out.println(" Operation "+point+" Lerp "+at);
                stage = Maths.lerp(stagePoint, from, to);
                stagePoint += t;
            }
            from = to;
            stagePoint = 0;
            /*if(in.results.First()<0.1){
             in.a=Random.NextDouble();//add fuel
             in.c=-Random.NextDouble();
             }
             //in.c-=t;//fire entropy
             //System.out.println(sys+" Rate Of Change "+in.c+" >> " + in.changes.ToString(10));
             */
            i++;
        } while (!in.Finalize());
    }
}

abstract class Shot extends Particle {

    Shot(Point at) {
        super(at, 1, 1);
    }
}

class MachineShot extends Shot {

    public MachineShot(float x, float y, float angle, int f, int e, float ms) {
        super(new Point(x, y));
        this.v = new Velocity(0, 0);
        this.mass = new Mass(new Point(0, 0), ms, 1);
        this.energy = e;
        this.force = f;
        this.entropy = new Time(e);
    }

    boolean RunSimulation() {

        if (this.energy > 0) {
            float e = (float) (this.energy * Game.time.AnimTime());

            this.v.Value().x = (float) (e * Math.cos(angle.phi));
            this.v.Value().y = (float) (e * Math.sin(angle.phi));
            this.x += v.Value().x;
            this.y += v.Value().y;
            this.entropy.t -= e;
            if (this.entropy.t < 0) {
                this.energy--;
                this.entropy.t = energy;
            }
            return true;
        }
        return false;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        g.setColor(Color.red);
        g.drawLine(osy, osy, osy, osy);
    }

    DetectorResult Detect(java.util.ArrayList<MapElement> sec) {
        for (int i = 1; i < sec.size(); i++) {
            MapElement me = sec.get(i);

            if (me != this) {

                DetectorResult result = null;
                DetectorResult detected = null;
                for (int d = 0; d < this.detector.length; d++) {
                    if (result == null) {
                        result = detector[d].Detect(me, this);
                        detected = result;
                    } else {
                        while (detected.next != null) {
                            detected = detected.next;
                        }

                        DetectorResult test = detector[d].Detect(me, this);

                        if (test != null) {
                            detected.next = test;
                            detected = detected.next;
                        }
                    }

                }
                //if(result!=null){
                //System.out.println("Detect"+result+me.getClass());
                //}
                return result;
            }
        }
        return null;
    }

    boolean CollisionSimulation(MapElement with) {
        return false;
    }

    void Collision(DetectorResult with) {
        //CarRage.square.MoveElement(this, dx, dy);
        //System.out.println(with!=null);

        //System.out.println("obj "+with.obj.local+with.obj.getClass().toString());
        //System.out.println("collided "+with.with.local+with.with.getClass().toString());
        //System.out.println("at "+with.atx+" "+with.aty);
        this.effect = with;
    }
}
