/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn
 */
enum BuildingType {

    Derelict, Rectangular, SymRobotic, ASymRobotic, AngleRobotic, ReinforcedAngleRobotic
}

public class Building {

    public static final byte TYPE_OPEN = 0;
    public static final byte TYPE_WALL = 1;
    public static final byte TYPE_DOOR_NS = 2;
    public static final byte TYPE_DOOR_EW = 3;
    public static final byte TYPE_WINDOW_NS = 4;
    public static final byte TYPE_WINDOW_EW = 5;
    public static final byte TYPE_FENCE = 6;
    public static final byte TYPE_STAIRS_UP = 7;
    public static final byte TYPE_STAIRS_DOWN = 8;
    //
    public static final byte TYPE_FURNITURE = 9;
    public static final byte TYPE_ITEM = 10;
    public static final byte TYPE_CONTAINER = 11;
    public static final byte TYPE_TOOL = 12;
    public static final byte TYPE_MELEE_WEAPON = 13;
    public static final byte TYPE_RANGED_WEAPON = 14;
    public static final byte TYPE_AMMO = 15;
    public static final byte TYPE_VEHICLE = 20;
    public static final byte TYPE_PERSON = 30;
    public static final byte TYPE_ANIMAL = 40;
    public static final byte TYPE_MONSTER = 50;
    public static final byte TYPE_ALIEN = 60;
    BuildingType type;
    byte breadth;
    byte width;
    byte depth;
    byte height;
    byte map[][][];

    Building(byte w, byte b, byte d, byte h) {
        this.width = w;
        this.breadth = b;
        this.depth = d;
        this.height = h;
        map = new byte[w][b][Math.abs(d) + h];
    }

    void BuildWall(byte x, byte y, byte w, byte b, int h) {
        h += Math.abs(depth);
        int yb = y + b - 1;
        for (int wx = x; wx < w && wx < width; wx++) {
            if (map[wx][y][h] == TYPE_OPEN) {
                map[wx][y][h] = TYPE_WALL;
            }
            if (map[wx][yb][h] == TYPE_OPEN) {
                map[wx][yb][h] = TYPE_WALL;
            }
        }
        int xw = x + w - 1;
        for (int wy = y; wy < b && wy < breadth; wy++) {
            if (map[x][wy][h] == TYPE_OPEN) {
                map[x][wy][h] = TYPE_WALL;
            }
            if (map[xw][wy][h] == TYPE_OPEN) {
                map[xw][wy][h] = TYPE_WALL;
            }

        }
    }

    void RandomChance(int x, int y, int h, byte wall, byte door, byte window, boolean n) {
        if (Random.Next(wall) == 0) {
            if (Random.Next(door) == 0) {
                if (n) {
                    map[x][y][h] = TYPE_DOOR_NS;
                } else {
                    map[x][y][h] = TYPE_DOOR_EW;
                }

            } else {
                if (Random.Next(window) == 0) {

                    if (n) {
                        map[x][y][h] = TYPE_WINDOW_NS;
                    } else {
                        map[x][y][h] = TYPE_WINDOW_EW;
                    }
                } else {

                    map[x][y][h] = TYPE_WALL;
                }
            }
        }
    }

    void BuildWall(byte x, byte y, byte w, byte b, byte h, byte wall, byte door, byte window) {
        h += Math.abs(depth);
        int yb = y + b - 1;
        for (int wx = x; wx < w && wx < width; wx++) {
            if (map[wx][y][h] == TYPE_OPEN) {
                RandomChance(wx, y, h, wall, door, window, true);
            }
            if (map[wx][yb][h] == TYPE_OPEN) {
                RandomChance(wx, yb, h, wall, door, window, true);
            }
        }
        int xw = x + w - 1;
        for (int wy = y; wy < b && wy < breadth; wy++) {
            if (map[x][wy][h] == TYPE_OPEN) {
                RandomChance(x, wy, h, wall, door, window, false);
            }
            if (map[xw][wy][h] == TYPE_OPEN) {
                RandomChance(xw, wy, h, wall, door, window, false);
            }

        }
    }
}

class Dome extends ServiceBuilding {

    float radius;
    java.util.Set<HumanSettlement> contains = new java.util.HashSet<HumanSettlement>();

    public Dome(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
        this.shade = new Pigment(64, 0, 0, 255);
        this.color = shade.Colour();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        //if(this.verts.size()>0){

        int w = (int) (LandMap.blockSizeInPixels * scale);
        int x = (int) (atx * w) + locx;
        int y = (int) (aty * w) + locy;
        float r = (radius * w);
        float r2 = (radius * w) * 0.5f;
        /*Location lv=this.verts.get(0);
         Location end=lv;
            
         for(int v=1;v<this.verts.size();v++){
         Location vert=this.verts.get(v);
         g.drawLine(x+(int)(lv.x*w), y+(int)(lv.y*w), x+(int)(vert.x*w), y+(int)(vert.y*w));
         lv=vert;
         }
         g.drawLine(x+(int)(lv.x*w), y+(int)(lv.y*w), x+(int)(end.x*w), y+(int)(end.y*w));
         */
        g.setColor(color);
        g.fillOval((int) (x - r2), (int) (y - r2), (int) (r), (int) (r));
        g.setColor(governed.color);
        g.drawOval((int) (x - r2), (int) (y - r2), (int) (r), (int) (r));
        //}

    }

