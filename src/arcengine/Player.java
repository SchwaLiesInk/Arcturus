/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Gerwyn Jones
 */
class BitPattern {

    static byte Standing[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Signaling1[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Signaling2[][] = {
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0}
    };
    static byte AimPistol[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 2, 2, 0, 0},
        {0, 0, 0, 2, 1, 1, 0, 0},
        {0, 0, 4, 2, 1, 1, 0, 0},
        {0, 0, 0, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte AimPistol2[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 4, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte AimPistol3[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 4, 2, 2, 2, 0, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte AimGun[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 2, 2, 0, 0},
        {0, 0, 0, 2, 1, 1, 0, 0},
        {0, 4, 4, 2, 1, 1, 0, 0},
        {0, 0, 0, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte AimRifle[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 2, 2, 0, 0},
        {0, 0, 0, 2, 1, 1, 0, 0},
        {4, 4, 4, 4, 1, 1, 0, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Walking1[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 2, 0, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Walking2[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 0, 2, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Grabing1[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Grabing2[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Throwing1[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 2, 0, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Throwing2[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 2, 2, 2, 0, 0},
        {0, 0, 0, 0, 1, 1, 3, 0},
        {0, 0, 0, 3, 1, 1, 0, 0},
        {0, 0, 0, 0, 0, 2, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}
    };
    static byte Crawling[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 1, 1, 0, 0, 0},
        {0, 2, 0, 1, 1, 0, 2, 0},
        {0, 0, 2, 2, 2, 2, 2, 0},
        {0, 0, 0, 2, 2, 0, 0, 0},
        {0, 0, 0, 3, 3, 0, 0, 0},
        {0, 0, 0, 3, 0, 3, 0, 0},
        {0, 0, 0, 3, 3, 0, 0, 0}
    };
    static byte Dead[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 0, 0, 0},
        {0, 0, 0, 1, 1, 0, 2, 0},
        {0, 2, 2, 2, 2, 2, 2, 0},
        {0, 2, 0, 2, 2, 0, 0, 0},
        {0, 0, 0, 3, 3, 0, 0, 0},
        {0, 0, 3, 0, 0, 3, 0, 0},
        {0, 3, 0, 0, 0, 0, 3, 0}
    };

    static java.awt.image.BufferedImage CreateImage(byte pixs[][], int w, int h, Pigment p[]) {
        ColorMap bmap = new ColorMap(w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    bmap.SetPixel(x, y, p[pixs[x][y] - 1], true, false);
                }
            }
        }
        return bmap.ToBufferedImage();
    }

    static java.awt.image.BufferedImage CreateLargeImage(byte pixs[][], int w, int h, Pigment p[]) {
        ColorMap bmap = new ColorMap(w << 1, h << 1);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    int xx = (x << 1);
                    int yy = (y << 1);
                    bmap.SetPixel(xx, yy, p[pixs[x][y] - 1], true, false);
                    bmap.SetPixel(xx + 1, yy, p[pixs[x][y] - 1], true, false);
                    bmap.SetPixel(xx, yy + 1, p[pixs[x][y] - 1], true, false);
                    bmap.SetPixel(xx - 1, yy, p[pixs[x][y] - 1], true, false);
                    bmap.SetPixel(xx, yy - 1, p[pixs[x][y] - 1], true, false);
                }
            }
        }
        return bmap.ToBufferedImage();
    }

    static java.awt.image.BufferedImage CreateImage(byte pixs[][], int w, int h, Pigment p[], Pigment shade[], PixelEffect effect) {
        ColorMap bmap = new ColorMap(w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    bmap.SetPixel(x, y, p[pixs[x][y] - 1], true, false);
                    bmap.EffectPixel(x, y, shade[pixs[x][y] - 1], true, false, effect);
                }
            }
        }
        return bmap.ToBufferedImage();
    }

    static java.awt.image.BufferedImage CreateBluredImage(byte pixs[][], int w, int h, Pigment p[], Pigment shade[], PixelEffect effect) {
        ColorMap bmap = new ColorMap(w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    int at = pixs[x][y] - 1;
                    bmap.SetPixel(x, y, p[at], true, false);
                    bmap.EffectPixel(x - 1, y - 1, shade[at], true, false, effect);
                    bmap.EffectPixel(x - 1, y, shade[at], true, false, effect);
                    bmap.EffectPixel(x - 1, y + 1, shade[at], true, false, effect);
                    bmap.EffectPixel(x, y - 1, shade[at], true, false, effect);
                    bmap.EffectPixel(x, y, shade[at], true, false, effect);
                    bmap.EffectPixel(x, y + 1, shade[at], true, false, effect);
                    bmap.EffectPixel(x + 1, y - 1, shade[at], true, false, effect);
                    bmap.EffectPixel(x + 1, y, shade[at], true, false, effect);
                    bmap.EffectPixel(x + 1, y + 1, shade[at], true, false, effect);
                }
            }
        }
        return bmap.ToBufferedImage();
    }

    static java.awt.image.BufferedImage CreateLargeBluredImage(byte pixs[][], int w, int h, Pigment p[], Pigment shade[], PixelEffect effect) {
        ColorMap bmap = new ColorMap(w << 1, h << 1);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    int at = pixs[x][y] - 1;
                    int xx = (x << 1);
                    int yy = (y << 1);
                    bmap.EffectPixel(xx - 1, yy - 1, p[at], false, true, PixelEffect.Average);
                    bmap.SetPixel(xx - 1, yy, p[at], false, true);
                    bmap.EffectPixel(xx - 1, yy + 1, p[at], false, true, PixelEffect.Average);
                    bmap.SetPixel(xx, yy - 1, p[at], false, true);
                    bmap.SetPixel(xx, yy, p[at], false, true);
                    bmap.SetPixel(xx, yy + 1, p[at], false, true);
                    bmap.EffectPixel(xx + 1, yy - 1, p[at], false, true, PixelEffect.Average);
                    bmap.SetPixel(xx + 1, yy, p[at], false, true);
                    bmap.EffectPixel(xx + 1, yy + 1, p[at], false, true, PixelEffect.Average);

                }
            }
        }
        //
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (pixs[x][y] > 0) {
                    int at = pixs[x][y] - 1;
                    for (int xx = (x << 1) - 1; xx <= (x << 1) + 1; xx++) {
                        for (int yy = (y << 1) - 1; yy <= (y << 1) + 1; yy++) {

                            bmap.EffectPixel(xx - 1, yy - 1, shade[at], false, true, effect);
                            bmap.EffectPixel(xx - 1, yy, shade[at], false, true, effect);
                            bmap.EffectPixel(xx - 1, yy + 1, shade[at], false, true, effect);
                            bmap.EffectPixel(xx, yy - 1, shade[at], false, true, effect);
                            bmap.EffectPixel(xx, yy, shade[at], false, true, effect);
                            bmap.EffectPixel(xx, yy + 1, shade[at], false, true, effect);
                            bmap.EffectPixel(xx + 1, yy - 1, shade[at], false, true, effect);
                            bmap.EffectPixel(xx + 1, yy, shade[at], false, true, effect);
                            bmap.EffectPixel(xx + 1, yy + 1, shade[at], false, true, effect);
                        }
                    }
                }
            }
        }
        return bmap.ToBufferedImage();
    }
}

abstract class PhysicalPoint extends Point {

    final static double Scale = 7e3f;
    final static double MeterScale = 1.0f / 7e3f;
    protected Mass mass;
    protected Speed speed;
    protected SphericalCoord angle;
    protected Velocity v;
    protected Length radius;
    protected Local local;
}

class Design {

    byte size;
    byte wheels;
    byte fuel;
    byte engine;
    byte armour;
    byte material;

    Design(byte size, byte wheels, byte fuel, byte engine) {

        this.size = size;
        this.wheels = wheels;
        this.fuel = fuel;
        this.engine = engine;
        this.material = 10;
        this.armour = 10;
    }

    Design(byte size, byte wheels, byte fuel, byte engine, byte armour, byte material) {

        this.size = size;
        this.wheels = wheels;
        this.fuel = fuel;
        this.engine = engine;
        this.material = material;
        if (this.material > 16) {
            this.material = 16;
        }
        if (this.material < 1) {
            this.material = 1;
        }
        if (armour > 8) {
            armour = 8;
        }
        if (armour < 1) {
            armour = 1;
        }
        this.armour = (byte) (2 + Math.abs(armour * material - 3));
    }

