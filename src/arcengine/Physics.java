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
public class Physics {

    static final double year = (24 * 60 * 60 * 356);
    static final double day = (24 * 60 * 60);
    static final double hour = (60 * 60);
    static final double week = day * 7;
    static final double month = day * 31;
    static final double time = week;
    static final double SpeedOfLight = (3.0e8);
    static final double lightYear = (3.0e8) * year;
    static double uG = 6.5e-11;

    static double CalculateOrbitalVelocityMagnitude(double centerOfGravity, double dis) {
        double ga = (uG * centerOfGravity) / (dis);
        return Math.sqrt(ga);
    }

    static Vector CentrefugalForce(Vector velocity, Vector directionNormalToCenter, double distance) {
        double v2 = velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z;
        double d2 = distance;
        double ratio = (v2 / d2);
        return new Vector(directionNormalToCenter.x * ratio, directionNormalToCenter.y * ratio, directionNormalToCenter.z * ratio);
    }

    static double CentrefugalForceMagnitude(Vector velocity, double distance) {
        double v2 = velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z;
        double d2 = distance;
        double ratio = (v2 / d2);
        return ratio;
    }

    static double CentrefugalMagnitude(double velocity, double distance) {
        double v2 = velocity * velocity;
        double d2 = distance;
        double ratio = (v2 / d2);
        return ratio;
    }

    static double CircularVelocityMagnitude(double centeralMass, double distance) {
        return Math.sqrt(uG * centeralMass / distance);
    }

    static double EscapeVelocityMagnitude(double centeralMass, double distance) {
        return Math.sqrt(2.0 * uG * centeralMass / distance);
    }

    static double SurfaceGravityMagnitude(double centeralMass, double radius) {
        return (uG * centeralMass) / (radius * radius);
    }

    static double GravitationalForceMagnitude(double centeralMass1, double centeralMass2, double distance) {
        return (uG * centeralMass1 * centeralMass2) / (distance * distance);
    }

    static double MomentOfInertia(double mass, double distance) {
        return mass * (distance * distance);
    }

    static double MomentOfInertialSpin(double mass, double radius) {
        return 0.4 * mass * (radius * radius);
    }

    static double CurvedKineticEnergy(double momentOfIneria, double angularVelocity) {
        return momentOfIneria * angularVelocity * angularVelocity * 0.5;
    }

    static double KineticEnergy(double mass, double velocity) {
        return mass * velocity * velocity * 0.5;
    }

    static double MomentumMagnitude(double mass, double velocity) {
        return mass * velocity;
    }

    static double RecoilVelocityMagnitude(double bulletMass, double gunMass, double muzzleVelocity) {
        return (bulletMass * muzzleVelocity) / gunMass;
    }

    static double ForceMomentumMagnitude(double mass, double velocity1, double velocity2, double time) {
        return mass * (velocity1 - velocity1) / time;
    }

    static double VelocityMagnitude(double acceleration, double time) {
        return acceleration * time;
    }

    static double VelocityDisplacementMagnitude(double acceleration, double time) {
        return (0.5 * acceleration * time * time);
    }

    static double VelocityChange(double acceleration, double displacement) {
        return Math.sqrt(2 * acceleration * displacement);
    }

    static double ForceMagnitude(double mass, double acceleration) {
        return mass * acceleration;
    }

    static double SphereVolume(double radius) {
        return (radius * radius * radius) * 4.0 / 3.0 * Math.PI;
    }

    static double SphereArea(double radius) {
        return (radius * radius) * Math.PI * 4;
    }
}
