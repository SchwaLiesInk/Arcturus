/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


class Hud {

    void PaintStats(Graphics2D g, int x, int y,Color col,Fighter c) {
        g.setColor(Color.blue);
        g.fillRect(x, y, c.armour*8,16);        
        g.setColor(col);
        g.drawRect(x, y, c.armour*8,16);        
        g.drawString("ARMOUR["+c.armour+"]", x-14, y);
        y+=28;
        g.setColor(Color.cyan);
        g.fillRect(x, y, (c.hitPoints>>3),16); 
        g.setColor(col);
        g.drawRect(x, y, (c.hitPoints>>3),16);        
        g.drawString("BODY["+c.hitPoints+"]", x-14, y);
        y+=28;
        if(c.forcefield!=null){
        g.setColor(Color.green);
        g.fillRect(x, y,(int)(c.forcefield.powerLeft),16);        
        g.setColor(col);
        g.drawRect(x, y,(int)(c.forcefield.powerLeft),16);        
        g.drawString("FORCE["+(int)(c.forcefield.powerLeft)+"]", x-14, y);
        y+=28;
        }
        g.setColor(Color.yellow);
        g.fillRect(x, y,c.missiles>>1,16);        
        g.setColor(col);
        g.drawRect(x, y,c.missiles>>1,16);        
        g.drawString("MISSILES["+c.missiles+"]", x-14, y);
        y+=28;
        g.setColor(Color.orange);
        g.fillRect(x, y,c.power>>2,16);        
        g.setColor(col);
        g.drawRect(x, y,c.power>>2,16);        
        g.drawString("POWER["+c.power+"]", x-14, y);
        y+=28;
        g.setColor(Color.red);
        g.fillRect(x, y,c.bombs*8,16);        
        g.setColor(col);
        g.drawRect(x, y,c.bombs*8,16);        
        g.drawString("BOMBS["+c.bombs+"]", x-14, y);
        y+=28;
        if(c.jet!=null){
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y,c.jet.powerLeft,16);        
        g.setColor(col);
        g.drawRect(x, y,c.jet.powerLeft,16);        
        g.drawString("BOOSTER", x-14, y);
        y+=28;
        }
        
        
    }
    void PaintCompass(Graphics2D g, int x, int y,Color col, double angle,Fighter c,double gmt,double arc) {
        
        g.setColor(Color.gray);
        g.fillRect(x, y-16, 134,36);
        g.setColor(Color.black);
        g.drawRect(x, y-16, 134,36);
        g.setColor(Color.white);
        int i=0;
        for(double a=angle;a<angle+Linear.PI2;a+=(Linear.PI2/12.0)){
            int ax=x+(int)(64-Math.cos(a)*64);
            if(i%3==0){
            g.drawString(" | ", ax, y);
            
            }else{
            g.drawString(" - ", ax, y);
            }    
            i++;
        }
        g.setColor(Color.black);
        //angle += Math.PI * 0.5;
        if (angle > 0) {
            double xss = Math.cos((angle) + Math.PI);
            int s = 64-(int) (xss * 64);
            g.drawString("S", (int) (x + s), y);
        } else {
            double xsn =  Math.cos((angle));
            int n = 64-(int) (xsn * 64);
            g.drawString("N", (int) (x + n), y);
        }
        if (angle < -Math.PI*0.5 || angle > Math.PI*0.5) {
            double xsw = Math.cos((angle) + Math.PI * 0.5);
            int w = 64-(int) (xsw * 64);
            g.drawString("W", (int) (x + w), y);
        } else {
            double xse = Math.cos((angle) - Math.PI * 0.5);
            int e = 64-(int) (xse * 64);
            g.drawString("E", (int) (x + e), y);
        }
        //
        g.setColor(Color.white);
        g.drawString("[      ]", (int) (x + 56), y);
        g.drawString("["+(int)((Math.PI+angle)*57.29578)+"]", (int) (x + 56), y+16);
        g.setColor(col);
        g.drawString("LONG " + (c.local.x - LandScape.ScapeMiddle) + " LAT" + (c.local.y - LandScape.ScapeMiddle), x, y + 32);
        g.drawString("GMT " + (int)(gmt) + " :" + (int)((gmt-(int)(gmt))*60) +" ArcSec "+(int)(arc*60), x, y + 48);
        g.setColor(Color.red);
        float e=(float)Math.log(1+c.fieldEffect*1e5);
        g.fillRect(x, y+48, (int)e,16);        
        g.setColor(col);
        g.drawRect(x, y+48,(int)e,16);        
        g.drawString("FIELDS["+(float)e+"]", x, y+76);
        
    }

}

