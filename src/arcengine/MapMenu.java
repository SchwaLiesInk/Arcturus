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
import java.util.ArrayList;
import java.util.Hashtable;

abstract interface Choice {

    public abstract MenuChoice GetChoice();
}

abstract interface MenuAction {

    public abstract void Choice(MenuChoice c);
}

class MenuChoice extends ActionAbility {

    String name;
    Screen screen;
    short mainMenuIndex;
    short menuIndex;
    short menuItemIndex;
    short subMenuItemIndex;

    MenuChoice(String name, Action act) {
        super(act);
        this.name = name;
    }
}

class MenuList {

    Hashtable<String, MapMenu> mainMenus = new Hashtable<>();
    Hashtable<String, MapMenuItem> items = new Hashtable<>();

    void AddMenu(MapMenu menu) {
        mainMenus.put(menu.label, menu);
    }

    void AddMenuItem(MapMenuItem item, String into) {
        MapMenu menu = mainMenus.get(into);
        menu.menuItems.add(item);
        items.put(item.label, item);
    }

    void AddMenuItem(MapMenuItem item, MapMenu into) {
        into.menuItems.add(item);
        items.put(item.label, item);

    }

    MapMenu[] GetMenus() {
        MapMenu[] m = new MapMenu[this.mainMenus.values().size()];
        m = this.mainMenus.values().toArray(m);
        return m;
    }
}

/**
 *
 * @author Gerwyn Jones
 */
class MapMenuItem extends MapButton implements Choice {

    MenuChoice choice;

    MapMenuItem(String name, int atx, int aty, int w, int h, Box in) {
        super(name, atx, aty, w, h);
        choice = new MenuChoice(name, this::OnAction);
        inside = in;
        this.text = name;
        this.On = false;
    }

    @Override
    public void OnAction() {
        choice.screen.Choice(choice);
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
        this.mouseMoved(mouseZone.event);
        //System.out.println("Test");
        if ((!this.On) && InterFocus(this.mouseX, this.mouseY)) {
            this.On = true;
            /*for (int i = 0; i < this.menuItems.size(); i++) {
             this.menuItems.get(i).testZone(mouseZone, this.menuItems.get(i));
             }*/
        } else if (this.On && InterFocus(this.mouseX, this.mouseY)) {
            /*for (int i = 0; i < this.menuItems.size(); i++) {
             this.menuItems.get(i).testZone(mouseZone, this.menuItems.get(i));
             }*/

        } else {
            this.On = false;

        }
    }

    @Override
    public void paint(Graphics2D g) {
        int fromx = (int) (origin.x);
        int fromy = (int) (origin.y);
        if (this.inside != null) {
            fromx += (int) (inside.origin.x);
            fromy += (int) (inside.origin.y);
        }
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

    @Override
    public MenuChoice GetChoice() {
        return choice;
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

public class MapMenu extends MapButton implements Choice {

    MenuChoice choice;
    protected ArrayList<MapMenuItem> menuItems = new ArrayList<>();
    protected int currentItem;
    protected ArrayList<GImage> images = new ArrayList<>();
    protected int currentImage = 0;

    MapMenu(String name, int atx, int aty, int w, int h) {
        super(name, atx, aty, w, h);
        choice = new MenuChoice(name, this::OnAction);

    }

    @Override
    public void OnAction() {
        if (currentItem < menuItems.size()) {
            menuItems.get(currentItem).OnAction();
            this.On = false;
        }
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
            g.drawString(text, fromx + 5, fromy);
            g.setColor(onFore);
            g.drawRect(fromx, fromy, sizex, sizey);
            g.drawString(text, fromx + 4, fromy);
            //
            if (currentImage < images.size()) {
                images.get(currentImage).Draw(fromx, fromy, g);
            }
            for (int i = 0; i < menuItems.size(); i++) {
                menuItems.get(i).paint(g);
            }
        } else if (!inFocus) {
            g.setColor(Color.black);
            g.drawString(text, fromx + 5, fromy + 16);
            g.setColor(fore);
            g.drawString(text, fromx + 4, fromy + 16);
        } else {
            if (inAble) {

                g.setColor(fore);
                g.drawString(text, fromx + 5, fromy + 16);
                g.setColor(back);
                g.drawString(text, fromx + 4, fromy + 16);
            } else {

                g.setColor(fore);
                g.drawString(text, fromx + 5, fromy + 16);
                g.setColor(Color.black);
                g.drawString(text, fromx + 4, fromy + 16);
            }
        }
    }

    boolean TextFocus(int x, int y) {
        if (x > origin.x && y > (origin.y) && x < (origin.x + text.length() * 8) && y < (origin.y + 32)) {
            return true;
        }
        return false;
    }

    @Override
    public void AbleAction() {
    }

    @Override
    public void FocusAction() {
    }

    @Override
    public void print() {
    }

    @Override
    public void active() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void active(short state) {
    }

    @Override
    public void reset(short state) {
    }

    @Override
    public void testZone(MouseZone mouseZone, InterAction act) {
        this.mouseMoved(mouseZone.event);
        //System.out.println("Test");
        if ((!this.On) && TextFocus(this.mouseX, this.mouseY)) {
            this.On = true;
            for (int i = 0; i < this.menuItems.size(); i++) {
                this.menuItems.get(i).testZone(mouseZone, this.menuItems.get(i));
            }
        } else if (this.On && Focus(this.mouseX, this.mouseY)) {
            for (int i = 0; i < this.menuItems.size(); i++) {
                this.menuItems.get(i).testZone(mouseZone, this.menuItems.get(i));
            }

        } else {
            this.On = false;

        }
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    @Override
    public MenuChoice GetChoice() {
        return choice;
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
