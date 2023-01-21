/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn Jones
 */
class StellarBody extends StarSystem {

    float a;
    float b;
    double v;
    float pr;
    double rrad;
    double rad;
    double gx;
    double gy;
    float m;
    float d;
    Point zone = new Point();

    StellarBody(StarSystem sys, Molecula rnd) {
        super(0, 0, sys, rnd);
    }
}

public class StarSystem extends Point {

    String name;
    Orbit orbital;
    Relative relative;//earths
    Relative relativeRender;//earths*7000(Physical point Scale) 
    Point relativeCoord;
    //core
    Core core;
    Pigment color;
    //atmosphere
    Atmosphere atmosphere;
    Angle spin;
    double magnetosphere;

    int counter = 0;
    float lys = 7.0f;
    int epixel = 250000;
//double au=1.5e11/6e6;//pixels
    double scale = 1.0 / (lys * epixel);
    //GImage trace;
    boolean start = false;
    float rot = 0;
    float sc;
    float m;
    float s1 = 1;//1+random(1)-random(1);
    float s2 = 1;//(s1+random(s1))*0.5;
    //color
    float b3;
    float g3;
    float r3;
    float sm;//star mass
    int oids = 0;
    int small = 0;
    int planets = 0;
    int large = 0;
    int huge = 0;
    int giant = 0;
    int superp = 0;
    int stars = 0;
    //
    float scaleAnim = 0;
    float scaleAnimRate = 0.1f;
    float scaleLimit = 20;
    float scaleTime = 1;
    //
    StarSystem system = null;

    ArrayList<StellarBody> bodies = new ArrayList<StellarBody>();

    StarSystem(Molecula rnd) {
        this(rnd.NextInt(rnd.NextInt(7)+rnd.NextInt(7)),rnd.NextInt(27), null, rnd);
    }
    double CalcDay(Star  star,SmallPlanet plan){
    double t=PlanetMap.PI8/(plan.relative.radius*plan.relative.radius);
    double h=(plan.relative.mass/star.relative.mass*plan.relative.radius/plan.orbital.radius)*(t*plan.orbital.radius*plan.orbital.radius);
    
    h= h/(plan.relative.radius*plan.relative.radius*star.relative.radius*star.relative.radius)*plan.orbital.radius;//mean magnetic area
   
    h= h-(PlanetMap.PI8/plan.orbital.radius/(h*PlanetMap.PI8));//retrogrades
    h= h-(plan.orbital.radius+star.relative.radius)*(h/PlanetMap.PI2)/plan.relative.radius;//magnetic area
    h= h+PlanetMap.PI8*star.relative.radius/plan.orbital.radius;//gravitic spin
    
    //h/(plan.orbital.radius*plan.orbital.radius)/plan.relative.mass;//24*((Math.abs(h))/(12.0/plan.orbital.radius/plan.relative.radius));
    return h/PlanetMap.PI;
    }
    void FromPlanetarySystem(Star star, Molecula rnd) {
        this.sm = (float) (star.relative.mass * PlanetMap.Sols);//sols to jupiters
        star.sm = this.sm;
        bodies = new ArrayList<StellarBody>();
        star.prime.pr = (float) star.prime.relative.radius;
        star.prime.d = (float) star.prime.orbital.radius;
        star.prime.rrad = (float) star.prime.orbital.radius;

        star.prime.day=CalcDay(star,star.prime);
        star.prime.relative.temperature+=(0.2*star.prime.day*star.prime.relative.temperature)/356.25;
        star.prime.a = rnd.Next((float) Math.PI * 2);
        star.prime.x = star.prime.d * Math.sin(star.prime.a);
        star.prime.y = star.prime.d * Math.cos(star.prime.a);
        star.prime.m = (float) star.prime.relative.mass;
        star.prime.v = 1.0;
        
        this.r3+=star.color.Red()*3;
        this.b3+=star.color.Blue()*3;
        this.g3+=star.color.Green()*3;
        this.r3*=0.25f;this.g3*=0.25f;this.b3*=0.25f;
        boolean added = false;
        Node<SmallPlanet> p = star.planets.Start();
        for (; p.data != null; p = p.next) {
            SmallPlanet plan = p.data;
            if (added == false && p.data.orbital.radius > star.prime.orbital.radius) {

                bodies.add(star.prime);
                added = true;
            }
            plan.d = plan.orbital.radius;
            plan.a = rnd.Next((float) Math.PI * 2);
            plan.day=CalcDay(star,plan);
            plan.relative.temperature+=(0.2*plan.day*plan.relative.temperature)/356.25;
            plan.x = plan.d * Math.sin(plan.a);
            plan.y = plan.d * Math.cos(plan.a);
            plan.b = 0;
            plan.v = 1;
            plan.pr = (float) plan.relative.radius;
            plan.m = (float) plan.relative.mass;
            plan.rrad = 0;
            plan.rad = 0;
            plan.gx = 0;
            plan.gy = 0;
            plan.b3 = (rnd.Next(4) + rnd.Next(4));
            plan.g3 = (b3 + rnd.Next(b3) - rnd.Next(b3));
            plan.r3 = (g3 + rnd.Next(b3) - rnd.Next(b3));
            bodies.add(plan);
        }
        if (added == false) {

            bodies.add(star.prime);
            added = true;
        }
        if (star.moon != null) {
            for (int i = 0; i < star.moon.length; i++) {
                Satalite pi = star.moon[i];

                pi.d = pi.orbital.radius;
                pi.a = rnd.Next((float) Math.PI * 2);
                pi.x = pi.d * Math.sin(pi.a);
                pi.y = pi.d * Math.cos(pi.a);
                pi.b = 0;
                pi.v = 1;
                pi.pr = (float) pi.relative.radius;
                pi.m = (float) pi.relative.mass;
                pi.rrad = 0;
                pi.rad = 0;
                pi.gx = 0;
                pi.gy = 0;
                pi.b3 = (rnd.Next(4) + rnd.Next(4));
                pi.g3 = (b3 + rnd.Next(b3) - rnd.Next(b3));
                pi.r3 = (g3 + rnd.Next(b3) - rnd.Next(b3));
                bodies.add(pi);
            }
        }
        if (star.largeMoon != null) {
            for (int i = 0; i < star.largeMoon.length; i++) {
                Planetoid pi = star.largeMoon[i];
                pi.d = pi.orbital.radius;
                pi.a = rnd.Next((float) Math.PI * 2);
                pi.x = pi.d * Math.sin(pi.a);
                pi.y = pi.d * Math.cos(pi.a);
                pi.b = 0;
                pi.v = 1;
                pi.pr = (float) pi.relative.radius;
                pi.m = (float) pi.relative.mass;
                pi.rrad = 0;
                pi.rad = 0;
                pi.gx = 0;
                pi.gy = 0;
                pi.b3 = (rnd.Next(4) + rnd.Next(4));
                pi.g3 = (b3 + rnd.Next(b3) - rnd.Next(b3));
                pi.r3 = (g3 + rnd.Next(b3) - rnd.Next(b3));
                bodies.add(pi);
            }
        }
    }

