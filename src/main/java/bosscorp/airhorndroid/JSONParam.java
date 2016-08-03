package bosscorp.airhorndroid;

public class JSONParam<K,V>
{
	private K mKey;
	private V mValue;

	public JSONParam(K key, V value)
	{
		mKey = key;
		mValue = value;
	}

	public K key()
	{
		return mKey;
	}

	public V value()
	{
		return mValue;
	}
}
