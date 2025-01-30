package br.com.sortech.sorpag.service;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.sortech.sorpag.filter.CorsFilter;
import br.com.sortech.sorpag.model.EmpresaBoleto;
import br.com.sortech.sorpag.model.Retorno;
import br.com.sortech.sorpag.util.BoletoGenerator;
import br.com.sortech.sorpag.util.PropertiesLoader;

public class App {

	public static int PORTA_SERVIDOR_HTTP;

	public static String NOME_SERVIDOR_HTTP;

	public static String URL_SMS;

	public static final Logger logger = Logger.getLogger(App.class.getName());

	private final ObjectMapper om = new ObjectMapper();

	public static String FIREBASE_SUPER_SECRET_KEY = "sortechsefdf";

	public static String BROKER;

	public static String RPC;

	public static String AMBIENTE;

	protected App(String porta) {

		PropertiesLoader props = new PropertiesLoader();
		NOME_SERVIDOR_HTTP = props.prop.getProperty("NomeServidor");
		PORTA_SERVIDOR_HTTP = Integer.parseInt(porta); // Integer.parseInt(props.prop.getProperty("PortaHTTP"));
		URL_SMS = props.prop.getProperty("URLSMS");
		BROKER = props.prop.getProperty("BROKER");
		RPC = props.prop.getProperty("RPC");
		AMBIENTE = props.prop.getProperty("AMBIENTE");
	}

	public static void main(String[] args) throws Throwable {

		App app = new App(args[0]);

		app.execAplicacao();

	}

	public void execAplicacao() throws IOException, TimeoutException, Exception {

		logger.info("Inicializando servidor. Nome:" + NOME_SERVIDOR_HTTP + " Porta:" + PORTA_SERVIDOR_HTTP);

		port(PORTA_SERVIDOR_HTTP);

		CorsFilter.enableCORS();

		get("/teste", (req, res) -> {;
			return String.format("Aplicação sorpag %s está funcionando", AMBIENTE);
		});

		get("/erro", (req, res) -> {
			return "Ocorreu um erro inesperado, por favor, tente mais tarde.";
		});

		get("/errojson", (req, res) -> {
			Retorno ret = new Retorno();
			res.type("application/json");
			om.setVisibility(om.getSerializationConfig().getDefaultVisibilityChecker()
					.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

			ret.setcodigo(0);
			ret.setmensagem(
					"Ocorreu um erro no formato JSON, favor validar os tipos de dados, formato e obrigatoriedade, de acordo com o manual de integra��o.");
			res.status(479);

			return om.writeValueAsString(ret);

		});

		logger.info("Servidor preparado.");

		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

		path("apic/", () -> {

			
			get("gerar/auth", (req, res) -> {
				Retorno ret = new Retorno();
	            // URL do endpoint de autenticação
	            String url = "https://oauth.hm.bb.com.br/oauth/token";

	            // Informações do cliente (substitua pelos valores corretos)
	            String clientId = "eyJpZCI6IjJmOTU1NWYtZGJlMS00OGMyLWEiLCJjb2RpZ29QdWJsaWNhZG9yIjowLCJjb2RpZ29Tb2Z0d2FyZSI6MTE4MzI1LCJzZXF1ZW5jaWFsSW5zdGFsYWNhbyI6MX0";
	            String clientSecret = "eyJpZCI6ImExNjE5NDMtYzIxMC00ZmYwLWFkOWUtOSIsImNvZGlnb1B1YmxpY2Fkb3IiOjAsImNvZGlnb1NvZnR3YXJlIjoxMTgzMjUsInNlcXVlbmNpYWxJbnN0YWxhY2FvIjoxLCJzZXF1ZW5jaWFsQ3JlZGVuY2lhbCI6MSwiYW1iaWVudGUiOiJob21vbG9nYWNhbyIsImlhdCI6MTczMzEzMTM3ODMxMH0";

	            // Codificação Base64 para o cabeçalho Authorization (Basic Auth)
	            String authHeader = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
	            
	            // Corpo da requisição
	            String requestBody = "grant_type=client_credentials";

	            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	                HttpPost httpPost = new HttpPost(url);
	                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
	                httpPost.setHeader("Accept", "application/json");
	                httpPost.setHeader("Authorization", "Basic " + authHeader);

	                // Define o corpo da requisição
	                HttpEntity stringEntity = new StringEntity(requestBody);
	                httpPost.setEntity(stringEntity);

	                // Envia a requisição e recebe a resposta
	                CloseableHttpResponse response = httpClient.execute(httpPost);

	                // Lê o conteúdo da resposta
	                int statusCode = response.getStatusLine().getStatusCode();
	                String responseBody = EntityUtils.toString(response.getEntity());

		            // Exibição da resposta HTTP
		            
		            JSONObject jsonResponse = new JSONObject(responseBody);
		            String accessToken = jsonResponse.getString("access_token");
		            
		            res.status(200);
                    ret.setcodigo(1);
                    ret.setmensagem(accessToken);
                    return om.writeValueAsString(ret);

		        } catch (IOException e) {
		            e.printStackTrace();
		            logger.error("outsortech: " + e.toString());
                    ret.setcodigo(0);
                    ret.setmensagem(e.toString());
                    res.status(400);
                    return om.writeValueAsString(ret);
		        }
            });

			post("gerar/cobranca", (req, res) -> {
                Retorno ret = new Retorno();
                EmpresaBoleto empresa = null;
                BoletoGenerator boletoGenerator = new BoletoGenerator();
                try {

                    res.type("application/json");

                    empresa = om.readValue(req.body(), EmpresaBoleto.class);
                    boletoGenerator.comEmpresa(empresa).gerarBoleto();
                    res.status(200);
                    ret.setcodigo(1);
                    ret.setmensagem(boletoGenerator.getNomeBoleto());
                    return om.writeValueAsString(ret);

                } catch (Throwable e) {
                    logger.error("outsortech: " + e.toString());
                    ret.setcodigo(0);
                    ret.setmensagem(e.toString());
                    res.status(400);
                    return om.writeValueAsString(ret);
                }
            });

		});

	}
	

}