package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

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
	
	private static final String W3C_XML_SCHEMA_NS_URI = null;
	private NfvReader monitor;
	private DateFormat dateFormat;
	public NetworkProvider np;	
	
	/**
	 * Default constructror
	 * @throws NfvReaderException 
	 */
	public NfvInfoSerializer() throws NfvReaderException {
		NfvReaderFactory factory = NfvReaderFactory.newInstance();
		monitor = factory.newNfvReader();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		this.np=new NetworkProvider();
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
			wf.printAll(args[0]);
		} catch (NfvReaderException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		}
	}


	public void printAll(String f) throws DatatypeConfigurationException {
		printHosts();
		printCatalog();
		printNffgs();
		printPerformance();
		
File filename = new File (f);
		
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance( "it.polito.dp2.NFV.sol1.jaxb" );
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
			Schema schema  = sf.newSchema(new File("xsd/nfvInfo.xsd"));
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


	private void printPerformance() {
		
		System.out.println("**START PRINT PERFORMANCE **");
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		PerformanceType pt=new PerformanceType();
		InType inn=new InType();				
		HostType ht = new HostType();
	
			for (HostReader sri: set) {
			System.out.print("\t"+sri.getName());			
			ht.setHostName(sri.getName());
		}
		System.out.println(" ");
		for (HostReader sri: set) {			
			System.out.print(sri.getName());
			for (HostReader srj: set) {
				if (sri.getName()!=srj.getName()) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				pt.setAvgThroughput(cpr.getThroughput());
				pt.setLatency(cpr.getLatency());
				pt.setSourceHost(sri.getName());
				pt.setDestinationHost(srj.getName());
				inn.getPerformance().add(pt);
				System.out.print("\t"+cpr.getThroughput());
				}
			
			}
			np.getIn().add(inn);
			System.out.println(" ");
			
		}
		/*
		printHeader("#Latency Matrix:");
		for (HostReader sri: set) {
			System.out.print("\t"+sri.getName());
		}
		System.out.println(" ");
		for (HostReader sri: set) {
			System.out.print(sri.getName());
			for (HostReader srj: set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				System.out.print("\t"+cpr.getLatency());
			}
			System.out.println(" ");
		}*/
		System.out.println("** FINISH PRINTPERFORMANCE **");
	}

	private void printCatalog() {
		System.out.println("** START PRINTCATALOG **");
		Set<VNFTypeReader> set = monitor.getVNFCatalog();
		
		// Create list of Ftype
		//List<FType> ftype_list = np.getCatalog().getFunctionaltype();		
		
		CatalogType c = new CatalogType();
		// For each VNF type print name and class
		for (VNFTypeReader vnfType_r: set) {
			//System.out.println("Type name " + vnfType_r.getName() +"\tFunc type: "+vnfType_r.getFunctionalType().value()+
								//"\tRequired Mem:"+vnfType_r.getRequiredMemory()+"\tRequired Sto:"+vnfType_r.getRequiredStorage());
			
			// Create ftype,set and add
			FType ftype = new FType();	
			ftype.setFunctionaltypeId(vnfType_r.getName());
			ftype.setFunctionalTypeName(NodeFunctionalType.valueOf(vnfType_r.getFunctionalType().toString()));
			ftype.setRequiredMemory(vnfType_r.getRequiredMemory());
			ftype.setRequiredStorage(vnfType_r.getRequiredStorage());
			c.getFunctionaltype().add(ftype);
			System.out.println("aaaa");
		}
		np.setCatalog(c);
		System.out.println("** FINISH PRINTCATALOG **");
	}

	private void printHosts() {
		System.out.println("** START PRINTHOSTS **");
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		InType intype = new InType();
		HostType ht = new HostType();
		for (HostReader host_r: set) {
			ht.setHostName(host_r.getName());
			ht.setNumberVNFs(host_r.getMaxVNFs());
			ht.setMemory(host_r.getAvailableMemory());
			ht.setDiskStorage(host_r.getAvailableStorage());
			intype.getHost().add(ht);
		}
		np.getIn().add(intype);
		System.out.println("** FINISH PRINTHOSTS **");
	}


	private void printNffgs() throws DatatypeConfigurationException {
		System.out.println("** START PRINTNFFG **");
		
		// Get the list of NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);
		
		// Create nffglist
		List<NffgType> nffglist = np.getNffg();
		
		// For each NFFG print related data
		for (NffgReader nffg_r: set) {
			//printHeader('%',"###Data for NF-FG " + nffg_r.getName());
			
			// Create nffgtype and set name
			NffgType nffg = new NffgType();
			nffg.setNameNffg(nffg_r.getName());
			
			// Print deploy time
			//Calendar deployTime = nffg_r.getDeployTime();
			//printHeader("#Deploy time: "+dateFormat.format(deployTime.getTime()));
			
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
				//printHeader('+',"+Node " + nr.getName() +"\tType: "+nr.getFuncType().getName()+"\t Allocated on: "+nr.getHost().getName()+
						//"\tNumber of links: "+nr.getLinks().size());
				
				// Create node,set node and add into nodelist
				NodeType node = new NodeType();
				node.setFunctionaltypeId(nr.getFuncType().getName());
				node.setNodeName(nr.getName());
				node.setHostName(nr.getHost().getName());
				nodelist.add(node);
				
				Set<LinkReader> linkSet = nr.getLinks();
				// Create linklist
				List<LinkType> linklist = node.getLink();
								
				for (LinkReader lr: linkSet) {
					System.out.println(lr.getName()+"\t"+lr.getSourceNode().getName()+"\t"+lr.getDestinationNode().getName());
				
				
				// Create link,set link and add into listlink
				LinkType link = new LinkType();
				link.setLinkName(lr.getName());
				link.setSourceNode(lr.getSourceNode().getName());
				link.setDestinationNode(lr.getDestinationNode().getName());
				linklist.add(link);
				}
			}
			
			System.out.println("###End of Nodes");
			
			//add into nffglist
			nffglist.add(nffg);
		}	
		System.out.println("** FINISH PRINTCATALOG **");
	}

/*
	private void printLine(char c) {
		System.out.println(makeLine(c));
	}

	private void printHeader(String header) {
		System.out.println(header);
	}

	private void printHeader(String header, char c) {		
		System.out.println(header);
		printLine(c);	
	}
	
	private void printHeader(char c, String header) {		
		printLine(c);	
		System.out.println(header);
	}
	
	private StringBuffer makeLine(char c) {
		StringBuffer line = new StringBuffer(132);
		
		for (int i = 0; i < 132; ++i) {
			line.append(c);
		}
		return line;
	}
*/

}
