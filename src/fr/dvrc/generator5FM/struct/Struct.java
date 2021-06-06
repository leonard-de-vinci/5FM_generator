package fr.dvrc.generator5FM.struct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class Struct {
	protected String template;
	protected String name;

	public static int nbConcepts;
	public static int nbRows;
	public static int nbKeys;

	public Struct(String name, String templateName) {
		template = readTemplate (templateName);
		this.name = name;
		generate();
	}

	public String readTemplate (String templateName) {
		String template = "";
		try {
			BufferedReader br = new BufferedReader (new FileReader("data/templates/"+templateName));
			String line;
			while((line = br.readLine ()) != null) {
				template += "\n"+line;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	protected abstract void generate();
	public abstract String toString();
}
