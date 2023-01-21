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
public class Cartesian implements ToFile,FromFile{

    //
    public double x;
    public double y;
    public double z;

    public Cartesian() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Cartesian(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Cartesian(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cartesian(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Cartesian(Vector p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Cartesian(Normal p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public int Orientation2D(Point p0, Point p1, Point p2) {
        Vector a = p1.Sub(p1, p0);
        Vector b = p2.Sub(p2, p0);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public int Orientation2D(Cartesian p1, Cartesian p2) {
        Cartesian a = Sub(p1, this);
        Cartesian b = Sub(p2, this);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > 0) {
            return 1;
        }
        if (sa < 0) {

            return -1;
        }
        return 0;
    }

    public int Orientation3D(Cartesian p0, Cartesian p1, Cartesian p2) {
        Cartesian a = Sub(p1, p0);
        Cartesian b = Sub(p2, p0);
        Cartesian sa = a.CrossProduct(a, b);
        double ln = sa.x + sa.y + sa.z;
        if (ln > 0) {
            return 1;
        }
        if (ln < 0) {

            return -1;
        }
        return 0;
    }

    public int Orientation3D(Cartesian p1, Cartesian p2) {
        Cartesian a = Sub(p1, this);
        Cartesian b = Sub(p2, this);
        Cartesian sa = a.CrossProduct(a, b);
        double ln = sa.x + sa.y + sa.z;
        if (ln > 0) {
            return 1;
        }
        if (ln < 0) {

            return -1;
        }
        return 0;
    }

    public boolean Parallel2D(Cartesian p) {
        Cartesian na = Rotate90Degrees2D();
        return Math.abs(DotProduct2D(na, p)) < Linear.Epsilon;
    }

    public boolean Parallel3D(Cartesian p) {
        Cartesian na = CrossProduct(this, p);
        return Math.abs(DotProduct(na, p)) < Linear.Epsilon;
    }

    public boolean Parallel2D(Cartesian p, double r) {
        Cartesian na = Rotate90Degrees2D();
        return r * r < Math.abs(DotProduct2D(na, p));
    }

    public Cartesian Rotate90Degrees2D() {
        return new Cartesian(-y, x);
    }

    public Cartesian Flip2D() {
        return new Cartesian(x, -y);
    }

    public Cartesian Mirror2D() {
        return new Cartesian(-x, y);
    }

    public boolean Collides2D(Cartesian p) {
        return Math.abs(x - p.x) < Linear.Epsilon && Math.abs(y - p.y) < Linear.Epsilon;
    }

    public boolean Collides2D(Cartesian p, double threshold) {
        return Math.abs(x - p.x) < threshold && Math.abs(y - p.y) < threshold;
    }

    public boolean Collides3D(Cartesian p) {
        return Math.abs(x - p.x) < Linear.Epsilon && Math.abs(y - p.y) < Linear.Epsilon && Math.abs(z - p.z) < Linear.Epsilon;
    }

    public boolean Collides3D(Cartesian p, double threshold) {
        return Math.abs(x - p.x) < threshold && Math.abs(y - p.y) < threshold && Math.abs(z - p.z) < threshold;
    }

    public Normal Normal() {
        double len = Length();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        } else {
            return new Normal(0, 0, 0);
        }
        return new Normal(x * im, y * im, z * im);
    }

    public Vector UnityVector2D() {
        double len = Length2D();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        return new Vector(x * im, y * im);
    }

    public Normal Normal2D() {
        double len = Length2D();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        return new Normal(x * im, y * im);
    }

    public Cartesian Rotate2D(double ang) {
        double cs = (double) Math.cos(ang);
        double sn = (double) Math.sin(ang);
        return new Cartesian(x * cs - y * sn, y * cs + x * sn);
    }

    public Cartesian Rotate3D(double phi, double theta) {
        double r = (double) Math.sqrt(x * x + y * y);
        double p = Length();
        theta += (double) Math.atan2(y, x);
        phi += (double) Math.acos(z / p);
        return new Cartesian(p * (double) Math.sin(phi) * (double) Math.cos(theta), p * (double) Math.sin(phi) * (double) Math.sin(theta), p * (double) Math.cos(phi));
    }

    public Cartesian Rotate2D(Cartesian at, double ang) {
        double cs = (double) Math.cos(ang);
        double sn = (double) Math.sin(ang);
        return Add(at, new Cartesian(x * cs - y * sn, y * cs + x * sn));
    }

    public Cartesian Rotate3D(Cartesian at, double phi, double theta) {
        double r = (double) Math.sqrt(x * x + y * y);
        double p = Length();
        theta += (double) Math.atan2(y, x);
        phi += (double) Math.acos(z / p);
        return Add(at, new Cartesian(p * (double) Math.sin(phi) * (double) Math.cos(theta), p * (double) Math.sin(phi) * (double) Math.sin(theta), p * (double) Math.cos(phi)));
    }

    public Cartesian Unity() {
        double len = Length();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        return new Cartesian(x * im, y * im, z * im);
    }

    public Cartesian Unity2D() {
        double len = Length2D();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        return new Cartesian(x * im, y * im);
    }

    public Normal Normal3D() {
        double len = Length();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        return new Normal(x * im, y * im, z * im);
    }

    public void Normalize() {
        double len = Length();
        double im = 1.0f;
        if (len != 0) {
            im /= len;
        }
        x = (x * im);
        y = y * im;
        z = z * im;
    }

    public boolean EqualCube(Cartesian p, double threshold) {
        return Math.abs(x - p.x) < threshold && Math.abs(y - p.y) < threshold && Math.abs(z - p.z) < threshold;
    }

    public boolean Equals(Cartesian p0, Cartesian p1) {
        return p0 == p1;
    }

    public double EnclosedAngle2D(Cartesian p0, Cartesian p1) {
        Cartesian u1 = p0.Unity2D();
        Cartesian u2 = p1.Unity2D();
        double dp = DotProduct2D(u1, u2);
        return (double) Math.acos(dp);
    }

    public double EnclosedAngle(Cartesian p0, Cartesian p1) {
        Cartesian u1 = p0.Unity();
        Cartesian u2 = p1.Unity();
        double dp = DotProduct(u1, u2);
        return (double) Math.acos(dp);
    }

    public boolean Collision(double r1, Cartesian p, double r2) {
        Cartesian a = Sub(this, p);
        double r3 = r1 * r1 + r2 * r2;
        if (a.LengthSquared() < r3) {
            return true;
        }
        return false;
    }

    public boolean Collision(Cartesian p1, double r1, Cartesian p2, double r2) {
        Cartesian a = Sub(p1, p2);
        double r3 = r1 * r1 + r2 * r2;
        if (a.LengthSquared() < r3) {
            return true;
        }
        return false;
    }

    public byte ClassifyPosition2D(Cartesian p0, Cartesian p1) {
        Cartesian p2 = this;
        Cartesian a = Sub(p1, p0);
        Cartesian b = Sub(p2, p0);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > Linear.Epsilon) {
            return Linear.PositionLeft;
        }
        if (sa < -Linear.Epsilon) {
            return Linear.PositionRight;
        }
        if ((a.x * b.x) < -Linear.Epsilon || (a.y * b.y) < -Linear.Epsilon) {
            return Linear.PositionBehind;
        }
        if (a.LengthSquared() < b.LengthSquared()) {
            return Linear.PositionBeyond;
        }
        if (p0.Equal(p0, p2)) {
            return Linear.PositionOrigin;
        }
        if (p1.Equal(p1, p2)) {
            return Linear.PositionDestination;
        }
        return Linear.PositionBetween;
    }

    public byte ClassifyPosition2D(Point p0, Point p1) {
        Point p2 = AsPoint(1);
        Vector a = p1.Sub(p1, p0);
        Vector b = p2.Sub(p2, p0);
        double sa = a.x * b.y - a.y * b.x;
        if (sa > Linear.Epsilon) {
            return Linear.PositionLeft;
        }
        if (sa < -Linear.Epsilon) {
            return Linear.PositionRight;
        }
        if ((a.x * b.x) < -Linear.Epsilon || (a.y * b.y) < -Linear.Epsilon) {
            return Linear.PositionBehind;
        }
        if (a.LengthSquared() < b.LengthSquared()) {
            return Linear.PositionBeyond;
        }
        if (Equal(p0, p2)) {
            return Linear.PositionOrigin;
        }
        if (Equal(p1, p2)) {
            return Linear.PositionDestination;
        }
        return Linear.PositionBetween;
    }

    // Geometry Internal Functions
    public Cartesian Add(Cartesian v, Vector p) {
        return new Cartesian(v.x + p.x, v.y + p.y, v.z + p.z);
    }

    public Cartesian Sub(Cartesian v, Vector p) {
        return new Cartesian(v.x - p.x, v.y - p.y, v.z - p.z);
    }

    public Cartesian Add(Cartesian v, Normal p) {
        return new Cartesian(v.x + p.x, v.y + p.y, v.z + p.z);
    }

    public Cartesian Sub(Cartesian v, Normal p) {
        return new Cartesian(v.x - p.x, v.y - p.y, v.z - p.z);
    }

    public Cartesian Add(Cartesian v, Cartesian p) {
        return new Cartesian(v.x + p.x, v.y + p.y, v.z + p.z);
    }

    public Cartesian Sub(Cartesian v, Cartesian p) {
        return new Cartesian(v.x - p.x, v.y - p.y, v.z - p.z);
    }

    public Cartesian Sub(Cartesian a, Point p) {
        return new Cartesian(a.x - p.x, a.y - p.y, a.z - p.z);
    }

    public Cartesian Negative(Cartesian p) {
        return new Cartesian(-p.x, -p.y, -p.z);
    }

    public double Distance(Cartesian p1, Cartesian p2) {
        return (Sub(p1, p2)).Length();
    }

    public double DistanceSquared(Cartesian p1, Cartesian p2) {
        return (Sub(p1, p2)).LengthSquared();
    }

    public boolean Equal(Cartesian a, Cartesian p) {
        if (a == (null) && p == (null)) {
            return true;
        } else if (a == (null) || p == (null)) {
            return false;
        } else {
            return Math.abs(a.x - p.x) < Linear.Epsilon && Math.abs(a.y - p.y) < Linear.Epsilon && Math.abs(a.z - p.z) < Linear.Epsilon;
        }
    }

    public boolean NotEqual(Cartesian a, Cartesian p) {
        return !Equal(a, p);
    }

    public boolean LessThan(Cartesian a, Cartesian p) {
        return (a.x < p.x) || (a.x == p.x && a.y < p.y);
    }

    public boolean Greater(Cartesian a, Cartesian p) {
        return (a.x > p.x) || (a.x == p.x && a.y > p.y);
    }

    public boolean Parallel3D(Cartesian p, double r) {
        Cartesian na = CrossProduct(this, p);
        return r * r < Math.abs(DotProduct(p, na));
    }

    public double Sine(double invr) {
        return x * invr;
    }

    public double Cosine(double invr) {
        return y * invr;
    }

    public double LengthSquared() {
        return x * x + y * y + z * z;
    }

    public double Length() {
        return (double) Math.sqrt(LengthSquared());
    }

    public double InvLength() {
        return (double) (1.0f / Math.sqrt(LengthSquared()));
    }

    public double Tangent(byte axis) {
        double t = 0;
        if (axis == Linear.AxisZ) {
            if (x != 0) {
                t = (double) y / x;
            }
        } else if (axis == Linear.AxisY) {
            if (x != 0) {
                t = (double) z / x;
            }
        } else {
            if (z != 0) {
                t = (double) y / z;
            }
        }
        return t;
    }

    public double PolarAngle2D() {
        if ((x == 0.0) && (y == 0.0)) {
            return -1;
        }
        if (x == 0.0) {
            return ((y > 0) ? Linear.PI90 : Linear.PI270);
        }
        double theta = Math.atan(y / x);
        if (x > 0) {
            return ((y >= 0) ? theta : Linear.PI2 + theta);
        } else {
            return (Math.PI + theta);
        }
    }

    public Point AsPoint(double r) {
        return new Point(x, y, z, r);
    }

    public Normal AsNormal() {
        return new Normal(x, y, z);
    }

    public Vector AsVector() {
        return new Vector(x, y, z);
    }

    public double Angle() {
        double theta = (double) (Math.atan2(y, x));
        return theta;
    }

    public double Length2DSquared() {
        return x * x + y * y;
    }

    public double Length2D() {
        return (double) Math.sqrt(Length2DSquared());
    }

    public double Dot(Cartesian n1, Cartesian n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double DotProduct(Cartesian n1, Cartesian n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double AbsDot(Cartesian n1, Cartesian n2) {
        return Math.abs(n1.x * n2.x + n1.y * n2.y + n1.z * n2.z);
    }

    public double Dot(Cartesian n1, Point n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double DotProduct(Cartesian n1, Point n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double AbsDot(Cartesian n1, Point n2) {
        return Math.abs(n1.x * n2.x + n1.y * n2.y + n1.z * n2.z);
    }

    public double Dot(Cartesian n1, Vector n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double DotProduct(Cartesian n1, Vector n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double AbsDot(Cartesian n1, Vector n2) {
        return Math.abs(n1.x * n2.x + n1.y * n2.y + n1.z * n2.z);
    }

    public double Dot(Cartesian n1, Normal n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double DotProduct(Cartesian n1, Normal n2) {
        return n1.x * n2.x + n1.y * n2.y + n1.z * n2.z;
    }

    public double AbsDot(Cartesian n1, Normal n2) {
        return Math.abs(n1.x * n2.x + n1.y * n2.y + n1.z * n2.z);
    }

    public void Scale2D(double s) {
        x *= s;
        y *= s;
    }

    public void Scale(double s) {
        x *= s;
        y *= s;
        z *= s;
    }

    public Vector Scale(Cartesian a, double s) {
        return new Vector(a.x * s, a.y * s, a.z * s);
    }

    public Vector Project2D(Cartesian onto) {
        double dp = DotProduct2D(onto, onto);
        if (dp > 0) {
            double pd = DotProduct2D(this, onto);
            return onto.Scaled(pd / dp).AsVector();
        }
        return new Vector(onto.x, onto.y, onto.z);
    }

    public Vector Project(Cartesian onto) {
        double dp = DotProduct(onto, onto);
        if (dp > 0) {

            double pd = DotProduct(this, onto);
            return onto.Scaled(pd / dp).AsVector();
        }
        return new Vector(onto.x, onto.y, onto.z);
    }

    //
    public Vector Project2D(Cartesian p0, Cartesian onto) {
        double dp = DotProduct2D(onto, onto);
        if (dp > 0) {
            double pd = DotProduct2D(p0, onto);
            return onto.Scaled(pd / dp).AsVector();
        }
        return new Vector(onto.x, onto.y, onto.z);
    }

    public Vector Project(Cartesian p0, Cartesian onto) {
        double dp = DotProduct(onto, onto);
        if (dp > 0) {

            double pd = DotProduct(p0, onto);
            return onto.Scaled(pd / dp).AsVector();
        }
        return new Vector(onto.x, onto.y, onto.z);
    }

    public Cartesian Scaled(Cartesian v) {

        return new Vector(x * v.x, y * v.y, z * v.z);
    }

    public Cartesian Scaled(double v) {
        return new Cartesian(x * v, y * v, z * v);
    }

    public double DotProduct2D(Cartesian p0, Cartesian p1) {
        return (p0.x * p1.x + p0.y * p1.y);
    }

    public double DotProduct2DXY(Cartesian p0, Cartesian p1) {
        return (p0.x * p1.x + p0.y * p1.y);
    }

    public double DotProduct2DYZ(Cartesian p0, Cartesian p1) {
        return (p0.z * p1.z + p0.y * p1.y);
    }

    public double DotProduct2DZX(Cartesian p0, Cartesian p1) {
        return (p0.x * p1.x + p0.z * p1.z);
    }

    public double ScalarProduct(Cartesian p0, Cartesian p1) {
        return (p0.x * p1.x + p0.y * p1.y + p0.z * p1.z);
    }

    public Cartesian Cross(Cartesian v1, Cartesian v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian CrossProduct(Cartesian v1, Cartesian v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian VectorProduct(Cartesian v1, Cartesian v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian Cross(Cartesian v1, Normal v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian VectorProduct(Cartesian v1, Normal v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian CrossProduct(Cartesian v1, Normal v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian Cross(Cartesian v1, Vector v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian VectorProduct(Cartesian v1, Vector v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian CrossProduct(Cartesian v1, Vector v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian Cross(Cartesian v1, Point v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian VectorProduct(Cartesian v1, Point v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian CrossProduct(Cartesian v1, Point v2) {
        return new Cartesian((v1.y * v2.z) - (v1.z * v2.y), (v1.z * v2.x) - (v1.x * v2.z), (v1.x * v2.y) - (v1.y * v2.x));
    }

    public Cartesian Negative() {
        return new Cartesian(-x, -y, -z);
    }

    @Override
    public boolean To(StellarFiles f) {
        f.Add(x, y, z);
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
            return true;
            }
            return false;
        } catch (Exception ex) {
            Logger.getLogger(Cartesian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
