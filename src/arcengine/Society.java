/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn
 */
public class Society extends HumanSettlement {

    People young;
    People adult;
    People old;
    People sick;

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
