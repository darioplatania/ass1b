package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.sol1.jaxb.PerformanceType;

public class ConnectionPerformanceReaderImpl implements ConnectionPerformanceReader {
	
	private float throughput;
	private int latency;
	private String myhrsource;
	private String myhrdest;
	
	public ConnectionPerformanceReaderImpl(PerformanceType pf) {
		this.throughput = pf.getAvgThroughput();
		this.latency = (int) pf.getLatency();
		this.myhrsource = pf.getSourceHost();
		this.myhrdest = pf.getDestinationHost();
	}
	
	@Override
	public int getLatency() {
		return this.latency;
	}

	@Override
	public float getThroughput() {
		return this.throughput;
	}
	
	public String getsourceHost() {
		return this.myhrsource;
	}
	
	public String getdestHost() {
		return this.myhrdest;
	}
}
