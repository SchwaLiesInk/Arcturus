/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Gerwyn Jones
 */
public class TextZone extends MapButton {

    ArrayList<String> lines;

    TextZone(String name, int x, int y, int w, int h) {
        super(name, x, y, w, h);
        lines = new ArrayList();
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
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
        } else if (!inFocus) {
            g.setColor(Color.black);
            g.drawString(text, fromx + 5, fromy);
            g.setColor(fore);
            g.drawString(text, fromx + 4, fromy);
        } else {
            if (inAble) {

                g.setColor(fore);
                g.drawString(text, fromx + 5, fromy);
                g.setColor(back);
                g.drawString(text, fromx + 4, fromy);
            } else {

                g.setColor(fore);
                g.drawString(text, fromx + 5, fromy);
                g.setColor(Color.black);
                g.drawString(text, fromx + 4, fromy);
            }
        }
        int n = fromy + 16;
        for (int i = 0; i < lines.size(); i++) {

            g.drawString(lines.get(i), fromx, n);
            n += 16;
        }
    }

    @Override
    public void testZone(MouseZone mouseZone, InterAction act) {

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
    public void OnSizeChange(Box oldBox, Box newBox) {
    }

    @Override
    public void Update(GameTime time) {
    }

}
