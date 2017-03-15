package principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import model.TesteConfig;

public class ControladorExec {
	private String pathStruture;
	private TesteConfig config;
	private HashSet<String> arqExecutar;
	private LocalShell shell;

	public ControladorExec( TesteConfig config) {
		super();
		this.config = config;
		this.pathStruture = System.getProperty("user.dir").concat("/").concat(config.getNameDirTestes());
		arqExecutar = new HashSet<String>();
		shell = new LocalShell();
	}
	
	public void run(){
		montaListaArquivosExecutar();
		gravarListaArquivosExecutar();
		lancarJobs();
	}
	
	private void lancarJobs(){
		System.out.println("Executando comandos");
		try {
			for(String caminho : arqExecutar){
				while( Integer.valueOf(shell.executeCommand("echo $(qselect -u es91661 | wc -l)").trim()) > config.getLimitJobs() ){
					System.out.println("Esperando....");
					Thread.sleep(300000);
				}
				shell.executeScript(caminho);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for(String caminho : arqExecutar){
				//shell.executeScript(caminho);
		//}
	}
	
	
	private void montaListaArquivosExecutar(){
		ArrayList<String> tempCaminhos = new ArrayList<String>();
		File diretorio = new File(pathStruture);
		
		buscar(diretorio, config.getNameScriptStart(), tempCaminhos);
		arqExecutar.addAll(tempCaminhos);
		
	}
	
	public void print(){
		System.out.println("Caminho default = "+ pathStruture);
		System.out.println("Arquivos para executar:");
		for(String str : arqExecutar){
			System.out.println("    "+ str);
		}
	}
	
	/**
	 * Realiza a busca (recursiva) de todos os arquivos com o nome informado
	 * @param arquivo = File contendo o caminho que se deseja procurar.
	 * @param palavra = String com o nome que se deseja buscar
	 * @param lista = ArrayList<String> que retornará os arquivos encontrados
	 * @return Lista de Strings com todos os caminhos absolutos dos arquivos com o nome encontrado
	 */
	public ArrayList<String> buscar(File arquivo, String palavra, ArrayList<String> lista) {
        if (arquivo.isDirectory()) {
            File[] subPastas = arquivo.listFiles();
            for (int i = 0; i < subPastas.length; i++) {
                lista = buscar(subPastas[i], palavra, lista);
                if (arquivo.getName().equalsIgnoreCase(palavra)) lista.add(arquivo.getAbsolutePath());
                else if (arquivo.getName().indexOf(palavra) > -1) lista.add(arquivo.getAbsolutePath());
            }
        }
        else if (arquivo.getName().equalsIgnoreCase(palavra)) lista.add(arquivo.getAbsolutePath());
        //else if (arquivo.getName().indexOf(palavra) > -1) lista.add(arquivo.getAbsolutePath());
        return lista;
    }
	
	protected void gravarListaArquivosExecutar(){
		try {
			FileWriter arq = new FileWriter(pathStruture.concat("/"+config.getNomeArqControle()));
			PrintWriter gravarArq = new PrintWriter(arq);
			
			for (String arqExec : arqExecutar) {
				gravarArq.println(arqExec);
			}
			
			gravarArq.flush();
			gravarArq.close();
			arq.close();
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo exp_linux_metrics.txt");
			e.printStackTrace();
		}
	}
	

	
	
}
