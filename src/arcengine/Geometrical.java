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
public abstract class Geometrical {

    public abstract CollisionDetector ToDetector();

    public abstract CollisionDetector ToDetector(float mass);

    public abstract CollisionDetector ToDetector(float mass, float radius);
}

class CylindricalCoord {

    double r;
    double xyz;
    double theta;

    CylindricalCoord() {
        xyz = 0;
        r = 1;
        theta = 0;
    }

    CylindricalCoord(double radius, double angle, double axis) {
        xyz = axis;
        r = radius;
        theta = angle;
    }

    CylindricalCoord(Cartesian at, byte poleAxis) {
        if (poleAxis == Linear.AxisZ) {
            r = at.Length2D();
            theta = (double) (Math.atan2(at.y, at.x));
            xyz = at.z;
        } else if (poleAxis == Linear.AxisY) {
            r = (double) (Math.sqrt(at.x * at.x + at.z * at.z));
            theta = (double) (Math.atan2(at.z, at.x));
            xyz = at.y;
        } else {
            r = (double) (Math.sqrt(at.y * at.y + at.z * at.z));
            theta = (double) (Math.atan2(at.z, at.y));
            xyz = at.x;
        }
    }

    Point AtZPole() {
        return new Point(r * (double) Math.cos(theta), r * (double) Math.sin(theta), xyz);
    }

    Point AtYPole() {
        return new Point(r * (double) Math.cos(theta), xyz, r * (double) Math.sin(theta));
    }

    Point AtXPole() {
        return new Point(xyz, r * (double) Math.cos(theta), r * (double) Math.sin(theta));
    }

    Normal ToZPole() {
        Normal n = new Normal(r * (double) Math.cos(theta), r * (double) Math.sin(theta), xyz);
        n.Normalize();
        return n;
    }

    Normal ToYPole() {
        Normal n = new Normal(r * (double) Math.cos(theta), xyz, r * (double) Math.sin(theta));
        n.Normalize();
        return n;
    }

    Normal ToXPole() {
        Normal n = new Normal(xyz, r * (double) Math.cos(theta), r * (double) Math.sin(theta));
        n.Normalize();
        return n;
    }

    double Sine(double invr) {
        return xyz * invr;
    }

    double Cosine(double invr) {
        return xyz * invr;
    }
}

class Plane {

    double distance;
    Normal normal;

    Plane() {
        distance = 0;
        normal = new Normal(0, 1, 0);
    }

    Plane(Point pos, Normal norm) {
        normal = norm;
        distance = -(normal.DotProduct(normal, pos));
    }

    Plane(Normal dir, double d, Normal norm) {
        normal = norm;
        distance = -(normal.DotProduct(norm, dir.Scaled(d)));
    }

    Plane(double d, Normal norm) {

        normal = norm;
        distance = d;
    }

    Plane(Normal norm, double d) {

        normal = norm;
        distance = d;
    }

    Plane(Point pos1, Point pos2, Point pos3) {
        Normal n = pos1.CrossProduct(pos1.Sub(pos2, pos1), pos1.Sub(pos3, pos1)).Normal();
        normal = new Normal(n.x, n.y, n.z);
        distance = -(normal.DotProduct(n, pos1));
    }

    Plane(Vector pos1, Vector pos2, Vector pos3) {
        Normal n = pos1.CrossProduct(pos1.Sub(pos2, pos1), pos1.Sub(pos3, pos1)).Normal();
        normal = new Normal(n.x, n.y, n.z);
        distance = -(normal.DotProduct(n, pos1));
    }

    void Flip() {
        normal = normal.Negative().AsNormal();
    }

    double Product(Point p) {

        return normal.DotProduct(normal, p) + distance;

    }

