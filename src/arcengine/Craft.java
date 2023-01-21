/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
class BoosterJet extends Component implements GraphicsUpdate,Action{

    int power;
    int powerLeft;
    double relativistic;
    double accel;
    Fighter user;
    boolean activated=false;
    public BoosterJet(Fighter user, Tech tec, int size) {
        super(tec);
        this.user = user;
        this.radius = new Length((float) (user.r*size/tech.level));
        this.mass = new Mass(user, (float) (user.mass.Value()*size) / (float) (tech.level*tech.level), this.radius.Value());
        this.speed = new Speed((float) (size * tech.level * tech.level));
        this.v = new Velocity(new Vector());
        powerLeft = power = (int) (this.user.mass.Value()*size * size * tech.level);
        
    }
    
    @Override
    public void Update(GameTime time, Camera cam) {
        
        this.x = user.x;
        this.y = user.y;
        this.z = user.z;
        if(activated){
        if (powerLeft <0) {
            powerLeft =0;
            this.activated=false;
        }else{
            double rv=user.velocity.v.Length();
            relativistic-=(accel*user.mass.Value()/tech.level*0.25)/Math.sqrt((mass.Value()*this.speed.Value())/(user.power*rv*time.cameraTime));
        
            if(relativistic <0){
                relativistic=0;
                powerLeft--;
            }
        }
        double vs=(speed.Value()*powerLeft*0.0625)*(time.cameraTime*time.cameraTime)/(user.mass.Value()*user.r*user.r*Math.PI);
        accel=user.pa/time.cameraTime+vs;
        user.velocity.v.x += Math.cos(cam.angle.x) * user.pa+Math.cos(cam.angle.x)*vs*time.cameraTime;
        user.velocity.v.z += Math.sin(cam.angle.x) * user.pa+Math.sin(cam.angle.x)*vs*time.cameraTime;
        }else{    
            
        relativistic+=((user.power*user.mass.Value()*user.r*tech.level*4)/(this.speed.Value()*mass.Value()))/time.cameraTime;//cool down
        //System.out.println("jet rel "+relativistic);
        if(relativistic>this.power){
            relativistic=this.power;
            powerLeft+=(int)(tech.level);
            if(powerLeft>this.power){
            powerLeft=this.power;
            }
        }
        }
        //user.velocity.v.y += Math.cos(cam.angle.y) * user.pa*vs;
        //System.out.println("r"+r);
    }

    @Override
    public void active() {
        this.activated=true;
        user.power+=powerLeft;
        int lim=(int)(user.force*this.tech.level);
        if(user.power>lim){
        user.power=lim;
        }
        if(powerLeft==0){
            this.activated=false;
        }
    }
}
/**
 *
 * @author Gerwyn Jones
 */
public abstract class Craft extends GameObject implements GraphicsUpdate {

    int hitPoints;
    int armour;
    byte evasion = 0;
    boolean dispose = false;
    boolean crashing = false;
    TessaShape shape;
    LandScape scape;
    Quarternion rotation;
    Velocity velocity = new Velocity(new Vector());
    Pigment[] colours;
    ForceField forcefield=null;
    BoosterJet jet=null;
    Mass engineMass;
    double fieldEffect;

