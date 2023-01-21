/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;

/**
 *
 * @author Gerwyn Jones
 */
public abstract class MouseZone extends Box implements InterPaint, InstantInterAction, InterActionTest, InterAction, Actor, Changeable, Updater {

    protected static final int DEFAULT_OFFSET = 16;
    protected static final int DEFAULT_BUTTON = 24;

    ///////////////////////
    protected volatile double cx;
    protected volatile double cy;
    public volatile int mouseX;
    public volatile int mouseY;
    public volatile int clicks = 0;
    public volatile boolean pressed;
    public volatile boolean click;
    public volatile boolean release = true;
    public volatile boolean over;
    public volatile short buttonNumber;
    public String label;
    public static MouseEvent event;
    public Box inside;
    protected RingList<Action> actions = new RingList();
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public MouseZone(int x, int y, int width, int height, String label) {
        super(x, y, width, height);
        cx = x + width * 0.5f;
        cy = y + height * 0.5f;
        mouseX = 0;
        mouseY = 0;
        this.click = false;
        this.pressed = false;
        this.over = false;
        this.label = label;

    }

    public Point Center() {
        return new Point(cx, cy);
    }

// <editor-fold defaultstate="collapsed" desc="Interfaces">
    public void print() {
        System.out.println(this.toString());
        //OVERIDE

    }

    public void active() {
    }

    public void active(short stat) {
        switch (stat) {
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
                break;
            }
            default: {
                break;
            }
        }
    }

    public void reset(short state) {
        this.click = false;
        this.pressed = false;
        this.over = false;
        this.buttonNumber = state;

    }

    public void reset() {
        this.click = false;
        this.pressed = false;
        this.over = false;
        this.buttonNumber = 0;
    }

    public void mouseReleased(MouseEvent me) {
        if (me != null) {
            this.event = me;
            release = true;
        }

    }

    public void mouseEntered(MouseEvent me) {
        if (me != null) {
            this.event = me;
            this.mouseX = me.getX();
            this.mouseY = me.getY() - 16;
        }
    }

    public void mouseExited(MouseEvent me) {
        this.event = me;
    }

    public short mouseLogic() {
        return this.mouseLogic(this.event);
    }

    public short mouseLogic(MouseEvent event) {
//this.event=me; 
/*if(event.getID()==event.BUTTON1_MASK)
         {this.buttonNumber=1;}else{if(event.getID()==event.BUTTON2_MASK)
         {this.buttonNumber=6;}else{if(event.getID()==event.BUTTON3_MASK)
         {this.buttonNumber=12;}else{this.buttonNumber=1;}}}
         */
        if (this.clicks > 0) {
            this.pressed = true;
            this.buttonNumber = 1;
        } else {
            //this.buttonNumber=0;
            //this.click=false; 
            // this.pressed=false;
            //this.release=false;
            //this.over=false;
            this.release = true;
        }// END OF CLICK IF
//
        if (event.isAltDown() == true) {
            this.buttonNumber += 1;
        } else {
            if (event.isMetaDown() == true) {
                this.buttonNumber += 2;
            } else {
                if (event.isShiftDown() == true) {
                    this.buttonNumber += 3;
                } else {
                    if (event.isControlDown() == true) {
                        this.buttonNumber += 4;
                    } else {
                        return this.buttonNumber;
                    }
                }
            }
        }

        return this.buttonNumber;
    }

    public void mouseMoved(MouseEvent me) {
        if (me != null) {
            this.event = me;
            
            this.clicks = this.event.getClickCount();
            this.mouseLogic(me);
            this.mouseX = me.getX();
            this.mouseY = me.getY() - DEFAULT_OFFSET;
            this.over = true;
            if(me.getButton()>0){
                this.mouseDragged(me);
            }
        }
    }

    public void mouseDragged(MouseEvent me) {
        if (me != null) {
            this.pressed = true;
            this.mouseX = me.getX();
            this.mouseY = me.getY() - DEFAULT_OFFSET;
            this.event = me;
        }
    }
    public void mouseWheeled(MouseWheelEvent me) {
        if (me != null) {
            this.mouseX = me.getX();
            this.mouseY = me.getY() - DEFAULT_OFFSET;
            this.event = me;
        }
    }

    public final MouseZone mouseAction() {
        return new MouseArea(mouseX, mouseY, 1, 1, this.toString());
    }

    @Override
    public abstract void mouseAction(boolean b);

    @Override
    public abstract void mouseAction(MouseZone m, InterAction i);

    @Override
    public abstract void paint(Graphics2D g);

    @Override
    public abstract void testZone(MouseZone mouseZone, InterAction act);

    @Override
    public abstract void OnSizeChange(Box oldBox, Box newBox);

    @Override
    public abstract void Update(GameTime time);
