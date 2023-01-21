/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Gerwyn Jones
 */
abstract class Sheet {

    Zone zone;
    boolean on = false;
    boolean hovering = false;
    boolean hold = false;
    boolean drop = false;
    GameObject held;
    GameObject data;
    GameObject swap;
    Icon hover;
}

public class CharacterSheet extends Sheet {

    Icon inLeftHand;
    Icon inRightHand;
    Icon onHead;
    Icon onBack;
    Icon onFront;
    Icon onLegs;
    Icon onLeftLeg;
    Icon onRightLeg;
    //
    Icon head;
    Icon mouth;
    Icon leftHand;
    Icon rightHand;
    Icon body;
    PC character;

    CharacterSheet(PC pc) {
        this.character = pc;
        zone = new Zone(512, 160, 96, 160, 0);
        onHead = new Icon(Game.InfoIcons.get("Object"), zone.x + 32, zone.y + 0);
        head = new Icon(Game.InfoIcons.get("HeadMale"), Game.InfoIcons.get("HeadMale1"), zone.x + 32, zone.y + 32);
        mouth = new Icon(Game.InfoIcons.get("MouthOpen2"), Game.InfoIcons.get("MouthSmile2"), zone.x + 32, zone.y + 64);
        leftHand = new Icon(Game.InfoIcons.get("HandLeft2"), Game.InfoIcons.get("HandLeft4"), zone.x + 0, zone.y + 64);
        rightHand = new Icon(Game.InfoIcons.get("HandRight2"), Game.InfoIcons.get("HandRight4"), zone.x + 64, zone.y + 64);
        inLeftHand = new Icon(Game.InfoIcons.get("Object"), zone.x + 0, zone.y + 32);
        inRightHand = new Icon(Game.InfoIcons.get("Object"), zone.x + 64, zone.y + 32);
        body = new Icon(Game.InfoIcons.get("Character1"), zone.x + 32, zone.y + 96);
        onFront = new Icon(Game.InfoIcons.get("Object"), zone.x + 0, zone.y + 96);
        onBack = new Icon(Game.InfoIcons.get("Object"), zone.x + 64, zone.y + 96);
        onLegs = new Icon(Game.InfoIcons.get("Object"), zone.x + 32, zone.y + 128);
        onLeftLeg = new Icon(Game.InfoIcons.get("Object"), zone.x + 0, zone.y + 128);
        onRightLeg = new Icon(Game.InfoIcons.get("Object"), zone.x + 64, zone.y + 128);

    }

    public void DataZones(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (inRightHand.zone.TestZone(mx, my)) {
        } else if (inLeftHand.zone.TestZone(mx, my)) {
        } else if (onBack.zone.TestZone(mx, my)) {
        } else if (onFront.zone.TestZone(mx, my)) {
        } else if (onLeftLeg.zone.TestZone(mx, my)) {
        } else if (onRightLeg.zone.TestZone(mx, my)) {
        } else if (onLegs.zone.TestZone(mx, my)) {
        } else if (onHead.zone.TestZone(mx, my)) {
        } else //
        if (head.zone.TestZone(mx, my)) {
        } else if (mouth.zone.TestZone(mx, my)) {
        } else if (body.zone.TestZone(mx, my)) {
        } else if (leftHand.zone.TestZone(mx, my)) {
        } else if (rightHand.zone.TestZone(mx, my)) {
        }
    }

    public void DropZones(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (inRightHand.zone.TestZone(mx, my)) {
        } else if (inLeftHand.zone.TestZone(mx, my)) {
        } else if (onBack.zone.TestZone(mx, my)) {
        } else if (onFront.zone.TestZone(mx, my)) {
        } else if (onLeftLeg.zone.TestZone(mx, my)) {
        } else if (onRightLeg.zone.TestZone(mx, my)) {
        } else if (onLegs.zone.TestZone(mx, my)) {
        } else if (onHead.zone.TestZone(mx, my)) {
        } else //
        if (head.zone.TestZone(mx, my)) {
        } else if (mouth.zone.TestZone(mx, my)) {
        } else if (body.zone.TestZone(mx, my)) {
        } else if (leftHand.zone.TestZone(mx, my)) {
        } else if (rightHand.zone.TestZone(mx, my)) {
        }

        hover = null;
        TurnOffHover();
    }

