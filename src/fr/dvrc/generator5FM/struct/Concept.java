package fr.dvrc.generator5FM.struct;

import java.util.ArrayList;
import java.util.List;

public class Concept extends Struct {
	protected List<Row> rows;

	public Concept(String conceptName) {
		super(conceptName, "template_concept.xml");
	}

	@Override
	protected void generate() {
		rows = new ArrayList<Row> ();
		for(int i=0;i<nbRows;i++) {
			rows.add(new Row(name+"_"+(i+1)));
		}
	}

	public KeyOid getOID () {
		return rows.get(0).ID;
	}

	public Key getLastKey () {
		return rows.get(0).getLastKey();
	}

	public String toString () {
		String XML = template.replaceAll("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		for(Row r : rows) {
			sb.append(r.toString());
		}
		return XML.replaceAll("\\$rows", sb.toString());
	}
}
