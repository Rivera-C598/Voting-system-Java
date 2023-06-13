package myTheme;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class CTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Font defaultFont = new CustomFont().myCustomFont;
	String fontString = new CustomFont().toString(defaultFont);

	public CTextField() {
		this(null, null, 0);
		setCustomProperty();
	}

	public CTextField(String text) {
		this(null, text, 0);
		setCustomProperty();
	}

	public CTextField(Document doc, String text, int columns) {
		if (columns < 0) {
			throw new IllegalArgumentException("columns less than zero.");
		}
		if (doc == null) {
			doc = createDefaultModel();
		}
		setDocument(doc);
		if (text != null) {
			setText(text);
		}
		setCustomProperty();
	}

	void setCustomProperty() {
		// add modifications here
		setFont(new Font(fontString,0,25));
		setPreferredSize(new Dimension(305, 40));
		setBackground(Colors.MILD);
		setBorder(BorderFactory.createBevelBorder(1));
	}
}
