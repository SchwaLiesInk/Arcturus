/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

class Arc extends Game implements Activator {

    ArcSetUp arcfx;
    short state;
    ArcFrame frame;

    Arc(ArcSetUp arcx) {
        super();

        arcfx = arcx;
    }

    void SetUp(ArcFrame f) {
        frame = f;
    }

    @Override
    public void Activate(ActionState act) {
        frame.Activate(act);
    }

    @Override
    public void InterActivate(MouseInterAction act) {
        frame.InterActivate(act);

    }

    @Override
    public void PaintGame(Painter p) {
        frame.PaintGame(p);
    }

    @Override
    public Game GetGame() {
        return this;
    }

    @Override
    public void Close() {
        frame.Close();
    }

    @Override
    public int GetWidth() {

        return this.width;
    }

    @Override
    public int GetHeight() {
        return this.height;
    }

    @Override
    public Graphical Graphic() {
        return frame.Graphic();
    }

    @Override
    public MultiKeyPressListener KeyBoard() {
        return frame.KeyBoard();
    }

    @Override
    public void Update(GameTime time) {
        frame.Update(time);
    }
}

@FunctionalInterface
abstract interface Settings {

    public abstract void SetUp();
}

@FunctionalInterface
abstract interface Active {

    public abstract Activator GetGame();
}

abstract class SetUp implements Active, Settings {

    @Override
    public abstract void SetUp();

    @Override
    public abstract Activator GetGame();
}

/**
 *
 * @author Gerwyn Jones
 */
public class ArcSetUp extends SetUp {

    Arc arc;
    ArcFrame frame;

    ArcSetUp(ArcEngine engine) {
        SetUp();
        engine.game = arc;
        TelescopeScreen telescreen = new TelescopeScreen(frame, 0, 0, frame.GetWidth(), frame.GetHeight(), "Telescope");
        arc.screens.add(new ScapeScreen(frame, 0, 0, frame.GetWidth(), frame.GetHeight(), "LandScape",telescreen.scope));
        //arc.screens.add(new ArcOptionScreen(frame, 0, 0, frame.GetWidth(), frame.GetHeight(), "Options"));
        arc.screens.add(telescreen);
        arc.currentScreen = 0;
        ArcEngine.DefaultGraphical = arc.Graphic();
    }

    @Override
    public void SetUp() {
        arc = new Arc(this);
        arc.SetUp(this);
        frame = new ArcFrame(this);
        arc.SetUp(frame);
        frame.setVisible(true);
    }

    @Override
    public Activator GetGame() {
        return arc;
    }

}
