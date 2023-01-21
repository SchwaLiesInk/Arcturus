/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;
abstract class Component extends PhysicalPoint{
    Tech tech;
    public Component(Tech tec){
    tech=tec;
    }
}
/**
 *
 * @author Gerwyn
 */
public class Tech extends Resource {

    protected double level;

    public Tech(double level) {
        if (level < 0) {
            level = 0;
        }
        if (level >= 4) {
            level = 3.9f;
        }
        this.level = level;
    }

    public double Level() {
        return level;
    }

    public void Advance(double a) {
        level += a / (1 + level);
        if (level < 0) {
            level = 0;
        }
        if (level >= 4) {
            level = 3.9f;
        }

    }

    public void Retardance(double a) {
        level -= a / (1 + level);
        if (level < 0) {
            level = 0;
        }
        if (level >= 4) {
            level = 3.9f;
        }

    }

    public static boolean CanBuild(double objectLevel, double beingLevel) {
        int i = (int) (objectLevel * 9);
        int j = (int) (beingLevel * 10);
        return (j - i > 0);
    }

    public static double CostFactor(double objectLevel, double beingLevel) {
        double cost = (objectLevel * objectLevel + objectLevel) / (1 + beingLevel);
        return cost * cost + cost;//0.022->19.11->319.184
    }

    public static double SizeFactor(double objectLevel, double beingLevel) {
        double cost = (objectLevel * objectLevel + objectLevel) / (1 + beingLevel);
        double i = (objectLevel * 9);
        double j = (beingLevel * 10);
        double sf = (j - i);
        if (sf > 1) {
            return (cost) / sf;
        } else {
            return (cost * cost + cost) * (1 + Math.abs(sf));
        }
    }

    public static double EfficancyFactor(double objectLevel, double beingLevel) {
        double cost = (objectLevel * objectLevel + objectLevel) / (1 + beingLevel);
        double i = (objectLevel * 9);
        double j = (beingLevel * 10);
        double sf = (j - i);
        if (sf > 1) {
            return 1.0f - 0.4f / sf;
        } else {
            return 0.6f / (1 + Math.abs(sf));
        }
    }

    public static double EffectFactor(double objectLevel, double beingLevel) {
        double effect = (objectLevel * objectLevel + objectLevel) * beingLevel;//max 74.529
        return effect;
    }

    public String TechLevel() {
        int i = (int) (level * 10);
        switch (i) {
            case 0: {
                return ("Animal");
            }//fire
            case 1: {
                return ("Stone Age");
            }//stone tools
            case 2: {
                return ("Copper Age");
            }//wheel
            case 3: {
                return ("Bronze Age");
            }//bronze tools
            case 4: {
                return ("Iron Age");
            }//steel tools 
            case 5: {
                return ("Steel Age");
            }//guns
            case 6: {
                return ("Steam Age");
            }//coal power
            case 7: {
                return ("Chemical Age");
            }//gas power
            case 8: {
                return ("Electrical Age");
            }// trans radio magnetics
            case 9: {
                return ("Atomic Age");
            }//thorium to strontium
            case 10: {
                return ("Computer Age");
            }// parrallel internet
            case 11: {
                return ("Cyber Age");
            }//robots (now)
            case 12: {
                return ("Nano Age");
            }//nano robots
            case 13: {
                return ("Fission Age");
            }//iron to aluminium
            case 14: {
                return ("Sub Fusion Age");
            }//torqcamach gen
            case 15: {
                return ("Fusion Age");
            }//cold fusion
            case 16: {
                return ("Super Fusion Age");
            }//micro fusion 
            case 17: {
                return ("Solar System Age");
            }//near planet colonization
            case 18: {
                return ("Sub Stellar Age");
            }//near star exploration
            case 19: {
                return ("Stellar Age");
            }// near star colonization
            case 20: {
                return ("Super Stellar Age");
            }// far star colonization
            case 21: {
                return ("Sub Gravity Age");
            }//gravity reversal
            case 22: {
                return ("Gravity Age");
            }//gravity propultion
            case 23: {
                return ("Super Gravity Age");
            }//99.9% free energy curve
            case 24: {
                return ("Sub Hyper Age");
            }//force fields
            case 25: {
                return ("Hyper Age");
            }//FTL travel
            case 26: {
                return ("Super Hyper Age");
            }//matter energy alteration
            case 27: {
                return ("Sub Galactic Age");
            }//galactic mapping 
            case 28: {
                return ("Galactic Age");
            }//galactic exploration
            case 29: {
                return ("Super Galactic Age");
            }//galactic traversal
            case 30: {
                return ("Sub Temporal Age");
            }//4d communication
            case 31: {
                return ("Temporal Age");
            }//time travel
            case 32: {
                return ("Super Temporal Age");
            }// alternate reality control
            case 33: {
                return ("Sub Quantum Age");
            }//matter energy creation
            case 34: {
                return ("Quantum Age");
            }// baby universe creation
            case 35: {
                return ("Super Quantum Age");
            }// black hole stablization
            case 36: {
                return ("Dimensional Age");
            }// alternate reality travelersal
            case 37: {
                return ("Sub Universal Age");
            }// universe exploration
            case 38: {
                return ("Universal Age");
            }//universe colonization
            case 39: {
                return ("Super Universal Age");
            }//universe creation

        }
        return "unknown";
    }
}
