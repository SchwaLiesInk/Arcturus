/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class AUSorter implements java.util.Comparator<AstralBody> {

    @Override
    public int compare(AstralBody a, AstralBody b) {
        if (a != null && b != null) {
            if (a.orbit.Near() < b.orbit.Near()) {
                return -1;
            } else if (a.orbit.Near() > b.orbit.Near()) {
                return 1;
            }
        }
        return 0;
    }

}

/**
 *
 * @author Gerwyn Jones
 */
class Substance {

    public static final int StatePlasma = 1, StateGas = 2, StateDust = 4, StateSolid = 8, StateCrystal = 16, StateLiquid = 32;

    public static final int SubDwarf = 0, Dwarf = 1, Small = 2, Medium = 3, Main = 4, Large = 5, VeryLarge = 6, Giant = 7, SuperGiant = 8, HyperGiant = 9;

    public static final int Light = 1, LightCompact = 2, Compact = 3, VeryCompact = 4, Dense = 5, VeryDense = 6, SuperDense = 7, HyperDense = 8;
    //
    private double plasma;
    private double gas;
    private double dust;
    private double ice;
    private double rock;
    private double metal;
    private double radioactive;
    private double space;

    //
    public Substance(double plasma, double gas, double dust, double ice, double rock, double metal, double radioactive, double space) {
        this.plasma = plasma;
        this.gas = gas;
        this.dust = dust;
        this.ice = ice;
        this.rock = rock;
        this.metal = metal;
        this.radioactive = radioactive;
        this.space = space;
    }

    public void Check() {

        if (this.plasma < 0) {
            this.plasma = 0;
        }
        if (this.gas < 0) {
            this.gas = 0;
        }
        if (this.dust < 0) {
            this.dust = 0;
        }
        if (this.ice < 0) {
            this.ice = 0;
        }
        if (this.rock < 0) {
            this.rock = 0;
        }
        if (this.metal < 0) {
            this.metal = 0;
        }
        if (this.radioactive < 0) {
            this.radioactive = 0;
        }
        if (this.space < 1) {
            this.space = 1;
        }
    }

    //
    public Substance Add(Substance to, Substance add) {
        to.plasma += add.plasma;
        to.gas += add.gas;
        to.dust += add.dust;
        to.ice += add.ice;
        to.rock += add.rock;
        to.metal += add.metal;
        to.radioactive += add.radioactive;
        to.space += add.space;
        to.Check();
        return to;
    }

    //
    public Substance Sub(Substance to, Substance sub) {
        to.plasma -= sub.plasma;
        to.gas -= sub.gas;
        to.dust -= sub.dust;
        to.ice -= sub.ice;
        to.rock -= sub.rock;
        to.metal -= sub.metal;
        to.radioactive -= sub.radioactive;
        to.space -= sub.space;
        to.Check();

        return to;
    }

    public boolean Greater(Substance test, double n) {
        return test.plasma > n || test.gas > n || test.dust > n || test.ice > n || test.rock > n || test.metal > n || test.radioactive > n;
    }

    public boolean Less(Substance test, double n) {
        return test.Total() < n;
    }

    public double Total() {
        return this.plasma + this.gas + this.dust + this.ice + this.rock + this.metal + this.radioactive;
    }

    public double Plasma() {
        return this.plasma;
    }

    public double Gas() {
        return this.gas;
    }

    public double Dust() {
        return this.dust;
    }

    public double Ice() {
        return this.ice;
    }

    public double Rock() {
        return this.rock;
    }

    public double Metal() {
        return this.metal;
    }

    public double Rads() {
        return this.radioactive;
    }

    public double Space() {
        return this.space;
    }
}

class BodyOrbit {

    private double far;
    private double near;
    private double velocity;//+=clockwize
    private double spin;//+=clockwize
    private double tilt;
    private double wobble;
    private double nearTemperature;
    private double farTemperature;
    private AstralBody centerOfGravity;

    public BodyOrbit(double far, double near, double velocity, double spin, double tilt, double wobble) {
        if (far > near) {
            this.far = far;
            this.near = near;
        } else {
            this.far = near;
            this.near = far;
        }
        //System.Console.WriteLine(this.far);
        this.velocity = velocity;
        this.spin = spin;
        this.tilt = tilt;
        this.wobble = wobble;
        this.nearTemperature = 200;
        this.farTemperature = 200;
        this.centerOfGravity = null;
    }

    public AstralBody Center() {
        return this.centerOfGravity;
    }

    public void Center(AstralBody set) {

        this.centerOfGravity = set;
    }

    public double FarAU() {
        return this.far / AstralBody.AU;
    }

    public double NearAU() {
        return this.near / AstralBody.AU;
    }

    public double Far() {
        return this.far;
    }

    public double Near() {
        return this.near;
    }

    public double Velocity() {
        return this.velocity;
    }

    public void Velocity(double v) {

        this.velocity = v;
    }

    public double Spin() {
        return this.spin;
    }

    public void Spin(double s) {

        this.spin = s;
    }

    public double Tilt() {
        return this.tilt;
    }

    public double Wobble() {
        return this.wobble;
    }

    public void Wobble(double w) {
        this.wobble = w;
    }
}
/// <summary>
/// Summary description for Body.
/// </summary>

class AstralBody {

    public static final int Gas = 1, Dust = 2, Meteor = 3, Comet = 4, Planetoid = 5, Planet = 6, LiquidPlanet = 7, GasPlanet = 8, Ring = 9, Belt = 10, Star = 11;

    public static final double G = 6.67259e-11;//m^3/kg*s^2
    public static final double g = 9.807;//m/s^2
    public static final double GR = 1 + Math.sqrt(5) * 0.5;//1.118
    public static final double PI2 = 2 * Math.PI;
    public static final double MATTER = (Math.PI * Math.E * GR);//m/kg^0.5
    public static final double GRAVITY = (1 / Math.E + Math.PI);//
    public static final double RATIO = GRAVITY * GRAVITY * GRAVITY;//kg/m^2
    public static final double TIME = 1e6 * 365 * 24 * 60 * 60;
    public static final double AU = 1.496e11;
    public static double DISTANCE = 32 * AU;
    protected Substance contains;
    protected int state;
    protected int mark = 0;
    protected int type;
    protected int sizeType;
    protected int densityType;
    //
    protected double mass;
    protected double radius;
    protected double magnetics;
    protected double gravity;
    //
    protected RingList<AstralBody> satelites = new RingList();
    protected BodyOrbit orbit;

    public String ToString() {
        StringBuilder str = new StringBuilder();
        str.append("Body\n");
        str.append("Mass:" + this.mass + "\t");
        str.append("Radius:" + this.radius + "\t");
        str.append("Magnetics:" + this.magnetics + "\t");
        str.append("Gravity:" + this.gravity + "\n");
        str.append("Orbiting:\tFrom:" + this.orbit.NearAU() + "\tTo:" + this.orbit.FarAU() + "\n");
        return str.toString();
    }

    public RingList<AstralBody> Saterlites() {
        return this.satelites;
    }

