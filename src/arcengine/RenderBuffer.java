/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn Jones
 */
interface Render3D {

    public abstract void Draw3D();

    public abstract void Begin();

    public abstract void End();
}

enum RenderState {

    Ready, Processing, Waiting
}

public class RenderBuffer implements Action, Render3D, Runnable, ActionReset {

    Drawable[] processList;
    Drawable[] nextList;
    Drawable[] pastList;

    RenderState proState;
    RenderState sortState;
    Screen screen;
    Action render;

    @Override
    public void active() {
        if (render != null) {
            Game.exec.execute(render::active);
        }
    }

    boolean Ready() {
        return (screen.drawing == false && sortState == RenderState.Ready && proState == RenderState.Waiting);
    }

    RenderBuffer(Screen scr) {
        this.screen = scr;
        this.proState = RenderState.Waiting;
        this.sortState = RenderState.Waiting;
    }

    @Override
    public void Draw3D() {

        //System.out.println("Sort "+sortState);
        //System.out.println("Process "+proState);
        if (sortState == RenderState.Waiting) {
            //if(nextList!=null){
            //System.out.println("Sort "+sortState+" "+nextList.length);

            pastList = processList;
            sortState = RenderState.Ready;
            //}
            if (processList != null) {
                //System.out.println("Process "+proState +" "+processList.length);
                screen.drawing = true;
                screen.DrawSortedList(pastList, screen.graphical);
                screen.drawing = false;
            }

        } else {

            if (pastList != null) {
                screen.drawing = true;
                screen.DrawSortedList(pastList, screen.graphical);
                screen.drawing = false;
            }
        }

    }

    @Override
    public void Begin() {
        if (sortState == RenderState.Ready && proState == RenderState.Waiting) {
            proState = RenderState.Ready;

            run();
        }
    }

    @Override
    public void End() {
        if (sortState == RenderState.Waiting) {

            screen.End();
            this.processList = this.nextList;
            this.nextList = screen.sortedList;
            //System.gc();
        }

    }

    @Override
    public void run() {
        if (proState == RenderState.Ready) {
            proState = RenderState.Processing;
            sortState = RenderState.Processing;

            screen.ZSort();

            sortState = RenderState.Waiting;
            proState = RenderState.Waiting;
        }
    }

    @Override
    public void reset() {

        sortState = RenderState.Waiting;
        proState = RenderState.Waiting;
    }
}
