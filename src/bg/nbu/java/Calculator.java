package bg.nbu.java;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Calculator {
	private final JFrame frame;
	private JTextField display;
	private Double operand1;
	private Double operand2;
	private BinaryOperation op;
	// true when next number input should clear the display
	private boolean cleanDisplay;

	private final String DEFAULT_MESSAGE = "";
	private final Font FONT = new Font("Helvetica", Font.PLAIN, 20);

	/**
	 * Constructor that initializes the calculator object
	 */
	public Calculator() {
		frame = new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel(new GridBagLayout());
		frame.setContentPane(contentPane);

		cleanDisplay = false;

		// creating the calculators buttons and text fields
		addDisplay();
		addNumberButtons();
		addDecimalButton();
		addBinaryOperationButtons();
		addUnaryOperationButtons();
		addClearButton();
		addEvaluateButton();
	}

	/**
	 * Draws the Calculator on the screen.
	 */
	public void showCalculator() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Helper method for adding buttons to the frame.
	 *
	 * @param label The label that will be drawn on top of the button.
	 * @param toolTip The text that will appear as a tool tip of the button.
	 * @param constraints GridBagConstraints object which should contain the information about the buttons position
	 *
	 * @return reference to the newly created button
	 */
	private JButton addButton(String label, String toolTip, GridBagConstraints constraints) {
		JButton button = new JButton(label);
		button.setPreferredSize(new Dimension(65, 40));
		button.setToolTipText(toolTip);
		button.setFont(FONT);
	
		frame.getContentPane().add(button, constraints);
	
		return button;
	}

	/**
	 * Adds evaluate button to the calculator ui.
	 */
	private void addEvaluateButton() {
		JButton button = addButton("=", "EVALUATE", generateConstraints(4, 4, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				operand2 = castDouble(display.getText());
				writeToDisplay(calculate());
				cleanDisplay = true;
			}
		});
	}

	/**
	 * Adds clear button the calculator ui.
	 */
	private void addClearButton() {
		JButton button = addButton("C", "CLEAR", generateConstraints(2, 4, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				display.setText(DEFAULT_MESSAGE);
			}
		});
	}

	/**
	 * Adds buttons for each of the supported binary arithmetic operations
	 */
	private void addBinaryOperationButtons() {
		int counter = 0;
		for (BinaryOperation operation : BinaryOperation.values()) {
			JButton button = addButton(operation.symbol, operation.toolTip, generateConstraints(3 + counter / 4, 1 + counter % 4, 1, 1));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					operand1 = castDouble(display.getText());
					op = operation;
					cleanDisplay = true;
				}
			});
			counter++;
		}
	}

	/**
	 * Adds buttons for each of the supported unary arithmetic operations
	 */
	private void addUnaryOperationButtons() {
		JButton button = addButton(UnaryOperation.FACTORIAL.symbol, UnaryOperation.FACTORIAL.toolTip, generateConstraints(4, 3, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Double operand = castDouble(display.getText());
				writeToDisplay(factorial(operand));
				cleanDisplay = true;
			}
		});

		button = addButton(UnaryOperation.SQUARE_ROOT.symbol, UnaryOperation.SQUARE_ROOT.toolTip, generateConstraints(4, 2, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Double operand = castDouble(display.getText());
				writeToDisplay(Math.sqrt(operand));
				cleanDisplay = true;
			}
		});
	}

	/**
	 * Adds a button for decimal point
	 */
	private void addDecimalButton() {
		JButton button = addButton(".", null, generateConstraints(1, 4, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentInput = display.getText();
				if (!currentInput.contains(".")) {
					display.setText(currentInput + ".");
				}
			}
		});
	}

	/**
	 * Adds buttons for the numpad
	 */
	private void addNumberButtons() {
		for (int i = 1; i < 10; i++) {
			addNumber(i, (i - 1) % 3, 3 - (i - 1) / 3);
		}
		addNumber(0, 0, 4);
	}

	/**
	 * Adds button for a given number
	 *
	 * @param number the number for which a button should be added
	 * @param gridX the x coordinate of the button
	 * @param gridY the y coordinate of the button
	 */
	private void addNumber(Integer number, int gridX, int gridY) {
		JButton button = addButton(String.valueOf(number), null, generateConstraints(gridX, gridY, 1, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cleanDisplay) {
					display.setText(DEFAULT_MESSAGE);
					cleanDisplay = false;
				}
				String currentText = display.getText();
				display.setText(currentText + String.valueOf(number));
			}
		});
	}

	/**
	 * Adds the display of the calculator
	 */
	private void addDisplay() {
		display = new JTextField(20);
		display.setEditable(false);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(FONT);
		frame.getContentPane().add(display, generateConstraints(0, 0, 5, 1));
	}

	/**
	 * Helper function for generating GridBagConstraint objects.
	 * 
	 * @param gridX the x coordinate of the widget
	 * @param gridY the y coordinate of the widget
	 * @param gridWidth the width of the widget
	 * @param gridHeight the height of the widget
	 * 
	 * @return constructed GridBagConstraints object
	 */
	private GridBagConstraints generateConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;

		return constraints;
	}

	/**
	 * Executes the current operation stored in op variable with the operands stored in operand1 and operand2
	 *
	 * @return the result of the operation
	 */
	private Double calculate() {
		if (op == BinaryOperation.SUM) {
			return operand1 + operand2;
		} else if (op == BinaryOperation.SUBTRACTION) {
			return operand1 - operand2;
		} else if (op == BinaryOperation.MULTIPLICATION) {
			return operand1 * operand2;
		} else if (op == BinaryOperation.DIVISION) {
			return operand1 / operand2;
		} else if (op == BinaryOperation.MODULUS_DIVISION) {
			return operand1 % operand2;
		}

		return 0d;
	}

	/**
	 * Calculates the factorial of the provided number
	 * 
	 * @param number the number for which a factorial should be calculated
	 * 
	 * @return the factorial of the number
	 */
	private Double factorial(Double number) {
		Double result = 1d;
		for (int i = 2; i <= number; i++) {
			result *= i;
		}
		return result;
	}

	/**
	 * Helper method for casting a double from string
	 * 
	 * @param input The String to be casted
	 * 
	 * @return the casted double form the input, or 0 if the string is equal to the default message
	 */
	private Double castDouble(String input) {
		if (input.equals(DEFAULT_MESSAGE)) {
			return 0d;
		}
		return Double.valueOf(input);
	}

	/**
	 * Writes the provided double value to the display
	 * 
	 * @param result Double value which should be written to the display
	 */
	private void writeToDisplay(Double result) {
		String output = String.valueOf(result);
		// check if the number is integer
		if (result == Math.floor(result) && !Double.isInfinite(result)) {
			output = output.substring(0, output.lastIndexOf("."));
		}
		display.setText(output);
	}

}
