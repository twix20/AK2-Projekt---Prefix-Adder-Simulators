
public enum Bit {
	Zero,
	One;
	
	public static int getValue(Bit b){
		return b == Zero ? 0 : 1;
	}
}