    void FillPlanetarySystem(Star star, Molecula rnd) {
        this.sm = (float) (star.relative.mass * PlanetMap.Sols);//sols to jupiters
        star.sm = this.sm;

        System.out.println("solar mass " + sm + " Jupiters");
        RingList<Satalite> sats = new RingList();
        oids = 0;
        small = 0;
        planets = 0;
        large = 0;
        huge = 0;
        giant = 0;
        superp = 0;
        stars = star.stars.length;
        double s = 0;
        star.planets = new RingList();
        star.prime.pr = (float) star.prime.relative.radius;
        star.prime.d = (float) star.prime.orbital.radius;
        star.prime.rrad = (float) star.prime.orbital.radius;
        star.prime.day=CalcDay(star,star.prime);
        star.prime.relative.temperature+=(0.2*star.prime.day*star.prime.relative.temperature)/356.25;
        star.prime.a = rnd.Next((float) Math.PI * 2);
        star.prime.x = star.prime.d * Math.sin(star.prime.a);
        star.prime.y = star.prime.d * Math.cos(star.prime.a);
        star.prime.m = (float) star.prime.relative.mass;
        star.prime.v = 1.0;
        
        boolean added = false;
        this.r3+=star.color.Red()*3;
        this.b3+=star.color.Blue()*3;
        this.g3+=star.color.Green()*3;
        this.r3*=0.25f;this.g3*=0.25f;this.b3*=0.25f;
        for (int i = 0; i < bodies.size(); i++) {
            StellarBody b = bodies.get(i);
            SmallPlanet p = null;
            if (b.m < 0.5) {
                Planetoid oid = new Planetoid(this, rnd);
                sats.add(oid);
                oid.x = b.x;
                oid.y = b.y;
                oid.a = b.a;
                oid.b = b.b;
                oid.v = b.v;
                oid.pr = b.pr;
                oid.rrad = b.rrad;
                oid.rad = b.rad;
                oid.gx = b.gx;
                oid.gy = b.gy;
                oid.b3 = b.b3;
                oid.g3 = b.g3;
                oid.r3 = b.r3;
                oids++;
                s = 128;
            } else if (b.m < 1.0) {

                s = 64;
                p = new SmallPlanet(this, rnd);
                small++;
            } else if (b.m < 5.0) {

                s = 32;
                p = new Planet(this, rnd);
                planets++;
            } else if (b.m < 10.0) {

                s = 16;
                p = new LargePlanet(this, rnd);
                large++;
            } else if (b.m < 100.0) {

                s = 8;
                p = new HugePlanet(this, rnd);
                huge++;
            } else if (b.m < 1000.0) {

                s = 4;
                p = new GiantPlanet(this, rnd);
                giant++;
            } else if (b.m < 10000.0) {

                s = 2;
                p = new SuperPlanet(this, rnd);
                superp++;
            } else {

                s = 1;
                p = new Star(this, rnd);
                stars++;
            }
            if (b.m >= 0.5 && p != null) {
                p.SetUpFromSystem(this, rnd, b, star, s);
                p.day=CalcDay(star,p);
                p.relative.temperature+=(0.2*p.day*p.relative.temperature)/356.25;
                if (added == false && p.orbital.radius > star.prime.orbital.radius) {

                    star.planets.Append(star.prime);
                    planets++;
                    added = true;
                }
                p.zone = new Point(0, 0);
                star.planets.Append(p);
            }
        }
        
        if (added == false) {

            star.planets.Append(star.prime);
            added = true;
            planets++;
        }
        bodies = new ArrayList<StellarBody>();
        Node<SmallPlanet> sp = star.planets.Start();
        int o = 0;
        for (; sp.data != null; sp = sp.next) {
            bodies.add(sp.data);

            String bn = StellarNames.PlanetName(star.classification, (int) star.relativeCoord.Length(), sp.data.d, o);
            sp.data.name = bn;
            System.out.println(o + " at " + sp.data.orbital.radius + " AUs mass " + sp.data.relative.mass + " Radius " + sp.data.relative.radius + " Gravity " + (sp.data.gravity / 9.8) + " Water " + sp.data.water + " Atmosphere " + sp.data.atmospheres + " Temp " + (-273.0 + 360.0 * sp.data.relative.temperature) + " c");
            o++;
        }
        Node<Satalite> pi = sats.Start();
        for (; pi.data != null; pi = pi.next) {
            bodies.add(pi.data);
        }

        star.bodies = new StellarBody[bodies.size()];
        for (int i = 0; i < bodies.size(); i++) {
            star.bodies[i] = bodies.get(i);
            //System.out.println(star.bodies[i].d);
        }
        this.relativeCoord = star.relativeCoord;
        System.out.println("Planetoids " + oids + " Small"
                + small + " Planets"
                + planets + " Large "
                + large + " Huge "
                + huge + " Gas Giants"
                + giant + " Super Gas planets"
                + superp + " Stars "
                + stars + " Bodies " + bodies.size());
        System.out.println("_____________________________");

    }

