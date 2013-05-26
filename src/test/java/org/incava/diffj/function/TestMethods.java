package org.incava.diffj.function;

import org.incava.diffj.ItemsTest;
import org.incava.diffj.Lines;

public class TestMethods extends ItemsTest {
    public TestMethods(String name) {
        super(name);
    }

    public void testClassAllMethodsAdded() {
        evaluate(new Lines("class Test {",
                           "",
                           "}"),

                 new Lines("class Test {",
                           "",
                           "    void foo() {}",
                           "}"),
                 
                 makeMethodRef(null, "foo()", locrg(1, 1, 3, 1), locrg(3, 5, 17)));
    }

    public void testClassOneMethodAdded() {
        evaluate(new Lines("class Test {",
                           "    int bar() { return -1; }",
                           "",
                           "}"),

                 new Lines("class Test {",
                           "",
                           "    int bar() { return -1; }",
                           "    void foo() {}",
                           "}"),
                 
                 makeMethodRef(null, "foo()", locrg(1, 1, 4, 1), locrg(4, 5, 17)));
    }

    public void testClassAllMethodsRemoved() {
        evaluate(new Lines("class Test {",
                           "",
                           "    void foo() {}",
                           "}"),

                 new Lines("class Test {",
                           "",
                           "}"),
                 
                 makeMethodRef("foo()", null, locrg(3, 5, 17), locrg(1, 1, 3, 1)));
    }

    public void testClassNoMethodsChanged() {
        evaluate(new Lines("class Test {",
                           "    void foo() {}",
                           "    int bar() { return -1; }",
                           "",
                           "}"),

                 new Lines("class Test {",
                           "",
                           "    int bar() { ",
                           "        return -1;",
                           "    }",
                           "",
                           "    void foo() {}",
                           "}"),

                 NO_CHANGES);
    }

    // @todo make assumption of single line, a la TestFieldDiff

    // public void testClassMethodAccessRemoved() {
    //     evaluate(new Lines("class Test {",
    //              "",
    //              "    public void foo() {}",
    //              "}"),
    //              new Lines("class Test {",
    //              "",
    //              "    void foo() {}",
    //              "}"),
    //              
    //              makeMethodRef("foo()", null, loc(3, 5), loc(3, 17), loc(1, 1), loc(3, 1)));
    // }
}
