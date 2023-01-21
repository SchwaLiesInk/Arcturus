/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn Jones
 */
public class Telescope {

    RingList<SmallPlanet> starMap = new RingList();
    SphericalCoord direction;
    Point sphere;
    Point at;
    Tech tech;

    SmallPlanet home;
    Star homeStar;
    StarSystem homeSystem;
    Telescope(Point sphere, Tech tec) {
        at = new Point(0, 0, 0, sphere.r);
        direction = new SphericalCoord(sphere.AsVector());
        //System.Console.WriteLine("x" + direction.At().x + " y" + direction.At().y + " z" + direction.At().z);
        Point survay = direction.At();
        if (survay.x == (Double.NaN) || survay.y == (Double.NaN) || survay.z == (Double.NaN)) {
            survay = new Point(0, 0, 0);
        }
        this.sphere = at.Add(at, survay).AsPoint(sphere.r);
        tech = tec;

    }

    void MapZone(Planet home, Core wcore, Atmosphere watmos) {
        starMap.Clear();
        Point to = sphere;
        System.out.println("________________________________");
        System.out.println("x" + to.x + " y" + to.y + " z" + to.z);
        Molecula zone = new Molecula(1 + (int) Math.abs((to.x - to.r)), (int) Math.abs(to.y - to.r), (int) Math.abs(to.z - to.r));
        System.out.println("Telescope Scan " + to.r);
        int p = 0;
        double r = Linear.MAX_MAP - (double) (Math.sqrt(to.x * to.x + to.z * to.z + 2));
        double d = 2 * (r / (1 + Math.abs(to.y)) + r * (double) Math.sin(to.x / Linear.MAX_MAP * Linear.PI2) + r * (double) Math.cos(to.z / Linear.MAX_MAP * Linear.PI2)) / Linear.MAX_MAP;
        double d2 = (double) Math.sqrt(d * d * 3);
        System.out.println("d" + d + " r" + r);

        for (int serv = 1; serv <= to.r; serv++) {
            double ix = to.r * (zone.NextDouble() - zone.NextDouble()) * 5;
            double iy = to.r * (zone.NextDouble() - zone.NextDouble()) * 5;
            double iz = to.r * (zone.NextDouble() - zone.NextDouble()) * 5;
            double ir = 3.0 / d2;
            double density1 = (double) (d * zone.NextDouble());
            double density2 = (double) (d * zone.NextDouble());
            double density3 = (double) (d * zone.NextDouble());

            double i = (double) (zone.NextDouble() - zone.NextDouble()) / density1;
            double j = (double) (zone.NextDouble() - zone.NextDouble()) / density2;
            double k = (double) (zone.NextDouble() - zone.NextDouble()) / density3;

            //System.out.println("i" + i + " j" + j + " k" + k);
            do {
                ix += to.r * (zone.NextDouble() - zone.NextDouble());
                iy += to.r * (zone.NextDouble() - zone.NextDouble());
                iz += to.r * (zone.NextDouble() - zone.NextDouble());
                double dr = (double) (Math.abs(i) + Math.abs(j) + Math.abs(k) + d2 * Math.PI) * ir;

                if ((zone.NextDouble() + zone.NextDouble()) * Math.PI < dr && Math.sqrt(ix * ix + iy * iy + iz * iz) > 5) {
                    p++;
                    StarSystem system = new StarSystem(zone);
                    //Star star = new Star(system,zone);
                    Planet plan = new Planet(system, zone);
                    //plan.system=system;
                    Point starCoord = new Point(to.x + ix + i, to.y + iy + j, to.z + iz + k);
                    Point relativeCoord = new Point(i + ix, j + iy, k + iz);
                    Point focusCoord = new Point(to.x + ix, to.y + iy, to.z + iz);
                    //plan.revolvesRound = star;
                    //System.Console.WriteLine("Planet " + p + " x:" + plan.system.starCoord.x + " y:" + plan.system.starCoord.y + " z:" + plan.system.starCoord.z);
                    double mass = 1.5*zone.NextDouble() + PlanetMap.TerraPI * (zone.NextDouble() - zone.NextDouble()) * PlanetMap.Golden;
                    double radius = 1.5*zone.NextDouble() + PlanetMap.TerraPI * (zone.NextDouble() - zone.NextDouble()) * PlanetMap.Golden;
                    SmallPlanet.SetUpRandomTerraPlanet(plan, mass, radius, zone, new Core(wcore, zone), Atmosphere.Add(watmos, Atmosphere.RandomStarAtmosphere(zone)));
                    ScanPlanetStar(plan, starCoord, zone);
                    plan.revolvesRound.relativeCoord = relativeCoord;
                    plan.revolvesRound.focusCoord = focusCoord;
                    //
                    StellarNames.SetSystemName(plan, plan.revolvesRound);
                    system.FillPlanetarySystem(plan.revolvesRound, zone);
                    starMap.Append(plan);
                    zone = new Molecula(1 + (int) Math.abs(to.x + ix), (int) Math.abs(to.y + iy), (int) Math.abs(to.z + iz));
                    density1 = (double) (d * zone.NextDouble());
                    density2 = (double) (d * zone.NextDouble());
                    density3 = (double) (d * zone.NextDouble());
                    break;
                }
                i = (double) (zone.NextDouble() - zone.NextDouble()) / density1;
                j = (double) (zone.NextDouble() - zone.NextDouble()) / density2;
                k = (double) (zone.NextDouble() - zone.NextDouble()) / density3;
                //System.Console.WriteLine("i" + i + " j" + j + " k" + k);
            /*ix++;
                 if (ix > to.r) {
                 ix = -to.r;
                 iy++;
                 if (iy > to.r) {
                 iy = -to.r;
                 iz++;
                 }
                 }*/
                iz++;
            } while (iz < to.r);
        }
        System.out.println("________________________________");
        System.out.println("Telescope Worlds " + starMap.Length());
        for (Node<SmallPlanet> s = starMap.Start(); s.data != null; s = s.next) {
            System.out.println(s.data.revolvesRound.name + " World " + s.data.name + " Other Planets " + s.data.system.bodies.size());
        }
        System.out.println("________________________________");
    }

