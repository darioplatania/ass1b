package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.NamedEntityReader;

public class NamedEntityReaderImpl implements NamedEntityReader {
	private String name;
	
	public NamedEntityReaderImpl(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
