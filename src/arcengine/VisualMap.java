/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 *
 * @author Gerwyn Jones
 */
public class VisualMap extends Screen {

    MapSection elements[] = new MapSection[64];
    MapSection ebuffer[] = new MapSection[128];
    int light[] = new int[64];

    VisualMap(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);

    }

    @Override
    public void paintAll(Graphics2D g, double scale) {
    }

    @Override
    public void paint(Graphics2D g, double scale) {
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    @Override
    public void paint(Graphics2D g) {
    }

    @Override
    public void print() {
    }

    @Override
    public void active() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void active(short state) {
    }

    @Override
    public void reset(short state) {
    }

    public void paint(MapElement player) {
        int fromx = (int) (this.origin.x);
        int fromy = (int) (this.origin.y);
        int tox;
        int toy;
        int xc = 0;//(int) (cx - CarRage.playersVehicle.atx);
        int yc = 0;//(int) (cy - CarRage.playersVehicle.aty);
        if (player != null) {
            /*if (CarRage.player.driving) {
             fromx = (int) (CarRage.playersVehicle.at.x * Map.blockScale) - 4;
             fromy = (int) (CarRage.playersVehicle.at.y * Map.blockScale) - 4;
             xc = (int) (cx - CarRage.playersVehicle.at.x);
             yc = (int) (cy - CarRage.playersVehicle.at.y);
             } else {
             fromx = (int) (CarRage.player.at.x * Map.blockScale) - 4;
             fromy = (int) (CarRage.player.at.y * Map.blockScale) - 4;
             xc = (int) (cx - CarRage.player.at.x);
             yc = (int) (cy - CarRage.player.at.y);

             }*/
        } else {
            fromx = (int) (512 * Map.blockScale) - 4;
            fromy = (int) (512 * Map.blockScale) - 4;

            xc = (int) (cx - fromx);
            yc = (int) (cy - fromy);
        }
        tox = fromx + 8;
        toy = fromy + 8;
        if (fromx < 0) {
            fromx = 0;
        } else if (fromx >= Map.square.width) {
            fromx = Map.square.width - 1;
        }
        if (fromy < 0) {
            fromy = 0;
        } else if (fromy >= Map.square.height) {
            fromy = Map.square.height - 1;
        }
        if (tox < 0) {
            tox = 0;
        } else if (tox >= Map.square.width) {
            tox = Map.square.width - 1;
        }
        if (toy < 0) {
            toy = 0;
        } else if (toy >= Map.square.height) {
            toy = Map.square.height - 1;
        }
        //
        bg.setColor(Color.black);
        bg.fillRect(0, 0, frame.GetWidth(), frame.GetHeight());
        //

        int max = 0;
        if (Map.square != null) {
            int xy = 0;
            float xx = -4f;//+((float)(CarRage.playersVehicle.atx * Map.blockScale)-fromx)/4.5f;
            for (int x = fromx; x < tox; x++) {

                float yy = -4f;//+((float)(CarRage.playersVehicle.aty * Map.blockScale)-fromy)/4.5f;
                for (int y = fromy; y < toy; y++) {
                    elements[xy] = Map.square.element[x][y].Get(0);//.paintAll(bg,(int)(cx-CarRage.playersVehicle.atx),(int)(cy-CarRage.playersVehicle.aty));
                    if (elements[xy].contence.size() > max) {
                        max = elements[xy].contence.size();
                    }

                    float l = 0;
                    for (Light light1 : Map.lights) {
                        if (light1 != null) {
                            if (light1.on) {
                                int xl = (int) ((light1.x) * Map.blockScale) - fromx - 4;
                                int yl = (int) ((light1.y) * Map.blockScale) - fromy - 4;
                                int d = (int) (Math.abs(xx + xl) + Math.abs(yy + yl));
                                l += (light1.size - d) * light1.brightness;
                                if (l > 0) {
                                    l += (light1.e - d) * light1.brightness;
                                }
                                light1.energy -= (float) (light1.size + light1.e) * Game.time.AnimTime2();
                                if (light1.energy < 0) {
                                    light1.fuel = false;
                                    light1.on = false;
                                }
                            }
                        }
                    }
                    if (l < 0) {
                        l = 0;
                    }
                    if (l > 9) {
                        l = 9;
                    }
                    light[xy] = (int) (9 - l);
                    xy++;
                    yy++;
                }
                xx++;
            }
            //ebuffer = new MapSection[xy];
            //Location local[] = new Location[xy];
            //int lbuffer[] = new int[xy];
            int w = 0;
            do {
                for (int i = 0; i < xy; i++) {
                    if (w >= elements[i].contence.size()) {
                        continue;
                    }
                    elements[i].contence.get(w).paint(bg, xc, yc, light[i]);
                }
                w++;
            } while (w < max);
            //if(i!=3){
            //System.out.print(("x"+fromx+4)+" y"+(fromy+4));
        }
        bg.setColor(Color.blue);
        bg.drawString(Float.toString((float) Game.time.AnimTime()) + "::" + Float.toString((float) Game.simulation.AnimTime()), 256, 16);

        if (player != null) {
            /* if (CarRage.player.driving) {
             bg.setColor(Color.orange);
             bg.drawString("Force:" + Integer.toString(CarRage.playersVehicle.force), 32, 32);
             bg.drawString("Power:" + Integer.toString(CarRage.playersVehicle.power), 32, 48);
             bg.drawString("Energy:" + Integer.toString(CarRage.playersVehicle.energy), 32, 64);
             bg.drawString("Body:" + Integer.toString(CarRage.playersVehicle.bodyPoints) + " Armour:" + Integer.toString(CarRage.playersVehicle.armourPoints), 32, 80);
             bg.drawString("Gear:" + Float.toString(CarRage.playersVehicle.currentGear.Value()), 32, 96);
             bg.drawString("Speed:" + Float.toString(CarRage.playersVehicle.speed.Value() / 2.5f), 32, 112);

             bg.setColor(Color.yellow);
             bg.drawString("Fuel:" + Float.toString(CarRage.playersVehicle.fuel.Value()), 32, 128);
             bg.drawString("Heading:" + Float.toString(CarRage.playersVehicle.angle.phi * 57), 32, 142);
             //bg.drawString("Speed:"+Float.toString(CarRage.playersVehicle.speed*60), 10,128);
             }
             CarRage.player.PaintSheet(bg);*/
        }
    }

    public void update(Graphics2D g) {

        try {
            //this.paint();
            //Graphics g = frame.getGraphics();
            Pigment p = null;
            Pigment l = new Pigment(255, 0, 0, 0);

            if (g != null && this.buffer != null) {
                /*
                 * for(int x=0;x<this.buffer.getWidth();x++){ for(int
                 * y=0;y<this.buffer.getHeight();y++){ int
                 * rgb=this.buffer.getRGB(x, y); int red=(rgb&0x00FF0000)>>16;
                 * int green=(rgb&0x0000FF00)>>8; int blue=(rgb&0x000000FF);
                 * p=new Pigment(255,red,green,blue); p.Average(l);
                 * this.buffer.setRGB(x, y, p.ToRGB()); } }
                 */
                g.drawImage(buffer, 0, 0, null);
                //java.awt.Toolkit.getDefaultToolkit().sync();
                g.dispose();
            } else {
                System.out.println("Update Error");
            }
        } catch (Exception ex) {
            System.out.println("Error in Update ");

        }

    }

    @Override
    public void Update(GameTime time) {
    }

    @Override
    public void Choice(MenuChoice c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
