package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;


/*
 * 
 * La classe astratta NfvReaderFactory implementa il Factory Pattern
 * Ritorna classi concrete che implementano l'interfaccia NfvReader
 * 
 * 
 * 
 */

public class NfvReaderFactory extends it.polito.dp2.NFV.NfvReaderFactory {

	/**
     * Void constructor
     */
    public NfvReaderFactory() {}

    /**
     * Create an instance of the concrete class NfvReader which extends the
     * abstract interface NfvReader
     *
     * @return
     * @throws it.polito.dp2.NFV.NfvReaderFactory
     */
    @Override
    //Creates a new instance of a NfvReader implementation.
    public NfvReader newNfvReader() throws NfvReaderException {
		return new NfvReaderImpl();
    }

}
