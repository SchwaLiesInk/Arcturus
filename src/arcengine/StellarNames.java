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
public class StellarNames {

    final static char[] vouel = new char[]{'a', 'e', 'i', 'o', 'u'};
    final static char[] conso = new char[]{'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};

    static void SetSystemName(Planet home, Star system) {
        double s = 0.1f;
        int sx = (int) (system.starCoord.x * s);
        int sy = (int) (system.starCoord.y * s);
        int sz = (int) (system.starCoord.z * s);
        home.system.name = DistantName(system.starCoord.Sub(system.starCoord, home.revolvesRound.starCoord).AsPoint(s)) + SectorName(sx, sy, sz) + CatalogNumber(system.prime.core, system.prime.atmosphere);
    }

    public static String PlanetName(StellarClass star, int distanceLys, double distanceAus, int at) {

        String name = "";
        int planet = (int) (distanceLys * 10);
        at++;
        name = "";
        //System.Console.WriteLine("Planet " + planet);
        StringBuilder finalName = new StringBuilder();
        int n1 = (int) ((1 + (int) (star.SizeValue())) * (1 + star.subType) + planet)%300 ;
        int n2 = (int) ((1 + (int) (star.SizeValue())) * (1 + star.subType) * planet)%300 ;
        int n3 = (int) ((1 + (int) (star.SizeValue())) + (1 + star.subType) + planet)%300 ;
        int n4 = (int) ((1 + (int) (star.SizeValue())) + (1 + star.subType) * planet)%300 ;
        int n5 = (int) ((1 + (int) (star.SpectralValue())) * (1 + star.subType) + planet)%300;
        int n6 = (int) ((1 + (int) (star.SpectralValue())) * (1 + star.subType) * planet)%300;
        int n7 = (int) ((1 + (int) (star.SpectralValue())) + (1 + star.subType) + planet)%300;
        int n8 = (int) ((1 + (int) (star.SpectralValue())) + (1 + star.subType) * planet)%300;
        switch (n1) {
            case 0: {
                name += "Nar";
                break;
            }
            case 1: {
                name += "Micr";
                break;
            }
            case 2: {
                name += "Sma";
                break;
            }
            case 3: {
                name += "Lit";
                break;
            }
            case 4: {
                name += "Tin";
                break;
            }
            case 5: {
                name += "Cop";
                break;
            }
            case 6: {
                name += "Pal";
                break;
            }
            case 7: {
                name += "Iro";
                break;
            }
            case 8: {
                name += "Kyr";
                break;
            }
            case 9: {
                name += "Neo";
                break;
            }
            case 10: {
                name += "Nigh";
                break;
            }
            case 11: {
                name += "Col";
                break;
            }
            case 12: {
                name += "Fris";
                break;
            }
            case 13: {
                name += "Fro";
                break;
            }
            case 14: {
                name += "Myt";
                break;
            }
            case 15: {
                name += "En";
                break;
            }
            case 16: {
                name += "Ell";
                break;
            }
            case 17: {
                name += "Meph";
                break;
            }
            case 18: {
                name += "Gre";
                break;
            }
            case 19: {
                name += "Pla";
                break;
            }
            case 20: {
                name += "Flo";
                break;
            }
            case 21: {
                name += "So";
                break;
            }
            case 22: {
                name += "Den";
                break;
            }
            case 23: {
                name += "Das";
                break;
            }
            case 24: {
                name += "Dis";
                break;
            }
            case 25: {
                name += "Le";
                break;
            }
            case 26: {
                name += "Li";
                break;
            }
            case 27: {
                name += "Las";
                break;
            }
            case 28: {
                name += "La";
                break;
            }
            case 29: {
                name += "Ith";
                break;
            }
            case 30: {
                name += "Ila";
                break;
            }
            case 31: {
                name += "Ult";
                break;
            }
            case 32: {
                name += "Uth";
                break;
            }
            case 33: {
                name += "Ald";
                break;
            }
            case 34: {
                name += "Sal";
                break;
            }
            case 35: {
                name += "Sea";
                break;
            }
            case 36: {
                name += "Io";
                break;
            }
            case 37: {
                name += "Chi";
                break;
            }
            case 38: {
                name += "Ki";
                break;
            }
            case 39: {
                name += "Ri";
                break;
            }
            case 40: {
                name += "Gar";
                break;
            }
            case 41: {
                name += "Mag";
                break;
            }
            case 42: {
                name += "Ger";
                break;
            }
            case 43: {
                name += "Mei";
                break;
            }
            case 44: {
                name += "Pad";
                break;
            }
            case 45: {
                name += "Ohm";
                break;
            }
            case 46: {
                name += "Ama";
                break;
            }
            case 47: {
                name += "Add";
                break;
            }
            case 48: {
                name += "Eve";
                break;
            }
            case 49: {
                name += "For";
                break;
            }
            case 50: {
                name += "Thy";
                break;
            }
            case 51: {
                name += "Tar";
                break;
            }
            case 52: {
                name += "Ter";
                break;
            }
            case 53: {
                name += "Tem";
                break;
            }
            case 54: {
                name += "Tep";
                break;
            }
            case 55: {
                name += "Lath";
                break;
            }
            case 56: {
                name += "Nyr";
                break;
            }
            case 57: {
                name += "Tet";
                break;
            }
            case 58: {
                name += "Tho";
                break;
            }
            case 59: {
                name += "Pyr";
                break;
            }
            case 60: {
                name += "Mach";
                break;
            }
            case 61: {
                name += "Kilo";
                break;
            }
            case 62: {
                name += "Meg";
                break;
            }
            case 63: {
                name += "Reth";
                break;
            }
            case 64: {
                name += "Illi";
                break;
            }
            case 65: {
                name += "Ther";
                break;
            }
            case 66: {
                name += "Eth";
                break;
            }
            case 67: {
                name += "Neth";
                break;
            }
            case 68: {
                name += "Not";
                break;
            }
            case 69: {
                name += "Nand";
                break;
            }
            case 70: {
                name += "Nor";
                break;
            }
            case 71: {
                name += "Tor";
                break;
            }
            case 72: {
                name += "Po";
                break;
            }
            case 73: {
                name += "Ded";
                break;
            }
            case 74: {
                name += "Non";
                break;
            }
            case 75: {
                name += "Nar";
                break;
            }
            case 76: {
                name += "Cra";
                break;
            }
            case 77: {
                name += "Arc";
                break;
            }
            case 78: {
                name += "Acr";
                break;
            }
            case 79: {
                name += "Aci";
                break;
            }
            case 80: {
                name += "Pol";
                break;
            }
            case 81: {
                name += "Para";
                break;
            }
            case 82: {
                name += "Ra";
                break;
            }
            case 83: {
                name += "Pla";
                break;
            }
            case 84: {
                name += "War";
                break;
            }
            case 85: {
                name += "Des";
                break;
            }
            case 86: {
                name += "Ter";
                break;
            }
            case 87: {
                name += "Hai";
                break;
            }
            case 88: {
                name += "Nev";
                break;
            }
            case 89: {
                name += "Ube";
                break;
            }
            case 90: {
                name += "Syl";
                break;
            }
            case 91: {
                name += "Sco";
                break;
            }
            case 92: {
                name += "Eva";
                break;
            }
            case 93: {
                name += "Boi";
                break;
            }
            case 94: {
                name += "Hot";
                break;
            }
            case 95: {
                name += "Bur";
                break;
            }
            case 96: {
                name += "Kil";
                break;
            }
            case 97: {
                name += "Fri";
                break;
            }
            case 98: {
                name += "Mur";
                break;
            }
            case 99: {
                name += "Dea";
                break;
            }
            case 100: {
                name += "Tod";
                break;
            }
            case 101: {
                name += "Cra";
                break;
            }
            case 102: {
                name += "Sala";
                break;
            }
            case 103: {
                name += "Til";
                break;
            }
            case 104: {
                name += "Nia";
                break;
            }
            case 105: {
                name += "Cap";
                break;
            }
            case 106: {
                name += "Alp";
                break;
            }
            case 107: {
                name += "Rho";
                break;
            }
            case 108: {
                name += "Tyr";
                break;
            }
            case 109: {
                name += "Eon";
                break;
            }
            case 110: {
                name += "Dark";
                break;
            }
            case 111: {
                name += "Kla";
                break;
            }
            case 112: {
                name += "Ris";
                break;
            }
            case 113: {
                name += "Ros";
                break;
            }
            case 114: {
                name += "Mat";
                break;
            }
            case 115: {
                name += "Enio";
                break;
            }
            case 116: {
                name += "Eki";
                break;
            }
            case 117: {
                name += "Luci";
                break;
            }
            case 118: {
                name += "Grou";
                break;
            }
            case 119: {
                name += "Plat";
                break;
            }
            case 120: {
                name += "Fol";
                break;
            }
            case 121: {
                name += "Sal";
                break;
            }
            case 122: {
                name += "Day";
                break;
            }
            case 123: {
                name += "Dai";
                break;
            }
            case 124: {
                name += "Ids";
                break;
            }
            case 125: {
                name += "Lie";
                break;
            }
            case 126: {
                name += "Loi";
                break;
            }
            case 127: {
                name += "Les";
                break;
            }
            case 128: {
                name += "Lama";
                break;
            }
            case 129: {
                name += "Id";
                break;
            }
            case 130: {
                name += "Kle";
                break;
            }
            case 131: {
                name += "Ram";
                break;
            }
            case 132: {
                name += "Ja";
                break;
            }
            case 133: {
                name += "Dal";
                break;
            }
            case 134: {
                name += "Mal";
                break;
            }
            case 135: {
                name += "Oci";
                break;
            }
            case 136: {
                name += "Vul";
                break;
            }
            case 137: {
                name += "Cha";
                break;
            }
            case 138: {
                name += "Ka";
                break;
            }
            case 139: {
                name += "Ray";
                break;
            }
            case 140: {
                name += "Cari";
                break;
            }
            case 141: {
                name += "Mari";
                break;
            }
            case 142: {
                name += "Goe";
                break;
            }
            case 143: {
                name += "Uri";
                break;
            }
            case 144: {
                name += "Pio";
                break;
            }
            case 145: {
                name += "Om";
                break;
            }
            case 146: {
                name += "Ada";
                break;
            }
            case 147: {
                name += "Ath";
                break;
            }
            case 148: {
                name += "Eneg";
                break;
            }
            case 149: {
                name += "Fir";
                break;
            }
            case 150: {
                name += "Ti";
                break;
            }
            case 151: {
                name += "Taj";
                break;
            }
            case 152: {
                name += "Tang";
                break;
            }
            case 153: {
                name += "Rai";
                break;
            }
            case 154: {
                name += "Ter";
                break;
            }
            case 155: {
                name += "Ath";
                break;
            }
            case 156: {
                name += "Zyr";
                break;
            }
            case 157: {
                name += "Ket";
                break;
            }
            case 158: {
                name += "Pho";
                break;
            }
            case 159: {
                name += "Ryr";
                break;
            }
            case 160: {
                name += "Ach";
                break;
            }
            case 161: {
                name += "Kino";
                break;
            }
            case 162: {
                name += "Ega";
                break;
            }
            case 163: {
                name += "Eth";
                break;
            }
            case 164: {
                name += "Elli";
                break;
            }
            case 165: {
                name += "Ker";
                break;
            }
            case 166: {
                name += "Atr";
                break;
            }
            case 167: {
                name += "Alt";
                break;
            }
            case 168: {
                name += "Bar";
                break;
            }
            case 169: {
                name += "Bre";
                break;
            }
            case 170: {
                name += "Bir";
                break;
            }
            case 171: {
                name += "Que";
                break;
            }
            case 172: {
                name += "To";
                break;
            }
            case 173: {
                name += "Eed";
                break;
            }
            case 174: {
                name += "Yond";
                break;
            }
            case 175: {
                name += "Anth";
                break;
            }
            case 176: {
                name += "Tra";
                break;
            }
            case 177: {
                name += "Art";
                break;
            }
            case 178: {
                name += "Ar";
                break;
            }
            case 179: {
                name += "Ac";
                break;
            }
            case 180: {
                name += "Tol";
                break;
            }
            case 181: {
                name += "Zara";
                break;
            }
            case 182: {
                name += "Za";
                break;
            }
            case 183: {
                name += "Ela";
                break;
            }
            case 184: {
                name += "Jar";
                break;
            }
            case 185: {
                name += "Jes";
                break;
            }
            case 186: {
                name += "Jer";
                break;
            }
            case 187: {
                name += "Has";
                break;
            }
            case 188: {
                name += "Kev";
                break;
            }
            case 189: {
                name += "Uae";
                break;
            }
            case 190: {
                name += "Sil";
                break;
            }
            case 191: {
                name += "Scri";
                break;
            }
            case 192: {
                name += "Deva";
                break;
            }
            case 193: {
                name += "Koi";
                break;
            }
            case 194: {
                name += "Dot";
                break;
            }
            case 195: {
                name += "Kur";
                break;
            }
            case 196: {
                name += "Ril";
                break;
            }
            case 197: {
                name += "Ori";
                break;
            }
            case 198: {
                name += "Mir";
                break;
            }
            case 199: {
                name += "Tea";
                break;
            }
            case 200: {
                name += "Kri";
                break;
            }
            case 201: {
                name += "Rai";
                break;
            }
            case 202: {
                name += "Sam";
                break;
            }
            case 203: {
                name += "Tal";
                break;
            }
            case 204: {
                name += "Nik";
                break;
            }
            case 205: {
                name += "Cop";
                break;
            }
            case 206: {
                name += "Api";
                break;
            }
            case 207: {
                name += "Dar";
                break;
            }
            case 208: {
                name += "Tra";
                break;
            }
            case 209: {
                name += "Oni";
                break;
            }
            case 210: {
                name += "Denk";
                break;
            }
            case 211: {
                name += "Kas";
                break;
            }
            case 212: {
                name += "Rin";
                break;
            }
            case 213: {
                name += "Rar";
                break;
            }
            case 214: {
                name += "Man";
                break;
            }
            case 215: {
                name += "Eio";
                break;
            }
            case 216: {
                name += "Eie";
                break;
            }
            case 217: {
                name += "Lok";
                break;
            }
            case 218: {
                name += "Gros";
                break;
            }
            case 219: {
                name += "Pali";
                break;
            }
            case 220: {
                name += "Frol";
                break;
            }
            case 221: {
                name += "Sla";
                break;
            }
            case 222: {
                name += "Dag";
                break;
            }
            case 223: {
                name += "Die";
                break;
            }
            case 224: {
                name += "Idio";
                break;
            }
            case 225: {
                name += "Los";
                break;
            }
            case 226: {
                name += "Lone";
                break;
            }
            case 227: {
                name += "Low";
                break;
            }
            case 228: {
                name += "Lasa";
                break;
            }
            case 229: {
                name += "Idro";
                break;
            }
            case 230: {
                name += "Kil";
                break;
            }
            case 231: {
                name += "Rak";
                break;
            }
            case 232: {
                name += "Jav";
                break;
            }
            case 233: {
                name += "Dew";
                break;
            }
            case 234: {
                name += "Mil";
                break;
            }
            case 235: {
                name += "Octo";
                break;
            }
            case 236: {
                name += "Vol";
                break;
            }
            case 237: {
                name += "Chu";
                break;
            }
            case 238: {
                name += "Kas";
                break;
            }
            case 239: {
                name += "Ris";
                break;
            }
            case 240: {
                name += "Cris";
                break;
            }
            case 241: {
                name += "Mer";
                break;
            }
            case 242: {
                name += "Got";
                break;
            }
            case 243: {
                name += "Eru";
                break;
            }
            case 244: {
                name += "Pois";
                break;
            }
            case 245: {
                name += "Omi";
                break;
            }
            case 246: {
                name += "Ard";
                break;
            }
            case 247: {
                name += "Ethe";
                break;
            }
            case 248: {
                name += "Edg";
                break;
            }
            case 249: {
                name += "Frai";
                break;
            }
            case 250: {
                name += "Tow";
                break;
            }
            case 251: {
                name += "Tchu";
                break;
            }
            case 252: {
                name += "Tass";
                break;
            }
            case 253: {
                name += "Rin";
                break;
            }
            case 254: {
                name += "Tara";
                break;
            }
            case 255: {
                name += "Azath";
                break;
            }
            case 256: {
                name += "Zera";
                break;
            }
            case 257: {
                name += "Kati";
                break;
            }
            case 258: {
                name += "Sphi";
                break;
            }
            case 259: {
                name += "Rorr";
                break;
            }
            case 260: {
                name += "Arch";
                break;
            }
            case 261: {
                name += "Non";
                break;
            }
            case 262: {
                name += "Eago";
                break;
            }
            case 263: {
                name += "Theth";
                break;
            }
            case 264: {
                name += "Ali";
                break;
            }
            case 265: {
                name += "Kree";
                break;
            }
            case 266: {
                name += "Atra";
                break;
            }
            case 267: {
                name += "Ultr";
                break;
            }
            case 268: {
                name += "Bas";
                break;
            }
            case 269: {
                name += "Brit";
                break;
            }
            case 270: {
                name += "Bori";
                break;
            }
            case 271: {
                name += "Quet";
                break;
            }
            case 272: {
                name += "Tar";
                break;
            }
            case 273: {
                name += "Edom";
                break;
            }
            case 274: {
                name += "Yog";
                break;
            }
            case 275: {
                name += "Athaz";
                break;
            }
            case 276: {
                name += "Tama";
                break;
            }
            case 277: {
                name += "Aor";
                break;
            }
            case 278: {
                name += "Aroi";
                break;
            }
            case 279: {
                name += "Acra";
                break;
            }
            case 280: {
                name += "Toka";
                break;
            }
            case 281: {
                name += "Zaman";
                break;
            }
            case 282: {
                name += "Zari";
                break;
            }
            case 283: {
                name += "Eath";
                break;
            }
            case 284: {
                name += "Jama";
                break;
            }
            case 285: {
                name += "Esu";
                break;
            }
            case 286: {
                name += "Jeru";
                break;
            }
            case 287: {
                name += "Haru";
                break;
            }
            case 288: {
                name += "Kay";
                break;
            }
            case 289: {
                name += "Aus";
                break;
            }
            case 290: {
                name += "Carb";
                break;
            }
            case 291: {
                name += "San";
                break;
            }
            case 292: {
                name += "Diva";
                break;
            }
            case 293: {
                name += "Kold";
                break;
            }
            case 294: {
                name += "Dar";
                break;
            }
            case 295: {
                name += "Kior";
                break;
            }
            case 296: {
                name += "Riol";
                break;
            }
            case 297: {
                name += "Oion";
                break;
            }
            case 298: {
                name += "Mion";
                break;
            }
            case 299: {
                name += "Tion";
                break;
            }
            default: {
                switch (n1 % 10) {
                    case 0: {
                        name += Character.toUpperCase(vouel[n2 % 5]);
                        name += (vouel[n3 % 5]);
                        name += (vouel[n4 % 5]);
                        break;
                    }

                    case 1: {
                        name += Character.toUpperCase(vouel[n2 % 5]);
                        name += (conso[n3 % 21]);
                        name += (vouel[n4 % 5]);
                        break;
                    }
                    case 2: {
                        name += Character.toUpperCase(vouel[n2 % 5]);
                        name += (vouel[n3 % 5]);
                        name += (conso[n4 % 21]);
                        break;
                    }

                    case 3: {
                        name += Character.toUpperCase(conso[n2 % 21]);
                        name += (vouel[n3 % 5]);
                        name += (vouel[n4 % 5]);
                        break;
                    }

                    case 4: {
                        name += Character.toUpperCase(conso[n2 % 21]);
                        name += (vouel[n3 % 5]);
                        name += (conso[n4 % 5]);
                        break;
                    }
                    case 5: {
                        name += Character.toUpperCase(conso[n2 % 21]);
                        name += (vouel[n3 % 5]);
                        name += (vouel[n4 % 5]);
                        name += (conso[n5 % 21]);
                        break;
                    }

                    case 6: {
                        name += Character.toUpperCase(vouel[n2 % 5]);
                        name += (conso[n3 % 21]);
                        name += (vouel[n4 % 5]);
                        name += (vouel[n5 % 5]);
                        break;
                    }
                    case 7: {
                        name += Character.toUpperCase(vouel[n2 % 5]);
                        name += (vouel[n3 % 5]);
                        name += (conso[n4 % 21]);
                        name += (vouel[n5 % 5]);
                        break;
                    }

                    case 8: {
                        name += Character.toUpperCase(conso[n2 % 21]);
                        name += (vouel[n3 % 5]);
                        name += (conso[n4 % 21]);
                        name += (vouel[n5 % 5]);
                        break;
                    }

                    case 9: {
                        name += Character.toUpperCase(conso[n2 % 21]);
                        name += (vouel[n3 % 5]);
                        name += (conso[n4 % 21]);
                        name += (conso[n5 % 21]);
                        break;
                    }
                }
                break;
            }
        }
        finalName.append(name);
        /*int v = planet % 5;
         int c = planet % 21;
         int end=name.Length-1;
         bool c1 = false;
         bool c2 = false;
         if (name[end] == 'a' || name[end] == 'e' || name[end] == 'i' || name[end] == 'o' || name[end] == 'u')
         {
         c1 = true;
         }*/
        name = "";
        switch (n5) {
            case 0: {
                name += "iom";
                break;
            }
            case 1: {
                name += "am";
                break;
            }
            case 2: {
                name += "ana";
                break;
            }
            case 3: {
                name += "mara";
                break;
            }
            case 4: {
                name += "mos";
                break;
            }
            case 5: {
                name += "mus";
                break;
            }
            case 6: {
                name += "us";
                break;
            }
            case 7: {
                name += "ro";
                break;
            }
            case 8: {
                name += "ko";
                break;
            }
            case 9: {
                name += "ta";
                break;
            }
            case 10: {
                name += "la";
                break;
            }
            case 11: {
                name += "lo";
                break;
            }
            case 12: {
                name += "riad";
                break;
            }
            case 13: {
                name += "iad";
                break;
            }
            case 14: {
                name += "ria";
                break;
            }
            case 15: {
                name += "osa";
                break;
            }
            case 16: {
                name += "nara";
                break;
            }
            case 17: {
                name += "kama";
                break;
            }
            case 18: {
                name += "ton";
                break;
            }
            case 19: {
                name += "rion";
                break;
            }
            case 20: {
                name += "ion";
                break;
            }
            case 21: {
                name += "um";
                break;
            }
            case 22: {
                name += "oom";
                break;
            }
            case 23: {
                name += "tra";
                break;
            }
            case 24: {
                name += "sa";
                break;
            }
            case 25: {
                name += "ra";
                break;
            }
            case 26: {
                name += "na";
                break;
            }
            case 27: {
                name += "ma";
                break;
            }
            case 28: {
                name += "da";
                break;
            }
            case 29: {
                name += "oia";
                break;
            }
            case 30: {
                name += "ioa";
                break;
            }
            case 31: {
                name += "troa";
                break;
            }
            case 32: {
                name += "trop";
                break;
            }
            case 33: {
                name += "ee";
                break;
            }
            case 34: {
                name += "ele";
                break;
            }
            case 35: {
                name += "elia";
                break;
            }
            case 36: {
                name += "ia";
                break;
            }
            case 37: {
                name += "lia";
                break;
            }
            case 38: {
                name += "mia";
                break;
            }
            case 39: {
                name += "eon";
                break;
            }
            case 40: {
                name += "argo";
                break;
            }
            case 41: {
                name += "go";
                break;
            }
            case 42: {
                name += "wyn";
                break;
            }
            case 43: {
                name += "rig";
                break;
            }
            case 44: {
                name += "ith";
                break;
            }
            case 45: {
                name += "ter";
                break;
            }
            case 46: {
                name += "frye";
                break;
            }
            case 47: {
                name += "sky";
                break;
            }
            case 48: {
                name += "oud";
                break;
            }
            case 49: {
                name += "ras";
                break;
            }
            case 50: {
                name += "pas";
                break;
            }
            case 51: {
                name += "los";
                break;
            }
            case 52: {
                name += "nos";
                break;
            }
            case 53: {
                name += "das";
                break;
            }
            case 54: {
                name += "emp";
                break;
            }
            case 55: {
                name += "on";
                break;
            }
            case 56: {
                name += "gone";
                break;
            }
            case 57: {
                name += "tion";
                break;
            }
            case 58: {
                name += "sion";
                break;
            }
            case 59: {
                name += "roc";
                break;
            }
            case 60: {
                name += "ron";
                break;
            }
            case 61: {
                name += "mon";
                break;
            }
            case 62: {
                name += "tor";
                break;
            }
            case 63: {
                name += "age";
                break;
            }
            case 64: {
                name += "kal";
                break;
            }
            case 65: {
                name += "lee";
                break;
            }
            case 66: {
                name += "lie";
                break;
            }
            case 67: {
                name += "lyr";
                break;
            }
            case 68: {
                name += "ius";
                break;
            }
            case 69: {
                name += "nius";
                break;
            }
            case 70: {
                name += "lath";
                break;
            }
            case 71: {
                name += "een";
                break;
            }
            case 72: {
                name += "pol";
                break;
            }
            case 73: {
                name += "ity";
                break;
            }
            case 74: {
                name += "ni";
                break;
            }
            case 75: {
                name += "uio";
                break;
            }
            case 76: {
                name += "arch";
                break;
            }
            case 77: {
                name += "ust";
                break;
            }
            case 78: {
                name += "cano";
                break;
            }
            case 79: {
                name += "icid";
                break;
            }
            case 80: {
                name += "ithid";
                break;
            }
            case 81: {
                name += "thul";
                break;
            }
            case 82: {
                name += "oth";
                break;
            }
            case 83: {
                name += "rat";
                break;
            }
            case 84: {
                name += "rath";
                break;
            }
            case 85: {
                name += "set";
                break;
            }
            case 86: {
                name += "tep";
                break;
            }
            case 87: {
                name += "rot";
                break;
            }
            case 88: {
                name += "roth";
                break;
            }
            case 89: {
                name += "er";
                break;
            }
            case 90: {
                name += "tal";
                break;
            }
            case 91: {
                name += "ium";
                break;
            }
            case 92: {
                name += "pion";
                break;
            }
            case 93: {
                name += "ide";
                break;
            }
            case 94: {
                name += "nide";
                break;
            }
            case 95: {
                name += "een";
                break;
            }
            case 96: {
                name += "ix";
                break;
            }
            case 97: {
                name += "dias";
                break;
            }
            case 98: {
                name += "tan";
                break;
            }
            case 99: {
                name += "hell";
                break;
            }
            case 100: {
                name += "arth";
                break;
            }
            case 101: {
                name += "amri";
                break;
            }
            case 102: {
                name += "anari";
                break;
            }
            case 103: {
                name += "yarmi";
                break;
            }
            case 104: {
                name += "ioros";
                break;
            }
            case 105: {
                name += "pius";
                break;
            }
            case 106: {
                name += "psis";
                break;
            }
            case 107: {
                name += "ro";
                break;
            }
            case 108: {
                name += "kla";
                break;
            }
            case 109: {
                name += "tly";
                break;
            }
            case 110: {
                name += "ara";
                break;
            }
            case 111: {
                name += "lo";
                break;
            }
            case 112: {
                name += "tios";
                break;
            }
            case 113: {
                name += "dios";
                break;
            }
            case 114: {
                name += "ra";
                break;
            }
            case 115: {
                name += "os";
                break;
            }
            case 116: {
                name += "tra";
                break;
            }
            case 117: {
                name += "ran";
                break;
            }
            case 118: {
                name += "triak";
                break;
            }
            case 119: {
                name += "kros";
                break;
            }
            case 120: {
                name += "ei";
                break;
            }
            case 121: {
                name += "eum";
                break;
            }
            case 122: {
                name += "ini";
                break;
            }
            case 123: {
                name += "shri";
                break;
            }
            case 124: {
                name += "shra";
                break;
            }
            case 125: {
                name += "ia";
                break;
            }
            case 126: {
                name += "na";
                break;
            }
            case 127: {
                name += "gon";
                break;
            }
            case 128: {
                name += "dago";
                break;
            }
            case 129: {
                name += "niaro";
                break;
            }
            case 130: {
                name += "nia";
                break;
            }
            case 131: {
                name += "roll";
                break;
            }
            case 132: {
                name += "tri";
                break;
            }
            case 133: {
                name += "elim";
                break;
            }
            case 134: {
                name += "eimel";
                break;
            }
            case 135: {
                name += "litha";
                break;
            }
            case 136: {
                name += "iat";
                break;
            }
            case 137: {
                name += "laba";
                break;
            }
            case 138: {
                name += "ondo";
                break;
            }
            case 139: {
                name += "adda";
                break;
            }
            case 140: {
                name += "ard";
                break;
            }
            case 141: {
                name += "dos";
                break;
            }
            case 142: {
                name += "tra";
                break;
            }
            case 143: {
                name += "par";
                break;
            }
            case 144: {
                name += "esp";
                break;
            }
            case 145: {
                name += "tsio";
                break;
            }
            case 146: {
                name += "lag";
                break;
            }
            case 147: {
                name += "stal";
                break;
            }
            case 148: {
                name += "and";
                break;
            }
            case 149: {
                name += "fos";
                break;
            }
            case 150: {
                name += "tan";
                break;
            }
            case 151: {
                name += "sin";
                break;
            }
            case 152: {
                name += "cos";
                break;
            }
            case 153: {
                name += "taris";
                break;
            }
            case 154: {
                name += "emis";
                break;
            }
            case 155: {
                name += "osen";
                break;
            }
            case 156: {
                name += "eson";
                break;
            }
            case 157: {
                name += "ati";
                break;
            }
            case 158: {
                name += "sani";
                break;
            }
            case 159: {
                name += "sal";
                break;
            }
            case 160: {
                name += "so";
                break;
            }
            case 161: {
                name += "om";
                break;
            }
            case 162: {
                name += "ot";
                break;
            }
            case 163: {
                name += "ga";
                break;
            }
            case 164: {
                name += "ak";
                break;
            }
            case 165: {
                name += "es";
                break;
            }
            case 166: {
                name += "lei";
                break;
            }
            case 167: {
                name += "ari";
                break;
            }
            case 168: {
                name += "ios";
                break;
            }
            case 169: {
                name += "nos";
                break;
            }
            case 170: {
                name += "das";
                break;
            }
            case 171: {
                name += "en";
                break;
            }
            case 172: {
                name += "tel";
                break;
            }
            case 173: {
                name += "ray";
                break;
            }
            case 174: {
                name += "oni";
                break;
            }
            case 175: {
                name += "ion";
                break;
            }
            case 176: {
                name += "chu";
                break;
            }
            case 177: {
                name += "st";
                break;
            }
            case 178: {
                name += "an";
                break;
            }
            case 179: {
                name += "cid";
                break;
            }
            case 180: {
                name += "hod";
                break;
            }
            case 181: {
                name += "tal";
                break;
            }
            case 182: {
                name += "ite";
                break;
            }
            case 183: {
                name += "ein";
                break;
            }
            case 184: {
                name += "ar";
                break;
            }
            case 185: {
                name += "ter";
                break;
            }
            case 186: {
                name += "et";
                break;
            }
            case 187: {
                name += "ot";
                break;
            }
            case 188: {
                name += "th";
                break;
            }
            case 189: {
                name += "er";
                break;
            }
            case 190: {
                name += "el";
                break;
            }
            case 191: {
                name += "in";
                break;
            }
            case 192: {
                name += "on";
                break;
            }
            case 193: {
                name += "da";
                break;
            }
            case 194: {
                name += "er";
                break;
            }
            case 195: {
                name += "me";
                break;
            }
            case 196: {
                name += "usi";
                break;
            }
            case 197: {
                name += "as";
                break;
            }
            case 198: {
                name += "is";
                break;
            }
            case 199: {
                name += "ill";
                break;
            }
            
            case 200: {
                name += "rath";
                break;
            }
            case 201: {
                name += "amra";
                break;
            }
            case 202: {
                name += "anar";
                break;
            }
            case 203: {
                name += "yara";
                break;
            }
            case 204: {
                name += "ios";
                break;
            }
            case 205: {
                name += "ius";
                break;
            }
            case 206: {
                name += "usis";
                break;
            }
            case 207: {
                name += "roa";
                break;
            }
            case 208: {
                name += "kao";
                break;
            }
            case 209: {
                name += "tay";
                break;
            }
            case 210: {
                name += "lara";
                break;
            }
            case 211: {
                name += "lola";
                break;
            }
            case 212: {
                name += "riad";
                break;
            }
            case 213: {
                name += "adi";
                break;
            }
            case 214: {
                name += "rai";
                break;
            }
            case 215: {
                name += "osra";
                break;
            }
            case 216: {
                name += "tara";
                break;
            }
            case 217: {
                name += "rama";
                break;
            }
            case 218: {
                name += "trion";
                break;
            }
            case 219: {
                name += "krion";
                break;
            }
            case 220: {
                name += "oni";
                break;
            }
            case 221: {
                name += "uhm";
                break;
            }
            case 222: {
                name += "oon";
                break;
            }
            case 223: {
                name += "trya";
                break;
            }
            case 224: {
                name += "sara";
                break;
            }
            case 225: {
                name += "raia";
                break;
            }
            case 226: {
                name += "naia";
                break;
            }
            case 227: {
                name += "maia";
                break;
            }
            case 228: {
                name += "daia";
                break;
            }
            case 229: {
                name += "noia";
                break;
            }
            case 230: {
                name += "ionia";
                break;
            }
            case 231: {
                name += "troll";
                break;
            }
            case 232: {
                name += "trip";
                break;
            }
            case 233: {
                name += "elium";
                break;
            }
            case 234: {
                name += "eleim";
                break;
            }
            case 235: {
                name += "liath";
                break;
            }
            case 236: {
                name += "iad";
                break;
            }
            case 237: {
                name += "lada";
                break;
            }
            case 238: {
                name += "onda";
                break;
            }
            case 239: {
                name += "dda";
                break;
            }
            case 240: {
                name += "ardo";
                break;
            }
            case 241: {
                name += "do";
                break;
            }
            case 242: {
                name += "tron";
                break;
            }
            case 243: {
                name += "pit";
                break;
            }
            case 244: {
                name += "eath";
                break;
            }
            case 245: {
                name += "tes";
                break;
            }
            case 246: {
                name += "fray";
                break;
            }
            case 247: {
                name += "stol";
                break;
            }
            case 248: {
                name += "ond";
                break;
            }
            case 249: {
                name += "fras";
                break;
            }
            case 250: {
                name += "pras";
                break;
            }
            case 251: {
                name += "las";
                break;
            }
            case 252: {
                name += "kos";
                break;
            }
            case 253: {
                name += "tas";
                break;
            }
            case 254: {
                name += "ems";
                break;
            }
            case 255: {
                name += "ons";
                break;
            }
            case 256: {
                name += "ones";
                break;
            }
            case 257: {
                name += "iot";
                break;
            }
            case 258: {
                name += "soni";
                break;
            }
            case 259: {
                name += "sol";
                break;
            }
            case 260: {
                name += "son";
                break;
            }
            case 261: {
                name += "nom";
                break;
            }
            case 262: {
                name += "rot";
                break;
            }
            case 263: {
                name += "gae";
                break;
            }
            case 264: {
                name += "lak";
                break;
            }
            case 265: {
                name += "ess";
                break;
            }
            case 266: {
                name += "ley";
                break;
            }
            case 267: {
                name += "lar";
                break;
            }
            case 268: {
                name += "isis";
                break;
            }
            case 269: {
                name += "nus";
                break;
            }
            case 270: {
                name += "dath";
                break;
            }
            case 271: {
                name += "ens";
                break;
            }
            case 272: {
                name += "tol";
                break;
            }
            case 273: {
                name += "itray";
                break;
            }
            case 274: {
                name += "nion";
                break;
            }
            case 275: {
                name += "uon";
                break;
            }
            case 276: {
                name += "cha";
                break;
            }
            case 277: {
                name += "usti";
                break;
            }
            case 278: {
                name += "can";
                break;
            }
            case 279: {
                name += "scid";
                break;
            }
            case 280: {
                name += "thod";
                break;
            }
            case 281: {
                name += "thal";
                break;
            }
            case 282: {
                name += "ith";
                break;
            }
            case 283: {
                name += "stein";
                break;
            }
            case 284: {
                name += "sath";
                break;
            }
            case 285: {
                name += "ster";
                break;
            }
            case 286: {
                name += "ept";
                break;
            }
            case 287: {
                name += "ote";
                break;
            }
            case 288: {
                name += "oth";
                break;
            }
            case 289: {
                name += "rer";
                break;
            }
            case 290: {
                name += "tel";
                break;
            }
            case 291: {
                name += "imon";
                break;
            }
            case 292: {
                name += "eon";
                break;
            }
            case 293: {
                name += "ida";
                break;
            }
            case 294: {
                name += "ider";
                break;
            }
            case 295: {
                name += "ome";
                break;
            }
            case 296: {
                name += "xus";
                break;
            }
            case 297: {
                name += "ias";
                break;
            }
            case 298: {
                name += "tanis";
                break;
            }
            case 299: {
                name += "ell";
                break;
            }
            default: {
                switch (n4 % 10) {
                    case 0: {
                        name += (vouel[n5 % 5]);
                        name += (vouel[n6 % 5]);
                        name += (vouel[n7 % 5]);
                        break;
                    }

                    case 1: {
                        name += (vouel[n5 % 5]);
                        name += (conso[n6 % 21]);
                        name += (vouel[n7 % 5]);
                        break;
                    }
                    case 2: {
                        name += (vouel[n5 % 5]);
                        name += (vouel[n6 % 5]);
                        name += (conso[n7 % 21]);
                        break;
                    }

                    case 3: {
                        name += (conso[n5 % 21]);
                        name += (vouel[n6 % 5]);
                        name += (vouel[n7 % 5]);
                        break;
                    }

                    case 4: {
                        name += (conso[n5 % 21]);
                        name += (vouel[n6 % 5]);
                        name += (conso[n7 % 5]);
                        break;
                    }
                    case 5: {
                        name += (conso[n5 % 21]);
                        name += (vouel[n6 % 5]);
                        name += (vouel[n7 % 5]);
                        name += (conso[n8 % 21]);
                        break;
                    }

                    case 6: {
                        name += (vouel[n5 % 5]);
                        name += (conso[n6 % 21]);
                        name += (vouel[n7 % 5]);
                        name += (vouel[n8 % 5]);
                        break;
                    }
                    case 7: {
                        name += (vouel[n5 % 5]);
                        name += (vouel[n6 % 5]);
                        name += (conso[n7 % 21]);
                        name += (vouel[n8 % 5]);
                        break;
                    }

                    case 8: {
                        name += (conso[n5 % 21]);
                        name += (vouel[n6 % 5]);
                        name += (conso[n7 % 21]);
                        name += (vouel[n8 % 5]);
                        break;
                    }

                    case 9: {
                        name += (conso[n5 % 21]);
                        name += (vouel[n6 % 5]);
                        name += (conso[n7 % 21]);
                        name += (conso[n8 % 21]);
                        break;
                    }
                }
                break;
            }
        }
        /*end = name.Length-1 ;
         if (name[end] == 'a' || name[end] == 'e' || name[end] == 'i' || name[end] == 'o' || name[end] == 'u')
         {
         c2 = true;
         }
         if (c1 && c2)
         {
         finalName.Append(conso[c]);
         finalName.Append(name);
         }
         else
         if ((c1 && (!c2))){
         finalName.Append(vouel[v]);
         finalName.Append(conso[c]);
         finalName.Append(name[end]);
                
         }else if ((!c1) && c2)
         {
         finalName.Append(conso[c]);
         finalName.Append(vouel[v]);
         finalName.Append(name[end]);
         }
         else {
         finalName.Append(vouel[v]);
         finalName.Append(name);
            
         }*/

        finalName.append(name);
        name = "";
        while(at>=0){
        switch (at%24) {
            case 0: {
                name += "Prime";
                break;
            }
            case 1: {
                name += "Alpha";
                break;
            }
            case 2: {
                name += "Beta";
                break;
            }
            case 3: {
                name += "Gamma";
                break;
            }
            case 4: {
                name += "Delta";
                break;
            }
            case 5: {
                name += "Epsilon";
                break;
            }
            case 6: {
                name += "Zeta";
                break;
            }
            case 7: {
                name += "Eta";
                break;
            }
            case 8: {
                name += "Theta";
                break;
            }
            case 9: {
                name += "Iota";
                break;
            }
            case 10: {
                name += "Kappa";
                break;
            }
            case 11: {
                name += "Lambda";
                break;
            }
            case 12: {
                name += "Mu";
                break;
            }
            case 13: {
                name += "Nu";
                break;
            }
            case 14: {
                name += "Xi";
                break;
            }
            case 15: {
                name += "Omicron";
                break;
            }
            case 16: {
                name += "Pi";
                break;
            }
            case 17: {
                name += "Rho";
                break;
            }
            case 18: {
                name += "Sigma";
                break;
            }
            case 19: {
                name += "Tau";
                break;
            }
            case 20: {
                name += "Upsilon";
                break;
            }
            case 21: {
                name += "Phi";
                break;
            }
            case 22: {
                name += "Chi";
                break;
            }
            case 23: {
                name += "Psi";
                break;
            }
        }
        at-=23;
        }
        finalName.append(name);
        /*
         StringBuilder shuffle = new StringBuilder();
         int shuffles =(int)( ((int)(star.spec) + (int)(star.size)) * star.subType * distance);
         int len = name.Length;
         int flen = finalName.Length;
         shuffles = (flen+len + (shuffles % flen))>>1;
         Adder ada = new Adder(0, len-1, 1 + (shuffles % 4));
         Adder bada = new Adder(0, flen-1, 1 + (shuffles % 4));
         Adder cada = new Adder('a', 'z',1);
         for (int i = len%flen; i < shuffles ; i++)
         {
         int j = i % 4;
         if (j == 0) { 
         cada.Subject=name[ada.Subject];
         cada.Addition();
         shuffle.Append((char)cada.Subject);
         //cada.Number++;
         } else
         if (j == 3)
         {
         bada.Addition();
         shuffle.Append(finalName[bada.Subject]);
         }
         else {
         //ada.Addition();
         //bada.Addition();
         if ((i % len)%2 == 0) {shuffle.Append(name[i % len]); }
         if ((i % flen) % 2 == 0) { shuffle.Append(finalName[i % flen]); }
         }
         }
         finalName = shuffle.ToString();*/
        return finalName.toString();
    }

    public static String DistantName(Point p) {
        String name = "";
        int d = (int) Math.abs(p.Length());
        int n = 0;
        if (d < 150) {
            n = 1;
        }
        if (d > 150) {
            d = (d + (d * d) % (500)) / (d - 150);
        }
        if (d > 500) {
            n = 2 + (d % 7);
        }
        for (int i = 0; i < n; i++) {

            switch (d) {
                case 0: {
                    name += "Raya-";
                    break;
                }
                case 1: {
                    name += "Jove-";
                    break;
                }
                case 2: {
                    name += "Appollo-";
                    break;
                }
                case 3: {
                    name += "Alpha-";
                    break;
                }
                case 4: {
                    name += "Beta-";
                    break;
                }
                case 5: {
                    name += "Gamma-";
                    break;
                }
                case 6: {
                    name += "Delta-";
                    break;
                }
                case 7: {
                    name += "Epsilon-";
                    break;
                }
                case 8: {
                    name += "Zeta-";
                    break;
                }
                case 9: {
                    name += "Eta-";
                    break;
                }
                case 10: {
                    name += "Theta-";
                    break;
                }
                case 11: {
                    name += "Iota-";
                    break;
                }
                case 12: {
                    name += "Kappa-";
                    break;
                }
                case 13: {
                    name += "Lambda-";
                    break;
                }
                case 14: {
                    name += "Mu-";
                    break;
                }
                case 15: {
                    name += "Nu-";
                    break;
                }
                case 16: {
                    name += "Xi-";
                    break;
                }
                case 17: {
                    name += "Omicron-";
                    break;
                }
                case 18: {
                    name += "Pi-";
                    break;
                }
                case 19: {
                    name += "Rho-";
                    break;
                }
                case 20: {
                    name += "Sigma-";
                    break;
                }
                case 21: {
                    name += "Tau-";
                    break;
                }
                case 22: {
                    name += "Upsilon-";
                    break;
                }
                case 23: {
                    name += "Phi-";
                    break;
                }
                case 24: {
                    name += "Chi-";
                    break;
                }
                case 25: {
                    name += "Psi-";
                    break;
                }
                case 26: {
                    name += "Omega-";
                    break;
                }
                case 27: {
                    name += "Wolf-";
                    break;
                }
                case 28: {
                    name += "Ross-";
                    break;
                }
                case 29: {
                    name += "Rayet-";
                    break;
                }
                case 30: {
                    name += "Kepler-";
                    break;
                }
                case 31: {
                    name += "Newton-";
                    break;
                }
                case 32: {
                    name += "Galilaeo-";
                    break;
                }
                case 33: {
                    name += "Einstein-";
                    break;
                }
                case 34: {
                    name += "Planck-";
                    break;
                }
                case 35: {
                    name += "Hubble-";
                    break;
                }
                case 36: {
                    name += "Higgs-";
                    break;
                }
                case 37: {
                    name += "Hawking-";
                    break;
                }
                case 38: {
                    name += "Young-";
                    break;
                }
                case 39: {
                    name += "Thomson-";
                    break;
                }
                case 40: {
                    name += "Heisenberg-";
                    break;
                }
                case 41: {
                    name += "Gamow-";
                    break;
                }
                case 42: {
                    name += "LeVarrier-";
                    break;
                }
                case 43: {
                    name += "Feynman-";
                    break;
                }
                case 44: {
                    name += "DeBroglie-";
                    break;
                }
                case 45: {
                    name += "Schrodinger-";
                    break;
                }
                case 46: {
                    name += "GellMann-";
                    break;
                }
                case 47: {
                    name += "Zweig-";
                    break;
                }
                case 48: {
                    name += "Rutherford-";
                    break;
                }
                case 49: {
                    name += "Ratcliffe-";
                    break;
                }
                case 50: {
                    name += "Bohr-";
                    break;
                }
                case 51: {
                    name += "Wolfgang-";
                    break;
                }
                case 52: {
                    name += "Dehmelt-";
                    break;
                }
                case 53: {
                    name += "Binnig-";
                    break;
                }
                case 54: {
                    name += "Rohrer-";
                    break;
                }
                case 55: {
                    name += "Quate-";
                    break;
                }
                case 56: {
                    name += "Gerber-";
                    break;
                }
                case 57: {
                    name += "Cockroft-";
                    break;
                }
                case 58: {
                    name += "Walton-";
                    break;
                }
                case 59: {
                    name += "Chadwick-";
                    break;
                }
                case 60: {
                    name += "Lawrence-";
                    break;
                }
                case 61: {
                    name += "Livingstone-";
                    break;
                }
                case 62: {
                    name += "Enola-";
                    break;
                }
                case 63: {
                    name += "Mendeleev-";
                    break;
                }
                case 64: {
                    name += "Fermi-";
                    break;
                }
                case 65: {
                    name += "Stern-";
                    break;
                }
                case 66: {
                    name += "Kilby-";
                    break;
                }
                case 67: {
                    name += "Neumann-";
                    break;
                }
                case 68: {
                    name += "Oppenheimer-";
                    break;
                }
                case 69: {
                    name += "Tessler-";
                    break;
                }
                case 70: {
                    name += "Maiman-";
                    break;
                }
                case 71: {
                    name += "Gabor-";
                    break;
                }
                case 72: {
                    name += "Jones-";
                    break;
                }
                case 73: {
                    name += "Bose-";
                    break;
                }
                case 74: {
                    name += "Osheroff-";
                    break;
                }
                case 75: {
                    name += "Richardson-";
                    break;
                }
                case 76: {
                    name += "Lee-";
                    break;
                }
                case 77: {
                    name += "Kleppner-";
                    break;
                }
                case 78: {
                    name += "Chu-";
                    break;
                }
                case 79: {
                    name += "Phillips-";
                    break;
                }
                case 80: {
                    name += "Tannoudji-";
                    break;
                }
                case 81: {
                    name += "Ketterle-";
                    break;
                }
                case 82: {
                    name += "Wieman-";
                    break;
                }
                case 83: {
                    name += "Cornell-";
                    break;
                }
                case 84: {
                    name += "Onnes-";
                    break;
                }
                case 85: {
                    name += "Helmerson-";
                    break;
                }
                case 86: {
                    name += "Andrews-";
                    break;
                }
                case 87: {
                    name += "Mewes-";
                    break;
                }
                case 88: {
                    name += "Schrieffer-";
                    break;
                }
                case 89: {
                    name += "Bardeen-";
                    break;
                }
                case 90: {
                    name += "Cooper-";
                    break;
                }
                case 91: {
                    name += "Bednorz-";
                    break;
                }
                case 92: {
                    name += "Muller-";
                    break;
                }
                case 93: {
                    name += "Josephson-";
                    break;
                }
                case 94: {
                    name += "Anderson-";
                    break;
                }
                case 95: {
                    name += "VonKlitzing-";
                    break;
                }
                case 96: {
                    name += "Born-";
                    break;
                }
                case 97: {
                    name += "Bohm-";
                    break;
                }
                case 98: {
                    name += "Bell-";
                    break;
                }
                case 99: {
                    name += "McClellan-";
                    break;
                }
                //
                case 100: {
                    name += "Moore-";
                    break;
                }
                case 101: {
                    name += "Turing-";
                    break;
                }
                case 102: {
                    name += "Eddington-";
                    break;
                }
                case 103: {
                    name += "Bethe-";
                    break;
                }
                case 104: {
                    name += "Hoyle-";
                    break;
                }
                case 105: {
                    name += "Chandrasekhar-";
                    break;
                }
                case 106: {
                    name += "Davis-";
                    break;
                }
                case 107: {
                    name += "Dirac-";
                    break;
                }
                case 108: {
                    name += "Glaser-";
                    break;
                }
                case 109: {
                    name += "Casimir-";
                    break;
                }
                case 110: {
                    name += "Fraunhofer-";
                    break;
                }
                case 111: {
                    name += "Hubble-";
                    break;
                }
                case 112: {
                    name += "Niven-";
                    break;
                }
                case 113: {
                    name += "Miller-";
                    break;
                }
                case 114: {
                    name += "Dick-";
                    break;
                }
                case 115: {
                    name += "Maxwell-";
                    break;
                }
                case 116: {
                    name += "Weyl-";
                    break;
                }
                case 117: {
                    name += "Yang-";
                    break;
                }
                case 118: {
                    name += "Yukawa-";
                    break;
                }
                case 119: {
                    name += "Geneva-";
                    break;
                }
                case 120: {
                    name += "Huston-";
                    break;
                }
                case 121: {
                    name += "Weinberg-";
                    break;
                }
                case 122: {
                    name += "Salam-";
                    break;
                }
                case 123: {
                    name += "Ting-";
                    break;
                }
                case 124: {
                    name += "Goldhaber-";
                    break;
                }
                case 125: {
                    name += "Hooft-";
                    break;
                }
                case 126: {
                    name += "Wells-";
                    break;
                }
                case 127: {
                    name += "Szilard-";
                    break;
                }
                case 128: {
                    name += "Gernsback-";
                    break;
                }
                case 129: {
                    name += "Campbell-";
                    break;
                }
                case 130: {
                    name += "Bear-";
                    break;
                }
                case 131: {
                    name += "Stephenson-";
                    break;
                }
                case 132: {
                    name += "Darwin-";
                    break;
                }
                case 133: {
                    name += "Steno-";
                    break;
                }
                case 134: {
                    name += "Hutton-";
                    break;
                }
                case 135: {
                    name += "Buffon-";
                    break;
                }
                case 136: {
                    name += "Cuvier-";
                    break;
                }
                case 137: {
                    name += "Lyell-";
                    break;
                }
                case 138: {
                    name += "Smith-";
                    break;
                }
                case 139: {
                    name += "Whewell-";
                    break;
                }
                case 140: {
                    name += "Orbigny-";
                    break;
                }
                case 141: {
                    name += "Agassiz-";
                    break;
                }
                case 142: {
                    name += "Gilbert-";
                    break;
                }
                case 143: {
                    name += "Wegner-";
                    break;
                }
                case 144: {
                    name += "Holmes-";
                    break;
                }
                case 145: {
                    name += "Simpson-";
                    break;
                }
                case 146: {
                    name += "Hess-";
                    break;
                }
                case 147: {
                    name += "Neir-";
                    break;
                }
                case 148: {
                    name += "Abbot-";
                    break;
                }
                case 149: {
                    name += "Abeill-";
                    break;
                }
                case 150: {
                    name += "Abetti-";
                    break;
                }
                case 151: {
                    name += "Adams-";
                    break;
                }
                case 152: {
                    name += "Aitken-";
                    break;
                }
                case 153: {
                    name += "Aldrin-";
                    break;
                }
                case 154: {
                    name += "Alfven-";
                    break;
                }
                case 155: {
                    name += "Armstrong-";
                    break;
                }
                case 156: {
                    name += "Arp-";
                    break;
                }
                case 157: {
                    name += "Schmidt-";
                    break;
                }
                case 158: {
                    name += "Bailey-";
                    break;
                }
                case 159: {
                    name += "Barnard-";
                    break;
                }
                case 160: {
                    name += "Bappu-";
                    break;
                }
                case 161: {
                    name += "Becvar-";
                    break;
                }
                case 162: {
                    name += "Beer-";
                    break;
                }
                case 163: {
                    name += "Bennet-";
                    break;
                }
                case 164: {
                    name += "Bessel-";
                    break;
                }
                case 165: {
                    name += "Blaauw-";
                    break;
                }
                case 166: {
                    name += "Biot-";
                    break;
                }
                case 167: {
                    name += "Blagg-";
                    break;
                }
                case 168: {
                    name += "Blazhko-";
                    break;
                }
                case 169: {
                    name += "Bliss-";
                    break;
                }
                case 170: {
                    name += "Bode-";
                    break;
                }
                case 171: {
                    name += "Bok-";
                    break;
                }
                case 172: {
                    name += "Bolton-";
                    break;
                }
                case 173: {
                    name += "Boltzmann-";
                    break;
                }
                case 174: {
                    name += "Bond-";
                    break;
                }
                case 175: {
                    name += "Bondi-";
                    break;
                }
                case 176: {
                    name += "Bonner-";
                    break;
                }
                case 177: {
                    name += "Bootes-";
                    break;
                }
                case 178: {
                    name += "Borrelly-";
                    break;
                }
                case 179: {
                    name += "Boss-";
                    break;
                }
                case 180: {
                    name += "Bowen-";
                    break;
                }
                case 181: {
                    name += "Bradley-";
                    break;
                }
                case 182: {
                    name += "Brahe-";
                    break;
                }
                case 183: {
                    name += "Tycho-";
                    break;
                }
                case 184: {
                    name += "Brashear-";
                    break;
                }
                case 185: {
                    name += "Braun-";
                    break;
                }
                case 186: {
                    name += "Brans-";
                    break;
                }
                case 187: {
                    name += "Brooks-";
                    break;
                }
                case 188: {
                    name += "Brouwer-";
                    break;
                }
                case 189: {
                    name += "Brorsen-";
                    break;
                }
                case 190: {
                    name += "Brown-";
                    break;
                }
                case 191: {
                    name += "Bruno-";
                    break;
                }
                case 192: {
                    name += "Burnham-";
                    break;
                }
                case 193: {
                    name += "Caldwell-";
                    break;
                }
                case 194: {
                    name += "Campbell-";
                    break;
                }
                case 195: {
                    name += "Candy-";
                    break;
                }
                case 196: {
                    name += "Cannon-";
                    break;
                }
                case 197: {
                    name += "Carrington-";
                    break;
                }
                case 198: {
                    name += "Carter-";
                    break;
                }
                case 199: {
                    name += "Cassini-";
                    break;
                }
                //
                case 200: {
                    name += "Celsius-";
                    break;
                }
                case 201: {
                    name += "Challis-";
                    break;
                }
                case 202: {
                    name += "Chamberlin-";
                    break;
                }
                case 203: {
                    name += "Chandra-";
                    break;
                }
                case 204: {
                    name += "Charlier-";
                    break;
                }
                case 205: {
                    name += "Chi-";
                    break;
                }
                case 206: {
                    name += "Clairaut-";
                    break;
                }
                case 207: {
                    name += "Clark-";
                    break;
                }
                case 208: {
                    name += "Clarke-";
                    break;
                }
                case 209: {
                    name += "Coblentz-";
                    break;
                }
                case 210: {
                    name += "Common-";
                    break;
                }
                case 211: {
                    name += "Cooke-";
                    break;
                }
                case 212: {
                    name += "Copernicus-";
                    break;
                }
                case 213: {
                    name += "Cor-";
                    break;
                }
                case 214: {
                    name += "Cordoba-";
                    break;
                }
                case 215: {
                    name += "Curtis-";
                    break;
                }
                case 216: {
                    name += "Crommelin-";
                    break;
                }
                case 217: {
                    name += "DeLaRue-";
                    break;
                }
                case 218: {
                    name += "Delaunay-";
                    break;
                }
                case 219: {
                    name += "Denning-";
                    break;
                }
                case 220: {
                    name += "Descartes-";
                    break;
                }
                case 221: {
                    name += "DeSitter-";
                    break;
                }
                case 222: {
                    name += "Deslandres-";
                    break;
                }
                case 223: {
                    name += "DeVaucouleurs-";
                    break;
                }
                case 224: {
                    name += "DeVico-";
                    break;
                }
                case 225: {
                    name += "Dicke-";
                    break;
                }
                case 226: {
                    name += "Digges-";
                    break;
                }
                case 227: {
                    name += "Drake-";
                    break;
                }
                case 228: {
                    name += "Draper-";
                    break;
                }
                case 229: {
                    name += "Dreyer-";
                    break;
                }
                case 230: {
                    name += "Duner-";
                    break;
                }
                case 231: {
                    name += "EdgeWorth-";
                    break;
                }
                case 232: {
                    name += "Kuiper-";
                    break;
                }
                case 233: {
                    name += "Eddington-";
                    break;
                }
                case 234: {
                    name += "Encke-";
                    break;
                }
                case 235: {
                    name += "Euler-";
                    break;
                }
                case 236: {
                    name += "Evershed-";
                    break;
                }
                case 237: {
                    name += "Fabricius-";
                    break;
                }
                case 238: {
                    name += "Faulkes-";
                    break;
                }
                case 239: {
                    name += "Fitzgerald-";
                    break;
                }
                case 240: {
                    name += "Fizeau-";
                    break;
                }
                case 241: {
                    name += "Flammarion-";
                    break;
                }
                case 242: {
                    name += "Flamsteed-";
                    break;
                }
                case 243: {
                    name += "Flemming-";
                    break;
                }
                case 244: {
                    name += "Fowler-";
                    break;
                }
                case 245: {
                    name += "Franklin-";
                    break;
                }
                case 246: {
                    name += "Fraunhofer-";
                    break;
                }
                case 247: {
                    name += "Gagarin-";
                    break;
                }
                case 248: {
                    name += "Galilei-";
                    break;
                }
                case 249: {
                    name += "Galle-";
                    break;
                }
                case 250: {
                    name += "Gascoigne-";
                    break;
                }
                case 251: {
                    name += "Gassendi-";
                    break;
                }
                case 252: {
                    name += "Gauss-";
                    break;
                }
                case 253: {
                    name += "Geller-";
                    break;
                }
                case 254: {
                    name += "Giacconi-";
                    break;
                }
                case 255: {
                    name += "Gilbert-";
                    break;
                }
                case 256: {
                    name += "Gill-";
                    break;
                }
                case 257: {
                    name += "Glenn-";
                    break;
                }
                case 258: {
                    name += "Goddard-";
                    break;
                }
                case 259: {
                    name += "Goodricke-";
                    break;
                }
                case 260: {
                    name += "Gould-";
                    break;
                }
                case 261: {
                    name += "Greenstein-";
                    break;
                }
                case 262: {
                    name += "Greenwich-";
                    break;
                }
                case 263: {
                    name += "Gregorian-";
                    break;
                }
                case 264: {
                    name += "Gregory-";
                    break;
                }
                case 265: {
                    name += "Guth-";
                    break;
                }
                case 266: {
                    name += "Hadley-";
                    break;
                }
                case 267: {
                    name += "Hale-";
                    break;
                }
                case 268: {
                    name += "Bopp-";
                    break;
                }
                case 269: {
                    name += "Halley-";
                    break;
                }
                case 270: {
                    name += "Harriot-";
                    break;
                }
                case 271: {
                    name += "Hartmann-";
                    break;
                }
                case 272: {
                    name += "Hawking-";
                    break;
                }
                case 273: {
                    name += "Hertz-";
                    break;
                }
                case 274: {
                    name += "Henderson-";
                    break;
                }
                case 275: {
                    name += "Henry-";
                    break;
                }
                case 276: {
                    name += "Herschel-";
                    break;
                }
                case 277: {
                    name += "Hertzsprung-";
                    break;
                }
                case 278: {
                    name += "Hevelius-";
                    break;
                }
                case 279: {
                    name += "Hewish-";
                    break;
                }
                case 280: {
                    name += "Hey-";
                    break;
                }
                case 281: {
                    name += "Hill-";
                    break;
                }
                case 282: {
                    name += "Hogg-";
                    break;
                }
                case 283: {
                    name += "Hoffleit-";
                    break;
                }
                case 284: {
                    name += "Haffmeister-";
                    break;
                }
                case 285: {
                    name += "Holden-";
                    break;
                }
                case 286: {
                    name += "Hooke-";
                    break;
                }
                case 287: {
                    name += "Horrocks-";
                    break;
                }
                case 288: {
                    name += "Hoyle-";
                    break;
                }
                case 289: {
                    name += "Hubble-";
                    break;
                }
                case 290: {
                    name += "Huggins-";
                    break;
                }
                case 291: {
                    name += "Huchra-";
                    break;
                }
                case 292: {
                    name += "VanDeHulse-";
                    break;
                }
                case 293: {
                    name += "Humason-";
                    break;
                }
                case 294: {
                    name += "Innes";
                    break;
                }
                case 295: {
                    name += "Isaac-";
                    break;
                }
                case 296: {
                    name += "Jansky-";
                    break;
                }
                case 297: {
                    name += "Janssen-";
                    break;
                }
                case 298: {
                    name += "Jeans-";
                    break;
                }
                case 299: {
                    name += "Jeffreys-";
                    break;
                }
                //
                case 300: {
                    name += "Jodrell-";
                    break;
                }
                case 301: {
                    name += "Johnson-";
                    break;
                }
                case 302: {
                    name += "Jones-";
                    break;
                }
                case 303: {
                    name += "Joy-";
                    break;
                }
                case 304: {
                    name += "Julian-";
                    break;
                }
                case 305: {
                    name += "Kapteyn-";
                    break;
                }
                case 306: {
                    name += "Keck-";
                    break;
                }
                case 307: {
                    name += "Kelvin-";
                    break;
                }
                case 308: {
                    name += "Kepler-";
                    break;
                }
                case 309: {
                    name += "Kirch-";
                    break;
                }
                case 310: {
                    name += "Kraus-";
                    break;
                }
                case 311: {
                    name += "Kuiper-";
                    break;
                }
                case 312: {
                    name += "Lagrange-";
                    break;
                }
                case 313: {
                    name += "Lalande-";
                    break;
                }
                case 314: {
                    name += "Bootis-";
                    break;
                }
                case 315: {
                    name += "Langley-";
                    break;
                }
                case 316: {
                    name += "Langrenus-";
                    break;
                }
                case 317: {
                    name += "Laplace-";
                    break;
                }
                case 318: {
                    name += "Leavitt-";
                    break;
                }
                case 319: {
                    name += "Lemaitre-";
                    break;
                }
                case 320: {
                    name += "LeMonnier-";
                    break;
                }
                case 321: {
                    name += "Leonov-";
                    break;
                }
                case 322: {
                    name += "LeVerrier-";
                    break;
                }
                case 323: {
                    name += "Lexell-";
                    break;
                }
                case 324: {
                    name += "Lick-";
                    break;
                }
                case 325: {
                    name += "Linde-";
                    break;
                }
                case 326: {
                    name += "LiuXin-";
                    break;
                }
                case 327: {
                    name += "Lomonosov-";
                    break;
                }
                case 328: {
                    name += "Lorentz-";
                    break;
                }
                case 329: {
                    name += "Long-";
                    break;
                }
                case 330: {
                    name += "Lovell-";
                    break;
                }
                case 331: {
                    name += "Luyten-";
                    break;
                }
                case 332: {
                    name += "Lyot-";
                    break;
                }
                case 333: {
                    name += "McDonald-";
                    break;
                }
                case 334: {
                    name += "McMath-";
                    break;
                }
                case 335: {
                    name += "Mach-";
                    break;
                }
                case 336: {
                    name += "Maraidi-";
                    break;
                }
                case 337: {
                    name += "Maria-";
                    break;
                }
                case 338: {
                    name += "Marius-";
                    break;
                }
                case 339: {
                    name += "Maskelyne-";
                    break;
                }
                case 340: {
                    name += "Masursky-";
                    break;
                }
                case 341: {
                    name += "Maury-";
                    break;
                }
                case 342: {
                    name += "Kea-";
                    break;
                }
                case 343: {
                    name += "Maxwell-";
                    break;
                }
                case 344: {
                    name += "Menzel-";
                    break;
                }
                case 345: {
                    name += "Michell-";
                    break;
                }
                case 346: {
                    name += "Michelson-";
                    break;
                }
                case 347: {
                    name += "Mills-";
                    break;
                }
                case 348: {
                    name += "Milne-";
                    break;
                }
                case 349: {
                    name += "Minkowski-";
                    break;
                }
                case 350: {
                    name += "Minnaert-";
                    break;
                }
                case 351: {
                    name += "Mitchell-";
                    break;
                }
                case 352: {
                    name += "Montanari-";
                    break;
                }
                case 353: {
                    name += "Moore-";
                    break;
                }
                case 354: {
                    name += "Morgan-";
                    break;
                }
                case 355: {
                    name += "Moulton-";
                    break;
                }
                case 356: {
                    name += "Narlikar-";
                    break;
                }
                case 357: {
                    name += "Newcomb-";
                    break;
                }
                case 358: {
                    name += "Newton-";
                    break;
                }
                case 359: {
                    name += "Nozomi-";
                    break;
                }
                case 360: {
                    name += "Olbers-";
                    break;
                }
                case 361: {
                    name += "Oort-";
                    break;
                }
                case 362: {
                    name += "Opik-";
                    break;
                }
                case 363: {
                    name += "VonOppolzer-";
                    break;
                }
                case 364: {
                    name += "Parsons-";
                    break;
                }
                case 365: {
                    name += "Paschen-";
                    break;
                }
                case 366: {
                    name += "Payne-";
                    break;
                }
                case 367: {
                    name += "Peirsec-";
                    break;
                }
                case 368: {
                    name += "Perrine-";
                    break;
                }
                case 369: {
                    name += "Piazzi-";
                    break;
                }
                case 370: {
                    name += "Pickering-";
                    break;
                }
                case 371: {
                    name += "Planck-";
                    break;
                }
                case 372: {
                    name += "Plaskett-";
                    break;
                }
                case 373: {
                    name += "Pogson-";
                    break;
                }
                case 374: {
                    name += "Poincare-";
                    break;
                }
                case 375: {
                    name += "Pond-";
                    break;
                }
                case 376: {
                    name += "Pons-";
                    break;
                }
                case 377: {
                    name += "Porter-";
                    break;
                }
                case 378: {
                    name += "Proctor-";
                    break;
                }
                case 379: {
                    name += "Ptolemy-";
                    break;
                }
                case 380: {
                    name += "VonPurbach-";
                    break;
                }
                case 381: {
                    name += "Purcell-";
                    break;
                }
                case 382: {
                    name += "Quetelet-";
                    break;
                }
                case 383: {
                    name += "Reber-";
                    break;
                }
                case 384: {
                    name += "Rees-";
                    break;
                }
                case 385: {
                    name += "Reinmuth-";
                    break;
                }
                case 386: {
                    name += "Riccioli-";
                    break;
                }
                case 387: {
                    name += "Wallingford-";
                    break;
                }
                case 388: {
                    name += "Richer-";
                    break;
                }
                case 389: {
                    name += "Richey-";
                    break;
                }
                case 390: {
                    name += "Roche-";
                    break;
                }
                case 391: {
                    name += "Rittenhouse-";
                    break;
                }
                case 392: {
                    name += "Romer-";
                    break;
                }
                case 393: {
                    name += "Roque-";
                    break;
                }
                case 394: {
                    name += "Ross-";
                    break;
                }
                case 395: {
                    name += "Rosen-";
                    break;
                }
                case 396: {
                    name += "Rosette-";
                    break;
                }
                case 397: {
                    name += "Rosetti-";
                    break;
                }
                case 398: {
                    name += "Rosse-";
                    break;
                }
                case 399: {
                    name += "Rossi-";
                    break;
                }
                case 400: {
                    name += "Rowland-";
                    break;
                }
                //
                case 401: {
                    name += "Rubin-";
                    break;
                }
                case 402: {
                    name += "Russell-";
                    break;
                }
                case 403: {
                    name += "Rutherford-";
                    break;
                }
                case 404: {
                    name += "Ryle-";
                    break;
                }
                case 405: {
                    name += "Safronov-";
                    break;
                }
                case 406: {
                    name += "Sagan-";
                    break;
                }
                case 407: {
                    name += "Saha-";
                    break;
                }
                case 408: {
                    name += "Sakigake-";
                    break;
                }
                case 409: {
                    name += "Sandage-";
                    break;
                }
                case 410: {
                    name += "Salyut-";
                    break;
                }
                case 411: {
                    name += "Scheiner-";
                    break;
                }
                case 412: {
                    name += "Schiaparelli-";
                    break;
                }
                case 413: {
                    name += "Schmidt-";
                    break;
                }
                case 414: {
                    name += "Schonberg-";
                    break;
                }
                case 415: {
                    name += "Schramm-";
                    break;
                }
                case 416: {
                    name += "Schroter-";
                    break;
                }
                case 417: {
                    name += "Schwabe-";
                    break;
                }
                case 418: {
                    name += "Schwa-";
                    break;
                }
                case 419: {
                    name += "Schwartzchild-";
                    break;
                }
                case 420: {
                    name += "Schwartz-";
                    break;
                }
                case 421: {
                    name += "Sciama-";
                    break;
                }
                case 422: {
                    name += "Secchi-";
                    break;
                }
                case 423: {
                    name += "Selene-";
                    break;
                }
                case 424: {
                    name += "Seyfert-";
                    break;
                }
                case 425: {
                    name += "Shakerly-";
                    break;
                }
                case 426: {
                    name += "Shapley-";
                    break;
                }
                case 427: {
                    name += "ShenZhou-";
                    break;
                }
                case 428: {
                    name += "Shepard-";
                    break;
                }
                case 429: {
                    name += "Shklovskii-";
                    break;
                }
                case 430: {
                    name += "Shoemaker-";
                    break;
                }
                case 431: {
                    name += "Levy-";
                    break;
                }
                case 432: {
                    name += "Short-";
                    break;
                }
                case 433: {
                    name += "Slipher-";
                    break;
                }
                case 434: {
                    name += "Sloan-";
                    break;
                }
                case 435: {
                    name += "Smithsonian-";
                    break;
                }
                case 436: {
                    name += "Smyth-";
                    break;
                }
                case 437: {
                    name += "Smythii-";
                    break;
                }
                case 438: {
                    name += "South-";
                    break;
                }
                case 439: {
                    name += "Spitzer-";
                    break;
                }
                case 440: {
                    name += "Sporer-";
                    break;
                }
                case 441: {
                    name += "Sputnik-";
                    break;
                }
                case 442: {
                    name += "Stefan-";
                    break;
                }
                case 443: {
                    name += "Stebbins-";
                    break;
                }
                case 444: {
                    name += "Stormgren-";
                    break;
                }
                case 445: {
                    name += "Struve-";
                    break;
                }
                case 446: {
                    name += "Suisei-";
                    break;
                }
                case 447: {
                    name += "Taylor-";
                    break;
                }
                case 448: {
                    name += "Tauenberg-";
                    break;
                }
                case 449: {
                    name += "Tempel-";
                    break;
                }
                case 450: {
                    name += "Tinsley-";
                    break;
                }
                case 451: {
                    name += "Titius-";
                    break;
                }
                case 452: {
                    name += "Bode-";
                    break;
                }
                case 453: {
                    name += "Trumpler-";
                    break;
                }
                case 454: {
                    name += "Tsiolkovskii-";
                    break;
                }
                case 455: {
                    name += "Tsiolkovsky-";
                    break;
                }
                case 456: {
                    name += "Turner-";
                    break;
                }
                case 457: {
                    name += "Tully-";
                    break;
                }
                case 458: {
                    name += "Fisher-";
                    break;
                }
                case 459: {
                    name += "Tuttle-";
                    break;
                }
                case 460: {
                    name += "Unsold-";
                    break;
                }
                case 461: {
                    name += "Urey-";
                    break;
                }
                case 462: {
                    name += "VanBiesbroeck-";
                    break;
                }
                case 463: {
                    name += "VanAllen-";
                    break;
                }
                case 464: {
                    name += "VanMaanen-";
                    break;
                }
                case 465: {
                    name += "Vogel-";
                    break;
                }
                case 466: {
                    name += "Schpiner-";
                    break;
                }
                case 467: {
                    name += "Vogt-";
                    break;
                }
                case 468: {
                    name += "VonKarman-";
                    break;
                }
                case 469: {
                    name += "Voskhod-";
                    break;
                }
                case 470: {
                    name += "Weizsacker-";
                    break;
                }
                case 471: {
                    name += "Hershel-";
                    break;
                }
                case 472: {
                    name += "Wilkins-";
                    break;
                }
                case 473: {
                    name += "Wildt-";
                    break;
                }
                case 474: {
                    name += "Wilhelm-";
                    break;
                }
                case 475: {
                    name += "Winthrop-";
                    break;
                }
                case 476: {
                    name += "Wilson-";
                    break;
                }
                case 477: {
                    name += "Harrington-";
                    break;
                }
                case 478: {
                    name += "Keck-";
                    break;
                }
                case 479: {
                    name += "Wolf-";
                    break;
                }
                case 480: {
                    name += "Rayet-";
                    break;
                }
                case 481: {
                    name += "Etienne-";
                    break;
                }
                case 482: {
                    name += "Pons-";
                    break;
                }
                case 483: {
                    name += "Wollaston-";
                    break;
                }
                case 484: {
                    name += "Woolly-";
                    break;
                }
                case 485: {
                    name += "Wright-";
                    break;
                }
                case 486: {
                    name += "Yohkoh-";
                    break;
                }
                case 487: {
                    name += "Old-";
                    break;
                }
                case 488: {
                    name += "Zach-";
                    break;
                }
                case 489: {
                    name += "Zarya-";
                    break;
                }
                case 490: {
                    name += "Zeeman-";
                    break;
                }
                case 491: {
                    name += "Zeldovich-";
                    break;
                }
                case 492: {
                    name += "Zelenchukskaya-";
                    break;
                }
                case 493: {
                    name += "Zollner-";
                    break;
                }
                case 494: {
                    name += "Zwicky-";
                    break;
                }
                case 495: {
                    name += "Zond-";
                    break;
                }
                case 496: {
                    name += "Zvezda-";
                    break;
                }
                case 497: {
                    name += "Dragon-";
                    break;
                }
                case 498: {
                    name += "Gohome-";
                    break;
                }
                case 499: {
                    name += "Beyond-";
                    break;
                }
                //  

            }
            d = (d + d) % 500;
        }
        if (n > 1) {
            StringBuilder lang = new StringBuilder();
            while (d > 0) {
                int a = d % name.length();
                d -= a * a;
                int b = a % 7;
                int c = a + b;
                if (c >= name.length()) {
                    c = name.length();
                }
                for (int i = a; i < c; i++) {
                    lang.append(name.charAt(i));
                }
            }
            name = lang.toString();
        }
        return name;
    }

    public static String SectorName(int sx, int sy, int sz) {
        String name = "Sol";
        if (sx != 0 || sy != 0 || sz != 0) {
            int nx = 0, ny = 0, nz = 0;
            name = "";
            if (sx > 100) {
                nx = sx - 100;
                sx = 100;
            } else if (sx < -100) {
                nx = sx + 100;
                sx = -100;
            }
            if (sy > 100) {
                ny = sy - 100;
                sy = 100;
            } else if (sy < -100) {
                ny = sy + 100;
                sy = -100;
            }
            if (sz > 100) {
                nz = sz - 100;
                sz = 100;
            } else if (sz < -100) {
                nz = sz + 100;
                sz = -100;
            }
            switch (sx) {
                case -100: {
                    name += "Core-";
                    break;
                }
                case -99: {
                    name += "Eridanus-";
                    break;
                }
                case -98: {
                    name += "Horologium-";
                    break;
                }
                case -97: {
                    name += "Lynx-";
                    break;
                }
                case -96: {
                    name += "Mensa-";
                    break;
                }
                case -95: {
                    name += "Monoceros-";
                    break;
                }
                case -94: {
                    name += "Octas-";
                    break;
                }
                case -93: {
                    name += "Pavo-";
                    break;
                }
                case -92: {
                    name += "Lagoon-";
                    break;
                }
                case -91: {
                    name += "Tucanae-";
                    break;
                }
                case -90: {
                    name += "Ring-";
                    break;
                }

                case -89: {
                    name += "Cone-";
                    break;
                }
                case -88: {
                    name += "Dog-";
                    break;
                }
                case -87: {
                    name += "Bird-";
                    break;
                }
                case -86: {
                    name += "Hand-";
                    break;
                }
                case -85: {
                    name += "Folix-";
                    break;
                }
                case -84: {
                    name += "Felix-";
                    break;
                }
                case -83: {
                    name += "FoundSomething-";
                    break;
                }
                case -82: {
                    name += "Disk-";
                    break;
                }
                case -81: {
                    name += "Wire-";
                    break;
                }
                case -80: {
                    name += "Ashes-";
                    break;
                }

                case -79: {
                    name += "Wasch-";
                    break;
                }
                case -78: {
                    name += "Chaos-";
                    break;
                }
                case -77: {
                    name += "Sterile-";
                    break;
                }
                case -76: {
                    name += "Strontium-";
                    break;
                }
                case -75: {
                    name += "Skull-";
                    break;
                }
                case -74: {
                    name += "Scatter-";
                    break;
                }
                case -73: {
                    name += "Mek-";
                    break;
                }
                case -72: {
                    name += "DeversStation-";
                    break;
                }
                case -71: {
                    name += "King-";
                    break;
                }
                case -70: {
                    name += "Shaula-";
                    break;
                }

                case -69: {
                    name += "Tunnel-";
                    break;
                }
                case -68: {
                    name += "Warrior-";
                    break;
                }
                case -67: {
                    name += "Slab-";
                    break;
                }
                case -66: {
                    name += "March-";
                    break;
                }
                case -65: {
                    name += "Forward-";
                    break;
                }
                case -64: {
                    name += "Onward-";
                    break;
                }
                case -63: {
                    name += "Sulafat-";
                    break;
                }
                case -62: {
                    name += "Base-";
                    break;
                }
                case -61: {
                    name += "Hauser-";
                    break;
                }
                case -60: {
                    name += "Antares-";
                    break;
                }

                case -59: {
                    name += "Gate-";
                    break;
                }
                case -58: {
                    name += "Port-";
                    break;
                }
                case -57: {
                    name += "Beach-";
                    break;
                }
                case -56: {
                    name += "SandDune-";
                    break;
                }
                case -55: {
                    name += "Sand-";
                    break;
                }
                case -54: {
                    name += "Rockpool-";
                    break;
                }
                case -53: {
                    name += "Acrab-";
                    break;
                }
                case -52: {
                    name += "DriftWood-";
                    break;
                }
                case -51: {
                    name += "SeaWeed-";
                    break;
                }
                case -50: {
                    name += "Coral-";
                    break;
                }

                case -49: {
                    name += "Cantata-";
                    break;
                }
                case -48: {
                    name += "Inrae-";
                    break;
                }
                case -47: {
                    name += "Daue-";
                    break;
                }
                case -46: {
                    name += "Opus-";
                    break;
                }
                case -45: {
                    name += "Ophiuchus-";
                    break;
                }
                case -44: {
                    name += "Cypher-";
                    break;
                }
                case -43: {
                    name += "Scorpius-";
                    break;
                }
                case -42: {
                    name += "Lyra-";
                    break;
                }
                case -41: {
                    name += "Sapho-";
                    break;
                }
                case -40: {
                    name += "Dschubba-";
                    break;
                }

                case -39: {
                    name += "Tetradon-";
                    break;
                }
                case -38: {
                    name += "Kali-";
                    break;
                }
                case -37: {
                    name += "Rakshashra-";
                    break;
                }
                case -36: {
                    name += "Therion-";
                    break;
                }
                case -35: {
                    name += "Megatherion-";
                    break;
                }
                case -34: {
                    name += "Hyperion-";
                    break;
                }
                case -33: {
                    name += "Amon-";
                    break;
                }
                case -32: {
                    name += "Mara-";
                    break;
                }
                case -31: {
                    name += "Yama-";
                    break;
                }
                case -30: {
                    name += "Rama-";
                    break;
                }

                case -29: {
                    name += "Sextans-";
                    break;
                }
                case -28: {
                    name += "Electra-";
                    break;
                }
                case -27: {
                    name += "Girtab-";
                    break;
                }
                case -26: {
                    name += "Spica-";
                    break;
                }
                case -25: {
                    name += "Vega-";
                    break;
                }
                case -24: {
                    name += "Ra-";
                    break;
                }
                case -23: {
                    name += "Set-";
                    break;
                }
                case -22: {
                    name += "Zaurak-";
                    break;
                }
                case -21: {
                    name += "Hydrus-";
                    break;
                }
                case -20: {
                    name += "Cronos-";
                    break;
                }
                case -19: {
                    name += "Peacock-";
                    break;
                }
                case -18: {
                    name += "YedPrior-";
                    break;
                }
                case -17: {
                    name += "Alphard-";
                    break;
                }
                case -16: {
                    name += "Acamar-";
                    break;
                }
                case -15: {
                    name += "Alhazrad-";
                    break;
                }
                case -14: {
                    name += "Achernar-";
                    break;
                }
                case -13: {
                    name += "Pavonis-";
                    break;
                }
                case -12: {
                    name += "Vindemiatrix-";
                    break;
                }
                case -11: {
                    name += "YedPosterior-";
                    break;
                }
                case -10: {
                    name += "Hydra-";
                    break;
                }
                case -9: {
                    name += "Cursa-";
                    break;
                }
                case -8: {
                    name += "Sabik-";
                    break;
                }
                case -7: {
                    name += "Cebairai-";
                    break;
                }
                case -6: {
                    name += "Virgo-";
                    break;
                }
                case -5: {
                    name += "Volans-";
                    break;
                }
                case -4: {
                    name += "Rasalhague-";
                    break;
                }
                case -3: {
                    name += "Porrima-";
                    break;
                }
                case -2: {
                    name += "Indi-";
                    break;
                }
                case -1: {
                    name += "Eridani-";
                    break;
                }

                case 0: {
                    name += "Sol-";
                    break;
                }
                case 1: {
                    name += "Centauri-";
                    break;
                }
                case 2: {
                    name += "Ceti-";
                    break;
                }
                case 3: {
                    name += "Arcturus-";
                    break;
                }
                case 4: {
                    name += "Procyon-";
                    break;
                }
                case 5: {
                    name += "Alpheratz-";
                    break;
                }
                case 6: {
                    name += "Menkent-";
                    break;
                }
                case 7: {
                    name += "Altair-";
                    break;
                }
                case 8: {
                    name += "Skat-";
                    break;
                }
                case 9: {
                    name += "Cetus-";
                    break;
                }
                case 10: {
                    name += "Wazn-";
                    break;
                }
                case 11: {
                    name += "Mirach-";
                    break;
                }
                case 12: {
                    name += "Gacrux-";
                    break;
                }
                case 13: {
                    name += "Muhlifain-";
                    break;
                }
                case 14: {
                    name += "Markab-";
                    break;
                }
                case 15: {
                    name += "Nihal-";
                    break;
                }
                case 16: {
                    name += "Lepus-";
                    break;
                }
                case 17: {
                    name += "Almaak";
                    break;
                }
                case 18: {
                    name += "Gomeisa-";
                    break;
                }
                case 19: {
                    name += "Bootis-";
                    break;
                }
                case 20: {
                    name += "Nekkar-";
                    break;
                }
                case 21: {
                    name += "Capella-";
                    break;
                }
                case 22: {
                    name += "Izar-";
                    break;
                }
                case 23: {
                    name += "Tarazed-";
                    break;
                }
                case 24: {
                    name += "Tajat-";
                    break;
                }
                case 25: {
                    name += "Bellatrix-";
                    break;
                }
                case 26: {
                    name += "Phact-";
                    break;
                }
                case 27: {
                    name += "Aries-";
                    break;
                }
                case 28: {
                    name += "Hamai-";
                    break;
                }
                case 29: {
                    name += "Sheratan-";
                    break;
                }
                case 30: {
                    name += "Sasaluud-";
                    break;
                }
                case 31: {
                    name += "Geminid-";
                    break;
                }
                case 32: {
                    name += "Acrux-";
                    break;
                }
                case 33: {
                    name += "Algenib-";
                    break;
                }
                case 34: {
                    name += "Propus-";
                    break;
                }
                case 35: {
                    name += "Becrux-";
                    break;
                }
                case 36: {
                    name += "Crux-";
                    break;
                }
                case 37: {
                    name += "Sadalmelik-";
                    break;
                }
                case 38: {
                    name += "Chimera-";
                    break;
                }
                case 39: {
                    name += "Manticore-";
                    break;
                }
                case 40: {
                    name += "Fate-";
                    break;
                }
                case 41: {
                    name += "Menkalinan-";
                    break;
                }
                case 42: {
                    name += "Betelgeuse-";
                    break;
                }
                case 43: {
                    name += "Styks-";
                    break;
                }
                case 44: {
                    name += "Ithica-";
                    break;
                }
                case 45: {
                    name += "Troy-";
                    break;
                }
                case 46: {
                    name += "Aquila-";
                    break;
                }
                case 47: {
                    name += "Bayble-";
                    break;
                }
                case 48: {
                    name += "Best-";
                    break;
                }
                case 49: {
                    name += "Mirzam-";
                    break;
                }
                case 50: {
                    name += "Ionia-";
                    break;
                }
                case 51: {
                    name += "Auriga-";
                    break;
                }
                case 52: {
                    name += "Hadar-";
                    break;
                }
                case 53: {
                    name += "Egyptus-";
                    break;
                }
                case 54: {
                    name += "Lupus-";
                    break;
                }
                case 55: {
                    name += "Romanus-";
                    break;
                }
                case 56: {
                    name += "Gem-";
                    break;
                }
                case 57: {
                    name += "Gemini-";
                    break;
                }
                case 58: {
                    name += "Psicor-";
                    break;
                }
                case 59: {
                    name += "Illythid-";
                    break;
                }
                case 60: {
                    name += "Patra-";
                    break;
                }
                case 61: {
                    name += "Orphius-";
                    break;
                }
                case 62: {
                    name += "Trilo-";
                    break;
                }
                case 63: {
                    name += "Kiko-";
                    break;
                }
                case 64: {
                    name += "Salem-";
                    break;
                }
                case 65: {
                    name += "Zylarius-";
                    break;
                }
                case 66: {
                    name += "LostHart-";
                    break;
                }
                case 67: {
                    name += "Enif-";
                    break;
                }
                case 68: {
                    name += "Geni-";
                    break;
                }
                case 69: {
                    name += "Jinni-";
                    break;
                }
                case 70: {
                    name += "Faust-";
                    break;
                }
                case 71: {
                    name += "Roc-";
                    break;
                }
                case 72: {
                    name += "Saiph-";
                    break;
                }
                case 73: {
                    name += "Aries-";
                    break;
                }
                case 74: {
                    name += "Pisces-";
                    break;
                }
                case 75: {
                    name += "Aquarius-";
                    break;
                }
                case 76: {
                    name += "Siren-";
                    break;
                }
                case 77: {
                    name += "Rigel-";
                    break;
                }
                case 78: {
                    name += "Seginus-";
                    break;
                }
                case 79: {
                    name += "Harpy-";
                    break;
                }
                case 80: {
                    name += "Cyclops-";
                    break;
                }
                case 81: {
                    name += "Gemma-";
                    break;
                }
                case 82: {
                    name += "Alnitak-";
                    break;
                }
                case 83: {
                    name += "Geminorum-";
                    break;
                }
                case 84: {
                    name += "Loop-";
                    break;
                }
                case 85: {
                    name += "LambdaOrionis-";
                    break;
                }
                case 86: {
                    name += "Orion-";
                    break;
                }
                case 87: {
                    name += "Mintaka-";
                    break;
                }
                case 88: {
                    name += "Alnilam-";
                    break;
                }
                case 89: {
                    name += "Norma-";
                    break;
                }
                case 90: {
                    name += "Mebsuta-";
                    break;
                }
                case 91: {
                    name += "Corona-";
                    break;
                }
                case 92: {
                    name += "Columba-";
                    break;
                }
                case 93: {
                    name += "Circinus-";
                    break;
                }
                case 94: {
                    name += "Centaurus-";
                    break;
                }
                case 95: {
                    name += "Caelum-";
                    break;
                }
                case 96: {
                    name += "Canis-";
                    break;
                }
                case 97: {
                    name += "Ara-";
                    break;
                }
                case 98: {
                    name += "Antlia-";
                    break;
                }
                case 99: {
                    name += "Andromeda-";
                    break;
                }
                //
                case 100: {
                    name += "Void-";
                    break;
                }
            }

            switch (sy) {
                case -100: {
                    name += "VoidPole-";
                    break;
                }

                case -99: {
                    name += "Dorado-";
                    break;
                }
                case -98: {
                    name += "Lacerta-";
                    break;
                }
                case -97: {
                    name += "Telescopium -";
                    break;
                }
                case -96: {
                    name += "Gum-";
                    break;
                }
                case -95: {
                    name += "DarkCloud-";
                    break;
                }
                case -94: {
                    name += "Helix-";
                    break;
                }
                case -93: {
                    name += "Necronom-";
                    break;
                }
                case -92: {
                    name += "Nialinium-";
                    break;
                }
                case -91: {
                    name += "Negatus-";
                    break;
                }
                case -90: {
                    name += "Zeron-";
                    break;
                }

                case -89: {
                    name += "Voidanoi-";
                    break;
                }
                case -88: {
                    name += "Nullon-";
                    break;
                }
                case -87: {
                    name += "Negadra-";
                    break;
                }
                case -86: {
                    name += "Hela-";
                    break;
                }
                case -85: {
                    name += "Carriad-";
                    break;
                }
                case -84: {
                    name += "Regor-";
                    break;
                }
                case -83: {
                    name += "Zor-";
                    break;
                }
                case -82: {
                    name += "Algra-";
                    break;
                }
                case -81: {
                    name += "Isolon-";
                    break;
                }
                case -80: {
                    name += "Xcoatle-";
                    break;
                }

                case -79: {
                    name += "Flower-";
                    break;
                }
                case -78: {
                    name += "Petle-";
                    break;
                }
                case -77: {
                    name += "Voidkraft-";
                    break;
                }
                case -76: {
                    name += "Absuns-";
                    break;
                }
                case -75: {
                    name += "Absolute-";
                    break;
                }
                case -74: {
                    name += "Abros-";
                    break;
                }
                case -73: {
                    name += "Zillvoid-";
                    break;
                }
                case -72: {
                    name += "Voidwall-";
                    break;
                }
                case -71: {
                    name += "Astra-";
                    break;
                }
                case -70: {
                    name += "Beltus-";
                    break;
                }

                case -69: {
                    name += "Destos-";
                    break;
                }
                case -68: {
                    name += "Emulus-";
                    break;
                }
                case -67: {
                    name += "Infinitus-";
                    break;
                }
                case -66: {
                    name += "Sinus-";
                    break;
                }
                case -65: {
                    name += "Stonus-";
                    break;
                }
                case -64: {
                    name += "RiverRift-";
                    break;
                }
                case -63: {
                    name += "Yaros-";
                    break;
                }
                case -62: {
                    name += "Desurtus-";
                    break;
                }
                case -61: {
                    name += "Powder-";
                    break;
                }
                case -60: {
                    name += "Pip-";
                    break;
                }

                case -59: {
                    name += "Cleo-";
                    break;
                }
                case -58: {
                    name += "Tolomey-";
                    break;
                }
                case -57: {
                    name += "Suhail-";
                    break;
                }
                case -56: {
                    name += "Circan-";
                    break;
                }
                case -55: {
                    name += "Tori-";
                    break;
                }
                case -54: {
                    name += "Zot-";
                    break;
                }
                case -53: {
                    name += "Markeb-";
                    break;
                }
                case -52: {
                    name += "Zieben-";
                    break;
                }
                case -51: {
                    name += "Zela-";
                    break;
                }
                case -50: {
                    name += "Zien-";
                    break;
                }

                case -49: {
                    name += "Kien-";
                    break;
                }
                case -48: {
                    name += "Feoria-";
                    break;
                }
                case -47: {
                    name += "Nia-";
                    break;
                }
                case -46: {
                    name += "Non-";
                    break;
                }
                case -45: {
                    name += "Mono-";
                    break;
                }
                case -44: {
                    name += "Mythcycle-";
                    break;
                }
                case -43: {
                    name += "Myth-";
                    break;
                }
                case -42: {
                    name += "Alula-";
                    break;
                }
                case -41: {
                    name += "Doivid-";
                    break;
                }
                case -40: {
                    name += "Vacan-";
                    break;
                }

                case -39: {
                    name += "Spark-";
                    break;
                }
                case -38: {
                    name += "Candle-";
                    break;
                }
                case -37: {
                    name += "Fire-";
                    break;
                }
                case -36: {
                    name += "Coin-";
                    break;
                }
                case -35: {
                    name += "Crown-";
                    break;
                }
                case -34: {
                    name += "Vampire-";
                    break;
                }
                case -33: {
                    name += "Specter-";
                    break;
                }
                case -32: {
                    name += "Demon-";
                    break;
                }
                case -31: {
                    name += "Ghost-";
                    break;
                }
                case -30: {
                    name += "Dust-";
                    break;
                }

                case -29: {
                    name += "Libra-";
                    break;
                }
                case -28: {
                    name += "Hypervoid-";
                    break;
                }
                case -27: {
                    name += "Valis-";
                    break;
                }
                case -26: {
                    name += "Tetracyanide-";
                    break;
                }
                case -25: {
                    name += "Illia-";
                    break;
                }
                case -24: {
                    name += "TaniaAustralis-";
                    break;
                }
                case -23: {
                    name += "Ethus-";
                    break;
                }
                case -22: {
                    name += "Eriathis-";
                    break;
                }
                case -21: {
                    name += "Emptipathis-";
                    break;
                }
                case -20: {
                    name += "Hologen-";
                    break;
                }
                case -19: {
                    name += "Anoton-";
                    break;
                }
                case -18: {
                    name += "Muscida-";
                    break;
                }
                case -17: {
                    name += "Blizsphere-";
                    break;
                }
                case -16: {
                    name += "Zubeneschamali-";
                    break;
                }
                case -15: {
                    name += "Thoth-";
                    break;
                }
                case -14: {
                    name += "Yara";
                    break;
                }
                case -13: {
                    name += "Tania-";
                    break;
                }
                case -12: {
                    name += "Dubhe-";
                    break;
                }
                case -11: {
                    name += "Alkaid-";
                    break;
                }
                case -10: {
                    name += "Alhena-";
                    break;
                }
                case -9: {
                    name += "Pictor-";
                    break;
                }
                case -8: {
                    name += "Alioth-";
                    break;
                }
                case -7: {
                    name += "Zubenelenubi-";
                    break;
                }
                case -6: {
                    name += "Mizar-";
                    break;
                }
                case -5: {
                    name += "Castor-";
                    break;
                }
                case -4: {
                    name += "UrsaMajor-";
                    break;
                }
                case -3: {
                    name += "Pollux-";
                    break;
                }
                case -2: {
                    name += "Talitha-";
                    break;
                }
                case -1: {
                    name += "Vela-";
                    break;
                }
                case 0: {
                    name += "Sol-";
                    break;
                }
                case 1: {
                    name += "Sirius-";
                    break;
                }
                case 2: {
                    name += "Edge-";
                    break;
                }
                case 3: {
                    name += "Denebola-";
                    break;
                }
                case 4: {
                    name += "Mothallah-";
                    break;
                }
                case 5: {
                    name += "Ankaa-";
                    break;
                }
                case 6: {
                    name += "Zosma-";
                    break;
                }
                case 7: {
                    name += "Regulus-";
                    break;
                }
                case 8: {
                    name += "Algorab-";
                    break;
                }
                case 9: {
                    name += "Altais-";
                    break;
                }
                case 10: {
                    name += "Edasich-";
                    break;
                }
                case 11: {
                    name += "Triangulum-";
                    break;
                }
                case 12: {
                    name += "Algeiba-";
                    break;
                }
                case 13: {
                    name += "Hercules-";
                    break;
                }
                case 14: {
                    name += "Etamin-";
                    break;
                }
                case 15: {
                    name += "Kornephoros-";
                    break;
                }
                case 16: {
                    name += "Sculptor-";
                    break;
                }
                case 17: {
                    name += "Chertan-";
                    break;
                }
                case 18: {
                    name += "Phoenix-";
                    break;
                }
                case 19: {
                    name += "Crater-";
                    break;
                }
                case 20: {
                    name += "Homam-";
                    break;
                }
                case 21: {
                    name += "Matar-";
                    break;
                }
                case 22: {
                    name += "UrsaMinor-";
                    break;
                }
                case 23: {
                    name += "Magi-";
                    break;
                }
                case 24: {
                    name += "Familiar-";
                    break;
                }
                case 25: {
                    name += "Leo-";
                    break;
                }
                case 26: {
                    name += "Adhafera-";
                    break;
                }
                case 27: {
                    name += "Evorius-";
                    break;
                }
                case 28: {
                    name += "Magnus-";
                    break;
                }
                case 29: {
                    name += "Aberant-";
                    break;
                }
                case 30: {
                    name += "Corvus-";
                    break;
                }
                case 31: {
                    name += "Oberon-";
                    break;
                }
                case 32: {
                    name += "Titania-";
                    break;
                }
                case 33: {
                    name += "Furud-";
                    break;
                }
                case 34: {
                    name += "Draco-";
                    break;
                }
                case 35: {
                    name += "Trius-";
                    break;
                }
                case 36: {
                    name += "Rastaban-";
                    break;
                }
                case 37: {
                    name += "NovaGalis-";
                    break;
                }
                case 38: {
                    name += "Rasalgethi-";
                    break;
                }
                case 39: {
                    name += "Calanova";
                    break;
                }
                case 40: {
                    name += "Cymrig-";
                    break;
                }
                case 41: {
                    name += "Atria-";
                    break;
                }
                case 42: {
                    name += "Polaris-";
                    break;
                }
                case 43: {
                    name += "Adhara-";
                    break;
                }
                case 44: {
                    name += "Andorius-";
                    break;
                }
                case 45: {
                    name += "Vulcan-";
                    break;
                }
                case 46: {
                    name += "MatterVoid-";
                    break;
                }
                case 47: {
                    name += "HotStar-";
                    break;
                }
                case 48: {
                    name += "Pherkad-";
                    break;
                }
                case 49: {
                    name += "BackToWork-";
                    break;
                }
                case 50: {
                    name += "Heading-";
                    break;
                }
                case 51: {
                    name += "Nearus-";
                    break;
                }
                case 52: {
                    name += "Perseus-";
                    break;
                }
                case 53: {
                    name += "Away-";
                    break;
                }
                case 54: {
                    name += "FarAway-";
                    break;
                }
                case 55: {
                    name += "Station-";
                    break;
                }
                case 56: {
                    name += "Transit-";
                    break;
                }
                case 57: {
                    name += "JumpTo-";
                    break;
                }
                case 58: {
                    name += "FoundIt-";
                    break;
                }
                case 59: {
                    name += "Mirphak-";
                    break;
                }
                case 60: {
                    name += "Algol-";
                    break;
                }
                case 61: {
                    name += "DarkStar-";
                    break;
                }
                case 62: {
                    name += "VoidNova-";
                    break;
                }
                case 63: {
                    name += "TerraNova-";
                    break;
                }
                case 64: {
                    name += "NovaSpace-";
                    break;
                }
                case 65: {
                    name += "NovaTime-";
                    break;
                }
                case 66: {
                    name += "TimeTerra-";
                    break;
                }
                case 67: {
                    name += "Dirt-";
                    break;
                }
                case 68: {
                    name += "Ground-";
                    break;
                }
                case 69: {
                    name += "Pebble-";
                    break;
                }
                case 70: {
                    name += "Gravis-";
                    break;
                }
                case 71: {
                    name += "Island-";
                    break;
                }
                case 72: {
                    name += "Pelego-";
                    break;
                }
                case 73: {
                    name += "SteppingStone-";
                    break;
                }
                case 74: {
                    name += "Nothia-";
                    break;
                }
                case 75: {
                    name += "Beth-";
                    break;
                }
                case 76: {
                    name += "Neriad-";
                    break;
                }
                case 77: {
                    name += "Vacnon-";
                    break;
                }
                case 78: {
                    name += "NovaVoid-";
                    break;
                }
                case 79: {
                    name += "Colditz-";
                    break;
                }
                case 80: {
                    name += "Carrion-";
                    break;
                }
                case 81: {
                    name += "Potent-";
                    break;
                }
                case 82: {
                    name += "Damnation-";
                    break;
                }
                case 83: {
                    name += "CatsEye-";
                    break;
                }
                case 84: {
                    name += "Owl-";
                    break;
                }
                case 85: {
                    name += "JewelBox-";
                    break;
                }
                case 86: {
                    name += "Variable-";
                    break;
                }
                case 87: {
                    name += "DarkGlobule-";
                    break;
                }
                case 88: {
                    name += "FootPrint-";
                    break;
                }
                case 89: {
                    name += "Praesepe-";
                    break;
                }
                case 90: {
                    name += "ChiPersei-";
                    break;
                }
                case 91: {
                    name += "California-";
                    break;
                }
                case 92: {
                    name += "Wezen-";
                    break;
                }
                case 93: {
                    name += "Aludra-";
                    break;
                }
                case 94: {
                    name += "Vulpecula-";
                    break;
                }
                case 95: {
                    name += "Tucana-";
                    break;
                }
                case 96: {
                    name += "Pegasus-";
                    break;
                }
                case 97: {
                    name += "Cancer-";
                    break;
                }
                case 98: {
                    name += "Coma-";
                    break;
                }
                case 99: {
                    name += "Chamaeleon-";
                    break;
                }
                //
                case 100: {
                    name += "PoleVoid-";
                    break;
                }
            }
            switch (sz) {
                case -100: {
                    name += "AntiSpinward-";
                    break;
                }

                case -99: {
                    name += "Microscopium-";
                    break;
                }
                case -98: {
                    name += "Sagitta-";
                    break;
                }
                case -97: {
                    name += "Scutum-";
                    break;
                }
                case -96: {
                    name += "Trifid-";
                    break;
                }
                case -95: {
                    name += "Omega-";
                    break;
                }
                case -94: {
                    name += "Haydes-";
                    break;
                }
                case -93: {
                    name += "Majelan-";
                    break;
                }
                case -92: {
                    name += "Micromajelan-";
                    break;
                }
                case -91: {
                    name += "Mouse-";
                    break;
                }
                case -90: {
                    name += "Bat-";
                    break;
                }

                case -89: {
                    name += "Frog-";
                    break;
                }
                case -88: {
                    name += "Cat-";
                    break;
                }
                case -87: {
                    name += "Unfinished-";
                    break;
                }
                case -86: {
                    name += "Protoworld-";
                    break;
                }
                case -85: {
                    name += "Dots-";
                    break;
                }
                case -84: {
                    name += "Space-";
                    break;
                }
                case -83: {
                    name += "TimeSpace-";
                    break;
                }
                case -82: {
                    name += "GasSpace-";
                    break;
                }
                case -81: {
                    name += "DarkSpace-";
                    break;
                }
                case -80: {
                    name += "ColdSpace-";
                    break;
                }

                case -79: {
                    name += "Enday-";
                    break;
                }
                case -78: {
                    name += "Waitin-";
                    break;
                }
                case -77: {
                    name += "Nomore-";
                    break;
                }
                case -76: {
                    name += "LookSee-";
                    break;
                }
                case -75: {
                    name += "View-";
                    break;
                }
                case -74: {
                    name += "GravityBomb-";
                    break;
                }
                case -73: {
                    name += "TimeCharge-";
                    break;
                }
                case -72: {
                    name += "SpaceCharge-";
                    break;
                }
                case -71: {
                    name += "GammaBurst-";
                    break;
                }
                case -70: {
                    name += "NeutronVoid-";
                    break;
                }

                case -69: {
                    name += "ProtonVoid-";
                    break;
                }
                case -68: {
                    name += "ProtonWave-";
                    break;
                }
                case -67: {
                    name += "ShockTide-";
                    break;
                }
                case -66: {
                    name += "SkyCharge-";
                    break;
                }
                case -65: {
                    name += "PlasmaField-";
                    break;
                }
                case -64: {
                    name += "Meteorus-";
                    break;
                }
                case -63: {
                    name += "VoidGas-";
                    break;
                }
                case -62: {
                    name += "BlastRadius-";
                    break;
                }
                case -61: {
                    name += "DarkEnergy-";
                    break;
                }
                case -60: {
                    name += "MegaNova-";
                    break;
                }

                case -59: {
                    name += "DarkMatter-";
                    break;
                }
                case -58: {
                    name += "BlastDiameter-";
                    break;
                }
                case -57: {
                    name += "GasVoid-";
                    break;
                }
                case -56: {
                    name += "Debri-";
                    break;
                }
                case -55: {
                    name += "PlasmaBelt-";
                    break;
                }
                case -54: {
                    name += "SkyLight-";
                    break;
                }
                case -53: {
                    name += "ShockWave-";
                    break;
                }
                case -52: {
                    name += "ProtonHale-";
                    break;
                }
                case -51: {
                    name += "NeutronRain-";
                    break;
                }
                case -50: {
                    name += "GammaRays-";
                    break;
                }

                case -49: {
                    name += "TimeWave-";
                    break;
                }
                case -48: {
                    name += "SpaceWave-";
                    break;
                }
                case -47: {
                    name += "GravityWave-";
                    break;
                }
                case -46: {
                    name += "LastStand-";
                    break;
                }
                case -45: {
                    name += "Lost-";
                    break;
                }
                case -44: {
                    name += "Hope-";
                    break;
                }
                case -43: {
                    name += "HelpPoint-";
                    break;
                }
                case -42: {
                    name += "Bastion-";
                    break;
                }
                case -41: {
                    name += "Taurus-";
                    break;
                }
                case -40: {
                    name += "Pleiades-";
                    break;
                }

                case -39: {
                    name += "Alianus-";
                    break;
                }
                case -38: {
                    name += "Popus-";
                    break;
                }
                case -37: {
                    name += "Alloy-";
                    break;
                }
                case -36: {
                    name += "Alcyone-";
                    break;
                }
                case -35: {
                    name += "Tree-";
                    break;
                }
                case -34: {
                    name += "GreenGrass-";
                    break;
                }
                case -33: {
                    name += "Leaf-";
                    break;
                }
                case -32: {
                    name += "Sharazad-";
                    break;
                }
                case -31: {
                    name += "Julia-";
                    break;
                }
                case -30: {
                    name += "Musca-";
                    break;
                }

                case -29: {
                    name += "KausMedia-";
                    break;
                }
                case -28: {
                    name += "Pronos-";
                    break;
                }
                case -27: {
                    name += "Prios";
                    break;
                }
                case -26: {
                    name += "Aus-";
                    break;
                }
                case -25: {
                    name += "Adria-";
                    break;
                }
                case -24: {
                    name += "Tarius-";
                    break;
                }
                case -23: {
                    name += "Sagittarius-";
                    break;
                }
                case -22: {
                    name += "Nunki-";
                    break;
                }
                case -21: {
                    name += "Zelack-";
                    break;
                }
                case -20: {
                    name += "Grus-";
                    break;
                }
                case -19: {
                    name += "Zylon-";
                    break;
                }
                case -18: {
                    name += "Pulse-";
                    break;
                }
                case -17: {
                    name += "Datus-";
                    break;
                }
                case -16: {
                    name += "Reticulum-";
                    break;
                }
                case -15: {
                    name += "Binar-";
                    break;
                }
                case -14: {
                    name += "KausAustralis";
                    break;
                }
                case -13: {
                    name += "Alnath-";
                    break;
                }
                case -12: {
                    name += "Fairy-";
                    break;
                }
                case -11: {
                    name += "Indus-";
                    break;
                }
                case -10: {
                    name += "Alnair-";
                    break;
                }
                case -9: {
                    name += "Alnasi-";
                    break;
                }
                case -8: {
                    name += "Ascella-";
                    break;
                }
                case -7: {
                    name += "KausBorealis-";
                    break;
                }
                case -6: {
                    name += "Unukalhai-";
                    break;
                }
                case -5: {
                    name += "Serpens-";
                    break;
                }
                case -4: {
                    name += "Aldebaran-";
                    break;
                }
                case -3: {
                    name += "OuterRim-";
                    break;
                }
                case -2: {
                    name += "Fomalhaut-";
                    break;
                }
                case -1: {
                    name += "InnerRim-";
                    break;
                }
                case 0: {
                    name += "Sol-";
                    break;
                }
                case 1: {
                    name += "Rigel-";
                    break;
                }
                case 2: {
                    name += "Tureis-";
                    break;
                }
                case 3: {
                    name += "Algedi-";
                    break;
                }
                case 4: {
                    name += "Alderamin-";
                    break;
                }
                case 5: {
                    name += "Caph-";
                    break;
                }
                case 6: {
                    name += "Errai-";
                    break;
                }
                case 7: {
                    name += "Gienah-";
                    break;
                }
                case 8: {
                    name += "Kaitos-";
                    break;
                }
                case 9: {
                    name += "Ruchbah-";
                    break;
                }
                case 10: {
                    name += "Luis-";
                    break;
                }
                case 11: {
                    name += "Miaplacidus-";
                    break;
                }
                case 12: {
                    name += "Gerig-";
                    break;
                }
                case 13: {
                    name += "Merwyn-";
                    break;
                }
                case 14: {
                    name += "Merlin-";
                    break;
                }
                case 15: {
                    name += "Cygnus-";
                    break;
                }
                case 16: {
                    name += "Polus-";
                    break;
                }
                case 17: {
                    name += "Eliesium";
                    break;
                }
                case 18: {
                    name += "Nerieth-";
                    break;
                }
                case 19: {
                    name += "Mytherion-";
                    break;
                }
                case 20: {
                    name += "Thia-";
                    break;
                }
                case 21: {
                    name += "Mytheriad-";
                    break;
                }
                case 22: {
                    name += "Shedir-";
                    break;
                }
                case 23: {
                    name += "Menkar-";
                    break;
                }
                case 24: {
                    name += "Vortisol-";
                    break;
                }
                case 25: {
                    name += "Mortia-";
                    break;
                }
                case 26: {
                    name += "Corpius-";
                    break;
                }
                case 27: {
                    name += "Nos-";
                    break;
                }
                case 28: {
                    name += "NovaNos-";
                    break;
                }
                case 29: {
                    name += "Necronos-";
                    break;
                }
                case 30: {
                    name += "Necrovoid-";
                    break;
                }
                case 31: {
                    name += "Canopus-";
                    break;
                }
                case 32: {
                    name += "Ementhy-";
                    break;
                }
                case 33: {
                    name += "Capricornus-";
                    break;
                }
                case 34: {
                    name += "Dabih-";
                    break;
                }
                case 35: {
                    name += "Holovoid-";
                    break;
                }
                case 36: {
                    name += "Nothnon-";
                    break;
                }
                case 37: {
                    name += "Barian-";
                    break;
                }
                case 38: {
                    name += "Albireo-";
                    break;
                }
                case 39: {
                    name += "Nuthnull-";
                    break;
                }
                case 40: {
                    name += "llerin-";
                    break;
                }
                case 41: {
                    name += "Ryan-";
                    break;
                }
                case 42: {
                    name += "Susa-";
                    break;
                }
                case 43: {
                    name += "Cycone-";
                    break;
                }
                case 44: {
                    name += "LittleCycone-";
                    break;
                }
                case 45: {
                    name += "ilax-";
                    break;
                }
                case 46: {
                    name += "Sul-";
                    break;
                }
                case 47: {
                    name += "Nonvoid-";
                    break;
                }
                case 48: {
                    name += "Antivoid-";
                    break;
                }
                case 49: {
                    name += "Provoid-";
                    break;
                }
                case 50: {
                    name += "FirstStars-";
                    break;
                }
                case 51: {
                    name += "DarkEmpty-";
                    break;
                }
                case 52: {
                    name += "LastStars-";
                    break;
                }
                case 53: {
                    name += "Nothingness-";
                    break;
                }
                case 54: {
                    name += "FrozenVoid-";
                    break;
                }
                case 55: {
                    name += "FallingStars-";
                    break;
                }
                case 56: {
                    name += "FoundStars-";
                    break;
                }
                case 57: {
                    name += "LostStars-";
                    break;
                }
                case 58: {
                    name += "NewStars-";
                    break;
                }
                case 59: {
                    name += "Alfirk-";
                    break;
                }
                case 60: {
                    name += "Cassius-";
                    break;
                }
                case 61: {
                    name += "Cassiopeia-";
                    break;
                }
                case 62: {
                    name += "Flyby-";
                    break;
                }
                case 63: {
                    name += "Avior-";
                    break;
                }
                case 64: {
                    name += "LongStart-";
                    break;
                }
                case 65: {
                    name += "LongTime-";
                    break;
                }
                case 66: {
                    name += "LongNight-";
                    break;
                }
                case 67: {
                    name += "Gotthere-";
                    break;
                }
                case 68: {
                    name += "Aracnid-";
                    break;
                }
                case 69: {
                    name += "Aspidiske-";
                    break;
                }
                case 70: {
                    name += "FarField-";
                    break;
                }
                case 71: {
                    name += "DreamLand-";
                    break;
                }
                case 72: {
                    name += "Cepheus-";
                    break;
                }
                case 73: {
                    name += "Garnet-";
                    break;
                }
                case 74: {
                    name += "Kadath-";
                    break;
                }
                case 75: {
                    name += "Fall-";
                    break;
                }
                case 76: {
                    name += "FallingVoid-";
                    break;
                }
                case 77: {
                    name += "Distant-";
                    break;
                }
                case 78: {
                    name += "ToFar-";
                    break;
                }
                case 79: {
                    name += "Fish-";
                    break;
                }
                case 80: {
                    name += "Ball-";
                    break;
                }
                case 81: {
                    name += "Vista-";
                    break;
                }
                case 82: {
                    name += "Cloud-";
                    break;
                }
                case 83: {
                    name += "Valhala-";
                    break;
                }
                case 84: {
                    name += "CatsPaw-";
                    break;
                }
                case 85: {
                    name += "Halo-";
                    break;
                }
                case 86: {
                    name += "Cocoon-";
                    break;
                }
                case 87: {
                    name += "Veil-";
                    break;
                }
                case 88: {
                    name += "ThumbPrint-";
                    break;
                }
                case 89: {
                    name += "DarkNebula-";
                    break;
                }
                case 90: {
                    name += "Cepheid-";
                    break;
                }
                case 91: {
                    name += "EtaCarina-";
                    break;
                }
                case 92: {
                    name += "NorthAmeriaca-";
                    break;
                }
                case 93: {
                    name += "Puppis-";
                    break;
                }
                case 94: {
                    name += "Naos-";
                    break;
                }
                case 95: {
                    name += "Equuleus-";
                    break;
                }
                case 96: {
                    name += "Delphinus-";
                    break;
                }
                case 97: {
                    name += "Sadr-";
                    break;
                }
                case 98: {
                    name += "Deneb-";
                    break;
                }
                case 99: {
                    name += "Carina-";
                    break;
                }
                //
                case 100: {
                    name += "Spinward-";
                    break;
                }
            }
            if ((nx > 0 || ny > 0 || nz > 0)) {
                name += "_" + nx + "_" + ny + "_" + nz;
            }
        }
        return name;
    }

    public static String CatalogNumber(Core core, Atmosphere atmos) {
        int c = 0;
        c = c + (int) core.oxygen + (int) core.hydrogen + (int) core.helium + (int) core.heavyMetals + (int) core.iron + (int) core.lightMetals + (int) core.carbon + (int) core.calcium;
        int a = 256;
        a = a + ((int) (atmos.oxygen) * 2 + (int) (atmos.hydrogen) * 2 + (int) (atmos.nitrogen) * 2) - ((int) atmos.methane + (int) atmos.sulphur + (int) (atmos.chlorine) * 2 + (int) (atmos.ammonia) * 2);
        return "*C" + c + "A" + a;
    }
}
