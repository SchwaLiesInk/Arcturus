/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import static arcengine.Game.gc;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class GameGraphics3D implements Graphical {

    Graphics2D g;
    int index;

    GameGraphics3D(Graphics2D g) {
        this.g = g;
    }

    @Override
    public void SetGraphics(Graphics2D g) {
        this.g = g;

    }

    @Override
    public void SetGraphics(Graphics2D g, int index) {
        this.g = g;
        this.index = index;
    }

    /*
     @Override
     public void DrawLine(Vertex2D from, Vertex2D to, double r, Pigment argb, DrawType type,int index) {
     g.setColor(argb.Color());
     switch(type){
     case Point:{
     g.drawLine((int)from.x, (int)from.y,(int)from.x, (int)from.y);
     g.drawLine((int)to.x,(int) to.y, (int)to.x,(int) to.y);
    
     break;}
     case Line:{
     g.drawLine((int)from.x, (int)from.y, (int)to.x,(int) to.y);
     break;}
     case Triangle:{
     g.drawLine((int)from.x, (int)from.y, (int)to.x,(int) to.y);
                
     break;}
     case Text:{
     g.drawLine((int)from.x, (int)from.y, (int)to.x,(int) to.y);
                
     break;}
     case Voxel:{
     g.drawLine((int)from.x, (int)from.y, (int)to.x,(int) to.y);
                
     break;}
        
     }
     }

     @Override
     public void DrawDot(Vertex2D at, double r, Pigment argb,DrawType type,int index) {
     g.setColor(argb.Color());
     switch(type){
     case Point:{
     g.drawLine((int)at.x, (int)at.y,(int)at.x, (int)at.y);
    
     break;}
     case Line:{
     g.drawLine((int)at.x, (int)at.y,(int)at.x, (int)at.y);
     break;}
     case Triangle:{
     g.drawLine((int)at.x, (int)at.y,(int)at.x, (int)at.y);
                
     break;}
     case Text:{
     g.drawLine((int)at.x, (int)at.y,(int)at.x, (int)at.y);
                
     break;}
     case Voxel:{
     g.drawLine((int)at.x, (int)at.y,(int)at.x, (int)at.y);
                
     break;}
        
     }
     }

     @Override
     public void DrawCircle(Vertex2D at, double r, Pigment argb, DrawType type,int index) {
     g.setColor(argb.Color());
     switch(type){
     case Point:{
     g.fillOval((int)(at.x-r*0.5), (int)(at.y-r*0.5),(int)r,(int)r);
     break;}
     case Line:{
     g.drawOval((int)(at.x-r*0.5), (int)(at.y-r*0.5),(int)r,(int)r);
     break;}
     case Triangle:{
                    
     g.drawOval((int)(at.x-r*0.5), (int)(at.y-r*0.5),(int)r,(int)r);
     break;}
     case Text:{
     g.drawOval((int)(at.x-r*0.5), (int)(at.y-r*0.5),(int)r,(int)r);
                
     break;}
     case Voxel:{
     g.fillOval((int)(at.x-r*0.5), (int)(at.y-r*0.5),(int)r,(int)r);
                
     break;}
        
     }
     }

     @Override
     public void DrawTriangle(Vertex2D at1, Vertex2D at2, Vertex2D at3, double r, Pigment argb, DrawType type,int index) {
     g.setColor(argb.Color());
     switch(type){
     case Point:{
     g.drawLine((int)at1.x, (int)at1.y,(int)at1.x, (int)at1.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at3.x, (int)at3.y);
    
     break;}
     case Line:{
     g.drawLine((int)at1.x, (int)at1.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at3.x, (int)at3.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at1.x, (int)at1.y);
     break;}
     case Triangle:{
     int xPoints[]=new int[]{
     (int)at1.x,(int)at2.x,(int)at3.x
     };
     int yPoints[]=new int[]{
     (int)at1.y,(int)at2.y,(int)at3.y
     };
     g.fillPolygon(xPoints, yPoints, 3);
     break;}
     case Text:{ g.drawLine((int)at1.x, (int)at1.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at3.x, (int)at3.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at1.x, (int)at1.y);
                
     break;}
     case Voxel:{
     int xPoints[]=new int[]{
     (int)at1.x,(int)at2.x,(int)at3.x
     };
     int yPoints[]=new int[]{
     (int)at1.y,(int)at2.y,(int)at3.y
     };
     g.fillPolygon(xPoints, yPoints, 3);
     break;}
        
     }
     }

     @Override
     public void DrawQuad(Vertex2D at1, Vertex2D at2, Vertex2D at3, Vertex2D at4, double r, Pigment argb, DrawType type,int index) {
     g.setColor(argb.Color());
     switch(type){
     case Point:{
     g.drawLine((int)at1.x, (int)at1.y,(int)at1.x, (int)at1.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at3.x, (int)at3.y);
     g.drawLine((int)at4.x, (int)at4.y,(int)at4.x, (int)at4.y);
    
     break;}
     case Line:{
     g.drawLine((int)at1.x, (int)at1.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at3.x, (int)at3.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at4.x, (int)at4.y);
     g.drawLine((int)at4.x, (int)at4.y,(int)at1.x, (int)at1.y);
     break;}
     case Triangle:{
     int xPoints[]=new int[]{
     (int)at1.x,(int)at2.x,(int)at3.x,(int)at4.x
     };
     int yPoints[]=new int[]{
     (int)at1.y,(int)at2.y,(int)at3.y,(int)at4.y
     };
     g.fillPolygon(xPoints, yPoints, 4);
     break;}
     case Text:{ 
     g.drawLine((int)at1.x, (int)at1.y,(int)at2.x, (int)at2.y);
     g.drawLine((int)at2.x, (int)at2.y,(int)at3.x, (int)at3.y);
     g.drawLine((int)at3.x, (int)at3.y,(int)at4.x, (int)at4.y);
     g.drawLine((int)at4.x, (int)at4.y,(int)at1.x, (int)at1.y);
                
     break;}
     case Voxel:{
     int xPoints[]=new int[]{
     (int)at1.x,(int)at2.x,(int)at3.x,(int)at4.x
     };
     int yPoints[]=new int[]{
     (int)at1.y,(int)at2.y,(int)at3.y,(int)at4.y
     };
     g.fillPolygon(xPoints, yPoints, 4);
     break;}
        
     }
     }

     @Override
     public void DrawText(Vertex2D at, String text, double space, Pigment argb, DrawType type,int index) {
     int s=(int)space;
     switch(type){
     case Point:{
     g.setColor(argb.Color());
     g.drawString(text,(int) at.x, (int)at.y);
    
     break;}
     case Line:{
     Pigment c=new Pigment(argb);
     Pigment b=new Pigment(0,0,0,0);
     c.Average(b);
     g.setColor(c.Color());
     g.drawString(text,(int) at.x, (int)at.y);
     g.setColor(argb.Color());
     g.drawString(text,(int) at.x+s, (int)at.y);
     break;}
     case Triangle:{
                
     Pigment c=new Pigment(argb);
     Pigment b=new Pigment(0,0,0,0);
     c.Average(b);
     g.setColor(c.Color());
     g.drawString(text,(int) at.x-s, (int)at.y-1);
     g.drawString(text,(int) at.x+s, (int)at.y+1);
     g.setColor(argb.Color());
        
     g.drawString(text,(int) at.x, (int)at.y);
     break;}
     case Text:{ 
     g.setColor(argb.Color());
     g.drawString(text,(int) at.x, (int)at.y);
                
     break;}
     case Voxel:{
     g.setColor(argb.Color());
     g.drawString(text,(int) at.x, (int)at.y);
     break;}
        
     }
     }
     */
    @Override
    public void DrawLine(Vertex2D from, Vertex2D to, double r, Pigment argb, DrawType type, int index) {
        g.setColor(argb.Colour());
        g.drawLine((int) from.x, (int) from.y, (int) to.x, (int) to.y);
    }

    @Override
    public void DrawDot(Vertex2D at, double r, Pigment argb, DrawType type, int index) {
        g.setColor(argb.Colour());
        //System.out.println("DOT "+at.x+" "+at.y+" "+at.z+" "+at.w+" "+argb.Colour().getRGB());
        g.drawLine((int) at.x, (int) at.y, (int) at.x, (int) at.y);

    }

    @Override
    public void DrawCircle(Vertex2D at, double r, Pigment argb, DrawType type, int index) {
        g.setColor(argb.Colour());
        //System.out.println("Circle "+at.x+" "+at.y+" "+r);

        g.fillOval((int) (at.x - r), (int) (at.y - r), (int) r*2, (int) r*2);

    }

    @Override
    public void DrawTriangle(Vertex2D at1, Vertex2D at2, Vertex2D at3, double r, Pigment argb, DrawType type, int index) {
        g.setColor(argb.Colour());
        //g.drawLine((int)at1.x, (int)at1.y,(int)at2.x, (int)at2.y);
        //g.drawLine((int)at2.x, (int)at2.y,(int)at3.x, (int)at3.y);
        //g.drawLine((int)at3.x, (int)at3.y,(int)at1.x, (int)at1.y);
        //at1.y+=512;
        //at2.y+=512;
        //at3.y+=512;
        //System.out.println("TRIANGLE v1 "+at1.x+" "+at1.y);
        //System.out.println("TRIANGLE v2 "+at2.x+" "+at2.y);
        //System.out.println("TRIANGLE v3 "+at3.x+" "+at3.y);
        int xPoints[] = new int[]{
            (int) at1.x, (int) at2.x, (int) at3.x,};
        int yPoints[] = new int[]{
            (int) at1.y, (int) at2.y, (int) at3.y,};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void DrawQuad(Vertex2D at1, Vertex2D at2, Vertex2D at3, Vertex2D at4, double r, Pigment argb, DrawType type, int index) {
        g.setColor(argb.Colour());

        int xPoints[] = new int[]{
            (int) at1.x, (int) at2.x, (int) at3.x, (int) at4.x
        };
        int yPoints[] = new int[]{
            (int) at1.y, (int) at2.y, (int) at3.y, (int) at4.y
        };
        g.fillPolygon(xPoints, yPoints, 4);

    }

    @Override
    public void DrawText(Vertex2D at, String text, double space, Pigment argb, DrawType type, int index) {

        g.setColor(argb.Colour());
        g.drawString(text, (int) at.x, (int) at.y);
    }

}

class GraphicsIndex extends GameGraphics3D implements GraphicalIndexer {

    Graphical[] graph = new Graphical[64];
    int indecies = 0;

    public GraphicsIndex(Graphics2D g) {
        super(g);
        graph[0] = new GameGraphics3D(g);
        graph[0].SetGraphics(g, 0);
        indecies = 1;
    }

    public void SetAllGraphics(Graphics2D g) {
        for (int i = 0; i < indecies; i++) {
            graph[i].SetGraphics(g, i);
        }
    }

    public void SetGraphics(Graphics2D g, int index) {
        if (index <= indecies) {
            graph[index].SetGraphics(g, index);
        }
    }

    int MaxIndecies() {
        return graph.length;
    }

    @Override
    public int AddIndex(Graphical graphic) {
        if (indecies < 64) {
            graph[indecies] = graphic;
            indecies++;
            return indecies;
        }
        return -1;
    }

    @Override
    public void DrawLine(Vertex2D from, Vertex2D to, double r, Pigment argb, DrawType type, int index) {
        graph[index].DrawLine(from, to, r, argb, type, index);
    }

    @Override
    public void DrawDot(Vertex2D at, double r, Pigment argb, DrawType type, int index) {
        graph[index].DrawDot(at, r, argb, type, index);
    }

    @Override
    public void DrawCircle(Vertex2D at, double r, Pigment argb, DrawType type, int index) {
        graph[index].DrawCircle(at, r, argb, type, index);
    }

    @Override
    public void DrawTriangle(Vertex2D at1, Vertex2D at2, Vertex2D at3, double r, Pigment argb, DrawType type, int index) {
        graph[index].DrawTriangle(at1, at2, at3, r, argb, type, index);
    }

    @Override
    public void DrawQuad(Vertex2D at1, Vertex2D at2, Vertex2D at3, Vertex2D at4, double r, Pigment argb, DrawType type, int index) {
        graph[index].DrawQuad(at1, at2, at3, at4, r, argb, type, index);
    }

    @Override
    public void DrawText(Vertex2D at, String text, double space, Pigment argb, DrawType type, int index) {
        graph[index].DrawText(at, text, space, argb, type, index);
    }

    @Override
    public Graphical GetGraphic(int index) {
        return graph[index];
    }

    @Override
    public Graphical CurrentGraphic() {
        return graph[index];
    }
}

abstract class GameEngine implements Action, Settings {

    static Graphical DefaultGraphical;
    SetUp setup;
    Game game;
}

/**
 *
 * @author Gerwyn Jones
 */
public class ArcEngine extends GameEngine {

    ArcEngine() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Game.gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        ArcEngine engine = new ArcEngine();
        engine.SetUp();
        engine.active();
        /*
         Accretion.accrete = new Accretion("Test");

         int loops = 1000;
         do {
         //if(running)
         {
         if (Accretion.accrete.Process()) {
         //telescopeData=accrete.FarRadialMapData(1e19);
         //telescopeDataChange=true;
         }
         }
         System.out.println("Processing Loop " + loops);
         loops--;
         } while (loops > 0);
         */
        //NumberSequance res=Maths.TestSine(40,Math.PI);
        //IntegralOperation res=Maths.TestCalc();
        //GImage gi=Accretion.accrete.FarRadialMap(1024,1024, 1e19);
        //GImage.SaveImage("Test", "BMP",gi);
        
        //StellarFiles test=new StellarFiles();
        
        /*test.Add(100);
        test.Add(-101);
        test.Add(+102);
        int array[]={1,2,3,4,5,6,7,8,9,10};
        test.Add(array);
        int map[][]={{1,2,3},{4,5,6},{7,8,9}};
        test.Add(map, 3, 3);
        test.SaveFile("Test.txt");
        test.ClearFile();
        */
         //TessaShape bs = Game.ObjectLoader("Objects\\DEF1.obj", new Vector(0, 0, 0), new Pigment(0, 0, 255), new Pigment(0, 0, 255), new Pigment(0, 0, 255),5);
        
        //TessaShape.Normalize(bs,new Vector(10,10,10),new Vector(1,2,1),new Vector(2,1,2),new Vector(3,1,3));//space vessel
        //TessaShape.NormalScale(bs,new Vector(1,1,1),new Vector(1,2,1),new Vector(2,1,2),new Vector(3,0.5,3),false,true);
        //bs.To(test);
        //test.SaveFile("Test2.txt");
        //test.ClearFile();
        //test.Reset();
        /*System.out.println("LOADING FILE");
        test.LoadFile("Test2.txt",false);
        TessaShape bs=new TessaShape();
        bs.From(test);
        
        test.LoadFile("Test.txt",true);
        test.Reset();
        double d1=test.ReadDouble();
        System.out.println(d1);
        int i1=test.ReadInt();
        System.out.println(i1);
        i1=test.ReadInt();
        System.out.println(i1);
        boolean b1=test.ReadBoolean();
        System.out.println(b1);
        Boolean b2[]=test.ReadBooleanArray();
        System.out.println(b2[0]);
        System.out.println(b2[1]);
        String str=test.ReadString();
        System.out.println(str);
        String str1[]=test.ReadStringArray();
        for(int i=0;i<str1.length;i++){
        System.out.print(str1[i]+",");
        }
        
        Integer ret1[]=test.ReadIntArray();
        for(int i=0;i<ret1.length;i++){
        System.out.print(ret1[i]+",");
        }
        System.out.println();
        
        Double ret2[]=test.ReadDoubleArray();
        for(int i=0;i<ret2.length;i++){
        System.out.print(ret2[i]+",");
        }
        System.out.println();
        Integer imap1[][]=test.ReadIntArrayMap(3,3);
        Integer size[]=test.GetNameData();
        for(int i=0;i<size[0];i++){
        for(int j=0;j<size[1];j++){
        System.out.print(imap1[i][j]+",");
        }
        System.out.print("\n");
        }
        Double imap2[][]=test.ReadDoubleArrayMap(3,3);
        size=test.GetNameData();
        for(int i=0;i<size[0];i++){
        for(int j=0;j<size[1];j++){
        System.out.print(imap2[i][j]+",");
        }
        System.out.print("\n");
        }*/
    }

    @Override
    public void active() {

        try {
            this.game.mainLoop(this);

            /*try {
             Thread.sleep(1);
             } catch (InterruptedException ex) {
             Logger.getLogger(ArcEngine.class.getName()).log(Level.SEVERE, null, ex);
             }*/
        } catch (Exception ex) {
            Logger.getLogger(ArcEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void SetUp() {
        setup = new ArcSetUp(this);
    }

}
