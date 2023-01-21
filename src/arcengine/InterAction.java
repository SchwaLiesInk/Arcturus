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
public abstract interface InterAction {

    public abstract void mouseAction(MouseZone m, InterAction i);
}
