package lt.bit.sandelys.exceptions;

public class ErrorResponse {
	private String name;
	private String description;
	private Integer code;
	
	
	
	
	public ErrorResponse() {
	}
	
	public ErrorResponse(String name, String description, Integer code) {
		super();
		this.name = name;
		this.description = description;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
