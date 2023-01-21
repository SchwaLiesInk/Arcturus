/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

class ListParser extends List {

    static boolean LOG;

    static boolean Delimiter(char c) {
        return (c == '~' || c == ' ' || c == '(' || c == ')' || c == ';');
    }
    protected RingList<Atom> lists[] = new RingList[Script.PRIME + 1];
    Atom used;
    List returned;
    ListParser parent;
    public Error errors;

    public ListParser(String name) {
        super(name);
        errors = new Error(name);
        parent = null;
    }

    ListParser(String name, ListParser p) {
        super(name);
        errors = new Error(name);
        parent = p;
    }

    void SetParent(ListParser p) {
        parent = p;
    }

    boolean mk_atom(String name, boolean self) {
        int code = 0;
        code = Script.hashCode(0, name, name.length(), code);
        int sz = lists[code].Length();
        boolean ok = true;
        if (sz > 0) {
            for (Node<Atom> i = lists[code].Start(); i.data != null; i = i.next) {
                if (Script.StringsEqual(name, i.data.Name())) {
                    ok = false;
                    break;
                }
            }
        } else {
            ok = false;
        }
        if (ok) {
            List at = new List(name);
            if (self) {
                at.Add(at.Reference());
            }
            lists[hash].Append(at);
        }
        return ok;
    }

    void Pass(Reader file, boolean log) {
        LOG = log;
        if (LOG) {
            System.out.println("Program\n");
        }

        int sz = file.Lines();
        int n = 0;
        for (Node<String> i = file.lines.Start(); i.data != null; i = i.next) {
            String line = i.data;
            n++;
            int siz = (line.length());
            if (siz > 0) {

                if (LOG) {
                    System.out.println("Line " + n + " " + line);
                }

                Pass(line, 0, siz);
            }
        }
    }

    boolean Path(Atom item, String str, int code, String line, int at, int len) {

        boolean ok = false;
        int sz = lists[code].Length();
        if (sz > 0) {
            for (Node<Atom> i = lists[code].Start(); i.data != null; i = i.next) {
                item = i.data;
                if (Script.StringsEqual(str, item.Name())) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                if (item instanceof List) {
                    List list = (List) item;
                    returned = list.Call(list, FunctionInput(line, at, len));
                    Add(returned);
                } else {
                    if (item.NoUse()) {
                        used = (Atom) (item.Call(FunctionInput(line, at, len)));
                        Add(used);
                    } else {
                        used = (Atom) (item.Use(FunctionInput(line, at, len)));
                        Add(used);
                    }
                }
            }
        }
        return ok;
    }

    void Pass(String line, int at, int len) {
        char c = line.charAt(at);

        if (at < len) {
            while (c == ' ') {
                at++;
                c = line.charAt(at);
            }
            if (c == '~') {
                if (LOG) {
                    System.out.println("Setting\n");
                }

                Set(line, ++at, len);
            } else if (c == '(') {
                if (LOG) {
                    System.out.println("Function\n");
                }

                Function(line, ++at, len);
            } else if (c == ';') {
                if (LOG) {
                    System.out.println("Comment\n");
                }

                return;
            } else if (c == ')') {
                if (LOG) {
                    System.out.println("Line Error\n " + line);
                }

                errors.Read("End Of Function Before Beginning");
                return;
            } else {
                int code = -1;
                String str = null;
                StringBuilder statement = new StringBuilder();
                Statement(statement, line, at, len);
                code = HashTest(statement, str);

                if (LOG) {
                    System.out.println("Atom Statement\n");
                }

                if (hash >= 0) {
                    boolean ok = false;
                    Atom item = null;
                    if (parent != this && parent != null) {
                        ok = parent.Path(item, str, code, line, at, len);
                    }
                    if (!ok) {
                        ok = Path(item, str, code, line, at, len);
                    }
                } else {
                    errors.Read("Could Not Find Atom In Statement " + line);

                }
            }
            if (at < len) {
                Pass(line, ++at, len);
            }
        }
    }

