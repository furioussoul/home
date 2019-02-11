package vm;

/**
 * Created by szj on 2016/6/5.
 */
public class ActiveOperand {

	private Class clazz;	 
	
	private Object value;

	public ActiveOperand(Class clazz, Object value) {
		super();
		this.clazz = clazz;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public Class getClazz() {
		return clazz;
	}

	@Override
	public String toString() {
		return clazz.toString() + " " + value.toString();
	}
}
