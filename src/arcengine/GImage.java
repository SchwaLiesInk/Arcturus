/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import static arcengine.Game.gc;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gerwyn Jones
 */
enum SkinTexture {

    DNA, Organic, SemiOrganic, InOrganic, Skin, Armour, Carapace, Fur, Random
}

class SkinData {

    public int[] add;
    public int[] ax;
    public int[] ay;

    public SkinData(int dots) {
        add = new int[dots];
        ax = new int[dots];
        ay = new int[dots];
        for (int d = 0; d < dots; d++) {
            this.add[d] = Random.Next(3) + 1;
            this.ax[d] = Random.Next(3) + 1;
            this.ay[d] = Random.Next(3) + 1;
        }
    }

    public SkinData(DNA dna, int dots) {
        add = new int[dots];
        ax = new int[dots];
        ay = new int[dots];
        int at = 0;
        int sz = dna.size;
        int h1 = dna.GetX(at);
        int h2 = dna.GetY(at);
        int h3 = dna.GetZ(at);
        for (int d = 0; d < dots; d++) {
            this.add[d] = (h3 >> 1) + 1;
            this.ax[d] = (h1 >> 1) + 1;
            this.ay[d] = (h2 >> 1) + 1;
            at++;
            if (at >= sz) {
                at = 0;
            }
        }
    }

    public SkinData(DNA dna, int at, int dots) {
        add = new int[dots];
        ax = new int[dots];
        ay = new int[dots];
        int sz = dna.size;
        int h1 = dna.GetX(at);
        int h2 = dna.GetY(at);
        int h3 = dna.GetZ(at);
        for (int d = 0; d < dots; d++) {
            this.add[d] = (h3 >> 1) + 1;
            this.ax[d] = (h1 >> 1) + 1;
            this.ay[d] = (h2 >> 1) + 1;
            at++;
            if (at >= sz) {
                at = 0;
            }
        }
    }

}

class Skin {

