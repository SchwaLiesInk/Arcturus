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
class MapList {

    java.util.ArrayList<MapSection> sectionAbove = new java.util.ArrayList<>();
    java.util.ArrayList<MapSection> sectionBelow = new java.util.ArrayList<>();

    MapList() {
    }

    MapList(MapSection first) {
        sectionAbove.clear();
        sectionAbove.add(first);
        sectionBelow.clear();
    }

    boolean AboveContains(MapElement me) {

        for (int i = 0; i < sectionAbove.size(); i++) {
            int sz = sectionAbove.get(i).contence.size();
            for (int j = 0; j < sz; j++) {
                MapElement m = sectionAbove.get(i).contence.get(j);
                if (m == me) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean AboveContains(MapElement me, int i) {

        int sz = sectionAbove.get(i).contence.size();
        for (int j = 0; j < sz; j++) {
            MapElement m = sectionAbove.get(i).contence.get(j);
            if (m == me) {
                return true;
            }
        }
        return false;
    }

    boolean BelowContains(MapElement me) {

        for (int i = 0; i < sectionBelow.size(); i++) {
            int sz = sectionBelow.get(i).contence.size();
            for (int j = 0; j < sz; j++) {
                MapElement m = sectionBelow.get(i).contence.get(j);
                if (m == me) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean RemoveBelow(MapElement me) {

        for (int i = 0; i < sectionBelow.size(); i++) {
            if (sectionBelow.get(i).contence.contains(me)) {
                sectionBelow.get(i).contence.remove(me);
                return true;
            }
        }
        return false;

    }

    boolean RemoveAbove(MapElement me) {

        for (int i = 0; i < sectionAbove.size(); i++) {
            if (sectionAbove.get(i).contence.contains(me)) {
                sectionAbove.get(i).contence.remove(me);
                return true;
            }
        }
        return false;

    }

    boolean RemoveAbove(MapElement me, int i) {

        if (sectionAbove.get(i).contence.contains(me)) {
            sectionAbove.get(i).contence.remove(me);
            return true;
        }
        return false;

    }

    boolean AddAbove(MapElement me, int i) {
        if (i < this.sectionAbove.size()) {
            this.sectionAbove.get(i).contence.add(me);
            return true;
        }
        return false;
    }

    boolean AddBelow(MapElement me, int i) {
        if (i < 0) {
            int b = Math.abs(i) - 1;
            if (b < this.sectionBelow.size()) {
                this.sectionBelow.get(b).contence.add(me);
                return true;
            }
        }
        return false;
    }

    MapSection Get(int i) {
        if (i >= 0) {
            if (i < sectionAbove.size()) {
                return sectionAbove.get(i);
            } else {
                return null;
            }
        } else {
            int b = Math.abs(i) - 1;
            if (b < sectionBelow.size()) {
                return sectionBelow.get(b);
            } else {
                return null;
            }

        }
    }

    boolean Add(MapElement me, int i) {
        if (i >= 0) {
            if (i < sectionAbove.size()) {
                sectionAbove.get(i).contence.add(me);
                return true;
            } else {
                return false;
            }
        } else {
            int b = Math.abs(i) - 1;
            if (b < sectionBelow.size()) {
                sectionBelow.get(b).contence.add(me);
                return true;
            } else {
                return false;
            }

        }
    }

    boolean Remove(MapElement me, int i) {
        if (i >= 0) {
            if (i < sectionAbove.size()) {
                if (sectionAbove.get(i).contence.contains(me)) {
                    sectionAbove.get(i).contence.remove(me);
                }
                return true;
            }
            return false;
        } else {
            int b = Math.abs(i) - 1;
            if (b < sectionBelow.size()) {
                if (sectionBelow.get(i).contence.contains(me)) {
                    sectionBelow.get(b).contence.remove(me);
                    return true;
                }
            }
            return false;

        }
    }

    boolean Contains(MapElement me, int i) {
        if (i >= 0) {
            if (i < sectionAbove.size()) {
                if (sectionAbove.get(i).contence.contains(me)) {
                    return true;
                }
            }
            return false;
        } else {
            int b = Math.abs(i) - 1;
            if (b < sectionBelow.size()) {
                if (sectionBelow.get(i).contence.contains(me)) {
                    return true;
                }
            }
            return false;

        }
    }
}

class MapSection {

    protected java.util.ArrayList<MapElement> contence = new java.util.ArrayList<>();

    boolean CollisionTest(MapElement me) {
        /*DetectorResult tested = null;
         DetectorResult result = me.effect;
         DetectorResult detected = me.effect;
        
            
         tested = me.Detect(this.contence);

         if (tested != null) {
         if (detected == null) {
         detected = tested;
         result = tested;
         } else {
         while (detected.next != null) {
         detected = detected.next;
         }
         detected.next = tested;
         }

         }

         if (result != null) {
         me.Collision(result);
         return true;
         }*/
        return false;
    }
}

/**
 *
 * @author Gerwyn
 */
enum GameObjectType {

    GROUND, TALL, BLOCK, PARTICLE, ANIMAL, PLAYER, MONSTER, ROBOT, GAME_OBJECT, VEHICLE
}

abstract class MapElement extends PhysicalPoint {

    static double Angle = Math.PI + Math.PI * 0.5;
    static double PI2 = Math.PI * 2;
    static double PID2 = Math.PI / 2;
    static double sqrt2 = Math.sqrt(2);
    protected byte type;

    protected int armourPoints;
    protected int force;
    protected int power;
    protected int energy;
    protected int bodyPoints;
    protected int animSequance;
    protected boolean reverse;
    protected DetectorResult effect;
    protected CollisionDetector detector[];

    MapElement() {
        type = 0;
        force = 1;
        power = 1;
        energy = 1;
        local = new Local(0, 0);
        v = new Velocity(0, 0);
        angle = new SphericalCoord(0);
        speed = new Speed(0);

    }

    abstract GameObjectType Type();

    Local Local() {
        return local;
    }

    int Body() {
        return force + power + energy;
    }

    void CheckAngle() {
        while (true) {
            if (this.angle.phi <= 0) {
                this.angle.phi += PI2;
                continue;
            } else if (this.angle.phi >= PI2) {
                this.angle.phi -= PI2;
                continue;
            }
            return;
        }
    }

    void RotateBody() {
        /*if (this.detector != null) {
         for (int d = 0; d < this.detector.length; d++) {
         this.detector[d].Rotate(Angle + angle.phi);
         }
         }*/
    }

    abstract boolean RunSimulation();

    abstract DetectorResult Detect(java.util.ArrayList<MapElement> me);

    abstract void Collision(DetectorResult result);

    abstract boolean CollisionSimulation(MapElement with);

    abstract void paint(Graphics2D g, int osx, int osy, int light);
}

abstract class Geograph {

    final static int blockSizeInPixels = 128;
    final static int blockCenterInPixels = 64;
    static double blockScale = 1.0 / blockSizeInPixels;
    MapList element[][];
    int width;
    int height;
    int cx;
    int cy;
    int size;
    int pixwidth;
    int pixheight;

    public Geograph(int w, int h) {

        width = w;
        height = h;
        cx = w >> 1;
        cy = h >> 1;
        size = w * h;
        pixwidth = w * blockSizeInPixels;
        pixheight = h * blockSizeInPixels;
        element = new MapList[w][h];
    }
}

public class Map extends Geograph {

    public static volatile Light lights[];
    public static volatile Map square;
    byte imageGen[][];
    byte buildGen[][];
    byte currentLevel;

    public Map(int w, int h) {
        super(w, h);
        currentLevel = 0;
        imageGen = new byte[w][h];
        buildGen = new byte[w * 4][h * 4];

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                element[x][y] = new MapList();
                //element[x][y].contains=new Ground(x*Map.blockSizeInPixels,y*Map.blockSizeInPixels);
            }
        }
    }

    void SetUpRandomMap() {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                element[x][y] = new MapList(new MapSection());
                element[x][y].AddAbove(new Ground(x * Map.blockSizeInPixels, y * Map.blockSizeInPixels), 0);
            }
        }
    }

    void PrepareGround() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //element[x][y].contains=new Ground(x*Map.blockSizeInPixels,y*Map.blockSizeInPixels,Ground.TYPE_GRASS);
                imageGen[x][y] = Ground.TYPE_GRASS;
            }
        }
    }

    byte GetRoadSubType(int x, int y, boolean round[][]) {
        byte sub = 4;
        round = new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}};
        if (x > 0) {
            if (y > 0) {
                round[0][0] = imageGen[x - 1][y - 1] == Ground.TYPE_ROAD;
            }
            round[0][1] = imageGen[x - 1][y] == Ground.TYPE_ROAD;
            if (y < height - 1) {
                round[0][2] = imageGen[x - 1][y + 1] == Ground.TYPE_ROAD;
            }
        }
        if (y > 0) {
            round[1][0] = imageGen[x][y - 1] == Ground.TYPE_ROAD;

        }
        if (x < width - 1) {
            if (y > 0) {
                round[2][0] = imageGen[x + 1][y - 1] == Ground.TYPE_ROAD;
            }

            round[2][1] = imageGen[x + 1][y] == Ground.TYPE_ROAD;
            if (y < height - 1) {
                round[2][2] = imageGen[x + 1][y + 1] == Ground.TYPE_ROAD;
            }
        }
        if (y < height - 1) {
            round[1][2] = imageGen[x][y + 1] == Ground.TYPE_ROAD;

        }
        if ((!round[0][0]) && round[1][0] && (!round[2][0]) && (!round[0][1]) && (!round[2][1]) && (!round[0][2]) && round[1][2] && (!round[2][2])) {
            sub = 1;//+
            return sub;
        } else if (round[0][0] && round[1][0] && round[2][0] && round[0][1] && round[2][1] && round[0][2] && round[1][2] && round[2][2]) {
            sub = 9;//[ ]
            return sub;

        } else if (round[1][0] && round[0][1] && round[2][1] && round[1][2] && !(round[0][0] && round[2][0] && round[0][2] && round[2][2])) {
            sub = 3;//+
            return sub;

        } else if (round[0][0] && round[2][0] && round[0][2] && round[2][2] && !(round[1][0] && round[0][1] && round[2][1] && round[1][2])) {
            sub = 8;//+
            return sub;

        } else if (round[1][0] && round[0][1] && round[1][2] && !(round[2][1] || round[0][0] || round[2][0] || round[0][2] || round[2][2])) {
            //-|
            sub = 4;
            return sub;
        } else if (round[1][0] && round[2][1] && round[1][2] && !(round[0][1] || round[0][0] || round[2][0] || round[0][2] || round[2][2])) {
            //|-

            sub = 4;
            return sub;
        } else if (round[0][1] && round[1][0] && round[2][1] && !(round[1][2] || round[0][0] || round[2][0] || round[0][2] || round[2][2])) {
            //-+-

            sub = 4;
            return sub;
        } else if (round[0][1] && round[1][2] && round[2][1] && !(round[1][0] || round[0][0] || round[2][0] || round[0][2] || round[2][2])) {
            //-+-

            sub = 4;
            return sub;
        } else if (round[0][1] && round[2][1]) {
            //---

            sub = 0;
            return sub;
        } else if (round[1][0] && round[1][2]) {
            //|||
            sub = 1;
            return sub;

        } else if (round[0][1] || round[2][1]) {
            //---

            sub = 5;
            return sub;
        } else if (round[1][0] || round[1][2]) {
            //|||
            sub = 6;
            return sub;

        } else if (round[0][0] || round[2][0] || round[0][2] || round[2][2]) {
            sub = 2;//[]
            return sub;

        } else if (round[1][0] || round[2][1] || round[1][2] || round[0][1]) {
            sub = 7;//[]
            return sub;

        }
        return sub;
    }

    void SetUpTownMap() {
        PrepareGround();
        Seed seed = new Seed(1, 1, 1, 0, 0, 20, 1, 1, 1);
        seed.SetMolecules();
        seed.SetEffectSequance(PixelEffect.Average, 0);
        seed.SetColor(new Pigment(0, 255, 0), 0);
        seed.SetColorSequance(new Pigment(0, 155, 0), new Pigment(0, 255, 0), new Pigment(0, 2, 0), 0);
        TurtleCommand[] commands = new TurtleCommand[360];
        for (int i = 0; i < 360; i++) {
            switch (Random.Next(6) + Random.Next(6)) {
                case 0: {
                    commands[i] = TurtleCommand.Backward;
                    break;
                }
                case 1: {
                    commands[i] = TurtleCommand.Backward;
                    break;
                }
                case 2: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 3: {
                    commands[i] = TurtleCommand.AntiClockwise;
                    break;
                }
                case 4: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 5: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 6: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 7: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 8: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 9: {
                    commands[i] = TurtleCommand.Forward;
                    break;
                }
                case 10: {
                    commands[i] = TurtleCommand.Clockwise;
                    break;
                }
                case 11: {
                    commands[i] = TurtleCommand.Reverse;
                    break;
                }
                case 12: {
                    commands[i] = TurtleCommand.Backward;
                    break;
                }
            }
        }

        Turtle road = new Turtle(seed, 512, 512, width, height, 1, 0, 90, 128, commands);
        road.Move();
        System.out.println(road.currentLine.helix.size());
        for (int i = 0; i < road.currentLine.helix.size(); i++) {
            NTidePoint at = road.currentLine.helix.get(i);
            //System.out.println(at.v[0] + ":" + at.v[1]);
            int x = at.v[0];
            int y = at.v[1];
            imageGen[x][y] = Ground.TYPE_ROAD;
            if (x > 0 && y > 0 && x < width - 1 && y < height - 1) {
                if (imageGen[x - 1][y - 1] != Ground.TYPE_ROAD) {
                    imageGen[x - 1][y - 1] = Ground.TYPE_PAVED;
                }
                if (imageGen[x + 1][y - 1] != Ground.TYPE_ROAD) {
                    imageGen[x + 1][y - 1] = Ground.TYPE_PAVED;
                }
                if (imageGen[x - 1][y] != Ground.TYPE_ROAD) {
                    imageGen[x - 1][y] = Ground.TYPE_PAVED;
                }
                if (imageGen[x + 1][y] != Ground.TYPE_ROAD) {
                    imageGen[x + 1][y] = Ground.TYPE_PAVED;
                }
                if (imageGen[x - 1][y + 1] != Ground.TYPE_ROAD) {
                    imageGen[x - 1][y + 1] = Ground.TYPE_PAVED;
                }
                if (imageGen[x + 1][y + 1] != Ground.TYPE_ROAD) {
                    imageGen[x + 1][y + 1] = Ground.TYPE_PAVED;
                }
                if (imageGen[x][y - 1] != Ground.TYPE_ROAD) {
                    imageGen[x][y - 1] = Ground.TYPE_PAVED;
                }
                if (imageGen[x][y + 1] != Ground.TYPE_ROAD) {
                    imageGen[x][y + 1] = Ground.TYPE_PAVED;
                }

            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (imageGen[x][y] == Ground.TYPE_ROAD) {
                    boolean round[][] = new boolean[3][3];
                    byte sub = GetRoadSubType(x, y, round);
                    element[x][y] = new MapList(new MapSection());
                    element[x][y].AddAbove(new Ground(x * Map.blockSizeInPixels, y * Map.blockSizeInPixels, imageGen[x][y], sub), 0);

                } else {
                    element[x][y] = new MapList(new MapSection());

                    element[x][y].AddAbove(new Ground(x * Map.blockSizeInPixels, y * Map.blockSizeInPixels, imageGen[x][y]), 0);
                }
            }
        }
    }

    void CollisionTest(MapElement me) {
        int xat = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
        int yat = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
        for (int xa = -1; xa < 2; xa++) {
            int ax = xat + xa;
            for (int ya = -1; ya < 2; ya++) {
                int ay = yat + ya;
                if (ax >= 0 && ax < square.width && ay >= 0 && ay < square.height) {
                    if (element[ax][ay].Get(currentLevel) != null) {
                        element[ax][ay].Get(currentLevel).CollisionTest(me);
                    }
                }
            }
        }
    }

    boolean PlaceElement(MapElement me) {
        int locx = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
        int locy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
        me.local.x = locx;
        me.local.y = locy;
        element[locx][locy].AddAbove(me, 0);
        return true;
    }

    boolean RemoveElement(MapElement me) {
        int locx = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
        int locy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
        System.out.println(me.local.x + " from " + me.local.y);
        me.local.x = locx;
        me.local.y = locy;
        System.out.println(locx + " At " + locy);

        element[me.local.x][me.local.y].Remove(me, currentLevel);
        return true;
    }

    boolean MoveElement(MapElement me, float pix, float piy) {
        int locx = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
        int locy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
        me.local.x = locx;
        me.local.y = locy;

        me.x += pix;
        me.y += piy;
        if (!(me.x < Map.blockCenterInPixels || me.x > pixwidth || me.y < 0 || me.y > pixheight)) {
            int tox = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
            int toy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
            //
            //

            //System.out.println(locx+" "+locy+" move "+tox+" "+toy);
            if (locx != tox || locy != toy) {

                //
                if (tox < 0) {
                    tox += width;
                    me.x = (tox * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                } else if (tox >= width) {
                    tox -= width;
                    me.x = (tox * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                }
                if (toy < 0) {
                    toy += height;
                    me.y = (toy * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                } else if (toy >= height) {
                    toy -= height;
                    me.y = (toy * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                }
                me.local.x = (int) tox;
                me.local.y = (int) toy;
                if (element[locx][locy].Contains(me, currentLevel)) {
                    if (element[locx][locy].Remove(me, currentLevel)) {
                        element[me.local.x][me.local.y].Add(me, currentLevel);
                        //System.out.println(locx+" "+locy+" move "+tox+" "+toy);
                        return true;
                    } else {
                        return false;
                    }
                } else {

                    element[me.local.x][me.local.y].Add(me, currentLevel);
                    return true;
                }
            }
        }
        return false;
    }

    boolean MoveElementTo(MapElement me, float pix, float piy) {
        int locx = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
        int locy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
        me.local.x = locx;
        me.local.y = locy;
        me.x = pix;
        me.y = piy;
        if (!(me.x < Map.blockCenterInPixels || me.x > pixwidth || me.y < 0 || me.y > pixheight)) {
            int tox = (int) ((me.x - Map.blockCenterInPixels) * blockScale);
            int toy = (int) ((me.y - Map.blockCenterInPixels) * blockScale);
            //

            //System.out.println(locx+" "+locy+" move to "+tox+" "+toy);
            //
            if (locx != tox || locy != toy) {

                //
                if (tox < 0) {
                    tox += width;
                    me.x = (tox * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                } else if (tox >= width) {
                    tox -= width;
                    me.x = (tox * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                }
                if (toy < 0) {
                    toy += height;
                    me.y = (toy * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                } else if (toy >= height) {
                    toy -= height;
                    me.y = (toy * Map.blockSizeInPixels) - Map.blockCenterInPixels;
                }
                me.local.x = tox;
                me.local.y = toy;
                if (element[locx][locy].Contains(me, currentLevel)) {
                    if (element[locx][locy].Remove(me, currentLevel)) {
                        element[me.local.x][me.local.y].Add(me, currentLevel);
                        return true;
                    } else {
                        return false;
                    }
                } else {

                    element[me.local.x][me.local.y].Add(me, currentLevel);
                    return true;
                }
            }
        }
        return false;
    }
}

class Ground extends MapElement {

    public static final byte TYPE_ICE = 0;
    public static final byte TYPE_OIL = 1;
    public static final byte TYPE_WET = 2;
    public static final byte TYPE_PAVED = 3;
    public static final byte TYPE_ROAD = 4;
    public static final byte TYPE_GRAVEL = 5;
    public static final byte TYPE_GRASS = 6;
    public static final byte TYPE_MUD = 7;
    public static final byte TYPE_BOG = 8;
    public static final byte TYPE_WATER = 9;
    public static EffectMap maps[][] = new EffectMap[10][10];
    public static java.awt.image.BufferedImage images[][][] = new java.awt.image.BufferedImage[10][10][10];
    Coefficient friction;
    byte subType;
    //TallMapElement above;

    Ground(int x, int y) {
        this.x = x;
        this.y = y;

        this.type = (byte) (Random.Next(5) + Random.Next(5));
        subType = (byte) Random.Next(10);
        this.friction = new Coefficient((float) (1 + type) / 11);
        //this.friction*=this.friction;
        //detector=new CollisionDetector[4][4];
    }

    Ground(int x, int y, byte type) {
        this.x = x;
        this.y = y;

        this.type = type;
        subType = (byte) Random.Next(10);
        this.friction = new Coefficient((float) (1 + type) / 11);
        //this.friction*=this.friction;
        //detector=new CollisionDetector[4][4];
    }

    Ground(int x, int y, byte type, byte subType) {
        this.x = x;
        this.y = y;

        this.type = type;
        this.subType = subType;
        this.friction = new Coefficient((float) (1 + type) / 11);
        //this.friction*=this.friction;
        //detector=new CollisionDetector[4][4];
    }

    GameObjectType Type() {
        return GameObjectType.GROUND;
    }

    boolean RunSimulation() {
        return false;
    }

    public static boolean LoadGroundImages(LoadFrame lf) {
        for (int t = 0; t < 10; t++) {
            if (lf != null) {
                lf.Add(2);
            }
            for (int st = 0; st < 10; st++) {
                images[t][st][0] = Game.LoadImage("Ground\\Ground" + t + "_" + st + ".png", "png");
                if (images[t][st][0] == null) {
                    return false;
                } else {
                    for (int i = 1; i < 10; i++) {
                        images[t][st][i] = new java.awt.image.BufferedImage(images[t][st][0].getWidth(), images[t][st][0].getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
                        for (int x = 0; x < images[t][st][0].getWidth(); x++) {

                            for (int y = 0; y < images[t][st][0].getHeight(); y++) {
                                int rgb = images[t][st][i - 1].getRGB(x, y);
                                int r = (rgb & 0x00FF0000) >> 16;
                                int g = (rgb & 0x0000FF00) >> 8;
                                int b = (rgb & 0x000000FF);
                                Pigment c = new Pigment(255, r, g, b);
                                Pigment d = new Pigment(255, 0, 0, 0);
                                //d.Average(c);
                                c.Hint(d);
                                images[t][st][i].setRGB(x, y, c.ToRGB());
                            }
                        }
                    }
                }
            }
        }
        return true;

    }

    public static boolean SaveGroundImages() {
        for (int t = 0; t < 10; t++) {
            for (int st = 0; st < 10; st++) {
                Game.SaveImage("Ground\\Ground" + t + "_" + st + ".png", "png", images[t][st][0]);
            }
        }
        return true;
    }

    public static void DrawGroundImages(boolean repeat) {

        //SkinData skin=new SkinData(8);
        //imap.FillRectangle(x+r,y+r,x+w-r,y+h-r,skin,glass,roof,metal,false,true,PixelEffect.Hint);//body
        for (int type = 0; type < 10; type++) {
            Pigment high = new Pigment(0, 255, 0);
            Pigment color = new Pigment(0, 128, 0);
            Pigment shade = new Pigment(0, 64, 0);
            switch (type) {
                case TYPE_ICE: {
                    color = (new Pigment(0, 255, 255));
                    break;
                }
                case TYPE_OIL: {
                    color = (new Pigment(0, 0, 0));
                    break;
                }
                case TYPE_WET: {
                    color = (new Pigment(0, 0, 128));
                    break;
                }
                case TYPE_PAVED: {
                    color = (new Pigment(128, 0, 0));
                    break;
                }
                case TYPE_ROAD: {
                    color = (new Pigment(32, 32, 32));
                    high = new Pigment(64, 64, 64);
                    shade = new Pigment(16, 16, 16);
                    break;
                }
                case TYPE_GRAVEL: {
                    color = (new Pigment(128, 128, 128));
                    break;
                }
                case TYPE_GRASS: {
                    color = (new Pigment(0, 128, 0));
                    break;
                }
                case TYPE_MUD: {
                    color = (new Pigment(128, 64, 32));
                    break;
                }
                case TYPE_BOG: {
                    color = (new Pigment(32, 64, 32));
                    break;
                }
                case TYPE_WATER: {
                    color = (new Pigment(0, 0, 255));
                    break;
                }

            }
            high.Add(color);
            shade.Average(color);

            for (int subtype = 0; subtype < 10; subtype++) {

                if (type == TYPE_OIL) {
                    int c = Random.Next(8);
                    color = (new Pigment(c, c, c));
                    high = new Pigment(Random.Next(64), Random.Next(64), Random.Next(64));
                    shade = new Pigment(Random.Next(16), Random.Next(16), Random.Next(16));
                    high.Add(color);
                    shade.Average(color);
                }
                if (!repeat) {
                    maps[type][subtype] = new EffectMap(Map.blockSizeInPixels, Map.blockSizeInPixels);
                    maps[type][subtype].FillRectangle(0, 0, Map.blockSizeInPixels, Map.blockSizeInPixels, color, false, true, PixelEffect.Average);//body
                }
                for (int x = 0; x < Map.blockSizeInPixels; x++) {
                    for (int y = 0; y < Map.blockSizeInPixels; y++) {
                        Pigment c;//
                        if (type == TYPE_PAVED) {
                            c = new Pigment(Random.Next(1 + color.Red()), (Random.Next(1 + color.Green()) + Random.Next(255) + (x * 10) % 255 + (y * 10) % 255) >> 2, Random.Next(1 + color.Blue()));

                        } else {
                            c = new Pigment(Random.Next(1 + color.Red()), (Random.Next(1 + color.Green()) + Random.Next(255)) >> 1, Random.Next(1 + color.Blue()));
                        }

                        c.Average(color);
                        maps[type][subtype].EffectPixel(x, y, c, false, true, PixelEffect.Hint);
                        c.Tint(high);
                        maps[type][subtype].EffectPixel(x, y, c, false, true, PixelEffect.Hint);
                        c.Hint(shade);
                        maps[type][subtype].EffectPixel(x, y, c, false, true, PixelEffect.Hint);
                    }
                }
                switch (type) {
                    case TYPE_ICE: {
                        Pigment w = new Pigment(255, 255, 255);
                        w.Average(color);
                        DrawCracks(maps[type][subtype], w, 1 + subtype);
                        ;
                        break;
                    }
                    case TYPE_OIL: {
                        Pigment o = new Pigment(Random.Next(255), Random.Next(255), Random.Next(255));
                        DrawRipples(maps[type][subtype], o, 1 + subtype);
                        ;
                        break;
                    }
                    case TYPE_WET: {
                        Pigment w = new Pigment(255, 255, 255);
                        w.Average(color);
                        DrawRipples(maps[type][subtype], w, 1 + subtype);
                        break;
                    }
                    case TYPE_PAVED: {
                        DrawPaving(maps[type][subtype], new Pigment(0, 0, 0));
                        DrawCracks(maps[type][subtype], shade, 1 + subtype);
                        if (subtype > 5) {

                            Pigment g = new Pigment(0, 255, 0);
                            g.Average(color);
                            DrawGrass(maps[type][subtype], g, 1 + subtype);
                        }
                        ;
                        break;
                    }
                    case TYPE_ROAD: {

                        Pigment w = new Pigment(255, 255, 255);
                        w.Average(color);
                        DrawRoad(maps[type][subtype], w, 1 + subtype);

                        Pigment g = new Pigment(32, 32, 32);
                        g.Average(shade);
                        DrawStones(maps[type][subtype], g, 1 + subtype);
                        break;
                    }
                    case TYPE_GRAVEL: {

                        if (subtype > 5) {
                            Pigment g = new Pigment(0, 255, 0);
                            g.Average(color);
                            DrawWeeds(maps[type][subtype], shade, 1 + subtype);
                        }
                        Pigment w = new Pigment(255, 255, 255);
                        w.Average(color);
                        DrawStones(maps[type][subtype], w, 1 + subtype);
                        w.Average(shade);
                        DrawStones(maps[type][subtype], w, 1 + subtype);
                        break;
                    }
                    case TYPE_GRASS: {

                        Pigment g = new Pigment(0, 255, 0);
                        g.Average(color);
                        DrawGrass(maps[type][subtype], g, 1 + subtype);
                        break;
                    }
                    case TYPE_MUD: {

                        Pigment m = new Pigment(0, 0, 0);
                        m.Average(color);
                        DrawRipples(maps[type][subtype], m, 1 + subtype);
                        Pigment g = new Pigment(0, 255, 0);
                        g.Average(color);
                        DrawWeeds(maps[type][subtype], g, 1 + subtype);
                        break;
                    }
                    case TYPE_BOG: {
                        Pigment g = new Pigment(0, 255, 0);
                        g.Average(color);
                        DrawWeeds(maps[type][subtype], g, 1 + subtype);
                        DrawGrass(maps[type][subtype], g, 1 + subtype);
                        break;
                    }
                    case TYPE_WATER: {

                        DrawRipples(maps[type][subtype], shade, 1 + subtype);
                        break;
                    }

                }
                maps[type][subtype].SolidColor(8);
                if (type != TYPE_ROAD) {
                    if (subtype > 0) {
                        maps[type][subtype].Merge(maps[type][subtype - 1], PixelEffect.Average);
                    } else if (repeat) {
                        maps[type][subtype].Merge(maps[type][9], PixelEffect.Average);

                    }
                }
                EffectMap copy = new EffectMap(Map.blockSizeInPixels, Map.blockSizeInPixels);
                copy.Merge(maps[type][subtype], PixelEffect.Average);
                copy.BlurMore(PixelEffect.Average, PixelEffect.Hint, PixelEffect.Tint);
                maps[type][subtype].Merge(copy, PixelEffect.Add);

                images[type][subtype][0] = maps[type][subtype].ToBufferedImage();
            }
        }
    }

    public static void DrawCracks(EffectMap emap, Pigment shade, int stype) {
        int count = emap.width;
        do {
            count--;
            int x = Random.Next(emap.width);
            int y = Random.Next(emap.height);
            int length = (Random.Next(emap.height * stype) + Random.Next(emap.height)) / stype;
            int xx = x;
            int yy = y;
            int dx = 1 + Random.Next(4);
            int dy = 1 + Random.Next(4);
            Molecula m = new Molecula();
            do {
                m.LineCalc();
                Pigment c = new Pigment(Random.Next(1 + shade.Red()), Random.Next(1 + shade.Green()), Random.Next(1 + shade.Blue()));
                c.Average(shade);

                emap.EffectPixel(xx, yy, c, true, false, PixelEffect.Average);
                xx += (int) (dx * m.cosine);
                yy += (int) (dy * m.sine);
                length--;
            } while (length > 0);
        } while (count > 0);
    }

    public static void DrawRipples(EffectMap emap, Pigment shade, int stype) {
        int count = emap.width;
        do {
            count--;
            int x = Random.Next(emap.width);
            int y = Random.Next(emap.height);
            int length = (Random.Next(emap.height * stype) + Random.Next(emap.height)) / stype;
            double xx = x;
            double yy = y;
            //int dx=1+Random.Next(2);            
            //int dy=1+Random.Next(2);
            Molecula m = new Molecula();
            do {
                m.SingleCalc(0.0001);
                Pigment c = new Pigment(Random.Next(1 + shade.Red()), Random.Next(shade.Green() + 256) % 255, Random.Next(1 + shade.Blue()));
                c.Average(shade);

                emap.EffectPixel((int) (xx), (int) (yy), c, true, false, PixelEffect.Average);
                xx += (m.cosine);
                yy += (m.sine);
                length--;
            } while (length > 0);
        } while (count > 0);
    }

    public static void DrawPaving(EffectMap emap, Pigment shade) {
        int d = emap.width / 5;
        for (int x = 1; x < emap.width; x += d) {
            emap.DrawLine(x, 0, x, emap.height - 1, shade, true, false, PixelEffect.Average);
        }
        for (int y = 1; y < emap.height; y += d) {
            emap.DrawLine(0, y, emap.width - 1, y, shade, true, false, PixelEffect.Average);
        }

    }

    public static void DrawRoad(EffectMap emap, Pigment shade, int subtype) {
        if (subtype > 5) {
            subtype %= 5;
            shade.Average(new Pigment(255, 255, 0));
        }
        if (subtype == 1) {
            emap.FillRectangle(emap.width >> 2, (emap.height >> 1) + (emap.height >> 4), (emap.width >> 1) + (emap.width >> 2), (emap.height >> 1) - (emap.height >> 4), shade, false, true);
        } else if (subtype == 2) {
            emap.FillRectangle((emap.width >> 1) + (emap.width >> 4), emap.height >> 2, (emap.width >> 1) - (emap.width >> 4), (emap.height >> 1) + (emap.height >> 2), shade, false, true);
        } else if (subtype == 3) {
            emap.FillRectangle((emap.width >> 1) + (emap.width >> 4), (emap.height >> 1) + (emap.height >> 4), (emap.width >> 1) - (emap.width >> 4), (emap.height >> 1) - (emap.height >> 4), shade, false, true);
            emap.FillRectangle(emap.width >> 2, emap.height >> 2, (emap.width >> 1) + (emap.width >> 2), (emap.height >> 1) + (emap.height >> 2), shade, false, true);

        } else if (subtype == 4) {
            emap.FillRectangle(emap.width >> 2, (emap.height >> 1) + (emap.height >> 4), (emap.width >> 1) + (emap.width >> 2), (emap.height >> 1) - (emap.height >> 4), shade, false, true);
            emap.FillRectangle((emap.width >> 1) + (emap.width >> 4), emap.height >> 2, (emap.width >> 1) - (emap.width >> 4), (emap.height >> 1) + (emap.height >> 2), shade, false, true);

        } else if (subtype == 5) {
            emap.FillRectangle((emap.width >> 1) + (emap.width >> 4), emap.height >> 2, (emap.width >> 1) - (emap.width >> 4), (emap.height >> 1) + (emap.height >> 2), shade, false, true);
            emap.FillRectangle(emap.width >> 1, (emap.height >> 1) + (emap.height >> 4), (emap.width >> 1) + (emap.width >> 2), (emap.height >> 1) - (emap.height >> 4), shade, false, true);
        }
    }

    public static void DrawStones(EffectMap emap, Pigment shade, int stype) {
        int count = emap.width;
        do {
            count--;
            int x = Random.Next(emap.width);
            int y = Random.Next(emap.height);
            int length = (Random.Next(emap.height) + Random.Next(emap.height)) / stype;
            int xx = x;
            int yy = y;
            int dx = 1 + Random.Next(4);
            int dy = 1 + Random.Next(4);
            Molecula m = new Molecula();
            do {
                m.Calc();
                Pigment c = new Pigment(Random.Next(1 + shade.Red()), Random.Next(1 + shade.Green()), Random.Next(1 + shade.Blue()));
                c.Average(shade);

                emap.EffectPixel(xx, yy, c, true, false, PixelEffect.Average);
                xx += (int) (dx * m.cosine);
                yy += (int) (dy * m.sine);
                length--;
            } while (length > 0);
        } while (count > 0);
    }

    public static void DrawWeeds(EffectMap emap, Pigment shade, int stype) {
        int count = emap.width;
        do {
            count--;
            int x = Random.Next(emap.width);
            int y = Random.Next(emap.height);
            int length = (Random.Next(emap.height * stype) + Random.Next(emap.height)) / stype;
            double xx = x;
            double yy = y;
            //int dx=1+Random.Next(2);            
            //int dy=1+Random.Next(2);
            Molecula m = new Molecula();
            do {
                m.SingleCalc(10);
                Pigment c = new Pigment(Random.Next(1 + shade.Red()), Random.Next(shade.Green() + 256) % 255, Random.Next(1 + shade.Blue()));
                c.Average(shade);

                emap.EffectPixel((int) (xx), (int) (yy), c, true, false, PixelEffect.Average);
                xx += (m.cosine);
                yy += (m.sine);
                length--;
            } while (length > 0);
        } while (count > 0);
    }

    public static void DrawGrass(EffectMap emap, Pigment shade, int stype) {
        int count = emap.width;
        do {
            count--;
            int x = Random.Next(emap.width);
            int y = Random.Next(emap.height);
            int length = (Random.Next(emap.height * stype) + Random.Next(emap.height)) / stype;
            double xx = x;
            double yy = y;
            //int dx=1+Random.Next(2);            
            //int dy=1+Random.Next(2);
            Molecula m = new Molecula();
            do {
                m.SingleCalc(100);
                Pigment c = new Pigment(Random.Next(1 + shade.Red()), Random.Next(shade.Green() + 256) % 255, Random.Next(1 + shade.Blue()));
                c.Average(shade);

                emap.EffectPixel((int) (xx), (int) (yy), c, true, false, PixelEffect.Average);
                xx += (m.cosine);
                yy += (m.sine);
                length--;
            } while (length > 0);
        } while (count > 0);
    }

    @Override
    void Collision(DetectorResult with) {

    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        return true;
    }

    @Override
    DetectorResult Detect(java.util.ArrayList<MapElement> me) {
        return null;
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        if (x >= -Map.blockSizeInPixels && y >= -Map.blockSizeInPixels && x < 640 && y < 360) {

            g.drawImage(images[type][subType][light], x, y, null);

        }
    }
}
