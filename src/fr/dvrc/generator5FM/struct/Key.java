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

	public String toString () {
		return template.replaceAll("\\$var", name);
	}
}
