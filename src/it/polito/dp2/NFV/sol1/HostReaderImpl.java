package it.polito.dp2.NFV.sol1;

import java.util.*;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.HostType;

public class HostReaderImpl extends NamedEntityReaderImpl implements HostReader {
	
	private int availablememory;
	private int availablestorage;
	private int maxvnf;
	private HashMap<String,NodeReader> nodes;
	
	public HostReaderImpl(HostType host){
		super(host.getHostName());
		this.availablememory = host.getMemory();
		this.availablestorage = host.getDiskStorage();
		this.maxvnf = host.getNumberVNFs();
		this.nodes = new HashMap<String,NodeReader>();
	}

	@Override
	public int getAvailableMemory() {
		return this.availablememory;
	}

	@Override
	public int getAvailableStorage() {
		return this.availablestorage;
	}

	@Override
	public int getMaxVNFs() {
		return this.maxvnf;
	}

	@Override
	public Set<NodeReader> getNodes() {
		return new LinkedHashSet<NodeReader>(this.nodes.values());
	}
	
	 @Override
	 public String getName() {
		 return super.getName();
	 }
	
	 public void addNode(NodeReaderImpl node) {
		 if(node != null) {
			 this.nodes.put(node.getName(), node);
		 }
	}
	
}
