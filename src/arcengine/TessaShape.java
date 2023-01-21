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
public class TessaShape implements FromFile,ToFile{

    RingList<Tessa> list = new RingList();
    BoundingBox bb = new BoundingBox();
    Sphere sphere = new Sphere();

    TessaShape() {
    }

    TessaShape(TessaShape shape) {
        Node<Tessa> n = shape.list.Start();
        for (; n.data != null; n = n.next) {
            list.Append(new Tessa(n.data));
        }
    }

    TessaShape(RingList<Tessa> tessas) {
        list = (tessas);
    }

    @Override
    public boolean From(StellarFiles f) {
        
        boolean ok=true;
        Tessa first=new Tessa();
        first.From(f);
        Integer i[]=f.GetNameData();
        list.Append(first);
        for(int n=1;n<i[0];n++){
         first=new Tessa();
         first.From(f);
         list.Append(first);
         
        }
        bb=new BoundingBox();
        ok&=bb.pMin.From(f);
        ok&=bb.pMax.From(f);
        sphere=new Sphere();
        ok&=sphere.center.From(f);
        sphere.radius=sphere.center.r;
        VectorEquality(list);
        return ok;
    }
    @Override
    public boolean To(StellarFiles f) {
        boolean ok=true;
        f.AddName("Shape "+list.Length());
        for(Node<Tessa>n=list.Start();n.data!=null;n=n.next){
            ok&=n.data.To(f);
        }
        ok&=bb.pMin.To(f);
        ok&=bb.pMax.To(f);
        ok&=sphere.center.To(f);
        return ok;
    }
    void AddTessaShape(RingList<Tessa> tessas) {
        list.PasteAll(tessas);
    }

    void CopyTessaShape(RingList<Tessa> tessas) {
        list.Append(tessas);
    }

    static void TestBounds(TessaShape shape, Node<Tessa> n) {

        if (n.data.vert[0].v.x < shape.bb.pMin.x) {
            shape.bb.pMin.x = n.data.vert[0].v.x;
        } else if (n.data.vert[0].v.x > shape.bb.pMax.x) {
            shape.bb.pMax.x = n.data.vert[0].v.x;
        }
        if (n.data.vert[1].v.x < shape.bb.pMin.x) {
            shape.bb.pMin.x = n.data.vert[1].v.x;
        } else if (n.data.vert[1].v.x > shape.bb.pMax.x) {
            shape.bb.pMax.x = n.data.vert[1].v.x;
        }
        if (n.data.vert[2].v.x < shape.bb.pMin.x) {
            shape.bb.pMin.x = n.data.vert[2].v.x;
        } else if (n.data.vert[2].v.x > shape.bb.pMax.x) {
            shape.bb.pMax.x = n.data.vert[2].v.x;
        }
        //
        if (n.data.vert[0].v.y < shape.bb.pMin.y) {
            shape.bb.pMin.y = n.data.vert[0].v.y;
        } else if (n.data.vert[0].v.y > shape.bb.pMax.y) {
            shape.bb.pMax.y = n.data.vert[0].v.y;
        }
        if (n.data.vert[1].v.y < shape.bb.pMin.y) {
            shape.bb.pMin.y = n.data.vert[1].v.y;
        } else if (n.data.vert[1].v.y > shape.bb.pMax.y) {
            shape.bb.pMax.y = n.data.vert[1].v.y;
        }
        if (n.data.vert[2].v.y < shape.bb.pMin.y) {
            shape.bb.pMin.y = n.data.vert[2].v.y;
        } else if (n.data.vert[2].v.y > shape.bb.pMax.y) {
            shape.bb.pMax.y = n.data.vert[2].v.y;
        }
        //
        if (n.data.vert[0].v.z < shape.bb.pMin.z) {
            shape.bb.pMin.z = n.data.vert[0].v.z;
        } else if (n.data.vert[0].v.z > shape.bb.pMax.z) {
            shape.bb.pMax.z = n.data.vert[0].v.z;
        }
        if (n.data.vert[1].v.z < shape.bb.pMin.y) {
            shape.bb.pMin.z = n.data.vert[1].v.z;
        } else if (n.data.vert[1].v.z > shape.bb.pMax.z) {
            shape.bb.pMax.z = n.data.vert[1].v.z;
        }
        if (n.data.vert[2].v.z < shape.bb.pMin.z) {
            shape.bb.pMin.z = n.data.vert[2].v.z;
        } else if (n.data.vert[2].v.z > shape.bb.pMax.z) {
            shape.bb.pMax.z = n.data.vert[2].v.z;
        }
        //
    }

    static void Transform(TessaShape shape, Matrix m) {
        Node<Tessa> t = shape.list.Start();
        shape.bb = new BoundingBox();
        //Vector center=new Vector();
        Vector v[][] = new Vector[shape.list.Length()][3];
        Normal n[][] = new Normal[shape.list.Length()][3];
        int i = 0;
        for (; t.data != null; i++, t = t.next) {
            v[i][0] = m.Mult(m, t.data.vert[0].v);
            v[i][1] = m.Mult(m, t.data.vert[1].v);
            v[i][2] = m.Mult(m, t.data.vert[2].v);
            //
            //
            n[i][0] = m.Mult(m, t.data.vert[0].n.AsVector()).Unity().AsNormal();
            n[i][1] = m.Mult(m, t.data.vert[1].n.AsVector()).Unity().AsNormal();
            n[i][2] = m.Mult(m, t.data.vert[2].n.AsVector()).Unity().AsNormal();
        }
        i = 0;
        t = shape.list.Start();
        for (; t.data != null; i++, t = t.next) {
            t.data.vert[0].v = v[i][0];
            t.data.vert[1].v = v[i][1];
            t.data.vert[2].v = v[i][2];
            TestBounds(shape, t);
            t.data.vert[0].n = n[i][0];
            t.data.vert[1].n = n[i][1];
            t.data.vert[2].n = n[i][2];
        }
        shape.sphere.center = shape.bb.pMin.Add(shape.bb.pMin, shape.bb.pMax).Scaled(0.5).AsPoint(Math.max(shape.bb.pMin.Length(), shape.bb.pMax.Length()));
        shape.sphere.radius = shape.sphere.center.r;
        VectorEquality(shape.list);

    }

