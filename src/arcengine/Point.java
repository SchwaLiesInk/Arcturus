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
public class Point extends Cartesian {

    public double r;

    public Point(double x, double y) {
        super(x, y);
        r = 1;
    }

    public Point(double rad) {
        r = rad;
    }

    public Point() {
        r = 1;
    }

    public Point(double x, double y, double z) {
        super(x, y, z);
        r = 1;
    }

    public Point(double x, double y, double z, double radius) {
        super(x, y, z);
        r = radius;
    }

    public Vector Sub(Point p1, Point p2) {
        return new Vector(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }

    public boolean Parallel3D(Point p, double r) {
        Vector na = CrossProduct(this, p).AsVector();
        return r * r < Math.abs(DotProduct(na, p));
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

    public Point Add2D(Vector q) {
        double xx = this.x + q.x;
        double yy = this.y + q.y;
        return new Point(xx, yy);
    }

    public Point Add(Vector q) {
        double xx = this.x + q.x;
        double yy = this.y + q.y;
        double zz = this.z + q.z;
        return new Point(xx, yy, zz);
    }

    public Vector Subtract2D(Vector q) {
        double xx = x - q.x;
        double yy = y - q.y;
        return new Vector(xx, yy);
    }

    public Point Subtract(Vector q) {
        double xx = x - q.x;
        double yy = y - q.y;
        double zz = z - q.z;
        return new Point(xx, yy, zz, r);
    }

    public Vector Subtract(Point q) {
        double xx = x - q.x;
        double yy = y - q.y;
        double zz = z - q.z;
        return new Vector(xx, yy, zz);
    }

    public Point Multiply2D(float s) {
        float xx = (float) (x * s);
        float yy = (float) (y * s);
        return new Point(xx, yy, z, r);
    }

    public Point Multiply(float s) {
        float xx = (float) (x * s);
        float yy = (float) (y * s);
        float zz = (float) (z * s);
        return new Point(xx, yy, zz, r);
    }

    public Point Multiply(Vector s) {
        float xx = (float) (x * s.x);
        float yy = (float) (y * s.y);
        float zz = (float) (z * s.z);
        return new Point(xx, yy, zz, r);
    }

    public Point Negative2D() {
        double xx = -x;
        double yy = -y;
        return new Point(xx, yy, z, r);
    }

    @Override
    public Point Negative() {
        double xx = -x;
        double yy = -y;
        double zz = -z;
        return new Point(xx, yy, zz, r);
    }

    public Point CrossProduct2D(Point vect2) {
        double xy = x * vect2.y;
        double yx = y * vect2.x;
        return new Point(xy - yx, yx - xy);
    }

    public Point Midpoint2D(Point from) {
        double xp = x + from.x;
        double yp = y + from.y;
        return new Point(xp * 0.5, yp * 0.5, z, r);
    }

    public Point Midpoint(Point from) {
        double xp = x + from.x;
        double yp = y + from.y;
        double zp = z + from.z;
        return new Point(xp * 0.5, yp * 0.5, zp * 0.5, r);
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


    public Vector Absolute() {
        return new Vector(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Point Scaled2D(double s) {

        return new Point(x * s, y * s);
    }

    public Point Scaled3D(double s) {

        return new Point(x * s, y * s, z * s);
    }

    public Point Scaled3D(Vector s) {

        return new Point(x * s.x, y * s.y, z * s.z);
    }

    public void Scale3D(Vector s) {

        x *= s.x;
        y *= s.y;
        z *= s.z;
    }

    public void Scale2D(Point s) {

        x *= s.x;
        y *= s.y;
    }

    public Local Localize(double s) {
        short xs = (short) (x * s);
        short ys = (short) (y * s);
        return new Local(xs, ys);
    }

    public Point Localize(Local loc, double s) {
        double iss = 1.0f / s;
        double xs = (double) (loc.x) * iss;
        double ys = (double) (loc.y) * iss;
        return new Point(xs, ys);
    }

    public Point Centralize(Local loc, double s) {
        double iss = 1.0f / s;
        double xs = (((double) (loc.x) + 0.5f) * iss);
        double ys = (((double) (loc.y) + 0.5f) * iss);
        return new Point(xs, ys);
    }

    public Point Localize(Local loc, double h, double s) {
        double iss = 1.0f / s;
        double xs = (double) (loc.x) * iss;
        double ys = (double) (loc.y) * iss;
        return new Point(xs, h, ys);
    }

    public Point Centralize(Local loc, double h, double s) {
        double iss = 1.0f / s;
        double xs = (((double) (loc.x) + 0.5f) * iss);
        double ys = (((double) (loc.y) + 0.5f) * iss);
        return new Point(xs, h, ys);
    }

    public double Max() {
        double mxy = Math.max(x, y);
        double mxz = Math.max(mxy, z);
        return mxz;
    }

    public double Min() {
        double mxy = Math.min(x, y);
        double mxz = Math.min(mxy, z);
        return mxz;
    }

    public Point Max(Point m) {
        return new Point(Math.max(x, m.x), Math.max(y, m.y), Math.max(z, m.z));
    }

    public Point Min(Point m) {
        return new Point(Math.min(x, m.x), Math.min(y, m.y), Math.min(z, m.z));
    }

    public Point Set3D(double xyz[]) {
        x = xyz[0];
        y = xyz[1];
        z = xyz[2];
        return this;
    }

    public Point Set2D(double xyz[]) {
        x = xyz[0];
        y = xyz[1];
        return this;
    }

    public boolean EqualSphere(Point p, double r) {
        Vector a = Sub(this, p);
        double r2 = (this.r * r) * 2;
        if (a.LengthSquared() < r2) {
            return true;
        }
        return false;
    }

    public boolean EqualCube(Point p, double threshold) {
        return Math.abs(x - p.x) < threshold && Math.abs(y - p.y) < threshold && Math.abs(z - p.z) < threshold;
    }

    public double Distance(Point p2) {
        return Sub(this, p2).Length();
    }

    public double DistanceSquared(Point p2) {
        return Sub(this, p2).LengthSquared();
    }

    public int PointTest(Point p, Point q) {
        if (LessThan(p, q)) {
            return -1;
        }
        if (Greater(p, q)) {
            return 1;
        }
        return 0;
    }

    public int PointTest(Point q) {
        if (LessThan(this, q)) {
            return -1;
        }
        if (Greater(this, q)) {
            return 1;
        }
        return 0;
    }

    public int TestPoints(Point p, Point q) {
        if (LessThan(p, q)) {
            return -1;
        }
        if (Greater(p, q)) {
            return 1;
        }
        return 0;
    }

    public int PointAreaTest(Point p, Point q) {
        double pointAreaWidth = p.r + q.r;
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

    public int SideCompare2D(Point a, Point b, byte side) {
        switch (side) {
            case Linear.SideLeft: {

                if (a.x < b.x) {
                    return -1;
                }
                if (a.x > b.x) {
                    return 1;
                }
                break;
            }
            case Linear.SideRight: {

                if (a.x > b.x) {
                    return -1;
                }
                if (a.x < b.x) {
                    return 1;
                }
                break;
            }
            case Linear.SideFront: {

                if (a.y < b.y) {
                    return -1;
                }
                if (a.y > b.y) {
                    return 1;
                }
                break;
            }
            case Linear.SideBack: {

                if (a.y > b.y) {
                    return -1;
                }
                if (a.y < b.y) {
                    return 1;
                }
                break;
            }
            case Linear.SideTop: {

                if (a.z < b.z) {
                    return -1;
                }
                if (a.z > b.z) {
                    return 1;
                }
                break;
            }
            case Linear.SideBottom: {

                if (a.z > b.z) {
                    return -1;
                }
                if (a.z < b.z) {
                    return 1;
                }
                break;
            }
        }
        return 0;
    }

    public int SideCompare3D(Point a, Point b, byte side) {
        switch (side) {
            case Linear.SideLeft: {

                if (a.x < b.x) {
                    return -1;
                }
                if (a.x > b.x) {
                    return 1;
                }
                break;
            }
            case Linear.SideRight: {

                if (a.x > b.x) {
                    return -1;
                }
                if (a.x < b.x) {
                    return 1;
                }
                break;
            }
            case Linear.SideFront: {

                if (a.z < b.z) {
                    return -1;
                }
                if (a.z > b.z) {
                    return 1;
                }
                break;
            }
            case Linear.SideBack: {

                if (a.z > b.z) {
                    return -1;
                }
                if (a.z < b.z) {
                    return 1;
                }
                break;
            }
            case Linear.SideTop: {

                if (a.y < b.y) {
                    return -1;
                }
                if (a.y > b.y) {
                    return 1;
                }
                break;
            }
            case Linear.SideBottom: {

                if (a.y > b.y) {
                    return -1;
                }
                if (a.y < b.y) {
                    return 1;
                }
                break;
            }
        }
        return 0;
    }

    public int LeftToRightCompare(Point a, Point b) {
        if (LessThan(a, b)) {
            return -1;
        }
        if (Greater(a, b)) {
            return 1;
        }
        return 0;
    }

    public int RightToLeftCompare(Point a, Point b) {
        if (LessThan(b, a)) {
            return -1;
        }
        if (Greater(b, a)) {
            return 1;
        }
        return 0;
    }@Override
    public boolean To(StellarFiles f) {
        f.Add(x, y, z,r);
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
            r=get[3];
            return true;
            }
            return false;
        } catch (Exception ex) {
            Logger.getLogger(Cartesian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
