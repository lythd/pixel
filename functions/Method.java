package functions;

public class Method {

	private String name;
	private Function function;
	
	public Method(String name, Function function) {
		this.name = name;
		this.function = function;
	}
	
	public String convert(String conv) {
		return function.convert(conv);
	}
	
	public String getName() {
		return name;
	}
}