    boolean Front(Point p, double product) {
        double dp = product;
        if (dp > Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean Back(Point p, double product) {
        double dp = product;
        if (dp < -Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean On(Point p, double product) {
        double dp = product;
        if (dp < Linear.Epsilon && dp > -Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean Front(Point p) {
        double dp = normal.DotProduct(normal, p) + distance;
        if (dp > Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean Back(Point p) {
        double dp = normal.DotProduct(normal, p) + distance;
        if (dp < -Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean On(Point p) {
        double dp = normal.DotProduct(normal, p) + distance;
        if (dp < Linear.Epsilon && dp > -Linear.Epsilon) {
            return true;
        }
        return false;
    }

    boolean Front(Point p, double product, double thickness) {
        double dp = product;
        if (dp > thickness) {
            return true;
        }
        return false;
    }

    boolean Back(Point p, double product, double thickness) {
        double dp = product;
        if (dp < -thickness) {
            return true;
        }
        return false;
    }

    boolean On(Point p, double product, double thickness) {
        double dp = product;
        if (dp < thickness && dp > -thickness) {
            return true;
        }
        return false;
    }
}

class SphericalCoord {

    public double p;
    public double theta;
    public double phi;

    SphericalCoord() {
        p = 1;
        theta = 0;
        phi = 0;
    }

    SphericalCoord(float a) {
        theta = 0;
        phi = a;
        p = 1;
    }

    SphericalCoord(float a, float r, float t) {
        theta = 0;
        phi = a;
        p = r;
    }

    SphericalCoord(Vector at) {

        p = (float) Math.sqrt(at.x * at.x + at.y * at.y + 1);
        theta = (float) Math.atan2(1, at.x);
        phi = (float) Math.acos(at.y / p);
    }

    SphericalCoord(float x, float y) {

        p = (float) Math.sqrt(x * x + y * y + 1);
        theta = (float) Math.atan2(1, x);
        phi = (float) Math.acos(y / p);
    }

    Point At() {
        
            double sy = (double)Math.sin(phi);
            double cy = (double)Math.cos(phi);
            double cx = (double)Math.cos(theta);
            double sx = (double)Math.sin(theta);
            //
            return new Point(p * sx * cy, p * sy, p * cx * cy,p);
        //return new Point((float) (p * Math.sin(phi) * Math.cos(theta)), (float) (p * Math.cos(phi)));
    }

    SphericalCoord(SphericalCoord from, SphericalCoord to) {
        p = (from.p + to.p) * 0.5f;
        theta = (from.theta + to.theta) * 0.5f;
        phi = (from.phi + to.phi) * 0.5f;
    }

    SphericalCoord(double xang, double yang, double rad) {
        phi = yang;
        theta = xang;
        p = rad;

    }

    SphericalCoord(Cartesian at, byte pole) {

        if (pole == Linear.AxisZ) {
            double r = (double) Math.sqrt(at.x * at.x + at.y * at.y);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.y, at.x);
            phi = (double) Math.acos(at.z / p);
        } else if (pole == Linear.AxisY) {
            double r = (double) Math.sqrt(at.x * at.x + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.x);
            phi = (double) Math.acos(at.y / p);

        } else {
            double r = (double) Math.sqrt(at.y * at.y + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.y);
            phi = (double) Math.acos(at.x / p);

        }
    }

    SphericalCoord(Point at, byte pole) {

        if (pole == Linear.AxisZ) {
            double r = (double) Math.sqrt(at.x * at.x + at.y * at.y);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.y, at.x);
            phi = (double) Math.acos(at.z / p);
        } else if (pole == Linear.AxisY) {
            double r = (double) Math.sqrt(at.x * at.x + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.x);
            phi = (double) Math.acos(at.y / p);

        } else {
            double r = (double) Math.sqrt(at.y * at.y + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.y);
            phi = (double) Math.acos(at.x / p);

        }
    }

    SphericalCoord(Vector at, byte pole) {

        if (pole == Linear.AxisZ) {
            double r = (double) Math.sqrt(at.x * at.x + at.y * at.y);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.y, at.x);
            phi = (double) Math.acos(at.z / p);
        } else if (pole == Linear.AxisY) {
            double r = (double) Math.sqrt(at.x * at.x + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.x);
            phi = (double) Math.acos(at.y / p);

        } else {
            double r = (double) Math.sqrt(at.y * at.y + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.y);
            phi = (double) Math.acos(at.x / p);

        }
    }

    SphericalCoord(Normal at, byte pole) {

        if (pole == Linear.AxisZ) {
            double r = (double) Math.sqrt(at.x * at.x + at.y * at.y);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.y, at.x);
            phi = (double) Math.acos(at.z / p);
        } else if (pole == Linear.AxisY) {
            double r = (double) Math.sqrt(at.x * at.x + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.x);
            phi = (double) Math.acos(at.y / p);

        } else {
            double r = (double) Math.sqrt(at.y * at.y + at.z * at.z);
            p = (double) at.Length();
            theta = (double) Math.atan2(at.z, at.y);
            phi = (double) Math.acos(at.x / p);

        }
    }

    Vector AtZPole() {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) Math.cos(theta);
        double sx = (double) Math.sin(theta);
        //
        return new Vector(p * sy * cx, p * sy * sx, p * cy, p);
    }

    Vector AtYPole() {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) Math.cos(theta);
        double sx = (double) Math.sin(theta);
        //
        return new Vector(p * sx * cy, p * sy, p * cx * cy, p);
    }

    Vector AtXPole() {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) Math.cos(theta);
        double sx = (double) Math.sin(theta);
        //
        return new Vector(p * cy, p * sx * sy, p * cx * cy, p);
    }

    Normal ToYPole() {

        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) Math.cos(theta);
        double sx = (double) Math.sin(theta);
        Normal ret = new Normal(sx * cy, sy, cx * cy);
        ret.Normalize();
        return ret;
    }

    Normal ToXPole() {

        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) Math.cos(theta);
        double sx = (double) Math.sin(theta);
        Normal ret = new Normal(cy, sx * sy, cx * cy);
        ret.Normalize();
        return ret;
    }

    Normal ToZPole() {
        double sp = Math.sin(phi);
        //
        Normal n = new Normal(sp * Math.cos(theta), sp * Math.sin(theta), Math.cos(phi));
        n.Normalize();
        return n;
    }

    Normal SphericalDirectionZPole(double sintheta, double costheta, double phi) {
        Normal ret = new Normal(sintheta * Math.cos(phi),
                sintheta * Math.sin(phi),
                costheta);
        ret.Normalize();
        return ret;
    }

    Normal SphericalDirectionYPole(double sintheta, double costheta, double phi) {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) costheta;
        double sx = (double) sintheta;
        Normal ret = new Normal(sx * cy, sy, cx * cy);
        ret.Normalize();
        return ret;
    }

    Normal SphericalDirectionXPole(double sintheta, double costheta, double phi) {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) costheta;
        double sx = (double) sintheta;
        Normal ret = new Normal(cy, sx * sy, cx * cy);
        ret.Normalize();
        return ret;
    }

    Vector SphericalDirectionZPole(double sintheta, double costheta, double phi, Vector x, Vector y, Vector z) {
        Vector xs = x.Scaled((double) (sintheta * Math.cos(phi)));
        Vector ys = y.Scaled((double) (sintheta * Math.sin(phi)));
        Vector zs = z.Scaled(costheta);
        return xs.Add(xs, xs.Add(ys, zs)).AsVector();
    }

    Vector SphericalDirectionYPole(double sintheta, double costheta, double phi, Vector x, Vector y, Vector z) {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) costheta;
        double sx = (double) sintheta;
        //return x * sx * cy + y * sy + z * cx * cy;
        Vector xs = x.Scaled((double) (sx * cy));
        Vector ys = y.Scaled((double) (sy));
        Vector zs = z.Scaled(cx * cy);
        return xs.Add(xs, xs.Add(ys, zs)).AsVector();

    }

    Vector SphericalDirectionXPole(double sintheta, double costheta, double phi, Vector x, Vector y, Vector z) {
        double sy = (double) Math.sin(phi);
        double cy = (double) Math.cos(phi);
        double cx = (double) costheta;
        double sx = (double) sintheta;
        //return x * cy + y * sx * sy + z * cx * cy;
        Vector xs = x.Scaled((double) (cy));
        Vector ys = y.Scaled((double) (sx * sy));
        Vector zs = z.Scaled(cx * cy);
        return xs.Add(xs, xs.Add(ys, zs)).AsVector();

    }

    double SphericalThetaZPole(Point v) {
        return (double) (Math.acos(Linear.Clamp(v.z, -1.0f, 1.0f)));
    }

    double SphericalThetaXPole(Point v) {
        return (double) (Math.acos(Linear.Clamp(v.x, -1.0f, 1.0f)));
    }

    double SphericalThetaYPole(Point v) {
        return (double) (Math.acos(Linear.Clamp(v.y, -1.0f, 1.0f)));
    }

    double SphericalPhiZPole(Point v) {
        double p = (double) Math.atan2(v.y, v.x);
        return (p < 0.0f) ? p + Linear.PI2 : p;
    }

    double SphericalPhiXPole(Point v) {
        double p = (double) Math.atan2(v.y, v.z);
        return (p < 0.0f) ? p + Linear.PI2 : p;
    }

    double SphericalPhiYPole(Point v) {
        double p = (double) Math.atan2(v.z, v.x);
        return (p < 0.0f) ? p + Linear.PI2 : p;
    }
}

class Molecula {

    static java.util.Random rnd = new java.util.Random();
    double cosine;
    double sine;
    double startAngle;
    double currentAngle;
    double spin;

    ///
    Molecula() {
        startAngle = Math.PI * rnd.nextDouble() / rnd.nextInt(3600);
        currentAngle = Linear.PI2 * rnd.nextDouble();
        spin = startAngle * rnd.nextDouble();
        cosine = Math.cos(spin);
        sine = Math.sin(spin);
    }
    Molecula(Molecula m) {
        startAngle = Math.PI * m.NextDouble() / m.NextInt(3600);
        currentAngle = Linear.PI2 * m.NextDouble();
        spin = startAngle * m.NextDouble();
        cosine = Math.cos(spin);
        sine = Math.sin(spin);
    }

