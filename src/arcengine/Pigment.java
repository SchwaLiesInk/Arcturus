/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerwyn
 */
abstract class Defined {

    public static byte X = 0;
    public static byte Y = 1;
    public static byte Z = 2;
    public static byte W = 3;
    public static byte A = 0;
    public static byte R = 1;
    public static byte G = 2;
    public static byte B = 3;
    public static byte SHIFT = 8;
    public static double Third = 1.0 / 3.0;

    //
    abstract boolean Move(Local offset);

    abstract void Paint(Graphics g, int x, int y, int tx, int ty);

    abstract void MapPaint(Graphics g, int x, int y);

    abstract void ScanPaint(Graphics g, int x, int y, int area);

    abstract Color Colour();

}

public class Pigment extends Defined implements ToFile,FromFile {

    int v[];

    Color Color() {
        return new Color(v[R], v[G], v[B], v[A]);
    }
    void Clamp(){
        if (v[A] > 255) {
            v[A] = 255;
        }else
        if (v[A]< 0) {
            v[A] = 0;
        }
        if (v[R] > 255) {
            v[R] = 255;
        }else
        if (v[R] < 0) {
            v[R] = 0;
        }
        if (v[G] > 255) {
            v[G] = 255;
        }else
        if (v[G] < 0) {
            v[G] = 0;
        }
        if (v[B] > 255) {
            v[B] = 255;
        }else
        if (v[B] < 0) {
            v[B] = 0;
        }
    }
    public Pigment() {
        v = new int[4];
    }

    public Pigment(int argb) {
        v = new int[4];
        v[0] = (argb >> 24) & 255;
        v[1] = (argb >> 16) & 255;
        v[2] = (argb >> 8) & 255;
        v[3] = (argb) & 255;
    }

    public Pigment(int a, int r, int g, int b) {
        v = new int[4];
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    public Pigment(int r, int g, int b) {
        v = new int[4];
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = 255;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    public Pigment(Pigment copy) {
        v = new int[4];
        v[A] = copy.v[A];
        v[R] = copy.v[R];
        v[G] = copy.v[G];
        v[B] = copy.v[B];
    }

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
        return new Color(v[R], v[G], v[B], v[A]);
    }

    public String GetString() {
        return "Pigment A:" + v[A] + " R:" + v[R] + " G:" + v[G] + " B:" + v[B];
    }

    public static Pigment RandomPigment(int a, int r, int g, int b) {
        Pigment p = new Pigment();
        p.v[A] = (byte) (a * Random.NextDouble());
        p.v[R] = (byte) (r * Random.NextDouble());
        p.v[G] = (byte) (g * Random.NextDouble());
        p.v[B] = (byte) (b * Random.NextDouble());
        return p;
    }

    static Pigment[] GradiantLine(Adder a, Adder r, Adder g, Adder b, int step, Gradiant aGrade, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int length) {
        Pigment p[] = new Pigment[length];
        int a1 = 0;
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < length; i++) {
            int s = 0;
            if (s <= 0) {
                switch (aGrade) {
                    case Add: {
                        a1 = a.Add();
                        break;
                    }
                    case Subtract: {
                        a1 = a.Subtract();
                        break;
                    }
                    case Calc: {
                        a1 = a.Calc();
                        break;
                    }
                }
                switch (rGrade) {
                    case Add: {
                        r1 = r.Add();
                        break;
                    }
                    case Subtract: {
                        r1 = r.Subtract();
                        break;
                    }
                    case Calc: {
                        r1 = r.Calc();
                        break;
                    }
                }
                switch (gGrade) {
                    case Add: {
                        g1 = g.Add();
                        break;
                    }
                    case Subtract: {
                        g1 = g.Subtract();
                        break;
                    }
                    case Calc: {
                        g1 = g.Calc();
                        break;
                    }
                }
                switch (bGrade) {
                    case Add: {
                        b1 = b.Add();
                        break;
                    }
                    case Subtract: {
                        b1 = b.Subtract();
                        break;
                    }
                    case Calc: {
                        b1 = b.Calc();
                        break;
                    }
                }
                s = step;
            }
            s--;
            p[i] = new Pigment(a1, r1, g1, b1);
        }
        return p;
    }

