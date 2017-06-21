
public class LadnerFischerAdder extends PrefixAdderGenerator{

	@Override
	public void insertNodeByPosition(int globalPosition, int stagePos, int stage, int gapSize, Node parent,  ApplicationController temp) {
		
		if(stage <= depth && gapSize <= stagePos % (gapSize*2)){ // Create Yellow Circles
			prevParentPosition = parent.getPosition() - (stagePos % gapSize) - 1;
			Node prevParent = getNodeByPosition(prevParentPosition);

			addWorkerNode(stage, globalPosition, parent, prevParent, temp);
		} 
		else if(stage <= depth){ //Create Green Circles
			
			addHangingNode(stage, globalPosition, parent, temp);
			
		}
		
	}

}
