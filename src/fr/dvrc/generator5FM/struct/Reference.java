package fr.dvrc.generator5FM.struct;

public class Reference extends Struct{
	Row source;
	Row target;

	public Reference(Row source, Row target) {
		super(source.ID.name+"_"+target.getLastKey().name, "template_reference.xml");
		this.source = source;
		this.target = target;
	}

	public String keySource () {
		return source.ID.name;
	}

	public String keyTarget () {
		return target.getLastKey().name;

	}
	
	@Override
	public void generate () {

	}

	@Override
	public String toString () {
		return signature ();
	}

	@Override
	public String toXML () {
		return template.replaceAll("\\$var1", name).replaceAll("\\$var2", keySource()).replaceAll("\\$var3", keyTarget());
	}

	@Override
	public String signature() {
		return "["+keySource()+"->"+keyTarget()+"]";
	}
}
