package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.*;
import javax.xml.bind.*;
import javax.xml.validation.*;
import java.io.*;
import java.util.*;
import it.polito.dp2.NFV.sol1.jaxb.*;
import org.xml.sax.SAXException;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class NfvReaderImpl implements NfvReader {
	
	private NetworkProvider np = null;
	//private Set<HostReader>  hosts;
	//private Set<NffgReader> nffgp;
	//private Set<FType> vnfs;
	private ConnectionPerformanceReader cpr;
	
	//MYLIST
    //private List<NffgType> nffgTypes = null;
	//private Set<HostReaderImpl> host_list;
    //private Set<VNFTypeReaderImpl> vnf_list;
    
    private HashMap<String, NffgReaderImpl> nffgs = new HashMap<>();
    private HashMap<String, HostReaderImpl> host_list = new HashMap<>();
    private HashMap<String, VNFTypeReaderImpl> vnf_list = new HashMap<>();
    private HashMap<String, ConnectionPerformanceReaderImpl> cpr_list = new HashMap<>();
	
	
	private static final String XSD_FOLDER = "xsd/";
    private static final String XSD_FILE = "nfvInfo.xsd";
    private static final String PACKAGE = "it.polito.dp2.NFV.sol1.jaxb";
    
    
	public NfvReaderImpl() throws NfvReaderException {
		
		
		try {
            // take file name from the property
            String fileName = System.getProperty("it.polito.dp2.NFV.sol1.NfvInfo.file");
            if (fileName == null) {
                throw new NfvReaderException("Property is null!");
            }
            np = unmarshallDocument(new File(fileName));
        } catch (SAXException e) {
            e.printStackTrace();
            throw new NfvReaderException("SAXException (!): " + e.getMessage());
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new NfvReaderException("JAXBException (!): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new NfvReaderException("IllegalArgumentException (!): " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new NfvReaderException("NullPointerException (!): " + e.getMessage());
        }
		
		
		
		List<NffgType> nffgTypes = this.np.getNffg();
					
		if(nffgTypes.isEmpty())
			throw new NfvReaderException("No Nffgs are present");
		
	
		
		//Ciclo Host 	
		for(HostType host : this.np.getIn().getHost()) {
			HostReaderImpl hri = new HostReaderImpl(host);
			host_list.put(host.getHostName(), hri);
		}
		//DA CONTROLLARE PER VEDERE SE METTERLA O MENO
		if(host_list.isEmpty())
			throw new NfvReaderException("No Hosts are present");
	
		
		//Ciclo FType
		for(FType ft : this.np.getCatalog().getFunctionaltype()) {
			VNFTypeReaderImpl myvnf = new VNFTypeReaderImpl(ft);
			vnf_list.put(ft.getFunctionaltypeId(),myvnf);
		}
		if(vnf_list.isEmpty())
			throw new NfvReaderException("No vnfs are present");
		
		
		for(NffgType nffgType:nffgTypes){

			
			NffgReaderImpl nffgImpl = new NffgReaderImpl(nffgType.getNameNffg(),nffgType.getDeployTime().toGregorianCalendar());
			ArrayList<NodeReaderImpl> nodes = new ArrayList<NodeReaderImpl>();
			
			
			for(NodeType n:nffgType.getNode()){
				HostReaderImpl host_reader = cercaHost(n);
            		VNFTypeReaderImpl vnfri = cercaVnf(n);
            		
            		NodeReaderImpl newNode = new NodeReaderImpl(n.getNodeName(),host_reader,nffgImpl,vnfri);			
				
				if(!n.getLink().isEmpty()){
					
					for(LinkType l: n.getLink()){
						NodeReaderImpl dstnode = cercaNodo(l.getDestinationNode(),nffgType,nffgImpl);
						if(dstnode == null)
							throw new NfvReaderException("Destination Node not found");
						
						LinkReaderImpl link_impl = new LinkReaderImpl(l.getLinkName(),newNode,dstnode,l.getMaxLatency(),l.getMinThroughput());
						newNode.addLink(link_impl);
					}
					
					/* Vado a vedere se ci sono link che puntano a un destNode col mio stesso Id, ossia
					 * se esistono link che puntano a me che sono in uno stato incosistente.
					 * In tal caso vado ad aggiornare tale link.
					 */
					
					for(int i=0;i<nodes.size();i++){
						
						NodeReaderImpl tmp = nodes.get(i);
						if(!(tmp.getLinks().isEmpty())){
							for(LinkReader lr:tmp.getLinks()){
								if(lr.getDestinationNode().getName().equals(newNode.getName()))
									if(lr.getDestinationNode().getFuncType()==null){
										((LinkReaderImpl)nodes.get(i).getLink(lr.getName())).setDestinationNode(newNode);
										
									}
							}
						}
						
					}	
					
					
				}
				
						
				//Aggiungo tale nodo all'insieme
				nodes.add(newNode);
				
				//Aggiungo tale nodo all'nffg
				nffgImpl.addNode(newNode);
				
			}
			
			//Aggiungo l'nffg all'insieme degli Nffg del ProviderNetwork(Root XML)
			this.nffgs.put(nffgType.getNameNffg(),nffgImpl);
			
		}
		
		//HOST
		for (Map.Entry<String, HostReaderImpl> host_r : host_list.entrySet()) {
			for(Map.Entry<String, NffgReaderImpl> nffg : nffgs.entrySet()) {
				for(NodeReader node : nffg.getValue().getNodes()) {
					//System.out.println("NON ENTRO IF");
					if(node.getHost().getName().equals(host_r.getValue().getName())) {	
						//System.out.println("ENTRO IF");						
						host_r.getValue().addNode((NodeReaderImpl)node);		
					}					
				}
			}
		}
		System.out.println("DOPO FUNZIONE: " + host_list.size());
        
        
        //PERFORMANCE
		for(Map.Entry<String, HostReaderImpl> i : host_list.entrySet()) {
			for(PerformanceType pf : this.np.getIn().getPerformance()) {
				//if((i.getValue().getName().equals(pf.getSourceHost()) || i.getValue().getName().equals(pf.getDestinationHost())) && (!pf.getSourceHost().equals(pf.getDestinationHost()))) {
					ConnectionPerformanceReaderImpl cpri = new ConnectionPerformanceReaderImpl(pf);
					String var = pf.getSourceHost() + "-" + pf.getDestinationHost();
					cpr_list.put(var,cpri);
				//}
			}
		}
            

	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader arg0, HostReader arg1)  {
		
		String var = arg0.getName() + "-" + arg1.getName();	
		return this.cpr_list.get(var);			
			
	}

	@Override
	public HostReader getHost(String arg0) {
		for(Map.Entry<String, HostReaderImpl> hr : host_list.entrySet()){
			if(hr.getValue().getName().equals(arg0))
				return (HostReader) hr;
		}
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		return new LinkedHashSet<HostReader>(this.host_list.values());
	}

	@Override
	public NffgReader getNffg(String arg0) {
		for(Map.Entry<String, NffgReaderImpl> nffgr:this.nffgs.entrySet()){
			if(nffgr.getValue().getName().equals(arg0))
				return (NffgReader) nffgr;
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar arg0) {
		return new LinkedHashSet<NffgReader>(this.nffgs.values());
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		return new LinkedHashSet<VNFTypeReader>(this.vnf_list.values());
	}
	
	 private NetworkProvider unmarshallDocument(File inputFile) throws JAXBException, SAXException, IllegalArgumentException {
	        JAXBContext myJAXBContext = JAXBContext.newInstance(PACKAGE);

	        SchemaFactory mySchemaFactory;
	        Schema mySchema;

			/*
	         * - creating the XML schema to validate the XML file before read it -
			 */
	        mySchemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	        mySchema = mySchemaFactory.newSchema(new File(XSD_FOLDER + XSD_FILE));

	        Unmarshaller myUnmarshaller = myJAXBContext.createUnmarshaller();
	        myUnmarshaller.setSchema(mySchema);

	        return (NetworkProvider) myUnmarshaller.unmarshal(inputFile);
	    }
	 
	 /*METHOD*/
	 
	 /*SEARCH NODE METHOD*/
	 private NodeReaderImpl cercaNodo(String node, NffgType nffg, NffgReaderImpl nffgri) {
	        for (NodeType nodeType : nffg.getNode()) {
	            if (nodeType.getNodeName().equals(node)) {
	            	HostReaderImpl hr = cercaHost(nodeType);
	            	VNFTypeReaderImpl vnf = cercaVnf(nodeType);
	                return new NodeReaderImpl(node,hr,nffgri,vnf);
	            }
	        }

	        return null;
	    }
	 
	 /*SEARCH HOST METHOD*/
	 private HostReaderImpl cercaHost(NodeType node) {
		 for(Map.Entry<String, HostReaderImpl> host : host_list.entrySet()  ) {
			 if(node.getHostName().equals(host.getValue().getName())) {
				 return host.getValue();
			 }
		 }
		 
		 return null;
		 
	 }
	 
	 /*SEARCH VNF METHOD*/
	 private VNFTypeReaderImpl cercaVnf( NodeType node) {
		 for(Map.Entry<String, VNFTypeReaderImpl> vnf : vnf_list.entrySet()) {			 		 
			 if(vnf.getValue().getName().equals(node.getFunctionaltypeId())) {
				 return vnf.getValue();
			 }
		 }
		 return null;
	 }
		 
}
