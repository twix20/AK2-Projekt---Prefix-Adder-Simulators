

public class ResultNode extends Node {
	public static ResultNode FAKE_NODE;
	static {
		FAKE_NODE = new ResultNode(-1, -1, null, null);
		FAKE_NODE.getResult().generation = Bit.Zero;
		FAKE_NODE.getResult().propagation = Bit.Zero;
		FAKE_NODE.getResult().status = Status.Computed;
		FAKE_NODE.ap = null;
	}

	public ResultNode(int stage, int postion, ResultNode prevParent,  ApplicationController temp) {
		super(stage, postion, temp);
		
		this.type = "R";
		
		this.prevParent = prevParent;
	}
	
	@Override
	public void computeResult() {
		Bit previousCarry = this.prevParent.getResult().generation; // Previous result C-1
		
		Bit rootParentPropagation = getRootParent().getResult().propagation;
		Bit sum = BitCalculator.xor(rootParentPropagation, previousCarry); 
		
		this.getResult().sum = sum;
		this.getResult().generation = getParent().getResult().generation;
		this.getResult().status = Status.Computed;
	}
	
	@Override
	public String toString(){
		int prevParentPosIfExist = getPrevParent() == null ? -1 : getPrevParent().getPosition();
		
		return String.format("|{%1$d}{%2$d} C:%3$s S:%4$s T:%5$s| ", 
				getPosition(), 
				prevParentPosIfExist, 
				getResult().generation, 
				getResult().sum,
				getNodeType());
	}

}
