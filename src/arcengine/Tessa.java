package arcengine;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gerwyn Jones
 */
abstract class Vertex3D implements Drawable ,ToFile,FromFile{
    Vector v;
    Vector p;
    Normal n;

    int graphicsIndex;
    @Override
    public boolean To(StellarFiles f) {
        v.To(f);
        n.To(f);
        p.To(f);
        return true;
    }
    

    @Override
    public boolean From(StellarFiles f) {
        if(v.From(f)){
        if(n.From(f)){
        if(p.From(f)){
            return true;
        }
        }
        }
        return false;
    }
    Vector MidPoint(Vertex3D v2) {
        return new Vector((v.x + v2.v.x) * 0.5, (v.y + v2.v.y) * 0.5, (v.z + v2.v.z) * 0.5);
    }

    boolean Equal(Vertex3D test) {
        return Math.abs(test.v.x - v.x) < Linear.Epsilon && Math.abs(test.v.y - v.y) < Linear.Epsilon && Math.abs(test.v.z - v.z) < Linear.Epsilon;
    }

    boolean Equal(Vertex3D test, double epsilon) {
        return Math.abs(test.v.x - v.x) < epsilon && Math.abs(test.v.y - v.y) < epsilon && Math.abs(test.v.z - v.z) < epsilon;
    }
}
class Triangle extends Vertex3D
{
    Vertex3D parent;
    Vertex3D next;
    Vertex3D prev;
    @Override
    public void SetDrawing(Point center, DrawType typ, DrawType subtyp, DrawChain chain, Pigment col, int index) {
        v = center.AsVector();
        p = new Vector(col.Red() * 0.004, col.Green() * 0.004, col.Blue() * 0.004, col.Alpha() * 0.004);

        graphicsIndex = index;
    }

    @Override
    public String Name() {
        return "Triangle3D" +graphicsIndex;
    }

    @Override
    public Quarternion Rotation() {
        return null;
    }

    @Override
    public DrawType Draws() {
        return DrawType.Triangle;
    }

    @Override
    public DrawType SubDraws() {
        return DrawType.Point;
    }

    @Override
    public DrawChain Drawings() {
        return this;
    }

    @Override
    public int GetGraphicsIndex() {
        return graphicsIndex;
    }

    @Override
    public Drawable Drawing(int p) {
        if(p>0 && next!=null){
            p--;
        next.Drawing(p);
        }
        return next;
    }

    @Override
    public boolean DoubleSided() {
        return true;
    }

    @Override
    public Pigment Colour() {
        return new Pigment((int) (p.w * 255), (int) (p.x * 255), (int) (p.y * 255), (int) (p.z * 255));
    }

    @Override
    public int Points() {
        return 3;
    }

    @Override
    public Vector GetVector(int i) {
        return v;
    }

    @Override
    public Normal Norm(int i) {
        return n;
    }

    @Override
    public Point Center() {
        return this.v.AsPoint(1);
    }

    @Override
    public Drawable Next() {
        return next;
    }

    @Override
    public Drawable Prev() {
        return prev;
    }

    @Override
    public Graphical Graphic() {
        return ArcEngine.DefaultGraphical;
    }

    @Override
    public void Draw(Drawable chain, Graphical g) {

    }
}
class TessaVertex extends Vertex3D implements Drawable{

    SphericalCoord sv;
    double h;
    boolean sided;
    int index;
    int adjacent;
    int currentAdj = -1;
    TessaVertex adj[] = new TessaVertex[16];
    Tessa parent = null;

    TessaVertex() {
        v = new Vector();
        n = new Normal(0, 1, 0);
        p = new Vector(1, 1, 1);
        h = 1;
        sv = new SphericalCoord(v, Linear.AxisY);
    }

    TessaVertex(Vector vec, Normal norm, Vector colour, double ht) {
        v = vec;
        n = norm;
        p = colour;
        h = ht;
        sv = new SphericalCoord(v, Linear.AxisY);
    }

    TessaVertex(TessaVertex copy) {
        v = new Vector(copy.v);
        n = new Normal(copy.n.x, copy.n.y, copy.n.z);
        p = new Vector(copy.p);
        h = copy.h;
        sv = new SphericalCoord(v, Linear.AxisY);
    }

    
    @Override
    public void SetDrawing(Point center, DrawType typ, DrawType subtyp, DrawChain chain, Pigment col, int index) {
        v = center.AsVector();
        p = new Vector(col.Red() * 0.004, col.Green() * 0.004, col.Blue() * 0.004, col.Alpha() * 0.004);

        graphicsIndex = index;
    }

    @Override
    public String Name() {
        return "Vertex3D" + index + "Adj" + adjacent;
    }

    @Override
    public Quarternion Rotation() {
        return null;
    }

    @Override
    public DrawType Draws() {
        return DrawType.Triangle;
    }

    @Override
    public DrawType SubDraws() {
        return DrawType.Point;
    }

    @Override
    public DrawChain Drawings() {
        return this;
    }

    @Override
    public int GetGraphicsIndex() {
        return graphicsIndex;
    }

    @Override
    public Drawable Drawing(int p) {
        return this.adj[p];
    }

    @Override
    public boolean DoubleSided() {
        return sided;
    }

    @Override
    public Pigment Colour() {
        return new Pigment((int) (p.w * 255), (int) (p.x * 255), (int) (p.y * 255), (int) (p.z * 255));
    }

    @Override
    public int Points() {
        return adjacent;
    }

    @Override
    public Vector GetVector(int i) {
        return v;
    }

    @Override
    public Normal Norm(int i) {
        return n;
    }

    @Override
    public Point Center() {
        return this.v.AsPoint(1);
    }

    @Override
    public Drawable Next() {
        currentAdj++;
        if (currentAdj > adjacent) {
            currentAdj = -1;
            return null;
        }
        return adj[currentAdj];
    }

    @Override
    public Drawable Prev() {
        currentAdj--;
        if (currentAdj == -1) {
            currentAdj = adjacent;
            return null;
        }
        return adj[currentAdj];
    }

    @Override
    public Graphical Graphic() {
        return ArcEngine.DefaultGraphical;
    }

    @Override
    public void Draw(Drawable chain, Graphical g) {

    }


}

class Tessa implements Drawable ,ToFile,FromFile{

    static Molecula mole1 = new Molecula();
    static Molecula mole2 = new Molecula();
    static Molecula mole3 = new Molecula();
    static double T = 0.23456789123456789123456789;
    static float S = 2.0f;

    static double RandomAverage(double scale) {
        mole1.SingleCalc();
        S = (float) (mole1.Cos() * mole1.Sin());
        //return noise((1+S)+(float)mole1.Cos()*(1+S), (1+S)+(float)mole1.Sin()*(1+S))*rnd.nextDouble()*S*scale;
        return (Molecula.rnd.nextDouble() - Molecula.rnd.nextDouble()) * Molecula.rnd.nextDouble() * S * scale;
    }

    static Vector NoiseVector(Vector v1, double scale) {
        mole2.SingleCalc();
        mole1.SingleCalc();
        S = (float) (RandomAverage((mole1.Cos() + mole1.Sin())) * scale);
        return new Vector(noise((float) ((1 + v1.x) * S), (float) (1 + mole2.Cos()) * S) * S, noise((float) (1 + v1.y) * S, (float) (1 + mole2.Sin()) * S) * S, noise((float) (1 + v1.z) * S, (float) ((2 + mole2.Cos() + mole2.Sin()) * S)) * S);
    }

