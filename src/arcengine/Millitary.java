/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

abstract class HumanSettlement extends LandElement {

    java.util.ArrayList<Local> verts = new java.util.ArrayList<>();
    Government governed;
    float radius;
    People total;
    java.util.Set<Tech> offence = new java.util.HashSet<Tech>();
    java.util.Set<Tech> defence = new java.util.HashSet<Tech>();
    java.util.Set<Resource> logistics = new java.util.HashSet<Resource>();
    java.util.Set<Money> expenditure = new java.util.HashSet<Money>();

    abstract void SetShape();
}

abstract class Service extends HumanSettlement {

    People men;
    People women;
    int computers;
    int robots;
}

abstract class ServiceBuilding extends Service implements InterAction {

    Pigment shade;
    Color color;

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void SetShape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/**
 *
 * @author Gerwyn
 */
public class Millitary extends Service {

    @Override
    boolean RunSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void paint(Graphics2D g, int locx, int locy, float scale) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void SetShape() {
        throw new UnsupportedOperationException("Not supported yet.");
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