    Craft(Point at, TessaShape shape, Vector scale, Vector angles, LandScape scape, int force) {
        super();

        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.scape = scape;
        this.shape = shape;
        rotation = null;
        power = 0;
        this.force = force;
        if (angles != null) {
            Quarternion q1 = new Quarternion(angles.x, new Normal(1, 0, 0));
            Quarternion q2 = new Quarternion(angles.z, new Normal(0, 0, 1));
            Quarternion q3 = q1.Mult(q1, q2);
            TessaShape.Transform(this.shape, q3);
        }
        if (scale != null) {
            Matrix m = new Matrix(new Vector(0, 0, 0), scale);
            TessaShape.Transform(this.shape, m);
            mass = new Mass(at, (float) scale.Length(), (float) shape.sphere.radius);
            this.r = shape.sphere.radius;
        } else {
            if (shape != null) {
                mass = new Mass(at, 1, (float) shape.sphere.radius);
                this.r = shape.sphere.radius;
            } else {

                mass = new Mass(at, 1, (float) at.r);
            }
        }
        this.r = at.r;
        int i = (int) (0 / scape.quadR);
        int j = (int) (0 / scape.quadR);
        int x1 = Math.abs(LandScape.ScapeMiddle + i) % LandScape.ScapeSize;
        int y1 = Math.abs(LandScape.ScapeMiddle + j) % LandScape.ScapeSize;
        this.local = new Local(x1, y1);
    }
    double CraftFieldEffect(Ray field,double radius,int dammage,Hit t,double d){
    double vel=this.velocity.v.Length();
        if(vel>0){
        double c1=3e8/LandScape.quadR;
        double cv=c1/vel;
        Ray vf=new Ray();
        vf.from=this.AsVector();
        vf.direction=this.velocity.v.Scaled(Game.time.cameraTime).AsNormal();
            RingList<Craft> test = scape.fighters;
            for (Node<Craft> n = test.Start(); n.data != null; n = n.next) {
                Point c = n.data.AsPoint(n.data.r + radius);

                if(n.data.forcefield!=null){
                    double dis=n.data.Distance(this);
                    Point mp=n.data.forcefield.Hit(field, dis, dammage);
                    if(mp!=null){
                    mp.Scale(cv/this.mass.Value());
                    double m2=mp.Length();
                    fieldEffect+=m2;
                    Vector dif=n.data.Sub(this,n.data);
                    double dot=dif.Dot(dif,this.velocity.v)/(dis*vel);
                    double a;
                    if(dot!=0 && !Double.isInfinite(dot)){
                    //double ac=-(dot)/LandScape.quadR;    
                    double ac=(Math.acos(-dot)*Math.PI);    
                    a=ac*Math.sqrt((Linear.PI2)/vel*Game.time.cameraTime/dis)*(vel/dis*c1*Game.time.cameraTime);
                    a*=c1/(dis*Math.E)*Game.time.cameraTime;
                    //a*=m2*Game.time.cameraTime;
                    //System.out.println("FieldEffect Nutonic pulse "+mp.Length()+" target "+this.field.direction.x+" "+this.field.direction.y+" "+this.field.direction.z);
                    double s1=Math.sin(-a);
                    double s2=Math.sin(a);
                    field.direction.x+=s1;
                    field.direction.y+=s1*s2*2;
                    field.direction.z+=s2;
                    field.direction.Normalize();
                    this.velocity.v.x+=mp.x;
                    this.velocity.v.y+=mp.y;
                    this.velocity.v.z+=mp.z;
                    }
                    //System.out.println("FieldEffect Nutonic pulse "+mp.Length()+" target "+this.field.direction.x+" "+this.field.direction.y+" "+this.field.direction.z);
                    }
                }
                if (field.Penetrates(c, t) && t.time < d) {
                    d = t.time;
                    t.hit = n.data;
                    //at = field.Penetrates(c);
                }
            }
        }
        return d;
    }
    double BaseFieldEffect(Ray field,double radius,int dammage,Hit t,double d){
        double vel=this.velocity.v.Length();
        if(vel>0){
        double c1=3e8/LandScape.quadR;
        double cv=c1/vel;
        Ray vf=new Ray();
        vf.from=this.AsVector();
        vf.direction=this.velocity.v.Scaled(Game.time.cameraTime).AsNormal();
            for(int i=-LandScape.lod;i<=LandScape.lod;i++){
            for(int j=-LandScape.lod;j<=LandScape.lod;j++){
            Local loc= LandScape.CheckXY(local.x+i,local.y+j);
            RingList<Base> test = scape.elements[loc.x][loc.y];
            for (Node<Base> n = test.Start(); n.data != null; n = n.next) {
                Point c = n.data.AsPoint(n.data.r + radius);
                if(n.data.forcefields.Length()>0){
                    double dis=n.data.Distance(this);
                    Node<ForceField> f=n.data.forcefields.Start();
                    for(;f.data!=null;f=f.next){
                    Point mp=f.data.Hit(vf,dis,dammage);
                    if(mp!=null){
                    mp.Scale(cv/this.mass.Value());
                    
                    //double vel=this.velocity.v.Length();
                    double m2=mp.Length();
                    fieldEffect+=m2;
                    //double dot=mp.Dot(mp,this.velocity.v)/(vel+m2);
                    double a=0;
                    Vector dif=f.data.Sub(this,f.data);
                    double dot=dif.Dot(dif,this.velocity.v)/(dis*vel);
                    if(dot!=0 && !Double.isInfinite(dot)){
                    //double ac=-(dot)/LandScape.quadR;    
                    double ac=(Math.acos(-dot)*Math.PI);    
                    a=ac*Math.log(Math.sqrt((Linear.PI2)/vel*Game.time.cameraTime/dis)*(vel/dis*c1*Game.time.cameraTime));
                    a*=c1/(dis*Math.E)*Game.time.cameraTime;
                    //a*=m2*Game.time.cameraTime;
                    //System.out.println("FieldEffect Nutonic pulse "+mp.Length()+" target "+this.field.direction.x+" "+this.field.direction.y+" "+this.field.direction.z);
                    double s1=Math.sin(-a);
                    double s2=Math.sin(a);
                    field.direction.x+=s1;
                    field.direction.y+=s1*s2*2;
                    field.direction.z+=s2;
                    field.direction.Normalize();
                    this.velocity.v.x+=mp.x;
                    this.velocity.v.y+=mp.y;
                    this.velocity.v.z+=mp.z;
                    }
                    //System.out.println("FieldEffect Nutonic pulse "+mp.Length()+" target "+this.field.direction.x+" "+this.field.direction.y+" "+this.field.direction.z);
                    }
                    }
                }
                if (field.Penetrates(c, t) && t.time < d) {
                    d = t.time;
                    //at=field.Penetrates(c);
                    t.hit = n.data;
                }

            }
            }
            }
        }
        return d;
    }
    boolean Dispose() {
        return dispose;
    }

