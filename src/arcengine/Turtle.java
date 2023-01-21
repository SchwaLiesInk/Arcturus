/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Graphics;

enum TurtleCommand {

    GiveBirth, Die, Home,
    Grow, Shrink, NewLine,
    Forward, Backward, Clockwise, AntiClockwise, CalcTurn,
    Accelerate, Decelerate, PowerUp, PowerDown,
    Jump, Invert, Reverse, Insert, Rewrite,
    WriteRandomDirection, RandomDirection,
    //
    HalfForward, HalfBackward, ThirdForward, ThirdBackward,
    FourthForward, FourthBackward, FithForward, FithBackward, SixthForward, SixthBackward,
    SeventhForward, SeventhBackward, EighthForward, EighthBackward,
    NinethForward, NinethBackward, TenthForward, TenthBackward,
    ChaosForward, ChaosBackward,
    //
    HalfClockwise, HalfAntiClockwise, ThirdClockwise, ThirdAntiClockwise,
    FourthClockwise, FourthAntiClockwise, FithClockwise, FithAntiClockwise, SixthClockwise, SixthAntiClockwise,
    SeventhClockwise, SeventhAntiClockwise, EighthClockwise, EighthAntiClockwise,
    NinethClockwise, NinethAntiClockwise, TenthClockwise, TenthAntiClockwise,
    //
    RandomTurn, RandomClockwise, RandomAntiClockwise,
    RandomAverageTurn, RandomAverageClockwise, RandomAverageAntiClockwise,
    RandomDeviateClockwise, RandomDeviateAntiClockwise,
    RandomDistance, RandomBackward, RandomForward,
    RandomAverageDistance, RandomAverageForward, RandomAverageBackward,
    RandomDeviateDistance, RandomDeviateForward, RandomDeviateBackward,
    NotACommand,
}

/**
 *
 * @author Gerwyn
 */
public class Turtle extends Defined {

    boolean Move(Local offset) {
        return false;
    }

    void Paint(Graphics g, int x, int y, int tx, int ty) {
    }

    void MapPaint(Graphics g, int x, int y) {
    }

    void ScanPaint(Graphics g, int x, int y, int area) {
    }

    Color Colour() {
        return Color.BLACK;
    }

    static TurtleCommand[] Replace(TurtleCommand[] inside, TurtleCommand with, TurtleCommand[] replace) {
        TurtleCommand[] result = new TurtleCommand[inside.length];
        for (int i = 0; i < inside.length; i++) {
            boolean ok = true;
            for (int j = 0; j < replace.length; j++) {
                TurtleCommand use = replace[j];
                if (inside[i] == use) {
                    result[i] = with;
                    ok = false;
                    break;
                }
            }
            if (ok) {
                result[i] = inside[i];
            }
        }
        return result;
    }

    static TurtleCommand[] Insert(TurtleCommand[] inside, TurtleCommand insert, boolean before, TurtleCommand[] elements) {
        java.util.Vector<TurtleCommand> list = new java.util.Vector<>();
        list.addAll(Arrays.asList(insert));
        int at = 0;
        for (int i = 0; i < inside.length; i++, at++) {

            for (int j = 0; j < elements.length; j++) {
                TurtleCommand use = elements[j];
                if (inside[i] == use) {
                    if (before) {
                        list.insertElementAt(insert, at);
                        at++;
                    } else {
                        at++;
                        list.insertElementAt(insert, at);
                    }
                    break;
                }
            }

        }
        TurtleCommand[] coms = new TurtleCommand[list.size()];
        for (int i = 0; i < coms.length; i++) {
            coms[i] = (TurtleCommand) (list.get(i));
        }
        return coms;
    }

    static TurtleCommand[] Add(TurtleCommand[] outside, boolean infront, TurtleCommand[] add) {

        TurtleCommand[] result = new TurtleCommand[outside.length + add.length];
        if (infront) {
            System.arraycopy(add, 0, result, 0, add.length);
            for (int i = add.length; i < result.length; i++) {
                result[i] = outside[(i - add.length)];
            }
        } else {
            System.arraycopy(outside, 0, result, 0, outside.length);
            for (int i = outside.length; i < result.length; i++) {
                result[i] = add[(i - outside.length)];
            }

        }
        return result;
    }

