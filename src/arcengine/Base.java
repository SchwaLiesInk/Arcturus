/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;

class SkyBomb extends GameObject implements GraphicsUpdate {

    SkyBody explosion;
    Adder red = new Adder(0, 255, 1);
    Adder green = new Adder(0, 255, 1);
    Adder blue = new Adder(0, 255, 1);

    SkyBomb(Point at) {
        explosion = new SkyBody(new Light(at, new Pigment(255, 255, 255), new Pigment(255, 255, 255)), 0);
    }

    @Override
    public Point Hit(Ray field, double tr, int damage) {
        return this;
    }

    @Override
    void PaintData(Graphics2D g, int x, int y) {

    }

    @Override
    public void Update(GameTime time, Camera cam) {

    }

}

class ForceField extends Component implements GraphicsUpdate, CollisionUpdate {

    float power;
    float powerLeft;
    double relativistic;
    GameObject user;

    ForceField(GameObject user, Tech tech, int size) {
        super(tech);
        this.user = user;
        r=(size*size*tech.level);
        this.radius = new Length((float)r);
        this.mass = new Mass(user, (float) (size) / (float) (tech.level), this.radius.Value());
        this.speed = new Speed((float) (size * tech.level * tech.level));
        this.v = new Velocity(new Vector());
        powerLeft = power = (float)(size * tech.level);
        //System.out.println("power "+power+" speed ");
    }

    @Override
    public Point Hit(Ray field, double tr, int damage) {
        if (powerLeft > 0) {
            Point fp = field.from.Sub(this, field.from).AsPoint(this.radius.Value());
            double len = fp.Length();
            //System.out.println(len);
            if (len > 0 ) {
                Vector np = field.direction.Negative().AsVector();
                this.v.v = np.Scaled((this.radius.Value()) / len * speed.Value() * (double)(powerLeft) / (tr * tr) * this.relativistic).AsVector();
                double vel=this.v.v.Length();
                powerLeft-=(mass.Value()*vel*r)/(Game.time.cameraTime*Game.time.cameraTime*3e8);
                if(powerLeft<0){powerLeft=0;}
                return v.v.AsPoint(r);
            }
        }
        return null;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        if (powerLeft < power) {
            powerLeft += ((1+powerLeft)*0.125f*time.cameraTime);
        }
        relativistic = time.cameraTime*time.cameraTime*time.cameraTime;
        this.x = user.x;
        this.y = user.y;
        this.z = user.z;
        //System.out.println("r"+r);
    }
}

/**
 *
 * @author Gerwyn Jones
 */
public class Base extends GameObject implements GraphicsUpdate, CollisionUpdate {

    int hitPoints = 2000;
    int armour = 9;
    TessaShape shape;
    boolean dispose = false;
    RingList<ForceField> forcefields = new RingList();

    Base(Point at, TessaShape shape, Vector scale, Vector angles) {
        super();
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.shape = shape;
        if (angles != null) {
            Quarternion q1 = new Quarternion(angles.x, new Normal(1, 0, 0));
            Quarternion q2 = new Quarternion(angles.z, new Normal(0, 0, 1));
            Quarternion q3 = q1.Mult(q1, q2);
            TessaShape.Transform(this.shape, q3);
        }
        if (scale != null) {
            Matrix m = new Matrix(new Vector(0, 0, 0), scale);
            TessaShape.Transform(this.shape, m);
            if (shape != null) {
                mass = new Mass(at, (float) scale.Length(), (float) shape.sphere.radius);
                this.r = shape.sphere.radius;
            } else {
                mass = new Mass(at, (float) scale.Length(), 1);
                this.r = 1;
            }
        } else {
            if (shape != null) {
                mass = new Mass(at, 1, (float) shape.sphere.radius);
                this.r = shape.sphere.radius;
            } else {
                mass = new Mass(at, 1, 1);
                this.r = 1;
            }
        }
        this.bodyPoints = this.hitPoints;
        //System.out.println("Base Radius " + this.r);
        SetLocal();
    }

    void SetLocal() {
        double lim = LandScape.ScapeMiddle * LandScape.quadR;
        while (z > lim) {
            z = lim - (z - lim);
            x += lim;
        }
        while (z < -lim) {
            z = -lim - (lim + z);
            x += lim;
            //System.out.println(z);
        }
        while (x > lim) {
            x -= lim * 2;
        }
        while (x < -lim) {
            x += lim * 2;
        }

        int i = (int) ((x + LandScape.quadR * 0.5) / LandScape.quadR);
        int j = (int) ((z + LandScape.quadR * 0.5) / LandScape.quadR);
        int x1 = Math.abs(LandScape.ScapeMiddle + i) % LandScape.ScapeSize;
        int y1 = (LandScape.ScapeMiddle + j);
        /*while (y1 < 0) {
         y1 = Math.abs(y1);
         x1 += LandScape.ScapeMiddle;
         x1 %= LandScape.ScapeSize;
         }
         while (y1 >= LandScape.ScapeSize) {
         y1 = LandScape.ScapeSize-Math.abs(y1 - LandScape.ScapeSize);
         x1 += LandScape.ScapeMiddle;
         x1 %= LandScape.ScapeSize;
         }*/

        this.local = new Local(x1, y1);

    }

