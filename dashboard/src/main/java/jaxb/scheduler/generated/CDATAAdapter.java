package jaxb.scheduler.generated;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;


//@XmlRootElement(name="script")
public class CDATAAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String arg0) throws Exception {
			return  "<![CDATA[" + arg0 + "]]>";

	}

	@Override
	public String unmarshal(String arg0) throws Exception {
		 return arg0;
	}

}
