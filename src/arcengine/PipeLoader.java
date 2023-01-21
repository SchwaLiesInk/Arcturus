/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 * @author Gerwyn Jones
 */
class ZSorter implements java.util.Comparator<Drawable> {

    @Override
    public int compare(Drawable a, Drawable b) {
        if (a != null && b != null) {
            if (a.Center().z < b.Center().z) {
                return 1;
            } else if (a.Center().z > b.Center().z) {
                return -1;
            }
        }
        return 0;
    }

}

public abstract class PipeLoader<T> extends RingList<T> implements Collector<T, RingList<Vertex2D>, RingList<Drawable>> {

    Screen screen;
    DrawType type;
    DrawType subtype;
    double iz;
    double scale;
    double minimumRadius;
    Vector offset;
    int s;//stride counter
    int stride = 1;
    Vertex2D from;
    Vertex2D to;
    Quarternion rot;
    int zedz;
    Vector tezz;

    //
    abstract Vertex2D Transform(T p);

    abstract void Add(RingList<Vertex2D> list, Vertex2D add);

    public abstract void AddTransform(RingList<Vertex2D> list, T p);

    abstract Vertex2D Result(T p, Vector pos);

    abstract Vector LinearTransform(T p);

    abstract boolean IsoTransform(Vector pos);

    abstract boolean InterTransform(Vector pos);

    abstract boolean ExaTransform(Vector pos);

    abstract void Offset(Vector pos);

    public abstract void PipeStream(RingList<Drawable> load);

    public abstract void PumpStream(RingList<Drawable> load);

    public abstract void PipeStream();

    public abstract void PumpStream();

}
//UNTESTED

class PipeTriangleLoader extends PipeLoader<Tessa> {

    Point objAt;
    RingList<Tessa> tri;
    RingList<Light> lights;
    Vector vn;
    Point v0;//center

    PipeTriangleLoader(Screen scr, Vector offset, Pigment color, Quarternion rot, double scale, double minimumRadius, DrawType type, DrawType stype) {
        this.screen = scr;
        this.offset = offset;
        this.rot = rot;
        this.scale = scale;
        this.minimumRadius = minimumRadius;
        this.type = type;
        this.subtype = stype;

        stride = 3;
    }

    @Override
    Vertex2D Transform(Tessa p) {

        Vector pos = LinearTransform(p);
        switch (screen.cam.viewer) {
            case Isometric: {
                if (IsoTransform(pos)) {
                    return Result(p, pos);
                }
            }
            case Internal: {
                if (InterTransform(pos)) {

                    return Result(p, pos);
                }
            }
            case External: {
                if (ExaTransform(pos)) {
                    return Result(p, pos);
                }
            }
        }
        return null;
    }

    @Override
    void Add(RingList<Vertex2D> list, Vertex2D add) {
        if (add != null) {
            s++;
            if (s == 1) {
                to = from = add;
            }
            if (s <= stride) {
                add.prev = to;
                to.next = add;
                to = add;
            }
            if (s == stride) {
                list.add(from);
                from = to = null;
                s = 0;
            }
        } else if (s != 0) {
            for (int i = 0; i < s; i++) {
                list.Trim();
            }
            from = to = null;
            s = 0;
        }
    }