    void AddForceField(ForceField f) {
        this.forcefield = f;
    }
    void AddBooster(BoosterJet j) {
        this.jet=j;
    }

    void SetLocal() {
        double lim = LandScape.ScapeMiddle * scape.quadR;
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

        int i = (int) ((x + scape.quadR * 0.5) / scape.quadR);
        int j = (int) ((z + scape.quadR * 0.5) / scape.quadR);
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

    @Override
    void PaintData(Graphics2D g, int x, int y) {
    }

    Pigment[] Colours() {
        return colours;
    }
}

enum FighterCommands {

    Forward, Back, LeftSide, RightSide, Up, Down, TiltUp, TiltDown, TurnLeft, TurnRight, FireMain, FireAuxilary, DropOrd,FireJets,EvasionLeft,EvasionRight
}

class Fighter extends Craft {

    double camRange = 25;
    double pa;
    double speed;
    double sense;
    int missiles = 100;
    int bombs = 0;
    Missile currentMissile = null;
    Blaster currentBlaster = null;
    double blasterRate = 2;
    double blasterTime;
    double missileRate = 4;
    double missileTime;
    RingList<FighterCommands> coms = new RingList<FighterCommands>();

    Fighter(Point at, TessaShape shape, Vector scale, Vector angles, LandScape scape, int force) {
        super(at, shape, scale, angles, scape, force);
        this.hitPoints = 1000 + force;
        this.bodyPoints=this.hitPoints;
        this.armour=5;
        this.engineMass=new Mass(this,this.mass.Value()*0.1f,(float)this.r);
        //dumbies
    }

    void Command(FighterCommands com) {
        coms.Append(com);
    }

    void SetControls(GameTime time) {
        pa = (this.engineMass.Value()*power*time.cameraTime) / (scape.airPresure*this.mass.Value()*(this.r*this.r*this.r*Linear.PI4/3.0)*0.125);
        speed = velocity.v.Length();
        sense = (Linear.PID4/(this.r*this.mass.Value()))/(Linear.PI2+speed)*scape.airPresure*time.cameraTime;

    }