    static void Organic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel((int) (x1), (int) (y1), highlight, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1), highlight, fold, fail);
        image.AveragePixel((int) (x1), (int) (y1 + 1), color, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1 - 1), color, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1), color, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1), color, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1 - 1), shade, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1 - 1), shade, fold, fail);
    }

    static void Organic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel((int) (x1), (int) (y1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x1), (int) (y1 + 1), color, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1 - 1), color, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1), color, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1), color, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1 - 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1 - 1), shade, fold, fail, effect);
    }

    static void Fur(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel((int) (x1), (int) (y1), shade, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1), shade, fold, fail);
        image.AveragePixel((int) (x1), (int) (y1 + 1), color, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1 + 1), color, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1 - 1), color, fold, fail);
        image.AveragePixel((int) (x2 + 1), (int) (y1 - 1), color, fold, fail);
        image.AveragePixel((int) (x1 - 1), (int) (y1 + 1), highlight, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1 + 1), highlight, fold, fail);
        image.AveragePixel((int) (x1), (int) (y1 - 1), shade, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1 - 1), shade, fold, fail);
    }

    static void Fur(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel((int) (x1), (int) (y1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1), shade, fold, fail, effect);
        image.EffectPixel((int) (x1), (int) (y1 + 1), color, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1 + 1), color, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1 - 1), color, fold, fail, effect);
        image.EffectPixel((int) (x2 + 1), (int) (y1 - 1), color, fold, fail, effect);
        image.EffectPixel((int) (x1 - 1), (int) (y1 + 1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1 + 1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x1), (int) (y1 - 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1 - 1), shade, fold, fail, effect);
    }

    static void SemiOrganic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel((int) (x1), (int) (y1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1), highlight, fold, fail, effect);
        image.EffectPixel((int) (x1), (int) (y1 + 1), color, fold, fail, effect);
        image.EffectPixel((int) (x1), (int) (y1 - 1), color, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1 + 1), color, fold, fail, effect);
        image.EffectPixel((int) (x2), (int) (y1 - 1), color, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1), color, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1), color, fold, fail, effect);
        //
        image.EffectPixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail, effect);
        image.EffectPixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail, effect);
    }

    static void SemiOrganic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel((int) (x1), (int) (y1), highlight, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1), highlight, fold, fail);
        image.AveragePixel((int) (x1), (int) (y1 + 1), color, fold, fail);
        image.AveragePixel((int) (x1), (int) (y1 - 1), color, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1 + 1), color, fold, fail);
        image.AveragePixel((int) (x2), (int) (y1 - 1), color, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1), color, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1), color, fold, fail);
        //
        image.AveragePixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail);
        image.AveragePixel((int) (x1 + 1), (int) (y1 + 1), shade, fold, fail);
        image.AveragePixel((int) (x2 - 1), (int) (y1 + 1), shade, fold, fail);
    }

    static void Armour(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel(x1, y1, color, fold, fail);
        image.AveragePixel(x1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1, shade, fold, fail);
        image.AveragePixel(x1 + 1, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1 + 1, shade, fold, fail);
        image.AveragePixel(x2, y1, color, fold, fail);
        image.AveragePixel(x2, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1, shade, fold, fail);
        image.AveragePixel(x2 - 1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1 - 1, shade, fold, fail);
    }

    static void Armour(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel(x1, y1, color, fold, fail, effect);
        image.EffectPixel(x1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 + 1, shade, fold, fail, effect);
        image.EffectPixel(x2, y1, color, fold, fail, effect);
        image.EffectPixel(x2, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 - 1, shade, fold, fail, effect);
    }

    static void InOrganic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel(x1, y1, color, fold, fail);
        image.AveragePixel(x1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1, shade, fold, fail);
        image.AveragePixel(x1 - 1, y1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1 + 1, shade, fold, fail);
        image.AveragePixel(x2, y1, color, fold, fail);
        image.AveragePixel(x2, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1, shade, fold, fail);
        image.AveragePixel(x2 + 1, y1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1 + -1, shade, fold, fail);
    }

    static void InOrganic(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel(x1, y1, color, fold, fail, effect);
        image.EffectPixel(x1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x1 - 1, y1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 + 1, shade, fold, fail, effect);
        image.EffectPixel(x2, y1, color, fold, fail, effect);
        image.EffectPixel(x2, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x2 + 1, y1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 + -1, shade, fold, fail, effect);
    }

    static void Skin(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel(x1, y1, color, fold, fail);
        image.AveragePixel(x1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1, shade, fold, fail);
        image.AveragePixel(x1 - 1, y1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1 + 1, shade, fold, fail);
        image.AveragePixel(x2, y1, color, fold, fail);
        image.AveragePixel(x2, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1, shade, fold, fail);
        image.AveragePixel(x2 + 1, y1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1 - 1, shade, fold, fail);
    }

    static void Skin(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel(x1, y1, color, fold, fail, effect);
        image.EffectPixel(x1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x1 - 1, y1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1 + 1, shade, fold, fail, effect);
        image.EffectPixel(x2, y1, color, fold, fail, effect);
        image.EffectPixel(x2, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x2 + 1, y1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1 - 1, shade, fold, fail, effect);
    }

    static void Carapace(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail) {
        image.AveragePixel(x1, y1, color, fold, fail);
        image.AveragePixel(x1, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x1, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x1 + 1, y1, shade, fold, fail);
        image.AveragePixel(x1 - 1, y1, shade, fold, fail);
        image.AveragePixel(x2, y1, color, fold, fail);
        image.AveragePixel(x2, y1 - 1, highlight, fold, fail);
        image.AveragePixel(x2, y1 + 1, highlight, fold, fail);
        image.AveragePixel(x2 - 1, y1, shade, fold, fail);
        image.AveragePixel(x2 + 1, y1, shade, fold, fail);
    }

    static void Carapace(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, boolean fold, boolean fail, PixelEffect effect) {
        image.EffectPixel(x1, y1, color, fold, fail, effect);
        image.EffectPixel(x1, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x1, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x1 + 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x1 - 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x2, y1, color, fold, fail, effect);
        image.EffectPixel(x2, y1 - 1, highlight, fold, fail, effect);
        image.EffectPixel(x2, y1 + 1, highlight, fold, fail, effect);
        image.EffectPixel(x2 - 1, y1, shade, fold, fail, effect);
        image.EffectPixel(x2 + 1, y1, shade, fold, fail, effect);
    }

    static void Texture(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, int dots, boolean fold, boolean fail) {
        int[] add = new int[dots];
        int[] ax = new int[dots];
        int[] ay = new int[dots];
        for (int d = 0; d < dots; d++) {
            add[d] = Random.Next(3) + 1;
            ax[d] = Random.Next(3) + 1;
            ay[d] = Random.Next(3) + 1;
        }
        for (int d = 0; d < dots; d++) {
            int xadd = ax[d] - 2;
            int yadd = ay[d] - 2;
            switch (add[d]) {
                case 1: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, highlight, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, highlight, fold, fail);
                    break;
                }
                case 2: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, color, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, color, fold, fail);
                    break;
                }
                case 3: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, shade, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, shade, fold, fail);
                    break;
                }
            }
        }
    }

    static void Texture(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, int dots, boolean fold, boolean fail, PixelEffect effect) {
        int[] add = new int[dots];
        int[] ax = new int[dots];
        int[] ay = new int[dots];
        for (int d = 0; d < dots; d++) {
            add[d] = Random.Next(3) + 1;
            ax[d] = Random.Next(3) + 1;
            ay[d] = Random.Next(3) + 1;
        }
        for (int d = 0; d < dots; d++) {
            int xadd = ax[d] - 2;
            int yadd = ay[d] - 2;
            switch (add[d]) {
                case 1: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, highlight, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, highlight, fold, fail, effect);
                    break;
                }
                case 2: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, color, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, color, fold, fail, effect);
                    break;
                }
                case 3: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, shade, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, shade, fold, fail, effect);
                    break;
                }
            }
        }
    }

    static void Texture(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, SkinData derm, boolean fold, boolean fail, PixelEffect effect) {
        for (int d = 0; d < derm.add.length; d++) {
            int xadd = derm.ax[d] - 2;
            int yadd = derm.ay[d] - 2;
            switch (derm.add[d]) {

                case 1: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, highlight, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, highlight, fold, fail, effect);
                    break;
                }
                case 2: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, color, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, color, fold, fail, effect);
                    break;
                }
                case 3: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, shade, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, shade, fold, fail, effect);
                    break;
                }
            }
        }
    }

    static void Texture(ColorMap image, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, SkinData derm, boolean fold, boolean fail) {
        for (int d = 0; d < derm.add.length; d++) {
            int xadd = derm.ax[d] - 2;
            int yadd = derm.ay[d] - 2;
            switch (derm.add[d]) {
                case 1: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, highlight, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, highlight, fold, fail);
                    break;
                }
                case 2: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, color, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, color, fold, fail);
                    break;
                }
                case 3: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, shade, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, shade, fold, fail);
                    break;
                }
            }
        }
    }

    static void Texture(ColorMap image, DNA dna, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, byte dots, boolean fold, boolean fail) {
        int[] add = new int[dots];
        int[] ax = new int[dots];
        int[] ay = new int[dots];
        //
        int at = 0;
        int sz = dna.Size();
        int h1 = dna.GetX(at);
        int h2 = dna.GetY(at);
        int h3 = dna.GetZ(at);
        //
        for (int d = 0; d < dots; d++) {
            add[d] = (h3 >> 1) + 1;
            ax[d] = (h1 >> 1) + 1;
            ay[d] = (h2 >> 1) + 1;
            at++;
            if (at >= sz) {
                at = 0;
            }
            h1 = dna.GetX(at);
            h2 = dna.GetY(at);
            h3 = dna.GetZ(at);
        }
        for (int d = 0; d < dots; d++) {
            int xadd = ax[d] - 2;
            int yadd = ay[d] - 2;
            switch (add[d]) {
                case 1: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, highlight, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, highlight, fold, fail);
                    break;
                }
                case 2: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, color, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, color, fold, fail);
                    break;
                }
                case 3: {
                    image.AveragePixel(x1 + xadd, y1 + yadd, shade, fold, fail);
                    image.AveragePixel(x2 - xadd, y1 + yadd, shade, fold, fail);
                    break;
                }
            }
        }
    }

    static void Texture(ColorMap image, DNA dna, int x1, int x2, int y1, Pigment highlight, Pigment color, Pigment shade, byte dots, boolean fold, boolean fail, PixelEffect effect) {
        int[] add = new int[dots];
        int[] ax = new int[dots];
        int[] ay = new int[dots];
        //
        int at = 0;
        int sz = dna.Size();
        int h1 = dna.GetX(at);
        int h2 = dna.GetY(at);
        int h3 = dna.GetZ(at);
        //
        for (int d = 0; d < dots; d++) {
            add[d] = (h3 >> 1) + 1;
            ax[d] = (h1 >> 1) + 1;
            ay[d] = (h2 >> 1) + 1;
            at++;
            if (at >= sz) {
                at = 0;
            }
            h1 = dna.GetX(at);
            h2 = dna.GetY(at);
            h3 = dna.GetZ(at);
        }
        for (int d = 0; d < dots; d++) {
            int xadd = ax[d] - 2;
            int yadd = ay[d] - 2;
            switch (add[d]) {
                case 1: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, highlight, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, highlight, fold, fail, effect);
                    break;
                }
                case 2: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, color, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, color, fold, fail, effect);
                    break;
                }
                case 3: {
                    image.EffectPixel(x1 + xadd, y1 + yadd, shade, fold, fail, effect);
                    image.EffectPixel(x2 - xadd, y1 + yadd, shade, fold, fail, effect);
                    break;
                }
            }
        }
    }

    //            
    protected SkinData derm[];
    protected Pigment color[][];
    protected PixelEffect sheen[][];

    ///
    public Skin(int derms) {
        derm = new SkinData[derms];
        color = new Pigment[derms][3];
        sheen = new PixelEffect[derms][2];
    }

    public int Derms() {
        return derm.length;
    }
}