    @Override
    void SetShape() {
        float r = this.radius * 2;
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PID10) {
                this.verts.add(new Local((float) (r * Math.cos(a)), (float) (r * Math.sin(a))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Farm extends ServiceBuilding {

    java.util.Set<Food> crops = new java.util.HashSet<Food>();
    java.util.Set<Food> animals = new java.util.HashSet<Food>();

    public Farm(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        this.shade = new Pigment(64, 0, 255, 0);
        this.color = shade.Colour();
        SetShape();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {

        if (this.verts.size() > 0) {

            int w = (int) (LandMap.blockSizeInPixels * scale);
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;
            float r = (radius * w);
            float r2 = (radius * w) * 2;
            for (int v = 0; v < this.verts.size(); v++) {
                Local lv = this.verts.get(v);
                g.setColor(color);
                g.fillRect(x + (int) (lv.x * w - r), y + (int) (lv.y * w - r), (int) (r2), (int) (r2));
                g.setColor(governed.color);
                g.drawRect(x + (int) (lv.x * w - r), y + (int) (lv.y * w - r), (int) (r2), (int) (r2));

            }
        }
    }

    @Override
    void SetShape() {
        float r = this.radius / 2;
        if (this.verts.size() == 0) {

            for (float a = 0; a < PlanetMap.PI2 - PlanetMap.PID3; a += PlanetMap.PID3) {
                this.verts.add(new Local((float) (r * Math.cos(a)), (float) (r * Math.sin(a))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class City extends ServiceBuilding {

    java.util.Set<Society> society = new java.util.HashSet<Society>();
    java.util.Set<People> civilization = new java.util.HashSet<People>();

    public City(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
        this.shade = new Pigment(64, 128, 128, 128);
        this.color = shade.Colour();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        if (this.verts.size() > 0) {

            int w = (int) (LandMap.blockSizeInPixels * scale);
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;
            float r = (radius * w);
            float r2 = (radius * w) / 2;

            for (int v = 0; v < this.verts.size(); v++) {
                Local lv = this.verts.get(v);
                g.setColor(color);
                g.fillOval(x + (int) (lv.x * w - r2), y + (int) (lv.y * w - r2), (int) (r), (int) (r));
                g.setColor(governed.color);
                g.drawOval(x + (int) (lv.x * w - r2), y + (int) (lv.y * w - r2), (int) (r), (int) (r));
            }
        }
    }

    @Override
    void SetShape() {
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PID3) {
                this.verts.add(new Local((float) (this.radius * Math.cos(a)), (float) (this.radius * Math.sin(a))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Citadel extends ServiceBuilding {

    java.util.Set<Food> store = new java.util.HashSet<Food>();
    java.util.Set<Tech> weapons = new java.util.HashSet<Tech>();

    public Citadel(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        if (this.verts.size() > 0) {
            g.setColor(governed.color);

            int w = (int) (LandMap.blockSizeInPixels * scale);
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;

            for (int v = 0; v < this.verts.size(); v += 3) {
                Local lv0 = this.verts.get(v);
                Local lv1 = this.verts.get(v + 1);
                Local lv2 = this.verts.get(v + 2);
                g.drawLine(x + (int) (lv0.x * w), y + (int) (lv0.y * w), x + (int) (lv1.x * w), y + (int) (lv1.y * w));
                g.drawLine(x + (int) (lv1.x * w), y + (int) (lv1.y * w), x + (int) (lv2.x * w), y + (int) (lv2.y * w));
                g.drawLine(x + (int) (lv2.x * w), y + (int) (lv2.y * w), x + (int) (lv0.x * w), y + (int) (lv0.y * w));
            }
        }
    }

    void SetShape() {
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PID3) {
                this.verts.add(new Local((float) (this.radius * Math.cos(a)), (float) (this.radius * Math.sin(a))));
                this.verts.add(new Local((float) (this.radius * 0.5 * Math.cos(a + PlanetMap.PID3 * 0.5)), (float) (this.radius * 0.5 * Math.sin(a + PlanetMap.PID3 * 0.5))));
                this.verts.add(new Local((float) (this.radius * Math.cos(a + PlanetMap.PID3)), (float) (this.radius * Math.sin(a + PlanetMap.PID3))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Port extends ServiceBuilding {

    java.util.Set<Tech> vehicles = new java.util.HashSet<Tech>();
    java.util.Set<Resource> transport = new java.util.HashSet<Resource>();

    public Port(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        if (this.verts.size() > 0) {

            g.setColor(governed.color);

            int w = (int) (LandMap.blockSizeInPixels * scale);
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;
            for (int v = 0; v < this.verts.size(); v += 3) {
                Local lv0 = this.verts.get(v);
                Local lv1 = this.verts.get(v + 1);
                Local lv2 = this.verts.get(v + 2);
                g.drawLine(x + (int) (lv0.x * w), y + (int) (lv0.y * w), x + (int) (lv1.x * w), y + (int) (lv1.y * w));
                g.drawLine(x + (int) (lv1.x * w), y + (int) (lv1.y * w), x + (int) (lv2.x * w), y + (int) (lv2.y * w));
                g.drawLine(x + (int) (lv2.x * w), y + (int) (lv2.y * w), x + (int) (lv0.x * w), y + (int) (lv0.y * w));
            }
        }
    }

    void SetShape() {
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PI90) {
                this.verts.add(new Local((float) (this.radius * Math.cos(a)), (float) (this.radius * Math.sin(a))));
                this.verts.add(new Local((float) (this.radius * 0.5 * Math.cos(a + PlanetMap.PI45)), (float) (this.radius * 0.5 * Math.sin(a + PlanetMap.PI45))));
                this.verts.add(new Local((float) (this.radius * Math.cos(a + PlanetMap.PI90)), (float) (this.radius * Math.sin(a + PlanetMap.PI90))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Factory extends ServiceBuilding {

    java.util.Set<Resource> produce = new java.util.HashSet<Resource>();
    java.util.Set<Resource> process = new java.util.HashSet<Resource>();

    public Factory(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
        this.shade = new Pigment(64, 255, 0, 0);
        this.color = shade.Colour();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        if (this.verts.size() > 0) {

            int w = (int) (LandMap.blockSizeInPixels * scale);
            float r = (radius * w);
            float r2 = (radius * w) / 2;
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;
            for (int v = 0; v < this.verts.size(); v += 2) {
                Local lv0 = this.verts.get(v);
                Local lv1 = this.verts.get(v + 1);
                g.setColor(color);

                g.fillOval(x + (int) (lv0.x * w - r2), y + (int) (lv0.y * w - r2), (int) (r), (int) (r));
                g.fillOval(x + (int) (lv1.x * w - r2), y + (int) (lv1.y * w - r2), (int) (r), (int) (r));
                g.setColor(governed.color);

                g.drawOval(x + (int) (lv0.x * w - r2), y + (int) (lv0.y * w - r2), (int) (r), (int) (r));
                g.drawOval(x + (int) (lv1.x * w - r2), y + (int) (lv1.y * w - r2), (int) (r), (int) (r));
            }
        }
    }

    void SetShape() {
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PI90) {
                this.verts.add(new Local((float) (this.radius * Math.cos(a)), (float) (this.radius * Math.sin(a))));
                this.verts.add(new Local((float) (this.radius * 0.5 * Math.cos(a)), (float) (this.radius * 0.5 * Math.sin(a))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Mine extends ServiceBuilding {

    java.util.Set<Resource> extract = new java.util.HashSet<Resource>();

    public Mine(Government govern, float r, int x, int y) {
        this.governed = govern;
        this.atx = x;
        this.aty = y;
        this.radius = r;
        SetShape();
        this.shade = new Pigment(64, 0, 0, 0);
        this.color = shade.Colour();
    }

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        if (this.verts.size() > 0) {

            int w = (int) (LandMap.blockSizeInPixels * scale);
            int x = (int) (atx * w) + locx;
            int y = (int) (aty * w) + locy;
            float r = (radius * w);
            float r2 = (radius * w) / 2;
            for (int v = 0; v < this.verts.size(); v += 2) {
                Local lv0 = this.verts.get(v);
                Local lv1 = this.verts.get(v + 1);
                g.setColor(color);
                g.fillRect(x + (int) (lv0.x * w - r2), y + (int) (lv0.y * w - r2), (int) (r), (int) (r));
                g.fillRect(x + (int) (lv1.x * w - r2), y + (int) (lv1.y * w - r2), (int) (r), (int) (r));
                g.setColor(governed.color);
                g.drawRect(x + (int) (lv0.x * w - r2), y + (int) (lv0.y * w - r2), (int) (r), (int) (r));
                g.drawRect(x + (int) (lv1.x * w - r2), y + (int) (lv1.y * w - r2), (int) (r), (int) (r));
            }
        }
    }

    void SetShape() {
        if (this.verts.size() == 0) {
            for (float a = 0; a < PlanetMap.PI2; a += PlanetMap.PI90) {
                this.verts.add(new Local((float) (this.radius * Math.cos(a)), (float) (this.radius * Math.sin(a))));
                this.verts.add(new Local((float) (this.radius * 0.5 * Math.cos(a)), (float) (this.radius * 0.5 * Math.sin(a))));
            }
        }
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