    StarSystem(int nb, int mb, StarSystem sys, Molecula rnd) {
        sc = 0.8f + rnd.Next(0.2f);
        this.relative = new Relative();
        this.relativeRender = new Relative();
        relativeCoord = new Point();

        b3 = (rnd.Next(4) + rnd.Next(4));
        g3 = (b3 + rnd.Next(b3) - rnd.Next(b3));
        r3 = (g3 + rnd.Next(b3) - rnd.Next(b3));
        //trace = new GImage(w, h);
        system = sys;
        if (sys == null) {
            m = mb;
            float m3 = m * m * m;
            sm = (m3 + rnd.Next(m3) + rnd.Next(m3)) / m;
            
            float tm = 0;
            int n4 = 1+(int) (rnd.Next(m/(nb*2)));//small bodies
            for (int i = 0; i < n4; i++) {
                StellarBody b = new StellarBody(this, rnd);
                b.a = rnd.Next((float) Math.PI * 2);
                b.m = (m*0.25f * (float) (1 + i) / n4 + rnd.Next(m*0.25f * (float) (1 + i) / n4) * (float) (1 + i) / n4 - rnd.Next(m*0.25f * (float) (1 + i) / n4) * (float) (1 + i) / n4) *0.5f;
                b.m *= b.m;

                tm += b.m;
                b.pr = (float) ((b.m * 2) / (m*0.5f + Math.sqrt(b.m))) * 4;

                float sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                b.pr *= sg1 * 0.102;
                b.pr *= 1 + (float) (i) / n4;
                //
                //sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                //System.out.println(i + " sg1 " + sg1 * 0.102 + " Mass " + b.m + " Radius " + b.pr);
                b.v = 1;
                bodies.add(b);
            }
            int n2 = 1 + (int) (rnd.Next(m/(nb*2)));//giant bodies
            for (int i = 0; i < n2; i++) {

                StellarBody b = new StellarBody(this, rnd);
                b.a = rnd.Next((float) Math.PI * 2);
                b.m = (m * (float) (1 + i) / n2 + rnd.Next(m * (float) (1 + i) / n2) * (float) (1 + i) / n2 - rnd.Next(m * (float) (1 + i) / n2) * (float) (1 + i) / n2);
                b.m *= b.m;
                tm += b.m;
                b.pr = (float) ((b.m * 2) / (m*2 + Math.sqrt(b.m))) * 4;

                float sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                b.pr *= sg1 * 0.102;
                b.pr *= 1 + (float) (i) / n2;
                //
                //sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                //System.out.println(i + " sg1 " + sg1 * 0.102 + " Mass " + b.m + " Radius " + b.pr);
                b.v = 1;

                bodies.add(b);
            }
            int n3 = 1+(int) (rnd.Next(m/(nb*2)));//large bodies
            for (int i = 0; i < n3; i++) {
                StellarBody b = new StellarBody(this, rnd);
                b.a = rnd.Next((float) Math.PI * 2);
                b.m = (m *0.5f* (float) (1 + i) / n3 + rnd.Next(m*0.5f * (float) (1 + i) / n3) * (float) (1 + i) / n3 - rnd.Next(m*0.5f * (float) (1 + i) / n3) * (float) (1 + i) / n3) * 0.5f;
                b.m *= b.m;

                tm += b.m;
                b.pr = (float) ((b.m * 2) / (m  + Math.sqrt(b.m))) * 4;

                float sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                b.pr *= sg1 * 0.102;
                b.pr *= 1 + (float) (i) / n3;
                //
                //sg1 = (float) Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth);
                //System.out.println(i + " sg1 " + sg1 * 0.102 + " Mass " + b.m + " Radius " + b.pr);
                b.v = 1;
                bodies.add(b);
            }

            for (int i = 0; i < bodies.size(); i++) {
                int prob = n2 - i;
                if (prob > 0) {
                    int det = (int) (rnd.Next(prob) - rnd.Next(prob));

                    if (det > 0) {
                        StellarBody b = bodies.get(det);
                        bodies.remove(det);
                        bodies.add(b);
                    }
                }
            }

            float si = (float) (epixel / sm) / (tm);
            float dis = si * 0.5f;
            for (int i = 0; i < bodies.size(); i++) {

                StellarBody b = bodies.get(i);
                dis = b.d = ((rnd.Next(si) + rnd.Next(2 * (float) Math.sqrt(si * dis)))) * (1 + i * si*0.5f);
                b.x = b.d * Math.sin(b.a);
                b.y = b.d * Math.cos(b.a);
                //b.rrad=b.d;
                System.out.println(dis);

            }
            System.out.println("solar " + sm + " effect " + si + " on " + tm);
            b3 *= 16;
            r3 *= 16;
            g3 *= 16;
            if (b3 > 255) {
                b3 = 255;
            }
            if (r3 > 255) {
                r3 = 255;
            }
            if (g3 > 255) {
                g3 = 255;
            }
            if (b3 < 0) {
                b3 = 0;
            }
            if (r3 < 0) {
                r3 = 0;
            }
            if (g3 < 0) {
                g3 = 0;
            }
        }
    }

