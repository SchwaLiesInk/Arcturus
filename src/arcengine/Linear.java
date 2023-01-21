/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerwyn Jones
 */
public class Linear implements FromFile,ToFile{

    static double colorTable[];
    static final double MAX_MAP = 9e16f;
    static final double E = 0.00001f;
    static final double Epsilon = 0.001f;
    static final double PI90 = (double) Math.PI * 0.5f;
    static final double PI180 = (double) Math.PI;
    static final double PI = (double) Math.PI;
    static final double PI2 = (double) Math.PI * 2;
    static final double PI4 = (double) Math.PI * 4;
    static final double PI8 = (double) Math.PI * 8;
    static final double PI4D3 = (double) Math.PI * 4.0/3.0;
    static final double PID2 = (double) Math.PI * 0.5;
    static final double PID4 = (double) Math.PI * 0.25;
    static final double InvPI2 = (double) (0.5 / Math.PI);
    static final double InvPI = (double) (1.0 / Math.PI);
    static final double PI270 = PI90 * 3.0f;
    static final double PISqrd = 9.8696044010893586188344909998762;

    static final byte PositionBetween = 0, PositionBeyond = 1, PositionOrigin = 2, PositionDestination = 3, PositionBehind = 4, PositionAbove = 5, PositionBelow = 6, PositionLeft = 7, PositionRight = 8;
    static final byte AxisX = 0, AxisY = 1, AxisZ = 2, AxisW = 3;
    static final byte SideLeft = 1, SideRight = 2, SideTop = 4, SideBottom = 8, SideFront = 16, SideBack = 32;
    public int x;
    public int y;
    public int z;

    static double Max3(double a, double b, double c) {
        if (a > b) {
            if (a > c) {
                return a;
            } else {
                return c;
            }
        } else if (b > c) {
            return b;
        } else {
            return c;
        }
    }

    static double Min3(double a, double b, double c) {
        if (a < b) {
            if (a < c) {
                return a;
            } else {
                return c;
            }
        } else if (b < c) {
            return b;
        } else {
            return c;
        }
    }

    static boolean Between2D(Cartesian minimum, Cartesian at, Cartesian maximum) {
        return (minimum.x <= at.x && at.x <= maximum.x) && (minimum.y <= at.y && at.y <= maximum.y);
    }

    static boolean Between3D(Cartesian minimum, Cartesian at, Cartesian maximum) {
        return (minimum.x <= at.x && at.x <= maximum.x) && (minimum.y <= at.y && at.y <= maximum.y) && (minimum.z <= at.z && at.z <= maximum.z);
    }

    static boolean Overlapping2D(Cartesian minimum, Cartesian maximum) {
        return (minimum.x <= maximum.x) && (minimum.y <= maximum.y);
    }

    static boolean Overlapping2D(Cartesian minimum, Cartesian maximum, double threshold) {
        return (minimum.x <= maximum.x + threshold) && (minimum.y <= maximum.y + threshold);
    }

    static boolean Overlapping3D(Cartesian minimum, Cartesian maximum) {
        return (minimum.x <= maximum.x) && (minimum.y <= maximum.y) && (minimum.z <= maximum.z);
    }

    static boolean Overlapping3D(Cartesian minimum, Cartesian maximum, double threshold) {
        return (minimum.x <= maximum.x + threshold) && (minimum.y <= maximum.y + threshold) && (minimum.z <= maximum.z + threshold);
    }

    static double Clamp(double v, double min, double max) {
        if (v < min) {
            v = min;
        } else if (v > max) {
            v = max;
        }
        return v;
    }

    static void Clamp(Cartesian c) {
        c.x = Clamp(c.x, 0, 1);
        c.y = Clamp(c.y, 0, 1);
        c.z = Clamp(c.z, 0, 1);
    }

    static double lerp(double t, double v1, double v2) {
        return (1.0f - t) * v1 + t * v2;
    }

    static Vector lerp(double t, Vector v1, Vector v2) {
        return new Vector(lerp(t, v1.x, v2.x), lerp(t, v1.y, v2.y), lerp(t, v1.z, v2.z));
    }

    static Point lerp(double t, Point v1, Point v2) {
        return new Point(lerp(t, v1.x, v2.x), lerp(t, v1.y, v2.y), lerp(t, v1.z, v2.z), lerp(t, v1.r, v2.r));
    }
    
    @Override
    public boolean To(StellarFiles f) {
        f.Add(x, y, z);
        return true;
    }

    @Override
    public boolean From(StellarFiles f) {
        
        try {
            Integer[] get=f.ReadIntArray();
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



class Matrix {

    double m[][] = new double[4][4];

    Matrix() {
        m[0][0] = 1;
        m[1][1] = 1;
        m[2][2] = 1;
        m[3][3] = 1;
    }

    Matrix(Vector t) {
        m[0][0] = 1;
        m[1][1] = 1;
        m[2][2] = 1;
        m[3][3] = 1;
        m[3][0] = t.x;
        m[3][1] = t.y;
        m[3][2] = t.z;
    }

    Matrix(Vector t, Vector s) {
        m[0][0] = s.x;
        m[1][1] = s.y;
        m[2][2] = s.z;
        m[3][3] = 1;
        m[3][0] = t.x;
        m[3][1] = t.y;
        m[3][2] = t.z;
    }

    Matrix RotateX(double a) {
        Matrix x = new Matrix();
        x.m[1][1] = Math.cos(a);
        x.m[1][2] = Math.sin(a);
        x.m[2][1] = -Math.sin(a);
        x.m[2][2] = Math.cos(a);
        return x;
    }

    Matrix RotateY(double a) {
        Matrix y = new Matrix();
        y.m[0][0] = Math.cos(a);
        y.m[2][0] = -Math.sin(a);
        y.m[0][2] = Math.sin(a);
        y.m[2][2] = Math.cos(a);
        return y;
    }

    Matrix RotateZ(double a) {

        Matrix z = new Matrix();
        z.m[0][0] = Math.cos(a);
        z.m[1][0] = Math.sin(a);
        z.m[0][1] = -Math.sin(a);
        z.m[1][1] = Math.cos(a);
        return z;
    }

    Matrix Multiply(Matrix by) {
        Matrix matrix = new Matrix();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                matrix.m[i][j] = m[i][0] * by.m[0][j] + m[i][1] * by.m[1][j] + m[i][2] * by.m[2][j] + m[i][3] * by.m[3][j];
            }
        }
        return matrix;
    }

    Matrix Mult(Matrix mult, Matrix by) {

        Matrix matrix = new Matrix();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                matrix.m[i][j] = mult.m[i][0] * by.m[0][j] + mult.m[i][1] * by.m[1][j] + mult.m[i][2] * by.m[2][j] + mult.m[i][3] * by.m[3][j];
            }
        }
        return matrix;
    }

    Vector Mult(Matrix mult, Vector by) {
        double x = by.x * mult.m[0][0] + by.y * mult.m[1][0] + by.z * mult.m[2][0] + by.w * mult.m[3][0];
        double y = by.x * mult.m[0][1] + by.y * mult.m[1][1] + by.z * mult.m[2][1] + by.w * mult.m[3][1];
        double z = by.x * mult.m[0][2] + by.y * mult.m[1][2] + by.z * mult.m[2][2] + by.w * mult.m[3][2];
        double w = by.x * mult.m[0][3] + by.y * mult.m[1][3] + by.z * mult.m[2][3] + by.w * mult.m[3][3];
        return new Vector(x, y, z, w);
    }
}

class Quarternion {

    double[] q = new double[4];

    Quarternion() {
        q[0] = 1;
        q[1] = 1;
        q[2] = 1;
        q[3] = 0;
    }

    Quarternion(Quarternion copy) {
        q[0] = copy.q[0];
        q[1] = copy.q[1];
        q[2] = copy.q[2];
        q[3] = copy.q[3];
    }

    Quarternion(double qw, double qx, double qy, double qz) {
        q[0] = qx;
        q[1] = qy;
        q[2] = qz;
        q[3] = qw;
    }

    Quarternion(Vector v, double qs) {
        q[0] = v.x;
        q[1] = v.y;
        q[2] = v.z;
        q[3] = qs;
    }

    Quarternion(Vector v) {
        q[0] = v.x;
        q[1] = v.y;
        q[2] = v.z;
        q[3] = 0;
    }

    Quarternion(Normal v) {
        q[0] = v.x;
        q[1] = v.y;
        q[2] = v.z;
        q[3] = 0;
    }

    Quarternion(Point v) {
        q[0] = v.x;
        q[1] = v.y;
        q[2] = v.z;
        q[3] = 0;
    }

    Quarternion(double a, Normal v) {
        double a2 = a * 0.5;
        double sa2 = Math.sin(a2);
        double ca2 = Math.cos(a2);
        q[0] = v.x * sa2;
        q[1] = v.y * sa2;
        q[2] = v.z * sa2;
        q[3] = ca2;
    }

    Quarternion(double a, Point v) {
        double a2 = a * 0.5;
        double sa2 = Math.sin(a2);
        double ca2 = Math.cos(a2);
        q[0] = v.x * sa2;
        q[1] = v.y * sa2;
        q[2] = v.z * sa2;
        q[3] = ca2;
    }

    Quarternion Mult(Quarternion a, Quarternion b) {
        Vector q = new Vector(a.q[0], a.q[1], a.q[2]);
        Vector p = new Vector(b.q[0], b.q[1], b.q[2]);
        Vector pqx = q.Scaled(b.q[3]);
        Vector pqy = p.Scaled(a.q[3]);
        Vector pqz = p.Cross(p, q).AsVector();
        Vector pq = pqx.Add(pqx, pqx.Add(pqy, pqz)).AsVector();
        double pqs = b.q[3] * a.q[3] - p.DotProduct(p, q);
        return new Quarternion(pqs, pq.x, pq.y, pq.z);
    }

    Quarternion Add(Quarternion a, Quarternion b) {
        return new Quarternion(a.q[0] + b.q[0], a.q[1] + b.q[1], a.q[2] + b.q[2], a.q[3] + b.q[3]);
    }

    Quarternion Sub(Quarternion a, Quarternion b) {
        return new Quarternion(a.q[0] - b.q[0], a.q[1] - b.q[1], a.q[2] - b.q[2], a.q[3] - b.q[3]);
    }

