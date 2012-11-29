package seep.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import seep.P;
import seep.operator.Operator;
import seep.operator.QuerySpecificationI;

public class QueryPlan {
	
	static final int CONTROL_SOCKET = Integer.parseInt(P.valueFor("controlSocket"));
	static final int DATA_SOCKET = Integer.parseInt(P.valueFor("dataSocket"));
	
	private ArrayList<Operator> ops = new ArrayList<Operator>();
	public Map<Integer,QuerySpecificationI> elements = new HashMap<Integer, QuerySpecificationI>();
	//More than one source is supported
	private ArrayList<Operator> src = new ArrayList<Operator>();
	private Operator snk;
	//Mapping of operators to node
	private Map<Integer, ArrayList<Operator>> mapOperatorToNode = new HashMap<Integer, ArrayList<Operator>>();
	
	public ArrayList<Operator> getOps() {
		return ops;
	}

	public Map<Integer, QuerySpecificationI> getElements() {
		return elements;
	}

	public ArrayList<Operator> getSrc() {
		return src;
	}

	public Operator getSnk() {
		return snk;
	}
	
	public Map<Integer, ArrayList<Operator>> getMapOperatorToNode(){
		return mapOperatorToNode;
	}
	
	//This method is still valid to define which is the first operator in the query
	public void setSource(Operator source) {
		NodeManager.nLogger.info("Configured NEW SOURCE, Operator: "+src.toString());
		src.add(source);
	}

	public void setSink(Operator snk){
		NodeManager.nLogger.info("Configured SINK as Operator: "+snk.toString());
		this.snk = snk;
	}
	
	public void addOperator(Operator o) {
		ops.add(o);
		elements.put(o.getOperatorId(), o);
		NodeManager.nLogger.info("Added new Operator to Infrastructure: "+o.toString());
	}
	
	public void place(Operator o, Node n){
		int nodeId = n.getNodeId();
		if(mapOperatorToNode.containsKey(nodeId)){
			mapOperatorToNode.get(nodeId).add(o);
		}
		else{
			ArrayList<Operator> opsOfNode = new ArrayList<Operator>();
			opsOfNode.add(o);
			mapOperatorToNode.put(nodeId, opsOfNode);
		}
	}
}