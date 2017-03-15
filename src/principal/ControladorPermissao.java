package principal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class ControladorPermissao {
	private HashSet<String> arquivosPermissaoExec = new HashSet<String>();
	private String pastaInicial;
	
	 public ControladorPermissao(String pastaInicial) {
		 this.pastaInicial = pastaInicial;
		 addNomeArquivo("SparCraft");
		 addNomeArquivo("EvalSparcraftState.sh");
	}
	
	
	void addNomeArquivo(String nomeArq){
		arquivosPermissaoExec.add(nomeArq);
	}

	public String getPastaInicial() {
		return pastaInicial;
	}

	public void setPastaInicial(String pastaInicial) {
		this.pastaInicial = pastaInicial;
	}
	
	public void aplicarPermissao(){
		
		File dirInicial = new File(pastaInicial);
		if(!dirInicial.isDirectory()){
			System.out.println("Pasta inicial não é um diretório!");
		}else{
			ArrayList<String> lista = new ArrayList<String>();
			File temp;
			for (String palavra : arquivosPermissaoExec) {
				lista =  buscar(dirInicial, palavra, lista);
				for (String caminho : lista) {
					temp = new File(caminho);
					temp.setExecutable(true, false);
				}
				lista.clear();
			}
		}
	}
	
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
}
