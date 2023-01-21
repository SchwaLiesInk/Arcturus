/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn
 */
public class EffectMap extends ColorMap {

    public EffectMap(int w, int h) {
        super(w, h);
    }

    public EffectMap(int w, int h, Pigment fillColor) {
        super(w, h, fillColor);
    }

    void SolidColor(int limit) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    c.Alpha(255);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void SolidColor(int limit, int alpha) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    c.Alpha(alpha);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void AddColor(int limit, int alpha) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    c.Alpha(alpha);
                    c.Add(c);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void GlowColor(int limit, int alpha) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    c.Alpha(alpha);
                    c.Add(c);
                    c.Add(c);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }
                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void MetalicColor(int limit, int alpha) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    Pigment p = new Pigment((255 - c.Alpha()) * c.Red(), (255 - c.Alpha()) * c.Green(), (255 - c.Alpha()) * c.Blue());
                    Pigment d = new Pigment(c);
                    c.Average(p);
                    c.Multiply(p);
                    c.Average(d);
                    c.Alpha(alpha);
                    c.Add(c);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void DarkenColor(int limit, int alpha) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    Pigment p = new Pigment((255 - c.Alpha()) * c.Red(), (255 - c.Alpha()) * c.Green(), (255 - c.Alpha()) * c.Blue());
                    c.Multiply(p);
                    c.Alpha(alpha);
                    c.Add(c);
                    if (c.Red() == 0) {
                        c.Red(limit);
                    }
                    if (c.Green() == 0) {
                        c.Green(limit);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(limit);
                    }
                } else {
                    c.Alpha(0);
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void EffectColor(int limit, int alpha, PixelEffect effect) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c = this.data[x][y];
                if (c.Alpha() > limit) {
                    c.Alpha(alpha);

                    switch (effect) {
                        case Add: {
                            c.Add(c);
                            break;
                        }
                        case Average: {
                            c.Average(c);
                            break;
                        }
                        case Subtract: {
                            c.Subtract(c);
                            break;
                        }
                        case Multiply: {
                            c.Multiply(c);
                            break;
                        }
                        case Divide: {
                            c.Divide(c);
                            break;
                        }
                        case Tint: {
                            c.Tint(c);
                            break;
                        }
                        case Hint: {
                            c.Hint(c);
                            break;
                        }
                        case Alpha: {
                            c.Alpha(c);
                            break;
                        }
                        case Negative: {
                            c.Negative(c);
                            break;
                        }
                    }
                } else {
                    c.Alpha(0);
                    if (c.Red() == 0) {
                        c.Red(1);
                    }
                    if (c.Green() == 0) {
                        c.Green(1);
                    }
                    if (c.Blue() == 0) {
                        c.Blue(1);
                    }
                }

                this.data[x][y] = new Pigment(c);
            }

        }
    }

    void Effect(int offsetX, int offsetY, PixelEffect effect, double alphaIntensity, double redIntensity, double greenIntensity, double blueIntensity) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int a = data[x][y].Alpha();
                int r = data[x][y].Red();
                int g = data[x][y].Green();
                int b = data[x][y].Blue();
                buffer[x][y] = new Pigment((int) (a * alphaIntensity), (int) (r * redIntensity), (int) (g * greenIntensity), (int) (b * blueIntensity));
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x + offsetX, y + offsetY, buffer[x][y], true, false, effect);
            }
        }
    }

    void Stain(double a, double r, double g, double b, PixelEffect effect) {

        this.Effect(-1, -1, effect, a, r, g, b);
        this.Effect(-1, 0, effect, a, r, g, b);
        this.Effect(-1, 1, effect, a, r, g, b);
        this.Effect(0, -1, effect, a, r, g, b);
        this.Effect(0, 0, effect, a, r, g, b);
        this.Effect(0, 1, effect, a, r, g, b);
        this.Effect(1, -1, effect, a, r, g, b);
        this.Effect(1, 0, effect, a, r, g, b);
        this.Effect(1, 1, effect, a, r, g, b);
    }

    void Blur(PixelEffect effect) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, buffer[x][y], true, false, effect);
            }
        }
    }

    void Blur(PixelEffect effect, PixelEffect blur) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, effect, blur);
            }
        }
    }

    void BlurLess(PixelEffect effect, PixelEffect blur) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        switch (effect) {
                            case Add: {
                                c[i][j].Add(buffer[x][y]);
                                break;
                            }
                            case Average: {
                                c[i][j].Average(buffer[x][y]);
                                break;
                            }
                            case Subtract: {
                                c[i][j].Subtract(buffer[x][y]);
                                break;
                            }
                            case Multiply: {
                                c[i][j].Multiply(buffer[x][y]);
                                break;
                            }
                            case Divide: {
                                c[i][j].Divide(buffer[x][y]);
                                break;
                            }
                            case Tint: {
                                c[i][j].Tint(buffer[x][y]);
                                break;
                            }
                            case Hint: {
                                c[i][j].Hint(buffer[x][y]);
                                break;
                            }
                            case Alpha: {
                                c[i][j].Alpha(buffer[x][y]);
                                break;
                            }
                            case Negative: {
                                c[i][j].Negative(buffer[x][y]);
                                break;
                            }
                        }
                    }
                }
                a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);

            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, blur);
            }
        }
    }

    void BlurLess(PixelEffect effect, PixelEffect blur1, PixelEffect blur2) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        switch (effect) {
                            case Add: {
                                c[i][j].Add(buffer[x][y]);
                                break;
                            }
                            case Average: {
                                c[i][j].Average(buffer[x][y]);
                                break;
                            }
                            case Subtract: {
                                c[i][j].Subtract(buffer[x][y]);
                                break;
                            }
                            case Multiply: {
                                c[i][j].Multiply(buffer[x][y]);
                                break;
                            }
                            case Divide: {
                                c[i][j].Divide(buffer[x][y]);
                                break;
                            }
                            case Tint: {
                                c[i][j].Tint(buffer[x][y]);
                                break;
                            }
                            case Hint: {
                                c[i][j].Hint(buffer[x][y]);
                                break;
                            }
                            case Alpha: {
                                c[i][j].Alpha(buffer[x][y]);
                                break;
                            }
                            case Negative: {
                                c[i][j].Negative(buffer[x][y]);
                                break;
                            }
                        }
                    }
                }
                a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);

            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, blur1, blur2);
            }
        }
    }

    void BlurLess(PixelEffect effects[][], PixelEffect blur1, PixelEffect blur2) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        switch (effects[i][j]) {
                            case Add: {
                                c[i][j].Add(buffer[x][y]);
                                break;
                            }
                            case Average: {
                                c[i][j].Average(buffer[x][y]);
                                break;
                            }
                            case Subtract: {
                                c[i][j].Subtract(buffer[x][y]);
                                break;
                            }
                            case Multiply: {
                                c[i][j].Multiply(buffer[x][y]);
                                break;
                            }
                            case Divide: {
                                c[i][j].Divide(buffer[x][y]);
                                break;
                            }
                            case Tint: {
                                c[i][j].Tint(buffer[x][y]);
                                break;
                            }
                            case Hint: {
                                c[i][j].Hint(buffer[x][y]);
                                break;
                            }
                            case Alpha: {
                                c[i][j].Alpha(buffer[x][y]);
                                break;
                            }
                            case Negative: {
                                c[i][j].Negative(buffer[x][y]);
                                break;
                            }
                        }
                    }
                }
                a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);

            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, blur1, blur2);
            }
        }
    }

    void BlurLess(PixelEffect effects[][], PixelEffect blur) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        switch (effects[i][j]) {
                            case Add: {
                                c[i][j].Add(buffer[x][y]);
                                break;
                            }
                            case Average: {
                                c[i][j].Average(buffer[x][y]);
                                break;
                            }
                            case Subtract: {
                                c[i][j].Subtract(buffer[x][y]);
                                break;
                            }
                            case Multiply: {
                                c[i][j].Multiply(buffer[x][y]);
                                break;
                            }
                            case Divide: {
                                c[i][j].Divide(buffer[x][y]);
                                break;
                            }
                            case Tint: {
                                c[i][j].Tint(buffer[x][y]);
                                break;
                            }
                            case Hint: {
                                c[i][j].Hint(buffer[x][y]);
                                break;
                            }
                            case Alpha: {
                                c[i][j].Alpha(buffer[x][y]);
                                break;
                            }
                            case Negative: {
                                c[i][j].Negative(buffer[x][y]);
                                break;
                            }
                        }
                    }
                }
                a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, blur);
            }
        }
    }

    void BlurMore(PixelEffect effect1, PixelEffect effect2, PixelEffect effect3, PixelEffect blur1, PixelEffect blur2, PixelEffect blur3) {
        Blur(effect1, blur1);
        Blur(effect2, blur2);
        Blur(effect3, blur3);
    }

    void BlurMore(PixelEffect effect1, PixelEffect effect2, PixelEffect effect3) {
        Blur(effect1);
        Blur(effect2);
        Blur(effect3);
    }

    void Smudge(PixelEffect effect) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x - 1, y - 1, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x - 1, y, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x - 1, y + 1, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x, y - 1, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x, y + 1, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x + 1, y - 1, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x + 1, y, buffer[x][y], true, false, effect);
                this.EffectPixel(x, y, x + 1, y + 1, buffer[x][y], true, false, effect);
            }
        }
    }

    void Smudge(PixelEffect effects[][]) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, x - 1, y - 1, buffer[x][y], true, false, effects[0][0]);
                this.EffectPixel(x, y, x - 1, y, buffer[x][y], true, false, effects[0][1]);
                this.EffectPixel(x, y, x - 1, y + 1, buffer[x][y], true, false, effects[0][2]);
                this.EffectPixel(x, y, x, y - 1, buffer[x][y], true, false, effects[1][0]);
                this.EffectPixel(x, y, x, y, buffer[x][y], true, false, effects[1][1]);
                this.EffectPixel(x, y, x, y + 1, buffer[x][y], true, false, effects[1][2]);
                this.EffectPixel(x, y, x + 1, y - 1, buffer[x][y], true, false, effects[2][0]);
                this.EffectPixel(x, y, x + 1, y, buffer[x][y], true, false, effects[2][1]);
                this.EffectPixel(x, y, x + 1, y + 1, buffer[x][y], true, false, effects[2][2]);
            }
        }
    }

    void DoubleBlur(PixelEffect effect, PixelEffect motion) {
        MotionBlur(-1, -1, effect, motion);
        MotionBlur(-1, 0, effect, motion);
        MotionBlur(-1, 1, effect, motion);
        MotionBlur(0, -1, effect, motion);
        MotionBlur(0, 0, effect, motion);
        MotionBlur(0, 1, effect, motion);
        MotionBlur(1, -1, effect, motion);
        MotionBlur(1, 0, effect, motion);
        MotionBlur(1, 1, effect, motion);
    }

    void MotionBlur(int nx, int ny, PixelEffect effect, PixelEffect motion) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, false, false);
                c[0][1] = GetPixel(x - 1, y, false, false);
                c[0][2] = GetPixel(x - 1, y + 1, false, false);
                c[1][0] = GetPixel(x, y - 1, false, false);
                c[1][1] = GetPixel(x, y, false, false);
                c[1][2] = GetPixel(x, y + 1, false, false);
                c[2][0] = GetPixel(x + 1, y - 1, false, false);
                c[2][1] = GetPixel(x + 1, y, false, false);
                c[2][2] = GetPixel(x + 1, y + 1, false, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        if (nx > 0) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    switch (effect) {
                        case Add: {
                            buffer[x][y].Add(GetPixel(x, y, false, false));
                            break;
                        }
                        case Average: {
                            buffer[x][y].Average(GetPixel(x, y, false, false));
                            break;
                        }
                        case Alpha: {
                            buffer[x][y].Alpha(GetPixel(x, y, false, false));
                            break;
                        }
                        case Multiply: {
                            buffer[x][y].Multiply(GetPixel(x, y, false, false));
                            break;
                        }
                        case Divide: {
                            buffer[x][y].Divide(GetPixel(x, y, false, false));
                            break;
                        }
                        case Subtract: {
                            buffer[x][y].Subtract(GetPixel(x, y, false, false));
                            break;
                        }
                        case Tint: {
                            buffer[x][y].Tint(GetPixel(x, y, false, false));
                            break;
                        }
                        case Hint: {
                            buffer[x][y].Hint(GetPixel(x, y, false, false));
                            break;
                        }
                        case Negative: {
                            buffer[x][y].Negative(GetPixel(x, y, false, false));
                            break;
                        }
                    }
                    this.EffectPixel(x, y, x + nx, y + ny, buffer[x][y], false, false, motion);
                }
            }
        } else {
            for (int x = width - 1; x >= 0; x--) {
                for (int y = height - 1; y >= 0; y--) {

                    switch (effect) {
                        case Add: {
                            buffer[x][y].Add(GetPixel(x, y, false, false));
                            break;
                        }
                        case Average: {
                            buffer[x][y].Average(GetPixel(x, y, false, false));
                            break;
                        }
                        case Alpha: {
                            buffer[x][y].Alpha(GetPixel(x, y, false, false));
                            break;
                        }
                        case Multiply: {
                            buffer[x][y].Multiply(GetPixel(x, y, false, false));
                            break;
                        }
                        case Divide: {
                            buffer[x][y].Divide(GetPixel(x, y, false, false));
                            break;
                        }
                        case Subtract: {
                            buffer[x][y].Subtract(GetPixel(x, y, false, false));
                            break;
                        }
                        case Tint: {
                            buffer[x][y].Tint(GetPixel(x, y, false, false));
                            break;
                        }
                        case Hint: {
                            buffer[x][y].Hint(GetPixel(x, y, false, false));
                            break;
                        }
                        case Negative: {
                            buffer[x][y].Negative(GetPixel(x, y, false, false));
                            break;
                        }
                    }
                    this.EffectPixel(x, y, x + nx, y + ny, buffer[x][y], false, false, motion);
                }
            }
        }
    }

    void ReflectionBlur(boolean vertical, int n, PixelEffect effect, boolean fold) {
        n = Math.abs(n);
        if (n == 0) {
            n = 1;
        }
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, fold, false);
                c[0][1] = GetPixel(x - 1, y, fold, false);
                c[0][2] = GetPixel(x - 1, y + 1, fold, false);
                c[1][0] = GetPixel(x, y - 1, fold, false);
                c[1][1] = GetPixel(x, y, fold, false);
                c[1][2] = GetPixel(x, y + 1, fold, false);
                c[2][0] = GetPixel(x + 1, y - 1, fold, false);
                c[2][1] = GetPixel(x + 1, y, fold, false);
                c[2][2] = GetPixel(x + 1, y + 1, fold, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        if (vertical) {
            for (int x1 = 0, x2 = width - 1; x1 < width; x1++, x2--) {
                for (int y1 = 0, y2 = height - 1; y1 < height; y1++, y2--) {

                    this.EffectPixel(x1, y1, x2 - n, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - n, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - n, y2 + n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 + n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2 + n, buffer[x1][y1], fold, false, effect);

                    this.EffectPixel(x2, y2, x1 - n, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - n, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - n, y1 + n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 + n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1 + n, buffer[x2][y2], fold, false, effect);
                }
            }
        } else {
            for (int y1 = 0, y2 = height - 1; y1 < height; y1++, y2--) {
                for (int x1 = 0, x2 = width - 1; x1 < width; x1++, x2--) {

                    this.EffectPixel(x1, y1, x2 - n, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - n, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - n, y2 + n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 + n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2 - n, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + n, y2 + n, buffer[x1][y1], fold, false, effect);

                    this.EffectPixel(x2, y2, x1 - n, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - n, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - n, y1 + n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 + n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1 - n, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + n, y1 + n, buffer[x2][y2], fold, false, effect);
                }
            }
        }
    }

    void DistructionBlur(int numx, int numy, PixelEffect effect, boolean fold, boolean fire) {
        numx = Math.abs(numx);
        if (numx == 0) {
            numx = 1;
        }
        numy = Math.abs(numy);
        if (numy == 0) {
            numy = 1;
        }
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, fold, false);
                c[0][1] = GetPixel(x - 1, y, fold, false);
                c[0][2] = GetPixel(x - 1, y + 1, fold, false);
                c[1][0] = GetPixel(x, y - 1, fold, false);
                c[1][1] = GetPixel(x, y, fold, false);
                c[1][2] = GetPixel(x, y + 1, fold, false);
                c[2][0] = GetPixel(x + 1, y - 1, fold, false);
                c[2][1] = GetPixel(x + 1, y, fold, false);
                c[2][2] = GetPixel(x + 1, y + 1, fold, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int y1 = 0, y2 = height - 1; y1 < height; y1++, y2--) {
            for (int x1 = 0, x2 = width - 1; x1 < width; x1++, x2--) {

                int nx = numx;
                int ny = numy;
                if (fire) {
                    nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                    ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                }
                this.EffectPixel(x1, y1, x2 - nx, y2 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2 - nx, y2, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2 - nx, y2 + ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2, y2 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2, y2 + ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2 + ny, buffer[x1][y1], fold, false, effect);
                if (fire) {
                    nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                    ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                }
                this.EffectPixel(x2, y2, x1 - nx, y1 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1 - nx, y1, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1 - nx, y1 + ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1, y1 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1, y1 + ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1 + ny, buffer[x2][y2], fold, false, effect);
                //
                if (fire) {
                    nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                    ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                }
                this.EffectPixel(x1, y1, x2 - nx, y2 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2 - nx, y2, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2 - nx, y2 + ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2, y2 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2, y2 + ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2 - ny, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2, buffer[x2][y2], fold, false, effect);
                this.EffectPixel(x1, y1, x2 + nx, y2 + ny, buffer[x2][y2], fold, false, effect);
                if (fire) {
                    nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                    ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                }
                this.EffectPixel(x2, y2, x1 - nx, y1 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1 - nx, y1, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1 - nx, y1 + ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1, y1 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1, y1 + ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1 - ny, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1, buffer[x1][y1], fold, false, effect);
                this.EffectPixel(x2, y2, x1 + nx, y1 + ny, buffer[x1][y1], fold, false, effect);
                //
                //n=(num+Random.Next(num)-Random.Next(num))>>1; 
        /*this.EffectPixel(x1, y1,x2,y2, buffer[x1][y1], fold,false, effect);
                 this.EffectPixel(x2, y2,x1,y1, buffer[x2][y2], fold,false, effect);
                 this.EffectPixel(x1, y1,x2,y2, buffer[x2][y2], fold,false, effect);
                 this.EffectPixel(x2, y2,x1,y1, buffer[x1][y1], fold,false, effect);
                 this.EffectPixel(x2, y2,x2,y2, buffer[x1][y1], fold,false, effect);
                 this.EffectPixel(x1, y1,x1,y1, buffer[x2][y2], fold,false, effect);
                 this.EffectPixel(x1, y1,x1,y1, buffer[x1][y1], fold,false, effect);
                 this.EffectPixel(x2, y2,x2,y2, buffer[x2][y2], fold,false, effect);*/

            }
        }
    }

    void CrossBlur(int numx, int numy, PixelEffect effect, boolean fold, boolean fire, boolean circle) {
        numx = Math.abs(numx);
        if (numx == 0) {
            numx = 1;
        }
        numy = Math.abs(numy);
        if (numy == 0) {
            numy = 1;
        }
        if (numx >= width) {
            numx = width;
        }
        if (numy >= height) {
            numy = height;
        }
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, fold, false);
                c[0][1] = GetPixel(x - 1, y, fold, false);
                c[0][2] = GetPixel(x - 1, y + 1, fold, false);
                c[1][0] = GetPixel(x, y - 1, fold, false);
                c[1][1] = GetPixel(x, y, fold, false);
                c[1][2] = GetPixel(x, y + 1, fold, false);
                c[2][0] = GetPixel(x + 1, y - 1, fold, false);
                c[2][1] = GetPixel(x + 1, y, fold, false);
                c[2][2] = GetPixel(x + 1, y + 1, fold, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        int tx = width >> 1;
        int ty = height >> 1;
        int tx2 = (tx + numx);
        int ty2 = (ty + numy);
        int x = 0;
        int r = (int) (Math.sqrt(tx * tx + ty * ty) * 0.70710678118654752440084436210485);
        do {
            int y = 0;
            do {
                int x1 = tx + x;
                int y1 = ty + y;
                int x2 = tx - x;
                int y2 = ty - y;
                if (x1 >= width) {
                    x1 -= width;
                }
                if (y1 >= height) {
                    y1 -= height;
                }
                if (x2 < 0) {
                    x2 += width;
                }
                if (y2 < 0) {
                    y2 += height;
                }

                int l = (int) (Math.sqrt(x * x + y * y));
                if (l < r || !circle) {

                    int nx = numx;
                    int ny = numy;
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x1, y1, x2 - nx, y2 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - nx, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - nx, y2 + ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 + ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2 + ny, buffer[x1][y1], fold, false, effect);
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x2, y2, x1 - nx, y1 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - nx, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - nx, y1 + ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 + ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1 + ny, buffer[x2][y2], fold, false, effect);
                    //
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x1, y1, x2 - nx, y2 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - nx, y2, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 - nx, y2 + ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2 + ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2 - ny, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2 + nx, y2 + ny, buffer[x2][y2], fold, false, effect);
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x2, y2, x1 - nx, y1 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - nx, y1, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 - nx, y1 + ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1 + ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1 + nx, y1 + ny, buffer[x1][y1], fold, false, effect);
                    //
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x1, y2, x2 - nx, y1 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2 - nx, y1, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2 - nx, y1 + ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2, y1 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2, y1 + ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2 + nx, y1 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2 + nx, y1, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x1, y2, x2 + nx, y1 + ny, buffer[x1][y2], fold, false, effect);
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x2, y1, x1 - nx, y2 - ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2 + ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 - ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 + ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 - ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 + ny, buffer[x2][y1], fold, false, effect);
                    //
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x2, y1, x1 - nx, y2 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2 + ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 + ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 - ny, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2, buffer[x1][y2], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 + ny, buffer[x1][y2], fold, false, effect);
                    if (fire) {
                        nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                        ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                    }
                    this.EffectPixel(x2, y1, x1 - nx, y2 - ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 - nx, y2 + ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 - ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1, y2 + ny, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 - ny, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2, buffer[x2][y1], fold, false, effect);
                    this.EffectPixel(x2, y1, x1 + nx, y2 + ny, buffer[x2][y1], fold, false, effect);
                    //
                    //n=(num+Random.Next(num)-Random.Next(num))>>1; 
                    this.EffectPixel(x1, y1, x2, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x2, y2, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x2, y2, x1, y1, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x2, y2, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x1, y1, x1, y1, buffer[x2][y2], fold, false, effect);
                    this.EffectPixel(x1, y1, x1, y1, buffer[x1][y1], fold, false, effect);
                    this.EffectPixel(x2, y2, x2, y2, buffer[x2][y2], fold, false, effect);
                }
                y++;
            } while (y < ty2 && y < r);
            x++;
        } while (x < tx2 && x < r);
    }

    void BlockBlur(int numx, int numy, int size, PixelEffect effect, boolean fold) {
        numx = Math.abs(numx);
        if (numx == 0) {
            numx = 1;
        }//,NTidePoint step1,NTidePoint step2,NTidePoint step3,
        numy = Math.abs(numy);
        if (numy == 0) {
            numy = 1;
        }
        if (numx >= width) {
            numx = width;
        }
        if (numy >= height) {
            numy = height;
        }
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, fold, false);
                c[0][1] = GetPixel(x - 1, y, fold, false);
                c[0][2] = GetPixel(x - 1, y + 1, fold, false);
                c[1][0] = GetPixel(x, y - 1, fold, false);
                c[1][1] = GetPixel(x, y, fold, false);
                c[1][2] = GetPixel(x, y + 1, fold, false);
                c[2][0] = GetPixel(x + 1, y - 1, fold, false);
                c[2][1] = GetPixel(x + 1, y, fold, false);
                c[2][2] = GetPixel(x + 1, y + 1, fold, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        int tx = width >> 1;
        int ty = height >> 1;

        int x = 0;
        do {
            int y = 0;
            do {
                int x1 = tx + x;
                int y1 = ty + y;
                int x2 = tx - x;
                int y2 = ty - y;
                if (x1 >= width) {
                    x1 -= width;
                }
                if (y1 >= height) {
                    y1 -= height;
                }
                if (x2 < 0) {
                    x2 += width;
                }
                if (y2 < 0) {
                    y2 += height;
                }
                int nx = numx;
                int ny = numy;
                //if(fire){nx=numx+(Random.Next(3)-Random.Next(3))>>1;ny=numy+(Random.Next(3)-Random.Next(3))>>1;} 
                int atx0 = 0, atx1 = 0, atx2 = 0;
                int aty0 = 0, aty1 = 0, aty2 = 0;
                for (int at0x = 0; at0x < 4; at0x++) {
                    for (int at0y = 0; at0y < 4; at0y++) {
                        for (int at1x = 0; at1x < 4; at1x++) {
                            for (int at1y = 0; at1y < 4; at1y++) {
                                for (int at2x = 0; at2x < 4; at2x++) {
                                    for (int at2y = 0; at2y < 4; at2y++) {
                                        switch (at0x) {
                                            case 0: {
                                                atx0 = x1;
                                                break;
                                            }
                                            case 1: {
                                                atx0 = y1;
                                                break;
                                            }
                                            case 2: {
                                                atx0 = x2;
                                                break;
                                            }
                                            case 3: {
                                                atx0 = y2;
                                                break;
                                            }
                                        }
                                        switch (at1x) {
                                            case 0: {
                                                atx1 = x1;
                                                break;
                                            }
                                            case 1: {
                                                atx1 = y1;
                                                break;
                                            }
                                            case 2: {
                                                atx1 = x2;
                                                break;
                                            }
                                            case 3: {
                                                atx1 = y2;
                                                break;
                                            }
                                        }
                                        switch (at2x) {
                                            case 0: {
                                                atx2 = x1;
                                                break;
                                            }
                                            case 1: {
                                                atx2 = y1;
                                                break;
                                            }
                                            case 2: {
                                                atx2 = x2;
                                                break;
                                            }
                                            case 3: {
                                                atx2 = y2;
                                                break;
                                            }
                                        }
                                        switch (at0y) {
                                            case 0: {
                                                aty0 = x1;
                                                break;
                                            }
                                            case 1: {
                                                aty0 = y1;
                                                break;
                                            }
                                            case 2: {
                                                aty0 = x2;
                                                break;
                                            }
                                            case 3: {
                                                aty0 = y2;
                                                break;
                                            }
                                        }
                                        switch (at1y) {
                                            case 0: {
                                                aty1 = x1;
                                                break;
                                            }
                                            case 1: {
                                                aty1 = y1;
                                                break;
                                            }
                                            case 2: {
                                                aty1 = x2;
                                                break;
                                            }
                                            case 3: {
                                                aty1 = y2;
                                                break;
                                            }
                                        }
                                        switch (at2y % 4) {
                                            case 0: {
                                                aty2 = x1;
                                                break;
                                            }
                                            case 1: {
                                                aty2 = y1;
                                                break;
                                            }
                                            case 2: {
                                                aty2 = x2;
                                                break;
                                            }
                                            case 3: {
                                                aty2 = y2;
                                                break;
                                            }
                                        }
                                        if (atx1 != aty0 && atx1 != aty1 && atx1 != aty2 && atx2 != aty0 && atx2 != aty1 && atx2 != aty2 //      && atx0!=atx1&& atx0!=atx2 && aty0!=aty1&& aty0!=atx2
                                                ) {
                                            this.EffectPixel(atx0, aty0, atx1 - nx, aty1, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1 - nx, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1, aty1 - ny, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1, aty1, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1 + nx, aty1 - ny, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1 + nx, aty1, buffer[atx2][aty2], fold, false, effect);
                                            this.EffectPixel(atx0, aty0, atx1 + nx, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                                            //
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                y++;
            } while (y < size);
            x++;
        } while (x < size);
    }

    void Explode(int numx, int numy, int damage, PixelEffect effect, boolean fold, boolean fire) {
        numx = Math.abs(numx);
        if (numx == 0) {
            numx = 1;
        }
        numy = Math.abs(numy);
        if (numy == 0) {
            numy = 1;
        }
        if (numx >= width) {
            numx = width;
        }
        if (numy >= height) {
            numy = height;
        }
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, fold, false);
                c[0][1] = GetPixel(x - 1, y, fold, false);
                c[0][2] = GetPixel(x - 1, y + 1, fold, false);
                c[1][0] = GetPixel(x, y - 1, fold, false);
                c[1][1] = GetPixel(x, y, fold, false);
                c[1][2] = GetPixel(x, y + 1, fold, false);
                c[2][0] = GetPixel(x + 1, y - 1, fold, false);
                c[2][1] = GetPixel(x + 1, y, fold, false);
                c[2][2] = GetPixel(x + 1, y + 1, fold, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        int tx = width >> 1;
        int ty = height >> 1;
        int tx2 = (tx + numx);
        int ty2 = (ty + numy);

        int x = 0;
        do {
            int y = 0;
            do {
                int x1 = tx + Random.Next(tx2);
                int y1 = ty + Random.Next(ty2);
                int x2 = tx - Random.Next(tx2);
                int y2 = ty - Random.Next(ty2);
                if (x1 >= width) {
                    x1 -= width;
                }
                if (y1 >= height) {
                    y1 -= height;
                }
                if (x2 < 0) {
                    x2 += width;
                }
                if (y2 < 0) {
                    y2 += height;
                }
                int nx = numx;
                int ny = numy;
                if (fire) {
                    nx = numx + (Random.Next(3) - Random.Next(3)) >> 1;
                    ny = numy + (Random.Next(3) - Random.Next(3)) >> 1;
                }
                int atx0 = 0, atx1 = 0, atx2 = 0;
                int aty0 = 0, aty1 = 0, aty2 = 0;
                int at0x = Random.Next(4), at1x = Random.Next(4), at2x = Random.Next(4);
                int at0y = Random.Next(4), at1y = Random.Next(4), at2y = Random.Next(4);
                switch (at0x) {
                    case 0: {
                        atx0 = x1;
                        break;
                    }
                    case 1: {
                        atx0 = y1;
                        break;
                    }
                    case 2: {
                        atx0 = x2;
                        break;
                    }
                    case 3: {
                        atx0 = y2;
                        break;
                    }
                    case 4: {
                        atx0 = x1;
                        break;
                    }
                }
                switch (at1x) {
                    case 0: {
                        atx1 = x1;
                        break;
                    }
                    case 1: {
                        atx1 = y1;
                        break;
                    }
                    case 2: {
                        atx1 = x2;
                        break;
                    }
                    case 3: {
                        atx1 = y2;
                        break;
                    }
                    case 4: {
                        atx1 = x1;
                        break;
                    }
                }
                switch (at2x) {
                    case 0: {
                        atx2 = x1;
                        break;
                    }
                    case 1: {
                        atx2 = y1;
                        break;
                    }
                    case 2: {
                        atx2 = x2;
                        break;
                    }
                    case 3: {
                        atx2 = y2;
                        break;
                    }
                    case 4: {
                        atx2 = x1;
                        break;
                    }
                }
                switch (at0y) {
                    case 0: {
                        aty0 = x1;
                        break;
                    }
                    case 1: {
                        aty0 = y1;
                        break;
                    }
                    case 2: {
                        aty0 = x2;
                        break;
                    }
                    case 3: {
                        aty0 = y2;
                        break;
                    }
                    case 4: {
                        aty0 = x1;
                        break;
                    }
                }
                switch (at1y) {
                    case 0: {
                        aty1 = x1;
                        break;
                    }
                    case 1: {
                        aty1 = y1;
                        break;
                    }
                    case 2: {
                        aty1 = x2;
                        break;
                    }
                    case 3: {
                        aty1 = y2;
                        break;
                    }
                    case 4: {
                        aty1 = x1;
                        break;
                    }
                }
                switch (at2y) {
                    case 0: {
                        aty2 = x1;
                        break;
                    }
                    case 1: {
                        aty2 = y1;
                        break;
                    }
                    case 2: {
                        aty2 = x2;
                        break;
                    }
                    case 3: {
                        aty2 = y2;
                        break;
                    }
                    case 4: {
                        aty2 = x1;
                        break;
                    }
                }
                if (atx1 != aty0 && atx1 != aty1 && atx1 != aty2 && atx2 != aty0 && atx2 != aty1 && atx2 != aty2 //      && atx0!=atx1&& atx0!=atx2 && aty0!=aty1&& aty0!=atx2
                        ) {
                    this.EffectPixel(atx0, aty0, atx1 - nx, aty1, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1 - nx, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1, aty1 - ny, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1, aty1, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1 + nx, aty1 - ny, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1 + nx, aty1, buffer[atx2][aty2], fold, false, effect);
                    this.EffectPixel(atx0, aty0, atx1 + nx, aty1 + ny, buffer[atx2][aty2], fold, false, effect);
                    //
                }
                y++;
            } while (y < damage);
            x++;
        } while (x < damage);
    }

    void Blend(PixelEffect blend, ColorMap image, PixelEffect effect) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = GetPixel(x - 1, y - 1, true, false);
                c[0][1] = GetPixel(x - 1, y, true, false);
                c[0][2] = GetPixel(x - 1, y + 1, true, false);
                c[1][0] = GetPixel(x, y - 1, true, false);
                c[1][1] = GetPixel(x, y, true, false);
                c[1][2] = GetPixel(x, y + 1, true, false);
                c[2][0] = GetPixel(x + 1, y - 1, true, false);
                c[2][1] = GetPixel(x + 1, y, true, false);
                c[2][2] = GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                switch (blend) {
                    case Add: {
                        buffer[x][y].Add(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Average: {
                        buffer[x][y].Average(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Alpha: {
                        buffer[x][y].Alpha(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Multiply: {
                        buffer[x][y].Multiply(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Divide: {
                        buffer[x][y].Divide(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Subtract: {
                        buffer[x][y].Subtract(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Tint: {
                        buffer[x][y].Tint(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Hint: {
                        buffer[x][y].Hint(image.GetPixel(x, y, true, false));
                        break;
                    }
                    case Negative: {
                        buffer[x][y].Negative(image.GetPixel(x, y, true, false));
                        break;
                    }
                }
                this.EffectPixel(x, y, buffer[x][y], true, false, effect);
            }
        }
    }

    void Merge(ColorMap image, PixelEffect effect) {
        Pigment buffer[][] = new Pigment[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pigment c[][] = new Pigment[3][3];
                c[0][0] = image.GetPixel(x - 1, y - 1, true, false);
                c[0][1] = image.GetPixel(x - 1, y, true, false);
                c[0][2] = image.GetPixel(x - 1, y + 1, true, false);
                c[1][0] = image.GetPixel(x, y - 1, true, false);
                c[1][1] = image.GetPixel(x, y, true, false);
                c[1][2] = image.GetPixel(x, y + 1, true, false);
                c[2][0] = image.GetPixel(x + 1, y - 1, true, false);
                c[2][1] = image.GetPixel(x + 1, y, true, false);
                c[2][2] = image.GetPixel(x + 1, y + 1, true, false);
                int a = c[0][0].Alpha() + c[0][1].Alpha() + c[0][2].Alpha() + c[1][0].Alpha() + c[1][1].Alpha() + c[1][2].Alpha() + c[2][0].Alpha() + c[2][1].Alpha() + c[2][2].Alpha();
                int r = c[0][0].Red() + c[0][1].Red() + c[0][2].Red() + c[1][0].Red() + c[1][1].Red() + c[1][2].Red() + c[2][0].Red() + c[2][1].Red() + c[2][2].Red();
                int g = c[0][0].Green() + c[0][1].Green() + c[0][2].Green() + c[1][0].Green() + c[1][1].Green() + c[1][2].Green() + c[2][0].Green() + c[2][1].Green() + c[2][2].Green();
                int b = c[0][0].Blue() + c[0][1].Blue() + c[0][2].Blue() + c[1][0].Blue() + c[1][1].Blue() + c[1][2].Blue() + c[2][0].Blue() + c[2][1].Blue() + c[2][2].Blue();
                buffer[x][y] = new Pigment(a / 9, r / 9, g / 9, b / 9);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.EffectPixel(x, y, buffer[x][y], true, false, effect);
            }
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, SkinData value[][], int valueLengthx, int valueLengthy, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean discard) {
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
        for (int x = fromx; x < tox; x += 3) {
            for (int y = fromy; y < toy; y += 3) {
                //this.SetPixel(x,y,value[ix][iy],fold,discard);
                Skin.Texture(this, x - 1, x + 1, y, highlight, color, shade, value[ix][iy], fold, discard);
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

    void FillRectangle(int fromx, int fromy, int tox, int toy, SkinData value[][], int valueLengthx, int valueLengthy, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean discard, PixelEffect effect) {
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
        for (int x = fromx; x < tox; x += 3) {
            for (int y = fromy; y < toy; y += 3) {
                //this.EffectPixel(x,y,value[ix][iy],fold,discard,effect);
                Skin.Texture(this, x - 1, x + 1, y, highlight, color, shade, value[ix][iy], fold, discard, effect);

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

    void FillRectangle(int fromx, int fromy, int tox, int toy, SkinData value, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean discard) {
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
        for (int x = fromx; x < tox; x += 3) {
            for (int y = fromy; y < toy; y += 3) {
                //this.SetPixel(x,y,value,fold,discard);
                Skin.Texture(this, x - 1, x + 1, y, highlight, color, shade, value, fold, discard);

                //iy++;
            }
            //ix++;
            //iy=0;
        }
    }

    void FillRectangle(int fromx, int fromy, int tox, int toy, SkinData value, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean discard, PixelEffect effect) {
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
        for (int x = fromx; x < tox; x += 3) {
            for (int y = fromy; y < toy; y += 3) {
                //this.EffectPixel(x,y,value,fold,discard,effect);

                Skin.Texture(this, x - 1, x + 1, y, highlight, color, shade, value, fold, discard, effect);
                //iy++;
            }
            //ix++;
            //iy=0;
        }
    }
}
