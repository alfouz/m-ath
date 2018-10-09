package com.alfouz.tfm.tfm.Util;

//Not used
public enum MathElement {
    NUMBER1("1"),
    NUMBER2("2"),
    NUMBER3("3"),
    NUMBER4("4"),
    NUMBER5("5"),
    NUMBER6("6"),
    NUMBER7("7"),
    NUMBER8("8"),
    NUMBER9("9"),
    NUMBER0("0"),
    POINT("."),
    SIGNPLUS("+"),
    SIGNMINUS("-"),
    SIGNMULTIPLY("*"),
    SIGNDIV("/"),
    SIGNEQUAL("="),
    SIGNPARENTON("("),
    SIGNPARENTOFF(")"),
    OPLN("\\ln"),
    OPLOG("\\log"),
    OPEXP("^"),
    OPSIN("\\sin"),
    OPCOS("\\cos"),
    OPTAN("\\tan"),
    OPCOSEC("\\csc"),
    OPSEC("\\sec"),
    OPCOTAN("\\cot"),
    OPARCSIN("\\arcsin"),
    OPARCCOS("\\arccos"),
    OPARCTAN("\\arctan"),
    OPSINH("\\sinh"),
    OPCOSH("\\cosh"),
    OPTANH("\\tanh"),
    SYMBOLPI("\\pi"),
    SYMBOLE("e"),
    SYMBOLX("x"),
    SYMBOLY("y"),
    SYMBOLZ("z"),
    SYMBOLPERCENT("%"),
    OPSQRT("\\sqrt"),
    OPFACT("!");

    private String value;

    private MathElement(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
