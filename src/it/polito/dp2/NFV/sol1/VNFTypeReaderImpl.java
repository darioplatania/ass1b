package it.polito.dp2.NFV.sol1;


import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.*;

public class VNFTypeReaderImpl extends NamedEntityReaderImpl implements VNFTypeReader {
	
	private int requiredmemory;
	private int requiredstorage;	
	private FunctionalType functionalType;
	private NodeFunctionalType fType;
		
	public VNFTypeReaderImpl(FType functionalType) {
		super(functionalType.getFunctionaltypeId());
		this.requiredmemory = functionalType.getRequiredMemory();
		this.requiredstorage = functionalType.getRequiredStorage();
		this.functionalType = FunctionalType.valueOf(functionalType.getFunctionalTypeName().toString());
		this.fType = functionalType.getFunctionalTypeName();
	}

	@Override
	public FunctionalType getFunctionalType() {
		return this.functionalType;
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