    static TurtleCommand[] Scramble(TurtleCommand[] inside, TurtleCommand[] elements) {

        java.util.Vector<TurtleCommand> list = new java.util.Vector<>();
        list.addAll(Arrays.asList(inside));
        int at = 0;
        java.util.ArrayList<Integer> changes = new java.util.ArrayList<>();
        do {
            boolean changed = false;
            TurtleCommand com = (TurtleCommand) (list.get(at));
            for (int i = 0; i < changes.size(); i++) {
                int j = (int) changes.get(i);
                if (j == at) {
                    changed = true;
                    break;
                }
            }
            if (!changed) {
                for (int j = 0; j < elements.length; j++) {
                    TurtleCommand use = elements[j];
                    if (com == use) {
                        list.remove(at);
                        int to = (int) (list.size() * Random.NextDouble());
                        list.insertElementAt(com, to);
                        for (int i = 0; i < changes.size(); i++) {
                            int k = (int) (changes.get(i));
                            if (k >= to) {
                                if (k < at) {
                                    changes.set(i, k + 1);
                                }
                            } else {
                                if (k >= at) {
                                    changes.set(i, k - 1);
                                }
                            }
                        }
                        changes.add(to);
                        break;
                    }
                }
            }
            at++;
        } while (at < list.size());
        TurtleCommand[] coms = new TurtleCommand[list.size()];
        for (int i = 0; i < coms.length; i++) {
            coms[i] = (TurtleCommand) (list.get(i));
        }
        return coms;
    }

// <editor-fold defaultstate="collapsed" desc="Turtle Base Commands">
    static TurtleCommand[] TriAngle = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] VonKochCurve = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] VonKochSnowFlake = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] SawWave = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] LargeTree = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] SmallTree = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] SmallBranch = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] Square = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,};

    static TurtleCommand[] SquareCurve = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward, //
    };

    static TurtleCommand[] RectangleCurve = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward, //
    };

    static TurtleCommand[] RectangleGap = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise, //
    };

    static TurtleCommand[] SquareGap = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise, //
    };

    static TurtleCommand[] LongGap = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise, //
    };

    static TurtleCommand[] LargeGap = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise, //
    };
    static TurtleCommand[] Rectangle = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] SmallRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward, // Art.TurtleCommand.AntiClockwise, 
    //Art.TurtleCommand.Forward, 
    // Art.TurtleCommand.Clockwise, 
    //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] TRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] LargeRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward, //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] LargeTRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] HugeRectangularRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] HugeBoxRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, //Art.TurtleCommand.AntiClockwise, city
    };

    static TurtleCommand[] HugeSquareRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise, //Art.TurtleCommand.AntiClockwise, city
    };
    static TurtleCommand[] SmallGapSquare = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};

    static TurtleCommand[] SmallCorridor = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};
    static TurtleCommand[] Corridor = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};

    static TurtleCommand[] WideCorridor = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};

    static TurtleCommand[] LongCorridor = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};

    static TurtleCommand[] LongWideCorridor = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};
    static TurtleCommand[] SquareWave = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] Crucifix = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //right
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] SquareLoop = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,};

    static TurtleCommand[] OctLoop = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] OctWave = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] Infinity = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] Box = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,};
    static TurtleCommand[] OctSquare = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] Octagon = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] OctCurve = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] LargePod = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] LoopCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,//distance between loops
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] TriCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] ChrisCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] FracCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,};

    static TurtleCommand[] FlatOctChrisCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,};
    static TurtleCommand[] DiagonalOctChrisCross = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] ClosedCross = new TurtleCommand[]{
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //right
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] Cross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //right
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] WideCross = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //right
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] TShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        //TurtleCommand.Forward,//widen
        TurtleCommand.Forward,
        //right
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] LongTShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        //Art.TurtleCommand.Forward,//widen
        TurtleCommand.Forward,
        //right
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] VeryLongTShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        //TurtleCommand.Forward,//widen
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] VeryLargeTShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        //Art.TurtleCommand.Forward,//widen
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] LargeTShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        //TurtleCommand.Forward,//widen
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] YShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //middle
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] RectangleRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] InnerRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] LargeInnerRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] UShape = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,};
    static TurtleCommand[] NBox = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,};
    static TurtleCommand[] SmallInnerRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] SquareRoom = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        //left
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //top
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        //right
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise,
        TurtleCommand.Forward,
        //
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] MoleculaCurve1 = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise, TurtleCommand.WriteRandomDirection,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] MoleculaCurve2 = new TurtleCommand[]{
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise, TurtleCommand.WriteRandomDirection,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,};

    static TurtleCommand[] MoleculaCurve3 = new TurtleCommand[]{
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise, TurtleCommand.WriteRandomDirection,
        TurtleCommand.Forward,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};
    static TurtleCommand[] MoleculaCurve4 = new TurtleCommand[]{
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise, TurtleCommand.WriteRandomDirection,
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,};

    static TurtleCommand[] MoleculaCurve5 = new TurtleCommand[]{
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward,
        TurtleCommand.Clockwise, TurtleCommand.Clockwise, TurtleCommand.WriteRandomDirection,
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,
        TurtleCommand.AntiClockwise,
        TurtleCommand.Forward, TurtleCommand.WriteRandomDirection,};
    // </editor-fold>
    NTidePoint at;
    protected Adder x;
    protected Adder y;
    protected Adder turner;
    protected int move;
    protected int turn;
    protected int size;
    ///
    protected int birthRate = 0;
    protected int deathPoint = -1;
    protected int birthPoint = -1;
    protected int age = 0;
    protected boolean alive = true;
    protected boolean centred = true;
    protected boolean forwardX = true;
    protected boolean forwardY = true;
    //
    protected int growthRate;
    protected Adder growth;
    protected int birthAngle;
    protected int birthSize;

    protected Seed seed;
    protected Adder com;
    java.util.ArrayList<TurtleCommand> commands;
    java.util.ArrayList<Gradiant> gradiantCommands;
    java.util.ArrayList<Mole> moleCommands;
    NTidePoint birthLocation;
    protected DNA currentLine;

    Turtle(Seed seed, int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, TurtleCommand[] commands) {
        this.seed = seed;
        birthLocation = new NTidePoint(x, y);

        at = new NTidePoint(x, y);
        move = moveValue;
        this.size = size;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 0, 360, turnAngle);
        this.growth = null;
        this.growthRate = 0;
        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);
        this.x.subject = x;
        this.y.subject = y;

    }

    Turtle(Seed seed, int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, TurtleCommand[] commands) {
        this.seed = seed;
        birthLocation = new NTidePoint(x, y);
        at = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 0, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;

        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);
        this.x.subject = x;
        this.y.subject = y;

    }

    Turtle(Seed seed, int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, int birthRate, int lifeTime, TurtleCommand[] commands) {
        this.seed = seed;
        birthLocation = new NTidePoint(x, y);
        at = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 0, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;
        this.birthRate = birthRate;
        this.birthPoint = lifeTime;
        this.deathPoint = lifeTime;

        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);
        this.x.subject = x;
        this.y.subject = y;

    }

    Turtle(Seed seed, int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, int birthRate, int breedTime, int lifeTime, TurtleCommand[] commands) {
        this.seed = seed;
        birthLocation = new NTidePoint(x, y);
        at = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 0, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;
        this.birthRate = birthRate;
        this.birthPoint = breedTime;
        this.deathPoint = lifeTime;
        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);
        this.x.subject = x;
        this.y.subject = y;

    }

    Turtle(int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, TurtleCommand[] commands) {

        birthLocation = new NTidePoint(x, y);
        move = moveValue;
        this.size = size;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 1, 360, turnAngle);
        this.growth = null;
        this.growthRate = 0;
        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);

    }

    Turtle(int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, TurtleCommand[] commands) {

        birthLocation = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 1, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;

        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);

    }

    Turtle(int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, int birthRate, int lifeTime, TurtleCommand[] commands) {

        birthLocation = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 1, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;
        this.birthRate = birthRate;
        this.birthPoint = lifeTime;
        this.deathPoint = lifeTime;

        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);

    }

    Turtle(int x, int y, int width, int height, int moveValue, int startAngle, int turnAngle, int size, int growth, int growthRate, int birthRate, int breedTime, int lifeTime, TurtleCommand[] commands) {

        birthLocation = new NTidePoint(x, y);
        move = moveValue;
        if (growthRate == 0) {
            growthRate = 1;
        }
        this.size = size / growthRate;
        turn = turnAngle;
        turner = new Adder(startAngle % 360, 1, 360, turnAngle);
        this.growth = new Adder(size, growth * growthRate, 1);
        this.growthRate = growthRate;
        this.birthRate = birthRate;
        this.birthPoint = breedTime;
        this.deathPoint = lifeTime;
        this.currentLine = new DNA(2);
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com = new Adder(0, commands.length - 1, 1);
        this.gradiantCommands = new java.util.ArrayList<>();
        this.moleCommands = new java.util.ArrayList<>();
        this.x = new Adder(0, width - 1, 1);
        this.y = new Adder(0, height - 1, 1);

    }

    void OnNewLine() {

    }

    void OnBirth() {

    }

    void OnDeath() {

    }

    void NewLine() {
        this.currentLine = new DNA(2);
        this.currentLine.Add(at);
        this.OnNewLine();
    }

    public boolean Dead() {
        return !this.alive;
    }

    public boolean Alive() {
        return this.alive;
    }

    public int CommandCount() {
        return this.commands.size();
    }

    public TurtleCommand[] GetCommands() {
        Object[] common = this.commands.toArray();
        TurtleCommand[] coms = new TurtleCommand[common.length];
        for (int i = 0; i < common.length; i++) {
            coms[i] = (TurtleCommand) (common[i]);
        }
        return coms;
    }

    public void SetCommands(TurtleCommand[] commands) {
        this.commands = new java.util.ArrayList<>();
        this.commands.addAll(Arrays.asList(commands));
        this.com.to += commands.length;

    }

    public void AddCommands(TurtleCommand[] commands) {
        for (int i = 0; i < commands.length; i++) {
            TurtleCommand use = commands[i];
            if (use != TurtleCommand.Insert && use != TurtleCommand.Rewrite) {
                this.commands.add(use);
            }
        }
        this.com.to = this.commands.size() - 1;
    }

    //
    public void Move() {
        for (int i = 0; i < this.commands.size(); i++) {
            NextMove(Gradiant.Add);
        }
    }

    protected void OnMove(int startX, int startY, int endX, int endY) {

        if (this.alive) {
            this.currentLine.Add(new NTidePoint(endX, endY));
            this.age++;
            if (this.age == this.deathPoint) {
                this.alive = false;
                this.OnDeath();
                this.age = 0;
            }
            if (this.age == this.birthPoint) {
                this.OnBirth();
            }
        }
    }

    protected void Move(int m) {
        int xx = (int) (m * Math.cos(this.turner.subject / 57.295779513082320876798154814105));
        int yy = (int) (m * Math.sin(this.turner.subject / 57.295779513082320876798154814105));
        int sx = this.at.v[X];
        int sy = this.at.v[Y];
        x.number = xx;
        y.number = yy;
        x.Add();
        y.Add();
        this.at.v[X] = x.subject;
        this.at.v[Y] = y.subject;
        OnMove(sx, sy, this.at.v[X], this.at.v[Y]);
    }

    protected void Move(int m, boolean forward) {
        int xx = (int) (m * Math.cos(this.turner.subject / 57.295779513082320876798154814105));
        int yy = (int) (m * Math.sin(this.turner.subject / 57.295779513082320876798154814105));
        int sx = this.at.v[X];
        int sy = this.at.v[Y];
        x.number = xx;
        y.number = yy;
        if (forward) {
            this.x.Add();
            this.y.Add();
        } else {
            this.x.Subtract();
            this.y.Subtract();

        }
        this.at.v[X] = x.subject;
        this.at.v[Y] = y.subject;
        OnMove(sx, sy, this.at.v[X], this.at.v[Y]);
    }

    protected void MoveChaos(int m) {
        int mx = m;
        int my = m;
        if (this.forwardX) {
            mx = -m;
        }
        if (this.forwardY) {
            my = -m;
        }
        int xx = (int) (m * Math.cos(this.turner.subject / 57.295779513082320876798154814105));
        int yy = (int) (m * Math.sin(this.turner.subject / 57.295779513082320876798154814105));
        int sx = this.at.v[X];
        int sy = this.at.v[Y];
        if (this.x.number == -xx) {
            this.x.number = 0;
            this.forwardX = !this.forwardX;
        }
        if (this.y.number == -yy) {
            this.y.number = 0;
            this.forwardY = !this.forwardY;
        }
        x.number = xx;
        y.number = yy;
        x.Add();
        y.Add();
        this.at.v[X] = x.subject;
        this.at.v[Y] = y.subject;
        OnMove(sx, sy, this.at.v[X], this.at.v[Y]);
    }

    public void MoveTurtle(Gradiant move, int times) {
        for (int i = 0; i < times; i++) {
            NextMove(move);
        }
    }

    protected void NextCommand(Gradiant move) {
        switch (move) {
            case Add: {
                this.com.Add();
                break;
            }
            case Calc: {
                this.com.Calc();
                break;
            }
            case Subtract: {
                this.com.Subtract();
                break;
            }
        }
    }

    public TurtleCommand GetCommand() {
        return (TurtleCommand) this.commands.get(this.com.subject);
    }

    public void SetCommand(TurtleCommand value) {
        this.commands.set(this.com.subject, value);

    }

    protected void NextMove(Gradiant move) {
        TurtleCommand at = this.GetCommand();
        int jump = 0;
        do {
            if (at == TurtleCommand.Insert || at == TurtleCommand.Rewrite) {
                this.commands.remove(this.com.subject);
                this.com.to--;
            } else {
                if (at != TurtleCommand.Jump) {
                    NextMove(at);
                } else {
                    this.commands.remove(this.com.subject);
                    this.com.to--;
                    at = this.GetCommand();
                    jump++;
                }
            }
            jump--;
        } while (jump >= 0);
        this.NextCommand(move);
    }

    protected void Turn(Gradiant turn) {
        switch (turn) {
            case Add: {
                this.turner.Add();
                break;
            }
            case Calc: {
                this.turner.Calc();
                break;
            }

            case Subtract: {
                this.turner.Subtract();
                break;
            }
        }
    }

    protected void Turn(int deg) {
        this.turner.number = deg;
        this.turner.Add();
    }

    protected void Turn(int deg, boolean clockwise) {
        if (clockwise) {
            this.turner.number = deg;
            this.turner.Add();
        } else {
            this.turner.number = deg;
            this.turner.Subtract();
        }
    }

    private void NextMove(TurtleCommand at) {

        switch (at) {

            case NewLine: {
                this.NewLine();
                break;
            }
            case GiveBirth: {
                this.OnBirth();
                break;
            }
            case Die: {
                this.alive = false;
                this.OnDeath();
                break;
            }
            case Home: {
                this.at.v[X] = this.birthLocation.v[X];
                this.at.v[Y] = this.birthLocation.v[Y];
                break;
            }

            case Grow: {
                this.size *= this.growthRate;
                break;
            }

            case Shrink: {
                this.size *= this.birthSize;
                break;
            }
            case PowerUp: {
                this.move += this.move;
                break;
            }
            case PowerDown: {
                this.move = this.move >> 1;
                break;
            }
            case Accelerate: {
                this.move++;
                break;
            }
            case Decelerate: {
                this.move--;
                break;
            }
            case Invert: {
                this.turner.number = -this.turner.number;
                break;
            }
            case Reverse: {
                this.move = -this.move;
                break;
            }
            case Forward: {
                this.Move(this.move);
                break;
            }
            case Backward: {
                this.Move(-this.move);
                break;
            }
            case HalfForward: {
                this.Move(this.move >> 1);
                break;
            }
            case HalfBackward: {
                this.Move(-(this.move >> 1));
                break;
            }

            case ThirdForward: {
                this.Move(this.move / 3);
                break;
            }
            case ThirdBackward: {
                this.Move(-this.move / 3);
                break;
            }
            case FourthForward: {
                this.Move(this.move >> 2);
                break;
            }
            case FourthBackward: {
                this.Move(-(this.move >> 2));
                break;
            }
            case FithForward: {
                this.Move(this.move / 5);
                break;
            }
            case FithBackward: {
                this.Move(-this.move / 5);
                break;
            }
            case SixthForward: {
                this.Move(this.move / 6);
                break;
            }
            case SixthBackward: {
                this.Move(-this.move / 6);
                break;
            }
            case SeventhForward: {
                this.Move(this.move / 7);
                break;
            }
            case SeventhBackward: {
                this.Move(-this.move / 7);
                break;
            }
            case EighthForward: {
                this.Move(this.move >> 3);
                break;
            }
            case EighthBackward: {
                this.Move(-(this.move >> 3));
                break;
            }
            case NinethForward: {
                this.Move(this.move / 9);
                break;
            }
            case NinethBackward: {
                this.Move(-this.move / 9);
                break;
            }
            case TenthForward: {
                this.Move(this.move / 10);
                break;
            }
            case TenthBackward: {
                this.Move(-this.move / 10);
                break;
            }
            case ChaosForward: {
                this.MoveChaos(this.move);
                break;
            }
            case ChaosBackward: {
                this.MoveChaos(-this.move);
                break;
            }
            case CalcTurn: {
                this.Turn(Gradiant.Calc);
                break;
            }
            case Clockwise: {
                this.Turn(Gradiant.Add);
                break;
            }
            case AntiClockwise: {

                this.Turn(Gradiant.Subtract);
                break;
            }

            case HalfClockwise: {
                this.Turn(this.turn / 2);
                break;
            }
            case HalfAntiClockwise: {

                this.Turn(-this.turn / 2);
                break;
            }

            case ThirdClockwise: {
                this.Turn(this.turn / 3);
                break;
            }
            case ThirdAntiClockwise: {

                this.Turn(-this.turn / 3);
                break;
            }

            case FourthClockwise: {
                this.Turn(this.turn / 4);
                break;
            }
            case FourthAntiClockwise: {

                this.Turn(-this.turn / 4);
                break;
            }

            case FithClockwise: {
                this.Turn(this.turn / 5);
                break;
            }
            case FithAntiClockwise: {

                this.Turn(-this.turn / 5);
                break;
            }

            case SixthClockwise: {
                this.Turn(this.turn / 6);
                break;
            }
            case SixthAntiClockwise: {

                this.Turn(-this.turn / 6);
                break;
            }

            case SeventhClockwise: {
                this.Turn(this.turn / 7);
                break;
            }
            case SeventhAntiClockwise: {

                this.Turn(-this.turn / 7);
                break;
            }

            case EighthClockwise: {
                this.Turn(this.turn / 8);
                break;
            }
            case EighthAntiClockwise: {

                this.Turn(-this.turn / 8);
                break;
            }
            case NinethClockwise: {
                this.Turn(this.turn / 9);
                break;
            }
            case NinethAntiClockwise: {

                this.Turn(-this.turn / 9);
                break;
            }

            case TenthClockwise: {
                this.Turn(this.turn / 10);
                break;
            }
            case TenthAntiClockwise: {

                this.Turn(-this.turn / 10);
                break;
            }
            case RandomClockwise: {
                this.Turn((int) (this.turn * Random.NextDouble()));
                break;
            }
            case RandomAntiClockwise: {

                this.Turn(-(int) (this.turn * Random.NextDouble()));
                break;
            }
            case RandomAverageClockwise: {
                double a = this.turn / 2;

                this.Turn((int) (this.turn + (a * Random.NextDouble()) - (a * Random.NextDouble())));
                break;
            }
            case RandomAverageAntiClockwise: {

                double a = this.turn / 2;

                this.Turn(-(int) (this.turn + (a * Random.NextDouble()) - (a * Random.NextDouble())));
                break;
            }

            case RandomDeviateClockwise: {
                double a = this.turn / 3;

                this.Turn((int) (this.turn + (a * Random.NextDouble()) - (a * Random.NextDouble())));
                break;
            }
            case RandomDeviateAntiClockwise: {

                double a = this.turn / 3;

                this.Turn(-(int) (this.turn + (a * Random.NextDouble()) - (a * Random.NextDouble())));
                break;
            }
            case RandomTurn: {

                this.Turn((int) (this.turn * Random.NextDouble() - this.turn * Random.NextDouble()));

                break;
            }
            case RandomDirection: {
                boolean cw = Random.NextBool();
                if (cw) {
                    this.Turn(this.turn);
                } else {
                    this.Turn(-this.turn);
                }
                break;
            }
            //
            case RandomDistance: {
                this.Move((int) ((this.move * Random.NextDouble()) - (this.move * Random.NextDouble())));
                break;
            }
            case RandomAverageDistance: {
                double m = this.move / 2;
                this.Move((int) ((this.move * Random.NextDouble()) + (m * Random.NextDouble()) - (m * Random.NextDouble()) + (m * Random.NextDouble())));
                break;
            }

            case RandomDeviateDistance: {
                double m = this.move / 3;
                this.Move((int) ((this.move * Random.NextDouble()) + (m * Random.NextDouble()) - (m * Random.NextDouble()) + (m * Random.NextDouble())));
                break;
            }
            case RandomForward: {
                this.Move((int) (this.move * Random.NextDouble()));
                break;
            }
            case RandomBackward: {
                this.Move(-(int) (this.move * Random.NextDouble()));
                break;
            }
            case RandomAverageForward: {
                double m = this.move / 2;
                this.Move((int) (this.move + (m * Random.NextDouble()) - (m * Random.NextDouble())));

                break;
            }
            case RandomAverageBackward: {

                double m = this.move / 2;
                this.Move(-(int) (this.move + (m * Random.NextDouble()) - (m * Random.NextDouble())));
                break;
            }

            case RandomDeviateForward: {
                double m = this.move / 3;
                this.Move((int) (this.move + (m * Random.NextDouble()) - (m * Random.NextDouble())));

                break;
            }
            case RandomDeviateBackward: {

                double m = this.move / 3;
                this.Move(-(int) (this.move + (m * Random.NextDouble()) - (m * Random.NextDouble())));
                break;
            }
        }
    }
}
