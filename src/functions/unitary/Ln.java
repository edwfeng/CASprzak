package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;

import java.util.Map;


public class Ln extends UnitaryFunction {
	/**
	 * Constructs a new Ln
	 * @param function The function which natural log is operating on
	 */
	public Ln(Function function) {
		super(function);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.log(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), function));
	}

	public UnitaryFunction me(Function operand) {
		return new Ln(operand);
	}

}
