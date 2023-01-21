/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Gerwyn Jones
 */
abstract class ScopeScreen extends Screen {

    Telescope scope;
    static void StoreGrid(Screen scr, Vector offset, RingList<Segment> grid, Pigment color, Quarternion rot, double r, DrawType subType, int index) {
        Segment s = grid.First();
        while (s != null) {
            scr.LoadVertexLine(offset, s.p1.AsPoint(r), s.p2.AsPoint(r), color, rot, r, 0.1f, scr.renderList, DrawType.Line, subType, index);
            s = grid.Next();
        };
    }

    static PipeLineLoader StreamGrid(Screen scr, Vector offset, RingList<Segment> grid, Pigment color, Quarternion rot, double r, DrawType subType, int index) {
        Segment s = grid.First();
        PipeLineLoader pill = new PipeLineLoader(scr, offset, color, rot, r, 0.1f, DrawType.Line, subType, index);
        while (s != null) {
            pill.Append(s.p1.AsPoint(r));
            pill.Append(s.p2.AsPoint(r));
            s = grid.Next();
        };
        pill.points.Append(pill);
        return pill;
    }

    static void StreamGrid(Screen scr, PipeLineLoader pill, double r, RingList<Segment> grid) {
        Segment s = grid.First();
        RingList<Point> p = new RingList<>();
        while (s != null) {
            p.Append(s.p1.AsPoint(r));
            p.Append(s.p2.AsPoint(r));
            s = grid.Next();
        };
        pill.PasteAll(p);
        pill.points.PasteAll(p);
    }

    static void StoreGrid(Screen scr, Vector offset, RingList<Segment> grid, double scale, Pigment color, Quarternion rot, double r, DrawType subType, int index) {

        Segment s = grid.First();
        while (s != null) {
            Point sp1 = s.p1.AsPoint(r);
            Point sp2 = s.p2.AsPoint(r);
            sp1.Scale(scale);
            sp2.Scale(scale);
            scr.LoadVertexLine(offset, sp1, sp2, color, rot, r, 0.1f, scr.renderList, DrawType.Line, subType, index);
            s = grid.Next();
        };
    }

    static void CreateGrid(RingList<Segment> grid, double r, double ratio) {
        double sd = r * ratio * 2;
        double sr = r;
        double sre = sr + Linear.Epsilon;
        for (double j = -sr; j <= sre; j += sd) {
            grid.Append(new Segment(new Vector(-sr, j, sre, 1), new Vector(sre, j, sre, 1)));
            grid.Append(new Segment(new Vector(j, -sr, sre, 1), new Vector(j, sre, sre, 1)));
            //
            grid.Append(new Segment(new Vector(-sr, sre, j, 1), new Vector(sre, sre, j, 1)));
            grid.Append(new Segment(new Vector(j, sre, -sr, 1), new Vector(j, sre, sre, 1)));
            //
            grid.Append(new Segment(new Vector(j, -sr, sre, 1), new Vector(j, -sr, -sr, 1)));
            grid.Append(new Segment(new Vector(-sr, -sr, j, 1), new Vector(sre, -sr, j, 1)));
            //
            grid.Append(new Segment(new Vector(-sr, -sr, j, 1), new Vector(-sr, sre, j, 1)));
            grid.Append(new Segment(new Vector(-sr, j, -sr, 1), new Vector(-sr, j, sre, 1)));
            //
            grid.Append(new Segment(new Vector(sre, j, -sr, 1), new Vector(-sr, j, -sr, 1)));
            grid.Append(new Segment(new Vector(j, -sr, -sr, 1), new Vector(j, sre, -sr, 1)));
            //
            grid.Append(new Segment(new Vector(sre, -sr, j, 1), new Vector(sre, sre, j, 1)));
            grid.Append(new Segment(new Vector(sre, j, -sr, 1), new Vector(sre, j, sre, 1)));
        }
    }

