package principal;

import java.util.HashSet;
import java.util.Random;

import model.StateUnits;
import model.TesteConfig;

public class ControladorEstados {
	private TesteConfig config;
	private HashSet<StateUnits> estados;
	private HashSet<StateUnits> estadosUtilizados;
	private Random gerador = new Random(0);

	public ControladorEstados(TesteConfig configuracao) {
		super();
		this.config = configuracao;
		estados = new HashSet<StateUnits>();
		estadosUtilizados = new HashSet<StateUnits>();
		gerarEstados();
	}
	/**
	 * Gera os estados com base no arquivo de configuração.
	 * Irá gerar sempre estados simétricos com inversão de lados.
	 */
	public void gerarEstados(){
		for (int i = 0; i < config.getQtdEstadosNaoSimetricos(); i++ ) {
			StateUnits state = new StateUnits(config.getNumUnidades(), gerador);
			state.setID(i);
			state.gerarState(config);
			estados.add(state);
			
			StateUnits stateSim = state.gerarStateSimetrico();
			stateSim.setID(i+1);
			
			estados.add(stateSim);
		}
	}
	
	public StateUnits obterProximoEstado(){
		for(StateUnits state : estados){
			if(!estadosUtilizados.contains(state)){
				estadosUtilizados.add(state);
				return state;
			}
		}
		return null;
	}
	
}
