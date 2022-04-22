package testing;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;

public class classTest {

	public classTest() {}
	
	private static class MyClass {
		
        public MyClass(int x) {
        }

//        public MyClass() {
//        }
//
//        public MyClass(String s, BigInteger[] integers) {
//        }
    }
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
		// TODO Auto-generated method stub
		Class<MyClass> c = MyClass.class;
		Constructor cons = c.getConstructor(int.class);
		System.out.println(cons.getParameters()[0]);
		
		
		
//		cons = c.getConstructors(int.class);
//        System.out.println(cons);
//        
//        cons = c.getConstructors(String.class, BigInteger[].class);
//        System.out.println(cons);
    }

}