    @Override
    public void AddTransform(RingList<Vertex2D> list, Tessa p) {
        Add(list, Transform(p));
    }
    /*Point v = new Point(cam.x - tri.center.x, cam.y - tri.center.y, cam.z - tri.center.z);

     Normal n = tri.Norm(0);
     if (tri.rotation != null) {
     v = tri.rotation.Rotate(v);
     n = tri.rotation.Rotate(n);
     }
     if (cam.linear != null) {
     n = cam.linear.Mult(cam.linear, n.AsVector()).AsNormal();
     }
     if (n.Dot(n, v) > 0) {

     double iz1 = 0;
     double iz2 = 0;
     double iz3 = 0;
     Vector pos1 = tri.vert[0].v.AsVector();
     Vector pos2 = tri.vert[1].v.AsVector();
     Vector pos3 = tri.vert[2].v.AsVector();
     Point v0 = tri.Center();
     Vector vn = new Vector(n.AsVector());

     if (cam.linear != null) {
     v0 = cam.linear.Mult(cam.linear, v0.AsVector()).AsPoint(v0.r);
     vn = cam.linear.Mult(cam.linear, vn);
     pos1 = cam.linear.Mult(cam.linear, pos1);
     pos2 = cam.linear.Mult(cam.linear, pos2);
     pos3 = cam.linear.Mult(cam.linear, pos3);
     }

     switch (cam.viewer) {
     case Isometric: {
     pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);
     pos2 = cam.viewProjection.Mult(cam.viewProjection, pos2);
     pos3 = cam.viewProjection.Mult(cam.viewProjection, pos3);
     double z1 = pos1.z;
     double z2 = pos2.z;
     double z3 = pos3.z;
     //
     iz1 = cam.zoom;
     iz2 = cam.zoom;
     iz3 = cam.zoom;
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos3 = pos3.Scaled(iz3);
     pos1 = pos1.Add(pos1, Center()).AsVector();
     pos2 = pos1.Add(pos1, Center()).AsVector();
     pos3 = pos1.Add(pos1, Center()).AsVector();

     pos1.z = z1;
     pos2.z = z2;
     pos3.z = z3;
     break;
     }
     case Internal: {

     pos1 = cam.projectionView.Mult(cam.projectionView, pos1);
     if (pos1.z < cam.near) {
     return;
     }
     pos2 = cam.projectionView.Mult(cam.projectionView, pos2);
     if (pos2.z < cam.near) {
     return;
     }
     pos3 = cam.projectionView.Mult(cam.projectionView, pos3);
     if (pos3.z < cam.near) {
     return;
     }

     double z1 = pos1.z;
     double z2 = pos2.z;
     double z3 = pos3.z;
     double zi = (1.0f / (pos1.z * pos1.w));
     if (zi < 0) {
     return;
     }
     iz1 = zi * cam.zoom;
     zi = (1.0f / (pos2.z * pos2.w));
     if (zi < 0) {
     return;
     }
     iz2 = zi * cam.zoom;
     zi = (1.0f / (pos3.z * pos3.w));
     if (zi < 0) {
     return;
     }
     iz3 = zi * cam.zoom;
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos3 = pos3.Scaled(iz3);
     pos1 = cam.screen.Mult(cam.screen, pos1);
     pos2 = cam.screen.Mult(cam.screen, pos2);
     pos3 = cam.screen.Mult(cam.screen, pos3);

     //System.Console.WriteLine("Loading Triangle " + z1 + " " + z2 + " " + z3);
     pos1.z = z1;
     pos2.z = z2;
     pos3.z = z3;
     break;
     }
     case External: {
     pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);

     pos2 = cam.viewProjection.Mult(cam.viewProjection, pos2);

     pos3 = cam.viewProjection.Mult(cam.viewProjection, pos3);

     double z1 = pos1.z;
     double z2 = pos2.z;
     double z3 = pos3.z;
     iz1 = (1.0f + pos1.z * 0.001f) * cam.zoom;
     if (iz1 < cam.near) {
     return;
     }
     iz2 = (1.0f + pos2.z * 0.001f) * cam.zoom;
     if (iz2 < cam.near) {
     return;
     }
     iz3 = (1.0f + pos3.z * 0.001f) * cam.zoom;
     if (iz3 < cam.near) {
     return;
     }
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos3 = pos3.Scaled(iz3);
     pos1 = pos1.Add(pos1, Center()).AsVector();
     pos2 = pos2.Add(pos2, Center()).AsVector();
     pos3 = pos3.Add(pos3, Center()).AsVector();
     pos1.z = z1;
     pos2.z = z2;
     pos3.z = z3;
     //at.y *= 0.25f;
     break;
     }
     }
     if ((pos1.x > origin.x && pos1.x < edge.x && pos1.y > origin.y && pos1.y < edge.y) || (pos2.x > origin.x && pos2.x < edge.x && pos2.y > origin.y && pos2.y < edge.y)
     || (pos3.x > origin.x && pos3.x < edge.x && pos3.y > origin.y && pos3.y < edge.y)) {

     double rad = tri.Center().r * iz1 * scale;
     if (rad < minimumRadius) {
     rad = minimumRadius;
     }
     pos3.w = pos2.w = pos1.w = rad;
     Pigment c = new Pigment(tri.color);
     if (lights != null) {
     Light lit = lights.First();
     int r = 0;
     int g = 0;
     int b = 0;
     while (lit != null) {
     Pigment lp = lit.Effect(lit, v0, n, c);
     r += lp.Red();
     g += lp.Green();
     r += lp.Blue();
     lit = lights.Next();
     }
     if (lights.Length() > 1) {
     r /= lights.Length();
     g /= lights.Length();
     b /= lights.Length();
     }
     c = new Pigment(c.Alpha(), r, g, b);
     }
     Pigment c1 = c;
     Pigment c2 = c;
     Pigment c3 = c;

     Vertex2D v2d = new Vertex2D(pos1.x, pos1.y, pos1.z, pos1.w, tri.GetGraphicsIndex());
     v2d.SetDrawing(tri.center, type, stype, v2d, c1, tri.GetGraphicsIndex());
     Vertex2D v2d2 = new Vertex2D(pos2.x, pos2.y, pos2.z, pos2.w, tri.GetGraphicsIndex());
     v2d2.SetDrawing(tri.center, type, stype, v2d2, c2, tri.GetGraphicsIndex());
     Vertex2D v2d3 = new Vertex2D(pos3.x, pos3.y, pos3.z, pos3.w, tri.GetGraphicsIndex());
     v2d3.SetDrawing(tri.center, type, stype, v2d3, c3, tri.GetGraphicsIndex());
     //
     v2d.SetNext(v2d2);
     v2d2.SetNext(v2d3);
     //
     load.Append(v2d);
     //System.Console.WriteLine("Line");
     }
     }*/