    int HashTest(StringBuilder test, String convert) {
        int sz = test.length();
        String sent = new String(test);
        int code = 0;
        code = Script.hashCode(0, sent, sz, code);
        convert = sent;
        if (LOG) {
            System.out.println("Hashing " + hash);
        }
        return code;
    }

    void Statement(StringBuilder statement, String line, Integer at, int len) {
        //statement.clear();
        if (LOG) {
            System.out.println("Statement\n");
        }

        char c = line.charAt(at);
        //printf("%s", line);
        //char* pass = new char[len];
        int i = 0;
        while ((!Delimiter(c)) && at < len) {
            //printf("%c", c);
            //pass[i] = c;
            statement.append(c);
            at++;
            i++;
            c = line.charAt(at);
        }
        //pass[i] = '\0';
//if(LOG)System.out.println(" "+pass);

        if (c == ')') {

            if (LOG) {
                System.out.println("Data End In Statement\n");
            }

            at++;
            c = line.charAt(at);
            //
            while (c == ' ') {
                at++;
                c = line.charAt(at);
            }
            if (at < len) {
                //ReadFunction(line, ++at, len);
            }
        }
    }

    void LoadLine(RingList<String> load, String str) {
        load.Append(str);
    }

    int SetTest(StringBuilder statement, String sent, String line, Integer at, int len, Atom item) {

        for (; at < len; at++) {
            char c = line.charAt(at);
            if (!Delimiter(c) && at < len) {
                statement.append(c);
                //printf("%d at %c code %d ",at,c,c);
            } else {
                break;
            }
        }
        int code = HashTest(statement, sent);
        if (code != -1) {
            item = null;
            if (parent != this && parent != null) {
                int sz = parent.lists[code].size();
                if (sz > 0) {
                    for (Node<Atom> i = parent.lists[code].Start(); i.data != null; i = i.next) {
                        item = i.data;
                        if (Script.StringsEqual(sent, item.Name())) {
                            break;
                        }
                    }
                }
            }
            if (item == null) {
                int sz = lists[code].size();
                if (sz > 0) {
                    for (Node<Atom> i = lists[code].Start(); i.data != null; i = i.next) {
                        item = i.data;
                        if (Script.StringsEqual(sent, item.Name())) {
                            break;
                        }
                    }
                }
            }
        }
        return code;
    }

    Atom Set(String line, Integer at, int len) {
        if (at < len) {
            Atom item = null;
            String sent = null;
            StringBuilder statement = new StringBuilder();

            //printf("%s line \n", line);
            int hash = SetTest(statement, sent, line, at, len, item);
            if (item != null) {
                if (LOG) {
                    System.out.println("Set Hashing OK " + hash);
                }

                if (at < len) {
                    ReSet(item, line, ++at, len);
                }
            } else {

                if (LOG) {
                    System.out.println("No Set Hash Found " + hash);
                }

                item = NewAtom(hash, sent, line, ++at, len);
                if (item != null) {
                    if (LOG) {
                        System.out.println("Setting List Data With New Atom " + hash);
                    }

                    lists[hash].Append(item);

                    //if (at<len)
                    //ReSet(item, line, ++at, len);
                }
            }
            return item;

        } else {
            //error
            if (LOG) {
                System.out.println("Setting Error\n");
            }

            errors.Read("Not Enough Data To Process");
        }
        return null;
    }

    void ReSet(Atom item, String line, Integer at, int len) {
        int hash = -1;
        String sent = null;
        StringBuilder statement = new StringBuilder();
        while (line.charAt(at) == ')' || line.charAt(at) == '(' || line.charAt(at) == ' ' && at < len) {
            at++;
        }
        if (at < len) {
            if (line.charAt(at) == '~') {
                if (LOG) {
                    System.out.println("In Set\n");
                }

                Atom add = Set(line, ++at, len);
                if (add != null) {

                    if (LOG) {
                        System.out.println("Adding List To List\n");
                    }

                    item.Add(add);
                }
                return;
            }
            Atom item2 = null;
            statement = new StringBuilder();
            //printf("%s line \n", line);
            int hash2 = SetTest(statement, sent, line, at, len, item2);
            if (item2 != null && hash2 != -1) {

                if (LOG) {
                    System.out.println("Set List Item OK " + hash2);
                }

                item.Add(item2);
                if (at < len) {
                    ReSet(item, line, ++at, len);
                }
            } else if (hash2 != -1) {
                if (LOG) {
                    System.out.println("Setting Item Data " + hash2);
                }

                item.lines.Append(statement.toString());

                if (at < len) {
                    ReSet(item, line, ++at, len);
                }

            } else {

                if (LOG) {
                    System.out.println("Setting Item Error " + hash2 + " " + line + " at " + line.charAt(at));
                }

                errors.Read("Setting Data Error");
            }
        }
    }

