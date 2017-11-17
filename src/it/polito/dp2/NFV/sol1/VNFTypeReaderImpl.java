package it.polito.dp2.NFV.sol1;


import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.VNFTypeReader;

public class VNFTypeReaderImpl extends NamedEntityReaderImpl implements VNFTypeReader {
	
	int requiredmemory;
	int requiredstorage;	
	private FunctionalType ft;
	
	public VNFTypeReaderImpl(String name,int requiredmemory,int requiredstorage,FunctionalType ft) {
		super(name);
		this.requiredmemory = requiredmemory;
		this.requiredstorage = requiredstorage;
		this.ft = ft;
	}

	@Override
	public FunctionalType getFunctionalType() {
		return this.ft;
	}

	@Override
	public int getRequiredMemory() {
		return this.requiredmemory;
	}

	@Override
	public int getRequiredStorage() {
		return this.requiredstorage;
	}
	
	public String getName() {
		return super.getName();
	}
		
}