    static void NormalScale(TessaShape shape, Vector normRadius,Vector normDeg, Vector deg, Vector radialEffect,boolean derived,boolean postNormal) {
    Node<Tessa> t = shape.list.Start();
        shape.bb = new BoundingBox();
        //Vector center=new Vector();
        deg=deg.Scaled(0.5);
        normDeg.Scaled(normRadius);
        Vector v[][] = new Vector[shape.list.Length()][3];
        int i = 0;
        for (; t.data != null; i++, t = t.next) {
            v[i][0] = t.data.vert[0].v.AsVector();
            v[i][1] = t.data.vert[1].v.AsVector();
            v[i][2] = t.data.vert[2].v.AsVector();
            v[i][0] = v[i][0].GetNormal().Scaled(normRadius).Added(t.data.vert[0].v).Scaled(deg);
            v[i][1] = v[i][1].GetNormal().Scaled(normRadius).Added(t.data.vert[1].v).Scaled(deg);
            v[i][2] = v[i][2].GetNormal().Scaled(normRadius).Added(t.data.vert[2].v).Scaled(deg);
            if(derived){
            v[i][0] = v[i][0].Added(radialEffect.Scaled(v[i][0].Length()));
            v[i][1] = v[i][1].Added(radialEffect.Scaled(v[i][1].Length()));
            v[i][2] = v[i][2].Added(radialEffect.Scaled(v[i][2].Length()));
            }else{
            v[i][0] = v[i][0].Added(radialEffect.Scaled(t.data.vert[0].v.Length()));
            v[i][1] = v[i][1].Added(radialEffect.Scaled(t.data.vert[1].v.Length()));
            v[i][2] = v[i][2].Added(radialEffect.Scaled(t.data.vert[2].v.Length()));
            }
            if(postNormal){
            
            v[i][0] = v[i][0].GetNormal().Scaled(normRadius).Added(v[i][0]).Scaled(deg);
            v[i][1] = v[i][1].GetNormal().Scaled(normRadius).Added(v[i][1]).Scaled(deg);
            v[i][2] = v[i][2].GetNormal().Scaled(normRadius).Added(v[i][2]).Scaled(deg);
            }
            //
            //
            //
        }
        i = 0;
        t = shape.list.Start();
        Vector recenter=new Vector();
        Vector min=new Vector(Linear.MAX_MAP,Linear.MAX_MAP,Linear.MAX_MAP);
        Vector max=new Vector(-Linear.MAX_MAP,-Linear.MAX_MAP,-Linear.MAX_MAP);
        for (; t.data != null; i++, t = t.next) {
           double mx1=Linear.Max3(v[i][0].x, v[i][1].x, v[i][2].x);
           double my1=Linear.Max3(v[i][0].y, v[i][1].y, v[i][2].y);
           double mz1=Linear.Max3(v[i][0].z, v[i][1].z, v[i][2].z);
           double mx2=Linear.Min3(v[i][0].x, v[i][1].x, v[i][2].x);
           double my2=Linear.Min3(v[i][0].y, v[i][1].y, v[i][2].y);
           double mz2=Linear.Min3(v[i][0].z, v[i][1].z, v[i][2].z);
           max.x=Math.max(max.x,mx1);
           max.y=Math.max(max.y,my1);
           max.z=Math.max(max.z,mz1);
           min.x=Math.min(min.x, mx2);
           min.y=Math.min(min.y, my2);
           min.z=Math.min(min.z, mz2);
           
        }
        recenter.x=(max.x+min.x)*0.5;
        recenter.y=(max.y+min.y)*0.5;
        recenter.z=(max.z+min.z)*0.5;
        i = 0;
        t = shape.list.Start();
        for (; t.data != null; i++, t = t.next) {
            t.data.vert[0].v = v[i][0].Subtract(recenter);
            t.data.vert[1].v = v[i][1].Subtract(recenter);
            t.data.vert[2].v = v[i][2].Subtract(recenter);
            TestBounds(shape, t);
        }
        shape.sphere.center = shape.bb.pMin.Add(shape.bb.pMin, shape.bb.pMax).Scaled(0.5).AsPoint(Math.max(shape.bb.pMin.Length(), shape.bb.pMax.Length()));
        shape.sphere.radius = shape.sphere.center.r;
        VectorEquality(shape.list);
    }
    static void Equalify(TessaShape shape, double dis) {

        Node<Tessa> t = shape.list.Start();
        Node<Tessa> s = shape.list.Start();
        int k = 0;
        dis *= dis;
        RingList<Tessa> remove=new RingList();
        double d[]=new double [9];
        boolean greater[]=new boolean [9];
        for (; t.data != null; t = t.next) {
            //j=0;

            s = t.next;
            for (; s.data != null; s = s.next) {
                if (s.data != t.data) {
                    int n=0;
                    int c=0;
                    for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                            if (t.data.vert[a].v != s.data.vert[b].v) {
                                
                                d[n] = t.data.vert[a].v.DistanceSqrd(s.data.vert[b].v);
                                if(d[n]<Linear.Epsilon){
                                t.data.vert[a].v = s.data.vert[b].v;
                                }else
                                if (d[n] < dis) {
                                    c++;
                                    greater[n]=true;
                                }
                            }
                            n++;
                        }
                    }
                    n=0;
                    double lowest=Linear.MAX_MAP;
                    int m=0;
                    int an=0;
                    int bn=0;
                    int cn=0;
                    while(c>0){
                    n=0;
                    for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                           if(greater[n]){
                           if(d[n]<lowest){
                           lowest=d[n];m=n;
                           an=a;bn=b;
                           }
                           } 
                           n++;
                        }
                    }
                    if( t.data.vert[an].v != s.data.vert[bn].v){
                       t.data.vert[an].v = s.data.vert[bn].v;
                       k++;
                       cn++;
                       if(cn==2){
                           d=new double [9];
                           greater=new boolean [9];
                           break;}
                    }
                    c--;
                    d[m]=0;
                    greater[m]=false;
                    lowest=Linear.MAX_MAP;
                    m=0;
                    an=0;
                    bn=0;
                           
                    }
                }
            }
        }
        System.out.println("Equality Result " + k + " of " + (shape.list.Length()*3)+" leaving "+((shape.list.Length()*3)-k));
        /*Node<Tessa> r = remove.Start();
        for(;r.data!=null;r=r.next){
           shape.list.Remove(r.data);
        }*/

    }

    static void Transform(TessaShape shape, Quarternion q) {
        Node<Tessa> t = shape.list.Start();
        shape.bb = new BoundingBox();
        Vector v[][] = new Vector[shape.list.Length()][3];
        Normal n[][] = new Normal[shape.list.Length()][3];
        int i = 0;
        for (; t.data != null; i++, t = t.next) {
            v[i][0] = q.Rotate(t.data.vert[0].v);
            v[i][1] = q.Rotate(t.data.vert[1].v);
            v[i][2] = q.Rotate(t.data.vert[2].v);

            n[i][0] = q.Rotate(t.data.vert[0].n).Unity().AsNormal();
            n[i][1] = q.Rotate(t.data.vert[1].n).Unity().AsNormal();
            n[i][2] = q.Rotate(t.data.vert[2].n).Unity().AsNormal();
        }
        //
        i = 0;
        t = shape.list.Start();
        for (; t.data != null; i++, t = t.next) {
            t.data.vert[0].v = v[i][0];
            t.data.vert[1].v = v[i][1];
            t.data.vert[2].v = v[i][2];
            TestBounds(shape, t);
            t.data.vert[0].n = n[i][0];
            t.data.vert[1].n = n[i][1];
            t.data.vert[2].n = n[i][2];
        }
        shape.sphere.center = shape.bb.pMin.Add(shape.bb.pMin, shape.bb.pMax).Scaled(0.5).AsPoint(Math.max(shape.bb.pMin.Length(), shape.bb.pMax.Length()));
        shape.sphere.radius = shape.sphere.center.r;
        VectorEquality(shape.list);

    }

    static void Explode(TessaShape shape, double s, boolean bounded) {
        Node<Tessa> t = shape.list.Start();
        //Vector center=new Vector();
        //Vector v[][] = new Vector[shape.list.Length()][3];
        //Normal n[][] = new Normal[shape.list.Length()][3];
        double s2 = s * s;
        for (; t.data != null; t = t.next) {
            //
            //t.data.vert[0].v = t.data.vert[0].v.AsVector().Scaled(s);
            //t.data.vert[1].v = t.data.vert[1].v.AsVector().Scaled(s);
            //t.data.vert[2].v = t.data.vert[2].v.AsVector().Scaled(s);
            Vector d = t.data.vert[0].v.Add(t.data.vert[0].v, t.data.vert[1].v, t.data.vert[2].v).Normal().Scaled(s2).AsVector();
            d.y -= s + Math.abs(d.y) * s;
            d.x += (d.x) * s;
            d.z += (d.z) * s;

            t.data.vert[0].v = t.data.vert[0].v.Added(d);
            t.data.vert[1].v = t.data.vert[1].v.Added(d);
            t.data.vert[2].v = t.data.vert[2].v.Added(d);
            //

        }
        if (bounded) {

            shape.bb = new BoundingBox();
            t = shape.list.Start();
            for (; t.data != null; t = t.next) {
                TestBounds(shape, t);
            }
            shape.sphere.center = shape.bb.pMin.Add(shape.bb.pMin, shape.bb.pMax).Scaled(0.5).AsPoint(Math.max(shape.bb.pMin.Length(), shape.bb.pMax.Length()));
            shape.sphere.radius = shape.sphere.center.r;
        }

    }

    static void Equality(RingList<Tessa> list) {
        //merge duplicate verts 
        int eq = 0;
        Node<Tessa> n1 = list.Start();
        for (; n1.data != null; n1 = n1.next) {

            Node<Tessa> n2 = list.Start();
            Tessa test = n1.data;
            for (; n2.data != null; n2 = n2.next) {
                Tessa against = n2.data;
                if (test != against) {
                    eq += against.Equity(test);
                }
                //triangle[j].Equality(triangle[i], 7);
            }

        }
        System.out.println("Equity Eq " + eq + " of Size " + list.Length());

    }

    static void VectorEquality(RingList<Tessa> list) {
        //merge duplicate verts 
        int eq = 0;
        Node<Tessa> n1 = list.Start();
        for (; n1.data != null; n1 = n1.next) {

            Node<Tessa> n2 = list.Start();
            Tessa test = n1.data;
            for (; n2.data != null; n2 = n2.next) {
                Tessa against = n2.data;
                if (test != against) {
                    eq += against.VectorEquity(test);
                }
                //triangle[j].Equality(triangle[i], 7);
            }
        }
        System.out.println("Equity Eq " + eq + " of Size " + list.Length());

    }

    static void Adjacancy(RingList<Tessa> list) {

        double nc = Linear.MAX_MAP;
        Node<Tessa> tes = list.Start();
        int ja = 0;
        for (; tes.data != null; tes = tes.next) {
            Tessa test = tes.data;

            //int n[]=new int[3];
            Node<Tessa> tessa = list.Start();
            for (; tessa.data != null; tessa = tessa.next) {

                Tessa adjTest = tessa.data;
                //if(adjTest!=test){
                for (int v1 = 0; v1 < 3; v1++) {
                    for (int v2 = 0; v2 < 3; v2++) {
                        if (test.vert[v1] == adjTest.vert[v2]) {
                            if (test.vert[v1].adjacent > 0) {
                                int sofar = test.vert[v1].adjacent;
                                for (int v3 = 0; v3 < 3; v3++) {
                                    boolean ok = true;
                                    for (int na = 0; na < sofar; na++) {
                                        if (v3 == v2 || test.vert[v1].adj[na] == adjTest.vert[v3]) {
                                            ok = false;
                                        }
                                    }
                                    if (ok) {
                                        test.vert[v1].adj[test.vert[v1].adjacent] = adjTest.vert[v3];
                                        test.vert[v1].adjacent++;
                                        ja++;
                                    }
                                }
                            } else {
                                for (int v3 = 0; v3 < 3; v3++) {
                                    if (v3 != v2 && test.vert[v1].adj[test.vert[v1].adjacent] != adjTest.vert[v3]) {
                                        test.vert[v1].adj[test.vert[v1].adjacent] = adjTest.vert[v3];
                                        test.vert[v1].adjacent++;
                                        ja++;
                                    }

                                }
                            }
                        }
                    }
                }

                System.out.println("Adjacancy adj " + ja + " of Size " + list.size());
            }
        }
    }


}

