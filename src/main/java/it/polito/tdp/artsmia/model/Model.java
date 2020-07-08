package it.polito.tdp.artsmia.model;

import java.awt.List;
import java.util.ArrayList;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private ArrayList<String> ruoli;
	private ArrayList<Artisti> artisti;
	private SimpleWeightedGraph<Artisti, DefaultWeightedEdge> grafo;

	
	public Model() {
		dao= new ArtsmiaDAO();
	}

	public ArrayList<String> getAllRuoli() {
		ruoli=dao.getAllRuoli();
		return ruoli;
	}

	public ArrayList<Collegamento> creaGrafo(String s) {
		artisti=dao.getArtistiByRuolo(s);
		grafo=new SimpleWeightedGraph<Artisti, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, artisti);
		ArrayList<Collegamento> collegamenti=new ArrayList<Collegamento>();
		int numero=0;
		for(Artisti a1:artisti) {
			for(Artisti a2:artisti) {
				if(!grafo.containsEdge(a1, a2) && !a1.equals(a2)) {
				numero=dao.getArco(a1,a2, s);
				if(numero==-1) {
					System.out.println("ERRORE!!!");
				}
				if(numero>0) {
					Graphs.addEdge(grafo, a1, a2, numero);
					collegamenti.add(new Collegamento(a1,a2,numero));
				}
			}
			}
		}
		System.out.println("grafo con "+artisti.size()+" vertici e archi"+grafo.edgeSet().size());
		
		return collegamenti;

		
	}

	public boolean okay(int artista) {
		return artisti.contains(new Artisti(artista,null));
	}
	
	
	private int max;
	private ArrayList<Artisti> massimo;

	public ArrayList<Artisti> cercaCammino(int artista) {
		Artisti ar = new Artisti(artista,null);
		ArrayList<Integer> pesi=new ArrayList<Integer>();
		pesi.clear();
		int peso=0;
		for(Artisti a:Graphs.neighborListOf(grafo, ar)) {
			peso=(int) grafo.getEdgeWeight(grafo.getEdge(ar, a));
			if(peso<1) {
				System.out.println("ERRORE");
				return null;
			}
			if(!pesi.contains(peso))
				pesi.add(peso);
		}
		ArrayList<Artisti> parziale=new ArrayList<Artisti>();
		max=0;
		int livello;
		for(int pesoRicercato: pesi) {
			livello=0;
			parziale.clear();
			parziale.add(ar);
			espandi(livello, parziale,ar, pesoRicercato);
		}
		
		System.out.println(massimo);
		return massimo;
		
	}

	private void espandi(int livello, ArrayList<Artisti> parziale, Artisti ar, int pesoRicercato) {
		ArrayList<Artisti> possibili=this.cercaPossibili(parziale,ar,pesoRicercato);
		if(possibili.size()==0) {
			if(parziale.size()>max) {
				massimo=new ArrayList<Artisti>(parziale);
				max=massimo.size();
			}
			return;
		}
		for(Artisti temp: possibili) {
		parziale.add(temp);
		espandi(livello+1,parziale,temp,pesoRicercato);
		parziale.remove(temp);
		}
	}

	private ArrayList<Artisti> cercaPossibili(ArrayList<Artisti> parziale, Artisti ar, int pesoRicercato) {
		ArrayList<Artisti> vicini=new ArrayList(Graphs.neighborListOf(grafo, ar));
		vicini.removeAll(parziale);
		if(vicini.size()!=0) {
			ArrayList<Artisti> daRimuovere=new ArrayList();
		for(Artisti temp: vicini) {
			if(!(pesoRicercato==grafo.getEdgeWeight(grafo.getEdge(ar, temp)))) {
				daRimuovere.add(temp);
			}
		}
		vicini.removeAll(daRimuovere);
		}
		return vicini;
	}

}
