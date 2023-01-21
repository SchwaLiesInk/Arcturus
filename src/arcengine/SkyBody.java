/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;

/**
 *
 * @author Gerwyn Jones
 */
public 
class SkyBody implements GraphicsUpdate {

    Light directLight;
    Pigment skyPigment = new Pigment(32, 64, 128);
    Pigment color = new Pigment(32, 64, 128);
    protected int energy = 54;//   
    protected int specRed = 255 - energy * 2;//max 255-energy*2    
    protected int specGreen = 255 - energy * 2;//max 255-energy*2
    protected int specBlue = 0;//max 255-energy*2
    double ambient = 1.0;
    double light = 0.25;
    double shade = 0.125;
    double dark = 0.0195;
    double arc = 0.0195;
    double asscention = 0.0195;
    Action SetShade = () -> {
        shade = 0.125;
    };
    GraphicsUpdate OnDark;
    GraphicsUpdate OnLight;
    Cartesian angle = new Cartesian(Linear.PID2, Linear.PID2, 0);
    Cartesian arcAngle = new Cartesian(0, 0, 0);
    Point at = new Point(0, Linear.MAX_MAP, -Linear.MAX_MAP, 7);
    double distance = Linear.MAX_MAP;

    SkyBody(Light lite, double dis) {
        light = light;
        distance = dis;
    }

    @Override
    public void Update(GameTime time, Camera cam) {
        double et = time.animTime / Physics.day * 10;
        angle.y += et;
        angle.x += et;
        color = new Pigment((int) Math.abs(specRed + energy + Math.abs(energy * Math.cos(angle.y))), specGreen + energy - (int) Math.abs(energy * Math.cos(angle.y)), specBlue + energy + (int) Math.abs(energy * Math.sin(angle.y)));
        //
        at.y = distance * Math.sin(angle.y);
        at.x = distance * Math.sin(angle.x);
        at.z = distance * Math.cos(angle.x);
        //
        light = 0.25 + 0.25 * Math.sin(angle.y);
        dark = arc + Math.sin(angle.y - arc);
        asscention = arc + Math.sin(angle.y - arc);
        arc = 3e-3 / ((dark * light) * Math.abs(Linear.PID2 + Math.cos(angle.y)) * Math.abs(Math.PI - Math.sin(angle.x + cam.angle.x)));
        directLight.direction = at.Sub(at, cam).Unity().AsNormal();
        directLight.brightness = 1.0f;
        directLight.color = new Pigment(color.Red(), color.Green(), color.Blue());
        if (arc < -arc) {
            arc = 0;
        } else {
            if (arc < arc) {
                arc = Math.abs(arc);
            }
        }
        if (dark < 0) {
            OnDark.Update(time, cam);
        } else {

            OnLight.Update(time, cam);
        }
        ambient = 1.0 / light;
        SetShade.active();
    }
}

class SkyScape {

    Point sun = new Point(0, Linear.MAX_MAP,0, 7);
    //Point moon[];
    Cartesian sunNorthAngle = new Cartesian(0 , 0 , 0);//sunrise in middle
    //Cartesian moonNorthAngle[];// = new Cartesian(-Math.PI * 0.5, 0, 0);
    //Cartesian moonArc[];// = new Cartesian(1.1, 1.2, 0);
    Color sunColor = new Color(196, 255, 196);
    RingList<Cluster> stars=new RingList();
    //Color moonColor[];// = new Color(196, 196, 255);
    double ambient = 1.0;
    double sunshade = 0.125;
    double light = 0.25;
    double night = 0.0195;
    double sunarc = 1e-3;
    double arc = 0.0195;
    double asscention = 0.0195;
    private int energy = 54;//   
    private int specRed = 255 - energy * 2;//max 255-energy*2    
    private int specGreen = 255 - energy * 2;//max 255-energy*2
    private int specBlue = 0;//max 255-energy*2
    Color skyColor = new Color(32, 64, 128);
    DirectionalLight sunLight = new DirectionalLight();
    //DirectionalLight moonLight[];// = new DirectionalLight();
    RingList<SkyBody> bodies;
    RingList<SkyBomb> explosions;
    StarSystem system;
    Planet home;
    double sunAnglex;
    double sunAngley;
    double sunAnglez;
    double moonAnglex;
    double moonAngley;
    double axialTilt;
    int day = 1;
    double year;
    double gmt;
    SmallPlanet planet;
    void SetUp(SmallPlanet p) {
        planet=p;
        sun = new Point(0, Linear.MAX_MAP*p.orbital.radius, -Linear.MAX_MAP*p.orbital.radius, 5*p.revolvesRound.relative.radius);
        //
    energy = (int)(256*p.revolvesRound.relative.mass/p.orbital.radius);//   
    specRed = (int)(energy *(p.revolvesRound.color.Red())*0.004);//max 255-energy*2    
    specGreen =(int)(energy *(p.revolvesRound.color.Green())*0.004);//max 255-energy*2
    specBlue =(int)(energy *(p.revolvesRound.color.Blue())*0.004);//max 255-energy*2
    sunColor=   p.revolvesRound.color.Color();
    skyColor=   p.air.Color();
        Cluster.CreateStarField(stars, new Molecula(p.revolvesRound.relative.mass,p.revolvesRound.relative.radius,p.revolvesRound.relative.density));
        //moon=new Point[p.moon.length];
        //moonNorthAngle=new Cartesian[p.moon.length];
        //moonArc=new Cartesian[p.moon.length];
        //moonColor=new Color[p.moon.length];
    }

