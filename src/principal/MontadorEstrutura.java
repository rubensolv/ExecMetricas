package principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.StateUnits;
import model.TesteConfig;

public class MontadorEstrutura {
	
	private String pathStruture, nomeDiretorioPadrao;
	private TesteConfig configuracao;
	private ControladorEstados cEstados;

	public MontadorEstrutura(TesteConfig configuracao) {
		this.pathStruture = System.getProperty("user.dir");
		this.nomeDiretorioPadrao = "Testes";
		this.configuracao = configuracao;
		this.cEstados = new ControladorEstados(configuracao);
	}
	
	public MontadorEstrutura(String nomeDiretorioPadrao, TesteConfig configuracao) {
		super();
		this.nomeDiretorioPadrao = nomeDiretorioPadrao;
		this.pathStruture = System.getProperty("user.dir");
		this.configuracao = configuracao;
		this.cEstados = new ControladorEstados(configuracao);
	}

	public MontadorEstrutura(String pathStruture, String nomeDiretorioPadrao, TesteConfig configuracao) {
		super();
		this.setPathStruture(pathStruture);
		this.nomeDiretorioPadrao = nomeDiretorioPadrao;
		this.configuracao = configuracao;
		this.cEstados = new ControladorEstados(configuracao);
	}

	public String getPathStruture() {
		return pathStruture;
	}

	public void setPathStruture(String pathStruture) {
		this.pathStruture = pathStruture;
	}
	
	public String getNomeDiretorioPadrao() {
		return nomeDiretorioPadrao;
	}

	public TesteConfig getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(TesteConfig configuracao) {
		this.configuracao = configuracao;
	}

	public void montar(){
		criarDiretorioDefault();
		criarPastasEstado();
	}
	
	protected void criarPastasEstado(){
		File diretorio;
		File subdiretorio;
		for (int i = 1; i <= configuracao.getQtdEstados() ; i++) {
			//monto o diretorio do teste
			diretorio = new File(pathStruture.concat("/").concat(nomeDiretorioPadrao).concat("/").concat("EstadoNumber"+i));
			diretorio.mkdir();
			//realizar a gravação do estado que será utilizado com base
			StateUnits estadoSelecionado = cEstados.obterProximoEstado();
			estadoSelecionado.gravarEstadoTxt(diretorio.getAbsolutePath());
			
			//monto o subdiretorio com o número de batalhas
			for (int j = 1; j <= configuracao.getQtdTestes(); j++) {
				subdiretorio = new File(diretorio.getAbsolutePath().concat("/").concat("Teste"+j));
				subdiretorio.mkdir();
				realizarCopia(subdiretorio);
				estadoSelecionado.gravarEstadoTxt(subdiretorio.getAbsolutePath().concat("/sample_experiment/"));
			}
		}
	}
	
	@SuppressWarnings("static-access")
	protected void realizarCopia(File diretorio){
		CopiaArquivos copiador = new CopiaArquivos();
		String pathOrig = pathStruture.concat("/").concat("baseDados");
		File origem = new File(pathOrig);
		//1 step - copy folder
		try {
			copiador.copyAll(origem, diretorio, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao copiar pastas para o novo diretório!");
			e.printStackTrace();
		}
		//2 step - change path in exp_linux_metrics
		criarArqExp(diretorio);
	}
	
	protected void criarArqExp(File diretorioCorrente){
		try {
			FileWriter arq = new FileWriter(diretorioCorrente.getAbsolutePath().concat("/sample_experiment/").concat("exp_linux_metrics.txt"));
			PrintWriter gravarArq = new PrintWriter(arq);
			
			gravarArq.println(configuracao.getPlayer0());
			gravarArq.println(configuracao.getPlayer1());
			gravarArq.println(configuracao.getStateDescriptor()+" "+diretorioCorrente.getAbsolutePath().concat("/sample_experiment/").concat("sample_state.txt"));
			
			gravarArq.flush();
			gravarArq.close();
			arq.close();
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo exp_linux_metrics.txt");
			e.printStackTrace();
		}
	}
	
	protected void criarDiretorioDefault(){
		
		File diretorioEstrutura = new File(pathStruture.concat("/"+nomeDiretorioPadrao));
		
		if(!diretorioEstrutura.exists()){
			diretorioEstrutura.mkdir();
		}
	}

}
