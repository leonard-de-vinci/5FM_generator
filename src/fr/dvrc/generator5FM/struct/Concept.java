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
		String keyID = null;
		for(int i=0;i<nbRows;i++) {
			if(i==0) {
				Row row = new Row(name+"_"+(i+1));
				rows.add(row);
				keyID = row.getKeyID();
			} else {
				Row row = new Row(name+"_"+(i+1), keyID);
				rows.add(row);
			}
		}
	}

	public KeyOid getOID () {
		return rows.get(0).ID;
	}

	public Key getLastKey () {
		return rows.get(0).getLastKey();
	}


	@Override
	public String toString () {
		return signature ();
	}

	@Override
	public String toXML () {
		String XML = template.replaceAll("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		for(Row r : rows) {
			sb.append("\n"+r.toXML());
		}
		return XML.replaceAll("\\$rows", sb.toString());
	}

	@Override
	public String signature() {
		StringBuffer sb = new StringBuffer (name+"{"+rows.get(0).signature()) ;
		
		for(int i=1;i<rows.size();i++)
			sb.append(","+rows.get(i).signature());
		sb.append("}");
		
		return sb.toString();
	}
}