/**
 *
 * @author Gerwyn Jones
 */
public class ScapeScreen extends ScopeScreen {

    //RenderBuffer buffer;
    LandScape scape;
    Vector loc = new Vector(0, 0, 0);
    SkyScape sky = new SkyScape();
    Hud hud = new Hud();

    ScapeScreen(Activator f, int x, int y, int w, int h, String name, Telescope tele) {
        super(f, x, y, w, h, name);
        this.scope=tele;//essential
        //buffer = new RenderBuffer(this);
        //buffer.render = this::Render;
        this.sky.SetUp(scope.home);
        scape = new LandScape(this, new LoadFrame());
        cam = new Camera(1 * scape.quadR, 150, 1 * scape.quadR, Viewer.Internal, this);
        //cam = new Camera(0, 150, -1, Viewer.Internal, this);
        cam.zoom = 1;
        Game.time.CalcTimes();
        Game.time.CalcTimes();
    }

    @Override
    public void Update(GameTime time) {
        this.scape.fighter.SetControls(time);
        for (Integer k : frame.KeyBoard().pressed) {
            switch (k) {
                case KeyEvent.VK_CONTROL: {
                    this.scape.fighter.sense *= 0.1;
                    break;
                }
                case KeyEvent.VK_W: {
                    scape.fighter.Command(FighterCommands.Forward);
                    break;
                }
                case KeyEvent.VK_S: {
                    scape.fighter.Command(FighterCommands.Back);
                    break;
                }
                case KeyEvent.VK_A: {
                    scape.fighter.Command(FighterCommands.LeftSide);
                    break;
                }
                case KeyEvent.VK_D: {
                    scape.fighter.Command(FighterCommands.RightSide);
                    break;
                }
                case KeyEvent.VK_EQUALS: {
                    scape.fighter.Command(FighterCommands.Up);

                    break;
                }
                case KeyEvent.VK_MINUS: {
                    scape.fighter.Command(FighterCommands.Down);
                    break;
                }
                case KeyEvent.VK_UP: {
                    scape.fighter.Command(FighterCommands.TiltUp);
                    break;
                }
                case KeyEvent.VK_DOWN: {

                    scape.fighter.Command(FighterCommands.TiltDown);

                    break;
                }
                case KeyEvent.VK_LEFT: {

                    scape.fighter.Command(FighterCommands.TurnLeft);
                    //cam.angle.x -= ax=sense;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    scape.fighter.Command(FighterCommands.TurnRight);
                    //cam.angle.x += ax=sense;
                    break;
                }
                case KeyEvent.VK_SPACE: {
                    scape.fighter.Command(FighterCommands.FireMain);
                    //cam.angle.x += ax=sense;
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    scape.fighter.Command(FighterCommands.FireAuxilary);
                    //cam.angle.x += ax=sense;
                    break;
                }
                case KeyEvent.VK_R: {
                    scape.fighter.Command(FighterCommands.FireJets);
                    //cam.angle.x += ax=sense;
                    break;
                }
                case KeyEvent.VK_Q: {
                    scape.fighter.Command(FighterCommands.EvasionLeft);
                    //cam.angle.x += ax=sense;
                    break;
                }
                case KeyEvent.VK_E: {
                    scape.fighter.Command(FighterCommands.EvasionRight);
                    //cam.angle.x += ax=sense;
                    break;
                }
            }

        }
        scape.fighter.Update(time, cam);
        this.UpdateCamera((float) time.cameraTime, scape.fighter.direct.AsVector(), new Normal(0, 1, 0));
        this.cam.SetLocal(scape.quadR);
        sky.Update(time, cam,scope.home.day);

        if (sky.night < 0) {
            if (Math.cos(sky.moonAngley) > 0) {
                ambientLight = new Cartesian((double) (sky.skyColor.getRed()) / 256.0, (double) (sky.skyColor.getGreen()) / 256.0, (double) (sky.skyColor.getBlue()) / 256.0);
            } else {
                ambientLight = new Cartesian(0, 0, 0);
            }
            sky.sunshade = 1.0 / (8.0 + (int) (Math.abs(sky.night) * 256));
            fog = (-sky.arc * 0.125 + sky.night * sky.arc * 0.25);
        } else {

            ambientLight = new Cartesian((double) (sky.skyColor.getRed() + sky.sunColor.getRed()) / 512.0, (double) (sky.skyColor.getGreen() + sky.sunColor.getGreen()) / 512.0, (double) (sky.skyColor.getBlue() + sky.sunColor.getBlue()) / 512.0);
            fog = (sky.arc * 3.0 - sky.night * sky.arc * 2.0);
        }
        cam.linear = null;//new Matrix(loc);
        //buffer.active();
        this.scape.Update(time);

        Render();
        ZSort();
        if (unsortedList != null) {
            //System.out.println("Transfer"+unsortedList.length);
            sortedList = new Drawable[unsortedList.length];
            for (int i = 0; i < unsortedList.length; i++) {
                sortedList[i] = unsortedList[i].data;
            }
            unsortedList = null;
            //Game.exec.execute(this::ZSort);
        }
        //Game.exec.execute(this::ZSort);

        //Game.exec.execute(this::GC);
    }

