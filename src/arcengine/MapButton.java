/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;
import java.util.function.Function;

/**
 *
 * @author Gerwyn Jones
 */
class BuildMapButton extends MapButton {

    BuildMapButton(String name, int atx, int aty, int w, int h) {
        super(name, atx, aty, w, h);
        back = new Color(64, 64, 64, 64);
    }

    @Override
    public void OnAction() {
    }

    @Override
    public void AbleAction() {
    }

    @Override
    public void FocusAction() {
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    @Override
    public void testZone(MouseZone mouseZone, InterAction act) {
        this.mouseX = mouseZone.mouseX;
        this.mouseY = mouseZone.mouseY;

    }

    @Override
    public void OnSizeChange(Box oldBox, Box newBox) {
        if (newBox != null) {

            this.origin = newBox.origin;
            this.size = newBox.size;
            cx = this.origin.x * 0.5;
            cy = this.origin.y * 0.5;

        }

    }

    @Override
    public void Update(GameTime time) {
    }
}

class DataMapButton extends MapButton {

    DataMapButton(String name, int atx, int aty, int w, int h, Color color) {
        super(name, atx, aty, w, h);
        back = color;
        onBack = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
        fore = new Color((128 + color.getRed()) >> 1, (128 + color.getGreen()) >> 1, (128 + color.getBlue()) >> 1, 255);
        onFore = new Color((255 + color.getRed()) >> 1, (255 + color.getGreen()) >> 1, (255 + color.getBlue()) >> 1, 255);
    }

    @Override
    public void OnAction() {
    }

    @Override
    public void AbleAction() {
    }

    @Override
    public void FocusAction() {
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    @Override
    public void testZone(MouseZone mouseZone, InterAction act) {
        this.mouseX = mouseZone.mouseX;
        this.mouseY = mouseZone.mouseY;

    }

    @Override
    public void OnSizeChange(Box oldBox, Box newBox) {
        if (newBox != null) {

            this.origin = newBox.origin;
            this.size = newBox.size;
            cx = this.origin.x * 0.5;
            cy = this.origin.y * 0.5;

        }
    }

    @Override
    public void Update(GameTime time) {
    }
}

abstract interface ActionAble {

    abstract void AbleAction();

}

abstract interface ActionFocus {

    abstract void FocusAction();
}

abstract interface ActionOn {

    abstract void OnAction();
}

class Actioner implements Actor, Action {

    RingList<ActionAbility> abilities = new RingList();

    @Override
    public void AddActive(Action act) {
        if (act instanceof ActionAbility) {

            abilities.Append((ActionAbility) act);
        } else {
            abilities.Append(new ActionAbility(act));
        }
    }

    @Override
    public void RemoveActive(Action act) {
        if (act instanceof ActionAbility) {
            abilities.Remove((ActionAbility) act);
        }
    }

    @Override
    public void TransActive(Collection<Action> acts) {
        for (Action act : acts) {
            if (act instanceof ActionAbility) {
                abilities.Append((ActionAbility) act);
            }
        }
    }

    @Override
    public void ReActive(Collection<Action> acts) {
        for (Action act : acts) {
            if (act instanceof ActionAbility) {
                abilities.Remove((ActionAbility) act);
            }
        }
    }

    @Override
    public void active() {
        for (ActionAbility able : abilities) {
            able.active();
        }
    }
}

class ActionAbility implements Action, ActionState {

    Action act;
    Action able;
    Action focus;
    short state;

    ActionAbility(Action act) {
        this.state = 0;
        this.act = act;
        this.able = null;
        this.focus = null;
    }

    ActionAbility(Action act, boolean all) {
        this.state = 0;
        this.act = act;
        if (all) {
            this.able = act;
            this.focus = act;
        }
    }

    ActionAbility(short state, Action act, Action able, Action focus) {
        this.state = state;
        this.act = act;
        this.able = able;
        this.focus = focus;
    }

    @Override

    public void active() {
        active(state);
    }

    @Override
    public void active(short state) {
        if (state == 0) {
            if (act != null) {
                act.active();
            }
        } else {
            if (state == 1) {
                if (able != null) {
                    able.active();
                }
            } else {

                if (state > -1) {
                    if (focus != null) {
                        focus.active();
                    }

                }
            }
        }
    }

}

public abstract class MapButton extends MouseZone implements InterAction, ActionAble, ActionFocus, ActionOn {

    Color back = new Color(64, 64, 64, 64);
    Color onBack = new Color(0, 0, 0, 255);
    Color fore = new Color(128, 128, 128, 255);
    Color onFore = new Color(255, 255, 255, 255);

    boolean inFocus = false;
    boolean inAble = false;
    boolean On = false;
    String text;

    //
    //
    MapButton(String name, int atx, int aty, int w, int h) {
        super(atx, aty, w, h, name);
        this.text = name;
    }

    @Override
    public void paint(Graphics2D g) {
        int fromx = (int) origin.x;
        int fromy = (int) origin.y;
        int sizex = (int) (size.x);
        int sizey = (int) (size.y);

        if (On) {

            g.setColor(onBack);
            g.fillRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
            g.setColor(fore);
            g.drawRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
            g.drawString(text, fromx + 5, fromy + 16);
            g.setColor(onFore);
            g.drawRect(fromx, fromy, sizex, sizey);
            g.drawString(text, fromx + 4, fromy + 16);
        } else if (!inFocus) {
            g.setColor(back);
            g.fillRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
            g.setColor(Color.black);
            g.drawRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
            g.drawString(text, fromx + 5, fromy + 16);
            g.setColor(fore);
            g.drawRect(fromx, fromy, sizex, sizey);
            g.drawString(text, fromx + 4, fromy + 16);
        } else {
            if (inAble) {

                g.setColor(back);
                g.fillRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
                g.setColor(fore);
                g.drawRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
                g.drawString(text, fromx + 5, fromy + 16);
                g.setColor(back);
                g.drawRect(fromx, fromy, sizex, sizey);
                g.drawString(text, fromx + 4, fromy + 16);
            } else {

                g.setColor(back);
                g.fillRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
                g.setColor(fore);
                g.drawRect(fromx + 1, fromy + 1, sizex + 1, sizey + 2);
                g.drawString(text, fromx + 5, fromy + 16);
                g.setColor(Color.black);
                g.drawRect(fromx, fromy, sizex, sizey);
                g.drawString(text, fromx + 4, fromy + 16);
            }
        }
    }

    boolean Focus(int x, int y) {
        if (x > origin.x && y > (origin.y) && x < (origin.x + size.x) && y < (origin.y + size.y)) {
            return true;
        }
        return false;
    }

    boolean InterFocus(int x, int y) {
        int originx = (int) (origin.x);
        int originy = (int) (origin.y);
        if (inside != null) {
            originx += (int) inside.origin.x;
            originy += (int) inside.origin.y;
        }
        if (x > originx && y > (originy) && x < (originx + size.x) && y < (originy + size.y)) {
            return true;
        }
        return false;
    }

    @Override
    public abstract void OnAction();

    @Override
    public abstract void AbleAction();

    @Override
    public abstract void FocusAction();

    @Override
    public void AddActive(Action act) {
        this.actions.Append(act);
    }

    @Override
    public void RemoveActive(Action act) {
        this.actions.Remove(act);
    }

    @Override
    public void TransActive(Collection<Action> acts) {
        for (Action ac : acts) {
            actions.Append(ac);
        }
    }

    @Override
    public void ReActive(Collection<Action> acts) {
        for (Action ac : acts) {
            actions.Remove(ac);
        }
    }
}