    public AstralBody(int size, double massRatio, Random rnd, int type, Substance seed) {
        DISTANCE = 32 * AU * massRatio;
        this.type = type;
        double sz = Math.abs((double) (Integer.MAX_VALUE) - size);
        sz *= (rnd.NextDouble() * rnd.NextDouble() * 8 * massRatio);
        double sz1 = (sz) / GR;
        double sz2 = (sz) / Math.E;
        double sz3 = (sz) / Math.PI;
        double sz4 = (sz) / (PI2);
        switch (this.type) {
            case AstralBody.Gas: {
                //gas cloud
                double g = seed.Gas() * (rnd.NextDouble() / sz1);
                double d = seed.Dust() * (rnd.NextDouble() / sz2);
                double i = seed.Ice() * (rnd.NextDouble() / sz3);
                double rad = seed.Rads() * (rnd.NextDouble() / sz4);
                double s = seed.Space() * (rnd.NextDouble() / sz1);
                this.contains = new Substance(0, g, d, i, 0, 0, rad, s);
                double dis = DISTANCE * (rnd.NextDouble());
                double vel = Math.sqrt(DISTANCE / dis) * (rnd.NextDouble() / sz4);
                double spin = Math.sqrt(vel) * (rnd.NextDouble() / sz4);
                double tilt = Math.sqrt(spin) * (rnd.NextDouble() / sz4);
                double w = Math.sqrt(spin + tilt) * (rnd.NextDouble() / sz4);
                double f = dis * 2 * rnd.NextDouble() * rnd.NextDouble();
                double n = dis * 2 * rnd.NextDouble() * rnd.NextDouble();
                if (n > f) {
                    double swap = f;
                    f = n;
                    n = swap;
                }
                this.state = Substance.StateGas;
                this.orbit = new BodyOrbit(f, n, vel, spin, tilt, w);
                break;
            }
            case AstralBody.Dust: {
                //dust cloud
                double g = seed.Gas() * (rnd.NextDouble() / sz2);
                double d = seed.Dust() * (rnd.NextDouble() / sz1);
                double i = seed.Ice() * (rnd.NextDouble() / sz3);
                double rad = seed.Rads() * (rnd.NextDouble() / sz4);
                double s = seed.Space() * (rnd.NextDouble() / sz1);
                this.contains = new Substance(0, g, d, i, 0, 0, rad, s);
                double dis = DISTANCE * (rnd.NextDouble());
                double vel = Math.sqrt(DISTANCE / dis) * (rnd.NextDouble() / sz3);
                double spin = Math.sqrt(vel) * (rnd.NextDouble() / sz3);
                double tilt = Math.sqrt(spin) * (rnd.NextDouble() / sz3);
                double w = Math.sqrt(spin + tilt) * (rnd.NextDouble() / sz3);
                double f = dis * 2 * rnd.NextDouble();
                double n = dis * 2 * rnd.NextDouble();
                if (n > f) {
                    double swap = f;
                    f = n;
                    n = swap;
                }
                this.state = Substance.StateDust;
                this.orbit = new BodyOrbit(f, n, vel, spin, tilt, w);
                break;
            }

            case AstralBody.Meteor: {
                //meteor
                double g = seed.Gas() * (rnd.NextDouble() / sz3);
                double r = seed.Rock() * (rnd.NextDouble() / sz1);
                double m = seed.Metal() * (rnd.NextDouble() / sz2);
                double rad = seed.Rads() * (rnd.NextDouble() / sz3);
                double s = seed.Space() * (rnd.NextDouble() / sz4);
                this.contains = new Substance(0, g, 0, 0, r, m, rad, s);
                double dis = DISTANCE * (rnd.NextDouble());
                double vel = Math.sqrt(DISTANCE / dis) * (rnd.NextDouble() / sz2);
                double spin = Math.sqrt(vel) * (rnd.NextDouble() / sz2);
                double tilt = Math.sqrt(spin) * (rnd.NextDouble() / sz2);
                double w = Math.sqrt(spin + tilt) * (rnd.NextDouble() / sz2);
                double f = dis * 3 * rnd.NextDouble();
                double n = dis * 3 * rnd.NextDouble();
                if (n > f) {
                    double swap = f;
                    f = n;
                    n = swap;
                }
                this.state = Substance.StateSolid;
                this.orbit = new BodyOrbit(f, n, vel, spin, tilt, w);
                break;
            }

            case AstralBody.Comet: {
                //comet
                double g = seed.Gas() * (rnd.NextDouble() / sz3);
                double d = seed.Dust() * (rnd.NextDouble() / sz2);
                double i = seed.Ice() * (rnd.NextDouble() / sz1);
                double r = seed.Rock() * (rnd.NextDouble() / sz3);
                double s = seed.Space() * (rnd.NextDouble() / sz4);
                this.contains = new Substance(0, g, d, i, r, 0, 0, s);
                double dis = DISTANCE * (rnd.NextDouble());
                double vel = Math.sqrt(DISTANCE / dis) * (rnd.NextDouble() / sz1);
                double spin = Math.sqrt(vel) * (rnd.NextDouble() / sz1);
                double tilt = Math.sqrt(spin) * (rnd.NextDouble() / sz1);
                double w = Math.sqrt(spin + tilt) * (rnd.NextDouble() / sz1);
                double f = dis * 4 * rnd.NextDouble();
                double n = dis * 4 * rnd.NextDouble();
                if (n > f) {
                    double swap = f;
                    f = n;
                    n = swap;
                }
                this.state = Substance.StateCrystal;
                this.orbit = new BodyOrbit(f, n, vel, spin, tilt, w);
                break;
            }
        }
        //
        this.mass = this.contains.Total();
        this.CalcStartingRadius();
        this.CalcGravity();
    }

    public void StellarNuclearSynthisis() {
        this.type = AstralBody.Star;
        this.radius += (this.radius / Math.PI);
        this.magnetics /= Math.PI;
        double p = this.contains.Rock() + this.contains.Rads() + this.contains.Plasma() + this.contains.Metal() + this.contains.Ice() + this.contains.Gas() + this.contains.Dust();
        double s = this.contains.Space() / Math.PI;
        this.contains = new Substance(p, 0, 0, 0, 0, 0, 0, s);
        this.CalcMass();
        this.CalcGravity();
        this.SetBodyType();
    }

    public void SolarWind(AstralBody star) {
        double mr = (star.mass * star.radius) / (Math.abs(this.mass * this.orbit.Near()) + this.magnetics);
        double p = this.contains.Plasma() - this.contains.Plasma() * mr;
        if (p < 0) {
            p = 0;
        }
        double g = this.contains.Gas() - this.contains.Gas() * mr;
        if (g < 0) {
            g = 0;
        }
        this.contains = new Substance(p, g, this.contains.Dust(), this.contains.Ice(), this.contains.Rock(), this.contains.Metal(), this.contains.Rads(), this.contains.Space());
        this.CalcMass();
        this.CalcGravity();
    }

    public void SetBodyType() {
        if (this.type != AstralBody.Ring && this.type != AstralBody.Belt && this.type != AstralBody.Star) {
            double c = this.contains.Ice() + this.contains.Gas();
            double b = this.contains.Dust() + this.contains.Rock();
            double m = +this.contains.Rads() + this.contains.Metal() + this.contains.Dust();
            double g = this.contains.Plasma() + this.contains.Gas() + this.contains.Ice();
            //
            if ((g / m) < this.gravity) {
                this.type = AstralBody.LiquidPlanet;
            } else if (g > m) {
                this.type = AstralBody.GasPlanet;
            } else {
                if (c > b) {
                    this.type = AstralBody.Planet;
                } else {
                    this.type = AstralBody.Planetoid;
                }
            }
        }
        //
        if (this.type == AstralBody.Star) {
            this.sizeType = ((int) (Math.log10(Math.sqrt(this.mass)) / Math.PI) - 1);
            this.densityType = ((int) (Math.log10(this.mass / this.radius) / Math.PI) - 1);
        } else {
            this.sizeType = ((int) (Math.log10(this.mass) / Math.PI) - 1);
            this.densityType = ((int) (Math.log10(this.mass / this.radius) / Math.PI) - 1);
        }
    }

    public void Belter() {
        this.type = AstralBody.Belt;
        this.radius = Math.abs(this.orbit.Far() - this.orbit.Near());
        this.contains = new Substance(0, 0, this.contains.Dust(), this.contains.Ice(), this.contains.Rock(), this.contains.Metal(), this.contains.Rads(), this.contains.Space() * Math.PI * 2);
        this.CalcMass();
        this.CalcGravity();
        this.SetBodyType();
    }

    public AstralBody(double mass, double rad, double mag, int type, BodyOrbit orb) {
        this.type = type;
        this.mass = mass;
        this.radius = rad;
        this.magnetics = mag;
        this.orbit = orb;
        this.CalcGravity();
    }

