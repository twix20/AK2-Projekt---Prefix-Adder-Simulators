

//Yellow circle
public class WorkerNode extends Node{

	public WorkerNode(int stage, int postion,  ApplicationController temp) {
		super(stage, postion, temp);
		
		this.type = "W";
	}

	@Override
	public void computeResult() {
		Bit newPropagation = BitCalculator.and(parent.getResult().propagation, prevParent.getResult().propagation);
		Bit newGeneration = BitCalculator.or(BitCalculator.and(parent.getResult().propagation, prevParent.getResult().generation), parent.getResult().generation);
		
		getResult().propagation = newPropagation;
		getResult().generation = newGeneration;
		getResult().status = Status.Computed;
	}

}
