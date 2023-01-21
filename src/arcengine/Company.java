package arcengine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gerwyn Jones
 */
class PlanetShare{
    Company owner;
    Point systemCoord;
    int planetIndex;
    double value;
    Core resources;
    
}
public class Company extends PlanetShare{
    String name;
    double investment;
    double profits;
    double balance;
    People employees;
    Government laws;
}
class Corporation extends Company{
    RingList<PlanetShare> shares=new RingList();
    RingList<Company> companies=new RingList();
    
    
}