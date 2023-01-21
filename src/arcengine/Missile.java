/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

class Blaster extends Craft {
    int explosives=10;
    double pa;
    double sense;
    boolean armed = false;
    boolean detonating = false;
    double detonationRadius = 10;
    Vector lastPos;
    Ray field;
    Hit t;
    double timeOut=0;
    Blaster(Point at, Vector veloc, LandScape scape, int force) {
        super(at, null, null, null, scape, force);
        this.mass.m=0.01f;
        this.velocity = new Velocity(veloc.AsVector());
        colours = new Pigment[]{
            new Pigment(8, 0, 128, 64),
            new Pigment(16, 64, 255, 128),
            new Pigment(32, 128, 255, 255),
            new Pigment(64, 255, 255, 255),};
    }

    void SetControls(GameTime time) {
        pa = (power * time.cameraTime) / (scape.airPresure * force * time.cameraTime);
        speed.s =(float) velocity.v.Length();
        sense = Math.PI * time.cameraTime / (Math.PI + speed.s * 0.5);

    }

    void Fire(Vector from, Normal direct, double fireDistance) {
        power = force >> 1;
        field = new Ray();
        this.direct = direct.Unity().AsVector();
        double det = detonationRadius + fireDistance;
        this.x = from.x + direct.x * (det);
        this.y = from.y + direct.y * (det);
        this.z = from.z + direct.z * (det);
        field.from = this.AsVector();
        field.direction = direct;
        this.r = detonationRadius;

    }

    void Arm() {
        armed = true;
    }

    double Proxi() {
        Point at = null;
        double yt = Linear.MAX_MAP;
        boolean hitGround = false;
        //for(int ix=local.x-1;ix<local.x+1;ix++){
        //int i=Math.abs(ix);
        //for(int jy=local.y-1;jy<local.y+1;jy++){
        //int j=Math.abs(jy);
        if (this.y < 0) {
            RingList<Tessa> sec = scape.scape[scape.land[local.x][local.y]][scape.lod - 1].list;

            //Vector offset=new Vector((local.x-LandScape.ScapeMiddle)*scape.quadR,0,(local.y-LandScape.ScapeMiddle)*scape.quadR);
            //System.out.println("x"+x+"y"+y+"z"+z+"q"+scape.quadR);
            //System.out.println("ox"+offset.x+"oy"+offset.y+"oz"+offset.z);
            for (Node<Tessa> n = sec.Start(); n.data != null; n = n.next) {
                double cy = (((n.data.vert[0].v.y + n.data.vert[1].v.y + n.data.vert[2].v.y) * Defined.Third));
                if (this.y < cy) {
                    //c=offset.Add(c,offset).AsPoint(c.r);
                    double d = cy;
                    if (d < yt) {
                        yt = d;
                        hitGround = true;
                    }
                }
            }
            // }
            //}
        }
        t = new Hit();
        double d = t.time;
        if (d > detonationRadius) {
            at = null;
            
            d=BaseFieldEffect(field,this.detonationRadius,explosives,t,d);
            //System.out.println("Testing "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
            if (t.hit != null) {

                if (t.hit instanceof HumanCity) {
                    HumanCity site = (HumanCity) t.hit;//this.Add(,direct.Scaled(-t.hit.r)).AsVector()
                    Ray f = new Ray();
                    Vector dr=field.direction.Scaled(d*2).AsVector();
                    f.from = t.at.Add(dr.Negative(), t.at).AsVector();
                    f.direction = field.direction.AsNormal();
                    Point building = site.Hit(f, this.detonationRadius, explosives);//.Scaled(-t.hit.r)

                    if (building != null) {
                        t.at = at = building;//.Scaled(-t.hit.r);
                        this.x = at.x;
                        this.y = at.y;
                        this.z = at.z;
                        this.Detonate();
                        return d;
                    }
                } else if (t.hit instanceof Base) {
                    t.hit.Hit(field,this.detonationRadius,explosives);
                    
                    at = t.at;//t.at = at = t.hit.Hit(direct.Scaled(-t.hit.r),this.detonationRadius,1);
                    this.x = at.x;
                    this.y = at.y;
                    this.z = at.z;
                    this.Detonate();
                    return d;
                }
                //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
            }

        }else{
            
            //System.out.println("Testing "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d);
            }
        if (d > detonationRadius) {
            at = null;
            d=CraftFieldEffect(field,this.detonationRadius,explosives,t,d);
            if (t.hit != null) {
                //System.out.println("Craft Hit");
                Point hitLocation=t.hit.Hit(field, detonationRadius, explosives);
                at = t.at;//t.at = at = t.hit.Add(direct.Scaled(-t.hit.r));
                this.x = at.x;
                this.y = at.y;
                this.z = at.z;
                this.Detonate();

                //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
                return d;
            }
        }
        if (hitGround) {
            //Vector offset=new Vector((local.x-LandScape.ScapeMiddle)*scape.quadR,0,(local.y-LandScape.ScapeMiddle)*scape.quadR);
            //System.out.println("Loc "+this.x+" "+this.y+" "+this.z+" ox"+offset.x+" oz"+offset.z);
            //System.out.println("Pens "+this.local.x+" "+this.local.y+" cx"+at.x+" cz"+at.z+" cr"+at.r+" d "+yt);

            //System.out.println("Loc0 "+this.local.x+" "+this.local.y+" "+yt);
            //System.out.println("Loc1 "+this.x+" "+this.y+" "+this.z);
            //return to original location
            this.x -= velocity.v.x * this.pa * 0.5;
            this.y -= velocity.v.y * this.pa * 0.5;
            this.z -= velocity.v.z * this.pa * 0.5;
            //System.out.println("Loc2 "+this.x+" "+this.y+" "+this.z);
            this.SetLocal();
            this.Detonate();

            //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
            return yt;

        }
        return d;
    }

