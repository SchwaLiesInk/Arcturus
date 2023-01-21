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
public class Box extends Geometrical {

    protected Point origin;
    protected Vector size;

    public Box(Point from, Vector sz) {
        origin = from;
        size = sz.Absolute();
    }

    public Box(double fromx, double fromy, double tox, double toy) {
        origin = new Point(fromx, fromy);
        size = new Vector(tox - fromx, toy - fromy);
    }

    public Box(int x, int y, int w, int h) {
        origin = new Point(x, y);
        size = new Vector(w, h);
    }

    public Box() {
        origin = new Point(-0.5f, -0.5f);
        size = new Vector(1, 1);
    }

    @Override
    public CollisionDetector ToDetector() {
        Point at = new Point(origin.x + size.x * 0.5, origin.y + size.y * 0.5);
        at.r = size.Length2D() * 0.5;
        return new CollisionDetector(at);
    }

    @Override
    public CollisionDetector ToDetector(float mass) {
        Point at = new Point(origin.x + size.x * 0.5, origin.y + size.y * 0.5);
        at.r = size.Length2D() * 0.5;
        return new CollisionDetector(at, mass);

    }

    @Override
    public CollisionDetector ToDetector(float mass, float radius) {
        Point at = new Point(origin.x + size.x * 0.5, origin.y + size.y * 0.5);
        at.r = radius;
        return new CollisionDetector(at, mass, radius);
    }

    public Box Enlarge2D(Vector with) {
        Box e = new Box();
        e.origin.x = Math.min(origin.x, with.x);
        e.origin.y = Math.min(origin.y, with.y);
        e.size.x = Math.max(origin.x + size.x, with.x);
        e.size.y = Math.max(origin.y + size.y, with.y);
        e.size = e.size.Sub(e.size, e.origin).AsVector();
        return e;
    }

    public Box Enlarge3D(Vector with) {
        Box e = new Box();
        e.origin.x = Math.min(origin.x, with.x);
        e.origin.y = Math.min(origin.y, with.y);
        e.origin.z = Math.min(origin.z, with.z);
        e.size.x = Math.max(origin.x + size.x, with.x);
        e.size.y = Math.max(origin.y + size.y, with.y);
        e.size.z = Math.max(origin.z + size.z, with.z);
        e.size = e.size.Sub(e.size, e.origin).AsVector();
        return e;
    }

    public Vector Corner2D(byte face, byte side) {
        Vector corner = origin.AsVector();
        switch (face) {

            case Linear.SideBack: {
                corner.y += size.y;
                break;
            }
            case Linear.SideFront: {
                break;
            }

        }
        switch (side) {
            case Linear.SideLeft: {
                break;
            }
            case Linear.SideRight: {
                corner.x += size.x;
                break;
            }

        }
        return corner;

    }

    public Vector Corner3D(byte face, byte side, byte base) {
        Vector corner = Corner2D(face, side);
        switch (base) {
            case Linear.SideTop: {
                corner.z += size.z;
                break;
            }
        }
        return corner;

    }

    public boolean SeparatingAxis2D(Segment axis) {
        Segment e1 = new Segment();
        Segment e2 = new Segment();
        Range r = new Range();
        Range r1 = new Range();
        Range r2 = new Range();
        Range pro = new Range();
        Vector n = axis.p1.Sub(axis.p1, axis.p2).AsVector();
        e1.p1 = Corner2D(Linear.SideFront, Linear.SideLeft);
        e1.p2 = Corner2D(Linear.SideBack, Linear.SideRight);
        e2.p1 = Corner2D(Linear.SideBack, Linear.SideLeft);
        e2.p2 = Corner2D(Linear.SideFront, Linear.SideRight);
        r1 = e1.Project2D(n);
        r2 = e2.Project2D(n);
        pro = new Range(r1, r2);
        r = axis.Project2D(n);
        return !r.Overrlapping(pro);

    }