    Quarternion Scale(Quarternion a, double s) {
        Quarternion sc = new Quarternion(a);
        sc.q[0] *= s;
        sc.q[1] *= s;
        sc.q[2] *= s;
        sc.q[3] *= s;
        return sc;
    }

    Quarternion Scaled(double s) {
        Quarternion sc = new Quarternion(this);
        sc.q[0] *= s;
        sc.q[1] *= s;
        sc.q[2] *= s;
        sc.q[3] *= s;
        return sc;
    }

    Quarternion Perspect(Quarternion a, double d) {
        double s = 1.0 / d;
        Quarternion sc = new Quarternion(a);
        sc.q[0] *= s;
        sc.q[1] *= s;
        sc.q[2] *= s;
        sc.q[3] *= s;
        return sc;
    }

    Vector Rotate(Vector v) {
        Quarternion vq = new Quarternion(v);
        Quarternion p = this.Mult((this.Mult(this, vq)), InverseOfUnit());
        return new Vector(p.q[0], p.q[1], p.q[2], p.q[3]);
    }

    Normal Rotate(Normal v) {
        Quarternion vq = new Quarternion(v);
        Quarternion p = this.Mult((this.Mult(this, vq)), InverseOfUnit());
        return new Normal(p.q[0], p.q[1], p.q[2]);
    }

    Point Rotate(Point v) {
        Quarternion vq = new Quarternion(v);
        Quarternion p = this.Mult((this.Mult(this, vq)), InverseOfUnit());
        return new Point(p.q[0], p.q[1], p.q[2]);
    }

    void Normalize() {
        Quarternion c = Conjugate();
        double sq = 1.0 / Math.sqrt(q[0] * c.q[0] + q[1] * c.q[1] + q[2] * c.q[2] + q[3] * c.q[3]);
        q[0] *= sq;
        q[1] *= sq;
        q[2] *= sq;
        q[3] *= sq;
    }

    double MagnitudeSquared() {
        double sq = (q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        return sq;
    }

    double Magnitude() {
        double sq = Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        return sq;
    }

    double InvMagnitude() {
        double sq = 1.0 / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        return sq;
    }

    double ScaleMagnitude() {
        double sq = 2.0 / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        return sq;
    }

    double ScaleMagnitude(double s) {
        double sq = s / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        return sq;
    }

    Quarternion Normal() {
        Quarternion n = new Quarternion(this);
        n.Normalize();
        return n;
    }

    Quarternion Conjugate() {
        return new Quarternion(q[3], -q[0], -q[1], -q[2]);
    }

    Quarternion InverseOfUnit() {
        return new Quarternion(q[3], -q[0], -q[1], -q[2]);
    }

    Quarternion Inverse() {
        Quarternion qn = new Quarternion(this);
        qn.Normalize();
        double ns = 1.0 / this.MagnitudeSquared();
        return qn.Conjugate().Scaled(ns);
    }

    Quarternion Net(Quarternion sq, Quarternion[] qs) {
        Quarternion e = new Quarternion(sq);
        for (int i = 0; i < qs.length; i++) {
            Quarternion f = qs[i];
            e = e.Mult(e, f);
        }
        return e;
    }

    Quarternion LinearInterop(Quarternion a, Quarternion b, double t) {
        Quarternion ar = a.Scaled(1.0 - t);
        Quarternion br = b.Scaled(t);
        Quarternion r = (ar.Add(ar, br));
        r.Normalize();
        return r;
    }

    Quarternion SphereInterop(Quarternion a, Quarternion b, double t) {
        Point q = new Point(a.q[0], a.q[1], a.q[2]);
        Point p = new Point(b.q[0], b.q[1], b.q[2]);
        //
        double phi = Math.acos(p.DotProduct(q, p) * q.InvLength());
        double sp = Math.sin(phi);
        double wa = (Math.sin(1.0 - t) * phi) / sp;
        double wb = Math.sin(t * phi) / sp;
        Quarternion ar = a.Scaled(wa);
        Quarternion br = b.Scaled(wb);
        Quarternion r = ar.Add(ar, br);
        //r.Normalize();
        return r;
    }

    Matrix GetMatrix() {
        Matrix qm = new Matrix();
        qm.m[0][0] = 1.0 - 2.0 * (q[1] * q[1] - q[2] * q[2]);
        qm.m[1][0] = 2.0 * (q[0] * q[1] + q[2] * q[3]);
        qm.m[2][0] = 2.0 * (q[0] * q[2] - q[1] * q[3]);
        //
        qm.m[0][1] = 2.0 * (q[0] * q[1] - q[2] * q[3]);
        qm.m[1][1] = 1.0 - 2.0 * (q[0] * q[0] + q[2] * q[2]);
        qm.m[2][1] = 2.0 * (q[1] * q[2] + q[0] * q[3]);
        //
        qm.m[0][2] = 2.0 * (q[0] * q[2] + q[1] * q[3]);
        qm.m[1][2] = 2.0 * (q[1] * q[2] - q[0] * q[3]);
        qm.m[2][2] = 1.0 - 2.0 * (q[0] * q[0] - q[1] * q[1]);
        return qm;
    }

    Matrix GetMatrix(double scale) {
        Matrix qm = new Matrix();
        qm.m[0][0] = 1.0 - scale * (q[1] * q[1] - q[2] * q[2]);
        qm.m[1][0] = scale * (q[0] * q[1] + q[2] * q[3]);
        qm.m[2][0] = scale * (q[0] * q[2] - q[1] * q[3]);
        //
        qm.m[0][1] = scale * (q[0] * q[1] - q[2] * q[3]);
        qm.m[1][1] = 1.0 - scale * (q[0] * q[0] + q[2] * q[2]);
        qm.m[2][1] = scale * (q[1] * q[2] + q[0] * q[3]);
        //
        qm.m[0][2] = scale * (q[0] * q[2] + q[1] * q[3]);
        qm.m[1][2] = scale * (q[1] * q[2] - q[0] * q[3]);
        qm.m[2][2] = 1.0 - scale * (q[0] * q[0] - q[1] * q[1]);

        return qm;
    }

    Quarternion(Matrix m) {
        double trace = m.m[0][0] + m.m[1][1] + m.m[2][2];
        if (trace > 0) {
            double s = Math.sqrt(trace + 1.0);
            q[3] = s * 0.5;
            double t = 0.5 / s;
            q[0] = (m.m[2][1] - m.m[1][2]) * t;
            q[1] = (m.m[0][2] - m.m[2][0]) * t;
            q[2] = (m.m[1][0] - m.m[0][1]) * t;
        } else {
            int i = 0;
            if (m.m[1][1] > m.m[0][0]) {
                i = 1;
            }
            if (m.m[2][2] > m.m[i][i]) {
                i = 2;
            }
            int[] next = new int[]{1, 2, 0};
            int j = next[i];
            int k = next[j];

            double s = Math.sqrt((m.m[i][j] - (m.m[j][j] + m.m[k][k])) + 1.0);
            q[i] = s * 0.5;
            double t;
            if (s != 0) {
                t = 0.5 / s;
            } else {
                t = s;
            }
            q[3] = (m.m[k][j] - m.m[j][k]) * t;
            q[j] = (m.m[j][i] + m.m[i][j]) * t;
            q[k] = (m.m[k][i] + m.m[i][k]) * t;

        }
    }
}

class Anim//sqt
{

    Vector scale;
    Quarternion transform;
    Vector translate;
}

class AxisAngle//sqt
{

    Vector angles;
    Vector direction;
    Quarternion transform;
}

class Orientation {

    Vector angle;
    Normal direction;
    Point at;
    Vector scale;
    Vector translate;
    Quarternion transform;
}

class Dual {

    Quarternion a;
    Quarternion b;

    Quarternion GetValue() {
        return a.Add(a, b);
    }
}

class SphereTransform {

    Quarternion x;
    Quarternion y;
    Quarternion z;

    Quarternion GetTransform(Vector a) {
        x = new Quarternion(a.x, new Normal(1, 0, 0));
        y = new Quarternion(a.y, new Normal(0, 1, 0));
        z = new Quarternion(a.z, new Normal(0, 0, 1));
        return x.Mult(x, x.Mult(y, z));
    }

    Quarternion GetTransform(Vector a, SphereTransform to, double t) {

        x = new Quarternion(a.x, new Normal(1, 0, 0));
        y = new Quarternion(a.y, new Normal(0, 1, 0));
        z = new Quarternion(a.z, new Normal(0, 0, 1));
        return x.SphereInterop(x.Mult(x, x.Mult(y, z)), to.GetTransform(), t);
    }

    Quarternion GetTransform(Vector a, Vector b, SphereTransform to, double t) {

        x = new Quarternion(a.x, new Normal(1, 0, 0));
        y = new Quarternion(a.y, new Normal(0, 1, 0));
        z = new Quarternion(a.z, new Normal(0, 0, 1));
        return x.SphereInterop(x.Mult(x, x.Mult(y, z)), to.GetTransform(b), t);
    }

    Quarternion GetTransform(Vector a, Quarternion to, double t) {

        x = new Quarternion(a.x, new Normal(1, 0, 0));
        y = new Quarternion(a.y, new Normal(0, 1, 0));
        z = new Quarternion(a.z, new Normal(0, 0, 1));
        return x.SphereInterop(x.Mult(x, x.Mult(y, z)), to, t);
    }

    Quarternion GetTransform() {
        return x.Mult(x, x.Mult(y, z));
    }

}

class Transforms {

    Point scale;
    double rotate;
    Vector trans;

    Transforms(Point s, double r, Vector t) {
        scale = s;
        rotate = r;
        trans = t;
    }

    Transforms(Transforms t1, Transforms t2) {
        scale = new Point(t1.scale.x * t2.scale.x, t1.scale.y * t2.scale.y, t1.scale.z * t2.scale.z, t1.scale.r * t2.scale.r);
        rotate = t1.rotate + t2.rotate;
        trans = new Vector(t1.trans.x + t2.trans.x, t1.trans.y + t2.trans.y, t1.trans.z + t2.trans.z);
    }

