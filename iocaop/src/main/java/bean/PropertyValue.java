package bean;

/**
 * 描述bean的一个属性及值
 */
public class PropertyValue {
	
	private final String name;
	
	private final Object value;
	
	public PropertyValue(String name, Object value){
		this.name  = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}