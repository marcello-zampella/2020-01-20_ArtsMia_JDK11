package it.polito.tdp.artsmia.model;

import java.util.ArrayList;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Model m = new Model();
		Artisti a=new Artisti(1, "giambiero");
		Artisti b=new Artisti(1, null);
		ArrayList<Artisti>ar=new ArrayList<Artisti>();
		ArrayList<Artisti>ab=new ArrayList<Artisti>();
		ab.add(b);
		ar.add(a);
		ar.removeAll(ab);
		System.out.println(ar);
		
	}

}
