package org.stjs.generator.writer.statements;

import org.stjs.javascript.Array;

import static org.stjs.javascript.Global.console;
import static org.stjs.javascript.JSCollections.$array;

public class Statements11 {

	public void method() {
		Array<Integer> a = $array(1, 2);
		for (Integer i : a)
			console.info(a.$get(i));
	}

}
