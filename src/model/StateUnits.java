package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class StateUnits {
	private int ID;
	private ArrayList<Unit> unidades;
	private int limitUnidadesPlayer;
	private ArrayList<String> tiposUnidades;
	private Random gerador;
	int max = Integer.MAX_VALUE, min = 0;
	
	
	public StateUnits(int totalUnidadesPlayer, Random gerador) {
		this.limitUnidadesPlayer = totalUnidadesPlayer;
		unidades = new ArrayList<Unit>();
		tiposUnidades = new ArrayList<String>();
		this.gerador = gerador;
	}

	public ArrayList<Unit> getUnidades() {
		return unidades;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}

	public void addUnit(Unit unidade){
		this.unidades.add(unidade);
	}
	
	public void gerarState(TesteConfig configuracoes){
		tiposUnidades = configuracoes.getTiposUnidades();
		int xbase, ybase;
		
		for(int i = 1; i<=limitUnidadesPlayer; i++){
			Unit unP0 = new Unit();
			Unit unP1 = new Unit();
			
			//calcula a distância
			xbase = (getIntRandom() % (2*configuracoes.getXlimit())) - configuracoes.getXlimit();
			ybase = (getIntRandom() % (2*configuracoes.getYlimit())) - configuracoes.getYlimit();
			
			unP0.setPlayer(0);
			unP0.setX(configuracoes.getCx1() + xbase);
			unP0.setY(configuracoes.getCy1() + ybase);
			unP0.setTipo(getTipoUnidade(0));
			
			unidades.add(unP0);
			
			unP1.setPlayer(1);
			unP1.setX(configuracoes.getCx2() - xbase);
			unP1.setY(configuracoes.getCy2() - ybase);
			unP1.setTipo(unP0.getTipo());
			
			unidades.add(unP1);
		}
	}
	
	public StateUnits gerarStateSimetrico(){
		StateUnits newState = new StateUnits(this.limitUnidadesPlayer, this.gerador);
		ArrayList<Unit> units = new ArrayList<Unit>(this.getUnidades());
		for(Unit un : units){
			Unit temp = new Unit();
			if(un.getPlayer() == 0){
				temp.setPlayer(1);
			}else{
				temp.setPlayer(0);
			}
			temp.setTipo(un.getTipo());
			temp.setX(un.getX());
			temp.setY(un.getY());
			
			newState.addUnit(temp);
		}
		return newState;
	}
	
	protected String getTipoUnidade(int Player){
		int qtdUnitPorTipo = (limitUnidadesPlayer / tiposUnidades.size());
		HashMap<String, Integer> mapTiposQtd = calcTiposQtd(Player);
		
		for(String tipo : tiposUnidades){
			if(mapTiposQtd.get(tipo)<qtdUnitPorTipo){
				return tipo;
			}
		}
		
		return "";
	}
	
	protected HashMap<String, Integer> calcTiposQtd(int Player){
		HashMap<String, Integer> retorno = new HashMap<String, Integer>();
		for(String tipo : tiposUnidades){
			retorno.put(tipo, 0);
		}
		for(Unit un : unidades){
			if(un.getPlayer() == Player){
				retorno.put(un.getTipo(), retorno.get(un.getTipo())+1);
			}
		}
		return retorno;
	}
	
	public void print(){
		Collections.sort(unidades);
		System.out.println("----ID= "+ ID);
		for(Unit un : unidades){
			un.print();
		}
	}
	
	public int getIntRandom(){
		return (gerador.nextInt(257) % (max - min))+min; 
	}
	
	private void gravarDadosUnidadesEstado(PrintWriter arq){
		Collections.sort(unidades);
		for(Unit un : unidades){
			arq.println(un.getString());
		}
	}
	
	/**
	 * Obtem o caminho absoluto da pasta (não é necessário informar o nome do caminho)
	 * @param caminhoAbsoluto = Caminho absoluto da pasta onde deseja-se gravar o estado
	 */
	public void gravarEstadoTxt(String caminhoAbsoluto){
		File estadoDefinido = new File(caminhoAbsoluto.concat("/").concat("sample_state.txt"));
		if(estadoDefinido.exists()){
			estadoDefinido.delete();
		}
		try {
			FileWriter arq = new FileWriter(estadoDefinido);
			PrintWriter gravarArq = new PrintWriter(arq);
			
			gravarDadosUnidadesEstado(gravarArq);
			
			gravarArq.flush();
			gravarArq.close();
			arq.close();
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo sample_state.txt");
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateUnits other = (StateUnits) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
	
	
}
