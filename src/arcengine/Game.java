/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.*;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn Jones
 */
enum ProcessState {

    Waiting, Running, Stopped, Started, Finished
}

public class Game implements Painter, ActionState, InterAction, InterZone, MouseInterAction {
    // LINUX = "//" WINDOWS "\\"
    public static String SYSTEMDIR="/";
    //
    protected static java.util.concurrent.Executor exec = java.util.concurrent.Executors.newFixedThreadPool(8);
    public static java.util.Hashtable<String, java.awt.image.BufferedImage> Icons = new java.util.Hashtable<>();
    public static java.util.Hashtable<String, java.awt.image.BufferedImage> InfoIcons = new java.util.Hashtable<>();
    // <editor-fold defaultstate="collapsed" desc="Image Loading Methods">

    static boolean SaveImage(String file, String format, java.awt.image.BufferedImage image) {
        java.io.File ifile = new java.io.File(file);

        if (image != null) {
            javax.imageio.stream.ImageOutputStream stream = null;
            try {
                stream = javax.imageio.ImageIO.createImageOutputStream(ifile);
            } catch (java.io.IOException ioex) {
                return false;
            }

            if (stream != null) {
                try {
                    javax.imageio.ImageIO.write(image, format, stream);
                    return true;
                } catch (java.io.IOException iioex) {
                    return false;
                }
            }
        }
        return false;
    }