    Molecula(double start, double current, double spin) {
        startAngle = start;
        currentAngle = current;
        this.spin = startAngle * spin;
        cosine = Math.cos(spin);
        sine = Math.sin(spin);
    }

    String ToString() {
        return "Molecula angles\tstart" + startAngle + "\tcurrent" + currentAngle + "\tspin" + spin + "\ttrig cos" + cosine + "sine" + cosine;
    }

    double Angle() {
        return this.currentAngle;
    }

    double Spin() {
        return this.spin;
    }

    double Cos() {
        return cosine;
    }

    double Sin() {
        return sine;
    }

    void SingleCalc() {
        spin += currentAngle;
        cosine = Math.cos(spin);
        sine = Math.sin(spin);
        currentAngle += startAngle;
    }

    void SingleCalc(double scale) {
        spin += currentAngle;
        cosine = Math.cos(spin);
        sine = Math.sin(spin);
        currentAngle += startAngle * scale;
    }

    void Calc() {
        spin += currentAngle;
        cosine = Math.cos(spin);
        spin += currentAngle;
        sine = Math.sin(spin);
        currentAngle += startAngle;
    }

    void Calc(double scale) {
        spin += currentAngle;
        cosine = Math.cos(spin);
        spin += currentAngle;
        sine = Math.sin(spin);
        currentAngle += startAngle * scale;
    }

    void LineCalc() {
        spin += currentAngle;
        spin /= startAngle;
        cosine = Math.cos(spin);
        spin += currentAngle;
        spin /= startAngle;
        sine = Math.sin(spin);
        currentAngle += startAngle;
    }

    void LineCalc(double scale) {
        spin += currentAngle;
        spin /= startAngle;
        cosine = Math.cos(spin);
        spin += currentAngle;
        spin /= startAngle;
        sine = Math.sin(spin);
        currentAngle += startAngle * scale;
    }

    int NextInt(int p) {
        this.SingleCalc();
        return (int) ((Math.abs(this.cosine) + Math.abs(this.sine)) * p * 0.5) % p;
    }

    double NextDouble() {
        this.SingleCalc();
        return ((Math.abs(this.cosine) + Math.abs(this.sine)) * 0.5);
    }

    float Next(float f) {
        this.SingleCalc();
        return (float) ((Math.abs(this.cosine) + Math.abs(this.sine)) * 0.5) * f;
    }
}

class Line extends Geometrical {

    Point start;
    Vector direction;

    //
    @Override
    public CollisionDetector ToDetector() {
        return new CollisionDetector(start);
    }

    @Override
    public CollisionDetector ToDetector(float mass) {

        return new CollisionDetector(start, mass);
    }

    @Override
    public CollisionDetector ToDetector(float mass, float radius) {

        return new CollisionDetector(start, mass, radius);
    }

    Line(Point from, Vector dir) {
        start = from;
        direction = dir;
    }

    Line(Point from, Normal dir) {
        start = from;
        direction = dir.AsVector();

    }

    Line() {
        start = new Point();
        direction = new Vector(1, 0);

    }

    boolean Equvialent(Line to) {
        if (!direction.Parallel2D(to.direction)) {
            return false;
        }
        Vector p = start.Sub(start, to.start);
        return p.Parallel2D(to.direction);
    }

    boolean Collide2D(Line with) {
        if (direction.Parallel2D(with.direction)) {
            boolean eq = Equvialent(with);
            return eq;
        }
        return true;
    }

    boolean Collide3D(Line with) {
        if (direction.Parallel2D(with.direction)) {
            boolean eq = Equvialent(with);
            if (eq) {
                Line xz = new Line(new Point(start.x, start.z), new Vector(direction.x, direction.z));
                Line wxz = new Line(new Point(with.start.x, with.start.z), new Vector(with.direction.x, with.direction.z));
                return eq && xz.Collide2D(wxz);
            }
            return eq;

        }
        return true;
    }

    boolean Collide2D(Point p) {
        if (start.Collides2D(p)) {
            return true;
        }
        Vector lp = p.Sub(p, start);
        return lp.Parallel2D(direction);
    }

    boolean Collide3D(Point p) {
        if (start.Collides3D(p, Linear.Epsilon)) {
            return true;
        }
        Vector lp = p.Sub(p, start);
        return lp.Parallel3D(direction);
    }

    boolean Collide2D(Point p, double r) {
        if (start.Collides2D(p, r)) {
            return true;
        }
        Vector lp = p.Sub(p, start);
        return lp.AsPoint(start.r).Parallel2D(direction, r);
    }

    boolean Collide3D(Point p, double r) {
        if (start.Collides3D(p, r)) {
            return true;
        }
        Vector lp = p.Sub(p, start);
        return lp.Parallel3D(direction, r);
    }

    Line Add(Line p, Vector offset) {
        Point at = p.start.Add(p.start, offset).AsPoint(p.start.r);
        return new Line(at, p.direction);
    }

    Line Sub(Line p, Vector offset) {
        return new Line(p.start.Sub(p.start, offset).AsPoint(p.start.r), p.direction);
    }

    Point Parametric(double t) {
        return start.Add(start, direction.Scaled(t)).AsPoint(start.r);
    }

    Point ParametricIntersect(double t1, Line l2, double t2) {
        Point v1 = start.Add(start, direction.Scaled(t1)).AsPoint(start.r);
        Point v2 = l2.start.Add(l2.start, l2.direction.Scaled(t2)).AsPoint(l2.start.r);
        Point solve = new Point(v1.x * t1 - v2.x * t2, v1.y * t1 + v2.y * t2, v1.z * t1 - v2.z * t2);
        return solve;
    }

    double Slope2D(byte axis) {
        Vector p = start.Sub(start.Unity2D(), direction.Unity2D()).AsVector();
        if (p.x != 0) {
            if (axis == Linear.AxisX) {
                return (p.y / p.x) * p.y;
            } else if (axis == Linear.AxisY) {
                return (p.y) + p.Length2D();
            }
        } else if (axis == Linear.AxisY) {
            return (p.y) + p.Length2D();
        }
        return 1;
    }
};

class Range {

    double maxim;

    double minim;

    Range(double from, double to) {
        minim = from;
        maxim = to;
        Sort();
    }

    Range(Range r1, Range r2) {
        if (r1.minim < r2.minim) {
            minim = r1.minim;
        } else {
            minim = r2.minim;
        }
        if (r1.maxim > r2.maxim) {
            maxim = r1.maxim;
        } else {
            maxim = r2.maxim;
        }
    }

    Range() {
        maxim = 1;
        minim = -1;
    }

    void Sort() {
        if (maxim < minim) {
            double m = maxim;
            maxim = minim;
            minim = m;
        }
    }

    boolean Overrlapping(Range r) {
        Point p1 = new Point(minim, maxim);
        Point p2 = new Point(r.minim, r.maxim);
        return Linear.Overlapping2D(p1, p2, Linear.Epsilon);
    }

    boolean Overrlapping(Range r, double threshold) {
        Point p1 = new Point(minim, maxim);
        Point p2 = new Point(r.minim, r.maxim);
        return Linear.Overlapping2D(p1, p2, threshold);
    }

