package it.polito.dp2.NFV.sol1;

import java.util.*;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NodeReader;

public class HostReaderImpl extends NamedEntityReaderImpl implements HostReader {
	
	private int availablememory;
	private int availablestorage;
	private int maxvnf;
	Set<NodeReader> nodes;
	
	public HostReaderImpl(String name,int availablememory,int availablestorage,int maxvnf,Set<NodeReader> nodes ) {
		super(name);
		this.availablememory = availablememory;
		this.availablestorage = availablestorage;
		this.maxvnf = maxvnf;
		this.nodes = nodes;
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
		return this.nodes;
	}
	
	 @Override
	 public String getName() {
		 return super.getName();
	 }
	
	
}
