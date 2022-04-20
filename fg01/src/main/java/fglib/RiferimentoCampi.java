package fglib;


public class RiferimentoCampi {

	private String BeanType=null;
	private Object Value=null;
	private String FieldType=null;
	private Object key=null;
	public RiferimentoCampi() {
	}
	public String getBeanType() {
		return BeanType;
	}
	public void setBeanType(String beanType) {
		BeanType = beanType;
	}
	public Object getValue() {
		return Value;
	}
	public void setValue(Object value) {
		Value = value;
	}
	public String getFieldType() {
		return FieldType;
	}
	public void setFieldType(String fieldType) {
		FieldType = fieldType;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}

}