    @Override
    Vertex2D Result(Tessa tri, Vector pos) {

        //
        if (pos.x > screen.origin.x && pos.x < screen.edge.x && pos.y > screen.origin.y && pos.y < screen.edge.y) {

            double rad = tri.center.r * iz * scale;
            if (rad < minimumRadius) {
                rad = minimumRadius;
            }
            pos.w = rad;
            Pigment c = new Pigment(tri.color);
            if (s == 0 && lights != null) {
                Light lit = lights.First();
                int r = 0;
                int g = 0;
                int b = 0;
                while (lit != null) {
                    Pigment lp = lit.Effect(lit, v0, vn.AsNormal(), c);
                    r += lp.Red();
                    g += lp.Green();
                    r += lp.Blue();
                    lit = lights.Next();
                }
                if (lights.Length() > 1) {
                    r /= lights.Length();
                    g /= lights.Length();
                    b /= lights.Length();
                }
                c = new Pigment(c.Alpha(), r, g, b);
            }

            Vertex2D v2d = new Vertex2D(pos.x, pos.y, pos.z, pos.w, tri.GetGraphicsIndex());
            v2d.SetDrawing(tri.center, type, subtype, v2d, c, tri.GetGraphicsIndex());
            return v2d;
            //System.Console.WriteLine("Line");
        }
        return null;
    }

    @Override
    Vector LinearTransform(Tessa tri) {
        Point v = new Point(screen.cam.x - tri.center.x, screen.cam.y - tri.center.y, screen.cam.z - tri.center.z);

        Normal n = tri.Norm(0);
        if (tri.rotation != null) {
            v = tri.rotation.Rotate(v);
            n = tri.rotation.Rotate(n);
        }
        if (screen.cam.linear != null) {
            n = screen.cam.linear.Mult(screen.cam.linear, n.AsVector()).AsNormal();
        }
        if (n.Dot(n, v) > 0) {
            Vector pos = tri.vert[s].v.AsVector();
            v0 = tri.Center();
            vn = new Vector(n.AsVector());

            if (screen.cam.linear != null) {
                v0 = screen.cam.linear.Mult(screen.cam.linear, v0.AsVector()).AsPoint(v0.r);
                vn = screen.cam.linear.Mult(screen.cam.linear, vn);
                pos = screen.cam.linear.Mult(screen.cam.linear, pos);
            }
            return pos;
        }
        return null;
    }

    @Override
    boolean IsoTransform(Vector pos) {
        pos = screen.cam.viewProjection.Mult(screen.cam.viewProjection, pos);
        double z = pos.z;
        //
        iz = screen.cam.zoom;
        pos = pos.Scaled(iz);
        pos = pos.Add(pos, screen.Center()).AsVector();
        pos.z = z;
        Offset(pos);
        return true;
    }

