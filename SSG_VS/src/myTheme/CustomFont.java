package myTheme;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class CustomFont{
	
	public Font myCustomFont = new Font("Brown", 0, 20);
	
	GraphicsEnvironment geKG;
	File fontFile = new File("Brown-Regular.ttf");
	{
		try {
			myCustomFont = Font.createFont(Font.TRUETYPE_FONT, new File("Brown-Regular.ttf")).deriveFont(20f);
			geKG = GraphicsEnvironment.getLocalGraphicsEnvironment();
			geKG.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
		} catch (IOException | FontFormatException e) {
			System.out.println("whoa");
                                
		}
	}
	public Font setFontModifications(Font font, float fontSize) {
		Font f = font.deriveFont(fontSize);
		return f;
	}
	public String toString(Font myFont) {
		return myFont.getFontName();
	}
	
	
}