    ScopeScreen(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);
    }

    @Override
    public void paintAll(Graphics2D g, double scale) {
        paint(g, scale);
    }

    @Override
    public void paint(Graphics2D g, double scale) {
        g.setColor(Color.black);
        g.fillRect(0, 0, frame.GetWidth(), frame.GetHeight());
        /*this.bg=buffer.createGraphics();
         bg.drawLine(mouseX, mouseY, mouseX, mouseY);
         g.drawImage(buffer,0,0,null);        
         bg.dispose();*/
        //g.setFont(frame.getFont());
        g.setColor(Color.yellow);
        g.drawString("X" + this.mouseX + " Y" + this.mouseY, (float) (origin.x), 1.0f + (float) (origin.y) + 16);
        g.drawString("FromX" + this.origin.x + " FromY" + this.origin.y, (float) (origin.x), 1.0f + (float) (origin.y) + 32);
        g.drawString("ToX" + (int) (this.origin.x + this.size.x) + " ToY" + (int) (this.origin.y + this.size.y), (float) (origin.x), 1.0f + (float) (origin.y) + 48);

    }

    @Override
    public void Choice(MenuChoice c) {
    }

}

enum TelescopeState {

    StarMap, SystemMap, OrbitMap, ProbeMap;
}

public class TelescopeScreen extends ScopeScreen {

    RenderBuffer buffer;
    Point homeCoord = new Point();
    TelescopeState state;
    SmallPlanet selectedSystem;
    SmallPlanet selectedPlanet;
    SmallPlanet hoveredSystem;
    SmallPlanet hoveredPlanet;
    StarMap starSystems;
    Local mat = new Local();
    boolean dragging = false;
    StringBuilder starText = new StringBuilder();
    String starString1 = new String();
    String starString2 = new String();
    String starString3 = new String();
    Color starColor = new Color(255, 255, 255);
    //
    boolean hovering = false;
    StringBuilder planetText = new StringBuilder();
    String planetString1 = new String();
    String planetString2 = new String();
    String planetString3 = new String();
    String planetString4 = new String();
    Color planetColor = new Color(255, 255, 255);

    // <editor-fold defaultstate="collapsed" desc="Probes">
    public static PlanetMap Probe(SmallPlanet planet, Molecula rnd) {
        int width = planet.size;
        int height = planet.size >> 1;
        PlanetMap pm = new PlanetMap(planet.size, rnd);
        pm.map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (Linear.PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;
        //planet.weatherMap = new HeightMap(width, height);
        //planet.weatherMap.dataScale = planet.planetMap.dataScale;
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);

        return pm;
    }

