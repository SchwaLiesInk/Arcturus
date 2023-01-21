/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 *
 * @author Gerwyn Jones
 */
public class TacticalMap extends Screen {

    MapButton enabled;
    MapButton dome;
    MapButton farm;
    MapButton city;
    MapButton mine;
    MapButton factory;
    MapButton citadel;
    MapButton port;
    MapButton Economy;
    MapButton Environment;
    MultiKeyPressListener multiKey;
    LandSection elements[] = new LandSection[262144];
    LandSection ebuffer[] = new LandSection[262144];
    float scale = 1.0f;
    boolean shift;

    TacticalMap(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);

        Economy = new DataMapButton("Econ", 6, 8, 64, 32, new Color(128, 0, 0, 64));
        dome = new BuildMapButton("Dome", 74, 8, 64, 32);
        farm = new BuildMapButton("Farm", 142, 8, 64, 32);
        city = new BuildMapButton("City", 210, 8, 64, 32);
        mine = new BuildMapButton("Mine", 278, 8, 64, 32);
        factory = new BuildMapButton("Factory", 346, 8, 64, 32);
        citadel = new BuildMapButton("Citadel", 414, 8, 64, 32);
        port = new BuildMapButton("Port", 482, 8, 64, 32);
        Environment = new DataMapButton("Enviro", 550, 8, 64, 32, new Color(0, 128, 0, 64));
        buttons.add(Environment);
        buttons.add(dome);
        buttons.add(farm);
        buttons.add(city);
        buttons.add(mine);
        buttons.add(factory);
        buttons.add(citadel);
        buttons.add(port);
        buttons.add(Economy);

    }

    void paint() {
        bg.setColor(new Color(64, 64, 64));
        bg.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        int fromx = (int) (Game.board.atx * LandMap.blockScale) - (int) (32 / scale);
        int fromy = (int) (Game.board.aty * LandMap.blockScale) - (int) (32 / scale);
        int tox = fromx + (int) (64 / scale);
        int toy = fromy + (int) (64 / scale);
        if (fromx < 0) {
            fromx = 0;
        } else if (fromx >= Game.board.map.width) {
            fromx = Game.board.map.width - 1;
        }
        if (fromy < 0) {
            fromy = 0;
        } else if (fromy >= Game.board.map.height) {
            fromy = Game.board.map.height - 1;
        }
        if (tox < 0) {
            tox = 0;
        } else if (tox >= Game.board.map.width) {
            tox = Game.board.map.width - 1;
        }
        if (toy < 0) {
            toy = 0;
        } else if (toy >= Game.board.map.height) {
            toy = Game.board.map.height - 1;
        }
        //
        //bg.setColor(Color.black);
        //bg.fillRect(0,0,this.getWidth(),this.getHeight()); 
        //
        int xc = (int) (cx - Game.board.atx * scale);
        int yc = (int) (cy - Game.board.aty * scale);

        elements = new LandSection[262144];
        if (Game.board != null) {
            int xy = 0;
            for (int x = fromx; x < tox; x++) {
                for (int y = fromy; y < toy; y++) {
                    elements[xy] = Game.board.map.sections[x][y];//.paintAll(bg,(int)(cx-CarRage.playersVehicle.atx),(int)(cy-CarRage.playersVehicle.aty));
                    xy++;
                }
            }
            ebuffer = new LandSection[xy];
            int z;
            do {
                z = 0;
                for (int i = 0; i < xy; i++) {
                    elements[i].contains.paint(bg, xc, yc, scale);
                    if (elements[i].next != null) {
                        if (elements[i].contains != null) {
                            ebuffer[z] = elements[i].next;
                            z++;
                        }
                    }
                }
                xy = z;
                elements = ebuffer;
                ebuffer = new LandSection[z];
                //System.out.println("z"+z);
            } while (z > 0);
            //if(i!=3){
        }
        bg.setColor(Color.black);
        bg.drawString("Year:" + Game.time.Year(), 31, 63);
        bg.drawString("Population:", 31, 73);
        bg.drawString("Assets:", 31, 83);
        bg.setColor(Color.white);
        bg.drawString("Year:" + Game.time.Year(), 30, 62);
        bg.setColor(Color.green);
        bg.drawString("Population:", 30, 72);
        bg.setColor(Color.yellow);
        bg.drawString("Assets:", 30, 82);
        for (int i = 0; i < this.buttons.size(); i++) {
            MapButton but = (MapButton) buttons.get(i);
            but.paint(bg);
        }
    }

    public void update(Graphics2D g) {
        try {
            //this.paint();
            //this.paintComponent(bg);
            this.paint();
            //Graphics g = frame.getGraphics();
            if (g != null && this.buffer != null) {
                g.drawImage(buffer, 0, 20, null);
                //g.dispose();
                //g=this.getGraphics();
                //this.jPanel1.paint(g);
                g.dispose();
                //this.invalidate();
            } else {
                System.out.println("Update Error");
            }
        } catch (Exception ex) {
            System.out.println("Error in Update ");
            ex.printStackTrace();

        }
    }

    void Keys() {
        try {
            //System.out.println(("ax"+Dominion.board.atx)+" ay"+Dominion.board.aty);
            //System.out.println(("fx"+fromx)+" fy"+(fromy));
            //System.out.println(("tx"+tox)+" ty"+(toy));
            Object keys[] = this.multiKey.pressed.toArray();
            for (int i = 0; i < this.multiKey.pressed.size(); i++) {
                Integer key = (int) keys[i];
                if (key == KeyEvent.VK_SPACE) {
                } else if (key == KeyEvent.VK_PAGE_UP) {
                    scale += 0.03125f;
                    if (scale > LandMap.blockCenterInPixels) {
                        scale = LandMap.blockCenterInPixels;
                    }
                } else if (key == KeyEvent.VK_PAGE_DOWN) {
                    scale -= 0.03125f;
                    if (scale < LandMap.blockScale * 4) {
                        scale = (float) LandMap.blockScale * 4;
                    }
                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    Game.board.aty -= 1.0f / (scale * scale);

                    if (shift) {
                        Game.board.aty -= 2.0f / (scale * scale);

                    }
                    if (Game.board.aty < 0) {
                        Game.board.aty = 0;
                    }
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    Game.board.aty += 1.0f / (scale * scale);
                    if (shift) {
                        Game.board.aty += 2.0f / (scale * scale);
                    }
                    if (Game.board.aty > Game.board.map.height * LandMap.blockSizeInPixels) {
                        Game.board.aty = Game.board.map.height * LandMap.blockSizeInPixels;
                    }
                } else //
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                    Game.board.atx -= 1.0f / (scale * scale);
                    if (shift) {
                        Game.board.map.RotateLeft();
                    }
                    if (Game.board.atx < 0) {
                        Game.board.map.RotateLeft();
                        Game.board.atx = 0;
                    }
                } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                    Game.board.atx += 1.0f / (scale * scale);
                    if (shift) {
                        Game.board.map.RotateRight();
                    }
                    if (Game.board.atx > Game.board.map.width * LandMap.blockSizeInPixels) {
                        Game.board.map.RotateRight();
                        Game.board.atx = Game.board.map.width * LandMap.blockSizeInPixels;
                    }

                } else //
                if (key == KeyEvent.VK_1) {
                } else if (key == KeyEvent.VK_2) {
                } else if (key == KeyEvent.VK_3) {
                } else if (key == KeyEvent.VK_4) {
                } else if (key == KeyEvent.VK_5) {
                } else if (key == KeyEvent.VK_6) {
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            this.multiKey.pressed.clear();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < this.buttons.size(); i++) {
            MapButton but = (MapButton) buttons.get(i);
            if (but.Focus(e.getX(), e.getY())) {
                but.inFocus = true;
                if (e.getButton() == e.BUTTON1) {
                    for (int j = 0; j < this.buttons.size(); j++) {
                        MapButton b = (MapButton) buttons.get(j);
                        b.On = false;
                        b.inFocus = false;
                    }
                    but.On = true;
                    but.OnAction();

                }
            } else {
                but.inFocus = false;

            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for (int i = 0; i < this.buttons.size(); i++) {
            MapButton but = (MapButton) buttons.get(i);
            if (but.Focus(e.getX(), e.getY())) {
                but.inFocus = true;
                but.FocusAction();
            } else {
                but.inFocus = false;

            }
        }
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

    @Override
    public void paintAll(Graphics2D g, double scale) {
    }

    @Override
    public void paint(Graphics2D g, double scale) {
    }

    @Override
    public void Choice(MenuChoice c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(GameTime time) {
    }
}