public class GImage {

    static boolean SaveImage(String file, String format, GImage image) {
        java.io.File ifile = new java.io.File(file);

        if (image != null) {
            javax.imageio.stream.ImageOutputStream stream = null;
            try {
                stream = javax.imageio.ImageIO.createImageOutputStream(ifile);
            } catch (java.io.IOException ioex) {
                return false;
            }

            if (stream != null) {
                try {
                    javax.imageio.ImageIO.write(image.bits, format, stream);
                    return true;
                } catch (java.io.IOException iioex) {
                    return false;
                }
            }

        }
        return false;
    }

    static GImage LoadImage(String file, String format) {
        java.awt.image.BufferedImage image = null;
        java.io.File ifile = new java.io.File(file);
        javax.imageio.stream.ImageInputStream stream = null;
        try {
            stream = javax.imageio.ImageIO.createImageInputStream(ifile);
        } catch (java.io.IOException ioex) {

            System.out.println(file + " Could Not Find File.");
        }

        if (stream != null) {
            try {
                image = javax.imageio.ImageIO.read(stream);
                if (image != null) {
                    System.out.println(file + " Loaded.");
                } else {

                    System.out.println(file + " Could Not Load.");
                }
            } catch (java.io.IOException iioex) {
                System.out.println(file + " Could Be Found.");
            }
        }

        if (Game.gc != null) {
            java.awt.image.BufferedImage copy;
            int trans = image.getColorModel().getTransparency();
            copy = Game.gc.createCompatibleImage(image.getWidth(), image.getHeight(), trans);
            Graphics2D g = copy.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return new GImage(copy);
        }
        return new GImage(image);
    }
    BufferedImage bits;

    protected GImage(BufferedImage img) {
        bits = img;
    }

    GImage(int w, int h) {
        bits = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        if (Game.gc != null) {
            int trans = bits.getColorModel().getTransparency();
            bits = Game.gc.createCompatibleImage(bits.getWidth(), bits.getHeight(), trans);
        }
        Clear();
    }

    void Clear() {
        Graphics g = bits.getGraphics();
        g.clearRect(0, 0, bits.getWidth(), bits.getHeight());
    }

    void Clear(Pigment c) {

        Graphics g = bits.getGraphics();
        g.setColor(c.Color());
        g.clearRect(0, 0, bits.getWidth(), bits.getHeight());

    }

    void ClearAdd(Pigment p, float light, float sat) {
        float r = p.Red();
        float g = p.Green();
        float b = p.Blue();

        float d = sat / (sat + light);
        for (int i = 0; i < bits.getWidth(); i++) {
            for (int j = 0; j < bits.getHeight(); j++) {
                Color c = new Color(bits.getRGB(i, j));
                if (c.getRed() > 0 || c.getGreen() > 0 || c.getBlue() > 0) {
                    r = p.Red();
                    g = p.Green();
                    b = p.Blue();
                    r += c.getRed() * light;
                    g += c.getGreen() * light;
                    b += c.getBlue() * light;
                    r *= d;
                    g *= d;
                    b *= d;
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
                    bits.setRGB(i, j, new Color((int) (r), (int) (g), (int) (b), 32).getRGB());
                }
            }
        }
    }