    double Clamp(double x) {
        if (x < minim) {
            return minim;
        } else if (x > maxim) {
            return maxim;
        } else {
            return x;
        }
    }
};

class Segment extends Geometrical {

    Vector p1;
    Vector p2;

    Segment(Vector from, Vector to) {
        p1 = from;
        p2 = to;
    }

    Segment() {
        p1 = new Vector(0, 0);
        p2 = new Vector(1, 0);
    }

    @Override
    public CollisionDetector ToDetector() {
        Point at = new Point((p1.x + p2.x) * 0.5, (p1.y + p2.y) * 0.5);
        double sx = (p1.x - p2.x);
        double sy = (p1.y - p2.y);
        at.r = Math.sqrt(sx * sx + sy * sy);
        return new CollisionDetector(at);
    }

    @Override
    public CollisionDetector ToDetector(float mass) {
        Point at = new Point((p1.x + p2.x) * 0.5, (p1.y + p2.y) * 0.5);
        double sx = (p1.x - p2.x);
        double sy = (p1.y - p2.y);
        at.r = Math.sqrt(sx * sx + sy * sy);

        return new CollisionDetector(at, mass);
    }

    @Override
    public CollisionDetector ToDetector(float mass, float radius) {
        Point at = new Point((p1.x + p2.x) * 0.5, (p1.y + p2.y) * 0.5);

        return new CollisionDetector(at, mass, radius);
    }

    boolean OnSide2D(Line axis) {
        Cartesian d1 = p1.Sub(p1.AsPoint(1), axis.start);
        Cartesian d2 = p2.Sub(p2.AsPoint(1), axis.start);
        Cartesian n = axis.direction.Rotate90Degrees2D();
        return n.DotProduct2D(n, d1) * n.DotProduct2D(n, d2) > 0;
    }

    boolean OnSide3D(Line axis) {
        Cartesian d1 = p1.Sub(p1.AsPoint(1), axis.start);
        Cartesian d2 = p2.Sub(p2.AsPoint(1), axis.start);
        Cartesian n = p1.CrossProduct(axis.start, axis.direction);
        return n.DotProduct(n, d1) * n.DotProduct(n, d2) > 0;
    }

    Range Project2D(Point onto) {
        Cartesian u = onto.Unity2D().AsVector();
        return new Range(u.DotProduct2D(u, p1), u.DotProduct2D(u, p2));
    }

    Range Project3D(Point onto) {
        Cartesian u = onto.Unity();
        return new Range(u.DotProduct(u, p1), u.DotProduct(u, p2));
    }

    Range Project2D(Vector onto) {
        Cartesian u = onto.Unity2D();
        return new Range(u.DotProduct2D(u, p1), u.DotProduct2D(u, p2));
    }

    Range Project3D(Vector onto) {
        Cartesian u = onto.Unity();
        return new Range(u.DotProduct(u, p1), u.DotProduct(u, p2));
    }

    boolean Collide2D(Point p) {
        Cartesian d = p2.Sub(p2, p1);
        Cartesian lp = p.Sub(p, p1.AsPoint(1));
        Cartesian pr = lp.Project2D(d);
        return lp == pr && pr.Length2DSquared() <= d.Length2DSquared() && pr.DotProduct2D(pr, d) >= 0;
    }

    boolean Collide3D(Point p) {
        Cartesian d = p2.Sub(p2, p1);
        Cartesian lp = p.Sub(p, p1.AsPoint(1));
        Cartesian pr = lp.Project(d);
        return lp == pr && pr.LengthSquared() <= d.LengthSquared() && pr.DotProduct(pr, d) >= 0;
    }

    boolean Collide2D(Point p, double r) {
        Cartesian d = p2.Sub(p2, p1);
        Cartesian lp = p.Sub(p, p1.AsPoint(1));
        Cartesian pr = lp.Project2D(d);
        return lp == pr && pr.Length2DSquared() <= d.Length2DSquared() && pr.DotProduct2D(pr, d) >= 0;
    }

    boolean Collide3D(Point p, double r) {
        Cartesian d = p2.Sub(p2, p1);
        Cartesian lp = p.Sub(p, p1.AsPoint(1));
        Cartesian pr = lp.Project(d);
        boolean ok = Math.abs(lp.x - pr.x) < r && Math.abs(lp.y - pr.y) < r && Math.abs(lp.z - pr.z) < r;
        if (ok) {
            double r2 = r * r;
            return pr.LengthSquared() <= (d.LengthSquared() + r2) && pr.DotProduct2D(pr, d) >= -r2;
        }
        return false;
    }

    boolean Collide3D(double thickness, Point p, double r) {
        Cartesian d = p2.Sub(p2, p1);
        Cartesian lp = p.Sub(p, p1.AsPoint(1));
        Cartesian pr = lp.Project(d);
        double rt = r + thickness;
        boolean ok = Math.abs(lp.x - pr.x) < rt && Math.abs(lp.y - pr.y) < rt && Math.abs(lp.z - pr.z) < rt;
        if (ok) {
            double r2 = rt * rt;
            return pr.LengthSquared() <= (d.LengthSquared() + r2) && pr.DotProduct2D(pr, d) >= -rt;
        }
        return false;
    }

    boolean Collide2D(Segment with) {
        Line ax1 = new Line();
        Line ax2 = new Line();
        ax1.start = p1.AsPoint(1);
        ax1.direction = p1.Sub(p1, p2).Unity2D().AsVector();
        if (with.OnSide2D(ax1)) {
            return false;
        }
        ax2.start = with.p1.AsPoint(1);
        ax2.direction = with.p1.Sub(with.p1, with.p2).Unity2D().AsVector();
        if (OnSide2D(ax2)) {
            return false;
        }
        if (ax1.direction.Parallel2D(ax2.direction)) {
            Range r1 = Project2D(ax1.direction);
            Range r2 = with.Project2D(ax2.direction);
            return r1.Overrlapping(r2);
        } else {
            return true;
        }
    }

    boolean Collide2D(Segment with, double halfThickness) {
        Line ax1 = new Line();
        Line ax2 = new Line();
        ax1.start = p1.AsPoint(1);
        ax1.direction = p1.Sub(p1, p2).Unity2D().AsVector();
        if (with.OnSide2D(ax1)) {
            return false;
        }
        ax2.start = with.p1.AsPoint(1);
        ax2.direction = with.p1.Sub(with.p1, with.p2).Unity2D().AsVector();
        if (OnSide2D(ax2)) {
            return false;
        }
        if (ax1.direction.Parallel2D(ax2.direction)) {
            Range r1 = Project2D(ax1.direction);
            Range r2 = with.Project2D(ax2.direction);
            return r1.Overrlapping(r2, halfThickness);
        } else {
            return true;
        }
    }

