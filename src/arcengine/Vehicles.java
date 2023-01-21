/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Gerwyn Jones
 */
public class Vehicles {

}

class Bike extends Vehicle {

    public Bike(Design d, Pigment color) {
        this.Build(d);
        //System.out.println(this.accelRate);
        this.detector = new CollisionDetector[3];
        int r = 1 + (int) (0.5 * d.size * sqrt2 + d.size);
        float m = mass.Value() / 4.0f;
        this.detector[0] = new CollisionDetector(m, 0, 0, d.size, r);
        this.detector[1] = new CollisionDetector(m + m, 1, 0, 0, r);
        this.detector[2] = new CollisionDetector(m, 2, 0, -d.size, r);
        this.anim = new VehicleImage(this, color);
        weapons = new Weapon[5][this.detector.length];
    }

    void paint(Graphics g, int osx, int osy, int light) {
        //
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        anim.paint(g, x, y, (float) angle.phi, light);
        g.setColor(new Color(128, 128, 128));
        /*for(int d=0;d<this.detector.length;d++){
         g.drawOval((int)(x+this.detector[d].atx-this.detector[d].radius), (int)(y+this.detector[d].aty-this.detector[d].radius), (int)this.detector[d].radius*2, (int)this.detector[d].radius*2);
         } */
    }
}

class Bus extends Vehicle {

    public Bus(Design d, Pigment color) {

        this.Build(d);
        this.CalcEfficency();
        //System.out.println(this.accelRate);
        this.detector = new CollisionDetector[5];
        int r = 1 + (int) (0.5 * d.size * sqrt2 + d.size);

        float m = mass.Value() / 7.0f;
        this.detector[0] = new CollisionDetector(m + m, 0, 0, d.size * 2, r);
        this.detector[1] = new CollisionDetector(m, 1, 0, d.size, r);
        this.detector[2] = new CollisionDetector(m, 2, 0, 0, r);
        this.detector[3] = new CollisionDetector(m, 3, 0, -d.size, r);
        this.detector[4] = new CollisionDetector(m + m, 4, 0, -d.size * 2, r);
        this.anim = new VehicleImage(this, color);
        weapons = new Weapon[5][this.detector.length];
    }

    void paint(Graphics g, int osx, int osy, int light) {
        //
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        anim.paint(g, x, y, (float) angle.phi, light);
        /*g.setColor(new Color(128,128,128));
         for(int d=0;d<this.detector.length;d++){
         g.drawOval((int)(x+this.detector[d].atx-this.detector[d].radius), (int)(y+this.detector[d].aty-this.detector[d].radius), (int)this.detector[d].radius*2, (int)this.detector[d].radius*2);
         } */
    }
}

class Car extends Vehicle {

    Car(Design d, Pigment color) {
        super();
        //System.out.println("OK build");
        this.Build(d);
        //System.out.println("OK built");
        //System.out.println(this.accelRate);
        this.detector = new CollisionDetector[3];
        int r = 1 + (int) (0.5 * d.size * sqrt2 + d.size);
        float m = mass.Value() / 3.0f;
        this.detector[0] = new CollisionDetector(m, 0, 0, d.size, r);
        this.detector[1] = new CollisionDetector(m, 1, 0, 0, r);
        this.detector[2] = new CollisionDetector(m, 2, 0, -d.size, r);
        this.anim = new VehicleImage(this, color);

        weapons = new Weapon[5][this.detector.length];
    }

