package br.com.sortech.sorpag.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.sortech.sorpag.model.EmpresaBoleto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;

public class PreventivosGenerator {

    private static String PATH_CARTA;
    private static String PATH_BOLETO;
    private static String PATH_TEMPLATE;
    private static String PATH_IMAGEM;
    private String nomeBoleto;
    private EmpresaBoleto empresaBoleto;
    private String jasperPath;

    public PreventivosGenerator() {
        PropertiesLoader props = new PropertiesLoader();
        PATH_BOLETO = props.prop.getProperty("pathPreventivo");
        PATH_TEMPLATE = props.prop.getProperty("pathTemplate");
        PATH_CARTA= props.prop.getProperty("pathCarta");
        PATH_IMAGEM = props.prop.getProperty("pathImg");
    }

    public PreventivosGenerator comEmpresa(EmpresaBoleto empresaBoleto) {
        this.empresaBoleto = empresaBoleto;
        this.nomeBoleto = "preventivo" + empresaBoleto.getNossoNumeroBeneficiario() + ".pdf";

        return this;
    }

    public void gerarBoleto() {

        Datas datas = Datas.novasDatas()
                .comProcessamento(empresaBoleto.getDataProcessamento())
                .comVencimento(empresaBoleto.getDataVencimento());

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro(empresaBoleto.getLogradouroBeneficiario())
                .comBairro(empresaBoleto.getBairroBeneficiario())
                .comCep(empresaBoleto.getCepBeneficiario())
                .comCidade(empresaBoleto.getCidadeBeneficiario())
                .comUf(empresaBoleto.getUfBeneficiario());

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario(empresaBoleto.getNomeBeneficiario())
                .comDocumento(empresaBoleto.getDocumentoBeneficiario())
                .comAgencia(empresaBoleto.getAgenciaBeneficiario())
                .comDigitoAgencia(empresaBoleto.getDigitoAgenciaBeneficiario())
                .comCodigoBeneficiario(empresaBoleto.getCodigoBeneficiario())
                .comDigitoCodigoBeneficiario(empresaBoleto.getDigitoCodigoBeneficiario())
                .comNumeroConvenio(empresaBoleto.getNumeroConvenioBeneficiario())
                .comCarteira(empresaBoleto.getCarteiraBeneficiario())
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero(empresaBoleto.getNossoNumeroBeneficiario())
                .comDigitoNossoNumero(empresaBoleto.getDigitoNossoNumeroBeneficiario());

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro(empresaBoleto.getLogradouroPagador())
                .comBairro(empresaBoleto.getBairroPagador())
                .comCep(empresaBoleto.getCepPagador())
                .comCidade(empresaBoleto.getCidadePagador())
                .comUf(empresaBoleto.getUfPagador());

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome(empresaBoleto.getNomePagador())
                .comDocumento(empresaBoleto.getDocumentoPagador())
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comEspecieDocumento("REC")
                .comValorBoleto(empresaBoleto.getValorBoleto())
                .comNumeroDoDocumento(empresaBoleto.getNumeroDocumento())
                .comLocaisDePagamento(empresaBoleto.getLocaisDePagamento().toArray(new String[0]));

        jasperPath = PATH_TEMPLATE + "/jasper/v1Preventivos.jasper";

        if (Files.notExists(Paths.get(jasperPath))) {
            try {
                JasperCompileManager.compileReportToFile(PATH_TEMPLATE + "/jasper/v1Preventivos.jrxml",
                        jasperPath);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            //Mapa para parâmetros
            Map<String, Object> parametros = new HashMap<>();

            parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
            parametros.put("informacaoPreventivo", empresaBoleto.getInstrucoes().get(0));
            parametros.put("informacaoPreventivo2", empresaBoleto.getInstrucoes().get(1));
            parametros.put("logoBancoDoBrasil", PATH_IMAGEM);

            //carrega o conteúdo do arquivo em um InputStream
            InputStream templateBoleto = new FileInputStream(jasperPath);

            GeradorDeBoleto gerador = new GeradorDeBoleto(templateBoleto, parametros, boleto);

            String arquivoBoleto = PATH_BOLETO + "/" + nomeBoleto;
            // Para gerar um boleto em PDF
            gerador.geraPDF(arquivoBoleto);
            adicionarCarta(arquivoBoleto);

            new BoletoCrypto().comEmpresa(empresaBoleto).comArquivo(arquivoBoleto).cryptografarBoleto();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void adicionarCarta(String arquivoBoleto) {
    	
    	String path = PATH_CARTA;
    	boolean isContador = empresaBoleto.getInstrucoes()
    			.get(0)
    			.substring(31, 39)
    			.trim()
    			.equalsIgnoreCase("contador");
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("664.00"))) {
			path += "/CartaContador.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("587.00"))) {
			path += "/CartaTecnico.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("557.00"))) {
			path += "/CartaTecnicoDTE.pdf";
		}
		
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("630.00"))) {
			path += "/CartaContadorDTE.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("293.50"))) {
			path += "/CartaTecnicoDesconto.pdf";
		}
		
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("332.00"))) {
			path += "/CartaContadorDesconto.pdf";
		}
    	
    	/*if (empresaBoleto.getValorBoleto().equals(new BigDecimal("636.00"))) {
			path += "/CartaFX01.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("956.00"))) {
			path += "/CartaFX02.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("1278.00"))) {
			path += "/CartaFX03.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("1598.00"))) {
			path += "/CartaFX04.pdf";
		}
    	
    	if (empresaBoleto.getValorBoleto().equals(new BigDecimal("316.00"))) {
			path += "/CartaSLU.pdf";
		}*/
    	
        ArrayList<File> pdfs = new ArrayList<>();
        pdfs.add(new File(path));	
        pdfs.add(new File(arquivoBoleto));
        try {
            PDFMergerUtility ut = new PDFMergerUtility();
            ut.setDestinationFileName(arquivoBoleto);
            for (File pdf : pdfs) {
                ut.addSource(pdf);
            }
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNomeBoleto() {
        return nomeBoleto;
    }
}