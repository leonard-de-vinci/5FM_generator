package fr.dvrc.generator5FM.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Row extends Struct {
	protected KeyOid ID;
	protected List<Key> keys;

	public Row(String rowName) {
		super(rowName, "template_row.xml");
	}

	@Override
	public void generate() {
		ID = new KeyOid(name+"_ID");
		keys = new ArrayList<Key> ();
		for(int i=0;i<nbKeys;i++) {
			keys.add(new Key(name+"_"+(i+1)));
		}
	}

	public Key getLastKey() {
		return keys.get(keys.size()-1);
	}

	public String toString () {
		String XML = template.replaceAll("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		sb.append(ID.toString());
		for(Key k : keys) {
			sb.append(k.toString());
		}
		return XML.replaceAll("\\$keys", Matcher.quoteReplacement(sb.toString()));
	}
}