    static Design Cycle() {

        return new Design((byte) (4 + Random.Next(4)), (byte) (2), (byte) (1 + Random.Next(2)), (byte) (8 + Random.Next(32)));

    }

    static Design Carrage() {

        return new Design((byte) (8 + Random.Next(4)), (byte) (4), (byte) (1 + Random.Next(4)), (byte) (16 + Random.Next(32)));

    }

    static Design Vantage() {

        return new Design((byte) (8 + Random.Next(6)), (byte) (4), (byte) (2 + Random.Next(4)), (byte) (16 + Random.Next(32)));

    }

    static Design Truckage() {

        return new Design((byte) (8 + Random.Next(8)), (byte) (6), (byte) (4 + Random.Next(4)), (byte) (32 + Random.Next(32)));

    }

    static Design Omnibus() {

        return new Design((byte) (8 + Random.Next(8)), (byte) (6), (byte) (8 + Random.Next(4)), (byte) (16 + Random.Next(32)));

    }
}
@FunctionalInterface
interface CollisionUpdate{
    public Point Hit(Ray field,double tr,int damage);
}
abstract class GameObject extends MapElement implements CollisionUpdate{

    Vector direct = new Vector(1, 0, 0);
    java.awt.image.BufferedImage Icon;

    RingList<ParticlePoint> particles = new RingList();
    RingList<ParticlePoint> removeParticles = new RingList();

    GameObject(float mass, int radius) {
        super();
        this.mass = new Mass(new Point(0, 0), mass, radius);
        this.detector = new CollisionDetector[1];
        this.detector[0] = new CollisionDetector(this.mass.Value(), 0, 0, 0, radius);
    }

    GameObject() {
        super();
    }

    @Override
    boolean RunSimulation() {
        return true;
    }

    GameObjectType Type() {
        return GameObjectType.GAME_OBJECT;
    }

    void RenderParticles(Screen screen) {

        Node<ParticlePoint> e = this.particles.Start();

        for (; e.data != null; e = e.next) {
            e.data.Render(screen);
        }
    }

    void UpdateParticles(GameTime time, Camera cam) {

        Node<ParticlePoint> e = this.particles.Start();

        //System.out.println("Ready Particles "+this.particles.Length());
        for (; e.data != null; e = e.next) {
            e.data.Update(time, cam);
            if (e.data.Dispose()||this.particles.Length()>10+removeParticles.Length()) {
                removeParticles.Append(e.data);
            }
            //System.out.println("Updating Missile");
        }
    }

    void RemoveParticles() {
        if (this.removeParticles.header.next != null) {
            Node<ParticlePoint> n = this.removeParticles.header.next.Remove();
            if (n.data != null) {
                this.particles.Remove(n.data);
            }
        }
        //System.out.println("Ready To Remove"+this.removeParticles.Length());
    }
    public abstract Point Hit(Ray field,double tr,int damage);
    abstract void PaintData(Graphics2D g, int x, int y);

    @Override
    DetectorResult Detect(java.util.ArrayList<MapElement> sec) {
        for (int i = 1; i < sec.size(); i++) {
            MapElement me = sec.get(i);
            if (me != this) {

                DetectorResult result = null;
                DetectorResult detected = null;
                for (int d = 0; d < this.detector.length; d++) {
                    if (result == null) {
                        result = detector[d].Detect(me, this);
                        detected = result;
                    } else {
                        while (detected.next != null) {
                            detected = detected.next;
                        }

                        DetectorResult test = detector[d].Detect(me, this);

                        if (test != null) {
                            detected.next = test;
                            detected = detected.next;
                        }
                    }

                }
                //if(result!=null){
                //System.out.println("Detect"+result+me.getClass());
                //}
                return result;
            }
        }
        return null;
    }

