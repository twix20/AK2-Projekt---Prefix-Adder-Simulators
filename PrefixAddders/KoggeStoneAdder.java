
public class KoggeStoneAdder extends PrefixAdderGenerator{

	@Override
	public void insertNodeByPosition(int globalPosition, int stagePos, int stage, int gapSize, Node parent,  ApplicationController temp) {
		
		if(stage <= depth && gapSize <= stagePos){ // Create Yellow Circles
			prevParentPosition = parent.getPosition() - gapSize;
			Node prevParent = getNodeByPosition(prevParentPosition);

			addWorkerNode(stage, globalPosition, parent, prevParent, temp);
		} 
		else if(stage <= depth){ //Create Green Circles

			addHangingNode(stage, globalPosition, parent, temp);
		}
		
	}

}