    void AddForceField(ForceField f) {
        this.forcefields.Append(f);
    }

    @Override
    public Point Hit(Ray field, double tr, int damage) {
        damage -= armour;

        if (damage > 0) {
            Node<ForceField> f = this.forcefields.Start();
            for (; f.data != null; f = f.next) {
                damage -= f.data.powerLeft;
                if (damage > 0) {
                    f.data.powerLeft -= damage;
                    if (f.data.powerLeft < 0) {
                        f.data.powerLeft = 0;
                    }
                }
            }
            hitPoints -= (damage);
        }
        if (hitPoints < 0) {
            this.dispose = true;
            shape = new TessaShape(shape);
            this.particles.Append(new Explosion(this, 20, this.bodyPoints >> 1, new Pigment(32, 256, 128, 64), this.bodyPoints));
            this.particles.Append(new Smoke(this, 10, this.bodyPoints, new Pigment(32, 32, 32, 32), this.bodyPoints >> 3));
        }

        //System.out.println("DISPOSED" + this.dispose + " " + this.hitPoints);
        return field.from.AsPoint(r + tr);
    }

    @Override
    void PaintData(Graphics2D g, int x, int y) {

    }

    boolean Dispose() {
        return dispose;
    }

    void Render(LandScape scape, double curve, RingList<Light> suns, double emissive1) {
        boolean ok = scape.screen.LoadVertexTriangle(new Point(this.mass.center.x, this.mass.center.y + curve, this.mass.center.z, this.mass.center.r), this.shape.list, 1.0, this.shape.sphere.radius, scape.screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive1);
        if (ok) {
            this.RenderParticles(scape.screen);
        }
        if (ok && this.forcefields.Length() > 0) {
            Point at = new Point();
            Node<ForceField> f = this.forcefields.Start();
            for (; f.data != null; f = f.next) {
                scape.screen.LoadVertexPoint(f.data.AsPoint(this.r * 2), new Pigment(32, 0, 0, 255), null, 1.0, 1.0, scape.screen.renderList, DrawType.Circle, DrawType.None, at, 0);
            }
        }
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        if (this.dispose) {
            this.r *= 0.99;
            this.y *= 0.9;
            TessaShape.Explode(shape, (double) (this.bodyPoints) * 0.002, false);

        } else {
            if (this.forcefields.Length() > 0) {
                Node<ForceField> f = this.forcefields.Start();
                for (; f.data != null; f = f.next) {
                    f.data.Update(time, cam);
                }
            }
        }
        UpdateParticles(time, cam);
        RemoveParticles();
    }
}

class HumanCity extends Base {

    TessaShape[] buildings;
    byte shapes[][] = new byte[32][32];
    boolean destroy[][] = new boolean[32][32];
    Point points[][] = new Point[32][32];
    BoundingBox bb[][] = new BoundingBox[32][32];

    HumanCity(Point at, Molecula rnd, long pop, TessaShape[] build) {
        super(at, null, null, null);
        boolean full = false;
        buildings = build;
        int site = (int) (pop / 64);
        while (pop > 0) {
            int xr = (rnd.NextInt(32) + rnd.NextInt(32)) >> 1;
            int yr = (rnd.NextInt(32) + rnd.NextInt(32)) >> 1;
            shapes[xr][yr] += (byte) (1 + rnd.NextInt(8));
            if (shapes[xr][yr] > 63) {
                shapes[xr][yr] = 63;
                return;
            }
            pop -= site;

            //System.out.print(pop);
        }

    }

