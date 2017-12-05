package it.polito.dp2.NFV.sol1;

import java.util.*;

import it.polito.dp2.NFV.*;


public class NffgReaderImpl extends NamedEntityReaderImpl implements NffgReader {
	
	 Calendar DeployTime;
	 private HashMap<String,NodeReader> nodes;	
		
	public NffgReaderImpl(String name,Calendar DeployTime){
		super(name);
		this.DeployTime = DeployTime;
		this.nodes = new HashMap<>();
		
	}
		
	 
	 public void addNode(NodeReaderImpl n){
			if(n!=null)
				this.nodes.put(n.getName(), n);
		}
	 
	 @Override
	 public Calendar getDeployTime() {
		 return this.DeployTime;
	 }
	 
	 @Override
	 public Set<NodeReader> getNodes(){
		 return new LinkedHashSet<NodeReader>(this.nodes.values());
	 }
	 
	 @Override
		public NodeReader getNode(String arg0) {
			for(Map.Entry<String, NodeReader> nr: this.nodes.entrySet()){
				if(nr.getValue().getName()==arg0)
					return  nr.getValue();
			}
			return null;
		}
	 
	 @Override
	 public String getName() {
		 return super.getName();
	 }
	 
}
