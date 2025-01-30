package br.com.sortech.sorpag.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

public class BoletoGenerator {
    private static String PATH_BOLETO;
    private static String PATH_TEMPLATE;
    private static String PATH_IMAGEM;
    private String nomeBoleto;
    private EmpresaBoleto empresaBoleto;
    private String jasperPath;

    public BoletoGenerator() {
        PropertiesLoader props = new PropertiesLoader();
        PATH_BOLETO = props.prop.getProperty("pathCobranca");
        PATH_TEMPLATE = props.prop.getProperty("pathTemplate");
        PATH_IMAGEM = props.prop.getProperty("pathImg");
    }

    public BoletoGenerator comEmpresa(EmpresaBoleto empresaBoleto) {
        this.empresaBoleto = empresaBoleto;
        this.nomeBoleto = "boleto" + empresaBoleto.getNossoNumeroBeneficiario() + ".pdf";

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
                .comInstrucoes(empresaBoleto.getInstrucoes().toArray(new String[0]))
                .comLocaisDePagamento(empresaBoleto.getLocaisDePagamento().toArray(new String[0]));

        jasperPath = PATH_TEMPLATE + "/jasper/BoletoBBv1.jasper";

        if (Files.notExists(Paths.get(jasperPath))) {
            try {
                JasperCompileManager.compileReportToFile(PATH_TEMPLATE + "/jasper/BoletoBBv1.jrxml",
                        jasperPath);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            //Mapa para parâmetros
            Map<String, Object> parametros = new HashMap<>();

            parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
            parametros.put("logoBancoDoBrasil", PATH_IMAGEM);
            
            parametros.put("QR_CODE", empresaBoleto.getQrCode());

            //carrega o conteúdo do arquivo em um InputStream
            InputStream templateBoleto = new FileInputStream(jasperPath);

            GeradorDeBoleto gerador = new GeradorDeBoleto(templateBoleto, parametros, boleto);

            String arquivoBoleto = PATH_BOLETO + "/" + nomeBoleto;
            // Para gerar um boleto em PDF
            gerador.geraPDF(arquivoBoleto);

            new BoletoCrypto().comEmpresa(empresaBoleto).comArquivo(arquivoBoleto).cryptografarBoleto();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public String getNomeBoleto() {
        return nomeBoleto;
    }
}