    public boolean SeparatingAxis3D(Segment axis) {
        Segment e1 = new Segment();
        Segment e2 = new Segment();
        Segment e3 = new Segment();
        Range r = new Range();
        Range r1 = new Range();
        Range r2 = new Range();
        Range r3 = new Range();
        Range pro = new Range();
        Vector n = axis.p1.Sub(axis.p1, axis.p2).AsVector();
        e1.p1 = Corner3D(Linear.SideFront, Linear.SideLeft, Linear.SideTop);
        e1.p2 = Corner3D(Linear.SideBack, Linear.SideRight, Linear.SideBottom);
        e2.p1 = Corner3D(Linear.SideBack, Linear.SideLeft, Linear.SideBottom);
        e2.p2 = Corner3D(Linear.SideFront, Linear.SideRight, Linear.SideTop);
        e3.p1 = Corner3D(Linear.SideFront, Linear.SideLeft, Linear.SideBottom);
        e3.p2 = Corner3D(Linear.SideBack, Linear.SideRight, Linear.SideTop);
        r1 = e1.Project3D(n);
        r2 = e2.Project3D(n);
        r3 = e3.Project3D(n);
        pro = new Range(new Range(r1, r2), r3);
        r = axis.Project2D(n);
        return !r.Overrlapping(pro);
    }

    public boolean Collide2D(Box with) {
        Point extx = new Point(origin.x, origin.x + size.x);
        Point wextx = new Point(with.origin.x, with.origin.x + with.size.x);
        Point exty = new Point(origin.y, origin.y + size.y);
        Point wexty = new Point(with.origin.y, with.origin.y + with.size.y);
        return Linear.Overlapping2D(extx, wextx) && Linear.Overlapping2D(exty, wexty);
    }

    public boolean Collide3D(Box with) {
        Point extx = new Point(origin.x, origin.x + size.x);
        Point wextx = new Point(with.origin.x, with.origin.x + with.size.x);
        Point exty = new Point(origin.y, origin.y + size.y);
        Point wexty = new Point(with.origin.y, with.origin.y + with.size.y);
        Point extz = new Point(origin.z, origin.z + size.z);
        Point wextz = new Point(with.origin.z, with.origin.z + with.size.z);
        return Linear.Overlapping2D(extx, wextx) && Linear.Overlapping2D(exty, wexty) && Linear.Overlapping2D(extz, wextz);
    }

    public Point Clamp2D(Point p) {
        Range x = new Range(origin.x, origin.x + size.x);
        Range y = new Range(origin.y, origin.y + size.y);
        return new Point(x.Clamp(p.x), y.Clamp(p.y));
    }

    public Point Clamp3D(Point p) {
        Range x = new Range(origin.x, origin.x + size.x);
        Range y = new Range(origin.y, origin.y + size.y);
        Range z = new Range(origin.z, origin.z + size.z);
        return new Point(x.Clamp(p.x), y.Clamp(p.y), z.Clamp(p.z));
    }

    public boolean Collide2D(Point p) {
        double left = origin.x;
        double right = origin.x + size.x;
        double back = origin.y;
        double front = origin.y + size.y;

        if (p.x < left) {
            return false;
        } else if (p.x > right) {
            return false;
        } else if (p.y > front) {
            return false;
        } else if (p.y < back) {
            return false;
        }
        //System.Console.WriteLine("TEST" + p.x + " " + p.y + " " + left + " " + right + " " + back + " " + front);
        return true;
    }

    public boolean Collide3D(Point p) {
        double left = origin.x;
        double right = origin.x + size.x;
        double back = origin.y;
        double front = origin.y + size.y;
        double bottom = origin.z;
        double top = origin.z + size.z;

        if (p.x < left) {
            return false;
        } else if (p.x > right) {
            return false;
        } else if (p.y > front) {
            return false;
        } else if (p.y < back) {
            return false;
        } else if (p.z > top) {
            return false;
        } else if (p.z < bottom) {
            return false;
        } else {
            return true;
        }
    }

    public boolean Collide2D(Line ln) {
        Cartesian n = ln.direction.Rotate90Degrees2D();
        double dp1, dp2, dp3, dp4;
        Point c1 = origin;
        Point c2 = origin.Add(origin, size).AsPoint(origin.r);
        Point c3 = new Point(c2.x, c1.y);
        Point c4 = new Point(c1.x, c2.y);
        Vector v1 = c1.Sub(c1, ln.start);
        Vector v2 = c2.Sub(c2, ln.start);
        Vector v3 = c3.Sub(c3, ln.start);
        Vector v4 = c4.Sub(c4, ln.start);
        dp1 = n.DotProduct2D(n, v1);
        dp2 = n.DotProduct2D(n, v2);
        dp3 = n.DotProduct2D(n, v3);
        dp4 = n.DotProduct2D(n, v4);
        return (dp1 * dp2 <= 0) || (dp2 * dp3 <= 0) || (dp3 * dp4 <= 0);
    }

