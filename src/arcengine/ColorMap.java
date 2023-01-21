/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import static arcengine.Defined.A;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Gerwyn
 */
enum PixelEffect {

    None, Add, Subtract, Multiply, Divide, Average, Tint, Hint, Alpha, Negative, AND, OR, XOR
}

class Chassis extends Defined {

    double v[];
    int structure;
    int form;
    DNA result;
    private static double ARC = 1080;
    private static double PD10 = 0.31415926535897932384626433832795;

    ///
    Chassis(int structure, int form, int dnaSize) {
        v = new double[2];
        this.structure = structure;
        this.form = form;
        result = new DNA(dnaSize);
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
        return Color.BLACK;
    }

    NTidePoint GetNextStructure(Seed seed) {
        switch (structure) {
            case 0: {
                v[X] = Math.cos(Math.sqrt(v[Y])) * v[X];
                v[Y] = Math.sin(Math.sqrt(v[X] * v[Y])) * v[Y];
                break;
            }
            case 1: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] + v[X] + v[X]))) * v[X]);
                v[Y] = (Math.sin(v[Y] * v[Y] * v[Y]) * v[Y]);
                break;
            }
            case 2: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[Y]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[Y]);
                break;
            }
            case 3: {
                v[X] = (Math.cos((v[X] * v[X]) / v[Y]) * v[X]);
                v[Y] = (Math.sin((v[Y] * v[Y]) / v[X]) * v[Y]);
                break;
            }
            case 4: {
                v[X] = 0.1 * (Math.tan(v[X] / v[Y] + (ARC / (v[X] * v[Y] + v[X]))) * Math.sqrt(Math.abs(v[X]) * Math.PI)); // NEEDLE STAR
                v[Y] = 0.1 * (Math.tan(v[Y] / v[X] + (ARC * (v[X] * v[Y] + v[Y]))) * Math.sqrt(Math.abs(v[Y]) * Math.PI));

                break;
            }
            case 5: {
                v[X] = (Math.sin(v[X] * v[Y] * v[Y]) * v[X]);
                v[Y] = (Math.cos(v[X] * v[X] * v[Y]) * v[Y]);
                break;
            }
            case 6: {
                v[X] = (Math.sin(v[X]) * v[X]); // CONE
                v[Y] = (Math.tan(Math.sin(v[Y]) * Math.cos(v[Y])) * v[Y] * 0.5);

                break;
            }
            case 7: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[X] * v[X]))) * v[Y]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] * v[Y]))) * v[X]);
                break;
            }
            case 8: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[X] * v[X]))) * v[X] * 2);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] * v[Y]))) * v[Y] * 0.5);

                break;
            }
            case 9: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[X] * v[X]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] * v[Y]))) * v[Y]);

                break;
            }
            case 10: {
                v[X] = (Math.sin(v[X] + v[Y]) * v[X]);
                v[Y] = (Math.cos(v[Y] + v[X]) * v[Y]);
                break;
            }
            case 11: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[X] + v[X]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] + v[Y]))) * v[Y]);

                break;
            }
            case 12: {
                v[X] = Math.cos(Math.sqrt(Math.abs(v[X] + v[Y] + v[X]))) * v[X];
                v[Y] = (Math.sin(v[X] * v[Y] * v[X]) * v[Y]);

                break;
            }
            case 13: {
                v[X] = (Math.sin(Math.sqrt(v[X] * v[Y]) + Math.sqrt(Math.abs(v[X] + v[Y] * 2))) * v[X]);
                v[Y] = (Math.cos(Math.sqrt(v[X] + v[Y])) * v[Y]);

                break;
            }
            case 14: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] + v[Y] * v[X]))) * v[X]);
                v[Y] = (Math.sin(v[X] * (v[Y] + v[X])) * v[Y]);

                break;
            }
            case 15: {
                v[X] = (float) 0.5 * (Math.tan(v[X] * v[X] * (ARC / (v[X] * v[Y] + v[Y]))) * Math.sqrt(Math.abs(v[X] * Math.PI))); // FORM
                v[Y] = (float) 0.5 * (Math.tan(v[Y] * v[Y] * (ARC / (v[X] * v[Y] + v[X]))) * Math.sqrt(Math.abs(v[Y] * Math.PI)));

                break;
            }
            case 16: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[Y] * v[X]))) * v[X]);
                v[Y] = (Math.sin(v[X] * v[Y]) * v[Y]);

                break;
            }
            case 17: {
                v[X] = (Math.cos(v[Y] * (v[Y] + v[X])) * v[Y]);
                v[Y] = (Math.sin(v[X] * (v[X] + v[X])) * v[X]);
                break;
            }
            case 18: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[Y] * v[Y]))) * v[X]);
                v[Y] = (float) (Math.sin(v[X] * v[Y]) * v[Y] * 0.5);

                break;
            }
            case 19: {
                v[Y] = (Math.tan(Math.sin(v[Y] / v[X]) * Math.cos(v[X] + v[Y])) * v[Y] * 0.5);
                v[X] = (Math.tan(Math.cos(v[X] / v[Y]) * Math.sin(v[X] + v[Y])) * v[X]); // DISC

                break;
            }
            case 20: {
                v[Y] = (Math.tan(Math.sin(v[Y] / v[X]) * Math.cos(v[X] + v[Y])) * v[Y]);
                v[X] = (Math.tan(Math.cos(v[X] / v[Y]) * Math.sin(v[X] + v[Y])) * v[X]); // DISC

                break;
            }
            case 21: {
                v[X] = (Math.sin(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[X]);
                v[Y] = (Math.cos(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[Y]);

                break;
            }
            case 22: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] + v[Y] * v[X])) * v[X]) * v[X]);
                v[Y] = (Math.sin(v[X] * (v[Y] + v[X]) * v[Y]) * v[Y]);

                break;
            }
            case 23: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[X]);
                v[Y] = (Math.sin(Math.abs(v[X] * v[Y])) * v[Y]);
                break;
            }
            case 24: {
                v[X] = (Math.sin(v[Y]) * v[X]);
                v[Y] = (Math.cos(v[X]) * v[Y]);
                break;
            }
            case 25: {
                v[X] = (Math.cos(v[Y]) * v[X]);
                v[Y] = (Math.sin(v[X]) * v[Y]);

                break;
            }
            case 26: {
                v[X] = (Math.sin(v[X]) * v[X]);
                v[Y] = (Math.tan(v[Y]) * (v[Y] / 100));

                break;
            }
            case 27: {
                v[X] = (Math.sin(v[X] * v[Y]) * v[X]);
                v[Y] = (Math.cos(v[X] * v[Y]) * v[Y]);
                break;
            }
            case 28: {
                v[X] = (Math.cos(v[Y]) * v[Y]);
                v[Y] = (Math.sin(v[X]) * v[X]);
                break;
            }
            case 29: {
                v[X] = ((Math.cos(v[X] * v[X]) + Math.sin(v[Y] * v[Y])) * v[X] * 0.5);
                v[Y] = ((Math.sin(v[X] * v[X]) + Math.cos(v[Y] * v[Y])) * v[Y] * 0.1);

                break;
            }
            case 30: {
                v[X] = (Math.sin(v[X]) * v[X] * 0.5);
                v[Y] = (Math.cos(v[Y]) * v[Y] * 0.1);
                break;
            }
            case 31: {
                v[X] = (Math.sin(v[X]) * v[X]);
                v[Y] = (Math.cos(v[Y]) * v[Y]);
                break;
            }
            case 32: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[Y]))) * v[X]);
                v[Y] = (Math.sin(v[Y] * v[X]) * v[Y]);
                break;
            }
            case 33: {
                v[X] = 0.05 * (Math.cos(Math.sqrt(Math.abs(v[X] + v[Y])) * v[X]) * v[X]);
                v[Y] = 0.05 * (Math.sin(v[X] * v[Y]) * v[Y] * v[Y]);
                break;
            }
            case 34: {
                v[X] = 0.5 * ((Math.sin(v[Y] + v[X] + 3) - Math.cos(v[Y] + v[X] + 3)) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] - v[Y] + 3) + Math.cos(v[X] - v[Y] + 3)) * v[Y]);

                break;
            }
            case 35: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[Y] * v[X]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[Y]);
                break;
            }
            case 36: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[Y] + v[X]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[X] + v[Y]))) * v[Y]);
                break;
            }
            case 37: {
                v[X] = (Math.cos(v[Y]) * v[X]); //  needle
                v[Y] = (Math.tan(v[Y]) * v[Y] * 0.01);
                break;
            }
            case 38: {
                v[X] = (Math.cos((v[Y] * v[Y] * v[Y]) / v[X]) * v[X]);
                v[Y] = (Math.sin((v[X] * v[X] * v[X]) / v[Y]) * v[Y]);
                break;
            }
            case 39: {
                v[X] = (Math.cos(v[Y] - v[X]) * v[X]);
                v[Y] = (float) (Math.sin(v[X] + v[Y]) * v[Y]);
                break;
            }

            case 40: {
                v[X] = (Math.tan(Math.cos(v[Y]) * Math.sin(v[X])) * v[X] * 0.5); // NEEDLE/cone
                v[Y] = (Math.tan(Math.sin(v[Y]) * Math.cos(v[X])) * v[Y] * 0.1);

                break;
            }
            case 41: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * 3))) * v[X] * 2);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * 3))) * v[Y] * 0.5);

                break;
            }
            case 42: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * 5))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * 5))) * v[Y]);
                break;
            }
            case 43: {
                v[X] = (Math.cos(v[X] + v[Y]) * v[X]);
                v[Y] = (Math.sin(v[Y] / v[X]) * v[Y]);

                break;
            }
            case 44: {
                v[X] = (Math.cos(Math.sqrt(Math.abs(v[X] * v[X]))) * v[X]);
                v[Y] = (Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y]))) * v[Y]);
                break;
            }
            case 45: {
                v[X] = ((Math.cos(v[X]) + Math.sin(v[Y] * v[Y] * v[Y] + Math.PI)) * v[X]);
                v[Y] = (float) ((Math.sin(v[X] * v[X] + Math.PI) + Math.cos(v[Y])) * v[Y] * 0.5);
                break;
            }
            case 46: {
                v[X] = ((Math.sin(v[Y] * v[X] + Math.PI) * Math.cos(v[Y] + v[X] + Math.PI)) * v[X]);
                v[Y] = (float) ((Math.sin(v[X] + v[Y] + Math.PI) * Math.cos(v[X] * v[Y] + Math.PI)) * v[Y]);

                break;
            }
            case 47: {
                v[X] = ((Math.cos(v[X] * v[X] + Math.PI) + Math.sin(v[Y] * v[Y] + Math.PI)) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[X] + Math.PI) - Math.cos(v[Y] * v[Y] + Math.PI)) * v[Y] * 0.5);

                break;
            }
            case 48: {
                v[X] = (Math.sin(v[Y]) * v[X]); // needle
                v[Y] = (Math.tan(v[X]) * v[Y] * 0.01);

                break;
            }
            case 49: {
                v[X] = ((Math.cos(v[X] * v[Y] + PD10) * Math.sin(v[Y] * v[X] + PD10)) * v[X] * 2);
                v[Y] = (float) ((Math.sin(v[X] * v[Y] + PD10) * Math.cos(v[Y] * v[X] + PD10)) * v[Y] * 2);

                break;
            }
            case 50: {
                v[X] = (Math.sin(v[Y]) * v[X]); // CONE
                v[Y] = (Math.tan(Math.sin(v[Y]) * Math.cos(v[X])) * v[Y] * 0.5);

                break;
            }
            case 51: {
                v[X] = (Math.cos(v[X] + v[Y]) * v[X]); // CYLINDER
                v[Y] = (Math.tan(Math.sin(v[X] - v[Y]) * Math.cos(v[Y] - v[X])) * v[Y] * 0.5);

                break;
            }
            case 52: {
                v[X] = (Math.cos(v[X] * ARC) * v[X]); //needle
                v[Y] = (Math.sin(v[Y] * ARC) * v[Y] * 0.5);

                break;
            }
            case 53: {
                v[X] = (Math.cos(v[X] * ARC) * v[X]); //needle
                v[Y] = (Math.sin(v[Y] * ARC) * v[Y]);

                break;
            }
            case 54: {
                v[X] = (Math.tan(Math.cos(v[X] - v[Y]) * Math.sin(v[X] + v[Y])) * v[X]); // DISC/SHELL
                v[Y] = (float) (Math.tan(Math.sin(v[Y] - v[X]) * Math.cos(v[X] + v[Y])) * v[Y]);

                break;
            }
            case 55: {
                v[X] = ((Math.sin(v[Y] * v[X] * v[X] + Math.PI) * Math.cos(v[Y] + v[X] * v[X] + Math.PI)) * v[X]);
                v[Y] = ((Math.sin(v[X] + v[Y] * v[Y] + Math.PI) * Math.cos(v[X] * v[Y] * v[Y] + Math.PI)) * v[Y]);

                break;
            }
            case 56: {
                v[X] = ((Math.cos(v[X] - v[Y] * v[Y] / v[X]) * Math.sin(v[Y] + v[X] * v[X] / v[Y])) * v[X]);
                v[Y] = ((Math.sin(v[X] + v[Y] * v[X] / v[Y]) * Math.cos(v[Y] - v[X] * v[X] / v[Y])) * v[Y]);

                break;
            }
            case 57: {
                v[X] = ((Math.cos(v[X] * v[X] + v[Y]) - Math.sin(v[Y] * v[Y] + v[X])) * v[X]);
                v[Y] = (float) ((Math.sin(v[X] * v[X] + v[Y]) - Math.cos(v[Y] * v[Y] + v[X])) * v[Y] * 0.5);

                break;
            }
            case 58: {
                v[X] = ((Math.cos(v[X] * v[X] * Math.PI) + Math.sin(v[Y] * v[Y] * Math.PI)) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[X] * Math.PI) + Math.cos(v[Y] * v[Y] * Math.PI)) * v[Y] * 0.5);

                break;
            }
            case 59: {
                v[X] = ((Math.cos(v[X] * v[X] * Math.PI) * 4 / (2 + Math.sin(v[Y] * v[Y] * Math.PI))) * (Math.sqrt(Math.abs(v[X]))));
                v[Y] = ((Math.sin(v[X] * v[X] * Math.PI) * 4 / (2 + Math.cos(v[Y] * v[Y] * Math.PI))) * (Math.sqrt(Math.abs(v[Y]))));

                break;
            }

            case 60: {
                v[X] = 0.5 * ((Math.cos(v[X] * v[X] + v[Y]) + Math.sin(v[Y] * v[Y] + v[X])) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] + v[Y]) + Math.cos(v[Y] * v[Y] + v[X])) * v[Y]);

                break;
            }
            case 61: {
                v[X] = ((Math.cos(Math.sqrt(Math.abs(v[X] * v[X] + v[Y]))) * Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[X] * 1.5);
                v[Y] = ((Math.sin(Math.sqrt(Math.abs(v[X] * v[X] + v[Y]))) * Math.cos(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[Y]) * 0.5;

                break;
            }
            case 62: {
                v[X] = ((Math.cos(v[X] * v[X] + v[Y]) + Math.sin(v[Y] * v[Y] + v[X])) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[X] + v[Y]) + Math.cos(v[Y] * v[Y] + v[X])) * v[Y] * 0.5);

                break;
            }
            case 63: {
                v[X] = ((Math.cos(Math.sqrt(Math.abs(v[X] * v[X] + v[Y]))) - Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[X]) * 0.5;
                v[Y] = ((Math.sin(v[X] * v[X] + v[Y]) - Math.cos(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[Y]) * 0.25;

                break;
            }
            case 64: {
                v[X] = 0.5 * ((Math.cos(Math.sqrt(Math.abs(v[X] * v[X] + v[Y]))) - Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] + v[Y]) - Math.cos(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[Y]);

                break;
            }
            case 65: {
                v[X] = 0.5 * ((Math.cos(Math.sqrt(Math.abs(v[X] * v[X] + v[Y]))) + Math.sin(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] + v[Y]) + Math.cos(Math.sqrt(Math.abs(v[Y] * v[Y] + v[X])))) * v[Y]);

                break;
            }
            case 66: {
                v[X] = 0.5 * ((Math.cos(v[X] * v[X] * v[X] + v[Y]) - Math.sin(v[Y] * v[Y] * v[X] + v[X])) * v[X] * 2);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] * v[Y] + v[Y]) - Math.cos(v[Y] * v[Y] * v[Y] + v[X])) * v[Y] * 0.5);

                break;
            }
            case 67: {
                v[X] = ((Math.cos(v[Y] * v[X] * v[Y] / v[X] + 3) * Math.sin(v[Y] * v[X] * v[X] / v[Y] + 3)) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] + v[Y] * v[Y] / v[X] + 3) * Math.cos(v[X] + v[Y] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }
            case 68: {
                v[X] = 0.5 * ((Math.cos(v[X] * v[X] + v[X] / v[Y]) + Math.sin(v[Y] * v[Y] + v[Y] / v[X])) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] + v[X] / v[Y]) + Math.cos(v[Y] * v[Y] + v[Y] / v[X])) * v[Y] * 2);

                break;
            }
            case 69: {
                v[X] = ((Math.cos(v[Y] + v[X] * v[Y] / v[X] + 3) + Math.sin(v[Y] + v[X] * v[X] / v[Y] + 3)) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[Y] * v[Y] / v[X] + 3) * Math.cos(v[X] * v[Y] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }

            case 70: {
                v[X] = 0.5 * (Math.tan(v[X] * v[X] - (ARC * (v[X] * v[Y] + v[Y]))) * Math.sqrt(Math.abs(v[X]))); // WING
                v[Y] = 0.5 * (Math.tan(v[Y] * v[Y] + (ARC * (v[X] * v[Y] + v[X]))) * Math.sqrt(Math.abs(v[Y])));

                break;
            }
            case 71: {
                v[X] = ((Math.cos(v[X] + v[Y] + v[X] / v[Y]) * Math.sin(v[Y] * v[X] + v[X] / v[Y])) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[Y] + v[Y] / v[X]) * Math.cos(v[Y] + v[Y] + v[Y] / v[X])) * v[Y]);

                break;
            }
            case 72: {
                v[X] = (Math.cos(v[Y]) * v[X]); // CONE
                v[Y] = (Math.tan(Math.sin(v[X]) * Math.cos(v[Y])) * v[Y] * 0.5);

                break;
            }
            case 73: {
                v[X] = ((Math.cos(v[X] + v[Y] * v[X] / v[Y]) + Math.sin(v[Y] - v[X] * v[Y] / v[X])) * v[X]);
                v[Y] = ((Math.sin(v[X] + v[Y] * v[X] / v[Y]) + Math.cos(v[Y] - v[X] * v[Y] / v[X])) * v[Y] * 0.5);

                break;
            }
            case 74: {
                v[X] = ((Math.cos(v[X] + v[Y] + v[X] * v[X]) * Math.sin(v[Y] * v[X] + v[X] * v[X])) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] * v[Y] + v[X] * v[X]) * Math.cos(v[Y] + v[Y] + v[X] * v[X])) * v[Y] * 0.5);

                break;
            }
            case 75: {
                v[X] = ((Math.cos(v[X] + v[Y] + v[X] * v[X]) * Math.sin(v[Y] * v[X] + v[X] * v[X])) * v[X]);
                v[Y] = ((Math.sin(v[X] * v[Y] + v[X] * v[X]) * Math.cos(v[Y] + v[Y] + v[X] * v[X])) * v[Y]);

                break;
            }
            case 76: {
                v[X] = ((Math.cos(v[X] * v[Y] * v[X] / v[Y]) * Math.sin(v[Y] * v[X] * v[Y] / v[X])) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] * v[Y] * v[X] / v[Y]) * Math.cos(v[Y] * v[X] * v[Y] / v[X])) * v[Y]);

                break;
            }
            case 77: {
                v[X] = 0.5 * ((Math.cos(v[X] * v[X] * v[X] + v[Y]) - Math.sin(v[Y] * v[Y] * v[X] + v[X])) * v[X] * 2);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[X] * v[Y] + v[Y]) - Math.cos(v[Y] * v[Y] * v[Y] + v[X])) * v[Y] * 0.5);

                break;
            }
            case 78: {
                v[X] = 0.5 * ((Math.cos(v[Y] + v[X] * v[Y] / v[X] + 3) + Math.sin(v[Y] + v[X] * v[X] / v[Y] + 3)) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[Y] * v[Y] / v[X] + 3) * Math.cos(v[X] * v[Y] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }
            case 79: {
                v[X] = ((Math.cos(v[X] * v[Y] * v[Y] / v[X]) + Math.sin(v[Y] * v[X] * v[X] / v[Y])) * v[X] * 0.5);
                v[Y] = ((Math.sin(v[X] * v[Y] * v[X] / v[Y]) + Math.cos(v[Y] * v[X] * v[X] / v[Y])) * v[Y] * 0.5);

                break;
            }
            case 80: {
                v[X] = ((Math.cos(v[X] + v[Y] * v[Y] / v[X] + 3) + Math.sin(v[Y] - v[X] * v[Y] / v[X] + 3)) * v[X]);
                v[Y] = ((Math.sin(v[X] - v[Y] * v[X] / v[Y] + 3) + Math.cos(v[Y] + v[X] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }
            case 81: {
                v[X] = 0.25 * (Math.tan(v[X] * v[Y] + (ARC / (v[X] * v[Y] + v[Y]))) * Math.sqrt(Math.abs(v[X]))); // SHAPE 100
                v[Y] = 0.1 * (Math.tan(v[Y] / v[X] + (ARC * (v[X] * v[Y] + v[X]))) * Math.sqrt(Math.abs(v[Y])));

                break;
            }
            case 82: {
                v[X] = 0.5 * ((Math.cos(v[X] * v[Y] + v[Y]) + Math.sin(v[Y] * v[X] + v[X])) * v[X]);
                v[Y] = 0.5 * ((Math.sin(v[X] * v[Y] + v[Y]) + Math.cos(v[Y] * v[X] + v[X])) * v[Y]);

                break;
            }
            case 83: {
                v[X] = 0.25 * ((Math.cos(v[Y] - v[X] * v[Y] / v[X] + 3) + Math.sin(v[Y] - v[X] * v[X] / v[Y] + 3)) * v[X] * 6);
                v[Y] = 0.25 * ((Math.sin(v[X] - v[Y] * v[Y] / v[X] + 3) * Math.cos(v[X] - v[Y] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }
            case 84: {
                v[Y] = ((Math.sin(v[Y]) - Math.cos(v[X])) * v[Y] * 0.5);
                v[X] = (Math.tan(Math.cos(v[X]) * Math.sin(v[Y])) * v[X]); // CYLINDER

                break;
            }
            case 85: {
                v[X] = ((Math.cos(v[Y] * v[X] * v[Y] / v[X] + 3) * Math.sin(v[Y] * v[X] * v[X] / v[Y] + 3)) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] - v[Y] * v[Y] / v[X] + 3) - Math.cos(v[X] - v[Y] * v[X] / v[Y] + 3)) * v[Y] * 0.5);

                break;
            }
            case 86: {
                v[X] = ((Math.cos(v[Y] * v[X] * v[Y] / v[X] + 3) * Math.sin(v[Y] * v[X] * v[X] / v[Y] + 3)) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] - v[Y] * v[Y] / v[X] + 3) + Math.cos(v[X] - v[Y] * v[X] / v[Y] + 3)) * v[Y] * 0.5);

                break;
            }
            case 87: {
                v[X] = ((Math.cos(v[X] + v[Y] * v[Y] / v[X] + 3) * Math.sin(v[Y] + v[X] * v[Y] / v[X] + 3)) * v[X] * 2);
                v[Y] = ((Math.sin(v[X] + v[Y] * v[X] / v[Y] + 3) * Math.cos(v[Y] + v[X] * v[X] / v[Y] + 3)) * v[Y]);

                break;
            }
            case 88: {
                v[X] = 0.01 * ((Math.cos(v[X] * v[Y] * v[Y] / v[X] * v[Y]) + Math.sin(v[Y] * v[X] * v[X] / v[Y]) * v[X]) * v[X]);
                v[Y] = 0.01 * ((Math.sin(v[X] * v[Y] * v[X] / v[Y] * v[X]) + Math.cos(v[Y] * v[X] * v[X] / v[Y]) * v[Y]) * v[Y]);

                break;
            }
            case 89: {
                break;
            }

            case 90: {
                break;
            }
            case 91: {
                break;
            }
            case 92: {
                break;
            }
            case 93: {
                break;
            }
            case 94: {
                break;
            }
            case 95: {
                break;
            }
            case 96: {
                break;
            }
            case 97: {
                break;
            }
            case 98: {
                break;
            }
            case 99: {
                break;
            }
            case 100: {
                break;
            }
        }
        return new NTidePoint((int) v[X], (int) v[Y]);
    }

    NTidePoint GetNextForm(Seed seed) {
        switch (structure) {
            case 0: {
                break;
            }
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
            case 6: {
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                break;
            }
            case 9: {
                break;
            }
            case 10: {
                break;
            }
            case 11: {
                break;
            }
            case 12: {
                break;
            }
            case 13: {
                break;
            }
            case 14: {
                break;
            }
            case 15: {
                break;
            }
            case 16: {
                break;
            }
            case 17: {
                break;
            }
            case 18: {
                break;
            }
            case 19: {
                break;
            }
            case 20: {
                break;
            }
            case 21: {
                break;
            }
            case 22: {
                break;
            }
            case 23: {
                break;
            }
            case 24: {
                break;
            }
            case 25: {
                break;
            }
            case 26: {
                break;
            }
            case 27: {
                break;
            }
            case 28: {
                break;
            }
            case 29: {
                break;
            }
            case 30: {
                break;
            }
            case 31: {
                break;
            }
            case 32: {
                break;
            }
            case 33: {
                break;
            }
            case 34: {
                break;
            }
            case 35: {
                break;
            }
            case 36: {
                break;
            }
            case 37: {
                break;
            }
            case 38: {
                break;
            }
            case 39: {
                break;
            }

            case 40: {
                break;
            }
            case 41: {
                break;
            }
            case 42: {
                break;
            }
            case 43: {
                break;
            }
            case 44: {
                break;
            }
            case 45: {
                break;
            }
            case 46: {
                break;
            }
            case 47: {
                break;
            }
            case 48: {
                break;
            }
            case 49: {
                break;
            }
            case 50: {
                break;
            }
            case 51: {
                break;
            }
            case 52: {
                break;
            }
            case 53: {
                break;
            }
            case 54: {
                break;
            }
            case 55: {
                break;
            }
            case 56: {
                break;
            }
            case 57: {
                break;
            }
            case 58: {
                break;
            }
            case 59: {
                break;
            }

            case 60: {
                break;
            }
            case 61: {
                break;
            }
            case 62: {
                break;
            }
            case 63: {
                break;
            }
            case 64: {
                break;
            }
            case 65: {
                break;
            }
            case 66: {
                break;
            }
            case 67: {
                break;
            }
            case 68: {
                break;
            }
            case 69: {
                break;
            }

            case 70: {
                break;
            }
            case 71: {
                break;
            }
            case 72: {
                break;
            }
            case 73: {
                break;
            }
            case 74: {
                break;
            }
            case 75: {
                break;
            }
            case 76: {
                break;
            }
            case 77: {
                break;
            }
            case 78: {
                break;
            }
            case 79: {
                break;
            }
            case 80: {
                break;
            }
            case 81: {
                break;
            }
            case 82: {
                break;
            }
            case 83: {
                break;
            }
            case 84: {
                break;
            }
            case 85: {
                break;
            }
            case 86: {
                break;
            }
            case 87: {
                break;
            }
            case 88: {
                break;
            }
            case 89: {
                break;
            }

            case 90: {
                break;
            }
            case 91: {
                break;
            }
            case 92: {
                break;
            }
            case 93: {
                break;
            }
            case 94: {
                break;
            }
            case 95: {
                break;
            }
            case 96: {
                break;
            }
            case 97: {
                break;
            }
            case 98: {
                break;
            }
            case 99: {
                break;
            }
            case 100: {
                break;
            }
        }
        return new NTidePoint((int) v[X], (int) v[Y]);
    }

    public void CalcPath(int xpos, int ypos, int size, double perx, double pery, double scalex, double scaley, DoubleAdder xcalc, DoubleAdder ycalc, Seed seed) {
        NTidePoint pos = GetNextStructure(seed);
        result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        //
        for (int i = 0; i < size; i++) {
            v[X] = xcalc.Calc() * perx;
            v[Y] = ycalc.Calc() * pery;
            pos = this.GetNextStructure(seed);
            pos.v[X] *= scalex;
            pos.v[Y] *= scaley;
            result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        }
    }

    public void AddPath(int xpos, int ypos, int size, double perx, double pery, double scalex, double scaley, DoubleAdder xcalc, DoubleAdder ycalc, Seed seed) {
        NTidePoint pos = GetNextStructure(seed);
        result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        //
        for (int i = 0; i < size; i++) {
            v[X] = xcalc.Add() * perx;
            v[Y] = ycalc.Add() * pery;
            pos = this.GetNextStructure(seed);
            pos.v[X] *= scalex;
            pos.v[Y] *= scaley;
            result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        }
    }

    public void MolePath(int xpos, int ypos, int size, double perx, double pery, double scalex, double scaley, Molecula calc, Seed seed) {
        NTidePoint pos = GetNextStructure(seed);
        result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        //
        for (int i = 0; i < size; i++) {
            calc.SingleCalc();
            v[X] = calc.cosine * perx;
            v[Y] = calc.sine * pery;
            pos = this.GetNextStructure(seed);
            pos.v[X] *= scalex;
            pos.v[Y] *= scaley;
            result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        }
    }

    public void FreeMolePath(int xpos, int ypos, int size, double perx, double pery, double scalex, double scaley, Molecula calc, Seed seed) {
        NTidePoint pos = GetNextStructure(seed);
        result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        //
        for (int i = 0; i < size; i++) {
            calc.LineCalc(perx * pery * 1e24);
            v[X] = calc.cosine * perx;
            v[Y] = calc.sine * pery;
            pos.v[X] = (int) (v[X]);
            pos.v[Y] = (int) (v[Y]);
            //pos=this.GetNextStructure(seed);
            pos.v[X] *= scalex;
            pos.v[Y] *= scaley;
            result.Add(new NTidePoint(xpos + pos.v[X], ypos + pos.v[Y]));
        }
    }
}

