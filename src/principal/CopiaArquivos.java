package principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CopiaArquivos {
	
	
	/**
     * Copia arquivos de um local para o outro.
     * @param origem - Arquivo de origem
     * @param destino - Arquivo de destino
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException
     */
    public static void copy(File origem, File destino, boolean overwrite) throws IOException {
        if (destino.exists() && !overwrite) {
            return;
        }
        
        FileInputStream source = new FileInputStream(origem);
        FileOutputStream destination = new FileOutputStream(destino);
        FileChannel sourceFileChannel = source.getChannel();
        FileChannel destinationFileChannel = destination.getChannel();
        long size = sourceFileChannel.size();
        sourceFileChannel.transferTo(0, size, destinationFileChannel);
        sourceFileChannel.close();
        source.close();
        destinationFileChannel.close();
        destination.close();
    }
    
    
    /**
     * Copia todos os arquivos que tenham uma determinada extensão de uma pasta de origem para outra de destino.
     * @param origem - Diretório onde estão os arquivos a serem copiados
     * @param destino - Diretório onde os arquivos serão copiados
     * @param extensao - <i>String</i> Extensão do arquivo que deve ser copiada. Você pode inserir a extensão no formato: ".doc" ou "doc" (por exemplo)
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException, UnsupportedOperationException
     */
    public static void copyAll(File origem, File destino, String extensao, boolean overwrite) throws IOException, UnsupportedOperationException {
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }
        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + "/" + files[i].getName()), overwrite);
            } else {
                if (files[i].getName().endsWith(extensao)) {
                    copy(files[i], new File(destino + "/" + files[i].getName()), overwrite);
                }
            }
        }
    }
    
    
    /**
     * Copia todos os arquivos de dentro de uma pasta para outra.
     * @param origem - Diretório onde estão os arquivos a serem copiados
     * @param destino - Diretório onde os arquivos serão copiados
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException, UnsupportedOperationException
     */
    public static void copyAll(File origem, File destino, boolean overwrite) throws IOException, UnsupportedOperationException {
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }
        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + "/" + files[i].getName()), overwrite);
            } else {
                copy(files[i], new File(destino + "/" + files[i].getName()), overwrite);
            }
        }
    }
}
