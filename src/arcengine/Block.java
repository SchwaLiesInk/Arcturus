package arcengine;

import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Gerwyn
 */
public abstract class Block extends MapElement {

    public static int SIZE = 32;
    public static int STATICMASS = 10000;
    static java.awt.image.BufferedImage images[][][] = new java.awt.image.BufferedImage[10][10][10];
    byte subType;

    Block(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    GameObjectType Type() {
        return GameObjectType.BLOCK;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {

        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        if (x >= -Map.blockSizeInPixels && y >= -Map.blockSizeInPixels && x < 640 && y < 360) {

            g.drawImage(images[type][subType][light], x, y, null);

        }
    }

    static void CreateImages() {
        CreateWalls();
        CreateDoors();
        CreateWindows();
    }

    static void CreateWalls() {
        EffectMap emap = new EffectMap(SIZE, SIZE);
        for (int st = 0; st < 10; st++) {
            Pigment base = new Pigment(255 - (25 * st), 255 + (25 * (st - 5)), (25 * st));
            Pigment high = new Pigment(base.Red(), base.Green(), base.Blue());
            high.Average(new Pigment(255, 255, 255));
            Pigment d = new Pigment(255, 0, 0, 0);
            Pigment low = new Pigment(base.Red(), base.Green(), base.Blue());
            low.Average(d);
            Pigment pattern[][] = new Pigment[8][8];
            for (int x = 0; x < emap.width; x += emap.width >> 3) {
                int xx = x >> 3;
                for (int y = 0; y < emap.height; y += emap.height >> 3) {
                    int yy = y >> 3;
                    pattern[xx][yy] = new Pigment(Random.Next(1 + base.Red()), Random.Next(1 + base.Green()), Random.Next(1 + base.Blue()));
                }
            }
            for (int l = 0; l < 10; l++) {

                //d.Average(c);
                base.Hint(d);
                high.Hint(d);
                low.Hint(d);
                emap.FillRectangle(0, 0, emap.width, emap.height, base, false, false);
                for (int x = 0; x < emap.width; x += emap.width >> 3) {
                    int xx = x >> 3;

                    for (int y = 0; y < emap.height; y += emap.height >> 3) {
                        int yy = y >> 3;
                        emap.FillRectangle(x, y, x + (emap.width >> 3), y + (emap.height >> 3), pattern[xx][yy], false, false, PixelEffect.Average);

                        pattern[xx][yy].Hint(d);
                    }
                }
                emap.DrawRectangle(0, 0, emap.width, emap.height, high, false, false);
                emap.DrawRectangle(1, 1, emap.width, emap.height, low, false, false);
                emap.DrawRectangle(1, 1, emap.width >> 1, emap.height >> 1, low, false, false, PixelEffect.Average);
                emap.DrawRectangle((emap.width >> 2), (emap.height >> 2), (emap.width >> 2) + (emap.width >> 1), (emap.height >> 2) + (emap.height >> 1), low, false, false, PixelEffect.Average);
                emap.DrawRectangle(emap.width >> 1, emap.height >> 1, emap.width - 1, emap.height - 1, low, false, false, PixelEffect.Average);

                images[Building.TYPE_WALL][st][l] = emap.ToBufferedImage();
            }

        }
    }

    static void CreateDoors() {
        EffectMap emap = new EffectMap(SIZE, SIZE);

        for (int st = 0; st < 10; st++) {
            Pigment base = new Pigment(255 - (25 * st), 255 + (25 * (st - 5)), (25 * st));
            Pigment high = new Pigment(base.Red(), base.Green(), base.Blue());
            high.Average(new Pigment(255, 255, 255));
            Pigment d = new Pigment(255, 64, 0, 0);
            Pigment low = new Pigment(base.Red(), base.Green(), base.Blue());
            low.Average(d);

            Pigment pattern[] = new Pigment[8];
            for (int y = 0; y < emap.height; y += emap.height >> 3) {
                int yy = y >> 3;
                pattern[yy] = new Pigment(Random.Next(1 + base.Red()), Random.Next(1 + base.Green()), Random.Next(1 + base.Blue()));
            }
            for (int l = 0; l < 10; l++) {

                //d.Average(c);
                base.Hint(d);
                high.Hint(d);
                low.Hint(d);
                emap.FillRectangle(0, (emap.height >> 2), emap.width, (emap.height >> 2) + (emap.height >> 1), base, false, false);

                for (int y = (emap.height >> 2); y < (emap.height >> 2) + (emap.height >> 1); y += emap.height >> 3) {
                    int yy = y >> 3;
                    emap.FillRectangle(0, y, emap.width, y + (emap.height >> 3), pattern[yy], false, false, PixelEffect.Average);

                    pattern[yy].Hint(d);
                }

                emap.DrawRectangle(0, (emap.height >> 2), emap.width, (emap.height >> 2) + (emap.height >> 1), high, false, false);
                emap.DrawRectangle(1, (emap.height >> 2) + 1, emap.width - 1, (emap.height >> 2) + (emap.height >> 1) - 1, low, false, false);
                emap.FillRectangle(2, (emap.height >> 2) + (emap.height >> 3), emap.width - 2, (emap.height >> 1) + (emap.height >> 3), low, false, false, PixelEffect.Average);
                images[Building.TYPE_DOOR_NS][st][l] = emap.ToBufferedImage();
            }

        }
        for (int st = 0; st < 10; st++) {
            Pigment base = new Pigment(255 - (25 * st), 255 + (25 * (st - 5)), (25 * st));
            Pigment high = new Pigment(base.Red(), base.Green(), base.Blue());
            high.Average(new Pigment(255, 255, 255));
            Pigment d = new Pigment(255, 64, 0, 0);
            Pigment low = new Pigment(base.Red(), base.Green(), base.Blue());
            low.Average(d);

            Pigment pattern[] = new Pigment[8];
            for (int x = 0; x < emap.width; x += emap.width >> 3) {
                int xx = x >> 3;
                pattern[xx] = new Pigment(Random.Next(1 + base.Red()), Random.Next(1 + base.Green()), Random.Next(1 + base.Blue()));
            }
            for (int l = 0; l < 10; l++) {

                //d.Average(c);
                base.Hint(d);
                high.Hint(d);
                low.Hint(d);
                emap.FillRectangle((emap.width >> 2), 0, (emap.width >> 2) + (emap.width >> 1), emap.height, base, false, false);
                for (int x = (emap.width >> 2); x < (emap.width >> 2) + (emap.width >> 1); x += emap.width >> 3) {
                    int xx = x >> 3;
                    emap.FillRectangle(x, 0, x + (emap.width >> 3), emap.height, pattern[xx], false, false, PixelEffect.Average);

                    pattern[xx].Hint(d);
                }

                emap.DrawRectangle((emap.width >> 2), 0, (emap.width >> 2) + (emap.width >> 1), emap.height, high, false, false);
                emap.DrawRectangle((emap.width >> 2) + 1, 1, (emap.width >> 2) + (emap.width >> 1) - 1, emap.height - 1, low, false, false);
                emap.FillRectangle((emap.width >> 2) + (emap.width >> 3), 2, (emap.width >> 1) + (emap.width >> 3), emap.height - 2, low, false, false, PixelEffect.Average);
                images[Building.TYPE_DOOR_EW][st][l] = emap.ToBufferedImage();
            }

        }
    }

    static void CreateWindows() {
        EffectMap emap = new EffectMap(SIZE, SIZE);
        for (int st = 0; st < 10; st++) {
            Pigment base = new Pigment(255 - (25 * st), 255 + (25 * (st - 5)), (25 * st));
            Pigment d = new Pigment(255, 0, 0, 0);

            Pigment high2 = new Pigment(base.Red(), base.Green(), base.Blue());
            high2.Average(new Pigment(128, 128, 255));
            Pigment d2 = new Pigment(255, 0, 0, 64);
            Pigment low2 = new Pigment(base.Red(), base.Green(), base.Blue());

            Pigment pattern[] = new Pigment[8];
            for (int y = 0; y < emap.height; y += emap.height >> 3) {
                int yy = y >> 3;
                pattern[yy] = new Pigment(Random.Next(1 + base.Red()), Random.Next(1 + base.Green()), Random.Next(1 + base.Blue()));
            }
            for (int l = 0; l < 10; l++) {

                //d.Average(c);
                base.Hint(d);
                high2.Hint(d2);
                low2.Hint(d2);
                emap.FillRectangle(0, (emap.height >> 2), emap.width, (emap.height >> 2) + (emap.height >> 1), low2, false, false, PixelEffect.Average);
                for (int y = (emap.height >> 2); y < (emap.height >> 2) + (emap.height >> 1); y += emap.height >> 3) {
                    int yy = y >> 3;
                    emap.FillRectangle(0, y, emap.width, y + (emap.height >> 3), pattern[yy], false, false, PixelEffect.Average);

                    pattern[yy].Hint(d);
                }
                emap.DrawRectangle(0, (emap.height >> 2), emap.width, (emap.height >> 2) + (emap.height >> 1), high2, false, false, PixelEffect.Average);
                emap.DrawRectangle(1, (emap.height >> 2) + 1, emap.width - 1, (emap.height >> 2) + (emap.height >> 1) - 1, low2, false, false, PixelEffect.Average);
                images[Building.TYPE_WINDOW_NS][st][l] = emap.ToBufferedImage();
            }

        }
        for (int st = 0; st < 10; st++) {
            Pigment base = new Pigment(255 - (25 * st), 255 + (25 * (st - 5)), (25 * st));
            Pigment d = new Pigment(255, 0, 0, 0);
            //
            Pigment high2 = new Pigment(base.Red(), base.Green(), base.Blue());
            high2.Average(new Pigment(128, 128, 255));
            Pigment d2 = new Pigment(255, 0, 0, 64);
            Pigment low2 = new Pigment(base.Red(), base.Green(), base.Blue());

            Pigment pattern[] = new Pigment[8];
            for (int x = 0; x < emap.width; x += emap.width >> 3) {
                int xx = x >> 3;
                pattern[xx] = new Pigment(Random.Next(1 + base.Red()), Random.Next(1 + base.Green()), Random.Next(1 + base.Blue()));
            }

            for (int l = 0; l < 10; l++) {

                //d.Average(c);
                base.Hint(d);
                high2.Hint(d2);
                low2.Hint(d2);
                emap.FillRectangle((emap.width >> 2), 0, (emap.width >> 2) + (emap.width >> 1), emap.height, low2, false, false, PixelEffect.Average);
                for (int x = (emap.width >> 2); x < (emap.width >> 2) + (emap.width >> 1); x += emap.width >> 3) {
                    int xx = x >> 3;
                    emap.FillRectangle(x, 0, x + (emap.width >> 3), emap.height, pattern[xx], false, false, PixelEffect.Average);

                    pattern[xx].Hint(d);
                }
                emap.DrawRectangle((emap.width >> 2), 0, (emap.width >> 2) + (emap.width >> 1), emap.height, high2, false, false, PixelEffect.Average);
                emap.DrawRectangle((emap.width >> 2) + 1, 1, (emap.width >> 2) + (emap.width >> 1) - 1, emap.height - 1, low2, false, false, PixelEffect.Average);
                images[Building.TYPE_WINDOW_EW][st][l] = emap.ToBufferedImage();
            }

        }
    }
}

class Wall extends Block {

    Wall(int x, int y, byte subType) {
        super(x, y);
        type = Building.TYPE_WALL;
        this.subType = subType;
        this.mass = new Mass(new Point(16, 16), STATICMASS, 24);
        this.force = (short) (subType * 800);
        this.power = (short) (force << 1);
        this.energy = (short) (power + force);
        this.armourPoints = (short) (subType * 10);
        this.bodyPoints = (short) (subType * subType * 400);
        this.detector = new CollisionDetector[1];
        this.detector[0] = new CollisionDetector(this.mass.Value(), 0, 16, 16, 24);
    }

    @Override
    boolean RunSimulation() {
        return false;
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        //ELECTRO MAG

        if (with != null) {
            float m = with.mass.Value();
            float s = with.speed.Value();
            float ax = (float) (this.x - with.x);
            float ay = (float) (this.y - with.y);
            //float a=(float)Math.sqrt(ax*ax+ay*ay);
            if (this != with) {
                //n++;
                //if(ax>1e-3 || ax<-1e-3)with.atx -= (this.detector[0].radius/ax*a);else with.atx -=ax;
                //if(ay>1e-3 || ay<-1e-3)with.aty -= (this.detector[0].radius/ay*a);else with.aty -=ay;

                Class c = with.getClass();
                if (c.equals(Car.class) || c.equals(Van.class) || c.equals(Truck.class) || c.equals(Bus.class)) {
                    with.x -= (ax * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    with.y -= (ay * with.detector[0].radius.Value() * 0.5) / Map.blockSizeInPixels;
                    Map.square.MoveElementTo(with, (float) with.x, (float) with.y);

                } else {
                    with.x -= (ax * with.detector[0].radius.Value()) / Map.blockCenterInPixels;
                    with.y -= (ay * with.detector[0].radius.Value()) / Map.blockCenterInPixels;
                    Map.square.MoveElementTo(with, (float) with.x, (float) with.y);
                    //System.out.println(c);
                }

            }
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

        }

        return true;

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
    void Collision(DetectorResult result) {
        this.effect = result;
    }
}

class Door extends Wall {

    Door(int x, int y, byte subType, boolean n) {
        super(x, y, subType);

        this.detector = new CollisionDetector[3];
        if (n) {
            type = Building.TYPE_DOOR_NS;

            this.detector[0] = new CollisionDetector(mass.Value(), 0, 8, 16, 20);
            this.detector[1] = new CollisionDetector(mass.Value(), 1, 16, 16, 20);
            this.detector[2] = new CollisionDetector(mass.Value(), 2, 24, 16, 20);
        } else {
            type = Building.TYPE_DOOR_EW;

            this.detector[0] = new CollisionDetector(mass.Value(), 0, 16, 8, 20);
            this.detector[1] = new CollisionDetector(mass.Value(), 1, 16, 16, 20);
            this.detector[2] = new CollisionDetector(mass.Value(), 2, 16, 24, 20);
        }

    }
    /*
     * @Override boolean RunSimulation() { return false; }
     *
     * @Override DetectorResult Detect(MapElement me) { return null; }
     *
     * @Override void Collision(DetectorResult result) { this.effect=result; }
     *
     * @Override boolean CollisionSimulation(MapElement with) {return false;}
     * @Override void paint(Graphics g, int osx, int osy, int light) {
     }
     */
}

class Window extends Wall {

    Window(int x, int y, byte subType, boolean n) {
        super(x, y, subType);

        if (n) {
            type = Building.TYPE_WINDOW_NS;
        } else {
            type = Building.TYPE_WINDOW_EW;
        }
    }

    /*
     * @Override boolean RunSimulation() { return false; }
     *
     * @Override DetectorResult Detect(MapElement me) { return null; }
     *
     * @Override void Collision(DetectorResult result) {
     *
     * this.effect=result; }
     *
     * boolean CollisionSimulation(MapElement with) {return false;} @Override
     * void paint(Graphics g, int osx, int osy, int light) {
     *
     * }
     */
}