    //
    void Update(GameTime time, Camera cam,double relativeTime) {
        double et = time.animTime / Physics.day * relativeTime;
        //sunNorthAngle.y += et;
        sunNorthAngle.x += et;
        sunNorthAngle.z += et;
        if (sunNorthAngle.x > Math.PI) {
            sunNorthAngle.x -= Linear.PI2;
            day++;
            if (day > 357) {
                day = 1;
            }
        }

        if (sunNorthAngle.z > Math.PI) {
            sunNorthAngle.z -= Linear.PI2;
        }
        if (sunNorthAngle.y > Math.PI) {
            sunNorthAngle.y -= Linear.PI2;
        }
        //moonNorthAngle.x += et * moonArc.x;
        //moonNorthAngle.y += et * moonArc.y;

        //if (moonNorthAngle.x > Linear.PI2) {
        //    moonNorthAngle.x -= Linear.PI2;
        //}
        //if (moonNorthAngle.y > Linear.PI2) {
        //    moonNorthAngle.y -= Linear.PI2;
        //}
        double x = ((double) (cam.local.x) + cam.fraction.x) / LandScape.ScapeSize;
        double y = ((double) (cam.local.y) + cam.fraction.y) / LandScape.ScapeSize;
        
        year=(Linear.PI2/356.25 *day );
        axialTilt=(((y-0.5)) * Linear.PID2)+(Math.cos(year)*0.3);
        sunAnglex = (sunNorthAngle.x + (x-0.5) * Math.PI);
        sunAngley = Linear.PID2+Linear.PID2*0.5;
        sunAnglez = (sunNorthAngle.z + (x-0.5) * Math.PI);
        if(sunAnglex>Math.PI){
        sunAnglex-=Linear.PI2;
        }
        if(sunAnglez>Math.PI){
        sunAnglez-=Linear.PI2;
        }
        //moonAnglex = (moonNorthAngle.x + x * Linear.PI2);
        //moonAngley = (moonNorthAngle.y + y * Linear.PI2) / (1.0 + (Math.sin(356.25 / day * Linear.PI2) * (y * Math.PI / 5.0)));
        
        //
        double sy=sun.y = Math.sin(sunAnglex+axialTilt);//starts with summer in north
        double sx=sun.x = -Math.sin(sunAnglex);//dawn in africa sun set in pacific(6 am GMT)
        double sz=sun.z = Math.cos(sunAnglex);
        gmt=((Math.PI+sunAnglex)/Linear.PI2)*24-6;
        if(gmt<0){gmt=24+gmt;}
        //galactic
        sun.x=sx*Math.cos(sunAngley)-sz*Math.sin(sunAngley);
        sun.z=sz*Math.cos(sunAngley)+sz*Math.sin(sunAngley);
        sx=sun.x;
        sz=sun.z;
        //
        sun.Scale(planet.orbital.radius*Linear.MAX_MAP );
        /*int r=(int)(specRed +specRed + energy + Math.abs(energy * Math.cos(sunAnglex)))>>1;
        int g=(int)(specGreen +specGreen + energy -  Math.abs(energy * Math.cos(sunAnglex)))>>1;
        int b=(int)(specBlue + specBlue + energy +  Math.abs(energy * Math.sin(sunAnglex)))>>1;*/
        int r=(int)(specRed +specRed + energy + Math.abs(energy * sy))>>1;
        int g=(int)(specGreen +specGreen + energy -  Math.abs(energy* sy))>>1;
        int b=(int)(specBlue + specBlue + energy +  Math.abs(energy * -sy))>>1;
        
        if(r>255){r=255;}
        else if(r<0){r=0;}
        if(g>255){g=255;}
        else if(g<0){g=0;}
        if(b>255){b=255;}
        else if(b<0){b=0;}
        sunColor = new Color(r,g,b);
        //moon.y = Linear.MAX_MAP * Math.sin(moonAngley);
        //moon.x = Linear.MAX_MAP * Math.sin(moonAnglex);
        //moon.z = Linear.MAX_MAP * Math.cos(moonAnglex);
        //
        light = 0.25 + 0.25 * sy;
        night = arc+sy;
        asscention = arc + Math.sin(moonAnglex - arc);
        sunarc = 3e-3 / ((night * light) * Math.abs(Linear.PID2 + sx) * Math.abs(Math.PI - Math.sin(sunAngley + cam.angle.y)));
        sunLight.direction = sun.Sub(sun, cam).Unity().AsNormal();
        sunLight.brightness = 1.0f;
        sunLight.color = new Pigment(sunColor.getRed(), sunColor.getGreen(), sunColor.getBlue());
        //moonLight.direction = sun.Sub(moon, cam).Unity().AsNormal();
        //moonLight.brightness = 0.25f;
        //moonLight.color = new Pigment(moonColor.getRed(), moonColor.getGreen(), moonColor.getBlue());
        if (sunarc < -arc) {
            sunarc = 0;
        } else {
            if (sunarc < arc) {
                sunarc = Math.abs(sunarc);
            }
        }
        if (night < 0) {
            
            //moonColor = new Color(196, 255, 255);
        } else {

            //moonColor = new Color(128, 196, 255);
        }
        ambient = 1.0 / light;
        sunshade = 0.125;
        
    }
    void Render(Screen scr,Point viewer){
            
        if (night < 0) {
            Quarternion ecliptic=new Quarternion(this.sunAnglex+year,new Normal(0,0,1)); 
            ecliptic=ecliptic.Mult(ecliptic,new Quarternion(axialTilt,new Normal(1,0,0)));
            scr.cam.linear=null;
            Cluster.StoreClusters(scr,viewer,ecliptic,stars);
            //moonColor = new Color(196, 255, 255);
        } else {

            //moonColor = new Color(128, 196, 255);
        }
    }
}