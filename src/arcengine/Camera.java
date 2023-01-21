/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

enum Viewer {

    Isometric, Internal, External
}

/**
 *
 * @author Gerwyn Jones
 */
public class Camera extends Cartesian {

    public double r;
    //
    double near = 1.0f / Linear.MAX_MAP;
    double invNear = 1.0f;
    double far = Linear.MAX_MAP;
    double zoom = 10;
    //

    static Normal UP = new Normal(0, 1, 0);
    Matrix linear;
    Quarternion rotation;
    Matrix projection;
    Matrix view;
    Matrix projectionView;
    Matrix viewProjection;
    Matrix screen;
    Viewer viewer;
    //
    Point lookAt = new Point();
    //Cartesian angle = new Cartesian(0, 0,0);
    Cartesian angle = new Cartesian(0, 0, -Linear.PID2);
    Cartesian direction = new Cartesian(0, 0, 0);
    Vector distance = new Vector();
    Vector viewCenter = new Vector();
    Point screenAt = new Point();
    Local local = new Local();
    Vector fraction = new Vector();//fraction part of local coords
    double reverseView = 1;
    double fov = 0.5;
    double fovE = 0.5;
    double ratio = 1.0;

    Camera(double x, double y, double z, Viewer v, Screen pro) {
        super(x, y, z);
        r = (double) (800);
        viewer = v;
        viewCenter = pro.Center().AsVector();
        screenAt = pro.origin;
        ratio = viewCenter.y / viewCenter.x;

        switch (viewer) {
            case Isometric: {
                fov = 3;
                fovE = (double) (Math.tan(Math.PI / fov) * 2);
                this.projection = Camera.Projection(this.lookAt, fovE, 1);
                this.view = Camera.LookAtIsoView(this.AsPoint(r), new Point(0, 0, 0), UP);
                break;
            }
            case Internal: {
                fov = 3;
                fovE = (double) (Math.tan(Math.PI / fov) * 2);
                this.projection = Camera.PerspectiveProjection(this, fov, fovE, ratio);
                this.screen = Camera.ScreenProjection(screenAt, this, fov, fovE, angle.z);
                this.view = Camera.LookAtView(this.AsPoint(r), new Point(0, 0, 0), UP, angle);
                break;
            }
            case External: {

                fov = 3;
                fovE = (double) (Math.tan(Math.PI / fov) * 2);
                this.projection = Camera.Projection(this.lookAt, fovE, 1);
                this.view = Camera.LookAtIsoView(this.AsPoint(r), new Point(0, 0, 0), UP);
                break;
            }
        }

        this.direction = new Cartesian(-Linear.PID2, 0, 0);

        this.distance = new Vector(0, 0, near / far);
        this.linear = new Matrix();
        this.linear = this.linear.RotateY(0);
        this.projectionView = this.view.Mult(view, this.projection);
        this.viewProjection = this.projection.Mult(this.projection, view);
    }

    public void SetFOV(double angulaFOV) {
        fov = angulaFOV;
        fovE = (double) (Math.tan(Math.PI / fov) * 2);
    }

    public void Update(double et, Vector direct, Normal up, double fovE) {
        while(angle.x>Math.PI){
        angle.x-=Linear.PI2;
        }
        while(angle.x<-Math.PI){
        angle.x+=Linear.PI2;
        }
        while(angle.y>Math.PI){
        angle.y-=Linear.PI2;
        }
        while(angle.y<-Math.PI){
        angle.y+=Linear.PI2;
        }
        switch (viewer) {
            case Isometric: {
                //this.lookAt = direct.AsPoint(1);// *reverseView;//
                this.distance = lookAt.Sub(lookAt, this.AsPoint(r));
                this.projection = Camera.Projection(this.lookAt, fovE, 1);
                view = Camera.LookAtIsoView(this.AsPoint(r), new Point(0, 0, 0), up);
                break;
            }
            case Internal: {
                this.lookAt = this.Add(this, direct).AsPoint(r);// *reverseView;//
                this.distance = lookAt.Sub(lookAt, this.AsPoint(r));
                this.projection = Camera.PerspectiveProjection(this, fov, fovE, ratio);
                this.screen = Camera.ScreenProjection(screenAt, this, fov, fovE, angle.z);
                //view =Camera.EulerView(this,angle);
                view = Camera.LookAtView(this.AsPoint(r), lookAt, up, angle);
                break;
            }
            case External: {
                //this.lookAt = direct.AsPoint(1);// *reverseView;//
                this.distance = lookAt.Sub(lookAt, this.AsPoint(r));
                this.projection = Camera.Projection(this.lookAt, fov, 1);
                view = Camera.LookAtIsoView(this.AsPoint(r), new Point(0, 0, 0), up);
                break;
            }
        }

        this.projectionView = this.view.Mult(view, this.projection);
        this.viewProjection = this.projection.Mult(this.projection, view);

    }

