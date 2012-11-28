/**
 *  Copyright 2011 Alexandru Craciun, Eyal Kaspi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.stjs.server;

import org.stjs.javascript.Array;
import org.stjs.javascript.SortFunction;
import org.stjs.javascript.functions.Callback1;

import java.util.*;

/**
 * This class implements the {@link Array} interface to be used on the server side.
 * @author acraciun
 * @param <V>
 */
public class ArrayImpl<V> implements Array<V> {
	private final List<V> array = new ArrayList<V>();

    @Override
    public int size() {
        return array.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Object o) {
        return array.contains(o);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public Iterator<V> iterator() {
        return array.iterator();
        /*
		return new Iterator<V>() {
			private int current = 0;

			@Override
			public boolean hasNext() {
				return current < array.size();
			}

			@Override
			public V next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
                V aux = array.get(current);
                current++;
				return aux;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		/**/

	}

    @Override
    public Object[] toArray() {
        return array.toArray();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return array.toArray(a);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(V v) {
        return array.add(v);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Object o) {
        return array.remove(o);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return array.containsAll(c);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        return array.addAll(c);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return array.removeAll(c);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return array.retainAll(c);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
        array.clear();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public V $get(int index) {
		if ((index < 0) || (index >= array.size())) {
			return null;
		}
		return array.get(index);
	}

	@Override
	public V $get(String index) {
		return $get(Integer.valueOf(index));
	}

	@Override
	public void $set(int index, V value) {
		if (index < 0) {
			return;
		}
		if (index >= array.size()) {
			$length(index + 1);
		}
		array.set(index, value);
	}

	@Override
	public void $set(String index, V value) {
		$set(Integer.valueOf(index), value);
	}

	@Override
	public int $length() {
		return array.size();
	}

	@Override
	public void $length(int newLength) {
		if (newLength < array.size()) {
			splice(newLength, array.size() - newLength);
		} else {
			while (array.size() < newLength) {
				array.add(null);
			}
		}

	}

	@Override
	public Array<V> concat(Array<V>... arrays) {
		ArrayImpl<V> ret = new ArrayImpl<V>();
		ret.array.addAll(this.array);
		for (Array<V> a : arrays) {
			for (int i = 0; i < a.$length(); ++i) {
				ret.array.add(a.$get(i));
			}
		}
		return ret;
	}

	@Override
	public int indexOf(V element) {
		return array.indexOf(element);
	}

	@Override
	public int indexOf(V element, int start) {
		int pos = array.subList(start, array.size()).indexOf(element);
		if (pos < 0) {
			return pos;
		}
		return pos + start;
	}

	@Override
	public String join() {
		return join(",");
	}

	@Override
	public String join(String separator) {
		StringBuilder sb = new StringBuilder();
		for (V value : array) {
			if (sb.length() != 0) {
				sb.append(separator);
			}
			sb.append(value != null ? value.toString() : "");
		}
		return sb.toString();
	}

	@Override
	public V pop() {
		if (array.size() == 0) {
			return null;
		}
		return array.remove(array.size() - 1);
	}

	@Override
	public int push(V... values) {
		for (V value : values) {
			array.add(value);
		}
		return array.size();
	}

	@Override
	public void reverse() {
		Collections.reverse(array);
	}

	@Override
	public V shift() {
		if (array.size() == 0) {
			return null;
		}
		return array.remove(0);
	}

	@Override
	public Array<V> slice(int start) {
		if (start < 0) {
			return slice(java.lang.Math.max(0, array.size() - start), array.size());
		}
		return slice(start, array.size());
	}

	@Override
	public Array<V> slice(int start, int end) {
		int s = start < 0 ? java.lang.Math.max(0, array.size() - start) : start;
		s = java.lang.Math.min(s, array.size());
		int e = java.lang.Math.min(end, array.size());
		if (s <= e) {
			return new ArrayImpl<V>();
		}
		ArrayImpl<V> ret = new ArrayImpl<V>();
		ret.array.addAll(array.subList(s, e));
		return ret;
	}

	@Override
	public Array<V> splice(int start) {
		return splice(start, 1);
	}

	@Override
	public Array<V> splice(int start, int howMany) {
		ArrayImpl<V> ret = new ArrayImpl<V>();
		for (int i = 0; i < howMany; ++i) {
			if (start >= array.size()) {
				break;
			}
			ret.array.add(array.remove(start));
		}
		return ret;
	}

	@Override
	public Array<V> splice(int start, int howMany, V... values) {
		Array<V> removed = splice(start, howMany);
		array.addAll(start, Arrays.asList(values));
		return removed;
	}

	@Override
	public void sort(final SortFunction<V> function) {
		Collections.sort(array, new Comparator<V>() {
			@Override
			public int compare(V a, V b) {
				return function.$invoke(a, b);
			}
		});
	}

	@Override
	public int unshift(V... values) {
		array.addAll(0, Arrays.asList(values));
		return array.size();
	}

	@Override
	public void forEach(Callback1<V> callback) {
		for (V value : array) {
			callback.$invoke(value);
		}
	}

	@Override
	public String toString() {
		return array.toString();
	}
}