    static java.awt.image.BufferedImage LoadImage(String file, String format) {
        java.awt.image.BufferedImage image = null;
        java.io.File ifile = new java.io.File(file);
        javax.imageio.stream.ImageInputStream stream = null;
        try {
            stream = javax.imageio.ImageIO.createImageInputStream(ifile);
        } catch (java.io.IOException ioex) {

            System.out.println(file + " Could Not Find File.");
        }

        if (stream != null) {
            try {
                image = javax.imageio.ImageIO.read(stream);
                if (image != null) {
                    System.out.println(file + " Loaded.");
                } else {

                    System.out.println(file + " Could Not Load.");
                }
            } catch (java.io.IOException iioex) {
                System.out.println(file + " Could Be Found.");
            }
        }
        if (gc != null) {
            java.awt.image.BufferedImage copy;
            int trans = image.getColorModel().getTransparency();
            copy = gc.createCompatibleImage(image.getWidth(), image.getHeight(), trans);
            Graphics2D g = copy.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return copy;
        }
        return image;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object Loading Methods">

    public static TessaShape ObjectLoader(String objectFileName, Vector defaultLocation, Pigment primeColor, Pigment secondColor, Pigment defaultColor,double check) {
        String dir = System.getProperty("user.dir");
        System.out.println("LOADING:" + dir + "//" + objectFileName + "\n");
        // java.io.File file=new java.io.File(dir,"Cube.obj");
        java.io.File file = new java.io.File(dir, objectFileName);
        if (file.exists()) {
            java.util.ArrayList<java.lang.StringBuilder> strings = new java.util.ArrayList<>();
            java.lang.StringBuilder build = new java.lang.StringBuilder();
            try {
                java.io.FileReader reader = new java.io.FileReader(file);
                int c = reader.read();
                while (c >= 0) {
                    if (c == '\n') {
                        strings.add(build);
                        build = new StringBuilder();
                        //System.out.print("[loaded]");
                    } else {
                        build.append((char) c);
                        //System.out.print((char)c);
                    }
                    c = reader.read();
                }
            } catch (java.io.IOException ioex) {
                //System.out.print("could not find object");

            }
            RingList<Vector> verts = new RingList();
            RingList<Normal> normals = new RingList();
            java.util.ArrayList<Normal> na = new java.util.ArrayList();
            java.util.ArrayList<Vector> va = new java.util.ArrayList();
            java.util.ArrayList<Pigment> ca = new java.util.ArrayList();
            RingList<Tessa> index = new RingList();
            for (int i = 0; i < strings.size(); i++) {
                String str = strings.get(i).toString();
                if (str.charAt(0) == '#') {
                    continue;
                } else {
                    java.util.StringTokenizer tokens;
                    if (str.charAt(0) == 'v' || str.charAt(0) == 'V') {
                        if (str.charAt(1) == 'n' || str.charAt(1) == 'N') {
                            //NORMAL
                            str = str.substring(2);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                float x = Float.parseFloat(tokens.nextToken());
                                float y = Float.parseFloat(tokens.nextToken());
                                float z = Float.parseFloat(tokens.nextToken());
                                Normal norm = new Normal(x, y, z);
                                normals.Append(norm);
                                na.add(norm);
                            }
                        } else {
                            //VERTEX
                            str = str.substring(1);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                float x = Float.parseFloat(tokens.nextToken());
                                float y = Float.parseFloat(tokens.nextToken());
                                float z = Float.parseFloat(tokens.nextToken());
                                Vector vert = new Vector(x, y, z);
                                verts.Append(vert);
                                va.add(vert);

                                //System.out.print("[Pass]v "+str);
                            } else {
                                if (tokens.countTokens() == 6) {
                                    float x = Float.parseFloat(tokens.nextToken());
                                    float y = Float.parseFloat(tokens.nextToken());
                                    float z = Float.parseFloat(tokens.nextToken());
                                    float r = Float.parseFloat(tokens.nextToken());
                                    float g = Float.parseFloat(tokens.nextToken());
                                    float b = Float.parseFloat(tokens.nextToken());
                                    Vector vert = new Vector(x, y, z);
                                    verts.Append(vert);
                                    va.add(vert);
                                    ca.add(new Pigment((int) (r * 255), (int) (g * 255), (int) (b * 255)));
                                    //System.out.print("[Pass]v "+str);
                                }
                            }
                        }
                    } else {
                        //FACES
                        //System.out.print("[interpreted]#"+vertCount+" verticies, "+normalCount+" vertices normals");
                        if (str.charAt(0) == 'f' || str.charAt(0) == 'F') {
                            //
                            str = str.substring(1);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                //System.out.print("[Pass]f ");
                                int vi[] = new int[3];
                                int ni[] = new int[3];
                                for (int nv = 0; nv < 3; nv++) {
                                    String nt = tokens.nextToken();
                                    java.util.StringTokenizer face = new java.util.StringTokenizer(nt);
                                    if (nt.contains("//")) {
                                        int v = Integer.parseInt(face.nextToken("/"));
                                        int n = Integer.parseInt(face.nextToken("/"));
                                        //System.out.print(v+"//"+n+" ");
                                        vi[nv] = v - 1;
                                        ni[nv] = n - 1;
                                    } else {
                                        int v = Integer.parseInt(face.nextToken("/"));
                                        int t = Integer.parseInt(face.nextToken("/"));
                                        int n = Integer.parseInt(face.nextToken("/"));
                                        //System.out.print(v+"//"+n+" ");
                                        vi[nv] = v - 1;
                                        ni[nv] = n - 1;
                                    }
                                }
                                Pigment color = defaultColor;
                                if (ca.size() == verts.Length()) {
                                    Pigment c0 = ca.get(vi[0]);
                                    Pigment c1 = ca.get(vi[1]);
                                    Pigment c2 = ca.get(vi[2]);
                                    color = new Pigment((c0.Red() + c1.Red() + c2.Red()) / 3, (c0.Green() + c1.Green() + c2.Green()) / 3, (c0.Blue() + c1.Blue() + c2.Blue()) / 3);
                                    color.Average(defaultColor);
                                    color.Tint(secondColor);
                                    color.Hint(primeColor);
                                }
                                Normal n0 = na.get(ni[0]);
                                Normal n1 = na.get(ni[1]);
                                Normal n2 = na.get(ni[2]);
                                //Normal norm=new Normal((n0.x+n1.x+n2.x)*(float)Defined.Third,(n0.y)+n1.y+n2.y)*(float)Defined.Third,(n0.z+n1.z+n2.z)*(float)Defined.Third);
                                Vector v0 = va.get(vi[0]);
                                Vector v1 = va.get(vi[1]);
                                Vector v2 = va.get(vi[2]);
                                Vector vr = v0.Sub(v0, v1.Sub(v1, v2)).AsVector();
                                double rv = vr.Length();
                                Vector vc = new Vector(color.Red() * 0.0039, color.Green() * 0.0039, color.Blue() * 0.0039);
                                TessaVertex vx1 = new TessaVertex(v0, n0, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                TessaVertex vx2 = new TessaVertex(v1, n1, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                TessaVertex vx3 = new TessaVertex(v2, n2, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                index.Append(new Tessa(vx1, vx2, vx3, rv));

                                //System.out.print("\n");
                            }
                        }
                    }
                }
            }
            if (verts.Length() > 0 && index.Length() > 0) {
                //Space size=new Space(25,0,0);
                // Time moment=new Time(defaultLocation,new Vector());
                //Physical body=new Physical(null,size,moment,null);

                TessaShape shape = new TessaShape(index);
                TessaShape.Equalify(shape,check);
        
                return shape;
            }
        } else {

            System.out.print("could not find file");
        }
        return null;
    }
// </editor-fold>

    public static TessaShape TestObjectLoader(Vector defaultLocation, Pigment defaultColor) {
        String dir = System.getProperty("user.dir");
        System.out.print(dir + "\n");
        
        // java.io.File file=new java.io.File(dir,"Cube.obj");
        java.io.File file = new java.io.File(dir, "ArcFusionTurbine2.obj");
        if (file.exists()) {
            java.util.ArrayList<java.lang.StringBuilder> strings = new java.util.ArrayList<>();
            java.lang.StringBuilder build = new java.lang.StringBuilder();
            try {
                java.io.FileReader reader = new java.io.FileReader(file);
                int c = reader.read();
                while (c >= 0) {
                    if (c == '\n') {
                        strings.add(build);
                        build = new StringBuilder();
                        System.out.print("[loaded]");
                    } else {
                        build.append((char) c);
                        System.out.print((char) c);
                    }

                    c = reader.read();
                }
            } catch (java.io.IOException ioex) {
                System.out.print("could not find object");

            }
            RingList<Vector> verts = new RingList();
            RingList<Normal> normals = new RingList();
            java.util.ArrayList<Vector> va = new java.util.ArrayList<>();
            java.util.ArrayList<Normal> na = new java.util.ArrayList<>();
            java.util.ArrayList<Pigment> ca = new java.util.ArrayList<>();
            RingList<Tessa> index = new RingList();
            for (int i = 0; i < strings.size(); i++) {
                String str = strings.get(i).toString();
                if (str.charAt(0) == '#') {
                    continue;
                } else {
                    java.util.StringTokenizer tokens;
                    if (str.charAt(0) == 'v' || str.charAt(0) == 'V') {
                        if (str.charAt(1) == 'n' || str.charAt(1) == 'N') {
                            //NORMAL
                            str = str.substring(2);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                float x = Float.parseFloat(tokens.nextToken());
                                float y = Float.parseFloat(tokens.nextToken());
                                float z = Float.parseFloat(tokens.nextToken());
                                Normal norm = new Normal(x, y, z);
                                normals.Append(norm);
                                na.add(norm);
                                System.out.print("[Pass]vn " + str);
                            }
                        } else {
                            //VERTEX
                            str = str.substring(1);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                float x = Float.parseFloat(tokens.nextToken());
                                float y = Float.parseFloat(tokens.nextToken());
                                float z = Float.parseFloat(tokens.nextToken());
                                Vector vert = new Vector(x, y, z);
                                verts.Append(vert);
                                va.add(vert);

                                System.out.print("[Pass]v " + str);
                            } else {
                                if (tokens.countTokens() == 6) {
                                    float x = Float.parseFloat(tokens.nextToken());
                                    float y = Float.parseFloat(tokens.nextToken());
                                    float z = Float.parseFloat(tokens.nextToken());
                                    float r = Float.parseFloat(tokens.nextToken());
                                    float g = Float.parseFloat(tokens.nextToken());
                                    float b = Float.parseFloat(tokens.nextToken());
                                    Vector vert = new Vector(x, y, z);
                                    verts.Append(vert);
                                    va.add(vert);
                                    ca.add(new Pigment((int) (r * 255), (int) (g * 255), (int) (b * 255)));
                                    System.out.print("[Pass]v " + str);
                                }
                            }
                        }
                    } else {
                        //FACES
                        //System.out.print("[interpreted]#"+vertCount+" verticies, "+normalCount+" vertices normals");
                        if (str.charAt(0) == 'f' || str.charAt(0) == 'F') {
                            //
                            str = str.substring(1);
                            tokens = new java.util.StringTokenizer(str);
                            if (tokens.countTokens() == 3) {
                                System.out.print("[Pass]f ");
                                int vi[] = new int[3];
                                int ni[] = new int[3];
                                for (int nv = 0; nv < 3; nv++) {
                                    String nt = tokens.nextToken();
                                    java.util.StringTokenizer face = new java.util.StringTokenizer(nt);
                                    if (nt.contains("//")) {
                                        int v = Integer.parseInt(face.nextToken("/"));
                                        int n = Integer.parseInt(face.nextToken("/"));
                                        System.out.print(v + "//" + n + " ");
                                        vi[nv] = v - 1;
                                        ni[nv] = n - 1;
                                    } else {
                                        int v = Integer.parseInt(face.nextToken("/"));
                                        int t = Integer.parseInt(face.nextToken("/"));
                                        int n = Integer.parseInt(face.nextToken("/"));
                                        System.out.print(v + "//" + n + " ");
                                        vi[nv] = v - 1;
                                        ni[nv] = n - 1;
                                    }
                                }
                                Pigment color = defaultColor;
                                if (ca.size() == verts.Length()) {
                                    Pigment c0 = ca.get(vi[0]);
                                    Pigment c1 = ca.get(vi[1]);
                                    Pigment c2 = ca.get(vi[2]);
                                    color = new Pigment((c0.Red() + c1.Red() + c2.Red()) / 3, (c0.Green() + c1.Green() + c2.Green()) / 3, (c0.Blue() + c1.Blue() + c2.Blue()) / 3);
                                    color.Average(defaultColor);
                                }
                                Normal n0 = na.get(ni[0]);
                                Normal n1 = na.get(ni[1]);
                                Normal n2 = na.get(ni[2]);
                                //Normal norm=new Normal((n0.x+n1.x+n2.x)*(float)Defined.Third,(n0.y)+n1.y+n2.y)*(float)Defined.Third,(n0.z+n1.z+n2.z)*(float)Defined.Third);
                                Vector v0 = va.get(vi[0]);
                                Vector v1 = va.get(vi[1]);
                                Vector v2 = va.get(vi[2]);
                                Vector vr = v0.Sub(v0, v1.Sub(v1, v2)).AsVector();
                                double rv = vr.Length();
                                Vector vc = new Vector(color.Red() * 0.0039, color.Green() * 0.0039, color.Blue() * 0.0039);
                                TessaVertex vx1 = new TessaVertex(v0, n0, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                TessaVertex vx2 = new TessaVertex(v1, n1, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                TessaVertex vx3 = new TessaVertex(v2, n2, vc, rv); //,va.get(vi[1]),va.get(vi[2])
                                index.Append(new Tessa(vx1, vx2, vx3, rv));

                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            if (verts.Length() > 0 && index.Length() > 0) {
                //Space size=new Space(25,0,0);
                //Time moment=new Time(defaultLocation,new Vector());
                //Physical body=new Physical(null,size,moment,null);

                TessaShape shape = new TessaShape(index);
                return shape;
            }
        } else {

            System.out.print("could not find file");
        }
        return null;
    }
    static boolean running = true;
    static GameTime time = new GameTime();
    static GraphicsConfiguration gc;
    static int steps = 12;
    static int maxSteps = 100;
    static int step = maxSteps / steps;
    static Activator frame;
    static ArrayList<Screen> screens;
    static Simulation simulation = new Simulation();
    static int currentScreen;
    static volatile PlanetMap board;
    static volatile Government gov;
    static volatile GeoSphere geosphere;
    static volatile java.util.Set<Government> governments = new java.util.HashSet<>();
// <editor-fold defaultstate="collapsed" desc="Icon Loading Methods">

    static void LoadInfoIcons() {

        String file = "Icons\\Dead";
        for (int i = 1; i < 16; i++) {
            String str = "";
            if (i <= 1) {
                str = "Object";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Skull";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HeadMale";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HeadFemale";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "SkullMale";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "SkullFemale";
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 4) {

                str = "HandLeft" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HandRight" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "MouthBleeding" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "MouthSmile" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
            }

            if (i <= 5) {

                str = "HeadMale" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HeadFemale" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 7) {

                str = "Mouth" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Character" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 8) {
                str = "" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));

            }

            if (i <= 10) {

                str = "MouthOpen" + i;
                InfoIcons.put(str, LoadImage(file + str + ".png", ".png"));
            }
        }
    }

    static void LoadIcons() {
        String file = "Icons\\Dead";
        for (int i = 1; i < 16; i++) {
            String str = "";
            if (i <= 1) {
                str = "BackPack";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Armour";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "AutoGL";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "BandAid";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Battery";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "BatteryDead";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));

                str = "Bat";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));

                str = "BatteryBomb";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "BBBomb";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "CrowBar";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Cutters";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Drill";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Flare";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "FirstAid";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "FireTorch";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Dynamite";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Flamer";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "GarlicBomb";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "GL";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "GoldCross";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "SilverCross";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "WoodCross";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Grave";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Karasin";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Key";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Lighter";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "LighterSpark";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "LSDBomb";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Mirror";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Money";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Mushrooms";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "NailBomb";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "NailGun";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Nails";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Necronomicon";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "PLaser";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Pump";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Poker";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Radio";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "RadioActive";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Saw";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "ScrewDriver";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "SurgicalAid";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Toilet";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Vest";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "WoodStake";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Wood";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Torch";
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 2) {

                str = "Bag" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "AutoPistol" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "AutoRifle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Axe" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Bible" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Book" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Cloths" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Fuel" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "LAG" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Laser" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "MCT" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "MG" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Oil" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Petrol" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "PickAxe" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Pistol" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "SpiritsBottle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Spade" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "WaterBottle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "WineBottle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HolyWater" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Tunnel" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 3) {

                str = "AutoHandGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "AutoShotGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "BatteryTorch" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "BBGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Bio" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Blade" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Can" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));

                str = "Generator" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Hammer" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));

                str = "Mushrooms" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Pants" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Shirt" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Sword" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Water" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "WaterCan" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 4) {
                str = "Case" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "AssaultRifle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HandShotGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "ShotGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 5) {

                str = "Garlic" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Rifle" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 6) {
                str = "Food" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Gas" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "Granade" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }

            if (i <= 7) {
            }
            if (i <= 8) {

                str = "SMG" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 9) {
                str = "Ammo" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
                str = "HandGun" + i;
                Icons.put(str, LoadImage(file + str + ".png", ".png"));
            }
            if (i <= 10) {
            }
            if (i <= 11) {
            }
            if (i <= 12) {
            }
            if (i <= 13) {
            }
            if (i <= 14) {
            }
            if (i <= 15) {
            }
        }
    }