    Atom NewAtom(int hash, String name, String line, Integer at, int len) {

        if (LOG) {
            System.out.println("New Atom\n");
        }

        if (at < len) {
            char c = line.charAt(at);
            while (c == ' ' && at < len) {
                at++;
                c = line.charAt(at);
            }
            if (at < len) {
                if (c == '~') {
                    Atom list = Set(line, ++at, len);

                    if (LOG) {
                        System.out.println("Set New Atom List\n");
                    }

                    return list;
                } else if (c == ';') {
                    return null;
                } else if (c == ')' || c == '(') {

                    return NewAtom(hash, name, line, ++at, len);
                    //return Set(line, ++at, len);
                } else {

                    if (LOG) {
                        System.out.println("Adding New Atom " + hash);
                    }

                    List list = new List(name, hash);
                    ReSet(list, line, at, len);

                    return list;
                }
            }

        } else {
            //error
            if (LOG) {
                System.out.println("New Atom Error\n");
            }

            errors.Read("Not Enough Data To Process");
        }
        return null;
    }

    Atom SetAtom(int hash, List item, String line, Integer at, int len) {
        if (at < len) {

            char c = line.charAt(at);
            if (c == '(') {
                if (LOG) {
                    System.out.println("Setting Atom\n");
                }

                return SetAtom(hash, item, line, ++at, len);
            } else if (c == ')') {
                if (LOG) {
                    System.out.println("Setting Atom\n");
                }

                return SetAtom(hash, item, line, ++at, len);
            } else if (c == ' ') {
                if (LOG) {
                    System.out.println("Setting Atom Data\n");
                }

                Atom atom = Set(line, ++at, len);

                return atom;
            } else if (c == ';') {
                return null;
            } else if (c == '~') {
                Atom atom = SetAtom(hash, item, line, ++at, len);
                return atom;

            } else {
                item.Print();
            }
        } else {
            //error
            errors.Read("Not Enough Data To Process");
        }
        return null;
    }

    //
    void Print() {
        System.out.println("Parser Data\n");
        for (int i = 0; i <= Script.PRIME; i++) {
            int sz = lists[i].Length();

            if (sz > 0) {
                for (Node<Atom> j = lists[i].Start(); j.data != null; j = j.next) {

                    System.out.println("hash " + j.data.Hash());
                    j.data.Print();
                    //lists[i][j]->PrintElements();
                }
            }
        }
        PrintStack();
    }

    void PrintStack() {

        System.out.println("Stack Data\n");
        super.PrintElements();
    }

    void Clear() {
        for (int i = 0; i <= Script.PRIME; i++) {
            int sz = lists[i].size();
            for (Node<Atom> j = lists[i].Start(); j.data != null; j = j.next) {
                j.data.Clear();

            }
            lists[i].clear();

        }
    }

    //VIRTUAL ABSTRACT
    void Function(String line, Integer at, int len) {

    }

    List ReadFunction(String name, String line, Integer at, int len) {
        return null;
    }

    List FunctionInput(String line, int at, int len) {
        return null;
    }

    List UseFunction(int hash, List item, String line, Integer at, int len) {
        return null;
    }

    void Key(String line, Integer at, int len) {
    }

    List UseKey(int hash, List item, String line, Integer at, int len) {
        return null;
    }
}

/**
 *
 * @author Gerwyn Jones
 */
public class FunctionParser extends ListParser {