    @Override
    public Point Hit(Ray f, double tr, int damage) {
        if(this.forcefield!=null){
        damage -= forcefield.powerLeft;
                if (damage > 0) {
                    forcefield.powerLeft -= damage;
                    if (forcefield.powerLeft < 0) {
                       forcefield.powerLeft = 0;
                    }
                }
            }
        if(damage>0){
        damage -= armour;

        if (damage > 0) {
            hitPoints -= (damage);
        }
        if (hitPoints < 0) {
            this.dispose = true;
        }
        }
        return this;
    }
    void CameraFieldEffect(GameTime time,Camera cam){
     double vel=this.velocity.v.Length();
        if(vel>0){
        Ray vf=new Ray();
        vf.from=this.AsVector();
        vf.direction=this.velocity.v.Scaled(time.cameraTime).AsNormal();
        
        double c=3e8/LandScape.quadR;
        double cv=c/vel;
        for(int i=-LandScape.lod;i<=LandScape.lod;i++){
            for(int j=-LandScape.lod;j<=LandScape.lod;j++){
            
            Local loc= LandScape.CheckXY(local.x+i,local.y+j);
            RingList<Base> test = scape.elements[loc.x][loc.y];
            for (Node<Base> n = test.Start(); n.data != null; n = n.next) {
                double dis=n.data.Distance(this);
                if(n.data.forcefields.Length()>0){
                    Node<ForceField> f=n.data.forcefields.Start();
                    for(;f.data!=null;f=f.next){
                    Point mp=f.data.Hit(vf,dis, 1);
                    if(mp!=null){
                    //System.out.println(n.data.x+" "+n.data.z+"Before FieldEffect Nutonic pulse "+mp.Length()+" target "+this.velocity.v.x+" "+this.velocity.v.y+" "+this.velocity.v.z);
                    
                    
                    //if(Math.abs(mp.x)>Linear.Epsilon && Math.abs(mp.y)>Linear.Epsilon && Math.abs(mp.z)>Linear.Epsilon){
                        
                    mp.Scale(cv/this.mass.Value());
                    double m2=mp.Length();
                    fieldEffect+=m2*time.cameraTime;
                    Vector dif=f.data.Sub(this,f.data);
                    double dot=dif.Dot(dif,this.velocity.v)/(dis*vel);
                    if(dot!=0 && !Double.isInfinite(dot)){
                    double ac=Math.acos(-dot);    
                    double a=(ac*Math.sqrt((Linear.PI8)/(vel)*time.cameraTime/dis)*(vel/dis*c*time.cameraTime));
                        
                    //cam.angle.x+=ac*Math.sqrt((Linear.PI2)/(1+vel)*time.cameraTime/dis)*(vel/dis*Linear.PI8*time.cameraTime);
                    //System.out.println(dot+" ang "+cam.angle.x+":"+ac);//1.570796356
                    cam.angle.x+=a*m2/dis*time.cameraTime;
                    this.velocity.v.x+=mp.x;
                    this.velocity.v.y+=mp.y;
                    this.velocity.v.z+=mp.z;
                    
                    //System.out.println("mp"+mp.x+" "+mp.y+" "+mp.z+" ");
                    //System.out.println("v"+this.velocity.v.x+" "+this.velocity.v.y+" "+this.velocity.v.z+" ");
                    //System.out.println("pos"+x+" "+y+" "+z+" ");
                    }
                    //System.out.println(n.data.x+" "+n.data.z+"After FieldEffect Nutonic pulse "+mp.Length()+" target "+this.velocity.v.x+" "+this.velocity.v.y+" "+this.velocity.v.z);
                    }
                    }
                }
            }
        }}
        }
    }
    @Override
    public void Update(GameTime time, Camera cam) {
        double ax = 0;
        double ay = 0;
        evasion=0;
        blasterTime += time.cameraTime;
        missileTime += time.cameraTime;
        if(this.jet!=null){
        this.jet.activated=false;
        }
        fieldEffect=0;
        CameraFieldEffect(time,cam);
        //System.out.println(blasterTime+" "+missileTime);
        //time.cameraTime=1;
        int priority[] = new int[32];
        for (Node<FighterCommands> com = coms.Start(); com.data != null; com = com.next) {

            switch (com.data) {
                case Forward: {
                    priority[0]++;
                    break;
                }
                case Back: {

                    priority[1]++;
                    break;
                }
                case LeftSide: {

                    priority[2]++;
                    break;
                }
                case RightSide: {

                    priority[3]++;
                    break;
                }

                case Up: {

                    priority[4]++;
                    break;
                }
                case Down: {

                    priority[5]++;
                    break;
                }
                case TiltUp: {

                    priority[6]++;
                    break;
                }
                case TiltDown: {

                    priority[7]++;
                    break;
                }
                case TurnLeft: {

                    priority[8]++;
                    break;
                }

                case TurnRight: {

                    priority[9]++;
                    break;
                }
                case FireMain: {

                    priority[10]++;
                    break;
                }
                case FireAuxilary: {

                    priority[11]++;
                    break;
                }
                case DropOrd: {

                    priority[12]++;
                    break;
                }
                case FireJets: {

                    priority[13]++;
                    break;
                }
                case EvasionLeft: {

                    priority[14]++;
                    break;
                }
                case EvasionRight: {

                    priority[15]++;
                    break;
                }
            }
        }
        coms = new RingList<FighterCommands>();
        for (int i = 0; i < 32; i++) {
            if (priority[i] > 0) {
                int k = i;
                switch (k) {
                    case 0: {

                        if (power < force|| this.jet!=null && this.jet.activated) {
                            power += 2;
                        }
                        velocity.v.x += Math.cos(cam.angle.x) * pa;
                        velocity.v.z += Math.sin(cam.angle.x) * pa;
                        //velocity.v.y += Math.cos(cam.angle.y) * pa;
                        break;
                    }
                    case 1: {

                        if (power < force|| this.jet!=null && this.jet.activated) {
                        
                            power += 2;
                        }
                        velocity.v.x -= Math.cos(cam.angle.x) * pa;
                        velocity.v.z -= Math.sin(cam.angle.x) * pa;
                        //velocity.v.y -= Math.cos(cam.angle.y) * pa;
                        break;
                    }
                    case 2: {
                        if (power < force|| this.jet!=null && this.jet.activated) {
                            power += 2;
                        }

                        velocity.v.x += Math.sin(cam.angle.x) * pa;
                        velocity.v.z -= Math.cos(cam.angle.x) * pa;

                        break;
                    }
                    case 3: {
                        if (power < force || this.jet!=null && this.jet.activated) {
                            power += 2;
                        }

                        velocity.v.x -= Math.sin(cam.angle.x) * pa;
                        velocity.v.z += Math.cos(cam.angle.x) * pa;

                        break;
                    }
                    case 4: {
                        if (cam.y < 450) {
                            cam.y += time.cameraTime*pa;
                        }

                        break;
                    }
                    case 5: {
                        if (cam.y > 100) {
                            cam.y -= time.cameraTime*pa;
                        }
                        break;
                    }
                    case 6: {
                        cam.angle.y += ay = sense;
                        if (cam.angle.y > Linear.PID4) {
                            cam.angle.y = Linear.PID4;
                        }
                        break;
                    }
                    case 7: {
                        cam.angle.y += ay = - sense;
                        if (cam.angle.y < -Linear.PID4) {
                            cam.angle.y = -Linear.PID4;
                        }
                        break;
                    }
                    case 8: {
                        cam.angle.x += ax = -sense;
                        break;
                    }
                    case 9: {
                        cam.angle.x += ax = sense;
                        break;
                    }
                    case 10: { //fire main

                        if (currentBlaster == null && blasterTime > blasterRate) {
                            blasterTime = 0;
                            Normal dir = new Normal(this.direct.x, this.direct.y, this.direct.z);
                            dir.Normalize();
                            currentBlaster = new Blaster(new Point(this.x, this.y, this.z, this.r), dir.Scaled(this.speed).AsVector(), scape, 300);
                            currentBlaster.Fire(this.AsVector(), dir, this.r);
                            this.scape.missiles.Append(currentBlaster);
                        } else {
                            currentBlaster = null;
                        }
                        //System.out.println("B"+(currentBlaster==null)+" "+blasterTime+" "+scape.removeMissiles.Length()+" of "+scape.missiles.Length());
                        break;
                    }
                    case 11: {
                        //fire aux
                        if (currentMissile == null && missiles > 0 && missileTime > missileRate) {
                            missileTime = 0;
                            Normal dir = new Normal(this.direct.x, this.direct.y, this.direct.z);
                            dir.Normalize();
                            currentMissile = new Missile(new Point(this.x, this.y, this.z, this.r), dir.Scaled(this.speed).AsVector(), scape, 250);
                            currentMissile.Fire(this.AsVector(), dir, this.r * 2);
                            this.scape.missiles.Append(currentMissile);
                            missiles--;
                        } else {
                            currentMissile = null;
                        }
                        //System.out.println("M"+(currentMissile==null)+" "+missileTime+" "+scape.removeMissiles.Length()+" of "+scape.missiles.Length());
                        break;
                    }
                    case 12: {
                        break;
                    }
                    case 13: {
                        if(this.jet!=null){
                            this.jet.active();
                            if(this.jet.activated){
                            velocity.v.x += Math.cos(cam.angle.x) * pa;
                            velocity.v.z += Math.sin(cam.angle.x) * pa;
                            //velocity.v.y += Math.cos(cam.angle.y) * pa;
                            }
                        }
                        break;
                    }
                    case 14: {
                        this.evasion=-1;
                        break;
                    }
                    case 15: {
                        this.evasion=1;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
        Hit t;
        t = new Hit(cam.y);
        double h = cam.y;
        Ray field = new Ray();
        field.from = this.AsVector();
        field.direction = new Normal(0, -1, 0);
        
        if(this.jet!=null){
        this.jet.Update(time, cam);
        
        }
        if(this.evasion==1){
            if(power<force){
            power += 2;
            }
            velocity.v.x += Math.cos(cam.angle.x) * pa;
            velocity.v.z += Math.sin(cam.angle.x) * pa;
            //velocity.v.y += Math.cos(cam.angle.y) * pa;
            cam.angle.z-=sense*Math.PI;
            if(cam.angle.z<-Math.PI){cam.angle.z+=Linear.PI2;}
            ax-=(cam.angle.z+Linear.PID2)/Math.PI;
        }else
        if(this.evasion==-1){
            if(power<force){
            power += 2;
            }
            velocity.v.x += Math.cos(cam.angle.x) * pa;
            velocity.v.z += Math.sin(cam.angle.x) * pa;
            //velocity.v.y += Math.cos(cam.angle.y) * pa;
            cam.angle.z+=sense*Math.PI;
            
            if(cam.angle.z>Math.PI){cam.angle.z-=Linear.PI2;}
            ax-=(cam.angle.z+Linear.PID2)/Math.PI;
        }
        else{
            double s1=1024;
            double s2=(s1*2)-sense;
            cam.angle.z=(-Linear.PID2*s1+cam.angle.z*s2)/(s1*3);
            if(cam.angle.z>-Linear.PID2)
            {
                ax-=(cam.angle.z+Linear.PID2)/Math.PI;
                //cam.angle.z=-Linear.PID2-ax*Linear.PID2;
            }
            
        }
        Vector offset = new Vector((this.local.x - LandScape.ScapeMiddle) * scape.quadR, 0, (this.local.y - LandScape.ScapeMiddle) * scape.quadR);
        //System.out.println("x"+x+"y"+y+"z"+z+"q"+scape.quadR);
        //System.out.println("ox"+offset.x+"oy"+offset.y+"oz"+offset.z);
        //GROUND
        RingList<Tessa> sec = scape.scape[scape.land[this.local.x][this.local.y]][scape.lod - 1].list;
        for (Node<Tessa> n = sec.Start(); n.data != null; n = n.next) {
            Point c = new Point((Math.abs(Linear.Max3(n.data.vert[0].v.x, n.data.vert[1].v.x, n.data.vert[2].v.x))),
                    (Math.abs(Linear.Max3(n.data.vert[0].v.y, n.data.vert[1].v.y, n.data.vert[2].v.y))),
                    (Math.abs(Linear.Max3(n.data.vert[0].v.z, n.data.vert[1].v.z, n.data.vert[2].v.z))));
            if (c.y > this.y) {
                c = offset.Add(c, offset).AsPoint(c.r);
                if (field.Penetrates(c, t)) {
                    if (t.time < h) {
                        h = t.time;
                    }
                }
            }
        }
        //external force fields
        
        if (!crashing) {
            double fp = Math.PI / (Math.E + h);
            this.velocity.v = this.velocity.v.Scaled(1.0 / (1 + speed * scape.airPresure));
            cam.x += this.velocity.v.x * time.cameraTime - (Math.cos(scape.windAngle) * scape.windSpeed * time.cameraTime);
            cam.z += this.velocity.v.z * time.cameraTime - (Math.sin(scape.windAngle) * scape.windSpeed * time.cameraTime);
            cam.y += this.velocity.v.y * time.cameraTime + time.cameraTime * fp * pa - cam.y * scape.airPresure * time.cameraTime - 0.5 * time.cameraTime * time.cameraTime;
            if (cam.y < h) {
                cam.y = h;
            }
            if (this.power > 0) {
                this.power--;
            }
            direct = new Vector(Math.cos(cam.angle.x), -Math.sin(cam.angle.y), Math.sin(cam.angle.x));
            direct.Normalize();
        } else {
            this.velocity.v = this.velocity.v.Add(this.velocity.v, new Vector(0, -0.5 * time.cameraTime, 0));//.Scaled(1.0 / (1 + speed * scape.airPresure));
            cam.x += this.velocity.v.x * time.cameraTime - (Math.cos(scape.windAngle) * scape.windSpeed * 0.5 / this.velocity.v.x * time.cameraTime);
            cam.z += this.velocity.v.z * time.cameraTime - (Math.sin(scape.windAngle) * scape.windSpeed * 0.5 / this.velocity.v.z * time.cameraTime);
            cam.y += this.velocity.v.y * time.cameraTime - cam.y * scape.airPresure * time.cameraTime - 0.5 * time.cameraTime * time.cameraTime;
            
            this.power = 0;

            direct = new Vector(Math.cos(cam.angle.x + this.velocity.v.x * Math.PI), -Math.sin(cam.angle.y + this.velocity.v.y * Math.PI), Math.sin(cam.angle.x + this.velocity.v.z * Math.PI));
            direct.Normalize();

        }
        
        Point at = new Point(cam.x + direct.x * camRange, cam.y + direct.y * camRange, cam.z + direct.z * camRange, this.mass.center.r);
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = at.r;
        this.SetLocal();
        this.rotation = new Quarternion(cam.angle.x, new Normal(0, 1, 0));
        if (ax != 0) {
            this.rotation = this.scape.fighter.rotation.Mult(this.rotation, new Quarternion(-ax * Math.PI, new Normal(1, 0, 0)));
        }
        if (ay != 0) {
            this.rotation = this.scape.fighter.rotation.Mult(this.rotation, new Quarternion(-ay, new Normal(0, 0, 1)));
        }
        if (this.forcefield != null) {

            this.forcefield.Update(time, cam);
        }
        if(jet.activated){
        
        this.particles.Append(new Jet(this,1,30,new Pigment(32,255,0,0),30));
        this.particles.Append(new Jet(this,1,30,new Pigment(64,255,255,0),20));
        this.particles.Append(new Jet(this,1,30,new Pigment(128,255,255,255),10));
        }
        UpdateParticles(time, cam);
        RemoveParticles();
        //System.out.println("Locals this"+this.local.x+" "+this.local.y+" scape "+(scape.x+LandScape.ScapeMiddle)+" "+(scape.y+LandScape.ScapeMiddle));
    }
}
