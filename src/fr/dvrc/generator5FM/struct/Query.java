package fr.dvrc.generator5FM.struct;

import java.util.ArrayList;
import java.util.List;

public class Query {
	List<String> keys = new ArrayList<String> ();
	public Query() {
		// TODO Auto-generated constructor stub
	}

	public boolean add (String key) {
		if(!keys.contains(key)) {
			keys.add(key);
			return true;
		}
		return false;
	}

	public boolean contains (String key) {
		return keys.contains (key);
	}

	public boolean contains (String key, DataStore ds) {
		if(keys.contains (key))
			return true;
		for(String k : keys) {
			List<Row> rows = ds.getRows (k);
			for(Row r : rows) {
				KeyOid oid = r.ID;
				if(oid.name.compareTo(key) == 0)
					return true;
				Key last = r.getLastKey();
				if(last.name.compareTo(key) == 0)
					return true;
			}
		}
		return false;
	}

	public String toString () {
		StringBuffer sb = new StringBuffer ();
		if(keys.size() > 0)
			sb.append(keys.get(0));
		for(int i=1; i<keys.size();i++)
			sb.append(","+keys.get(i));
		return sb.toString();
	}
}