    static void ScanPlanetStar(SmallPlanet plan, Point coord, Molecula zone) {
        plan.revolvesRound = new Star(plan, zone);
        while (!plan.revolvesRound.Liveable()) {
            plan.revolvesRound = new Star(plan, zone);
        }
        plan.revolvesRound.orbital.radius = 0;//relative center
        plan.revolvesRound.SortStars();//by mass
        plan.revolvesRound.PlaceInSystem(plan, true);
        plan.revolvesRound.starCoord = coord;
        plan.revolvesRound.prime = (Planet) plan;
        int dis = (int) plan.revolvesRound.starCoord.Length();
        plan.name = StellarNames.PlanetName(plan.revolvesRound.classification, dis, plan.orbital.radius,-1);
        System.out.println("Planet " + plan.name + " At " + plan.orbital.radius + " AUs " + dis + " Light Years Away");
        plan.system.color = new Pigment(plan.revolvesRound.color);
    }

    static void SetPlanetStar(SmallPlanet plan, String name, double mass, double radius, double temperature, Molecula zone) {
        plan.revolvesRound = new Star(plan, mass, radius, temperature, zone);
        while (!plan.revolvesRound.Liveable()) {
            plan.revolvesRound = new Star(plan, zone);
        }
        plan.revolvesRound.orbital.radius = 0;//relative center
        plan.revolvesRound.SortStars();//by mass
        plan.revolvesRound.PlaceInSystem(plan, true);
        
        plan.name = name;// SmallPlanet.PlanetName(plan.revolvesRound.classification, plan.orbital.radius, zone);
        System.out.println("Planet " + plan.name + " At " + plan.orbital.radius + " AUs ");
        plan.system.color = new Pigment(plan.revolvesRound.color);
    }

}
