/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn Jones
 */
class Debri extends Particle implements GraphicsUpdate {

    Debri(Point at, float spd) {
        super(at, 0.1f, spd);
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        return null;
    }

    @Override
    void Collision(DetectorResult result) {
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        return false;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {

    }

    @Override
    public void Update(GameTime time, Camera cam) {
        Move(time.cameraTime, travels, 0.02, 0);
    }

    void Render(Screen scr, Pigment c) {
        Point result = new Point();
        //(int)(this.travelled/(1+travels)*255)
        Pigment p = new Pigment(64, c.Red(), c.Green(), c.Blue());
        //System.out.println("exp "+x+" "+y+" "+z+" ");
        boolean ok = scr.LoadVertexPoint(this, p, null, 1.0, 5.0, scr.renderList, DrawType.Circle, DrawType.None, result, 0);
        if (ok) {
            //System.out.println(ok+" "+r+"result"+result.x+" "+result.y+" "+result.r);
        }
    }
}

abstract class ParticlePoint extends Point implements GraphicsUpdate {

    GameObject user;
    RingList<Debri> particles = new RingList();
    double timer;
    Pigment color;

    void Move(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    boolean Dispose() {
        return timer < 0;
    }

    public abstract void Render(Screen scr);
}

public class Explosion extends ParticlePoint implements GraphicsUpdate {

    Pigment finalColor = new Pigment(8,0, 0, 0);

    Explosion(Point at, int p, double t, Pigment c,int r) {
        if(at instanceof GameObject){
            user=(GameObject)at;
        }
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = r;
        for (int i = 0; i < p; i++) {
            Debri part = new Debri(this, 1000);
            part.decay = 0.125;
            part.gravity = -1;
            part.travels = 100*t;
            particles.Append(part);
        }
        timer = t*0.125;
        color = c;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        if (timer > 0) {
            Node<Debri> d = particles.Start();
            if (user != null) {
                for (; d.data != null; d = d.next) {
                    d.data.Update(time, cam);

                    d.data.startingPoint = user.AsPoint(r);
                }
            } else {
                for (; d.data != null; d = d.next) {
                    d.data.Update(time, cam);

                }
            }
            timer -= time.cameraTime;
        }
    }

    @Override
    public void Render(Screen scr) {
        if (timer > 0) {
            Pigment c;
            Node<Debri> d = particles.Start();
            for (; d.data != null; d = d.next) {
                c=new Pigment(color);
                c.Shade(finalColor,1+(int)(d.data.r/this.r));
                d.data.Render(scr, c);
            }
        }
        //System.out.println("t"+timer);
    }
}

class Smoke extends ParticlePoint implements GraphicsUpdate {
    double release;
    Pigment finalColor = new Pigment(8,255, 255, 255);

    Smoke(Point at, int p, double t, Pigment c,int r) {
        if(at instanceof GameObject){
            user=(GameObject)at;
        }
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = r;
        for (int i = 0; i < p; i++) {
            Debri part = new Debri(this, 10);
            part.decay = -0.125;
            part.gravity = 1;
            part.travels = 10 * t;
            particles.Append(part);
        }
        
        timer = t * 10;
        release=(t/p);
        color = c;
    }

    boolean Dispose() {
        return timer < 0;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        if (timer > 0) {
            ;
            Node<Debri> d = particles.Start();
            double re=release;
            if (user != null) {
                
                for (; d.data != null; d = d.next) {
                    
                    d.data.startingPoint = user.AsPoint(r);
                    re-= time.cameraTime;
                    if(re>0){
                    d.data.x=user.x;
                    d.data.y=user.y;
                    d.data.z=user.z;
                    }else{
                        
                    d.data.Update(time, cam);
                    
                    }
                }
            } else {
                for (; d.data != null; d = d.next) {
                    re-= time.cameraTime;
                    if(re>0){
                    d.data.x=this.x;
                    d.data.y=this.y;
                    d.data.z=this.z;
                    }else{
                    d.data.Update(time, cam);
                    
                    }
                }
            }
            release-=time.cameraTime;
            timer -= time.cameraTime;
        }
    }

    @Override
    public void Render(Screen scr) {
        if (timer > 0) {
            Pigment c;
            Node<Debri> d = particles.Start();
            for (; d.data != null; d = d.next) {
                c=new Pigment(color);
                c.Shade(finalColor,1+(int)(d.data.r/this.r));
                d.data.Render(scr, c);
            }
        }
        //System.out.println("t"+timer);
    }
}
class Jet extends ParticlePoint implements GraphicsUpdate {

    Pigment finalColor = new Pigment(8,0, 0, 0);

    Jet(Point at, int p, double t, Pigment c,double r) {
        if(at instanceof GameObject){
            user=(GameObject)at;
        }
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = r;
        for (int i = 0; i < p; i++) {
            Debri part = new Debri(this,0.001f);
            part.decay = t/(r);
            part.gravity = 0;
            part.travels = t;
            particles.Append(part);
        }
        timer = t;
        color = c;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        if (timer > 0) {
            Node<Debri> d = particles.Start();
            double rt=r/(1+timer);
            if (user != null) {
                for (; d.data != null; d = d.next) {
                    d.data.Update(time, cam);
                    double dis=d.data.Distance(user);
                    double t1=time.cameraTime*(d.data.r/Linear.PI4);
                    double s1=t1/(rt*dis*Linear.PI8);
                    double s2=1.0-s1;
                    d.data.x=(d.data.x*s1+user.x*s2-user.direct.x*t1*dis);
                    d.data.y=user.y;
                    d.data.z=(d.data.z*s1+user.z*s2-user.direct.z*t1*dis);
                    d.data.r=rt;
                    d.data.startingPoint = user.AsPoint(r);
                }
            } else {
                for (; d.data != null; d = d.next) {
                    d.data.Update(time, cam);

                }
            }
            timer -= time.cameraTime;
        }
    }

    @Override
    public void Render(Screen scr) {
        if (timer > 0) {
            Pigment c;
            Node<Debri> d = particles.Start();
            for (; d.data != null; d = d.next) {
                c=new Pigment(color);
                c.Shade(finalColor,1+(int)(d.data.r/this.r));
                d.data.Render(scr, c);
            }
        }
        //System.out.println("t"+timer);
    }
}

