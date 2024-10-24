package application;

public class Case {
	
	private int _l;	
	private int _c;
	
	public Case(){
		this._l = -1;
		this._c = -1;
	}
	
	public Case(int l, int c) {
		this._l = l;
		this._c = c;
	}

	public int get_l() {
		return _l;
	}

	public void set_l(int _l) {
		this._l = _l;
	}

	public int get_c() {
		return _c;
	}

	public void set_c(int _c) {
		this._c = _c;
	}

	public boolean isEqual(Case c){
		if(this._c == c.get_c() && this._l == c.get_l())
			return true;
		
		return false;
	}
	
	public String printCase(){
		return " (" + (_l + 1) + "," + (_c + 1) + ") ";
	}
	
	public Case clone(){
		return new Case(this._l, this._c);
	}
}
