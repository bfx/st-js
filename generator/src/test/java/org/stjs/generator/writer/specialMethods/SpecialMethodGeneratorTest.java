package org.stjs.generator.writer.specialMethods;

import static org.stjs.generator.utils.GeneratorTestHelper.assertCodeContains;
import static org.stjs.generator.utils.GeneratorTestHelper.generate;

import org.junit.Test;
import org.stjs.generator.JavascriptGenerationException;

public class SpecialMethodGeneratorTest {

	@Test
	public void testSpecialGet() {
		// x.$get -> x[]
		assertCodeContains(SpecialMethod1.class, "this[\"3\"]");
	}

	@Test
	public void testSpecialSet() {
		// x.$set -> x[x]
		assertCodeContains(SpecialMethod2.class, "this[\"3\"]=4");
	}

	@Test
	public void testSpecialPut() {
		// x.$put -> x[x]
		assertCodeContains(SpecialMethod3.class, "this[\"3\"]=4");
	}

	@Test
	public void testSpecialInvoke() {
		// x.$invoke(a) -> x(a)
		assertCodeContains(SpecialMethod4.class, "f(4)");
	}

	@Test
	public void testSpecialMap() {
		// $map(k,v) -> {k:v}
		assertCodeContains(SpecialMethod5.class, "{\"key\":1}");
	}

	@Test
	public void testSpecialNumber() {
		// $map(k,v) -> {k:v}
		assertCodeContains(SpecialMethod5a.class, "{2:1}");
	}

	@Test(expected = JavascriptGenerationException.class)
	public void testWrongMapKey() {
		// $map(k,v) -> {k:v}
		generate(SpecialMethod5b.class);
	}

	@Test
	public void testSpecialArray() {
		// $array(a,b) -> [a,b]
		assertCodeContains(SpecialMethod6.class, "[1,2]");
	}

	@Test
	public void testSpecialMethodAsProp1() {
		// x.$length() -> x.length
		assertCodeContains(SpecialMethod7.class, "this.length;");
	}

	@Test
	public void testSpecialMethodAsProp2() {
		// x.$length(y) -> x.length = y
		assertCodeContains(SpecialMethod8.class, "this.length = 1");
	}

	@Test
	public void testSpecialLengthAppliedToString() {
		// $length(x, y) -> x.length = y
		assertCodeContains(SpecialMethod13.class, "(this).length = 1");
	}

	@Test
	public void testSpecialOr() {
		// $or(a,b) -> a || b
		assertCodeContains(SpecialMethod9.class, "3 || 4");
	}

	@Test
	public void testSpecialEquals() {
		// x.equals(y) -> x == y
		assertCodeContains(SpecialMethod10.class, "(x == 2)");
	}

	@Test
	public void testSpecialNotEquals() {
		// !x.equals(y) -> !(x == y)
		assertCodeContains(SpecialMethod11.class, "!(x == 2)");
	}

	@Test
	public void testSpecialGetObject() {
		// $get(x,y) -> x[y]
		assertCodeContains(SpecialMethod12.class, "(obj)[\"a\"]");
	}

	@Test
	public void testAssertMethods() {
		// the special parameter THIS should not be added
		assertCodeContains(SpecialMethod14.class,
				"assertArgEquals(\"SpecialMethod14.java:8\",\"assertArgEquals(\\\"123\\\", x)\", \"123\", x);");
	}

	@Test
	public void testSpecialDelete() {
		// x.$delete(key) -> delete x[key]
		assertCodeContains(SpecialMethod15.class, "delete this[\"key\"]");
	}

	@Test
	public void testStringLength() {
		// string.length() -> string.length
		assertCodeContains(SpecialMethod16.class, "n = (\"a\" + \"b\").length;");
	}

	@Test
	public void testProperties() {
		// Map<K,V> x = $properties(obj) -> var x = obj;
		assertCodeContains(SpecialMethod17.class, "var map = (123);");
	}

	@Test
	public void testObject() {
		// x = $object(map) -> var x = map;
		assertCodeContains(SpecialMethod18.class, "var p = (map);");
	}

	@Test
	public void testCastArray() {
		// Array<V> x = $castArray(obj[]) -> var x = obj;
		assertCodeContains(SpecialMethod19.class, "var a = (\"abc\".split(\",\"));");
	}

	@Test
	public void testKeepJQuery$() {
		assertCodeContains(SpecialMethod20.class, "var div = $(\"div\");");
	}

	@Test
	public void testJs() {
		assertCodeContains(SpecialMethod21.class, "x = s.a");
	}

	@Test
	public void testTemplateNode() {
		assertCodeContains(SpecialMethod22.class, "n = m.$get(0)");
	}
}