class SphereVertex extends Sphere {

    public float depth;
    float radi;
    float angle;
    Vector offset;

}

class Lathe {

    public int width;
    int height;
    Vector start = new Vector();
    float depthStep[] = new float[512];
    SphereVertex sphereVertex[][] = new SphereVertex[512][256];

    void SetUpShape(int w, int h, Vector start, float radius, float rad, float depth, float depthStep, float scale) {

        if (w < 3) {
            w = 3;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        this.start = start;
        width = w;
        height = h;
        h = h + 2;
        float radi = rad;
        for (int j = 0; j < h; j++) {
            this.depthStep[j] = depthStep * scale;
            radi = rad;
            for (int i = 0; i < w; i++) {

                sphereVertex[i][j] = new SphereVertex();
                sphereVertex[i][j].radius = radius * scale;
                sphereVertex[i][j].radi = radi;
                sphereVertex[i][j].depth = depth * scale;
                radi += rad;
            }

        }
    }

    void SetUpShapeDisc(int w, int h, float radius, float depth, float depthStep, float scale, float oscillate, int corrigate) {
        if (w < 3) {
            w = 3;
        }
        if (h < 3) {
            h = 3;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        width = w;
        height = h;
        h += 2;
        float ry = (float) Linear.PI2 * radius / h;
        float rd = (float) Linear.PI2 / w;
        float r = 0;
        float ang = 0;
        float ra = (float) Math.PI / h;
        int dh = h >> 1;
        dh--;
        float offset = 0;
        //double n=3;
        for (int j = 0; j < h; j++) {
            ang += ra;
            this.depthStep[j] = depthStep * scale;
            offset += this.depthStep[j];
            float rad = 0;
            float d = depth;
            if (j == 0 || j == height) {
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    sphereVertex[i][j].radi = rad;
                    if (corrigate > 0) {
                        sphereVertex[i][j].depth = d * (float) Math.cos(rad * corrigate) * scale; //corigate
                    } else {
                        sphereVertex[i][j].depth = d * scale;
                    }
                    rad += rd;
                }
            } else {
                //d=depth;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = r * scale;
                    sphereVertex[i][j].radi = rad; //+ang;
                    if (corrigate > 0) {
                        sphereVertex[i][j].depth = d * (float) Math.cos(rad * corrigate) * scale; //corigate
                    } else {
                        sphereVertex[i][j].depth = d * scale;
                    }
                    rad += rd;
                }
            }
            if (oscillate != 0) {
                if (j < dh) {
                    r += ry * Math.sin(ang * oscillate);
                } else {
                    r -= ry * Math.sin(ang * oscillate);
                }
            } else if (j < dh) {
                r += ry;
            } else {
                r -= ry;
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapePoly(int w, int h, float radius, float depth, float depthStep, float scale, boolean teardrop, float oscillate) {
        if (w < 4) {
            w = 4;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        //double ht=h/depth;
        width = w + 1;
        height = h;
        h = height + 2;
        //double ry=PI2*radius/h;

        float rd = (float) Linear.PI2 / w;
        float r = radius;
        float ang = 0;
        float ra = (float) Math.PI / h;
        int dh = h >> 1;
        dh--;
        float ry = r / dh;
        w++;
        float offset = 0;
        for (int j = 0; j < h; j++) {
            ang += ra;
            float rad = 0;
            float d = depth;
            if (j == 0 || j == height) {
                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = r * scale;
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }

            if (oscillate != 0) {
                if (j < dh) {
                    r += ry * Math.sin(ang * oscillate);
                } else {
                    r -= ry * Math.sin(ang * oscillate);
                }
            } else {
                if (teardrop) {
                    if (j < dh) {
                        r += ry;
                    } else {
                        r -= ry;
                    }
                }
            }
            //if(j<dh){r+=ry;}else{r-=ry;}
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeCylinder(int w, int h, float radius, float depth, float depthStep, float point, float scale, boolean teardrop, float oscillate) {
        if (w < 4) {
            w = 4;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        //double ht=h/depth;
        width = w + 1;
        height = h;
        h = height + 2;
        //double ry=PI2*radius/h;

        float rd = (float) Linear.PI2 / w;
        float r = radius;
        float ang = 0;
        float ra = (float) Math.PI / h;

        int dh = h >> 1;
        //dh--;
        float ry = r / dh;
        w++;
        float offset = 0;
        for (int j = 0; j < h; j++) {
            ang += ra;
            float rad = 0;
            float d = depth;
            if (j == 0 || j == height) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = 0;
                    if (j == 0) {
                        offset += point; //shape.depthStep[j];
                        sphereVertex[i][j].depth = (depthStep - point) * scale;
                    }
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = r * scale;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            if (oscillate != 0) {
                if (j < dh) {
                    r += ry * Math.sin(ang * oscillate);
                } else {
                    r -= ry * Math.sin(ang * oscillate);
                }
            } else {
                if (teardrop) {
                    if (j < dh) {
                        r += ry;
                    } else {
                        r -= ry;
                    }
                }
            }
            //if(j<dh){r+=ry;}else{r-=ry;}
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeWedge(int w, int h, float radius, float depth, float depthStep, float point, float scale, int smoothness, int sharpness, boolean teardrop) {
        if (w < 3) {
            w = 3;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        //double ht=h/depth;
        width = w;
        height = h;
        h = height + 2;
        float rd = (float) Linear.PI2 / (w);
        //int s2=s1<<1;
        //if(divide){rd*=0.5;}

        if (sharpness < 1) {
            sharpness = 1;
        }
        if (smoothness < 1) {
            smoothness = 1;
        }
        float r = radius / 2.0f;
        int dh = h >> 1;
        float ry = r / dh;
        if (dh != sharpness) {
            ry = r / (dh - sharpness);
        }
        dh--;
        //w--;
        float offset = 0;
        float dr = r;
        for (int j = 0; j < h; j++) {
            float rad = 0;
            float d = depth;
            rad = rd;
            if (j == 0 || j == height) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = 0;
                    if (j == 0) {
                        offset = +point; //shape.depthStep[j];
                        sphereVertex[i][j].depth = -(point * scale);
                    }
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {

                    sphereVertex[i][j] = new SphereVertex();
							//double ab=fabs((double)(i<<1)-(w));
                    //if(i<w-1)

                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad));///2
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(sin(rad)+cos(rad));///2
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(sin(rad)*cos(rad));///4
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad))+dr*fabs(sin(rad));///4
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad))-dr*fabs(sin(rad));///2
                    //shape.sphereVertex[i][j]->radius=r+dr*cos(PI45+rad/4);//shell
                    //shape.sphereVertex[i][j]->radius=r+dr*cos(rad*4);//4 diamonds
                    //shape.sphereVertex[i][j]->radius=r+dr*cos(rad*4)/4;//4 pods
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad*4));//stars
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad)*cos(rad*2)*cos(rad*3)*cos(rad*4));//shuttle
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad)*cos(rad*2)*cos(rad*4)*cos(rad*8));//shuttle
                    //shape.sphereVertex[i][j]->radius=r+dr*fabs(cos(rad)*cos(rad*2));//wedge
                    float c = radius;
                    for (int k = 1; k < smoothness; k += sharpness) {
                        c *= Math.cos(rad * k);
                    }
                    sphereVertex[i][j].radius = (float) (r + dr * Math.abs(c)) * scale; //wedge

                    //double c=dr*PI;
                    //double a=c*(cos(rad));
                    //double b=c*(sin(rad));
                    //shape.sphereVertex[i][j]->radius=r+sqrt(a*a+b*b);///1
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            if (teardrop) {
                if (j < dh) {
                    dr += ry;
                } else {
                    dr -= ry;
                }
            } else {
                dr += ry;
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
        //shape.start=MakeVector(0,0,0);
    }

    void SetUpShapeCone(int w, int h, float radius, float depth, float depthStep, float oscillate, float scale) {

        if (w < 3) {
            w = 3;
        }
        if (h < 3) {
            h = 3;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        width = w;
        height = h;
        h += 2;
        float ry = (float) Math.PI * radius / h;
        float rd = (float) Linear.PI2 / w;
        float ang = 0;
        float ra = (float) Math.PI / h;
        float r = 0;
        int dh = h >> 1;
        dh += dh >> 1;
        dh--;
        float offset = 0;
        for (int j = 0; j < h; j++) {
            ang += ra;
            this.depthStep[j] = depthStep * scale;
            offset += this.depthStep[j];
            float rad = 0;
            float d = depth;
            if (j == 0 || j == height) {
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    rad += rd;
                }
            } else {
                //d=depth;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = r * scale;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            //r+=ry;
            if (oscillate != 0) {
                if (j < dh) {
                    r += ry * Math.sin(ang * oscillate);
                } else {
                    r -= ry * Math.sin(ang * oscillate);
                }
            } else if (j < dh) {
                r += ry;
            } else {
                r -= ry;
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeRocket(int w, int h, float radius, float depth, float depthStep, float point, float scale, int pods, int sharpness, int type, boolean teardrop, float twist) {
        if (w < 4) {
            w = 4;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        int t = type % 2;
        int add = 0;
        if (type > 3) {
            add = type - 3;
            type = type % 4;
        }
        //double ht=h/depth;
        width = w;
        height = h;
        h = height + 2;
        float rd = (float) Linear.PI2 / (w);
        float ang = 0;
        float ra = (float) Math.PI / h;
        //int s2=s1<<1;
        //if(divide){rd*=0.5;}
        pods += add;
        sharpness += add;
        float r = radius / 2.0f;
        int dh = h >> 1;
        float ry = r / dh;
        if (dh != sharpness) {
            ry = r / (dh - sharpness);
        }
        dh--;
        //w--;
        float offset = 0;
        float dr = r;
        for (int j = 0; j < h; j++) {
            float rad = 0;
            float d = depth;
            if (twist != 0) {
                ang += ra * twist;
            }
            rad = rd;
            if (j == 0 || j == height) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = 0;
                    if (j == 0) {
                        offset = +point; //shape.depthStep[j];
                        sphereVertex[i][j].depth = -(point * scale);
                    }
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {

                    sphereVertex[i][j] = new SphereVertex();

                    double c = 1;
                    double s = 1;

                    switch (type) {
                        case 0: {
                            c = Math.sin((rad + ang) * (sharpness)) + Math.cos((rad + ang) * (pods));
                            break;
                        }
                        case 1: {
                            c = Math.sin((rad + ang) * (sharpness)) * Math.cos((rad + ang) * (pods));

                            break;
                        }
                        case 2: {
                            c = Math.cos((rad + ang) * (pods));
                            s = Math.sin((rad + ang) * (sharpness));

                            break;
                        }
                        case 3: {

                            c = Math.cos((rad + ang) * (pods));
                            s = Math.sin((rad + ang) * (sharpness));

                            break;
                        }
                        default: {
                            c = Math.sin((rad + ang) * (sharpness)) + Math.cos((rad + ang) * pods);

                            break;
                        }
                    }
                    if (type < 2) {
                        sphereVertex[i][j].radius = (float) (r + (ry * add) + dr * Math.abs(c));
                    } else {
                        if (t > 0) {
                            sphereVertex[i][j].radius = (float) (r + (ry * add) + dr * Math.abs(c) - dr * Math.abs(s)) * dr;
                        } else {
                            sphereVertex[i][j].radius = (float) (r + (ry * add) + dr * Math.abs(c) + dr * Math.abs(s));
                        }
                    }
                    sphereVertex[i][j].radius *= scale;
							//double c=dr*PI;
                    //double a=c*(cos(rad));
                    //double b=c*(sin(rad));

                    //shape.sphereVertex[i][j]->radius=r+sqrt(a*a+b*b);///1
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            if (teardrop) {
                if (j < dh) {
                    dr += ry;
                } else {
                    dr -= ry;
                }
            } else {
                dr += ry;
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeFracRocket(Molecula m, double seed, int w, int h, float radius, float depth, float depthStep, float point, float scale, int pods, int sharpness, int type, boolean teardrop, float twist) {
        if (w < 4) {
            w = 4;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        int t = type % 2;
        int add = 0;
        if (type > 3) {
            add = type - 3;
            type = type % 4;
        }
        //double ht=h/depth;
        width = w;
        height = h;
        h = height + 2;
        float rd = (float) Linear.PI2 / (w);
        float ang = 0;
        float ra = (float) Math.PI / h;
        //int s2=s1<<1;
        //if(divide){rd*=0.5;}
        pods += add;
        sharpness += add;
        float r = radius / 2.0f;
        int dh = h >> 1;
        float ry = r / dh;
        if (dh != sharpness) {
            ry = r / (dh - sharpness);
        }
        dh--;
        //w--;
        float offset = 0;
        float dr = r;
        Molecula fractal[] = new Molecula[2];
        m.SingleCalc();
        double s1 = 0.707 * (m.cosine + seed);
        double s2 = 0.707 * (m.sine + seed);
        m.SingleCalc();
        fractal[0] = new Molecula(s1, 10000, m.sine);
        fractal[1] = new Molecula(s2, 10000, m.cosine);
        fractal[0].SingleCalc(w);
        fractal[1].SingleCalc(h);
        for (int j = 0; j < h; j++) {
            float rad = 0;
            float d = depth;
            if (twist != 0) {
                ang += ra * twist;
            }
            rad = rd;
            fractal[0].SingleCalc(w);
            fractal[1].SingleCalc(h);
            if (j == 0 || j == height) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = 0;
                    if (j == 0) {
                        offset = +point; //shape.depthStep[j];
                        sphereVertex[i][j].depth = -(point * scale);
                    }
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {

                    sphereVertex[i][j] = new SphereVertex();

                    float c = 1;
                    float s = 1;

                    switch (type) {
                        case 0: {
                            //c=sin((rad+ang)*(sharpness))+cos((rad+ang)*(pods));
                            c = (float) (fractal[0].sine * Math.sin((rad + ang) * (sharpness)) + fractal[0].cosine * Math.cos((rad + ang) * (pods)));
                            break;
                        }
                        case 1: {
                            //c=sin((rad+ang)*(sharpness))*cos((rad+ang)*(pods));
                            c = (float) (fractal[0].sine * Math.sin((rad + ang) * (sharpness)) * fractal[0].cosine * Math.cos((rad + ang) * (pods)));

                            break;
                        }
                        case 2: {
                            c = (float) (fractal[0].cosine * Math.cos((rad + ang) * (pods)));
                            s = (float) (fractal[0].sine * Math.sin((rad + ang) * (sharpness)));

                            break;
                        }
                        case 3: {

                            c = (float) (fractal[0].cosine * Math.cos((rad + ang) * pods));
                            s = (float) (fractal[0].sine * Math.sin((rad + ang) * sharpness));

                            break;
                        }
                        default: {
                            c = (float) (fractal[0].sine * Math.sin((rad + ang) * (sharpness)) + fractal[0].cosine * Math.cos((rad + ang) * pods));

                            break;
                        }
                    }
                    if (type < 2) {
                        sphereVertex[i][j].radius = (float) (r + (ry * add) + dr * Math.abs(c));
                    } else {
                        if (t > 0) {
                            sphereVertex[i][j].radius = (float) ((r + (ry * add) + dr * Math.abs(c) - dr * Math.abs(s)) * dr);
                        } else {
                            sphereVertex[i][j].radius = (float) (r + (ry * add) + dr * Math.abs(c) + dr * Math.abs(s));
                        }
                    }
                    sphereVertex[i][j].radius *= scale;
							//double c=dr*PI;
                    //double a=c*(cos(rad));
                    //double b=c*(sin(rad));

                    //shape.sphereVertex[i][j]->radius=r+sqrt(a*a+b*b);///1
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = (float) (d * Math.abs(fractal[1].cosine) * scale);
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            if (teardrop) {
                if (j < dh) {
                    dr += (float) (ry * Math.abs(fractal[1].sine));
                } else {
                    dr -= (float) (ry * Math.abs(fractal[1].sine));
                }
            } else {
                dr += ry;
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    /// engines
    void SetUpShapeEngine(int w, int h, float radius, float depth, float depthStep, float scale) {

        if (w < 4) {
            w = 4;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        float ht = h / depth;
        width = w + 1;
        height = h;
        h = height + 2;
        //double ry=PI2*radius/h;

        float rd = (float) Linear.PI2 / w;
        float r = radius;
        //int dh=h>>1;
        //dh--;
        w++;
        float offset = 0;
        for (int j = 0; j < h; j++) {
            float rad = 0;
            float d = depth;
            if (j == 0 || j == height - 1) {
                this.depthStep[j] = 0;
                offset += this.depthStep[j] * scale;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = 0;
                    rad += rd;
                }
            } else {

                this.depthStep[j] = depthStep * ht * scale;
                offset += this.depthStep[j];
                //d=depth;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = r * scale;
                    //shape.sphereVertex[i][j]->radius=r*cos(rad*(w/n));//corigate
                    sphereVertex[i][j].radi = rad;
                    sphereVertex[i][j].depth = d * scale;
                    //shape.sphereVertex[i][j]->depth=d*cos(rad*(w/n));//corigate
                    rad += rd;
                }
            }
            //if(j<dh){r+=ry;}else{r-=ry;}
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeTorusEngine(int w, int h, float radius, float depth, float depthStep, float delta, float scale, boolean inverse, boolean addRadius, boolean addDepth, boolean sineDepth, boolean curveEdge) {

        if (w < 3) {
            w = 3;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
        //if(h>254){h=254;}
        //double ht=h/depth;
        width = w + 1;
        height = h;
        h = height + 2;
        float rd = (float) Linear.PI2 / w;
        float dr = radius;

        w++;
        float offset = depthStep;
        float c = (float) Math.PI / h;
        depthStep *= scale;
        //for(int k=0;k<h;k++){
        for (int j = 0; j < h; j++) {
            this.depthStep[j] = depthStep;

            //ang+=ra;
            float rad = 0;
            float Aq = (float) ((((delta - 1) * Math.PI * j) * Math.tan(((j * j * j) / (delta * Math.PI)))) / w * (delta - 2));
            float Ap = (float) ((delta * Math.PI * j) / w);
            float r = (float) (delta + dr * Math.cos(Aq));
            if (curveEdge) {
                r *= Math.cos(c * j);
            }
            if (addRadius) {
                r += dr * Math.cos(Ap);
            } else {
                r *= dr * Math.cos(Ap);
            }

            float d = 0;
            if (sineDepth) {
                d = r * (float) Math.sin(Ap);
            } else {
                d = r * (float) Math.cos(Ap);
            }
            if (addDepth) {
                d += dr;
            }
            if (j == 0) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    sphereVertex[i][j].radi = rad;

                    rad += rd;
                }
            } else {
                //d=depth;
                if (inverse) {
                    for (int i = 0; i < w; i++) {
                        sphereVertex[i][j] = new SphereVertex();
                        sphereVertex[i][j].radius = d * scale;
                        sphereVertex[i][j].radi = rad;
                        sphereVertex[i][j].depth = r * scale;
                        rad += rd;
                    }

                } else {
                    for (int i = 0; i < w; i++) {
                        sphereVertex[i][j] = new SphereVertex();
                        sphereVertex[i][j].radius = r * scale;
                        sphereVertex[i][j].radi = rad;
                        sphereVertex[i][j].depth = d * scale;
                        rad += rd;
                    }
                }
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    void SetUpShapeConeEngine(int w, int h, float radius, float depth, float depthStep, float delta, float scale, boolean inverse, boolean addRadius, boolean addDepth, boolean sineDepth, boolean curveEdge) {
        //printf("w:%d:h:%d:", w, h);
        if (w < 3) {
            w = 3;
        }
        if (h < 2) {
            h = 2;
        }
        if (w >= 512) {
            w = 511;
        }
        if (h >= 255) {
            h = 254;
        }
				//printf("w:%d:h:%d:\n", w, h);

        //if(h>254){h=254;}
        //double ht=h/depth;
        width = w + 1;
        height = h;
        h = height + 2;
        float rd = (float) Linear.PI2 / w;
        float dr = radius;

        w++;
        float offset = depthStep;
        //double c=PI/h;
        depthStep *= scale;
        //for(int k=0;k<h;k++){
        for (int j = 0; j < h; j++) {
            this.depthStep[j] = depthStep;

            //ang+=ra;
            float rad = 0;
            float Aq = (float) (((delta - 1) * (float) (Math.PI) * j) * Math.tan((j * j * j) / (delta * (float) (Math.PI)))) / w * (delta - 2);
            float Ap = (float) (delta * (float) (Math.PI) * j) / w;
            float r = (float) (delta + dr * Math.cos(Aq));
            if (curveEdge) {
                r *= Math.cos(Ap);
            }
            if (addRadius) {
                r += dr;
            }
            float d = 0;
            if (sineDepth) {
                d = r * (float) Math.sin(Ap);
            } else {
                d = r * (float) Math.cos(Ap);
            }
            if (addDepth) {
                d += dr;
            }
            if (j == 0) {
                this.depthStep[j] = 0; //depthStep;
                for (int i = 0; i < w; i++) {
                    sphereVertex[i][j] = new SphereVertex();
                    sphereVertex[i][j].radius = 0;
                    sphereVertex[i][j].radi = rad;
                    rad += rd;
                }
            } else {
                //d=depth;
                if (inverse) {
                    for (int i = 0; i < w; i++) {
                        sphereVertex[i][j] = new SphereVertex();
                        sphereVertex[i][j].radius = d * scale;
                        sphereVertex[i][j].radi = rad;
                        sphereVertex[i][j].depth = r * scale;
                        rad += rd;
                    }

                } else {
                    for (int i = 0; i < w; i++) {
                        sphereVertex[i][j] = new SphereVertex();
                        sphereVertex[i][j].radius = r * scale;
                        sphereVertex[i][j].radi = rad;
                        sphereVertex[i][j].depth = d * scale;
                        rad += rd;
                    }
                }
            }
        }
        start.x = 0;
        start.y = offset * 0.5f;
        start.z = 0;
    }

    static RingList<Tessa> SetShape(float scale, boolean inside, boolean enclose, int noEngine, Lathe shape, Pigment c) {

        //double p=pow(2,lod);
        int w = shape.width;
        int h = shape.height;
        float dw = (float) w;
        float dh = (float) h;
        //
        int wd = w + 1;
        //int hd=h+1;
        //double r=scale;
        //double dxz=PI2/(double)(w);
        //double dy=PI/(double)(h-1);
        float dy = 0;
        float ty = 1.0f / (dh + 1 + noEngine);
        float txz = 1.0f / dw;
        //sphere
        int index = 0;
        //int verts=0;
        //int vertexCount=(world.geocentric.geoWidth[lod]*world.geocentric.geoHeight[lod])-world.geocentric.geoHeight[lod]+2;//correct
        //int indexCount=(vertexCount*6)-world.geocentric.geoHeight[lod]*6-6;//+world.geocentric.geoWidth[lod]*3;// unknown -geoWidth*6+12
        Vector offset = shape.start.Scaled(scale);
        //
        //int vertexCount = ((w + 1) * h) - (w - 1);
        //int indexCount = (vertexCount * 6) - (w * 2);
        //printf("astro shape x:%d\ty:%d\n",w,h);
        //printf("start v:%d\ti:%d\n",vertexCount,indexCount);
        RingList<Tessa> atom = new RingList();///(vertexCount, indexCount);
        //double sy=sin(PI90);
        //
        //set=(set+ToString(":Est Verts:"));
        //set=(set+IntToString(vertexCount));
        //set=(set+ToString(":Est Index:"));
        //set=(set+IntToString(indexCount));
        int yy = 0;
        boolean pole = true;
        dy = 0; //shape.start.v[Y];
        Vector pc = new Vector((Random.Next(255) + c.Red()) * 0.002, (Random.Next(255) + c.Green()) * 0.002, (Random.Next(255) + c.Blue()) * 0.002);

        RingList<TessaVertex> lastRotation = null;
        RingList<TessaVertex> rotation = new RingList();
        TessaVertex point = new TessaVertex();
        point.v.x = (float) (offset.x);
        point.v.y = (float) (offset.y);
        point.v.z = (float) (offset.z);
        point.p = pc;
        point.h = offset.Length();
        if (inside) {
            point.n = new Normal(0, -1, 0);
        } else {
            point.n = new Normal(0, 1, 0);
        }

        for (int y = 0; y < h; y++) {

            //int start=index;
            float angxz = 0;
            int xx = 0;
            //if(yy==h){yy=0;}    
            //printf("%f\n",sy);
            for (int xz = 0; xz < w; xz++) {
                TessaVertex vert = new TessaVertex();

                float r = (float) shape.sphereVertex[xx][yy].radius * scale;
                vert.v.x = (float) (offset.x + (r * Math.cos(angxz)));
                vert.v.y = (float) (offset.y + (dy + shape.sphereVertex[xx][yy].depth) * -scale);
                vert.v.z = (float) (offset.z + (r * Math.sin(angxz)));
                pc = new Vector((Random.Next(255) + c.Red()) * 0.002, (Random.Next(255) + c.Green()) * 0.002, (Random.Next(255) + c.Blue()) * 0.002);
                vert.p = pc;
                vert.h = r;
                //vert.t[0] = (float)(u);
                //vert.t[1] = (float)(v);
                //world.graphics.graph[x][z]=a(GEO) ;//Geograph(this,vert.x,vert.y,vert.z,tri);

                Vector v = new Vector(vert.v);
                vert.n = v.Unity().AsNormal();
                if (inside) {
                    vert.n.x = (float) (-vert.n.x);
                    vert.n.y = (float) (-vert.n.y);
                    vert.n.z = (float) (-vert.n.z);
                }
                rotation.Append(vert);

                //u += txz;//text
                angxz = shape.sphereVertex[xx][yy].radi;
                xx++;
            }//xz
            xx--;
            if (pole) {
                Node<TessaVertex> n = rotation.Start();
                for (; n.data != null && n.next.data != null; n = n.next) {
                    Tessa t = new Tessa(point, n.data, n.next.data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t);
                }
                if (n.data != null) {
                    Tessa t = new Tessa(point, n.data, rotation.Start().data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t);
                }
                pole = false;
            } else {
                Node<TessaVertex> n1 = lastRotation.Start();
                Node<TessaVertex> n2 = rotation.Start();
                for (; n1.data != null && n1.next.data != null && n2.data != null && n2.next.data != null; n1 = n1.next, n2 = n2.next) {
                    Tessa t1 = new Tessa(n1.data, n2.data, n1.next.data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t1);
                    Tessa t2 = new Tessa(n1.next.data, n2.data, n2.next.data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t2);
                }
                if (n1.data != null && n2.data != null) {
                    Tessa t1 = new Tessa(n1.data, n2.data, lastRotation.Start().data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t1);
                }
                if (n2.data != null) {
                    Tessa t2 = new Tessa(lastRotation.Start().data, n2.data, rotation.Start().data, (float) shape.sphereVertex[xx][yy].radius * scale);
                    atom.Append(t2);
                }
            }
            lastRotation = rotation;
            rotation = new RingList();
            //angy+=dy;
            dy += shape.depthStep[yy];
            //v += ty;//text
            yy++;

        }//y
        if (enclose) {
            point = new TessaVertex();
            point.v.x = (float) (offset.x);
            point.v.y = (float) (offset.y + dy);
            point.v.z = (float) (offset.z);
            point.p = pc;
            point.h = offset.Length();
            if (inside) {
                point.n = new Normal(0, 1, 0);
            } else {
                point.n = new Normal(0, -1, 0);
            }
            Node<TessaVertex> n = lastRotation.Start();
            for (; n.data != null && n.next.data != null; n = n.next) {
                Tessa t = new Tessa(point, n.data, n.next.data, scale);
                atom.Append(t);
            }
            if (n.data != null) {
                Tessa t = new Tessa(point, n.data, lastRotation.Start().data, scale);
                atom.Append(t);
            }
        }
				//calculate normals
        //set=(set+ToString(":Final Verts:"));
        //set=(set+IntToString(world.geocylinder.currentVerts));
        //set=(set+ToString(":Final Index:"));
        //set=(set+IntToString(world.geocylinder.currentIndecies));
        //set=(set+ToString(":Index Error:"));

        //printf("final v:%d\ti:%d\n",geocylinder->currentVerts,geocylinder->currentIndecies);
        //set=(set+IntToString(index-geocylinder->currentVerts));
        return atom;
    }

    /*			inline static Atomic* StepShape(float scale, bool inside, bool enclose, bool noEngine, Lathe * shape) {

     //double p=pow(2,lod);
     int w = shape->width;
     int h = shape->height + 1;
     float dw = (float)w;
     float dh = (float)h;
     //
     int wd = w + 1;
     //int hd=h+1;
     //double r=scale;
     //double dxz=PI2/(double)(w);
     //double dy=PI/(double)(h-1);
     float dy = 0;
     float ty = 1.0f / (dh + 1 + noEngine);
     float txz = 1.0f / dw;
     //sphere
     int index = 0;
     //int verts=0;
     //int vertexCount=(world.geocentric.geoWidth[lod]*world.geocentric.geoHeight[lod])-world.geocentric.geoHeight[lod]+2;//correct
     //int indexCount=(vertexCount*6)-world.geocentric.geoHeight[lod]*6-6;//+world.geocentric.geoWidth[lod]*3;// unknown -geoWidth*6+12
     Linear::Vector offset = shape->start* scale;
     //
     int vertexCount = ((w + 1) * h) - (w - 1); //
     int indexCount = (vertexCount * 6) - (w * 2); //
     //printf("step shape x:%d\ty:%d\n",w,h);
     //printf("start v:%d\ti:%d\n",vertexCount,indexCount);
     Atomic* atom=new Atomic(vertexCount, indexCount);
     //double sy=sin(PI90);
     //
     //set=(set+ToString(":Est Verts:"));
     //set=(set+IntToString(vertexCount));
     //set=(set+ToString(":Est Index:"));
     //set=(set+IntToString(indexCount));
     float v = 0;
     int yy = 0;
     bool pole = true;
     dy = 0; //shape.start.v[Y];
     for (int y = 0; y <= h; y++) {
     int tri = (y <= 1) ? -1 : (y == h) ? 1 : 0;
     if (pole && (tri == -1)) {
     index--;
     }
     pole = (tri != 0);
     // int start=index;
     float angxz = 0;
     float u = 0;
     int xx = 0;
     //if(yy==h){yy=0;}

     //printf("%f\n",sy);
     for (int xz = 0; xz <= w; xz++) {
     CustomVertex vert;
     if (xx == w) {
     xx = 0;
     if (!enclose) {
     break;
     }
     }
     double r = shape->sphereVertex[xx][yy].radius * scale;
     vert.v[0] = (float)(offset.x() + (r * cosf(angxz)));
     vert.v[1] = (float)(offset.y() + (dy + shape->sphereVertex[xx][yy].depth) * -scale);
     vert.v[2] = (float)(offset.z() + (r * sinf(angxz)));
     vert.t[0] = (float)(u);
     vert.t[1] = (float)(v);
     //world.graphics.graph[x][z]=a(GEO) ;//Geograph(this,vert.x,vert.y,vert.z,tri);
     index++;
     if (index > 0) { //&& index!=verts
     //verts=index;
     if (pole) {
     if (tri < 0) {
     int i[3] = { index - 1, index, 0 };
     atom->Index(i);

     }
     else {
     int i[3] = { index - 1, index, vertexCount - 1 };
     atom->Index(i);
     }
     }
     else {
     int i[3] = { index - wd, index - 1, index };
     atom->Index(i);
     int j[3] = { index - wd + 1, index - wd, index };
     atom->Index(j);

     }
     Linear::Vector v = Linear::Vector(vert.v[0], vert.v[1], vert.v[2]);
     v = v.Normal();
     if (inside) {
     vert.n[0] = (float)(-v.x());
     vert.n[1] = (float)(-v.y());
     vert.n[2] = (float)(-v.z());
     }
     else {
     vert.n[0] = (float)(v.x());
     vert.n[1] = (float)(v.y());
     vert.n[2] = (float)(v.z());
     }
     }
     atom->Vertex(vert);
     if (y == 0 || y == h) {
     //if(!y%2)index--;
     break;
     }
     u += txz;
     angxz = shape->sphereVertex[xx][yy].radi;
     xx++;
     }//xz
     //angy+=dy;
     dy += shape->depthStep[yy];
     v += ty;
     yy++;

     }
     //calculate normals
     //set=(set+ToString(":Final Verts:"));
     //set=(set+IntToString(world.geocylinder.currentVerts));
     //set=(set+ToString(":Final Index:"));
     //set=(set+IntToString(world.geocylinder.currentIndecies));
     //set=(set+ToString(":Index Error:"));

     //printf("final v:%d\ti:%d\n",geocylinder->currentVerts,geocylinder->currentIndecies);
     //set=(set+IntToString(index-geocylinder->currentVerts));
     return atom;
     }
     inline static Atomic* SumShape(float scale, bool inside, bool enclose, bool noEngine, Lathe * shape) {

     //double p=pow(2,lod);
     int w = shape->width;
     int h = shape->height + 1;
     float dw = (float)w;
     float dh = (float)h;
     //
     int wd = w + 1;
     //int hd=h+1;
     //double r=scale;
     //double dxz=PI2/(double)(w);
     //double dy=PI/(double)(h-1);
     float dy = 0;
     float ty = 1.0f / (dh + 1 + noEngine);
     float txz = 1.0f / dw;
     //sphere
     int index = 0;
     //int verts=0;
     //int vertexCount=(world.geocentric.geoWidth[lod]*world.geocentric.geoHeight[lod])-world.geocentric.geoHeight[lod]+2;//correct
     //int indexCount=(vertexCount*6)-world.geocentric.geoHeight[lod]*6-6;//+world.geocentric.geoWidth[lod]*3;// unknown -geoWidth*6+12
     Linear::Vector offset = shape->start* scale;
     offset[1] *= 0.5f;
     //
     int vertexCount = ((w + 1) * h) - (w - 1);
     int indexCount = (vertexCount * 6) - (w * 2);
     //printf("sum shape x:%d\ty:%d\n",w,h);
     //printf("start v:%d\ti:%d\n",vertexCount,indexCount);
     Atomic* atom=new Atomic(vertexCount, indexCount);
     //double sy=sin(PI90);
     //
     //set=(set+ToString(":Est Verts:"));
     //set=(set+IntToString(vertexCount));
     //set=(set+ToString(":Est Index:"));
     //set=(set+IntToString(indexCount));
     float v = 0;
     int yy = 0;
     bool pole = true;
     dy = 0; //shape.start.v[Y];
     for (int y = 0; y <= h; y++) {
     int tri = (y <= 1) ? -1 : (y == h) ? 1 : 0;
     if (pole && (tri == -1)) {
     index--;
     }
     pole = (tri != 0);
     //int start=index;
     float angxz = 0;
     float u = 0;
     int xx = 0;
     //if(yy==h){yy=0;}

     //printf("%f\n",sy);
     float s = 0;
     for (int xz = 0; xz <= w; xz++) {
     CustomVertex vert;
     if (xx == w) {
     xx = 0;
     if (!enclose) {
     break;
     }
     }
     double r = shape->sphereVertex[xx][yy].radius * scale;
     vert.v[0] = (float)(offset.x() + (r * cosf(angxz)));
     vert.v[1] = (float)(offset.y() + (dy + shape->sphereVertex[xx][yy].depth) * -scale);
     vert.v[2] = (float)(offset.z() + (r * sinf(angxz)));
     vert.t[0] = (float)(u);
     vert.t[1] = (float)(v);
     s += shape->sphereVertex[xx][yy].depth * scale;
     //world.graphics.graph[x][z]=a(GEO) ;//Geograph(this,vert.x,vert.y,vert.z,tri);
     index++;
     if (index > 0) { //&& index!=verts
     //verts=index;
     if (pole) {
     if (tri < 0) {
     int i[3] = { index - 1, index, 0 };
     atom->Index(i);

     }
     else {
     int i[3] = { index - 1, index, vertexCount - 1 };
     atom->Index(i);
     }
     }
     else {
     int i[3] = { index - wd, index - 1, index };
     atom->Index(i);
     int j[3] = { index - wd + 1, index - wd, index };
     atom->Index(j);

     }
     Linear::Vector v = Linear::Vector(vert.v[0], vert.v[1], vert.v[2]);
     v = v.Normal();
     if (inside) {
     vert.n[0] = (float)(-v.x());
     vert.n[1] = (float)(-v.y());
     vert.n[2] = (float)(-v.z());
     }
     else {
     vert.n[0] = (float)(v.x());
     vert.n[1] = (float)(v.y());
     vert.n[2] = (float)(v.z());
     }
     }
     atom->Vertex(vert);
     if (y == 0 || y == h) {
     //if(!y%2)index--;
     break;
     }
     u += txz;
     angxz = shape->sphereVertex[xx][yy].radi;
     xx++;
     }//xz
     //angy+=dy;
     dy += shape->depthStep[yy];
     dy *= 0.5f;
     dy += (s / w);
     v += ty;
     yy++;

     }//y
     //calculate normals
     //set=(set+ToString(":Final Verts:"));
     //set=(set+IntToString(world.geocylinder.currentVerts));
     //set=(set+ToString(":Final Index:"));
     //set=(set+IntToString(world.geocylinder.currentIndecies));
     //set=(set+ToString(":Index Error:"));

     //printf("final v:%d\ti:%d\n",geocylinder->currentVerts,geocylinder->currentIndecies);
     //set=(set+IntToString(index-geocylinder->currentVerts));
     return atom;
     }
     */
}
