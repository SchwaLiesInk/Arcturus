/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn
 */
public class Adder {

    int from;
    int to;
    int number;
    int subject;

    Adder(int start, int from, int to, int add) {
        this.from = from;
        this.to = to;
        this.number = add;
        this.subject = start;
    }

    Adder(int from, int to, int add) {
        this.from = from;
        this.to = to;
        this.number = add;
        this.subject = from;
    }

    public static Adder[] ColorRotation(Pigment from, Pigment to, Pigment step) {
        Adder c[] = new Adder[]{
            new Adder(from.Alpha(), to.Alpha(), step.Alpha()),
            new Adder(from.Red(), to.Red(), step.Red()),
            new Adder(from.Green(), to.Green(), step.Green()),
            new Adder(from.Blue(), to.Blue(), step.Blue())
        };
        return c;
    }

    public String ToString() {
        return "Adder from" + from + "\tto" + to + "\tadd" + number + "\tcurrent" + subject;
    }

    int Add() {
        this.subject += this.number;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    int Subtract() {
        this.subject -= this.number;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    int Calc() {
        this.subject += this.number;
        if (this.from > this.to) {
            if (this.subject <= this.to || this.subject >= this.from) {
                //bounce
                this.subject -= this.number + this.number;
                this.number = -this.number;
            }
        } else {
            if (this.subject >= this.to || this.subject <= this.from) {
                //bounce
                this.subject -= this.number + this.number;
                this.number = -this.number;
            }
        }
        return this.subject;
    }
}

class DoubleAdder {

    double from;
    double to;
    double number;
    double subject;

    DoubleAdder(double start, double from, double to, double add) {
        this.from = from;
        this.to = to;
        this.number = add;
        this.subject = start;
    }

    DoubleAdder(double from, double to, double add) {
        this.from = from;
        this.to = to;
        this.number = add;
        this.subject = from;
    }

    public static DoubleAdder[] ColorRotation(Pigment from, Pigment to, Pigment step) {
        DoubleAdder c[] = new DoubleAdder[]{
            new DoubleAdder(from.Alpha(), to.Alpha(), step.Alpha()),
            new DoubleAdder(from.Red(), to.Red(), step.Red()),
            new DoubleAdder(from.Green(), to.Green(), step.Green()),
            new DoubleAdder(from.Blue(), to.Blue(), step.Blue())
        };
        return c;
    }

    public String ToString() {
        return "Adder from" + from + "\tto" + to + "\tadd" + number + "\tcurrent" + subject;
    }

    double Add() {
        this.subject += this.number;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    double Add(double n) {
        this.subject += n;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    double Subtract() {
        this.subject -= this.number;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    double Subtract(double n) {
        this.subject -= n;
        if (from > to) {
            if (this.subject < to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject > from) {
                this.subject = to + (this.subject - from);
            }
        } else {
            if (this.subject > to) {
                this.subject = from - (to - this.subject);
            }
            if (this.subject < from) {
                this.subject = to + (this.subject - from);
            }
        }

        return this.subject;
    }

    double Calc() {
        this.subject += this.number;
        if (this.from > this.to) {
            if (this.subject <= this.to || this.subject >= this.from) {
                //bounce
                this.subject -= this.number + this.number;
                this.number = -this.number;
            }
        } else {
            if (this.subject >= this.to || this.subject <= this.from) {
                //bounce
                this.subject -= this.number + this.number;
                this.number = -this.number;
            }
        }
        return this.subject;
    }
}