    void SetLocal(Local copy, Vector fraction) {
        this.local.x = copy.x;
        this.local.y = copy.y;
        this.local.z = copy.z;
        this.fraction = fraction;
    }

    void SetLocal(double tileRadius) {
        double lim = LandScape.ScapeMiddle * tileRadius;
        while (z > lim) {
            z = lim - (z - lim);
            x += lim;
            this.angle.x += Math.PI;
        }
        while (z < -lim) {
            z = -lim - (lim+z);
            x += lim;
            this.angle.x += Math.PI;
            //System.out.println(z);
        }
        while (x > lim) {
            x -= lim * 2;
        }
        while (x < -lim) {
            x += lim * 2;
        }
        //
        double ax=(x+tileRadius*0.5);
        double az=(z+tileRadius*0.5);
        this.fraction.x = (ax / tileRadius) - (int) (ax / tileRadius);
        this.fraction.y = (az / tileRadius) - (int) (az / tileRadius);
        //
        int i = (int) (ax/ tileRadius);
        int j = (int) (az / tileRadius);
        int x1 = Math.abs(LandScape.ScapeMiddle + i) % LandScape.ScapeSize;
        int y1 = (LandScape.ScapeMiddle + j);
        /*while (y1 < 0) {
            y1 = Math.abs(y1);
            x1 += LandScape.ScapeMiddle;
            x1 %= LandScape.ScapeSize;
        }
        while (y1 >= LandScape.ScapeSize) {
            y1 = LandScape.ScapeSize-Math.abs(y1 - LandScape.ScapeSize);
            x1 += LandScape.ScapeMiddle;
            x1 %= LandScape.ScapeSize;
        }*/
        //System.out.println("At "+x+" "+z +" Loc "+x1+" "+y1);
        //System.out.println("At "+(x-lim)+" "+(z-lim) +" Loc "+(x1-LandScape.ScapeMiddle)+" "+(y1-LandScape.ScapeMiddle));
        this.local = new Local(x1,y1);

    }
// <editor-fold defaultstate="collapsed" desc="View Tests">

