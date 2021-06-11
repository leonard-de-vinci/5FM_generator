package fr.dvrc.generator5FM.struct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class DataStore extends Struct{
	private List<Concept> concepts;
	private List<Reference> references;
	private List<Query> queries;

	public DataStore() {
		super("c"+Struct.nbConcepts+"_r"+Struct.nbRows+"_k"+Struct.nbKeys, "template_5FM.xml");
		useCase ();
	}

	protected void useCase () {
		queries = new ArrayList<Query> ();
		for(int i=0; i<nbQueries;i++)
			queries.add(query());
	}

	protected Query query () {
		Query q = new Query ();
		int joins = new Long(Math.round(nbJoins[0]+(nbJoins[1]+1)*Math.random ())).intValue();
		List<Row> rows = new ArrayList<Row> ();
		if(joins > 0 && concepts.size() > 1) {
			List<Reference> refs = new ArrayList<Reference> ();
			for(int i=0; i<joins&&i<references.size(); i++) {
				findReference (q, refs, rows);
			}
		} else {
			int rand = new Double(Math.floor((concepts.size())*Math.random ())).intValue();
			rows.add(concepts.get(rand).rows.get(0));
		}
		for(Row r : rows) {
			findKeys(q, r);
		}
		return q;
	}

	protected void findReference (Query q, List<Reference> refs, List<Row> rows) {
		Reference r = null;
		while(r == null) {
			int rand = new Double(Math.floor((references.size())*Math.random ())).intValue();
			r = references.get (rand);
			if (refs.size() == 0)
				;
			else if (refs.contains (r))
				r = null;
			else if(!(rows.contains(r.source) || rows.contains(r.target)))
				r = null;
		}
		refs.add (r);
		if(!rows.contains(r.source))
			rows.add(r.source);
		if(!rows.contains(r.target))
			rows.add(r.target);
		q.add(r.keySource());
		q.add(r.keyTarget());
	}

	protected List<Row> getRows (String key){
		List<Row> rows = new ArrayList<Row> ();
		for(Concept c : concepts) {
			for(Row r : c.rows) {
				if(r.contains(key)) {
					rows.add (r);
				}
			}
		}
		return rows;
	}

	protected void findKeys (Query q, Row r) {
		int nbKeys = new Long(Math.round(nbKeysPerRow[0]+(nbKeysPerRow[1]+1)*Math.random ())).intValue();
		int loops = 0;
		while(nbKeys > 0) {
			int rand = new Double(Math.floor((r.keys.size() + 1)*Math.random ())).intValue();
			String k;
			if(rand == r.keys.size())
				k = r.ID.name;
			else
				k = r.keys.get(rand).name;
			if(q.add(k))
				nbKeys--;
			loops++;
			if(loops >= 20)
				break;
		}
	}

	@Override
	protected void generate() {
		concepts = new ArrayList<Concept> ();
		references = new ArrayList<Reference> ();
		for(int i=0;i<nbConcepts;i++) {
			concepts.add(new Concept("c"+(i+1)));
			if(i>0) {
				Row rowSource;
				if(topology == 1)
					rowSource = concepts.get(i-1).rows.get(0);
				else
					rowSource = concepts.get(0).rows.get(0);
				references.add(new Reference(rowSource, concepts.get(i).rows.get(0)));
			}
		}
	}

	@Override
	public String toString () {
		return signature ();
	}

	@Override
	public String signature() {
		StringBuffer sb = new StringBuffer (concepts.get(0).signature());
		
		for(int i=1;i<concepts.size();i++)
			sb.append(","+concepts.get(i).signature());

		if(references.size()>0) {
			sb.append(" "+references.get(0).signature());
			for(int i=1;i<references.size();i++)
				sb.append(","+references.get(i).signature());
		}
		
		return sb.toString();
	}

	@Override
	public String toXML () {
		String XML = template.replaceAll ("\\$var", name);
		StringBuffer sb = new StringBuffer ();
		for(Concept c : concepts) {
			sb.append("\n"+c.toXML());
		}
		XML = XML.replaceAll("\\$concepts", Matcher.quoteReplacement(sb.toString()));
		sb.setLength(0);
		for(Reference r : references) {
			sb.append ("\n"+r.toXML ());
		}
		XML = XML.replaceAll("\\$references", Matcher.quoteReplacement(sb.toString()));
		return XML;
	}

	public String resume () {
		StringBuffer sb = new StringBuffer (signature());
		for(Query q : queries)
			sb.append("\n\t"+q);
		return sb.toString ();
	}

	public void writeFile () {
		String folder = "data/output/c"+Struct.nbConcepts+"/r"+Struct.nbRows+"/k"+Struct.nbKeys;
		try {
			(new File(folder)).mkdirs();

			String fileName = name+".xml";
			BufferedWriter bw = new BufferedWriter (new FileWriter(folder+"/"+fileName));
			bw.write(this.toXML());
			bw.flush();
			bw.close();

			fileName = name+"_queries.txt";
			bw = new BufferedWriter (new FileWriter(folder+"/"+fileName));
			bw.write(queries.get(0).toString());
			for(int i=1;i<queries.size();i++)
				bw.write("\n"+queries.get(i).toString());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
