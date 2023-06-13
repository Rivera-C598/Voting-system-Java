package myTheme;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.text.Document;

public class CPasswordField extends JPasswordField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font defaultFont = new CustomFont().myCustomFont;
	
	public CPasswordField() {
		this(null, null, 0);
		setCustomProperty();
	}

	public CPasswordField(String text) {
		this(null, text, 0);
		setCustomProperty();
	}

	public CPasswordField(int columns) {
		this(null, null, columns);
		setCustomProperty();
	}

	public CPasswordField(String text, int columns) {
		this(null, text, columns);
		setCustomProperty();
	}

	public CPasswordField(Document doc, String txt, int columns) {
		super(doc, txt, columns);
		enableInputMethods(false);
		setCustomProperty();
	}

	void setCustomProperty() {
		setFont(defaultFont);
		setEchoChar('*');
		setMargin(new Insets(2, 2, 2, 2));
		setPreferredSize(new Dimension(305, 40));
		setBackground(Colors.MILD);
		setBorder(BorderFactory.createBevelBorder(1));

	}
}
