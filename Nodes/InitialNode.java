

//Red Square
public class InitialNode extends Node{
	public InitialNode(int stage, int postion,  ApplicationController temp) {
		super(stage, postion, temp);
		this.type = "I";
	}


	@Override
	public void computeResult() {
		this.getResult().propagation = BitCalculator.xor(a, b);
		this.getResult().generation = BitCalculator.and(a, b);
		this.getResult().status = Status.Computed;
	}

}