    protected Interpreter inter;

    public
            FunctionParser(String name) {
        super(name);
    }

    void Internal(Interpreter in) {
        inter = in;
    }

    void Function(String line, Integer at, int len) {
        if (at < len) {
            if (LOG) {
                System.out.println("Passing Function Data " + line + " at " + line.charAt(at));
            }

            if (line.charAt(at) == '~' && parent == this) {//only one stack frame to a method
                at++;

                int hash = -1;
                String str = null;
                StringBuilder statement = new StringBuilder();
                Statement(statement, line, at, len);
                hash = HashTest(statement, str);
                if (hash >= 0) {
                    boolean ok = true;

                    if (LOG) {
                        System.out.println("Function Statement\n");
                    }

                    Atom item = null;
                    int sz = lists[hash].size();
                    if (sz > 0) {
                        for (Node<Atom> i = lists[hash].Start(); i.data != null; i = i.next) {
                            item = i.data;
                            if (Script.StringsEqual(str, item.Name())) {
                                ok = false;
                                if (LOG) {
                                    System.out.println("Function Statement Already Defined " + str);
                                }

                                break;
                            }
                        }

                    }
                    if (ok) {
                        Lambda lambda = (Lambda) (ReadFunction(str, line, at, len));
                        lists[hash].Append(lambda);
                    }
                    at++;
                }
            } else {
                int hash = -1;
                String str = null;
                StringBuilder statement = new StringBuilder();
                Statement(statement, line, at, len);
                hash = HashTest(statement, str);
                if (LOG) {
                    System.out.println("Lambda Statement\n");
                }

            }
            //Pass(line, ++at, len);
        }
    }

    void Then(ListFunction func) {
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
        return null;
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

    List ReadFunction(String name, String line, Integer at, int len) {
        Lambda lambda = new Lambda(name, this, new FunctionParser(name));// , new function(&List::Call));
        while (line.charAt(at) == ' ') {
            at++;
        }
        if (LOG) {
            System.out.println("Reading Function\n");
        }

        if (line.charAt(at) == '(') {
            List arg = null;
            GetArguments(name, arg, line, ++at, len);
            if (arg != null) {
                lambda.Add(arg);
            }
            StringBuilder pro = new StringBuilder();
            char c = line.charAt(at);
            while (c != '(') {
                c = line.charAt(at);
                at++;
            }
            int scope = 0;
            while (scope >= 0 && at < len) {
                if (c == '(') {
                    scope++;
                } else if (c == ')') {
                    scope--;
                }
                pro.append(c);
                c = line.charAt(at);
                at++;
            }
            if (scope > 0) {
                //error
                if (LOG) {
                    System.out.println("Function Body Error\n");
                }

                errors.Append("Function Body Error Out Of Scope");
            } else {
                lambda.Append(pro.toString());
            }
        } else {

            if (LOG) {
                System.out.println("Function Statement Error\n");
            }

            errors.Append("Function Statement Error In Arguments");
        }
        return lambda;
    }

    void GetArguments(String name, List list, String line, Integer at, int len) {
        StringBuilder arg = new StringBuilder();
        char c = line.charAt(at);
        do {
            while (!Delimiter(c)) {
                arg.append(c);
                at++;
                c = line.charAt(at);
            }
            if (c == ' ' && arg.length() > 0) {
                String str = arg.toString();
                //
                if (list == null) {

                    list = new List(name);
                    list.Append(str);
                    list.Add(new List(str));

                } else {
                    list.Append(str);
                    list.Add(new List(str));

                }
            } else if (c != ')') {
                //error

                if (LOG) {
                    System.out.println("Function Argument List Error\n");
                }

                errors.Append("Argument List Error In Function");
            }
        } while (c != ')');

    }

    List FunctionInput(String line, Integer at, int len) {
        return null;
    }

    List UseFunction(int hash, List item, String line, Integer at, int len) {
        return null;
    }

    void Key(String line, Integer at, int len) {
    }

    List UseKey(int hash, List item, String line, Integer at, int len) {
        return null;
    }
}
