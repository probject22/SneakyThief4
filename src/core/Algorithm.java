package core;

public class Algorithm {
	public static boolean guardExploration = true;
	public static boolean covarage = true;
	public static boolean catchalg = true;
	public static boolean escape = true;
	
	public static Escape escapeAlg = Escape.sneaky;
	public enum Escape{
		dt,
		sneaky,
		prey;
	}

}