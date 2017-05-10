package bg.nbu.java;

public enum BinaryOperation {
	SUM("SUM", "+"), 
	SUBTRACTION("SUBTRACTION", "-"),
	MULTIPLICATION("DIVISION", "*"), 
	DIVISION("DIVISION", "÷"),
	MODULUS_DIVISION("MODULUS DIVISION", "%");

	public final String toolTip;
	public final String symbol;

	private BinaryOperation(String name, String symbol) {
		this.toolTip = name;
		this.symbol = symbol;
	}

}