/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn
 */
class Frac extends Point{

    int symetry;
    int type;
    double seed1;
    double seed2;
    Molecula molecule1;
    Molecula molecule2;
}

class Relative {

    double distance;// aus
    double radius;// moons earths jupiters
    double mass;// moons earths jupiters
    double density;//moons earths jupiters
    double temperature;//moons earths jupiters
    double energy;//moons earths jupiters
}

enum SpectralType {

    DarkRed, DeepRed, Red, OrangeRed, Orange, Yellow, YellowWhite, White, BlueWhite, Blue, Neutron
}

enum StellarLuminosity {

    SuperDwarf, SubDwarf, Dwarf, MainSequence, SubGiant, Giant, BrightGiant, SuperGiant, LuminousSuperGiant, HyperGiant, Titanic
}

enum OrbitalType {

    MagneticLock, TidalLock, PolarLock
}

class StellarClass {

    SpectralType spec;
    StellarLuminosity size;
    double subType;

    int SizeValue() {
        switch (size) {
            case SuperDwarf: {
                return 0;
            }
            case SubDwarf: {
                return 1;
            }
            case Dwarf: {
                return 2;
            }
            case MainSequence: {
                return 3;
            }

            case SubGiant: {
                return 4;
            }
            case Giant: {
                return 5;
            }
            case BrightGiant: {
                return 6;
            }
            case SuperGiant: {
                return 7;
            }
            case LuminousSuperGiant: {
                return 8;
            }
            case HyperGiant: {
                return 9;
            }
            case Titanic: {
                return 10;
            }
        }
        return 0;
    }

    static StellarLuminosity GetSize(int size) {
        switch (size) {
            case 0: {
                return StellarLuminosity.SuperDwarf;
            }
            case 1: {
                return StellarLuminosity.SubDwarf;
            }
            case 2: {
                return StellarLuminosity.Dwarf;
            }
            case 3: {
                return StellarLuminosity.MainSequence;
            }

            case 4: {
                return StellarLuminosity.SubGiant;
            }
            case 5: {
                return StellarLuminosity.Giant;
            }
            case 6: {
                return StellarLuminosity.BrightGiant;
            }
            case 7: {
                return StellarLuminosity.SuperGiant;
            }
            case 8: {
                return StellarLuminosity.LuminousSuperGiant;
            }
            case 9: {
                return StellarLuminosity.HyperGiant;
            }
            case 10: {
                return StellarLuminosity.Titanic;
            }
        }
        return StellarLuminosity.SuperDwarf;
    }

    int SpectralValue() {
        switch (spec) {
            case DarkRed: {
                return 0;
            }
            case DeepRed: {
                return 1;
            }
            case Red: {
                return 2;
            }
            case OrangeRed: {
                return 3;
            }
            case Orange: {
                return 4;
            }
            case Yellow: {
                return 5;
            }
            case YellowWhite: {
                return 6;
            }
            case White: {
                return 7;
            }
            case BlueWhite: {
                return 8;
            }
            case Blue: {
                return 9;
            }
            case Neutron: {
                return 10;
            }
        }
        return 10;
    }

    static SpectralType GetSpectral(int spec) {
        switch (spec) {
            case 0: {
                return SpectralType.DarkRed;
            }
            case 1: {
                return SpectralType.DeepRed;
            }
            case 2: {
                return SpectralType.Red;
            }
            case 3: {
                return SpectralType.OrangeRed;
            }
            case 4: {
                return SpectralType.Orange;
            }
            case 5: {
                return SpectralType.Yellow;
            }
            case 6: {
                return SpectralType.YellowWhite;
            }
            case 7: {
                return SpectralType.White;
            }
            case 8: {
                return SpectralType.BlueWhite;
            }
            case 9: {
                return SpectralType.Blue;
            }
            case 10: {
                return SpectralType.Neutron;
            }
        }
        return SpectralType.Neutron;
    }
}

class Core {

    int hydrogen;
    int helium;
    int carbon;
    int oxygen;
    int iron;
    int calcium;
    int heavyMetals;
    int lightMetals;

    Core() {

    }

    Core(Core c, Molecula rnd) {

        Core d = RandomStarCore(rnd);
        this.hydrogen = (d.hydrogen + c.hydrogen) >> 1;
        this.helium = (d.helium + c.helium) >> 1;
        this.calcium = (d.calcium + c.calcium) >> 1;
        this.carbon = (d.carbon + c.carbon) >> 1;
        this.oxygen = (d.oxygen + c.oxygen) >> 1;
        this.iron = (d.iron + c.iron) >> 1;
        this.heavyMetals = (d.heavyMetals + c.heavyMetals) >> 1;
        this.lightMetals = (d.lightMetals + c.lightMetals) >> 1;
    }

    public static Core RandomStarCore() {
        Core core = new Core();
        core.iron = 128 + Random.Next(128) - Random.Next(128);
        core.heavyMetals = 128 - (core.iron >> 1);
        if (core.heavyMetals < 1) {
            core.heavyMetals = 1;
        }
        core.hydrogen = 128 + Random.Next(128) - (core.iron >> 1);
        core.helium = 1 + Random.Next(128) % (1 + core.hydrogen);
        core.carbon = 128 + Random.Next(128) - Random.Next(128);
        core.oxygen = 128 + Random.Next(128) - ((core.helium + core.iron + core.carbon + core.hydrogen) >> 3);//128+Next(128)-Next(128);
        core.lightMetals = 255 - core.oxygen % (core.heavyMetals);
        if (core.lightMetals < 1) {
            core.lightMetals = 1;
        }
        core.calcium = (255 - core.carbon) % (core.lightMetals);
        //
        return core;
    }

    public static Core RandomStarCore(Molecula rnd) {
        Core core = new Core();
        core.iron = 128 + (int) rnd.Next(128) - (int) rnd.Next(128);
        core.heavyMetals = 128 - (core.iron >> 1);
        if (core.heavyMetals < 1) {
            core.heavyMetals = 1;
        }
        core.hydrogen = 128 + (int) rnd.Next(128) - (core.iron >> 1);
        core.helium = 1 + (int) rnd.Next(128) % (1 + core.hydrogen);
        core.carbon = 128 + (int) rnd.Next(128) - (int) rnd.Next(128);
        core.oxygen = 128 + (int) rnd.Next(128) - ((core.helium + core.iron + core.carbon + core.hydrogen) >> 3);//128+Next(128)-Next(128);
        core.lightMetals = 255 - core.oxygen % (core.heavyMetals);
        if (core.lightMetals < 1) {
            core.lightMetals = 1;
        }
        core.calcium = (255 - core.carbon) % (core.lightMetals);
        //
        return core;
    }

    public static Core Add(Core a, Core b) {
        Core d = new Core();
        d.hydrogen = (a.hydrogen + b.hydrogen) >> 1;
        d.helium = (a.helium + b.helium) >> 1;
        d.calcium = (a.calcium + b.calcium) >> 1;
        d.carbon = (a.carbon + b.carbon) >> 1;
        d.oxygen = (a.oxygen + b.oxygen) >> 1;
        d.iron = (a.iron + b.iron) >> 1;
        d.heavyMetals = (a.heavyMetals + b.heavyMetals) >> 1;
        d.lightMetals = (a.lightMetals + b.lightMetals) >> 1;
        return d;
    }

    public int Total() {
        int t = 0;
        t += this.hydrogen;
        t += this.helium;
        t += this.calcium;
        t += this.carbon;
        t += this.oxygen;
        t += this.iron;
        t += this.heavyMetals;
        t += this.lightMetals;
        return t;
    }
}

class Orbit {

    OrbitalType type;//magnetic tidal polar
    //
    float radius;//aus
    //float tilt;//%
    //float wobble;//%
    //float excentricity;//%
    float period;//years
    float spin;//days
    float velocity;//m/s
    float magnetics;//%

    float arc;//angle
    float arcs;//angle distance/h

    Orbit() {
    }

    Orbit(Star star, float aus, float start) {
        radius = aus;
        double a = aus * PlanetMap.AU;
        double v = Physics.CircularVelocityMagnitude(star.relative.mass * PlanetMap.SolMass, a);
        velocity = (float) (v * 1e-3 / PlanetMap.Hour);//km/h
        arcs = (float) (PlanetMap.PI2 / ((PlanetMap.PI2 * a * 1e-3) / velocity));
        arc = arcs * start;
        while (arc > Math.PI) {
            arc -= Linear.PI2;
        }
        while (arc < -Math.PI) {
            arc += Linear.PI2;
        }
    }

    Point RelativePoint() {
        double rr = radius * PlanetMap.AU / PlanetMap.RadiusOfEarth;
        return new Point(rr * Math.cos(arc), 0, rr * Math.sin(arc));
    }
}

class RingSystem extends StellarBody {

    LargePlanet encircles;
    Relative relativeExtent;//earths

    RingSystem(LargePlanet p, Molecula rnd) {
        super(p.system, rnd);
        this.encircles = p;
        this.relative.mass = p.relative.mass * rnd.NextDouble() * 0.01;
        this.relative.radius = p.relative.radius * rnd.NextDouble() * 0.1;
        this.relative.distance = p.relative.radius + this.relative.radius;
        this.core = new Core(p.core, rnd);
        this.atmosphere = new Atmosphere(p.atmosphere, rnd, p);
        this.relativeExtent = new Relative();
        this.relativeExtent.mass = this.relative.mass * p.relative.radius * rnd.NextDouble();
        this.relativeExtent.radius = p.relative.radius + p.relative.radius * rnd.NextDouble();
        this.relativeExtent.distance = this.relativeExtent.radius + p.relative.radius * rnd.NextDouble();

        this.r = (double) this.relative.radius;
    }

    RingSystem(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }
}

class Star extends SuperPlanet {

    Star[] stars;
    Planet prime;
    Point starCoord;
    Point focusCoord;
    RingList<SmallPlanet> planets = new RingList();

    static int HighMass(Star a, Star b) {
        if (a.relative.mass > b.relative.mass) {
            return 1;
        } else if (b.relative.mass > a.relative.mass) {
            return -1;
        }
        return 0;
    }

    static Pigment StarColor(StellarClass c) {
        int rs = 0;
        int gs = 0;
        int bs = 0;
        switch (c.spec) {
            case DarkRed: {
                rs = 128;
                break;
            }
            case DeepRed: {
                rs = 192;
                break;
            }
            case Red: {
                rs = 255;
                break;
            }
            case OrangeRed: {
                rs = 255;
                gs = 128;
                break;
            }
            case Orange: {
                rs = 255;
                gs = 192;
                break;
            }
            case Yellow: {
                rs = 255;
                gs = 255;
                break;
            }
            case YellowWhite: {
                rs = 255;
                gs = 224;
                bs = 192;
                break;
            }
            case White: {
                rs = 192;
                gs = 255;
                bs = 255;
                break;
            }
            case BlueWhite: {
                rs = 224;
                gs = 224;
                bs = 255;
                break;
            }
            case Blue: {
                rs = 32;
                gs = 128;
                bs = 255;
                break;
            }
        }
        int sz = c.SizeValue();
        double sl = (1 + (int) (sz)) * (1 + (int) (c.subType));//(765)
        rs += (int) Math.sqrt(rs * (1 + c.subType));
        gs += (int) Math.sqrt(gs * (1 + c.subType));
        bs += (int) Math.sqrt(bs * (1 + c.subType));
        rs = (int) (rs + rs + rs + sl) >> 2;
        gs = (int) (gs + gs + gs - sl) >> 2;
        bs = (int) (bs + bs + bs - sl) >> 2;
        if (rs > 255) {
            rs = 255;
        }
        if (gs < 0) {
            gs = 0;
        }
        if (bs < 0) {
            bs = 0;
        }
        return new Pigment(rs, gs, bs);
    }
    static String[] spectralCode = new String[]{
        "C", "S", "R", "M", "K", "G", "F", "A", "B", "O"
    };
    static String[] stellarCode = new String[]{
        "viii", "vii", "vi", "v", "iv", "iii", "ii", "ia", "ib", "o"
    };
    StellarClass classification;
    StellarBody[] bodies;
    Point relativeCoord;
    double greenZone;
    double radiation;

    //double heliosphere;

    double HelioSphere() {
        return this.magnetosphere * this.greenZone;
    }

    Star(SmallPlanet home, double mass, double radius, double temperature, Molecula rnd) {

        super(home.system, rnd);
        this.core = new Core(home.core, rnd);
        this.atmosphere = new Atmosphere(home.atmosphere, rnd, this);

        this.relative = new Relative();
        this.relative.mass = mass;
        this.relative.radius = radius;
        this.relative.temperature = temperature;
        SetUpStar(home, rnd);

        this.name = spectralCode[(int) classification.SpectralValue()] + classification.subType + stellarCode[(int) classification.SizeValue()];
        this.r = (double) this.relative.radius * PlanetMap.SolRadiusInEarths;
    }

    Star(SmallPlanet home, Molecula rnd) {
        super(home.system, rnd);

        this.core = new Core(home.core, rnd);
        this.atmosphere = new Atmosphere(home.atmosphere, rnd, this);

        this.relative = new Relative();
        while ((int) (this.relative.mass * 100) == 0) {
            this.relative.mass += (home.system.sm * Defined.Third / PlanetMap.Sols) + Math.abs((rnd.NextDouble() * rnd.NextDouble() + rnd.NextDouble() - rnd.NextDouble()) * (Math.pow(rnd.NextDouble() * rnd.NextDouble() + rnd.NextDouble() - rnd.NextDouble(), 2) + rnd.NextDouble() - rnd.NextDouble())) / (Math.pow(rnd.NextDouble() * rnd.NextDouble() + rnd.NextDouble(), 2) + rnd.NextDouble());
        }
        while ((int) (this.relative.radius * 100) == 0) {
            this.relative.radius += Math.abs((rnd.NextDouble() * rnd.NextDouble() * 0.25) / this.relative.mass + rnd.NextDouble() - rnd.NextDouble());
        }
        while ((int) (this.relative.temperature * 100) == 0) {
            this.relative.temperature += Math.sqrt(Math.abs(this.relative.mass * this.relative.mass + rnd.NextDouble() - rnd.NextDouble()) / (this.relative.radius * this.relative.radius + rnd.NextDouble()));
        }
        SetUpStar(home, rnd);
    }

    double RedZone() {
        return greenZone - (greenZone * radiation);
    }

    double BlueZone() {
        return greenZone + (greenZone * radiation);
    }

    boolean Liveable() {
        return greenZone > radiation;
    }

    double RadsAt(Orbit orb) {
        double radiationZone = PlanetMap.PI * (double) Math.sqrt((this.relative.density * this.relative.mass * PlanetMap.RadiusOfSol * this.relative.temperature * this.relative.temperature) / (Linear.PI2 * PlanetMap.AU * orb.radius));
        return radiationZone;
    }

    double LightAt(Orbit orb) {
        double temp = PlanetMap.PI * (double) Math.sqrt(((PlanetMap.PI4 * this.relative.radius * this.relative.radius * PlanetMap.RadiusOfSol) * this.relative.temperature * this.relative.temperature * this.relative.temperature) / (Linear.PI2 * PlanetMap.AU * orb.radius));
        return temp;
    }