    boolean Collide3D(Segment with) {
        Line ax1 = new Line();
        Line ax2 = new Line();
        ax1.start = p1.AsPoint(1);
        ax1.direction = p1.Sub(p1, p2).Unity2D().AsVector();
        if (with.OnSide2D(ax1)) {
            return false;
        }
        ax2.start = with.p1.AsPoint(1);
        ax2.direction = with.p1.Sub(with.p1, with.p2).Unity2D().AsVector();
        if (OnSide2D(ax2)) {
            return false;
        }
        if (ax1.direction.Parallel2D(ax2.direction)) {
            Range r1 = Project2D(ax1.direction);
            Range r2 = with.Project2D(ax2.direction);
            boolean overlap = r1.Overrlapping(r2);
            if (overlap) {
                Segment szx = new Segment(new Vector(p1.x, p1.z), new Vector(p2.x, p2.z));
                Segment wszx = new Segment(new Vector(with.p1.x, with.p1.z), new Vector(with.p2.x, with.p2.z));
                return szx.Collide2D(wszx);
            }
            return overlap;
        } else {
            Segment szx = new Segment(new Vector(p1.x, p1.z), new Vector(p2.x, p2.z));
            Segment wszx = new Segment(new Vector(with.p1.x, with.p1.z), new Vector(with.p2.x, with.p2.z));
            return szx.Collide2D(wszx);
        }
    }

    boolean Collide3D(Segment with, double halfThickness) {
        Line ax1 = new Line();
        Line ax2 = new Line();
        ax1.start = p1.AsPoint(1);
        ax1.direction = p1.Sub(p1, p2).Unity2D().AsVector();
        if (with.OnSide2D(ax1)) {
            return false;
        }
        ax2.start = with.p1.AsPoint(1);
        ax2.direction = with.p1.Sub(with.p1, with.p2).Unity2D().AsVector();
        if (OnSide2D(ax2)) {
            return false;
        }
        if (ax1.direction.Parallel2D(ax2.direction)) {
            Range r1 = Project2D(ax1.direction);
            Range r2 = with.Project2D(ax2.direction);
            boolean overlap = r1.Overrlapping(r2, halfThickness);
            if (overlap) {
                Segment szx = new Segment(new Vector(p1.x, p1.z), new Vector(p2.x, p2.z));
                Segment wszx = new Segment(new Vector(with.p1.x, with.p1.z), new Vector(with.p2.x, with.p2.z));
                return szx.Collide2D(wszx);
            }
            return overlap;
        } else {
            Segment szx = new Segment(new Vector(p1.x, p1.z), new Vector(p2.x, p2.z));
            Segment wszx = new Segment(new Vector(with.p1.x, with.p1.z), new Vector(with.p2.x, with.p2.z));
            return szx.Collide2D(wszx, halfThickness);
        }
    }

    boolean Collide2D(Line line) {
        return !OnSide2D(line);
    }

    boolean Collide3D(Line line) {
        return !OnSide3D(line);
    }

    double Slope2D(byte axis) {
        Cartesian p = p1.Sub(p1, p2);
        if (p.x != 0) {
            if (axis == Linear.AxisX) {
                return (p.y / p.x) * p.y;
            } else if (axis == Linear.AxisY) {
                return (p.y) + p.Length2D();
            }
        } else if (axis == Linear.AxisY) {
            return (p.y) + p.Length2D();
        }
        return 1;
    }

    Vector Parametric2D(double t) {
        return p1.Add(p1, p2.Unity2D().Scaled(t)).AsVector();
    }

    Vector Parametric3D(double t) {
        return p1.Add(p1, p2.Unity().Scaled(t)).AsVector();
    }

    Point ParametricIntersect2D(double t1, Segment s2, double t2) {
        Cartesian v1 = p1.Add(p1, p2.Unity2D().Scaled(t1));
        Cartesian v2 = s2.p1.Add(s2.p1, s2.p2.Unity2D().Scaled(t2));
        Point solve = new Point(v1.x * t1 - v2.x * t2, v1.y * t1 + v2.y * t2);
        return solve;
    }

    Point ParametricIntersect3D(double t1, Segment s2, double t2) {
        Cartesian v1 = p1.Add(p1, p2.Unity().Scaled(t1));
        Cartesian v2 = s2.p1.Add(s2.p1, s2.p2.Unity().Scaled(t2));
        Point solve = new Point(v1.x * t1 - v2.x * t2, v1.y * t1 + v2.y * t2, v1.z * t1 - v2.z * t2);
        return solve;
    }

    Segment Add(Segment seg, Vector offset) {
        return new Segment(seg.p1.Add(seg.p1, offset).AsVector(), seg.p2.Add(seg.p2, offset).AsVector());
    }

    Segment Sub(Segment seg, Vector offset) {
        return new Segment(seg.p1.Sub(seg.p1, offset).AsVector(), seg.p2.Sub(seg.p2, offset).AsVector());
    }
};

class Circle extends Geometrical {

    Point center;
    double radius;

    Circle(Point at, double rad) {
        center = at;
        radius = rad;
    }

    Circle() {

        center = new Point(0, 0);
        radius = 1;
    }

    @Override
    public CollisionDetector ToDetector() {
        return new CollisionDetector(center);
    }

    @Override
    public CollisionDetector ToDetector(float mass) {

        return new CollisionDetector(center, mass);
    }

    @Override
    public CollisionDetector ToDetector(float mass, float radius) {

        return new CollisionDetector(center, mass, radius);
    }

    boolean Collide2D(Circle with) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.Length2DSquared();
        return len < r2;
    }

    boolean Collide3D(Circle with) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        return len < r2;
    }

    boolean Collide2D(Point p) {
        double r2 = radius * radius;
        Vector d = center.Sub(center, p);
        double dis2 = d.Length2DSquared();
        return dis2 < r2;
    }

    boolean Collide3D(Point p) {
        double r2 = radius * radius;
        Vector d = center.Sub(center, p);
        double dis2 = d.LengthSquared();
        return dis2 < r2;
    }

    boolean Collide2D(Point p, double r) {
        double r2 = radius + r;
        Vector d = center.Sub(center, p);
        double dis2 = d.Length2D();
        return dis2 < r2;
    }

    boolean Collide3D(Point p, double r) {
        double r2 = radius * radius + r * r;
        Vector d = center.Sub(center, p);
        double dis2 = d.LengthSquared();
        return dis2 < r2;
    }

    boolean Collide2D(Line ln) {
        Vector d = center.Sub(center, ln.start);
        Vector p = d.Project2D(ln.direction);
        Point nearest = ln.start.Add(ln.start, p).AsPoint(ln.start.r);
        return Collide2D(nearest);
    }

    boolean Collide3D(Line ln) {
        Vector d = center.Sub(center, ln.start);
        Vector p = d.Project(ln.direction);
        Point nearest = ln.start.Add(ln.start, p).AsPoint(ln.start.r);
        return Collide3D(nearest);
    }

    boolean Collide2D(Segment seg) {
        if (Collide2D(seg.p1.AsPoint(1))) {
            return true;
        }
        if (Collide2D(seg.p2.AsPoint(1))) {
            return true;
        }
        Cartesian d = seg.p1.Sub(seg.p1, seg.p2);
        Cartesian ln = center.Sub(center, seg.p1.AsPoint(1));
        Cartesian p = ln.Project2D(d);
        Point nearest = (seg.p1.Add(seg.p1, p)).AsPoint(1);
        return Collide2D(nearest) && p.Length2DSquared() <= d.Length2DSquared() && p.DotProduct2D(p, d) >= 0;
    }

    boolean Collide3D(Segment seg) {
        if (Collide3D(seg.p1.AsPoint(1))) {
            return true;
        }
        if (Collide3D(seg.p2.AsPoint(1))) {
            return true;
        }
        Cartesian d = seg.p1.Sub(seg.p1, seg.p2);
        Cartesian ln = center.Sub(center, seg.p1.AsPoint(1));
        Cartesian p = ln.Project(d);
        Point nearest = (seg.p1.Add(seg.p1, p)).AsPoint(1);
        return Collide3D(nearest) && p.LengthSquared() <= d.LengthSquared() && p.DotProduct(p, d) >= 0;
    }

    Circle Add(Circle c, Vector offset) {
        return new Circle(c.center.Add(c.center, offset).AsPoint(c.center.r), c.radius);
    }

    Circle Add(Circle c, double offset) {
        return new Circle(c.center, Math.abs(c.radius + offset));
    }

    Circle Sub(Circle c, double offset) {
        return new Circle(c.center, Math.abs(c.radius - offset));
    }

    Circle Sub(Circle c, Vector offset) {
        return new Circle(c.center.Sub(c.center, offset).AsPoint(c.center.r), c.radius);
    }
};

