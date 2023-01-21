/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
//

abstract interface Actor {

    public abstract void AddActive(Action act);

    public abstract void RemoveActive(Action act);

    public abstract void TransActive(Collection<Action> acts);

    public abstract void ReActive(Collection<Action> acts);
}

@FunctionalInterface
abstract interface Actions {

    public abstract void active(Collection<Action> c);
}

@FunctionalInterface
abstract interface PrintAction {

    public abstract void print();
}

@FunctionalInterface
abstract interface Action {

    public abstract void active();
}

@FunctionalInterface
abstract interface ActionState {

    public abstract void active(short state);
}

@FunctionalInterface
abstract interface ActionReset {

    public abstract void reset();
}

@FunctionalInterface
abstract interface ActionStateReset {

    public abstract void reset(short state);
}

@FunctionalInterface
abstract interface InterZone {

    public abstract MouseZone mouseAction();

}

@FunctionalInterface
abstract interface InstantInterAction {

    public abstract void mouseAction(boolean b);

}

@FunctionalInterface
abstract interface InterActionTest {

    public abstract void testZone(MouseZone mouseZone, InterAction act);
}

@FunctionalInterface
abstract interface ZoneInterAction {

    public abstract void mouseAction(MouseZone m);
}

@FunctionalInterface
abstract interface RecordInterAction {

    public abstract void mouseAction(MouseEvent me);

}

@FunctionalInterface
abstract interface MouseInterAction {

    public abstract void mouseAction(Mouse m);

}

@FunctionalInterface
abstract interface RecordWheelInterAction {

    public abstract void mouseAction(MouseWheelEvent mw);

}

@FunctionalInterface
abstract interface InterCollision {

    public abstract void collision(CollisionDetector cd);

}

@FunctionalInterface
abstract interface InterPaint {

    public abstract void paint(Graphics2D g);

}

@FunctionalInterface
abstract interface InterUpdate {

    public abstract void update(GameTime t);

}

/**
 *
 * @author Gerwyn Jones
 */
public class Mouse {

    public MouseEvent event;
    public MouseWheelEvent wheelEvent;
    public MouseEvent dragged;
    public MouseEvent released;
}

class MultiKeyPressListener implements KeyListener {

    // Set of currently pressed keys
    public final java.util.Set<Integer> pressed = new java.util.HashSet<Integer>();

    void ClearKeys() {
        pressed.clear();
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());

    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {/*
         * Not used
         */ }
}
