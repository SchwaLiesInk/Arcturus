/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;

/**
 *
 * @author Gerwyn Jones
 */
public class StarMap extends MouseArea implements GraphicsUpdate {

    RingList<Segment> grid1 = new RingList();
    RingList<Segment> grid2 = new RingList();
    RingList<Segment> grid3 = new RingList();
    RingList<Point> systems = new RingList();
    RingList<SmallPlanet> selected = new RingList();
    RingList<Point> selectedPoints = new RingList();
    int range = 5;
    TelescopeScreen teleScreen;

    StarMap(TelescopeScreen scr) {
        super(scr.frame.GetWidth(), scr.frame.GetHeight());
        teleScreen = scr;
        TelescopeScreen.CreateGrid(grid1, range, 1.0 / 3.0);
        TelescopeScreen.CreateGrid(grid2, range * 3, 1.0 / 3.0);
        TelescopeScreen.CreateGrid(grid3, range * 9, 1.0 / 3.0);
    }

    void Render() {
        TelescopeScreen.StoreGrid(teleScreen, new Vector(), grid1, new Pigment(32, 128, 128, 255), null, 10, DrawType.None, 0);
        TelescopeScreen.StoreGrid(teleScreen, new Vector(), grid2, new Pigment(16, 128, 128, 255), null, 30, DrawType.None, 0);
        TelescopeScreen.StoreGrid(teleScreen, new Vector(), grid3, new Pigment(8, 128, 128, 255), null, 90, DrawType.None, 0);
        this.systems = new RingList();
        Node<SmallPlanet> p = teleScreen.scope.starMap.Start();
        for (; p.data != null; p = p.next) {
            Point at = new Point();
            SmallPlanet plan = p.data;
            Pigment p1 = new Pigment(plan.revolvesRound.color);
            p1.Alpha(8);
            Pigment p2 = new Pigment(plan.revolvesRound.color);
            p2.Alpha(16);
            Pigment p3 = new Pigment(plan.revolvesRound.color);
            Pigment shades[] = new Pigment[]{p1, p2, p3};
            Point to = plan.system.relativeCoord.AsPoint(plan.revolvesRound.relative.mass*7);
            teleScreen.LoadVertexPoints(to, shades, null, 1.0, 3.0, teleScreen.renderList, DrawType.Circle, DrawType.None, at, 0);
            this.systems.Append(at);
        }
    }

    public void Paint(Graphics2D g) {
        Node<Point> at = systems.Start();
        Node<SmallPlanet> p = teleScreen.scope.starMap.Start();
        for (; at.data != null && p.data != null; at = at.next, p = p.next) {
            SmallPlanet plan = p.data;
            if (at.data.x != 0 && at.data.y != 0) {

                g.setColor(plan.revolvesRound.color.Color());
                g.drawString(plan.name, (float) (at.data.x + at.data.r * 0.25), (float) (at.data.y));
            }
        }
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {

        this.selected = new RingList();
        this.selectedPoints = new RingList();
        
        Point mouse = new Point(m.mouseX, m.mouseY, 0, 1);
        //
        Node<Point> at = systems.Start();
        Node<SmallPlanet> p = teleScreen.scope.starMap.Start();
        for (; at.data != null && p.data != null; at = at.next, p = p.next) {
            SmallPlanet plan = p.data;
            if (at.data.x != 0 && at.data.y != 0) {
                if (at.data.Collides2D(mouse, at.data.r * 0.5)) {
                    this.selected.Append(plan);
                    this.selectedPoints.Append(at.data);
                }
            }
        }

                //System.out.println("mx "+m.mouseX+"my "+m.mouseY+" "+m.pressed+" "+m.buttonNumber);
        //System.out.println("selected "+selected.Length());
        if (selected.Length() > 0) {
            if (selected.Length() > 1) {
                SmallPlanet choice = null;
                double z = Linear.MAX_MAP;
                at = selectedPoints.Start();
                p = selected.Start();
                for (; at.data != null && p.data != null; at = at.next, p = p.next) {
                    if (at.data.z < z) {
                        choice = p.data;
                        z = at.data.z;
                    }
                }

                if (m.pressed) {
                    if (m.buttonNumber == 1) {
                        this.teleScreen.selectedSystem = choice;
                        this.teleScreen.state = TelescopeState.SystemMap;
                    }
                } else {
                    this.teleScreen.hoveredSystem = choice;
                }
            } else {
                if (m.pressed) {
                    if (m.buttonNumber == 1) {
                        this.teleScreen.selectedSystem = selected.First();
                        this.teleScreen.state = TelescopeState.SystemMap;
                    }
                } else {
                    this.teleScreen.hoveredSystem = selected.First();

                }
            }
        }else{
        this.teleScreen.hoveredSystem = null;
        }

    }

    @Override
    public void Update(GameTime time, Camera cam) {

    }

}