    static Pigment[] GradiantLine(int alpha, Adder r, Adder g, Adder b, int step, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int length) {
        Pigment p[] = new Pigment[length];
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < length; i++) {
            int s = 0;
            if (s <= 0) {
                switch (rGrade) {
                    case Add: {
                        r1 = r.Add();
                        break;
                    }
                    case Subtract: {
                        r1 = r.Subtract();
                        break;
                    }
                    case Calc: {
                        r1 = r.Calc();
                        break;
                    }
                }
                switch (gGrade) {
                    case Add: {
                        g1 = g.Add();
                        break;
                    }
                    case Subtract: {
                        g1 = g.Subtract();
                        break;
                    }
                    case Calc: {
                        g1 = g.Calc();
                        break;
                    }
                }
                switch (bGrade) {
                    case Add: {
                        b1 = b.Add();
                        break;
                    }
                    case Subtract: {
                        b1 = b.Subtract();
                        break;
                    }
                    case Calc: {
                        b1 = b.Calc();
                        break;
                    }
                }
                s = step;
            }
            s--;
            p[i] = new Pigment(alpha, r1, g1, b1);
        }
        return p;
    }

    static Pigment[][] Gradiant(int alpha, Adder r, Adder g, Adder b, int step, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int width, int height) {
        Pigment p[][] = new Pigment[width][height];
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < width; i++) {
            int s = 0;
            if (s <= 0) {
                switch (rGrade) {
                    case Add: {
                        r1 = r.Add();
                        break;
                    }
                    case Subtract: {
                        r1 = r.Subtract();
                        break;
                    }
                    case Calc: {
                        r1 = r.Calc();
                        break;
                    }
                }
                switch (gGrade) {
                    case Add: {
                        g1 = g.Add();
                        break;
                    }
                    case Subtract: {
                        g1 = g.Subtract();
                        break;
                    }
                    case Calc: {
                        g1 = g.Calc();
                        break;
                    }
                }
                switch (bGrade) {
                    case Add: {
                        b1 = b.Add();
                        break;
                    }
                    case Subtract: {
                        b1 = b.Subtract();
                        break;
                    }
                    case Calc: {
                        b1 = b.Calc();
                        break;
                    }
                }
                s = step;
            }
            s--;
            for (int j = 0; j < height; j++) {
                p[i][j] = new Pigment(alpha, r1, g1, b1);
            }
        }
        return p;
    }

    static Pigment[][] Gradiant(Adder a, Adder r, Adder g, Adder b, int step, Gradiant aGrade, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int width, int height) {
        Pigment p[][] = new Pigment[width][height];
        int a1 = 0;
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < width; i++) {
            int s = 0;
            if (s <= 0) {
                switch (aGrade) {
                    case Add: {
                        a1 = a.Add();
                        break;
                    }
                    case Subtract: {
                        a1 = a.Subtract();
                        break;
                    }
                    case Calc: {
                        a1 = a.Calc();
                        break;
                    }
                }
                switch (rGrade) {
                    case Add: {
                        r1 = r.Add();
                        break;
                    }
                    case Subtract: {
                        r1 = r.Subtract();
                        break;
                    }
                    case Calc: {
                        r1 = r.Calc();
                        break;
                    }
                }
                switch (gGrade) {
                    case Add: {
                        g1 = g.Add();
                        break;
                    }
                    case Subtract: {
                        g1 = g.Subtract();
                        break;
                    }
                    case Calc: {
                        g1 = g.Calc();
                        break;
                    }
                }
                switch (bGrade) {
                    case Add: {
                        b1 = b.Add();
                        break;
                    }
                    case Subtract: {
                        b1 = b.Subtract();
                        break;
                    }
                    case Calc: {
                        b1 = b.Calc();
                        break;
                    }
                }
                s = step;
            }
            s--;
            for (int j = 0; j < height; j++) {
                p[i][j] = new Pigment(a1, r1, g1, b1);
            }
        }
        return p;
    }

    static Pigment[][] GradiantPattern(Adder a, Adder r, Adder g, Adder b, int step, Gradiant aGrade, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int width, int height) {
        Pigment p[][] = new Pigment[width][height];
        int a1 = 0;
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < width; i++) {
            int s = 0;

            for (int j = 0; j < height; j++) {
                if (s <= 0) {
                    switch (aGrade) {
                        case Add: {
                            a1 = a.Add();
                            break;
                        }
                        case Subtract: {
                            a1 = a.Subtract();
                            break;
                        }
                        case Calc: {
                            a1 = a.Calc();
                            break;
                        }
                    }
                    switch (rGrade) {
                        case Add: {
                            r1 = r.Add();
                            break;
                        }
                        case Subtract: {
                            r1 = r.Subtract();
                            break;
                        }
                        case Calc: {
                            r1 = r.Calc();
                            break;
                        }
                    }
                    switch (gGrade) {
                        case Add: {
                            g1 = g.Add();
                            break;
                        }
                        case Subtract: {
                            g1 = g.Subtract();
                            break;
                        }
                        case Calc: {
                            g1 = g.Calc();
                            break;
                        }
                    }
                    switch (bGrade) {
                        case Add: {
                            b1 = b.Add();
                            break;
                        }
                        case Subtract: {
                            b1 = b.Subtract();
                            break;
                        }
                        case Calc: {
                            b1 = b.Calc();
                            break;
                        }
                    }
                    s = step;
                }
                s--;
                p[i][j] = new Pigment(a1, r1, g1, b1);
            }
        }
        return p;
    }

    static Pigment[][] GradiantPattern(int alpha, Adder r, Adder g, Adder b, int step, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade, int width, int height) {
        Pigment p[][] = new Pigment[width][height];
        int r1 = 0;
        int g1 = 0;
        int b1 = 0;
        for (int i = 0; i < width; i++) {
            int s = 0;
            for (int j = 0; j < height; j++) {
                if (s <= 0) {
                    switch (rGrade) {
                        case Add: {
                            r1 = r.Add();
                            break;
                        }
                        case Subtract: {
                            r1 = r.Subtract();
                            break;
                        }
                        case Calc: {
                            r1 = r.Calc();
                            break;
                        }
                    }
                    switch (gGrade) {
                        case Add: {
                            g1 = g.Add();
                            break;
                        }
                        case Subtract: {
                            g1 = g.Subtract();
                            break;
                        }
                        case Calc: {
                            g1 = g.Calc();
                            break;
                        }
                    }
                    switch (bGrade) {
                        case Add: {
                            b1 = b.Add();
                            break;
                        }
                        case Subtract: {
                            b1 = b.Subtract();
                            break;
                        }
                        case Calc: {
                            b1 = b.Calc();
                            break;
                        }
                    }
                    s = step;
                }
                s--;
                p[i][j] = new Pigment(alpha, r1, g1, b1);
            }
        }
        return p;
    }

    public int Alpha() {
        return v[A];
    }

    public int Red() {
        return v[R];
    }

    public int Green() {
        return v[G];
    }

    public int Blue() {
        return v[B];
    }

    public void Alpha(int a) {
        if (a > 255) {
            a = 255;
        }
        if (a < 0) {
            a = 0;
        }
        v[A] = a;
    }

    public void Red(int r) {
        if (r > 255) {
            r = 255;
        }
        if (r < 0) {
            r = 0;
        }
        v[R] = r;
    }

    public void Green(int g) {
        if (g > 255) {
            g = 255;
        }
        if (g < 0) {
            g = 0;
        }
        v[G] = g;
    }

    public void Blue(int b) {
        if (b > 255) {
            b = 255;
        }
        if (b < 0) {
            b = 0;
        }
        v[B] = b;
    }

    void Add(Pigment p) {
        int a = v[A] + p.v[A] + p.v[A];
        int r = v[R] + p.v[R] + p.v[R];
        int g = v[G] + p.v[G] + p.v[G];
        int b = v[B] + p.v[B] + p.v[B];
        a >>= 1;
        r >>= 1;
        g >>= 1;
        b >>= 1;
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Add(int alpha, int light) {
        int a = v[A] + alpha;
        int r = v[R] + light;
        int g = v[G] + light;
        int b = v[B] + light;
        a >>= 1;
        r >>= 1;
        g >>= 1;
        b >>= 1;
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Negative(Pigment p) {
        int a = ((int) (v[A]) + (int) (p.v[A])) >> 1;
        int r = ((int) (v[R]) + (int) (p.v[R])) >> 1;
        int g = ((int) (v[G]) + (int) (p.v[G])) >> 1;
        int b = ((int) (v[B]) + (int) (p.v[B])) >> 1;
        v[A] = a;
        v[R] = 255 - r;
        v[G] = 255 - g;
        v[B] = 255 - b;
    }

    void PixelEffect(Pigment p, PixelEffect effect) {

        switch (effect) {
            case Add: {
                this.Add(p);
                break;
            }
            case Average: {
                this.Average(p);
                break;
            }
            case Subtract: {
                this.Subtract(p);
                break;
            }
            case Multiply: {
                this.Multiply(p);
                break;
            }
            case Divide: {
                this.Divide(p);
                break;
            }
            case Tint: {
                this.Tint(p);
                break;
            }
            case Hint: {
                this.Hint(p);
                break;
            }
            case Alpha: {
                this.Alpha(p);
                break;
            }
            case Negative: {
                this.Negative(p);
                break;
            }
        }
    }

    void NegativeEffect(Pigment p, PixelEffect effect) {
        ///
        switch (effect) {
            case Add: {
                this.Add(p);
                break;
            }
            case Average: {
                this.Average(p);
                break;
            }
            case Subtract: {
                this.Subtract(p);
                break;
            }
            case Multiply: {
                this.Multiply(p);
                break;
            }
            case Divide: {
                this.Divide(p);
                break;
            }
            case Tint: {
                this.Tint(p);
                break;
            }
            case Hint: {
                this.Hint(p);
                break;
            }
            case Alpha: {
                this.Alpha(p);
                break;
            }
            case Negative: {
                this.Negative(p);
                break;
            }
        }
        int a = (int) (v[A]);
        int r = (int) (v[R]);
        int g = (int) (v[G]);
        int b = (int) (v[B]);
        v[A] = a;
        v[R] = 255 - r;
        v[G] = 255 - g;
        v[B] = 255 - b;
    }

    void Average(Pigment p) {
        int a = ((int) (v[A]) + (int) (p.v[A])) >> 1;
        int r = ((int) (v[R]) + (int) (p.v[R])) >> 1;
        int g = ((int) (v[G]) + (int) (p.v[G])) >> 1;
        int b = ((int) (v[B]) + (int) (p.v[B])) >> 1;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void AND(Pigment p) {
        int a = ((int) (v[A]) & (int) (p.v[A])) >> 1;
        int r = ((int) (v[R]) & (int) (p.v[R])) >> 1;
        int g = ((int) (v[G]) & (int) (p.v[G])) >> 1;
        int b = ((int) (v[B]) & (int) (p.v[B])) >> 1;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void OR(Pigment p) {
        int a = ((int) (v[A]) | (int) (p.v[A])) >> 1;
        int r = ((int) (v[R]) | (int) (p.v[R])) >> 1;
        int g = ((int) (v[G]) | (int) (p.v[G])) >> 1;
        int b = ((int) (v[B]) | (int) (p.v[B])) >> 1;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void XOR(Pigment p) {
        int a = ((int) (v[A]) ^ (int) (p.v[A])) >> 1;
        int r = ((int) (v[R]) ^ (int) (p.v[R])) >> 1;
        int g = ((int) (v[G]) ^ (int) (p.v[G])) >> 1;
        int b = ((int) (v[B]) ^ (int) (p.v[B])) >> 1;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Tint(Pigment p) {
        int a = ((int) (v[A] + v[A] + v[A]) + (int) (p.v[A])) >> 2;
        int r = ((int) (v[R] + v[R] + v[R]) + (int) (p.v[R])) >> 2;
        int g = ((int) (v[G] + v[G] + v[G]) + (int) (p.v[G])) >> 2;
        int b = ((int) (v[B] + v[B] + v[B]) + (int) (p.v[B])) >> 2;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Hint(Pigment p) {
        int a = ((int) (v[A] + v[A] + v[A] + v[A] + v[A] + v[A] + v[A]) + (int) (p.v[A])) >> 3;
        int r = ((int) (v[R] + v[R] + v[R] + v[R] + v[R] + v[R] + v[R]) + (int) (p.v[R])) >> 3;
        int g = ((int) (v[G] + v[G] + v[G] + v[G] + v[G] + v[G] + v[G]) + (int) (p.v[G])) >> 3;
        int b = ((int) (v[B] + v[B] + v[B] + v[B] + v[B] + v[B] + v[B]) + (int) (p.v[B])) >> 3;
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Average(Pigment p1, Pigment p2, Pigment p3) {
        int a = ((int) (v[A]) + (int) (p1.v[A]) + (int) (p2.v[A]) + (int) (p3.v[A])) >> 2;
        int r = ((int) (v[R]) + (int) (p1.v[R]) + (int) (p2.v[R]) + (int) (p3.v[R])) >> 2;
        int g = ((int) (v[G]) + (int) (p1.v[G]) + (int) (p2.v[G]) + (int) (p3.v[G])) >> 2;
        int b = ((int) (v[B]) + (int) (p1.v[B]) + (int) (p2.v[B]) + (int) (p3.v[B])) >> 2;

        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Average(int alpha, int shade) {
        int a = ((int) (v[A]) + (int) (alpha)) >> 1;
        int r = ((int) (v[R]) + (int) (shade)) >> 1;
        int g = ((int) (v[G]) + (int) (shade)) >> 1;
        int b = ((int) (v[B]) + (int) (shade)) >> 1;

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Alpha(Pigment p) {

        int a0 = p.v[A];//
        int r0 = p.v[R];//
        int g0 = p.v[G];//;
        int b0 = p.v[B];//
        //
        int alpha = 255 - a0;
        int r = (((a0 * r0) >> 7) + ((alpha * v[R]) >> 7)) >> 1;
        int g = (((a0 * g0) >> 7) + ((alpha * v[G]) >> 7)) >> 1;
        int b = (((a0 * b0) >> 7) + ((alpha * v[B]) >> 7)) >> 1;
        int a = (((a0 * a0) >> 7) + ((alpha * v[A]) >> 7)) >> 1;//(a0+v[A])>>1;
        //
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Alpha(Pigment p, int scale) {

        int a0 = p.v[A];//
        int r0 = p.v[R];//
        int g0 = p.v[G];//;
        int b0 = p.v[B];//
        //
        int alpha = 255 - a0;
        int r = (((a0 * r0) >> 7) + ((alpha * v[R]) >> 7)) >> 1;
        int g = (((a0 * g0) >> 7) + ((alpha * v[G]) >> 7)) >> 1;
        int b = (((a0 * b0) >> 7) + ((alpha * v[B]) >> 7)) >> 1;
        //
        v[A] = scale;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Multiply(Pigment p) {
        int a = ((int) (v[A]) + (int) (p.v[A])) >> 1;
        int r = (int) (((((double) (v[R]) / 255.0) * (double) (p.v[R]) / 255.0)) * 255.0);
        int g = (int) (((((double) (v[G]) / 255.0) * (double) (p.v[G]) / 255.0)) * 255.0);
        int b = (int) (((((double) (v[B]) / 255.0) * (double) (p.v[B]) / 255.0)) * 255.0);
        r = (v[R] + v[R] + v[R] + r) >> 2;
        g = (v[G] + v[G] + v[G] + g) >> 2;
        b = (v[B] + v[B] + v[B] + b) >> 2;
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Multiply(int alpha, int brightness) {
        int a = ((int) (v[A]) + (int) (alpha)) >> 1;
        int r = ((int) (v[R]) * (int) (brightness)) >> SHIFT;
        int g = ((int) (v[G]) * (int) (brightness)) >> SHIFT;
        int b = ((int) (v[B]) * (int) (brightness)) >> SHIFT;

        r = (v[R] + v[R] + v[R] + r) >> 2;
        g = (v[G] + v[G] + v[G] + g) >> 2;
        b = (v[B] + v[B] + v[B] + b) >> 2;
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Colorize(Pigment p, int scale) {
        int s = scale + 1;
        int a = ((int) (v[A]) + (int) (p.v[A] * scale)) / (s);
        int r = ((int) (v[R]) + (int) (p.v[R] * scale)) / (s);
        int g = ((int) (v[G]) + (int) (p.v[G] * scale)) / (s);
        int b = ((int) (v[B]) + (int) (p.v[B] * scale)) / (s);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Shade(Pigment p, int scale) {
        int s = scale + 1;
        int a = ((int) (v[A]) * scale + (int) (p.v[A])) / (s);
        int r = ((int) (v[R]) * scale + (int) (p.v[R])) / (s);
        int g = ((int) (v[G]) * scale + (int) (p.v[G])) / (s);
        int b = ((int) (v[B]) * scale + (int) (p.v[B])) / (s);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Darken(Pigment p, int scale) {
        int s = scale + 2;
        int a = ((int) (v[A]) * scale + (int) (p.v[A])) / (s);
        int r = ((int) (v[R]) * scale + (int) (p.v[R])) / (s);
        int g = ((int) (v[G]) * scale + (int) (p.v[G])) / (s);
        int b = ((int) (v[B]) * scale + (int) (p.v[B])) / (s);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Brighten(Pigment p, int scale) {
        if (scale == 0) {
            scale = 1;
        }
        int s = scale;
        int a = ((int) (v[A]) * scale + (int) (p.v[A])) / (s);
        int r = ((int) (v[R]) * scale + (int) (p.v[R])) / (s);
        int g = ((int) (v[G]) * scale + (int) (p.v[G])) / (s);
        int b = ((int) (v[B]) * scale + (int) (p.v[B])) / (s);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Colorize(Pigment p, int scale1, int scale2) {
        int s = scale1 + scale2;
        int a = ((int) (v[A]) * scale1 + (int) (p.v[A] * scale2)) / (s);
        int r = ((int) (v[R]) * scale1 + (int) (p.v[R] * scale2)) / (s);
        int g = ((int) (v[G]) * scale1 + (int) (p.v[G] * scale2)) / (s);
        int b = ((int) (v[B]) * scale1 + (int) (p.v[B] * scale2)) / (s);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Divide(Pigment p) {
        int a = v[A];
        int r = v[R];
        int g = v[G];
        int b = v[B];

        if (p.v[A] > 0) {
            a /= p.v[A];
        }
        if (p.v[R] > 0) {
            r /= p.v[R];
        }
        if (p.v[G] > 0) {
            g /= p.v[G];
        }
        if (p.v[B] > 0) {
            b /= p.v[B];
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Divide(int alpha, int shade) {
        int a = v[A];
        int r = v[R];
        int g = v[G];
        int b = v[B];

        if (alpha != 0) {
            a /= alpha;
        }
        if (shade != 0) {
            r /= shade;
            g /= shade;
            b /= shade;
        }

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Subtract(Pigment p) {
        int a = v[A] + v[A] - p.v[A];
        int r = v[R] + v[R] - p.v[R];
        int g = v[G] + v[G] - p.v[G];
        int b = v[B] + v[B] - p.v[B];

        a >>= 1;
        r >>= 1;
        g >>= 1;
        b >>= 1;
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }

        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    void Subtract(int alpha, int dark) {
        int a = v[A] + v[A] - alpha;
        int r = v[R] + v[R] - dark;
        int g = v[G] + v[G] - dark;
        int b = v[B] + v[B] - dark;

        a >>= 1;
        r >>= 1;
        g >>= 1;
        b >>= 1;
        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (a < 0) {
            a = 0;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }

        v[A] = a;
        v[R] = r;
        v[G] = g;
        v[B] = b;
    }

    int ToRGB() {
        return (((((int) (v[A]) << 24) | ((int) (v[R]) << 16)) | ((int) (v[G]) << 8)) | (int) (v[B]));
    }

    @Override
    public boolean To(StellarFiles f) {
        f.Add(v);
        return true;
    }

    @Override
    public boolean From(StellarFiles f) {
        try {
            Integer p[]=f.ReadIntArray();
            if(p!=null){
            v[0]=p[0];
            v[1]=p[1];
            v[2]=p[2];
            v[3]=p[3];
            return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Pigment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