    public boolean Collide3D(Line ln) {
        Vector n = ln.start.CrossProduct(ln.start, ln.direction).Unity().AsVector();
        double dp1, dp2, dp3, dp4, dp5, dp6;
        Point c1 = origin;
        Point c2 = origin.Add(origin, size).AsPoint(origin.r);
        Point c3 = new Point(c2.x, c1.y, c1.z);
        Point c4 = new Point(c1.x, c2.y, c1.z);
        Point c5 = new Point(c2.x, c1.y, c2.z);
        Point c6 = new Point(c1.x, c2.y, c2.z);
        Vector v1 = c1.Sub(c1, ln.start);
        Vector v2 = c2.Sub(c2, ln.start);
        Vector v3 = c3.Sub(c3, ln.start);
        Vector v4 = c4.Sub(c4, ln.start);
        Vector v5 = c5.Sub(c5, ln.start);
        Vector v6 = c6.Sub(c6, ln.start);
        dp1 = n.DotProduct(n, v1);
        dp2 = n.DotProduct(n, v2);
        dp3 = n.DotProduct(n, v3);
        dp4 = n.DotProduct(n, v4);
        dp5 = n.DotProduct(n, v5);
        dp6 = n.DotProduct(n, v6);
        return (dp1 * dp2 <= 0) || (dp2 * dp3 <= 0) || (dp3 * dp4 <= 0) || (dp4 * dp5 <= 0) || (dp5 * dp6 <= 0);
    }

    public boolean Collide2D(Segment seg) {
        Line line = new Line();
        line.start = seg.p1.AsPoint(1);
        line.direction = seg.p2.Sub(seg.p2, seg.p1).Unity2D().AsVector();
        if (!Collide2D(line)) {
            return false;
        }
        Range r = new Range();
        Range s = new Range();
        r.minim = origin.x;
        r.maxim = origin.x + size.x;
        s.minim = seg.p1.x;
        s.maxim = seg.p2.x;
        s = new Range(r, s);
        if (!r.Overrlapping(s)) {
            return false;
        }
        r.minim = origin.y;
        r.maxim = origin.y + size.y;
        s.minim = seg.p1.y;
        s.maxim = seg.p2.y;
        s = new Range(r, s);
        if (!r.Overrlapping(s)) {
            return false;
        }
        return true;
    }

    public boolean Collide3D(Segment seg) {
        Line line = new Line();
        line.start = seg.p1.AsPoint(1);
        line.direction = seg.p2.Sub(seg.p2, seg.p1).Unity().AsVector();
        if (!Collide3D(line)) {
            return false;
        }
        Range r = new Range();
        Range s = new Range();
        r.minim = origin.x;
        r.maxim = origin.x + size.x;
        s.minim = seg.p1.x;
        s.maxim = seg.p2.x;
        s = new Range(r, s);
        if (!r.Overrlapping(s)) {
            return false;
        }
        r.minim = origin.y;
        r.maxim = origin.y + size.y;
        s.minim = seg.p1.y;
        s.maxim = seg.p2.y;
        s = new Range(r, s);
        if (!r.Overrlapping(s)) {
            return false;
        }
        r.minim = origin.z;
        r.maxim = origin.z + size.z;
        s.minim = seg.p1.z;
        s.maxim = seg.p2.z;
        s = new Range(r, s);
        if (!r.Overrlapping(s)) {
            return false;
        }
        return true;
    }

    public boolean Collide2D(Circle circ) {
        Point clamp = Clamp2D(circ.center);
        return circ.Collide2D(clamp);
    }

    public boolean Collide3D(Circle circ) {
        Point clamp = Clamp3D(circ.center);
        return circ.Collide3D(clamp);
    }

    public Box Add(Box p, Vector offset) {
        Point at = p.origin.Add(p.origin, offset).AsPoint(p.origin.r);
        return new Box(at, p.size);
    }

    public Box Sub(Box p, Point offset) {
        Cartesian at = p.origin.Sub(p.origin, offset);

        return new Box(at.AsPoint(p.origin.r), p.size);
    }
};