    double Gravity() {
        double r = this.relative.radius * PlanetMap.RadiusOfSol;
        return (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.SolMass) / (r * r));
    }

    double Gravity(Star s) {
        double r = this.relative.radius * PlanetMap.RadiusOfSol;
        return (double) ((PlanetMap.uG * this.relative.mass * s.relative.mass * PlanetMap.SolMass) / (r * r));
    }

    double Distant(Star s) {
        double g = Gravity(s);
        double g1 = (double) ((greenZone * g) / radiation);
        double g2 = (double) ((s.greenZone * g) / s.radiation);
        return (double) ((g1 + g2) / ((greenZone + s.greenZone) * g)) * PlanetMap.PI2;
    }

    Star(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
        system = sys;
    }

    void SetUpStar(SmallPlanet home, Molecula rnd) {
        home.revolvesRound = this;
        double temp = 1;
        double size = 1;
        double lum = 1;
        this.relative.density = this.relative.mass / (PlanetMap.FourThirdPI * this.relative.radius * this.relative.radius * this.relative.radius);
        this.relative.energy = this.relative.mass * 9e16;
        double coolent = (core.carbon + core.oxygen + core.lightMetals + atmosphere.nitrogen + atmosphere.oxygen + atmosphere.sulphur + atmosphere.chlorine) * this.relative.density;
        temp += (core.heavyMetals + core.helium + core.hydrogen + core.iron + core.calcium + atmosphere.hydrogen) * this.relative.mass;
        size += (temp + coolent) / 128 * this.relative.radius * this.relative.mass;
        temp += size * temp * this.relative.density;
        temp -= coolent * this.relative.radius;
        if (temp < 1) {
            temp = 1;
        } else {
            temp /= 256;
        }
        size += size * this.relative.radius;
        if (size < 1) {
            size = 1;
        }
        lum = (size * temp) * Math.PI;
        double lumi = Math.log10(lum);
        if (lumi > 10) {
            lumi = 9.9f;
        } else if (lumi < 0) {
            lumi = 0;
        }
        temp = (int) (5 + Math.log(temp));
        size = (int) (1 + Math.log(size));
        //System.Console.WriteLine("Temp Log=" + temp + " Size Log=" + size);
        this.relative.mass *= (lumi + size);
        this.relative.radius *= (lumi + size);
        this.relative.temperature *= (lumi + (temp)) * 0.5;
        double rad = (this.relative.radius * PlanetMap.SolRadiusInEarths * PlanetMap.RadiusOfSol);
        this.relative.density = (this.relative.mass * PlanetMap.SolMass) / (PlanetMap.FourThirdPI * rad * rad * rad);
        this.relative.energy = this.relative.mass * 9e16;
        this.orbital = new Orbit();
        if (size > 9) {
            size = 9;
        }
        if (temp > 9) {
            temp = 9;
        }
        lumi = (int) (lumi * 10) / 10.0;
        this.greenZone = PlanetMap.PI4 * Math.sqrt(((PlanetMap.PI2 * this.relative.radius * this.relative.radius * PlanetMap.RadiusOfSol) * this.relative.temperature * this.relative.temperature * this.relative.temperature) / (Linear.PI2 * PlanetMap.AU));
        double radiationZone = PlanetMap.PI4 * Math.sqrt((this.relative.density * this.relative.mass * PlanetMap.RadiusOfSol * this.relative.temperature * this.relative.temperature) / (Linear.PI2 * PlanetMap.AU));
        this.radiation = ((int) (radiationZone * 1000)) * 0.001;
        this.magnetosphere = (double) (((double) (core.heavyMetals + core.iron) / 255.0) * this.relative.mass / radiationZone);
        //this.heliosphere = this.magnetosphere * this.greenZone;
        String code1 = spectralCode[(int) temp];
        String code2 = stellarCode[(int) size];
        SpectralType st = StellarClass.GetSpectral((int) temp);
        StellarLuminosity lm = StellarClass.GetSize((int) size);
        this.classification = new StellarClass();
        this.classification.spec = st;
        this.classification.size = lm;
        this.classification.subType = (double) lumi;
        this.color = StarColor(this.classification);
        Star[] stars = null;
        if (home.revolvesRound.stars == null) {
            stars = new Star[1];
            stars[0] = this;
            stars[0].orbital.radius = 0;
        } else {
            stars = new Star[home.revolvesRound.stars.length + 1];
            Star d = null;
            int i = 0;
            for (; i < home.revolvesRound.stars.length; i++) {
                stars[i] = home.revolvesRound.stars[i];
                if (d != null) {
                    double dis = stars[i].Distant(d);
                    stars[i].orbital.radius = (float) dis;
                    System.out.println("Distance " + i + " " + (i + 1) + " " + dis);
                }
                d = stars[i];
            }
            stars[home.revolvesRound.stars.length] = this;

            double thisdis = stars[i].Distant(this);
            stars[home.revolvesRound.stars.length].orbital.radius = (float) thisdis;
            System.out.println("Distance " + i + " " + (i + 1) + " " + thisdis);

        }

        this.name = st + " " + lm + " " + code1 + lumi + code2;
        home.revolvesRound.stars = stars;
        //System.out.println("star core "+home.revolvesRound.core!=null);
        this.r = (double) this.relative.radius * PlanetMap.SolRadiusInEarths;
        System.out.println(stars.length + "*Star " + st + " " + lm + " " + code1 + lumi + code2);
        System.out.println("Sols mass " + this.relative.mass + " radius " + this.relative.radius);
        System.out.println("Sols density " + this.relative.density + " temperature " + this.relative.temperature);
        System.out.println("Sols energy " + this.relative.energy + " Green Zone " + greenZone + " Radiation " + radiation);
        System.out.println("___________");
    }

    void SlowStarSort(Star[] unsortedList, int lookFor) {

        for (int i = 0; i < unsortedList.length - 1; i++) {
            int mini = i;
            for (int j = i + 1; j < unsortedList.length; j++) {
                if (Star.HighMass(unsortedList[j], unsortedList[mini]) == lookFor) {
                    mini = j;
                }
                if (mini != i) {

                    Star d = unsortedList[mini];
                    unsortedList[mini] = unsortedList[i];
                    unsortedList[i] = d;
                }
            }
        }
    }

    void SortStars() {
        SlowStarSort(stars, 1);
    }

    void PlaceInSystem(SmallPlanet p, boolean prime) {
        if (prime) {
            this.prime = (Planet) p;
        }
        Star s = p.revolvesRound;
        if (s != null) {
            p.orbital.radius = (float) s.greenZone;
            if (p.relative.temperature > 1) {
                p.orbital.radius -= (double) (s.RedZone() * Math.abs(p.relative.temperature - 1));
            } else if (p.relative.temperature < 1) {
                p.orbital.radius += (double) (s.BlueZone() * Math.abs(p.relative.temperature - 1));
                p.orbital.radius *= 0.5;
            }
            
            p.orbital.radius = Math.abs(p.orbital.radius);
            p.orbital.radius += (float)(p.orbital.radius/(s.relative.mass/p.relative.mass));//componsate for star mass orbital/day length period of planet (Tidal Lock)
            
        }
    }

    void FillSystem(Molecula rnd) {
        for (Star s : stars) {
            int planetoids = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) * 6.0 / Math.PI);
            int small = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) * 5.0 / Math.PI);
            int large = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) * 4.0 / Math.PI);
            int huge = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) * 3.0 / Math.PI);
            int giant = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) * 2.0 / Math.PI);
            int superg = (int) (Math.abs(s.relative.mass + rnd.NextDouble() - rnd.NextDouble()) / Math.PI);
            //
            int i = 0;
            for (i = 0; i < superg; i++) {
                int sz = 128 + rnd.NextInt(128) - rnd.NextInt(128);
                if (sz == 0) {
                    sz = 1;
                }
                Orbit orbit = new Orbit();
                orbit.radius = (float) ((Math.pow(s.greenZone * Linear.PI2 * rnd.NextDouble(), 2) + rnd.NextDouble()) / s.relative.mass);
                SuperPlanet sp = new SuperPlanet(this, s, orbit, (byte) sz, rnd);
                planets.Append(sp);
            }
            for (i = 0; i < giant; i++) {
                int sz = 128 + rnd.NextInt(128) - rnd.NextInt(128);
                if (sz == 0) {
                    sz = 1;
                }
                Orbit orbit = new Orbit();
                orbit.radius = (float) ((Math.pow(s.greenZone * Math.PI * rnd.NextDouble(), 2) + rnd.NextDouble()) / s.relative.mass);
                GiantPlanet gi = new GiantPlanet(this, s, orbit, (byte) sz, rnd);
                planets.Append(gi);
            }
            for (i = 0; i < huge; i++) {
                int sz = 128 + rnd.NextInt(128) - rnd.NextInt(128);
                if (sz == 0) {
                    sz = 1;
                }
                Orbit orbit = new Orbit();
                orbit.radius = (float) ((Math.pow(s.greenZone + s.greenZone * Math.PI * rnd.NextDouble(), 2) + rnd.NextDouble()) / s.relative.mass);
                HugePlanet hg = new HugePlanet(this, s, orbit, (byte) sz, rnd);
                planets.Append(hg);
            }
            for (i = 0; i < large; i++) {
                int sz = 128 + rnd.NextInt(128) - rnd.NextInt(128);
                if (sz == 0) {
                    sz = 1;
                }
                Orbit orbit = new Orbit();
                orbit.radius = (float) ((Math.pow(s.greenZone * rnd.NextDouble() + s.greenZone * rnd.NextDouble(), 2) + rnd.NextDouble()) / s.relative.mass);
                LargePlanet lg = new LargePlanet(this, s, orbit, (byte) sz, rnd);
                planets.Append(lg);
            }
            for (i = 0; i < small; i++) {
                int sz = 128 + rnd.NextInt(128) - rnd.NextInt(128);
                if (sz == 0) {
                    sz = 1;
                }
                Orbit orbit = new Orbit();
                orbit.radius = (float) ((Math.pow(s.greenZone * rnd.NextDouble() + s.greenZone * rnd.NextDouble(), 2) + rnd.NextDouble()) / s.relative.mass);
                SmallPlanet sm = new SmallPlanet(this, rnd);
                SmallPlanet.SetUpRandomSmallPlanet(sm, s, orbit, rnd);
                planets.Append(sm);
            }

        }

    }
}

class Moon extends Satalite {

    double water;
    double atmospheres;
    Planetoid revolvesRound;

    Moon(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }

}

class Satalite extends StellarBody {

    Pigment soil;
    Pigment air;
    StarSystem system;
    HeightMap planetMap;
    ColorMap surfaceMap;
    Pigment chemicalStain;
    double gravity;
    int size;

    Satalite(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }
}

class Planetoid extends Satalite {

    double theta;
    double aTheta;
    //
    double phi;
    double day;
    Frac fractalSky;
    Frac fractalSurface;

    Planetoid(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }
}

class SmallPlanet extends Planetoid {

    //HeightMap weatherMap;
    //creative image
    //ColorMap atmosphereMap;
    //ColorMap transMap;
    double water;
    double atmospheres;
    //
    //
    Satalite[] moon;
    Star revolvesRound;

    GImage transMap;

    SmallPlanet(StarSystem sys, Star revolves, double mass, double radius, Orbit orbit, Molecula rnd, Core baseCore, Atmosphere baseAtmos) {
        super(sys, rnd);
        int sz = (int) ((mass + radius) * 32);
        if (sz > 255) {
            sz = 255;
        }
        this.size = sz;
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = mass;
        this.relative.radius = radius;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        core = Core.Add(core, baseCore);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.atmosphere = Atmosphere.Add(atmosphere, baseAtmos);
        this.relative.temperature = revolves.RadsAt(this.orbital) + revolves.LightAt(this.orbital);
        //this.relative.temperature *= 2+Math.abs(Math.log10(Math.sqrt(this.relative.energy/this.orbital.radius)));
        this.magnetosphere = (double) (((double) (core.heavyMetals + core.iron) / 255.0) * this.relative.energy);
        CalcAtmosphere();
        //this.relative.temperature *=1+Math.abs(Math.log10(Math.sqrt(this.atmospheres/ this.relative.radius)));
        this.r = (double) this.relative.radius;

    }

    void SetUpTransMap() {
        int sz = size;
        if (this.relative.radius > 3) {
            sz = 32 + (int) (32 * this.relative.radius);
        }
        transMap = new GImage(sz, sz);
        Pigment life = new Pigment(255 - this.revolvesRound.color.Green(), 255 - this.revolvesRound.color.Red(), 255 - this.revolvesRound.color.Blue());
        Pigment temp = new Pigment((int) (128 * (this.relative.temperature)/this.relative.mass) % 255, (int) (12.8 * this.water/this.relative.mass) % 255, (int) (128 / this.relative.temperature*this.relative.radius) % 255);
        Graphics2D g = (Graphics2D) transMap.bits.getGraphics();
        int sx = sz >> 1;
        int sy = sz >> 1;
        double sqr2 = 0.5 * Math.sqrt(2.0);
        int rl = life.Red() >> 5;
        int gl = life.Green() >> 3;
        int bl = life.Blue() >> 4;
        int rm = temp.Red() >> 3;
        int gm = temp.Green() >> 4;
        int bm = temp.Blue() >> 5;
        boolean limit = this.relative.mass < 3.0;
        Molecula land = new Molecula(this.relative.mass, this.relative.density, this.relative.radius);
        Molecula sea = new Molecula(this.water, this.relative.radius, this.relative.temperature);
        Molecula cloud = new Molecula(this.atmospheres, this.relative.radius, this.relative.temperature);
        Molecula shade = new Molecula(this.core.Total() / 2048.0, this.atmosphere.Total() / 2048.0, this.relative.temperature);
        Molecula rad = new Molecula(this.orbital.radius, this.relative.radius, this.relative.temperature);
        //Molecula circ=new Molecula(this.relative.mass,this.relative.radius,this.relative.temperature);
        Pigment c = new Pigment();
        double rs = Math.sqrt(sx * sx + sy * sy) - sx * 0.1;
        double sr = rs * sqr2 - sx * 0.1;
        double xx1 = 0;
        double yy1 = 0;
        double xx2 = 0;
        double yy2 = 0;
        double xx3 = 0;
        double yy3 = 0;
        int mc = (int) ((this.relative.mass + Math.abs(255 - sz) )* (8.0 / this.relative.temperature));
        double sc = (double) (sz * 2);
        int c1 = 1 + (int) (sc / (1 + (Math.abs(this.relative.mass + this.relative.radius - this.water))));
        int c2 = 1 + (int) (sc / (2 + (Math.abs(this.relative.mass - this.water / this.relative.mass))));
        int c3 = 1 + (int) (sc / (4 + (Math.abs(this.relative.mass - this.atmospheres / this.relative.mass))));
        int c4 = 0;
        int c5 = 180 + (int) (sc * this.relative.radius);
        int br=(int)(this.relative.radius+(this.atmospheres/this.relative.radius)/sx);
        if (!limit) {
            c5 *= this.relative.radius * 0.5;
            c2/=2;
            c3/=2;
        }
        double r1 = (rs / sx);
        if (!limit) {
            r1 *= 0.5;
        }
        sr -= r1;
        double rs2 = sr * 2;
        double scale = this.relative.radius / this.relative.mass;
        Pigment ac = new Pigment(this.chemicalStain);
        Pigment bc = new Pigment(this.color);
        ac.Average(this.air);
        bc.Average(this.soil);
        ac.Average(bc);
        ac.Average(temp);
        if(limit){
        g.setColor(ac.Color());
        }else{
        g.setColor(temp.Color());
        
        }
        g.fillOval(sx / 5, sy / 5, (int) (rs2), (int) (rs2));
    //g.setColor(a.Color());
        //g.fillOval(0,0,(int)(rs2),(int)(rs2));
        while (c4 < c5) {
            c4++;
            double xs = sr * (rad.cosine);
            if (limit) {
                for (int i = 0; i < c1; i++) {
                    //
                    land.SingleCalc(scale);
                    shade.SingleCalc(scale);
                    //circ.SingleCalc();
                    rad.SingleCalc(scale);

                    c.Red(((int) ((temp.Red() + this.soil.Red() - this.air.Red() + ac.Red() + this.color.Red()) * Math.abs(shade.cosine)) >> 5) % 255);
                    c.Green(((int) ((temp.Green() + this.soil.Green() - this.air.Green() + ac.Green() + this.color.Green()) * Math.abs(shade.sine)) >> 3) % 255);
                    c.Red(((int) ((temp.Blue() + this.soil.Blue() - this.air.Blue() + ac.Blue() + this.color.Blue()) * Math.abs(shade.sine * shade.cosine)) >> 4) % 255);

                        xx1 += (r1 * rad.sine + r1 * land.cosine);
                        yy1 += (r1 * rad.cosine + r1 * land.sine);
                        double dr = Math.sqrt(xx1 * xx1 + yy1 * yy1);
                        if (dr > sr) {
                            rad.SingleCalc(scale);
                            xx1 = sr * rad.cosine;
                            yy1 = sr * rad.sine;
                            //dr=sr;
                        }
                    double yc = 1 + Math.abs(yy1 * mc);
                    int fz = (int) (1 + c4  / this.water) + (int) (yc / sy / this.relative.temperature);
                    int hot1 = (int) Math.abs(rad.cosine / yc * this.relative.temperature * this.relative.temperature) %255;
                    int hot2 = (int) Math.abs(rad.sine / yc * this.relative.temperature) % 255;
                    int cold1 = (int) Math.abs(shade.cosine * yc / this.relative.temperature) % 255;
                    int cold2 = (int) Math.abs(shade.sine * yc / this.relative.temperature) % 255;
     //int r2=(int)(sr-dr);

     //g.setColor(new Color((c.Red()+soil.Red())>>2,(c.Green()+soil.Green())>>1,(c.Blue()+soil.Blue())>>3,(int)Math.abs(shade.cosine+shade.sine)*32));
                    //g.fillOval((int)(sx+xx1),(int)(sy+ yy1),(int)(r4),(int)(r4));
                        transMap.HintPixel((int) (sx + xx1), (int) (sy + yy1), (int) 255, (c.Red() + soil.Red() + hot1 + this.chemicalStain.Red() + fz + life.Red() * rm) >> 4, (c.Green() + soil.Green() - hot2 + cold2 + this.chemicalStain.Green() + fz + life.Green() * gm) >> 3, (c.Blue() + soil.Blue() + cold1 + this.chemicalStain.Blue() + fz + life.Blue() * bm) >> 5);
                    
                }
            }
            xs = sr * (rad.cosine);
            for (int i = 0; i < c2; i++) {
                //
                sea.SingleCalc(scale);
                shade.SingleCalc(scale);
                //circ.SingleCalc();
                rad.SingleCalc(scale);

                c.Red(((int) ((temp.Red() * rl - this.soil.Red() + this.air.Red() + ac.Red() + this.color.Red()) * Math.abs(shade.cosine)) >> 5) % 255);
                c.Green(((int) ((temp.Green() * gl - this.soil.Green() + this.air.Green() + ac.Green() + this.color.Green()) * Math.abs(shade.sine)) >> 4) % 255);
                c.Red(((int) ((temp.Blue() * bl - this.soil.Blue() + this.air.Blue() + ac.Blue() + this.color.Blue()) * Math.abs(shade.sine * shade.cosine)) >> 3) % 255);

                if (limit) {
                    xx2 += (int) (r1 * rad.sine + r1 * sea.cosine);
                    yy2 += (int) (r1 * rad.cosine + r1 * sea.sine);
                    double dr = Math.sqrt(xx2 * xx2 + yy2 * yy2);
                    if (dr > sr) {
                        rad.SingleCalc(scale);
                        xx2 = sr * rad.cosine;
                        yy2 = sr * rad.sine;
                        //dr=sr;
                    }
                } else {
                    xs += Math.abs((r1 * sea.cosine));
                    yy2 += (r1 * sea.sine);

                    double dr = Math.sqrt(xs * xs + yy2 * yy2);
                    if (dr > sr) {
                        rad.SingleCalc(scale);
                        xs = sr * ((sea.cosine));
                        yy2 = sr * ((sea.sine));
                        //dr=sr;
                    }

                }

                double yc = 1 + Math.abs(yy2 * mc);
                int fz = (int) (1 + c4 / this.relative.temperature * this.relative.radius) + (int) ((yc / sy / this.relative.temperature)*this.water);
                int hot1 = (int) Math.abs(rad.cosine / yc * this.relative.temperature * this.relative.temperature) % 255;
                int hot2 = (int) Math.abs(rad.sine / yc * this.relative.temperature) % 255;
                int cold1 = (int) Math.abs(shade.cosine * yc / this.relative.temperature) % 255;
                int cold2 = (int) Math.abs(shade.sine * yc / this.relative.temperature) % 255;
     //double r2=(int)(sr-dr);
                //double r4=Math.abs(circ.sine+circ.cosine)*0.5*r2;
                //g.setColor(new Color((c.Red())>>3,(c.Green()+this.air.Green())>>2,(c.Blue()+255)>>1,(int)Math.abs(shade.cosine-shade.sine)*64));
                //g.fillOval((int)(sx+xx2), (int)(sy+yy2),(int)(r4),(int)(r4));
                if (limit) {
                    
                    transMap.AddPixel((int) (sx + xx2), (int) (sy + yy2), 255, (c.Red()+ this.air.Red()*rl + hot1 + this.chemicalStain.Red() * rm + fz) >> 5, (c.Green() + this.air.Green()*gl - hot2 + cold2 + this.chemicalStain.Green() * gm + fz) >> 4, (c.Blue() + this.air.Blue()*bl + cold1 + this.chemicalStain.Blue() * bm + fz) >> 3);
                } else {
                    int gr=(c.Red() * rl + this.air.Red() + hot1 + this.chemicalStain.Red() * rm + fz) >> 5;
                    int gg=(c.Green() * gl + this.air.Green() - hot2 + cold2 + this.chemicalStain.Green() * gm + fz) >> 4;
                    int gb=(c.Blue() * bl + this.air.Blue() + cold1 + this.chemicalStain.Blue() * bm + fz) >> 3;
                    //System.out.println(gr+" "+gg+" "+gb+" ");
                    transMap.TintPixel((int) (sx + xs), (int) (sy + yy2), 255,gr ,gg ,gb );
                }

            }
            xs = sr * (rad.cosine);

            for (int i = 0; i < c3; i++) {
                //
                cloud.SingleCalc(scale);
                shade.SingleCalc(scale);
                //circ.SingleCalc();
                rad.SingleCalc(scale);
                c.Red(((int) ((temp.Red() * rl - this.soil.Red() + this.air.Red() + ac.Red() - this.color.Red()) * Math.abs(shade.cosine)) >> 5) % 255);
                c.Green(((int) ((temp.Green() * gl - this.soil.Green() + this.air.Green() + ac.Green() - this.color.Green()) * Math.abs(shade.sine)) >> 4) % 255);
                c.Red(((int) ((temp.Blue() * bl - this.soil.Blue() + this.air.Blue() + ac.Blue() - this.color.Blue()) * Math.abs(shade.sine * shade.cosine)) >> 3) % 255);

                if (limit) {
                    xx3 += (int) (r1 * rad.sine + r1 * cloud.cosine);
                    yy3 += (int) (r1 * rad.cosine + r1 * cloud.sine);
                    double dr = Math.sqrt(xx3 * xx3 + yy3 * yy3);
                    if (dr > sr) {
                        rad.SingleCalc(scale);
                        xx3 = sr * rad.cosine;
                        yy3 = sr * rad.sine;
                        //dr=sr;
                    }
                } else {
                    xs += Math.abs((r1 * cloud.cosine));
                    yy3 += (r1 * cloud.sine);

                    double dr = Math.sqrt(xs * xs + yy3 * yy3);
                    if (dr > sr) {
                        rad.SingleCalc(scale);
                        xs = sr * ((cloud.cosine));
                        yy3 = sr * ((cloud.sine));
                        //dr=sr;
                    }

                }
                double yc = 1 + Math.abs(yy3 * mc);
                int fz = (int) (1 + c4 *this.water / (this.relative.mass*this.relative.radius)) + (int) ((yc / sy / this.relative.temperature)*this.atmospheres);
                int hot1 = (int) Math.abs(rad.cosine / yc * this.relative.temperature) % 255;
                int hot2 = (int) Math.abs(rad.sine / yc * this.relative.temperature) % 255;
                int cold1 = (int) Math.abs(shade.cosine * yc / this.relative.temperature) % 255;
                int cold2 = (int) Math.abs(shade.sine * yc / this.relative.temperature) % 255;
     //double r2=(int)(sr-dr);
                //double r4=Math.abs(circ.sine+circ.cosine)*0.5*r2;
                //g.setColor(new Color((c.Red()+this.air.Red())>>1,(c.Green()+this.air.Green())>>1,(c.Blue()+this.air.Blue())>>1,(int)Math.abs(shade.cosine*shade.sine)*255));
                //g.fillOval((int)(sx+xx3),(int)(sy+ yy3),(int)(4),(int)(4));
                //g.setColor(new Color((c.Red()+this.air.Red())>>1,(c.Green()+this.air.Green())>>1,(c.Blue()+this.air.Blue())>>1,(int)Math.abs(shade.cosine*shade.sine)*128));
                //g.fillOval((int)(sx+xx3),(int)(sy+ yy3),(int)(2),(int)(2));
                if (limit) {
                    transMap.AddPixel((int) (sx + xx3), (int) (sy + yy3), (int) 255, (c.Red() + this.air.Red() * rm + hot1 + this.chemicalStain.Red() + fz) >> 5, (c.Green() + this.air.Green() * gm - hot2 + cold2 + this.chemicalStain.Green() + fz) >> 4, (c.Blue() + this.air.Blue() * bm + cold1 + this.chemicalStain.Blue() + fz) >> 3);
                } else {
                    int gr=(c.Red() + this.air.Red() * rm + hot1 * rl + this.chemicalStain.Red() + fz) >> 3;
                    int gg=(c.Green() + this.air.Green() * gm - hot2 * gl + cold2 + this.chemicalStain.Green() + fz) >> 4;
                    int gb=(c.Blue() + this.air.Blue() * bm + cold1 * bl + this.chemicalStain.Blue() + fz) >> 5;
                    //System.out.println(rm+" "+gm+" "+bm+" "+fz+" "+hot1+" "+cold1+" "+hot2+" "+cold2);
                    //System.out.println(gr+" "+gg+" "+gb+" ");
                    transMap.AddPixel((int) (sx + xs), (int) (sy + yy3), (int) 255,gr ,gg ,gb );
                }

            }
            if (c4 % size == br) {
                if (c4 < size) {
                    if (limit) {
                        Pigment s = new Pigment(life);
                        s.Average(temp);
                        transMap.Blur(s,2);
                    } else {
                        transMap.Blur(temp,1+(int)Math.abs(32-this.relative.radius));
                    }
                } else {

                    if (limit) {
                    transMap.Blur(8);
                    }else{
                    transMap.Blur(1+(int)Math.abs(32-this.relative.radius));
                    }
                }

            }
        }
        transMap.Blur(1);
    }

