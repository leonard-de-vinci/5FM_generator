package fr.dvrc.generator5FM.struct;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class DataStore extends Struct{
	private List<Concept> concepts;
	private List<Reference> references;

	public DataStore() {
		super("c"+Struct.nbConcepts+"_r"+Struct.nbRows+"_k"+Struct.nbKeys, "template_5FM.xml");
	}

	@Override
	protected void generate() {
		concepts = new ArrayList<Concept> ();
		references = new ArrayList<Reference> ();
		for(int i=0;i<nbConcepts;i++) {
			concepts.add(new Concept("c"+(i+1)));
			if(i>0) {
				KeyOid ID = concepts.get(i-1).getOID();
				Key k = concepts.get(i).getLastKey();
				references.add(new Reference(ID, k));
			}
		}
	}

	@Override
	public String toString () {
		String XML = template.replaceAll ("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		for(Concept c : concepts) {
			sb.append(c.toString());
		}
		XML = XML.replaceAll("\\$concepts", Matcher.quoteReplacement(sb.toString()));
		sb.setLength(0);
		for(Reference r : references) {
			sb.append (r.toString ());
		}
		XML = XML.replaceAll("\\$references", Matcher.quoteReplacement(sb.toString()));
		return XML;
	}

	public void writeFile () {
		String fileName = name+".xml";
		try {
			BufferedWriter bw = new BufferedWriter (new FileWriter("data/output/"+fileName));
			bw.write(this.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
