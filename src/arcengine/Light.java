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
@FunctionalInterface
interface LightEffect {

    Pigment Effect(Light from, Point to, Normal n, Pigment c);
}

public class Light extends Point implements LightEffect {

    float brightness = 0.5f;
    float energy = Short.MAX_VALUE;
    byte size;
    byte e;
    boolean on = true;
    boolean fuel = true;
    Pigment color;
    Pigment ambient;
    Normal direction;

    Light() {

    }

    Light(Point at, Pigment col, Pigment ambi) {
        this.x = at.x;
        this.y = at.y;
        this.z = at.z;
        this.r = at.r;
        this.color = col;
        this.ambient = ambi;
    }

    @Override
    public Pigment Effect(Light from, Point to, Normal n, Pigment c) {
        Vector t = from.Sub(from, to);
        double dot = t.DotProduct(t.Unity(), n);
        int r = from.ambient.Red();
        int g = from.ambient.Green();
        int b = from.ambient.Blue();
        if (dot > 0) {
            double d = t.Length();
            double dd = (Linear.MAX_MAP / (d * dot)) * from.r / (d * d);
            if (dd > 1.0f) {
                dd = 1.0f;
            } else if (dd < 0.0f) {
                dd = 0.0f;
            }
            r += c.Red() + (int) ((from.color.Red() + c.Red()) * dd);
            g += c.Green() + (int) ((from.color.Green() + c.Green()) * dd);
            b += c.Blue() + (int) ((from.color.Blue() + c.Blue()) * dd);
            r >>= 2;
            g >>= 2;
            b >>= 2;
        }
        Pigment res = new Pigment(c.Alpha(), r, g, b);
        return res;
    }
}

class DirectionalLight extends Light {

    DirectionalLight() {
        super();
    }

    @Override
    public Pigment Effect(Light from, Point to, Normal n, Pigment c) {
        double a = n.DotProduct(n, direction) * brightness;
        int r = ((int) (c.Red() * color.Red() * a) >> 8);
        int g = ((int) (c.Green() * color.Green() * a) >> 8);
        int b = ((int) (c.Blue() * color.Blue() * a) >> 8);
        //if(r>255){r=255;}
        //if(g>255){g=255;}
        //if(b>255){b=255;}
        //System.out.println("cos"+a+"r"+r+"g"+g+"b"+b);
        return new Pigment(r, g, b);

    }

}