    int At(int x, int y) {
        return bits.getRGB(x, y);
    }

    int Width() {
        return bits.getWidth();
    }

    int Height() {
        return bits.getHeight();
    }

    void AddAlphaPixel(int x, int y, int a, int r, int g, int b) {
        if(x<0){x=0;}
        else if(x>=this.Width()){x=this.Width()-1;}
        if(y<0){y=0;}
        else if(y>=this.Height()){y=this.Height()-1;}
        int at = At(x, y);
        Color c = new Color(at);
        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
        r >>= 1;
        g >>= 1;
        b >>= 1;
        c=new Color(r,g,b);
        bits.setRGB(x, y,c.getRGB());
    }

    void SetPixel(int x, int y, int a, int r, int g, int b) {

        if(x<0){x=0;}
        else if(x>=this.Width()){x=this.Width()-1;}
        if(y<0){y=0;}
        else if(y>=this.Height()){y=this.Height()-1;}
        Color c=new Color(r,g,b,a);
        bits.setRGB(x, y, c.getRGB());
    }

    void AddPixel(int x, int y, int a, int r, int g, int b) {
        if(x<0){x=0;}
        else if(x>=this.Width()){x=this.Width()-1;}
        if(y<0){y=0;}
        else if(y>=this.Height()){y=this.Height()-1;}
        int at = At(x, y);
        Color c = new Color(at);
        a += c.getAlpha();
        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
        a >>= 1;
        r >>= 1;
        g >>= 1;
        b >>= 1;
        if(a<0){a=0;}
        else if(a>255){a=255;}
        if(r<0){r=0;}
        else if(r>255){r=255;}
        if(g<0){g=0;}
        else if(g>255){g=255;}
        if(b<0){b=0;}
        else if(b>255){b=255;}
        c=new Color(r,g,b);
        bits.setRGB(x, y, c.getRGB());
    }
    void TintPixel(int x, int y, int a, int r, int g, int b) {
        if(x<0){x=0;}
        else if(x>=this.Width()){x=this.Width()-1;}
        if(y<0){y=0;}
        else if(y>=this.Height()){y=this.Height()-1;}
        int at = At(x, y);
        Color c = new Color(at);
        a += c.getAlpha()*3;
        r += c.getRed()*3;
        g += c.getGreen()*3;
        b += c.getBlue()*3;
        a >>= 2;
        r >>= 2;
        g >>= 2;
        b >>= 2;
        if(a<0){a=0;}
        else if(a>255){a=255;}
        if(r<0){r=0;}
        else if(r>255){r=255;}
        if(g<0){g=0;}
        else if(g>255){g=255;}
        if(b<0){b=0;}
        else if(b>255){b=255;}
        c=new Color(r,g,b);
        bits.setRGB(x, y, c.getRGB());
    }
    void HintPixel(int x, int y, int a, int r, int g, int b) {
        if(x<0){x=0;}
        else if(x>=this.Width()){x=this.Width()-1;}
        if(y<0){y=0;}
        else if(y>=this.Height()){y=this.Height()-1;}
        int at = At(x, y);
        Color c = new Color(at);
        a += c.getAlpha()*7;
        r += c.getRed()*7;
        g += c.getGreen()*7;
        b += c.getBlue()*7;
        a >>= 3;
        r >>= 3;
        g >>= 3;
        b >>= 3;
        if(a<0){a=0;}
        else if(a>255){a=255;}
        if(r<0){r=0;}
        else if(r>255){r=255;}
        if(g<0){g=0;}
        else if(g>255){g=255;}
        if(b<0){b=0;}
        else if(b>255){b=255;}
        c=new Color(r,g,b);
        bits.setRGB(x, y, c.getRGB());
    }
    void Blur(int deg){
    int c[][]=new int[Width()][Height()];
    for(int i=0;i<Width();i++){
    for(int j=0;j<Height();j++){
        c[i][j]=this.bits.getRGB(i, j);
    }
    }
    int deg2=8+Math.abs(deg);
    for(int i=1;i<Width()-1;i++){
    for(int j=1;j<Height()-1;j++){
        //c[i][j]=this.bits.getRGB(i, j);
        int p=c[i][j];
        Color c1=new Color(c[i][j]);
        Color c2=new Color(c[i-1][j-1]);
        Color c3=new Color(c[i-1][j]);
        Color c4=new Color(c[i-1][j+1]);
        Color c5=new Color(c[i][j-1]);
        Color c6=new Color(c[i][j+1]);
        Color c7=new Color(c[i+1][j-1]);
        Color c8=new Color(c[i+1][j]);
        Color c9=new Color(c[i+1][j+1]);
        int r=c2.getRed()+c3.getRed()+c4.getRed()+c5.getRed()+c6.getRed()+c7.getRed()+c8.getRed()+c9.getRed()+c1.getRed()*deg;
        int g=c2.getGreen()+c3.getGreen()+c4.getGreen()+c5.getGreen()+c6.getGreen()+c7.getGreen()+c8.getGreen()+c9.getGreen()+c1.getGreen()*deg;
        int b=c2.getBlue()+c3.getBlue()+c4.getBlue()+c5.getBlue()+c6.getBlue()+c7.getBlue()+c8.getBlue()+c9.getBlue()+c1.getBlue()*deg;
        Color col=new Color(r/(deg2),g/(deg2),b/(deg2),c1.getAlpha());
        p=col.getRGB();
        this.bits.setRGB(i, j, p);
    }
    }
    }
    
