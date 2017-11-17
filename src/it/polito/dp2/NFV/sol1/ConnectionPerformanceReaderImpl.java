package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.ConnectionPerformanceReader;

public class ConnectionPerformanceReaderImpl implements ConnectionPerformanceReader {
	
	private float throughput;
	private int latency;
	
	public ConnectionPerformanceReaderImpl(float throughput,int latency) {
		this.throughput = throughput;
		this.latency = latency;
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
