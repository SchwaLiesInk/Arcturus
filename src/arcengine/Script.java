/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

abstract class Element extends Node<String> {

    protected RingList<String> lines = new RingList<>();
    int references = 1;
    String name;

    public
            RingList<String> Data() {
        return lines;
    }

    boolean Dereference() {
        references--;
        return references <= 0;
    }

    void Append(String str) {
        String copy = new String(str);
        lines.Append(copy);
    }

    int Length() {
        return lines.Length();
    }

    String Pop() {
        if (lines.Length() > 0) {
            String str = lines.Last();
            lines.End().Remove();
            return str;
        } else {
            return "";
        }
    }

    boolean Contains(String str) {
        if (str != null) {

            for (Node<String> i = lines.Start(); i.data != null; i = i.next) {
                if (Script.StringsEqual(str, i.data)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Compare(Element e) {
        if (e != null) {
            if (e.lines.Length() == lines.Length()) {
                int sz = e.lines.Length();

                boolean ok = Script.StringsEqual(e.lines.First(), lines.First());
                for (int i = 1; i < sz; i++) {
                    if (!ok) {
                        return false;
                    }
                    ok = Script.StringsEqual(e.lines.Next(), lines.Next());
                }
                return true;
            }
        }
        return false;
    }

    void ConsumeDataLine(RingList<String> data) {

        for (Node<String> i = data.Start(); i.data != null; i = i.next) {
            String cpy = new String(i.data);
            lines.Append(cpy);
        }
        data.Clear();
    }

    void ReadDataLine(RingList<String> data) {
        for (Node<String> i = data.Start(); i.data != null; i = i.next) {
            String cpy = new String(i.data);
            lines.Append(cpy);
        }
    }

    void Consume(RingList<String> line) {
        lines.PasteAll(line);
    }

    void ReadLine(RingList<String> line) {
        lines.Append(line);
    }

    boolean Read(String data) {
        lines.Append(data);
        return true;
    }

    String Write(int i) {
        return lines.Get(i);
    }

    int Lines() {
        return lines.Length();
    }

    void Clear() {
        lines.Clear();
    }

    String Name() {
        return name;
    }

    void Print() {

        int i = 0;
        for (Node<String> str = lines.Start(); str.data != null; str = str.next) {
            System.out.println(i + " " + str.data);
            i++;
        }
    }

    public abstract boolean Add(Atom atm);

    public abstract void SetUse(Atomized au);

    public abstract Atom Call(Atom xyz);

    public abstract Atom Run(Atom xyz);

    public abstract Atom Use(Atom element);
}

@FunctionalInterface
interface Atomized {

    public abstract Atom Use(Atom a, Atom b);
}

class Atom extends Element implements Atomized {

    protected int hash;
    Atomized user;

    public
            Atom() {
        name = "nil";
        hash = Script.hashCode(0, name, name.length(), hash);
        user = null;

    }

    public Atom(String name) {
        this.name = name;
        hash = Script.hashCode(0, name, name.length(), hash);
        user = null;

    }

    public Atom(String name, int code) {

        this.name = name;
        hash = code;
        user = null;

    }

    public Atom(String name, Atomized aUse) {

        this.name = name;
        hash = Script.hashCode(0, name, name.length(), hash);
        user = aUse;
    }

    public Atom(String name, int code, Atomized aUse) {

        this.name = name;
        hash = code;
        user = aUse;
    }

    public int NameLength() {
        return name.length();
    }

    Atom Reference() {
        references++;
        return this;
    }

    public void SetName(String name) {
        this.name = name;
        hash = Script.hashCode(0, name, name.length(), hash);
    }

    public boolean Add(Atom set) {
        lines.Append(set.Name());
        return true;
    }

    public void PrintElements() {
        Print();
    }

    public int Hash() {
        return hash;
    }

    public String Name() {
        return name;
    }

    public boolean NoUse() {
        return user == null;
    }

    public void SetUse(Atomized au) {
        user = au;
    }

    public Atom Call(Atom xyz) {
        Print();
        return this;
    }

    public Atom Run(Atom xyz) {
        Print();
        return this;
    }

    public Atom Use(Atom element) {
        if (user != null) {
            return user.Use(this, element);
        }
        return this;
    }

    @Override
    public Atom Use(Atom subject, Atom element) {
        if (user != null) {
            return user.Use(subject, element);
        }
        return this;
    }

};

@FunctionalInterface
interface ListFunction {

    public abstract List function(List self, List program, List arguments);

};

interface Listed {

    public abstract List Make(List program);

    public abstract List[] Current(List program);

    public abstract List StackCall(List program, List input);

    public abstract List ReCall(List program, List input);

    public abstract List Call(List program, List input);

    public abstract List[] Run(List program, List input);
}

class List extends Atom implements Listed {

    protected RingList<Atom> elements = new RingList<>();

    public
            List(String name, List list) {
        super(name);
        elements.Append(list.elements);
    }

    List(String name) {
        super(name);
    }

    List(String name, RingList<Atom> e) {
        super(name);
        elements.Append(e);
    }

    List(String name, int hCode) {

        super(name, hCode);
    }

    int Size() {
        return elements.Length();
    }

    Atom Get(int i) {
        //up to caller to reference
        if (i < elements.size()) {
            return elements.Get(i);
        }
        return this;
    }

    Atom PopElement() {
        if (elements.size() > 0) {
            Atom at = elements.Last();
            elements.End().Remove();
            //up to caller to reference
            return at;
        } else {
            return this;
        }
    }

    boolean Contains(String e) {
        for (Node<Atom> elm = elements.Start(); elm.data != null; elm = elm.next) {
            if (elm.data.Contains(e)) {
                return true;
            }
        }
        return super.Contains(e);
    }

    boolean Contains(Element e) {
        for (Node<Atom> elm = elements.Start(); elm.data != null; elm = elm.next) {
            if (elm.data == e) {
                return true;
            }
        }
        return false;
    }

    int Contains(List list) {
        int k = 0;
        for (Node<Atom> i = elements.Start(); i.data != null; i = i.next) {
            boolean ok = false;
            for (Node<Atom> j = list.elements.Start(); j.data != null; j = j.next) {
                if (i.data == j.data) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                k++;
            }
        }
        return k;
    }

    void Clear() {
        super.Clear();
        elements.Clear();
    }

    boolean Set(List set) {

        if (set != null) {
            Clear();
            elements.Append(set);
            return true;
        }
        return false;
    }

    @Override
    public boolean Add(Atom set) {
        if (set != null) {
            elements.Append(set);
            return true;
        }
        return false;
    }

    boolean Add(List set) {
        if (set != null) {
            elements.Append(set);
            return true;
        }
        return false;
    }

    @Override
    void Print() {
        int sz = lines.size();
        System.out.println(Name() + " " + sz);
        int n = 0;
        for (Node<String> i = lines.Start(); i.data != null; i = i.next) {
            System.out.println("L" + n + " " + i.data);
            n++;
        }
        PrintElements();
    }

    @Override
    public void PrintElements() {

        int n = 0;
        for (Node<Atom> i = elements.Start(); i.data != null; i = i.next) {
            System.out.println("E" + n + " " + i.data);
            n++;
        }
    }

    void SetFunction(ListFunction func) {
    }

    void AddFunction(ListFunction func) {
    }

    boolean Insert(int i, ListFunction func) {
        return false;
    }

    @Override
    public List Make(List program) {
        List list = new List(this.name + program.name);
        list.Add(this);
        list.Add(program);
        return list;
    }

    @Override
    public List[] Current(List program) {
        List list[] = new List[this.elements.Length() + program.elements.Length()];
        int i = 0;
        for (Node<Atom> n = this.elements.Start(); n.data != null; n = n.next) {
            if (n.data instanceof List) {
                list[i] = (List) n.data;
                i++;
            }
        }
        for (Node<Atom> n = program.elements.Start(); n.data != null; n = n.next) {
            if (n.data instanceof List) {
                list[i] = (List) n.data;
                i++;
            }

        }
        return list;
    }

    @Override
    public List StackCall(List program, List input) {
        return null;
    }

    @Override
    public List ReCall(List program, List input) {
        return null;
    }

    @Override
    public List Call(List program, List input) {
        return null;
    }

    @Override
    public List[] Run(List program, List input) {
        return null;
    }
};

class Lambda extends List {

    protected RingList<ListFunction> f = new RingList<>();
    List current;
    List currentInput;
    int threadNumber = 0;
    ListParser parser;
    ListParser stackFrame;

    public Lambda(String name) {
        super(name);
    }

    Lambda(String name, ListParser pass, ListParser frame, ListFunction func) {
        super(name);
        f.Append(func);
        stackFrame = frame;
        stackFrame.SetParent(pass);
        pass.Reference();
        parser = pass;

    }

    Lambda(String name, ListParser pass, ListParser frame) {
        super(name);
        stackFrame = frame;
        stackFrame.SetParent(pass);
        pass.Reference();
        parser = pass;
    }

    Lambda(String name, int code, ListParser pass, ListParser frame, ListFunction func) {
        super(name, code);
        f.Append(func);
        pass.Reference();
        parser = pass;
        stackFrame = frame;
        stackFrame.SetParent(pass);

    }

    void SetFunction(ListFunction func) {
        f.Clear();
        f.Append(func);
    }

    void AddFunction(ListFunction func) {
        f.Append(func);
    }

    boolean Insert(int i, ListFunction func) {
        f.Set(i);
        f.Insert(func);
        f.First();
        return false;
    }

    @Override
    public List Make(List program) {
        return null;
    }

    @Override
    public List[] Current(List program) {
        if (program != null) {
            ReadDataLine(program.Data());
        }
        return new List[]{current};
    }

    @Override
    public List StackCall(List program, List input) {
        String str = input.Pop();
        List output = new List(str);
        for (Node<String> i = program.Data().Start(); i.data != null; i = i.next) {
            Integer at = 0;
            String line = i.data;
            List core = parser.UseFunction(hash, input, line, at, line.length());
            output.Add(core);
            core.Dereference();
        }
        return output;
    }

    @Override
    public List ReCall(List program, List input) {
        List output = new List(program.Data().First(), input);
        for (Node<String> i = program.Data().Start(); i.data != null; i = i.next) {
            Integer at = 0;
            String line = i.data;
            List ret = parser.UseFunction(hash, output, line, at, line.length());
            output.Dereference();
            output = (List) (ret.Reference());
        }
        return output;
    }

    @Override
    public List Call(List program, List input) {
        current = program;
        currentInput = input;
        for (Node<ListFunction> i = f.Start(); i.data != null; i = i.next) {
            current = i.data.function(this, current, currentInput);
        }
        return current;
    }

    @Override
    public List[] Run(List program, List input) {
        current = program;
        currentInput = input;
        boolean ok = true;
        //try to run in a thread
        if (!ok) {
            current = Call(program, input);
        }
        return new List[]{current};
    }
}

class Reader extends List {

    java.io.FileReader reading;
    Error errors;

    Reader(String fileName) {
        super(fileName);
    }

    void Print() {

    }

    void Clear() {

    }

    String ReadFileLine() {
        if (reading != null) {
            StringBuilder line = new StringBuilder();
            try {
                int scope = 0;
                boolean start = true;
                int lineNumber = 0;
                int read = 0;
                int last = 0;
                while (read != -1) {

                    last = read;
                    read = reading.read();

                    if (read != -1) {
                        if (read == ';') {
                            while (!(read == '\n' || read == '\r')) {

                                last = read;
                                read = reading.read();
                                if (read == -1) {
                                    return (line.toString());
                                }
                            }
                            if (last != ' ') {
                                line.append(' ');
                            }
                            lineNumber++;
                            read = reading.read();
                        }
                        if (read == '\n' || read == '\r') {
                            if (last != ' ') {
                                line.append(' ');
                            }
                            lineNumber++;
                        } else if (read == '\t') {
                            if (!start) {
                                if (last != ' ') {
                                    line.append(' ');
                                }

                            }
                        } else if (read == '(') {
                            scope++;
                            line.append(read);
                            start = false;
                        } else if (!start && read == ')' && scope > 0) {
                            line.append(read);
                            scope--;
                        } else {

                            line.append(read);
                        }
                        if (start && (read == ')')) {
                            errors.lines.Append("Program Out Of Scope by %d at Line " + scope + " " + lineNumber);
                            scope = 0;
                            start = true;
                            return (line.toString());

                            //error
                        } else if (!start && scope == 0) {
                            start = true;
                            return (line.toString());
                        } else {
                            if (scope < 0) {
                                scope = 0;
                                errors.lines.Append("Program Out Of Scope by %d at Line " + scope + " " + lineNumber);
                            }
                        }
                    } else {
                        return (line.toString());
                    }
                }
            } catch (IOException ioex) {
                errors.Append("File IO Error in " + name + " at " + line.toString());
            }
        }
        return null;
    }

    boolean Read(Error errors) {
        File file = new File(name);
        this.errors = errors;
        try {
            reading = new java.io.FileReader(file);

        } catch (FileNotFoundException ex) {
            errors.Append("File Not Found Error in " + name);
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String read = ReadFileLine();
        while (read != null) {
            Read(read);
            read = ReadFileLine();
        }
        return (errors.Length() == 0);
    }

    boolean Read(String str) {
        lines.Append(str);
        return false;
    }
}

class Error extends List {

    Error(String name) {
        super(name);
    }

    int Length() {
        return 0;
    }

    boolean Read(Error errors) {
        lines.Append(errors);
        return false;
    }

    void Print() {
        int n = 0;
        for (Node<String> i = lines.Start(); i.data != null; i = i.next) {
            System.out.println("Error " + n + " " + i.data);
            n++;
        }
    }
}

class Interpreter {

    Interpreter(String name, FunctionParser parser, Reader reader) {

    }

    void Pass(Reader code) {
    }
}

/**
 *
 * @author Gerwyn Jones
 */
public class Script {

    public static final int PRIME = 1023;

    public static int hashCode(int a, String structName, int len, int hash) {
        a = 0;

        for (; a < len; a++) {
            hash += (hash + ((int) (structName.charAt(a)) << 8) + structName.charAt(a));
        }
        hash = 1 + Math.abs(hash) % PRIME;
        return hash;
    }

    public static boolean StringsEqual(String str1, String str2) {

        int len1 = (str1).length();
        int len2 = (str2).length();
        if (len1 == len2) {
            int at = 0;
            char c1;
            char c2;
            for (; at < len1; at++) {
                c1 = str1.charAt(at);
                c2 = str2.charAt(at);
                if (c1 != c2) {
                    return false;
                }
            };
            return true;
        }
        return false;
    }

    public static boolean HashsEqual(String str1, int hash1, String str2, int hash2) {
        if (hash1 == hash2) {
            return StringsEqual(str1, str2);
        }
        return false;
    }

    public static boolean HashCollision(String str1, int hash1, String str2, int hash2) {
        if (hash1 == hash2) {
            return !StringsEqual(str1, str2);
        }
        return false;
    }

    public static int ObjectFileToken(char tok[][], String str, int offset) {

        //TOKENS
        int t = 0;
        int tl = 0;
        int sl = str.length();
        int c = offset;
        for (; c < sl;) {
            if (Character.isDigit(str.charAt(c)) || str.charAt(c) == '.' || str.charAt(c) == '-') {
                tok[t][tl] = str.charAt(c);
                tl++;
                c++;
            } else {
                if (tl > 0) {
                    tok[t][tl] = '\0';
                    tl = 0;
                    t++;
                }
                while (!(Character.isDigit(str.charAt(c)) || str.charAt(c) == '-' || str.charAt(c) == '.')) {
                    c++;
                }
                if (t == 9) {
                    return t;
                }
            }
        }
        tok[t][tl] = '\0';
        t++;
        return t;
    }

    public void ReadScript(String file) {
        Reader reader = new Reader(file);

        Error fileErrors = new Error("fileErrors");
        if (reader.Read(fileErrors)) {
            if (fileErrors.Length() > 0) {
                fileErrors.Print();
            } else {
                System.out.println("Opening File " + file);
                reader.Print();
                FunctionParser parser = new FunctionParser("Vector.");
                Interpreter inter = new Interpreter("Hello", parser, reader);
                parser.Internal(inter);
                inter.Pass(reader);
                reader.Clear();
            }
        } else {
            System.out.println("Could Not Open File");
        }

    }
}
