package fr.dvrc.generator5FM.struct;

public class Reference extends Struct{
	protected String var2;
	protected String var3;

	public Reference(KeyOid source, Key target) {
		super(source.name+"_"+target.name, "template_reference.xml");
		var2 = source.name;
		var3 = target.name;
	}

	@Override
	public void generate () {

	}

	public String toString () {
		return template.replaceAll("\\$var1", name).replaceAll("\\$var2", var2).replaceAll("\\$var3", var3);
	}
}