    boolean InView(Screen scr, Point go, Quarternion rot, double scale, double minRadius) {
        Point got = new Point();
        if (scr.GetViewLocation(go, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Circle go, Quarternion rot, double scale, double minRadius) {
        Point got = new Point();
        if (scr.GetViewLocation(go.center, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Segment go, Quarternion rot, double scale, double minRadius) {
        Vector c = go.p1.Add(go.p1, go.p2).Scaled(0.5f).AsVector();
        double r = (go.p1.Sub(go.p1, go.p2)).Length();
        Point got = new Point();
        if (scr.GetViewLocation(c.AsPoint(r), rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Line go, Quarternion rot, double scale, double minRadius) {
        Vector dis = go.direction.Scaled(far);
        Vector end = go.start.Add(go.start, dis).AsVector();
        Point c = go.start.Add(go.start, end).Scaled(0.5).AsPoint(1);
        c.r = go.start.Sub(go.start, dis).Length();
        Point got = new Point();
        if (scr.GetViewLocation(c, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Box go, Quarternion rot, double scale, double minRadius) {
        Point c = go.origin.Add(go.origin, go.size).Scaled(0.5).AsPoint(1);
        c.r = go.size.Length() * 0.5;
        Point got = new Point();
        if (scr.GetViewLocation(c, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Rectalinear go, Quarternion rot, double scale, double minRadius) {
        Point c = go.center.AsPoint(1);
        c.r = go.halfExtent.Length();
        Point got = new Point();
        if (scr.GetViewLocation(c, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }

    boolean InView(Screen scr, Sphere go, Quarternion rot, double scale, double minRadius) {

        Point got = new Point();
        if (scr.GetViewLocation(go.center, rot, scale, minRadius, got)) {
            return TestInView(got);
        }
        return false;
    }
    /*boolean InView(Screen scr, Triangle go, Quarternion rot, double scale, double minRadius)
     {
     Vector c = (go.bbox.pMax + go.bbox.pMin) * 0.5f;
     double r = (double)Math.Max(go.bbox.pMax.Length(), go.bbox.pMin.Length())*0.5f;

            
     Point got = new Point();
     if(scr.GetViewLocation(c.AsPoint(r),rot, scale, minRadius,ref got)){
     return TestInView(got);
     }
     return false;
     }*/

    boolean TestInView(Point p) {

        double z = p.z;
        double r = p.r;
        //
        if (z - r > far || z + r < -r) {
            return false;
        }
        double test1 = (viewCenter.x);
        if (p.x - r > test1 || p.x + r < -test1) {
            return false;
        }
        double test2 = (viewCenter.y);

        if (p.y - r > test2 || p.y + r < -test2) {
            return false;
        }
        //System.Console.WriteLine("x"+p.x + "y" + p.y + " z" + z + "r" + r + "t1" + test1 + "t2" + test2);
        return true;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="3D Matrix Getters">

    static Matrix LookAtIsoView(Point eye, Point lookAt, Normal dirUp) {
        Matrix mat = new Matrix();
        //Matrix rot = new Matrix();
        //rot = Matrix.RotateZ(upAngle);

        Point lat = lookAt;

        Vector view = eye.Sub(eye, lat);
        //
        Vector up = dirUp.AsVector();
        //up = up * rot;
        double dot = -up.DotProduct(up, view);
        Vector upv = up.Add(up, view.Scaled(dot)).AsVector();
        Vector right = upv.CrossProduct(upv, view.Negative()).AsVector();
        //
        //right = right * rot;
        upv.Normalize();
        right.Normalize();
        view.Normalize();
        right.y = -right.y;
        upv.y = -upv.y;
        view.y = -view.y;
        //
        mat.m[0][0] = right.x;
        mat.m[0][1] = right.y;
        mat.m[0][2] = right.z;
        mat.m[1][0] = upv.x;
        mat.m[1][1] = upv.y;
        mat.m[1][2] = upv.z;
        mat.m[2][0] = view.x;
        mat.m[2][1] = view.y;
        mat.m[2][2] = view.z;
        mat.m[3][0] = -eye.DotProduct(eye, right);
        mat.m[3][1] = -eye.DotProduct(eye, upv);
        mat.m[3][2] = -eye.DotProduct(eye, view);
        mat.m[0][3] = 0;
        mat.m[1][3] = 0;
        mat.m[2][3] = 0;
        mat.m[3][3] = 1;
        //
        return mat;
    }

    static Matrix Projection(Point pos, double fovE, double ratio) {
        Matrix m = new Matrix();

        m.m[0][0] = fovE;
        m.m[1][0] = 0;
        m.m[2][0] = 0;
        m.m[3][0] = pos.x;
        m.m[0][1] = 0;
        m.m[1][1] = fovE * ratio;

        m.m[2][1] = 0;
        m.m[3][1] = pos.y;
        m.m[0][2] = 0;
        m.m[1][2] = 0;
        m.m[2][2] = 1;
        m.m[3][2] = 0;
        m.m[0][3] = 0;
        m.m[1][3] = 0;
        m.m[2][3] = 1;
        m.m[3][3] = 0;
        //
        return m;
    }

    static Matrix EulerView(Cartesian cam, Cartesian a) {
        Matrix mat = new Matrix();
        mat.m[3][0] = -cam.x;
        mat.m[3][1] = -cam.y;
        mat.m[3][2] = -cam.z;
        //mat.m[0][3] = - cam.x;
        //mat.m[1][3] = - cam.y;
        //mat.m[2][3] = - cam.z;
        return mat;
    }

    static Matrix LookAtView(Point eye, Point lookAt, Normal dirUp, Cartesian cam) {
        Matrix mat = new Matrix();
        //Matrix rot = Matrix.RotateY(cam.z);

        Vector view = (lookAt.Sub(lookAt, eye));
        view.Normalize();
        //
        //Vector up = Cartesian.CrossProduct(dirUp,view);

        /*double dot = dirUp.DotProduct(dirUp, view);
         Vector up = dirUp.Sub(dirUp, view.Scaled(dot)).AsVector();
         up.Normalize();
         //
         Vector right = view.CrossProduct(view, up).AsVector();
         //up = Point.CrossProduct(right,view);
         //
         */
        Vector up = dirUp.CrossProduct(dirUp.AsVector(), view).AsVector();

        up.Normalize();
        //float dot = -Point.DotProduct(dirUp.AsPoint(), view);
        //Point up = dirUp.AsPoint() + (view * dot);
        Vector right = view.CrossProduct(view, up).AsVector();
        right.Normalize();
        up = right.CrossProduct(right, view).AsVector();

        up.Normalize();
        //right = right * rot;

        //right.y = right.y;
        //up.y = -up.y;
        //view.y = -view.y;
        //
        mat.m[0][0] = right.x;
        mat.m[1][0] = right.y;
        mat.m[2][0] = right.z;
        mat.m[0][1] = up.x;
        mat.m[1][1] = up.y;
        mat.m[2][1] = up.z;
        mat.m[0][2] = view.x;
        mat.m[1][2] = view.y;
        mat.m[2][2] = view.z;
        //
        mat.m[0][3] = 0;
        mat.m[1][3] = 0;
        mat.m[2][3] = 0;
        mat.m[3][0] = -right.DotProduct(right, eye);
        mat.m[3][1] = -up.DotProduct(up, eye);
        mat.m[3][2] = -view.DotProduct(view, eye);
        //
        mat.m[3][3] = 1;
        //
        return mat;
    }

    static Matrix PerspectiveProjection(Camera cam, double fov, double fovE, double ratio) {
        Matrix m = new Matrix();
        //
        m.m[0][0] = fovE;
        m.m[1][0] = 0;
        m.m[2][0] = 0;
        m.m[1][1] = fovE * ratio;

        m.m[0][1] = 0;
        m.m[0][2] = 0;
        //*/
        m.m[2][1] = 0;
        m.m[1][2] = 0;
        m.m[2][2] = 0;
        //
        m.m[3][0] = 0;
        m.m[3][1] = 0;
        m.m[3][2] = fov;
        //
        m.m[0][3] = 0;
        m.m[1][3] = 0;
        m.m[2][3] = cam.near * cam.far;
        m.m[3][3] = 0;

        return m;// *o;
    }

    static Matrix ScreenProjection(Point screenAt, Camera cam, double fov, double fovE, double a) {
        Matrix n = new Matrix();
        double alpha = cam.viewCenter.x - 0.5f;
        double beta = cam.viewCenter.y - 0.5f;
        //
        n.m[0][0] = (double) (alpha * fov);
        n.m[1][0] = 0;
        n.m[2][0] = 0;
        n.m[3][0] = alpha + screenAt.x;
        n.m[0][1] = 0;
        n.m[1][1] = -(double) (beta * fov);

        //*/
        n.m[2][1] = 0;
        n.m[3][1] = beta + screenAt.y;
        n.m[0][2] = 0;
        n.m[1][2] = 0;
        n.m[2][2] = 1;
        n.m[3][2] = 0;
        n.m[0][3] = 0;
        n.m[1][3] = 0;
        n.m[2][3] = 0;
        n.m[3][3] = 1;
        //

        return n.Mult(n.RotateZ(a), n);// *o;
    }// </editor-fold>
}