    void CalcAtmosphere() {
        double p = this.atmosphere.Total();
        //System.out.println(this.size+" "+p+" "+this.relative.temperature);
        this.atmospheres = (double) Math.sqrt(((p / this.size) * 0.5 * this.relative.temperature * this.relative.radius * this.relative.mass));
        this.atmospheres *= this.atmospheres;

        double w = (this.core.hydrogen + this.core.oxygen + this.atmosphere.hydrogen + this.atmosphere.oxygen) * this.relative.mass;
        //System.out.println(this.size+" "+w+" "+this.relative.temperature);
        if (this.relative.temperature < 3) {
            this.water = (double) ((w / (this.size + (this.size >> 1))) * (3 - this.relative.temperature) * this.relative.density);
        } else {
            this.water = (w * this.atmospheres) / (this.relative.temperature * size);
        }
        this.water *= (this.gravity / PlanetMap.PISqrd) * 1.5f;
        this.atmospheres *= (this.gravity / PlanetMap.PISqrd) * 0.5f;

        this.relative.temperature *= (this.atmospheres / (this.relative.mass * 2)) * (this.relative.radius * this.relative.radius);
        this.relative.temperature = Math.sqrt(this.relative.temperature * this.relative.temperature * 8*Math.PI)/(this.relative.radius*this.relative.radius);
        this.relative.temperature /=(this.atmospheres/this.relative.mass);
        if (this.chemicalStain == null) {
            this.chemicalStain = StellarBody.ChemicalStain(core, atmosphere, this.revolvesRound.color);
        }

        if (this.air == null) {
            this.air = StellarBody.Air(atmosphere, this.revolvesRound.color);
        }
        if (this.soil == null) {
            this.soil = StellarBody.Soil(core, this.revolvesRound.color);
        }
        if (this.color == null) {
            this.color = new Pigment(this.chemicalStain);
        }
        this.color.Add(this.air);

    }

