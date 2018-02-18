/*

*/

import java.io.*;
import java.util.*;

public class cowbasic {
  static HashMap<String, Integer> variables;
  static Scanner in;
  static PrintWriter out;
  public static void main(String[] args) throws Exception {
    in = new Scanner(new File("cowbasic.in"));
    out = new PrintWriter(new BufferedWriter(new FileWriter("cowbasic.out")));
    String[] programRaw = new String[100];
    int numLines = 0;
    while(in.hasNextLine()) {
      programRaw[numLines++] = in.nextLine().trim();
    }
    variables = new HashMap<String, Integer>();
    ArrayList<Statement> statements = parseLines(programRaw);
    for(Statement s : statements) {
      System.out.println(s);
    }
    for(Statement s : statements) {
      s.run();
    }
    System.out.println(variables);
    out.close();

    // Test parseExpression
    // System.out.println(parseExpression("(a) + ((b) + (2))"));
  }

  static ArrayList<Statement> parseLines(String[] lines) {
    ArrayList<Statement> statementList = new ArrayList<Statement>();
    int lineNumber = 0;
    while(lineNumber < lines.length) {
      if(lines[lineNumber] == null || lines[lineNumber].trim().length() == 0) // empty line
        lineNumber++;
      else if(lines[lineNumber].contains("RETURN")) { // return statement
        statementList.add(parseReturn(lines[lineNumber])); // TODO parseReturn
        lineNumber++;
      } else if(lines[lineNumber].contains("MOO")) { // moo loop
        //System.out.println("loop header:"+lines[lineNumber]);
        int loopEndLine = lineNumber+1;
        int loopDepth = 1;
        while(loopDepth > 0) {
          if(lines[loopEndLine].contains("{")) 
            loopDepth++;
          if(lines[loopEndLine].contains("}"))
            loopDepth--;
          loopEndLine++;
        }
        int numLoopLines = loopEndLine - lineNumber;
        String[] loopLines = new String[numLoopLines];
        for(int i = lineNumber; i < loopEndLine; i++)
          loopLines[i-lineNumber] = lines[i];
       // System.out.println("looplines:"+Arrays.toString(loopLines));
        statementList.add(parseLoop(loopLines)); // TODO parseLoop
        lineNumber += numLoopLines;
      } else { // variable assignment
        statementList.add(parseAssignment(lines[lineNumber]));
        lineNumber++;
      }
    }
    return statementList;
  }

  static abstract class Statement {
    public abstract void run();
  }
  static class Assignment extends Statement {
    String variableName;
    Expression value;
    public void run() {
      variables.put(variableName, value.getValue());
    }
    public Assignment(String name, Expression val) {
      variableName = name;
      value = val;
    }
    public String toString() {
      return "Assignment("+variableName+", "+value+")";
    }
  }
  static class Loop extends Statement {
    ArrayList<Statement> statements;
    int repetitions;
    public void run() {
      for(int i = 0; i < repetitions; i++) {
        for(int j = 0; j < statements.size(); j++) {
          statements.get(j).run();
        }
      }
    } 
    public Loop(int reps, ArrayList<Statement> arr) {
      repetitions = reps;
      statements = arr;
    }
    public String toString() {
      String result = "Loop("+repetitions+", {\n";
      for(Statement s : statements)
        result += s+"\n";
      result+="})";
      return result;
    }
  }
  static class Return extends Statement {
    Expression value;
    public void run() {
      out.println(value.getValue());
      out.close();
    }
    public Return(Expression v) {
      value = v;
    }
    public String toString() {
      return "Return("+value+")";
    }
  }
  public static Assignment parseAssignment(String raw) {
    String[] parts = raw.split("=");
    return new Assignment(parts[0].trim(), parseExpression(parts[1].trim()));
  }
  public static Loop parseLoop(String[] lines) {
    //System.out.println("loop header:"+lines[0]);
    int reps = Integer.parseInt(lines[0].split(" ")[0]);
    int numEnclosedLines = lines.length-2;
    String[] enclosedLines = new String[numEnclosedLines];
    for(int i = 1; i < lines.length-1; i++) {
      enclosedLines[i-1] = lines[i];
    }
    ArrayList<Statement> enclosedStatements = parseLines(enclosedLines);
    return new Loop(reps, enclosedStatements);
  }
  public static Return parseReturn(String raw) {
    raw = raw.trim();
    return new Return(parseExpression(raw.split(" ")[1]));
  }
  static abstract class Expression {
    public abstract int getValue();
  }
  static class Literal extends Expression {
    int value;
    public int getValue() {
      return value;
    }
    public Literal(int a) {
      value = a;
    }
    public String toString() {
      return "Literal("+value+")";
    }
  }
  static class Variable extends Expression {
    String name;
    public int getValue() {
      return variables.get(name);
    }
    public Variable(String s) {
      name = s;
    }
    public String toString() {
      return "Variable("+name+")";
    }
  }
  static class Sum extends Expression {
    Expression firstTerm;
    Expression secondTerm;
    public int getValue() {
      return (firstTerm.getValue() + secondTerm.getValue()) % 1000000007;
    }
    public Sum(Expression a, Expression b) {
      firstTerm = a;
      secondTerm = b;
    }
    public String toString() {
      return "Sum("+firstTerm.toString()+", "+secondTerm.toString()+")";
    }
  }
  public static Expression parseExpression(String raw) {
   // System.out.println("parsing expression: ["+raw+"]");
    raw = raw.trim();
    if(raw.contains("+")) // plus sign means it's a sum
      return parseSum(raw);
    else if(raw.charAt(0) < 58) // ascii digit means it's a literal
      return parseLiteral(raw);
    else // it's a variable
      return parseVariable(raw);
  }
  public static Literal parseLiteral(String raw) {
    return new Literal(Integer.parseInt(raw.trim()));
  }
  public static Variable parseVariable(String raw) {
    return new Variable(raw.trim());
  }
  public static Sum parseSum(String raw) {
    raw = raw.trim();
    int splitIndex = 1;
    int parenDepth = 1;
    while(parenDepth > 0 || raw.charAt(splitIndex) != '+') {
      if(raw.charAt(splitIndex) == '(') {
        parenDepth++;
      }
      else if(raw.charAt(splitIndex) == ')'){
        parenDepth--;
      }
      splitIndex++;
    }
    String[] rawTerms = new String[]{raw.substring(0, splitIndex).trim(), raw.substring(splitIndex+1).trim()};
    for(int i = 0; i < 2; i++) {
      rawTerms[i] = rawTerms[i].trim().substring(1, rawTerms[i].trim().length()-1);
    }
    return new Sum(parseExpression(rawTerms[0]), parseExpression(rawTerms[1]));
  }
}