    static double AverageNoise(double x, double y, double z, double scale) {
        mole3.SingleCalc();
        mole1.SingleCalc();
        S = (float) RandomAverage((mole1.Cos() + mole1.Sin()));
        return Math.abs(noise((float) ((1 + x) * S), (float) ((1 + mole3.Cos()) * S)) - noise((float) ((1 + y) * S), (float) ((1 + mole3.Sin()) * S))) * noise((float) ((1 + z) * S), (float) (((2 + mole3.Cos() + mole3.Sin()) * S))) * scale * S;
    }
    Tessa parent;
    TessaVertex vert[] = new TessaVertex[3];
    Point center;
    Tessa tess[] = null;
    Pigment color;
    Quarternion rotation = null;//new Quarternion();
    DrawType draws = DrawType.Triangle;
    DrawType subDraws = DrawType.Triangle;

    boolean sided = false;
    int currentVertex = -1;
    int graphicsIndex;

    @Override
    public boolean To(StellarFiles f) {
        for(int i=0;i<3;i++){
        vert[i].To(f);
        }
        center.To(f);
        color.To(f);
        return true;
    }

    @Override
    public boolean From(StellarFiles f) {
           
        boolean ok=true;          
        for(int i=0;i<3;i++){
            vert[i]=new TessaVertex();
            ok&=vert[i].From(f);
        }
        center=new Point();
        ok&=center.From(f);
        color=new Pigment();
        ok&=color.From(f);
        return ok;
    }
    Tessa(TessaVertex v1, TessaVertex v2, TessaVertex v3, double radius) {
        vert[0] = v1;
        vert[1] = v2;
        vert[2] = v3;
        center = v1.v.Add(v1.v, v2.v).AsPoint(radius);
        center.Scale(0.5);
        center = center.Add(center, v3.v).AsPoint(radius);
        center.Scale(0.5);
        Cartesian mag = v1.v.Sub(v1.v, v2.v);
        mag = mag.Sub(mag, v3.v);
        center.r = mag.Length();
        tess = new Tessa[4];
        double r = v1.p.x + v2.p.x + v3.p.x;
        double g = v1.p.y + v2.p.y + v3.p.y;
        double b = v1.p.z + v2.p.z + v3.p.z;
        double a = v1.p.w + v2.p.w + v3.p.w;
        color = new Pigment((int) (a * 85), (int) (r * 85), (int) (g * 85), (int) (b * 85));
    }

    Tessa(Tessa copy) {
        vert[0] = new TessaVertex(copy.vert[0]);
        vert[1] = new TessaVertex(copy.vert[1]);
        vert[2] = new TessaVertex(copy.vert[2]);
        center = new Point(copy.center.x, copy.center.y, copy.center.z, copy.center.r);
        tess = new Tessa[4];
        color = new Pigment((int) (copy.color.Alpha()), (int) (copy.color.Red()), (int) (copy.color.Green()), (int) (copy.color.Blue()));
    }
    Tessa() {
    }

    static float noise(float x) {
        return 0.5f * (float) (1 + Molecula.rnd.nextDouble() - Molecula.rnd.nextDouble());
    }

    static float noise(float x, float y) {
        return 0.5f * (float) (1 + Molecula.rnd.nextDouble() - Molecula.rnd.nextDouble());
    }
    /*void SubDivide(Vertex v1, Vertex v2, Vertex v3, GeoPlanet planet) {
     Vector p12=new Vector(v1.MidPoint(v2));
     Vector p23=new Vector(v2.MidPoint(v3));
     Vector p31=new Vector(v3.MidPoint(v1));
     double h1=(v1.h+v2.h)*noise((float)v1.h, (float)v2.h)*planet.weather+noise((float)v3.h)*planet.land-noise((float)v2.h)*planet.water;
     double h2=(v2.h+v3.h)*noise((float)v2.h, (float)v3.h)*planet.weather+noise((float)v1.h)*planet.land-noise((float)v3.h)*planet.water;
     double h3=(v1.h+v3.h)*noise((float)v1.h, (float)v3.h)*planet.weather+noise((float)v2.h)*planet.land-noise((float)v1.h)*planet.water;
     Point o=new Point(1);
     Cartesian v12=o.Sub(p12, o);
     Cartesian v23=o.Sub(p23, o);
     Cartesian v31=o.Sub(p31, o);
     //
     v12.Normalize();
     v23.Normalize();
     v31.Normalize();
     //
     p12=v12.Add(v12, o).AsVector();
     p23=v23.Add(v23, o).AsVector();
     p31=v31.Add(v31, o).AsVector();
     Vector c12=v1.p.Add(v1.p, v2.p).Scaled(0.5).AsVector();
     Vector c23=v1.p.Add(v2.p, v3.p).Scaled(0.5).AsVector();
     Vector c31=v1.p.Add(v3.p, v1.p).Scaled(0.5).AsVector();
     //
     Vertex vx12=new Vertex(p12, p12.AsNormal(), c12, h1);
     Vertex vx23=new Vertex(p23, p23.AsNormal(), c23, h2);
     Vertex vx31=new Vertex(p31, p31.AsNormal(), c31, h3);
     tess[0]=new Tessa(vx12, vx23, vx31);
     tess[1]=new Tessa(v1, vx12, vx31);
     tess[2]=new Tessa(v2, vx23, vx12);
     tess[3]=new Tessa(v3, vx31, vx23);
     }*/