    @Override
    public Point Hit(Ray ray, double tr, int damage) {
        this.Detonate();
        return this;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        timeOut+=time.cameraTime;
        if(timeOut>60){
        this.Detonate();
        }
        if (!this.detonating) {
            power += 2;
            if (power > force) {
                power = force;
            }
            SetControls(time);
            
            this.velocity.v = this.velocity.v.Add(this.velocity.v, this.field.direction.Scaled(pa * 1.0 / (1 + speed.s * scape.airPresure)).AsVector());
        
        lastPos = this.AsVector();
        x += this.velocity.v.x * time.cameraTime;//-(Math.cos(scape.windAngle)*scape.windSpeed*time.cameraTime);
        y += this.velocity.v.y * time.cameraTime;
        z += this.velocity.v.z * time.cameraTime;//-(Math.sin(scape.windAngle)*scape.windSpeed*time.cameraTime);
        }//cam.y += this.velocity.v.y*time.cameraTime+time.cameraTime*Math.PI*pa-cam.y*scape.airPresure*time.cameraTime-0.5*time.cameraTime*time.cameraTime; 
        //}
        field.from = this.AsVector();
        this.SetLocal();
        if (armed && !detonating) {
            double d = Proxi();
            //System.out.println("ARMED "+armed+" d "+d);
            if (d < detonationRadius) {
                this.Detonate();
            } else {
                double dis = cam.Sub(cam, this).Length();
                if (dis > scape.quadRLod) {
                    this.Detonate();
                }
            }

        } else if (!armed && !detonating) {
            double d = Proxi();
            if (d > detonationRadius) {
                Arm();
            }
        } else if (armed && detonating && this.r != 0) {
            this.r *= 2.7;
            if (this.r > scape.quadR) {
                this.r = 0;
            }
        } else if (armed && detonating && this.r == 0) {
            scape.removeMissiles.Append(this);
            if (t.hit != null) {
                
               if(t.hit instanceof Craft){
                t.hit.particles.add(new Explosion(t.hit, explosives, 50, new Pigment(32, 128, 256, 64),explosives));
 
               }else{
                t.hit.particles.add(new Explosion(t.at, 10+explosives, 50, new Pigment(32, 128, 256, 64),explosives));
               }
               t = new Hit();
            }

        }

    }

    void Detonate() {
        detonating = true;
        //this.x-=velocity.v.x*this.pa*2;
        //this.y-=velocity.v.y*this.pa*2;
        //this.z-=velocity.v.z*this.pa*2;
        //this.power=this.force;
        this.velocity.v = new Vector(0, 0, 0);
        this.r *= 2.7;
    }
}

/**
 *
 * @author Gerwyn Jones
 */
public class Missile extends Craft {
    int explosives=100;
    double pa;
    double speed;
    double sense;
    boolean armed = false;
    boolean detonating = false;
    double detonationRadius = 15;
    Vector lastPos;
    Ray field;
    Hit t;
    double timeOut=0;
    Missile(Point at, Vector veloc, LandScape scape, int force) {
        super(at, null, null, null, scape, force);
        this.velocity = new Velocity(veloc.AsVector());
        this.mass.m=0.1f;
        colours = new Pigment[]{
            new Pigment(8, 128, 64, 0),
            new Pigment(16, 255, 128, 64),
            new Pigment(32, 255, 255, 128),
            new Pigment(64, 255, 255, 255),};
    }
    