// </editor-fold>
    int width;
    int height;

    Game() {
        super();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        width = dim.width;
        height = dim.height;
    }

    public void SetUp(SetUp set) {

        time.Start();

        if (set != null) {
            frame = set.GetGame();
        } else {
            frame = new ArcFrame();
        }
        screens = new ArrayList<>();

        //FirstWorld(16);
        //FirstWorld(5);
        time.CalcTimes();
    }
// <editor-fold defaultstate="collapsed" desc="SetUp Methods">

    void FirstWorld(int sz) {
        //HOME
        LoadFrame lf = new LoadFrame();
        SetUpGameGovernment(Color.green);
        SetUpGameWorld((sz) << 5, lf);
        //
        if (sz == 5) {
            geosphere = new GeoSphere(board, 5, lf, step);
        }
        lf.dispose();

    }

    void SetUpGameGovernment(Color c) {
        gov = new Government(new Pigment(c.getRed() + Random.Next(64) - Random.Next(64), c.getGreen() + Random.Next(64) + Random.Next(64), c.getBlue() + Random.Next(64) + Random.Next(64)));
    }

    void SetUpGameWorld(int size, LoadFrame lf) {
        board = new PlanetMap(size, new Molecula());
        double em = PlanetMap.RandomEarths();
        double er = PlanetMap.RandomEarths();
        //EffectMap emap=null;
        board.SetUpRandomTerraPlanet(em, er, false);
        int h = board.planet.planetMap.height >> 1;
        int hh = h >> 1;
        double bx = (h * Random.NextDouble() - h * Random.NextDouble()) * Math.PI;
        double by = (hh * Random.NextDouble() - hh * Random.NextDouble()) * Math.PI;
        double cx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        double cy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        int v = 2;
        int vulatility = v + Random.Next(v) - Random.Next(v);
        lf.Add(step, "Generating Planet Surface Map");
        HeightMap.GeneratePlanetSurface(board.planet, bx, by, cx, cy, (v + 1) - vulatility);
        //for(int i=0;i<vulatility;i++){
        bx += (h * Random.NextDouble() - h * Random.NextDouble()) / Math.PI;
        by += (hh * Random.NextDouble() - hh * Random.NextDouble()) / Math.PI;
        cx += (0.5 + Random.NextDouble() - Random.NextDouble()) / Math.PI;//ws/ds;
        cy += (0.5 + Random.NextDouble() - Random.NextDouble()) / Math.PI;//ws/ds;

        lf.Add(step, "Generating Random Planet Surface Effects");
        HeightMap.RandomPlanetEffect(board.planet, bx, by, cx, cy, (v >> 1) + (vulatility >> 1));

        lf.Add(step, "Generating Planet Effects");
        HeightMap.PlanetEffects(board.planet, bx, by, cx, cy, vulatility);
        //float ds= board.planet.planetMap.dataScale;
        //board.planet.planetMap=new HeightMap(board.planet.planetMap.width,board.planet.planetMap.height);
        //board.planet.planetMap.dataScale=ds;
        //board.planet.surfaceMap=new ColorMap(board.planet.surfaceMap.width,board.planet.surfaceMap.height);
        //
        for (int j = 0; j < vulatility; j++) {
            lf.Add(step / vulatility, "Generating Random Planet Surface Effects");
            HeightMap.RandomPlanetEffect(board.planet, bx, by, cx, cy, 1);
        }
        /*HeightMap.GeneratePlanetSurfaceImage(board.planet,new Pigment(Random.Next(128)+Random.Next(128),Random.Next(128)+Random.Next(128),Random.Next(128)+Random.Next(128)));
         if(emap==null){
         emap=new EffectMap(board.planet.surfaceMap.width,board.planet.surfaceMap.height);
         emap.data=board.planet.surfaceMap.data;
         }else{
         emap.Merge(board.planet.surfaceMap,PixelEffect.Add);
         }*/
        //HeightMap.EnhancePlanetMap(board.planet.planetMap,board.planet.planetMap);
        //HeightMap.NoiseEnhancePlanetMap(board.planet.planetMap,board.planet.planetMap);

        //}
        lf.Add(step, "Generating Planet Surface");
        HeightMap.NoiseAmplifyPlanetMap(board.planet.planetMap, board.planet.planetMap);
        HeightMap.GeneratePlanetSurfaceImage(board.planet, new Pigment(Random.Next(128) + Random.Next(128), Random.Next(128) + Random.Next(128), Random.Next(128) + Random.Next(128)));

        lf.Add(step, "Generating Planet Surface Map");
        board.map.SetUpLandMap(board.planet);
        System.out.println("Generated Image");
        int mx = board.map.width >> 1;
        int my = board.map.height >> 1;
        board.map.sections[mx][my].Add(new Dome(gov, 0.5f, mx, my));
        board.map.sections[mx][my].Add(new City(gov, 0.5f, mx, my));
        //SaveImage("test.png","png",emap.ToBufferedImage());
        //SaveImage("test.png","png",board.planet.surfaceMap.ToBufferedImage());

    }

    void SetUpAnimWorld(LoadFrame lf, int size, int anims, int subs) {
        EffectMap lastAnim = null;//new EffectMap(1440,900);
        EffectMap firstAnim = null;//new EffectMap(1440,900);
        int anim = anims;
        int subanims = 1;
        if (size < 0) {
            size = Math.abs(size);
        }
        if (size > 1024) {
            size = 1024;
        }
        board = new PlanetMap(size, new Molecula());
        //EffectMap emap=null;
        int spin = size / (anims * subs);
        if (spin <= 0) {
            spin = 1;
        }
        double em = PlanetMap.RandomEarths();
        double er = PlanetMap.RandomEarths();
        while (anim > subanims) {
            board.SetUpRandomTerraPlanet(em, er, true);
            int h = board.planet.planetMap.height >> 1;
            int hh = h >> 1;
            double bx = (h * Random.NextDouble() - h * Random.NextDouble()) * Math.PI;
            double by = (hh * Random.NextDouble() - hh * Random.NextDouble()) * Math.PI;
            double cx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
            double cy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
            int v = 2;
            int vulatility = v + Random.Next(v) - Random.Next(v);
            lf.Add(step, "Generating Planet Surface Map " + anim);
            HeightMap.GeneratePlanetSurface(board.planet, bx, by, cx, cy, (v + 1) - vulatility);
            //for(int i=0;i<vulatility;i++){
            bx += (h * Random.NextDouble() - h * Random.NextDouble()) / Math.PI;
            by += (hh * Random.NextDouble() - hh * Random.NextDouble()) / Math.PI;
            cx += (0.5 + Random.NextDouble() - Random.NextDouble()) / Math.PI;//ws/ds;
            cy += (0.5 + Random.NextDouble() - Random.NextDouble()) / Math.PI;//ws/ds;

            lf.Add(step, "Generating Random Planet Surface Effects " + anim);
            HeightMap.RandomPlanetEffect(board.planet, bx, by, cx, cy, (v >> 1) + (vulatility >> 1));

            lf.Add(step, "Generating Planet Effects " + anim);
            HeightMap.PlanetEffects(board.planet, bx, by, cx, cy, vulatility);
            for (int j = 0; j < vulatility; j++) {
                lf.Add(step / vulatility, "Generating Random Planet Surface Effects " + anim);
                HeightMap.RandomPlanetEffect(board.planet, bx, by, cx, cy, 1);
            }
            lf.Add(step, "Generating Planet Surface " + anim);
            HeightMap.EnhancePlanetMap(board.planet.planetMap, board.planet.planetMap);
            HeightMap.NoiseAmplifyPlanetMap(board.planet.planetMap, board.planet.planetMap);
            HeightMap.GeneratePlanetSurfaceImage(board.planet, new Pigment(Random.Next(128) + Random.Next(128), Random.Next(128) + Random.Next(128), Random.Next(128) + Random.Next(128)));

            lf.Add(step, "Generating Planet Surface Map " + anim);
            for (int a = 0; a < subanims; a++) {
                if (lastAnim != null) {
                    Pigment buffer1 = null;
                    Pigment buffer2 = null;
                    for (int i = 0; i < spin; i++) {
                        for (int y = 0; y < lastAnim.height; y++) {
                            for (int x = 0; x < lastAnim.width; x++) {
                                if (x == 0) {
                                    buffer1 = new Pigment(lastAnim.data[x][y]);
                                    buffer2 = new Pigment(board.planet.surfaceMap.data[x][y]);
                                } else {
                                    lastAnim.data[x - 1][y] = lastAnim.data[x][y];
                                    board.planet.surfaceMap.data[x - 1][y] = board.planet.surfaceMap.data[x][y];
                                }
                            }
                            lastAnim.data[lastAnim.width - 1][y] = buffer1;
                            board.planet.surfaceMap.data[board.planet.surfaceMap.width - 1][y] = buffer2;
                        }
                    }
                }
                if (lastAnim == null) {
                    lastAnim = new EffectMap(board.planet.surfaceMap.width, board.planet.surfaceMap.height);
                    lastAnim.data = board.planet.surfaceMap.data;
                    firstAnim = new EffectMap(board.planet.surfaceMap.width, board.planet.surfaceMap.height);
                    firstAnim.data = board.planet.surfaceMap.data;
                } else {
                    lastAnim.Merge(board.planet.surfaceMap, PixelEffect.Hint);
                }

                lf.Add(-lf.at, "Next Anim " + anim);
                //board.map.SetUpLandMap(board.planet);
                System.out.println("Generated Image " + anim);

                //int mx=board.map.width>>1;
                //int my=board.map.height>>1;
                //board.map.sections[mx][my].Add(new Dome(gov,0.5f,mx,my));
                //board.map.sections[mx][my].Add(new City(gov,0.5f,mx,my));
                SaveImage(anim + ".png", "png", lastAnim.ToBufferedImage());

                anim--;
            }
            subanims = subs;
        }
        for (int a = 0; a < subanims; a++) {
            lastAnim.Merge(firstAnim, PixelEffect.Tint);
            anim--;
            SaveImage(anim + ".png", "png", lastAnim.ToBufferedImage());
        }
        //SaveImage("test.png","png",board.planet.surfaceMap.ToBufferedImage());
        lf.dispose();
    }