    void SubDivide(TessaVertex v1, TessaVertex v2, TessaVertex v3, GeoPlanet planet, int level) {
        Vector p12 = new Vector(v1.MidPoint(v2));
        Vector p23 = new Vector(v2.MidPoint(v3));
        Vector p31 = new Vector(v3.MidPoint(v1));
        double h1 = (v1.h + v2.h) * noise((float) v1.h, (float) v2.h) * planet.weather + noise((float) v3.h) * planet.land - noise((float) v2.h) * planet.water;
        double h2 = (v2.h + v3.h) * noise((float) v2.h, (float) v3.h) * planet.weather + noise((float) v1.h) * planet.land - noise((float) v3.h) * planet.water;
        double h3 = (v1.h + v3.h) * noise((float) v1.h, (float) v3.h) * planet.weather + noise((float) v2.h) * planet.land - noise((float) v1.h) * planet.water;
        Point o = new Point(1);
        Cartesian v12 = o.Sub(p12, o);
        Cartesian v23 = o.Sub(p23, o);
        Cartesian v31 = o.Sub(p31, o);
        //
        v12.Normalize();
        v23.Normalize();
        v31.Normalize();
        //
        p12 = v12.Add(v12, o).AsVector();
        p23 = v23.Add(v23, o).AsVector();
        p31 = v31.Add(v31, o).AsVector();
        Vector c12 = v1.p.Add(v1.p, v2.p).Scaled(0.5).AsVector();
        Vector c23 = v2.p.Add(v2.p, v3.p).Scaled(0.5).AsVector();
        Vector c31 = v3.p.Add(v3.p, v1.p).Scaled(0.5).AsVector();
        //
    /*int fracLevel=level;
         for (int j=0; j<fracLevel; j++) {
         c12=c12.Add(c12, new Vector(RandomAverage(planet.wind), RandomAverage(planet.land), RandomAverage(planet.water))).AsVector();
         c23=c23.Add(c23, new Vector(RandomAverage(planet.wind), RandomAverage(planet.land), RandomAverage(planet.water))).AsVector();
         c31=c31.Add(c31, new Vector(RandomAverage(planet.wind), RandomAverage(planet.land), RandomAverage(planet.water))).AsVector();
         //
         c12=c12.Add(c12, NoiseVector(p12, planet.mass)).AsVector();
         c23=c23.Add(c23, NoiseVector(p23, planet.mass)).AsVector();
         c31=c31.Add(c31, NoiseVector(p31, planet.mass)).AsVector();
         int rndLevel=level+rnd.nextInt(6);

         double t=noise((float)center.x, (float)center.z);
         for (int k=1; k<rndLevel; k++) {
         Vector r1=c12.Add(c23, c31).AsVector();
         Vector r2=c23.Add(c12, c31).AsVector();
         Vector r3=c31.Add(c23, c12).AsVector();
         double t1=noise((float)r1.y, (float)r1.z)*planet.soil;
         double t2=noise((float)r2.y, (float)r2.z)*planet.soil;
         double t3=noise((float)r3.y, (float)r3.z)*planet.soil;
         r1.Scale((AverageNoise(c12.x, c12.y, c12.z, 1)+T+Math.abs(p12.y*t1))/(c12.Length()));
         r2.Scale((AverageNoise(c23.x, c23.y, c23.z, 1)+T+Math.abs(p23.y*t2))/(c23.Length()));
         r3.Scale((AverageNoise(c31.x, c31.y, c31.z, 1)+T+Math.abs(p31.y*t3))/(c31.Length()));
         c12=c12.Add(c12, r1).AsVector();
         c23=c23.Add(c23, r2).AsVector();
         c31=c31.Add(c31, r3).AsVector();
         }
         }

         c12.Scale(0.25/fracLevel);
         c23.Scale(0.25/fracLevel);
         c31.Scale(0.25/fracLevel);*/

        Linear.Clamp(c12);
        Linear.Clamp(c23);
        Linear.Clamp(c31);
        //
        TessaVertex vx12 = new TessaVertex(p12, p12.AsNormal(), c12, h1);
        TessaVertex vx23 = new TessaVertex(p23, p23.AsNormal(), c23, h2);
        TessaVertex vx31 = new TessaVertex(p31, p31.AsNormal(), c31, h3);
        tess[0] = new Tessa(vx12, vx23, vx31, 1);
        tess[1] = new Tessa(v1, vx12, vx31, 1);
        tess[2] = new Tessa(v2, vx23, vx12, 1);
        tess[3] = new Tessa(v3, vx31, vx23, 1);
        level--;
        if (level > 0) {
            tess[0].SubDivide(tess[0].vert[0], tess[0].vert[1], tess[0].vert[2], planet, level);
            tess[1].SubDivide(tess[1].vert[0], tess[1].vert[1], tess[1].vert[2], planet, level);
            tess[2].SubDivide(tess[2].vert[0], tess[2].vert[1], tess[2].vert[2], planet, level);
            tess[3].SubDivide(tess[3].vert[0], tess[3].vert[1], tess[3].vert[2], planet, level);
        }
    }

    void GetLevel(ArrayList<Tessa> list, int level) {
        level--;
        if (level <= 0 || tess[0].tess[0] == null) {
            list.add(tess[0]);
            list.add(tess[1]);
            list.add(tess[2]);
            list.add(tess[3]);
        } else {
            if (tess[0].tess[0] != null) {
                tess[0].GetLevel(list, level);
                tess[1].GetLevel(list, level);
                tess[2].GetLevel(list, level);
                tess[3].GetLevel(list, level);
            }
        }
    }