class Sphere extends Circle {

    Normal pole;
    SphericalCoord direction;

    Sphere(Point at, double rad) {
        super(at, rad);

        pole = new Normal(0, 1, 0);
        direction = new SphericalCoord(new Point(0, 0, 1), Linear.AxisY);
    }

    Sphere() {
        pole = new Normal(0, 1, 0);
        direction = new SphericalCoord(new Point(0, 0, 1), Linear.AxisY);
    }
};

class Disk extends Circle {

    double thickness;
    Normal planeNormal;

    //
    Disk(Point at, double rad, Normal plane) {
        super(at, rad);
        planeNormal = plane;
        thickness = Linear.Epsilon;

    }

    Disk(Point at, double rad, Normal plane, double thicken) {
        super(at, rad);
        planeNormal = plane;
        thickness = thicken;

    }

    Disk() {
        planeNormal = new Normal(0, 1, 0);
        thickness = Linear.Epsilon;

    }

    boolean Collide3D(Circle with) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        if (len < r2) {
            double added = thickness + Linear.Epsilon;
            double d1 = dif.DotProduct(dif, planeNormal);
            double d2 = dif.DotProduct(dif, new Normal(0, 1, 0));
            double d3 = Math.abs(d1 + d2);
            return (d3 < added);
        } else {
            return false;
        }
    }

    boolean CollideWithSphere3D(Circle with) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        if (len < r2) {
            double added = thickness + Linear.Epsilon;
            double d1 = dif.DotProduct(dif, planeNormal);
            return (d1 > -added && d1 < added);
        } else {
            return false;
        }
    }

    boolean Collide3D(Circle with, double thickness) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        if (len < r2) {
            double added = this.thickness + thickness;
            double d1 = dif.DotProduct(dif, planeNormal);
            double d2 = dif.DotProduct(dif, new Normal(0, 1, 0));
            double d3 = Math.abs(d1 + d2);
            return (d3 < added);

        } else {
            return false;
        }
    }

    boolean Collide3D(Circle with, Normal norm, double halfThickness) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        if (len < r2) {
            double added = this.thickness + halfThickness;
            double d1 = dif.DotProduct(dif, planeNormal);
            double d2 = dif.DotProduct(dif, norm);
            double d3 = Math.abs(d1 + d2);
            return (d3 < added);
        } else {
            return false;
        }
    }

    boolean Collide3D(Disk with) {
        double r2 = radius * radius + with.radius * with.radius;
        Vector dif = center.Sub(center, with.center);
        double len = dif.LengthSquared();
        if (len < r2) {
            double added = thickness + with.thickness;
            double d1 = dif.DotProduct(dif, planeNormal);
            double d2 = dif.DotProduct(dif, with.planeNormal);
            double d3 = Math.abs(d1 + d2);
            return (d3 < added);

        } else {
            return false;
        }
    }
};

class Rectalinear extends Circle {

    Vector halfExtent;
    SphericalCoord angle;

    Rectalinear(Point at, Vector hext) {
        super(at, hext.Length());
        halfExtent = hext;
        angle = new SphericalCoord(at.Add(at, hext), Linear.AxisZ);
    }

    Rectalinear() {
        halfExtent = new Vector(0.5f, 0.5f);
        angle = new SphericalCoord(halfExtent, Linear.AxisZ);
    }

    Vector HalfCorner2D(byte face, byte side) {

        Vector corner = halfExtent;
        switch (face) {

            case Linear.SideBack: {
                corner.y -= corner.y;
                break;
            }
            case Linear.SideFront: {
                corner.y = -corner.y;
                break;
            }

        }
        switch (side) {
            case Linear.SideLeft: {

                corner.x = -corner.x;
                break;
            }
            case Linear.SideRight: {
                corner.x -= corner.x;
                break;
            }

        }
        return corner;
    }

    Vector HalfCorner3D(byte face, byte side, byte base) {

        Vector corner = HalfCorner2D(face, side);
        switch (base) {

            case Linear.SideTop: {
                corner.z -= corner.z;
                break;
            }
            case Linear.SideBottom: {
                corner.z = -corner.z;
                break;
            }

        }
        return corner;
    }

    Box Hull2D() {
        Box h = new Box(center, new Vector(0, 0));
        Vector c;
        c = HalfCorner2D(Linear.SideFront, Linear.SideLeft).Rotate2D(angle.theta).AsVector();
        h.Enlarge2D(c);
        c = HalfCorner2D(Linear.SideBack, Linear.SideRight).Rotate2D(angle.theta).AsVector();
        h.Enlarge2D(c);
        c = HalfCorner2D(Linear.SideFront, Linear.SideRight).Rotate2D(angle.theta).AsVector();
        h.Enlarge2D(c);
        c = HalfCorner2D(Linear.SideBack, Linear.SideLeft).Rotate2D(angle.theta).AsVector();
        h.Enlarge2D(c);
        return h;
    }

    Box Hull3D() {
        Box h = new Box(center, new Vector(0, 0));
        Vector c;
        c = HalfCorner3D(Linear.SideFront, Linear.SideLeft, Linear.SideTop);
        SphericalCoord ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;
        h.Enlarge3D(ang.AtZPole());

        c = HalfCorner3D(Linear.SideBack, Linear.SideRight, Linear.SideBottom);
        ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;

        h.Enlarge3D(ang.AtZPole());
        c = HalfCorner3D(Linear.SideFront, Linear.SideRight, Linear.SideTop);
        ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;

        h.Enlarge3D(ang.AtZPole());
        c = HalfCorner3D(Linear.SideBack, Linear.SideLeft, Linear.SideBottom);
        ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;

        h.Enlarge3D(ang.AtZPole());
        c = HalfCorner3D(Linear.SideBack, Linear.SideRight, Linear.SideTop);
        ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;

        h.Enlarge3D(ang.AtZPole());
        c = HalfCorner3D(Linear.SideFront, Linear.SideLeft, Linear.SideBottom);
        ang = new SphericalCoord(c, Linear.AxisZ);
        ang.phi += angle.phi;
        ang.theta += angle.theta;

        h.Enlarge3D(ang.AtZPole());
        return h;
    }