    public void Render() {

        //if (buffer.Ready()) {
        //buffer.Begin();
        sky.Render(this,this.scape.fighter);
        scape.Render();
        //buffer.End();
        //}
    }

    void DrawSky(Graphics2D g) {

        int sections = 64;
        double scale = ((Linear.PID2 + cam.angle.y) / sky.light);
        double red = sky.sunColor.getRed() * sky.sunarc;
        double green = sky.sunColor.getGreen() * sky.sunarc;
        double blue = sky.sunColor.getBlue() * sky.sunarc;
        double bright = 256.0 * sky.sunLight.brightness;
        for (int i = 1; i < sections; i++) {
            double grade = bright / (scale / ((1.0 + (double) (i) / sections)));
            if (grade < sky.ambient) {
                grade = sky.ambient;
            }
            if (grade > 255 - sky.ambient) {
                grade = 255 - sky.ambient;
            }
            if (grade < 0) {
                grade = 0;
            } else if (grade > 255) {
                grade = 255;
            }
            int rd = (int) ((grade * 4 + red * grade) * sky.sunshade);
            if (rd > 255) {
                rd = 255;
            }
            int gr = (int) ((grade * 8 + green * grade) * sky.sunshade);
            if (gr > 255) {
                gr = 255;
            }
            int bu = (int) ((grade * 16 + blue * grade) * sky.sunshade);
            if (bu > 255) {
                bu = 255;
            }
            sky.skyColor = new Color(rd, gr, bu, 255);
            g.setColor(sky.skyColor);
            g.fillRect(0, ((i - 1) * frame.GetHeight()) / sections, frame.GetWidth(), frame.GetHeight() / i);
        }
        if (sky.night > 0) {
            Point sunPoint = this.GetScreenLocation(sky.sun, null, 1, sky.sun.r + Math.abs(sky.sun.r * Math.cos(sky.sunAngley)));

            if (sunPoint != null) {
                g.setColor(sky.sunColor);
                g.fillOval((int) (sunPoint.x - sunPoint.r), (int) (sunPoint.y - sunPoint.r), (int) (sunPoint.r * 2), (int) (sunPoint.r * 2));
                g.setColor(new Color(255, 255, 255, 32));
                g.fillOval((int) (sunPoint.x - sunPoint.r * 1.5), (int) (sunPoint.y - sunPoint.r * 1.5), (int) (sunPoint.r * 3), (int) (sunPoint.r * 3));
                g.setColor(new Color(255, 255, 255, 32));
                g.fillOval((int) (sunPoint.x - sunPoint.r * 2), (int) (sunPoint.y - sunPoint.r * 2), (int) (sunPoint.r * 4), (int) (sunPoint.r * 4));
                //System.out.println(sunPoint.x+" "+sunPoint.y+" "+sunPoint.r );
            }
        }
        /*if (sky.asscention > 0) {
            Point moonPoint = this.GetScreenLocation(sky.moon, null, 1, sky.moon.r + Math.abs(sky.moon.r * Math.cos(sky.moonAngley)));
            if (moonPoint != null) {
                g.setColor(sky.moonColor);
                g.fillOval((int) (moonPoint.x - moonPoint.r), (int) (moonPoint.y - moonPoint.r), (int) (moonPoint.r * 2), (int) (moonPoint.r * 2));
                g.setColor(new Color(128, 196, 255, 32));
                g.fillOval((int) (moonPoint.x - moonPoint.r * 1.5), (int) (moonPoint.y - moonPoint.r * 1.5), (int) (moonPoint.r * 3), (int) (moonPoint.r * 3));
                //System.out.println(sunPoint.x+" "+sunPoint.y+" "+sunPoint.r );
            }
        }*/
    }