class Seed extends Defined implements java.io.Serializable {

    int randomizedSeed;
    //
    double scale[];
    ///
    Molecula mole[];//fractal
    Adder moleSequancer;
    Mole moleSequance[];
    int moleSequanceNumber[];
    double moleSequanceScale[];//if zero use no scale
    ///
    DNA dna;//constant seed data
    Skin skin;
    //body
    Chassis body;
    int structures[];
    int forms[];
    Adder structureSequancer;
    Adder formSequancer;
    // anim
    int anims;
    Adder sequancer;
    Adder angleSequancer;
    //effects
    Adder effectSequancer;
    PixelEffect effectSequance[];
    int shadeScaleSequance[];//if zero use pixel effect
    //color
    Adder colorSequancer[][];
    Pigment pigment[];
    int colors;

    //
    public Seed(double scaleX, double scaleY, int anims, int structure, int form, int dnaSize, int molecules, int colors, int effects) {
        scale = new double[]{scaleX, scaleY};
        this.anims = anims;
        this.body = new Chassis(structure, form, dnaSize);
        this.dna = new DNA(dnaSize);
        this.dna.Seed();
        this.mole = new Molecula[molecules];
        this.colors = colors;
        colorSequancer = new Adder[colors][4];
        pigment = new Pigment[colors];
        effectSequance = new PixelEffect[effects];
        shadeScaleSequance = new int[effects];//if zero use pixel effect
    }