    public AstralBody(Substance stuff, double rad, double mag, int type, BodyOrbit orb) {
        this.contains = stuff;
        this.type = type;
        this.mass = this.contains.Total();
        this.radius = rad;
        this.magnetics = mag;
        this.orbit = orb;
        this.CalcGravity();
    }

    public void CalcStartingRadius() {

        double r1 = (this.mass / this.contains.Space());
        if (Substance.StatePlasma == (this.state & Substance.StatePlasma)) {
            this.radius = r1 * Math.PI * 12;
        } else {
            if (Substance.StateGas == (this.state & Substance.StateGas)) {
                this.radius = r1 * Math.PI * 8;
            } else if (Substance.StateCrystal == (this.state & Substance.StateCrystal)) {
                this.radius = r1 * Math.PI * 6;
            } else if (Substance.StateDust == (this.state & Substance.StateDust)) {
                this.radius = r1 * Math.PI * 4;
            }
            if (Substance.StateLiquid == (this.state & Substance.StateLiquid)) {
                this.radius = r1 * Math.PI * 3;
            } else {
                this.radius = r1 * Math.PI * 2;
            }
        }
        this.radius = 4 * Math.sqrt(this.radius * Math.PI * 2);
    }

    public double CalcMass() {
        this.magnetics = this.contains.Metal() * this.radius;
        this.mass = this.contains.Total();
        return this.mass;
    }

    public double Magnetism() {
        return this.magnetics;
    }

    public BodyOrbit Orbital() {
        return this.orbit;
    }

    public int Type() {
        return this.type;
    }

    public int SizeType() {
        return this.sizeType;
    }

    public int DensityType() {
        return this.densityType;
    }

    public String TypeString() {
        switch (this.type) {
            case AstralBody.Gas: {
                return "gas";
            }
            case AstralBody.Dust: {
                return "dust";
            }
            case AstralBody.Meteor: {
                return "meteor";
            }
            case AstralBody.Comet: {
                return "comet";
            }
            case AstralBody.Planetoid: {
                return "planetoid";
            }
            case AstralBody.Planet: {
                return "planet";
            }
            case AstralBody.GasPlanet: {
                return "gas giant";
            }
        }
        return "star";

    }

    public double Mass() {
        return this.mass;
    }

    public double Radius() {
        return this.radius;
    }

    public double Gravity() {
        return this.gravity;
    }

    public double Velocity() {
        return this.orbit.Velocity();
    }
    /*public void SetAsCenter(){
     this.orbit=new Orbit(0,0,0,this.orbit.Spin,this.orbit.Tilt,this.orbit.Wobble);
     }*/

    public void Effect(double effect) {
        this.orbit.Velocity(this.orbit.Velocity() + effect);
        this.orbit.Spin(this.orbit.Spin() + effect);
        this.orbit.Wobble(this.orbit.Wobble() + effect);
        this.orbit.Velocity(this.orbit.Velocity() * 0.5);
        this.orbit.Spin(this.orbit.Spin() * 0.5);
        this.orbit.Wobble(this.orbit.Wobble() * 0.5);
    }

    public double FarAU() {
        return this.orbit.FarAU();
    }

    public double NearAU() {
        return this.orbit.NearAU();
    }

    public void Stabilize() {
        double f = Math.abs((this.orbit.Far() + this.orbit.Wobble()) * 0.5);
        double n = Math.abs((this.orbit.Near() + this.orbit.Wobble()) * 0.5);
        double v = Math.abs(this.orbit.Velocity() * 0.5);
        if (f > n) {
            this.orbit = new BodyOrbit(f, n, v, this.orbit.Spin() * 0.5, this.orbit.Tilt() * 0.5, this.orbit.Wobble());
        } else {
            this.orbit = new BodyOrbit(n, f, v, this.orbit.Spin() * 0.5, this.orbit.Tilt() * 0.5, this.orbit.Wobble());
        }
    }

    public void CenterOfGravity(AstralBody center) {
        this.orbit.Center(center);
        double f = Math.abs(center.orbit.Far() - this.orbit.Far());
        double n = Math.abs(this.orbit.Near() - center.orbit.Near());
        double v = Math.abs(this.orbit.Velocity() - center.orbit.Velocity());
        if (f > n) {
            this.orbit = new BodyOrbit(f, n, v, this.orbit.Spin(), this.orbit.Tilt(), this.orbit.Wobble() + center.orbit.Wobble());
        } else {
            this.orbit = new BodyOrbit(n, f, v, this.orbit.Spin(), this.orbit.Tilt(), this.orbit.Wobble() + center.orbit.Wobble());
        }
    }

    public void CalcGravity() {
        this.gravity = ((this.mass * G) / (this.radius * this.radius)) / g;
    }

    public double ForceOfGravity(double m, double dis) {
        return ((this.mass * m * G) / (dis * dis));
    }

    public static double ForceOfGravity(double m1, double m2, double dis) {
        return ((m1 * m2 * G) / (dis * dis));
    }

    public static double Escape(double mass, double distance) {
        return Math.sqrt((mass * 2 * G) / distance);
    }

    //m^2
    public static double AccretionVolume(double farDistance, double nearDistance, double velocity, double radius) {
        double a = farDistance - nearDistance;
        double d = Math.PI * a * a;
        double c = 2 * Math.PI * (a / 2);
        double v = 4 / 3 * Math.PI * radius * radius * radius;
        return (d * c + v) / velocity;
    }

    //KG/m^2
    public static double AccretionArea(double mass1, double mass2, double centralMass, double farDistance, double nearDistance, double velocity) {
        double d = farDistance - nearDistance;
        double c = (farDistance + nearDistance) * 0.5;
        double m = (mass1 + mass2) / d;
        return (m - (centralMass / c)) / velocity;
    }

    public void Absorb(AstralBody b) {
        double sat = b.mass;
        double rad = (b.radius > this.radius) ? (b.radius / this.radius) : (this.radius / b.radius);
        double mag = b.magnetics;
        this.mass += sat;
        this.radius += rad;
        this.magnetics += mag;
        double plasma = (b.contains.Plasma());
        double gas = (b.contains.Gas());
        double dust = (b.contains.Dust());
        double ice = (b.contains.Ice());
        double rock = (b.contains.Rock());
        double metal = (b.contains.Metal());
        double radio = (b.contains.Rads());
        double space = (b.contains.Space());
        //
        this.contains = new Substance(this.contains.Plasma() + plasma, this.contains.Gas() + gas, this.contains.Dust() + dust, this.contains.Ice() + ice,
                this.contains.Rock() + rock, this.contains.Metal() + metal, this.contains.Rads() + radio, this.contains.Space() + space);

    }

    public void Meld(AstralBody b) {
        BodyOrbit mid = new BodyOrbit((this.orbit.Far() + b.orbit.Far()) * 0.5, (this.orbit.Near() + b.orbit.Near()) * 0.5, (this.orbit.Velocity() + b.orbit.Velocity()) * 0.5,
                (this.orbit.Spin() + b.orbit.Spin()) * 0.5, (this.orbit.Tilt() + b.orbit.Tilt()) * 0.5, (this.orbit.Wobble() + b.orbit.Wobble()) / 2);
        //
        this.orbit = mid;
        double sat = b.mass;
        double rad = (b.radius > this.radius) ? (b.radius / this.radius) : (this.radius / b.radius);
        double mag = b.magnetics;
        this.mass += sat;
        this.radius += rad;
        this.magnetics += mag;
        mid.Center(this);
        double plasma = (b.contains.Plasma());
        double gas = (b.contains.Gas());
        double dust = (b.contains.Dust());
        double ice = (b.contains.Ice());
        double rock = (b.contains.Rock());
        double metal = (b.contains.Metal());
        double radio = (b.contains.Rads());
        double space = (b.contains.Space());
        //
        this.contains = new Substance(this.contains.Plasma() + plasma, this.contains.Gas() + gas, this.contains.Dust() + dust, this.contains.Ice() + ice,
                this.contains.Rock() + rock, this.contains.Metal() + metal, this.contains.Rads() + radio, this.contains.Space() + space);
        //
    }

