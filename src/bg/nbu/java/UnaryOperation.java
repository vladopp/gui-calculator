package bg.nbu.java;

public enum UnaryOperation {
	SQUARE_ROOT("SQUARE ROOT", "√"),
	FACTORIAL("FACTORIAL", "!");

	public final String toolTip;
	public final String symbol;

	private UnaryOperation(String name, String symbol) {
		this.toolTip = name;
		this.symbol = symbol;
	}

}