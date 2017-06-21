
//Green circle
public class HangingNode extends Node{

	public HangingNode(int stage, int postion,  ApplicationController temp) {
		super(stage, postion, temp);
		this.type = "H";
	}

	@Override
	public void computeResult() {
		getResult().propagation = parent.getResult().propagation;
		getResult().generation = parent.getResult().generation;;
		getResult().status = Status.Computed;
	}

}