    public void HoverZones(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        hover = null;
        if (inRightHand.zone.TestZone(mx, my)) {
            inRightHand.zone.hover = true;
            hover = inRightHand;
        } else if (inLeftHand.zone.TestZone(mx, my)) {
            inLeftHand.zone.hover = true;
            hover = inLeftHand;
        } else if (onBack.zone.TestZone(mx, my)) {
            onBack.zone.hover = true;
            hover = onBack;
        } else if (onFront.zone.TestZone(mx, my)) {
            onFront.zone.hover = true;
            hover = onFront;
        } else if (onLeftLeg.zone.TestZone(mx, my)) {
            onLeftLeg.zone.hover = true;
            hover = onLeftLeg;
        } else if (onRightLeg.zone.TestZone(mx, my)) {
            onRightLeg.zone.hover = true;
            hover = onRightLeg;
        } else if (onLegs.zone.TestZone(mx, my)) {
            onLegs.zone.hover = true;
            hover = onLegs;
        } else if (onHead.zone.TestZone(mx, my)) {
            onHead.zone.hover = true;
            hover = onHead;
        } else //
        if (head.zone.TestZone(mx, my)) {
            head.zone.hover = true;
            hover = head;
        } else if (mouth.zone.TestZone(mx, my)) {
            mouth.zone.hover = true;
            hover = mouth;
        } else if (body.zone.TestZone(mx, my)) {
            body.zone.hover = true;
            hover = body;
        } else if (leftHand.zone.TestZone(mx, my)) {
            leftHand.zone.hover = true;
            hover = leftHand;
        } else if (rightHand.zone.TestZone(mx, my)) {
            rightHand.zone.hover = true;
            hover = rightHand;
        }
        TurnOffHover();
    }

    void TurnOffHover() {
        if (hover != inRightHand) {
            inRightHand.zone.hover = false;
            inRightHand.flash = false;
        }
        if (hover != inLeftHand) {
            inLeftHand.zone.hover = false;
            inLeftHand.flash = false;
        }
        if (hover != onBack) {
            onBack.zone.hover = false;

            onBack.flash = false;
        }
        if (hover != onFront) {
            onFront.zone.hover = false;
            onFront.flash = false;
        }
        if (hover != onLeftLeg) {
            onLeftLeg.zone.hover = false;

            onLeftLeg.flash = false;
        }
        if (hover != onRightLeg) {
            onRightLeg.zone.hover = false;
            onRightLeg.flash = false;
        }
        if (hover != onLegs) {
            onLegs.zone.hover = false;
            onLegs.flash = false;
        }
        if (hover != onHead) {
            onHead.zone.hover = false;
            onHead.flash = false;
        }
        //
        if (hover != head) {
            head.zone.hover = false;
            head.flash = false;
        }
        if (hover != mouth) {
            mouth.zone.hover = false;

            mouth.flash = false;
        }
        if (hover != body) {
            body.zone.hover = false;
            body.flash = false;
        }
        if (hover != leftHand) {
            leftHand.zone.hover = false;
            leftHand.flash = false;
        }
        if (hover != rightHand) {
            rightHand.zone.hover = false;
            rightHand.flash = false;
        }
    }

    public void PickZones(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (inRightHand.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.inRightHand;

        } else if (inLeftHand.zone.TestZone(mx, my)) {

            this.hold = true;
            this.held = character.inventory.inLeftHand;
        } else if (onBack.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.onBack;
        } else if (onFront.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.onFront;
        } else if (onLeftLeg.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.onLeftLeg;
        } else if (onRightLeg.zone.TestZone(mx, my)) {

            this.hold = true;
            this.held = character.inventory.onRightLeg;
        } else if (onLegs.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.onLegs;
        } else if (onHead.zone.TestZone(mx, my)) {
            this.hold = true;
            this.held = character.inventory.onHead;
        }
        //
   /*
         * if(head.zone.TestZone(mx, my)){ this.hold=true;
         * this.held=character.inventory.onLegs; }else
         * if(mouth.zone.TestZone(mx, my)){}else if(body.zone.TestZone(mx,
         * my)){}else if(leftHand.zone.TestZone(mx, my)){}else
         * if(rightHand.zone.TestZone(mx, my)){}
         */
    }

