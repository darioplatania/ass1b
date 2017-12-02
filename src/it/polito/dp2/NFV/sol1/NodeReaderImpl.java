package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;

import java.util.*;

public class NodeReaderImpl extends NamedEntityReaderImpl implements NodeReader {
	
		private HostReader hosts;
		private NffgReader nffgs;
		private VNFTypeReader vnfs;		
		private HashMap<String,LinkReader> links;
	
	
	public NodeReaderImpl(String name,HostReader hosts,NffgReader nffgs,VNFTypeReader vnfs) {
		super(name);
		this.hosts = hosts;
		this.links = new HashMap<String,LinkReader>();
		this.nffgs = nffgs;
		this.vnfs = vnfs;
	}

	@Override
	public VNFTypeReader getFuncType() {
		return this.vnfs;
	}

	@Override
	public HostReader getHost() {
		return this.hosts;
	}

	@Override
	public Set<LinkReader> getLinks() {
		return new LinkedHashSet<LinkReader>(this.links.values());
	}

	@Override
	public NffgReader getNffg() {
		return this.nffgs;
	}
	
	/*public String getName() {
		return super.getName();
	}*/
	
	public void addLink(LinkReader link){
		if(link != null)
		this.links.put(link.getName(), link);
	}
	
	public LinkReader getLink(String LID){
		for(Map.Entry<String, LinkReader> lr: this.links.entrySet())
			if(lr.getValue().getName().equals(LID))
				return lr.getValue();
		return null;
	}
}