// </editor-fold>
    public void UpdateGame() {

    }

    public void mainLoop(ArcEngine eng) throws Exception {
        do {
            time.CalcTimes();
            UpdateGame();
            frame.PaintGame(this);
            frame.Activate(this);
            frame.InterActivate(this);

        } while (running);
        frame.Close();

        System.exit(0);
    }
// <editor-fold defaultstate="collapsed" desc="Interfaces">

    @Override
    public void paintAll(Graphics2D g, double scale) {
        if (screens.size() > currentScreen) {
            Screen scr = screens.get(currentScreen);
            scr.paintAll(g, scale);
        }
        //g.drawImage(planetImage, 0,0, frame);
    }

    @Override
    public void active(short state) {
        if (state < 0) {
            running = false;
        } else {
            if (screens.size() > currentScreen) {
                Screen scr = screens.get(currentScreen);
                scr.UpdateScreen(time);
            }
        }
    }

    @Override
    public MouseZone mouseAction() {
        if (screens.size() > currentScreen) {
            Screen scr = screens.get(currentScreen);
            return scr;
        }
        return null;
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        m.testZone(m, i);
    }

    @Override
    public void mouseAction(Mouse m) {
        //System.out.println("MOUSE");
        if (screens.size() > currentScreen) {
            Screen scr = screens.get(currentScreen);
            scr.mouseMoved(m.event);
            mouseAction(scr, scr);
            if(m.dragged!=null){
            scr.mouseDragged(m.dragged);
            }
            if(m.wheelEvent!=null){
            scr.mouseWheeled(m.wheelEvent);
            }
        }

    }

// </editor-fold>
    @Override
    public void paint(Graphics2D g, double scale) {
        if (screens.size() > currentScreen) {
            Screen scr = screens.get(currentScreen);
            scr.paint(g, scale);
        }

    }
}