    void SetSeedBody(int structure[], int form[], int structureStep, int formStep) {
        this.structures = structure;
        this.forms = form;
        structureSequancer = new Adder(0, this.structures.length - 1, structureStep);
        formSequancer = new Adder(0, this.forms.length - 1, formStep);
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
        return Color.BLACK;
    }

    int StructueSequances() {
        return structures.length;
    }

    int FormSequances() {
        return forms.length;
    }

    void SetMolecules() {
        for (int i = 0; i < mole.length; i++) {
            mole[i] = new Molecula();
        }
    }

    int Molecules() {
        return mole.length;
    }

    void SetColors(Adder a, Adder r, Adder g, Adder b, int step, Gradiant aGrade, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade) {
        pigment = Pigment.GradiantLine(a, r, g, b, step, aGrade, rGrade, bGrade, bGrade, colors);
    }

    void SetColor(Pigment color, int colorSequance) {
        pigment[colorSequance] = color;
    }

    void SetColors(int a, Adder r, Adder g, Adder b, int step, Gradiant aGrade, Gradiant rGrade, Gradiant gGrade, Gradiant bGrade) {
        pigment = Pigment.GradiantLine(a, r, g, b, step, rGrade, bGrade, bGrade, colors);
    }

    int Colors() {
        return colors;
    }

    void SetColorSequance(Pigment from, Pigment to, Pigment step, int colorSequance) {
        colorSequancer[colorSequance][A] = new Adder(from.Alpha(), to.Alpha(), step.Alpha());
        colorSequancer[colorSequance][R] = new Adder(from.Red(), to.Red(), step.Red());
        colorSequancer[colorSequance][G] = new Adder(from.Green(), to.Green(), step.Green());
        colorSequancer[colorSequance][B] = new Adder(from.Blue(), to.Blue(), step.Blue());
    }

    void SetEffectSequance(PixelEffect effect, int sequance) {
        effectSequance[sequance] = effect;
        shadeScaleSequance[sequance] = 0;
    }

    int EffectSequances() {
        return effectSequance.length;
    }

    void SetMoleSequancer(int sequances, int step) {

        moleSequancer = new Adder(0, sequances - 1, step);
        moleSequance = new Mole[sequances];
        moleSequanceNumber = new int[sequances];
        moleSequanceScale = new double[sequances];

    }

    void SetMoleSequance(Mole type, int use, double scale, int sequance) {
        moleSequance[sequance] = type;
        if (use >= mole.length) {
            use = mole.length - 1;
        } else if (use < 0) {
            use = 0;
        }
        moleSequanceNumber[sequance] = use;
        moleSequanceScale[sequance] = scale;
    }

    int MoleculaSequances() {
        return moleSequance.length;
    }
}
//
// Other me

public class ColorMap extends Defined {
    //
    // Fields

    protected Local location;
    protected int startx;
    protected int starty;
    protected int width;
    protected int height;
    protected Pigment data[][];
    protected NTidePoint scanlines[];
    protected String name;
    //
    // Constructors
    //

    public ColorMap(String name, Local location) {
        this.name = name;
        this.location = location;
    }

    public String toString() {
        return this.name;
    }
    //
    // Methods
    //

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
    //
    // Accessor methods
    //

    /**
     * Set the value of location
     *
     * @param newVar the new value of location
     */
    protected void setLocation(Local newVar) {
        location = newVar;
    }

    /**
     * Get the value of location
     *
     * @return the value of location
     */
    protected Local getLocation() {
        return location;
    }

