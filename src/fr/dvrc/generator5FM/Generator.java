package fr.dvrc.generator5FM;

import fr.dvrc.generator5FM.struct.DataStore;
import fr.dvrc.generator5FM.struct.Struct;

public class Generator {

	public static void main(String[] args) {
		Struct.nbRows = 1;
		for(int i=1;i<10;i++) {
			Struct.nbConcepts = i;
			for(int j=1;j<10;j++) {
				Struct.nbKeys = j;
				DataStore dataStore = new DataStore();
				dataStore.writeFile();
			}
		}
	}

}
