package fr.dvrc.generator5FM.struct;

public class Key extends Struct {
	public Key(String keyName) {
		super(keyName, "template_key.xml");
	}
	public Key(String keyName, String templateName) {
		super(keyName, templateName);
	}

	@Override
	public void generate() {

	}

	@Override
	public String toString () {
		return signature ();
	}

	@Override
	public String toXML () {
		return template.replaceAll("\\$var", name);
	}

	@Override
	public String signature() {
		return name;
	}
}