    Range Hull2D(Range r1, Range r2) {
        return new Range(r1, r2);
    }

    Range Hull3D(Range r1, Range r2, Range r3) {
        Range p1 = new Range(r1, r2);
        Range p2 = new Range(p1, r3);
        return p2;
    }

    boolean Collide2D(Line line) {
        if (super.Collide2D(line)) {
            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Line l2 = new Line();
            double rad = l2.start.r;
            l2.start = line.start.Sub(line.start, center).AsPoint(rad);
            l2.start = l2.start.Rotate2D(-angle.theta).AsPoint(rad);
            l2.start = l2.start.Add(l2.start, halfExtent).AsPoint(rad);
            l2.direction = line.direction.Rotate2D(-angle.theta).AsVector();
            return r.Collide2D(l2);
        }
        return false;
    }

    boolean Collide2D(Segment seg) {
        if (super.Collide2D(seg)) {

            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Segment s2 = new Segment();
            s2.p1 = seg.p1.Sub(seg.p1, center).AsVector();
            s2.p1 = s2.p1.Rotate2D(-angle.theta).AsVector();
            s2.p1 = s2.p1.Add(s2.p1, halfExtent).AsVector();
            s2.p2 = seg.p2.Sub(seg.p2, center).AsVector();
            s2.p2 = s2.p2.Rotate2D(-angle.theta).AsVector();
            s2.p2 = s2.p2.Add(s2.p2, halfExtent).AsVector();
            return r.Collide2D(s2);
        }
        return false;
    }

    boolean Collide3D(Segment seg) {
        if (super.Collide3D(seg)) {

            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Segment s2 = new Segment();
            s2.p1 = seg.p1.Sub(seg.p1, center).AsVector();
            s2.p1 = s2.p1.Rotate3D(-angle.phi, -angle.theta).AsVector();
            s2.p1 = s2.p1.Add(s2.p1, halfExtent).AsVector();
            s2.p2 = seg.p2.Sub(seg.p2, center).AsVector();
            s2.p2 = s2.p2.Rotate3D(-angle.phi, -angle.theta).AsVector();
            s2.p2 = s2.p2.Add(s2.p2, halfExtent).AsVector();
            return r.Collide3D(s2);
        }
        return false;
    }

    boolean Collide3D(Line line) {
        if (super.Collide3D(line)) {

            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Line l2 = new Line();
            double rad = l2.start.r;
            l2.start = line.start.Add(line.start, center).AsPoint(rad);
            l2.start = l2.start.Rotate3D(-angle.phi, -angle.theta).AsPoint(rad);
            l2.start = l2.start.Add(l2.start, halfExtent).AsPoint(rad);
            l2.direction = line.direction.Rotate3D(-angle.phi, -angle.theta).AsVector();
            return r.Collide3D(l2);
        }
        return false;
    }

    boolean Collide2D(Point with) {
        if (super.Collide2D(with)) {

            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Vector p = with.Sub(with, center);
            p = p.Rotate2D(-angle.theta).AsVector();
            p = p.Add(p, halfExtent).AsVector();
            return r.Collide2D(p.AsPoint(with.r));
        }
        return false;
    }

    boolean Collide3D(Point with) {
        if (super.Collide3D(with)) {

            Box r = new Box();
            r.origin.x = 0;
            r.origin.y = 0;
            r.size = halfExtent.Scaled(2);
            Vector p = with.Sub(with, center).AsVector();
            p = p.Rotate3D(angle.phi, angle.theta).AsVector();
            p = p.Add(p, halfExtent).AsVector();
            return r.Collide3D(p.AsPoint(with.r));
        }
        return false;
    }

    Segment Edge2D(byte side) {
        Segment edge = new Segment();
        Vector e1 = new Vector(halfExtent.x, halfExtent.y);
        Vector e2 = new Vector(halfExtent.x, halfExtent.y);
        switch (side) {
            case Linear.SideFront: {
                e1.x = -e1.x;
                break;
            }
            case Linear.SideRight: {
                e2.y = -e2.y;
                break;
            }
            case Linear.SideLeft: {
                e1.y = -e1.y;
                e2 = e2.Negative();
                break;
            }
            case Linear.SideBack: {
                e1 = e1.Negative();
                e2.x = -e2.x;
                break;
            }
        }
        e1 = e1.Rotate2D(angle.theta).AsVector();
        e1 = e1.Add(e1, center).AsVector();
        e2 = e2.Rotate2D(angle.theta).AsVector();
        e2 = e2.Add(e2, center).AsVector();
        edge.p1 = e1;
        edge.p2 = e2;
        return edge;
    }

    Segment Edge3D(Point at, byte side) {
        Segment edge = new Segment();
        Vector e1 = new Vector(at.x, at.y);
        Vector e2 = new Vector(at.x, at.y);
        switch (side) {
            case Linear.SideFront: {
                e1.x = -e1.x;
                break;
            }
            case Linear.SideRight: {
                e2.y = -e2.y;
                break;
            }
            case Linear.SideTop: {
                e2.z = -e2.z;
                break;
            }
            case Linear.SideBottom: {
                e1 = e1.Negative();
                e2.z = -e2.z;
                break;
            }
            case Linear.SideLeft: {
                e1.y = -e1.y;
                e2 = e2.Negative();
                break;
            }
            case Linear.SideBack: {
                e1 = e1.Negative();
                e2.x = -e2.x;
                break;
            }
        }
        e1 = e1.Add(e1, center).AsVector();
        e2 = e2.Add(e2, center).AsVector();
        edge.p1 = e1;
        edge.p2 = e2;
        return edge;
    }

    boolean SeparatingAxis2D(Segment axis) {
        Range ar = new Range();
        Range r0 = new Range();
        Range r2 = new Range();
        Range project = new Range();
        Segment e0 = Edge2D(Linear.SideFront);
        Segment e2 = Edge2D(Linear.SideLeft);
        Vector n = axis.p1.Sub(axis.p1, axis.p2).AsVector();
        ar = axis.Project2D(n);
        r0 = e0.Project2D(n);
        r2 = e2.Project2D(n);
        project = Hull2D(r0, r2);
        return !ar.Overrlapping(project);

    }

