package functions;

public class StandardLibrary {

	public static Function fTell = new Function() {
		@Override
		public String convert(String conv) {
			return conv;
		}
	};
	public static Method mTell = new Method("tell", fTell);

}
