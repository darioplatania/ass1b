package it.polito.dp2.NFV.sol1;


import it.polito.dp2.NFV.*;
import it.polito.dp2.NFV.sol1.jaxb.*;

public class VNFTypeReaderImpl extends NamedEntityReaderImpl implements VNFTypeReader {
	
	private int requiredmemory;
	private int requiredstorage;	
	private FunctionalType functionalType;
	private NodeFunctionalType fType;
	
/*	
	public VNFTypeReaderImpl(String name,int requiredmemory,int requiredstorage,FunctionalType ft) {
		super(name);
		this.requiredmemory = requiredmemory;
		this.requiredstorage = requiredstorage;
		this.ft = ft;
	}
*/
	
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
