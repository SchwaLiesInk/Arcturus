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
public class Local extends Linear implements Comparable {

    Local() {
        x = 0;
        y = 0;
        z = 0;
    }

    Local(short xx, short yy) {
        x = xx;
        y = yy;
        z = 0;
    }

    Local(int xx, int yy) {
        x = xx;
        y = yy;
        z = 0;
    }

    Local(double xx, double yy) {
        x = (int) xx;
        y = (int) yy;
        z = 0;
    }

    public Local(Local loc) {
        this.x = loc.x;
        this.y = loc.y;
        this.z = loc.z;
    }

    ;

    static boolean Equal(Local a, Local b) {
        return a.x == b.x && a.y == b.y;
    }

    static boolean NotEqual(Local a, Local b) {
        return a.x != b.x || a.y != b.y;
    }

    static Local Add(Local p, Local q) {
        return new Local(p.x + q.x, p.y + q.y);
    }

    static Local Subtract(Local p, Local q) {
        return new Local(p.x - q.x, p.y - q.y);
    }

    static Local Multiply(Local p, float s) {
        return new Local((int) (p.x * s), (int) (p.y * s));
    }

    boolean Equals(Local b) {
        return x == b.x && y == b.y;
    }

    boolean DoesNotEqual(Local b) {
        return x != b.x || y != b.y;
    }

    Local Add(Local q) {
        int xx = this.x + q.x;
        int yy = this.y + q.y;
        return new Local(xx, yy);
    }

    Local Subtract(Local q) {
        int xx = x - q.x;
        int yy = y - q.y;
        return new Local(xx, yy);
    }

    Local Multiply(float s) {
        int xx = (int) (x * s);
        int yy = (int) (y * s);
        return new Local(xx, yy);
    }

    Local Negative() {
        int xx = -x;
        int yy = -y;
        return new Local(xx, yy);
    }

    int Distance(Local from) {
        int xp = x - from.x;
        int yp = y - from.y;
        return (int) Math.sqrt(xp * xp + yp * yp);
    }

    int Distance2(Local from) {
        int xp = x - from.x;
        int yp = y - from.y;
        return (int) (xp * xp + yp * yp);
    }

    int Length2() {
        return (x * x + y * y);
    }

    int Length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    int Rank(int pitch) {
        return x + y * pitch;
    }

    static Local MaxValue() {
        return new Local(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    static Local MinValue() {
        return new Local(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    Local Max(Local m) {
        return new Local(Math.max(x, m.x), Math.max(y, m.y));
    }

    Local Min(Local m) {
        return new Local(Math.min(x, m.x), Math.min(y, m.y));
    }
    //
    // Methods
    //

    @Override
    public String toString() {
        return "Local x" + x + " y" + y;
    }

    //
    // Accessor methods
    //
    /**
     * Set the value of x
     *
     * @param newVar the new value of x
     */
    protected void setX(int newVar) {
        x = newVar;
    }

    /**
     * Get the value of x
     *
     * @return the value of x
     */
    protected int getX() {
        return x;
    }

    /**
     * Set the value of y
     *
     * @param newVar the new value of y
     */
    protected void setY(int newVar) {
        y = newVar;
    }

    /**
     * Get the value of y
     *
     * @return the value of y
     */
    protected int getY() {
        return y;
    }

    //
    // Other methods
    //
    @Override
    public int compareTo(Object o) {
        Local com = (Local) (o);
        if (com.x == x && com.y == y) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Local.class) {
            Local com = (Local) (o);
            return (com.x == x && com.y == y);
        }
        return hashCode() == o.hashCode();

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }

}