    VertexLine Transform(VertexLine v) {

        double s = Math.sin(rotate);
        double c = Math.cos(rotate);
        double rx = v.x * c - v.y * s;
        double ry = v.x * s + v.y * c;
        Point f = new Point(trans.x + rx * scale.x, trans.y + ry * scale.y, trans.z + v.z * scale.z, v.r * scale.r);
        rx = v.to.x * c - v.to.y * s;
        ry = v.to.x * s + v.to.y * c;
        Point t = new Point(trans.x + rx * scale.x, trans.y + ry * scale.y, trans.z + v.to.z * scale.z, v.to.r * scale.r);
        return new VertexLine(f, t, v.colour);
    }

    VertexLine Reflect(VertexLine v) {

        double s = Math.sin(rotate);
        double c = Math.cos(rotate);
        double rx = v.x * c - v.y * s;
        double ry = v.x * s + v.y * c;
        Point f = new Point(trans.x - rx * scale.x, trans.y + ry * scale.y, trans.z + v.z * scale.z, v.r * scale.r);
        rx = v.to.x * c - v.to.y * s;
        ry = v.to.x * s + v.to.y * c;
        Point t = new Point(trans.x - rx * scale.x, trans.y + ry * scale.y, trans.z + v.to.z * scale.z, v.to.r * scale.r);
        return new VertexLine(f, t, v.colour);
    }
}

class VertexLine extends Point {

    Point to;
    Vector colour;

    VertexLine(Point at, Point to, Vector c) {
        x = at.x;
        y = at.y;
        z = at.z;
        r = at.r;
        this.to = to.AsPoint(to.r);
        colour = c.AsVector();
    }

    VertexLine Transform(Matrix m) {
        Vector f = this.AsVector();
        Vector t = to.AsVector();
        f = m.Mult(m, f);
        t = m.Mult(m, t);
        return new VertexLine(f.AsPoint(r), t.AsPoint(to.r), colour);
    }

    VertexLine Translate(Vector v) {
        Point f = this.Add(this, v).AsPoint(r);
        Point t = to.Add(to, v).AsPoint(to.r);
        return new VertexLine(f, to, colour);
    }

    VertexLine Scale(Vector s) {
        Point f = new Point(this.x * s.x, this.y * s.y, this.z * s.z, this.r);
        Point t = new Point(to.x * s.x, to.y * s.y, to.z * s.z, to.r);
        return new VertexLine(f, t, colour);
    }

    VertexLine Rotate(double a) {
        double s = Math.sin(a);
        double c = Math.cos(a);
        double rx = this.x * c - this.y * s;
        double ry = this.x * s + this.y * c;
        Point f = new Point(rx, ry, z, this.r);
        rx = to.x * c - to.y * s;
        ry = to.x * s + to.y * c;
        Point t = new Point(rx, ry, to.z, to.r);
        return new VertexLine(f, t, colour);
    }
}

enum Position {

    LEFT, RIGHT, ABOVE, BELOW, BEYOND, BEHIND, BETWEEN, ORIGIN, DESTINATION,

}

enum PointPosition {

    INSIDE, OUTSIDE, BOUNDARY,
}

enum Agonic {

    COLLINEAR, PARALLEL, AGONIC, AGONIC_CROSS, AGONIC_NO_CROSS,
}

enum EdgePosition {

    TOUCHING, CROSSING, INESSENTIAL,
}

enum PolyPosition {

    UNKNOWN, PINQ, QINP,
}

class Coefficient {

    protected float c;
    protected float inv;

    Coefficient(float c) {
        this.c = c;
        inv = 1.0f / c;
    }

    float Value() {
        return c;
    }

    float Inverse() {
        return inv;
    }
}

class Credits {

    protected float c;

    Credits(float credits) {
        c = credits;
    }
}

class Acceleration {

    protected Vector a;

    Acceleration(Velocity v, Time t) {
        a = v.Value().Multiply(1.0f / t.Value());
    }

    Acceleration(Vector accel) {
        a = accel;
    }

    Acceleration(Vector dir, Time t) {
        a = dir.Multiply(1.0f / (t.Value() * t.Value()));
    }

    Vector Value() {
        return a;
    }

    Velocity Velocity(Time t) {
        return new Velocity(a.Multiply(t.Value()));
    }
}

class AccelerationRate {

    protected float at;

    AccelerationRate(float rate) {
        at = rate;
    }

    AccelerationRate(Acceleration acc, Time rate) {
        at = (float) (acc.Value().Length() / rate.Value());
    }

    float Value() {
        return at;
    }
}

class Length {

    protected float r;

    Length(Point v) {
        r = (float) v.Length();
    }

    Length(Length l) {
        r = l.Value();

    }

    Length(float d) {
        r = d;

    }

    float Value() {
        return r;
    }
}

class Area {

    public static final Coefficient Sphere = new Coefficient((float) (4.0 * Math.PI));
    public static final Coefficient CircleOrCone = new Coefficient((float) (Math.PI));
    public static final Coefficient Cylinder = new Coefficient((float) (2.0 * Math.PI));
    public static final Coefficient Trapazoid = new Coefficient(0.5f);
    protected float r2;

    Area(float a2) {
        r2 = a2;
    }

    Area(Area a2) {
        r2 = a2.r2;
    }

    Area(float w, float b) {
        r2 = w * b;
    }

    Area(Length w, Length b) {
        r2 = w.Value() * b.Value();
    }

    float Value() {
        return r2;
    }
}

class Volume {

    public static final Coefficient Sphere = new Coefficient((float) (4.0f / 3.0f * Math.PI));
    public static final Coefficient Cone = new Coefficient((float) (1.0f / 3.0f * Math.PI));

    public static final Coefficient Cylinder = new Coefficient((float) (Math.PI));
    protected float r3;

    Volume(float r) {
        r3 = r * r * r;
    }

    Volume(float r, Coefficient co) {
        r3 = r * r * r * co.Value();
    }

    Volume(Length r) {
        r3 = r.Value() * r.Value() * r.Value();
    }

    Volume(Vector v) {
        float r = (float) v.Length();
        r3 = r * r * r;
    }

    Volume(Vector v, Coefficient co) {
        float r = (float) v.Length();
        r3 = r * r * r * co.Value();
    }

    Volume(Length r, Coefficient co) {
        r3 = r.Value() * r.Value() * r.Value() * co.Value();
    }

    Volume(Length r1, Length r2, Length r3) {
        this.r3 = r1.Value() * r2.Value() * r3.Value();
    }

    Volume(Length r1, Length r2, Length r3, Coefficient co) {
        this.r3 = r1.Value() * r2.Value() * r3.Value() * co.Value();
    }

    float Value() {
        return r3;
    }

    void Update(float r) {
        r3 = r * r * r;
    }

    void Update(float r, Coefficient co) {
        r3 = r * r * r * co.Value();
    }

    void Update(Length r) {
        r3 = r.Value() * r.Value() * r.Value();
    }

    void Update(Vector v) {
        float r = (float) v.Length();
        r3 = r * r * r;
    }

    void Update(Vector v, Coefficient co) {
        float r = (float) v.Length();
        r3 = r * r * r * co.Value();
    }

    void Update(Length r, Coefficient co) {
        r3 = r.Value() * r.Value() * r.Value() * co.Value();
    }

    void Update(Length r1, Length r2, Length r3) {
        this.r3 = r1.Value() * r2.Value() * r3.Value();
    }

    void Update(Length r1, Length r2, Length r3, Coefficient co) {
        this.r3 = r1.Value() * r2.Value() * r3.Value() * co.Value();
    }
}

class Density {

    protected float p;

    Density(Mass m, Volume v) {
        p = m.Value() / v.Value();
    }

    Density(Mass m, Length r) {
        p = m.Value() / (r.Value() * r.Value() * r.Value());
    }

    Density(Mass m, Length r1, Length r2, Length r3) {
        p = m.Value() / (r1.Value() * r2.Value() * r3.Value());
    }

    Density(Mass m, Length r1, Length r2, Length r3, Coefficient co) {
        p = m.Value() / (r1.Value() * r2.Value() * r3.Value() * co.Value());
    }

    Density(Mass m, Point r) {
        float r3 = (float) r.Length();
        p = m.Value() / (r3 * r3 * r3);
    }

    Density(Mass m, Length r, Coefficient co) {

        p = m.Value() / (r.Value() * r.Value() * r.Value() * co.Value());
    }

    Density(Mass m, Point r, Coefficient co) {
        float r3 = (float) r.Length() * co.Value();
        p = m.Value() / (r3 * r3 * r3);
    }

    float Value() {
        return p;
    }
}

class Speed {

    protected float s;

    Speed(float spd) {
        s = spd;
    }

    Speed(Vector spd) {
        s = (float) spd.Length();
    }

    Speed(Velocity spd) {
        s = (float) spd.Value().Length();
    }

    Speed(Velocity spd, Time t) {
        s = (float) spd.Value().Multiply(t.Value()).Length();
    }

    float Value() {
        return s;
    }
}

class Angle {

    protected SphericalCoord rad;

    float Value() {
        return (float) rad.phi;
    }

    float Magnitude() {
        return (float) rad.p;
    }
}

class AngularVelocity {

    protected SphericalCoord v;

    AngularVelocity(float angle) {
        v = new SphericalCoord(angle);
    }

    AngularVelocity(Velocity v) {
        this.v = new SphericalCoord(v.Value());
    }

    AngularVelocity(Vector v) {
        this.v = new SphericalCoord(v);
    }

    AngularVelocity(float vx, float vy) {
        this.v = new SphericalCoord(vx, vy);
    }

    SphericalCoord Value() {
        return v;
    }

    float Angle() {
        return (float) v.phi;
    }

    float Magnitude() {
        return (float) v.p;
    }
}

class AngularAcceleration {

    protected SphericalCoord a;

    AngularAcceleration(float angle) {
        a = new SphericalCoord(angle);
    }

    AngularAcceleration(Acceleration v) {
        this.a = new SphericalCoord(v.Value());
    }

    AngularAcceleration(Vector v) {
        this.a = new SphericalCoord(v);
    }

    AngularAcceleration(float vx, float vy) {
        this.a = new SphericalCoord(vx, vy);
    }

    SphericalCoord Value() {
        return a;
    }

    float Angle() {
        return (float) a.phi;
    }

    float Magnitude() {
        return (float) a.p;
    }
}

class Time {

    float t;

    Time() {
    }