    @Override
    boolean InterTransform(Vector pos) {

        pos = screen.cam.projectionView.Mult(screen.cam.projectionView, pos);
        if (pos.z < screen.cam.near) {
            return false;
        }

        double zi = (1.0f / (pos.z * pos.w));
        if (zi < 0) {
            return false;
        }
        pos = pos.Scaled(iz);
        pos = screen.cam.screen.Mult(screen.cam.screen, pos);
        if (zedz == 0) {
            tezz = pos;
            pos.z = zi;//only first vertex hold z sort value info
        } else {
            tezz.z += zi;
            if (zedz == 2) {
                zedz = 0;
            }
        }
        zedz++;
        Offset(pos);
        return true;
    }

    @Override
    boolean ExaTransform(Vector pos) {

        pos = screen.cam.viewProjection.Mult(screen.cam.viewProjection, pos);

        double z1 = pos.z;
        iz = (1.0f + pos.z * 0.001f) * screen.cam.zoom;
        if (iz < screen.cam.near) {
            return false;
        }
        pos = pos.Scaled(iz);
        pos = pos.Add(pos, screen.Center()).AsVector();
        pos.z = z1;
        Offset(pos);
        return true;
    }

    @Override
    void Offset(Vector pos) {

        pos.x = pos.x + offset.x;
        pos.y = pos.y + offset.y;
    }

    @Override
    public Supplier<RingList<Vertex2D>> supplier() {
        return RingList::new;
    }

    @Override
    public BiConsumer<RingList<Vertex2D>, Tessa> accumulator() {
        return this::AddTransform;
    }

    @Override
    public Function finisher() {
        return Function.identity();
    }

    @Override
    public BinaryOperator<RingList<Vertex2D>> combiner() {
        return (list1, list2) -> {
            list1.PasteAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH, Collector.Characteristics.CONCURRENT));
    }

    @Override
    public void PipeStream(RingList<Drawable> load) {

        java.util.stream.Stream<Tessa> str;
        str = java.util.stream.Stream.generate(tri::Get).limit(tri.length);
        load = str.collect(this);
        str.close();
    }

    @Override
    public void PumpStream(RingList<Drawable> load) {

        java.util.stream.Stream<Tessa> str;
        str = java.util.stream.Stream.generate(tri::Get).limit(tri.length);
        load.PasteAll(str.collect(this));
        str.close();
    }

    @Override
    public void PipeStream() {

        java.util.stream.Stream<Tessa> str;
        str = java.util.stream.Stream.generate(tri::Get).limit(tri.length);
        screen.renderList = str.collect(this);
        str.close();
    }

    @Override
    public void PumpStream() {
        java.util.stream.Stream<Tessa> str;
        str = java.util.stream.Stream.generate(tri::Get).limit(tri.length);
        screen.renderList.PasteAll(str.collect(this));
        str.close();
    }
}

class PipeLineLoader extends PipeLoader<Point> {

    RingList<Point> points = new RingList();

    Pigment color;
    int index;

    PipeLineLoader(Screen scr, Vector offset, Pigment color, Quarternion rot, double scale, double minimumRadius, DrawType type, DrawType stype, int index) {
        this.screen = scr;
        this.offset = offset;
        this.color = color;
        this.rot = rot;
        this.scale = scale;
        this.minimumRadius = minimumRadius;
        this.type = type;
        this.subtype = stype;
        this.index = index;
        switch (type) {
            case Point: {
                stride = 1;
                break;
            }
            case Circle: {
                stride = 1;
                break;
            }
            case Line: {
                stride = 2;
                break;
            }
            case Triangle: {
                stride = 3;
                break;
            }
            case Quad: {
                stride = 4;
                break;
            }
            case Voxel: {
                stride = 1;
                break;
            }
            case Text: {
                stride = 1;
                break;
            }
        }
    }

