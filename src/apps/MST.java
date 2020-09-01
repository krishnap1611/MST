package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		PartialTreeList List = new PartialTreeList();
		int j=0;
		while(j < graph.vertices.length){
			PartialTree G = new PartialTree(graph.vertices[j]);
			graph.vertices[j].parent = G.getRoot();
			MinHeap mh = G.getArcs();
			Vertex.Neighbor num = graph.vertices[j].neighbors;
			while(num != null){
				Vertex v1 = graph.vertices[j];
				Vertex v2 = num.vertex;
				mh.insert(new PartialTree.Arc(v1, v2, num.weight));
				num=num.next;
			}
			List.append(G);
			j=j+1;
		}
		Iterator<PartialTree> iter = List.iterator();
		   while (iter.hasNext()) {
		       PartialTree pt = iter.next();
		       System.out.println(pt.toString());
		}
		return List;
	}


	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> arcList = new ArrayList<>();
		while (ptlist.size() > 1) {
			PartialTree pT1 = ptlist.remove();
			 MinHeap<PartialTree.Arc> pT2 = pT1.getArcs();
			 PartialTree.Arc parTreeArc = pT2.deleteMin();
			 
			 while (parTreeArc != null) {   
				 Vertex vertex1 = parTreeArc.v1;
	             Vertex vertex2 = parTreeArc.v2;
	             PartialTree TT;
	             TT = ptlist.removeTreeContaining(vertex1);
	             if (TT == null) {
	            	 TT = ptlist.removeTreeContaining(vertex2);
	             }
	             if (TT != null) {  
	            	 pT1.merge(TT);
	            	 arcList.add(parTreeArc);
	                 ptlist.append(pT1); 
	                 break;
	             }
	             parTreeArc = pT2.deleteMin();
			 }
		 }
		 return arcList;
	}
}