    Time(float s) {
        t = s;
    }

    float Value() {
        return t;
    }
}

class AngularMomentum {

    protected Mass m;
    protected AngularVelocity av;
    protected SphericalCoord angle;

    AngularMomentum(Mass mass, AngularVelocity angv, float phi, float p) {
        m = mass;
        av = angv;
        angle = new SphericalCoord(phi, p, 0);
    }

    Point Vector() {
        return angle.At();
    }

    SphericalCoord Value() {
        return angle;
    }

    float Magnitude() {
        return (float) angle.p;
    }

    float Angle() {
        return (float) angle.phi;
    }

    void SetAngle(float a) {
        angle.phi = a;
    }
}

class Velocity {

    protected Vector v;

    Velocity(Vector vel) {
        v = vel;
    }

    Velocity(float disx, float disy, Time t) {
        v = new Vector(disx, disy).Multiply(1.0f / t.Value());
    }

    Velocity(Length disx, Length disy, Time t) {
        v = new Vector(disx.Value(), disy.Value()).Multiply(1.0f / t.Value());
    }

    Velocity(float vx, float vy) {
        v = new Vector(vx, vy);
    }

    Velocity(Vector dir, Time t) {
        v = dir.Multiply(1.0f / t.Value());
    }

    Vector Value() {
        return v;
    }

    Speed Magnitude() {
        return new Speed(v);
    }

    SphericalCoord Spherical() {
        return new SphericalCoord((float) v.x, (float) v.y);
    }

    AngularVelocity Angular() {
        return new AngularVelocity(v);
    }
}

class Energy {

    protected Vector work;
}

class Mass {

    protected float m;
    protected Point center;
    //
    protected MomentOfInertia inertia;

    Mass(Point centr, float mass, float radius) {
        center = centr;
        m = mass;
        inertia = new MomentOfInertia(this, radius * radius);
    }

    Mass(Point centr, float mass, Point to) {
        center = centr;
        m = mass;
        inertia = new MomentOfInertia(this, center.Subtract(to));
    }

    Mass(Point centr, float mass, float radius, Coefficient c) {
        center = centr;
        m = mass;
        inertia = new MomentOfInertia(this, radius * radius * c.Value());
    }

    Mass(Point centr, float mass, Point to, Coefficient c) {
        center = centr;
        m = mass;
        inertia = new MomentOfInertia(this, center.Subtract(to), c);
    }

    void SetMass(float mass) {
        this.m = mass;
    }

    void Update(Point centr, float radius) {
        center = centr;
        inertia.Update(radius * radius);
    }

    void Update(Point centr, Point to) {
        center = centr;
        inertia = new MomentOfInertia(this, center.Subtract(to));
    }

    void Update(Point centr, float radius, Coefficient c) {
        center = centr;
        inertia.Update(radius * radius * c.Value());
    }

    void Update(Point centr, Point to, Coefficient c) {
        center = centr;
        inertia.Update(center.Subtract(to), c);
    }

    float Value() {
        return m;
    }
}

class MomentOfInertia {

    public static final Coefficient Regtangle = new Coefficient(1.0f / 12.0f);
    public static final Coefficient SolidSphere = new Coefficient(2.0f / 5.0f);
    public static final Coefficient HollowSphere = new Coefficient(2.0f / 3.0f);
    protected Mass mass;
    protected float i;

    MomentOfInertia(Mass m, Length dFromCG) {
        mass = m;
        Area area = new Area(dFromCG, dFromCG);
        i = m.Value() * area.Value();
    }

    MomentOfInertia(Mass m, float r2) {
        mass = m;
        i = m.Value() * r2;
    }

    MomentOfInertia(Mass m, Vector FromCG) {
        mass = m;
        float l = (float) FromCG.Length();
        Area area = new Area(l * l);
        i = m.Value() * area.Value();
    }

    MomentOfInertia(Mass m, Vector FromCG, Coefficient co) {
        mass = m;
        float l = (float) FromCG.Length();
        Area area = new Area(l * l);
        i = m.Value() * area.Value() * co.Value();
    }

    void Update(Vector FromCG, Coefficient co) {
        float l = (float) FromCG.Length();
        Area area = new Area(l * l);
        i = mass.Value() * area.Value() * co.Value();
    }

    void Update(float r, Coefficient co) {
        Area area = new Area(r * r);
        i = mass.Value() * area.Value() * co.Value();
    }

    void Update(Length r, Coefficient co) {
        Area area = new Area(r, r);
        i = mass.Value() * area.Value() * co.Value();
    }

    void Update(Vector FromCG) {
        float l = (float) FromCG.Length();
        Area area = new Area(l * l);
        i = mass.Value() * area.Value();
    }

    void Update(float r) {
        Area area = new Area(r * r);
        i = mass.Value() * area.Value();
    }

    void Update(Length r) {
        Area area = new Area(r, r);
        i = mass.Value() * area.Value();
    }

    Moment Torque(AngularAcceleration aac) {
        float phi = aac.Angle() * i;
        float p = aac.Magnitude() * i;
        Moment m = new Moment(phi, p);
        return m;
    }

    AngularMomentum AngularMoment(AngularVelocity av) {
        float phi = av.Angle() * i;
        float p = av.Magnitude() * i;
        AngularMomentum m = new AngularMomentum(mass, av, phi, p);
        return m;
    }

    float Value() {
        return i;
    }
}

class Moment {

    protected SphericalCoord m;

    Moment(float phi, float p) {
        m = new SphericalCoord(phi, p, 0);
    }
}

class Momentum {

    protected Mass mass;
    protected Velocity vel;
    protected Vector g;

    Momentum(Mass m, Velocity v) {
        mass = m;
        vel = v;
        g = v.Value().Multiply(m.Value());
    }

    void Update() {
        g = vel.Value().Multiply(mass.Value());
    }

    void Update(Mass m, Velocity v) {
        mass = m;
        vel = v;
        g = v.Value().Multiply(m.Value());
    }

    void Update(Velocity v) {
        vel = v;
        g = v.Value().Multiply(mass.Value());
    }

    Vector Value() {
        return g;
    }
}

class Force {

    protected Vector f;

    Force(Mass m, Acceleration a) {
        f = a.Value().Multiply(m.Value());
    }

    Force(Mass m, Velocity v, Time t) {
        f = (v.Value().Multiply(m.Value() / t.Value()));
    }

    Force(Mass m, Velocity v1, Velocity v2, Time t) {
        Vector v = v1.Value().Subtract(v2.Value());
        f = (v.Multiply(m.Value() / (t.Value() * t.Value())));
    }

    Force(Mass m1, Mass m2, Velocity v1, Velocity v2, Time t) {
        Vector v = v1.Value().Subtract(v2.Value());
        float m = (m1.Value() + m2.Value()) * 0.5f;
        f = (v.Multiply(m / (t.Value() * t.Value())));
    }

    Vector Opposite() {
        return f.Negative();
    }

    float Magnitude() {
        return (float) f.Length();
    }

    Vector Perpendicular() {

        return new Vector(-f.y, f.x);
    }

    SphericalCoord Spherical() {
        return new SphericalCoord((float) f.x, (float) f.y);
    }
}

class Frequancy {

    float z;

