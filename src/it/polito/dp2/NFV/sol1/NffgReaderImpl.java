package it.polito.dp2.NFV.sol1;

import java.util.*;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NodeReader;

public class NffgReaderImpl extends NamedEntityReaderImpl implements NffgReader {
	
	 Calendar UpdateTime;
	 private Set<NodeReader> nodes;
	 
	 public NffgReaderImpl(String name,Calendar UpdateTime, Set<NodeReader> nodes) {
		 super(name);
		 this.UpdateTime = UpdateTime;
		 this.nodes = nodes;
	 }
	 
	 public void addNode(NodeReader n){
			if(n!=null)
				this.nodes.add(n);
		}
	 
	 @Override
	 public Calendar getDeployTime() {
		 return this.UpdateTime;
	 }
	 
	 @Override
	 public Set<NodeReader> getNodes(){
		 return this.nodes;
	 }
	 
	 @Override
		public NodeReader getNode(String arg0) {
			for(NodeReader nr:this.nodes){
				if(nr.getName()==arg0)
					return nr;
			}
			return null;
		}
	 
	 @Override
	 public String getName() {
		 return super.getName();
	 }
	 
}
