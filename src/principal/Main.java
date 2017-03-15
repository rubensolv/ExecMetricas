package principal;



import java.io.File;

import model.TesteConfig;

public class Main {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		System.out.println("Configuracoes ------------------ \n");

		TesteConfig testador = new TesteConfig();		
		testador.print();
		System.out.println("Fim Testador ------------------");
		
		//testo se o arquivo com a listagem a processar está vazio
		File arq = new File(System.getProperty("user.dir").concat("/").concat(testador.getNameDirTestes()).concat("/"+testador.getNomeArqControle()));
		if(!arq.exists()){ 

			MontadorEstrutura montador = new MontadorEstrutura(testador.getNameDirTestes(), testador);
			montador.montar();


			System.out.println("Fim Montador------------------");

			ControladorPermissao control = new ControladorPermissao(System.getProperty("user.dir").concat("/"+testador.getNameDirTestes()));
			control.addNomeArquivo(testador.getNameScriptStart());
			control.aplicarPermissao();

			System.out.println("Fim Permissão------------------");

			ControladorExec executador = new ControladorExec(testador);
			executador.run();
		}else{
			System.out.println("Dando continuidade!");
			ControladorExec executador = new ControladorExec(testador);
			executador.continuarProcessamento();
		}

		System.out.println("Completado");
	}

}