    void Blur(Pigment mult,int deg){
    int c[][]=new int[Width()][Height()];
    for(int i=0;i<Width();i++){
    for(int j=0;j<Height();j++){
        c[i][j]=this.bits.getRGB(i, j);
    }
    }
    int deg1=deg+1;
    int deg2=2550;
            
    for(int i=1;i<Width()-1;i++){
    for(int j=1;j<Height()-1;j++){
        //c[i][j]=this.bits.getRGB(i, j);
        int p=c[i][j];
        Color c1=new Color(c[i][j]);
        Color c2=new Color(c[i-1][j-1]);
        Color c3=new Color(c[i-1][j]);
        Color c4=new Color(c[i-1][j+1]);
        Color c5=new Color(c[i][j-1]);
        Color c6=new Color(c[i][j+1]);
        Color c7=new Color(c[i+1][j-1]);
        Color c8=new Color(c[i+1][j]);
        Color c9=new Color(c[i+1][j+1]);
        int r=c2.getRed()+c3.getRed()+c4.getRed()+c5.getRed()+c6.getRed()+c7.getRed()+c8.getRed()+c9.getRed();
        int g=c2.getGreen()+c3.getGreen()+c4.getGreen()+c5.getGreen()+c6.getGreen()+c7.getGreen()+c8.getGreen()+c9.getGreen();
        int b=c2.getBlue()+c3.getBlue()+c4.getBlue()+c5.getBlue()+c6.getBlue()+c7.getBlue()+c8.getBlue()+c9.getBlue();
        Color col=new Color((((r*mult.Red())/deg2)+c1.getRed()*deg)/(deg1),((g*mult.Green())/deg2+c1.getGreen()*deg)/(deg1),((b*mult.Blue())/deg2+c1.getBlue()*deg)/(deg1),c1.getAlpha());
        p=col.getRGB();
        this.bits.setRGB(i, j, p);
    }
    }
    }
    void Update() {
    }

    void Draw(int x, int y, Graphics g) {
        g.drawImage(bits, x, y, null);
    }
}

class AnimImage {

    GImage image[];
    int size;

    AnimImage(int images, int sz) {
        size = sz;
        image = new GImage[images];
        for (int i = 0; i < images; i++) {
            image[i] = new GImage(sz, sz);
        }
    }

    void Animal(double seed, Pigment colour) {
        Molecula mole = new Molecula(Linear.PI2 / seed, (double) (seed * seed) / Integer.MAX_VALUE, 1.0 / seed);
        //
        double seeds = Linear.PI2 / (double) (seed);
        double seeder = (double) (seed) / Integer.MAX_VALUE;
        double seeded = seeder;
        int skinx[][] = new int[3][3];
        int skiny[][] = new int[3][3];
        int skinc[][][] = new int[3][3][3];
        int order[] = new int[3];
        int p[] = new int[3];
        p[0] = colour.Red();
        p[1] = colour.Green();
        p[2] = colour.Blue();
        //skin
        for (int si = 0; si < 3; si++) {
            mole.SingleCalc();
            order[si] = (int) (2 + mole.sine + mole.cosine);
            for (int sj = 0; sj < 3; sj++) {
                mole.SingleCalc();
                skinx[si][sj] = (int) (mole.sine + mole.cosine);
                mole.SingleCalc();
                skiny[si][sj] = (int) (mole.sine + mole.cosine);
                mole.SingleCalc();
                skinc[0][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Red()) >> 1;
                mole.SingleCalc();
                skinc[1][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Green()) >> 1;
                mole.SingleCalc();
                skinc[2][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Blue()) >> 1;
            }
        }
        for (int i = 0; i < 16; i++) {
            seeded = seeder * (1 + i);
            int w = image[i].Width() >> 1;
            double xb[] = new double[256];
            double yb[] = new double[256];
            for (int k = 0; k < 8; k++) {
                double ix = 0;
                double iy = 0;
                mole = new Molecula(seeds, seeder, seeded);
                seeded += seeder;
                for (int j = 0; j < size; j++) {
                    mole.LineCalc(1 + j);
                    ix += mole.cosine;
                    iy += mole.sine;
                    double sx = (ix + xb[j]) * 0.5;
                    double sy = (iy + yb[j]) * 0.5;
                    if (k > 0) {
                        xb[j] = sx;
                        yb[j] = sy;
                    } else {
                        xb[j] = ix;
                        yb[j] = iy;
                    }
                    int x1 = w + (short) (sx);
                    int x2 = w - (short) (sx);
                    int y1 = w + (short) (sy);
                    //ushort y2 = w - cast(short)(sy);
                    double n = (2 - Math.abs(Math.sqrt(ix * ix + iy * iy)) / w);
                    n *= Tessa.noise((float) ix, (float) iy);

                    int c[] = new int[3];
                    for (int oi = 0; oi < 3; oi++) {
                        switch (order[oi]) {
                            case 0: {
                                c[oi] = (int) (Math.abs((1 + mole.cosine) * p[oi] * n));
                                break;
                            }
                            case 1: {
                                c[oi] = (int) (Math.abs((1 + mole.sine) * p[oi] * n));
                                break;
                            }
                            case 2: {
                                c[oi] = (int) (Math.abs((2 + mole.cosine + mole.sine) * p[oi] * n * 0.5));
                                break;
                            }
                            case 3: {
                                c[oi] = (int) (Math.abs((2 + mole.cosine - mole.sine) * p[oi] * n * 0.5));
                                break;
                            }
                            case 4: {
                                c[oi] = (int) (Math.abs((2 - mole.cosine - mole.sine) * p[oi] * n * 0.5));
                                break;
                            }
                        }
                    }
                    for (int si = 0; si < 3; si++) {

                        for (int sj = 0; sj < 3; sj++) {
                            int rs = Math.abs((int) (c[0] + skinc[0][si][sj])) >> 1;
                            int gs = Math.abs((int) (c[1] + skinc[1][si][sj])) >> 1;
                            int bs = Math.abs((int) (c[2] + skinc[2][si][sj])) >> 1;
                            image[i].AddAlphaPixel(x1 + skinx[si][sj], y1 + skiny[si][sj], 255, rs, gs, bs);
                            image[i].AddAlphaPixel(x2 - skinx[si][sj], y1 + skiny[si][sj], 255, rs, gs, bs);
                        }
                    }
                }
            }
        }
    }

