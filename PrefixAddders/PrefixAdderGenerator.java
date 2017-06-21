import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PrefixAdderGenerator {
	protected int length, depth;
	protected int gapSize;
	protected int posCounter = 0;
	protected int parentPosition, prevParentPosition;
	protected String sA, sB;
	protected HashMap<Integer, Node> meshNodes = new HashMap<Integer, Node>();

	
	public abstract void insertNodeByPosition(int globalPosition, int stagePos, int stage, int gapSize, Node parent,  ApplicationController temp);
	
	public void generateMesh(String sA, String sB,  ApplicationController temp){
		posCounter = 0;
		
		depth = PrefixAdderGenerator.calculateMeshDepth(sA, sB);
		length = getLongerLength(sA, sB) + 1;
		
		sA = PrefixAdderGenerator.alignBinaryString(sA, length);
		sB = PrefixAdderGenerator.alignBinaryString(sB, length);
		
		for(int stage = 0; stage <= depth + 1; stage++){ // Iterate stages
			gapSize = (int)Math.pow(2, stage - 1);
			
			for(int pos = 0; pos < length; pos++) { // Iterate stage positions
				Node parent;
				parentPosition = posCounter - length;
				parent = getNodeByPosition(parentPosition);
				
				if(stage == 0){ // Create Red Squares
					Bit aBit = Bit.values()[Character.getNumericValue(sA.charAt(sA.length() - 1 - pos))];
					Bit bBit = Bit.values()[Character.getNumericValue(sB.charAt(sB.length() - 1 - pos))];
					this.addInitialNode(stage, posCounter, aBit, bBit, temp);
				} 
				else if(stage == depth + 1) { //Create Yellow and Green circles

					this.addResultNode(stage, posCounter, parent, temp);
				}
				else{
					
					this.insertNodeByPosition(posCounter, pos, stage, gapSize, parent, temp);
				}
				
				posCounter++;
			}
		}
		

		
	}
	
	public int getLength(){
		return length;
	}
	
	public void generateMesh(int a, int b,  ApplicationController temp){
		sA = PrefixAdderGenerator.intToBinaryString(a);
		sB = PrefixAdderGenerator.intToBinaryString(b);
		
		generateMesh(sA, sB, temp);
	}
	
	private static int closestNextPowerOfTwo(int a) {
		return Integer.BYTES * 8 - Integer.numberOfLeadingZeros(a - 1);
	}
	
	private static String intToBinaryString(int a){
		return Long.toBinaryString( a & 0xffffffffL | 0x100000000L ).substring(1).replaceAll("^0*", "");
	}
	
	public static int getLongerLength(String sA, String sB){
		return Integer.max(sA.length(), sB.length());
	}
	
	public static int calculateMeshDepth(String sA, String sB){
		int longerLength = getLongerLength(sA, sB);
		
		return closestNextPowerOfTwo(longerLength);
	}
	
	public static String alignBinaryString(String stringToPad, int padToLength){
		if(stringToPad.length() < padToLength) {
			stringToPad = String.format("%0" + String.valueOf(padToLength - stringToPad.length()) + "d%s",0,stringToPad);
	    }
		return stringToPad;
	}
	
	protected void addInitialNode(int stage, int pos, Bit aBit, Bit bBit,  ApplicationController temp){
		Node nodeToInsert = new InitialNode(stage, pos, temp);
		nodeToInsert.a = aBit;
		nodeToInsert.b = bBit;
		nodeToInsert.setRootParent(nodeToInsert);
		
		meshNodes.put(nodeToInsert.getPosition(), nodeToInsert);
	}
	protected void addWorkerNode(int stage, int pos, Node parent, Node prevParent,  ApplicationController temp){
		Node nodeToInsert = new WorkerNode(stage, pos, temp);
		nodeToInsert.setRootParent(parent.getRootParent());
		nodeToInsert.setParent(parent);
		nodeToInsert.setPrevParent(prevParent);
		
		parent.addChild(nodeToInsert);
		prevParent.addChild(nodeToInsert);
		
		meshNodes.put(nodeToInsert.getPosition(), nodeToInsert);
	}
	protected void addHangingNode(int stage, int pos, Node parent,  ApplicationController temp){
		Node nodeToInsert = new HangingNode(stage, pos, temp);
		nodeToInsert.setRootParent(parent.getRootParent());
		nodeToInsert.setParent(parent);
		
		parent.addChild(nodeToInsert);
		meshNodes.put(nodeToInsert.getPosition(), nodeToInsert);
	}
	protected void addResultNode(int stage, int pos, Node parent,  ApplicationController temp){
		Node prevNode = getNodeByPosition(pos - 1);
		ResultNode prevResultNode = prevNode instanceof ResultNode ? (ResultNode)prevNode : ResultNode.FAKE_NODE;
		
		Node nodeToInsert = new ResultNode(stage, pos, prevResultNode, temp);
		nodeToInsert.setRootParent(parent.rootParent);
		nodeToInsert.setParent(parent);
		
		prevResultNode.addChild(nodeToInsert);
		parent.addChild(nodeToInsert);
		
		meshNodes.put(nodeToInsert.getPosition(), nodeToInsert);
	}

	
	
	public Node getNodeByPosition(int pos){
		if(meshNodes.containsKey(pos))
			return meshNodes.get(pos);
			
		return null;
	}
	
	public List<Node> getResultMeshNodes(){
		List<Node> nodes = new ArrayList<Node>();
		
		for(Node n: meshNodes.values())
			if(n.getStage() == depth + 1)
				nodes.add(n);
		
		return nodes;
	}
	
	public List<Node> getMeshNodesByStage(int stage){
		List<Node> nodesAtStage = new ArrayList<Node>();
		
		Map<Integer, Node> allNodes = getMeshNodes();
		for (Node n : allNodes.values()) {
			if(n.getStage() == stage){
				nodesAtStage.add(n);
			}
		}

		return nodesAtStage;
	}
	
	public HashMap<Integer, Node> getMeshNodes() {
		return meshNodes;
	}
	
}