    Frequancy(Time t) {
        z = 1.0f / t.Value();
    }
}
/*
 class Vector2D extends Vector {

 Vector2D(float xx, float yy) {
 x = xx;
 y = yy;
 }

 Vector2D(double xx, double yy) {
 x = xx;
 y = yy;
 }

 static boolean Equal(Vector2D a, Vector2D b) {
 return a.x == b.x && a.y == b.y;
 }

 static boolean NotEqual(Vector2D a, Vector2D b) {
 return a.x != b.x || a.y != b.y;
 }

 static boolean LessThan(Vector2D a, Vector2D p) {
 return (a.x < p.x) || (a.x == p.x && a.y < p.y);
 }

 static boolean GreaterThan(Vector2D a, Vector2D p) {
 return (a.x > p.x) || (a.x == p.x && a.y > p.y);
 }

 static Vector2D Add(Vector2D p, Vector2D q) {
 return new Vector2D(p.x + q.x, p.y + q.y);
 }

 static Vector2D Subtract(Vector2D p, Vector2D q) {
 return new Vector2D(p.x - q.x, p.y - q.y);
 }

 static Vector2D Multiply(Vector2D p, float s) {
 return new Vector2D((float) (p.x * s), (float) (p.y * s));
 }

 static float DotProduct(Vector2D p0, Vector2D p1) {
 return (float) (p0.x * p1.x + p0.y * p1.y);
 }

 boolean Collision(float r1, Vector2D p, float r2) {
 Vector2D a = this.Subtract(p);
 float r3 = r1 * r1 + r2 * r2;
 if (a.Length2() < r3) {
 return true;
 }
 return false;
 }

 Position ClassifyPosition(Vector2D p0, Vector2D p1) {
 Vector2D p2 = new Vector2D(this.x, this.y);
 Vector2D a = p1.Subtract(p0);
 Vector2D b = p2.Subtract(p0);
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
 if (a.Length2() < b.Length2()) {
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

 double PolarAngle() {
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

 boolean Equals(Vector2D b) {
 return x == b.x && y == b.y;
 }

 Vector2D Perpendicular() {

 return new Vector2D(-y, x);
 }

 static int Orientation2D(Vector2D p0, Vector2D p1, Vector2D p2) {
 Vector2D a = p1.Subtract(p0);
 Vector2D b = p2.Subtract(p0);
 double sa = a.x * b.y - a.y * b.x;
 if (sa > 0) {
 return 1;
 }
 if (sa < 0) {

 return -1;
 }
 return 0;
 }

 int Orientation2D(Vector2D p1, Vector2D p2) {
 Vector2D a = p1.Subtract(this);
 Vector2D b = p2.Subtract(this);
 double sa = a.x * b.y - a.y * b.x;
 if (sa > 0) {
 return 1;
 }
 if (sa < 0) {

 return -1;
 }
 return 0;
 }

 boolean Equals(Vector2D p, float r) {
 Vector2D a = this.Subtract(p);
 float r2 = r * r;
 if (a.Length2() < r2) {
 return true;
 }
 return false;
 }

 boolean DoesNotEqual(Vector2D b) {
 return x != b.x || y != b.y;
 }

 boolean IsLessThan(Vector2D p) {
 return (this.x < p.x) || (this.x == p.x && this.y < p.y);
 }

 boolean IsGreaterThan(Vector2D p) {
 return (this.x > p.x) || (this.x == p.x && this.y > p.y);
 }

 void Middle(Vector2D q) {
 this.x = (this.x + q.x) * 0.5f;
 this.y = (this.y + q.y) * 0.5f;

 }

 void Plus(Vector2D q) {
 this.x = this.x + q.x;
 this.y = this.y + q.y;

 }

 void Minus(Vector2D q) {
 this.x = this.x - q.x;
 this.y = this.y - q.y;

 }

 void Scale(Vector2D q) {
 this.x = this.x * q.x;
 this.y = this.y * q.y;

 }

 void Scale(float s) {
 this.x = this.x * s;
 this.y = this.y * s;

 }

 Vector2D Add(Vector2D q) {
 double xx = this.x + q.x;
 double yy = this.y + q.y;
 return new Vector2D(xx, yy);
 }

 Vector2D Subtract(Vector2D q) {
 double xx = x - q.x;
 double yy = y - q.y;
 return new Vector2D(xx, yy);
 }

 Vector2D Multiply(float s) {
 float xx = (float) (x * s);
 float yy = (float) (y * s);
 return new Vector2D(xx, yy);
 }

 Vector2D Negative() {
 double xx = -x;
 double yy = -y;
 return new Vector2D(xx, yy);
 }

 Vector2D CrossProduct(Vector2D vect2) {
 double xy = x * vect2.y;
 double yx = y * vect2.x;
 return new Vector2D(xy - yx, yx - xy);
 }

 Vector2D Midpoint(Vector2D from) {
 double xp = x + from.x;
 double yp = y + from.y;
 return new Vector2D(xp * 0.5f, yp * 0.5f);
 }

 float Distance(Vector2D from) {
 double xp = x - from.x;
 double yp = y - from.y;
 return (float) Math.sqrt(xp * xp + yp * yp);
 }

 float Distance2(Vector2D from) {
 double xp = x - from.x;
 double yp = y - from.y;
 return (float) (xp * xp + yp * yp);
 }

 float Length2() {
 return (float) (x * x + y * y);
 }

 float Sine() {
 return (float) (y / Length());
 }

 float Cosine() {
 return (float) (x / Length());
 }

 float Tan() {
 return (float) (y / x);
 }

 float Cotan() {
 return (float) (x / y);
 }

 float Sec() {
 return (float) (Length() / x);
 }

 float Cosec() {
 return (float) (Length() / y);
 }

 Vector2D GetNormal2D() {
 double im = 1.0f / Length();
 return new Vector2D(x * im, y * im);
 }

 static Vector2D CrossProduct(Vector2D vect1, Vector2D vect2) {
 double xy = vect1.x * vect2.y;
 double yx = vect1.y * vect2.x;
 return new Vector2D(xy - yx, yx - xy);
 }

 static int PointTest(Vector2D p, Vector2D q) {
 if (p.IsLessThan(q)) {
 return -1;
 }
 if (p.IsGreaterThan(q)) {
 return 1;
 }
 return 0;
 }

 int PointTest(Vector2D q) {
 if (IsLessThan(q)) {
 return -1;
 }
 if (IsGreaterThan(q)) {
 return 1;
 }
 return 0;
 }
 static int pointAreaWidth = 64;

 static int PointAreaTest(Vector2D p, Vector2D q) {
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

 int PointAreaTest(Vector2D q) {
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

 static int LeftToRightCompare(Vector2D a, Vector2D b) {
 if (a.IsLessThan(b)) {
 return -1;
 }
 if (a.IsGreaterThan(b)) {
 return 1;
 }
 return 0;
 }

 static int RightToLeftCompare(Vector2D a, Vector2D b) {
 if (b.IsLessThan(a)) {
 return -1;
 }
 if (b.IsGreaterThan(a)) {
 return 1;
 }
 return 0;
 }

 static int PolarCompare(Vector2D p, Vector2D q, Vector2D original) {
 Vector2D vp = p.Subtract(original);
 Vector2D vq = q.Subtract(original);
 float pa = (float) vp.PolarAngle();
 float qa = (float) vq.PolarAngle();
 if (pa < qa) {
 return -1;
 }
 if (pa > qa) {
 return 1;
 }
 float vpl = vp.Length2();
 float vql = vq.Length2();
 if (vpl < vql) {
 return -1;
 }
 if (vpl > vql) {
 return 1;
 }
 return 0;
 }

 static int ClosestPoint(Vector2D p, Vector2D q, Vector2D to) {
 Vector2D vp = p.Subtract(to);
 Vector2D vq = q.Subtract(to);
 float pd = vp.Length2();
 float qd = vq.Length2();
 if (pd < qd) {
 return -1;
 }
 if (pd > qd) {
 return 1;
 }
 return 0;
 }
 }*/

class Box2D {

    // BBox Public Data
    Vector pMin;
    Vector pMax;

    // BBox Public Methods
    Box2D() {
        pMin = new Vector(Float.MAX_VALUE, Float.MAX_VALUE);
        pMax = new Vector(Float.MIN_VALUE, Float.MIN_VALUE);
    }

    Box2D(Vector p) {
        pMax = new Vector(p.x, p.y);
        pMin = new Vector(p.x, p.y);

    }

    Box2D(Vector p1, Vector p2) {
        pMin = new Vector(Math.min(p1.x, p2.x),
                Math.min(p1.y, p2.y));
        pMax = new Vector(Math.max(p1.x, p2.x),
                Math.max(p1.y, p2.y));
    }

    boolean Overlaps(Box2D b) {
        boolean x = (pMax.x >= b.pMin.x) && (pMin.x <= b.pMax.x);
        boolean y = (pMax.y >= b.pMin.y) && (pMin.y <= b.pMax.y);
        return (x && y);
    }

    boolean Inside(Vector pt) {
        return (pt.x >= pMin.x && pt.x <= pMax.x
                && pt.y >= pMin.y && pt.y <= pMax.y);
    }

    void Expand(float delta) {
        pMin.Subtract(new Vector(delta, delta));
        pMax.Add(new Vector(delta, delta));
    }

    float Area() {
        Vector d = pMax.Subtract(pMin);
        return (float) (d.x * d.y);
    }

    //x=0 or y=1
    int MaximumExtent() {
        Vector diag = pMax.Subtract(pMin);
        if (diag.x > diag.y) {
            return 0;
        } else {
            return 1;
        }
    }
}

class Vertex2D extends Vector implements Drawable, Iterator<Vertex2D> {

    protected Vertex2D next;
    protected Vertex2D prev;
    DrawType draws = DrawType.Point;
    DrawType subDraws = DrawType.Point;
    DrawChain chains;

    boolean sided = false;
    int graphicsIndex;
    Pigment color;

    Vertex2D(double xx, double yy, int index) {
        super(xx, yy);
        graphicsIndex = index;
        chains = this;
    }

    Vertex2D(double xx, double yy, double r, int index) {
        super(xx, yy);
        w = r;
        chains = this;
        graphicsIndex = index;
    }

    Vertex2D(double xx, double yy, double zz, double r, int index) {
        super(xx, yy);
        z = zz;
        w = r;
        chains = this;
        graphicsIndex = index;
    }

    Vertex2D(Vector p, int index) {
        super(p.x, p.y, p.z, p.w);

        graphicsIndex = index;
    }

    Vertex2D Insert(Vertex2D b) {
        Vertex2D c = next;
        b.next = c;
        b.prev = this;
        next = b;
        c.prev = b;
        return b;
    }

    Vertex2D Remove() {
        prev.next = next;
        next.prev = prev;
        next = prev = this;
        return this;
    }

    void Splice(Vertex2D b) {
        Vertex2D an = next;
        Vertex2D bn = b.next;
        next = bn;
        b.next = an;
        an.prev = b;
        bn.prev = this;
    }

    void Count(Integer start) {
        start++;
        if (next != null) {
            next.Count(start);
        }
    }

    int Count() {
        Integer start;
        start = (0);
        return start;
    }

    Vertex2D ClockWise() {
        return next;
    }

    Vertex2D CounterClockWise() {
        return prev;
    }

    Vertex2D Neighbor(int rotation) {
        if (rotation == 1) {
            return ClockWise();
        }
        return CounterClockWise();
    }

    Vertex2D Spinward(int rotation) {
        if (rotation > 0 && next != null) {
            rotation--;
            return next.Spinward(rotation);
        } else if (rotation < 0 && prev != null) {
            rotation++;
            return prev.Spinward(rotation);
        } else {
            return this;
        }
    }

    Vector At() {
        return new Vector(x, y);
    }

    Vertex2D Split(Vertex2D b) {
        Vertex2D bp = b.CounterClockWise().Insert(new Vertex2D(b.At(), b.graphicsIndex));
        Insert(new Vertex2D(At(), graphicsIndex));
        Splice(bp);
        return bp;
    }

    boolean Adjacent(Vertex2D w) {
        return w == next || w == prev;
    }

    static boolean Adjacent(Vertex2D v, Vertex2D w) {
        return w == v.next || w == v.prev;
    }

    static boolean DirectPointer(Vertex2D vu, Vertex2D vl, Vertex2D vx) {
        Vertex2D vup = vu.next;
        Vertex2D vlow = vl.prev;
        Vector pu = vup.At();//upper
        Vector pl = vlow.At();//lower
        if (pu.IsLessThan(pl)) {
            vx = vu = vup;
            return true;//upper
        } else {
            vx = vl = vlow;
            return false;//lower
        }
    }

    void SetNext(Vertex2D vx) {
        next = vx;
    }

    void SetPrev(Vertex2D vx) {
        prev = vx;
    }

    @Override
    public void SetDrawing(Point center, DrawType typ, DrawType subtyp, DrawChain chain, Pigment col, int index) {
        //this.x = center.x;
        //this.y = center.y;
        //this.z = center.z;
        //this.w = center.r;
        draws = typ;
        subDraws = subtyp;
        chains = chain;
        color = col;
        graphicsIndex = index;
    }

    @Override
    public String Name() {
        return "Vertex2D";
    }

    @Override
    public Quarternion Rotation() {
        return null;
    }

