package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.NfvReader;
import java.util.Calendar;
import java.util.Set;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;

public class NfvReaderImpl implements NfvReader {
	
	private Set<HostReader>  hosts;
	private Set<NffgReader> nffgs;
	private Set<VNFTypeReader> vnfs;
	ConnectionPerformanceReader cpr;
	
	public NfvReaderImpl(ConnectionPerformanceReader cpr,String hostname,Set<HostReader>  hosts,String nffgname,Set<NffgReader> nffgs,Set<VNFTypeReader> vnfs) {
		this.cpr = cpr;		
		this.hosts = hosts;		
		this.nffgs = nffgs;
		this.vnfs = vnfs;
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader arg0, HostReader arg1) {
		return this.cpr;
	}

	@Override
	public HostReader getHost(String arg0) {
		for(HostReader hr:this.hosts){
			if(hr.getName()==arg0)
				return hr;
		}
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		return this.hosts;
	}

	@Override
	public NffgReader getNffg(String arg0) {
		for(NffgReader nffgr:this.nffgs){
			if(nffgr.getName()==arg0)
				return nffgr;
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar arg0) {
		return this.nffgs;
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		return this.vnfs;
	}	
}
