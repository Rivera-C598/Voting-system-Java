package myTheme;

import java.awt.Color;

public class Colors {
	// INVERT 0xECE4B7
	// 311E10
	// DEFAULT COLORS
	public static Color LIGHT = new Color(0xECE4B7);
	public static Color DARK = new Color(0x392211);
	public static Color ORANGE = new Color(0xDD6031);
	public static Color MILD = new Color(0xDDE6DE);
	public static Color DARKEST = new Color(0x311E10);

	public static void setTheme(String theme) {
		if (theme.equals("Dark Slate Green")) {
			LIGHT = new Color(0x84A98C);
			DARK = new Color(0x2F3E46);
			ORANGE = new Color(0xF7F3EC);
			MILD = new Color(0xF7F3EC);
		} else if (theme.equals("Cameo Pink")) {
			LIGHT = new Color(0xE6BECE); //Cameo Pink
			DARK = new Color(0x2A1F26); //Raisin Black
			ORANGE = new Color(0x8E4162); //Quinacridone Magenta
			MILD = new Color(0xC7E8F3); //Colombia Blue
		} else if (theme.equals("Default")) {
			LIGHT = new Color(0xECE4B7);
			DARK = new Color(0x392211);
			ORANGE = new Color(0xDD6031);
			MILD = new Color(0xDDE6DE);
		}
	}
}