    void SetControls(GameTime time) {
        pa = (power * time.cameraTime) / (scape.airPresure * force * time.cameraTime);
        speed = velocity.v.Length();
        sense = Math.PI * time.cameraTime / (Math.PI + speed * 0.5);

    }

    void Fire(Vector from, Normal direct, double fireDistance) {
        power = force >> 3;
        field = new Ray();
        //this.direct=direct.AsVector();
        double det = detonationRadius + fireDistance;
        this.x = from.x + direct.x * (det);
        this.y = from.y + direct.y * (det);
        this.z = from.z + direct.z * (det);
        field.from = this.AsVector();
        field.direction = direct;
        this.r = detonationRadius;

    }

    @Override
    public Point Hit(Ray ray, double tr, int damage) {
        this.Detonate();
        return this;
    }

    void Arm() {
        armed = true;
    }

    double Proxi() {
        Point at = null;
        double yt = Linear.MAX_MAP;
        boolean hitGround = false;
        //for(int ix=local.x-1;ix<local.x+1;ix++){
        //int i=Math.abs(ix);
        //for(int jy=local.y-1;jy<local.y+1;jy++){
        //int j=Math.abs(jy);
        if (this.y < 0) {
            RingList<Tessa> sec = scape.scape[scape.land[local.x][local.y]][scape.lod - 1].list;

            //Vector offset=new Vector((local.x-LandScape.ScapeMiddle)*scape.quadR,0,(local.y-LandScape.ScapeMiddle)*scape.quadR);
            //System.out.println("x"+x+"y"+y+"z"+z+"q"+scape.quadR);
            //System.out.println("ox"+offset.x+"oy"+offset.y+"oz"+offset.z);
            for (Node<Tessa> n = sec.Start(); n.data != null; n = n.next) {
                double cy = (((n.data.vert[0].v.y + n.data.vert[1].v.y + n.data.vert[2].v.y) * Defined.Third));
                if (this.y < cy) {
                    //c=offset.Add(c,offset).AsPoint(c.r);
                    double d = cy;
                    if (d < yt) {
                        yt = d;
                        hitGround = true;
                    }
                }
            }
            // }
            //}
        }
        t = new Hit();
        double d = t.time;
        if (d > detonationRadius) {
            at = null;
            d=BaseFieldEffect(field,this.detonationRadius,explosives,t,d);
       
            //System.out.println("Testing "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);

            if (t.hit != null) {

                /*Ray detonation=new Ray();
                 detonation.from=this.Add(this,field.direction.Scaled(this.detonationRadius)).AsVector();
                 detonation.direction=field.direction;
                 Point dam=t.hit.AsPoint(t.hit.r);
                 t.at=detonation.Penetrates(dam);
                 
                 Point col = t.hit.AsPoint(t.hit.r + this.detonationRadius);
                 at = field.Penetrates(col);
                 //System.out.println("Hit "+col.x+" "+col.y+" "+col.z+" "+col.r);
                 //System.out.println("at "+at.x+" "+at.y+" "+at.z+" "+at.r);
                 this.x = at.x;
                 this.y = at.y;
                 this.z = at.z;
                 t.at = t.hit.Add(at, field.direction.Scaled(t.hit.r * 0.5)).AsPoint(t.hit.r);
                 this.Detonate();

                 //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
                 return d;*/
                if (t.hit instanceof HumanCity) {
                    HumanCity site = (HumanCity) t.hit;
                     Ray f = new Ray();
                    f.from = t.at.Add(field.direction.Negative().Scaled(d*2), t.at).AsVector();
                    f.direction = field.direction.AsNormal();
                    Point building = site.Hit(f, this.detonationRadius , explosives);//.Scaled(-t.hit.r)

                    if (building != null) {
                        t.at = at = building;//.Scaled(-t.hit.r);
                        this.x = at.x;
                        this.y = at.y;
                        this.z = at.z;
                        this.Detonate();
                        return d;
                    }

                } else if (t.hit instanceof Base) {
                    t.hit.Hit(field,this.detonationRadius,explosives);
                    at = t.at;////
                    this.x = at.x;
                    this.y = at.y;
                    this.z = at.z;
                    this.Detonate();
                    return d;
                }
            }

        }
        if (d > detonationRadius) {
            at = null;
            
            d=CraftFieldEffect(field,this.detonationRadius,explosives,t,d);
            if (t.hit != null) {
                Point hitLocation=t.hit.Hit(field, detonationRadius, explosives);
                
                at = t.at;//Point col = t.hit.AsPoint(t.hit.r + this.detonationRadius);
                //at = field.Penetrates(col);
                this.x = at.x;
                this.y = at.y;
                this.z = at.z;
                //t.at = t.hit.Add(at, field.direction.Scaled(t.hit.r * 0.5)).AsPoint(t.hit.r);

                this.Detonate();

                //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
                return d;
            }
        }
        if (hitGround) {
            //Vector offset=new Vector((local.x-LandScape.ScapeMiddle)*scape.quadR,0,(local.y-LandScape.ScapeMiddle)*scape.quadR);
            //System.out.println("Loc "+this.x+" "+this.y+" "+this.z+" ox"+offset.x+" oz"+offset.z);
            //System.out.println("Pens "+this.local.x+" "+this.local.y+" cx"+at.x+" cz"+at.z+" cr"+at.r+" d "+yt);

            //System.out.println("Loc0 "+this.local.x+" "+this.local.y+" "+yt);
            //System.out.println("Loc1 "+this.x+" "+this.y+" "+this.z);
            //return to original location
            this.x -= velocity.v.x * this.pa * 2;
            this.y -= velocity.v.y * this.pa * 2;
            this.z -= velocity.v.z * this.pa * 2;
            //System.out.println("Loc2 "+this.x+" "+this.y+" "+this.z);
            //this.SetLocal();
            this.Detonate();
            //System.out.println("Pens "+this.local.x+" "+this.local.y+" t "+t.time+" d "+d+" c "+c.r);
            return yt;

        }
        return d;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        timeOut+=time.cameraTime;
        if(timeOut>60){
        this.Detonate();
        }
        
        if (!this.detonating) {
            power++;
            if (power > force) {
                power = force;
            }
            SetControls(time);
            this.velocity.v = this.velocity.v.Add(this.velocity.v, this.field.direction.Scaled(pa * 1.0 / (1 + speed * scape.airPresure)).AsVector());
            lastPos = this.AsVector();
            x += this.velocity.v.x * time.cameraTime;//-(Math.cos(scape.windAngle)*scape.windSpeed*time.cameraTime);
            y += this.velocity.v.y * time.cameraTime;
            z += this.velocity.v.z * time.cameraTime;//-(Math.sin(scape.windAngle)*scape.windSpeed*time.cameraTime);
        }
        //cam.y += this.velocity.v.y*time.cameraTime+time.cameraTime*Math.PI*pa-cam.y*scape.airPresure*time.cameraTime-0.5*time.cameraTime*time.cameraTime; 
        //}
        field.from = this.AsVector();
        this.SetLocal();
        if (armed && !detonating) {
            double d = Proxi();
            if (d < detonationRadius) {
                this.Detonate();
            } else {
                double dis = cam.Sub(cam, this).Length();
                if (dis > scape.quadRLod) {
                    this.Detonate();
                }
            }

        } else if (!armed && !detonating) {
            double d = Proxi();
            if (d > detonationRadius) {
                Arm();
            }
        } else if (armed && detonating && this.r != 0) {
            this.r *= 2.7;
            if (this.r > scape.quadRLod) {
                this.r = 0;
            }
        } else if (armed && detonating && this.r == 0) {
            scape.removeMissiles.Append(this);
            if (t.hit != null) {
                if(t.hit instanceof Craft){
                
                t.hit.particles.add(new Explosion(t.hit, 5, explosives>>1, new Pigment(32, 256, 128, 64),explosives));
                t.hit.particles.add(new Jet(t.hit, 3, explosives>>1, new Pigment(32, 0, 0, 0),10));
                }else{
                t.hit.particles.add(new Explosion(t.at, 10, explosives>>1, new Pigment(32, 256, 128, 64),explosives));
                t.hit.particles.add(new Smoke(t.at, 5, explosives, new Pigment(32, 0, 0, 0),10));
                }
                t = new Hit();
            }
        }

        //System.out.println("missile at "+x+" "+y+" "+z+" "+r);
        //System.out.println("missile v "+this.velocity.v.x+" "+this.velocity.v.y+" "+this.velocity.v.z);
    }

    void Detonate() {
        detonating = true;
       //this.x-=velocity.v.x*this.pa*2;
        //this.y-=velocity.v.y*this.pa*2;
        //this.z-=velocity.v.z*this.pa*2;

        //this.power=this.force;        
        this.velocity.v = new Vector(0, 0, 0);
        this.r *= 2.7;
    }
}
