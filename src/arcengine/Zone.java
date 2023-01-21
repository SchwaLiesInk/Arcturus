/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics;

interface Changeable {

    void OnSizeChange(Box oldBox, Box newBox);
}

/**
 *
 * @author Gerwyn Jones
 */
public class Zone extends Box implements Changeable {

    int index = 0;
    int ID;
    int type;
    int state;
    int function;
    boolean on;
    boolean hover;
    boolean selected;
    boolean enabled;
    int x;
    int y;
    int cx;
    int cy;
    int right;
    int bottom;
    int width;
    int height;
    Action onTest = null;
    MouseInterAction onMouseTest = null;
    Point edge = new Point();
    Point centralCoord = new Point();
    Vector centerSize = new Vector();
    boolean autoTest = false;
    boolean autoMouseTest = false;
    boolean autoMouseHold = false;

    Zone(int x, int y, int w, int h, int index) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.right = x + w;
        this.bottom = y + h;
        this.cx = (x + (w >> 1));
        this.cy = (y + (h >> 1));
        this.edge = new Point(right, bottom);
        centerSize = size.Scaled(0.5);
        this.index = index;
        this.on = false;
        this.hover = false;
        this.selected = false;
        this.enabled = true;
    }

    boolean TestZone(int x, int y) {
        if (x > this.x && x < this.right && y > this.y && y < this.bottom) {
            return true;
        }
        return false;
    }

    public void OnSizeChange(Box oldBox, Box newBox) {
        this.edge = new Point(origin.x + size.x, origin.y + size.y);
        this.centerSize = size.Scaled(0.5);
    }

    void SetMouse(MouseInterAction set, boolean auto) {
        autoMouseTest = auto;
        onMouseTest = set;
    }

    void SetCollision(Action set, boolean auto) {
        autoTest = auto;
        onTest = set;
    }

    public void ExitScreen() {
    }

    public void Update(double et, Local offset) {
    }

    boolean MouseTest(Mouse m) {
        Point testing = new Point(m.event.getX(), m.event.getY());

        if (this.Collide2D(testing)) {
            //System.Console.WriteLine("TEST" + m.x + " " + m.y + " " + this.origin.x + " " + this.origin.y + " " + this.size.x + " " + this.size.y);

            if (autoMouseTest && onMouseTest != null) {
                onMouseTest.mouseAction(m);
            }
            return true;

        }
        return false;
    }

    public void Draw(Graphical g) {

    }

}

class Icon {

    java.awt.image.BufferedImage defaultImage;
    java.awt.image.BufferedImage image;
    Zone zone;
    boolean flash;
    float flashCounter;
    static float flashSpeed = 0.25f;

    Icon(java.awt.image.BufferedImage icon, int x, int y) {
        image = icon;
        zone = new Zone(x, y, icon.getWidth(), icon.getHeight(), 0);
        defaultImage = Game.InfoIcons.get("Object");
    }

    Icon(java.awt.image.BufferedImage icon, java.awt.image.BufferedImage defIcon, int x, int y) {
        image = icon;
        zone = new Zone(x, y, icon.getWidth(), icon.getHeight(), 0);
        defaultImage = defIcon;

    }

    void Paint(Graphics g) {

        if (zone.hover) {
            if (flash) {
                flashCounter += Game.time.AnimTime();
                g.drawImage(image, zone.x, zone.y, null);
                if (flashCounter >= flashSpeed) {
                    flashCounter = 0;
                    flash = false;
                }
            } else {
                flashCounter += Game.time.AnimTime();

                g.drawImage(defaultImage, zone.x, zone.y, null);
                if (flashCounter >= flashSpeed) {
                    flashCounter = 0;
                    flash = true;
                }
            }
        } else {
            g.drawImage(image, zone.x, zone.y, null);
        }
    }
}
