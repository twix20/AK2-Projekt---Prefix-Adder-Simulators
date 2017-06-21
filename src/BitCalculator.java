
public class BitCalculator {
	
	public static Bit or(Bit a, Bit b){
		return (a == Bit.One || b == Bit.One) ? Bit.One : Bit.Zero;
	}
	
	public static Bit xor(Bit a, Bit b){
		return a == b ? Bit.Zero : Bit.One;
	}
	
	public static Bit and(Bit a, Bit b){
		return (a == Bit.One && b == Bit.One) ? Bit.One : Bit.Zero;
	}
}