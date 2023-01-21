/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@FunctionalInterface
abstract interface Updater {

    public abstract void Update(GameTime time);
}

abstract interface Activator extends Updater {

    public abstract void Activate(ActionState act);

    public abstract MultiKeyPressListener KeyBoard();

    public abstract Game GetGame();

    public abstract Graphical Graphic();

    public abstract void InterActivate(MouseInterAction act);

    public abstract void PaintGame(Painter p);

    public abstract int GetWidth();

    public abstract int GetHeight();

    public abstract void Close();
}

enum DrawType {

    None, Point, Line, Triangle, Quad, Polygon, Circle, Voxel, Text
}

interface GraphicalIndexer {

    abstract int AddIndex(Graphical graph);

    Graphical GetGraphic(int index);

    Graphical CurrentGraphic();
}

interface Graphical {

    void SetGraphics(Graphics2D g);

    void SetGraphics(Graphics2D g, int index);

    abstract void DrawLine(Vertex2D from, Vertex2D to, double r, Pigment argb, DrawType type, int index);

    abstract void DrawDot(Vertex2D at, double r, Pigment argb, DrawType type, int index);

    abstract void DrawCircle(Vertex2D at, double r, Pigment argb, DrawType type, int index);

    abstract void DrawTriangle(Vertex2D at1, Vertex2D at2, Vertex2D at3, double r, Pigment argb, DrawType type, int index);

    abstract void DrawQuad(Vertex2D at1, Vertex2D at2, Vertex2D at3, Vertex2D at4, double r, Pigment argb, DrawType type, int index);

    abstract void DrawText(Vertex2D at, String text, double space, Pigment argb, DrawType type, int index);
        //
}

@FunctionalInterface
interface DrawChain {

    void Draw(Drawable chain, Graphical g);
}

interface Drawable extends DrawChain {

    abstract void SetDrawing(Point center, DrawType typ, DrawType subtyp, DrawChain draw, Pigment col, int index);

    abstract String Name();

    abstract Quarternion Rotation();

    abstract DrawType Draws();

    abstract DrawType SubDraws();

    abstract int GetGraphicsIndex();

    abstract Drawable Drawing(int p);

    abstract DrawChain Drawings();

    abstract boolean DoubleSided();

    abstract Pigment Colour();

    abstract int Points();

    abstract Vector GetVector(int i);

    abstract Normal Norm(int n);

    abstract Point Center();

    abstract Drawable Next();

    abstract Drawable Prev();

    abstract Graphical Graphic();

}

@FunctionalInterface
interface AutoDraw {

    void Draw(Graphical g);
}

/**
 *
 * @author Gerwyn Jones
 */
@FunctionalInterface
abstract interface Paints {

    public abstract void paint(Graphics2D g, double scale);

}

public abstract interface Painter extends Paints {

    public abstract void paintAll(Graphics2D g, double scale);// PAINT ALL

}