    public static PlanetMap Probe(LargePlanet planet, Molecula rnd) {

        int width = planet.size;
        int height = planet.size >> 1;
        PlanetMap pm = new PlanetMap(planet.size, rnd);
        pm.map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (Linear.PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;
        //planet.weatherMap = new HeightMap(width, height);
        //planet.weatherMap.dataScale = planet.planetMap.dataScale;
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap1 = new ColorMap(planet.planetMap.width >> 2, planet.planetMap.height >> 2);
        return pm;
    }

    public static PlanetMap Probe(HugePlanet planet, Molecula rnd) {

        int width = planet.size;
        int height = planet.size >> 1;
        PlanetMap pm = new PlanetMap(planet.size, rnd);
        pm.map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (Linear.PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;
        //planet.weatherMap = new HeightMap(width, height);
        //planet.weatherMap.dataScale = planet.planetMap.dataScale;
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap1 = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap2 = new ColorMap(planet.planetMap.width >> 2, planet.planetMap.height >> 2);
        return pm;
    }

    public static PlanetMap Probe(GiantPlanet planet, Molecula rnd) {

        int width = planet.size;
        int height = planet.size >> 1;
        PlanetMap pm = new PlanetMap(planet.size, rnd);
        pm.map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (Linear.PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;
        //planet.weatherMap = new HeightMap(width, height);
        // planet.weatherMap.dataScale = planet.planetMap.dataScale;
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap1 = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap2 = new ColorMap(planet.planetMap.width >> 2, planet.planetMap.height >> 2);
        //planet.transMap3 = new ColorMap(planet.planetMap.width >> 3, planet.planetMap.height >> 3);
        return pm;
    }

    public static PlanetMap Probe(SuperPlanet planet, Molecula rnd) {

        int width = planet.size;
        int height = planet.size >> 1;
        PlanetMap pm = new PlanetMap(planet.size, rnd);
        pm.map = new LandMap(width, height);
        planet.planetMap = new HeightMap(width, height);
        planet.planetMap.dataScale = (float) (Linear.PI2 * planet.relative.radius * 6e3) / planet.planetMap.width;
        //planet.weatherMap = new HeightMap(width, height);
        //planet.weatherMap.dataScale = planet.planetMap.dataScale;
        planet.surfaceMap = new ColorMap(planet.planetMap.width, planet.planetMap.height);
        //planet.atmosphereMap = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap1 = new ColorMap(planet.planetMap.width >> 1, planet.planetMap.height >> 1);
        //planet.transMap2 = new ColorMap(planet.planetMap.width >> 2, planet.planetMap.height >> 2);
        //planet.transMap3 = new ColorMap(planet.planetMap.width >> 3, planet.planetMap.height >> 3);
        //planet.transMap4 = new ColorMap(planet.planetMap.width >> 4, planet.planetMap.height >> 4);
        return pm;
    }

// </editor-fold>
    TelescopeScreen(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);
        buffer = new RenderBuffer(this);
        cam = new Camera(0, 0, -1, Viewer.External, this);
        cam.zoom = 1;
        starSystems = new StarMap(this);
        buffer.render = this::Render;
        state = TelescopeState.StarMap;
        SetUpTelescope();
    }

    void SetUpTelescope() {
        state = TelescopeState.StarMap;
        Tech tec = new Tech(2);
        Molecula rnd = new Molecula(1, 1, 1);
        StarSystem sol = new StarSystem(rnd);

        Planet earth = new Planet(sol, rnd);
        earth.system = sol;
        Core ecore = new Core();
        ecore.hydrogen = 192;
        ecore.oxygen = 200;
        ecore.lightMetals = 128;
        ecore.heavyMetals = 192;
        ecore.iron = 224;
        ecore.helium = 100;
        ecore.carbon = 224;
        ecore.calcium = 192;
        //
        Atmosphere eatmos = new Atmosphere();
        eatmos.hydrogen = 128;
        eatmos.methane = 100;
        eatmos.co2 = 100;
        eatmos.chlorine = 32;
        eatmos.ammonia = 64;
        eatmos.oxygen = 96;
        eatmos.nitrogen = 192;
        eatmos.sulphur = 96;

        Telescope.SetPlanetStar(earth, "Earth " + tec.Level() * 2000 + " ad ", 0.35f, 0.35f, 0.35f, rnd);
        Star.SetUpRandomTerraPlanet(earth, 1, 1, rnd, ecore, eatmos);
        System.out.println("Earth Map Size " + earth.size);
        SmallPlanet.SetUpRandomTerraSystem(earth, rnd);

        Point at = homeCoord.AsPoint(1);
        at.r = (int) (Math.PI + 4 * tec.Level());
        //
        //
        this.scope = new Telescope(at, tec);
        earth.revolvesRound.starCoord = homeCoord.Sub(homeCoord, scope.sphere).AsPoint(1);
        earth.revolvesRound.relativeCoord = homeCoord.Sub(homeCoord, scope.sphere).AsPoint(1);
        earth.revolvesRound.focusCoord = homeCoord.Sub(homeCoord, scope.sphere).AsPoint(1);
        scope.MapZone(earth, Core.Add(new Core(earth.core, rnd), Core.RandomStarCore(rnd)),
                Atmosphere.Add(new Atmosphere(earth.atmosphere, rnd, earth), Atmosphere.RandomStarAtmosphere(rnd)));
        this.scope.starMap.Append(earth);
        earth.system.FromPlanetarySystem(earth.revolvesRound, rnd);
        earth.SetUpTransMap();
        scope.home = earth;
    }

    public void Render() {

        if (buffer.Ready()) {
            buffer.Begin();
            switch (state) {
                case StarMap: {
                    starSystems.Render();

                    break;
                }
            }
            buffer.End();
        }
    }

    void PaintStarText(Graphics2D g, int x, int y) {
        if (starText != null) {
            g.setColor(Color.black);
            g.fillRect(x, y - 64, 768, 64);
            g.setColor(starColor);
            g.drawRect(x, y - 64, 768, 64);
            g.setColor(starColor);
            g.drawString(starString1, x, y - 48);
            g.setColor(starColor);
            g.drawString(starString2, x, y - 32);
            g.setColor(starColor);
            g.drawString(starString3, x, y - 16);
        }
    }

    void PaintPlanetText(Graphics2D g, int x, int y) {
        if (planetText != null) {
            g.setColor(Color.black);
            g.fillRect(x, y - 80, 768, 80);
            g.setColor(planetColor);
            g.drawRect(x, y - 80, 768, 80);
            g.setColor(planetColor);
            g.drawString(planetString1, x, y - 64);
            g.setColor(planetColor);
            g.drawString(planetString2, x, y - 48);
            g.setColor(planetColor);
            g.drawString(planetString3, x, y - 32);
            g.setColor(planetColor);
            g.drawString(planetString4, x, y - 16);
        }
    }

    @Override
    public void paint(Graphics2D g, double scale) {
        g.setColor(Color.black);
        g.fillRect(0, 0, frame.GetWidth(), frame.GetHeight());
        /*this.bg=buffer.createGraphics();
         bg.drawLine(mouseX, mouseY, mouseX, mouseY);
         g.drawImage(buffer,0,0,null);        
         bg.dispose();*/
        //g.setFont(frame.getFont());
        //g.setColor(Color.yellow);
        //g.drawString("X" + this.mouseX + " Y" + this.mouseY, (float) (origin.x), 1.0f + (float) (origin.y) + 16);
        //g.drawString("FromX" + this.origin.x + " FromY" + this.origin.y, (float) (origin.x), 1.0f + (float) (origin.y) + 32);
        //g.drawString("ToX" + (int) (this.origin.x + this.size.x) + " ToY" + (int) (this.origin.y + this.size.y), (float) (origin.x), 1.0f + (float) (origin.y) + 48);
        //DrawSortedList(this.sortedBufferList, frame.Graphic());
        switch (state) {
            case StarMap: {
                buffer.Draw3D();
                this.starSystems.Paint(g);
                PaintStarText(g, mouseX, mouseY);
                if (hoveredSystem != null) {
                    if (this.hoveredSystem.transMap != null) {
                        g.drawImage(hoveredSystem.transMap.bits, mouseX, mouseY, null);
                    } else {
                        this.hoveredSystem.SetUpTransMap();
                    }
                }
                break;
            }
            case SystemMap: {
                //SmallPlanet p=scope.starMap.First();
                if (this.selectedSystem != null) {
                    this.selectedSystem.system.Draw(this.frame.GetWidth(), this.frame.GetHeight(), g);
                    PaintStarText(g, 32, 64);
                    PaintPlanetText(g, mouseX, mouseY);
                }
                if (this.hoveredPlanet != null) {
                    if (this.hoveredPlanet.transMap != null) {
                        g.drawImage(hoveredPlanet.transMap.bits, mouseX, mouseY, null);

                    } else {
                        this.hoveredPlanet.SetUpTransMap();
                    }
                }
                break;
            }
            case OrbitMap: {
                //SmallPlanet p=scope.starMap.First();
                if (this.selectedPlanet != null) {
                    this.selectedPlanet.Draw(this.frame.GetWidth(), this.frame.GetHeight(), g);
                }
                break;
            }
            case ProbeMap: {
                break;
            }
        }
        g.drawImage(scope.home.transMap.bits, 0, 100, null);
        g.drawString("Location " + scope.home.name + " System " + scope.home.revolvesRound.name + " " + scope.home.system.name, 0, 128 + scope.home.transMap.Height());
        if (this.selectedSystem != null) {
            if (this.selectedSystem.transMap != null) {
                g.drawImage(selectedSystem.transMap.bits, 768 - selectedSystem.transMap.Width(), 0, null);

            } else {
                this.selectedSystem.SetUpTransMap();
            }
        }
        //this.renderList.Clear();
    }

    @Override
    public void Update(GameTime time) {

        for (Integer k : frame.KeyBoard().pressed) {
            switch (k) {
                case KeyEvent.VK_W: {
                    cam.lookAt.y -= time.cameraTime;
                    break;
                }
                case KeyEvent.VK_S: {
                    cam.lookAt.y += time.cameraTime;
                    break;
                }
                case KeyEvent.VK_A: {
                    cam.lookAt.x += time.cameraTime;
                    break;
                }
                case KeyEvent.VK_D: {
                    cam.lookAt.x -= time.cameraTime;
                    break;
                }
                case KeyEvent.VK_EQUALS: {
                    this.cam.zoom += 0.001f * (8 + this.cam.zoom * 0.5f) * time.cameraTime;
                    if (this.cam.zoom > 32) {
                        this.cam.zoom = 32;
                    }
                    break;
                }
                case KeyEvent.VK_MINUS: {
                    this.cam.zoom -= 0.001f * (8 + this.cam.zoom * 0.5f) * time.cameraTime;
                    if (this.cam.zoom < 0.1) {
                        this.cam.zoom = 0.1;
                    }
                    break;
                }
                case KeyEvent.VK_UP: {

                    cam.angle.y += 0.01 * time.cameraTime;
                    if (cam.angle.y > Linear.PID2) {
                        cam.angle.y = Linear.PID2;
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    cam.angle.y -= 0.01 * time.cameraTime;
                    if (cam.angle.y < -Linear.PID2) {
                        cam.angle.y = -Linear.PID2;
                    }
                    break;
                }
                case KeyEvent.VK_LEFT: {

                    cam.angle.x -= 0.01 * time.cameraTime;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    cam.angle.x += 0.01 * time.cameraTime;
                    break;
                }
            }

        }
        cam.linear = new Matrix();
        cam.linear = cam.linear.Mult(cam.linear.RotateY(cam.angle.x), cam.linear.RotateX(cam.angle.y));
        this.UpdateCamera((float) time.cameraTime, cam.lookAt.Unity().AsVector(), new Normal(0, 1, 0));
        switch (state) {
            case StarMap: {
                buffer.active();
                SetStarText(hoveredSystem);

                break;
            }
            case SystemMap: {
                SetStarText(selectedSystem);
                //SmallPlanet p=scope.starMap.First();
                if (this.selectedSystem != null) {
                    if (selectedSystem != null) {
                        selectedSystem.system.Update((float) (time.cameraTime), (float) this.cam.zoom);
                    }
                }
                SmallPlanet hover = this.hoveredPlanet;
                for (StellarBody sb : this.selectedSystem.system.bodies) {
                    if (sb instanceof SmallPlanet) {
                        SmallPlanet p = (SmallPlanet) (sb);
                        Point mp = new Point(mouseX, mouseY, 0, 1);
                        if (p.zone.Collides2D(mp, 16)) {
                            this.hoveredPlanet = p;

                        }

                    }
                }
                if (hoveredPlanet != null && hover != this.hoveredPlanet) {
                    Game.exec.execute(this::PlanetTransit);
                }
                this.SetPlanetText(hoveredPlanet);

                break;
            }
            case OrbitMap: {
                //SmallPlanet p=scope.starMap.First();
                if (this.selectedPlanet != null) {
                    if (selectedPlanet != null) {
                        selectedSystem.Update((float) (time.cameraTime), (float) this.cam.zoom);
                    }
                }
                break;
            }
            case ProbeMap: {
                break;
            }
        }

    }

    void PlanetTransit() {
        if (hoveredPlanet != null) {
            hovering = true;
            if (hoveredPlanet.transMap == null) {
                try {
                    hoveredPlanet.SetUpTransMap();
                } catch (Exception ex) {
                    //exception
                }
            }
            hovering = false;
        }
    }

    void SystemTransit() {
        if (hoveredSystem != null) {
            hovering = true;
            if (hoveredSystem.transMap == null) {
                try {
                    hoveredSystem.SetUpTransMap();
                } catch (Exception ex) {
                    //exception
                }
            }
            hovering = false;
        }
    }

    void SetPlanetText(SmallPlanet selected) {
        if (selected != null) {
            planetText = new StringBuilder();
            planetText.append(selected.name);
            planetText.append(" Orbits ");
            planetText.append((float) ((int) (selected.orbital.radius * 100)) * 0.01f);
            planetText.append(" AU's Temperature ");
            planetText.append((float) ((int) ((-273.0 + 360.0 * selected.relative.temperature) * 100)) * 0.01f);
            planetText.append(" c ");
            //
            planetString1 = planetText.toString();
            planetText = new StringBuilder();
            planetText.append(" Mass ");
            planetText.append((float) ((int) (selected.relative.mass * 10)) * 0.1f);
            planetText.append(" Radius ");
            planetText.append((float) ((int) (selected.relative.radius * 10)) * 0.1f);
            planetText.append(" Atmosphere ");
            planetText.append((float) ((int) (selected.atmospheres * 10)) * 0.1f);
            planetText.append(" Hydrosphere ");
            planetText.append((float) ((int) (selected.water * 10)) * 0.01f);
            planetText.append(" Gravity ");
            planetText.append((float) ((int) (selected.gravity / 9.807 * 100)) * 0.01f);
            planetText.append(" Day Length ");
            planetText.append((float) ((int) (selected.day * 100)) * 0.01f);
            planetString2 = planetText.toString();
            planetText = new StringBuilder();
            planetText.append(" Core Hydrocarbons ");
            planetText.append((float) ((int) ((selected.core.hydrogen + selected.core.carbon) * selected.relative.mass * 0.5) * 100) * 0.004f);
            planetText.append(" Metals ");
            planetText.append((float) ((int) ((selected.core.heavyMetals + selected.core.lightMetals) * selected.relative.mass * 0.5) * 100) * 0.004f);
            planetText.append(" Fertilizer ");
            planetText.append((float) ((int) ((selected.core.calcium + selected.core.oxygen) * selected.relative.mass * 0.5) * 100) * 0.004f);
            planetText.append(" Atomic Fuel ");
            planetText.append((float) ((int) ((selected.core.iron + selected.core.helium) * selected.relative.mass * 0.5) * 100) * 0.004f);
            planetString3 = planetText.toString();
            planetText = new StringBuilder();
            planetText.append(" Atmosphere Fertilizer ");
            planetText.append((float) ((int) ((selected.atmosphere.co2 + selected.atmosphere.nitrogen) * selected.atmospheres * 0.5) * 100) * 0.004f);
            planetText.append(" Air ");
            planetText.append((float) ((int) ((selected.atmosphere.oxygen + selected.atmosphere.hydrogen) * selected.atmospheres * 0.5) * 100) * 0.004f);
            planetText.append(" Poisons ");
            planetText.append((float) ((int) ((selected.atmosphere.ammonia + selected.atmosphere.methane + selected.atmosphere.chlorine + selected.atmosphere.sulphur) * selected.atmospheres * 0.25) * 100) * 0.004f);
            planetString4 = planetText.toString();
            planetColor = selected.color.Color();
        } else if (planetText != null) {
            planetText = null;
            planetString1 = "";
            planetString2 = "";
            planetString3 = "";
            planetString4 = "";
        }
    }

    void SetStarText(SmallPlanet selected) {

        if (selected != null) {
            starText = new StringBuilder();
            starText.append(" Star ");
            starText.append(selected.revolvesRound.name);
            starText.append(" System (");
            starText.append(selected.system.name);
            starText.append(") Distance ");
            starText.append((float) ((int) (selected.system.relativeCoord.Length() * 100)) * 0.01f);
            starText.append(" light years away");

            starString1 = starText.toString();
            starText = new StringBuilder();
            starText.append(selected.name);
            starText.append(" Orbits ");
            starText.append((float) ((int) (selected.orbital.radius * 100)) * 0.01f);
            starText.append(" AU's Temperature ");
            starText.append((float) ((int) ((-273.0 + 360.0 * selected.relative.temperature) * 100)) * 0.01f);
            starText.append(" c ");
            starString2 = starText.toString();
            starText = new StringBuilder();
            starText.append(" Mass ");
            starText.append((float) ((int) (selected.relative.mass * 10)) * 0.1f);
            starText.append(" Radius ");
            starText.append((float) ((int) (selected.relative.radius * 10)) * 0.1f);
            starText.append(" Atmosphere ");
            starText.append((float) ((int) (selected.atmospheres * 10)) * 0.1f);
            starText.append(" Hydrosphere ");
            starText.append((float) ((int) (selected.water * 10)) * 0.01f);
            starText.append(" Gravity ");
            starText.append((float) ((int) (selected.gravity / 9.807 * 100)) * 0.01f);
            starText.append(" Day Length ");
            starText.append((float) ((int) (selected.day * 100)) * 0.01f);
            starString3 = starText.toString();
            starColor = selected.revolvesRound.color.Color();
        } else if (starText != null) {
            starText = null;
            starString1 = "";
            starString2 = "";
            starString3 = "";
        }
    }

    @Override
    public void mouseWheeled(MouseWheelEvent me) {
        super.mouseWheeled(me); //To change body of generated methods, choose Tools | Templates.

        if (me.getWheelRotation() < 0) {
            this.cam.zoom += 0.01f * (8 + this.cam.zoom * 0.5f) * frame.GetGame().time.cameraTime;
            if (this.cam.zoom > 32) {
                this.cam.zoom = 32;
            }
        } else if (me.getWheelRotation() > 0) {
            this.cam.zoom -= 0.01f * (8 + this.cam.zoom * 0.5f) * frame.GetGame().time.cameraTime;
            if (this.cam.zoom < 0.1) {
                this.cam.zoom = 0.1;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        super.mouseDragged(me); //To change body of generated methods, choose Tools | Templates.
        if (me.getY() < mat.y) {

            cam.angle.y += 0.01 * frame.GetGame().time.cameraTime;
            if (cam.angle.y > Linear.PID2) {
                cam.angle.y = Linear.PID2;
            }
        } else if (me.getY() > mat.y) {
            cam.angle.y -= 0.01 * frame.GetGame().time.cameraTime;
            if (cam.angle.y < -Linear.PID2) {
                cam.angle.y = -Linear.PID2;
            }
        }
        if (me.getX() < mat.x) {

            cam.angle.x -= 0.01 * frame.GetGame().time.cameraTime;
        } else if (me.getX() > mat.x) {
            cam.angle.x += 0.01 * frame.GetGame().time.cameraTime;
        }
        mat = new Local(me.getX(), me.getY());
        this.starSystems.pressed = false;
        this.starSystems.release = true;
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        super.mouseAction(m, i); //To change body of generated methods, choose Tools | Templates.
        switch (state) {
            case StarMap: {

                this.starSystems.buttonNumber = m.buttonNumber;
                this.starSystems.click = m.click;
                this.starSystems.clicks = m.clicks;
                this.starSystems.mouseX = m.mouseX;
                this.starSystems.mouseY = m.mouseY;
                this.pressed = m.pressed;
                this.release = m.release;
                this.starSystems.testZone(this.starSystems, this.starSystems);

                if (hoveredSystem != null && scope.home != this.hoveredPlanet) {
                    Game.exec.execute(this::SystemTransit);
                }

                break;
            }
            case SystemMap: {
                //System.out.println(m.buttonNumber);
                if (m.pressed) {
                    if (this.hoveredPlanet != null && this.hoveredPlanet != scope.home) {
                        this.hoveredPlanet.transMap = null;
                    }
                    this.hoveredPlanet = null;

                    if (m.buttonNumber == 3) {
                        this.selectedSystem.system.ResetAnim();
                        if (this.selectedSystem != scope.home) {
                            this.selectedSystem.transMap = null;
                        }
                        this.selectedSystem = null;
                        this.state = TelescopeState.StarMap;

                    } else if (m.buttonNumber == 1) {
                        this.selectedPlanet = this.hoveredPlanet;
                        if (this.selectedPlanet != null) {
                            this.state = TelescopeState.OrbitMap;
                        }
                    } else {
                        if(this.hoveredPlanet!=null){
                        this.hoveredPlanet.transMap = null;
                        }
                        this.hoveredPlanet = this.selectedPlanet = null;
                    }

                }

                break;
            }
            case OrbitMap: {
                break;
            }
            case ProbeMap: {
                break;
            }
        }
    }

}
