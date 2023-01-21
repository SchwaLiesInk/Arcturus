/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 * STRUCTURE COORDS and seed feedback vector graphics data structure
 *
 * @author Gerwyn
 */
enum Mole {

    Line, Single, Calc
}

enum Gradiant {

    Add, Subtract, Calc
}

interface IRandom {

    double NextDouble();

    int Next(int prob);
}

class Random {

    static java.util.Random rnd = new java.util.Random();

    public static double NextDouble() {
        return rnd.nextDouble();
    }

    public static int Next(int prob) {
        return rnd.nextInt(prob);
    }

    public static float Next(float prob) {
        return (float) (rnd.nextDouble() * prob);
    }

    public static int d(int prob) {
        return 1 + rnd.nextInt(1 + prob);
    }

    public static int d(int n, int prob) {
        int dn = 0;
        for (int i = 0; i < n; i++) {
            dn += 1 + rnd.nextInt(1 + prob);
        }
        return dn;
    }

    public static int Next(int n, int to) {

        return n + rnd.nextInt(1 + to - n);
    }

    public static boolean NextBool() {
        return rnd.nextBoolean();
    }
}

class NTidePoint {

    int v[];
    NTidePoint chain = null;

    NTidePoint(int x, int y) {
        v = new int[]{x, y};
    }
}

public class DNA {

    protected int size;
    protected java.util.ArrayList<NTidePoint> helix;

    public DNA(int size) {
        this.size = 0;//size;
        helix = new java.util.ArrayList<>(size);

    }

    void Seed() {
        for (int i = 0; i < size; i++) {
            helix.add(new NTidePoint(Random.Next(4), Random.Next(4)));
        }
    }

    public int Size() {
        return size;
    }

    public void Add(NTidePoint add) {
        size++;
        helix.add(add);
    }

    public void Trim() {
        size--;
        helix.remove(size - 1);
    }

    public void Trim(int toSize) {
        int n = this.size - toSize;
        this.size = toSize;
        int at = this.size - 1;
        for (int i = 0; i < n; i++) {
            helix.remove(at);
        }
    }

    Gradiant ToGradiant(int i) {
        int g = ((NTidePoint) (helix.get(i))).v[0] + ((NTidePoint) (helix.get(i))).v[1];
        switch (g % 4) {
            case 0: {
                return Gradiant.Add;
            }
            case 1: {
                return Gradiant.Subtract;
            }
            case 2: {
                return Gradiant.Calc;
            }
            case 3: {
                return Gradiant.Add;
            }
        }
        return Gradiant.Add;
    }

    Mole ToMole(int i) {
        int g = ((NTidePoint) (helix.get(i))).v[0] + ((NTidePoint) (helix.get(i))).v[1];
        switch (g % 4) {
            case 0: {
                return Mole.Single;
            }
            case 1: {
                return Mole.Calc;
            }
            case 2: {
                return Mole.Line;
            }
            case 3: {
                return Mole.Single;
            }
        }
        return Mole.Single;
    }

    TurtleCommand ToTurtleCommand(int i) {
        int g = ((NTidePoint) (helix.get(i))).v[0] + ((NTidePoint) (helix.get(i))).v[1];
        switch (g % 8) {
            case 0: {
                return TurtleCommand.Forward;
            }
            case 1: {
                return TurtleCommand.Backward;
            }
            case 2: {
                return TurtleCommand.Clockwise;
            }
            case 3: {
                return TurtleCommand.AntiClockwise;
            }
            case 4: {
                return TurtleCommand.Reverse;
            }
            case 5: {
                return TurtleCommand.CalcTurn;
            }
            case 6: {
                return TurtleCommand.ChaosForward;
            }
            case 7: {
                return TurtleCommand.ChaosBackward;
            }
        }
        return TurtleCommand.Forward;
    }

    public int GetX(int i) {
        if (i >= size) {
            i = (i - size);
        }
        return ((NTidePoint) (helix.get(i))).v[0];
    }

    public int GetY(int i) {
        if (i >= size) {
            i = (i - size);
        }
        return ((NTidePoint) (helix.get(i))).v[1];
    }

    public int GetZ(int i) {
        if (i >= size) {
            i = (i - size);
        }
        return (((NTidePoint) (helix.get(i))).v[0] + ((NTidePoint) (helix.get(i))).v[1]) >> 1;
    }

    public int GetZ(int hx, int hy) {
        if (hx >= size) {
            hx = (hx - size);
        }
        if (hy >= size) {
            hy = (hy - size);
        }
        return (((NTidePoint) (helix.get(hx))).v[0] + ((NTidePoint) (helix.get(hy))).v[1]) >> 1;
    }
}
