package br.com.sortech.sorpag.util;

import br.com.sortech.sorpag.model.EmpresaBoleto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;

public class BoletoCrypto {

    private String arquivoBoleto;
    private EmpresaBoleto empresaBoleto;

    protected BoletoCrypto comEmpresa(EmpresaBoleto empresaBoleto){
        this.empresaBoleto = empresaBoleto;
        return this;
    }

    protected BoletoCrypto comArquivo(String arquivoBoleto){
        this.arquivoBoleto = arquivoBoleto;
        return this;
    }

    public void cryptografarBoleto() {

        String pattern = "\\D";
        String updated = empresaBoleto.getDocumentoPagador().replaceAll(pattern, "");

        File f = new File(arquivoBoleto);
        try {
            PDDocument pdd = PDDocument.load(f);
            AccessPermission ap = new AccessPermission();
            ap.canPrint();

            StandardProtectionPolicy stpp = new StandardProtectionPolicy("@S0rt3ch#", updated, ap);
            stpp.setEncryptionKeyLength(128);
            stpp.setPermissions(ap);
            pdd.protect(stpp);

            // save the document
            pdd.save(arquivoBoleto);
            pdd.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}