/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors.*;

/**
 *
 * @author Gerwyn Jones
 */
public abstract class Screen extends MouseArea implements InterAction, Render3D, Painter, Changeable, MenuAction {

    protected static final int DEFAULTX = 1920;
    protected static final int DEFAULTY = 1080;
    protected Activator frame;
    protected ArrayList<MouseZone> mouseZones = new ArrayList<>();
    protected ArrayList<Zone> zones = new ArrayList<>();
    protected ArrayList<Icon> icons = new ArrayList<>();
    protected ArrayList<MapButton> buttons = new ArrayList<>();
    protected volatile BufferedImage buffer;
    protected volatile BufferedImageOp iop;
    protected volatile Graphics2D bg;
    protected volatile Graphics2D dbg;
    protected volatile GImage background;
    protected Camera cam;
    protected Graphical graphical;
    protected Point edge;
    protected Point cutFrom;
    protected Point cutTo;
    protected double fog = 0.02;
    protected Cartesian ambientLight = new Cartesian(0.1, 0.1, 0.1);
    protected TwoStateFunction fogf = (dis, f) -> {
        return dis * f;
    };
    ///
    protected boolean drawing = false;
    protected RingList<Drawable> renderList = new RingList<Drawable>();
    protected RingList<Drawable> textList = new RingList<Drawable>();
    Node<Drawable>[] unsortedList = null;
    //List<Drawable> usl;
    //java.util.stream.Stream<Drawable> str;
    protected Drawable[] sortedList;

    Screen(Activator f, int x, int y, int w, int h, String name) {
        super(x, y, w, h, name);
        cutFrom = new Point(x, y);
        edge = new Point(x + w, y + h);
        cutTo = new Point(edge.x, edge.y + h);
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if (Game.gc != null) {
            int trans = buffer.getColorModel().getTransparency();
            buffer = Game.gc.createCompatibleImage(buffer.getWidth(), buffer.getHeight(), trans);
        }
        bg = buffer.createGraphics();
        frame = f;
        graphical = frame.Graphic();
        cam = new Camera(0, 0, -1, Viewer.External, this);
    }

    void SetGraphics(Graphical gr) {
        graphical = gr;
    }

    void SetUpCamera(Point at, Vector dir, Normal up, Viewer view, double angulaFOV) {

        cam = new Camera(at.x, at.y, at.z, view, this);
        cam.SetFOV(angulaFOV);
        cam.Update(0, dir, up, cam.fovE);
    }

    void UpdateCamera(float et, Vector dir, Normal up) {
        cam.Update(et, dir, up, cam.fovE);
    }
// <editor-fold defaultstate="collapsed" desc="3D">

