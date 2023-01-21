/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerwyn Jones
 */
public class Vector extends Cartesian {

    double w;

    public Vector() {
        w = 1;
    }

    public Vector(double xx, double yy) {
        x = xx;
        y = yy;
        z = 0;
        w = 1;
    }

    public Vector(double xx, double yy, double zz) {
        x = xx;
        y = yy;
        z = zz;
        w = 1;
    }

    public Vector(double xx, double yy, double zz, double ww) {
        x = xx;
        y = yy;
        z = zz;
        w = ww;
    }

    public Vector(Vector v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }

    public static boolean Equal2D(Vector a, Vector b) {
        return a.x == b.x && a.y == b.y;
    }

    public static boolean NotEqual2D(Vector a, Vector b) {
        return a.x != b.x || a.y != b.y;
    }

    public static boolean LessThan2D(Vector a, Vector p) {
        return (a.x < p.x) || (a.x == p.x && a.y < p.y);
    }

    static boolean GreaterThan2D(Vector a, Vector p) {
        return (a.x > p.x) || (a.x == p.x && a.y > p.y);
    }

    public static Vector Add2D(Vector p, Vector q) {
        return new Vector(p.x + q.x, p.y + q.y);
    }

    public static Vector Subtract2D(Vector p, Vector q) {
        return new Vector(p.x - q.x, p.y - q.y);
    }

    public static Vector Multiply(Vector p, float s) {
        return new Vector((p.x * s), (p.y * s), (p.z * s));
    }

    public static Vector Add(Vector p, Vector q) {
        return new Vector(p.x + q.x, p.y + q.y, p.z + q.z);
    }
    public static Vector Add(Vector p, Vector q, Vector r) {
        return new Vector(p.x + q.x+ r.x, p.y + q.y+ r.y, p.z + q.z+ r.z);
    }

    public static Vector Subtract(Vector p, Vector q) {
        return new Vector(p.x - q.x, p.y - q.y, p.z - q.z);
    }

    public static Vector Multiply2D(Vector p, float s) {
        return new Vector((p.x * s), (p.y * s));
    }

    public static float DotProduct2D(Vector p0, Vector p1) {
        return (float) (p0.x * p1.x + p0.y * p1.y);
    }

    public boolean Collision2D(float r1, Vector p, float r2) {
        Vector a = this.Subtract2D(p);
        float r3 = r1 * r1 + r2 * r2;
        if (a.Length2D() < r3) {
            return true;
        }
        return false;
    }

    public boolean Collision(float r1, Vector p, float r2) {
        Vector a = this.Subtract(p);
        float r3 = r1 * r1 + r2 * r2;
        if (a.Length() < r3) {
            return true;
        }
        return false;
    }

