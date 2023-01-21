package arcengine;

import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
class Resource {

    int raw;
    int processed;
    int waste;
    int total;
}

class Food extends Resource {
}

class EnergySource extends Resource {
}

class Chemical extends Resource {
}

class Material extends Resource {
}

class Money {

    Government source;
    float value;
    float interest;
    float share;
    float total;
}

class People {

    int intelligence;
    int education;
    long number;
    float credits;
}

class Assets {

    Food store;
    EnergySource grid;
    Chemical factor;
    Material resurces;
    //
    Money growth;
    Money social;
    Money tax;
    Money gdp;
    //
    java.util.Set<Money> credit = new java.util.HashSet<Money>();
    java.util.Set<Money> debt = new java.util.HashSet<Money>();
    java.util.Set<Money> exchange = new java.util.HashSet<Money>();
    java.util.Set<Money> corruption = new java.util.HashSet<Money>();
    //
    java.util.Set<Building> aquisitions = new java.util.HashSet<Building>();
    java.util.Set<Service> services = new java.util.HashSet<Service>();
    java.util.Set<Tech> technology = new java.util.HashSet<Tech>();
    java.util.Set<People> crime = new java.util.HashSet<People>();

}

class Population {

    People young;
    People adult;
    People old;
    People sick;
    java.util.Set<Society> powerfull = new java.util.HashSet<Society>();
    java.util.Set<Society> religion = new java.util.HashSet<Society>();
    java.util.Set<Society> rich = new java.util.HashSet<Society>();
    java.util.Set<Society> poor = new java.util.HashSet<Society>();
}

/**
 *
 * @author Gerwyn
 */
public class Government {

    Pigment shade;
    Adder r;
    Adder g;
    Adder b;
    Color color;
    LandSection seat;
    Assets assets;
    Population population;

    public Government(Pigment shade) {
        this.shade = shade;
        this.r = new Adder(shade.Red() >> 1, (shade.Red() + 255) >> 1, (1 + (shade.Red() >> 5)));
        this.g = new Adder(shade.Green() >> 1, (shade.Green() + 255) >> 1, (1 + (shade.Green() >> 5)));
        this.b = new Adder(shade.Blue() >> 1, (shade.Blue() + 255) >> 1, (1 + (shade.Blue() >> 5)));
        this.color = this.shade.Colour();
    }

    boolean RunSimulation() {
        r.Calc();
        g.Calc();
        b.Calc();
        this.shade = new Pigment(r.subject, g.subject, b.subject);
        this.color = this.shade.Colour();
        return false;
    }
}