    void ColorizeLevel(GeoPlanet planet, Planet map, Vector basis, Vector stain) {
        Vector c = new Vector(AverageNoise(center.x * planet.atmos, center.y * planet.atmos, center.z * planet.atmos, planet.clouds),
                AverageNoise(center.x * planet.water, center.y * planet.water, center.z * planet.water, planet.rivers),
                AverageNoise(planet.land * center.z, planet.land * center.y, planet.land * center.x, planet.rain));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {

                double ht = tess[i].vert[j].h / planet.mass;
                //Clamp(ht,-water,rain);
                double lum = (1 + Math.abs(tess[i].vert[j].v.y)) * planet.temp;
                if (tess[i].vert[j].p.y > tess[i].vert[j].p.z) {
                    tess[i].vert[j].p.z *= lum * (planet.rain / planet.temp);
                }

                tess[i].vert[j].p.z += Math.abs(ht * planet.water);
                if (tess[i].vert[j].p.z > tess[i].vert[j].p.y) {
                    tess[i].vert[j].p.y *= lum * (planet.rivers / planet.temp);
                }
                if (tess[i].vert[j].h > 0) {
                    tess[i].vert[j].p.y += ht * planet.water;
                }
                if (Math.abs(tess[i].vert[j].v.y) > tess[i].vert[j].p.x) {
                    tess[i].vert[j].p.x *= lum * (planet.temp / planet.clouds);
                }
                tess[i].vert[j].p.x += ht / planet.atmos;

                if (tess[i].vert[j].p.x < 0) {
                    tess[i].vert[j].p.x = 0;
                }
                c = c.Add(c, new Vector(AverageNoise(1 + center.z, 1 + center.x, 1 + center.y, planet.wind),
                        AverageNoise(1 + center.x, 1 + center.y, 1 + center.z, planet.land),
                        AverageNoise(1 + center.z, 1 + center.y, 1 + center.x, planet.water))).AsVector();

                //c=c.Add(c, new Vector(RandomAverage(wind), RandomAverage(land), RandomAverage(rain))).AsVector();
                c.Scale(0.5);
                tess[i].vert[j].p = c.Add(c, tess[i].vert[j].p).AsVector();
                tess[i].vert[j].p.Scale(0.5);
                //if (Math.abs(tess[i].vert[j].v.y)>tess[i].vert[j].p.z) {
                lum = (2.73 - planet.temp) * Math.abs(tess[i].vert[j].v.y) * Math.abs(tess[i].vert[j].v.y);
                tess[i].vert[j].p.x += lum * (planet.clouds / planet.temp) / planet.size;//+ht;
                tess[i].vert[j].p.y += lum * (planet.rivers / planet.temp) * planet.size;//+ht;
                tess[i].vert[j].p.z += lum * (planet.temp / planet.rain) * planet.size;//+Math.abs(ht);
                //}
            }
            c = c.Add(c, tess[i].vert[0].p).AsVector();
            c = c.Add(c, tess[i].vert[1].p).AsVector();
            c = c.Add(c, tess[i].vert[2].p).AsVector();
            c.Scale(0.25 * planet.temp);
            Vector c1 = tess[i].vert[0].p = c.Add(c, tess[i].vert[0].p).AsVector();
            tess[i].vert[0].p.Scale(Math.abs(tess[i].vert[0].h));
            Vector c2 = tess[i].vert[1].p = c.Add(c, tess[i].vert[1].p).AsVector();
            tess[i].vert[1].p.Scale(Math.abs(tess[i].vert[1].h));
            Vector c3 = tess[i].vert[2].p = c.Add(c, tess[i].vert[2].p).AsVector();
            tess[i].vert[2].p.Scale(Math.abs(tess[i].vert[2].h));
            //
            Vector r1 = c1.Add(c2, c3).AsVector();
            Vector r2 = c2.Add(c1, c3).AsVector();
            Vector r3 = c3.Add(c2, c1).AsVector();
            double t1 = noise((float) r1.y, (float) r1.z) * (Math.PI - planet.temp);
            double t2 = noise((float) r2.y, (float) r2.z) * (Math.PI - planet.temp);
            double t3 = noise((float) r3.y, (float) r3.z) * (Math.PI - planet.temp);
            r1.Scale((AverageNoise(c1.x, c1.y, c1.z, 1) + T + Math.abs(tess[i].vert[0].v.y * t1)) / (1 + c1.Length()));
            r2.Scale((AverageNoise(c2.x, c2.y, c2.z, 1) + T + Math.abs(tess[i].vert[1].v.y * t2)) / (1 + c2.Length()));
            r3.Scale((AverageNoise(c3.x, c3.y, c3.z, 1) + T + Math.abs(tess[i].vert[2].v.y * t3)) / (1 + c3.Length()));
            tess[i].vert[0].p = c1.Add(c1, r1).AsVector();
            tess[i].vert[1].p = c2.Add(c2, r2).AsVector();
            tess[i].vert[2].p = c3.Add(c3, r3).AsVector();
            tess[i].vert[0].p.Scale(T + 0.25 * planet.temp);
            tess[i].vert[1].p.Scale(T + 0.25 * planet.temp);
            tess[i].vert[2].p.Scale(T + 0.25 * planet.temp);
            //
            double cy = (T + Math.abs(0.5 - Math.abs(tess[i].center.y))) * (planet.temp);
            double cx = Math.abs(T + 1 + Math.abs(tess[i].center.y));
            tess[i].vert[0].p = tess[i].vert[0].p.Add(tess[i].vert[0].p, new Vector(tess[i].vert[0].h, tess[i].vert[0].h, tess[i].vert[0].h)).AsVector();
            tess[i].vert[1].p = tess[i].vert[1].p.Add(tess[i].vert[1].p, new Vector(tess[i].vert[1].h, tess[i].vert[1].h, tess[i].vert[1].h)).AsVector();
            tess[i].vert[2].p = tess[i].vert[2].p.Add(tess[i].vert[2].p, new Vector(tess[i].vert[2].h, tess[i].vert[2].h, tess[i].vert[2].h)).AsVector();
            tess[i].vert[0].p.Scale((cx + cy) / planet.mass);
            tess[i].vert[1].p.Scale((cx + cy) / planet.mass);
            tess[i].vert[2].p.Scale((cx + cy) / planet.mass);
            if (tess[i].vert[0].p.z < Linear.Epsilon) {
                tess[i].vert[0].p.z += Math.abs(tess[i].vert[0].h);
                tess[i].vert[0].p.z = 1.0 - noise(1 + (float) tess[i].vert[0].p.z);
            }
            if (tess[i].vert[1].p.z < Linear.Epsilon) {
                tess[i].vert[1].p.z += Math.abs(tess[i].vert[1].h);
                tess[i].vert[1].p.z = 1.0 - noise(1 + (float) tess[i].vert[1].p.z);
            }
            if (tess[i].vert[2].p.z < Linear.Epsilon) {
                tess[i].vert[2].p.z += Math.abs(tess[i].vert[2].h);
                tess[i].vert[2].p.z = 1.0 - noise(1 + (float) tess[i].vert[2].p.z);
            }
            if (tess[i].vert[0].h > planet.atmos * 2) {
                tess[i].vert[0].p.x /= tess[i].vert[0].h;
                tess[i].vert[0].p.y /= tess[i].vert[0].h;
                tess[i].vert[0].p.z /= tess[i].vert[0].h;
            }
            if (tess[i].vert[1].h > planet.atmos * 2) {
                tess[i].vert[1].p.x /= tess[i].vert[1].h;
                tess[i].vert[1].p.y /= tess[i].vert[1].h;
                tess[i].vert[1].p.z /= tess[i].vert[1].h;
            }
            if (tess[i].vert[2].h > planet.atmos * 2) {
                tess[i].vert[2].p.x /= tess[i].vert[2].h;
                tess[i].vert[2].p.y /= tess[i].vert[2].h;
                tess[i].vert[2].p.z /= tess[i].vert[2].h;
            }
            //
            if (tess[i].vert[0].h < planet.atmos && tess[i].vert[0].h > 0) {
                tess[i].vert[0].p.y += planet.rain / planet.mass;
            }
            if (tess[i].vert[1].h < planet.atmos && tess[i].vert[1].h > 0) {
                tess[i].vert[1].p.y += planet.rain / planet.mass;
            }
            if (tess[i].vert[2].h < planet.atmos && tess[i].vert[2].h > 0) {
                tess[i].vert[2].p.y += planet.rain / planet.mass;
            }

            if (tess[i].vert[0].h < planet.atmos && tess[i].vert[0].h > -planet.atmos) {
                tess[i].vert[0].p.y += planet.rain / planet.mass;
            }
            if (tess[i].vert[1].h < planet.atmos && tess[i].vert[1].h > -planet.atmos) {
                tess[i].vert[1].p.y += planet.rain / planet.mass;
            }
            if (tess[i].vert[2].h < planet.atmos && tess[i].vert[2].h > -planet.atmos) {
                tess[i].vert[2].p.y += planet.rain / planet.mass;
            }
            //
            if (tess[i].vert[0].h < -planet.water) {
                tess[i].vert[0].p.y += planet.water / Math.PI;
            }
            if (tess[i].vert[1].h < -planet.water) {
                tess[i].vert[1].p.y += planet.water / Math.PI;
            }
            if (tess[i].vert[2].h < -planet.water) {
                tess[i].vert[2].p.y += planet.water / Math.PI;
            }

            if (tess[i].vert[0].p.x < planet.temp) {
                tess[i].vert[0].p.x += planet.wind / planet.mass;
            }
            if (tess[i].vert[1].p.x < planet.temp) {
                tess[i].vert[1].p.x += planet.wind / planet.mass;
            }
            if (tess[i].vert[2].p.x < planet.temp) {
                tess[i].vert[2].p.x += planet.wind / planet.mass;
            }
            c1 = NoiseVector(tess[i].vert[0].p, 2);
            c2 = NoiseVector(tess[i].vert[1].p, 2);
            c3 = NoiseVector(tess[i].vert[2].p, 2);
            c1 = c1.Add(c1, stain).AsVector();
            c1.Scale(0.25 + (Math.abs(Math.asin(tess[i].vert[0].v.y))) / (planet.temp * Math.PI));
            //if(tess[i].vert[0].h>0)c1.Scale(Math.abs(tess[i].vert[0].h*planet.clouds)/(planet.temp*Math.PI*4));
            tess[i].vert[0].p = c1.Add(c1, tess[i].vert[0].p).AsVector();
            tess[i].vert[0].p.Scale(0.5);
            c2 = c2.Add(c2, stain).AsVector();
            c2.Scale(0.25 + (Math.abs(Math.asin(tess[i].vert[1].v.y))) / (planet.temp * Math.PI));
            //if(tess[i].vert[1].h>0)c2.Scale(Math.abs(tess[i].vert[1].h*planet.clouds)/(planet.temp*Math.PI*4));
            tess[i].vert[1].p = c2.Add(c2, tess[i].vert[1].p).AsVector();
            tess[i].vert[1].p.Scale(0.5);
            c3 = c3.Add(c3, stain).AsVector();
            c3.Scale(0.25 + (Math.abs(Math.asin(tess[i].vert[2].v.y))) / (planet.temp * Math.PI));
            //if(tess[i].vert[2].h>0)c3.Scale((tess[i].vert[2].h*planet.clouds)/(planet.temp*Math.PI*4));
            tess[i].vert[2].p = c3.Add(c3, tess[i].vert[2].p).AsVector();
            tess[i].vert[2].p.Scale(0.5);
            if (tess[i].vert[0].h < 0) {
                tess[i].vert[0].p.z += planet.water / (planet.mass / planet.temp);
            } else {
                tess[i].vert[0].p.y += planet.rain / (planet.mass + tess[i].vert[0].h);

            }
            if (tess[i].vert[1].h < 0) {
                tess[i].vert[1].p.z += planet.water / (planet.mass / planet.temp);
            } else {
                tess[i].vert[1].p.y += planet.rain / (planet.mass + tess[i].vert[1].h);

            }
            if (tess[i].vert[2].h < 0) {
                tess[i].vert[2].p.z += planet.water / (planet.mass / planet.temp);
            } else {
                tess[i].vert[2].p.y += planet.rain / (planet.mass + tess[i].vert[2].h);

            }
            Linear.Clamp(tess[i].vert[0].p);
            Linear.Clamp(tess[i].vert[1].p);
            Linear.Clamp(tess[i].vert[2].p);
            if (map != null && basis != null) {

                int fbx = (int) (basis.x);
                int fby = (int) (basis.y);
                int tbx = (int) (basis.x + basis.z);
                int tby = (int) (basis.y + basis.z * 0.5);
                for (int ix = fbx; ix < tbx; ix++) {
                    int bx = ix % map.surfaceMap.width;
                    for (int iy = fby; iy < tby; iy++) {
                        int by = iy;
                        if (by < 0) {
                            by = 0;
                        }
                        if (by >= map.surfaceMap.height) {
                            by = map.surfaceMap.height - 1;
                        };
                        tess[i].vert[0].p.x += map.surfaceMap.data[bx][by].Red() * 0.0039;
                        tess[i].vert[1].p.x += map.surfaceMap.data[bx][by].Red() * 0.0039;
                        tess[i].vert[2].p.x += map.surfaceMap.data[bx][by].Red() * 0.0039;
                        tess[i].vert[0].p.y += map.surfaceMap.data[bx][by].Green() * 0.0039;
                        tess[i].vert[1].p.y += map.surfaceMap.data[bx][by].Green() * 0.0039;
                        tess[i].vert[2].p.y += map.surfaceMap.data[bx][by].Green() * 0.0039;
                        tess[i].vert[0].p.z += map.surfaceMap.data[bx][by].Blue() * 0.0039;
                        tess[i].vert[1].p.z += map.surfaceMap.data[bx][by].Blue() * 0.0039;
                        tess[i].vert[2].p.z += map.surfaceMap.data[bx][by].Blue() * 0.0039;
                        tess[i].vert[0].p.Scale(0.5);
                        tess[i].vert[1].p.Scale(0.5);
                        tess[i].vert[2].p.Scale(0.5);
                    }
                }
            }
        }
    }

    void ColorizeLevel(GeoPlanet planet, Vector stain, int level) {

        level--;
        if (level <= 0 || tess[0].tess == null || tess[0].tess[0] == null) {
            ColorizeLevel(planet, null, null, stain);
        } else {
            //ColorizeLevel(planet);
            tess[0].ColorizeLevel(planet, stain, level);
            tess[1].ColorizeLevel(planet, stain, level);
            tess[2].ColorizeLevel(planet, stain, level);
            tess[3].ColorizeLevel(planet, stain, level);
        }
    }

    void ColorizeLevel(GeoPlanet planet, Planet map, Vector stain, int level, double z) {

        level--;
        if (level <= 0 || tess[0].tess == null || tess[0].tess[0] == null) {
            Vector basis = center.AsVector();
            double x = (Math.atan2(basis.z, basis.x) / Math.PI) * map.surfaceMap.width * 0.5;
            double y = (Math.acos(basis.y) / Math.PI) * map.surfaceMap.height * 0.5;
            basis = new Vector(map.surfaceMap.width * 0.5 + x, map.surfaceMap.height * 0.5 + y, z * (y + map.surfaceMap.height * 0.5));
            ColorizeLevel(planet, map, basis, stain);
        } else {
            //ColorizeLevel(planet);
            z *= 0.5;
            tess[0].ColorizeLevel(planet, map, stain, level, z);
            tess[1].ColorizeLevel(planet, map, stain, level, z);
            tess[2].ColorizeLevel(planet, map, stain, level, z);
            tess[3].ColorizeLevel(planet, map, stain, level, z);
        }
    }

    void Guide(Tessa t) {
        if (tess[0] != null) {

            tess[1].vert[0].parent = tess[1];
            tess[1].vert[1].parent = tess[1];
            tess[1].vert[2].parent = tess[1];
            //
            tess[2].vert[0].parent = tess[2];
            tess[2].vert[1].parent = tess[2];
            tess[2].vert[2].parent = tess[2];
            //
            tess[3].vert[0].parent = tess[3];
            tess[3].vert[1].parent = tess[3];
            tess[3].vert[2].parent = tess[3];
            //
            tess[0].vert[0].parent = tess[0];
            tess[0].vert[1].parent = tess[0];
            tess[0].vert[2].parent = tess[0];
            //
            tess[0].parent = t;
            tess[1].parent = t;
            tess[2].parent = t;
            tess[3].parent = t;
            tess[3].Guide(tess[3]);
            tess[2].Guide(tess[2]);
            tess[1].Guide(tess[1]);
            tess[0].Guide(tess[0]);
        }
    }

    void ContourLevel() {
        //double ht[]=new double[3];

        for (int i = 1; i < 4; i++) {
            double total = tess[i].vert[0].h + tess[i].vert[1].h + tess[i].vert[2].h;
            for (int j = 0; j < 3; j++) {

                tess[i].vert[j].h += total;//+ht[j];//+(RandomAverage(land/water)-RandomAverage(water/land))*Math.PI*land;
                tess[i].vert[j].h *= 0.25;
            }
        }
    }

    void ContourLevel(GeoPlanet planet, int level) {
        level--;
        if (level <= 0) {
            if (tess[0] != null) {

                for (int i = 0; i < 4; i++) {

                    double total = tess[i].vert[0].h + tess[i].vert[1].h + tess[i].vert[2].h;
                    for (int j = 0; j < 3; j++) {

                        tess[i].vert[j].h += total;//+ht[j];//+(RandomAverage(land/water)-RandomAverage(water/land))*Math.PI*land;

                        tess[i].vert[j].h *= 0.25;
                        if (tess[i].vert[j].h > planet.atmos * Math.PI * 2) {
                            tess[i].vert[j].h /= Math.PI;
                        }
                        if (tess[i].vert[j].h > planet.atmos * Math.PI) {
                            tess[i].vert[j].h -= noise((float) (tess[i].vert[j].h * planet.wind), (float) (tess[i].vert[j].h * planet.rain)) * planet.clouds * planet.size;
                        }
                    }
                }
            }
        } else {
            ContourLevel(planet, level);
        }
    }

    void RiverLevel(int level, int to) {
        level--;
        if (level <= to) {
            if (tess[0] != null) {
                int highi = 0;
                int highj = 0;
                double highest = -Linear.MAX_MAP;
                int lowi = 0;
                int lowj = 0;
                double lowest = Linear.MAX_MAP;
                for (int i = 0; i < 4; i++) {

                    for (int j = 0; j < 3; j++) {

                        if (tess[i].vert[j].h < lowest) {
                            lowest = tess[i].vert[j].h;
                            lowi = i;
                            lowj = j;
                        }
                        if (tess[i].vert[j].h > highest) {
                            highest = tess[i].vert[j].h;
                            highi = i;
                            highj = j;
                        }
                    }
                }
                if ((highest > 0 && lowest > 0) || (highest < 0 && lowest < 0)) {
                    tess[highi].vert[highj].h += tess[lowi].vert[lowj].h / tess[highi].vert[highj].h;
                    tess[highi].vert[highj].h *= 0.5;
                    tess[lowi].vert[lowj].h = tess[highi].vert[highj].h;
                    if ((highest > 0 && lowest > 0)) {
                        double w1 = Math.abs(tess[lowi].vert[1].h + tess[lowi].vert[2].h) * 0.5;
                        double w2 = Math.abs(tess[lowi].vert[0].h + tess[lowi].vert[2].h) * 0.5;
                        double w3 = Math.abs(tess[lowi].vert[1].h + tess[lowi].vert[0].h) * 0.5;
                        for (int i = 0; i < 4; i++) {
                            if (tess[i].vert[0].h > w1) {
                                tess[i].vert[0].h += w1;
                                tess[i].vert[0].h *= 0.5;
                            }
                            if (tess[i].vert[1].h > w2) {
                                tess[i].vert[1].h += w2;
                                tess[i].vert[1].h *= 0.5;
                            }
                            if (tess[i].vert[2].h > w3) {
                                tess[i].vert[2].h += w3;
                                tess[i].vert[2].h *= 0.5;
                            }
                        }
                        if (this.parent != null) {
                            if (this.parent.vert[0].h > w1) {
                                this.parent.vert[0].h += w1;
                                this.parent.vert[0].h *= 0.5;
                            }
                            if (this.parent.vert[1].h > w2) {
                                this.parent.vert[1].h += w2;
                                this.parent.vert[1].h *= 0.5;
                            }
                            if (this.parent.vert[2].h > w3) {
                                this.parent.vert[2].h += w3;
                                this.parent.vert[2].h *= 0.5;
                            }
                        }
                    }
                }
            } else {
                tess[0].RiverLevel(level, to);
                tess[1].RiverLevel(level, to);
                tess[2].RiverLevel(level, to);
                tess[3].RiverLevel(level, to);
            }
        }
    }

    void Move(Tessa t) {
        if (t.parent != null) {
            for (int i = 0; i < 4; i++) {
                t.parent.tess[i].vert[0].h += t.vert[0].h;
                t.parent.tess[i].vert[0].h *= 0.5;
                t.parent.tess[i].vert[1].h += t.vert[1].h;
                t.parent.tess[i].vert[1].h *= 0.5;
                t.parent.tess[i].vert[2].h += t.vert[2].h;
                t.parent.tess[i].vert[2].h *= 0.5;
            }
        }
    }

    void Transfer(int level) {
        level--;
        if (level <= 0) {
            if (tess[0] != null) {
                Move(tess[0]);
                Move(tess[1]);
                Move(tess[2]);
                Move(tess[3]);
            }
        } else {
            if (tess[0] != null) {
                tess[0].Transfer(level);
                tess[1].Transfer(level);
                tess[2].Transfer(level);
                tess[3].Transfer(level);
            }
        }
    }

    void Blend(GeoPlanet planet, Tessa t) {
        if (t.parent != null) {
            double me = 1.0 / planet.mass;
            for (int i = 0; i < 4; i++) {
                t.parent.tess[i].vert[0].h += -t.vert[0].h * me;
                t.parent.tess[i].vert[1].h += -t.vert[1].h * me;
                t.parent.tess[i].vert[2].h += -t.vert[2].h * me;
            }
        }
    }

    void Blender(GeoPlanet planet, int level) {
        level--;
        if (level <= 0) {
            if (tess[0] != null) {
                Blend(planet, tess[0]);
                Blend(planet, tess[1]);
                Blend(planet, tess[2]);
                Blend(planet, tess[3]);
            }
        } else {
            if (tess[0] != null) {
                tess[0].Blender(planet, level);
                tess[1].Blender(planet, level);
                tess[2].Blender(planet, level);
                tess[3].Blender(planet, level);
            }
        }
    }

    void Flatten(Tessa t) {
        if (t.parent != null) {
            for (int j = 0; j < 4; j++) {
                int i = Molecula.rnd.nextInt(4);
                if ((t.parent.tess[i].vert[0].h > 0 && t.parent.tess[i].vert[0].h > 0)) {
                    t.parent.tess[i].vert[0].h += t.vert[0].h / t.parent.tess[i].vert[0].h;
                    t.parent.tess[i].vert[0].h *= 0.5;
                } else if ((t.parent.tess[i].vert[0].h < 0 && t.parent.tess[i].vert[0].h < 0)) {
                    t.parent.tess[i].vert[0].h -= t.vert[0].h / t.parent.tess[i].vert[0].h;
                    t.parent.tess[i].vert[0].h *= 0.5;
                }
                if ((t.parent.tess[i].vert[1].h > 0 && t.parent.tess[i].vert[1].h > 0)) {
                    t.parent.tess[i].vert[1].h += t.vert[1].h / t.parent.tess[i].vert[1].h;
                    t.parent.tess[i].vert[1].h *= 0.5;
                } else if ((t.parent.tess[i].vert[1].h < 0 && t.parent.tess[i].vert[1].h < 0)) {
                    t.parent.tess[i].vert[1].h -= t.vert[1].h / t.parent.tess[i].vert[1].h;
                    t.parent.tess[i].vert[1].h *= 0.5;
                }
                if ((t.parent.tess[i].vert[2].h > 0 && t.parent.tess[i].vert[2].h > 0)) {
                    t.parent.tess[i].vert[2].h += t.vert[2].h / t.parent.tess[i].vert[2].h;
                    t.parent.tess[i].vert[2].h *= 0.5;
                } else if ((t.parent.tess[i].vert[2].h < 0 && t.parent.tess[i].vert[2].h < 0)) {
                    t.parent.tess[i].vert[2].h -= t.vert[2].h / t.parent.tess[i].vert[2].h;
                    t.parent.tess[i].vert[2].h *= 0.5;
                }
            }
        }
    }

    void Flattener(int level) {
        level--;
        if (level > 0) {
            if (tess[0] != null) {
                Flatten(tess[0]);
                Flatten(tess[1]);
                Flatten(tess[2]);
                Flatten(tess[3]);
                if (tess[0] != null) {
                    if (Molecula.rnd.nextInt(3) == 0) {
                        tess[0].Flattener(level);
                    }
                    if (Molecula.rnd.nextInt(3) == 0) {
                        tess[1].Flattener(level);
                    }
                    if (Molecula.rnd.nextInt(3) == 0) {
                        tess[2].Flattener(level);
                    }
                    if (Molecula.rnd.nextInt(3) == 0) {
                        tess[3].Flattener(level);
                    }
                }
            }
        }
    }

    void LandFall(Tessa t, double imass) {
        if (parent != null) {
            double me = imass;
            if ((parent.vert[0].h > 0 && t.vert[0].h > 0)) {
                double d = (t.vert[0].h - parent.vert[0].h);
                d *= me;

                t.vert[0].h += d;
            }
            if ((parent.vert[1].h > 0 && t.vert[1].h > 0)) {
                double d = (t.vert[1].h - parent.vert[1].h);
                d *= me;
                t.vert[1].h += d;
            }
            if ((parent.vert[2].h > 0 && t.vert[2].h > 0)) {
                double d = (t.vert[2].h - parent.vert[2].h);
                d *= me;

                t.vert[2].h += d;
            }
            //}
        }
    }

    void LandFalls(int level, double imass, int depth) {
        level--;
        if (level >= 0) {
            if (tess[0] != null) {
                LandFall(tess[0], imass);
                LandFall(tess[1], imass);
                LandFall(tess[2], imass);
                LandFall(tess[3], imass);
                if (tess[0] != null) {
                    for (int i = 0; i < depth - level; i++) {
                        if (Molecula.rnd.nextInt(2) == 0) {
                            tess[0].LandFalls(level, imass, depth);
                        }
                        if (Molecula.rnd.nextInt(2) == 0) {
                            tess[1].LandFalls(level, imass, depth);
                        }
                        if (Molecula.rnd.nextInt(2) == 0) {
                            tess[2].LandFalls(level, imass, depth);
                        }
                        if (Molecula.rnd.nextInt(2) == 0) {
                            tess[3].LandFalls(level, imass, depth);
                        }
                    }
                }
            }
        }
    }

    void River(GeoPlanet planet, Tessa t) {
        if (parent != null) {

            if ((parent.vert[0].h > 0 && t.vert[0].h > 0)) {
                double d = Math.abs(t.vert[0].h - parent.vert[0].h);
                d *= planet.mass;

                t.vert[0].h -= d * (planet.rain + planet.rivers);
                t.vert[0].h *= 0.5;
            }
            if ((parent.vert[1].h > 0 && t.vert[1].h > 0)) {
                double d = Math.abs(t.vert[1].h - parent.vert[1].h);
                d *= planet.mass;

                t.vert[1].h -= d * (planet.rain + planet.rivers);
                t.vert[1].h *= 0.5;
            }
            if ((parent.vert[2].h > 0 && t.vert[2].h > 0)) {
                double d = Math.abs(t.vert[2].h - parent.vert[2].h);
                d *= planet.mass;

                t.vert[2].h -= d * (planet.rain + planet.rivers);
                t.vert[2].h *= 0.5;
            }
            //}
        }
    }

    void Riverer(GeoPlanet planet, int level) {
        level--;
        if (tess[0] != null) {
            double av0 = (vert[0].h + vert[1].h + vert[2].h);
            if (av0 > Linear.Epsilon) {
                double av1 = (tess[0].vert[0].h + tess[0].vert[1].h + tess[0].vert[2].h);
                double av2 = (tess[1].vert[0].h + tess[1].vert[1].h + tess[1].vert[2].h);
                double av3 = (tess[2].vert[0].h + tess[2].vert[1].h + tess[2].vert[2].h);
                double av4 = (tess[3].vert[0].h + tess[3].vert[1].h + tess[3].vert[2].h);
                if (av1 < av0) {
                    River(planet, tess[0]);
                }
                if (av2 < av0) {
                    River(planet, tess[1]);
                }
                if (av3 < av0) {
                    River(planet, tess[2]);
                }
                if (av4 < av0) {
                    River(planet, tess[3]);
                }
                if (av1 < av0) {
                    tess[0].Riverer(planet, level);
                }
                if (av2 < av0) {
                    tess[1].Riverer(planet, level);
                }
                if (av3 < av0) {
                    tess[2].Riverer(planet, level);
                }
                if (av4 < av0) {
                    tess[3].Riverer(planet, level);
                }
            }
        }
    }

    int Equity(Tessa test) {

        //double scale=mass*temp;
        int n = 0;
        if (test != null) {
            for (int j = 0; j < 3; j++) {
                if (test.vert[0].Equal(vert[j])) {
                    vert[j] = test.vert[0];
                    n++;
                }
                if (test.vert[1].Equal(vert[j])) {
                    vert[j] = test.vert[1];
                    n++;
                }
                if (test.vert[2].Equal(vert[j])) {
                    vert[j] = test.vert[2];
                    n++;
                }
            }
        }
        return n;
    }

    int VectorEquity(Tessa test) {

        //double scale=mass*temp;
        int n = 0;
        if (test != null) {
            for (int j = 0; j < 3; j++) {
                if (test.vert[0].Equal(vert[j])) {
                    vert[j].v = test.vert[0].v;
                    vert[j].n = test.vert[0].n;
                    n++;
                }
                if (test.vert[1].Equal(vert[j])) {
                    vert[j].v = test.vert[1].v;
                    vert[j].n = test.vert[1].n;
                    n++;
                }
                if (test.vert[2].Equal(vert[j])) {
                    vert[j].v = test.vert[2].v;
                    vert[j].n = test.vert[2].n;
                    n++;
                }
            }
        }
        return n;
    }

    int VectorEquity(Tessa test, double epsilon) {

        //double scale=mass*temp;
        int n = 0;
        if (test != null) {
            for (int j = 0; j < 3; j++) {
                if (test.vert[0].Equal(vert[j], epsilon)) {
                    vert[j].v = test.vert[0].v;
                    vert[j].n = test.vert[0].n;
                    n++;
                }
                if (test.vert[1].Equal(vert[j], epsilon)) {
                    vert[j].v = test.vert[1].v;
                    vert[j].n = test.vert[1].n;
                    n++;
                }
                if (test.vert[2].Equal(vert[j], epsilon)) {
                    vert[j].v = test.vert[2].v;
                    vert[j].n = test.vert[2].n;
                    n++;
                }
            }
        }
        return n;
    }

    int Equity(Tessa test, double epsilon) {

        //double scale=mass*temp;
        int n = 0;
        if (test != null) {
            for (int j = 0; j < 3; j++) {
                if (test.vert[0].Equal(vert[j], epsilon)) {
                    vert[j] = test.vert[0];
                    n++;
                }
                if (test.vert[1].Equal(vert[j], epsilon)) {
                    vert[j] = test.vert[1];
                    n++;
                }
                if (test.vert[2].Equal(vert[j], epsilon)) {
                    vert[j] = test.vert[2];
                    n++;
                }
            }
        }
        return n;
    }

    void Equality(Tessa test) {
        if (test != null) {
            if (test.tess[0] != null && tess[0] != null) {

                for (int i = 0; i < 4; i++) {
                    tess[i].Equity(test);
                    tess[i].Equality(test);
                    for (int j = i + 1; j < 4; j++) {
                        tess[i].Equity(test.tess[j]);
                        tess[i].Equality(test.tess[j]);
                    }
                }
            }
        }
    }

    void Seed(int level, int from, int to, double seed) {
        level--;
        if (level < from && level > to) {
            if (tess[0] != null) {
                double av = vert[0].h + vert[1].h + vert[2].h
                        + tess[0].vert[0].h + tess[0].vert[1].h + tess[0].vert[2].h
                        + tess[1].vert[0].h + tess[1].vert[1].h + tess[1].vert[2].h
                        + tess[2].vert[0].h + tess[2].vert[1].h + tess[2].vert[2].h
                        + tess[3].vert[0].h + tess[3].vert[1].h + tess[3].vert[2].h;
                if ((av > 0 && seed > 0) || (av < 0 && seed < 0)) {
                    seed = -seed * Math.abs(av / 15);
                }
                for (int i = 0; i < 4; i++) {

                    double y = (1 + Math.abs(tess[i].center.y));
                    tess[i].vert[0].h += seed * y * noise((float) vert[1].h, (float) vert[2].h);
                    tess[i].vert[1].h += seed * y * noise((float) vert[0].h, (float) vert[2].h);
                    tess[i].vert[2].h += seed * y * noise((float) vert[1].h, (float) vert[0].h);

                    tess[i].vert[0].h *= 0.5;
                    tess[i].vert[1].h *= 0.5;
                    tess[i].vert[2].h *= 0.5;
                }
            }
        } else {
            if (level >= 0 && tess[0] != null) {

                double av = vert[0].h + vert[1].h + vert[2].h
                        + tess[0].vert[0].h + tess[0].vert[1].h + tess[0].vert[2].h
                        + tess[1].vert[0].h + tess[1].vert[1].h + tess[1].vert[2].h
                        + tess[2].vert[0].h + tess[2].vert[1].h + tess[2].vert[2].h
                        + tess[3].vert[0].h + tess[3].vert[1].h + tess[3].vert[2].h;
                if ((av > 0 && seed > 0) || (av < 0 && seed < 0)) {
                    seed = -seed * Math.abs(av / 15);
                }
                tess[0].Seed(Molecula.rnd.nextInt(1 + level), from, to, seed);
                tess[1].Seed(Molecula.rnd.nextInt(1 + level), from, to, seed);
                tess[2].Seed(Molecula.rnd.nextInt(1 + level), from, to, seed);
                tess[3].Seed(Molecula.rnd.nextInt(1 + level), from, to, seed);
            }
        }
    }

    void Merge(Tessa t) {

        if (t != null && tess[0] != null && tess[0].tess[0] == null) {
            for (int n = 0; n < 4; n++) {
                int i = Molecula.rnd.nextInt(4);

                tess[i].vert[0].h = tess[i].vert[0].h * 617 + t.vert[2].h;
                tess[i].vert[0].h *= 0.00161812297734627831715210355987;
                tess[i].vert[1].h = tess[i].vert[1].h * 617 + t.vert[0].h;
                tess[i].vert[1].h *= 0.00161812297734627831715210355987;
                tess[i].vert[2].h = tess[i].vert[2].h * 617 + t.vert[1].h;
                tess[i].vert[2].h *= 0.00161812297734627831715210355987;
            }
        } else {
            //for (int i=0; i<4; i++) {
            tess[Molecula.rnd.nextInt(4)].Merge(t.tess[Molecula.rnd.nextInt(4)]);
            tess[Molecula.rnd.nextInt(4)].Merge(t.tess[Molecula.rnd.nextInt(4)]);
            tess[Molecula.rnd.nextInt(4)].Merge(t.tess[Molecula.rnd.nextInt(4)]);
            tess[Molecula.rnd.nextInt(4)].Merge(t.tess[Molecula.rnd.nextInt(4)]);
            //}
        }
    }

    void Blur(int level, int to, Tessa t) {
        level--;
        if (level <= to) {
            if (tess[0] != null) {
                for (int i = 0; i < 4; i++) {
                    tess[i].vert[0].h += t.vert[0].h * t.vert[0].h + t.vert[1].h;
                    tess[i].vert[1].h += t.vert[1].h * t.vert[1].h + t.vert[2].h;
                    tess[i].vert[2].h += t.vert[2].h * t.vert[2].h + t.vert[0].h;

                    tess[i].vert[0].h *= 0.125;
                    tess[i].vert[1].h *= 0.125;
                    tess[i].vert[2].h *= 0.125;
                }
            }
        } else {
            Blur(level, to, t);
        }
    }

    void Add(Tessa test) {

        if (tess[0].tess[0] == null) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    tess[i].vert[j].h += test.vert[0].h * noise(1 + (float) test.vert[0].h);
                    tess[i].vert[j].p = tess[i].vert[0].p.Add(tess[i].vert[j].p, test.vert[j].p).AsVector();
                    tess[i].vert[j].p.Scale(0.5);
                }
            }
        } else {
            tess[0].Add(test.tess[0]);
            tess[1].Add(test.tess[1]);
            tess[2].Add(test.tess[2]);
            tess[3].Add(test.tess[3]);
        }
    }

    void Scale(Tessa test, double scale) {

        if (tess[0].tess[0] == null) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    tess[i].vert[j].h *= scale;
                }
            }
        } else {
            tess[0].Scale(test.tess[0], scale);
            tess[1].Scale(test.tess[1], scale);
            tess[2].Scale(test.tess[2], scale);
            tess[3].Scale(test.tess[3], scale);
        }
    }

    @Override
    public void SetDrawing(Point center, DrawType typ, DrawType subtyp, DrawChain chain, Pigment col, int index) {
        draws = typ;
        subDraws = subtyp;
        graphicsIndex = index;

    }

    @Override
    public String Name() {
        return "Tessa" + this.vert[0].index + "AdjTo" + this.vert[0].adjacent;
    }

    @Override
    public Quarternion Rotation() {
        return rotation;
    }

    @Override
    public DrawType Draws() {
        return draws;
    }

    @Override
    public DrawType SubDraws() {
        return subDraws;
    }

    @Override
    public Drawable Drawing(int p) {
        return tess[p];
    }

    @Override
    public DrawChain Drawings() {
        return this;
    }

    @Override
    public boolean DoubleSided() {
        return sided;
    }

    @Override
    public Pigment Colour() {
        return this.color;
    }

    @Override
    public int Points() {
        return 3;
    }

    @Override
    public Vector GetVector(int i) {
        return vert[i].v;
    }

    @Override
    public Normal Norm(int n) {
        return vert[n].n;
    }

    @Override
    public Point Center() {

        return center;
    }

    @Override
    public Drawable Next() {
        currentVertex++;
        if (currentVertex == 3) {
            currentVertex = -1;
            return null;
        }
        return vert[currentVertex];
    }

    @Override
    public Drawable Prev() {

        currentVertex--;
        if (currentVertex == -1) {
            currentVertex = 3;
            return null;
        }
        return vert[currentVertex];
    }

    @Override
    public Graphical Graphic() {

        return ArcEngine.DefaultGraphical;
    }

    @Override
    public void Draw(Drawable chain, Graphical g) {

    }

    @Override
    public int GetGraphicsIndex() {
        return graphicsIndex;
    }

}