    public Position ClassifyPosition2D(Vector p0, Vector p1) {
        Vector p2 = new Vector(this.x, this.y);
        Vector a = p1.Subtract2D(p0);
        Vector b = p2.Subtract2D(p0);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > 0.001f) {
            return Position.LEFT;
        }
        if (sa < -0.001f) {
            return Position.RIGHT;
        }
        if ((a.x * b.x) < -0.001f || (a.y * b.y) < -0.001f) {
            return Position.BEHIND;
        }
        if (a.Length2D() < b.Length2D()) {
            return Position.BEYOND;
        }
        if (p0 == p2) {
            return Position.ORIGIN;
        }
        if (p1 == p2) {
            return Position.DESTINATION;
        }
        return Position.BETWEEN;
    }

    public Position ClassifyPosition(Vector p0, Vector p1) {
        Vector p2 = new Vector(this.x, this.y, this.z);
        Vector a = p1.Subtract(p0);
        Vector b = p2.Subtract(p0);
        double sa = a.x * b.z - a.z * b.x;
        if (sa > 0.001f) {
            return Position.LEFT;
        }
        if (sa < -0.001f) {
            return Position.RIGHT;
        }
        if ((a.x * b.x) < -0.001f || (a.z * b.z) < -0.001f) {
            return Position.BEHIND;
        }
        if (a.Length2D() < b.Length2D()) {
            return Position.BEYOND;
        }
        if (p0 == p2) {
            return Position.ORIGIN;
        }
        if (p1 == p2) {
            return Position.DESTINATION;
        }
        return Position.BETWEEN;
    }

    public double PolarAngle2D() {
        if ((x == 0.0) && (y == 0.0)) {
            return -1;
        }
        if (x == 0.0) {
            return ((y > 0) ? Math.PI : Math.PI + Math.PI * 0.5);
        }
        double theta = Math.atan2(y, x);
        if (x > 0) {
            return ((y >= 0) ? theta : Math.PI + Math.PI + theta);
        } else {
            return (Math.PI + theta);
        }
    }

    public double PolarAngle() {
        if ((x == 0.0) && (z == 0.0)) {
            return -1;
        }
        if (x == 0.0) {
            return ((z > 0) ? Math.PI : Math.PI + Math.PI * 0.5);
        }
        double theta = Math.atan2(z, x);
        if (x > 0) {
            return ((z >= 0) ? theta : Math.PI + Math.PI + theta);
        } else {
            return (Math.PI + theta);
        }
    }

    public boolean Equals2D(Vector b) {
        return x == b.x && y == b.y;
    }

    public boolean Equals(Vector b) {
        return x == b.x && y == b.y && z == b.z;
    }

    public Vector Perpendicular2D() {

        return new Vector(-y, x);
    }

    public Vector Perpendicular() {

        return new Vector(x, y, -z);
    }

    public static int Orientation2D(Vector p0, Vector p1, Vector p2) {
        Vector a = p1.Subtract2D(p0);
        Vector b = p2.Subtract2D(p0);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public static int Orientation(Vector p0, Vector p1, Vector p2) {
        Vector a = p1.Subtract(p0);
        Vector b = p2.Subtract(p0);
        double sa = a.x * b.z - a.z * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public int Orientation2D(Vector p1, Vector p2) {
        Vector a = p1.Subtract2D(this);
        Vector b = p2.Subtract2D(this);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public int Orientation(Vector p1, Vector p2) {
        Vector a = p1.Subtract(this);
        Vector b = p2.Subtract(this);
        double sa = a.x * b.z - a.z * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public boolean Equals2D(Vector p, float r) {
        Vector a = this.Subtract2D(p);
        float r2 = r * r;
        if (a.Length2D() < r2) {
            return true;
        }
        return false;
    }

    public boolean DoesNotEqual2D(Vector b) {
        return x != b.x || y != b.y;
    }

    public boolean DoesNotEqual(Vector b) {
        return x != b.x || y != b.y || z != b.z;
    }

    public boolean IsLessThan2D(Vector p) {
        return (this.x < p.x) || (this.x == p.x && this.y < p.y);
    }

    public boolean IsLessThan(Vector p) {
        return (this.x < p.x) || (this.x == p.x && this.z < p.z);
    }

    public boolean IsGreaterThan2D(Vector p) {
        return (this.x > p.x) || (this.x == p.x && this.y > p.y);
    }

    public boolean IsGreaterThan(Vector p) {
        return (this.x > p.x) || (this.x == p.x && this.z > p.z);
    }

    public void Middle2D(Vector q) {
        this.x = (this.x + q.x) * 0.5f;
        this.y = (this.y + q.y) * 0.5f;

    }

    public void Middle(Vector q) {
        this.x = (this.x + q.x) * 0.5f;
        this.y = (this.y + q.y) * 0.5f;
        this.z = (this.z + q.z) * 0.5f;

    }

    public void Plus2D(Vector q) {
        this.x = this.x + q.x;
        this.y = this.y + q.y;

    }

    public void Plus(Vector q) {
        this.x = this.x + q.x;
        this.y = this.y + q.y;
        this.z = this.z + q.z;

    }

    public void Minus2D(Vector q) {
        this.x = this.x - q.x;
        this.y = this.y - q.y;

    }

    public void Minus(Vector q) {
        this.x = this.x - q.x;
        this.y = this.y - q.y;
        this.z = this.z - q.z;

    }

    public void Scale2D(Vector q) {
        this.x = this.x * q.x;
        this.y = this.y * q.y;

    }

    public void Scale2D(float s) {
        this.x = this.x * s;
        this.y = this.y * s;

    }

    public void Scale(float s) {
        this.x = this.x * s;
        this.y = this.y * s;
        this.z = this.z * s;

    }

    public Vector Add2D(Vector q) {
        double xx = this.x + q.x;
        double yy = this.y + q.y;
        return new Vector(xx, yy);
    }

    public Vector Added(Vector q) {
        double xx = this.x + q.x;
        double yy = this.y + q.y;
        double zz = this.z + q.z;
        return new Vector(xx, yy, zz);
    }
    public void Add(Vector q) {
        this.x += q.x;
        this.y += q.y;
        this.z += q.z;
    }

    public Vector Subtract2D(Vector q) {
        double xx = x - q.x;
        double yy = y - q.y;
        return new Vector(xx, yy);
    }

    public Vector Subtract(Vector q) {
        double xx = x - q.x;
        double yy = y - q.y;
        double zz = z - q.z;
        return new Vector(xx, yy, zz);
    }

    public Vector Multiply2D(float s) {
        float xx = (float) (x * s);
        float yy = (float) (y * s);
        return new Vector(xx, yy);
    }

    public Vector Multiply(float s) {
        float xx = (float) (x * s);
        float yy = (float) (y * s);
        float zz = (float) (z * s);
        return new Vector(xx, yy, zz);
    }

    public Vector Negative2D() {
        double xx = -x;
        double yy = -y;
        return new Vector(xx, yy);
    }

    public Vector CrossProduct2D(Vector vect2) {
        double xy = x * vect2.y;
        double yx = y * vect2.x;
        return new Vector(xy - yx, yx - xy);
    }

    public Vector Midpoint2D(Vector from) {
        double xp = x + from.x;
        double yp = y + from.y;
        return new Vector(xp * 0.5, yp * 0.5);
    }

    public Vector Midpoint(Vector from) {
        double xp = x + from.x;
        double yp = y + from.y;
        double zp = z + from.z;
        return new Vector(xp * 0.5, yp * 0.5, zp * 0.5);
    }

    public float Distance2D(Vector from) {
        double xp = x - from.x;
        double yp = y - from.y;
        return (float) Math.sqrt(xp * xp + yp * yp);
    }

    public float Distance(Vector from) {
        double xp = x - from.x;
        double yp = y - from.y;
        double zp = z - from.z;
        return (float) Math.sqrt(xp * xp + yp * yp + zp * zp);
    }

    public float DistanceSqrd(Vector from) {
        double xp = x - from.x;
        double yp = y - from.y;
        double zp = z - from.z;
        return (float) (xp * xp + yp * yp + zp * zp);
    }

    public float Sine() {
        return (float) (y / Length());
    }

    public float Cosine() {
        return (float) (x / Length());
    }

    public float Tan() {
        return (float) (y / x);
    }

    public float Cotan() {
        return (float) (x / y);
    }

    public float Sec() {
        return (float) (Length() / x);
    }

    public float Cosec() {
        return (float) (Length() / y);
    }

    public Vector GetNormal2D() {
        double im = 1.0f / Length2D();
        return new Vector(x * im, y * im);
    }

    public Vector GetNormal() {
        double im = 1.0f / Length();
        return new Vector(x * im, y * im, z * im);
    }

    public static Vector CrossProduct2D(Vector vect1, Vector vect2) {
        double xy = vect1.x * vect2.y;
        double yx = vect1.y * vect2.x;
        return new Vector(xy - yx, yx - xy);
    }

    public static int PointTest(Vector p, Vector q) {
        if (p.IsLessThan2D(q)) {
            return -1;
        }
        if (p.IsGreaterThan2D(q)) {
            return 1;
        }
        return 0;
    }

    public int PointTest(Vector q) {
        if (IsLessThan2D(q)) {
            return -1;
        }
        if (IsGreaterThan2D(q)) {
            return 1;
        }
        return 0;
    }
    public static int pointAreaWidth = 64;

    public static int PointAreaTest(Vector p, Vector q) {
        double np = p.x + p.y * pointAreaWidth;
        double nq = q.x + q.y * pointAreaWidth;
        if (np < nq) {
            return -1;
        }
        if (np > nq) {
            return 1;
        }
        return 0;
    }

    public int PointAreaTest(Vector q) {
        double np = x + y * pointAreaWidth;
        double nq = q.x + q.y * pointAreaWidth;
        if (np < nq) {
            return -1;
        }
        if (np > nq) {
            return 1;
        }
        return 0;
    }

    public static int LeftToRightCompare(Vector a, Vector b) {
        if (a.IsLessThan2D(b)) {
            return -1;
        }
        if (a.IsGreaterThan2D(b)) {
            return 1;
        }
        return 0;
    }

    public static int RightToLeftCompare(Vector a, Vector b) {
        if (b.IsLessThan2D(a)) {
            return -1;
        }
        if (b.IsGreaterThan2D(a)) {
            return 1;
        }
        return 0;
    }

    public static int PolarCompare(Vector p, Vector q, Vector original) {
        Vector vp = p.Subtract2D(original);
        Vector vq = q.Subtract2D(original);
        float pa = (float) vp.PolarAngle2D();
        float qa = (float) vq.PolarAngle2D();
        if (pa < qa) {
            return -1;
        }
        if (pa > qa) {
            return 1;
        }
        double vpl = vp.Length2D();
        double vql = vq.Length2D();
        if (vpl < vql) {
            return -1;
        }
        if (vpl > vql) {
            return 1;
        }
        return 0;
    }

    public static int ClosestPoint2D(Vector p, Vector q, Vector to) {
        Vector vp = p.Subtract2D(to);
        Vector vq = q.Subtract2D(to);
        double pd = vp.Length2D();
        double qd = vq.Length2D();
        if (pd < qd) {
            return -1;
        }
        if (pd > qd) {
            return 1;
        }
        return 0;
    }

    public static int ClosestPoint(Vector p, Vector q, Vector to) {
        Vector vp = p.Subtract(to);
        Vector vq = q.Subtract(to);
        double pd = vp.Length();
        double qd = vq.Length();
        if (pd < qd) {
            return -1;
        }
        if (pd > qd) {
            return 1;
        }
        return 0;
    }

    @Override
    public double Length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public double InvLength() {
        return 1.0 / Math.sqrt(x * x + y * y + z * z);
    }

    public double LengthSqrd() {
        return (x * x + y * y + z * z);
    }

    @Override
    public Normal Normal() {
        double vi = InvLength();
        return new Normal(vi * x, vi * y, vi * z);
    }

    @Override
    public Vector Negative() {
        return new Vector(-x, -y, -z);
    }

    public Vector Absolute() {
        return new Vector(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    @Override
    public void Normalize() {
        double vi = InvLength();
        Scale(vi);
    }

    public Vector Subtracted(Vector v) {
        return new Vector(
        x -v.x,y -v.y,z -v.z);
        
    }
    public void Sub(Vector v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void Scale(Vector v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
    }

    @Override
    public void Scale(double v) {
        x *= v;
        y *= v;
        z *= v;
    }

    public Vector Scaled(Vector v) {

        return new Vector(x * v.x, y * v.y, z * v.z);
    }

    @Override
    public Vector Scaled(double v) {
        return new Vector(x * v, y * v, z * v);
    }

    public Vector Scaled2D(double v) {
        return new Vector(x * v, y * v, z);
    }

    public void Clamp(double from, double to) {
        if (x < from) {
            x = from;
        } else if (x > to) {
            x = to;
        }
        if (y < from) {
            y = from;
        } else if (y > to) {
            y = to;
        }
        if (z < from) {
            z = from;
        } else if (z > to) {
            z = to;
        }
    }
@Override
    public boolean To(StellarFiles f) {
        f.Add(x, y, z,w);
        return true;
    }

    @Override
    public boolean From(StellarFiles f) {
        
        try {
            Double[] get=f.ReadDoubleArray();
            if(get!=null){
            x=get[0];
            y=get[1];
            z=get[2];
            w=get[3];
            return true;
            }
            return false;
        } catch (Exception ex) {
            Logger.getLogger(Cartesian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public double Dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector Cross(Vector v) {
        return new Vector(y * v.z + z * v.y, x * v.z + z * v.x, y * v.x + y * v.x);
    }
}