    @Override
    public void paint(Graphics2D g, double scale) {
        DrawSky(g);
        /*this.bg=buffer.createGraphics();
         bg.drawLine(mouseX, mouseY, mouseX, mouseY);
         g.drawImage(buffer,0,0,null);        
         bg.dispose();*/
        //g.setFont(frame.getFont());

        //buffer.Draw3D();
        if (sortedList != null) {
            DrawSortedList(sortedList, graphical);
        }
        //this.renderList.Clear();
        Color negSky=new Color(255 - sky.skyColor.getRed(), 255 - sky.skyColor.getGreen(), 255 - sky.skyColor.getBlue());
        g.setColor(negSky);
        g.drawString("X" + this.mouseX + " Y" + this.mouseY +" Time " + Game.time.cameraTime+" FPS "+Game.time.fps, (float) (origin.x), 1.0f + (float) (origin.y) + 16);
        g.drawString("FromX" + this.origin.x + " FromY" + this.origin.y, (float) (origin.x), 1.0f + (float) (origin.y) + 32);
        g.drawString("ToX" + (int) (this.origin.x + this.size.x) + " ToY" + (int) (this.origin.y + this.size.y), (float) (origin.x), 1.0f + (float) (origin.y) + 48);
        g.drawString("CAM X" + this.cam.x + " Y" + cam.y + " Z" + cam.z, (float) (origin.x), 1.0f + (float) (origin.y) + 64);
        g.drawString("ANG X" + this.cam.angle.x + " Y" + cam.angle.y + " Z" + cam.angle.z, (float) (origin.x), 1.0f + (float) (origin.y) + 78);
        g.drawString("AT X" + this.scape.fighter.x + " Y" + scape.fighter.y + " Z" + scape.fighter.z, (float) (origin.x), 1.0f + (float) (origin.y) + 128);
        //
        g.setColor(new Color(128,64,64,64));
        g.fillRect(((int) (edge.x)>>1)-(scape.map.Width()>>1)-288, (int)edge.y-scape.map.Height()-65, (int)(scape.map.Width()+576) ,scape.map.Height()+2);
        g.setColor(Color.black);
        g.drawRect(((int) (edge.x)>>1)-(scape.map.Width()>>1)-288, (int)edge.y-scape.map.Height()-65, (int)(scape.map.Width()+576) ,scape.map.Height()+2);
        g.drawImage(scape.map.bits,( (int) (edge.x)>>1) - (scape.map.Width()>>1), (int) (edge.y) - scape.map.Height()-64, null);
        g.setColor(negSky);
        g.drawOval(((int) (edge.x)>>1) + (this.scape.fighter.local.x - LandScape.ScapeMiddle) - scape.lod, (int) (edge.y) - (scape.map.Height()>>1)-64 + (this.scape.fighter.local.y - LandScape.ScapeMiddle) - scape.lod, scape.lod << 1, scape.lod << 1);
        hud.PaintCompass(g, ((int) (edge.x)>>1) + (scape.map.Width()>>1)+128, (int) (edge.y) - 270,negSky, this.cam.angle.x,this.scape.fighter,this.sky.gmt,this.scope.home.day/24.0);
        hud.PaintStats(g,  ((int) (edge.x)>>1)-(scape.map.Width()>>1)-256, (int) (edge.y) - 270,negSky,this.scape.fighter);
    }

    void GC() {
        System.gc();
    }
}
