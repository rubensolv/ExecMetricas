package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TesteConfig {
	
	private int qtdTestes, qtdEstados, numUnidades, xlimit, ylimit, cx1, cy1, cx2, cy2, limitJobs;
	private String nameScriptStart, nameDirTestes, player0, player1, stateDescriptor, nomeArqControle;
	private ArrayList<String> tiposUnidades;
	
	public TesteConfig() {
		tiposUnidades = new ArrayList<String>();
		lerConfiguracao();
	}
	
	public int getQtdTestes() {
		return qtdTestes;
	}
	public void setQtdTestes(int qtdTestes) {
		this.qtdTestes = qtdTestes;
	}
	/*
	 * Retorna o total de estados simétricos (estaods * 2)
	 */
	public int getQtdEstados() {
		return qtdEstados*2;
	}
	/*
	 * Retorna o total de estados não simétricos
	 */
	public int getQtdEstadosNaoSimetricos() {
		return qtdEstados;
	}
	public void setQtdEstados(int qtdEstados) {
		this.qtdEstados = qtdEstados;
	}
	public String getNameScriptStart() {
		return nameScriptStart;
	}
	public void setNameScriptStart(String nameScriptStart) {
		this.nameScriptStart = nameScriptStart;
	}
	
	public String getNameDirTestes() {
		return nameDirTestes;
	}

	public void setNameDirTestes(String nameDirTestes) {
		this.nameDirTestes = nameDirTestes;
	}

	public String getPlayer0() {
		return player0;
	}

	public void setPlayer0(String player0) {
		this.player0 = player0;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getStateDescriptor() {
		return stateDescriptor;
	}

	public void setStateDescriptor(String stateDescriptor) {
		this.stateDescriptor = stateDescriptor;
	}

	public int getNumUnidades() {
		return numUnidades;
	}

	public void setNumUnidades(int numUnidades) {
		this.numUnidades = numUnidades;
	}
		
	public int getXlimit() {
		return xlimit;
	}

	public void setXlimit(int xlimit) {
		this.xlimit = xlimit;
	}

	public int getYlimit() {
		return ylimit;
	}

	public void setYlimit(int ylimit) {
		this.ylimit = ylimit;
	}
	
	public int getCx1() {
		return cx1;
	}

	public void setCx1(int cx1) {
		this.cx1 = cx1;
	}

	public int getCy1() {
		return cy1;
	}

	public void setCy1(int cy1) {
		this.cy1 = cy1;
	}

	public int getCx2() {
		return cx2;
	}

	public void setCx2(int cx2) {
		this.cx2 = cx2;
	}

	public int getCy2() {
		return cy2;
	}

	public void setCy2(int cy2) {
		this.cy2 = cy2;
	}

	public ArrayList<String> getTiposUnidades() {
		return tiposUnidades;
	}
	
	public void addNewArrayTipoUnidade(String tipoUnidades){
		this.tiposUnidades.add(tipoUnidades);
	}

	public String getNomeArqControle() {
		return nomeArqControle;
	}

	public void setNomeArqControle(String nomeArqControle) {
		this.nomeArqControle = nomeArqControle;
	}

	public int getLimitJobs() {
		return limitJobs;
	}

	public void setLimitJobs(int limitJobs) {
		this.limitJobs = limitJobs;
	}

	protected void lerConfiguracao(){
		//System.out.println(System.getProperty("user.dir"));
		try {
			FileReader arq = new FileReader("configuracao");
			BufferedReader learArq = new BufferedReader(arq);
			
			String linha = learArq.readLine();
			
			while(linha != null){
				
				if(linha.contains("Quantidade_testes    =")){
					setQtdTestes(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("Quantidade_estados   =")){
					setQtdEstados(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("Nome_Script_Controle =")){
					setNameScriptStart(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("Nome_Dir_Testes	     =")){
					setNameDirTestes(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("Player0	             =")){
					setPlayer0(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("Player1	             =")){
					setPlayer1(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("StateDescriptor      =")){
					setStateDescriptor(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("Numero_unidades	     =")){
					setNumUnidades(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("xLimite              =")){
					setXlimit(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("yLimite              =")){
					setYlimit(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("cx1                  =")){
					setCx1(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("cy1                  =")){
					setCy1(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("cx2                  =")){
					setCx2(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("cy2                  =")){
					setCy2(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				if(linha.contains("TiposUnidades        =")){
					String tiposTemp = linha.substring(23, linha.length()).trim();
						for(String partic : tiposTemp.split(",")){
							addNewArrayTipoUnidade(partic);
						}
				}
				if(linha.contains("Nome_arq_Controle    =")){
					setNomeArqControle(linha.substring(23, linha.length()).trim());
				}
				if(linha.contains("LimitJobs            =")){
					setLimitJobs(Integer.valueOf(linha.substring(23, linha.length()).trim()));
				}
				
				linha = learArq.readLine();
			}
			learArq.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Arquivo não encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro ao ler linhas do arquivo de configuração");
			e.printStackTrace();
		}
		
	}
	
	public void print(){
		System.out.println("Quantidade_testes    = "+getQtdTestes());
		System.out.println("Quantidade_estados   = "+getQtdEstados());
		System.out.println("Nome_Script_Controle = "+getNameScriptStart());
		System.out.println("Nome_Dir_Testes	     = "+getNameDirTestes());
		System.out.println("Player0	             = "+getPlayer0());
		System.out.println("Player1	             = "+getPlayer1());
		System.out.println("StateDescriptor      = "+getStateDescriptor());
		System.out.println("Numero_unidades      = "+getNumUnidades());
		System.out.println("xLimite              = "+getXlimit());
		System.out.println("yLimite              = "+getYlimit());
		System.out.println("cx1                  = "+getCx1());
		System.out.println("cy1                  = "+getCy1());
		System.out.println("cx2                  = "+getCx2());
		System.out.println("cy2                  = "+getCy2());
		System.out.println("TiposUnidades	     =");
		for(String tipo : tiposUnidades){
			System.out.println("                      "+ tipo);
		}
		System.out.println("Nome_arq_Controle    = "+getNomeArqControle());
		System.out.println("LimitJobs            = "+getLimitJobs());
	}
}