    SmallPlanet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);

    }

    void SetUpFromSystem(StarSystem sys, Molecula rnd, StellarBody b, Star revolves, double scale) {

        this.m = b.m;
        this.d = b.d;
        this.x = b.x;
        this.y = b.y;
        this.a = b.a;
        this.b = b.b;
        this.v = b.v;

        this.pr = b.pr;
        this.rrad = b.rrad;
        this.rad = b.rad;
        this.gx = b.gx;
        this.gy = b.gy;
        this.b3 = b.b3;
        this.g3 = b.g3;
        this.r3 = b.r3;
        this.system = sys;
        int sz = (int) ((m + pr) * 64);
        if (sz > 255) {
            sz = 255;
        }
        this.size = sz;
        this.revolvesRound = revolves;
        this.orbital = new Orbit();
        this.orbital.radius = (float) (b.d);
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = b.m;
        this.relative.radius = b.pr;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        core = Core.Add(core, this.revolvesRound.prime.core);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.atmosphere = Atmosphere.Add(atmosphere, this.revolvesRound.atmosphere);
        //System.out.println(this.orbital.radius+"R "+this.size+" Temp "+this.relative.temperature+" "+revolves.RadsAt(this.orbital)+" "+revolves.LightAt(this.orbital));

        //this.relative.temperature = this.revolvesRound.relative.mass * (this.relative.density + this.revolvesRound.relative.density) / (this.orbital.radius * this.orbital.radius * Math.PI * 2);
        //System.out.println(this.size+" Temp "+this.relative.temperature+" "+revolves.RadsAt(this.orbital)+" "+revolves.LightAt(this.orbital));
        //this.relative.temperature *= 2+Math.abs(Math.log10(Math.sqrt(this.relative.energy / this.orbital.radius)));
        this.magnetosphere = (double) (((double) (core.heavyMetals + core.iron) / 255.0) * this.relative.energy);
        this.relative.temperature = this.revolvesRound.RadsAt(this.orbital) + this.revolvesRound.LightAt(this.orbital);
        CalcAtmosphere();

        this.r = (double) this.relative.radius;

    }

    static void SetUpRandomSmallPlanet(SmallPlanet planet, Star star, Orbit orbit, Molecula rnd) {
        //earths
        //planet.relative.mass=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;
        //planet.relative.radius=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;
        planet.revolvesRound = star;
        planet.relative.mass = planet.size * 0.025f + (rnd.NextDouble() - rnd.NextDouble()) * 0.01f;
        planet.relative.radius = planet.size * 0.025f + (rnd.NextDouble() - rnd.NextDouble()) * 0.01f;

        planet.size = (int) ((planet.relative.mass + planet.relative.radius) * 64);
        planet.relativeCoord.r = (double) (planet.relative.radius * PhysicalPoint.Scale);
        double r = planet.relative.radius * PlanetMap.RadiusOfEarth;
        planet.gravity = (double) ((PlanetMap.uG * planet.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        //planet.relative.mass=1.0;//TerraPI;
        //planet.relative.size=1.0;//TerraPI;
        planet.relative.density = planet.relative.mass / planet.relative.radius;
        planet.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * planet.relative.radius * planet.relative.mass;

        //planet.relative.temperature=Third-Third*NextDouble(STANDARD_PROB);
        int width = planet.size;
        int height = planet.size >> 1;
        //
        //planet.width = width;
        //planet.height = height;
        //
        planet.core = Core.RandomStarCore(rnd);
        planet.atmosphere = new Atmosphere(planet.revolvesRound.prime.atmosphere, rnd, planet);
        planet.chemicalStain = StellarBody.ChemicalStain(planet.core, planet.atmosphere, new Pigment(rnd.NextInt(255), rnd.NextInt(255), rnd.NextInt(255)));
        //planet.relative.temperature *= 1+Math.abs(Math.log10(Math.sqrt(planet.relative.energy / planet.orbital.radius)));
        planet.magnetosphere = (double) (((double) (planet.core.heavyMetals + planet.core.iron) / 255.0) * planet.relative.energy);
        planet.relative.temperature = planet.revolvesRound.RadsAt(orbit) + planet.revolvesRound.LightAt(orbit);
        
        planet.CalcAtmosphere();

        //planet.relative.temperature *=1+Math.abs(Math.log10(Math.sqrt(planet.atmospheres/ planet.relative.radius)));
        //planet.relative.temperature*=0.5;
        int m = (int) (planet.relative.mass * rnd.NextDouble() * 3);
        planet.moon = new Moon[m];
        //
        System.out.println("Planet mass " + planet.relative.mass + " radius " + planet.relative.radius + " density " + planet.relative.density);
        System.out.println("gravity " + (planet.gravity / PlanetMap.PISqrd) + " temp " + planet.relative.temperature + " water " + planet.water + " atmos " + planet.atmospheres);

        planet.r = (double) planet.relative.radius;
    }

    static void SetUpRandomTerraPlanet(Planet planet, double emass, double eradius, Molecula rnd, Core baseCore, Atmosphere baseAtmos) {
        //earths
        //planet.relative.mass=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;
        //planet.relative.radius=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;

        planet.relative.mass = emass;
        planet.relative.radius = eradius;

        planet.size = (int) ((emass + eradius) * 64);
        planet.relativeCoord.r = (double) (eradius * PhysicalPoint.Scale);
        double r = planet.relative.radius * PlanetMap.RadiusOfEarth;
        planet.gravity = (double) ((PlanetMap.uG * planet.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        //planet.relative.mass=1.0;//TerraPI;
        //planet.relative.size=1.0;//TerraPI;
        planet.relative.density = planet.relative.mass / planet.relative.radius;
        planet.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * planet.relative.radius * planet.relative.mass;
        planet.relative.temperature = Math.sqrt(planet.relative.energy);

        //planet.relative.temperature=Third-Third*NextDouble(STANDARD_PROB);
        int width = planet.size;
        int height = planet.size >> 1;
        //MUST BE EVEN
        if (width % 2 == 1) {
            width++;
        }
        if (height % 2 == 1) {
            height++;
        }
        //
        //planet.width = width;
        //planet.height = height;
        //
        planet.core = new Core(baseCore, rnd);
        //
        double p = 0;
        p += planet.atmosphere.ammonia = (rnd.NextInt(128) + rnd.NextInt(128) + baseAtmos.ammonia) >> 1;
        p += planet.atmosphere.nitrogen = Math.abs((baseAtmos.nitrogen >> 1) + rnd.NextInt(128) - (planet.atmosphere.ammonia >> 1));
        p += planet.atmosphere.hydrogen = Math.abs((baseAtmos.hydrogen >> 1) + rnd.NextInt(128) - (planet.atmosphere.ammonia >> 1));
        p += planet.atmosphere.co2 = Math.abs((baseAtmos.co2 >> 1) + rnd.NextInt(128) - rnd.NextInt(128));
        //
        p += planet.atmosphere.oxygen = ((255 - ((planet.atmosphere.nitrogen + planet.atmosphere.ammonia + planet.atmosphere.co2 + planet.atmosphere.hydrogen) >> 2)) + baseAtmos.oxygen) >> 1;//128+Next(128)-Next(128);
        p += planet.atmosphere.sulphur = ((255 - ((planet.atmosphere.oxygen + planet.atmosphere.co2) >> 1)) + baseAtmos.sulphur) >> 1;
        p += planet.atmosphere.methane = ((255 - planet.atmosphere.oxygen % (1 + planet.atmosphere.hydrogen)) + baseAtmos.methane) >> 1;
        p += planet.atmosphere.chlorine = ((255 - ((planet.atmosphere.oxygen + planet.atmosphere.co2 + planet.atmosphere.nitrogen + planet.atmosphere.hydrogen) >> 2)) + baseAtmos.chlorine) >> 1;
        //
        planet.chemicalStain = StellarBody.ChemicalStain(planet.core, planet.atmosphere, new Pigment(rnd.NextInt(255), rnd.NextInt(255), rnd.NextInt(255)));

        planet.air = StellarBody.Air(planet.atmosphere, new Pigment(rnd.NextInt(255), rnd.NextInt(255), rnd.NextInt(255)));
        planet.soil = StellarBody.Soil(planet.core, new Pigment(rnd.NextInt(255), rnd.NextInt(255), rnd.NextInt(255)));
        planet.color = new Pigment(planet.chemicalStain);
        planet.color.Add(planet.air);

        planet.magnetosphere = (double) (((double) (planet.core.heavyMetals + planet.core.iron) / 255.0) * planet.relative.energy);
        //planet.relative.temperature += Math.abs(Math.log10(Math.sqrt(planet.relative.energy / planet.orbital.radius)));
        planet.CalcAtmosphere();
        planet.relative.temperature = (1.1 + planet.relative.temperature * 0.25) * 0.5;//day and night
        int m = (int) (planet.relative.mass * rnd.NextDouble() * 3);
        planet.moon = new Moon[m];
        //
        System.out.println("Planet mass " + planet.relative.mass + " radius " + planet.relative.radius + " density " + planet.relative.density);
        System.out.println("gravity " + (planet.gravity / PlanetMap.PISqrd) + " temp " + planet.relative.temperature + " water " + planet.water + " atmos " + planet.atmospheres);

        planet.r = (double) planet.relative.radius;
    }

    static void SetUpRandomTerraSystem(SmallPlanet terra, Molecula rnd) {
        StarSystem sys = terra.system;
        Star star = new Star(sys, rnd);
        terra.system.name = "Sol Terra 1";
        Star s = terra.revolvesRound;
        float n = 3600;
        //
        Orbit orb = new Orbit(s, 0.387f, n);
        Core core = new Core();
        Atmosphere atmos = new Atmosphere();
        SmallPlanet mercury = new SmallPlanet(sys, s, 0.055f, (4.8794e6f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        mercury.name = "Mercury";
        s.planets.Append(mercury);
        n += n;
        //
        orb = new Orbit(s, 0.723f, n);

        core = new Core();
        atmos = new Atmosphere();
        SmallPlanet venus = new SmallPlanet(sys, s, 0.8149f, (12.104e6f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        venus.name = "Venus";
        s.planets.Append(venus);
        //
        n += n;
        orb = new Orbit(s, (float) (2.491e11 / PlanetMap.AU), n);
        core = new Core();
        atmos = new Atmosphere();

        SmallPlanet mars = new SmallPlanet(sys, s, 6.4191e23f / PlanetMap.MassOfEarth, (6.794e6f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        mars.name = "Mars";
        s.planets.Append(mars);
        //
        n += n;
        orb = new Orbit(s, 5.203f, n);
        core = new Core();
        atmos = new Atmosphere();
        GiantPlanet jupiter = new GiantPlanet(sys, s, 317.8f, PlanetMap.RadiusOfJupiter / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        jupiter.name = "Jupiter";

        s.planets.Append(jupiter);
        //
        n += n;
        orb = new Orbit(s, 9.539f, n);
        core = new Core();
        atmos = new Atmosphere();
        GiantPlanet saturn = new GiantPlanet(sys, s, 95.2f, (1.2e8f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        saturn.name = "Saturn";

        s.planets.Append(saturn);
        //
        n += n;
        orb = new Orbit(s, 19.22f, n);
        core = new Core();
        atmos = new Atmosphere();
        LargePlanet uranus = new LargePlanet(sys, s, 14.53f, (5.118e7f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        uranus.name = "Uranus";

        s.planets.Append(uranus);
        //

        n += n;
        orb = new Orbit(s, 30.06f, n);
        core = new Core();
        atmos = new Atmosphere();
        LargePlanet neptune = new LargePlanet(sys, s, 17.14f, (4.9528e7f * 0.5f) / PlanetMap.RadiusOfEarth, orb, rnd, core, atmos);
        neptune.name = "Neptune";

        s.planets.Append(neptune);

    }
}

class LargePlanet extends SmallPlanet {

    RingSystem ringSystem;
    //ColorMap transMap1;

    LargePlanet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }

    LargePlanet(StarSystem sys, Star revolves, Orbit orbit, int size, Molecula rnd) {
        super(sys, rnd);
        double sz = (double) (size) * 0.1f;
        this.size = (int) (sz);
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = sz * 2 + rnd.NextDouble() * sz - rnd.NextDouble() * sz;
        this.relative.radius = sz * 2 + rnd.NextDouble() * sz - rnd.NextDouble() * sz;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.relative.temperature = revolves.RadsAt(orbit) + revolves.LightAt(orbit);

        this.relative.temperature *= Math.sqrt(this.relative.energy);
        CalcAtmosphere();
        this.magnetosphere = (double) (((double) (this.core.heavyMetals + this.core.iron) / 255.0) * this.relative.energy);
        this.ringSystem = new RingSystem(this, rnd);

        this.r = (double) this.relative.radius;
    }

    LargePlanet(StarSystem sys, Star revolves, double mass, double radius, Orbit orbit, Molecula rnd, Core baseCore, Atmosphere baseAtmosphere) {
        super(sys, rnd);
        int sz = (int) ((mass + radius) * 3.2);
        if (sz > 255) {
            sz = 255;
        }
        this.size = sz;
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = mass;
        this.relative.radius = radius;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.core = Core.Add(core, baseCore);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.atmosphere = Atmosphere.Add(atmosphere, baseAtmosphere);
        this.relative.temperature = revolves.RadsAt(this.orbital) + revolves.LightAt(this.orbital);

        this.relative.temperature *= Math.sqrt(this.relative.energy);
        this.magnetosphere = (double) (((double) (core.heavyMetals + core.iron) / 255.0) * this.relative.energy);
        CalcAtmosphere();

        this.r = (double) this.relative.radius;

    }
}

class HugePlanet extends LargePlanet {

    Planetoid[] largeMoon;

    //ColorMap transMap2;
    HugePlanet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }

    HugePlanet(StarSystem sys, Star revolves, Orbit orbit, int size, Molecula rnd) {
        super(sys, rnd);
        double sz = (double) (size);
        this.size = (int) (sz);
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = size + rnd.NextDouble() * sz - rnd.NextDouble() * sz;
        this.relative.radius = size + rnd.NextDouble() * sz - rnd.NextDouble() * sz;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);

        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.relative.temperature = revolves.RadsAt(this.orbital) + revolves.LightAt(this.orbital);
        this.relative.temperature *= Math.sqrt(this.relative.energy);
        CalcAtmosphere();
        this.magnetosphere = (double) (((double) (this.core.heavyMetals + this.core.iron) / 255.0) * this.relative.energy);
        int lm = (int) (this.relative.mass * rnd.NextDouble()) / 50;
        this.largeMoon = new Planetoid[lm];

        this.r = (double) this.relative.radius;
    }
}

class GiantPlanet extends HugePlanet {

    //ColorMap transMap3;
    GiantPlanet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }

    GiantPlanet(StarSystem sys, Star revolves, Orbit orbit, int size, Molecula rnd) {
        super(sys, rnd);

        double sz = (double) (size);
        this.size = (int) (sz);
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = (size + rnd.NextDouble() * sz - rnd.NextDouble() * sz);
        this.relative.radius = (size + rnd.NextDouble() * sz - rnd.NextDouble() * sz);
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);

        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.relative.temperature = revolves.RadsAt(this.orbital) + revolves.LightAt(this.orbital);

        this.relative.temperature *= Math.sqrt(this.relative.energy);
        CalcAtmosphere();

        this.magnetosphere = (double) (((double) (this.core.heavyMetals + this.core.iron) / 255.0) * this.relative.energy);
        //

        this.r = (double) this.relative.radius;
    }

    GiantPlanet(StarSystem sys, Star revolves, double mass, double radius, Orbit orbit, Molecula rnd, Core baseCore, Atmosphere baseAtmosphere) {
        super(sys, rnd);
        int sz = (int) ((mass + radius) * 32);
        if (sz > 255) {
            sz = 255;
        }
        this.size = sz;
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = mass;
        this.relative.radius = radius;
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.core = Core.Add(core, baseCore);
        this.atmosphere = new Atmosphere(this.revolvesRound.prime.atmosphere, rnd, this);
        this.atmosphere = Atmosphere.Add(atmosphere, baseAtmosphere);
        this.relative.temperature = revolves.RadsAt(this.orbital) + revolves.LightAt(this.orbital);

        this.relative.temperature *= Math.sqrt(this.relative.energy);
        this.magnetosphere = (double) (((double) (core.heavyMetals + core.iron) / 255.0) * this.relative.energy);
        CalcAtmosphere();

        this.r = (double) this.relative.radius;
    }
}

class SuperPlanet extends GiantPlanet {

    //ColorMap transMap4;
    SuperPlanet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
    }

    SuperPlanet(StarSystem sys, Star revolves, Orbit orbit, int size, Molecula rnd) {
        super(sys, rnd);
        double sz = (double) (size);
        this.size = (int) (sz);
        this.revolvesRound = revolves;
        this.orbital = orbit;
        this.relativeCoord = this.orbital.RelativePoint();
        this.relative.mass = (size + rnd.NextDouble() * sz - rnd.NextDouble() * sz);
        this.relative.radius = (size * 2 + rnd.NextDouble() * sz - rnd.NextDouble() * sz);
        this.relativeCoord.r = (double) (this.relative.radius * PhysicalPoint.Scale);
        double r = this.relative.radius * PlanetMap.RadiusOfEarth;
        this.gravity = (double) ((PlanetMap.uG * this.relative.mass * PlanetMap.MassOfEarth) / (r * r));
        this.relative.density = this.relative.mass / this.relative.radius;
        this.relative.energy = (PlanetMap.FourThird + (PlanetMap.Third * rnd.NextDouble() - PlanetMap.FourThird * rnd.NextDouble())) * this.relative.radius * this.relative.mass;
        this.core = new Core(this.revolvesRound.core, rnd);
        this.atmosphere = new Atmosphere(revolvesRound.prime.atmosphere, rnd, this);
        this.relative.temperature = revolves.RadsAt(orbit) + revolves.LightAt(orbit);
        this.relative.temperature *= Math.sqrt(this.relative.energy);
        CalcAtmosphere();

        this.magnetosphere = (double) (((double) (this.core.heavyMetals + this.core.iron) / 255.0) * this.relative.energy);
        //

        this.r = (double) this.relative.radius;
    }
}

class Atmosphere {

    //
    int nitrogen;
    int co2;
    int hydrogen;
    int ammonia;
    int oxygen;
    int methane;
    int chlorine;
    int sulphur;

    //

    Atmosphere() {
    }

    Atmosphere(Atmosphere c, Molecula rnd, StellarBody type) {
        Atmosphere d = RandomStarAtmosphere(rnd);
        this.hydrogen = (d.hydrogen + c.hydrogen) >> 1;
        this.nitrogen = (d.nitrogen + c.nitrogen) >> 1;
        this.methane = (d.methane + c.methane) >> 1;
        this.oxygen = (d.oxygen + c.oxygen) >> 1;
        this.chlorine = (d.chlorine + c.chlorine) >> 1;
        this.sulphur = (d.sulphur + c.sulphur) >> 1;
        this.ammonia = (d.ammonia + c.ammonia) >> 1;
        this.co2 = (d.co2 + c.co2) >> 1;
        if (type instanceof Star) {
            this.ammonia = 0;
            this.co2 = 0;
            this.hydrogen = (255 + this.hydrogen) >> 1;
        } else if (type instanceof SmallPlanet) {

            this.hydrogen = (128 + this.hydrogen) >> 1;
            this.ammonia = (128 + this.ammonia) >> 1;
            this.methane = (128 + this.methane) >> 1;
            this.oxygen = (128 + this.oxygen) >> 1;
            this.co2 = (128 + this.co2) >> 1;
        } else if (type instanceof Planetoid) {
            this.co2 = (128 + this.co2) >> 1;
            this.methane = (128 + this.methane) >> 1;
            this.sulphur = (255 + this.methane) >> 1;
        } else if (type instanceof Moon) {
            this.hydrogen >>= 1;
            this.nitrogen >>= 1;
            this.methane >>= 1;
            this.oxygen >>= 1;
            this.chlorine >>= 1;
            this.sulphur >>= 1;
            this.ammonia >>= 1;
        } else if (type instanceof LargePlanet) {
            this.hydrogen = (128 + this.hydrogen) >> 1;
            this.ammonia = (128 + this.ammonia) >> 1;
            this.methane = (128 + this.methane) >> 1;
            this.co2 = (128 + this.co2) >> 1;
        } else if (type instanceof GiantPlanet) {
            this.hydrogen = (255 + this.hydrogen) >> 1;
            this.ammonia = (255 + this.ammonia) >> 1;
            this.methane = (255 + this.methane) >> 1;
        } else if (type instanceof SuperPlanet) {
            this.hydrogen = (255 + this.hydrogen) >> 1;
            this.ammonia = (this.ammonia) >> 1;
            this.methane = (this.methane) >> 1;
        }
    }

    static Atmosphere RandomStarAtmosphere(Molecula rnd) {
        Atmosphere atmos = new Atmosphere();
        atmos.hydrogen = Math.abs(128 + rnd.NextInt(128) - rnd.NextInt(128));
        atmos.oxygen = Math.abs(255 - (atmos.hydrogen >> 1));
        if (atmos.oxygen < 1) {
            atmos.oxygen = 1;
        }
        atmos.ammonia = Math.abs(128 + rnd.NextInt(128) - (atmos.hydrogen >> 1));
        atmos.methane = 1 + rnd.NextInt(128) % (1 + atmos.ammonia);
        atmos.co2 = 128 + rnd.NextInt(128) - rnd.NextInt(128);
        atmos.chlorine = Math.abs(255 - atmos.ammonia % (1 + atmos.methane));
        if (atmos.chlorine < 1) {
            atmos.chlorine = 1;
        }
        atmos.sulphur = Math.abs(255 - atmos.co2) % (1 + atmos.oxygen);
        //
        return atmos;
    }

    int Total() {
        int p = 0;
        p += this.ammonia;
        p += this.nitrogen;
        p += this.hydrogen;
        p += this.co2;
        //
        p += this.oxygen;
        p += this.sulphur;
        p += this.methane;
        p += this.chlorine;
        return p;
    }

    public static Atmosphere Add(Atmosphere a, Atmosphere b) {
        Atmosphere p = new Atmosphere();
        p.ammonia = (a.ammonia + a.ammonia) >> 1;
        p.nitrogen = (a.nitrogen + a.nitrogen) >> 1;
        p.hydrogen = (a.hydrogen + a.hydrogen) >> 1;
        p.co2 = (a.co2 + a.co2) >> 1;
        //
        p.oxygen = (a.oxygen + a.oxygen) >> 1;
        p.sulphur = (a.sulphur + a.sulphur) >> 1;
        p.methane = (a.methane + a.methane) >> 1;
        p.chlorine = (a.chlorine + a.chlorine) >> 1;
        return p;
    }
}

class HeightMap {

    final static double LOD = 1024;
    final static double MLOD = 512;

    int width;
    int height;
    float dataScale;//km
    Vector data[][];

    public HeightMap(int w, int h) {
        this.width = w;
        this.height = h;
        this.data = new Vector[w][h];
        for (int wd = 0; wd < w; wd++) {
            for (int ht = 0; ht < h; ht++) {
                this.data[wd][ht] = new Vector(0, 0, 0, 0);
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="Map Effects">
    static void AddPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += data.data[x][y].z;
                //
                ap.data[x][y].z *= 0.5;
            }
        }
    }

    static void SubtractPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z -= data.data[x][y].z;
                //
                ap.data[x][y].z *= 0.5;
            }
        }
    }

    static void FlattenPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += data.data[x][y].z;
                //
                ap.data[x][y].z /= 3.0;
            }
        }
    }

    static void AffectPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += data.data[x][y].z + data.data[x][y].z;
                //
                ap.data[x][y].z *= PlanetMap.Third;
            }
        }
    }

    static void RoughenPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += data.data[x][y].z + data.data[x][y].z + data.data[x][y].z;
                //
                ap.data[x][y].z *= PlanetMap.Third;
            }
        }
    }

    static void AmplifyPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += data.data[x][y].z;

            }
        }
    }

    static void NoiseAmplifyPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += (data.data[x][y].z * Random.NextDouble()) * 0.5;

            }
        }
    }

    static void NoiseEnhancePlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                double h = (data.data[x][y].z * data.data[x][y].z * Random.NextDouble()) * 0.5;

                ap.data[x][y].z = Math.sqrt(h * 2) * Math.signum(data.data[x][y].z);
            }
        }
    }

    static void EnhancePlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                double h = data.data[x][y].z * data.data[x][y].z;
                ap.data[x][y].z = Math.sqrt(h * 2) * Math.signum(data.data[x][y].z);
            }
        }
    }

    static void XAmplifyPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                if (ap.data[x][y].z > 0) {
                    ap.data[x][y].z += Math.abs(data.data[x][y].z);
                } else {
                    ap.data[x][y].z -= Math.abs(data.data[x][y].z);
                }
            }
        }
    }

    static void NoiseXAmplifyPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                if (ap.data[x][y].z > 0) {
                    ap.data[x][y].z += Math.abs(data.data[x][y].z + data.data[x][y].z * Random.NextDouble()) * 0.5;
                } else {
                    ap.data[x][y].z -= Math.abs(data.data[x][y].z + data.data[x][y].z * Random.NextDouble()) * 0.5;
                }
            }
        }
    }

    static void SmoothPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += ap.data[x][y].z + data.data[x][y].z;
                ap.data[x][y].z *= 0.25;
            }
        }
    }

    static void LandPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z += Math.abs(data.data[x][y].z);
                ap.data[x][y].z *= 0.5;
            }
        }
    }

    static void SeaPlanetMap(HeightMap ap, HeightMap data) {
        for (int x = 0; x < ap.width; x++) {
            for (int y = 0; y < ap.height; y++) {
                ap.data[x][y].z -= Math.abs(data.data[x][y].z);
                ap.data[x][y].z *= 0.5;
            }
        }
    }

    // </editor-fold>
    static void GetCrateredSurface(Planet moon, int age, HeightMap planetMap) {
        //
        int width = moon.planetMap.width;
        int height = moon.planetMap.height;
        planetMap.width = width;
        planetMap.height = height;
        planetMap.dataScale = moon.planetMap.dataScale;
        //
        int w = moon.planetMap.width >> 1;
        int h = moon.planetMap.height >> 1;
        int hh = h >> 1;
        double sz = moon.relative.radius;
        double ms = moon.relative.mass;
        double ds = PlanetMap.TerraPI + moon.relative.density;
        int size = (int) (((width + height) * ms)) >> 5;
        double seed = PlanetMap.A45D1000 * (moon.atmosphere.oxygen + moon.atmosphere.hydrogen + moon.atmosphere.co2 + moon.atmosphere.nitrogen);
        int dust = (int) (size * (moon.core.iron + moon.core.heavyMetals + moon.core.lightMetals + moon.core.carbon + moon.core.calcium));
        int lava = (int) (size * (moon.atmosphere.hydrogen + moon.atmosphere.oxygen + moon.core.hydrogen + moon.core.oxygen + moon.atmosphere.co2));
        //
        double t = (1.0 - Math.abs(moon.relative.temperature - 1.0));
        dust -= (int) ((lava >> 1) * t);
        lava -= (dust >> 2);
        if (lava < 1) {
            lava = 1;
        }
        if (dust < 1) {
            dust = 1;
        }

        if (lava > (dust * 100)) {
            dust = (int) Math.sqrt(dust);
            lava = (int) Math.sqrt(lava);
        }
        while (true) {

            System.out.println("Lava/Dust");
            if (lava > 16384 || dust > 16384) {
                lava -= 16384;
                dust -= 16384;
                continue;
            }
            break;
        }
        if (lava < 0) {
            lava = 0;
        }
        if (dust < 0) {
            dust = 0;
        }
        //
        while (age > 0) {
            age--;
            do {
                //
                moon.fractalSurface.seed1 = PlanetMap.A45 * (Random.NextDouble() + seed);
                moon.fractalSurface.seed2 = PlanetMap.A45 * (Random.NextDouble() + seed);
                moon.fractalSurface.molecule1 = new Molecula();
                moon.fractalSurface.molecule2 = new Molecula();
                moon.fractalSurface.molecule1.SingleCalc(sz);
                moon.fractalSurface.molecule2.SingleCalc(ms);
                double px1 = h * (moon.fractalSurface.molecule1.cosine + moon.fractalSurface.molecule2.sine);
                double py1 = hh * (moon.fractalSurface.molecule1.sine + moon.fractalSurface.molecule2.cosine);
                int land = lava;
                do {
                    moon.fractalSurface.molecule1.SingleCalc(sz);
                    moon.fractalSurface.molecule1.LineCalc(ms);
                    px1 += moon.fractalSurface.molecule1.cosine / ds;
                    py1 += moon.fractalSurface.molecule1.sine / ds;
                    if (py1 >= h) {
                        py1 = h - (py1 - h);
                        px1 = px1 + w;
                    } else if (py1 < -h) {
                        py1 = -h - (py1 + h);
                        px1 = px1 + w;
                    }
                    if (px1 >= w) {
                        px1 -= width;
                    } else if (px1 < -w) {
                        px1 += width;
                    }
                    int x1 = (int) (w + px1);
                    int y1 = (int) (h + py1);
                    moon.planetMap.data[x1][y1].z += Math.abs(moon.fractalSurface.molecule2.cosine - moon.fractalSurface.molecule2.sine) / ds;///planet.relative.density;
                    land--;
                } while (land > 0);

                moon.fractalSurface.seed1 = PlanetMap.A45 * (Random.NextDouble() + seed);
                moon.fractalSurface.seed2 = PlanetMap.A45 * (Random.NextDouble() + seed);
                moon.fractalSurface.molecule1 = new Molecula();
                moon.fractalSurface.molecule2 = new Molecula();
                moon.fractalSurface.molecule1.SingleCalc(ms);
                moon.fractalSurface.molecule2.SingleCalc(sz);
                double px2 = h * (moon.fractalSurface.molecule1.cosine + moon.fractalSurface.molecule2.sine);
                double py2 = hh * (moon.fractalSurface.molecule1.sine + moon.fractalSurface.molecule2.cosine);
                int water = dust;
                do {
                    moon.fractalSurface.molecule1.SingleCalc(sz);
                    moon.fractalSurface.molecule1.LineCalc(ms);
                    px2 += moon.fractalSurface.molecule2.cosine / ds;
                    py2 += moon.fractalSurface.molecule2.sine / ds;
                    if (py2 >= h) {
                        py2 = h - (py2 - h);
                        px2 = px2 + w;
                    } else if (py2 < -h) {
                        py2 = -h - (py2 + h);
                        px2 = px2 + w;
                    }
                    if (px2 >= w) {
                        px2 -= width;
                    } else if (px2 < -w) {
                        px2 += width;
                    }
                    int x2 = (int) (w + px2);
                    int y2 = (int) (h + py2);
                    planetMap.data[x2][y2].z += Math.abs(moon.fractalSurface.molecule1.cosine - moon.fractalSurface.molecule1.sine) / ds;///planet.relative.density;
                    water--;
                } while (water > 0);
                int collisions = age * age;
                int stable = (int) (((width + height) * ms)) >> 4;
                do {
                    int collision = (int) (stable * stable);
                    moon.fractalSurface.seed1 = PlanetMap.A45D10 * (3 + 1 / Random.Next(9999999));//*Next(9999999);
                    moon.fractalSurface.seed2 = PlanetMap.A45D10 * (3 + 1 / Random.Next(9999999));//*Next(9999999);
                    moon.fractalSurface.molecule1 = new Molecula();
                    moon.fractalSurface.molecule2 = new Molecula();
                    moon.fractalSurface.molecule1.SingleCalc(ms);
                    moon.fractalSurface.molecule2.SingleCalc(sz);
                    double px3 = width * (Random.NextDouble() - Random.NextDouble());
                    double py3 = height * (Random.NextDouble() - Random.NextDouble());
                    double crater = ms * PlanetMap.PI8;
                    crater *= crater * crater * crater;
                    do {
                        moon.fractalSurface.molecule2.SingleCalc(crater);
                        moon.fractalSurface.molecule1.LineCalc(ms);
                        px3 += moon.fractalSurface.molecule2.cosine;
                        py3 += moon.fractalSurface.molecule2.sine;
                        if (py3 >= h) {
                            py3 = h - (py3 - h);
                            px3 = px3 + w;
                        } else if (py3 < -h) {
                            py3 = -h - (py3 + h);
                            px3 = px3 + w;
                        }
                        if (px3 >= w) {
                            px3 -= width;
                        } else if (px3 < -w) {
                            px3 += width;
                        }
                        int x3 = (int) (w + px3);
                        int y3 = (int) (h + py3);
                        //if(moon.planetMap.data[x2][y2].v[Z]>0)
                        //moon.planetMap.data[x2][y2].v[Z]-=abs(moon.fractalSurface.molecule1.cosine+moon.fractalSurface.molecule1.sine)/ds;///planet.relative.density;
                        //else
                        planetMap.data[x3][y3].z -= Math.abs(moon.fractalSurface.molecule1.cosine + moon.fractalSurface.molecule1.sine) / (ds * 2);///planet.relative.density;
                        collision--;
                    } while (collision > 0);
                    collisions--;
                } while (collisions > 0);
                size--;
            } while (size > 0);

            int ht = h - 1;
            int wd = w - 1;
/// Smooth
            moon.fractalSurface.seed1 = PlanetMap.A45 * (Random.NextDouble() + seed);
            moon.fractalSurface.seed2 = PlanetMap.A45 * (Random.NextDouble() + seed);
            moon.fractalSurface.molecule1 = new Molecula();
            moon.fractalSurface.molecule2 = new Molecula();
            moon.fractalSurface.molecule1.SingleCalc(ms);
            moon.fractalSurface.molecule1.SingleCalc(sz);
            for (int y = -ht; y < ht; y++) {
                int y1 = h + y;
                int y0 = y1 - 1;
                int y2 = y1 + 1;

                for (int x = -width; x < width; x++) {
                    int x1 = w + x;
                    if (x1 >= width) {
                        x1 -= width;
                    } else if (x1 < 0) {
                        x1 += width;
                    }
                    int x0 = x1 - 1;
                    if (x0 >= width) {
                        x0 -= width;
                    } else if (x0 < 0) {
                        x0 += width;
                    }
                    int x2 = x1 + 1;
                    if (x2 >= width) {
                        x2 -= width;
                    } else if (x2 < 0) {
                        x2 += width;
                    }
                    moon.fractalSurface.molecule1.SingleCalc(ms);
                    moon.fractalSurface.molecule2.SingleCalc(sz);
                    double sum = (moon.fractalSurface.molecule1.sine + moon.fractalSurface.molecule2.cosine + moon.fractalSurface.molecule1.cosine + moon.fractalSurface.molecule2.sine) / ds;
                    double total = planetMap.data[x0][y0].z;
                    total += planetMap.data[x0][y1].z;
                    total += planetMap.data[x0][y2].z;
                    total += planetMap.data[x1][y0].z;
                    total += planetMap.data[x1][y1].z * (5 + sum);
                    total += planetMap.data[x1][y2].z;
                    total += planetMap.data[x2][y0].z;
                    total += planetMap.data[x2][y1].z;
                    total += planetMap.data[x2][y2].z;
                    planetMap.data[x1][y1].z = (total / 13.0);
                }
            }
            ///NOISE

            moon.fractalSurface.seed1 = PlanetMap.A45 * (Random.NextDouble() + seed);
            moon.fractalSurface.seed2 = PlanetMap.A45 * (Random.NextDouble() + seed);
            moon.fractalSurface.molecule1 = new Molecula();
            moon.fractalSurface.molecule2 = new Molecula();
            moon.fractalSurface.molecule1.SingleCalc(sz);
            moon.fractalSurface.molecule1.SingleCalc(ms);
            double pu = 1.0 / (PlanetMap.PI + moon.relative.density);
            double pd = 1.0 / (PlanetMap.PI - moon.relative.density);
            for (int y = -ht; y < ht; y++) {
                //int yy=h+y;//(y*abs(planet.fractalSurface.molecule1.sine));
                int y1 = h + y;
                int y0 = y1 - 1;
                int y2 = y1 + 1;
                for (int x = -wd; x < wd; x++) {

                    int x1 = w + x;
                    int x0 = x1 - 1;
                    int x2 = x1 + 1;
                    //int yy=h+(y*abs(moon.fractalSurface.molecule1.sine));
                    int xx = w + x;//(x*abs(moon.fractalSurface.molecule1.cosine));
                    moon.fractalSurface.molecule1.SingleCalc(sz);
                    moon.fractalSurface.molecule1.SingleCalc(ms);
                    //double s1=moon.fractalSurface.molecule1.sine;
                    //double c1=moon.fractalSurface.molecule1.cosine;
                    //double s2=moon.fractalSurface.molecule2.sine;
                    //double c2=moon.fractalSurface.molecule2.cosine;
                    double sum = Math.abs(moon.fractalSurface.molecule1.sine + moon.fractalSurface.molecule2.cosine + moon.fractalSurface.molecule1.cosine + moon.fractalSurface.molecule2.sine) / (PlanetMap.Third + Random.NextDouble() + Random.NextDouble() + Random.NextDouble());
                    double sqr = Math.sqrt(Math.abs(planetMap.data[x1][y1].z));
                    double high = (sum * sum + sqr) * pu;
                    double low = high * pd;
                    double mid = (low * pu) + (high * pd);
                    if (planetMap.data[x1][y1].z > 0) {
                        planetMap.data[x1][y1].z += high;
                        planetMap.data[x0][y0].z += mid;
                        planetMap.data[x1][y0].z += low;
                        planetMap.data[x0][y1].z += low;
                        planetMap.data[x2][y2].z += mid;
                        planetMap.data[x2][y0].z += mid;
                        planetMap.data[x0][y2].z += mid;
                        planetMap.data[x1][y2].z += low;
                        planetMap.data[x2][y1].z += low;
                    } else {
                        planetMap.data[x1][y1].z -= high;
                        planetMap.data[x0][y0].z -= low;
                        planetMap.data[x1][y0].z -= mid;
                        planetMap.data[x0][y1].z -= mid;
                        planetMap.data[x2][y2].z -= low;
                        planetMap.data[x2][y0].z -= low;
                        planetMap.data[x0][y2].z -= low;
                        planetMap.data[x1][y2].z -= mid;
                        planetMap.data[x2][y1].z -= mid;
                    }
                }
            }

        }
    }

    static void GetPlanetSurface(Planet planet, int age, HeightMap planetMap, double bx, double by, double cx, double cy, boolean vertical) {
//PMAP planetMap;
        int width = planet.planetMap.width;
        int height = planet.planetMap.height;
        double me = (1 + (planet.numberOfMoons / (PlanetMap.PI + planet.numberOfMoons)) + (planet.numberOfMoons / (2 + planet.numberOfMoons)) + (planet.numberOfMoons / (1 + planet.numberOfMoons)));
        me *= planet.relative.mass * (Math.PI * Random.NextDouble() + Math.PI * Random.NextDouble());

        int w = planet.planetMap.width >> 1;
        int h = planet.planetMap.height >> 1;
        int hh = h >> 1;
        double sz = planet.relative.radius;
        double ms = planet.relative.mass;
        double ds = planet.relative.density;
        int size = (int) (Math.sqrt(width + height)) >> 1;
        int earth = (int) (Math.PI * size * (planet.core.iron + planet.core.heavyMetals + planet.core.lightMetals + planet.core.carbon + planet.core.calcium));
        int sea = (int) (size * (planet.atmosphere.hydrogen + planet.atmosphere.oxygen + planet.core.hydrogen + planet.core.oxygen + planet.atmosphere.co2));
        //
        double t = (1.0 - Math.abs(planet.relative.temperature - 1.0));
        earth -= (int) ((sea >> 1) * t);
        sea -= (earth >> 2);
        if (sea < 1) {
            sea = 1;
        }
        if (earth < 1) {
            earth = 1;
        }
    //

        //earth*=2;
        if (sea > (earth * 100)) {
            earth = (int) Math.sqrt(earth);
            sea = (int) Math.sqrt(sea);
        }

        while (true) {

            System.out.println("Sea/Land");
            if (sea > 16384 || earth > 16384) {
                sea -= 16384;
                earth -= 16384;
                continue;
            }

            break;
        }
        if (sea < 0) {
            sea = 0;
        }
        if (earth < 0) {
            earth = 0;
        }
        double ws = sea / (double) (earth);
        while (age > 0) {
            age--;
            /*ds*=PlanetMap.PI;
             while(true){
             if(ids>w){ids-=h;continue;}
             break;
             }*/
            double idx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
            double idy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;

            size = (int) (Math.sqrt(width + height)) >> 1;
            do {
                //

                planet.fractalSurface.molecule1 = new Molecula();
                planet.fractalSurface.molecule2 = new Molecula();
                planet.fractalSurface.molecule1.SingleCalc(sz);
                planet.fractalSurface.molecule2.SingleCalc(ms);
                double px1 = (bx + (bx * Random.NextDouble() - bx * Random.NextDouble()) * Math.PI) * cx;
                double py1 = (by + (by * Random.NextDouble() - by * Random.NextDouble()) * Math.PI) * cy;
                int land = earth;
                do {
                    planet.fractalSurface.molecule1.SingleCalc(sz);
                    planet.fractalSurface.molecule2.SingleCalc(ms);
                    px1 += planet.fractalSurface.molecule1.cosine * idx;
                    py1 += planet.fractalSurface.molecule1.sine * idy;
                    while (true) {

                        if (py1 >= h) {
                            py1 = h - (py1 - h);
                            px1 = px1 + w;
                            continue;
                        } else if (py1 < -h) {
                            py1 = -h - (py1 + h);
                            px1 = px1 + w;
                            continue;
                        }
                        if (px1 >= w) {
                            px1 -= width;
                            continue;
                        } else if (px1 < -w) {
                            px1 += width;
                            continue;
                        }
                        break;
                    }
                    int x1 = (int) (w + px1);
                    int y1 = (int) (h + py1);
                    //if(x1<0 || x1>=width ||y1<0 || y1>=height)
                    //printf("\nx:%dy:%d\n",x1,y1);
                    planetMap.data[x1][y1].z += me * Math.abs(planet.fractalSurface.molecule2.cosine - planet.fractalSurface.molecule2.sine);///(ds+Math.abs(h/py1));///planet.relative.density;
                    land--;
                } while (land > 0);

                planet.fractalSurface.molecule1 = new Molecula();
                planet.fractalSurface.molecule2 = new Molecula();
                planet.fractalSurface.molecule1.SingleCalc(ms);
                planet.fractalSurface.molecule2.SingleCalc(sz);

                double px2 = (bx + (bx * Random.NextDouble() - bx * Random.NextDouble()) * Math.PI) * cx;
                double py2 = (by + (by * Random.NextDouble() - by * Random.NextDouble()) * Math.PI) * cy;
                int water = sea;
                do {
                    planet.fractalSurface.molecule2.SingleCalc(sz);
                    planet.fractalSurface.molecule1.SingleCalc(ms);
                    px2 += planet.fractalSurface.molecule2.cosine * idy;
                    py2 += planet.fractalSurface.molecule2.sine * idx;
                    while (true) {

                        if (py2 >= h) {
                            py2 = h - (py2 - h);
                            px2 = px2 + w;
                            continue;
                        } else if (py2 < -h) {
                            py2 = -h - (py2 + h);
                            px2 = px2 + w;
                            continue;
                        }
                        if (px2 >= w) {
                            px2 -= width;
                            continue;
                        } else if (px2 < -w) {
                            px2 += width;
                            continue;
                        }
                        break;
                    }
                    int x2 = (int) (w + px2);
                    int y2 = (int) (h + py2);
                    planetMap.data[x2][y2].z -= me * Math.abs(planet.fractalSurface.molecule1.cosine - planet.fractalSurface.molecule1.sine);///(ds+Math.abs(h/py2));///planet.relative.density;
                    water--;
                } while (water > 0);
                size--;
            } while (size > 0);

        }
        ds = (PlanetMap.TerraPI + planet.relative.density);
        int ht = h - 1;
        int wd = w - 1;
/// Smooth
        System.out.println("smooth");
        planet.fractalSurface.molecule1 = new Molecula();
        planet.fractalSurface.molecule2 = new Molecula();
        planet.fractalSurface.molecule1.SingleCalc(ms);
        planet.fractalSurface.molecule2.SingleCalc(sz);
        for (int y = -ht; y < ht; y++) {
            int y1 = h + y;
            int y0 = y1 - 1;
            int y2 = y1 + 1;
            //int ay=1+((int)abs((y*(double)y/h))>>2);
            int ay = 1 + (Math.abs(y) >> 3);
            int a = 1 + (ay >> 2);
            double as = a + 3;
            double av = 1.0 / (11 + a);//amp
            double dy = ds + Math.abs(y) / h;

            for (int x = -width; x < width; x++) {
                for (int z = -ay; z < ay; z += a) {
                    int x1 = w + x + z;
                    if (x1 >= width) {
                        x1 -= width;
                    } else if (x1 < 0) {
                        x1 += width;
                    }
                    int x0 = x1 - 1;
                    if (x0 >= width) {
                        x0 -= width;
                    } else if (x0 < 0) {
                        x0 += width;
                    }
                    int x2 = x1 + 1;
                    if (x2 >= width) {
                        x2 -= width;
                    } else if (x2 < 0) {
                        x2 += width;
                    }
                    planet.fractalSurface.molecule1.SingleCalc(ms);
                    planet.fractalSurface.molecule2.SingleCalc(sz);
                    double sum = (planet.fractalSurface.molecule1.sine + planet.fractalSurface.molecule2.cosine + planet.fractalSurface.molecule1.cosine + planet.fractalSurface.molecule2.sine) / dy;
                    double total = planetMap.data[x0][y0].z;
                    total += planetMap.data[x0][y1].z;
                    total += planetMap.data[x0][y2].z;
                    total += planetMap.data[x1][y0].z;
                    total += planetMap.data[x1][y1].z * (as + sum);
                    total += planetMap.data[x1][y2].z;
                    total += planetMap.data[x2][y0].z;
                    total += planetMap.data[x2][y1].z;
                    total += planetMap.data[x2][y2].z;
                    planetMap.data[x1][y1].z = (total * av);
                }
            }
        }
        ///NOISE
        //printf("noise\n");
        planet.fractalSurface.molecule1 = new Molecula();
        planet.fractalSurface.molecule2 = new Molecula();
        planet.fractalSurface.molecule1.SingleCalc(sz);
        planet.fractalSurface.molecule2.SingleCalc(ms);
        double pu = 1.0 / (PlanetMap.PI + planet.relative.density);
        double pd = 1.0 / (PlanetMap.PI - planet.relative.density);
        if (vertical) {

            for (int y = -ht; y < ht; y++) {
                int y1 = h + y;
                int y0 = y1 - 1;
                int y2 = y1 + 1;
                for (int x = -wd; x < wd; x++) {

                    int x1 = w + x;
                    int x0 = x1 - 1;
                    int x2 = x1 + 1;
                    int xx = w + x;
                    planet.fractalSurface.molecule2.SingleCalc(ms);
                    planet.fractalSurface.molecule1.SingleCalc(sz);
                    double sum = Math.abs(planet.fractalSurface.molecule1.sine + planet.fractalSurface.molecule2.cosine + planet.fractalSurface.molecule1.cosine + planet.fractalSurface.molecule2.sine) / (PlanetMap.Third + Random.NextDouble() + Random.NextDouble() + Random.NextDouble());//NextDouble(999999)+NextDouble(999999)+NextDouble(999999)
                    double sqr = Math.sqrt(Math.abs(planetMap.data[x1][y1].z));
                    double high = (sum * sum + sqr) * pu;
                    double low = high * pd;
                    double mid = (low * pu) + (high * pd);
                    double swap;
                    switch (Random.Next(8)) {
                        case 1: {
                            swap = high;
                            high = mid;
                            mid = low;
                            low = swap;
                            break;
                        }
                        case 2: {
                            swap = high;
                            high = low;
                            low = mid;
                            mid = swap;
                            break;
                        }
                        case 3: {
                            swap = low;
                            low = mid;
                            mid = swap;
                            break;
                        }
                        case 4: {
                            swap = high;
                            high = mid;
                            mid = swap;
                            break;
                        }
                        case 5: {
                            swap = high;
                            high = low;
                            low = swap;
                            break;
                        }
                        case 6: {
                            break;
                        }
                        case 7: {
                            break;
                        }
                        case 8: {
                            break;
                        }
                    }
                    if (planetMap.data[x1][y1].z > 0) {
                        planetMap.data[x1][y1].z += high;
                        planetMap.data[x0][y0].z += mid;
                        planetMap.data[x1][y0].z += low;
                        planetMap.data[x0][y1].z += low;
                        planetMap.data[x2][y2].z += mid;
                        planetMap.data[x2][y0].z += mid;
                        planetMap.data[x0][y2].z += mid;
                        planetMap.data[x1][y2].z += low;
                        planetMap.data[x2][y1].z += low;
                    } else {
                        planetMap.data[x1][y1].z -= high;
                        planetMap.data[x0][y0].z -= low;
                        planetMap.data[x1][y0].z -= mid;
                        planetMap.data[x0][y1].z -= mid;
                        planetMap.data[x2][y2].z -= low;
                        planetMap.data[x2][y0].z -= low;
                        planetMap.data[x0][y2].z -= low;
                        planetMap.data[x1][y2].z -= mid;
                        planetMap.data[x2][y1].z -= mid;
                    }
                }
            }
        } else {
            for (int x = -wd; x < wd; x++) {

                int x1 = w + x;
                int x0 = x1 - 1;
                int x2 = x1 + 1;
                int xx = w + x;
                for (int y = -ht; y < ht; y++) {
                    int y1 = h + y;
                    int y0 = y1 - 1;
                    int y2 = y1 + 1;
                    planet.fractalSurface.molecule1.SingleCalc(sz);
                    planet.fractalSurface.molecule2.SingleCalc(ms);
                    double sum = Math.abs(planet.fractalSurface.molecule1.sine + planet.fractalSurface.molecule2.cosine + planet.fractalSurface.molecule1.cosine + planet.fractalSurface.molecule2.sine) / (PlanetMap.Third + Random.NextDouble() + Random.NextDouble() + Random.NextDouble());//NextDouble(999999)+NextDouble(999999)+NextDouble(999999)
                    double sqr = Math.sqrt(Math.abs(planetMap.data[x1][y1].z));
                    double high = (sum * sum + sqr) * pu;
                    double low = high * pd;
                    double mid = (low * pu) + (high * pd);
                    double swap;
                    switch (Random.Next(8)) {
                        case 1: {
                            swap = high;
                            high = mid;
                            mid = low;
                            low = swap;
                            break;
                        }
                        case 2: {
                            swap = high;
                            high = low;
                            low = mid;
                            mid = swap;
                            break;
                        }
                        case 3: {
                            swap = low;
                            low = mid;
                            mid = swap;
                            break;
                        }
                        case 4: {
                            swap = high;
                            high = mid;
                            mid = swap;
                            break;
                        }
                        case 5: {
                            swap = high;
                            high = low;
                            low = swap;
                            break;
                        }
                        case 6: {
                            break;
                        }
                        case 7: {
                            break;
                        }
                        case 8: {
                            break;
                        }
                    }
                    if (planetMap.data[x1][y1].z > 0) {
                        planetMap.data[x1][y1].z += high;
                        planetMap.data[x0][y0].z += mid;
                        planetMap.data[x1][y0].z += low;
                        planetMap.data[x0][y1].z += low;
                        planetMap.data[x2][y2].z += mid;
                        planetMap.data[x2][y0].z += mid;
                        planetMap.data[x0][y2].z += mid;
                        planetMap.data[x1][y2].z += low;
                        planetMap.data[x2][y1].z += low;
                    } else {
                        planetMap.data[x1][y1].z -= high;
                        planetMap.data[x0][y0].z -= low;
                        planetMap.data[x1][y0].z -= mid;
                        planetMap.data[x0][y1].z -= mid;
                        planetMap.data[x2][y2].z -= low;
                        planetMap.data[x2][y0].z -= low;
                        planetMap.data[x0][y2].z -= low;
                        planetMap.data[x1][y2].z -= mid;
                        planetMap.data[x2][y1].z -= mid;
                    }
                }
            }

        }
        //return planetMap;
    }

    static void PlanetEffects(Planet planet, int age) {
        //PlanetMap add=(PlanetMap)(malloc(sizeof(PMAP)));free(add);
        int h = planet.planetMap.height >> 1;
        int hh = h >> 1;
        double bx = (h * Random.NextDouble() - h * Random.NextDouble()) * Math.PI;
        double by = (hh * Random.NextDouble() - hh * Random.NextDouble()) * Math.PI;
        double cx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        double cy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        PlanetEffects(planet, bx, by, cx, cy, age);
    }

    static void PlanetEffects(Planet planet, double bx, double by, double cx, double cy, int age) {
        //PlanetMap add=(PlanetMap)(malloc(sizeof(PMAP)));free(add);
        //RandomPlanetEffect(sun,planet);
        if (planet.numberOfMoons > 0) {

            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            RoughenPlanetMap(planet.planetMap, add);

        } else {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            //MOON moon;
            //GetCrateredSurface(planet,age,add);
            GetPlanetSurface(planet, age, add, bx, by, -cx, -cy, Random.NextBool());
            RoughenPlanetMap(planet.planetMap, add);

        }
        //
        if (planet.relative.density > 2) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);

            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            FlattenPlanetMap(planet.planetMap, add);

        } else if (planet.relative.density < 0.5) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            AddPlanetMap(planet.planetMap, add);

        }
        if (planet.atmospheres < 0.5) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            AmplifyPlanetMap(planet.planetMap, add);

        } else if (planet.atmospheres > 2) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);

            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            SmoothPlanetMap(planet.planetMap, add);

        }

        //
        if (planet.relative.temperature > 2 || planet.relative.temperature < 0.5) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);

            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            AffectPlanetMap(planet.planetMap, add);

        }
        if (planet.water > 1.5) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            SeaPlanetMap(planet.planetMap, add);

        } else if (planet.water < 0.5) {
            HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            LandPlanetMap(planet.planetMap, add);

        }
        //
        HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);

        GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
        XAmplifyPlanetMap(planet.planetMap, add);

    }

    static void RandomPlanetEffect(Planet planet, double bx, double by, double cx, double cy, int age) {
        System.out.println("Random Effects");
        HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);

        System.out.println("Get Surface");
        boolean b = Random.NextBool();
        if (b) {

            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
        } else {
            GetPlanetSurface(planet, age, add, bx, by, -cx, -cy, Random.NextBool());
            //GetCrateredSurface(planet,age,add);
        }
        int r = Random.Next(10);

        System.out.println("Add Surface");
        if (r == 1) {

            SubtractPlanetMap(planet.planetMap, add);
        } else if (r == 2) {
            RoughenPlanetMap(planet.planetMap, add);
        } else //
        if (r == 3) {
            FlattenPlanetMap(planet.planetMap, add);
        } else if (r == 4) {
            AddPlanetMap(planet.planetMap, add);
        } else if (r == 5) {
            //
            AmplifyPlanetMap(planet.planetMap, add);
        } else if (r == 6) {
            //
            SmoothPlanetMap(planet.planetMap, add);
        } else //
        if (r == 7) {
            AffectPlanetMap(planet.planetMap, add);
        } else if (r == 8) {
            SeaPlanetMap(planet.planetMap, add);
        } else if (r == 9) {
            LandPlanetMap(planet.planetMap, add);
        } else if (r == 10) {
            //

            XAmplifyPlanetMap(planet.planetMap, add);
        }

    }

    static void RandomPlanetEffect(Planet planet, int age) {
        System.out.println("Random Effects");
        HeightMap add = new HeightMap(planet.surfaceMap.width, planet.surfaceMap.height);
        int h = planet.planetMap.height >> 1;
        int hh = h >> 1;
        double bx = (h * Random.NextDouble() - h * Random.NextDouble()) * Math.PI;
        double by = (hh * Random.NextDouble() - hh * Random.NextDouble()) * Math.PI;
        double cx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        double cy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;

        System.out.println("Get Surface");
        boolean b = Random.NextBool();
        if (b) {

            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
        } else {
            GetPlanetSurface(planet, age, add, bx, by, cx, cy, Random.NextBool());
            //GetCrateredSurface(planet,age,add);
        }
        int r = Random.Next(10);

        System.out.println("Add Surface");
        if (r == 1) {

            SubtractPlanetMap(planet.planetMap, add);
        } else if (r == 2) {
            RoughenPlanetMap(planet.planetMap, add);
        } else //
        if (r == 3) {
            FlattenPlanetMap(planet.planetMap, add);
        } else if (r == 4) {
            AddPlanetMap(planet.planetMap, add);
        } else if (r == 5) {
            //
            AmplifyPlanetMap(planet.planetMap, add);
        } else if (r == 6) {
            //
            SmoothPlanetMap(planet.planetMap, add);
        } else //
        if (r == 7) {
            AffectPlanetMap(planet.planetMap, add);
        } else if (r == 8) {
            SeaPlanetMap(planet.planetMap, add);
        } else if (r == 9) {
            LandPlanetMap(planet.planetMap, add);
        } else if (r == 10) {
            //

            XAmplifyPlanetMap(planet.planetMap, add);
        }

    }

    static void GeneratePlanetSurface(Planet planet, int age) {
        int h = planet.planetMap.height >> 1;
        int hh = h >> 1;
        double bx = (h * Random.NextDouble() - h * Random.NextDouble()) * Math.PI;
        double by = (hh * Random.NextDouble() - hh * Random.NextDouble()) * Math.PI;
        double cx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        double cy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
        GeneratePlanetSurface(planet, bx, by, cx, cy, age);
    }

    static void GeneratePlanetSurface(Planet planet, double bx, double by, double cx, double cy, int age) {
        int width = planet.planetMap.width;
        int height = planet.planetMap.height;
        double me = 1 + (planet.numberOfMoons / (PlanetMap.PI + planet.numberOfMoons));
        me += (planet.numberOfMoons / (2 + planet.numberOfMoons));
        me += (planet.numberOfMoons / (1 + planet.numberOfMoons));
        me *= PlanetMap.PI2;
        System.out.printf("\nW:%dH:%d\n", width, height);
        int w = planet.planetMap.width >> 1;
        int h = planet.planetMap.height >> 1;
        int hh = h >> 1;
        double sz = planet.relative.radius;
        double ms = planet.relative.mass;
        double ds = planet.relative.density;
        int size = (int) (Math.sqrt(width + height)) >> 1;
        int earth = (int) (size * (planet.core.iron + planet.core.heavyMetals + planet.core.lightMetals + planet.core.carbon + planet.core.calcium));
        int sea = (int) (size * (planet.atmosphere.hydrogen + planet.atmosphere.oxygen + planet.core.hydrogen + planet.core.oxygen + planet.atmosphere.co2));
        //
        double t = (1.0 - Math.abs(planet.relative.temperature - 1.0));
        earth -= (int) ((sea >> 1) * t);
        sea -= (earth >> 2);
        if (sea < 1) {
            sea = 1;
        }
        if (earth < 1) {
            earth = 1;
        }
        //earth*=2;
        //tryagain:
        if (sea > (earth * 100)) {
            earth = (int) Math.sqrt(earth);
            sea = (int) Math.sqrt(sea);//goto tryagain;
        }
        while (true) {
            if (sea > 16384 || earth > 16384) {
                sea -= 16384;
                earth -= 16384;
                continue;
            }
            break;
        }
        if (sea < 0) {
            sea = 0;
        }
        if (earth < 0) {
            earth = 0;
        }

        //age=10;
        //double ws=sea/(double)(1+earth);
        //planet.water=(float)(sea/(sea+earth));
        while (age > 0) {
            age--;
            size = (int) (Math.sqrt(width + height)) >> 1;
            double idx = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
            double idy = 0.618 + (0.5 + Random.NextDouble() - Random.NextDouble());//ws/ds;
            do {
                //*//

                planet.fractalSurface.molecule1 = new Molecula();
                planet.fractalSurface.molecule2 = new Molecula();
                planet.fractalSurface.molecule1.SingleCalc(sz);
                planet.fractalSurface.molecule2.SingleCalc(ms);
                double px1 = (bx + (bx * Random.NextDouble() - bx * Random.NextDouble()) * Math.PI) * cx;
                double py1 = (by + (by * Random.NextDouble() - by * Random.NextDouble()) * Math.PI) * cy;
                int land = earth;

                //System.out.println("land"+land);
                do {
                    planet.fractalSurface.molecule1.SingleCalc(sz);
                    planet.fractalSurface.molecule2.SingleCalc(ms);
                    px1 += planet.fractalSurface.molecule1.cosine * idx;
                    py1 += planet.fractalSurface.molecule1.sine * idy;
                    while (true) {
                        if (py1 >= h) {
                            py1 = h - (py1 - h);
                            px1 = px1 + w;
                            continue;
                        } else if (py1 < -h) {
                            py1 = -h - (py1 + h);
                            px1 = px1 + w;
                            continue;
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        if (px1 >= w) {
                            px1 -= width;
                            continue;
                        } else if (px1 < -w) {
                            px1 += width;
                            continue;
                        } else {
                            break;
                        }
                    }
                    int x1 = (int) (w + px1);
                    int y1 = (int) (h + py1);
                    //if(x1<0 || x1>=width ||y1<0 || y1>=height) printf("\nx:%dy:%d\n",x1,y1);
                    planet.planetMap.data[x1][y1].z += Math.abs(planet.fractalSurface.molecule2.cosine - planet.fractalSurface.molecule2.sine);///(ds+Math.abs(h/py1));///planet.relative.density;

                    //System.out.println(planet.planetMap.data[x1][y1].z);
                    land--;
                } while (land > 0);

                planet.fractalSurface.molecule1 = new Molecula();
                planet.fractalSurface.molecule2 = new Molecula();
                planet.fractalSurface.molecule1.SingleCalc(ms);
                planet.fractalSurface.molecule2.SingleCalc(sz);

                double px2 = (bx + (bx * Random.NextDouble() - bx * Random.NextDouble()) * Math.PI) * cx;
                double py2 = (by + (by * Random.NextDouble() - by * Random.NextDouble()) * Math.PI) * cy;
                int water = sea;

                //System.out.println("water"+water);
                do {
                    planet.fractalSurface.molecule2.SingleCalc(sz);
                    planet.fractalSurface.molecule1.SingleCalc(ms);
                    px2 += planet.fractalSurface.molecule2.cosine * idy;
                    py2 += planet.fractalSurface.molecule1.sine * idx;
                    test2:

                    while (true) {
                        if (py2 >= h) {
                            py2 = h - (py2 - h);
                            px2 = px2 + w;
                            continue;
                        } else if (py2 < -h) {
                            py2 = -h - (py2 + h);
                            px2 = px2 + w;
                            continue;
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        if (px2 >= w) {
                            px2 -= width;
                            continue;
                        } else if (px2 < -w) {
                            px2 += width;
                            continue;
                        } else {
                            break;
                        }
                    }
                    int x2 = (int) (w + px2);
                    int y2 = (int) (h + py2);
                    planet.planetMap.data[x2][y2].z -= Math.abs(planet.fractalSurface.molecule1.cosine - planet.fractalSurface.molecule1.sine);///(ds+Math.abs(h/py2));///planet.relative.density;
                    water--;

                    //System.out.println(planet.planetMap.data[x2][y2].z);
                } while (water > 0);//*/
                size--;
            } while (size > 0);
        }
        ds = (PlanetMap.TerraPI + planet.relative.density);

        int ht = h - 1;
        int wd = w - 1;
/// Smooth

        System.out.println("smooth");
        planet.fractalSurface.molecule1 = new Molecula();
        planet.fractalSurface.molecule2 = new Molecula();
        planet.fractalSurface.molecule1.LineCalc(ms);
        planet.fractalSurface.molecule2.SingleCalc(sz);
        for (int y = ht; y < ht; y++) {
            int y1 = h + y;
            int y0 = y1 - 1;
            int y2 = y1 + 1;

            int ay = 1 + (Math.abs(y) >> 3);
            int a = 1 + (ay >> 2);
            double as = a + 3;
            double av = 1.0 / (11 + a);
            double dy = ds + Math.abs(y) / h;
            for (int x = -width; x < width; x++) {
                for (int z = -ay; z < ay; z += a) {
                    int x1 = w + x + z;
                    while (true) {
                        if (x1 >= width) {
                            x1 -= width;
                            continue;
                        } else if (x1 < 0) {
                            x1 += width;
                            continue;
                        } else {

                            break;
                        }
                    }
                    int x0 = x1 - 1;
                    while (true) {
                        if (x0 >= width) {
                            x0 -= width;
                            continue;
                        } else if (x0 < 0) {
                            x0 += width;
                            continue;
                        } else {
                            break;
                        }
                    }
                    int x2 = x1 + 1;
                    while (true) {
                        if (x2 >= width) {
                            x2 -= width;
                            continue;
                        } else if (x2 < 0) {
                            x2 += width;
                            continue;
                        } else {
                            break;
                        }
                    }
                    planet.fractalSurface.molecule1.LineCalc(ms);
                    planet.fractalSurface.molecule2.SingleCalc(sz);
                    double sum = (planet.fractalSurface.molecule1.sine + planet.fractalSurface.molecule2.cosine + planet.fractalSurface.molecule1.cosine + planet.fractalSurface.molecule2.sine) / (dy);
                    double total = planet.planetMap.data[x0][y0].z;
                    total += planet.planetMap.data[x0][y1].z;
                    total += planet.planetMap.data[x0][y2].z;
                    total += planet.planetMap.data[x1][y0].z;
                    total += planet.planetMap.data[x1][y1].z * (as + sum);
                    total += planet.planetMap.data[x1][y2].z;
                    total += planet.planetMap.data[x2][y0].z;
                    total += planet.planetMap.data[x2][y1].z;
                    total += planet.planetMap.data[x2][y2].z;
                    planet.planetMap.data[x1][y1].z = (total * av);

                }
            }
        }
   ///NOISE
        //*

        //printf("noise\n");
        planet.fractalSurface.molecule1 = new Molecula();
        planet.fractalSurface.molecule2 = new Molecula();
        planet.fractalSurface.molecule1.SingleCalc(sz);
        planet.fractalSurface.molecule2.SingleCalc(ms);
        double pu = 1.0 / (PlanetMap.PI + planet.relative.density);
        double pd = 1.0 / (PlanetMap.PI - planet.relative.density);

        for (int x = -wd; x < wd; x++) {

            int x1 = w + x;
            int x0 = x1 - 1;
            int x2 = x1 + 1;
            int xx = w + x;//(x*abs(planet.fractalSurface.molecule1.cosine));
            for (int y = -ht; y < ht; y++) {
                //int yy=h+y;//(y*abs(planet.fractalSurface.molecule1.sine));
                int y1 = h + y;
                int y0 = y1 - 1;
                int y2 = y1 + 1;
                //int yy=h+(y*abs(planet.fractalSurface.molecule1.sine));
                planet.fractalSurface.molecule1.SingleCalc(sz);
                planet.fractalSurface.molecule2.SingleCalc(ms);
                //double s1=planet.fractalSurface.molecule1.sine;
                //double c1=planet.fractalSurface.molecule1.cosine;
                //double s2=planet.fractalSurface.molecule2.sine;
                //double c2=planet.fractalSurface.molecule2.cosine;
                double sum = Math.abs(planet.fractalSurface.molecule1.sine + planet.fractalSurface.molecule2.cosine + planet.fractalSurface.molecule1.cosine + planet.fractalSurface.molecule2.sine) / (PlanetMap.Third + Random.NextDouble() + Random.NextDouble() + Random.NextDouble());
                double sqr = 1;//sqrt(fabs(planet.planetMap.data[x1][y1].v[Z]));
                double high = (sum * sum + sqr) * pu;
                double low = high * pd;
                double mid = (low * pu) + (high * pd);
                double swap;
                switch (Random.Next(8)) {
                    case 1: {
                        swap = high;
                        high = mid;
                        mid = low;
                        low = swap;
                        break;
                    }
                    case 2: {
                        swap = high;
                        high = low;
                        low = mid;
                        mid = swap;
                        break;
                    }
                    case 3: {
                        swap = low;
                        low = mid;
                        mid = swap;
                        break;
                    }
                    case 4: {
                        swap = high;
                        high = mid;
                        mid = swap;
                        break;
                    }
                    case 5: {
                        swap = high;
                        high = low;
                        low = swap;
                        break;
                    }
                    case 6: {
                        break;
                    }
                    case 7: {
                        break;
                    }
                    case 8: {
                        break;
                    }
                }

                if (planet.planetMap.data[x1][y1].z > 0) {
                    planet.planetMap.data[x1][y1].z += high;
                    planet.planetMap.data[x0][y0].z += mid;
                    planet.planetMap.data[x1][y0].z += low;
                    planet.planetMap.data[x0][y1].z += low;
                    planet.planetMap.data[x2][y2].z += mid;
                    planet.planetMap.data[x2][y0].z += mid;
                    planet.planetMap.data[x0][y2].z += mid;
                    planet.planetMap.data[x1][y2].z += low;
                    planet.planetMap.data[x2][y1].z += low;
                } else {
                    planet.planetMap.data[x1][y1].z -= high;
                    planet.planetMap.data[x0][y0].z -= low;
                    planet.planetMap.data[x1][y0].z -= mid;
                    planet.planetMap.data[x0][y1].z -= mid;
                    planet.planetMap.data[x2][y2].z -= low;
                    planet.planetMap.data[x2][y0].z -= low;
                    planet.planetMap.data[x0][y2].z -= low;
                    planet.planetMap.data[x1][y2].z -= mid;
                    planet.planetMap.data[x2][y1].z -= mid;
                }
            }
        }//*/

        double a = 0;
        for (int x = 0; x < planet.planetMap.width; x++) {
            for (int y = 0; y < planet.planetMap.height; y++) {
                //System.out.println(planet.planetMap.data[x][y].z);
                a += planet.planetMap.data[x][y].z;
            }
        }
        a /= planet.planetMap.height * planet.planetMap.width;
        System.out.println(a);
    }

    static void GeneratePlanetSurfaceImage(Planet planet, Pigment stain) {
        int width = planet.planetMap.width;
        int height = planet.planetMap.height;
        int ht = height >> 1;
        int hh = ht >> 1;
        //double sz=planet.relative.size;
        //double ms=planet.relative.mass;
        double dn = PlanetMap.TerraPI + planet.relative.density;
        double ds = planet.planetMap.dataScale;
        double dl = planet.planetMap.dataScale / dn;
        int water = planet.atmosphere.hydrogen + planet.atmosphere.oxygen + planet.core.hydrogen + planet.core.oxygen;
        double ghe = 1 + Math.abs((planet.atmosphere.ammonia + planet.atmosphere.methane * 3 + planet.atmosphere.co2 + planet.core.carbon) - water) * 0.5;
        double metals = (planet.core.heavyMetals + planet.core.lightMetals) >> 1;
        double iron = ((planet.atmosphere.sulphur + planet.core.oxygen) % (1 + planet.core.iron) + planet.atmosphere.co2) >> 1;
        double organic = (planet.core.carbon + planet.core.calcium) >> 1;
        int aminoAcid = (planet.atmosphere.ammonia + planet.atmosphere.methane + planet.atmosphere.chlorine + planet.atmosphere.co2) >> 2;
        double t = (planet.relative.temperature / (water / 27.3));
        int temp = (int) (planet.relative.temperature * PlanetMap.PI4);
        int atp = -(int) (100 * planet.relative.temperature - 80);
        if (atp > 192) {
            atp = 192;
        }
        if (atp < -192) {
            atp = -192;
        }
        double s = t;//stable
        t *= t / PlanetMap.FourThird;//cap
        ///
        for (int y = 0; y < height; y++) {
            int v = 128;
            int aty = Math.abs(y - ht);
            double ts = ((double) ((hh + aty * aty) * (256 - ghe)) / (height * PlanetMap.PI8 * temp * temp));//tundra
            double st = ((double) ((hh + ht - aty) * ghe * temp * temp) / (height * water));//desert
            double tempr = ts / t;//temporate
            int rt = (int) (tempr + (st * ghe * s) + planet.atmosphere.ammonia + planet.atmosphere.co2) >> 2;
            int gt = (int) (tempr + planet.atmosphere.methane + planet.atmosphere.ammonia + planet.atmosphere.chlorine) >> 2;
            int bt = (int) (tempr + planet.atmosphere.nitrogen + planet.atmosphere.hydrogen + planet.atmosphere.oxygen) >> 2;
            if (bt < 0) {
                bt = 0;
            }
            if (gt < 0) {
                gt = 0;
            }
            if (rt < 0) {
                rt = 0;
            }
            //
            for (int x = 0; x < width; x++) {
                planet.planetMap.data[x][y].x = x * ds;
                planet.planetMap.data[x][y].y = y * ds;
                double h = planet.planetMap.data[x][y].z;
                if (h > 0) {
                    int blue = (int) (h * metals / dl + bt + stain.Blue()) >> 1;
                    int green = (aminoAcid + (int) (h * organic / dl) + gt + gt + stain.Green()) >> 2;//night
                    int red = (int) (h * iron / dl + rt + stain.Red()) >> 1;
                    if (blue > 255 && green > 255 && red > 255) {

                        v = 128 + ((256 + (Math.abs(256 - red % 128 - green % 128 - blue % 128 - (x * x + y) % 128))) >> 2);

                        red += v;
                        green += v;
                        blue += v;
                        red >>= 1;
                        green >>= 1;
                        blue >>= 1;
                        while (true) {
                            if (blue > 255 && green > 255 && red > 255) {

                                red += v;
                                green += v;
                                blue += v;
                                red >>= 1;
                                green >>= 1;
                                blue >>= 1;
                                continue;
                            }
                            break;
                        }
                    }
                    red += atp;
                    green += atp;
                    blue += atp + atp;
                    if (blue < 0) {
                        blue = (Math.abs(blue) >> 1) + stain.Blue();
                    }
                    if (blue > 255) {
                        blue = 255;
                    }
                    if (green < 0) {
                        green = (Math.abs(green) >> 1) + stain.Green();
                    }
                    if (green > 255) {
                        green = 255;
                    }
                    if (red < 0) {
                        red = (Math.abs(red) >> 1) + stain.Red();
                    }
                    if (red > 255) {
                        red = 255;

                    }
                    planet.surfaceMap.SetPixel(x, y, new Pigment(red, green, blue), true, false);
                } else {
                    int blue = 255 + (int) (h * metals / dl + bt + stain.Blue()) >> 1;
                    int green = (aminoAcid + (int) (h * organic / dl + gt + stain.Green())) >> 1;
                    int red = (int) (h * iron / dl + rt + stain.Red()) >> 1;

                    if (blue > 255 && green > 255 && red > 255) {
                        v = 128 + ((256 + (Math.abs(256 - red % 128 - green % 128 - blue % 128 - (((y * y) << 2) + (x >> 1)) % 128))) >> 2);
                        red += v;
                        green += v;
                        blue += v;
                        red >>= 1;
                        green >>= 1;
                        blue >>= 1;
                        while (true) {
                            if (blue > 255 && green > 255 && red > 255) {

                                red += v;
                                green += v;
                                blue += v;
                                red >>= 1;
                                green >>= 1;
                                blue >>= 1;
                                continue;

                            }
                            break;
                        }
                    }
                    red += atp;
                    green += atp;
                    blue += atp + atp;

                    if (blue < 1) {
                        blue = (Math.abs(blue) >> 1) + stain.Blue();
                    }
                    if (blue > 255) {
                        blue = 255;
                    }
                    if (green < 0) {
                        green = (Math.abs(green) >> 1) + stain.Green();
                    }
                    if (green > 255) {
                        green = 255;
                    }
                    if (red < 0) {
                        red = (Math.abs(red) >> 1) + stain.Red();
                    }
                    if (red > 255) {
                        red = 255;
                    }
                    planet.surfaceMap.SetPixel(x, y, new Pigment(255, red, green, blue), true, false);

                }
            }
        }
    }
}

class Planet extends SmallPlanet {

    //relative amounts
    //
    //core
    //atmosphere
    //fractal
    //map
    //
    //
    int numberOfMoons;

    public Planet(StarSystem sys, Molecula rnd) {
        super(sys, rnd);
        system = sys;
        this.size = 128;
        this.orbital = new Orbit();
        this.relative = new Relative();
        this.core = new Core();
        this.atmosphere = new Atmosphere();
        this.fractalSky = new Frac();
        this.fractalSurface = new Frac();

    }

    public Planet(StarSystem sys, int size, Molecula rnd) {
        super(sys, rnd);
        this.size = size;
        this.orbital = new Orbit();
        this.relative = new Relative();
        this.core = new Core();
        this.atmosphere = new Atmosphere();
        this.fractalSky = new Frac();
        this.fractalSurface = new Frac();
    }
}

class Environment {

    byte waste;
    byte chemicals;
    byte rads;
    byte preditors;
    byte prey;
    byte parasites;
    byte bacteria;
    byte viruses;
    float temperature;
    float wind;
    float precipitation;
    float lightning;

    Environment(Land on, Planet p, int x, int y) {
        waste = 0;
        chemicals = (byte) (2 + (p.atmosphere.ammonia + p.atmosphere.chlorine + p.atmosphere.methane + p.atmosphere.sulphur) / (p.atmosphere.co2 + p.atmosphere.oxygen + p.atmosphere.hydrogen + p.atmosphere.nitrogen));
        rads = (byte) (2 + p.core.heavyMetals / (p.core.calcium + p.core.iron + p.core.lightMetals));
        chemicals = (byte) (Random.Next((chemicals >> 1)) + (chemicals >> 1));
        rads = (byte) (Random.Next(rads >> 1) + (rads >> 1));
        this.prey = (byte) (((int) on.plants + (int) on.trees + (int) on.animals) / Math.PI);
        this.preditors = (byte) ((this.prey) / Math.PI);
        this.parasites = (byte) ((int) (this.preditors + (int) this.prey) / Math.PI);
        double h = p.planetMap.data[x][y].z;
        if (h < 0) {
            h = 1.0;
        } else {
            h += 1.0;
        }
        this.wind = (float) (p.atmospheres / h);
        this.temperature = (float) ((this.wind * p.relative.temperature) * (double) (p.planetMap.height) / (double) (1 + Math.abs(y - (p.planetMap.height >> 1))));
        this.lightning = this.wind * this.temperature;
        this.precipitation = on.water * this.lightning;
        this.bacteria = (byte) ((p.atmosphere.methane + p.atmosphere.sulphur + on.plants + on.trees + on.animals) >> 3);
        this.viruses = (byte) ((p.atmosphere.ammonia + p.atmosphere.chlorine + on.plants + on.trees + on.animals) >> 3);
    }
}

class Land extends LandElement implements java.io.Serializable {

    byte heavyMetals;
    byte lightMetals;
    byte minerals;
    byte hydrocarbans;
    byte water;
    byte animals;
    byte plants;
    byte trees;
    Environment environment;
    Pigment pigment;
    Color color;

    public Land(Planet planet, int x, int y) {
        int r = planet.surfaceMap.data[x][y].Red();
        int g = planet.surfaceMap.data[x][y].Green();
        int b = planet.surfaceMap.data[x][y].Blue();
        double h = planet.planetMap.data[x][y].z;
        if (h > 0) {
            this.water = (byte) 128;
        } else {
            this.water = (byte) (b >> 1);
        }
        int alge = Math.abs(g + b - r);
        int hm = ((((g + b) * planet.core.heavyMetals) / (planet.core.lightMetals + planet.core.oxygen + planet.core.iron + planet.core.calcium))) >> 1;
        int lm = ((((r + b) * planet.core.lightMetals) / (planet.core.heavyMetals + planet.core.oxygen + planet.core.iron + planet.core.calcium))) >> 1;
        int m = ((((r + g) * planet.core.calcium) / (planet.core.carbon + planet.core.helium + planet.core.hydrogen))) >> 1;
        int hc = (((alge * (planet.core.carbon + planet.core.hydrogen + planet.core.oxygen)) / (planet.core.calcium + planet.core.helium + planet.core.oxygen + planet.core.lightMetals + planet.core.heavyMetals))) >> 1;

        if (hm > 100) {
            hm = 100;
        }
        if (lm > 100) {
            lm = 100;
        }
        if (m > 100) {
            m = 100;
        }
        if (hc > 100) {
            hc = 100;
        }
        this.heavyMetals = (byte) hm;
        this.lightMetals = (byte) lm;
        this.minerals = (byte) m;
        this.hydrocarbans = (byte) hc;
        //
        int p = ((alge * this.water) / 256);
        int a = ((alge * p) / 256);
        int t = ((alge * a) / 256);
        a = ((a * planet.atmosphere.oxygen) / 256);
        p = ((p * planet.atmosphere.co2) / 256);
        t = ((t * planet.atmosphere.co2) / 256);
        if (a > 100) {
            a = 100;
        }
        if (p > 100) {
            p = 100;
        }
        if (t > 100) {
            t = 100;
        }
        if (a < 0) {
            a = 0;
        }
        if (p < 0) {
            p = 0;
        }
        if (t < 0) {
            t = 0;
        }
        this.animals = (byte) a;
        this.plants = (byte) p;
        this.trees = (byte) t;
        this.pigment = planet.surfaceMap.data[x][y];
        this.color = this.pigment.Colour();
        this.environment = new Environment(this, planet, x, y);

        this.atx = x;
        this.aty = y;
    }

    boolean RunSimulation() {
        return true;
    }

    void paint(Graphics2D g, int locx, int locy, float scale) {
        g.setColor(color);
        int w = (int) (LandMap.blockSizeInPixels * scale);
        int x = (int) (atx * w);
        int y = (int) (aty * w);
        g.fillRect(x + locx, y + locy, w, w);
    }

    @Override
    GameObjectType Type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DetectorResult Detect(ArrayList<MapElement> me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Collision(DetectorResult result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean CollisionSimulation(MapElement with) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void paint(Graphics2D g, int osx, int osy, int light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

abstract class LandElement extends MapElement implements java.io.Serializable {

    float atx;
    float aty;

    abstract boolean RunSimulation();

    abstract void paint(Graphics2D g, int locx, int locy, float scale);
}

class LandSection implements java.io.Serializable {

    LandElement contains;
    LandSection next;
    LandSection prev;

    boolean contain(LandElement me) {
        if (contains != null) {
            if (this.contains.equals(me)) {
                return true;
            }
            if (next != null) {
                return next.contain(me);
            }
        }
        return false;
    }

    boolean Remove(LandElement me) {

        //System.out.println("Removeing"+me.getClass());
        if (contains != null) {
            if (contains.equals(me)) {
                if (next != null) {
                    if (next.contains != null) {
                        if (this.prev != null) {
                            this.prev.next = next;
                        }
                        this.contains = null;
                        //System.out.println("Removed"+me.getClass());
                    } else {
                        contains = null;
                        this.next = null;
                    }
                    return true;
                }
            } else {
                return next.Remove(me);
            }
        }
        return false;

    }

    void Add(LandElement me) {

        //System.out.println("Adding"+me.getClass());
        if (contains != null) {
            if (!this.contains.equals(me)) {
                if (this.next != null) {
                    this.next.Add(me);
                } else {
                    next = new LandSection();
                    next.prev = this;
                    next.contains = me;
                }
            }

            //System.out.println("Added"+me.getClass());
        } else {
            this.contains = me;
        }
    }

    void CollisionTest(LandElement me, Local loc) {

    }
}

class LandMap implements java.io.Serializable {

    static int blockSizeInPixels = 32;
    static int blockCenterInPixels = 16;
    static int blockQuaterInPixels = 8;
    static int blockEighthInPixels = 4;
    static double blockScale = 1.0 / blockSizeInPixels;
    LandSection sections[][];
    int width;
    int height;

    public LandMap(int width, int height) {
        this.width = width;
        this.height = height;
        sections = new LandSection[width][height];
    }

    void RotateLeft() {

        for (int y = 0; y < height; y++) {

            LandSection start = sections[width - 1][y];
            for (int x = width - 2; x >= 0; x--) {
                sections[x][y].contains.atx++;
                LandSection next = sections[x][y].next;
                while (next != null) {
                    next.contains.atx++;
                    next = next.next;
                }
                sections[x + 1][y] = sections[x][y];
            }
            start.contains.atx = 0;
            LandSection next = start.next;
            while (next != null) {
                next.contains.atx -= width;
                next = next.next;
            }
            sections[0][y] = start;
        }
        //LandSection end=sections[width-1][y];
    }

    void RotateRight() {
        for (int y = 0; y < height; y++) {

            LandSection start = sections[0][y];
            for (int x = 0; x < width - 1; x++) {
                sections[x][y] = sections[x + 1][y];
                sections[x][y].contains.atx--;
                LandSection next = sections[x][y].next;
                while (next != null) {
                    next.contains.atx--;
                    next = next.next;
                }
            }
            start.contains.atx = width - 1;

            LandSection next = start.next;
            while (next != null) {
                next.contains.atx += width;
                next = next.next;
            }
            sections[width - 1][y] = start;
        }
    }

    void SetUpLandMap(Planet planet) {

        System.out.println("ATMOSPHERE:");
        System.out.println("o2:" + (float) (planet.atmosphere.oxygen) / 256);
        System.out.println("co2:" + (float) (planet.atmosphere.co2) / 256);
        System.out.println("n:" + (float) (planet.atmosphere.nitrogen) / 256);
        System.out.println("methane:" + (float) (planet.atmosphere.methane) / 256);
        System.out.println("ammonia:" + (float) (planet.atmosphere.ammonia) / 256);
        System.out.println("chlorine:" + (float) (planet.atmosphere.chlorine) / 256);
        System.out.println("sulphur:" + (float) (planet.atmosphere.sulphur) / 256);
        float hm = 0;
        float lm = 0;
        float m = 0;
        float hc = 0;
        //
        float a = 0;
        float p = 0;
        float t = 0;
        //
        float r = 0;
        float c = 0;
        //
        float pred = 0;
        float pray = 0;
        float para = 0;
        float bact = 0;
        float vir = 0;
        //
        float rain = 0;
        float wind = 0;
        float elec = 0;
        float temp = 0;
        float area = planet.planetMap.width * planet.planetMap.height;
        for (int x = 0; x < planet.planetMap.width; x++) {
            for (int y = 0; y < planet.planetMap.height; y++) {
                sections[x][y] = new LandSection();
                sections[x][y].contains = new Land(planet, x, y);
                hm += (float) (((Land) (sections[x][y].contains)).heavyMetals);
                lm += (float) (((Land) (sections[x][y].contains)).lightMetals);
                m += (float) (((Land) (sections[x][y].contains)).minerals);
                hc += (float) (((Land) (sections[x][y].contains)).hydrocarbans);
                //
                a += (float) (((Land) (sections[x][y].contains)).animals);
                p += (float) (((Land) (sections[x][y].contains)).plants);
                t += (float) (((Land) (sections[x][y].contains)).trees);
                //
                pred += (float) (((Land) (sections[x][y].contains)).environment.preditors);
                pray += (float) (((Land) (sections[x][y].contains)).environment.prey);
                para += (float) (((Land) (sections[x][y].contains)).environment.parasites);
                bact += (float) (((Land) (sections[x][y].contains)).environment.bacteria);
                vir += (float) (((Land) (sections[x][y].contains)).environment.viruses);
                c += (float) (((Land) (sections[x][y].contains)).environment.chemicals);
                r += (float) (((Land) (sections[x][y].contains)).environment.rads);
                //
                rain += (float) (((Land) (sections[x][y].contains)).environment.precipitation);
                wind += (float) (((Land) (sections[x][y].contains)).environment.wind);
                temp += (float) (((Land) (sections[x][y].contains)).environment.temperature);
                elec += (float) (((Land) (sections[x][y].contains)).environment.lightning);
            }
        }
        System.out.println("INORGANIC");
        System.out.println("heavy metals:" + hm + "\t=" + hm / (area));
        System.out.println("light metals:" + lm + "\t=" + lm / (area));
        System.out.println("minerals:" + m + "\t=" + m / (area));
        System.out.println("hydrocarbans:" + hc + "\t=" + hc / (area));
        //
        System.out.println("ORGANIC");
        System.out.println("animals:" + a + "\t=" + a / (area));
        System.out.println("plants:" + p + "\t=" + p / (area));
        System.out.println("trees:" + t + "\t=" + t / (area));
        System.out.println("biomass:" + (a + t + p) + "\t=" + (a + t + p) / (area));
        //
        System.out.println("ENVIRO");
        System.out.println("preditors:" + pred + "\t=" + pred / (area));
        System.out.println("prey:" + pray + "\t=" + pray / (area));
        System.out.println("parasites:" + para + "\t=" + para / (area));
        System.out.println("bacteria:" + bact + "\t=" + bact / (area));
        System.out.println("viruses:" + vir + "\t=" + vir / (area));
        System.out.println("rads:" + r + "\t=" + r / (area));
        System.out.println("chemicals:" + c + "\t=" + c / (area));
        System.out.println("WEATHER");
        System.out.println("rain:" + rain + "\t=" + rain / (area));
        System.out.println("wind:" + wind + "\t=" + wind / (area));
        System.out.println("charge:" + elec + "\t=" + elec / (area));
        System.out.println("temerature:" + temp + "\t=" + temp / (area));

    }
}

public class PlanetMap implements java.io.Serializable {

    final static double PID10 = 0.3141592653589793238462643383279;
    final static double PI = 3.1415926535897932384626433832795;
    final static double PID3 = 1.0471975511965977461542144610932;
    final static double PI90 = 1.5707963267948966192313216916398;
    final static double PI60 = 1.0471975511965977461542144610932;
    final static double PI45 = 0.78539816339744830961566084581988;
    final static double PI23 = 0.39269908169872415480783042290994;
    final static double InvPI = 0.31830988618379067153776752674503;
    final static double InvPI2 = 0.15915494309189533576888376337251;
    final static double InvPI3 = 0.10610329539459689051258917558168;
    final static double InvPI4 = 0.079577471545947667884441881686257;
    final static double InvPI8 = 0.039788735772973833942220940843129;
    final static double PI2 = 6.283185307179586476925286766559;
    final static double PI3 = 9.4247779607693797153879301498385;
    final static double PI4 = 12.566370614359172953850573533118;
    final static double PI8 = 25.132741228718345907701147066236;
    final static double SqrtPI = 1.7724538509055160272981674833411;
    final static double CubertPI = 1.4645918875615232630201425272638;
    final static double PISqrd = 9.8696044010893586188344909998762;
    final static double PICube = 31.006276680299820175476315067101;
    final static double TerraPI = 0.56418958354775628694807945156077;
    final static double GTerraPI = 5.5683279968317078452848179821188;
    final static double Golden = 0.61803398874989484820458683436564;
    final static double GoldenRatio = 1.61803398874989484820458683436564;
    final static double Third = 0.3333333333333333333333333333333;
    final static double FourThird = 1.3333333333333333333333333333333;
    final static double FourThirdPI = 4.1887902047863909846168578443727;
    final static double A45 = 0.70710678118654752440084436210485;
    final static double A45D10 = 0.070710678118654752440084436210485;
    final static double A45D100 = 0.0070710678118654752440084436210485;
    final static double A45D1000 = 0.00070710678118654752440084436210485;
    final static double A45D10000 = 0.00007071067811865475244008443621048;
    final static double Inv360 = 1.0 / 360.0;
    final static double Inv180 = 1.0 / 180.0;
    final static double Inv90 = 1.0 / 90.0;
    final static double Inv45 = 1.0 / 45.0;
    final static double PID360 = PI / 360.0;
    final static double PID180 = PI / 180.0;
    final static double PID90 = PI / 90.0;
    final static double PID45 = PI / 45.0;
    final static double D360PI = 360.0 / PI;
    final static double D180PI = 180.0 / PI;
    final static double D90PI = 90.0 / PI;
    final static double D45PI = 45.0 / PI;
    final static double Sqrt2 = 1.4142135623730950488016887242097;
    final static double Sqrt22 = 2.8284271247461900976033774484194;

    final static double Sin120 = 0.86602540378443864676372317075294;
    final static double Sin240 = -0.86602540378443864676372317075294;
    final static double Cos120 = -0.5;
    final static double Cos240 = -0.5;
    final static double C1 = 3e8;//m/s
    final static double C2 = 9e16;//m/s^2
    final static double AU = 1.496e11;
    final static double AU11 = 1.6456e12;//11 au
    final static double AU32 = 4.7572e12;//32 au
    final static double AU64 = 9.5744e12;//64 au
// space
//
//time
    final static float MilliSecond = 0.001f;
    final static float Minute = 60;
    final static double Hour = 3.6e3;//s
    final static double Day = 8.64e4;//s
    final static double Year = 3.156e7;//s
    final static double SolYear = 3.1104e7;//s
    final static double ArcYear = 1.119744e10;//s =360 years
    final static double GigaYear = 3.156e16;//s

    final static double SolMass = 1.989e30f;
    final static double JupitersToSols = 1.0 / 1053.442619;
    final static double Sols = 1053.442619;
    final static double Sol2 = 2 * 1053.442619;
    final static double SolRadiusInEarths = 109.0;
    final static double JupitersRadiusToEarths = 10.84218379;
    final static double JupitersToEarths = 1.0 / 317.8;
    final static double EarthsToJupiters = 317.8;
    final static double MoonsToEarths = 1.0 / 0.0123;
    final static double MoonsRadiusToEarths = 0.2728008025;
    final static double MassOfEarth = 5.9763e24;
    final static double SolMassInEarths = SolMass / MassOfEarth;
    final static double JupitersMass = MassOfEarth * EarthsToJupiters;
    final static double RadiusOfEarth = 6.370949e6;
    final static double RadiusOfJupiter = 7.14e7f;
    final static double RadiusOfSol = 6.855e8f;
    final static double uG = 6.672041e-11;
    float atx;
    float aty;
    LandMap map;
    int planetSize;
    Planet planet;

    //Star sun;
    public PlanetMap(int size, Molecula rnd) {
        this.planetSize = size;
        this.planet = new Planet(new StarSystem(rnd), rnd);
    }

    public static double RandomEarths() {
        return 1.0 + TerraPI * (Random.NextDouble() - Random.NextDouble()) * Golden;

    }

    protected void SetUpRandomTerraPlanet(double emass, double eradius, boolean anim) {
        //earths
        //planet.relative.mass=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;
        //planet.relative.radius=1.0+TerraPI*(Random.NextDouble()-Random.NextDouble())*Golden;
        planet.relative.mass = emass;
        planet.relative.radius = eradius;

        double r = planet.relative.radius * RadiusOfEarth;
        planet.gravity = (float) ((uG * planet.relative.mass * MassOfEarth) / (r * r));
        //planet.relative.mass=1.0;//TerraPI;
        //planet.relative.size=1.0;//TerraPI;
        planet.relative.density = planet.relative.mass / planet.relative.radius;
        planet.relative.energy = (FourThird + (Third * Random.NextDouble() - FourThird * Random.NextDouble())) * planet.relative.radius * planet.relative.mass;
        planet.relative.temperature = Math.sqrt(planet.relative.energy);
        //planet.relative.temperature=Third-Third*NextDouble(STANDARD_PROB);
        double hyp = Math.sqrt(this.planetSize * 256);
        int width = (int) (planet.relative.radius * hyp);
        if (anim) {
            width = 1440;
        }
        if (width > this.planetSize) {
            width = this.planetSize;
        } else if (width < this.planetSize >> 1) {
            width = this.planetSize >> 1;
        }
        int height = (int) (planet.relative.radius * hyp * 0.5);
        if (anim) {
            height = 900;
        }
        if (height > this.planetSize >> 1) {
            height = this.planetSize >> 1;
        } else if (height < this.planetSize >> 2) {
            height = this.planetSize >> 2;
        }
        //MUST BE EVEN
        if (width % 2 == 1) {
            width++;
        }
        if (height % 2 == 1) {
            height++;
        }
        //

        map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;

        //weather
        //planet.weatherMap = new HeightMap(width, height);
        //planet.weatherMap.dataScale = planet.planetMap.dataScale;
        //
        planet.core = Core.RandomStarCore();
        //
        double p = 0;
        p += planet.atmosphere.ammonia = Random.Next(128) + Random.Next(128);
        p += planet.atmosphere.nitrogen = 128 + Random.Next(128) - (planet.atmosphere.ammonia >> 1);
        p += planet.atmosphere.hydrogen = 128 + Random.Next(128) - (planet.atmosphere.ammonia >> 1);
        p += planet.atmosphere.co2 = 128 + Random.Next(128) - Random.Next(128);
        //
        p += planet.atmosphere.oxygen = 255 - ((planet.atmosphere.nitrogen + planet.atmosphere.ammonia + planet.atmosphere.co2 + planet.atmosphere.hydrogen) >> 2);//128+Next(128)-Next(128);
        p += planet.atmosphere.sulphur = 255 - ((planet.atmosphere.oxygen + planet.atmosphere.co2) >> 1);
        p += planet.atmosphere.methane = 255 - planet.atmosphere.oxygen % (1 + planet.atmosphere.hydrogen);
        p += planet.atmosphere.chlorine = 255 - ((planet.atmosphere.oxygen + planet.atmosphere.co2 + planet.atmosphere.nitrogen + planet.atmosphere.hydrogen) >> 2);
        //
        planet.atmospheres = (float) Math.sqrt(((p / planet.planetMap.width) * planet.relative.temperature * planet.relative.radius * planet.relative.mass));
        double w = planet.core.hydrogen + planet.core.oxygen + planet.atmosphere.hydrogen + planet.atmosphere.oxygen;
        if (planet.relative.temperature < 3) {
            planet.water = (float) ((w / (planet.planetMap.width + planet.planetMap.height)) * (3 - planet.relative.temperature) * planet.relative.density);
            //printf("WATER:%f",planet.water);
        }
        planet.water *= (planet.gravity / PISqrd);
        planet.atmospheres *= (planet.gravity / PISqrd);
        //
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.transMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
    }
}