    @Override
    void Collision(DetectorResult result) {
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        if (with != null) {
            float ax = (float) (this.x - with.x);
            float ay = (float) (this.y - with.y);
            if (this != with) {
                Class c = with.getClass();
                if (!(c.equals(Car.class) || c.equals(Van.class) || c.equals(Truck.class) || c.equals(Bus.class) || c.equals(Bike.class))) {

                    if (c.equals(PC.class)) {
                        PC pc = (PC) with;
                        pc.inspect = this;
                    }
                    with.x -= (ax * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    with.y -= (ay * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    Map.square.MoveElementTo(with, (float) with.x, (float) with.y);
                    this.x += (ax * this.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    this.y += (ay * this.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    Map.square.MoveElementTo(this, (float) x, (float) y);
                } else {

                    return false;
                }
            }

            /* float damage = (float) ((m * m * Random.NextDouble() * s * s) / (100000.0f * mass*Map.blockSizeInPixels*Map.blockSizeInPixels));
             if (armourPoints > 0) {
             damage -= armourPoints;
             if (damage > (armourPoints >> 3)) {
             armourPoints--;
             damage -= armourPoints;
             }
             }
             if (damage > 0 && bodyPoints > 0) {
             bodyPoints -= damage;
             }*/
            return true;

        }

        return false;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {

        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        g.drawImage(Icon, x - 16, y - 16, null);
    }

}

class Item extends GameObject {

    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

class Container extends GameObject {

    byte space;
    java.util.ArrayList<GameObject> contains;

    boolean AddObject(GameObject add) {
        if (space > contains.size()) {
            contains.add(add);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

abstract class Weapon extends GameObject {

    MapElement placed;

    Weapon(float mass, int radius) {
        super(mass, radius);
    }

    void Place(Vehicle v, byte side, byte section) {

        this.placed = v;
        v.weapons[side][section] = this;
        int s = section;
        switch (side) {
            case 0: {

                this.x = v.detector[s].at.x;
                this.y = v.detector[s].at.y;
                this.angle.phi = (float) Math.PI;
                break;
            }
            case 1: {
                this.x = v.detector[s].at.x + v.detector[s].radius.Value();
                this.y = v.detector[s].at.y;
                break;
            }
            case 2: {
                this.x = v.detector[s].at.x;
                this.y = v.detector[s].at.y + v.detector[s].radius.Value();
                this.angle.phi = (float) Math.PI * 0.5f;
                break;
            }
            case 3: {
                this.x = v.detector[s].at.x;
                this.y = v.detector[s].at.y - v.detector[s].radius.Value();
                this.angle.phi = (float) (Math.PI + Math.PI * 0.5);
                break;
            }
            case 4: {
                this.x = v.detector[s].at.x - v.detector[s].radius.Value();
                this.y = v.detector[s].at.y;
                this.angle.phi = -(float) Math.PI;

                break;
            }
        }
    }
}

/**
 *
 * @author Gerwyn
 */
class Ammo extends GameObject {

    int amount;

    Ammo(byte type) {
        super(type, 8);
        this.type = (byte) (type % 10);
        switch (this.type) {
            case 0: {
                this.Icon = Game.Icons.get("Ammo1");
                break;
            }
            case 1: {
                this.Icon = Game.Icons.get("Ammo1");
                break;
            }
            case 2: {
                this.Icon = Game.Icons.get("Ammo2");
                break;
            }
            case 3: {
                this.Icon = Game.Icons.get("Ammo3");
                break;
            }
            case 4: {
                this.Icon = Game.Icons.get("Ammo4");
                break;
            }
            case 5: {
                this.Icon = Game.Icons.get("Ammo5");
                break;
            }
            case 6: {
                this.Icon = Game.Icons.get("Ammo6");
                break;
            }
            case 7: {
                this.Icon = Game.Icons.get("Ammo7");
                break;
            }
            case 8: {
                this.Icon = Game.Icons.get("Ammo8");
                break;
            }
            case 9: {
                this.Icon = Game.Icons.get("Ammo9");
                break;
            }
            default: {
                this.Icon = Game.Icons.get("Ammo9");
                break;
            }
        }
    }
    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

class Tool extends Weapon {

    Tool(float mass, int radius) {
        super(mass, radius);
    }

    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

class MeleeWeapon extends Weapon {

    MeleeWeapon(float mass, int radius) {
        super(mass, radius);
    }

    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

abstract class RangedWeapon extends Weapon {

    final static byte TURRET = 0;
    final static byte FRONT = 1;
    final static byte LEFT = 2;
    final static byte RIGHT = 3;
    final static byte REAR = 4;
    boolean internal;
    byte vehicular;
    float rof;
    float rate;
    Ammo ammo;
    byte ammoType;
    byte size;
    Mass ammoMass;
    Mass massOfBullet;
    Ray direction;

    public RangedWeapon(boolean internal, byte size, byte tech) {
        super(size * tech, size);
        this.internal = internal;
        this.size = size;

    }

    void Ammo(int add) {
        this.ammo.amount += add;
    }

    void UseAmmo() {
        this.ammo.amount--;
    }

    boolean RunSimulation() {
        rate += Game.time.AnimTime();
        return true;
    }

    abstract Shot Shoot();

    @Override
    public Point Hit(Ray field,double tr,int damage){
    return this;
    }
    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }
}

class Pistol extends RangedWeapon {

    public Pistol(boolean internal, byte size, byte type, byte tech) {
        super(internal, size, tech);
        this.rof = 1.0f / (20 - tech);
        this.force = (short) (this.mass.Value() * size * 10);
        this.energy = (short) (this.force * size);
        this.ammoType = 1;
        this.massOfBullet = new Mass(new Point(0, 0), (float) (this.mass.Value() / (this.ammoType * tech)), 1);
        this.type = (byte) (type % 3);
        switch (this.type) {
            case 0: {
                this.Icon = Game.Icons.get("Pistol1");
                break;
            }
            case 1: {
                this.Icon = Game.Icons.get("Pistol1");
                break;
            }
            case 2: {
                this.Icon = Game.Icons.get("Pistol2");
                break;
            }
            default: {
                this.Icon = Game.Icons.get("Pistol2");
                break;
            }
        }
    }

    Shot Shoot() {
        if (ammo.amount > 0 && rate > rof) {
            rate = 0;
            UseAmmo();
            return new MachineShot((float) (this.x + this.placed.x), (float) (this.y + this.placed.y), (float) (this.angle.phi), this.force, this.energy, this.massOfBullet.Value());
        }
        return null;
    }
}

class MachineGun extends RangedWeapon {

    public MachineGun(boolean internal, byte size, byte tech) {
        super(internal, size, tech);
        this.rof = 1.0f / (10 - tech);
        this.mass = new Mass(new Point(0, 0), size * tech, 1);
        this.force = (short) (this.mass.Value() * size * 10);
        this.energy = (short) (this.force * size);
        this.massOfBullet = new Mass(new Point(0, 0), (float) (this.mass.Value() / (Math.PI * tech)), 1);
    }

    Shot Shoot() {
        if (ammo.amount > 0 && rate > rof) {
            rate = 0;
            UseAmmo();
            return new MachineShot((float) (this.x + this.placed.x), (float) (this.y + this.placed.y), (float) (this.angle.phi), this.force, this.energy, this.massOfBullet.Value());
        }
        return null;
    }

    void paint(Graphics2D g, int osx, int osy, int light) {

    }

    DetectorResult Detect(MapElement me) {

        if (me != this) {

            DetectorResult result = null;
            DetectorResult detected = null;
            for (int d = 0; d < this.detector.length; d++) {
                if (result == null) {
                    result = detector[d].Detect(me, this);
                    detected = result;
                } else {
                    while (detected.next != null) {
                        detected = detected.next;
                    }

                    DetectorResult test = detector[d].Detect(me, this);

                    if (test != null) {
                        detected.next = test;
                        detected = detected.next;
                    }
                }

            }
            //if(result!=null){
            //System.out.println("Detect"+result+me.getClass());
            //}
            return result;
        }
        return null;
    }

    void Collision(DetectorResult with) {
        this.effect = with;
    }
}

/**
 *
 * @author Gerwyn
 */
abstract class Vehicle extends MapElement {

    protected VehicleImage anim;
    protected Design design;
    protected Weapon weapons[][];
    protected Container storage;
    protected AccelerationRate accelRate;
    //AngularMomentum angularMomentum;
    protected AngularVelocity av;
    protected AngularAcceleration aa;

    protected Coefficient dynamics;
    //
    protected Coefficient friction;//1
    protected Coefficient invfriction;//1
    protected Mass fuel;
    protected Coefficient fuelEfficency;
    protected Coefficient drive;
    protected SphericalCoord stearAngle;
    protected Coefficient gear;
    protected Coefficient currentGear;
    protected SphericalCoord stearing;
    protected Credits cost;
    protected byte fuelTank;
    protected byte engine;
    protected byte chassis;
    protected byte wheels[];
    protected MapList round[][];
    protected Driver driver;

    public Vehicle() {
        super();
    }

    GameObjectType Type() {
        return GameObjectType.VEHICLE;
    }

    void AddWeapon(Weapon add, byte side, byte section) {
        add.Place(this, side, section);
    }

    void FireAllWeapons() {
        for (int side = 0; side < 5; side++) {
            for (int sec = 0; sec < this.detector.length; sec++) {
                if (this.weapons[side][sec] != null) {
                    RangedWeapon w = (RangedWeapon) this.weapons[side][sec];
                    Shot s = w.Shoot();
                    if (s != null) {
                        Map.square.PlaceElement(s);
                        //CarRage.shotSims.add(s);
                    }
                }
            }
        }
    }
    //

    void Build(Design d) {
        design = d;
        this.chassis = (byte) (d.size >> 1);
        this.stearing = new SphericalCoord((float) (Math.PI / (d.wheels * d.size)));
        this.gear = new Coefficient((float) ((d.size * d.wheels)));
        this.currentGear = new Coefficient(gear.Value());
        this.drive = new Coefficient(0);
        this.stearAngle = new SphericalCoord(0);
        this.engine = (byte) (d.engine + d.size);
        this.wheels = new byte[d.wheels];
        //
        for (int w = 0; w < this.wheels.length; w++) {
            this.wheels[w] = d.wheels;
        }
        //
        this.fuelTank = d.fuel;
        this.fuel = new Mass(new Point(0, 0), fuelTank, 1);
        this.armourPoints = (short) (d.size * d.size * design.armour);
        this.CalcMassForce();
        this.power = (int) (10 * this.engine * this.wheels.length * Map.blockCenterInPixels);

        while (this.force > this.power) {
            if (engine > d.size * 4) {
                engine--;
            }
            if (engine < d.size * 4) {
                engine++;
            }
            if (fuelTank > 1) {
                fuelTank--;
            }
            //System.out.println("OK calc");
            this.CalcMassForce();
            //System.out.println("OK"+this.power+" "+this.force);
            this.power = (int) (10 * this.engine * this.wheels.length * Map.blockCenterInPixels);

            //System.out.println("Error?");
        }
        this.dynamics = new Coefficient(100000f * (float) (d.size * d.size * d.size * Map.blockCenterInPixels) / mass.Value());
        this.CalcEfficency();
        this.First();
        this.bodyPoints = this.force + this.power + this.energy;
        this.round = new MapList[3][3];
        this.cost = new Credits(this.mass.Value() * 5 * (1 + Math.abs(design.material - 8)));

        av = new AngularVelocity(0);
        aa = new AngularAcceleration(0);
        //angularMomentum=new AngularMomentum(mass,new AngularVelocity(0),0,1);
        System.out.println(this.mass + "kg");
        System.out.println(this.cost + "credits");
    }

    void CalcEfficency() {
        this.energy = (short) (Math.pow((float) this.power / (float) this.force, 2) * this.engine);
        this.accelRate = new AccelerationRate((float) (energy / (mass.Value() * this.wheels.length / (this.engine / mass.Value()))));
        this.fuelEfficency = new Coefficient((float) (this.mass.Value() / Math.sqrt(this.engine * this.mass.Value())));
    }

    void CalcMassForce() {
        this.mass = new Mass(new Point(0, 0), (float) (this.chassis * design.material + design.armour * design.size + this.engine * 50 + this.stearing.phi * design.material * this.wheels.length * design.material + this.fuelTank * design.material), design.size);
        this.force = (short) (this.mass.Value() * 10);

    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        //ELECTRO MAG
        if (with != null) {
            //float m = with.mass;
            //float s = with.speed;
            float ax = (float) (this.x - with.x);
            float ay = (float) (this.y - with.y);
            if (this != with) {
                        //n++;
                //

                //if(ax>1e-3 || ax<-1e-3)    with.atx -= (this.detector[0].radius/ax*this.detector[0].radius)*CarRage.time;
                //if(ay>1e-3 || ay<-1e-3)    with.aty -= (this.detector[0].radius/ay*this.detector[0].radius)*CarRage.time;
                with.x -= (ax * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                with.y -= (ay * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;

                //CarRage.square.MoveElementTo(with, with.atx, with.aty);       
            }

            /* float damage = (float) ((m * m * Random.NextDouble() * s * s) / (100000.0f * mass*Map.blockSizeInPixels*Map.blockSizeInPixels));
             if (armourPoints > 0) {
             damage -= armourPoints;
             if (damage > (armourPoints >> 3)) {
             armourPoints--;
             damage -= armourPoints;
             }
             }
             if (damage > 0 && bodyPoints > 0) {
             bodyPoints -= damage;
             }*/
            return true;

        }

        return false;
    }

    boolean RunSimulation() {

        int xat = this.local.x;//(int)(atx*Map.blockScale);
        int yat = this.local.y;//(int)(aty*Map.blockScale);
        //System.out.println(xat+" "+yat);
        for (int xa = -1; xa < 2; xa++) {
            int ax = xat + xa;
            for (int ya = -1; ya < 2; ya++) {
                int ay = yat + ya;
                if (ax >= 0 && ax < Map.square.width && ay >= 0 && ay < Map.square.height) {
                    this.round[1 + xa][1 + ya] = Map.square.element[ax][ay];
                } else {
                    this.round[1 + xa][1 + ya] = null;
                }
            }
        }
        boolean ok = !(this.round[1][1] == null || this.round[0][1] == null || this.round[1][0] == null || this.round[2][1] == null || this.round[1][2] == null || this.round[0][2] == null || this.round[2][0] == null || this.round[2][2] == null || this.round[0][0] == null);
        if (ok) {
            Ground gravis = (Ground) this.round[1][1].Get(0).contence.get(0);
            friction = gravis.friction;
            invfriction = new Coefficient((float) (1.0 / gravis.friction.Value()));

            if (this.fuel.Value() > 0) {
                this.fuel.SetMass(fuel.Value() - (float) (this.fuelEfficency.Value() * (this.drive.Value() * 12000 * this.mass.Value() * Game.time.AnimTime()) / Math.sqrt(9e16 / (1 + this.speed.Value()))));
            }
            this.speed = v.Magnitude();
            if (speed.Value() < 1.0e-8) {
                v.Value().x = 0;
                v.Value().y = 0;
            }
            float n2 = (float) ((1 + speed.Value() * speed.Value()) * 100000f * Game.time.AnimTime2());//N
            float a2 = 1.0f / (n2 / mass.Value());//1/ac
            float fi = 1.0f - friction.Value();
            //

            MapElement with = null;
            float momentum = speed.Value();
            double cs = Math.cos(this.angle.phi);
            double si = Math.sin(this.angle.phi) * Math.cos(angle.theta);
            //float dir = 1;
            if (this.reverse) {
                momentum = -momentum;
            }
            float ac = (float) (this.drive.Value() * 12000);
            float ar = (float) (stearAngle.phi * speed.Value() / mass.Value() / Game.time.AnimTime() * a2) * friction.Inverse();
            //if (ar < 0) {
            //    dir = -1;
            //}
            float mx = (float) (momentum * cs + v.Value().x) * 0.5f;
            float my = (float) (momentum * si + v.Value().y) * 0.5f;
            float dx = (float) (ac * cs);
            float dy = (float) (ac * si);
            this.effect = null;

            round[0][0].Get(0).CollisionTest(this);
            round[1][0].Get(0).CollisionTest(this);
            round[2][0].Get(0).CollisionTest(this);
            round[0][1].Get(0).CollisionTest(this);
            round[1][1].Get(0).CollisionTest(this);
            round[2][1].Get(0).CollisionTest(this);
            round[0][2].Get(0).CollisionTest(this);
            round[1][2].Get(0).CollisionTest(this);
            round[2][2].Get(0).CollisionTest(this);
            if (this.effect != null) {
                int n = 1;
                float an = 0;
                float tm = 0;
                Vector tv = new Vector(0, 0);
                while (this.effect != null) {
                    if (this.effect.with == this && this.effect.obj != with && this.effect.obj != driver && this.effect.with != driver) {
                        //
                        if (this.effect.obj.CollisionSimulation(this)) {
                            n++;
                            //
                            //System.out.println((this.effect.obj.detector==null)+" "+this.effect.obj.getClass().toString());
                            float me = (this.effect.obj.mass.Value() / this.mass.Value());
                            tm += me;
                            tv.Minus(this.effect.obj.v.Value());
                            SphericalCoord sc = new SphericalCoord(-(float) (v.Value().x), -(float) (v.Value().y));//perp
                            an += sc.phi * me;
                        }
                    }
                    this.effect = this.effect.next;
                }
                tv.Plus(v.Value().Multiply(n));
                if (n > 1) {
                    tm = 0.5f * tm / (n * n);
                    v.Value().x -= tv.x * tm;
                    v.Value().y -= tv.y * tm;
                }
                av.Value().phi += an * Game.time.AnimTime();
            }
            v.Value().x = (float) (mx * fi + v.Value().x * friction.Value());
            v.Value().y = (float) (my * fi + v.Value().y * friction.Value());

            float resist = (float) (((this.dynamics.Value() / (1 + speed.Value() * speed.Value()) * n2 * this.friction.Value()) / (mass.Value() * Map.blockSizeInPixels)) * Game.time.AnimTime());

            av.Value().phi *= resist;
            Map.square.MoveElementTo(this, (float) x, (float) y);
            //currentMove.next = new MoveResult(0, -(float)(v.Value().x * resist), -(float)(v.Value().y * resist), this);
            //currentMove = CarRage.currentMove.next;

            //currentMove.next = new MoveResult(ar + av.Value().phi, dx, dy, this);
            //currentMove = CarRage.currentMove.next;
            //
            return true;
        }
        return false;
    }

    boolean Running() {
        return bodyPoints > 0 && fuel.Value() > 0 && power > 0 && energy > 0 && force <= power && engine > 0 && chassis > 0;
    }

    void First() {
        this.currentGear = new Coefficient(this.gear.Value() * 6);
        this.reverse = false;
    }

    void Second() {
        this.currentGear = new Coefficient(this.gear.Value() * 5);
        this.reverse = false;
    }

    void Third() {
        this.currentGear = new Coefficient(this.gear.Value() * 4);
        this.reverse = false;
    }

    void Fourth() {
        this.currentGear = new Coefficient(this.gear.Value() * 3f);
        this.reverse = false;
    }

    void Fith() {
        this.currentGear = new Coefficient(this.gear.Value() * 2);
        this.reverse = false;
    }

    void Sixth() {
        this.currentGear = new Coefficient(this.gear.Value());
        this.reverse = false;
    }

    void Break() {
        //this.drive=0;
        float u = (float) (this.friction.Value() * this.force * this.stearing.phi * this.wheels.length * Game.time.AnimTime());
        this.v.Value().x -= u * this.v.Value().x / 60;//=this.wheels.length*friction;
        this.v.Value().y -= u * this.v.Value().y / 60;//-=this.wheels.length*friction;

    }

    void Cluch() {
        this.drive = new Coefficient(0);
    }

    void Accelerate() {
        this.reverse = false;
        if (this.Running()) {

            this.drive = new Coefficient(this.drive.Value() + this.friction.Value() * this.accelRate.Value() * this.currentGear.Value() * (float) Game.time.AnimTime());
            float rpm = (this.power / (this.mass.Value() * this.speed.Value())) * this.currentGear.Inverse();
            if (this.drive.Value() > rpm) {
                this.drive = new Coefficient(rpm);
            }
        }
    }

    void Decelerate() {
        if (speed.Value() < 0.01f) {
            this.reverse = true;
        }
        if (this.Running()) {
            if (this.reverse) {
                this.drive = new Coefficient(this.drive.Value() - this.friction.Value() * this.accelRate.Value() * this.gear.Value() * (float) Game.time.AnimTime());
                float rpm = (this.power / (this.mass.Value() * this.speed.Value())) * (this.gear.Inverse());
                if (this.drive.Value() < -rpm) {
                    this.drive = new Coefficient(-rpm);
                }
            } else {
                this.drive = new Coefficient(this.drive.Value() - this.friction.Value() * this.accelRate.Value() * this.gear.Value());
                float rpm = (this.power / (this.mass.Value() * this.speed.Value())) * (this.gear.Inverse());
                if (this.drive.Value() < -rpm) {
                    this.drive = new Coefficient(-rpm);
                }
            }
        }
    }

    void TurnClockWise(Vehicle playersVehicle) {
        float turn = (float) (Math.PI) * (float) Game.time.AnimTime();
        float sa = (float) (-playersVehicle.stearing.phi * turn);
        angle.phi += sa;

        this.stearAngle.phi += sa;//;

        if (this.stearAngle.phi < -playersVehicle.stearing.phi) {
            this.stearAngle.phi = -playersVehicle.stearing.phi;
        }
        if (this.stearAngle.phi < -1) {
            this.stearAngle.phi = -1;
        }

    }

    void TurnAntiClockWise(Vehicle playersVehicle) {
        float turn = (float) (Math.PI) * (float) Game.time.AnimTime();
        float sa = (float) (playersVehicle.stearing.phi * turn);
        angle.phi += sa;
        this.stearAngle.phi += sa;//+av.Angle()* CarRage.time;

        if (this.stearAngle.phi > playersVehicle.stearing.phi) {
            this.stearAngle.phi = playersVehicle.stearing.phi;
        }
        if (this.stearAngle.phi > 1) {
            this.stearAngle.phi = 1;
        }

    }

    DetectorResult Detect(java.util.ArrayList<MapElement> sec) {
        for (int i = 1; i < sec.size(); i++) {
            MapElement me = sec.get(i);

            if (me != this) {

                DetectorResult result = null;
                DetectorResult detected = null;
                for (int d = 0; d < this.detector.length; d++) {
                    if (result == null) {
                        result = detector[d].Detect(me, this);
                        detected = result;
                    } else {
                        while (detected.next != null) {
                            detected = detected.next;
                        }
                        DetectorResult test = detector[d].Detect(me, this);

                        if (test != null) {
                            detected.next = test;
                            detected = detected.next;
                        }
                    }

                }
                //if(result!=null){
                //System.out.println("Detect"+result+me.getClass());
                //}
                return result;
            }
        }
        return null;
    }

    void Collision(DetectorResult with) {
        this.effect = with;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
    }
}

public abstract class Player extends MapElement {

    public static void ShareDna(Player a, Player b) {
        a.ai.RecompileDna(b);
    }

    public static void ShareDna(java.util.ArrayList<Player> a, Player b) {
        for (int i = 0; i < a.size(); i++) {
            a.get(i).ai.RecompileDna(b);
        }
    }
    final static byte SeqWalk = 0;
    final static byte SeqStand = 1;
    final static byte SeqSignal = 2;
    final static byte SeqGrab = 3;
    final static byte SeqThrow = 4;
    final static byte SeqFirePistol = 5;
    final static byte SeqFireGun = 6;
    final static byte SeqFireRifle = 7;
    final static byte Dead = 8;
    java.awt.image.BufferedImage images[][];
    java.awt.image.BufferedImage result;
    byte sequanceType = 1;
    byte currentSequance = 2;
    Time currentSequanceTime;//0
    Time sequanceTimer;//1
    SphericalCoord animAngle;
    SphericalCoord lastAngle;
    Coefficient friction;
    MapList round[][];
    boolean male;
    boolean move = false;
    short stamina;
    short mentalForce;
    short mentalPower;
    short mentalEnergy;
    int psi;
    MapElement inspect;
    protected Map scan;
    protected AI ai;

    Player(java.awt.image.BufferedImage imgs[][]) {
        super();
        this.mass = new Mass(new Point(0, 0), 15, 3);
        this.force = (short) (this.mass.Value() * 10);
        this.images = imgs;
        round = new MapList[3][3];
        this.detector = new CollisionDetector[1];
        this.detector[0] = new CollisionDetector(mass.Value(), 0, 0, 0, 3);
        animAngle = new SphericalCoord(0);
        lastAngle = new SphericalCoord(0);
    }

    Player() {
        super();
        this.mass = new Mass(new Point(0, 0), 15, 3);
        this.force = (short) (this.mass.Value() * 10);
        round = new MapList[3][3];
        this.detector = new CollisionDetector[1];
        this.detector[0] = new CollisionDetector(mass.Value(), 0, 0, 0, 3);
        this.currentSequanceTime = new Time();
        this.sequanceTimer = new Time(1);
        this.images = new java.awt.image.BufferedImage[16][10];
        Pigment p[] = {new Pigment(128 + Random.Next(128), 128 + Random.Next(128), 128 + Random.Next(128)), new Pigment(64 + Random.Next(128), 64 + Random.Next(128), 64 + Random.Next(128)), new Pigment(32 + Random.Next(128), 32 + Random.Next(128), 32 + Random.Next(128)), new Pigment(32 + Random.Next(32), +Random.Next(32), +Random.Next(32))};
        Pigment s[] = new Pigment[4];
        for (int i = 0; i < 4; i++) {
            switch (Random.Next(8)) {
                case 0: {
                    int gray = Random.Next(255);
                    s[i] = new Pigment(gray, gray, gray);
                    break;
                }
                case 1: {
                    s[i] = new Pigment(Random.Next(255), Random.Next(255), Random.Next(255));
                    break;
                }
                case 2: {
                    s[i] = new Pigment(Random.Next(255), Random.Next(255), 0);
                    break;
                }

                case 3: {
                    s[i] = new Pigment(Random.Next(255), 0, Random.Next(255));
                    break;
                }

                case 4: {
                    s[i] = new Pigment(0, Random.Next(255), Random.Next(255));
                    break;
                }

                case 5: {
                    s[i] = new Pigment(Random.Next(255), 0, 0);
                    break;
                }

                case 6: {
                    s[i] = new Pigment(0, Random.Next(255), 0);
                    break;
                }

                case 7: {
                    s[i] = new Pigment(0, 0, Random.Next(255));
                    break;
                }
                default: {
                    s[i] = new Pigment(128 + Random.Next(128), 128 + Random.Next(128), 128 + Random.Next(128));
                    break;
                }

            }
        }
        Pigment r[] = {new Pigment(128 + Random.Next(128), 0, 0), new Pigment(128 + Random.Next(128), 0, 0), new Pigment(128 + Random.Next(128), 0, 0), new Pigment(128 + Random.Next(128), 0, 0)};

        images[0][0] = BitPattern.CreateLargeBluredImage(BitPattern.Walking1, 8, 8, p, s, PixelEffect.Hint);
        //CarRage.SaveImage("Test.png","png", images[0]);
        images[1][0] = BitPattern.CreateLargeBluredImage(BitPattern.Walking2, 8, 8, p, s, PixelEffect.Hint);
        images[2][0] = BitPattern.CreateLargeBluredImage(BitPattern.Standing, 8, 8, p, s, PixelEffect.Hint);
        images[3][0] = BitPattern.CreateLargeBluredImage(BitPattern.Signaling1, 8, 8, p, s, PixelEffect.Hint);
        images[4][0] = BitPattern.CreateLargeBluredImage(BitPattern.Signaling2, 8, 8, p, s, PixelEffect.Hint);
        images[5][0] = BitPattern.CreateLargeBluredImage(BitPattern.Grabing1, 8, 8, p, s, PixelEffect.Hint);
        images[6][0] = BitPattern.CreateLargeBluredImage(BitPattern.Grabing2, 8, 8, p, s, PixelEffect.Hint);
        images[7][0] = BitPattern.CreateLargeBluredImage(BitPattern.Throwing1, 8, 8, p, s, PixelEffect.Hint);
        images[8][0] = BitPattern.CreateLargeBluredImage(BitPattern.Throwing2, 8, 8, p, s, PixelEffect.Hint);
        images[9][0] = BitPattern.CreateLargeBluredImage(BitPattern.AimPistol, 8, 8, p, s, PixelEffect.Hint);
        images[10][0] = BitPattern.CreateLargeBluredImage(BitPattern.AimPistol2, 8, 8, p, s, PixelEffect.Hint);
        images[11][0] = BitPattern.CreateLargeBluredImage(BitPattern.AimPistol3, 8, 8, p, s, PixelEffect.Hint);
        images[12][0] = BitPattern.CreateLargeBluredImage(BitPattern.AimGun, 8, 8, p, s, PixelEffect.Hint);
        images[13][0] = BitPattern.CreateLargeBluredImage(BitPattern.AimRifle, 8, 8, p, s, PixelEffect.Hint);
        images[14][0] = BitPattern.CreateLargeBluredImage(BitPattern.Crawling, 8, 8, p, r, PixelEffect.Hint);
        images[15][0] = BitPattern.CreateLargeBluredImage(BitPattern.Dead, 8, 8, p, r, PixelEffect.Hint);

        for (int an = 0; an < 16; an++) {
            for (int i = 1; i < 10; i++) {
                //System.out.println("anim"+an+"light"+i);  
                images[an][i] = new java.awt.image.BufferedImage(images[an][0].getWidth(), images[an][0].getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
                for (int xx = 0; xx < images[an][0].getWidth(); xx++) {

                    for (int yy = 0; yy < images[an][0].getHeight(); yy++) {
                        int rgb = images[an][i - 1].getRGB(xx, yy);
                        int alpha = (rgb & 0xFF000000) >> 24;
                        int red = (rgb & 0x00FF0000) >> 16;
                        int green = (rgb & 0x0000FF00) >> 8;
                        int blue = (rgb & 0x000000FF);
                        if (alpha < 0) {
                            Pigment c = new Pigment(255, red, green, blue);
                            Pigment d = new Pigment(255, 32, 32, 32);
                            //d.Average(c);
                            c.Hint(d);
                            images[an][i].setRGB(xx, yy, c.ToRGB());
                        }
                    }
                }
            }
        }
        java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
        at.rotate(Vehicle.Angle + angle.phi, 8, 8);
        java.awt.image.AffineTransformOp op = new java.awt.image.AffineTransformOp(at, java.awt.image.AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        result = op.createCompatibleDestImage(images[Math.abs(this.currentSequance)][0], null);
        op.filter(images[Math.abs(this.currentSequance)][0], result);
        animAngle = new SphericalCoord((float) angle.phi);
        lastAngle = new SphericalCoord((float) angle.phi);
    }

    @Override
    GameObjectType Type() {
        return GameObjectType.PLAYER;
    }

    abstract void RandomStats();

    void MoveNorth() {
        move = true;
        if (this.energy > 0) {
            float ms = 19.8f * ((float) (this.power) / this.energy * (float) (this.energy) / this.force);
            this.v.Value().y = -ms;
            this.energy--;
            stamina--;
        } else {
            //energy++;
        }
    }

    void MoveSouth() {
        move = true;
        if (this.energy > 0) {

            float ms = 19.8f * ((float) (this.power) / this.energy * (float) (this.energy) / this.force);
            this.v.Value().y = ms;
            this.energy--;
            stamina--;
        } else {
            //energy++;
        }
    }

    void MoveWest() {
        move = true;
        if (this.energy > 0) {

            float ms = 19.8f * ((float) (this.power) / this.energy * (float) (this.energy) / this.force);
            this.v.Value().x = -ms;
            this.energy--;
            stamina--;
        } else {
            //energy++;
        }
    }

    void MoveEast() {
        move = true;
        if (this.energy > 0) {

            float ms = 19.8f * ((float) (this.power) / this.energy * (float) (this.energy) / this.force);
            this.v.Value().x = ms;
            this.energy--;
            stamina--;
        } else {
            //energy++;
        }
    }

    void Stop() {
        v.Value().x /= 9.8;
        v.Value().y /= 9.8;
        if (energy < stamina) {
            this.energy++;
        } else {
            if (stamina < (force + power)) {
                stamina++;
            }
        }

    }

    boolean CollisionSimulation(MapElement with) {
        //ELECTRO MAG
        if (with != null) {
            //float m = with.mass;
            //float s = with.speed;
            float ax = (float) (this.x - with.x);
            float ay = (float) (this.y - with.y);
            if (this != with) {
                //

                //if(ax>1e-3 || ax<-1e-3)with.atx -= this.detector[0].radius/ax;
                //if(ay>1e-3 || ay<-1e-3)with.aty -= this.detector[0].radius/ay;
                //if(ax>1e-4 || ax<-1e-4)    with.atx -= (this.detector[0].radius/ax*this.detector[0].radius)*CarRage.time;
                //if(ay>1e-4 || ay<-1e-4)    with.aty -= (this.detector[0].radius/ay*this.detector[0].radius)*CarRage.time;
                Class c = with.getClass();
                if (c.equals(Door.class) || c.equals(Window.class) || c.equals(Wall.class) || c.isAssignableFrom(GameObject.class)) {
                    with.x -= (ax * with.detector[0].radius.Value()) / Map.blockCenterInPixels;
                    with.y -= (ay * with.detector[0].radius.Value()) / Map.blockCenterInPixels;
                    Map.square.MoveElementTo(with, (float) with.x, (float) with.y);
                } else {
                    with.x -= (ax * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    with.y -= (ay * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;

                }

                //CarRage.square.MoveElementTo(with, with.atx, with.aty);
            }

            /*
             * float damage = (float) ((m * m * Random.NextDouble() * s * s) /
             * (100000.0f * mass*Map.blockSizeInPixels*Map.blockSizeInPixels));
             * if (armourPoints > 0) { damage -= armourPoints; if (damage >
             * (armourPoints >> 3)) { armourPoints--; damage -= armourPoints; }
             * } if (damage > 0 && bodyPoints > 0) { bodyPoints -= damage; }
             */
            return true;

        }

        return true;
    }

    boolean RunSimulation() {

        int xat = this.local.x;//(int)(atx*Map.blockScale);
        int yat = this.local.y;//(int)(aty*Map.blockScale);
        //System.out.println(xat+" "+yat);
        for (int xa = -1; xa < 2; xa++) {
            int ax = xat + xa;
            for (int ya = -1; ya < 2; ya++) {
                int ay = yat + ya;
                if (ax >= 0 && ax < Map.square.width && ay >= 0 && ay < Map.square.height) {
                    this.round[1 + xa][1 + ya] = Map.square.element[ax][ay];
                } else {
                    this.round[1 + xa][1 + ya] = null;
                }
            }
        }
        if (!move) {
            Stop();

        }

        boolean ok = !(this.round[1][1] == null || this.round[0][1] == null || this.round[1][0] == null || this.round[2][1] == null || this.round[1][2] == null || this.round[0][2] == null || this.round[2][0] == null || this.round[2][2] == null || this.round[0][0] == null);
        if (ok) {
            Ground gravis = (Ground) this.round[1][1].Get(0).contence.get(0);
            if (gravis != null) {
                friction = gravis.friction;
            }
            this.speed = new Speed(v);
            float n2 = (float) ((1 + speed.Value() * speed.Value()) * 100000.0 * Game.time.AnimTime2());//N
            float in = 1.0f / n2;// (1 + speed * speed * 100000f * CarRage.time2);//N
            float f2 = (float) (force * Game.time.AnimTime()) * in;
            //
            if (move) {
                move = false;
            }
            MapElement with = null;
            this.effect = null;
            round[0][0].Get(0).CollisionTest(this);
            round[1][0].Get(0).CollisionTest(this);
            round[2][0].Get(0).CollisionTest(this);
            round[0][1].Get(0).CollisionTest(this);
            round[1][1].Get(0).CollisionTest(this);
            round[2][1].Get(0).CollisionTest(this);
            round[0][2].Get(0).CollisionTest(this);
            round[1][2].Get(0).CollisionTest(this);
            round[2][2].Get(0).CollisionTest(this);
            float an = 0;
            if (this.effect != null) {
                int n = 1;
                float m = 0;
                float e = 0.05f;
                float s = 0;
                //float tm=0;
                //Vector2D tv=new Vector2D(0,0);
                while (this.effect != null) {
                    if (this.effect.with == this && this.effect.obj != with) {
                        this.effect.obj.CollisionSimulation(this);
                        n++;

                        float xd = (float) ((this.x) - (this.effect.obj.x) - (this.effect.withPart.at.x + this.effect.objPart.at.x));
                        float yd = (float) ((this.y) - (this.effect.obj.y) - (this.effect.withPart.at.y + this.effect.objPart.at.y));
                        double d = Math.sqrt((xd * xd) + (yd * yd));
                        float me = (this.effect.obj.mass.Value() / this.mass.Value());
                        float ma = (float) (me / d) * Map.blockSizeInPixels;
                        //tm+=me;
                        //tv.Minus(this.effect.obj.v.Value());

                        //float mn=(float)(me/nm)*Map.blockSizeInPixels;
                        /*
                         * if(sin>0.7071067811865475f ){
                         * sin=0.7071067811865475f;} if(
                         * sin<-0.7071067811865475f){ sin=-0.7071067811865475f;
                         * }
                         */
                        SphericalCoord sc = new SphericalCoord(-(float) v.Value().x, (float) -v.Value().y);//perp
                        an += ma * sc.p * sc.phi;
                        float md = (mass.Value() - e * this.effect.obj.mass.Value());
                        float mt = 1.0f / (this.effect.obj.mass.Value() + mass.Value());
                        float cx = (float) ((1 + e) * this.effect.obj.mass.Value() * mt * this.effect.obj.v.Value().x + this.effect.obj.v.Value().x * md * mt);
                        float cy = (float) ((1 + e) * this.effect.obj.mass.Value() * mt * this.effect.obj.v.Value().y + this.effect.obj.v.Value().y * md * mt);
                        float etx = (float) (v.Value().x / d) / Map.blockCenterInPixels * (float) Game.time.AnimTime();
                        float ety = (float) (v.Value().y / d) / Map.blockCenterInPixels * (float) Game.time.AnimTime();
                        //
                        //System.out.println(vx+":"+vy);
                        v.Value().x = (cx + v.Value().x) * 0.5f;
                        v.Value().y = (cy + v.Value().y) * 0.5f;
                        //mx += etx;
                        //my += ety;
                        cx = (float) ((cx - v.Value().x) * Game.time.AnimTime());
                        cy = (float) ((cy - v.Value().y) * Game.time.AnimTime());
                        this.x -= etx;
                        this.y -= ety;
                        this.x -= cx;
                        this.y -= cy;
                        m += this.effect.obj.mass.Value();
                        s += this.effect.obj.speed.Value();
                        //
                        if (this.effect.obj.speed.Value() <= this.speed.Value()) {
                            inspect = this.effect.obj;
                        }

                    }
                    this.effect = this.effect.next;
                }
                angle.phi += an;
                this.animAngle.phi = angle.phi;
                move = true;
                float damage = (float) ((m * m * Random.NextDouble() * s * s) / (100000.0f * mass.Value() * Map.blockSizeInPixels * Map.blockSizeInPixels));
                if (armourPoints > 0) {
                    damage -= armourPoints;
                    if (damage > (armourPoints >> 3)) {
                        armourPoints--;
                        damage -= armourPoints;
                    }
                }
                if (damage > 0 && bodyPoints > 0) {
                    bodyPoints -= damage;
                }

            } else {
                inspect = null;
                if (energy < stamina) {
                    this.energy++;
                } else {
                    if (stamina < (force + power)) {
                        stamina++;
                    }
                }

            }

            //Map.square.MoveElementTo(this, at.x, at.y);
            float resist = (float) ((1.0f / (1 + speed.Value() * speed.Value()) * n2 * this.friction.Value()) / (mass.Value() * Map.blockSizeInPixels)) * (float) Game.time.AnimTime();
            //System.out.println(vx+":"+vy);
            //CarRage.currentMove.next = new MoveResult(0, -v.Value().x * resist, -v.Value().y * resist, this);
            //CarRage.currentMove = CarRage.currentMove.next;

            //CarRage.currentMove.next = new MoveResult(0, v.Value().x * f2, v.Value().y * f2, this);
            //CarRage.currentMove = CarRage.currentMove.next;
            if (v.Value().x > -0.01 && v.Value().x < 0.01) {
                v.Value().x = 0;
            }
            if (v.Value().y > -0.01 && v.Value().y < 0.01) {
                v.Value().y = 0;
            }
            //
            if (v.Value().y < 0) {
                this.animAngle.phi = (float) (Math.PI * 0.5);

                if (v.Value().x > 0) {
                    this.animAngle.phi += (float) (Math.PI * 0.25);
                } else if (v.Value().x < 0) {
                    this.animAngle.phi -= (float) (Math.PI * 0.25);
                }
            } else if (v.Value().y > 0) {
                this.animAngle.phi = (float) (Math.PI + Math.PI * 0.5);

                if (v.Value().x > 0) {
                    this.animAngle.phi -= (float) (Math.PI * 0.25);
                } else if (v.Value().x < 0) {
                    this.animAngle.phi += (float) (Math.PI * 0.25);
                }
            } else if (v.Value().x > 0) {
                this.animAngle.phi = (float) (Math.PI);
            } else if (v.Value().x < 0) {
                this.animAngle.phi = 0;
            }
            return true;
        }
        return false;
    }

    void Update() {
        this.currentSequanceTime.t += Game.time.AnimTime();
        if (this.currentSequanceTime.Value() > this.sequanceTimer.Value()) {
            switch (sequanceType) {
                case 0: {
                    if (currentSequance != 0) {
                        currentSequance = 0;

                    } else if (currentSequance == 0) {
                        {
                            currentSequance = 1;
                        }
                    }
                    break;
                }
                case 1: {
                    currentSequance = 2;
                    break;
                }
                case 2: {
                    if (currentSequance == 3) {
                        currentSequance = 4;
                    } else if (currentSequance == 4) {
                        currentSequance = -3;
                    } else if (currentSequance == -3) {
                        currentSequance = 0;
                    } else if (currentSequance != 0) {
                        currentSequance = 0;

                    } else if (currentSequance == 0) {

                        currentSequance = 3;
                    }
                    break;
                }
            }
        }
    }

    @Override
    DetectorResult Detect(java.util.ArrayList<MapElement> sec) {
        for (int i = 1; i < sec.size(); i++) {
            MapElement me = sec.get(i);

            if (me != this) {

                DetectorResult result = null;
                DetectorResult detected = null;
                for (int d = 0; d < this.detector.length; d++) {
                    if (result == null) {
                        result = detector[d].Detect(me, this);
                        detected = result;
                    } else {
                        while (detected.next != null) {
                            detected = detected.next;
                        }

                        DetectorResult test = detector[d].Detect(me, this);

                        if (test != null) {
                            detected.next = test;
                            detected = detected.next;
                        }
                    }

                }
                //if(result!=null){
                //System.out.println("Detect"+result+me.getClass());
                //}
                return result;
            }
        }
        return null;
    }

    @Override
    void Collision(DetectorResult with) {
        //CarRage.square.MoveElement(this, dx, dy);
        //System.out.println(with!=null);

        //System.out.println("obj "+with.obj.local+with.obj.getClass().toString());
        //System.out.println("collided "+with.with.local+with.with.getClass().toString());
        //System.out.println("at "+with.atx+" "+with.aty);
        this.effect = with;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {

        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        if (animAngle.phi != lastAngle.phi) {
            java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
            at.rotate(Vehicle.Angle + animAngle.phi, 8, 8);
            java.awt.image.AffineTransformOp op = new java.awt.image.AffineTransformOp(at, java.awt.image.AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            result = op.createCompatibleDestImage(images[Math.abs(this.currentSequance)][light], null);
            op.filter(images[Math.abs(this.currentSequance)][light], result);
            lastAngle.phi = animAngle.phi;
        }
        g.drawImage(result, x - 8, y - 8, null);
        //System.out.println("Paint");
    }
}

abstract class Driver extends Player {

    boolean driving;
    short spiritualForce;
    short spiritualPower;
    short spiritualEnergy;
    short spirit;
    int sanity;
    Vehicle vehicle;
    //Light light;

    public Driver() {
        super();
    }

    abstract void PickUpObject();

    abstract void EnterVehicle(Vehicle playersVehicle);

    abstract void ExitVehicle(Vehicle playersVehicle);

    @Override
    boolean RunSimulation() {
        //if (light != null) {
        //light.x = (int) at.x;
        //light.y = (int) at.y;
        //}
        if (driving) {
            Map.square.MoveElementTo(this, (float) vehicle.x, (float) vehicle.y);

            return true;
        } else {
            return super.RunSimulation();
        }
    }

    @Override
    void RandomStats() {

        force += Random.d(20) + Random.d(20);
        if (male) {
            force += Random.d(20);
        }
        power += 360 + Random.d(20) + Random.d(20) - force;
        if (!male) {
            power += Random.d(20);
        }
        energy += force + power + Random.d(20) + Random.d(20);
        stamina = (short) energy;
        bodyPoints = force + power + energy;
        mentalForce = (short) (Random.d(20) + Random.d(20));
        mentalPower = (short) (mentalForce + Random.d(20) + Random.d(20));
        mentalEnergy = (short) (mentalPower + Random.d(20) + Random.d(20));
        psi = mentalForce + mentalPower + mentalEnergy;

        spiritualForce = (short) (force + mentalForce + Random.d(20) + Random.d(20));
        spiritualPower = (short) (spiritualForce + power + mentalPower + Random.d(20) + Random.d(20));
        spiritualEnergy = (short) (spiritualPower + energy + mentalEnergy + Random.d(20) + Random.d(20));
        sanity = spiritualForce + spiritualPower + spiritualEnergy;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        if (!driving) {
            int x = (int) (osx + this.x);
            int y = (int) (osy + this.y);
            if (animAngle.phi != lastAngle.phi) {
                java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
                at.rotate(Vehicle.Angle + animAngle.phi, 8, 8);
                java.awt.image.AffineTransformOp op = new java.awt.image.AffineTransformOp(at, java.awt.image.AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                result = op.createCompatibleDestImage(images[Math.abs(this.currentSequance)][light], null);
                op.filter(images[Math.abs(this.currentSequance)][light], result);
                lastAngle.phi = animAngle.phi;
            }
            g.drawImage(result, x - 8, y - 8, null);
        } else {
        }
        //System.out.println("Paint");
    }
}

class Inventory {

    GameObject inLeftHand;
    GameObject inRightHand;
    GameObject onHead;
    GameObject onBack;
    GameObject onFront;
    GameObject onLegs;
    GameObject onLeftLeg;
    GameObject onRightLeg;
    Container container;
}

class PC extends Driver {

    CharacterSheet sheet;
    Inventory inventory;

    PC() {
        super();
        armourPoints = 30;//10/50/100+d(50)
        this.RandomStats();
        this.inventory = new Inventory();
        sheet = new CharacterSheet(this);
        //light = new Light();
        //light.size = 3;
        //light.e = 5;
        //CarRage.lights[0] = light;
    }

    boolean AddToRightHand(GameObject go) {
        if (this.inventory.inRightHand != null) {
            if (this.inventory.inRightHand.getClass().equals(Container.class)) {
                Container co = (Container) this.inventory.inRightHand;
                if (!co.AddObject(go)) {
                    return AddToLeftHand(go);
                }
            } else {
                return AddToLeftHand(go);

            }

        } else {
            //System.out.println("2Right");
            this.inventory.inRightHand = go;
            sheet.rightHand.image = Game.InfoIcons.get("HandRight1");

            return true;
        }
        return false;
    }

    boolean AddToLeftHand(GameObject go) {
        if (this.inventory.inLeftHand != null) {
            if (this.inventory.inLeftHand.getClass().equals(Container.class)) {
                Container co = (Container) this.inventory.inLeftHand;
                if (!co.AddObject(go)) {
                    return AddToBack(go);
                }
            } else {
                return AddToBack(go);
            }
        } else {
            this.inventory.inLeftHand = go;
            sheet.leftHand.image = Game.InfoIcons.get("HandLeft1");
            return true;
        }
        return false;
    }

    boolean AddToBack(GameObject go) {
        if (this.inventory.onBack != null) {
            if (this.inventory.onBack.getClass().equals(Container.class)) {
                Container co = (Container) this.inventory.onBack;
                if (!co.AddObject(go)) {
                    return AddToFront(go);
                }
            } else {
                return AddToFront(go);
            }
        } else {
            this.inventory.onBack = go;
            return true;
        }
        return false;
    }

    boolean AddToFront(GameObject go) {
        if (this.inventory.onFront != null) {
            if (this.inventory.onFront.getClass().equals(Container.class)) {
                Container co = (Container) this.inventory.onFront;
                if (!co.AddObject(go)) {
                    return AddToLeftLeg(go);
                }
            } else {
                return AddToLeftLeg(go);
            }
        } else {
            this.inventory.onFront = go;
        }
        return true;
    }

    boolean AddToLeftLeg(GameObject go) {
        if (this.inventory.onLeftLeg == null && !go.getClass().equals(Container.class)) {
            this.inventory.onLeftLeg = go;
            return true;
        } else {
            return AddToRightLeg(go);
        }

    }

    boolean AddToRightLeg(GameObject go) {
        if (this.inventory.onRightLeg == null && !go.getClass().equals(Container.class)) {
            this.inventory.onRightLeg = go;
        } else {
            return false;
        }
        return true;
    }

    @Override
    void PickUpObject() {
        if (this.inspect != null) {
            try {
                if (AddToRightHand((GameObject) this.inspect)) {
                    Map.square.RemoveElement(this.inspect);
                    this.inspect = null;
                    this.sheet.UpDateIcons(this.inventory);
                } else {
                    //send message TO MUCH STUFF
                    //System.out.println("Pick Up Object Error "+this.inspect);
                }
            } catch (Exception ex) {
                System.out.println("Pick Up Object Error " + ex);
                ex.printStackTrace();
            }
        }
    }

    void EnterVehicle(Vehicle playersVehicle) {
        this.driving = true;
        vehicle = playersVehicle = (Vehicle) inspect;
        playersVehicle.driver = this;
        Map.square.MoveElementTo(this, (float) playersVehicle.x, (float) playersVehicle.y);
    }

    void ExitVehicle(Vehicle playersVehicle) {
        this.driving = false;
        playersVehicle.Cluch();
        playersVehicle.driver = null;

        int space = (playersVehicle.anim.image[0].getHeight() >> 1);
        Point dir = playersVehicle.angle.At();
        float x = (float) (space * dir.x);
        float y = (float) (-space * dir.y);
        Map.square.MoveElement(this, x, y);
        vehicle = playersVehicle = null;

    }

    public void PaintSheet(Graphics2D g) {
        sheet.Paint(g);
    }
}

class Person extends Player {

    byte dna[][];
    java.util.HashSet<GameObjectType> friend = new java.util.HashSet<>();
    java.util.HashSet<GameObjectType> foe = new java.util.HashSet<>();
    Class enemy;
    Class ali;
    boolean child;
    short spiritualForce;
    short spiritualPower;
    short spiritualEnergy;
    short spirit;
    int moral;

    public Person() {
        super();
        RandomStats();
    }

    @Override
    void RandomStats() {
        if (child) {
            force += Random.d(20);
            if (male) {
                force += Random.d(20);
            }
            power += 360 + Random.d(20) - force;
            if (!male) {
                power += Random.d(20);
            }
            energy += force + power + Random.d(20);
            bodyPoints = force + power + energy;
            mentalForce = (short) Random.d(20);
            mentalPower = (short) (mentalForce + Random.d(20));
            mentalEnergy = (short) (mentalPower + Random.d(20));
            psi = mentalForce + mentalPower + mentalEnergy;

            dna = new byte[2][psi >> 2];
            spiritualForce = (short) (force + mentalForce + Random.d(5, 20));
            spiritualPower = (short) (spiritualForce + power + mentalPower + Random.d(5, 20));
            spiritualEnergy = (short) (spiritualPower + energy + mentalEnergy + Random.d(5, 20));
            moral = spiritualForce + spiritualPower + spiritualEnergy;
        } else {

            force += Random.d(20) + Random.d(20);
            if (male) {
                force += Random.d(20);
            }
            power += 360 + Random.d(20) + Random.d(20) - force;
            if (!male) {
                power += Random.d(20);
            }
            energy += force + power + Random.d(20) + Random.d(20);
            bodyPoints = force + power + energy;
            mentalForce = (short) (Random.d(20) + Random.d(20));
            mentalPower = (short) (mentalForce + Random.d(20) + Random.d(20));
            mentalEnergy = (short) (mentalPower + Random.d(20) + Random.d(20));
            psi = mentalForce + mentalPower + mentalEnergy;

            dna = new byte[2][psi >> 2];
            spiritualForce = (short) (force + mentalForce + Random.d(20) + Random.d(20));
            spiritualPower = (short) (spiritualForce + power + mentalPower + Random.d(20) + Random.d(20));
            spiritualEnergy = (short) (spiritualPower + energy + mentalEnergy + Random.d(20) + Random.d(20));
            moral = spiritualForce + spiritualPower + spiritualEnergy;
        }
    }

}