    public void Add(AstralBody b) {
        if (b == null) {
            return;
        }
        BodyOrbit mid = new BodyOrbit((this.orbit.Far() + b.orbit.Far()) * 0.5, (this.orbit.Near() + b.orbit.Near()) * 0.5, (this.orbit.Velocity() + b.orbit.Velocity()) * 0.5,
                (this.orbit.Spin() + b.orbit.Spin()) * 0.5, (this.orbit.Tilt() + b.orbit.Tilt()) * 0.5, (this.orbit.Wobble() + b.orbit.Wobble()) * 0.5);
        //
        this.orbit = mid;
        double sat = b.mass / 4;
        double rad = (b.radius > this.radius) ? (b.radius / this.radius) : (this.radius / b.radius);
        double mag = b.magnetics / 2;
        this.mass += sat;
        this.radius += rad;
        this.magnetics += mag;
        mid.Center(this);
        AstralBody satelite = new AstralBody(sat, rad, mag, AstralBody.Planetoid, mid);
        double nplasma = (b.contains.Plasma()) / 4;
        double ngas = (b.contains.Gas()) / 4;
        double ndust = (b.contains.Dust()) / 4;
        double nice = (b.contains.Ice()) / 4;
        double nrock = (b.contains.Rock()) / 4;
        double nmetal = (b.contains.Metal()) / 4;
        double nradio = (b.contains.Rads()) / 4;
        double nspace = (b.contains.Space()) / 4;
        //
        for (Node<AstralBody> a = b.satelites.Start(); a.data != null; a = a.next) {

            AstralBody st = a.data;
            double f = Math.abs((st.orbit.Far() - this.orbit.Far()) / this.radius);
            double n = Math.abs((this.orbit.Near() - st.orbit.Near()) / this.radius);
            double v = Math.abs(this.orbit.Velocity() - st.orbit.Velocity());
            if (f > n) {
                st.orbit = new BodyOrbit(f, n, v, st.orbit.Spin(), st.orbit.Tilt(), st.orbit.Wobble() + this.orbit.Wobble());
            } else {
                st.orbit = new BodyOrbit(n, f, v, st.orbit.Spin(), st.orbit.Tilt(), st.orbit.Wobble() + this.orbit.Wobble());
            }
            this.satelites.Append(st);

        }
        //
        double p = nplasma / (this.satelites.size() + 1);
        double g = ngas / (this.satelites.size() + 1);
        double d = ndust / (this.satelites.size() + 1);
        double i = nice / (this.satelites.size() + 1);
        double r = nrock / (this.satelites.size() + 1);
        double m = nmetal / (this.satelites.size() + 1);
        double ra = nradio / (this.satelites.size() + 1);
        double sp = nspace / (this.satelites.size() + 1);
        //
        for (Node<AstralBody> a = this.satelites.Start(); a.data != null; a = a.next) {

            AstralBody st = a.data;
            st.contains = new Substance(st.contains.Plasma() + p, st.contains.Gas() + g, st.contains.Dust() + ndust + d, st.contains.Ice() + i,
                    st.contains.Rock() + r, st.contains.Metal() + m, st.contains.Rads() + ra, st.contains.Space() + sp);
        }
        if (b.type == AstralBody.Gas) {
            this.contains = new Substance(this.contains.Plasma() + nplasma * 3 + p, this.contains.Gas() + ngas * 3 + g, this.contains.Dust() + ndust * 3 + d, this.contains.Ice() + nice * 3 + i,
                    this.contains.Rock() + nrock * 3 + r, this.contains.Metal() + nmetal * 3 + m, this.contains.Rads() + nradio * 3 + ra, this.contains.Space() + nspace * 3 + sp);
        } else if (b.type == AstralBody.Dust) {
            this.contains = new Substance(this.contains.Plasma() + nplasma * 3, this.contains.Gas() + ngas * 3, this.contains.Dust() + ndust * 3, this.contains.Ice() + nice * 3,
                    this.contains.Rock() + nrock * 3, this.contains.Metal() + nmetal * 3, this.contains.Rads() + nradio * 3, this.contains.Space() + nspace * 3);
            satelite.contains = new Substance(p, g, d, i, r + this.radius, m, ra, sp + this.radius * this.radius * r * Math.PI);
            satelite.type = AstralBody.Ring;
            double f = Math.abs((b.orbit.Far() + this.orbit.Far()) / this.radius);
            double n = Math.abs((this.orbit.Near() + b.orbit.Near()) / this.radius);
            double v = (this.orbit.Velocity() + b.orbit.Velocity()) * 0.5;
            satelite.orbit = new BodyOrbit(n, f, v, b.orbit.Spin() + this.orbit.Spin(), b.orbit.Tilt(), b.orbit.Wobble() + this.orbit.Wobble());
            this.satelites.Append(satelite);
        } else {
            this.contains = new Substance(this.contains.Plasma() + nplasma * 3, this.contains.Gas() + ngas * 3, this.contains.Dust() + ndust * 3, this.contains.Ice() + nice * 3,
                    this.contains.Rock() + nrock * 3, this.contains.Metal() + nmetal * 3, this.contains.Rads() + nradio * 3, this.contains.Space() + nspace * 3);
            satelite.contains = new Substance(p, g, d, i, r, m, ra, sp);
            double f = Math.abs((b.orbit.Far() + this.orbit.Far()) / this.radius);
            double n = Math.abs((this.orbit.Near() + b.orbit.Near()) / this.radius);
            double v = (this.orbit.Velocity() + b.orbit.Velocity()) * 0.5;
            satelite.orbit = new BodyOrbit(n, f, v, b.orbit.Spin() + this.orbit.Spin(), b.orbit.Tilt(), b.orbit.Wobble() + this.orbit.Wobble());
            satelite.type = AstralBody.Planetoid;
            satelite.CalcStartingRadius();
            satelite.CalcGravity();
            this.satelites.Append(satelite);
        }
    }

    public void Condense(AstralBody central, double time) {
        this.CalcGravity();
        double s = time * (356.25 * 24 * 60 * 60 * Accretion.Span);
        double r1 = Math.abs(this.radius - 8 * Math.PI * (this.radius * this.gravity * s) / (this.mass));
        this.radius = (3 * this.radius + r1) / 4;
        double d = this.contains.Dust() / 4;
        this.contains = new Substance(this.contains.Plasma(), this.contains.Gas(), d, this.contains.Ice(), this.contains.Rock() + d, this.contains.Metal() + d, this.contains.Rads() + d, this.contains.Space() / 4);
        this.CalcGravity();
        for (Node<AstralBody> a = this.satelites.Start(); a.data != null; a = a.next) {
            a.data.Condense(central, time);
        }
        double f = Math.abs(this.orbit.Far() + this.radius * Math.PI * 2);
        double n = Math.abs(this.orbit.Near() + this.radius * Math.PI * 2);
        double m = (f + n) / 2;
        double t = (Accretion.BodyZ() * Accretion.BodyZ() * Accretion.BodyZ() * 2 * Math.PI) / time;
        f = (m + f * t) / (1 + t);
        n = (m + n * t) / (1 + t);
        if (f > n) {

            double r = (2 * Math.PI * central.radius) / n / AU;
            double rv = (r * Accretion.Span * Accretion.Span) / s;
            double v = (3 * this.orbit.Velocity() + rv) / 4;
            this.orbit = new BodyOrbit(n, f, v, this.orbit.Spin(), this.orbit.Tilt(), this.orbit.Wobble());
        } else {
            double r = (2 * Math.PI * central.radius) / f / AU;
            double rv = (r * Accretion.Span * Accretion.Span) / s;
            double v = (3 * this.orbit.Velocity() + rv) / 4;
            this.orbit = new BodyOrbit(f, n, v, this.orbit.Spin(), this.orbit.Tilt(), this.orbit.Wobble());
        }
    }