    @Override
    public DrawType Draws() {
        return draws;
    }

    @Override
    public DrawType SubDraws() {
        return draws;
    }

    @Override
    public int GetGraphicsIndex() {
        return graphicsIndex;
    }

    @Override
    public Drawable Drawing(int p) {
        return Spinward(p);
    }

    @Override
    public DrawChain Drawings() {
        return chains;
    }

    @Override
    public boolean DoubleSided() {
        return sided;
    }

    @Override
    public Pigment Colour() {
        return color;
    }

    @Override
    public int Points() {
        return Count();
    }

    @Override
    public Vector GetVector(int i) {
        return Spinward(i);
    }

    @Override
    public Normal Norm(int n) {

        return Spinward(n).Unity().AsNormal();
    }

    @Override
    public Point Center() {
        return this.AsPoint(w);
    }

    @Override
    public Drawable Next() {
        return ClockWise();
    }

    @Override
    public Drawable Prev() {
        return CounterClockWise();
    }

    @Override
    public Graphical Graphic() {
        return ArcEngine.DefaultGraphical;
    }

    @Override
    public void Draw(Drawable chain, Graphical g) {
        DrawType t = chain.Draws();
        Drawable d1 = chain;
        if (d1 instanceof Vertex2D) {
            while (d1 != null) {
                switch (t) {
                    case Point: {
                        //System.out.println("Missile");
                        g.DrawDot((Vertex2D) d1, w, color, d1.SubDraws(), graphicsIndex);
                        
                        break;
                    }
                    case Circle: {
                        Vertex2D v = (Vertex2D) d1;
                        g.DrawCircle(v, v.w, v.color, d1.SubDraws(), graphicsIndex);

                        break;
                    }
                    case Text: {
                        g.DrawText((Vertex2D) d1, d1.Name(), 8, color, d1.SubDraws(), graphicsIndex);

                        break;
                    }
                    case Line: {
                        Drawable d2 = d1.Next();
                        if (d2 != null) {
                            g.DrawLine((Vertex2D) d1, (Vertex2D) d2, w, color, d1.SubDraws(), graphicsIndex);
                        }
                        d1 = d2;
                        break;
                    }
                    case Triangle: {
                        Drawable d2 = d1.Next();
                        Drawable d3 = d2.Next();
                        if (d2 != null && d3 != null) {
                            Vertex2D v1 = (Vertex2D) d1;
                            Vertex2D v2 = (Vertex2D) d2;
                            Vertex2D v3 = (Vertex2D) d3;
                            //System.out.println("VERT1 "+v1.x+" "+v1.y+" "+v1.z);
                            //System.out.println("VERT2 "+v2.x+" "+v2.y+" "+v2.z);
                            //System.out.println("VERT3 "+v3.x+" "+v3.y+" "+v3.z);
                            g.DrawTriangle(v1, v2, v3, w, color, d1.SubDraws(), graphicsIndex);
                        }
                        d1 = d3;
                        break;
                    }
                    case Quad: {
                        Drawable d2 = d1.Next();
                        Drawable d3 = d2.Next();
                        Drawable d4 = d3.Next();
                        if (d2 != null && d3 != null && d4 != null) {
                            g.DrawQuad((Vertex2D) d1, (Vertex2D) d2, (Vertex2D) d3, (Vertex2D) d4, w, color, d1.SubDraws(), graphicsIndex);
                        }
                        d1 = d4;
                        break;
                    }
                    case Voxel: {
                        Drawable d2 = d1.Next();
                        Drawable d3 = d2.Next();
                        Drawable d4 = d3.Next();
                        if (d2 != null && d3 != null && d4 != null) {
                            g.DrawQuad((Vertex2D) d1, (Vertex2D) d2, (Vertex2D) d3, (Vertex2D) d4, w, color, d1.SubDraws(), graphicsIndex);
                        }
                        d1 = d4;
                        break;
                    }
                }
                d1 = d1.Next();
            }
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Vertex2D next() {
        return next;
    }
}

class Edge {

    Vector origin;
    Vector destination;
    boolean mark;

    Edge(Vector from, Vector to) {
        origin = new Vector(from.x, from.y);
        destination = new Vector(to.x, to.y);
        mark = false;
    }

    Edge() {
        origin = new Vector(0, 0);
        destination = new Vector(1, 0);
        mark = false;
    }

    Edge Rotate() {
        Vector m = Vector.Add(origin, destination).Multiply(0.5f);
        Vector v = Vector.Subtract(destination, origin);
        Vector n = new Vector(v.y, -v.x);
        Vector s = n.Multiply(0.5f);
        origin = m.Subtract(s);
        destination = m.Added(s);
        return this;
    }

    Edge Rotated() {
        Vector m = Vector.Add(origin, destination).Multiply(0.5f);
        Vector v = Vector.Subtract(destination, origin);
        Vector n = new Vector(v.y, -v.x);
        Vector s = n.Multiply(0.5f);
        Vector o = m.Subtract(s);
        Vector d = m.Added(s);
        return new Edge(o, d);
    }

    Edge Flip() {
        return Rotate().Rotate();
    }

    Edge Flipped() {
        return Rotated().Rotated();
    }

    Vector At(float t) {
        Vector d = (origin.Added(destination.Subtract(origin))).Multiply(t);
        return d;
    }

    EdgePosition PositionType(Vector from) {
        Vector v = new Vector(origin.x, origin.y);
        Vector w = new Vector(destination.x, destination.y);

        switch (from.ClassifyPosition2D(v, w)) {
            case LEFT: {
                return ((v.y < from.y) && (from.y <= w.x)) ? EdgePosition.CROSSING : EdgePosition.INESSENTIAL;
            }
            case RIGHT: {
                return ((w.y < from.y) && (from.y <= v.y)) ? EdgePosition.CROSSING : EdgePosition.INESSENTIAL;
            }
            case BETWEEN: {
                return EdgePosition.TOUCHING;
            }
            case ORIGIN: {
                return EdgePosition.TOUCHING;
            }
            case DESTINATION: {
                return EdgePosition.TOUCHING;
            }
            default: {
                return EdgePosition.INESSENTIAL;
            }
        }
    }

    double SignedAngle(Vector to) {
        Vector v = origin.Subtract(to);
        Vector w = destination.Subtract(to);
        double va = v.PolarAngle();
        double wa = w.PolarAngle();
        if (va == -1 || wa == -1) {
            return Math.PI;
        }
        double x = wa - va;
        if (x == Math.PI || x == -Math.PI) {
            return Math.PI;
        } else if (x < -Math.PI) {
            return x + Math.PI + Math.PI;
        } else if (x > Math.PI) {
            return x - Math.PI + Math.PI;
        } else {
            return x;
        }
    }

    static float DotProduct(Vector p0, Vector p1) {
        return (float) (p0.x * p1.x + p0.y * p1.y);
    }

    boolean AimsAt(Edge b, Position thisClass, Agonic crossType) {
        Vector va = destination.Subtract(origin);
        Vector vb = b.destination.Subtract(b.origin);
        if (crossType != Agonic.COLLINEAR) {

            if ((va.x * vb.y) >= (vb.x * va.y)) {
                return thisClass != Position.RIGHT;
            } else {
                return thisClass != Position.LEFT;
            }
        } else {
            return thisClass != Position.BEYOND;
        }
    }

    Agonic CrossingPoint(Edge edge, Vector p) {
        Time s = new Time();
        Time t = new Time();
        Agonic cls = Intersect(edge, s);
        if (cls == Agonic.COLLINEAR || cls == Agonic.PARALLEL) {
            return cls;
        }
        double len = destination.Subtract(origin).Length();
        if (s.t < -0.001f * len || s.t > 1.001f * len) {
            return Agonic.AGONIC_NO_CROSS;
        }
        edge.Intersect(this, t);
        double elen = (edge.destination.Subtract(edge.origin)).Length();
        if ((-0.001f * elen <= t.t) && (t.t <= 1.001f * elen)) {
            if (t.t <= 0.001f * elen) {
                p = edge.origin;
            } else if (t.t >= 0.001f * elen) {
                p = edge.origin;
            } else if (t.t <= 1.0f - 0.001f * elen) {
                p = edge.destination;
            } else if (s.t <= 0.001f * len) {
                p = origin;
            } else if (s.t >= 1.0f - 0.001f * len) {
                p = destination;
            } else {
                return Agonic.AGONIC_CROSS;
            }
        }
        return Agonic.AGONIC_NO_CROSS;
    }

    Agonic Intersect(Edge ed, Time t) {
        Vector a = new Vector(origin.x, origin.y);
        Vector b = new Vector(destination.x, destination.y);
        Vector c = new Vector(ed.origin.x, ed.origin.y);
        Vector d = new Vector(ed.destination.x, ed.destination.y);
        Vector n = new Vector((d.Subtract(c)).y, (c.Subtract(d)).x);
        float den = DotProduct(n, b.Subtract(a));
        if (den == 0) {
            Position ac = origin.ClassifyPosition2D(ed.origin, ed.destination);
            if (ac == Position.LEFT || ac == Position.RIGHT) {
                return Agonic.PARALLEL;

            } else {
                return Agonic.COLLINEAR;
            }
        }
        float num = DotProduct(n, a.Subtract(c));
        t.t = -num / den;
        return Agonic.AGONIC;
    }

    Agonic Cross(Edge ed, Time t) {
        Time s = new Time();
        Agonic ct = ed.Intersect(this, s);
        if (ct == Agonic.COLLINEAR || ct == Agonic.PARALLEL) {
            return ct;
        }

        if (s.t < 0 || s.t > 1) {
            return Agonic.AGONIC_NO_CROSS;
        }
        Intersect(ed, t);
        if (t.t >= 0 || t.t <= 1) {
            return Agonic.AGONIC_CROSS;
        }
        return Agonic.AGONIC_NO_CROSS;
    }

    boolean Mate(Vector s[], int n, Vector p) {
        Vector bp = null;
        Time t = new Time();

        float best = 9e16f;
        Edge f = Rotate();
        for (int i = 0; i < n; i++) {
            if (s[i].ClassifyPosition2D(origin, destination) == Position.RIGHT) {
                Edge g = new Edge(destination, s[i]);
                g.Rotate();
                f.Intersect(g, t);
                if (t.t < best) {
                    bp = s[i];
                    best = t.t;
                }
            }
        }
        if (bp != null) {
            p = bp;
            return true;
        }
        return false;
    }

    Time Distance(Vector p) {
        Edge ab = new Edge(this.origin, this.destination);
        ab = ab.Flip().Rotate();
        Vector n = (ab.destination.Subtract(ab.origin));
        n = n.Multiply((float) (1.0f / n.Length()));
        Edge f = new Edge(p, n.Added(p));
        Time t = new Time();
        f.Intersect(this, t);
        return t;
    }

    boolean IsVertical() {
        return origin.x == destination.x;
    }

    float Slope() {
        if (origin.x != destination.x) {
            return (float) ((destination.y - origin.y) / (destination.x - origin.x));
        } else {
            return Float.MAX_VALUE;
        }
    }

    float y(float x) {
        return (float) (Slope() * (x - origin.x) + origin.y);
    }

    static void Swap(Vector a, Vector b) {
        Vector c = a;
        a = b;
        b = c;
    }

    static Edge HullEdge(Vector s[]) {
        int m = 0;
        int i = 1;
        for (i = 1; i < s.length; i++) {
            if (s[i].IsLessThan(s[m])) {
                m = i;
            }
        }
        Swap(s[0], s[m]);

        m = 1;
        for (i = 2; i < s.length; i++) {
            Position c = s[i].ClassifyPosition2D(s[0], s[m]);
            if (c == Position.LEFT || c == Position.BETWEEN) {
                m = i;
            }
        }
        return new Edge(s[0], s[m]);
    }

    static int EdgeCompare(Edge a, Edge b) {
        if (a.origin.IsLessThan(b.origin)) {
            return -1;
        }
        if (a.origin.IsGreaterThan(b.origin)) {
            return 1;
        }
        if (a.destination.IsLessThan(b.destination)) {
            return -1;
        }
        if (a.destination.IsGreaterThan(b.destination)) {
            return 1;
        }
        return 0;
    }
}

class Polygon2D implements java.util.Collection<Vertex2D> {

    protected int size;
    protected Vertex2D v;

    protected void Resize() {
        if (v == null) {

            size = 0;
        } else {
            Vertex2D vert = v.ClockWise();
            for (size = 1; vert != v; ++size, v = v.ClockWise());
        }
    }

    Polygon2D(Polygon2D p) {
        size = p.size;
        if (size == 0) {
            v = null;

        } else {

            v = new Vertex2D(p.v.At(), p.v.graphicsIndex);
            for (int i = 1; i < size; i++) {
                p.v.Neighbor(1);
                v = v.Insert(new Vertex2D(p.v.At(), p.v.graphicsIndex));
            }
            p.v.Neighbor(1);
            v = v.ClockWise();
        }
    }

    Polygon2D(Vertex2D verts) {
        v = verts;
        Resize();
    }

    Polygon2D() {
        v = null;
        size = 0;
    }

    Vertex2D Verts() {
        return v;
    }

    int Size() {
        return size;
    }

    Vector At() {
        return v.At();
    }

    Edge Side() {
        return new Edge(At(), v.next.At());
    }

    Vertex2D ClockWise() {
        return v.ClockWise();
    }

    Vertex2D CounterClockWise() {
        return v.CounterClockWise();
    }

    Vertex2D Neighbor(int rotation) {
        return v.Neighbor(rotation);
    }

    Vertex2D Spinward(int rotation) {
        return v.Spinward(rotation);
    }

    Vertex2D Advance(int rotation) {
        return v = v.Neighbor(rotation);
    }

    void Advance(Polygon2D r, boolean in) {
        Advance(1);
        if (in) {
            Vector pr = r.v.At();
            Vector pv = v.At();
            if (pr != pv) {
                r.Insert(pv);
            }
        }
    }

    Vertex2D Set(Vertex2D verts) {
        return v = verts;
    }

    Vertex2D Insert(Vector p) {
        if (size++ == 0) {
            v = new Vertex2D(p, 0);
        } else {
            v.Insert(new Vertex2D(p, 0));
        }
        return v;
    }

    void Remove() {
        Vertex2D vert = v;
        v = (--size == 0) ? null : v.CounterClockWise();
        Vertex2D r = vert.Remove();
        r = null;
    }

    Vertex2D Split(Vertex2D b) {
        Vertex2D bp = v.Split(b);
        Resize();
        return new Vertex2D(bp, b.graphicsIndex);
    }

    boolean InsidePolygon(Vector s) {
        if (size == 1) {
            Vector a = At();
            return s.Equals(a);
        }
        if (size == 2) {
            Edge e = Side();
            Position c = s.ClassifyPosition2D(e.origin, e.destination);
            return (c == Position.BETWEEN || c == Position.ORIGIN || c == Position.DESTINATION);
        }
        Vertex2D org = v;
        for (int i = 0; i < size; i++, Advance(1)) {
            Edge side = Side();
            if (s.ClassifyPosition2D(side.origin, side.destination) == Position.LEFT) {
                v = org;
                return false;
            }
        }
        return true;
    }

    PointPosition PositionInPolygon2D(Vector s) {
        int parity = 0;
        for (int i = 0; i < size; i++, Advance(1)) {
            Edge edge = Side();
            switch (edge.PositionType(s)) {
                case TOUCHING: {
                    return PointPosition.BOUNDARY;
                }
                case CROSSING: {
                    parity = 1 - parity;
                }
            }

        }
        return parity == 1 ? PointPosition.INSIDE : PointPosition.OUTSIDE;
    }

    PointPosition PositionInsidePolygon2D(Vector s) {
        float total = 0;
        for (int i = 0; i < size; i++, Advance(1)) {
            Edge edge = Side();
            float x = (float) edge.SignedAngle(s);
            if (x == Math.PI) {
                return PointPosition.BOUNDARY;
            }
            total += x;
        }
        return total < -Math.PI ? PointPosition.INSIDE : PointPosition.OUTSIDE;
    }

    void SupportingLine(Vector s, Position side) {
        int rot = (side == Position.LEFT) ? 1 : -1;
        Vertex2D a = v;
        Vertex2D b = Neighbor(rot);
        Position ac = b.ClassifyPosition2D(s, a);
        while (ac == side || ac == Position.BEYOND || ac == Position.BETWEEN) {
            Advance(rot);
            a = v;
            b = Neighbor(rot);
            ac = b.ClassifyPosition2D(s, a);
        }

    }

    Vertex2D LeastVertexTest() {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.PointTest(v, b) < 0) {
                b = v;
            }

        }
        v = b;
        return b;
    }

