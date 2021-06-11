package fr.dvrc.generator5FM.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Row extends Struct {
	protected KeyOid ID;
	protected List<Key> keys;
	protected String keyID = null;

	public Row(String rowName) {
		super(rowName, "template_row.xml");
	}

	public Row(String rowName, String keyID) {
		super(rowName, "template_row.xml");
		this.keyID = keyID;
		if(keyID != null)
			ID = new KeyOid (keyID);		
	}

	public boolean contains (String key) {
		for(Key k : keys) {
			if(k.name.compareTo(key) == 0)
				return true;
		}
		return false;
	}

	@Override
	public void generate() {
		ID = new KeyOid(name+"_ID");
		keys = new ArrayList<Key> ();
		for(int i=0;i<nbKeys;i++) {
			keys.add(new Key(name+"_"+(i+1)));
		}
	}

	public String getKeyID () {
		return ID.name;
	}
	
	public Key getLastKey() {
		return keys.get(keys.size()-1);
	}

	@Override
	public String toString () {
		return signature ();
	}
	
	@Override
	public String toXML () {
		String XML = template.replaceAll("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		sb.append("\n"+ID.toXML());
		for(Key k : keys) {
			sb.append("\n"+k.toXML());
		}
		return XML.replaceAll("\\$keys", Matcher.quoteReplacement(sb.toString()));
	}

	@Override
	public String signature() {
		StringBuffer sb = new StringBuffer ("("+ID.signature())   ;
		
		for(int i=0;i<keys.size();i++)
			sb.append(","+keys.get(i).signature());
		sb.append(")");
		
		return sb.toString();
	}
}
