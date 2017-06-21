
import java.util.ArrayList;
import java.util.List;


public abstract class Node {
	protected int stage, position;
	protected Node rootParent;
	protected Node parent, prevParent;
	protected List<Node> childs;
	private NodeResult result;
	protected String type; // modyfikacja
	protected Bit a, b;
	protected ApplicationController ap;

	public Node(int stage, int postion, ApplicationController temp){
		this.childs = new ArrayList<Node>();
		
		this.setStage(stage);
		this.setPosition(postion);
		
		this.ap = temp;
		
		this.result = new NodeResult();
		this.result.status = Status.NotComputed;
	}
	
	public abstract void computeResult();
	
	public synchronized void tryToComputeResult(){

		//Check if result has already been computed
		if(this.getResult().status == Status.Computed)
			{
			return;
			}
		
		// Check if all parents are computed to proceed
		if(!areParentsComputed()) return;

		computeResult();

		notifyChildren();
	}
	
	public String toString(){

		int prevParentPos = getPrevParent() == null ? -1 : getPrevParent().getPosition();
		
		return String.format("|{%1$d}{%2$d} P:%3$s G:%4$s T:%5$s| ", 
				getPosition(), 
				prevParentPos, 
				getResult().propagation, 
				getResult().generation,
				getNodeType());
	}
	
	
	protected String getNodeType() {
		return type;
		
	}

	public void addChild(Node c){
		childs.add(c);
	}
	
	private void notifyChildren(){
		PrefixAdderSolver.tryToComputeResultForNodes(this.childs);
	}
	
	
	private Boolean areParentsComputed(){
		if(parent != null && parent.result.status == Status.NotComputed){
			return false;
		}
		
		if(prevParent != null && prevParent.result.status == Status.NotComputed){
			return false;
		}

		return true;
	}

	
	// Getters and Setters
	public List<Node> getChilds(){
		return this.childs;
	}
	
	
	public NodeResult getResult(){
		return result;
	}
	
	public Node getPrevParent() {
		return prevParent;
	}

	public void setPrevParent(Node prevParent) {
		this.prevParent = prevParent;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

 	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public Node getRootParent() {
		return rootParent;
	}

	public void setRootParent(Node rootParent) {
		this.rootParent = rootParent;
	}

}

class NodeResult {
	public Status status;
	public Bit propagation;
	public Bit generation;
	public Bit sum;
}

enum Status {
	Computed,
	NotComputed,
}