    Vertex2D LeastVertexClosestTest(Vector to) {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.ClosestPoint(v, b, to) < 0) {
                b = v;
            }
        }
        v = b;
        return b;
    }

    Vertex2D LeastVertexAreaTest() {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.PointAreaTest(v, b) < 0) {
                b = v;
            }

        }
        v = b;
        return b;
    }

    Vertex2D GreaterVertexTest() {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.PointTest(v, b) > 0) {
                b = v;
            }
        }
        v = b;
        return b;
    }

    Vertex2D GreaterVertexClosestTest(Vector to) {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.ClosestPoint(v, b, to) > 0) {
                b = v;
            }
        }
        v = b;
        return b;
    }

    Vertex2D GreaterVertexAreaTest() {
        Vertex2D b = v;
        Advance(1);
        for (int i = 1; i < size; i++, Advance(1)) {
            if (Vector.PointAreaTest(v, b) > 0) {
                b = v;
            }
        }
        v = b;
        return b;
    }

    static Polygon2D Star(Vector s[]) {
        Polygon2D p = new Polygon2D();
        p.Insert(s[0]);
        Vertex2D origin = p.v;
        Vector original = origin.At();
        for (int i = 1; i < s.length; i++) {
            p.Set(origin);
            p.Advance(1);
            while (Vector.PolarCompare(s[i], p.v, original) < 0) {
                p.Advance(1);
            }
            p.Advance(-1);
            p.Insert(s[i]);
        }
        return p;
    }

    static Polygon2D Hull(Vector s[]) {
        Polygon2D p = new Polygon2D();
        p.Insert(s[0]);
        for (int i = 1; i < s.length; i++) {
            if (p.InsidePolygon(s[i])) {
                continue;
            }
            Vector otherPoint = s[i];
            p.LeastVertexClosestTest(otherPoint);
            p.SupportingLine(s[i], Position.LEFT);
            Vertex2D l = p.v;
            p.SupportingLine(s[i], Position.RIGHT);
            Vertex2D d = p.Split(l);
            d = null;
            p.Insert(s[i]);
        }
        return p;
    }

    static Polygon2D Wrap(Vector s[]) {
        int a = 0;
        int i;
        for (i = 1; i < s.length; i++) {
            if (s[i].IsLessThan(s[a])) {
                a = i;
            }
        }
        s[s.length - 1] = s[a];
        Polygon2D poly = new Polygon2D();
        for (int m = 0; m < s.length; m++) {
            Edge.Swap(s[a], s[m]);
            poly.Insert(s[m]);
            for (i = m + 2; i <= m; i++) {
                Position c = s[i].ClassifyPosition2D(s[m], s[a]);
                if (c == Position.LEFT || c == Position.BEYOND) {
                    a = i;

                }

            }
            if (a == s.length) {
                return poly;
            }
        }
        return null;

    }

    static Polygon2D Scan(Vector s[]) {
        int m = 0;
        int i = 1;
        for (; i < s.length; i++) {
            if (s[i].y < s[m].y || (s[i].y == s[m].y && s[i].x < s[m].x)) {
                m = i;
            }
        }
        Edge.Swap(s[0], s[m]);
        Vector original = s[0];
        Vector p[] = new Vector[s.length];
        for (i = 0; i < s.length; i++) {
            p[i] = s[i];
        }
        //SORT
        for (int is = 1; is < s.length - 2; is++) {
            int ms = is;
            for (int js = is + 1; js < s.length - 1; js++) {
                if (Vector.PolarCompare(p[is], p[ms], original) < 0) {
                    ms = js;
                }
            }
            Edge.Swap(p[is], p[ms]);
        }
        for (i = 1; p[i + 1].ClassifyPosition2D(p[0], p[i]) == Position.BEYOND; i++);
        java.util.Stack<Vector> st = new java.util.Stack<>();
        st.push(p[0]);
        st.push(p[i]);
        Vector pop = st.pop();
        for (i = i + 1; i < s.length; i++) {
            while (p[i].ClassifyPosition2D(st.peek(), pop) != Position.LEFT) {
                pop = st.pop();
            }
            st.push(p[i]);
        }
        Polygon2D poly = new Polygon2D();
        while (!st.empty()) {
            Vector pt = st.pop();
            poly.Insert(pt);

        }
        return poly;
    }

    static Polygon2D Intersection(Polygon2D p, Polygon2D q) {
        Polygon2D r = new Polygon2D();
        Vector ip = new Vector(0, 0);
        Vector sp = new Vector(0, 0);
        PolyPosition flag = PolyPosition.UNKNOWN;
        int phase = 1;
        int maxi = 2 * (p.Size() + q.Size());
        for (int i = 1; i <= maxi || phase == 2; i++) {
            Edge pe = p.Side();
            Edge qe = q.Side();
            Position pc = pe.destination.ClassifyPosition(qe.destination, qe.origin);
            Position qc = qe.destination.ClassifyPosition(pe.destination, pe.origin);
            Agonic crossType = pe.CrossingPoint(qe, ip);
            if (crossType == Agonic.AGONIC_CROSS) {
                if (phase == 1) {
                    phase = 2;
                    r = new Polygon2D();
                    r.Insert(ip);
                    sp = ip;
                } else {
                    Vector rat = r.At();
                    if (ip != rat) {
                        if (ip != sp) {
                            r.Insert(ip);
                        } else {

                            return r;
                        }
                    }
                }
                if (pc == Position.RIGHT) {
                    flag = PolyPosition.PINQ;
                } else if (qc == Position.RIGHT) {
                    flag = PolyPosition.QINP;
                } else {
                    flag = PolyPosition.UNKNOWN;
                }

            } else if (crossType == Agonic.COLLINEAR && pc != Position.BEHIND && qc != Position.BEHIND) {
                flag = PolyPosition.UNKNOWN;
            }
            boolean pq = pe.AimsAt(qe, pc, crossType);
            boolean qp = qe.AimsAt(pe, qc, crossType);
            if (pq && qp) {
                if (flag == PolyPosition.QINP || (flag == PolyPosition.UNKNOWN && pc == Position.LEFT)) {
                    p.Advance(r, false);
                } else {
                    q.Advance(r, false);

                }
            } else if (pq) {
                p.Advance(r, flag == PolyPosition.PINQ);

            } else if (qp) {

                q.Advance(r, flag == PolyPosition.QINP);
            } else {
                if (flag == PolyPosition.QINP || (flag == PolyPosition.UNKNOWN && pc == Position.RIGHT)) {
                    p.Advance(r, false);

                } else {
                    q.Advance(r, false);
                }
            }
        }//for loop
        Vector pat = p.At();
        Vector qat = q.At();
        if (q.InsidePolygon(pat)) {
            return new Polygon2D(p);
        } else if (p.InsidePolygon(qat)) {
            return new Polygon2D(q);
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Vertex2D vx = v;
        while (vx != null) {
            if (vx.Equals((Vertex2D) o)) {
                return true;
            }
            vx = vx.next;
        }
        return false;
    }

    @Override
    public Iterator<Vertex2D> iterator() {
        return v;
    }

    @Override
    public Object[] toArray() {
        Vertex2D vx = v;
        int i = 0;
        Object a[] = new Object[size];
        while (vx != null) {
            a[i] = vx;
            i++;
            vx = vx.next;
        }
        return a;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Vertex2D vx = v;
        int i = 0;
        Vertex2D b[] = new Vertex2D[size];
        while (vx != null) {
            b[i] = vx;
            a[i] = (T) vx;
            i++;
            vx = vx.next;
        }
        return (T[]) b;
    }

    @Override
    public boolean add(Vertex2D e) {
        v.Insert(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Vertex2D vx = v;
        while (vx != null) {
            if (vx.Equals((Vertex2D) o)) {

                vx.next = vx.next.next;
                return true;
            } else {
                vx = vx.next;
            }
        }
        return false;

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean ok = false;
        int n = 0;
        for (Object o : c) {
            Vertex2D vx = v;
            while (vx != null) {

                if (vx.Equals((Vertex2D) o)) {
                    n++;
                }
                vx = vx.next;
            }
        }
        return n == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends Vertex2D> c) {
        boolean ok = false;
        int n = 0;
        for (Object o : c) {
            v.Insert((Vertex2D) o);
        }
        return n == c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ok = false;
        int n = 0;
        for (Object o : c) {
            Vertex2D vx = v;
            while (vx != null) {

                if (vx.Equals((Vertex2D) o)) {
                    vx.next = vx.next.next;
                } else {
                    vx = vx.next;
                }
            }
        }
        return n == c.size();
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean ok = false;
        int n = 0;
        Vertex2D vy = null;
        Vertex2D vx = v;
        while (vx != null) {
            for (Object o : c) {

                if (vx.Equals((Vertex2D) o)) {
                    if (vy != null) {
                        vy.Insert(new Vertex2D(vx, vx.graphicsIndex));
                        n++;
                    } else {
                        vy = new Vertex2D(vx, vx.graphicsIndex);
                        n++;
                    }
                } else {
                    vx = vx.next;
                }
            }
        }
        v = vy;
        if (n == c.size()) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        v = null;
    }

}

class Hit {

    Double time;
    GameObject hit;
    Point at;

    Hit() {
        time = Linear.MAX_MAP;
    }

    Hit(double t) {
        time = t;
    }
}

class Ray {

    Vector from = new Vector();
    Normal direction = new Normal(0, 0, 1);

    boolean Penetrates(Point center, Hit t) {
        
        Point collisionPoint = null;
        Vector v = new Vector(this.from);
        v=v.Sub(v,center.AsVector()).AsVector();//differance
        double a = this.direction.Dot(this.direction, this.direction);
        double b = 2.0 * (v.Dot(this.direction.AsVector()));//2*dot
        double c = (v.Dot(v)) - (center.r * center.r);//dot^2 -r^2
        double disc = (b * b) - (4.0 * a * c);
        if (disc < 0) {
            return false;
        }
        double e = Math.sqrt(disc);//sqrt of discriminant
        double den = 2.0 * a;
        //Vector collisionNormal=new Vector();
        //Vector collisionPoint=new Vector();
        double s0 = (-b - e) / den;
        if (s0 > Linear.E) {
            //
            t.time = s0;
            /* Vector dt=(direction.AsVector());
             dt.Scale(s0);
             v.Add(new Vector(dt.x, dt.y, dt.z));
             collisionNormal=v;
             collisionNormal.Scale(1.0f/center.r);
             collisionPoint=new Vector(from);
             collisionPoint.Add(new Vector(dt.x, dt.y, dt.z));
             collisionPoint.w=s0;*/

            Vector dt = (direction.AsVector());
            dt.Scale(s0);
            collisionPoint = new Point(from.x, from.y, from.z);
            collisionPoint = collisionPoint.Add(collisionPoint, new Cartesian(dt.x, dt.y, dt.z)).AsPoint(s0);
            t.at=collisionPoint;
            return true;
        }
        double s1 = (-b + e) / den;
        if (s1 > Linear.E) {

            t.time = s1;
            /*
             Vector dt=(direction.AsVector());
             dt.Scale(s1);
             v.Add(new Vector(dt.x, dt.y, dt.z));
             collisionNormal=v;
             collisionNormal.Scale(1.0f/center.r);
             collisionPoint=new Vector(from);
             collisionPoint.Add(new Vector(dt.x, dt.y, dt.z));
             collisionPoint.w=s1;*/

            Vector dt = (direction.AsVector());
            dt.Scale(s1);
            collisionPoint = new Point(from.x, from.y, from.z);
            collisionPoint = collisionPoint.Add(collisionPoint, new Cartesian(dt.x, dt.y, dt.z)).AsPoint(s1);
            t.at=collisionPoint;
            return true;
        }
        return false;
    }

    Point Penetrates(Point center) {
        Point collisionPoint = null;
        Vector v = new Vector(this.from);
        v.Sub(center.AsVector());//differance
        double a = this.direction.Dot(this.direction, this.direction);
        double b = 2.0 * (v.Dot(this.direction.AsVector()));//2*dot
        double c = (v.Dot(v)) - (center.r * center.r);//dot^2 -r^2
        double disc = (b * b) - (4.0 * a * c);
        if (disc < 0) {
            return null;
        }
        double e = Math.sqrt(disc);//sqrt of discriminant
        double den = 2.0 * a;
        //Vector collisionNormal=new Vector();
        //Vector collisionPoint=new Vector();
        double s0 = (-b - e) / den;
        if (s0 > Linear.E) {
            //
            //t=s0;
            Vector dt = (direction.AsVector());
            dt.Scale(s0);
            //v.Add(new Vector(dt.x, dt.y, dt.z));
            //v=v.Add(v,new Vector(dt.x, dt.y, dt.z));

            /*collisionNormal=v;
             collisionNormal.Scale(1.0f/center.r);
             collisionPoint=new Vector(from);
             collisionPoint.Add(new Vector(dt.x, dt.y, dt.z));
             collisionPoint.w=s0;*/
            collisionPoint = new Point(from.x, from.y, from.z);
            collisionPoint = collisionPoint.Add(collisionPoint, new Cartesian(dt.x, dt.y, dt.z)).AsPoint(s0);
            return collisionPoint;
        }
        double s1 = (-b + e) / den;
        if (s1 > Linear.E) {

            //t=s1;
            Vector dt = (direction.AsVector());
            dt.Scale(s1);
            //v=v.Add(v,new Vector(dt.x, dt.y, dt.z));
     /* collisionNormal=v;
             collisionNormal.Scale(1.0f/center.r);
             collisionPoint=new Vector(from);
             collisionPoint.Add(new Vector(dt.x, dt.y, dt.z));
             collisionPoint.w=s1;*/
            collisionPoint = new Point(from.x, from.y, from.z);
            collisionPoint = collisionPoint.Add(collisionPoint, new Cartesian(dt.x, dt.y, dt.z)).AsPoint(s1);
            return collisionPoint;
        }
        return null;
    }
}