    public boolean Collision(AstralBody with, AstralBody center, Double effect, double prob) {
        //
        if (with != center) {
            if (with == null) {
                return true;
            }
            double d = with.radius + this.radius + (Math.abs(this.orbit.Far() - this.orbit.Near()) + Math.abs(with.orbit.Far() - with.orbit.Near())) * Math.PI * 2;
            //double d=0;
            boolean collide = ((this.orbit.Near() < with.orbit.Far() + d && this.orbit.Far() > with.orbit.Far() - d)
                    || (this.orbit.Far() > with.orbit.Near() - d && this.orbit.Far() < with.orbit.Far() + d)
                    || (this.orbit.Near() < with.orbit.Near() + d && this.orbit.Far() > with.orbit.Far() - d));
            //
            effect = AccretionArea(with.mass, this.mass, center.mass, with.orbit.Far(), with.orbit.Near(), this.orbit.Velocity())
                    / AccretionArea(this.mass, with.mass, center.mass, this.orbit.Far(), this.orbit.Near(), this.orbit.Velocity());
            /*effect+=AccretionArea(this.mass,with.mass,center.mass,this.orbit.Far,this.orbit.Near,this.orbit.Velocity)
             /AccretionArea(with.mass,this.mass,center.mass,with.orbit.Far,with.orbit.Near,this.orbit.Velocity);
             */
            if (collide) {
                double p = prob / (Accretion.accrete.Bodies() + 314);
                //
                //double chance=AccretionVolume(with.orbit.Far,with.orbit.Near,with.orbit.Velocity,with.radius)/AccretionVolume(this.orbit.Far,this.orbit.Near,this.orbit.Velocity,this.radius);
                double chance = AccretionVolume(this.orbit.Far(), this.orbit.Near(), this.orbit.Velocity(), this.radius) / AccretionVolume(with.orbit.Far(), with.orbit.Near(), with.orbit.Velocity(), with.radius);
                //bool collide=chance>(Game.Rnd.Next((int)(p))/(p)*prob);
                collide = collide && chance > (Molecula.rnd.nextDouble() * p);
                //
                //System.out.println("Chance:"+chance+" "+prob+"\tEffect:"+effect);
                //System.Console.WriteLine(this.ToString());
                //System.Console.WriteLine("Collided With:");
                //System.Console.WriteLine(with.ToString());
                //System.Console.WriteLine("::");
            }
            return collide;
        }
        return false;
    }
}

public class Accretion implements java.util.stream.Collector<Node<AstralBody>, RingList<AstralBody>, RingList<AstralBody>> {

    public static Accretion accrete;
    public static final double Detail = 1e24;
    public static final double Span = 1e5;
    public static final double Milenia = 1e-5;
    protected RingList<AstralBody> bodies = new RingList<>();
    //
    protected double billionsOfYears = 0.001;
    protected int startingObjects;
    protected int attractedObjects;
    protected double attractedMass;
    protected double totalMass;
    protected double condenseTime;
    protected static int bodyZ = 0;
    protected int lastBodyCount = 0;
    protected int lastSatCount = 0;
    protected int satCount = 0;
    protected int stableCount = 0;
    protected int swallowedPlanets = 0;
    protected RingList<AstralBody> stars = new RingList<>();
    protected int nova = 0;
    protected float spin;
    protected static float angle;
    protected ProcessState state;
    protected AstralBody central;

    public Accretion(String name) {
        Random rnd = new Random();
        double mr = 1.0 / 4.0 + (rnd.NextDouble() + rnd.NextDouble() + rnd.NextDouble() + rnd.NextDouble()) * 0.5;
        //this.millionsOfYears=360*(rnd.NextDouble()+rnd.NextDouble());
        this.startingObjects = (int) (1000 + (int) (2000 * (rnd.NextDouble() + rnd.NextDouble())) * mr);
        //this.startingObjects=(int)(10000000);
        this.bodies = new RingList<>();
        this.spin = (float) ((Math.PI * 2) / this.bodies.size());
        SuperNova(mr, rnd);
        central = this.bodies.First();
    }

    public double BillionsOfYears() {
        return this.billionsOfYears;
    }

    public int Bodies() {
        return this.bodies.size();
    }

    public int StartingBodies() {
        return this.bodies.size();
    }

    public static int BodyZ() {
        return bodyZ;
    }

    public static float Angle() {
        return angle;
    }

    protected Substance Seed(double massRatio, Random rnd) {
        double g = massRatio * (3e31 + (3e32 * (rnd.NextDouble() + rnd.NextDouble())));
        double d = massRatio * (2e29 + (2e30 * rnd.NextDouble()));
        double i = massRatio * (1e27 + (1e29 * rnd.NextDouble()));
        double r = massRatio * (1e26 + (1e28 * rnd.NextDouble()));
        double m = massRatio * (1e25 + (1e27 * rnd.NextDouble()));
        double rad = massRatio * (1e24 + (1e25 * rnd.NextDouble()));
        double s = massRatio * (1e22 * rnd.NextDouble());
        Substance seed = new Substance(0, g, d, i, r, m, rad, s);

        return seed;
    }

    protected void SuperNova(double massRatio, Random rnd) {
        int obj = 2;
        Substance seed = Seed(massRatio, rnd);
        AstralBody massive = new AstralBody(obj, massRatio, rnd, AstralBody.Gas, seed);
        this.totalMass = massive.CalcMass();
        this.bodies.Append(massive);
        int y = 1;
        int o = obj;
        //
        System.out.println("Super Nova:");
        System.out.println(this.startingObjects + ":" + seed.Total());
        do {
            int x = obj;
            o += obj;
            obj += o;
            do {
                int t = rnd.Next(1, 8);
                if (t < 4) {
                    t = 1;
                } else if (t < 6) {
                    t = 2;
                } else if (t < 7) {
                    t = 3;
                } else {
                    t = 4;
                }
                AstralBody body = new AstralBody(o - rnd.Next(1, x) + rnd.Next(1, x), massRatio, rnd, (t), seed);
                this.totalMass += body.CalcMass();
                this.bodies.Append(body);
                o -= y;
            } while (o > 0);
            System.out.println("SubTotal:" + this.totalMass + ":" + obj);
        } while (obj < this.startingObjects);
        this.attractedObjects = obj - this.startingObjects;
        System.out.println("Attracted::" + this.attractedObjects);
        this.attractedMass = (seed.Total() - this.totalMass);//(this.totalMass);
        System.out.println("Passing Mass::" + this.attractedMass);
        System.out.println("Bodies " + this.bodies.size());

    }

    public GImage FarLinearMap(int w, int h, double massAbove) {
        int cw = w >> 1;
        int ch = h >> 1;
        boolean odd = w % cw > 0;
        GImage map = new GImage(w, h);
        int[] A = new int[h];
        int[] G = new int[h];
        double PI1 = Math.PI * h;
        for (AstralBody obj : this.bodies) {
            if (obj.Mass() > massAbove) {
                int au = (int) (obj.FarAU() / 1000);
                Pigment p = new Pigment(map.bits.getRGB(cw + A[au], au));
                int g = (int) (Math.log10(Math.pow((obj.Mass() - massAbove), 8)));
                G[au] = (int) (p.Red() + p.Green() + p.Blue() + g + (1111 - au) / Math.PI);
                G[au] >>= 2;
                //
                if (G[au] > 255) {
                    G[au] = 255;
                }
                Pigment c = new Pigment(0, 0, 0);
                switch (obj.Type()) {
                    case AstralBody.Gas: {
                        c = new Pigment(0, G[au], 0);
                        break;
                    }
                    case AstralBody.Dust: {
                        c = new Pigment(G[au], G[au], G[au]);
                        break;
                    }
                    case AstralBody.Meteor: {
                        c = new Pigment(G[au], 0, 0);
                        break;
                    }
                    case AstralBody.Comet: {
                        c = new Pigment(0, 0, G[au]);
                        break;
                    }
                    case AstralBody.Planetoid: {
                        c = new Pigment(G[au], G[au], 0);
                        break;
                    }
                    case AstralBody.Planet: {
                        c = new Pigment(0, G[au], G[au]);
                        break;
                    }
                    case AstralBody.LiquidPlanet: {
                        c = new Pigment(0, 0, G[au]);
                        break;
                    }
                    case AstralBody.GasPlanet: {
                        c = new Pigment(G[au], 0, G[au]);
                        break;
                    }
                    case AstralBody.Star: {
                        c = new Pigment(255, 255, 255);
                        break;
                    }
                }
                double B = Math.sqrt(obj.FarAU());
                A[au] += (int) (1 + B + B / PI1);
                if (odd) {
                    if (A[au] > cw) {
                        A[au] -= w;
                    }
                } else {
                    if (A[au] >= cw) {
                        A[au] -= w;
                    }
                }
                Pigment o = new Pigment((p.R + c.R) >> 1, (p.G + c.G) >> 1, (p.B + c.B) >> 1);
                map.AddPixel(cw + A[au], au, o.Alpha(), o.Red(), o.Green(), o.Blue());
            }
        }
        return map;
    }