    /*
    
     double iz1 = 0;
     double iz2 = 0;

     Vector pos1 = from.AsVector();
     Vector pos2 = to.AsVector();
     if (rot != null) {
     pos1 = rot.Rotate(pos1);
     pos2 = rot.Rotate(pos2);
     }

     if (cam.linear != null) {
     pos1 = cam.linear.Mult(cam.linear, pos1);
     pos2 = cam.linear.Mult(cam.linear, pos2);
     }
     switch (cam.viewer) {
     case Isometric: {
     pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);

     double z1 = pos1.z;
     pos2 = cam.viewProjection.Mult(cam.viewProjection, pos2);
     double z2 = pos2.z;
     iz1 = cam.zoom;
     iz2 = cam.zoom;
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos1 = pos1.Add(pos1, Center()).AsVector();
     pos2 = pos2.Add(pos2, Center()).AsVector();
     pos1.z = z1;
     pos2.z = z2;

     break;
     }
     case Internal: {

     pos1 = cam.projectionView.Mult(cam.projectionView, pos1);
     if (pos1.z < cam.near) {
     return;
     }
     pos2 = cam.projectionView.Mult(cam.projectionView, pos2);
     if (pos2.z < cam.near) {
     return;
     }
     double zi = (1.0f / (pos1.z * pos1.w));
     if (zi < 0) {
     return;
     }
     iz1 = zi * cam.zoom;
     zi = (1.0f / (pos2.z * pos2.w));
     if (zi < 0) {
     return;
     }
     iz2 = zi * cam.zoom;
     double z1 = pos1.z;
     double z2 = pos2.z;
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos1 = cam.screen.Mult(cam.screen, pos1);
     pos2 = cam.screen.Mult(cam.screen, pos2);
     pos1.z = z1;
     pos2.z = z2;
     break;
     }
     case External: {
     pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);
     double z1 = pos1.z;
     pos2 = cam.viewProjection.Mult(cam.viewProjection, pos2);
     double z2 = pos2.z;
     iz1 = (1.0f + pos1.z * 0.001) * cam.zoom;
     if (iz1 < cam.near) {
     return;
     }
     if (iz1 > cam.far * cam.zoom) {
     return;
     }
     iz2 = (1.0f + pos2.z * 0.001) * cam.zoom;
     if (iz2 < cam.near) {
     return;
     }
     if (iz2 > cam.far * cam.zoom) {
     return;
     }
     pos1 = pos1.Scaled(iz1);
     pos2 = pos2.Scaled(iz2);
     pos1 = pos1.Add(pos1, Center()).AsVector();
     pos2 = pos2.Add(pos2, Center()).AsVector();
     pos1.z = z1;
     pos2.z = z2;

     break;
     }
     }
     pos1.x = pos1.x + offset.x;
     pos1.y = pos1.y + offset.y;
     pos2.x = pos2.x + offset.x;
     pos2.y = pos2.y + offset.y;
     //
     if ((pos1.x > cutFrom.x && pos1.x < cutTo.x && pos1.y > cutFrom.y && pos1.y < cutTo.y) && (pos2.x > cutFrom.x && pos2.x < cutTo.x && pos2.y > cutFrom.y && pos2.y < cutTo.y)) {
     pos1.w = from.r * iz1 * scale;
     pos2.w = to.r * iz2 * scale;
     //System.Console.WriteLine(from.r);

     if (pos1.w < minimumRadius) {
     pos1.w = minimumRadius;
     }
     if (pos2.w < minimumRadius) {
     pos2.w = minimumRadius;
     }
     //(pos1.x > -Point.MAX_MAP && pos1.y > -Point.MAX_MAP && pos1.x < Point.MAX_MAP && pos1.y < Point.MAX_MAP) &&
     //System.Console.WriteLine("Line" + c.r);
     Vertex2D v2d = new Vertex2D(pos1.x, pos1.y, pos1.z, pos1.w, index);
     v2d.SetDrawing(new Point(pos1.x, pos1.y, pos1.z, pos1.w), type, stype, v2d, color, index);
     Vertex2D v2d2 = new Vertex2D(pos2.x, pos2.y, pos2.z, pos2.w, index);
     v2d2.SetDrawing(new Point(pos2.x, pos2.y, pos2.z, pos2.w), type, stype, v2d2, color, index);
     v2d.SetNext(v2d2);
     load.Append(v2d);
     //System.Console.WriteLine("Line" + load.Length());
     }
     */
    @Override
    Vertex2D Transform(Point p) {

        Vector pos = LinearTransform(p);
        switch (screen.cam.viewer) {
            case Isometric: {
                if (IsoTransform(pos)) {
                    return Result(p, pos);
                }
            }
            case Internal: {
                if (InterTransform(pos)) {

                    return Result(p, pos);
                }
            }
            case External: {
                if (ExaTransform(pos)) {
                    return Result(p, pos);
                }
            }
        }
        return null;
    }

    @Override
    void Add(RingList<Vertex2D> list, Vertex2D add) {
        if (add != null) {
            s++;
            if (s == 1) {
                to = from = add;
            }
            if (s <= stride) {
                add.prev = to;
                to.next = add;
                to = add;
            }
            if (s == stride) {
                list.add(from);
                from = to = null;
                s = 0;
            }
        } else if (s != 0) {
            for (int i = 0; i < s; i++) {
                list.Trim();
            }
            from = to = null;
            s = 0;
        }
    }

