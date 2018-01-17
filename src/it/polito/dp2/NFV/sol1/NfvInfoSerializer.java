package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.datatype.*;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.*;

public class NfvInfoSerializer {
	
	//private static final String W3C_XML_SCHEMA_NS_URI = null;
	private NfvReader monitor;
	private DateFormat dateFormat;
	private static final String XSD_FOLDER = "xsd/";
    private static final String XSD_FILE = "nfvInfo.xsd";
    private static final String PACKAGE = "it.polito.dp2.NFV.sol1.jaxb";
	private NetworkProvider np;	
	
	/**
	 * Default constructror
	 * @throws NfvReaderException 
	 */
	public NfvInfoSerializer() throws NfvReaderException {
		NfvReaderFactory factory = NfvReaderFactory.newInstance();
		monitor = factory.newNfvReader();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		this.np=new NetworkProvider();
		np.setIn(new InType());
	}
	
	public NfvInfoSerializer(NfvReader monitor) {
		super();
		this.monitor = monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
	}

	/**
	 * @param args
	 * @throws DatatypeConfigurationException 
	 */
	public static void main(String[] args) throws DatatypeConfigurationException {
		NfvInfoSerializer wf;
		try {
			wf = new NfvInfoSerializer();
			/*Print All*/
			wf.printAll(args[0]);
		} catch (NfvReaderException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	

	public void printAll(String f) throws DatatypeConfigurationException, NfvReaderException {
		
		printNffgs();
		printHosts();
		printPerformance();
		printCatalog();	
		
		File filename = new File (f);
		
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance( PACKAGE );
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // create an Marshaller
        Marshaller m = null;
		try {
			m = jc.createMarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		try {
			Schema schema  = sf.newSchema(new File(XSD_FOLDER + XSD_FILE));
			m.setSchema(schema);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
        try {
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //JAXBElement<NetworkProvider> net = (new ObjectFactory()).createNetworkProvider(np);
        try {
			m.marshal(np, filename);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	/*
	 * Function
	 * 
	 */

	private void printPerformance() {
		
		System.out.println("** START PRINT PERFORMANCE **");
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();		
	
			for (HostReader sri: set) {
			HostType ht = new HostType();		
			ht.setHostName(sri.getName());
		}
		for (HostReader sri: set) {			
			for (HostReader srj: set) {			
				PerformanceType pt=new PerformanceType();
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				pt.setAvgThroughput(cpr.getThroughput());
				pt.setLatency(cpr.getLatency());
				pt.setSourceHost(sri.getName());
				pt.setDestinationHost(srj.getName());
				np.getIn().getPerformance().add(pt);	
			}			
		}
		System.out.println("** FINISH PRINTPERFORMANCE **");
	}

	private void printCatalog() {
		System.out.println("** START PRINTCATALOG **");
		Set<VNFTypeReader> set = monitor.getVNFCatalog();
		CatalogType c = new CatalogType();
		
		// For each VNF type print name and class
		for (VNFTypeReader vnfType_r: set) {
			
			// Create ftype,set and add
			FType ftype = new FType();	
			ftype.setFunctionaltypeId(vnfType_r.getName());
			ftype.setFunctionalTypeName(NodeFunctionalType.valueOf(vnfType_r.getFunctionalType().toString()));
			ftype.setRequiredMemory(vnfType_r.getRequiredMemory());
			ftype.setRequiredStorage(vnfType_r.getRequiredStorage());
			c.getFunctionaltype().add(ftype);
		}
		np.setCatalog(c);
		System.out.println("** FINISH PRINTCATALOG **");
	}

	private void printHosts() {
		System.out.println("** START PRINTHOSTS **");
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
				
		for (HostReader host_r: set) {
			System.out.println("host:" + host_r.getName()) ;
			HostType ht = new HostType();
			ht.setHostName(host_r.getName());
			ht.setNumberVNFs(host_r.getMaxVNFs());
			ht.setMemory(host_r.getAvailableMemory());
			ht.setDiskStorage(host_r.getAvailableStorage());			
			np.getIn().getHost().add(ht);
		}
		System.out.println("** FINISH PRINTHOSTS **");
	}


	private void printNffgs() throws DatatypeConfigurationException, NfvReaderException {
		System.out.println("** START PRINTNFFG **");
		
		// Get the list of NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);
		
		// Create nffglist
		List<NffgType> nffglist = np.getNffg();
		
		// For each NFFG print related data
		for (NffgReader nffg_r: set) {
			
			// Create nffgtype and set name
			NffgType nffg = new NffgType();
			nffg.setNameNffg(nffg_r.getName());
	
			// Set deploytime for nffg
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(nffg_r.getDeployTime().getTime());
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			nffg.setDeployTime(xgc);
			
			// Print nodes
			Set<NodeReader> nodeSet = nffg_r.getNodes();
			// Create nodelist
			List<NodeType> nodelist = nffg.getNode();
			
			for (NodeReader nr: nodeSet) {
	
				// Create node,set node and add into nodelist
				NodeType node = new NodeType();
				node.setNodeName(nr.getName());
				node.setHostName(nr.getHost().getName());
				node.setFunctionaltypeId(nr.getFuncType().getName());
				nodelist.add(node);
				
				Set<LinkReader> linkSet = nr.getLinks();
				// Create linklist
				List<LinkType> linklist = node.getLink();
								
				for (LinkReader lr: linkSet) {
					if(lr.getSourceNode()!=lr.getDestinationNode()) {
					// Create link,set link and add into listlink
					LinkType link = new LinkType();
					link.setLinkName(lr.getName());
					link.setSourceNode(lr.getSourceNode().getName());
					link.setDestinationNode(lr.getDestinationNode().getName());
					link.setMinThroughput(lr.getThroughput());
					link.setMaxLatency(lr.getLatency());
					linklist.add(link);
					}
					else
						throw new NfvReaderException("Link Incorrect!");
				}
			}			
			//add into nffglist
			nffglist.add(nffg);
		}	
		System.out.println("** FINISH PRINTNFFG **");
	}
}
