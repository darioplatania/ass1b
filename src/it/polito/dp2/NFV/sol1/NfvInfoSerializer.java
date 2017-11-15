package it.polito.dp2.NFV.sol1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
import java.util.*;

public class NfvInfoSerializer {
	
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
	}
	
	public NfvInfoSerializer(NfvReader monitor) {
		super();
		this.monitor = monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		this.np = new NetworkProvider();
	}

	/**
	 * @param args
	 * @throws DatatypeConfigurationException 
	 */
	public static void main(String[] args) throws DatatypeConfigurationException {
		NfvInfoSerializer wf;
		try {
			wf = new NfvInfoSerializer();
			wf.printAll();
		} catch (NfvReaderException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		}
	}


	public void printAll() throws DatatypeConfigurationException {
		printLine(' ');
		printHosts();
		printCatalog();
		printNffgs();
		printPerformance();
	}


	private void printPerformance() {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		/* Print the header of the table */
		printHeader('#',"#Information about the Performance of Host to Host connections");
		printHeader("#Throughput Matrix:");
		for (HostReader sri: set) {
			System.out.print("\t"+sri.getName());
		}
		System.out.println(" ");
		for (HostReader sri: set) {
			System.out.print(sri.getName());
			for (HostReader srj: set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				System.out.print("\t"+cpr.getThroughput());
			}
			System.out.println(" ");
		}
		printBlankLine();
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
		}

	}

	private void printCatalog() {
		Set<VNFTypeReader> set = monitor.getVNFCatalog();
		
		// Create list of Ftype
		List<FType> ftype_list = np.getCatalog().getFunctionaltype();
		
		/* Print the header of the table */
		printHeader('#',"#Information about the CATALOG of VNFs");
		printHeader("#Number of VNF types: "+set.size());
		printHeader("#List of VNF types:");

		// For each VNF type print name and class
		for (VNFTypeReader vnfType_r: set) {
			System.out.println("Type name " + vnfType_r.getName() +"\tFunc type: "+vnfType_r.getFunctionalType().value()+
								"\tRequired Mem:"+vnfType_r.getRequiredMemory()+"\tRequired Sto:"+vnfType_r.getRequiredStorage());
			
			// Create ftype,set and add
			FType ftype = new FType();	
			ftype.setFunctionalTypeName(NodeFunctionalType.valueOf(vnfType_r.getName()));
			ftype.setRequiredMemory(vnfType_r.getRequiredMemory());
			ftype.setRequiredStorage(vnfType_r.getRequiredStorage());
			ftype.setFunctionaltypeId(vnfType_r.getFunctionalType().value());
			
			ftype_list.add(ftype);
		}
		printBlankLine();
	}

	private void printHosts() {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		/* Print the header of the table */
		printHeader('#',"#Information about HOSTS");
		printHeader("#Number of Hosts: "+set.size());
		printHeader("#List of Hosts:");
		
		// For each Host print related data
		for (HostReader host_r: set) {
			printHeader('%',"###Data for Host " + host_r.getName());
			
			// Print maximum number of nodes
			printHeader("#Maximum number of nodes: "+host_r.getMaxVNFs());

			// Print available memory
			printHeader("#Available memory: "+host_r.getAvailableMemory());
			
			// Print available storage
			printHeader("#Available storage: "+host_r.getAvailableStorage());	

			// Print allocated nodes
			Set<NodeReader> nodeSet = host_r.getNodes();
			printHeader("#Number of Allocated Nodes: "+nodeSet.size());
			printHeader("#List of Allocated Nodes:");
			for (NodeReader nr: nodeSet)
				System.out.println("Node " + nr.getName() +"\tType: "+nr.getFuncType().getName());
			System.out.println("###End of Allocated nodes");
		}
		printBlankLine();
	}

	private void printBlankLine() {
		System.out.println(" ");
	}

	private void printNffgs() throws DatatypeConfigurationException {
		// Get the list of NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);
		// Create nffglist
		List<NffgType> nffglist = np.getNffg();
		
		/* Print the header of the table */
		printHeader('#',"#Information about NF-FGs");
		printHeader("#Number of NF-FGs: "+set.size());
		printHeader("#List of NF-FGs:");	
		
		// For each NFFG print related data
		for (NffgReader nffg_r: set) {
			printHeader('%',"###Data for NF-FG " + nffg_r.getName());
			
			// Create nffgtype and set name
			NffgType nffg = new NffgType();
			nffg.setNameNffg(nffg_r.getName());
			
			// Print deploy time
			Calendar deployTime = nffg_r.getDeployTime();
			printHeader("#Deploy time: "+dateFormat.format(deployTime.getTime()));
			
			// Set deploytime for nffg
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(nffg_r.getDeployTime().getTime());
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			nffg.setDeployTime(xgc);
			
			// Print nodes
			Set<NodeReader> nodeSet = nffg_r.getNodes();
			// Create nodelist
			List<NodeType> nodelist = nffg.getNode();
			printHeader("#Number of Nodes: "+nodeSet.size());
			printHeader("#List of Nodes: ");
			for (NodeReader nr: nodeSet) {
				printHeader('+',"+Node " + nr.getName() +"\tType: "+nr.getFuncType().getName()+"\t Allocated on: "+nr.getHost().getName()+
						"\tNumber of links: "+nr.getLinks().size());
				
				// Create node,set node and add into nodelist
				NodeType node = new NodeType();
				node.setFunctionaltypeId(nr.getFuncType().getName());
				node.setNodeName(nr.getName());
				node.setHostName(nr.getHost().getName());
				nodelist.add(node);
				
				Set<LinkReader> linkSet = nr.getLinks();
				// Create linklist
				List<LinkType> linklist = node.getLink();
				
				System.out.println("+List of Links for node "+nr.getName());
				printHeader("Link name \tsource \tdestination",'-');
				for (LinkReader lr: linkSet) {
					System.out.println(lr.getName()+"\t"+lr.getSourceNode().getName()+"\t"+lr.getDestinationNode().getName());
				printLine('-');
				
				// Create link,set link and add into listlink
				LinkType link = new LinkType();
				link.setLinkName(lr.getName());
				link.setSourceNode(lr.getSourceNode().getName());
				link.setDestinationNode(lr.getDestinationNode().getName());
				linklist.add(link);
				}
			}
			printLine('+');
			System.out.println("###End of Nodes");
			
			//add into nffglist
			nffglist.add(nffg);
		}	
		printBlankLine();
	}
	
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


}
