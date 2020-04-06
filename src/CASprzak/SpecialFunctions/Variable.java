package CASprzak.SpecialFunctions;

import CASprzak.Function;
public class Variable extends Function {
	protected final char[] varNames;

	private final int varID;

	public Variable(int varID, char[] varNames) {
		this.varID = varID;
		this.varNames = varNames;
	}

	public String toString() {
		return "" + varNames[varID];
	}


	public Function getDerivative(int varID) {
		return new Constant((this.varID == varID ? 1 : 0));
	}

	public double evaluate(double... variableValues) {
		return variableValues[varID];
	}

	public Function clone() {
		return new Variable(varID, varNames);
	}

	public Function simplify() {
		return clone();
	}


	public boolean equals(Function that) {
		return (that instanceof Variable) && (varID == ((Variable)that).varID);
	}

	public int compareSelf( Function that) {
		return this.varID - ((Variable)that).varID;
	}
}