// </editor-fold>

}

class MouseArea extends MouseZone {

    //public mouseX;
    //public mouseY;
    public MouseArea(ArcFrame f) {
        //this.screen=screen;

        super(0, 0, f.getWidth(), f.getHeight(), "NEW");

    }

    public MouseArea(int w, int h) {
        //this.screen=screen;
        super(0, 0, w, h, "NEW");

    }

    public MouseArea(int xx, int yy, int w, int h) {
        //this.screen=screen;
        super(xx, yy, w, h, "NEW");

    }

    public MouseArea(int xx, int yy, int w, int h, String caption) {
        //this.screen=screen;
        super(xx, yy, w, h, caption);

    }

    @Override
    public void testZone(MouseZone mouseZone, InterAction act) {
        //this.screen=screen;
        //this.mouseZone=new MouseZone(x,y,width,height,l);

        //System.out.println("Screen Action Test");
        this.mouseX = mouseZone.mouseX;
        this.mouseY = mouseZone.mouseY;
        if (this.mouseX > mouseZone.origin.x && this.mouseY > mouseZone.origin.y && this.mouseX < (mouseZone.origin.x + mouseZone.size.x) && this.mouseY < (mouseZone.origin.y + mouseZone.size.y)) {
            if (this.click == true) {

                //this.click=false; 
                this.click = mouseZone.click = true;
                act.mouseAction(mouseZone, this);
                //act.mouseAction(event.getClickCount());
            } else if (event.getID() == event.MOUSE_PRESSED) {
                this.pressed = mouseZone.pressed = true;
                //this.click=false; 
                // this.pressed=false;
                //this.release=false;
                //act.mouseAction(event.isPopupTrigger());
                act.mouseAction(mouseZone, this);
            }
            if (event.getID() == event.MOUSE_RELEASED) {
                //this.release=false;
                //this.pressed=false;
                this.release = mouseZone.release = true;
                //this.click=false;
                //mouseZone.click=false;
                act.mouseAction(mouseZone, this);
            } else {
                if (event.getID() == event.MOUSE_MOVED) {
                    this.over = mouseZone.over = true;
                    //this.click=false; 
                    // this.pressed=false;
                    //this.release=false;
                    //this.over=false;
                    act.mouseAction(mouseZone, this);

                }
                if (event.getID() == event.MOUSE_PRESSED) {

                    //act.mouseAction(event.getClickCount());
                }
            }
        }
    }

    public MouseEvent getLastEvent() {
        return this.event;
    }

    public void mouseClicked(MouseEvent me) {
        release = true;
        this.event = me;
        if ((this.event.getClickCount() > 0) && (this.click = false)) {
            this.clicks++;
        } else {
            this.clicks = this.event.getClickCount();
        }
        //////////////////////////////////////////////////////////////////////////////////  
        this.mouseLogic(me);
        //
        this.mouseX = me.getX();
        this.mouseY = me.getY() - DEFAULT_OFFSET;
        this.click = true;

    }

    public void mousePressed(MouseEvent me) {
        release = false;
        this.event = me;
        this.clicks = this.event.getClickCount();
        this.mouseLogic(me);
        this.mouseX = me.getX();
        this.mouseY = me.getY() - DEFAULT_OFFSET;
        this.pressed = true;

    }

    @Override
    public void print() {
        System.out.println("MOUSE :: CLICKS ::" + this.clicks + "::X::" + this.mouseX + "::Y::" + this.mouseY + "::NUMBER::" + this.buttonNumber);
        System.out.println("STATE ::" + this.click + "::PRESSED::" + this.pressed + "::OVER::" + this.over + "::RELEASED::" + this.release);
    }

    @Override
    public void mouseAction(boolean b) {
    }

    @Override
    public void mouseAction(MouseZone m, InterAction i) {
    }

    @Override
    public void paint(Graphics2D g) {
    }

    @Override
    public void OnSizeChange(Box oldBox, Box newBox) {
    }

    @Override
    public void Update(GameTime time) {
    }
    // </editor-fold>

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
