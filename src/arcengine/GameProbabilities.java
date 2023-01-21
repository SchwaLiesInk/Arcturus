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
public class GameProbabilities {
    public final static double C4=303595763.73350418579;
    public final static double D4=2.096e7;
    private final static double Time=1.0;//s^5
    public final static double Percentage =0.01/Time;
    //
    private final static double Space=Time+Time;//2 s^5
    private final static double Matter=Space+Time;//3 s^5
    private final static double Gravity=Matter+Time;//4 s^5
    private final static double Light=Gravity*Time;//4  s^10
    private final static double Antimatter=Matter/Light;//0.75 s^-5
    private final static double Energy=Matter*Light*Light;//48 s^25
    private final static double DarkEnergy=Matter/Energy;//0.063*s^20
    public final static double DarkMatter=Time/Matter;//0.3333333333
    private final static double DarkGravity=DarkMatter/Energy;//6.944e-3 s^-25
    public final static double ElectroMagnetics=Gravity/Matter;//1.333333333333
    private final static double Electrons=ElectroMagnetics/Light;//0.33333333333 s^-10
    private final static double Darkness=DarkMatter/DarkEnergy;//5.3333333333333 s^20    
    private final static double TotalEnergy=DarkEnergy*Energy;//3 s^5
    private final static double TotalReality=TotalEnergy+Matter+Gravity+Space+Time;//13 s^5
    private final static double TotalPossiblities=((Math.pow(TotalReality*Electrons*Light*ElectroMagnetics*Math.PI*Math.PI, 5))/Time)*DarkGravity;//4.288e9 s^-5
    public final static double TotalLaws=(TotalPossiblities*Light)/Space;//8.576e9
    public final static double TotalUDS=(Math.pow((TotalLaws*Space)/(1.4e6*Time),2)*Space*C4*Physics.year*TotalPossiblities)/(14.3e9*Physics.year*C4);//9.001e7
    public final static double Dreams=C4-TotalUDS;//2.136e8
    public final static double WithLife=TotalUDS*Math.PI*Math.PI;//8.883e8
    public final static double WithIntelligentLife=WithLife*((Light)/(Space*Matter));//5.922e8
    public final static double SpaceTravellers=(WithIntelligentLife*TotalReality*Space*Gravity*Light*Electrons*Matter)/(TotalLaws*DarkMatter*DarkEnergy*Darkness*Math.pow(Time, 4));//258.555
    public final static double Alternate=(WithIntelligentLife+WithLife)/Dreams;//6.9
    public final static double DangerBeings=WithIntelligentLife/SpaceTravellers;//2.291e6
    public final static double DangerCreatures=WithLife/SpaceTravellers;//3.436e6
    public final static double Risks=DangerCreatures/DangerBeings;//1.5
    public final static double IntelligentExtinctions=WithIntelligentLife/WithLife;//6.66666
    public final static double LifeSaved=WithIntelligentLife-(WithIntelligentLife*IntelligentExtinctions)/SpaceTravellers;//5.907e8
    public final static double LifeFound=WithLife-(WithIntelligentLife*IntelligentExtinctions)/SpaceTravellers;//8.868e8
    public final static double Life=LifeFound/LifeSaved;//1.5
    public final static double Encounters=Life*Space;//0.03
    public final static double Interactions=(Life+Encounters*Encounters+Risks)/IntelligentExtinctions;//4.503
    public final static double UniversalEconomy=(TotalUDS*Life*(1.4e6*Time))/(Interactions*WithIntelligentLife);//709.3
    public final static double PoorAndEnslaved=(WithIntelligentLife+WithLife)/UniversalEconomy;//2.087e6
    public final static double RichAndFree=WithIntelligentLife/PoorAndEnslaved;//283.736
    public final static double Oppressed=(WithLife*WithIntelligentLife)/RichAndFree;//3.134e6
    public final static double Travelling=(WithLife+WithIntelligentLife-PoorAndEnslaved-RichAndFree)/LifeSaved;//2.5
    public final static double Masters=TotalUDS/Oppressed;//28.724
    public final static double Neutral=RichAndFree/Masters;//9.878
    public final static double War=(Masters*RichAndFree)/(Oppressed-PoorAndEnslaved);//7.789e-3
    public final static double Destruction=(War*Risks);//0.012
    public final static double Creation=(LifeSaved*Life)/(War*Masters*RichAndFree*PoorAndEnslaved);//6.7
    public final static double Death=(Creation-Destruction-Masters*War);//6.5
    public final static double NewLife=(Creation-Death)*Math.pow(Life,5);//0.018
    public final static double NearRaces=(RichAndFree-SpaceTravellers-Masters-Neutral-Travelling)/Life;//2.6
    public final static double DEW=(NewLife*Oppressed*NearRaces*SpaceTravellers*Neutral*Travelling*PoorAndEnslaved*Life*LifeSaved*TotalUDS)/(Masters*RichAndFree*Math.pow(1.4e6*Time,2)*C4*D4*Risks);//1.003
    //
}