    void Scape(double seed, Pigment colour) {
        Molecula mole = new Molecula(Linear.PI2 / seed, (double) (seed * seed) / Integer.MAX_VALUE, 1.0 / seed);
        //
        int skinx[][] = new int[3][3];
        int skiny[][] = new int[3][3];
        int skinc[][][] = new int[3][3][3];
        int order[] = new int[3];
        int p[] = new int[3];
        p[0] = colour.Red();
        p[1] = colour.Green();
        p[2] = colour.Blue();

        //skin
        for (int si = 0; si < 3; si++) {
            mole.SingleCalc();
            order[si] = (int) (2 + mole.sine + mole.cosine);
            for (int sj = 0; sj < 3; sj++) {
                mole.SingleCalc();
                skinx[si][sj] = (int) (mole.sine + mole.cosine);
                mole.SingleCalc();
                skiny[si][sj] = (int) (mole.sine + mole.cosine);
                mole.SingleCalc();
                skinc[0][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Red()) >> 1;
                mole.SingleCalc();
                skinc[1][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Green()) >> 1;
                mole.SingleCalc();
                skinc[2][si][sj] = Math.abs((int) (mole.sine + mole.cosine) * colour.Blue()) >> 1;
            }
        }
        double seeds = Linear.PI2 / (double) (seed);
        double seeder = (double) (seed) / Integer.MAX_VALUE;
        double seeded = seeder;
        Pigment bc = new Pigment(colour.Red() >> 4, colour.Green() >> 4, colour.Blue() >> 4);
        for (int i = 0; i < 16; i++) {
            image[i].Clear(bc);
            //seed++;
            seeded = seeder * (1 + i * 128);
            mole = new Molecula(seeds, seeder, seeded);
            int w = image[i].Width() >> 1;
            for (int k = 0; k < 8; k++) {
                double ix = 0;
                double iy = 0;
                for (int j = 0; j < size; j++) {
                    mole.SingleCalc();
                    ix += (mole.cosine * j);
                    iy += (mole.sine * j);
                    double sx = ix;
                    double sy = iy;

                    int x1 = w + (short) (sx);
                    int x2 = w - (short) (sx);
                    int y1 = w + (short) (sy);
                    int y2 = w - (short) (sy);

                    int c[] = new int[3];
                    for (int oi = 0; oi < 3; oi++) {
                        switch (order[oi]) {
                            case 0: {
                                c[oi] = (int) (Math.abs((1 + mole.cosine) * p[oi]));
                                break;
                            }
                            case 1: {
                                c[oi] = (int) (Math.abs((1 + mole.sine) * p[oi]));
                                break;
                            }
                            case 2: {
                                c[oi] = (int) (Math.abs((2 + mole.cosine + mole.sine) * p[oi] * 0.5));
                                break;
                            }
                            case 3: {
                                c[oi] = (int) (Math.abs((2 + mole.cosine - mole.sine) * p[oi] * 0.5));
                                break;
                            }
                            case 4: {
                                c[oi] = (int) (Math.abs((2 - mole.cosine - mole.sine) * p[oi] * 0.5));
                                break;
                            }
                        }
                    }
                    for (int si = 0; si < 3; si++) {

                        for (int sj = 0; sj < 3; sj++) {
                            int rs = Math.abs((int) (c[0] + skinc[0][si][sj])) >> 1;
                            int gs = Math.abs((int) (c[1] + skinc[1][si][sj])) >> 1;
                            int bs = Math.abs((int) (c[2] + skinc[2][si][sj])) >> 1;
                            image[i].AddAlphaPixel(x1 + skinx[si][sj], y1 + skiny[si][sj], 255, rs, gs, bs);
                            image[i].AddAlphaPixel(x2 - skinx[si][sj], y1 + skiny[si][sj], 255, rs, gs, bs);
                            image[i].AddAlphaPixel(x1 + skinx[si][sj], y2 - skiny[si][sj], 255, rs, gs, bs);
                            image[i].AddAlphaPixel(x2 - skinx[si][sj], y2 - skiny[si][sj], 255, rs, gs, bs);
                        }
                    }
                }
            }
        }
    }

    GImage GImage(int i) {
        return image[i];
    }
};

class VehicleImage {

    java.awt.image.BufferedImage image[];
    java.awt.image.BufferedImage result;
    int cx;
    int cy;
    float lastAngle;

