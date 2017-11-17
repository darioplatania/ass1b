package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NodeReader;

public class LinkReaderImpl extends NamedEntityReaderImpl implements LinkReader {
	
	private NodeReader source;
	private NodeReader dest;
	private int latency;
	private float throughput;
	
	private LinkReaderImpl(String name,NodeReader source, NodeReader dest,int latency,float throughput) {
		super(name);
		this.source = source;
		this.dest = dest;
		this.latency = latency;
		this.throughput = throughput;
	}
	
	@Override
	public NodeReader getDestinationNode() {
		return this.dest;
	}
	
	@Override
	public NodeReader getSourceNode() {
		return this.source;
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public int getLatency() {
		return this.latency;
	}

	@Override
	public float getThroughput() {
		return this.throughput;
	}

}
