import java.util.ArrayList;
import java.util.List;

public class PrefixAdderSolver {
	private PrefixAdderGenerator prefixAdder;
	
	public PrefixAdderSolver(PrefixAdderGenerator pa){
		this.prefixAdder = pa;
	}
	
	public static void tryToComputeResultForNodes(List<Node> nodes){
		nodes.parallelStream().forEach((node) -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			node.tryToComputeResult();
		});

	}
	
	public void startSolvingMesh() throws InterruptedException{
		List<Node> firstStageNodes = prefixAdder.getMeshNodesByStage(0);
		tryToComputeResultForNodes(firstStageNodes);
		
	}
	
	public void generateMesh(int a, int b,  ApplicationController temp){
		prefixAdder.generateMesh(a, b, temp);
	}
	
	public void generateMesh(String binA, String binB,  ApplicationController temp){
		prefixAdder.generateMesh(binA, binB, temp);
	}
	
	public String printSum(){
		List<Node> allResultNodes = prefixAdder.getResultMeshNodes();
		
		String sum = "";
		for(int i = allResultNodes.size() - 1; i >= 0; i--){
			ResultNode n = (ResultNode)allResultNodes.get(i);
			
			sum += Bit.getValue(n.getResult().sum);
		}
		//System.out.println(sum);
		return sum;
	}
	
	public void printMeshNodes(){

		ArrayList<String> allStrings = new ArrayList<String>();
		String line = "";
		
		for(int i = 0; i < prefixAdder.getMeshNodes().size(); i++)
		{
			Node n = prefixAdder.getNodeByPosition(i);
			line = n.toString() + line;
		
			
			if(i % prefixAdder.length == prefixAdder.length - 1){
				allStrings.add(line);
				line = "";
			}
				
		}
		
		for(int i = 0; i < allStrings.size(); i++)
			System.out.print(allStrings.get(i) + "\n");
	}

	
	public PrefixAdderGenerator getGenerator(){
		return this.prefixAdder;
	}
	
}
