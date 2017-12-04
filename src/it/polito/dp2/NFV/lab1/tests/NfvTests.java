package it.polito.dp2.NFV.lab1.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polito.dp2.NFV.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class NfvTests {
	private static NfvReader referenceNfvReader;	// reference data generator
	private static NfvReader testNfvReader;		// implementation under test
	private static long testcase;
	
    class NamedEntityReaderComparator implements Comparator<NamedEntityReader> {
        public int compare(NamedEntityReader f0, NamedEntityReader f1) {
        	return f0.getName().compareTo(f1.getName());
        }
    }

	// method for comparing two strings that should be non-null    
	public void compareString(String rs, String ts, String meaning) {
		assertNotNull(rs);
		assertNotNull("null "+meaning, ts);
        assertEquals("wrong "+meaning, rs, ts);		
	}
	
	// method for comparing two time instants that should be non-null with precision of 1 minute
	public void compareTime(Calendar rc, Calendar tc, String meaning) {
		assertNotNull(rc);
		assertNotNull("null "+meaning, tc);
		
		// Compute lower and upper bounds for checking with precision of 1 minute
		Calendar upperBound, lowerBound;
		upperBound = (Calendar)rc.clone();
		upperBound.add(Calendar.MINUTE, 1);
		lowerBound = (Calendar)rc.clone();
		lowerBound.add(Calendar.MINUTE, -1);
		
		// Compute the condition to be checked
		boolean condition = tc.after(lowerBound) && tc.before(upperBound);
		
		assertTrue("wrong "+meaning, condition);
	}
	
	// method for comparing two float with precision of 0.1
	public void compareFloat(float rc, float tc, String meaning) {
		double precision=0.1;
		
		// Compute the condition to be checked
		boolean condition = (tc>(rc-precision)) && (tc<(rc+precision));
		
		assertTrue("wrong "+meaning, condition);
	}
	
	/**
	 * Starts the comparison of two sets of elements that extend NamedEntiryReader.
	 * This method already makes some comparisons that are independent of the type
	 * (e.g. the sizes of the sets must match). Then the method arranges the set
	 * elements into ordered sets (TreeSet) and returns a pair of iterators that
	 * can be used later on for one-to-one matching of the set elements
	 * @param rs	the first set to be compared
	 * @param ts	the second set to be compared
	 * @param type	a string that specified the type of data in the set (will appear in test messages)
	 * @return		a list made of two iterators to be used for one-to-one comparisons of the set elements
	 */
	public <T extends NamedEntityReader> List<Iterator<T>> startComparison(Set<T> rs, Set<T> ts, String type) {
		// if one of the two sets is null while the other isn't null, the test fails
		if ((rs == null) && (ts != null) || (rs != null) && (ts == null)) {
		    fail("returned set of "+type+" was null when it should be non-null or vice versa");
		    return null;
		}

		// if both sets are null, there are no data to compare, and the test passes
		if ((rs == null) && (ts == null)) {
		    assertTrue("there are no "+type+"!", true);
		    return null;
		}
		
		// check that the number of elements matches
		assertEquals("wrong Number of "+type, rs.size(), ts.size());
		
		// create TreeSets of elements, using the comparator for sorting, one for reference and one for implementation under test 
		TreeSet<T> rts = new TreeSet<T>(new NamedEntityReaderComparator());
		TreeSet<T> tts = new TreeSet<T>(new NamedEntityReaderComparator());
   
		rts.addAll(rs);
		tts.addAll(ts);
		
		// get iterators and store them in a list
		List<Iterator<T>> list = new ArrayList<Iterator<T>>();
		list.add(rts.iterator());
		list.add(tts.iterator());
		
		// return the list
		return list;

	}

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	// Create reference data generator
        System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.Random.NfvReaderFactoryImpl");

        referenceNfvReader = NfvReaderFactory.newInstance().newNfvReader();

        // Create implementation under test
        System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.sol1.NfvReaderFactory");

        testNfvReader = NfvReaderFactory.newInstance().newNfvReader();
        
        // read testcase property
        Long testcaseObj = Long.getLong("it.polito.dp2.NFV.Random.testcase");
        if (testcaseObj == null)
        	testcase = 0;
        else
        	testcase = testcaseObj.longValue();
    }
    
    @Before
    public void setUp() throws Exception {
        assertNotNull("Internal tester error during test setup: null reference", referenceNfvReader);
        assertNotNull("Could not run tests: the implementation under test generated a null NfvReader", testNfvReader);
    }

    @Test
    public final void testGetHosts() {
		// call getHosts on the two implementations
		Set<HostReader> rss = referenceNfvReader.getHosts();
		Set<HostReader> tss = testNfvReader.getHosts();
		
		// compare the returned sets
		List<Iterator<HostReader>> list = startComparison(rss, tss, "Hosts");
		if (list!=null) {
			Iterator<HostReader> ri = list.get(0);
			Iterator<HostReader> ti = list.get(1);
			
			while (ri.hasNext() && ti.hasNext()) {
				compareHostReader(ri.next(),ti.next());
			}
		}
    }
    
    // private method for comparing two HostReader objects
	private void compareHostReader(HostReader rsr, HostReader tsr) {
		// check the HostReaders are not null
		assertNotNull("internal tester error: null server reader", rsr);
        assertNotNull("unexpected null server reader", tsr);
        
        System.out.println("Comparing server "+rsr.getName());

        // check the HostReaders return the same data 
        compareString(rsr.getName(), tsr.getName(), "server name");       
		assertEquals("wrong available memory in server", rsr.getAvailableMemory(), tsr.getAvailableMemory());
		assertEquals("wrong available storage in server", rsr.getAvailableStorage(), tsr.getAvailableStorage());
		assertEquals("wrong max vnfs in server", rsr.getMaxVNFs(), tsr.getMaxVNFs());
		List<Iterator<NodeReader>> list = startComparison(rsr.getNodes(), tsr.getNodes(), "Nodes");
		if (list!=null) {
			Iterator<NodeReader> ri = list.get(0);
			Iterator<NodeReader> ti = list.get(1);
			
			while (ri.hasNext() && ti.hasNext()) {
				compareNodeReader(ri.next(),ti.next());
			}
		}
	}

    @Test
    public final void testGetNffgs() {
		// call getNffgs on the two implementations
		Set<NffgReader> rns = referenceNfvReader.getNffgs(null);
		Set<NffgReader> tns = testNfvReader.getNffgs(null);
		
		// compare the returned sets
		List<Iterator<NffgReader>> list = startComparison(rns, tns, "Nffgs");
		if (list!=null) {
			Iterator<NffgReader> ri = list.get(0);
			Iterator<NffgReader> ti = list.get(1);
			
			while (ri.hasNext() && ti.hasNext()) {
				compareNffgReader(ri.next(),ti.next());
			}
		}		
    }
    
    // private method for comparing two NffgReader objects
	private void compareNffgReader(NffgReader rnr, NffgReader tnr) {
		// check the NffgReaders are not null
		assertNotNull("internal tester error: null nffg reader", rnr);
        assertNotNull("unexpected null nffg reader", tnr);
        
        System.out.println("Comparing nffg "+rnr.getName());

        // check the NffgReaders return the same data
        compareString(rnr.getName(), tnr.getName(), "nffg name");
		if (testcase == 2) // time checking only in this case
			compareTime(rnr.getDeployTime(), tnr.getDeployTime(), "update time");
		List<Iterator<NodeReader>> list = startComparison(rnr.getNodes(), tnr.getNodes(), "Nodes");
		if (list!=null) {
			Iterator<NodeReader> ri = list.get(0);
			Iterator<NodeReader> ti = list.get(1);
			
			while (ri.hasNext() && ti.hasNext()) {
				compareNodeReader(ri.next(),ti.next());
			}
		}
	}

	// private method for comparing two NodeReader objects
	private void compareNodeReader(NodeReader rnr, NodeReader tnr) {
		// check the NodeReaders are not null
		assertNotNull("internal tester error: null node reader", rnr);
        assertNotNull("a null NodeReader has been found", tnr);
        
        System.out.println("Comparing node " + rnr.getName());
        
        // check the NodeReaders return the same data
        compareString(rnr.getName(), tnr.getName(), "node name");
        compareFuncType (rnr.getFuncType(), tnr.getFuncType());
        if (rnr.getHost()==null)
            assertNull("wrong getHost", tnr.getHost());
        else {
    		assertNotNull("Internal tester error: null server reader", rnr.getHost());
            assertNotNull("wrong getHost", tnr.getHost());
        	assertEquals(rnr.getHost().getName(), tnr.getHost().getName());
        }
		List<Iterator<LinkReader>> list = startComparison(rnr.getLinks(), tnr.getLinks(), "Links");
		if (list!=null) {
			Iterator<LinkReader> ri = list.get(0);
			Iterator<LinkReader> ti = list.get(1);
			
			while (ri.hasNext() && ti.hasNext()) {
				compareLinkReader(ri.next(),ti.next());
			}
		}
	}
	
	// private method for comparing two VNFTypeReader objects
	private void compareFuncType(VNFTypeReader rvr, VNFTypeReader tvr) {
		// check the VNFTypeReaders are not null
		assertNotNull("internal tester error: null node reader", rvr);
        assertNotNull("a null NodeReader has been found", tvr);
        
        System.out.println("Comparing VNFType " + rvr.getName());
		
		compareString(rvr.getName(), tvr.getName(), "vnf type");
		assertEquals("wrong functional type", rvr.getFunctionalType(), tvr.getFunctionalType());
		assertEquals("wrong required memory in vnf type", rvr.getRequiredMemory(), tvr.getRequiredMemory());
		assertEquals("wrong required storage in vnf type", rvr.getRequiredStorage(), tvr.getRequiredStorage());
	}
	
	// private method for comparing two LinkReader objects
	private void compareLinkReader(LinkReader rlr, LinkReader tlr) {
		// check the LinkReaders are not null
		assertNotNull("internal tester error: null link reader", rlr);
        assertNotNull("a null LinkeReader has been found", tlr);
        
        System.out.println("Comparing node " + rlr.getName());

        // check the LinkReaders return the same data
        compareString(rlr.getName(), tlr.getName(), "node name");
        
		assertNotNull("internal tester error: null source node returned", rlr.getSourceNode());
        assertNotNull("a null source node has been returned", tlr.getSourceNode());
        compareString(rlr.getSourceNode().getName(), tlr.getSourceNode().getName(), "source node");
        
		assertNotNull("internal tester error: null destination node returned", rlr.getDestinationNode());
        assertNotNull("a null destination node has been returned", tlr.getDestinationNode());
        compareString(rlr.getDestinationNode().getName(), tlr.getDestinationNode().getName(), "destination node");
	}

    @Test
    public final void testGetPerformance() {
    	// get reference list of servers
		Set<HostReader> rss = referenceNfvReader.getHosts();
    	// for each pair of HostReader
    	for (HostReader server1:rss)
    		for(HostReader server2:rss) {
    			// call getConnectionPerformance on the two implementations
    			ConnectionPerformanceReader rpr = referenceNfvReader.getConnectionPerformance(server1, server2);
    			ConnectionPerformanceReader tpr = testNfvReader.getConnectionPerformance(server1, server2);
    			compareConnectionPerformanceReader(rpr, tpr);
    		}
    }

	private void compareConnectionPerformanceReader(ConnectionPerformanceReader rpr, ConnectionPerformanceReader tpr) {
		// check the ConnectionPerformanceReaders are not null
		//assertNotNull("internal tester error: null ConnectionPerformanceReader", rpr);
        //assertNotNull("a null ConnectionPerformanceReader has been found", tpr);
        
        // check the ConnectionPerformanceReaders return the same data
		if(rpr!=null) {
			assertNotNull("a null ConnectionPerformanceReader has been found", tpr);
			compareFloat(rpr.getThroughput(), tpr.getThroughput(),"throughput");
			assertEquals("wrong latency", rpr.getLatency(), tpr.getLatency());
		}
	}

}