    public GImage FarRadialMap(int w, int h, double massAbove) {
        int objects = 0;
        int cw = w >> 1;
        int ch = h >> 1;
        boolean odd = w % cw > 0;
        GImage map = new GImage(w, h);
        double[] A = new double[h];
        int[] G = new int[h];
        double PI1 = Math.PI * h;
        double PI2 = Math.PI * 2;
        double PI4 = PI2 * 2;
        double PI8 = PI4 * 2;
        double PISQR = Math.PI * Math.PI;
        double PISQR2 = PISQR * PISQR;
        double PISQR4 = PISQR2 * PISQR2;
        this.bodies.First();
        for (AstralBody obj : this.bodies) {
            if (obj.Mass() > massAbove) {
                objects++;
                int au = (int) (obj.FarAU() / 1000);
                double AU = (obj.FarAU() / 1000);
                Pigment p = new Pigment(map.bits.getRGB(cw + (int) (au * Math.cos(A[au])), ch + (int) (au * Math.sin(A[au]))));
                int g = (int) (Math.log10(Math.pow((obj.Mass() - massAbove), 8)));
                G[au] = (int) (p.Red() + p.Green() + p.Blue() + g + (1111 - AU) / Math.PI);
                G[au] >>= 2;
                //
                if (G[au] > 255) {
                    G[au] = 255;
                }
                Pigment c = new Pigment(0, 0, 0);
                switch (obj.Type()) {
                    case AstralBody.Gas: {
                        c = new Pigment(0, G[au], 0);
                        break;
                    }
                    case AstralBody.Dust: {
                        c = new Pigment(G[au], G[au], G[au]);
                        break;
                    }
                    case AstralBody.Meteor: {
                        c = new Pigment(G[au], 0, 0);
                        break;
                    }
                    case AstralBody.Comet: {
                        c = new Pigment(0, 0, G[au]);
                        break;
                    }
                    case AstralBody.Planetoid: {
                        c = new Pigment(G[au], G[au], 0);
                        break;
                    }
                    case AstralBody.Planet: {
                        c = new Pigment(0, G[au], G[au]);
                        break;
                    }
                    case AstralBody.LiquidPlanet: {
                        c = new Pigment(0, 0, G[au]);
                        break;
                    }
                    case AstralBody.GasPlanet: {
                        c = new Pigment(G[au], 0, G[au]);
                        break;
                    }
                    case AstralBody.Star: {
                        c = new Pigment(255, 255, 255);
                        break;
                    }
                }
                double B = Math.sqrt(obj.FarAU());
                //A[au]+=(PI2/(Math.PI/B));//core
                A[au] += (PI2 / (1 + B / AU));//blast
                //A[au]+=(PI2/(1+B+B/PI2));//spin
                if (A[au] > PI2) {
                    A[au] -= PI4;
                }
                Pigment o = new Pigment((p.Red() + c.Red()) >> 1, (p.Green() + c.Green()) >> 1, (p.Blue() + c.Blue()) >> 1);
                map.AddPixel(cw + (int) (AU * Math.cos(A[au])), ch + (int) (AU * Math.sin(A[au])), o.Alpha(), o.Red(), o.Green(), o.Blue());
            }
        }
        System.out.println(objects + ": Objects Above :" + massAbove + " Kg");
        return map;
    }

    public int[][] FarRadialMapData(int w, int h, double massAbove) {
        int objects = 0;
        int cw = w >> 1;
        int ch = h >> 1;
        double r = (Math.sqrt(w * w + h * h));
        boolean odd = w % cw > 0;
        int map[][] = new int[w][h];//,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        double[] A = new double[h];
        int[] G = new int[h];
        double PI1 = Math.PI * h;
        double PI2 = Math.PI * 2;
        double PI4 = PI2 * 2;
        double PI8 = PI4 * 2;
        double PISQR = Math.PI * Math.PI;
        double PISQR2 = PISQR * PISQR;
        double PISQR4 = PISQR2 * PISQR2;
        ArrayList<AstralBody> store = new ArrayList<>(this.bodies);
        store.addAll(this.bodies.First().Saterlites());
        for (AstralBody obj : store) {
            if (obj.Mass() > massAbove) {
                objects++;
                int au = (int) (Math.abs(obj.FarAU()) * PI4);
                double AU = (Math.abs(obj.FarAU()) * PI4);
                if (au > 0 && au < r && au != 0) {
                    int x = (int) (cw + AU * Math.cos(A[au]));
                    int y = (int) (ch + AU * Math.sin(A[au]));
                    try {
                        Pigment p = new Pigment((int) map[x][y]);
                        int g = (int) (Math.log10(Math.pow((obj.Mass() - massAbove), 8)));
                        G[au] = (int) (p.Red() + p.Green() + p.Blue() + g + (r - AU) / Math.PI);
                        G[au] >>= 2;
                        //
                        if (G[au] > 255) {
                            G[au] = 255;
                        }
                        Pigment c = new Pigment(0, 0, 0);
                        switch (obj.Type()) {
                            case AstralBody.Gas: {
                                c = new Pigment(0, G[au], 0);
                                break;
                            }
                            case AstralBody.Dust: {
                                c = new Pigment(G[au], G[au], G[au]);
                                break;
                            }
                            case AstralBody.Meteor: {
                                c = new Pigment(G[au], 0, 0);
                                break;
                            }
                            case AstralBody.Comet: {
                                c = new Pigment(0, 0, G[au]);
                                break;
                            }
                            case AstralBody.Planetoid: {
                                c = new Pigment(G[au], G[au], 0);
                                break;
                            }
                            case AstralBody.Planet: {
                                c = new Pigment(0, G[au], G[au]);
                                break;
                            }
                            case AstralBody.LiquidPlanet: {
                                c = new Pigment(0, 0, G[au]);
                                break;
                            }
                            case AstralBody.GasPlanet: {
                                c = new Pigment(G[au], 0, G[au]);
                                break;
                            }
                            case AstralBody.Belt: {
                                c = new Pigment(G[au], 0, 0);
                                break;
                            }
                            case AstralBody.Star: {
                                c = new Pigment(255, 255, 255);
                                break;
                            }
                        }
                        double B = Math.sqrt(obj.FarAU());
                        //A[au]+=(PI2/(Math.PI/B));//core
                        A[au] += (PI2 / (1 + B / AU)) + obj.Velocity();//blast (z=*v^2)
                        //A[au]+=(PI2/(1+B+B/PI2));//spin
                        if (A[au] > PI2) {
                            A[au] -= PI4;
                        }
                        int xx = (int) (cw + AU * Math.cos(A[au]));
                        int yy = (int) (ch + AU * Math.sin(A[au]));
                        Pigment o = new Pigment((p.Red() + c.Red()) >> 1, (p.Green() + c.Green()) >> 1, (p.Blue() + c.Blue()) >> 1);

                        map[xx][yy] = o.ToRGB();
                    } catch (Exception ex) {
                    }
                }
            }
        }

        map[cw][ch] = (int) Color.white.getRGB();
        System.out.println(objects + ": Objects Above :" + massAbove + " Kg");
        return map;
    }