    @Override
    public void AddTransform(RingList<Vertex2D> list, Point p) {
        Add(list, Transform(p));
    }

    @Override
    Vertex2D Result(Point p, Vector pos) {
        if ((pos.x > screen.cutFrom.x && pos.x < screen.cutTo.x && pos.y > screen.cutFrom.y && pos.y < screen.cutTo.y)) {
            pos.w = p.r * iz * scale;
            if (pos.w < minimumRadius) {
                pos.w = minimumRadius;
            }
            Vertex2D v2d = new Vertex2D(pos.x, pos.y, pos.z, pos.w, index);
            v2d.SetDrawing(new Point(pos.x, pos.y, pos.z, pos.w), type, subtype, v2d, color, index);
            return v2d;
        }
        return null;
    }

    @Override
    Vector LinearTransform(Point p) {
        Vector pos1 = p.AsVector();
        if (rot != null) {
            pos1 = rot.Rotate(pos1);
        }
        if (screen.cam.linear != null) {
            pos1 = screen.cam.linear.Mult(screen.cam.linear, pos1);
        }
        return pos1;
    }

    @Override
    boolean IsoTransform(Vector pos) {
        pos = screen.cam.viewProjection.Mult(screen.cam.viewProjection, pos);
        double z = pos.z;
        iz = screen.cam.zoom;
        pos = pos.Scaled(iz);
        pos = pos.Add(pos, screen.Center()).AsVector();
        pos.z = z;
        Offset(pos);
        return true;
    }

    @Override
    boolean InterTransform(Vector pos) {
        pos = screen.cam.projectionView.Mult(screen.cam.projectionView, pos);
        if (pos.z < screen.cam.near) {
            return false;
        }
        double zi = (1.0f / (pos.z * pos.w));
        if (zi < 0) {
            return false;
        }
        iz = zi * screen.cam.zoom;
        double z = pos.z;
        pos = pos.Scaled(iz);
        pos = screen.cam.screen.Mult(screen.cam.screen, pos);
        pos.z = z;
        Offset(pos);
        return true;
    }

    @Override
    boolean ExaTransform(Vector pos) {
        pos = screen.cam.viewProjection.Mult(screen.cam.viewProjection, pos);
        double z = pos.z;
        iz = (1.0f + pos.z * 0.001) * screen.cam.zoom;
        if (iz < screen.cam.near) {
            return false;
        }
        if (iz > screen.cam.far * screen.cam.zoom) {
            return false;
        }
        pos = pos.Scaled(iz);
        pos = pos.Add(pos, screen.Center()).AsVector();
        pos.z = z;
        Offset(pos);
        return true;
    }

    @Override
    void Offset(Vector pos) {

        pos.x = pos.x + offset.x;
        pos.y = pos.y + offset.y;
    }

    @Override
    public Supplier<RingList<Vertex2D>> supplier() {
        return RingList::new;
    }

    @Override
    public BiConsumer<RingList<Vertex2D>, Point> accumulator() {
        return this::AddTransform;
    }

    @Override
    public Function finisher() {
        return Function.identity();
    }

    @Override
    public BinaryOperator<RingList<Vertex2D>> combiner() {
        return (list1, list2) -> {
            list1.PasteAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }

    @Override
    public void PipeStream(RingList<Drawable> load) {

        java.util.stream.Stream<Point> str;
        str = java.util.stream.Stream.generate(points::Get).limit(points.length);
        load = str.collect(this);
        str.close();
    }

    @Override
    public void PumpStream(RingList<Drawable> load) {

        java.util.stream.Stream<Point> str;
        str = java.util.stream.Stream.generate(points::Get).limit(points.length);
        load.PasteAll(str.collect(this));
        str.close();
    }

    @Override
    public void PipeStream() {

        java.util.stream.Stream<Point> str;
        str = java.util.stream.Stream.generate(points::Get).limit(points.length);
        screen.renderList = str.collect(this);
        str.close();
    }

    @Override
    public void PumpStream() {
        java.util.stream.Stream<Point> str;
        str = java.util.stream.Stream.generate(points::Get).limit(points.length);
        screen.renderList.PasteAll(str.collect(this));
        str.close();
    }
}