    @Override
    public Point Hit(Ray field, double tr, int damage) {
        double d = Linear.MAX_MAP;
        int id = -1;
        int jd = -1;
        Point pos = new Point(field.from.x, field.from.y, field.from.z, tr);
        //System.out.println("Hit Directed At "+field.from.x+" "+field.from.y+" "+field.from.z);
        //System.out.println("Hit Location "+this.x+" "+this.y+" "+this.z+" radius "+this.r);
        //Hit t=new Hit();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (shapes[i][j] != 0) {
                    //double rd=(points[i][j].r+tr)*2;
                    double t = pos.Distance(points[i][j].AsVector());//-rd;
                    if (t < d) {
                        //System.out.println("point " +points[i][j].x+" "+points[i][j].y+" "+points[i][j].z+" "+points[i][j].r);
                        //System.out.println("at " +t.at.x+" "+t.at.y+" "+t.at.z+" "+t.at.r);
                        //if(t.time<d){
                        d = t;
                        id = i;
                        jd = j;
                        //}
                    }
                }
            }
        }
        if (id >= 0) {
            damage -= this.armour;
            Node<ForceField> f = this.forcefields.Start();
            for (; f.data != null; f = f.next) {
                damage -= f.data.powerLeft;
                if (damage > 0) {
                    f.data.powerLeft -= damage;
                    if (f.data.powerLeft < 0) {
                        f.data.powerLeft = 0;
                    }
                }
            }
            if (damage > 0) {
                //System.out.println("Hit Target "+id+" "+jd+" x"+pos.x+" y"+pos.y+" z"+pos.z);
                if (damage > shapes[id][jd]) {
                    destroy[id][jd] = true;
                    shapes[id][jd]--;
                } else {
                    if (shapes[id][jd] > 0) {
                        shapes[id][jd] -= damage;
                    }
                }
                if (shapes[id][jd] < 0) {
                    shapes[id][jd] = 0;
                    destroy[id][jd] = false;
                }
            }
            //System.out.println("Hit "+id+" "+jd+" at "+points[id][jd].x+" "+points[id][jd].y+" "+points[id][jd].z+" "+points[id][jd].r);
            return points[id][jd].AsPoint(points[id][jd].r);
        }
        return null;
    }

    @Override
    public void Update(GameTime time, Camera cam) {

        int rx = -32;
        int ry = -32;
        int rd = 0;
        int allDestroyed = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (shapes[i][j] > 0) {
                    if (destroy[i][j]) {
                        shapes[i][j]--;
                        if (shapes[i][j] < 0) {
                            shapes[i][j] = 0;
                            destroy[i][j] = false;
                        }
                    }
                    int cx = (i - 16);
                    int cy = (j - 16);
                    int d = cx * cx + cy * cy;
                    if (d > rd) {
                        rd = d;
                        rx = cx;
                        ry = cy;
                    }
                } else {
                    allDestroyed++;
                }
            }
        }
        double cd = Math.sqrt(rd);
        if (allDestroyed > 1023) {
            this.dispose = true;
            this.r *= 0.99;
        } else {
            if (rx != -32) {
                double cr = (1 + (cd * (1.0 + cd * 2))) * LandScape.quad;
                this.x = this.mass.center.x + (double) (rx * LandScape.quad * 8);
                this.y = this.mass.center.y - cr * 0.5;

                this.z = this.mass.center.z + (double) (ry * LandScape.quad * 8);
                this.r = cr * 2.5;
                //System.out.println(this.x+" "+this.z+" City Update "+this.r);
            }
        }
        if (this.forcefields.Length() > 0) {
            Node<ForceField> f = this.forcefields.Start();
            if (this.forcefields.Length() == 1) {

                f.data.Update(time, cam);
                f.data.y = this.mass.center.y + this.r * 0.0625;

            } else {
                double a = Linear.PI2 / this.forcefields.Length();
                double b = 0;
                for (; f.data != null; f = f.next) {

                    f.data.Update(time, cam);
                    f.data.y = this.mass.center.y + this.r * 0.0625;
                    f.data.x = this.mass.center.x + (this.r * 0.0625) * Math.cos(b);
                    f.data.z = this.mass.center.z + (this.r * 0.0625) * Math.sin(b);
                    b += a;
                }
            }
        }
        UpdateParticles(time, cam);
        RemoveParticles();
    }

    @Override
    void Render(LandScape scape, double curve, RingList<Light> suns, double emissive1) {
        boolean ok = false;
        for (int i = 0; i < 32; i++) {
            int ix = i - 16;

            for (int j = 0; j < 32; j++) {
                if (shapes[i][j] > 0) {
                    int jy = (j - 16);
                    int sx = 1 + Math.abs(ix << 1);
                    int sy = 1 + Math.abs(jy << 1);
                    double sr = buildings[shapes[i][j]].sphere.radius;
                    bb[i][j] = buildings[shapes[i][j]].bb;
                    Point at = new Point(this.mass.center.x + ix * sx * LandScape.quad, this.mass.center.y + curve, this.mass.center.z + jy * sy * LandScape.quad, sr);
                    points[i][j] = new Point(at.x + buildings[shapes[i][j]].sphere.center.x, buildings[shapes[i][j]].sphere.center.y, at.z + buildings[shapes[i][j]].sphere.center.z, at.r);
                    ok = scape.screen.LoadVertexTriangle(at, buildings[shapes[i][j]].list, 1.0, sr, scape.screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive1);
                } else {
                    bb[i][j] = null;
                    points[i][j] = null;
                }

            }

        }
        Point at = new Point();

        if (ok && this.forcefields.Length() > 0) {
            Node<ForceField> f = this.forcefields.Start();
            for (; f.data != null; f = f.next) {
                scape.screen.LoadVertexPoint(f.data.AsPoint(f.data.radius.Value()), new Pigment(32, 0, 0, 255), null, 1.0, 1.0, scape.screen.renderList, DrawType.Circle, DrawType.None, at, 0);
            }

        }
        if (ok) {
            scape.screen.LoadVertexPoint(this.AsPoint(r), new Pigment(32, 255, 0, 0), null, 1.0, 1.0, scape.screen.renderList, DrawType.Circle, DrawType.None, at, 0);
            this.RenderParticles(scape.screen);
        }
    }
}
