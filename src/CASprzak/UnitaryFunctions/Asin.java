package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Asin extends UnitaryFunction {
    public Asin(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Add( new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function))))));
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.asin(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Asin(function.clone());
    }

    public UnitaryFunction simplifyInternal() {
        return new Asin(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
