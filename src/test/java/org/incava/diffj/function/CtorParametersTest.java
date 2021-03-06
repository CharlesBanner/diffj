package org.incava.diffj.function;

import org.incava.diffj.util.Lines;

public class CtorParametersTest extends FunctionTestCase {
    public CtorParametersTest(String name) {
        super(name);
    }

    private Lines testCode(String params) {
        return lines("class Test {",
                     "    Test(" + params + ") {}",
                     "}");
    }
    
    public void testParameterAddedNoneToOne() {
        evaluate(testCode(""),
                 testCode("Integer i"),

                 paramAdded("i", locrg(2, 9, 10), locrg(2, 18, 18)));
    }

    public void testParameterAddedOneToTwo() {
        evaluate(testCode("String s"),
                 testCode("String s, Integer i"),
                 
                 paramAdded("i", locrg(2, 9, 18), locrg(2, 20, 28)));
    }

    public void testParameterAddedOneToThree() {
        evaluate(testCode("String s"),
                 testCode("List[] ary, String s, Integer i"),
                 
                 paramAdded("ary",         locrg(2, 9, 18),  locrg(2, 10, 19)),
                 paramAdded("i",           locrg(2, 9, 18),  locrg(2, 32, 40)),
                 paramReordered("s", 0, 1, locrg(2, 17, 17), locrg(2, 29, 29)));
    }

    public void testParameterRemovedOneToNone() {
        evaluate(testCode("Integer i[][][][]"),
                 testCode(""),
                 
                 paramRemoved("i", locrg(2, 18, 18), locrg(2, 9, 10)));
    }

    public void testParameterRemovedTwoToOne() {
        evaluate(testCode("String s, Integer i"),
                 testCode("String s"),
                 
                 paramRemoved("i", locrg(2, 20, 28), locrg(2, 9, 18)));
    }

    public void testParameterRemovedThreeToOne() {
        evaluate(testCode("List[] ary, String s, Integer i"),
                 testCode("String s"),
                 
                 paramRemoved("ary",       locrg(2, 10, 19), locrg(2, 9, 18)),
                 paramReordered("s", 1, 0, locrg(2, 29, 29), locrg(2, 17, 17)),
                 paramRemoved("i",         locrg(2, 32, 40), locrg(2, 9, 18)));
    }

    public void testParameterChangedType() {
        evaluate(testCode("int i"),
                 testCode("Integer i"),
                 
                 paramTypeChanged("int", "Integer", locrg(2, 10, 14), locrg(2, 10, 18)));
    }

    public void testParameterChangedName() {
        evaluate(testCode("int i"),
                 testCode("int x"),

                 paramNameChanged("i", "x", locrg(2, 14, 14), locrg(2, 14, 14)));
    }

    public void testParameterReordered() {
        evaluate(testCode("int i, double d"),
                 testCode("double d, int i"),
                 
                 paramReordered("i", 0, 1, locrg(2, 14, 14), locrg(2, 24, 24)),
                 paramReordered("d", 1, 0, locrg(2, 24, 24), locrg(2, 17, 17)));
    }

    public void testParameterReorderedAndRenamed() {
        evaluate(testCode("int i, double d"),
                 testCode("double dbl, int i2"),
                 
                 paramReorderedAndRenamed("i", 0, 1, "i2",  locrg(2, 14, 14), locrg(2, 26, 27)),
                 paramReorderedAndRenamed("d", 1, 0, "dbl", locrg(2, 24, 24), locrg(2, 17, 19)));
    }

    public void testParameterOneAddedOneReordered() {
        evaluate(testCode("int i"),
                 testCode("int i2, int i"),
                 
                 paramAdded("i2",          locrg(2, 9, 15),  locrg(2, 10, 15)),
                 paramReordered("i", 0, 1, locrg(2, 14, 14), locrg(2, 22, 22)));
    }
}
