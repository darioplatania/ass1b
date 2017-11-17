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
		private Set<LinkReader> links;
	
	public NodeReaderImpl(String name,HostReader hosts,NffgReader nffgs,VNFTypeReader vnfs,Set<LinkReader> links) {
		super(name);
		this.hosts = hosts;
		this.links = links;
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
		return this.links;
	}

	@Override
	public NffgReader getNffg() {
		return this.nffgs;
	}
	
	public String getName() {
		return super.getName();
	}
}