    void ResetAnim() {
        scaleAnim = 0;
        start = false;
    }

    void Update(float time, float zoom) {
        scaleTime = (float) (Physics.day * time);
        start = true;
        scale = zoom;
        sc = (float) (scale);
        //sc *= sc * sc * sc * sc;
        sc *= scaleAnim;
        if (scaleAnim < scaleLimit) {
            scaleAnim += scaleAnimRate * time;
        }

        for (int i = 0; i < bodies.size(); i++) {
            StellarBody b1 = bodies.get(i);
            double srx1 = (-b1.x) * PlanetMap.AU;
            double sry1 = (-b1.y) * PlanetMap.AU;
            double stx1 = (double) ((srx1 * srx1) * Physics.SpeedOfLight);
            double sty1 = (double) ((sry1 * sry1) * Physics.SpeedOfLight);
            //
            b1.gx = (Physics.uG * 2 * (b1.m * PlanetMap.MassOfEarth + sm * PlanetMap.JupitersMass)) / (stx1);
            b1.gy = (Physics.uG * 2 * (b1.m * PlanetMap.MassOfEarth + sm * PlanetMap.JupitersMass)) / (sty1);
            //System.out.println(i+" gr "+b1.gx+" "+b1.gy+" bm "+b1.m+" sm "+sm+" sx "+stx1+" sy "+sty1+" x "+b1.x+" y "+b1.y);
            for (int j = 0; j < bodies.size(); j++) {
                StellarBody b2 = bodies.get(j);
                if (b1 != b2) {
                    double x1 = ((b2.x - b1.x) * PlanetMap.AU);
                    double y1 = ((b2.y - b1.y) * PlanetMap.AU);

                    double xt1 = (double) ((x1 * x1) * Physics.SpeedOfLight);
                    double yt1 = (double) ((y1 * y1) * Physics.SpeedOfLight);
                    b1.gx += (Physics.uG * 2 * (b1.m * PlanetMap.MassOfEarth + b2.m * PlanetMap.MassOfEarth)) / (xt1);
                    b1.gy += (Physics.uG * 2 * (b1.m * PlanetMap.MassOfEarth + b2.m * PlanetMap.MassOfEarth)) / (yt1);
                }
            }
            b1.b = (float) Math.atan(b1.gx / b1.gy);
        }

    }

