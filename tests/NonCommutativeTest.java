import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonCommutativeTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x','y');

	@Test
	void basicSubtract() {
		Function test = parserX.parse("1-x+4-3+x+4-1-x");
		assertEquals(2, test.evaluate(3));
	}

	@Test
	void subtractWithMultiply() {
		Function test = parserX.parse("x-2*x+1");
		assertEquals(-2, test.evaluate(3));
	}

	@Test
	void basicNCPolynomial() {
		Function test = parserX.parse("x^2-5*x+4");
//		System.out.println(test);
		assertEquals(-2,test.evaluate(3));
	}

	@Test
	void mediumNCPolynomial() {
		Function test = parserX.parse("x^4-5*x^2+4");
//		System.out.println(test);
		assertEquals(40,test.evaluate(3));
	}

	@Test
	void negativeConstant() {
		Function test = parserX.parse("-3");
		assertEquals(-3, test.evaluate(17));
	}

	@Test
	void leadingNegative() {
		Function test = parserX.parse("-x");
		assertEquals(-3, test.evaluate(3));
	}

	@Test
	void multiVariableDivision() {
		Function test = parserXY.parse("-x/y+-y/3");
		assertEquals(-5.0/3,test.evaluate(2,3), .001);
	}
}
