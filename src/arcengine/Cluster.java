/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn Jones
 */
public class Cluster extends Frac
    {
        SphericalCoord sc;
        Vertex2D stars;
        Point at;
        Circle bounds;
        int number;
        Cluster(double mass, double xx, double yy, int nstars, double radius,Point scale,Pigment color)
        {
            double density = 0.1f;
            this.number = nstars;
            this.x=xx;
            this.y=yy;
            sc = new SphericalCoord();
            double d = this.Length2D();
            sc.phi = yy / d * PlanetMap.PI2;
            sc.theta = xx / d * PlanetMap.PI2;
            sc.p = Linear.MAX_MAP;
            this.at = sc.At();
            this.r = radius;
            seed1 = x * x+radius;
            seed2 = y * y+radius;
            molecule1 = new Molecula(mass,mass/seed1,seed1);
            molecule2 = new Molecula(mass,mass/seed2,seed2);
            molecule1.SingleCalc();
            molecule2.LineCalc();
            double mx = (double)molecule1.cosine * density;
            double my = (double)molecule1.sine * density;
            double rad = radius * density;
            radius += (double)(molecule2.cosine - molecule1.sine) * rad;
            SphericalCoord sc2 = new SphericalCoord(sc.theta + PlanetMap.PID3 * mx, sc.phi + PlanetMap.PID3 * my, Linear.MAX_MAP*radius*density);
            Point at2 = sc2.At();
            Vertex2D s = new Vertex2D(at2.x, at2.y, at2.z,at2.r,0);
            s.color = color;
            s.w =  mass * (double)Math.abs(mx +my); 
            stars = s;
            double b = at.Sub(at, s).Length();

                
            for (int i = 0; i < nstars; i++) {

                molecule1.SingleCalc();
                molecule2.LineCalc();
                mx = (double)molecule1.cosine ;
                my = (double)molecule1.sine ;
                radius += (double)(molecule2.cosine - molecule1.sine)*rad;
                sc2 = new SphericalCoord(sc2.theta + PlanetMap.PID3 * mx, sc2.phi + PlanetMap.PID3 * my, Linear.MAX_MAP * radius * density*scale.r);
                at2 = at.Add(at,sc2.At()).AsPoint(at.r);
                at2.x = at2.x * scale.x; at2.y = at2.y * scale.y; at2.z = at2.z * scale.z;
                Vertex2D t = new Vertex2D(at2.x,at2.z,at2.y+(molecule2.cosine*molecule1.sine)* radius * density*scale.r,at2.r,0);
                t.w = mass*(double)Math.abs(mx+my);
                
                t.color = new Pigment(color);
                t.color.Average(new Pigment(255,255,255));
                double rb = at.Sub(at,t).Length();
                if (rb > b) { b = rb; }
                s.SetNext(t);
                s=t;
            }
            this.bounds = new Circle(at, b);
        }
        boolean Transform(Screen scr,Point offset,Quarternion q) {
            Point loc=at.Add(at,offset).AsPoint(at.r);
            loc.r= this.bounds.radius;
            Point p =new Point();
            boolean ok=scr.GetViewLocation(loc,q, 1.0f,offset.r,p);
            if (p == null) { return false; }
            ok= Visable(scr,loc,q);
            return ok;
        }
        boolean Visable(Screen scr,Point loc,Quarternion q) {
            Circle c2 = new Circle(loc,loc.r);
            return scr.cam.InView(scr, c2,q, 1, 1) ;
        }
        Vertex2D Transformed(Screen scr, Point offset,Quarternion q)
        {
            Vertex2D s = null;
            Vertex2D t = null;
            Vertex2D r = null;
            Vertex2D st = stars;
            Point p = new Point();
            boolean start = true;
            Pigment c = new Pigment();
            if(st!=null){
                c=new Pigment(st.color);
                c.Average(new Pigment());
            }
            int i=0;
            while (st != null)
             {
                 t = new Vertex2D(st.Add(st,offset).AsVector(),0);
                 t.w = 1;
                 p=scr.GetScreenLocation(t.AsPoint(t.w),q, 1.0, offset.r);
                    
                 if (p != null)
                 {
                     if (p.x > scr.origin.x && p.x < scr.edge.x && p.y > scr.origin.y && p.y < scr.edge.y)
                     {
                         //System.out.println(p.x + " " + p.y);
                        
                         t = new Vertex2D(p.AsVector(),0);
                         t.SetDrawing(p, DrawType.Point, DrawType.None, t, st.color, 0);
                         t.w = st.w*0.125f;//st.r * st.r*0.001f;
                         if (start)
                         {
                             start = false; r = s = t;
                         }
                         else
                         {
                             s.SetNext(t);
                             s=t;
                             i++;
                         }
                         /*t = new Vertex2D(t);
                         t.Center.x = p.x; t.Center.y = p.y; t.Center.z = p.z;
                         t.color = st.color;
                         t.draw = Vertex2D.DrawDot;
                         t.Center.r = st.r * 0.025f;

                         s = s.SetNext(t);*/
                     }
                 }
                 st = st.ClockWise();
             } ;
             //System.out.println("Stars"+i);
            return r;
        }
        static void CreateStarField(RingList<Cluster>starField, Molecula rnd) {
            for (int xx = -180; xx < 180; xx += 9+rnd.NextInt(18))
            {

                double xa = (double)(xx) *0.0175;
                Point scale = new Point(Linear.PI2 - xa, 0.25+0.5f * xa, Linear.PI2 - xa, 0.1f);
                if (xx != 0)
                    for (int yy = -90; yy < 90; yy += 9 + rnd.NextInt(18))
                    {
                        double ya = (double)(yy) * 0.0175;
                        int x = xx; int y = yy;
                        if (yy != 0)
                        {
                            Pigment c = new Pigment(64, Math.abs(y), 128 - ((Math.abs(y) + Math.abs(x)) >> 1), Math.abs(x) + 64);
                            c.Clamp();
                            starField.Append(new Cluster((double)(360 - Math.abs(x) - Math.abs(y)) / 6, xa, ya, 1 + (Math.abs(x + y) >> 1), 1 + (Math.abs(x + y) >> 2), scale, c));
                        }
                    }
            }
        }
        static void StoreClusters(Screen scr,Point viewer,Quarternion ecliptic,RingList<Cluster> starField) {
            Cluster c = starField.First();
            while (c != null)
            {
                if (c.Transform(scr, viewer,ecliptic))
                {
                    Vertex2D v = c.Transformed(scr, viewer,ecliptic);
                    if (v != null)
                        scr.renderList.Append(v);
                }
                c = starField.Next();
            }
        }
    }