    void Draw(int w, int h, Graphics2D g) {

        float xs = w * 0.5f;
        float ys = h * 0.5f;
        //trace.Draw(0, 0, g);
        //fill(255);
        //g.drawString("At " + ((1.0 - scale) * lys), 100, 60);

        float na = (float) (0.5 / (epixel * Math.PI * 8));
        /*if (scale<1.0) {
         scale+=(lys*epixel*time)/((lightYear)*lspd);
         trak=32;
         } else {
         trak=8;
         start=true;
         }*/
        //double lax = 0;
        //double ax = 0;
        //double lay = 0;
        //double ay = 0;

        //StellarBody b0 = bodies.get(0);
        //System.out.println("ends gr "+b0.gx+" "+b0.gy+" bm "+b0.m+" sm "+sm+" x "+b0.x+" y "+b0.y);
        int l = 0;
        for (int k = 0; k < 64; k += l) {
            g.setColor(new Color(255, 255, 255, 64 - k));
            float k1 = sc * (1 + k);
            g.drawLine((int) (xs + k1), (int) (ys - k1), (int) (xs + k1), (int) (ys + k1));//au
            g.drawLine((int) (xs - k1), (int) (ys - k1), (int) (xs - k1), (int) (ys + k1));//au
            g.drawLine((int) (xs - k1), (int) (ys + k1), (int) (xs + k1), (int) (ys + k1));//au
            g.drawLine((int) (xs - k1), (int) (ys - k1), (int) (xs + k1), (int) (ys - k1));//au
            l += 1;
        }
        float ra = (float) (sm / (m * m) * sc * 0.002);
        for (int k = 0; k < 16; k++) {
            float kr = k * ra;
            //float kd = kr * 0.001f;
            float kc = 255 - k * k;
            if (kc < 1) {
                kc = 1;
            }
            //noStroke();
            //stroke((int)(r3*kc)%255, (int)(g3*kc)%255, (int)(b3*kc)%255,4);
            g.setColor(new Color((int) ((r3+r3+r3+kc)*0.25f) % 255, (int) ((g3+g3+g3+kc)*0.25f) % 255, (int) ((b3+b3+b3+kc)*0.25f) % 255, (int) (kc % 255)));
            g.fillOval((int) (xs) - (int) (kr * 0.5), (int) (ys) - (int) (kr * 0.5), (int) (kr), (int) (kr));
        }
        g.setColor(new Color(255, 255, 255, 32));
        g.fillOval((int) (xs) - (int) (ra * 16), (int) (ys) - (int) (ra * 16), (int) (ra * 32), (int) (ra * 32));
        g.setColor(new Color(255, 255, 255, 16));
        g.fillOval((int) (xs) - (int) (ra * 32), (int) (ys) - (int) (ra * 32), (int) (ra * 64), (int) (ra * 64));

        for (int i = 0; i < bodies.size(); i++) {
            StellarBody b = bodies.get(i);

            float gx1 = (float) (b.gx * scaleTime);
            float gy1 = (float) (b.gy * scaleTime);

            //float ara = (float) (Math.sqrt(x * x + y * y + 3e-8));
            double gr = Math.sqrt(b.gx * b.gx + b.gy * b.gy + 3e-8);//gravocity
            float ar = (float) (Math.sqrt(b.x * b.x + b.y * b.y + 3e-8));
            //double t = (Math.atan(b.gx / b.gy));
            //double s = Math.sin(t);
            //double c = Math.cos(t + Math.PI);
            //double ga = Math.sqrt(b.gx * b.gx + b.gy * b.gy + 3e-8);
            double tps = (((scaleTime) / ((Linear.PI2 * gr) * (Physics.SpeedOfLight))));
            double sv = scaleTime / tps;
            b.v = gr * scaleTime + sv / b.v;//velocity

            float rc1 = (float) (Math.sqrt(ar * 2));
            float va = (float) (na * b.v * scaleTime / (PlanetMap.RadiusOfEarth * ar * ar * Math.PI * 8)) * epixel * 0.5f;
            b.a += va;//;
            if (b.a > Linear.PI2) {
                b.a -= Linear.PI2;
            }
            b.rad = b.a;
            double emgf = (PlanetMap.PI8 * sv * (scaleTime / Physics.SpeedOfLight));
            double circ = ar * PlanetMap.AU * Linear.PI2;
            b.x += emgf / (ar * circ) * Math.sin(b.a);//em;
            b.y += emgf / (ar * circ) * Math.cos(b.a);//em;
            b.x += emgf / (circ) * Math.sin(b.a);//-b.gx*time;
            b.y += emgf / (circ) * Math.cos(b.a);//-b.gy*time;

            b.rrad = Math.sqrt(b.x * b.x + b.y * b.y + 3e-8);
            //float rot2 = (float) (b.b);
            float yn1 = (float) ((b.rrad) * Math.sin(b.a));
            float yn2 = (float) ((b.rrad) * Math.cos(b.a));
            float grav1 = (float) Math.sqrt(Physics.SurfaceGravityMagnitude(b.m * PlanetMap.MassOfEarth, b.pr * PlanetMap.RadiusOfEarth)) * 0.25f;
            float g1 = (rc1 * 2) * (float) Math.abs(b.relative.temperature - 0.5);
            float r1 = (rc1 * (float) b.relative.temperature);
            float b1 = ((float) b.relative.temperature / rc1);
            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }
            float sc1 = (float) (sc * s1);
            float sc2 = (float) (sc * s2);
            int batx = (int) (xs + (yn1 + gx1) * sc1);
            int baty = (int) (ys + (yn2 + gy1) * sc2);
            double gravityRadius = grav1 * sc;
            double massRadius = b.m * 0.01 * sc;
            double gravityIntensity = gr * sc * Physics.hour;
            b.zone.x = (int) (batx);
            b.zone.y = (int) (baty);
            b.zone.r = (int) (gravityRadius * 2);

            int red = 255 - (int) Math.abs(r1 * rc1) % 255;
            int green = 255 - (int) Math.abs(g1 * rc1) % 255;
            int blue = 255 - (int) Math.abs(b1 * rc1) % 255;
            g.setColor(new Color(red, green, blue, 255));
            //mass
            g.drawLine(batx, baty, batx, baty);
            g.setColor(new Color(red, green, blue, 64));
            g.fillOval(batx - (int) (massRadius * 0.5), baty - (int) (massRadius * 0.5), (int) (massRadius), (int) (massRadius));
            g.drawOval((int) (xs - (b.rrad + gx1) * sc1), (int) (ys - (b.rrad + gy1) * sc2), (int) ((b.rrad + gx1) * sc1 * 2), (int) ((b.rrad + gy1) * sc2 * 2));
            //gravity 
            g.drawRect((int) (batx - gravityRadius * 0.5), (int) (baty - gravityRadius * 0.5), (int) (gravityRadius), (int) (gravityRadius));

            g.setColor(new Color(red, green, blue, 32));
            g.fillOval((int) (batx) - (int) (gravityIntensity * 0.5), (int) (baty) - (int) (gravityIntensity * 0.5), (int) (gravityIntensity), (int) (gravityIntensity));
            //orbit
            if (start) {

                //trace.AddPixel((int) (xs + (yn1 + gx1) * sc1), (int) (ys + (yn2 + gy1) * sc2), 128, 255 - (int) (r1 * rc1) % 255, 255 - (int) (g1 * rc1) % 255, 255 - (int) (b1 * rc1) % 255);
            }
            //System.out.println(i+" "+b.rrad);
        }
        counter++;
        if (start && counter > 2) {
            //trace.ClearAdd(new Pigment(16, 32, 64, 255), 1500, 1.0f);
            counter = 0;
        }
        //trace.Update();
    }

    public static Pigment ChemicalStain(Core core, Atmosphere atmos,Pigment p) {

        int blue = (core.lightMetals + core.calcium + core.oxygen + core.helium + core.hydrogen + atmos.hydrogen + atmos.nitrogen + atmos.oxygen);//8
        int red = (core.iron + core.oxygen + core.carbon + core.iron + core.iron + atmos.ammonia + atmos.ammonia + atmos.sulphur + atmos.co2 + atmos.co2 + atmos.oxygen) - (core.hydrogen + atmos.hydrogen + atmos.nitrogen);//8
        int green = (core.heavyMetals + core.carbon + core.oxygen + atmos.sulphur + atmos.ammonia + atmos.ammonia + atmos.chlorine + atmos.methane + atmos.chlorine + atmos.methane + atmos.oxygen) - (core.hydrogen + atmos.hydrogen + atmos.nitrogen);//8
        int r = red >> 3;
        int g = green >> 3;
        int b = blue >> 3;
        blue = blue * blue - r * g;
        red = red * red - b * g;
        green = green * green - b * r;
        ///
        //(int)(100*(1+t-0.5));
        blue = (blue / (1 + r + g)) / (1+p.Red());
        red = (red / (1 + b + g)) / (1+p.Green());
        green = (green / (1 + r + b)) / (1+p.Blue());
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        return new Pigment(red, green, blue);

    }

    public static Pigment Air(Atmosphere atmos,Pigment p) {
        int red = ((atmos.ammonia + atmos.ammonia + atmos.sulphur + atmos.co2 + atmos.co2 + atmos.oxygen)>>1) - (atmos.hydrogen + atmos.nitrogen);//8
        int green =((atmos.sulphur + atmos.ammonia + atmos.ammonia + atmos.chlorine + atmos.methane + atmos.chlorine + atmos.methane + atmos.oxygen)>>1) - (atmos.hydrogen + atmos.nitrogen);//8
        int blue = (atmos.hydrogen + atmos.nitrogen + atmos.oxygen)*2;//8
        int r = red >> 2;
        int g = green >> 2;
        int b = blue >> 2;
        blue = blue * blue - r * g;
        red = red * red - b * g;
        green = green * green - b * r;
        ///
        blue = (blue / (1 + r + g)) / (1+p.Red());
        red = (red / (1 + b + g)) / (1+p.Green());
        green = (green / (1 + r + b)) / (1+p.Blue());
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        return new Pigment(red, green, blue);

    }

    public static Pigment Soil(Core core,Pigment p) {

        int red = ((core.iron + core.oxygen + core.carbon + core.iron + core.iron)) - (core.hydrogen);//8
        int green = ((core.heavyMetals + core.carbon + core.oxygen)) - (core.hydrogen);//8
        int blue = (core.lightMetals + core.calcium + core.oxygen + core.helium + core.hydrogen)>>1;//8
        int r = red >> 2;
        int g = green >> 2;
        int b = blue >> 2;
        blue = blue * blue - r * g;
        red = red * red - b * g;
        green = green * green - b * r;
        ///
        blue = (blue / (1 + r + g)) / (1+p.Red());
        red = (red / (1 + b + g)) / (1+p.Green());
        green = (green / (1 + r + b)) / (1+p.Blue());
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        return new Pigment(red, green, blue);

    }
}
