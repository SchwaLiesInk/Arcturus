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
interface OneStateFunction {

    public abstract double f(double x);
}

@FunctionalInterface
interface ZeroStateFunction {

    public abstract double f0();
}

@FunctionalInterface
interface TwoStateFunction {//reducer

    public abstract double f2(double x, double y);
}

@FunctionalInterface
interface ThreeStateFunction {

    public abstract double f3(double x, double y, double z);
}

@FunctionalInterface
interface FourStateFunction {

    public abstract double f4(Vector v);
}

@FunctionalInterface
interface VectorStateFunction {

    public abstract Vector fv(double x, double y, double z, double w);
}

@FunctionalInterface
interface State3DFunction {

    public abstract Vector f3D(Matrix m);
}

@FunctionalInterface
interface MultiStateFunction {

    public abstract double g(RingList<Double> xyz);
}

@FunctionalInterface
interface OneSumFunction {

    double h(OneStateFunction fn);
}

@FunctionalInterface
interface TwoSumFunction {

    double h2(TwoStateFunction fn);
}

@FunctionalInterface
interface ThreeSumFunction {

    double h3(ThreeStateFunction fn);
}

@FunctionalInterface
interface FourSumFunction {

    Vector h4(FourStateFunction fn);
}

@FunctionalInterface
interface MultiSumFunction {

    RingList<Double> i(MultiStateFunction fn);
}

@FunctionalInterface
interface Sum3DFunction {

    public abstract Matrix f3D(State3DFunction m);
}