    Point GetScreenLocation(Point from, Quarternion rot, double scale, double minimumRadius) {
        double iz1 = 0;
        Vector pos1 = from.AsVector();// new Point(from.y, from.x, from.z, from.r);
        if (rot != null) {
            pos1 = rot.Rotate(pos1);
            pos1.w=1;
        }
        if (cam.linear != null) {
            pos1 = cam.linear.Mult(cam.linear, pos1);
        }
        switch (cam.viewer) {
            case Isometric: {

                pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);
                double z = pos1.z;
                iz1 = cam.zoom;
                pos1 = pos1.Scaled(iz1);
                pos1 = pos1.Add(pos1, Center()).AsVector();
                pos1.w = from.r * iz1 * scale;
                pos1.z = z;
                break;
            }
            case Internal: {
                pos1 = cam.projectionView.Mult(cam.projectionView, pos1);
                //System.Console.WriteLine("a x" + pos1.x + " y" + pos1.y + " z" + pos1.z + " w" + pos1.r);
                if (pos1.z < cam.near) {
                    return null;
                }
                double zi = (1.0f / (pos1.z * pos1.w));
                if (zi < 0) {
                    return null;
                }
                iz1 = zi * cam.zoom;

                //double z = pos1.z;
                pos1 = pos1.Scaled(iz1);
                pos1 = cam.screen.Mult(cam.screen, pos1);

                pos1.w = from.r * size.x * iz1 * scale;// (scale / (from.r * pos1.w));// ;// iz1 ;
                pos1.z = -zi * 3;//as triangles
                break;
            }
            case External: {
                pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);

                double z = pos1.z;
                iz1 = (1.0f + pos1.z * 0.001f) * cam.zoom;
                if (iz1 < cam.near) {
                    return null;
                }
                pos1 = pos1.Scaled(iz1);
                pos1 = pos1.Add(pos1, Center()).AsVector();
                pos1.w = from.r * iz1 * scale;
                pos1.z = -iz1;
                break;
            }
        }
        //
        //System.Console.WriteLine("r="+pos1.w);
        if (pos1.w < minimumRadius) {
            pos1.w = minimumRadius;
        }
        Point p = new Point(pos1.x, pos1.y, pos1.z, pos1.w);
        return p;
    }

    boolean GetViewLocation(Point from, Quarternion rot, double scale, double minimumRadius, Point p) {

        Vector pos1 = from.AsVector();// new Point(from.y, from.x, from.z, from.r);

        if (rot != null) {
            pos1 = rot.Rotate(pos1);
            pos1.w=1;
        }
        if (cam.linear != null) {
            pos1 = cam.linear.Mult(cam.linear, pos1);
        }
        switch (cam.viewer) {
            case Isometric: {

                pos1 = cam.view.Mult(cam.view, pos1);
                pos1.w = from.r * cam.zoom;
                break;
            }
            case Internal: {
                pos1 = cam.view.Mult(cam.view, pos1);
                //if (pos1.z < cam.near) { return null; }
                double zi = (1.0f / (pos1.z * pos1.w));
                double d = from.Sub(from, cam).LengthSquared();
                if (zi < -d && zi > -d) { //System.Console.WriteLine(d + " " + zi + " " + pos1.z + " " + pos1.r); 
                    return false;
                }
                //System.Console.WriteLine(d+" "+zi+" "+pos1.z+" "+pos1.r);
                pos1.w = from.r * size.x * cam.zoom * scale;
                break;
            }
            case External: {
                pos1 = cam.view.Mult(cam.view, pos1);
                pos1.w = from.r * cam.zoom;
                break;
            }
        }
        //
        if (pos1.w < minimumRadius) {
            pos1.w = minimumRadius;
        }

        p = new Point(pos1.x, pos1.y, pos1.z, pos1.w);
        return true;
    }

    void LoadVertexLine(Vector offset, Point from, Point to, Pigment color, Quarternion rot, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, int index) {

        double iz1 = 0;
        double iz2 = 0;

        Vector pos1 = from.AsVector();
        Vector pos2 = to.AsVector();
        if (rot != null) {
            pos1 = rot.Rotate(pos1);
            pos2 = rot.Rotate(pos2);
            pos1.w=1;
            pos2.w=1;
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
                pos1 = pos1.Scaled(iz1);
                pos2 = pos2.Scaled(iz2);
                pos1 = cam.screen.Mult(cam.screen, pos1);
                pos2 = cam.screen.Mult(cam.screen, pos2);
                pos1.z = pos2.z = -zi * 3;
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
    }

    boolean LoadVertexTriangle(Point objAt, RingList<Tessa> tri, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, RingList<Light> lights, double emissive) {
        if (cam.linear != null) {
            objAt = cam.linear.Mult(cam.linear, objAt.AsVector()).AsPoint(objAt.r);
        }
        if (cam.InView(this, objAt, cam.rotation, scale, minimumRadius)) {
            Tessa t = tri.First();
            while (t != null) {
                LoadVertexTriangle(objAt, t, t.color, scale, minimumRadius, load, type, stype, lights, emissive);
                t = tri.Next();
            }
            return true;
        }
        return false;
    }

    void LoadVertexTriangles(Point objAt, RingList<Drawable> tri, double scale, double minimumRadius, RingList<Drawable> load, DrawType dtype, DrawType stype, RingList<Light> lights) {
        Point at = objAt;
        if (cam.linear != null) {
            at = cam.linear.Mult(cam.linear, at.AsVector()).AsPoint(at.r);
        }

        Quarternion rot = new Quarternion();
        if (cam.rotation != null) {

            rot = cam.rotation;
        }

        //System.Console.WriteLine("dis "+(cam - objAt).Length() + " r" + objAt.r);
        //bool reg = false;
        if (cam.InView(this, at, null, scale, minimumRadius)) {
            //double d = (objAt - cam).Length();
            //System.Console.WriteLine("In View");
            Drawable t = tri.First();
            if (t != null) {

                //System.Console.WriteLine("Not Null");
                Point cent = t.Center();
                double cz = Linear.MAX_MAP;
                if (t instanceof Tessa) {
                    while (t != null) {
                        //System.Console.WriteLine("Loading Triangle");
                        //LoadVertexTriangle((Triangle)t, t.Colour, scale, minimumRadius, load, draw, lights, func, vec);
                        //backface culing
                        //Vector v = objAt-(t.Center - cam.AsVector());
                        if (t.Rotation() != null) {
                            rot = rot.Net(rot, new Quarternion[]{t.Rotation()});
                        }
                        Point r = rot.Rotate(t.Center());
                        Point p = at.Add(at, r.Sub(r, cam.AsPoint(cam.r))).AsPoint(at.r);
                        p.Normalize();

                        Normal n = rot.Rotate(t.Norm(0));

                        int di = 0;
                        if (t.DoubleSided() || n.Dot(n, p) < 0) {
                            //System.Console.WriteLine("Wise");

                            double iz1 = 0;
                            double iz2 = 0;
                            double iz3 = 0;
                            Vector r1 = rot.Rotate(t.GetVector(0));
                            Vector r2 = rot.Rotate(t.GetVector(1));
                            Vector r3 = rot.Rotate(t.GetVector(2));
                            Vector pos1 = r1.Add(r1, objAt).AsVector();
                            Vector pos2 = r2.Add(r2, objAt).AsVector();
                            Vector pos3 = r3.Add(r3, objAt).AsVector();
                            Point v0 = objAt.Add(t.Center(), objAt).AsPoint(t.Center().r);
                            Vector vn = n.AsVector();

                            //double d = 1;// (v0 - cam.AsPoint(cam.r)).LengthSquared();
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
                                    pos2 = pos1.Add(pos2, Center()).AsVector();
                                    pos3 = pos1.Add(pos3, Center()).AsVector();
                                    //
                                    pos1.z = z1;
                                    pos2.z = z2;
                                    pos3.z = z3;
                                    break;
                                }
                                case Internal: {
                                    //System.Console.WriteLine(" x" + pos1.x + " y" + pos1.y + " z" + pos1.z + " r" + pos1.r);

                                    pos1 = cam.projectionView.Mult(cam.projectionView, pos1);
                                    //System.Console.WriteLine(" x" + pos1.x + " y" + pos1.y + " z" + pos1.z + " r" + pos1.r);
                                    if (pos1.z < 0 || pos1.w < 0) {
                                        di = 3;
                                        break;
                                    }
                                    pos2 = cam.projectionView.Mult(cam.projectionView, pos2);
                                    if (pos2.z < 0 || pos2.w < 0) {
                                        di = 3;
                                        break;
                                    }
                                    pos3 = cam.projectionView.Mult(cam.projectionView, pos3);
                                    //if (pos3.z < -1) { di = 3; break; }
                                    if (pos3.z < 0 || pos3.w < 0) {
                                        di = 3;
                                        break;
                                    }

                                    double z1 = pos1.z;
                                    double z2 = pos2.z;
                                    double z3 = pos3.z;
                                    double zi = (1.0f / (pos1.z * pos1.w));

                                    iz1 = zi * cam.zoom;
                                    pos1 = pos1.Scaled(iz1);
                                    pos1 = cam.screen.Mult(cam.screen, pos1);

                                    zi = (1.0f / (pos2.z * pos2.w));

                                    iz2 = zi * cam.zoom;
                                    pos2 = pos2.Scaled(iz2);
                                    pos2 = cam.screen.Mult(cam.screen, pos2);

                                    zi = (1.0f / (pos3.z * pos3.w));

                                    iz3 = zi * cam.zoom;
                                    pos3 = pos3.Scaled(iz3);
                                    pos3 = cam.screen.Mult(cam.screen, pos3);
                                    //if (di == 3) { break; }

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
                            if (di < 3) {
                                boolean ok1 = (pos1.x > cutFrom.x && pos1.x < cutTo.x && pos1.y > cutFrom.y && pos1.y < cutTo.y);
                                boolean ok2 = (pos2.x > cutFrom.x && pos2.x < cutTo.x && pos2.y > cutFrom.y && pos2.y < cutTo.y);
                                boolean ok3 = (pos3.x > cutFrom.x && pos3.x < cutTo.x && pos3.y > cutFrom.y && pos3.y < cutTo.y);
                                //bool ok4 = !(pos1.z < 0 || pos2.z < 0 || pos3.z < 0);
                                if (ok1 || ok2 || ok3) {

                                    if (pos1.z < cz || pos2.z < cz || pos3.z < cz) {
                                        //Point cn=((pos1+pos2)*0.5f+pos3)*0.5f;
                                        cz = Linear.Max3(pos1.z, pos2.z, pos3.z);
                                        cent.z = cz;
                                    }
                                    double rad = t.Center().r * size.x * iz1 * scale;
                                    if (rad < minimumRadius) {
                                        rad = minimumRadius;
                                    }
                                    pos3.w = pos2.w = pos1.w = rad;
                                    Pigment c = new Pigment(t.Colour());
                                    if (lights != null) {
                                        Light lit = lights.First();
                                        int cr = c.Red();
                                        int cg = c.Green();
                                        int cb = c.Blue();
                                        while (lit != null) {
                                            Pigment lp = lit.Effect(lit, v0, n, c);
                                            cr += lp.Red();
                                            cg += lp.Green();
                                            cb += lp.Blue();
                                            lit = lights.Next();
                                        }
                                        int lites = 2 + lights.Length();
                                        cr /= lites;
                                        cg /= lites;
                                        cb /= lites;

                                        c = new Pigment(c.Alpha(), cr, cg, cb);
                                    }

                                    Pigment c1 = c;
                                    Pigment c2 = c;
                                    Pigment c3 = c;

                                    if (!(ok1 && ok2 && ok3)) {
                                        if (!ok1) {
                                            if (pos1.x < cutFrom.x) {
                                                pos1.x = this.cutFrom.x;
                                            } else if (pos1.x > cutTo.x) {
                                                pos1.x = this.cutTo.x;
                                            }
                                            if (pos1.y < cutFrom.y) {
                                                pos1.y = this.cutFrom.y;
                                            } else if (pos1.y > cutTo.y) {
                                                pos1.y = this.cutTo.y;
                                            }
                                        }
                                        if (!ok2) {
                                            if (pos2.x < cutFrom.x) {
                                                pos2.x = this.cutFrom.x;
                                            } else if (pos2.x > cutTo.x) {
                                                pos2.x = this.cutTo.x;
                                            }
                                            if (pos2.y < cutFrom.y) {
                                                pos2.y = this.cutFrom.y;
                                            } else if (pos2.y > cutTo.y) {
                                                pos2.y = this.cutTo.y;
                                            }
                                        }
                                        if (!ok3) {
                                            if (pos3.x < cutFrom.x) {
                                                pos3.x = this.cutFrom.x;
                                            } else if (pos3.x > cutTo.x) {
                                                pos3.x = this.cutTo.x;
                                            }
                                            if (pos3.y < cutFrom.y) {
                                                pos3.y = this.cutFrom.y;
                                            } else if (pos3.y > cutTo.y) {
                                                pos3.y = this.cutTo.y;
                                            }
                                        }
                                    }
                                    Vertex2D v2d = new Vertex2D(pos1.x, pos1.y, pos1.z, pos1.w, t.GetGraphicsIndex());
                                    v2d.SetDrawing(cent, dtype, stype, v2d, c1, v2d.GetGraphicsIndex());
                                    Vertex2D v2d2 = new Vertex2D(pos2.x, pos2.y, pos2.z, pos2.w, t.GetGraphicsIndex());
                                    v2d2.SetDrawing(cent, dtype, stype, v2d2, c2, v2d2.GetGraphicsIndex());
                                    Vertex2D v2d3 = new Vertex2D(pos3.x, pos3.y, pos3.z, pos3.w, t.GetGraphicsIndex());
                                    v2d3.SetDrawing(cent, dtype, stype, v2d3, c3, v2d3.GetGraphicsIndex());
                                    //System.Console.WriteLine(t.AlphaBlend);

                                    v2d.SetNext(v2d2);
                                    v2d2.SetNext(v2d3);

                                    load.Append(v2d);
                                    //System.Console.WriteLine("Line");
                                }

                            }
                        }
                        t = tri.Next();
                    }
                }
            }
        }
    }

    void LoadVertexTriangle(Point objAt, Tessa tri, Pigment color, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, RingList<Light> lights, double emissive) {

        Normal n = tri.Norm(0);
        if (tri.rotation != null) {
            //v = tri.rotation.Rotate(v);
            n = tri.rotation.Rotate(n);
        }
        if (cam.linear != null) {
            n = cam.linear.Mult(cam.linear, n.AsVector()).AsNormal();
        }
        Point ca = objAt.Add(tri.center.AsVector());
        ca.r = tri.center.r;
        if (cam.InView(this, ca, null, scale, tri.center.r)) {
            //if (n.Dot(n, v) > 0) {
            double iz1 = 0;
            double iz2 = 0;
            double iz3 = 0;
            Vector pos1 = tri.vert[0].v.AsVector();
            //System.out.println("TRIANGLE "+pos1.x+" "+pos1.y+" "+pos1.z);
            Vector pos2 = tri.vert[1].v.AsVector();
            Vector pos3 = tri.vert[2].v.AsVector();
            Point v0 = tri.Center();
            v0 = v0.Add(objAt.AsVector());
            Vector vn = new Vector(n.AsVector());

            if (cam.linear != null) {
                v0 = cam.linear.Mult(cam.linear, v0.AsVector()).AsPoint(v0.r);
                vn = cam.linear.Mult(cam.linear, vn);
                pos1 = cam.linear.Mult(cam.linear, pos1);
                pos2 = cam.linear.Mult(cam.linear, pos2);
                pos3 = cam.linear.Mult(cam.linear, pos3);

            }
            if (cam.rotation != null) {
                v0 = cam.rotation.Rotate(v0.AsVector()).AsPoint(v0.r);
                //vn = cam.rotation.Rotate(vn);
                pos1 = cam.rotation.Rotate(pos1);
                pos2 = cam.rotation.Rotate(pos2);
                pos3 = cam.rotation.Rotate(pos3);

            }
            pos1 = pos1.Added(objAt.AsVector());
            pos2 = pos2.Added(objAt.AsVector());
            pos3 = pos3.Added(objAt.AsVector());

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
                    pos2 = cam.projectionView.Mult(cam.projectionView, pos2);
                    pos3 = cam.projectionView.Mult(cam.projectionView, pos3);
                    if (pos1.z < cam.near && pos3.z < cam.near && pos2.z < cam.near) {
                        return;
                    }

                    //double z1 = pos1.z;
                    //double z2 = pos2.z;
                    //double z3 = pos3.z;
                    double zi = (1.0 / (pos1.z * pos1.w));
                    pos1.z = -zi;//z1;
                    if (zi < 0) {
                        return;
                    }
                    iz1 = zi * cam.zoom;
                    zi = (1.0 / (pos2.z * pos2.w));
                    pos1.z += -zi;//z1;
                    if (zi < 0) {
                        return;
                    }
                    iz2 = zi * cam.zoom;
                    zi = (1.0 / (pos3.z * pos3.w));
                    pos1.z += -zi;//z1;
                    if (zi < 0) {
                        return;
                    }

                    iz3 = zi * cam.zoom;
                    pos3.z = pos2.z = pos1.z;
                    pos1 = pos1.Scaled2D(iz1);
                    pos2 = pos2.Scaled2D(iz2);
                    pos3 = pos3.Scaled2D(iz3);
                    pos1 = cam.screen.Mult(cam.screen, pos1);
                    pos2 = cam.screen.Mult(cam.screen, pos2);
                    pos3 = cam.screen.Mult(cam.screen, pos3);

                    //System.Console.WriteLine("Loading Triangle " + z1 + " " + z2 + " " + z3);
                    // v.z;//z2;
                    //v.z;//z3;
                    break;
                }
                case External: {
                    pos1 = cam.viewProjection.Mult(cam.viewProjection, pos1);

                    pos2 = cam.viewProjection.Mult(cam.viewProjection, pos2);

                    pos3 = cam.viewProjection.Mult(cam.viewProjection, pos3);

                    /* pos1=cam.projection.Mult(cam.projection, pos1);
                     pos1=cam.view.Mult(cam.view, pos1);
                     pos2=cam.projection.Mult(cam.projection, pos2);
                     pos2=cam.view.Mult(cam.view, pos2);
                     pos3=cam.projection.Mult(cam.projection, pos3);
                     pos3=cam.view.Mult(cam.view, pos3);*/
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
                    //System.out.println("TRIANGLE1 "+pos1.x+" "+pos1.y+" "+pos1.z);
                    //System.out.println("CENTER "+Center().x+" "+Center().y);
                    pos1 = pos1.Add(pos1, Center()).AsVector();
                    pos2 = pos2.Add(pos2, Center()).AsVector();
                    pos3 = pos3.Add(pos3, Center()).AsVector();
                    //System.out.println("TRIANGLE2 "+pos1.x+" "+pos1.y+" "+pos1.z);

                    pos1.z = z1;
                    pos2.z = z2;
                    pos3.z = z3;
                    //at.y *= 0.25f;
                    break;
                }
            }
            //double mx1=(pos1.x+pos2.x)*0.5;
            //double my1=(pos1.y+pos2.y)*0.5;
            //double mx2=(pos2.x+pos3.x)*0.5;
            //double my2=(pos2.y+pos3.y)*0.5;
            //double mx3=(pos1.x+pos3.x)*0.5;
            //double my3=(pos1.y+pos3.y)*0.5;
            /*if(pos1.x < cutFrom.x){pos1.x=cutFrom.x;}
             else if(pos1.x > cutTo.x){pos1.x=cutTo.x;}
             if(pos1.y < cutFrom.y){pos1.y=cutFrom.y;}
             else if(pos1.y > cutTo.y){pos1.y=cutTo.y;}
             if(pos2.x < cutFrom.x){pos2.x=cutFrom.x;}
             else if(pos2.x > cutTo.x){pos2.x=cutTo.x;}
             if(pos2.y < cutFrom.y){pos2.y=cutFrom.y;}
             else if(pos2.y > cutTo.y){pos2.y=cutTo.y;}
             if(pos3.x < cutFrom.x){pos3.x=cutFrom.x;}
             else if(pos3.x > cutTo.x){pos3.x=cutTo.x;}
             if(pos3.y < cutFrom.y){pos3.y=cutFrom.y;}
             else if(pos3.y > cutTo.y){pos3.y=cutTo.y;}*/

            if ((pos1.x > cutFrom.x && pos1.x < cutTo.x && pos1.y > cutFrom.y && pos1.y < cutTo.y)
                    || (pos2.x > cutFrom.x && pos2.x < cutTo.x && pos2.y > cutFrom.y && pos2.y < cutTo.y)
                    || (pos3.x > cutFrom.x && pos3.x < cutTo.x && pos3.y > cutFrom.y && pos3.y < cutTo.y)) {
                double rad = tri.Center().r * iz1 * scale;
                if (rad < minimumRadius) {
                    rad = minimumRadius;
                }
                Vector v = new Vector(cam.x - tri.center.x - objAt.x, cam.y - tri.center.y - objAt.y, cam.z - tri.center.z - objAt.z);

                pos3.w = pos2.w = pos1.w = rad;

                int r = 0;
                int g = 0;
                int b = 0;
                if (emissive > 0) {
                    double e=1.0/ (1 + emissive);
                    r =(int) ( (tri.color.Red() * emissive + emissive * ambientLight.x) *e);
                    g =(int) ( (tri.color.Green() * emissive + emissive * ambientLight.y)*e);
                    b =(int) ( (tri.color.Blue() * emissive + emissive * ambientLight.z) *e);
                    if (r > 255) {
                        r = 255;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                } else {
                    r = (tri.color.Red() + (int) (255 * ambientLight.x)) >> 1;
                    g = (tri.color.Green() + (int) (255 * ambientLight.y)) >> 1;
                    b = (tri.color.Blue() + (int) (255 * ambientLight.z)) >> 1;

                }
                Pigment c = new Pigment(r, g, b);
                if (emissive <= 0) {
                    if (lights != null) {
                        Light lit = lights.First();

                        r = 0;
                        g = 0;
                        b = 0;
                        while (lit != null) {
                            Pigment lp = lit.Effect(lit, v0, n, c);
                            r += lp.Red();
                            g += lp.Green();
                            b += lp.Blue();
                            lit = lights.Next();
                        }
                        if (lights.Length() > 1) {
                            r /= lights.Length();
                            g /= lights.Length();
                            b /= lights.Length();
                        }

                        if (r > 255) {
                            r = 255;
                        }
                        if (g > 255) {
                            g = 255;
                        }
                        if (b > 255) {
                            b = 255;
                        }
                        c = new Pigment(c.Alpha(), r, g, b);
                    } else {
                        c = new Pigment(c.Alpha(), c.Red() >> 1, c.Green() >> 1, c.Blue() >> 1);

                    }
                }
                c.Add(255, (int) (fogf.f2(v.Length(), fog)));
                Pigment c1 = c;
                Pigment c2 = c;
                Pigment c3 = c;

                //System.out.println("TRIANGLE1 "+pos1.x+" "+pos1.y+" "+pos1.z);
                //System.out.println("TRIANGLE2 "+pos2.x+" "+pos2.y+" "+pos2.z);
                //System.out.println("TRIANGLE3 "+pos3.x+" "+pos3.y+" "+pos3.z);
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

                //System.out.println("TRIANGLE1 "+v2d.x+" "+v2d.y+" "+v2d.z);
                //System.out.println("TRIANGLE2 "+v2d2.x+" "+v2d2.y+" "+v2d2.z);
                //System.out.println("TRIANGLE3 "+v2d3.x+" "+v2d3.y+" "+v2d3.z);
                load.Append(v2d);
                //System.Console.WriteLine("Line");
            }
        }
    }

    boolean LoadVertexPoints(Point relativeCoord, Pigment colors[], Quarternion rot, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, Point result, int index) {

        Point pos0 = GetScreenLocation(relativeCoord, rot, scale, minimumRadius);
        //System.out.println("r " + relativeCoord.x + " " + relativeCoord.y + " " + relativeCoord.z + " " + relativeCoord.r);

        if (pos0 != null) {
            //at.y *= 0.25f;

            //System.out.println(pos0.x + " " + pos0.y);
            //if (Math.abs(pos0.x) < 7000 && Math.abs(pos0.y) < 7000) {
            if (!(pos0.x - pos0.r > cutTo.x || pos0.x + pos0.r < cutFrom.x || pos0.y - pos0.r > cutTo.y || pos0.y + pos0.r < cutFrom.y)) {
                //System.out.println("p " + pos0.x + " " + pos0.y + " " + pos0.z + " " + pos0.r);
                Vertex2D v2d = new Vertex2D(pos0.x, pos0.y, pos0.z, pos0.r, index);
                Vertex2D v2n = v2d;
                //System.Console.WriteLine(pos0.r);
                double r = (pos0.r / colors.length);
                for (int i = 1; i < colors.length; i++) {
                    Vector pos = new Vector(pos0.x, pos0.y, pos0.z, r * (colors.length - i));
                    v2n.next = new Vertex2D(pos, index);
                    v2n.next.SetDrawing(pos0, type, stype, v2n.next, colors[i], index);
                    v2n = v2n.next;
                }
                v2d.SetDrawing(pos0, type, stype, v2d, colors[0], index);
                load.Append(v2d);
                result.x = pos0.x;
                result.y = pos0.y;
                result.z = pos0.z;
                result.r = pos0.r;
                return true;
            }
            //}

        }
        return false;
    }

    boolean LoadVertexPoint(Point relativeCoord, Pigment color, Quarternion rot, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, Point result, int index) {
        Point pos0 = GetScreenLocation(relativeCoord, rot, scale, minimumRadius);
        if (pos0 != null) {
            //at.y *= 0.25f;

            //System.out.println(pos0.x + " " + pos0.y+" " + pos0.r);
            //if (Math.abs(pos0.x) < 7000 && Math.abs(pos0.y) < 7000) {
            if (!(pos0.x - pos0.r > cutTo.x || pos0.x + pos0.r < cutFrom.x || pos0.y - pos0.r > cutTo.y || pos0.y + pos0.r < cutFrom.y)) {
                //System.Console.WriteLine("p " + pos0.x + " " + pos0.y + " " + pos0.z + " " + pos0.r);
                Vertex2D v2d = new Vertex2D(pos0.x, pos0.y, pos0.z, pos0.r, index);
                //System.Console.WriteLine(pos0.r);
                v2d.SetDrawing(pos0, type, stype, v2d, color, index);
                load.Append(v2d);
                result.x = pos0.x;
                result.y = pos0.y;
                result.z = pos0.z;
                result.r = pos0.r;

                return true;
            }
            //}

        }
        return false;
    }

    boolean LoadVertexPoint(Point offset, Point relativeCoord, Pigment color, Quarternion rot, double scale, double minimumRadius, RingList<Drawable> load, DrawType type, DrawType stype, int index, Point result) {
        Point pos0 = GetScreenLocation(relativeCoord, rot, scale, minimumRadius);
        if (pos0 != null) {
            //at.y *= 0.25f;
            System.out.println(pos0.x + " " + pos0.y);
            //pos0.x = pos0.x + offset.x;
            //pos0.y = pos0.y + offset.y;
            //if (Math.abs(pos0.x) < 7000 && Math.abs(pos0.y) < 7000) {
            if (!(pos0.x - pos0.r > cutTo.x || pos0.x + pos0.r < cutFrom.x || pos0.y - pos0.r > cutTo.y || pos0.y + pos0.r < cutFrom.y)) {
                //System.Console.WriteLine("p " + pos0.x + " " + pos0.y + " " + pos0.z + " " + pos0.r);

                Vertex2D v2d = new Vertex2D(pos0.x, pos0.y, pos0.z, pos0.r, index);
                //System.Console.WriteLine(pos0.r);
                v2d.SetDrawing(pos0, type, stype, v2d, color, index);
                load.Append(v2d);
                result = pos0;
                return true;
            }
            //}
        }
        return false;
    }

    boolean LoadVertexPoint(Point relativeCoord, Point center, Pigment color, Quarternion rot, double scale, double minimumRadius, RingList<Drawable> load, RingList<Light> lights, DrawType type, DrawType stype, int index, Point result) {

        Point pos0 = GetScreenLocation(relativeCoord, rot, scale, minimumRadius);
        if (pos0 != null) {
            //at.y *= 0.25f;
            //System.Console.WriteLine(at.x + " " + at.y);

            if (Math.abs(pos0.x) < 7000 && Math.abs(pos0.y) < 7000) {
                if (!(pos0.x - pos0.r > cutTo.x || pos0.x + pos0.r < cutFrom.x || pos0.y - pos0.r > cutTo.y || pos0.y + pos0.r < cutFrom.y)) {
                    Normal n = center.Add(center, relativeCoord).Unity().AsNormal();
                    //System.Console.WriteLine("p " + pos0.x + " " + pos0.y + " " + pos0.z + " " + pos0.r);
                    Vertex2D v2d = new Vertex2D(pos0.x, pos0.y, pos0.z, pos0.r, index);
                    Pigment c = new Pigment(color);
                    if (lights != null) {
                        Light lit = lights.First();
                        int r = 0;
                        int g = 0;
                        int b = 0;
                        while (lit != null) {
                            Pigment lp = lit.Effect(lit, relativeCoord, n, c);
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

                    //System.Console.WriteLine(pos0.r);
                    v2d.SetDrawing(pos0, type, stype, v2d, c, index);

                    load.Append(v2d);
                    result = pos0;
                    return true;
                }
            }
        }
        return false;
    }

    static int SortZ(Drawable a, Drawable b) {
        if (a != null && b != null) {
            if (a.Center().z < b.Center().z) {
                return 1;
            } else if (a.Center().z > b.Center().z) {
                return -1;
            }
        }
        return 0;
    }

    void SlowSortZ(Node<Drawable>[] unsortedList, int lookFor) {

        for (int i = 0; i < unsortedList.length - 1; i++) {
            int mini = i;
            for (int j = i + 1; j < unsortedList.length; j++) {
                if (SortZ(unsortedList[j].data, unsortedList[mini].data) == lookFor) {
                    mini = j;
                }
                if (mini != i) {

                    Node<Drawable> d = unsortedList[mini];
                    unsortedList[mini] = unsortedList[i];
                    unsortedList[i] = d;
                }
            }
        }
    }

    void ZSort() {

        unsortedList = null;
        if (renderList.Length() > 0) {
            //System.Console.WriteLine("List " + renderList.Length());
            unsortedList = new Node[renderList.Length()];
            renderList.CopyToArray(unsortedList);
            //str = java.util.stream.Stream.generate(renderList::Remove).limit(renderList.length);
            //usl = str.sorted(new ZSorter()).collect(Collectors.toList());
            //str.close();
            SlowSortZ(unsortedList, -1);
            //SlowSortZ(unsortedList,-1);

            //sortedList=(Drawable[])str.parallel().sorted(new ZSorter()).toArray();
            //new ArrayList(renderList); 
            //if(sortedList.Length>2)
            //SlowSortZ(unsortedList, -1);
            //usl.sort(Screen::SortZ);
            //System.Console.WriteLine("US List " + unsortedList.Length);
        }
        renderList = new RingList();
        //sortedList = unsortedList;
    }

    @Override
    public void Begin() {
        if (!drawing) {

            ZSort();
        }
    }

    @Override
    public void End() {
        if (!drawing && unsortedList != null) {
            sortedList = new Drawable[unsortedList.length];
            for (int i = 0; i < unsortedList.length; i++) {
                sortedList[i] = unsortedList[i].data;
            }
        }

    }

    @Override
    public void Draw3D() {

        if (!drawing) {
            drawing = true;
            DrawSortedList(this.graphical);
            drawing = false;
        }

    }

    boolean DrawSortedList(Drawable[] verts, Graphical g) {
        //if (verts != null && verts.length > 0) {
        //System.out.println("LIST"+verts.length);
        for (Drawable v : verts) {

            //System.out.println("LIST V1 "+v.GetVector(0).x+" "+v.GetVector(0).y+" "+v.GetVector(0).z);
            //System.out.println("LIST V2 "+v.GetVector(1).x+" "+v.GetVector(1).y+" "+v.GetVector(1).z);
            //System.out.println("LIST V3 "+v.GetVector(2).x+" "+v.GetVector(2).y+" "+v.GetVector(2).z);
            v.Draw(v, g);

        }
        return true;
        //}
        //return false;
    }

    boolean DrawSortedList(Graphical g) {

        if (sortedList != null && sortedList.length > 0) {
            for (Drawable v : sortedList) {

                //System.Console.WriteLine(v.Center.z);
                //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " z" + v.Center.z + " r" + v.Center.r);
                v.Draw(v, g);

            }
            return true;
        }
        return false;
    }

    RingList<Drawable> ZSort(RingList<Drawable> sort, int lookFor) {
        RingList<Drawable> res = null;

        //Drawable[] unsortedList = null;
        if (renderList.Length() > 0) {
            //System.Console.WriteLine("List " + renderList.Length());
            //sort.First();
            //unsortedList = new Drawable[sort.Length()];
            //sort.CopyToArray(unsortedList);
            //if(sortedList.Length>2)
            //SlowSortZ(unsortedList, lookFor);
            //ArrayList<Drawable> usl=new ArrayList(renderList); 
            //usl.sort(Screen::SortZ);
            //res = new RingList<Drawable>(usl);
            renderList.First();
            //unsortedList = new Node[renderList.Length()];
            //renderList.CopyToArray(unsortedList);
            java.util.stream.Stream<Drawable> str = java.util.stream.Stream.generate(renderList::Remove).limit(renderList.length);
            List<Drawable> usl = str.sorted(new ZSorter()).collect(Collectors.toList());
            res = new RingList<Drawable>(usl);
            //sortedList=(Drawable[])str.parallel().sorted(new ZSorter()).toArray();
            //System.Console.WriteLine("US List " + unsortedList.Length);
        }
        return res;

    }

    boolean DrawVertexList(RingList<Drawable> verts) {
        Drawable v = verts.Value();

        if (v == null) {
            return true;
        }
        if (this.graphical == null) {
            while (v != null) {

                v.Draw(v, v.Graphic());
                //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " r" + v.Center.r);
                v = verts.Next();

            }
        } else {
            while (v != null) {

                v.Draw(v, graphical);
                //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " r" + v.Center.r);
                v = verts.Next();

            }
        }

        return true;
    }

    boolean DrawSortedList(Node<Drawable>[] verts) {

        if (this.graphical == null) {
            for (Node<Drawable> v : verts) {

                //System.Console.WriteLine(v.Center.z);
                //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " z" + v.Center.z + " r" + v.Center.r);
                v.data.Draw(v.data, v.data.Graphic());

            }
        } else {
            for (Node<Drawable> v : verts) {

                //System.Console.WriteLine(v.Center.z);
                //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " z" + v.Center.z + " r" + v.Center.r);
                v.data.Draw(v.data, graphical);

            }
        }
        return true;
    }

    boolean DrawVertexList(RingList<Drawable> verts, Graphical g) {
        Drawable v = verts.Value();

        if (v == null) {
            return true;
        }

        while (v != null) {

            v.Draw(v, g);
            //System.Console.WriteLine("v Line x" + v.Center.x + " y" + v.Center.y + " r" + v.Center.r);
            v = verts.Next();

        }

        return true;
    }

    void DrawStringList(Vertex2D at, RingList<String> strs, Graphical g) {
        String v = strs.Value();
        Vertex2D vx = at;
        int i = 0;
        while (i < strs.Length() && vx != null) {
            g.DrawText(at, v, 8, at.Colour(), at.Draws(), at.GetGraphicsIndex());
            vx = at.ClockWise();
            v = strs.Next();
            i++;
        }

    }

    void DrawStringList(Vertex2D at, RingList<String> strs) {
        String v = strs.Value();
        Vertex2D vx = at;
        int i = 0;
        if (graphical == null) {
            while (i < strs.Length() && vx != null) {
                at.Graphic().DrawText(at, v, 8, at.Colour(), at.Draws(), at.GetGraphicsIndex());
                vx = at.ClockWise();
                v = strs.Next();
                i++;
            }
        } else {
            while (i < strs.Length() && vx != null) {
                graphical.DrawText(at, v, 8, at.Colour(), at.Draws(), at.GetGraphicsIndex());
                vx = at.ClockWise();
                v = strs.Next();
                i++;
            }
        }

    }

    @Override
    public void OnSizeChange(Box oldBox, Box newBox) {

    }
// </editor-fold>

    void UpdateScreen(GameTime time) {

        Point c = new Point(frame.GetWidth() >> 1, frame.GetWidth() >> 1);
        Vector dif = c.Sub(c, this.Center());
        if ((int) (this.origin.x) != (int) (dif.x) && (int) (this.origin.y) != (int) (dif.y)) {
            Point old = this.origin.AsPoint(this.origin.r);
            Vector oldSize = this.size.AsVector();
            this.origin = (dif).AsPoint(c.Length());
            OnSizeChange(new Box(old, oldSize), this);
            cam.screenAt = this.origin;

        }
        Update(time);
    }

    void Reset(Local offset) {

        this.renderList = new RingList<Drawable>();

    }

    @Override
    public abstract void paintAll(Graphics2D g, double scale);

    @Override
    public abstract void paint(Graphics2D g, double scale);

    @Override
    public abstract void Update(GameTime time);

}
