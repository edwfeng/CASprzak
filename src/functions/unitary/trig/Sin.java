package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sin extends UnitaryFunction {
	/**
	 * Constructs a new Sin
	 * @param function The function which sin is operating on
	 */
	public Sin(Function function) {
		super(function);
	}

	/**
	 * Returns the sine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sin of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.sin(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Cos(function), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sin(operand);
	}

}
