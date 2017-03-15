package model;

public class Unit implements Comparable<Unit> {
	private String tipo;
	private int player, x, y;
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public String getString(){
		String retorno = "";
		retorno = tipo.concat(" ").concat(Integer.toString(player));
		retorno = retorno.concat(" ").concat(Integer.toString(x)).concat(" ").concat(Integer.toString(y));
		
		return retorno;
	}
	
	public void print(){
		System.out.println(getString());
	}
	@Override
	public int compareTo(Unit outraUnidade) {
		if(this.player < outraUnidade.player){
			return -1;
		}
		if(this.player > outraUnidade.player){
			return 1;
		}
		return 0;
	}
	
	public void changePlayer(){
		if(this.player == 0){
			this.player = 1;
		}else{
			this.player = 0;
		}
	}
}
