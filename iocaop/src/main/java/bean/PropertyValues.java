package bean;

import java.util.ArrayList;
import java.util.List;
/**
 * 描述bean属性及值
 */
public class PropertyValues {
	private final List<PropertyValue> propertyValueList = new ArrayList<>();
	
	public void addPropertyValue(PropertyValue pv){
		this.propertyValueList.add(pv);
	}
	
	public List<PropertyValue> getPropertyValues(){
		return this.propertyValueList;
	}
}
