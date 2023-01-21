/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerwyn Jones
 */
@FunctionalInterface
interface ToFile{
    boolean To(StellarFiles f);
}
@FunctionalInterface
interface FromFile{
    boolean From(StellarFiles f);
}
public class StellarFiles {

    StringBuilder file = new StringBuilder();
    int at;
    int line;
    StringBuilder dataName;
    //
    void Reset() {
        at = 0;
        line = 0;
    }
// <editor-fold defaultstate="collapsed" desc="READ DATA">

    //returns false if EOF
    boolean IncCharacter() {
        at++;
        if (at < file.length()) {
            return true;
        }
        return false;
    }

    //returns false if EOF
    boolean WhileSpace() throws Exception {
        if (at < file.length()) {

            char c = file.charAt(at);
            while (c == '\n' || c == '\r') {
                line++;
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    return false;
                }
            }

            while (c == ' ' || c == '\t' || c == '\b') {
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    return false;
                }

            }

            if (c == '#') {
                while (!(c == '\n' || c == '\r')) {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        return false;
                    }

                }
                line++;
            }
            return true;
        } else {
            return false;
        }
    }

    Integer[] GetNameData() {
        int a = 0;
        char c;
        RingList<Integer> ints = new RingList();
        StringBuilder token = new StringBuilder();
        //System.out.print(dataName.toString());
        for (int i = 0; i < dataName.length(); i++) {
            c = dataName.charAt(i);
            if (Character.isDigit(c)) {
                while (Character.isDigit(c) && i < dataName.length()) {
                    token.append(c);
                    i++;
                    c = dataName.charAt(i);
                }
                ints.Append(Integer.parseInt(token.toString()));
                i--;
                token = new StringBuilder();
            }
        }
        if (ints.Length() == 0) {
            return null;
        } else {
            Integer array[] = new Integer[ints.Length()];
            Object oa[] = ints.toArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = (Integer) oa[i];
                //System.out.println("SIZE "+i+" "+array[i]);
            }
            return array;
        }
    }

    void ReadName() throws Exception {
        char c = file.charAt(at);
        dataName = new StringBuilder();
        dataName.append(c);
        while (c != ':') {
            if (IncCharacter()) {
                c = file.charAt(at);
            } else {
                throw new Exception("EOF Error " + at + " line " + line);
            }
            dataName.append(c);

        }
        //System.out.println(dataName.toString()+" "+at);
        if (IncCharacter()) {
            c = file.charAt(at);
        } else {
            throw new Exception("EOF Error " + at + " line " + line);
        }
    }

    int ReadInt() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            char c = file.charAt(at);
            if (c == '{') {
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (Character.isDigit(c) || c == '-' || c == '+') {
                    in.append(c);
                    while (c != '}') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        if (Character.isDigit(c)) {
                            in.append(c);
                        } else if (c == '}') {
                            at++;
                            break;
                        } else {
                            throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
                        }
                    }
                    if ((c == '\n' || c == '\r')) {
                        line++;
                    } else if (c == '#') {
                        while (c != '\n') {

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                        }
                        at--;
                    }
                } else if (c == '#') {
                    while (!(c == '\n' || c == '\r')) {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadInt();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }

            return Integer.parseInt(in.toString());
        } else {
            throw new Exception("EOF Error " + at + " line " + line);
        }
    }

    double ReadDouble() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            char c = file.charAt(at);
            if (c == '{') {
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (Character.isDigit(c) || c == '-' || c == '+') {
                    in.append(c);
                    while (c != '}' || c == ' ' || c == '\t') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        if (Character.isDigit(c) || c == '.' || c == 'E' || c == 'e') {
                            in.append(c);
                        } else if (c == '}') {
                            at++;
                            break;
                        } else {
                            throw new Exception("File Read Data Error " + at + " line " + line);
                        }
                    }
                    if ((c == '\n' || c == '\r')) {
                        line++;
                    } else if (c == '#') {
                        while (!(c == '\n' || c == '\r')) {

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                        }
                        at--;
                    }
                } else if (c == '#') {
                    while (c != '\n') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadDouble();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }

            return Double.parseDouble(in.toString());
        } else {
            throw new Exception("EOF Error " + at + " line " + line);
        }
    }

    boolean ReadBoolean() throws Exception {
        StringBuilder in = new StringBuilder();
        Boolean res = false;
        if (WhileSpace()) {
                if (!WhileSpace()) {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
            char c = file.charAt(at);
            
            if (c == '{') {
                if (!WhileSpace()) {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (c == 't' || c == 'f') {
                    in.append(c);
                    if (c == 't') {
                        res = true;
                    }
                    while (c != '}') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    if ((c == '\n' || c == '\r')) {
                        line++;
                    } else if (c == '#') {
                        while (!(c == '\n' || c == '\r')) {

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                        }
                        at--;
                    }
                } else if (c == '#') {
                    while (c != '\n') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line+" character "+c);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadBoolean();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }

            return res;
        } else {
            throw new Exception("EOF Error " + at + " line " + line);
        }
    }

    String ReadString() throws Exception {
        StringBuilder in = new StringBuilder();
        Boolean res = false;
        if (WhileSpace()) {
            
                if (!WhileSpace()) {
                return null;
                }
            char c = file.charAt(at);
            if (c == '{') {
                if (!WhileSpace()) {
                return null;
                }
                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (c == ':') {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    while (c != '}') {
                        in.append(c);
                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    if ((c == '\n' || c == '\r')) {
                        line++;
                    } else if (c == '#') {
                        while (!(c == '\n' || c == '\r')) {

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                        }
                        at--;
                    }
                    return in.toString();
                } else if (c == '#') {
                    while (c != '\n') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line+" character "+c);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadString();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }

            return in.toString();
        } else {
            throw new Exception("EOF Error " + at + " line " + line);
        }
    }

    Integer[] ReadIntArray() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            RingList<Integer> a = new RingList();
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (Character.isDigit(c) || c == '-' || c == '+') {
                    in.append(c);

                    while (c != '}') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        if (Character.isDigit(c)) {
                            in.append(c);
                        } else if (c == ',') {
                            Integer i = (Integer.parseInt(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                        } else if (c == '}') {
                            Integer i = (Integer.parseInt(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                            at++;
                            break;
                        } else {
                            throw new Exception("File Read Data Error " + at + " line " + line);
                        }
                    }

                    if ((c == '\n' || c == '\r')) {
                        line++;
                        at++;
                    }
                } else if ((c == '\n' || c == '\r')) {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    line++;
                } else if (c == '#') {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    while (!(c == '\n' || c == '\r')) {
                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }

                    }
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadIntArray();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }
            Integer array[] = new Integer[a.Length()];
            Object oa[] = a.toArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = (Integer) oa[i];
            }
            return array;
        } else {
            return null;
        }
    }

    Boolean[] ReadBooleanArray() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            RingList<Boolean> a = new RingList();
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (Character.isLetter(c) && (c == 't' || c == 'f')) {
                    in.append(c);

                    while (c != '}') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        if (Character.isLetter(c)) {
                            in.append(c);
                        } else if (c == ',') {
                            Boolean i = (Boolean.parseBoolean(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                        } else if (c == '}') {
                            Boolean i = (Boolean.parseBoolean(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                            at++;
                            break;
                        } else {
                            throw new Exception("File Read Data Error " + at + " line " + line);
                        }
                    }

                    if ((c == '\n' || c == '\r')) {
                        line++;
                        at++;
                    }
                } else if ((c == '\n' || c == '\r')) {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    line++;
                } else if (c == '#') {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    while (!(c == '\n' || c == '\r')) {
                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }

                    }
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadBooleanArray();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }
            Boolean array[] = new Boolean[a.Length()];
            Object oa[] = a.toArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = (Boolean) oa[i];
            }
            return array;
        } else {
            return null;
        }
    }

    String[] ReadStringArray() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            RingList<String> a = new RingList();
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (!WhileSpace()) {
                    return null;
                }
                if (c == ':') {
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    if (c != ':' || c != ',') {
                        in.append(c);

                        while (c != '}') {

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                            if (!(c == ':' || c == ','|| c == '}')) {
                                in.append(c);

                            } else if (c == ',') {

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                if (c == ':') {
                                    String i = (in.toString());
                                    in = new StringBuilder();
                                    a.Append(i);
                                } else{

                                    throw new Exception("File Read String Error " + at + " line " + line);
                                }
                            } else if (c == '}') {
                                String i = (in.toString());
                                in = new StringBuilder();
                                a.Append(i);
                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                break;
                            } else {
                                throw new Exception("File Read Data Error " + at + " line " + line);
                            }
                        }

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                        if ((c == '\n' || c == '\r')) {
                            line++;
                            at++;
                        }
                    } else if ((c == '\n' || c == '\r')) {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        line++;
                    } else if (c == '#') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        while (!(c == '\n' || c == '\r')) {
                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }

                        }
                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                    } else {
                        throw new Exception("File Read Data Error " + at + " line " + line);
                    }
                } else {
                    throw new Exception("File Read String Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadStringArray();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }
            String array[] = new String[a.Length()];
            Object oa[] = a.toArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = (String) oa[i];
            }
            return array;
        } else {
            return null;
        }
    }

    Double[] ReadDoubleArray() throws Exception {
        StringBuilder in = new StringBuilder();
        if (WhileSpace()) {
            RingList<Double> a = new RingList();
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (Character.isDigit(c) || c == '-' || c == '+') {
                    in.append(c);

                    while (c != '}') {

                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }
                        if (Character.isDigit(c)|| c == '-' || c == '+' || c == '.' || c == 'E' || c == 'e') {
                            in.append(c);
                        } else if (c == ',') {
                            Double i = (Double.parseDouble(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                        } else if (c == '}') {
                            Double i = (Double.parseDouble(in.toString()));
                            in = new StringBuilder();
                            a.Append(i);
                            at++;
                            break;
                        } else {
                            throw new Exception("File Read Data Error " + at + " line " + line+" character "+c);
                        }
                    }

                    if ((c == '\n' || c == '\r')) {
                        line++;
                        at++;
                    }
                } else if ((c == '\n' || c == '\r')) {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    line++;
                } else if (c == '#') {

                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                    while (!(c == '\n' || c == '\r')) {
                        if (IncCharacter()) {
                            c = file.charAt(at);
                        } else {
                            throw new Exception("EOF Error " + at + " line " + line);
                        }

                    }
                    if (IncCharacter()) {
                        c = file.charAt(at);
                    } else {
                        throw new Exception("EOF Error " + at + " line " + line);
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadDoubleArray();
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
            }
            Double array[] = new Double[a.Length()];
            Object oa[] = a.toArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = (Double) oa[i];
            }
            return array;
        } else {
            return null;
        }
    }

    Integer[][] ReadIntArrayMap(int width, int height) throws Exception {
        StringBuilder in = new StringBuilder();
        RingList<Integer> a = new RingList();
        RingList<Integer[]> b = new RingList();
        if (WhileSpace()) {
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (c == '{') {
                    while (c != '}') {

                        if (!WhileSpace()) {
                            break;//EOF
                        }
                        while (c != '}') {

                            if (!WhileSpace()) {
                                break;//EOF
                            }

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                            if (Character.isDigit(c) || c == '-' || c == '+'|| c == '-' || c == '+') {

                                in.append(c);

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                while (Character.isDigit(c)) {
                                    in.append(c);

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }
                                if (c == ',') {
                                    Integer i = Integer.parseInt(in.toString());
                                    in = new StringBuilder();
                                    a.Append(i);

                                    //System.out.println(i);
                                } else if (c == '}') {
                                    Integer i = Integer.parseInt(in.toString());
                                    in = new StringBuilder();
                                    a.Append(i);
                                    //System.out.println(i);
                                    break;

                                } else {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }
                            } else if ((c == '\n' || c == '\r')) {
                                line++;
                                if (!WhileSpace()) {
                                    break;
                                }

                            } else if (c == '#') {

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                while (!(c == '\n' || c == '\r')) {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }

                            } else {
                                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
                            }
                        }
                        if (c == '}') {

                            if (!WhileSpace()) {
                                break;//EOF
                            }

                            Integer array[] = new Integer[a.Length()];

                            Object oa[] = a.toArray();
                            for (int n = 0; n < oa.length; n++) {
                                array[n] = (Integer) oa[n];
                            }
                            a = new RingList();
                            b.Append(array);

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                            if (c == ',') {

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }

                            } else {
                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                if (c == '}') {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadIntArrayMap(width, height);
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line);
            }
            Integer ab[][] = new Integer[width][height];
            Node<Integer[]> n = b.Start();
            for (int i = 0; i < width && n.data != null; n = n.next, i++) {
                Integer[] ar = n.data;
                for (int j = 0; j < height; j++) {
                    ab[i][j] = ar[j];
                }
            }
            return ab;
        } else {
            return null;
        }
    }

    Double[][] ReadDoubleArrayMap(int width, int height) throws Exception {
        StringBuilder in = new StringBuilder();
        RingList<Double> a = new RingList();
        RingList<Double[]> b = new RingList();
        if (WhileSpace()) {
            if (!WhileSpace()) {
                return null;
            }
            char c = file.charAt(at);
            if (c == '{') {

                if (IncCharacter()) {
                    c = file.charAt(at);
                } else {
                    throw new Exception("EOF Error " + at + " line " + line);
                }
                if (c == '{') {
                    while (c != '}') {

                        if (!WhileSpace()) {
                            break;//EOF
                        }
                        while (c != '}') {

                            if (!WhileSpace()) {
                                break;//EOF
                            }

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                            if (Character.isDigit(c) || c == '-' || c == '+') {

                                in.append(c);

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                while (Character.isDigit(c) || c == '-' || c == '+'|| c == '.' || c == 'e' || c == 'E') {
                                    in.append(c);

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }
                                if (c == ',') {
                                    Double i = Double.parseDouble(in.toString());
                                    in = new StringBuilder();
                                    a.Append(i);

                                    //System.out.println(i);
                                } else if (c == '}') {
                                    Double i = Double.parseDouble(in.toString());
                                    in = new StringBuilder();
                                    a.Append(i);
                                    //System.out.println(i);
                                    break;

                                } else {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }
                            } else if ((c == '\n' || c == '\r')) {
                                line++;
                                if (!WhileSpace()) {
                                    break;
                                }

                            } else if (c == '#') {

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                while (!(c == '\n' || c == '\r')) {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                }
                            } else {
                                throw new Exception("File Read Data Error " + at + " line " + line + " character " + c);
                            }
                        }
                        if (c == '}') {

                            if (!WhileSpace()) {
                                break;//EOF
                            }

                            Double array[] = new Double[a.Length()];

                            Object oa[] = a.toArray();
                            for (int n = 0; n < oa.length; n++) {
                                array[n] = (Double) oa[n];
                            }
                            a = new RingList();
                            b.Append(array);

                            if (IncCharacter()) {
                                c = file.charAt(at);
                            } else {
                                throw new Exception("EOF Error " + at + " line " + line);
                            }
                            if (c == ',') {

                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }

                            } else {
                                if (IncCharacter()) {
                                    c = file.charAt(at);
                                } else {
                                    throw new Exception("EOF Error " + at + " line " + line);
                                }
                                if (c == '}') {

                                    if (IncCharacter()) {
                                        c = file.charAt(at);
                                    } else {
                                        throw new Exception("EOF Error " + at + " line " + line);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    throw new Exception("File Read Data Error " + at + " line " + line);
                }
            } else if (Character.isLetter(c)) {
                ReadName();
                return ReadDoubleArrayMap(width, height);
            } else {
                throw new Exception("File Read Data Error " + at + " line " + line);
            }
            Double ab[][] = new Double[width][height];
            Node<Double[]> n = b.Start();
            for (int i = 0; i < width && n.data != null; n = n.next, i++) {
                Double[] ar = n.data;
                for (int j = 0; j < height; j++) {
                    ab[i][j] = ar[j];
                }
            }
            return ab;
        } else {
            return null;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="ADD DATA">
    void AddName(String i) {
        file.append(i);
        file.append(':');
    }

    void Add(String i) {
        file.append('{');
        file.append(':');
        file.append(i);
        file.append('}');
    }

    void Add(String i[]) {
        file.append('{');
        for (int n = 0; n < i.length; n++) {
            file.append(':');
            file.append(i[n]);
        }
        file.append('}');
    }

    void AddComment(String i) {
        file.append('#');
        file.append(i);
        file.append('\n');
    }

    void Add(byte i) {
        file.append('{');
        file.append(i);
        file.append('}');
        file.append('\n');
    }

    void Add(int i) {
        file.append('{');
        file.append(i);
        file.append('}');
        file.append('\n');
    }

    void Add(double i) {
        file.append('{');
        file.append(i);
        file.append('}');
        file.append('\n');
    }

    void Add(float i) {
        file.append('{');
        file.append(i);
        file.append('}');
        file.append('\n');
    }

    void Add(boolean i) {
        file.append('{');
        file.append(i);
        file.append('}');
        file.append('\n');
    }

    void Add(byte i, byte j) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append('}');
        file.append('\n');
    }

    void Add(int i, int j) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append('}');
        file.append('\n');
    }

    void Add(double i, double j) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append('}');
        file.append('\n');
    }

    void Add(float i, float j) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append('}');
        file.append('\n');
    }

    void Add(boolean i, boolean j) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append('}');
        file.append('\n');
    }

    void Add(byte i, byte j, byte k) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(int i, int j, int k) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(double i, double j, double k) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(float i, float j, float k) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(boolean i, boolean j, boolean k) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(byte i, byte j, byte k, byte l) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append('}');
        file.append('\n');
    }

    void Add(int i, int j, int k, int l) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append(',');
        file.append(l);
        file.append('}');
        file.append('\n');
    }

    void Add(double i, double j, double k, double l) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append(',');
        file.append(l);
        file.append('}');
        file.append('\n');
    }

    void Add(float i, float j, float k, float l) {
        file.append('{');
        file.append(i);

        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append(',');
        file.append(l);
        file.append('}');
        file.append('\n');
    }

    void Add(boolean i, boolean j, boolean k, boolean l) {
        file.append('{');
        file.append(i);
        file.append(',');
        file.append(j);
        file.append(',');
        file.append(k);
        file.append(',');
        file.append(l);
        file.append('}');
        file.append('\n');
    }

    void Add(byte i[]) {
        file.append('{');
        file.append(i[0]);
        for (int n = 1; n < i.length; n++) {
            file.append(',');
            file.append(i[n]);
        }

        file.append('}');
        file.append('\n');
    }

    void Add(int i[]) {
        file.append('{');
        file.append(i[0]);
        for (int n = 1; n < i.length; n++) {
            file.append(',');
            file.append(i[n]);
        }

        file.append('}');
        file.append('\n');
    }

    void Add(double i[]) {
        file.append('{');
        file.append(i[0]);
        for (int n = 1; n < i.length; n++) {
            file.append(',');
            file.append(i[n]);
        }

        file.append('}');
        file.append('\n');
    }

    void Add(float i[]) {
        file.append('{');
        file.append(i[0]);
        for (int n = 1; n < i.length; n++) {
            file.append(',');
            file.append(i[n]);
        }

        file.append('}');
        file.append('\n');
    }

    void Add(boolean i[]) {
        file.append('{');
        file.append(i[0]);
        for (int n = 1; n < i.length; n++) {
            file.append(',');
            file.append(i[n]);
        }
        file.append('}');
        file.append('\n');
    }

    void Add(byte i[][], int w, int h) {
        file.append('{');
        for (int m = 0; m < w; m++) {
            file.append('{');
            file.append(i[m][0]);
            for (int n = 1; n < h; n++) {
                file.append(',');
                file.append(i[m][n]);
            }
            file.append('}');
            if (m < w - 1) {
                file.append(',');
            }
            file.append('\n');
        }
        file.append('}');
        file.append('\n');
    }

    void Add(int i[][], int w, int h) {
        file.append('{');
        for (int m = 0; m < w; m++) {
            file.append('{');
            file.append(i[m][0]);
            for (int n = 1; n < h; n++) {
                file.append(',');
                file.append(i[m][n]);
            }
            file.append('}');
            if (m < w - 1) {
                file.append(',');
            }
            file.append('\n');
        }
        file.append('}');
        file.append('\n');
    }

    void Add(double i[][], int w, int h) {
        file.append('{');
        for (int m = 0; m < w; m++) {
            file.append('{');
            file.append(i[m][0]);
            for (int n = 1; n < h; n++) {
                file.append(',');
                file.append(i[m][n]);
            }
            file.append('}');
            if (m < w - 1) {
                file.append(',');
            }
            file.append('\n');
        }
        file.append('}');
        file.append('\n');
    }

    void Add(float i[][], int w, int h) {
        file.append('{');
        for (int m = 0; m < w; m++) {
            file.append('{');
            file.append(i[m][0]);
            for (int n = 1; n < h; n++) {
                file.append(',');
                file.append(i[m][n]);
            }
            file.append('}');
            if (m < w - 1) {
                file.append(',');
            }
            file.append('\n');
        }
        file.append('}');
        file.append('\n');
    }

    void Add(boolean i[][], int w, int h) {
        file.append('{');
        for (int m = 0; m < w; m++) {
            file.append('{');
            file.append(i[m][0]);
            for (int n = 1; n < h; n++) {
                file.append(',');
                file.append(i[m][n]);
            }
            file.append('}');
            if (m < w - 1) {
                file.append(',');
            }
            file.append('\n');
        }
        file.append('}');
        file.append('\n');
    }
    
// </editor-fold>
    void ClearFile() {
        this.file = new StringBuilder();
    }

    boolean SaveFile(String file) {
        java.io.File f = new java.io.File(file);
        try {
            java.io.FileOutputStream fo = new java.io.FileOutputStream(f);
            String fs = this.file.toString();
            for (int i = 0; i < fs.length(); i++) {
                fo.write(fs.charAt(i));
            }
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StellarFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StellarFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean LoadFile(String file, boolean print) {
        java.io.File f = new java.io.File(file);
        try {
            ClearFile();

            java.io.FileInputStream fo = new java.io.FileInputStream(f);
            if (print) {
                while (fo.available() > 0) {
                    char r = (char) fo.read();
                    this.file.append(r);
                    System.out.print(r);
                }
            } else {
                while (fo.available() > 0) {
                    char r = (char) fo.read();
                    this.file.append(r);
                }

            }
            fo.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StellarFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StellarFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