    public void CalcCenter() {
        System.out.println("Bodies " + this.bodies.Length());
        AstralBody largest = this.bodies.First();
        System.out.println(0 + "Mass:" + largest.Mass() + "\tRadius:" + largest.Radius() + "\tGravity:" + largest.Gravity() + "\tAU:" + largest.NearAU());

        double max = largest.Mass();
        int i = 0;
        System.out.println("Finding Center of Gravity");
        for (Node<AstralBody> a = this.bodies.Start(); a.data != null; a = a.next) {

            if (max < a.data.Mass()) {
                largest = a.data;
                max = a.data.Mass();
            }
            if (a.data.Mass() > Detail) {
                System.out.println(i + "\t" + a.data.Type() + "\tMass:" + a.data.Mass() + "\tRadius:" + a.data.Radius() + "\tGravity:" + a.data.Gravity() + "\tAU:" + a.data.NearAU());
            }
            i++;
        }
        i = 0;
        RingList<AstralBody> pass = new RingList<>();
        if (central != largest) {

            System.out.println("Setting Center of Gravity");
            //this.bodyStore.Remove(largest);
            pass.Append(largest);
            for (Node<AstralBody> a = bodies.Start(); a.data != null; a = a.next) {
                if (a.data != largest) {
                    pass.Append(a.data);

                    if (a.data.Mass() > Detail) {
                        System.out.println(i + "\t" + a.data.Type() + "\tMass:" + a.data.Mass() + "\tRadius:" + a.data.Radius() + "\tGravity:" + a.data.Gravity() + "\tAU:" + a.data.NearAU());
                    }
                }
                i++;
            }
            bodies = pass;
        } else {
            System.out.println("Stability");
            largest.Stabilize();
        }
    }

    public void ReStart() {
        CalcCenter();
        this.state = ProcessState.Started;
    }

    public void AccreteSatalites(AstralBody body) {

        try {
            AstralBody central = body;
            System.out.println("Sat Count" + central.Saterlites().size());
            if (central == null) {
                //CalcCenter();
            }
            //
            /*java.util.stream.Stream<AstralBody> str = java.util.stream.Stream.generate(central.Saterlites()::Remove).limit(central.Saterlites().size());
             str.reduce((AstralBody a, AstralBody b) -> {
             Double effect = new Double(0);
             if (a.Collision(b, central, effect, 1e5)) {
             a.Meld(b);
             return a;
             } else {
             b.Effect(effect);
             return b;
             }

             });
             central.satelites = new RingList(str.collect(Collectors.toList()));
             */
            RingList<AstralBody> pass = new RingList<>();
            for (Node<AstralBody> a = central.Saterlites().Start(); a.data != null; a = a.next) {
                for (Node<AstralBody> b = a.next; b.data != null; b = b.next) {
                    if (a.data != null && b.data != null) {
                        if (a.data != null && b.data != null) {
                            if (b.data.mark == 0) {
                                AstralBody c = SatTest(a.data, b.data);
                                if (c == b.data) {
                                    c.mark = 1;

                                    a.data.mark = -1;
                                    break;
                                } else {

                                    //break;
                                }
                            }
                        }
                    }
                }
            }
            for (Node<AstralBody> a = central.Saterlites().Start(); a.data != null; a = a.next) {
                if (a.data.mark >= 0) {
                    pass.Append(a.data);
                }
                a.data.mark = 0;
            }
            central.Saterlites().Clear();
            central.Saterlites().PasteAll(pass);
            satCount += central.Saterlites().size();
            System.out.println("\tFinal Sat Count" + central.Saterlites().size());
        } catch (Exception ex) {
            //
        }
    }

    public Supplier<RingList<AstralBody>> supplier() {
        return RingList::new;
    }

    @Override
    public BiConsumer<RingList<AstralBody>, Node< AstralBody>> accumulator() {
        return this::Accrete;
    }

    @Override
    public Function finisher() {
        return Function.identity();
    }

