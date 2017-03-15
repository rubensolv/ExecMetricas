package principal;



import model.TesteConfig;

public class Main {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		System.out.println("Configuracoes ------------------ \n");
		
		TesteConfig testador = new TesteConfig();		
		testador.print();
		
		 
		System.out.println("Fim Testador ------------------");
		
		MontadorEstrutura montador = new MontadorEstrutura(testador.getNameDirTestes(), testador);
		montador.montar();
		
		
		System.out.println("Fim Montador------------------");
		
		ControladorPermissao control = new ControladorPermissao(System.getProperty("user.dir").concat("/"+testador.getNameDirTestes()));
		control.addNomeArquivo(testador.getNameScriptStart());
		control.aplicarPermissao();
		
		System.out.println("Fim Permissão------------------");
		
		ControladorExec executador = new ControladorExec(testador);
		executador.run();
		
		
		System.out.println("Completado");
	}

}
