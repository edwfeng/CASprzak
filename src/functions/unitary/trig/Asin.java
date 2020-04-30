package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Asin extends UnitaryFunction {
	/**
	 * Constructs a new Asin
	 * @param function The function which arcsin is operating on
	 */
	public Asin(Function function) {
		super(function);
	}

	/**
	 * Returns the inverse sine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sin of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.asin(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Sum(new Constant(1), new Product(new Constant(-1), new Pow(new Constant(2), function))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Asin(operand);
	}

}