    @Override
    public BinaryOperator<RingList<AstralBody>> combiner() {
        return (list1, list2) -> {
            list1.PasteAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }

    public AstralBody Test(AstralBody a, AstralBody b) {
        Double effect = new Double(0);

        if (b.Collision(a, central, effect, 1e5)) {
            b.Add(a);
            return b;
        } else {
            a.Effect(effect);
            return a;
        }
    }

    public AstralBody SatTest(AstralBody a, AstralBody b) {
        Double effect = new Double(0);

        if (b.Collision(a, central, effect, 1e5)) {
            b.Meld(a);
            return b;
        } else {
            a.Effect(effect);
            return a;
        }
    }

    void Accrete(RingList<AstralBody> bods, Node<AstralBody> a) {
        for (Node<AstralBody> b = a.next; b.data != null; b = b.next) {
            if (a != b) {
                AstralBody c = Test(a.data, b.data);
                //if(!bods.contains(c))
                bods.Append(c);
            }
        }
    }

    public boolean Process() {
        angle += this.spin;
        if (angle > (float) (Math.PI * 2)) {
            angle -= (float) (Math.PI * 4);
        }
        //
        //ReStart();
        RingList<AstralBody> pass = new RingList<>();
        central = this.bodies.First();
        if (central != null) {
            int p = 0;
            System.out.println("Central Processing " + this.bodies.size());
            for (Node<AstralBody> a = bodies.Start(); a.data != null; a = a.next) {
                for (Node<AstralBody> b = a.next; b.data != null; b = b.next) {
                    if (a.data != null && b.data != null) {
                        //if(b.data.mark==0){
                        AstralBody c = Test(a.data, b.data);
                        if (c == b.data) {

                            c.mark = 1;

                            a.data.mark = -1;
                            break;
                        } else {
                        }
                        //}
                    }
                }
            }
            for (Node<AstralBody> a = bodies.Start(); a.data != null; a = a.next) {
                if (a.data.mark >= 0) {
                    pass.Append(a.data);
                } else {
                    a.data.mark = 0;
                }
            }
            System.out.println("Collision Processing " + p);

            bodies.Clear();
            bodies.PasteAll(pass);
        }

        System.out.println("Passed Processing " + pass.Length());
        CalcCenter();
        System.out.println("Final Processing " + this.bodies.Length());

        /*    str.forEach(this::Sweep);
         System.out.println("Sweep Processing "+(this.bodyY >= this.bodyStore.size()));
         if (this.bodyY >= this.bodyStore.size()) {
                    
         if (this.attractedMass > 0) {
         Sweep(test);
         }
         AccreteSatalites();
         this.billionsOfYears += Milenia;//1,000 years
         this.condenseTime += Milenia;
         this.bodyX++;
         this.bodyY = this.bodyX + 1;
         System.out.println(this.bodyX + "Count" + this.bodyStore.size());
         if (this.bodyX >= this.bodyStore.size()) {
         Attract(central);
         this.bodyX = 0;
         this.bodyY = this.bodyX + 1;
         bodyZ++;
         this.spin = (float) (Math.PI * 2 / this.bodies.size());
         for (AstralBody body : this.bodyStore) {
         body.Condense(central, this.condenseTime);
         double m10 = Math.log10(body.Mass());
         if (body.Gravity() > m10) {
         if (m10 > Math.PI * 9) {
         body.StellarNuclearSynthisis();
         for (AstralBody bod : this.bodyStore) {
         bod.SolarWind(body);
         }
         this.attractedMass = 0;
         if (!stars.contains(body)) {
         this.stars.add(body);
         }
         nova++;
         } else {
         //belt
         body.Belter();
         }
         }
         }
         this.condenseTime = 0;

         if (lastBodyCount == this.bodyStore.size()) {
         if (lastBodyCount < (stableCount << 1)) {
         this.End();
         } else {
         stableCount++;
         }
         } else {
         stableCount = 0;
         }
         lastBodyCount = this.bodyStore.size();
         lastSatCount = satCount;
         satCount = 0;
         if (central.Gravity() > Math.log10(central.Mass())) {
         central.StellarNuclearSynthisis();
         if (!stars.contains(central)) {
         this.stars.add(central);
         }
         nova++;
         this.attractedMass = 0;
         }
         this.state = ProcessState.Finished;
         }
         }
         }*/
        //} catch (Exception ex) {
        /*System.out.println("Sub Processing ");
         Attract(central);
         //
         this.bodyX = 0;
         this.bodyY = this.bodyX + 1;
         bodyZ++;
         this.spin = (float) (Math.PI * 2 / this.bodies.size());
         for (AstralBody body : this.bodyStore) {
         body.Condense(central, this.condenseTime);
         double m10 = Math.log10(body.Mass());
         if (body.Gravity() > m10) {
         if (m10 > Math.PI * 9) {
         body.StellarNuclearSynthisis();
         for (AstralBody bod : this.bodyStore) {
         bod.SolarWind(body);
         }
         this.attractedMass = 0;
         if (!stars.contains(body)) {
         this.stars.add(body);
         }
         nova++;
         } else {
         //belt
         body.Belter();
         }
         }
         }
         this.condenseTime = 0;
         if (lastBodyCount == this.bodyStore.size()) {
         if (lastBodyCount < (stableCount << 1)) {
         this.End();
         } else {
         stableCount++;
         }
         } else {
         stableCount = 0;
         }
         lastBodyCount = this.bodyStore.size();
         lastSatCount = satCount;
         satCount = 0;
         if (central.Gravity() > Math.log10(central.Mass())) {
         central.StellarNuclearSynthisis();
         this.attractedMass = 0;
         if (!stars.contains(central)) {
         this.stars.add(central);
         }
         nova++;

         }
         this.state = ProcessState.Finished;
         //
         System.out.println(this.bodyX + "Count" + this.bodyStore.size());
         //System.Console.WriteLine(this.bodyX+" Error At:"+this.bodyY+"\n"+ex.Message+ex.StackTrace+ex.Source);
                    
         }*/
        return true;
    }

    protected void End() {
        /*AstralBody sun = (AstralBody) this.bodyStore.get(0);
         double a = (sun.Radius() + sun.Orbital().Far());
         for (int i = 1; i < this.bodyStore.size(); i++) {
         AstralBody body = this.bodyStore.get(i);
         if (a > body.Orbital().Far() + body.Radius()) {
         sun.Absorb(body);
         nova++;
         this.bodyStore.Remove(this.bodyStore.get(i));
         i--;
         }
         }
         for (int i = 1; i < sun.Saterlites().size(); i++) {
         AstralBody body = (AstralBody) sun.Saterlites().get(i);
         if (a > body.Orbital().Far() + body.Radius()) {
         sun.Absorb(body);
         nova++;
         sun.Saterlites().Remove(sun.Saterlites().get(i));
         i--;
         }
         }
         if (Math.log10(sun.Mass()) > Math.PI * 9) {
         sun.StellarNuclearSynthisis();
         if (!stars.contains(sun)) {
         this.stars.add(sun);
         }
         nova++;
         }
         boolean ok = false;
         do {
         ok = false;
         for (int i = 1; i < this.bodyStore.size(); i++) {
         AstralBody body = this.bodyStore.get(i);
         body.SolarWind(sun);

         double m10 = Math.log10(body.Mass());
         if (body.Gravity() > m10) {
         if (m10 > Math.PI * 8) {
         body.StellarNuclearSynthisis();
         for (AstralBody bod : this.bodyStore) {
         bod.SolarWind(body);
         }
         this.attractedMass = 0;
         if (!stars.contains(body)) {
         this.stars.add(body);
         }
         nova++;
         ok = true;
         } else {
         //belt;
         body.Belter();
         }
         }
         }
         } while (ok);

         for (int i = 1; i < this.bodyStore.size(); i++) {
         AstralBody body = this.bodyStore.get(i);
         body.SetBodyType();
         }
         for (int i = 0; i < this.bodyStore.size() - 1; i++) {
         for (int j = this.bodyStore.size() - 1; j > i; j--) {
         AstralBody test = (this.bodyStore.get(j));
         AstralBody against = (this.bodyStore.get(j - 1));
         if (test.FarAU() < against.FarAU()) {
         this.bodyStore.Set(j);
         this.bodyStore.Insert(against);
         this.bodyStore.Set(j - 1);
         this.bodyStore.Insert(test);
         }
         }
         }
         //
         double temp = ((sun.Gravity() * 9.8 * 2 * Math.PI * sun.Radius()) / 9e16) * Span * Span;//K
         double st = Math.sqrt(temp / 273) - Math.PI;
         double s = Math.sqrt(sun.Radius() / AstralBody.AU) - Math.PI;
         double stemp = st * 273;
         //
         for (int i = 0; i < this.bodyStore.size(); i++) {
         AstralBody test = (AstralBody) (this.bodyStore.get(i));
         System.out.println(i + "\t" + test.SizeType() + "\t" + test.DensityType() + "\t" + test.Type() + "\tMass:" + test.Mass() + "\tRadius:" + test.Radius() + "\tGravity:" + test.Gravity() + "\tAU:" + test.NearAU() + "/" + test.FarAU());
         System.out.println("\tSats:" + test.Saterlites().size());
         }
         System.out.println("Stars:" + stars.size());
         System.out.println("Nova:" + nova);
         System.out.println("Planets Swallowed:" + swallowedPlanets);
         this.attractedMass = 0;
         //Game.Form.Close();
         */
    }

    protected void Attract(AstralBody central) {
        if (this.attractedMass > 0) {
            double m = (central.Mass() + this.attractedMass) / (this.attractedObjects * Math.PI * 4);
            double cm = (2 * Math.PI * central.Mass() * central.Radius()) / central.Orbital().Near();
            double m1 = cm * Molecula.rnd.nextDouble();
            double m2 = cm * Molecula.rnd.nextDouble();
            double m3 = m * Molecula.rnd.nextDouble();
            double m4 = m * Molecula.rnd.nextDouble();
            double m5 = m * Molecula.rnd.nextDouble();
            double m6 = m * Molecula.rnd.nextDouble();
            double m7 = m * Molecula.rnd.nextDouble();
            Substance substance = new Substance(m1, m2, m3, m4, m5, m6, m7, 0);
            double m8 = (m1 + m2 + m3 + m4 + m5 + m6 + m7);
            this.attractedMass -= m8 * 8;
            double rv = ((m1 + m2 + m4) * central.Radius() * 2 * Math.PI) / central.Mass();
            double r = (m8) / central.Mass();
            central.Absorb(new AstralBody(substance, rv * r, central.Magnetism(), AstralBody.Gas, central.Orbital()));
        }
    }

    protected void Sweep(AstralBody body) {
        if (this.attractedMass > 0) {
            double m = ((body.Mass() + this.attractedMass) * body.Radius()) / (this.attractedObjects * Math.PI * 4 * body.Orbital().Near());
            double cm = (2 * Math.PI * body.Mass() * body.Radius()) / body.Orbital().Near();
            double m1 = cm * Molecula.rnd.nextDouble();
            double m2 = cm * Molecula.rnd.nextDouble();
            double m3 = m * Molecula.rnd.nextDouble();
            double m4 = m * Molecula.rnd.nextDouble();
            double m5 = m * Molecula.rnd.nextDouble();
            double m6 = m * Molecula.rnd.nextDouble();
            double m7 = m * Molecula.rnd.nextDouble();
            Substance substance = new Substance(m1, m2, m3, m4, m5, m6, m7, 0);
            double m8 = (m1 + m2 + m3 + m4 + m5 + m6 + m7);
            double rv = ((m1 + m2 + m4) * body.Radius() * 2 * Math.PI) / body.Mass();
            this.attractedMass -= m8 * 4;
            double r = (m8) / body.Mass();
            body.Absorb(new AstralBody(substance, r * rv, body.Magnetism(), AstralBody.Gas, body.Orbital()));
        }
    }
}
