package fr.dvrc.generator5FM;

import java.io.BufferedReader;
import java.io.FileReader;

import fr.dvrc.generator5FM.struct.DataStore;
import fr.dvrc.generator5FM.struct.Struct;

public class Generator {

	public static int [] nbConcepts;
	public static int [] nbKeys;
	public static int [] nbRows;

	public static void config () {
		try {
			BufferedReader br = new BufferedReader (new FileReader ("config.txt"));;
			String line;
			int min, max;
			while ((line = br.readLine ()) != null) {
				String [] opt = line.split("\t");
				if(opt.length >= 2) {
					switch(opt[0]) {
					case "nbConcepts":
						min = new Integer(opt[1]);
						max = new Integer(opt[2]);
						nbConcepts = new int [max - min + 1];
						for(int i=0;i<nbConcepts.length;i++)
							nbConcepts[i] = min+i;
						break;
					case "nbKeys":
						min = new Integer(opt[1]);
						max = new Integer(opt[2]);
						nbKeys = new int [max - min + 1];
						for(int i=0;i<nbKeys.length;i++)
							nbKeys[i] = min+i;
						break;
					case "nbRows":
						min = new Integer(opt[1]);
						max = new Integer(opt[2]);
						nbRows = new int [max - min + 1];
						for(int i=0;i<nbRows.length;i++)
							nbRows[i] = min+i;
						break;
					case "topology":
						Struct.topology = new Integer(opt[1]);
						break;
					case "nbQueries":Struct.nbQueries = new Integer(opt[1]);break;
					case "nbJoins":
						min = new Integer(opt[1]);
						max = new Integer(opt[2]);
						Struct.nbJoins = new int [max - min + 1];
						for(int i=0;i<Struct.nbJoins.length;i++)
							Struct.nbJoins[i] = min+i;
						break;
					case "nbKeysPerRow":
						min = new Integer(opt[1]);
						max = new Integer(opt[2]);
						Struct.nbKeysPerRow = new int [max - min + 1];
						for(int i=0;i<Struct.nbKeysPerRow.length;i++)
							Struct.nbKeysPerRow[i] = min+i;
						break;
					}
				}
			}
			br.close();
			System.out.println("Nb Concepts: "+nbConcepts[0]+"-"+nbConcepts[nbConcepts.length-1]
					+", Nb Rows: "+nbRows[0]+"-"+nbRows[nbRows.length-1]
					+", Nb keys: "+nbKeys[0]+"-"+nbKeys[nbKeys.length-1]
					+", topology: "+(Struct.topology==1?"line":"star")
					+", Nb queries: "+Struct.nbQueries
					+", Nb joins: "+Struct.nbJoins[0]+"-"+Struct.nbJoins[Struct.nbJoins.length-1]
					+", Nb keys per row: "+Struct.nbKeysPerRow[0]+"-"+Struct.nbKeysPerRow[Struct.nbKeysPerRow.length-1]
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		config();
		for(int i=0;i<nbConcepts.length;i++) {
			Struct.nbConcepts = nbConcepts[i];
			for(int j=0;j<nbRows.length;j++) {
				Struct.nbRows = nbRows[j];
				for(int k=0;k<nbKeys.length;k++) {
					Struct.nbKeys = nbKeys[k];
					DataStore dataStore = new DataStore();
					dataStore.writeFile();
					System.out.println(dataStore.resume());
				}
			}
		}
	}

}
