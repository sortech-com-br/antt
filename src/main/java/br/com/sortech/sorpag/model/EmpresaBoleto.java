package br.com.sortech.sorpag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpresaBoleto implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty
    private long IdEmpresaBoleto;
    @JsonProperty
    private String Token;
    @JsonProperty
    private String NomeEmpresaBoleto;
    @JsonProperty
    private Calendar dataProcessamento;
    @JsonProperty
    private Calendar dataVencimento;
    @JsonProperty
    private String logradouroBeneficiario;
    @JsonProperty
    private String bairroBeneficiario;
    @JsonProperty
    private String cepBeneficiario;
    @JsonProperty
    private String cidadeBeneficiario;
    @JsonProperty
    private String ufBeneficiario;
    @JsonProperty
    private String nomeBeneficiario;
    @JsonProperty
    private String documentoBeneficiario;
    @JsonProperty
    private String agenciaBeneficiario;
    @JsonProperty
    private String digitoAgenciaBeneficiario;
    @JsonProperty
    private String codigoBeneficiario;
    @JsonProperty
    private String digitoCodigoBeneficiario;
    @JsonProperty
    private String numeroConvenioBeneficiario;
    @JsonProperty
    private String carteiraBeneficiario;
    @JsonProperty
    private String nossoNumeroBeneficiario;
    @JsonProperty
    private String digitoNossoNumeroBeneficiario;
    @JsonProperty
    private String logradouroPagador;
    @JsonProperty
    private String bairroPagador;
    @JsonProperty
    private String cepPagador;
    @JsonProperty
    private String cidadePagador;
    @JsonProperty
    private String ufPagador;
    @JsonProperty
    private String nomePagador;
    @JsonProperty
    private String documentoPagador;
    @JsonProperty
    private BigDecimal valorBoleto;
    @JsonProperty
    private String numeroDocumento;
    @JsonProperty
    private List<String> instrucoes;
    @JsonProperty
    private List<String> locaisDePagamento;
    @JsonProperty
    private String qrCode;

    public String getNomeEmpresaBoleto() {
        return NomeEmpresaBoleto;
    }

    public long getIdEmpresaBoleto() {
        return IdEmpresaBoleto;
    }

    public String getToken() {
        return Token;
    }

    public Calendar getDataProcessamento() {
        return dataProcessamento;
    }

    public Calendar getDataVencimento() {
        return dataVencimento;
    }

    public String getLogradouroBeneficiario() {
        return logradouroBeneficiario;
    }

    public String getBairroBeneficiario() {
        return bairroBeneficiario;
    }

    public String getCepBeneficiario() {
        return cepBeneficiario;
    }

    public String getCidadeBeneficiario() {
        return cidadeBeneficiario;
    }

    public String getUfBeneficiario() {
        return ufBeneficiario;
    }

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public String getDocumentoBeneficiario() {
        return documentoBeneficiario;
    }

    public String getAgenciaBeneficiario() {
        return agenciaBeneficiario;
    }

    public String getDigitoAgenciaBeneficiario() {
        return digitoAgenciaBeneficiario;
    }

    public String getCodigoBeneficiario() {
        return codigoBeneficiario;
    }

    public String getDigitoCodigoBeneficiario() {
        return digitoCodigoBeneficiario;
    }

    public String getNumeroConvenioBeneficiario() {
        return numeroConvenioBeneficiario;
    }

    public String getCarteiraBeneficiario() {
        return carteiraBeneficiario;
    }

    public String getNossoNumeroBeneficiario() {
        return nossoNumeroBeneficiario;
    }

    public String getDigitoNossoNumeroBeneficiario() {
        return digitoNossoNumeroBeneficiario;
    }

    public String getLogradouroPagador() {
        return logradouroPagador;
    }

    public String getBairroPagador() {
        return bairroPagador;
    }

    public String getCepPagador() {
        return cepPagador;
    }

    public String getCidadePagador() {
        return cidadePagador;
    }

    public String getUfPagador() {
        return ufPagador;
    }

    public String getNomePagador() {
        return nomePagador;
    }

    public String getDocumentoPagador() {
        return documentoPagador;
    }

    public BigDecimal getValorBoleto() {
        return valorBoleto;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public List<String> getInstrucoes() {
        return instrucoes;
    }

    public List<String> getLocaisDePagamento() {
        return locaisDePagamento;
    }

    public void setIdEmpresaBoleto(long IdEmpresaBoleto) {
        this.IdEmpresaBoleto = IdEmpresaBoleto;
    }


    public void setToken(String Token) {
        this.Token = Token;
    }


    public void setNomeEmpresaBoleto(String NomeEmpresaBoleto) {
        this.NomeEmpresaBoleto = NomeEmpresaBoleto;
    }

    public void setDataProcessamento(Calendar dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public void setDataVencimento(Calendar dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setLogradouroBeneficiario(String logradouroBeneficiario) {
        this.logradouroBeneficiario = logradouroBeneficiario;
    }

    public void setBairroBeneficiario(String bairroBeneficiario) {
        this.bairroBeneficiario = bairroBeneficiario;
    }

    public void setCepBeneficiario(String cepBeneficiario) {
        this.cepBeneficiario = cepBeneficiario;
    }

    public void setCidadeBeneficiario(String cidadeBeneficiario) {
        this.cidadeBeneficiario = cidadeBeneficiario;
    }

    public void setUfBeneficiario(String ufBeneficiario) {
        this.ufBeneficiario = ufBeneficiario;
    }

    public void setNomeBeneficiario(String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
    }

    public void setDocumentoBeneficiario(String documentoBeneficiario) {
        this.documentoBeneficiario = documentoBeneficiario;
    }

    public void setAgenciaBeneficiario(String agenciaBeneficiario) {
        this.agenciaBeneficiario = agenciaBeneficiario;
    }

    public void setDigitoAgenciaBeneficiario(String digitoAgenciaBeneficiario) {
        this.digitoAgenciaBeneficiario = digitoAgenciaBeneficiario;
    }

    public void setCodigoBeneficiario(String codigoBeneficiario) {
        this.codigoBeneficiario = codigoBeneficiario;
    }

    public void setDigitoCodigoBeneficiario(String digitoCodigoBeneficiario) {
        this.digitoCodigoBeneficiario = digitoCodigoBeneficiario;
    }

    public void setNumeroConvenioBeneficiario(String numeroConvenioBeneficiario) {
        this.numeroConvenioBeneficiario = numeroConvenioBeneficiario;
    }

    public void setCarteiraBeneficiario(String carteiraBeneficiario) {
        this.carteiraBeneficiario = carteiraBeneficiario;
    }

    public void setNossoNumeroBeneficiario(String nossoNumeroBeneficiario) {
        this.nossoNumeroBeneficiario = nossoNumeroBeneficiario;
    }

    public void setDigitoNossoNumeroBeneficiario(String digitoNossoNumeroBeneficiario) {
        this.digitoNossoNumeroBeneficiario = digitoNossoNumeroBeneficiario;
    }

    public void setLogradouroPagador(String logradouroPagador) {
        this.logradouroPagador = logradouroPagador;
    }

    public void setBairroPagador(String bairroPagador) {
        this.bairroPagador = bairroPagador;
    }

    public void setCepPagador(String cepPagador) {
        this.cepPagador = cepPagador;
    }

    public void setCidadePagador(String cidadePagador) {
        this.cidadePagador = cidadePagador;
    }

    public void setUfPagador(String ufPagador) {
        this.ufPagador = ufPagador;
    }

    public void setNomePagador(String nomePagador) {
        this.nomePagador = nomePagador;
    }

    public void setDocumentoPagador(String documentoPagador) {
        this.documentoPagador = documentoPagador;
    }

    public void setValorBoleto(BigDecimal valorBoleto) {
        this.valorBoleto = valorBoleto;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setInstrucoes(List<String> instrucoes) {
        this.instrucoes = instrucoes;
    }

    public void setLocaisDePagamento(List<String> locaisDePagamento) {
        this.locaisDePagamento = locaisDePagamento;
    }

    //    public void popularEmpresaCartao(
//            final long IdEmpresaBoleto,
//            final String Token,
//            final String NomeEmpresaBoleto) {
//        this.IdEmpresaBoleto = IdEmpresaBoleto;
//        this.NomeEmpresaBoleto = NomeEmpresaBoleto;
//        this.Token = Token;
//    }

    public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public void popularEmpresaCartao(final long idEmpresaBoleto, final String token,
                                     final String nomeEmpresaBoleto,
                                     final Calendar dataProcessamento, final Calendar dataVencimento,
                                     final String logradouroBeneficiario, final String bairroBeneficiario,
                                     final String cepBeneficiario, final String cidadeBeneficiario,
                                     final String ufBeneficiario, final String nomeBeneficiario,
                                     final String documentoBeneficiario, final String agenciaBeneficiario,
                                     final String digitoAgenciaBeneficiario, final String codigoBeneficiario,
                                     final String digitoCodigoBeneficiario, final String numeroConvenioBeneficiario,
                                     final String carteiraBeneficiario, final String nossoNumeroBeneficiario,
                                     final String digitoNossoNumeroBeneficiario, final String logradouroPagador,
                                     final String bairroPagador, final String cepPagador, final String cidadePagador,
                                     final String ufPagador, final String nomePagador, final String documentoPagador,
                                     final BigDecimal valorBoleto, final String numeroDocumento,
                                     final List<String> instrucoes, final List<String> locaisDePagamento) {
        this.IdEmpresaBoleto = idEmpresaBoleto;
        this.Token = token;
        this.NomeEmpresaBoleto = nomeEmpresaBoleto;
        this.dataProcessamento = dataProcessamento;
        this.dataVencimento = dataVencimento;
        this.logradouroBeneficiario = logradouroBeneficiario;
        this.bairroBeneficiario = bairroBeneficiario;
        this.cepBeneficiario = cepBeneficiario;
        this.cidadeBeneficiario = cidadeBeneficiario;
        this.ufBeneficiario = ufBeneficiario;
        this.nomeBeneficiario = nomeBeneficiario;
        this.documentoBeneficiario = documentoBeneficiario;
        this.agenciaBeneficiario = agenciaBeneficiario;
        this.digitoAgenciaBeneficiario = digitoAgenciaBeneficiario;
        this.codigoBeneficiario = codigoBeneficiario;
        this.digitoCodigoBeneficiario = digitoCodigoBeneficiario;
        this.numeroConvenioBeneficiario = numeroConvenioBeneficiario;
        this.carteiraBeneficiario = carteiraBeneficiario;
        this.nossoNumeroBeneficiario = nossoNumeroBeneficiario;
        this.digitoNossoNumeroBeneficiario = digitoNossoNumeroBeneficiario;
        this.logradouroPagador = logradouroPagador;
        this.bairroPagador = bairroPagador;
        this.cepPagador = cepPagador;
        this.cidadePagador = cidadePagador;
        this.ufPagador = ufPagador;
        this.nomePagador = nomePagador;
        this.documentoPagador = documentoPagador;
        this.valorBoleto = valorBoleto;
        this.numeroDocumento = numeroDocumento;
        this.instrucoes = instrucoes;
        this.locaisDePagamento = locaisDePagamento;
    }


}