    //
    // Other methods
    //
    ColorMap(int w, int h) {
        width = w;
        height = h;
        data = new Pigment[w][h];
        scanlines = new NTidePoint[this.height];
        for (int i = 0; i < this.height; i++) {
            scanlines[i] = new NTidePoint(this.width, 0);
        }
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                data[x][y] = new Pigment();
            }
        }
    }

    ColorMap(int w, int h, Pigment fillColor) {
        width = w;
        height = h;
        data = new Pigment[w][h];
        scanlines = new NTidePoint[this.height];
        for (int i = 0; i < this.height; i++) {
            scanlines[i] = new NTidePoint(this.width, 0);
        }
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                data[x][y] = new Pigment(fillColor);
            }
        }
    }

    void ResetScanLines() {

        for (int i = 0; i < this.height; i++) {
            scanlines[i] = new NTidePoint(this.width, 0);
        }
    }

    public String GetString() {
        return "ColorMap Width" + width + "Height" + height;
    }

    boolean SetPixel(int atx, int aty, Pigment p, boolean fold, boolean discard) {
        if (fold) {
            boolean retest = false;
            do {
                if (retest == true) {
                    retest = false;
                }
                /// Torus Manifold
                if (atx < startx) {
                    atx += width;
                    retest = true;
                }
                if (atx >= width) {
                    atx -= width;
                    retest = true;
                }
                if (aty < starty) {
                    aty += height;
                    retest = true;
                }
                if (aty >= height) {
                    aty -= height;
                    retest = true;
                }
            } while (retest);

        } else {
            if (discard) {
                //dont add pixel
                if (atx < startx) {
                    return false;
                }
                if (atx >= width) {
                    return false;
                }
                if (aty < starty) {
                    return false;
                }
                if (aty >= height) {
                    return false;
                }

            } else {
                //limit 
                if (atx < startx) {
                    atx = startx;
                }
                if (atx >= width) {
                    atx = width - 1;
                }
                if (aty < starty) {
                    aty = starty;
                }
                if (aty >= height) {
                    aty = height - 1;
                }
            }
        }
        data[atx][aty] = p;
        return true;
    }

    Pigment GetPixel(int atx, int aty, boolean fold, boolean fail) {
        Pigment p = null;
        if (fold) {
            boolean retest = false;
            do {
                if (retest == true) {
                    retest = false;
                }
                /// Torus Manifold
                if (atx < startx) {
                    atx += width;
                    retest = true;
                }
                if (atx >= width) {
                    atx -= width;
                    retest = true;
                }
                if (aty < starty) {
                    aty += height;
                    retest = true;
                }
                if (aty >= height) {
                    aty -= height;
                    retest = true;
                }
            } while (retest);

        } else {
            if (fail) {
                //dont add pixel
                if (atx < startx) {
                    return null;
                }
                if (atx >= width) {
                    return null;
                }
                if (aty < starty) {
                    return null;
                }
                if (aty >= height) {
                    return null;
                }

            } else {
                //limit 
                if (atx < startx) {
                    atx = startx;
                }
                if (atx >= width) {
                    atx = width - 1;
                }
                if (aty < starty) {
                    aty = starty;
                }
                if (aty >= height) {
                    aty = height - 1;
                }
            }
        }
        return new Pigment(data[atx][aty]);
    }

    boolean SolidPixel(int atx, int aty, Pigment p, boolean fold, boolean fail, PixelEffect effect, int limit) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            if (c.Alpha() > limit) {
                switch (effect) {
                    case Add: {
                        c.Add(p);
                        break;
                    }
                    case Subtract: {
                        c.Subtract(p);
                        break;
                    }
                    case Multiply: {
                        c.Multiply(p);
                        break;
                    }
                    case Divide: {
                        c.Divide(p);
                        break;
                    }
                    case Average: {
                        c.Average(p);
                        break;
                    }
                    case Tint: {
                        c.Tint(p);
                        break;
                    }
                    case Hint: {
                        c.Hint(p);
                        break;
                    }
                    case Alpha: {
                        c.Alpha(p);
                        break;
                    }
                    case AND: {
                        c.AND(p);
                        break;
                    }
                    case OR: {
                        c.OR(p);
                        break;
                    }
                    case XOR: {
                        c.XOR(p);
                        break;
                    }
                }
                SetPixel(atx, aty, c, fold, fail);
            }
            return true;

        }
        return false;
    }

    boolean NegativePixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Negative(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean NegativePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Negative(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AveragePixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Average(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AveragePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Average(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AndPixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.AND(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AndPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.AND(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean OrPixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.OR(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean OrPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.OR(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean XorPixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.XOR(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean XorPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.XOR(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean TintPixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Tint(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean HintPixel(int atx, int aty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Hint(p);
            SetPixel(atx, aty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean TintPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Tint(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean HintPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Hint(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AlphaPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Alpha(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AlphaPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Alpha(p, scale);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean AddPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Add(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean SubtractPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Subtract(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean MultiplyPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Multiply(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean DividePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Divide(p);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean ShadePixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Shade(p, scale);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean ColorizePixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Colorize(p, scale);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean BrightenPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Brighten(p, scale);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    boolean DarkenPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Darken(p, scale);
            SetPixel(destx, desty, c, fold, fail);
            return true;
        }
        return false;
    }

    ///
    boolean AveragePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Average(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean AndPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.AND(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean OrPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.OR(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean XorPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.XOR(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean TintPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Tint(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean HintPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Hint(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean NegativePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Negative(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean AlphaPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Alpha(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean AlphaPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Alpha(p, scale);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean AddPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Add(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean SubtractPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Subtract(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean MultiplyPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Multiply(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean DividePixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Divide(p);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean ShadePixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Shade(p, scale);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean ColorizePixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Colorize(p, scale);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean BrightenPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Brighten(p, scale);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean DarkenPixel(int atx, int aty, int destx, int desty, Pigment p, int scale, boolean fold, boolean fail, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        if (c != null) {
            c.Darken(p, scale);
            EffectPixel(destx, desty, c, fold, fail, destEffect);
            return true;
        }
        return false;
    }

    boolean EffectPixel(int atx, int aty, Pigment p, boolean fold, boolean fail, PixelEffect effect) {
        switch (effect) {
            case Add: {
                return AddPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Subtract: {
                return SubtractPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Multiply: {
                return MultiplyPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Divide: {
                return DividePixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Average: {
                return AveragePixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Tint: {
                return TintPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Hint: {
                return HintPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Alpha: {
                return AlphaPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case Negative: {
                return NegativePixel(atx, aty, atx, aty, p, fold, fail);
            }
            case AND: {
                return AndPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case OR: {
                return OrPixel(atx, aty, atx, aty, p, fold, fail);
            }
            case XOR: {
                return XorPixel(atx, aty, atx, aty, p, fold, fail);
            }
        }
        return false;
    }

    boolean EffectPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect effect) {
        switch (effect) {
            case Add: {
                return AddPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Subtract: {
                return SubtractPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Multiply: {
                return MultiplyPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Divide: {
                return DividePixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Average: {
                return AveragePixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Tint: {
                return TintPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Hint: {
                return HintPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Alpha: {
                return AlphaPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case Negative: {
                return NegativePixel(atx, aty, destx, desty, p, fold, fail);
            }
            case AND: {
                return AndPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case OR: {
                return OrPixel(atx, aty, destx, desty, p, fold, fail);
            }
            case XOR: {
                return XorPixel(atx, aty, destx, desty, p, fold, fail);
            }

        }
        return false;
    }

    boolean EffectPixel(int atx, int aty, int destx, int desty, Pigment p, boolean fold, boolean fail, PixelEffect effect, PixelEffect destEffect) {
        switch (effect) {
            case Add: {
                return AddPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Subtract: {
                return SubtractPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Multiply: {
                return MultiplyPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Divide: {
                return DividePixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Average: {
                return AveragePixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Tint: {
                return TintPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Hint: {
                return HintPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Alpha: {
                return AlphaPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case Negative: {
                return NegativePixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case AND: {
                return AndPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case OR: {
                return OrPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
            case XOR: {
                return XorPixel(atx, aty, destx, desty, p, fold, fail, destEffect);
            }
        }
        return false;
    }

    boolean EffectPixel(int atx, int aty, int destx, int desty, Pigment p1, Pigment p2, boolean fold, boolean fail, PixelEffect effect1, PixelEffect effect2, PixelEffect effect3, int alpha) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        Pigment c1 = new Pigment(p1);
        Pigment c2 = new Pigment(p2);
        ///
        switch (effect1) {
            case Add: {
                c1.Add(c);
            }
            case Subtract: {
                c1.Subtract(c);
            }
            case Multiply: {
                c1.Multiply(c);
            }
            case Divide: {
                c1.Divide(c);
            }
            case Average: {
                c1.Average(c);
            }
            case Tint: {
                c1.Tint(c);
            }
            case Hint: {
                c1.Hint(c);
            }
            case Alpha: {
                c1.Alpha(c);
            }
            case Negative: {
                c1.Negative(c);
            }
            case AND: {
                c1.AND(c);
            }
            case OR: {
                c1.OR(c);
            }
            case XOR: {
                c1.XOR(c);
            }
        }
        switch (effect2) {
            case Add: {
                c2.Add(c);
            }
            case Subtract: {
                c2.Subtract(c);
            }
            case Multiply: {
                c2.Multiply(c);
            }
            case Divide: {
                c2.Divide(c);
            }
            case Average: {
                c2.Average(c);
            }
            case Tint: {
                c2.Tint(c);
            }
            case Hint: {
                c2.Hint(c);
            }
            case Alpha: {
                c2.Alpha(c);
            }
            case Negative: {
                c2.Negative(c);
            }
            case AND: {
                c2.AND(c);
            }
            case OR: {
                c2.OR(c);
            }
            case XOR: {
                c2.XOR(c);
            }
        }
        switch (effect3) {
            case Add: {
                c1.Add(c2);
            }
            case Subtract: {
                c1.Subtract(c2);
            }
            case Multiply: {
                c1.Multiply(c2);
            }
            case Divide: {
                c1.Divide(c2);
            }
            case Average: {
                c1.Average(c2);
            }
            case Tint: {
                c1.Tint(c2);
            }
            case Hint: {
                c1.Hint(c2);
            }
            case Alpha: {
                c1.Alpha(c2);
            }
            case Negative: {
                c1.Negative(c2);
            }
            case AND: {
                c1.AND(c2);
            }
            case OR: {
                c1.OR(c2);
            }
            case XOR: {
                c1.XOR(c2);
            }

        }
        if (alpha > 0) {
            c1.Alpha(alpha);
        }
        SetPixel(destx, desty, c1, fold, fail);
        return false;
    }

    boolean EffectPixel(int atx, int aty, int destx, int desty, Pigment p1, Pigment p2, boolean fold, boolean fail, PixelEffect effect1, PixelEffect effect2, PixelEffect effect3, int alpha, PixelEffect destEffect) {
        Pigment c = GetPixel(atx, aty, fold, fail);
        Pigment c1 = new Pigment(p1);
        Pigment c2 = new Pigment(p2);
        ///
        switch (effect1) {
            case Add: {
                c1.Add(c);
            }
            case Subtract: {
                c1.Subtract(c);
            }
            case Multiply: {
                c1.Multiply(c);
            }
            case Divide: {
                c1.Divide(c);
            }
            case Average: {
                c1.Average(c);
            }
            case Tint: {
                c1.Tint(c);
            }
            case Hint: {
                c1.Hint(c);
            }
            case Alpha: {
                c1.Alpha(c);
            }
            case Negative: {
                c1.Negative(c);
            }
            case AND: {
                c1.AND(c);
            }
            case OR: {
                c1.OR(c);
            }
            case XOR: {
                c1.XOR(c);
            }

        }
        switch (effect2) {
            case Add: {
                c2.Add(c);
            }
            case Subtract: {
                c2.Subtract(c);
            }
            case Multiply: {
                c2.Multiply(c);
            }
            case Divide: {
                c2.Divide(c);
            }
            case Average: {
                c2.Average(c);
            }
            case Tint: {
                c2.Tint(c);
            }
            case Hint: {
                c2.Hint(c);
            }
            case Alpha: {
                c2.Alpha(c);
            }
            case Negative: {
                c2.Negative(c);
            }
            case AND: {
                c2.AND(c);
            }
            case OR: {
                c2.OR(c);
            }
            case XOR: {
                c2.XOR(c);
            }

        }
        switch (effect3) {
            case Add: {
                c1.Add(c2);
            }
            case Subtract: {
                c1.Subtract(c2);
            }
            case Multiply: {
                c1.Multiply(c2);
            }
            case Divide: {
                c1.Divide(c2);
            }
            case Average: {
                c1.Average(c2);
            }
            case Tint: {
                c1.Tint(c2);
            }
            case Hint: {
                c1.Hint(c2);
            }
            case Alpha: {
                c1.Alpha(c2);
            }
            case Negative: {
                c1.Negative(c2);
            }
            case AND: {
                c1.AND(c2);
            }
            case OR: {
                c1.OR(c2);
            }
            case XOR: {
                c1.XOR(c2);
            }

        }
        if (alpha > 0) {
            c1.Alpha(alpha);
        }
        EffectPixel(destx, desty, c1, fold, fail, destEffect);
        return false;
    }

    NTidePoint Center() {
        return new NTidePoint(this.width >> 1, this.height >> 1);
    }

    public int CenterX() {
        return this.width >> 1;
    }

    public int CenterY() {
        return this.height >> 1;
    }

    Pigment[][] GetColors(int fromx, int fromy, int tox, int toy, boolean fold) {
        Pigment cols[][] = new Pigment[tox - fromx][toy - fromy];
        int ix = 0;
        int iy = 0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.ReadOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                cols[ix][iy] = this.GetPixel(x, y, fold, false);
                iy++;
            }
            ix++;
            iy = 0;
        }
        return cols;
    }

    void SetColors(int fromx, int fromy, int tox, int toy, Pigment value[][], boolean fold, boolean discard) {
        int ix = 0;
        int iy = 0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.WriteOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                this.SetPixel(x, y, value[ix][iy], fold, discard);
                iy++;
            }
            ix++;
            iy = 0;
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, Pigment value[][], int valueLengthx, int valueLengthy, boolean fold, boolean discard) {
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        int ix = 0;
        int iy = 0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.WriteOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                this.SetPixel(x, y, value[ix][iy], fold, discard);
                iy++;
                if (iy >= valueLengthy) {
                    iy = 0;
                }
            }
            ix++;
            if (ix >= valueLengthx) {
                ix = 0;
            }
            iy = 0;
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, Pigment value[][], int valueLengthx, int valueLengthy, boolean fold, boolean discard, PixelEffect effect) {
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        int ix = 0;
        int iy = 0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.WriteOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                this.EffectPixel(x, y, value[ix][iy], fold, discard, effect);
                iy++;
                if (iy >= valueLengthy) {
                    iy = 0;
                }
            }
            ix++;
            if (ix >= valueLengthx) {
                ix = 0;
            }
            iy = 0;
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard) {
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        //int ix=0;int iy=0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.WriteOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                this.SetPixel(x, y, value, fold, discard);
                //iy++;
            }
            //ix++;
            //iy=0;
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        //int ix=0;int iy=0;
        //BitmapData dat=this.image.LockBits(new Rectangle(fromx,fromy,tox-fromx,toy-fromy),System.Drawing.Imaging.ImageLockMode.WriteOnly,System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        for (int x = fromx; x < tox; x++) {
            for (int y = fromy; y < toy; y++) {
                this.EffectPixel(x, y, value, fold, discard, effect);
                //iy++;
            }
            //ix++;
            //iy=0;
        }
    }

    void DrawRectangle(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard) {
        tox--;
        toy--;
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        //X
        for (int x = fromx; x < tox; x++) {

            this.SetPixel(x, fromy, value, fold, discard);
        }
        for (int x = fromx; x < tox; x++) {
            this.SetPixel(x, toy, value, fold, discard);
        }
        //Y
        for (int y = fromy; y < toy; y++) {
            this.SetPixel(fromx, y, value, fold, discard);
        }
        for (int y = fromy; y < toy; y++) {
            this.SetPixel(tox, y, value, fold, discard);
        }

        this.SetPixel(tox, toy, value, fold, discard);
    }

    void DrawRectangle(int fromx, int fromy, int tox, int toy, Pigment value[], boolean fold, boolean discard) {
        tox--;
        toy--;
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        int at = 0;
        //X
        for (int x = fromx; x < tox; x++) {

            this.SetPixel(x, fromy, value[at], fold, discard);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        for (int x = fromx; x < tox; x++) {
            this.SetPixel(x, toy, value[at], fold, discard);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        //Y
        for (int y = fromy; y < toy; y++) {
            this.SetPixel(fromx, y, value[at], fold, discard);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        for (int y = fromy; y < toy; y++) {
            this.SetPixel(tox, y, value[at], fold, discard);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }

        this.SetPixel(tox, toy, value[at], fold, discard);
    }

    void DrawRectangle(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        tox--;
        toy--;
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        //X
        for (int x = fromx; x < tox; x++) {

            this.EffectPixel(x, fromy, value, fold, discard, effect);
        }
        for (int x = fromx; x < tox; x++) {
            this.EffectPixel(x, toy, value, fold, discard, effect);
        }
        //Y
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(fromx, y, value, fold, discard, effect);
        }
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(tox, y, value, fold, discard, effect);
        }

        this.EffectPixel(tox, toy, value, fold, discard, effect);
    }

    void DrawRectangle(int fromx, int fromy, int tox, int toy, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        tox--;
        toy--;
        int swap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
        }
        //X
        int at = 0;
        for (int x = fromx; x < tox; x++) {

            this.EffectPixel(x, fromy, value[at], fold, discard, effect);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        for (int x = fromx; x < tox; x++) {
            this.EffectPixel(x, toy, value[at], fold, discard, effect);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        //Y
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(fromx, y, value[at], fold, discard, effect);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(tox, y, value[at], fold, discard, effect);
            at++;
            if (at >= value.length) {
                at = 0;
            }
        }

        this.EffectPixel(tox, toy, value[at], fold, discard, effect);
    }

    void DrawRectangle(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, PixelEffect effectUp, PixelEffect effectDown, PixelEffect effectLeft, PixelEffect effectRight) {
        tox--;
        toy--;
        int swap;
        PixelEffect eswap;
        if (fromx > tox) {
            swap = fromx;
            fromx = tox;
            tox = swap;
            eswap = effectUp;
            effectUp = effectDown;
            effectDown = eswap;
        }
        if (fromy > toy) {
            swap = fromy;
            fromy = toy;
            toy = swap;
            eswap = effectLeft;
            effectLeft = effectRight;
            effectRight = eswap;
        }
        //X
        for (int x = fromx; x < tox; x++) {

            this.EffectPixel(x, fromy, value, fold, discard, effectUp);
        }
        for (int x = fromx; x < tox; x++) {
            this.EffectPixel(x, toy, value, fold, discard, effectDown);
        }
        //Y
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(fromx, y, value, fold, discard, effectLeft);
        }
        for (int y = fromy; y < toy; y++) {
            this.EffectPixel(tox, y, value, fold, discard, effectRight);
        }

        this.EffectPixel(tox, toy, value, fold, discard, effectRight);
    }

    void DrawScanLine(int fromx, int fromy, int tox, int toy) {
        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                if (y >= 0 && y < height) {
                    if (x < scanlines[y].v[0]) {
                        scanlines[y].v[0] = x;
                    }
                    if (x > scanlines[y].v[1]) {
                        scanlines[y].v[1] = x;
                    }
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                if (y >= 0 && y < height) {

                    if (x < scanlines[y].v[0]) {
                        scanlines[y].v[0] = x;
                    }
                    if (x > scanlines[y].v[1]) {
                        scanlines[y].v[1] = x;
                    }
                }

            }
        }
    }

    void FillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Pigment faceColor) {
        this.DrawScanLine(x1, y1, x2, y2);
        this.DrawScanLine(x2, y2, x3, y3);
        this.DrawScanLine(x3, y3, x1, y1);
        for (int y = 0; y < this.height; y++) {
            if (scanlines[y].v[0] < 0) {
                scanlines[y].v[0] = 0;
            }
            if (scanlines[y].v[1] >= this.width) {
                scanlines[y].v[1] = this.width - 1;
            }
            for (int x = scanlines[y].v[0]; x <= scanlines[y].v[1]; x++) {
                SetPixel(x, y, faceColor, false, true);
            }
            scanlines[y] = new NTidePoint(this.width, 0);
        }

    }

    void DrawLine(NTidePoint from, NTidePoint to, Pigment cvalue, int stride, boolean fold, boolean discard, boolean avarage) {
        int st = 1;
        int str = (stride);
        if (str <= 0) {
            str = 1;
        }
        Pigment value = new Pigment(cvalue);
        //Pigment sub=new Pigment(0,4,4,4);
        value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));
        DrawLine(from.v[0], from.v[1], to.v[0], to.v[1], value, fold, discard, PixelEffect.Tint, avarage);
        int s = 0;
        while (st < str) {
            s = (int) st;
            value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));
            DrawLine(from.v[0] + s, from.v[1], to.v[0] + s, to.v[1], value, fold, discard, PixelEffect.Tint, avarage);

            value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));
            DrawLine(from.v[0] - s, from.v[1], to.v[0] - s, to.v[1], value, fold, discard, PixelEffect.Tint, avarage);

            value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));
            DrawLine(from.v[0], from.v[1] - s, to.v[0], to.v[1] - s, value, fold, discard, PixelEffect.Tint, avarage);

            value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));
            DrawLine(from.v[0], from.v[1] + s, to.v[0], to.v[1] + s, value, fold, discard, PixelEffect.Tint, avarage);

            st++;
            value.Subtract(new Pigment(0, -1, -1, -1));
            value.Average(new Pigment(255, Random.d(cvalue.Red()), Random.d(cvalue.Green()), Random.d(cvalue.Blue())));

        };
        //}
    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, boolean avarage) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                if (this.SetPixel(x, y, value, fold, discard)) {
                    if (avarage) {
                        this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                    }
                } else {
                    break;
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                if (this.SetPixel(x, y, value, fold, discard)) {
                    if (avarage) {
                        this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                        this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                    }
                } else {
                    break;
                }
            }
        }

    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value[], boolean fold, boolean discard, boolean avarage) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int at = 0;
        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                this.SetPixel(x, y, value[at], fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value[at], fold, discard, PixelEffect.Average);

                }
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                this.SetPixel(x, y, value[at], fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value[at], fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value[at], fold, discard, PixelEffect.Average);

                }
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        }

    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, PixelEffect effect, boolean avarage) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
            }
        }

    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value[], boolean fold, boolean discard, PixelEffect effect, boolean avarage) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int at = 0;
        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                this.EffectPixel(x, y, value[at], fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value[at], fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value[at], fold, discard, effect);
                    this.EffectPixel(x, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x, y - 1, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value[at], fold, discard, effect);

                }
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                this.EffectPixel(x, y, value[at], fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value[at], fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value[at], fold, discard, effect);
                    this.EffectPixel(x, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x, y - 1, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value[at], fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value[at], fold, discard, effect);

                }
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        }

    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value, boolean fold, boolean discard, PixelEffect effect) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                this.EffectPixel(x, y, value, fold, discard, effect);
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                this.EffectPixel(x, y, value, fold, discard, effect);
            }
        }

    }

    void DrawLine(int fromx, int fromy, int tox, int toy, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {                           /*int swap;
         if(fromx>tox){
         swap=fromx;
         fromx=tox;
         tox=swap;
         }
         if(fromy>toy){
         swap=fromy;
         fromy=toy;
         toy=swap;
         }*/

        int at = 0;
        int dx = tox - fromx;
        int dy = toy - fromy;
        int dx2;
        int dy2;
        int ix;
        int iy;
        int i;
        int e;
        int x = fromx;
        int y = fromy;
        if (dx >= 0) {
            ix = 1;
        } else {
            ix = -1;
            dx = -dx;
        }
        if (dy >= 0) {
            iy = 1;
        } else {
            iy = -1;
            dy = -dy;
        }
        dx2 = dx << 1;
        dy2 = dy << 1;
        if (dx > dy) {
            e = dy2 - dx;
            for (i = 0; i <= dx; i++) {
                if (e >= 0) {
                    e -= dx2;
                    y += iy;
                }
                e += dy2;
                x += ix;
                this.EffectPixel(x, y, value[at], fold, discard, effect);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        } else {
            e = dx2 - dy;
            for (i = 0; i <= dy; i++) {
                if (e >= 0) {
                    e -= dy2;
                    x += ix;
                }
                e += dx2;
                y += iy;
                this.EffectPixel(x, y, value[at], fold, discard, effect);
                at++;
                if (at >= value.length) {
                    at = 0;
                }

            }
        }

    }

    void DrawSymetricLines(DNA points, int atx, int aty, double startAngle, double rotate, double oscilate, double amplitude, boolean symx, Pigment value, boolean fold, boolean discard, PixelEffect effect) {

        double a = startAngle / 57.295779513082320876798154814105;
        double rot = rotate / 57.295779513082320876798154814105;
        double start = a;
        double ax = 0;
        double ay = 0;
        double xa = points.GetX(0);
        double ya = points.GetY(0);
        ax = Math.cos(a) * xa - Math.sin(a) * ya;
        ay = Math.sin(a) * xa + Math.cos(a) * ya;
        int x = atx + (int) (ax);
        int y = aty + (int) (ay);
        int xs = atx - (int) (ax);
        int ys = aty - (int) (ay);
        int sx = x;
        int sy = y;
        /*double oscx=oscilateX/(double)amplitudeX;
         double oscy=oscilateY/(double)amplitudeY;
         double ox=oscx;
         double oy=oscy;
         double oLimX=amplitudeX;
         double oLimY=amplitudeY;
         * 
         */
        double osc = oscilate / (double) amplitude;
        double o = osc;
        double oLim = amplitude;
        for (int p = 1; p < points.Size(); p++) {

            xa = (double) (points.GetX(p)) + o;
            ya = (double) (points.GetY(p)) + o;
            ax = Math.cos(a) * xa - Math.sin(a) * ya;
            ay = Math.sin(a) * xa + Math.cos(a) * ya;

            int nx = atx + (int) (ax);
            int ny = aty + (int) (ay);
            //
            DrawLine(x, y, nx, ny, value, fold, discard, effect);
            x = nx;
            y = ny;
            a += rot;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
            /*ox+=oscx;
             oy+=oscy;
             if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
             if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/

        }
        if (symx) {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            DrawLine(sx, sy, xs, sy, value, fold, discard, effect);

            x = atx - points.GetX(0);
            y = aty + points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx - (int) (ax);
                int ny = aty + (int) (ay);
                //
                DrawLine(x, y, nx, ny, value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx-points.GetX(end),aty+points.GetY(end),value,fold,discard,effect);
        } else {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            DrawLine(sx, sx, sx, ys, value, fold, discard, effect);

            x = atx + points.GetX(0);
            y = aty - points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx + (int) (ax);
                int ny = aty - (int) (ay);
                //
                DrawLine(x, y, nx, ny, value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx+points.GetX(end),aty-points.GetY(end),value,fold,discard,effect);

        }
    }

    void DrawSymetricCircles(DNA points, int atx, int aty, double baseRadius, double startAngle, double rotate, double oscilate, double amplitude, boolean symx, Pigment value, boolean fold, boolean discard, PixelEffect effect) {

        double a = startAngle / 57.295779513082320876798154814105;
        double rot = rotate / 57.295779513082320876798154814105;
        double start = a;
        double ax = 0;
        double ay = 0;
        double xa = points.GetX(0);
        double ya = points.GetY(0);
        ax = Math.cos(a) * xa - Math.sin(a) * ya;
        ay = Math.sin(a) * xa + Math.cos(a) * ya;
        int x = atx + (int) (ax);
        int y = aty + (int) (ay);
        /*double oscx=oscilateX/(double)amplitudeX;
         double oscy=oscilateY/(double)amplitudeY;
         double ox=oscx;
         double oy=oscy;
         double oLimX=amplitudeX;
         double oLimY=amplitudeY;
         * 
         */
        double osc = oscilate / (double) amplitude;
        double o = osc;
        double oLim = amplitude;
        for (int p = 1; p < points.Size(); p++) {

            xa = (double) (points.GetX(p)) + o;
            ya = (double) (points.GetY(p)) + o;
            ax = Math.cos(a) * xa - Math.sin(a) * ya;
            ay = Math.sin(a) * xa + Math.cos(a) * ya;

            int nx = atx + (int) (ax);
            int ny = aty + (int) (ay);
            //
            DrawCircle(x, y, (int) (baseRadius / (1 + Math.sqrt(xa + ya - o - o))), value, fold, discard, effect);
            x = nx;
            y = ny;
            a += rot;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
            /*ox+=oscx;
             oy+=oscy;
             if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
             if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/

        }
        if (symx) {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sy,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx - points.GetX(0);
            y = aty + points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx - (int) (ax);
                int ny = aty + (int) (ay);
                //
                DrawCircle(x, y, (int) (baseRadius / (1 + Math.sqrt(xa + ya - o - o))), value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx-points.GetX(end),aty+points.GetY(end),value,fold,discard,effect);
        } else {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sx,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx + points.GetX(0);
            y = aty - points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx + (int) (ax);
                int ny = aty - (int) (ay);
                //
                DrawCircle(x, y, (int) (baseRadius / (1 + Math.sqrt(xa + ya - o - o))), value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx+points.GetX(end),aty-points.GetY(end),value,fold,discard,effect);

        }
    }

    void DrawSymetricPixels(DNA points, int atx, int aty, double startAngle, double rotate, double oscilate, double amplitude, boolean symx, Pigment value, boolean fold, boolean discard, PixelEffect effect) {

        double a = startAngle / 57.295779513082320876798154814105;
        double rot = rotate / 57.295779513082320876798154814105;
        double start = a;
        double ax = 0;
        double ay = 0;
        double xa = points.GetX(0);
        double ya = points.GetY(0);
        ax = Math.cos(a) * xa - Math.sin(a) * ya;
        ay = Math.sin(a) * xa + Math.cos(a) * ya;
        int x = atx + (int) (ax);
        int y = aty + (int) (ay);
        /*double oscx=oscilateX/(double)amplitudeX;
         double oscy=oscilateY/(double)amplitudeY;
         double ox=oscx;
         double oy=oscy;
         double oLimX=amplitudeX;
         double oLimY=amplitudeY;
         * 
         */
        double osc = oscilate / (double) amplitude;
        double o = osc;
        double oLim = amplitude;
        for (int p = 1; p < points.Size(); p++) {

            xa = (double) (points.GetX(p)) + o;
            ya = (double) (points.GetY(p)) + o;
            ax = Math.cos(a) * xa - Math.sin(a) * ya;
            ay = Math.sin(a) * xa + Math.cos(a) * ya;

            int nx = atx + (int) (ax);
            int ny = aty + (int) (ay);
            //
            EffectPixel(x, y, value, fold, discard, effect);
            x = nx;
            y = ny;
            a += rot;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
            /*ox+=oscx;
             oy+=oscy;
             if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
             if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/

        }
        if (symx) {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sy,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx - points.GetX(0);
            y = aty + points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx - (int) (ax);
                int ny = aty + (int) (ay);
                //
                EffectPixel(x, y, value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx-points.GetX(end),aty+points.GetY(end),value,fold,discard,effect);
        } else {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sx,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx + points.GetX(0);
            y = aty - points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx + (int) (ax);
                int ny = aty - (int) (ay);
                //
                EffectPixel(x, y, value, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx+points.GetX(end),aty-points.GetY(end),value,fold,discard,effect);

        }
    }

    void DrawSymetricDots(DNA points, int atx, int aty, double startAngle, double rotate, double oscilate, double amplitude, boolean symx, Pigment highlight, Pigment value, Pigment shade, SkinData derm, boolean fold, boolean discard, PixelEffect effect) {

        double a = startAngle / 57.295779513082320876798154814105;
        double rot = rotate / 57.295779513082320876798154814105;
        double start = a;
        double ax = 0;
        double ay = 0;
        double xa = points.GetX(0);
        double ya = points.GetY(0);
        ax = Math.cos(a) * xa - Math.sin(a) * ya;
        ay = Math.sin(a) * xa + Math.cos(a) * ya;
        int x = atx + (int) (ax);
        int y = aty + (int) (ay);
        /*double oscx=oscilateX/(double)amplitudeX;
         double oscy=oscilateY/(double)amplitudeY;
         double ox=oscx;
         double oy=oscy;
         double oLimX=amplitudeX;
         double oLimY=amplitudeY;
         * 
         */
        double osc = oscilate / (double) amplitude;
        double o = osc;
        double oLim = amplitude;
        for (int p = 1; p < points.Size(); p++) {

            xa = (double) (points.GetX(p)) + o;
            ya = (double) (points.GetY(p)) + o;
            ax = Math.cos(a) * xa - Math.sin(a) * ya;
            ay = Math.sin(a) * xa + Math.cos(a) * ya;

            int nx = atx + (int) (ax);
            int ny = aty + (int) (ay);
            //
            //EffectPixel(x,y,value,fold,discard,effect);
            Skin.Texture(this, x, x, y, highlight, value, shade, derm, fold, discard, effect);
            x = nx;
            y = ny;
            a += rot;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
            /*ox+=oscx;
             oy+=oscy;
             if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
             if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/

        }
        if (symx) {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sy,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx - points.GetX(0);
            y = aty + points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx - (int) (ax);
                int ny = aty + (int) (ay);
                //
                Skin.Texture(this, x, x, y, highlight, value, shade, derm, fold, discard, effect);

                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx-points.GetX(end),aty+points.GetY(end),value,fold,discard,effect);
        } else {
            o = osc;
            //ox=oscx;
            //oy=oscy;
            a = start;
            //DrawCircle(sx,sx,(int)Math.sqrt(sx+sy),value,fold,discard,effect);

            x = atx + points.GetX(0);
            y = aty - points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {

                xa = (double) (points.GetX(p)) + o;
                ya = (double) (points.GetY(p)) + o;
                ax = Math.cos(a) * xa - Math.sin(a) * ya;
                ay = Math.sin(a) * xa + Math.cos(a) * ya;
                int nx = atx + (int) (ax);
                int ny = aty - (int) (ay);
                //
                Skin.Texture(this, x, x, y, highlight, value, shade, derm, fold, discard, effect);
                x = nx;
                y = ny;
                a += rot;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
                /*ox+=oscx;
                 oy+=oscy;
                 if(ox>oLimX || ox<-oLimX){oscx=-oscx;}
                 if(oy>oLimY || oy<-oLimY){oscy=-oscy;}*/
            }
            //int end=points.Size()-1;
            //DrawLine(atx+points.GetX(end),aty+points.GetY(end),atx+points.GetX(end),aty-points.GetY(end),value,fold,discard,effect);

        }
    }

    void DrawSymetricLines(DNA points, int atx, int aty, double startAngle, double rotate, int oscilate, double amplitude, boolean symx, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {

        double a = startAngle / 57.295779513082320876798154814105;
        double rot = rotate / 57.295779513082320876798154814105;
        double start = a;
        double ax = 0;
        double ay = 0;
        double xa = points.GetX(0);
        double ya = points.GetY(0);
        ax = Math.cos(a) * xa - Math.sin(a) * ya;
        ay = Math.sin(a) * xa + Math.cos(a) * ya;
        int x = atx + (int) (ax);
        int y = aty + (int) (ay);
        double sz = points.Size() * 0.99609375;//(255.0/256.0);
        double a0 = from.Alpha() / sz;
        double r0 = from.Red() / sz;
        double g0 = from.Green() / sz;
        double b0 = from.Blue() / sz;
        double a1 = to.Alpha() / sz;
        double r1 = to.Red() / sz;
        double g1 = to.Green() / sz;
        double b1 = to.Blue() / sz;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        for (int p = 1; p < points.Size(); p++) {

            ax = points.GetX(p);
            ay = points.GetY(p);
            int nx = atx + (int) (ax);
            int ny = aty + (int) (ay);
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            DrawLine(x, y, nx, ny, value, fold, discard, effect);
            x = nx;
            y = ny;
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;
        }
        if (symx) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            DrawLine(atx + points.GetX(0), aty + points.GetY(0), atx - points.GetX(0), aty + points.GetY(0), value, fold, discard, effect);

            ai = from.Alpha();
            ri = from.Red();
            gi = from.Green();
            bi = from.Blue();
            x = atx - points.GetX(0);
            y = aty + points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {
                ax = points.GetX(p);
                ay = points.GetY(p);
                int nx = atx + (int) (ax);
                int ny = aty + (int) (ay);
                //
                value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);

                DrawLine(x, y, nx, ny, value, fold, discard, effect);
                x = nx;
                y = ny;
                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
            }
            int end = points.Size() - 1;
            DrawLine(atx + points.GetX(end), aty + points.GetY(end), atx - points.GetX(end), aty + points.GetY(end), value, fold, discard, effect);
        } else {

            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            DrawLine(atx + points.GetX(0), aty + points.GetY(0), atx + points.GetX(0), aty - points.GetY(0), value, fold, discard, effect);

            ai = from.Alpha();
            ri = from.Red();
            gi = from.Green();
            bi = from.Blue();
            x = atx + points.GetX(0);
            y = aty - points.GetY(0);

            for (int p = 1; p < points.Size(); p++) {
                ax = points.GetX(p);
                ay = points.GetY(p);
                int nx = atx + (int) (ax);
                int ny = aty + (int) (ay);
                //
                value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);

                DrawLine(x, y, nx, ny, value, fold, discard, effect);
                x = nx;
                y = ny;
                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
            }
            int end = points.Size() - 1;
            DrawLine(atx + points.GetX(end), aty + points.GetY(end), atx + points.GetX(end), aty - points.GetY(end), value, fold, discard, effect);

        }
    }

    void DrawCircle(int atx, int aty, int r, Pigment value, boolean fold, boolean discard, boolean avarage) {

        if (r != 0) {
            r = Math.abs(r);

            double a = 0;

            int x1 = 0;
            int y1 = 0;
            double d = r << 1;
            double inc = 2.2214414690791831235079404950303 / d;
            double p4 = 0.78539816339744830961566084581988;
            for (; a < p4; a += inc) {

                x1 = (int) (r * Math.cos(a));
                y1 = (int) (r * Math.sin(a));
                int x = atx + x1;
                int y = aty + y1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
                x = atx - x1;
                y = aty - y1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
                x = atx + x1;
                y = aty - y1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
                x = atx - x1;
                y = aty + y1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }

                x = atx + y1;
                y = aty + x1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
                x = atx - y1;
                y = aty - x1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }

                x = atx + y1;
                y = aty - x1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
                x = atx - y1;
                y = aty + x1;
                this.SetPixel(x, y, value, fold, discard);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x, y - 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, PixelEffect.Average);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, PixelEffect.Average);

                }
            }
        } else {
            this.SetPixel(atx, aty, value, fold, discard);
            if (avarage) {
                this.EffectPixel(atx - 1, aty, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx - 1, aty + 1, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx - 1, aty - 1, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx, aty + 1, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx, aty - 1, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx + 1, aty, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx + 1, aty + 1, value, fold, discard, PixelEffect.Average);
                this.EffectPixel(atx + 1, aty - 1, value, fold, discard, PixelEffect.Average);

            }

        }
    }

    void DrawCircle(int atx, int aty, int r, Pigment value, boolean fold, boolean discard, PixelEffect effect, boolean avarage) {

        if (r != 0) {
            r = Math.abs(r);

            double a = 0;

            int x1 = 0;
            int y1 = 0;
            double d = r << 1;
            double inc = 2.2214414690791831235079404950303 / d;
            double p4 = 0.78539816339744830961566084581988;
            for (; a < p4; a += inc) {

                x1 = (int) (r * Math.cos(a));
                y1 = (int) (r * Math.sin(a));
                int x = atx + x1;
                int y = aty + y1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
                x = atx - x1;
                y = aty - y1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
                x = atx + x1;
                y = aty - y1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
                x = atx - x1;
                y = aty + y1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }

                x = atx + y1;
                y = aty + x1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
                x = atx - y1;
                y = aty - x1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }

                x = atx + y1;
                y = aty - x1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
                x = atx - y1;
                y = aty + x1;
                this.EffectPixel(x, y, value, fold, discard, effect);
                if (avarage) {
                    this.EffectPixel(x - 1, y, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x - 1, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x, y - 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y + 1, value, fold, discard, effect);
                    this.EffectPixel(x + 1, y - 1, value, fold, discard, effect);

                }
            }
            this.EffectPixel(atx, aty, value, fold, discard, effect);
            if (avarage) {
                this.EffectPixel(atx - 1, aty, value, fold, discard, effect);
                this.EffectPixel(atx - 1, aty + 1, value, fold, discard, effect);
                this.EffectPixel(atx - 1, aty - 1, value, fold, discard, effect);
                this.EffectPixel(atx, aty + 1, value, fold, discard, effect);
                this.EffectPixel(atx, aty - 1, value, fold, discard, effect);
                this.EffectPixel(atx + 1, aty, value, fold, discard, effect);
                this.EffectPixel(atx + 1, aty + 1, value, fold, discard, effect);
                this.EffectPixel(atx + 1, aty - 1, value, fold, discard, effect);

            }

        }
    }

    void DrawCircle(int atx, int aty, int r, Pigment value[], boolean fold, boolean discard) {

        if (r != 0) {
            r = Math.abs(r);

            double a = 0;
            int at = 0;
            int x1 = 0;
            int y1 = 0;
            double d = r << 1;
            double inc = 2.2214414690791831235079404950303 / d;
            double p4 = 0.78539816339744830961566084581988;
            for (; a < p4; a += inc) {

                x1 = (int) (r * Math.cos(a));
                y1 = (int) (r * Math.sin(a));

                this.SetPixel(atx + x1, aty + y1, value[at], fold, discard);
                this.SetPixel(atx + y1, aty + x1, value[at], fold, discard);
                this.SetPixel(atx + x1, aty - y1, value[at], fold, discard);
                this.SetPixel(atx + y1, aty - x1, value[at], fold, discard);
                this.SetPixel(atx - x1, aty + y1, value[at], fold, discard);
                this.SetPixel(atx - y1, aty + x1, value[at], fold, discard);
                this.SetPixel(atx - x1, aty - y1, value[at], fold, discard);
                this.SetPixel(atx - y1, aty - x1, value[at], fold, discard);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        } else {
            this.SetPixel(atx, aty, value[0], fold, discard);
        }
    }

    void DrawCircle(int atx, int aty, int r, Pigment value, boolean fold, boolean discard, PixelEffect effect) {

        if (r != 0) {
            r = Math.abs(r);

            double a = 0;

            int x1 = 0;
            int y1 = 0;
            double d = r << 1;
            double inc = 2.2214414690791831235079404950303 / d;
            double p4 = 0.78539816339744830961566084581988;
            for (; a < p4; a += inc) {

                x1 = (int) (r * Math.cos(a));
                y1 = (int) (r * Math.sin(a));

                this.EffectPixel(atx + x1, aty + y1, value, fold, discard, effect);
                this.EffectPixel(atx + y1, aty + x1, value, fold, discard, effect);
                this.EffectPixel(atx + x1, aty - y1, value, fold, discard, effect);
                this.EffectPixel(atx + y1, aty - x1, value, fold, discard, effect);
                this.EffectPixel(atx - x1, aty + y1, value, fold, discard, effect);
                this.EffectPixel(atx - y1, aty + x1, value, fold, discard, effect);
                this.EffectPixel(atx - x1, aty - y1, value, fold, discard, effect);
                this.EffectPixel(atx - y1, aty - x1, value, fold, discard, effect);
            }
        } else {
            this.EffectPixel(atx, aty, value, fold, discard, effect);

        }
    }

    void DrawCircle(int atx, int aty, int r, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {

        if (r != 0) {
            r = Math.abs(r);

            double a = 0;
            int at = 0;
            int x1 = 0;
            int y1 = 0;
            double d = r << 1;
            double inc = 2.2214414690791831235079404950303 / d;
            double p4 = 0.78539816339744830961566084581988;
            for (; a < p4; a += inc) {

                x1 = (int) (r * Math.cos(a));
                y1 = (int) (r * Math.sin(a));

                this.EffectPixel(atx + x1, aty + y1, value[at], fold, discard, effect);
                this.EffectPixel(atx + y1, aty + x1, value[at], fold, discard, effect);
                this.EffectPixel(atx + x1, aty - y1, value[at], fold, discard, effect);
                this.EffectPixel(atx + y1, aty - x1, value[at], fold, discard, effect);
                this.EffectPixel(atx - x1, aty + y1, value[at], fold, discard, effect);
                this.EffectPixel(atx - y1, aty + x1, value[at], fold, discard, effect);
                this.EffectPixel(atx - x1, aty - y1, value[at], fold, discard, effect);
                this.EffectPixel(atx - y1, aty - x1, value[at], fold, discard, effect);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
            }
        } else {

            this.EffectPixel(atx, aty, value[0], fold, discard, effect);

        }
    }

    void DrawPolygon(int atx, int aty, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, boolean average) {

        double startAng = startAngle / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / sides;
        double ang2 = ang / 2.0;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        int x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
        int y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
            int y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
            this.DrawLine(x0, y0, x1, y1, value, fold, discard, average);
            x0 = x1;
            y0 = y1;
        }
        if (star) {
            a = startAng + ang2;
            int n = 0;
            if (opposing) {
                n = 1;
            }
            if (n == 0) {
                x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
                y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
            }
            for (; a < ps2; a += ang2, n++) {

                int x1;
                int y1;
                if (n % 2 == 0) {
                    x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
                } else {
                    x1 = atx + (int) (r2 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r2 * Math.sin(a) + 0.5);
                }
                this.DrawLine(x0, y0, x1, y1, value, fold, discard, average);
                x0 = x1;
                y0 = y1;
            }
        }
    }

    void DrawPolygon(int atx, int aty, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect, boolean average) {

        double startAng = startAngle / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / sides;
        double ang2 = ang / 2.0;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        int x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
        int y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
            int y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
            this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect, average);
            x0 = x1;
            y0 = y1;
        }
        if (star) {
            a = startAng + ang2;
            int n = 0;
            if (opposing) {
                n = 1;
            }
            if (n == 0) {
                x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
                y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
            }
            for (; a < ps2; a += ang2, n++) {

                int x1;
                int y1;
                if (n % 2 == 0) {
                    x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
                } else {
                    x1 = atx + (int) (r2 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r2 * Math.sin(a) + 0.5);
                }
                this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect, average);
                x0 = x1;
                y0 = y1;
            }
        }
    }

    void DrawPolygon(int atx, int aty, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard, boolean average) {

        double startAng = startAngle / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / sides;
        double ang2 = ang / 2.0;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        int x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
        int y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
            int y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
            this.DrawLine(x0, y0, x1, y1, value, fold, discard, average);
            x0 = x1;
            y0 = y1;
        }
        if (star) {
            a = startAng + ang2;
            int n = 0;
            if (opposing) {
                n = 1;
            }
            if (n == 0) {
                x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
                y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
            }
            for (; a < ps2; a += ang2, n++) {

                int x1;
                int y1;
                if (n % 2 == 0) {
                    x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
                } else {
                    x1 = atx + (int) (r2 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r2 * Math.sin(a) + 0.5);
                }
                this.DrawLine(x0, y0, x1, y1, value, fold, discard, average);
                x0 = x1;
                y0 = y1;
            }
        }
    }

    void DrawPolygon(int atx, int aty, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect) {

        double startAng = startAngle / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / sides;
        double ang2 = ang / 2.0;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        int x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
        int y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
            int y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
            this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect);
            x0 = x1;
            y0 = y1;
        }
        if (star) {
            a = startAng + ang2;
            int n = 0;
            if (opposing) {
                n = 1;
            }
            if (n == 0) {
                x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
                y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
            }
            for (; a < ps2; a += ang2, n++) {

                int x1;
                int y1;
                if (n % 2 == 0) {
                    x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
                } else {
                    x1 = atx + (int) (r2 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r2 * Math.sin(a) + 0.5);
                }
                this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect);
                x0 = x1;
                y0 = y1;
            }
        }
    }

    void DrawPolygon(int atx, int aty, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {

        double startAng = startAngle / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / sides;
        double ang2 = ang / 2.0;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        int x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
        int y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
            int y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
            this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect);
            x0 = x1;
            y0 = y1;
        }
        if (star) {
            a = startAng + ang2;
            int n = 0;
            if (opposing) {
                n = 1;
            }
            if (n == 0) {
                x0 = atx + (int) (r1 * Math.cos(a) + 0.5);
                y0 = aty + (int) (r1 * Math.sin(a) + 0.5);
            }
            for (; a < ps2; a += ang2, n++) {

                int x1;
                int y1;
                if (n % 2 == 0) {
                    x1 = atx + (int) (r1 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r1 * Math.sin(a) + 0.5);
                } else {
                    x1 = atx + (int) (r2 * Math.cos(a) + 0.5);
                    y1 = aty + (int) (r2 * Math.sin(a) + 0.5);
                }
                this.DrawLine(x0, y0, x1, y1, value, fold, discard, effect);
                x0 = x1;
                y0 = y1;
            }
        }
    }

    void DrawPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, Pigment value, boolean fold, boolean discard, boolean average) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawCircle(x1, y1, radius, value, fold, discard, average);
        }
    }

    void DrawPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, Pigment value[], boolean fold, boolean discard) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawCircle(x1, y1, radius, value, fold, discard);
        }
    }

    void DrawPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawCircle(x1, y1, radius, value, fold, discard, effect);
        }
    }

    void DrawPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawCircle(x1, y1, radius, value, fold, discard, effect);
        }
    }

    void DrawPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, boolean average) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawPolygon(x1, y1, r1, r2, sides, startAngle, star, opposing, value, fold, discard, average);
            startAngle += angInc;
        }
    }

    void DrawPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawPolygon(x1, y1, r1, r2, sides, startAngle, star, opposing, value, fold, discard, effect);
            startAngle += angInc;
        }
    }

    void DrawPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int r2, int sides, double startAngle, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            this.DrawPolygon(x1, y1, r1, r2, sides, startAngle, star, opposing, value, fold, discard, effect);
            startAngle += angInc;
        }
    }

    void FillPolygon(int atx, int aty, int r1, int r2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r2 - r + o), sides, startAngle, star, opposing, value, fold, discard, true);

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value, fold, discard, true);

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int rad1, int rad2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (rad2 - r + o), sides, startAngle, star, opposing, value, fold, discard, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int rad1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (rad1 + r + o), sides, startAngle, star, opposing, value, fold, discard, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int r2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r2 - r + o), sides, startAngle, star, opposing, value[at], fold, discard, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value[at], fold, discard, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int r2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r2 - r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int rad1, int rad2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (rad2 - r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;
            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }

        }
    }

    void FillPolygon(int atx, int aty, int rad1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (rad1 + r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;
            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }

        }
    }

    void FillPolygon(int atx, int aty, int r1, int r2, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, (int) (r + o), (int) (r2 - r + o), sides, startAngle, star, opposing, value[at], fold, discard, effect, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }
            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolygon(int atx, int aty, int r1, int step, int sides, double startAngle, double rotate, boolean star, int oscilate, double amplitude, boolean opposing, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawPolygon(atx, aty, r, r1 + r, sides, startAngle, star, opposing, value[at], fold, discard, effect, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }

            startAngle += rotate;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = r1; r > 0; r -= step) {
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value, fold, discard, true);
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            int at = 0;
            for (int r = r1; r > 0; r -= step) {
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value[at], fold, discard, true);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int rad1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        for (; a < ps2; a += ang) {
            double ai = from.Alpha();
            double ri = from.Red();
            double gi = from.Green();
            double bi = from.Blue();
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = rad1; r > 0; r -= step) {
                Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (rad1 + r + o), sides, startAngle, star, opposing, value, fold, discard, true);
                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = r1; r > 0; r -= step) {
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int r1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double angInc = 360 / angularSides;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            int at = 0;
            for (int r = r1; r > 0; r -= step) {
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (r1 + r + o), sides, startAngle, star, opposing, value[at], fold, discard, effect, true);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillPolyPolygons(int atx, int aty, int polyRadius, int polys, int polyStart, int angularSides, int rad1, int step, int sides, double startAngle, double rotate, int oscilate, double amplitude, boolean star, boolean opposing, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / (double) polys;
        double angInc = 360 / (double) (angularSides);
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        for (; a < ps2; a += ang) {
            double ai = from.Alpha();
            double ri = from.Red();
            double gi = from.Green();
            double bi = from.Blue();
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = rad1; r > 0; r -= step) {
                Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
                this.DrawPolygon(x1, y1, (int) (r + o), (int) (rad1 + r + o), sides, startAngle, star, opposing, value, fold, discard, effect, true);

                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
                startAngle += rotate;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
            startAngle += angInc;
        }
    }

    void FillCircle(int atx, int aty, int r1, int step, int oscilate, double amplitude, Pigment value, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawCircle(atx, aty, (int) (r + o), value, fold, discard, true);
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillCircle(int atx, int aty, int r1, int step, int oscilate, double amplitude, Pigment value[], boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawCircle(atx, aty, (int) (r + o), value[at], fold, discard, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillCircle(int atx, int aty, int rad1, int step, int oscilate, double amplitude, Pigment from, Pigment to, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = amplitude;
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawCircle(atx, aty, (int) (r + o), value, fold, discard, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillCircle(int atx, int aty, int r1, int step, int oscilate, double amplitude, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawCircle(atx, aty, (int) (r + o), value, fold, discard, effect, true);
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillCircle(int atx, int aty, int r1, int step, int oscilate, double amplitude, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        int at = 0;
        double osc = (double) (oscilate) / (double) r1;
        double o = osc;
        double oLim = amplitude;
        for (int r = r1; r > 0; r -= step) {
            //new Pigment(128+(r*4),128+(r*4),128+(r*4))
            this.DrawCircle(atx, aty, (int) (r + o), value[at], fold, discard, effect, true);
            at++;
            if (at >= value.length) {
                at = 0;
            }
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillCircle(int atx, int aty, int rad1, int step, int oscilate, double amplitude, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double osc = (double) (oscilate) / (double) rad1;
        double o = osc;
        double oLim = oscilate / amplitude;
        double a0 = from.Alpha() / rad1;
        double r0 = from.Red() / rad1;
        double g0 = from.Green() / rad1;
        double b0 = from.Blue() / rad1;
        double a1 = to.Alpha() / rad1;
        double r1 = to.Red() / rad1;
        double g1 = to.Green() / rad1;
        double b1 = to.Blue() / rad1;
        double ai = from.Alpha();
        double ri = from.Red();
        double gi = from.Green();
        double bi = from.Blue();
        step = Math.abs(step);
        for (int r = rad1; r > 0; r -= step) {
            Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
            this.DrawCircle(atx, aty, (int) (r + o), value, fold, discard, effect, true);
            ai -= a0;
            ri -= r0;
            gi -= g0;
            bi -= b0;
            ai += a1;
            ri += r1;
            gi += g1;
            bi += b1;
            o += osc;
            if (o > oLim || o < -oLim) {
                osc = -osc;
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment value, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) radius;
        double oLim = amplitude;
        double o = osc;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = radius; r > 0; r -= step) {
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value, fold, discard, true);
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment from, Pigment to, boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double a0 = from.Alpha() / radius;
        double r0 = from.Red() / radius;
        double g0 = from.Green() / radius;
        double b0 = from.Blue() / radius;
        double a1 = to.Alpha() / radius;
        double r1 = to.Red() / radius;
        double g1 = to.Green() / radius;
        double b1 = to.Blue() / radius;
        for (; a < ps2; a += ang) {
            double ai = from.Alpha();
            double ri = from.Red();
            double gi = from.Green();
            double bi = from.Blue();
            double osc = (double) (oscilate) / (double) radius;
            double oLim = amplitude;
            double o = osc;
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = radius; r > 0; r -= step) {
                Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value, fold, discard, true);
                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment value[], boolean fold, boolean discard) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) radius;
        double oLim = amplitude;
        double o = osc;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            int at = 0;
            for (int r = radius; r > 0; r -= step) {
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value[at], fold, discard, true);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment value, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;

        double osc = (double) (oscilate) / (double) radius;
        double oLim = amplitude;
        double o = osc;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = radius; r > 0; r -= step) {
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value, fold, discard, effect, true);
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment from, Pigment to, boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double a0 = from.Alpha() / radius;
        double r0 = from.Red() / radius;
        double g0 = from.Green() / radius;
        double b0 = from.Blue() / radius;
        double a1 = to.Alpha() / radius;
        double r1 = to.Red() / radius;
        double g1 = to.Green() / radius;
        double b1 = to.Blue() / radius;
        double osc = (double) (oscilate) / (double) radius;
        double oLim = amplitude;
        double o = osc;
        for (; a < ps2; a += ang) {
            double ai = from.Alpha();
            double ri = from.Red();
            double gi = from.Green();
            double bi = from.Blue();
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            for (int r = radius; r > 0; r -= step) {
                Pigment value = new Pigment((int) ai, (int) ri, (int) gi, (int) bi);
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value, fold, discard, effect, true);
                ai -= a0;
                ri -= r0;
                gi -= g0;
                bi -= b0;
                ai += a1;
                ri += r1;
                gi += g1;
                bi += b1;
                o += osc;
                if (o > oLim || o < -oLim) {
                    osc = -osc;
                }
            }
        }
    }

    void FillPolyCircles(int atx, int aty, int polyRadius, int polys, int polyStart, int radius, int step, int oscilate, double amplitude, Pigment value[], boolean fold, boolean discard, PixelEffect effect) {
        step = Math.abs(step);
        if (step == 0) {
            step = 1;
        }
        double startAng = polyStart / 57.295779513082320876798154814105;
        double ang = 6.283185307179586476925286766559 / polys;
        double ps2 = 6.283185307179586476925286766559 + startAng;
        double a = startAng;
        a += ang;
        ps2 += ang;
        double osc = (double) (oscilate) / (double) radius;
        double oLim = amplitude;
        double o = osc;
        for (; a < ps2; a += ang) {
            int x1 = atx + (int) (polyRadius * Math.cos(a) + 0.5);
            int y1 = aty + (int) (polyRadius * Math.sin(a) + 0.5);
            int at = 0;
            for (int r = radius; r > 0; r -= step) {
                //new Pigment(128+(r*4),128+(r*4),128+(r*4))
                this.DrawCircle(x1, y1, (int) (r + o), value[at], fold, discard, effect, true);
                at++;
                if (at >= value.length) {
                    at = 0;
                }
                o += osc;
                if (o > oLim) {
                    osc = -osc;
                }
            }
        }
    }

    java.awt.image.BufferedImage ToBufferedImage() {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        //java.awt.Graphics2D g=image.createGraphics();
        if (Game.gc != null) {
            int trans = image.getColorModel().getTransparency();
            image = Game.gc.createCompatibleImage(image.getWidth(), image.getHeight(), trans);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, data[x][y].ToRGB());
            }

        }
        return image;
    }

    void ToBufferedImage(java.awt.image.BufferedImage image) {
        //java.awt.image.BufferedImage image=new java.awt.image.BufferedImage(width, height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
        //java.awt.Graphics2D g=image.createGraphics();
        if (Game.gc != null && image == null) {
            image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            int trans = image.getColorModel().getTransparency();
            image = Game.gc.createCompatibleImage(image.getWidth(), image.getHeight(), trans);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, data[x][y].ToRGB());
            }

        }
    }

    java.awt.image.BufferedImage ToBitMapBufferedImage() {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        //java.awt.Graphics2D g=image.createGraphics();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, data[x][y].ToRGB());
            }

        }
        return image;
    }
}
