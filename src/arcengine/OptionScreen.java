/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Function;

/**
 *
 * @author Gerwyn Jones
 */
public abstract class OptionScreen extends Screen implements Settings {

    MenuChoice choice;
    MenuList menuLists[];
    MapMenu[] currentMenus;
    int currentMenu;

    OptionScreen(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);
        currentMenu = 0;
    }

    void AddMenuList(int x, int y, int w, int h, int spacein, int step, String mainMenu, String[] names, int menuIndex) {

        MapMenu main = new MapMenu(mainMenu, x, y, w, h);
        //races.On=true;
        int ix = 0;
        int iy = 0;
        menuLists[menuIndex] = new MenuList();
        menuLists[menuIndex].AddMenu(main);
        for (int j = 0; j < names.length; j++) {
            ix += spacein;
            if (ix >= w) {
                ix = spacein;
                iy += step;
            }
            MapMenuItem menuItem = new MapMenuItem(names[j], ix, iy, spacein, step, main);
            menuItem.choice.screen = this;
            menuItem.choice.mainMenuIndex = (short) menuIndex;
            menuItem.choice.menuIndex = (short) menuLists[menuIndex].mainMenus.size();
            menuItem.choice.menuItemIndex = (short) j;
            menuLists[menuIndex].AddMenuItem(menuItem, main);
        }
        currentMenus = menuLists[currentMenu].GetMenus();
    }

    @Override
    public void mouseAction(boolean b) {

    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
        for (int z = 0; z < this.mouseZones.size(); z++) {
            this.mouseZones.get(z).testZone(m, this.mouseZones.get(z));
        }
        currentMenus = menuLists[currentMenu].GetMenus();
        if (currentMenus != null) {
            for (int n = 0; n < currentMenus.length; n++) {
                currentMenus[n].testZone(m, currentMenus[n]);
            }
        }
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
        if (currentMenus != null) {

            for (int i = 0; i < currentMenus.length; i++) {
                currentMenus[i].paint(g);
            }
        }
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
    public void paint(Graphics2D g) {
    }

}

class OptionChoice {

    int chosen;
    String chosenName;
    String[] names;
}

class ArcOptionScreen extends OptionScreen {

    OptionChoice race;

    ArcOptionScreen(Activator f, int x, int y, int w, int h, String name) {
        super(f, x, y, w, h, name);
        race = new OptionChoice();
        race.chosen = 1;
        race.chosenName = "Human";
        SetUp();
    }

    @Override
    public void SetUp() {
        int pw = (int) (DEFAULTX);
        int ph = (int) (DEFAULTY);
        int sw = (int) (size.x);
        int sh = (int) (size.y);
        int x = (sw >> 1) - (sw >> 3);
        int y = (sh >> 1) - (sh >> 3);
        menuLists = new MenuList[1];
        race.names = new String[]{
            "Hominid", "Human", "Grey", "Blue", "Green", "Eon", "Io", "Arcturan",
            "Cyclops", "Giant", "Dwarf", "Elf", "Goblin", "Fairy", "Pixie", "Angel",
            "Lion", "Tiger", "Wolf", "Dolphin", "Whale", "Ape", "Lizard", "Bird",
            "Insectoid", "Arthropoid", "Lepedopterroid", "Aniplantoid", "Animaloid", "Robotic", "Exotic", "Apparitional"
        };
        AddMenuList(x, y, pw >> 2, ph >> 2, pw >> 4, DEFAULT_BUTTON, "Alien Races", race.names, 0);
    }

    @Override
    public void Choice(MenuChoice c) {
        switch (c.mainMenuIndex) {
            case 0: {
                switch (c.menuIndex) {
                    case 0: {
                        race.chosen = c.menuItemIndex;
                        race.chosenName = c.name;
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void Update(GameTime time) {
    }
}