    public void WeaponZones(MouseWheelEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (inRightHand.zone.TestZone(mx, my)) {
        } else if (inLeftHand.zone.TestZone(mx, my)) {
        } else if (onBack.zone.TestZone(mx, my)) {
        } else if (onFront.zone.TestZone(mx, my)) {
        } else if (onLeftLeg.zone.TestZone(mx, my)) {
        } else if (onRightLeg.zone.TestZone(mx, my)) {
        } else if (onLegs.zone.TestZone(mx, my)) {
        } else if (onHead.zone.TestZone(mx, my)) {
        } else //
        if (head.zone.TestZone(mx, my)) {
        } else if (mouth.zone.TestZone(mx, my)) {
        } else if (body.zone.TestZone(mx, my)) {
        } else if (leftHand.zone.TestZone(mx, my)) {
        } else if (rightHand.zone.TestZone(mx, my)) {
        }
    }

    void Paint(Graphics2D g) {
        if (data == null) {
            g.drawString("Physcal/Mental/Spiritual", zone.x - 32, zone.y - 128);
            g.drawString("Force:" + Integer.toString(character.force) + " / " + Integer.toString(character.mentalForce) + " / " + Integer.toString(character.spiritualForce), zone.x - 32, zone.y - 96);
            g.drawString("Power:" + Integer.toString(character.power) + " / " + Integer.toString(character.mentalPower) + " / " + Integer.toString(character.spiritualPower), zone.x - 32, zone.y - 80);
            g.drawString("Energy:" + Integer.toString(character.energy) + " / " + Integer.toString(character.mentalEnergy) + " / " + Integer.toString(character.spiritualEnergy), zone.x - 32, zone.y - 64);
            g.drawString("Body:" + Integer.toString(character.bodyPoints) + " Armour:" + Integer.toString(character.armourPoints), zone.x - 32, zone.y - 48);
            g.drawString("Psi:" + Integer.toString(character.psi), zone.x - 32, zone.y - 32);
            g.drawString("Sanity:" + Integer.toString(character.sanity), zone.x - 32, zone.y - 16);
            head.Paint(g);
            mouth.Paint(g);
            leftHand.Paint(g);
            rightHand.Paint(g);
            body.Paint(g);
            inLeftHand.Paint(g);
            inRightHand.Paint(g);
            if (on) {
                onHead.Paint(g);
                onBack.Paint(g);
                onFront.Paint(g);
                onLegs.Paint(g);
                onLeftLeg.Paint(g);
                onRightLeg.Paint(g);
            }
        } else {
            data.PaintData(g, zone.x - 32, zone.y - 128);
        }
    }

    void UpDateIcons(Inventory inv) {

        if (inv.inRightHand != null) {
            this.inRightHand.image = inv.inRightHand.Icon;
        } else {
            this.inRightHand.image = Game.InfoIcons.get("Object");
        }
        if (inv.inLeftHand != null) {
            this.inLeftHand.image = inv.inLeftHand.Icon;
        } else {
            this.inLeftHand.image = Game.InfoIcons.get("Object");
        }
        if (inv.onBack != null) {
            this.onBack.image = inv.onBack.Icon;
        } else {
            this.onBack.image = Game.InfoIcons.get("Object");
        }
        if (inv.onFront != null) {
            this.onFront.image = inv.onFront.Icon;
        } else {
            this.onFront.image = Game.InfoIcons.get("Object");
        }
        if (inv.onLegs != null) {
            this.onLegs.image = inv.onLegs.Icon;
        } else {
            this.onLegs.image = Game.InfoIcons.get("Object");
        }
        if (inv.onRightLeg != null) {
            this.onRightLeg.image = inv.onRightLeg.Icon;
        } else {
            this.onRightLeg.image = Game.InfoIcons.get("Object");
        }
        if (inv.onLeftLeg != null) {
            this.onLeftLeg.image = inv.onLeftLeg.Icon;
        } else {
            this.onLeftLeg.image = Game.InfoIcons.get("Object");
        }
    }
}