    void paint(Graphics g, int osx, int osy, int light) {
        //
        /*g.setColor(new Color(128,128,128));
         int x=10;
         int y=20;
         int nx=osx+(int)(atx);
         int ny=osy+(int)(aty);
         int xp[]=new int[]{x,x,-x,-x};
         int yp[]=new int[]{y,-y,-y,y};
         for(int i=0;i<4;i++){    
         int px=(int)(xp[i]*Math.cos(angle)-yp[i]*Math.sin(angle));
         int py=(int)(xp[i]*Math.sin(angle)+yp[i]*Math.cos(angle));
         xp[i]=nx+px;
         yp[i]=ny+py;
         }
         g.fillPolygon(xp,yp,4);
         g.setColor(new Color(64,64,64));
         x=5;
         y=10;
        
         xp=new int[]{x,x,-x,-x};
         yp=new int[]{y,-y*2,-y*2,y};
         for(int i=0;i<4;i++){    
         int px=(int)(xp[i]*Math.cos(angle)-yp[i]*Math.sin(angle));
         int py=(int)(xp[i]*Math.sin(angle)+yp[i]*Math.cos(angle));
         xp[i]=nx+px;
         yp[i]=ny+py;
         }
         g.fillPolygon(xp,yp,4);*/
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        anim.paint(g, x, y, (float) angle.phi, light);
        /*g.setColor(new Color(128,128,128));
         for(int d=0;d<this.detector.length;d++){
         g.drawOval((int)(x+this.detector[d].atx-this.detector[d].radius), (int)(y+this.detector[d].aty-this.detector[d].radius), (int)this.detector[d].radius*2, (int)this.detector[d].radius*2);
         } */
    }
}

class Truck extends Vehicle {

    public Truck(Design d, Pigment color) {
        this.Build(d);
        //System.out.println(this.accelRate);
        this.detector = new CollisionDetector[5];
        int r = 1 + (int) (0.5 * d.size * sqrt2 + d.size);
        float m = this.mass.Value() / 8.0f;
        this.detector[0] = new CollisionDetector(m + m, 0, 0, d.size * 2, r);
        this.detector[1] = new CollisionDetector(m, 1, 0, d.size, r);
        this.detector[2] = new CollisionDetector(m + m, 2, 0, 0, r);
        this.detector[3] = new CollisionDetector(m + m, 3, 0, -d.size, r);
        this.detector[4] = new CollisionDetector(m, 4, 0, -d.size * 2, r);
        this.anim = new VehicleImage(this, color);
        weapons = new Weapon[5][this.detector.length];
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        //
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        anim.paint(g, x, y, (float) angle.phi, light);
        //g.setColor(new Color(128,128,128));
        /*for(int d=0;d<this.detector.length;d++){
         g.drawOval((int)(x+this.detector[d].atx-this.detector[d].radius), (int)(y+this.detector[d].aty-this.detector[d].radius), (int)this.detector[d].radius*2, (int)this.detector[d].radius*2);
         } */
    }
}

class Van extends Vehicle {

    public Van(Design d, Pigment color) {
        this.Build(d);
        //System.out.println(this.accelRate);
        this.detector = new CollisionDetector[4];

        int r = 1 + (int) (0.5 * d.size * sqrt2 + d.size);
        float m = this.mass.Value() / 7.0f;

        this.detector[0] = new CollisionDetector(m, 0, 0, d.size * 1.5f, r);
        this.detector[1] = new CollisionDetector(m + m, 1, 0, d.size / 2, r);
        this.detector[2] = new CollisionDetector(m + m, 2, 0, -d.size / 2, r);
        this.detector[3] = new CollisionDetector(m + m, 3, 0, -d.size * 1.5f, r);
        this.anim = new VehicleImage(this, color);
        weapons = new Weapon[5][this.detector.length];
    }

    void paint(Graphics g, int osx, int osy, int light) {
        //
        int x = (int) (osx + this.x);
        int y = (int) (osy + this.y);
        anim.paint(g, x, y, (float) angle.phi, light);
        /*g.setColor(new Color(128,128,128));
         for(int d=0;d<this.detector.length;d++){
         g.drawOval((int)(x+this.detector[d].atx-this.detector[d].radius), (int)(y+this.detector[d].aty-this.detector[d].radius), (int)this.detector[d].radius*2, (int)this.detector[d].radius*2);
         }*/
    }
}