    boolean SeparatingAxis3D(Segment axis) {
        Range ar = new Range();
        Range r0 = new Range();
        Range r1 = new Range();
        Range r2 = new Range();
        Range pro = new Range();

        Segment e0 = Edge2D(Linear.SideFront);
        Segment e1 = Edge2D(Linear.SideLeft);
        Segment e2 = Edge2D(Linear.SideTop);
        Vector n = axis.p1.Sub(axis.p1, axis.p2).AsVector();
        ar = axis.Project3D(n);
        r0 = e0.Project3D(n);
        r1 = e1.Project3D(n);
        r2 = e2.Project3D(n);
        pro = Hull3D(r0, r1, r2);
        return !ar.Overrlapping(pro);

    }

    boolean Collide2D(Rectalinear with) {
        Segment edge = Edge2D(Linear.SideFront);
        if (with.SeparatingAxis2D(edge)) {
            return false;
        }
        edge = Edge2D(Linear.SideLeft);
        if (with.SeparatingAxis2D(edge)) {
            return false;
        }
        edge = Edge2D(Linear.SideRight);
        if (with.SeparatingAxis2D(edge)) {
            return false;
        }
        edge = Edge2D(Linear.SideBack);
        return !with.SeparatingAxis2D(edge);

    }

    boolean Collide3D(Rectalinear with) {
        Range cz = new Range(center.z - halfExtent.z, center.z + halfExtent.z);
        Range czw = new Range(with.center.z - with.halfExtent.z, with.center.z + with.halfExtent.z);
        if (cz.Overrlapping(czw)) {

            angle.p = radius;
            Point at = angle.AtZPole().AsPoint(1);
            Segment edge = Edge3D(at, Linear.SideFront);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }
            edge = Edge3D(at, Linear.SideLeft);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }
            edge = Edge3D(at, Linear.SideTop);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }
            edge = Edge3D(at, Linear.SideRight);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }
            edge = Edge3D(at, Linear.SideBack);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }
            edge = Edge3D(at, Linear.SideBottom);
            if (with.SeparatingAxis3D(edge)) {
                return false;
            }

            return true;
        }

        return false;
    }

    boolean CollideWith2D(Circle with) {
        if (super.Collide2D(with)) {
            Box r = new Box();
            r.size = new Vector(halfExtent.x * 2, halfExtent.y * 2);
            Circle c = new Circle();
            c.radius = with.radius;
            Vector d = with.center.Sub(with.center, center);
            d = d.Rotate2D(-angle.theta).AsVector();
            c.center = (d.Add(d, halfExtent)).AsPoint(c.radius);
            return r.Collide2D(c);
        }
        return false;
    }

    boolean CollideWith3D(Circle with) {
        if (super.Collide3D(with)) {
            Box r = new Box();
            r.size = new Vector(halfExtent.x * 2, halfExtent.y * 2, halfExtent.z * 2);
            Circle c = new Circle();
            c.radius = with.radius;
            Vector d = with.center.Sub(with.center, center);
            Vector at = angle.AtZPole();
            c.center = d.Sub(d, at).AsPoint(c.center.r);
            c.center = c.center.Add(c.center, halfExtent).AsPoint(c.radius);
            return r.Collide2D(c);
        }
        return false;
    }

    boolean CollideWith2D(Rectalinear with) {
        if (super.Collide2D(with)) {
            return Collide2D(with);
        }
        return false;
    }

    boolean CollideWith3D(Rectalinear with) {
        if (super.Collide3D(with)) {
            return Collide3D(with);
        }
        return false;
    }
};

class BoundingBox extends Geometrical {

    Vector pMin;
    Vector pMax;

    BoundingBox() {
        pMin = new Vector(Linear.MAX_MAP, Linear.MAX_MAP, Linear.MAX_MAP);
        pMax = new Vector(-Linear.MAX_MAP, -Linear.MAX_MAP, -Linear.MAX_MAP);
    }

    BoundingBox(Point p) {
        pMin = new Vector(p.x, p.y, p.z, p.r);
        pMax = new Vector(p.x, p.y, p.z, p.r);
    }

    BoundingBox(Point p1, Point p2) {
        pMin = new Vector(Math.min(p1.x, p2.x),
                Math.min(p1.y, p2.y),
                Math.min(p1.z, p2.z));
        pMax = new Vector(Math.max(p1.x, p2.x),
                Math.max(p1.y, p2.y),
                Math.max(p1.z, p2.z));
    }

    BoundingBox(double mx, double my, double mz) {
        pMin = new Vector(-mx, -my, -mz);
        pMax = new Vector(mx, my, mz);
    }

    BoundingBox(double mix, double miy, double miz, double max, double may, double maz) {
        pMin = new Vector(-mix, -miy, -miz);
        pMax = new Vector(max, may, maz);
    }

    @Override
    public CollisionDetector ToDetector() {
        Point at = new Point(pMax.x - pMin.x, pMax.y - pMin.y);
        at.r = pMax.Length2D();
        return new CollisionDetector(at);
    }

    @Override
    public CollisionDetector ToDetector(float mass) {
        Point at = new Point(pMax.x - pMin.x, pMax.y - pMin.y);
        at.r = pMax.Length2D();

        return new CollisionDetector(at, mass);

    }

    @Override
    public CollisionDetector ToDetector(float mass, float radius) {
        Point at = new Point(pMax.x - pMin.x, pMax.y - pMin.y);
        at.r = pMax.Length2D();

        return new CollisionDetector(at, mass, radius);
    }

    boolean Overlaps(BoundingBox b) {
        boolean x = (pMax.x >= b.pMin.x) && (pMin.x <= b.pMax.x);
        boolean y = (pMax.y >= b.pMin.y) && (pMin.y <= b.pMax.y);
        boolean z = (pMax.z >= b.pMin.z) && (pMin.z <= b.pMax.z);
        return (x && y && z);
    }

    boolean Inside(Point pt) {
        return (pt.x >= pMin.x && pt.x <= pMax.x
                && pt.y >= pMin.y && pt.y <= pMax.y
                && pt.z >= pMin.z && pt.z <= pMax.z);
    }

    boolean Inside(Point pt, double r) {
        return (pt.x - r >= pMin.x && pt.x + r <= pMax.x
                && pt.y - r >= pMin.y && pt.y + r <= pMax.y
                && pt.z - r >= pMin.z && pt.z + r <= pMax.z);
    }

    boolean Inside(Point loc,Point pt, double r) {
        return (pt.x - r >= pMin.x+loc.x && pt.x + r <= pMax.x+loc.x
                && pt.y - r >= pMin.y+loc.y && pt.y + r <= pMax.y+loc.y
                && pt.z - r >= pMin.z+loc.z && pt.z + r <= pMax.z+loc.z);
    }

    void Expand(double delta) {
        pMin = new Vector(pMin.x - delta, pMin.y - delta, pMin.z - delta);
        pMax = new Vector(pMax.x + delta, pMax.y + delta, pMax.z + delta);
    }

    double Volume() {
        Vector d = pMax.Sub(pMax, pMin).AsVector();
        return d.x * d.y * d.z;
    }

    int MaximumExtent() {
        Vector diag = pMax.Sub(pMax, pMin).AsVector();
        if (diag.x > diag.y && diag.x > diag.z) {
            return 0;
        } else if (diag.y > diag.z) {
            return 1;
        } else {
            return 2;
        }
    }
}
