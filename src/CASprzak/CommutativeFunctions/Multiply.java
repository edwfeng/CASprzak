package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.BinaryFunctions.Pow;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.SpecialFunctions.Variable;

import java.util.Arrays;

public class Multiply extends CommutativeFunction{
	public Multiply(Function... functions) {
		super(functions);
		identityValue = 1;
	}

	public double evaluate(double... variableValues) {
		double accumulator = identityValue;
		for (Function f : functions)
			accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	/**
	 * Returns a String representation of the Function
	 * @return String representation of the Function
	 */
	public String toString() {
			if (functions.length < 1)
				return "(empty product)";
			StringBuilder temp = new StringBuilder("(");
			for (int i = 0; i < functions.length - 1; i++) {
				temp.append(functions[i].toString());
				temp.append(" * ");
			}
			temp.append(functions[functions.length-1].toString());
			temp.append(")");
			return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			Function[] toMultiply = new Function[functions.length];
			for (int j = 0; j < functions.length; j++) {
				toMultiply = Arrays.copyOf(functions, functions.length);
				toMultiply[i] = functions[i].getSimplifiedDerivative(varID);
			}
			toAdd[i] = new Multiply(toMultiply);
		}

		return new Add(toAdd);
	}

	public Multiply clone() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++)
			toMultiply[i] = functions[i].clone();
		return new Multiply(toMultiply);
	}


	public Function simplify() {
		Multiply init = simplifyInternal();
		if (init.isTimesZero())
			return new Constant((0));
		else
			return init.simplifyOneElement();
	}

	public Multiply simplifyInternal() {
		Multiply current = (Multiply) super.simplifyInternal();
		current = current.addExponents();
		return current;
	}

	@Override
	public Multiply simplifyElements() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Multiply(toMultiply);
	}

	@Override
	public Multiply simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).constant * ((Constant) functions[j]).constant);
					Function[] toMultiply = ArrLib.deepClone(functions);
					toMultiply[i] = c;
					toMultiply = ArrLib.removeFunctionAt(toMultiply, j);
					return (new Multiply(toMultiply)).simplifyConstants();
				}
			}
		}
		return this;
	}

	public boolean isTimesZero() {
		for (Function function : functions) {
			if (function instanceof Constant) {
				if (((Constant) function).constant == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * If functions contains two of the same variable times each other such as x*x^3 then the exponents will be added and the terms will be combined into one element of functions
	 * @return A new {@link Multiply} with all variable combined with added exponents
	 */
	public Multiply addExponents() {
		Function[] simplifiedTerms = ArrLib.deepClone(functions);
		for (int a = 0; a < simplifiedTerms.length; a++) {
			if (simplifiedTerms[a] instanceof Variable)
				simplifiedTerms[a] = new Pow(new Constant(1), simplifiedTerms[a]);
		}
		for (int i = 1; i < simplifiedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (simplifiedTerms[i] instanceof Pow && simplifiedTerms[j] instanceof Pow) {
					if (((Pow) simplifiedTerms[i]).getFunction2().equals(((Pow) simplifiedTerms[j]).getFunction2())) {
						Pow combinedExponents = new Pow(new Add(((Pow) simplifiedTerms[i]).getFunction1(), ((Pow) simplifiedTerms[j]).getFunction1()), ((Pow) simplifiedTerms[i]).getFunction2());
						simplifiedTerms[j] = combinedExponents;
						simplifiedTerms = ArrLib.removeFunctionAt(simplifiedTerms, i);
						return (new Multiply(simplifiedTerms)).simplifyInternal();
					}
				}
			}
		}
		return clone();
	}

	public CommutativeFunction me(Function... functions) {
		return new Multiply(functions);
	}

	public Function distributeAll() { //TODO this doesn't distribute completely
		Function[] multiplyTerms = getFunctions();
		Function[] addTerms;
		for (int i = 0; i < multiplyTerms.length; i++) {
			if (multiplyTerms[i] instanceof Add) {
				addTerms = ((Add) multiplyTerms[i]).getFunctions();
				multiplyTerms = ArrLib.removeFunctionAt(multiplyTerms, i);
				return new Add(ArrLib.distribute(multiplyTerms, addTerms)).simplify();
			}
		}
		return clone();
	}
}