    public VehicleImage(Vehicle v, Pigment body) {
        //image=CarRage.LoadImage("Test.png",".png");
        int size = (int) v.design.size;
        int length = v.detector.length;
        int engine = v.engine;
        int sz = (3 + length) * size;
        EffectMap imap = new EffectMap(sz, sz);
        int y = size;
        int x = size + (int) (size * 0.5 * (length - 1));
        int w = size * 2;
        int h = size * (length + 1);
        if (v.getClass().equals(Bike.class)) {
            w = size;
            x += size >> 1;
        }
        int r = sz >> 4;
        int e = engine / (size >> 1);
        Pigment metal = new Pigment(32, 32, 32);
        Pigment roof = new Pigment(128, 128, 128);
        Pigment glass = new Pigment(255, 255, 255);
        metal.Average(body);
        roof.Add(body);
        glass.Hint(body);
        //imap.FillRectangle(x,y,x+w,y+h,metal,false,true);//body
        SkinData skin = new SkinData(8);
        imap.FillRectangle(x + r, y + r, x + w - r, y + h - r, skin, glass, roof, metal, false, true, PixelEffect.Hint);//body
        PaintJob(imap, x, y, w, h, sz, body);
        imap.SetPixel(x, y, new Pigment(0, 0, 0, 0), false, true);
        imap.SetPixel(x, y + h, new Pigment(0, 0, 0, 0), false, true);
        imap.SetPixel(x + w, y + h, new Pigment(0, 0, 0, 0), false, true);
        imap.SetPixel(x + w, y, new Pigment(0, 0, 0, 0), false, true);
        Shape(imap, r, x, y, w, h, true);
        Shape(imap, r, x, y, w, h, false);
        Shape(imap, r, x, y, w, h, true);
        Indent(imap, r, x, y, w, h, metal, v.getClass().equals(Van.class) || v.getClass().equals(Truck.class) || v.getClass().equals(Car.class));
        metal.Average(body);
        roof.Add(body);
        glass.Hint(body);
        if (v.getClass().equals(Van.class)) {
            imap.FillRectangle(x + 1, y + 1, x + w - 1, y + h - e, roof, false, true, PixelEffect.Tint);//roof
        } else if (v.getClass().equals(Truck.class)) {
            metal.Average(new Pigment(0, 0, 0));
            imap.FillRectangle(x + 1, y + 1, x + w - 1, y + h - e, metal, false, true, PixelEffect.Tint);//roof
        } else {
            imap.FillRectangle(x + r, y + r, x + w - r, y + h - r - e, roof, false, true, PixelEffect.Tint);//roof
        }
        if (v.getClass().equals(Car.class)) {
            imap.FillRectangle(x + r, y + h - r, x + w - r, y + h - r - e, glass, false, true, PixelEffect.Average);//wind screen
        } else if (v.getClass().equals(Van.class) || v.getClass().equals(Truck.class)) {
            imap.FillRectangle(x + r, y + h - r - e, x + w - r, y + h - r - e, glass, false, true, PixelEffect.Average);//wind screen
        } else if (v.getClass().equals(Bus.class)) {
            imap.FillRectangle(x + r, y + h - r + e, x + w - r, y + h - r - e, glass, false, true, PixelEffect.Average);//wind screen
        }

        if (v.getClass().equals(Bike.class)) {

            metal.Average(new Pigment(0, 0, 0));
            int r2 = r * 2;
            imap.FillRectangle(x + r, y - r2, x + w - r, y + r2 + 1, metal, false, true, PixelEffect.Average);//roof
            imap.FillRectangle(x + r, y + h - r2, x + w - r, y + h + r2, metal, false, true, PixelEffect.Average);//roof

        }
        Section(imap, r, x, y, w, h, roof);
        Part(imap, r, x, y, w, h, metal);
        Body(imap, r, x, y, w, h, glass);
        //
        Indent(imap, r, x, y, w, h, metal, v.getClass().equals(Van.class) || v.getClass().equals(Truck.class) || v.getClass().equals(Bike.class));
        if (v.getClass().equals(Truck.class)) {
            Indent(imap, r, x, y, w, h, metal, true);
        }
        //
        EffectMap paint = new EffectMap(sz, sz);
        paint.Merge(imap, PixelEffect.Negative);
        PaintJob(paint, x, y, w, h, sz, body);
        paint.Merge(imap, PixelEffect.Average);
        imap.Merge(paint, PixelEffect.Tint);
        imap.SolidColor(8);
        image = new java.awt.image.BufferedImage[10];
        image[0] = imap.ToBufferedImage();
        lastAngle = -Float.MAX_VALUE;
        for (int i = 1; i < 10; i++) {
            image[i] = new java.awt.image.BufferedImage(image[0].getWidth(), image[0].getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
            for (int xx = 0; xx < image[0].getWidth(); xx++) {

                for (int yy = 0; yy < image[0].getHeight(); yy++) {
                    int rgb = image[i - 1].getRGB(xx, yy);
                    int alpha = (rgb & 0xFF000000) >> 24;
                    int red = (rgb & 0x00FF0000) >> 16;
                    int green = (rgb & 0x0000FF00) >> 8;
                    int blue = (rgb & 0x000000FF);
                    if (alpha < 0) {
                        Pigment c = new Pigment(255, red, green, blue);
                        Pigment d = new Pigment(255, 0, 0, 0);
                        //d.Average(c);
                        c.Hint(d);
                        image[i].setRGB(xx, yy, c.ToRGB());
                    }
                }
            }
        }
        cx = image[0].getWidth() >> 1;
        cy = image[0].getHeight() >> 1;
    }

    void Body(EffectMap imap, int r, int x, int y, int w, int h, Pigment color) {
        int rx = Random.Next(w) / 2;
        int ry = Random.Next(h) / 2;
        imap.DrawRectangle(x + rx, y + ry, x + w / 2 - rx, y + h / 2 - ry, color, false, true, PixelEffect.Hint);//
        imap.DrawRectangle(x + w / 2 + rx, y + ry, x + w - rx, y + h / 2 - ry, color, false, true, PixelEffect.Hint);//
        imap.DrawRectangle(x + rx, y + ry + h / 2, x + w / 2 - rx, y + h - ry, color, false, true, PixelEffect.Hint);//
        imap.DrawRectangle(x + w / 2 + rx, y + ry + h / 2, x + w - rx, y + h - ry, color, false, true, PixelEffect.Hint);//

    }

    void Part(EffectMap imap, int r, int x, int y, int w, int h, Pigment color) {
        int rx = Random.Next(w >> 1);
        imap.DrawRectangle(x + rx, y, x + w / 2 - rx, y + h, color, false, true, PixelEffect.Hint);//
        imap.DrawRectangle(x + w / 2 + rx, y, x + w - rx, y + h, color, false, true, PixelEffect.Hint);//

    }

    void Section(EffectMap imap, int r, int x, int y, int w, int h, Pigment color) {
        int rx = Random.Next(w);
        int ry = Random.Next(h);
        imap.DrawRectangle(x + rx, y + ry, x + w - rx, y + h - ry, color, false, true, PixelEffect.Hint);//
    }

    void Indent(EffectMap imap, int r, int x, int y, int w, int h, Pigment color, boolean asym) {

        Pigment shade = new Pigment(0, 0, 0, 0);
        shade.Average(color);
        int rx = Random.Next(w);
        int ry = Random.Next(h);
        int sx = (w + Random.Next(w)) / (r * 2 + Random.Next(w >> 1));
        int sy = (h + Random.Next(h)) / (r * 2 + Random.Next(h >> 1));
        imap.FillRectangle(x + rx - sx, y + ry - sy, x + rx + sx, y + ry + sy, shade, false, true, PixelEffect.Multiply);//
        imap.FillRectangle(x + w - rx - sx, y + ry - sy, x + w - rx + sx, y + ry + sy, shade, false, true, PixelEffect.Multiply);//
        if (asym) {
            rx = Random.Next(w);
            ry = Random.Next(h);
            sx = (w + Random.Next(w)) / (r * 2 + Random.Next(w >> 1));
            sy = (h + Random.Next(h)) / (r * 2 + Random.Next(h >> 1));

        }
        imap.FillRectangle(x + rx - sx, y + h - ry - sy, x + rx + sx, y + h - ry + sy, shade, false, true, PixelEffect.Multiply);//
        imap.FillRectangle(x + w - rx - sx, y + h - ry - sy, x + w - rx + sx, y + h - ry + sy, shade, false, true, PixelEffect.Multiply);//
        //
    }

    void Shape(EffectMap imap, int r, int x, int y, int w, int h, boolean asym) {

        Pigment shade = new Pigment(255, 255, 255);
        shade.v[0] = -255;
        int rx = Random.Next(r);
        int ry = Random.Next(r);
        int sx = Random.Next(r);//(w+Random.Next(w))/(r*2+Random.Next(w>>1));
        int sy = Random.Next(r);//(h+Random.Next(h))/(r*2+Random.Next(h>>1));
        imap.FillRectangle(x + rx - sx, y + ry - sy, x + rx + sx, y + ry + sy, shade, false, true, PixelEffect.Multiply);//
        imap.FillRectangle(x + w - rx - sx, y + ry - sy, x + w - rx + sx, y + ry + sy, shade, false, true, PixelEffect.Multiply);//
        if (asym) {
            rx = Random.Next(r);
            ry = Random.Next(r);
            sx = Random.Next(r);//(w+Random.Next(w))/(r*2+Random.Next(w>>1));
            sy = Random.Next(r);//(h+Random.Next(h))/(r*2+Random.Next(h>>1));

        }
        imap.FillRectangle(x + rx - sx, y + h - ry - sy, x + rx + sx, y + h - ry + sy, shade, false, true, PixelEffect.Multiply);//
        imap.FillRectangle(x + w - rx - sx, y + h - ry - sy, x + w - rx + sx, y + h - ry + sy, shade, false, true, PixelEffect.Multiply);//
        //
    }

    public void paint(Graphics g, int osx, int osy, float angle, int light) {
        if (angle != lastAngle) {
            java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
            double a = Vehicle.Angle + angle;
            if (a > 0) {
                at.rotate(a, cx, cy);
                java.awt.image.AffineTransformOp op = new java.awt.image.AffineTransformOp(at, java.awt.image.AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                result = op.createCompatibleDestImage(image[light], null);
                op.filter(image[light], result);
                lastAngle = angle;
            }
        }
        g.drawImage(result, osx - cx, osy - cy, null);

    }

    void PaintJob(EffectMap imap, int x, int y, int w, int h, int sz, Pigment body) {
        Chassis chas = new Chassis(Random.Next(80), 0, 100);
        DoubleAdder xa = new DoubleAdder(Random.NextDouble(), 1 + Random.NextDouble(), 2 + Random.NextDouble() + Random.Next(10), 1 + Random.NextDouble());
        DoubleAdder ya = new DoubleAdder(Random.NextDouble(), 1 + Random.NextDouble(), 2 + Random.NextDouble() + Random.Next(50), 1 + Random.NextDouble());
        chas.CalcPath(0, Random.Next(sz * 2) - Random.Next(sz * 2), sz, 0.5, 0.5, 5 + Random.Next(10), 5 + Random.Next(10), xa, ya, null);

        //Pigment paint=new Pigment(Random.Next(255),Random.Next(255),Random.Next(255));
        //paint.Average(body);
        imap.startx = x;
        imap.starty = y;
        imap.width = x + w;
        imap.height = y + h;
        int ang = Random.Next(45);
        int rot = Random.Next(45);
        do {
            Pigment paint = new Pigment(Random.Next(255), Random.Next(255), Random.Next(255));
            paint.Average(body);
            paint.Average(body);
            imap.DrawSymetricLines(chas.result, sz >> 1, (sz >> 1) + Random.Next(sz >> 1) - Random.Next(sz >> 1), ang, rot, 2, 10, true, paint, false, true, PixelEffect.Hint);
            ang += Random.Next(45);
            rot += Random.Next(45);
            //System.out.println("Error "+ang+" "+rot);
        } while (ang < 360);
        System.out.println("Error " + ang + " " + rot);
        imap.startx = 0;
        imap.starty = 0;
        imap.width = sz;
        imap.height = sz;

    }
}
