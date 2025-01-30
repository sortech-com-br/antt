package br.com.sortech.sorpag.filter;

import static spark.Spark.afterAfter;
import static spark.Spark.before;
import static spark.Spark.options;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sortech.sorpag.model.EmpresaBoleto;
import br.com.sortech.sorpag.service.App;
import br.com.sortech.sorpag.util.JWTDecoder;
import br.com.sortech.sorpag.util.TokenVerify;
import spark.utils.StringUtils;


public final class CorsFilter {


    private static ObjectMapper omfilter = new ObjectMapper();


    private static String[] acao;


    public static void enableCORS() {

        omfilter.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        afterAfter((request, response) -> {
            try {


                if (StringUtils.isNotEmpty(request.body())) {
                    App.logger.info("dados do body " + request.pathInfo());

                } else {
                    App.logger.info("Erro no afterAfter request.body igual a null  acao " + request.pathInfo());
                }


            } catch (Throwable e) {
                // TODO Auto-generated catch block
                App.logger.error("Erro no afterAfter: " + e.getMessage() + " acao " + request.pathInfo());
                //e.printStackTrace();
            }
        });


        before((request, response) -> {

                    response.header("Access-Control-Allow-Origin", "*");
                    response.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                    response.header("Access-Control-Allow-Headers", "origin, content-type, accept, Authorization");
                    response.header("Access-Control-Expose-Headers", "authorization");
                    response.type("application/json");

                    acao = request.pathInfo().split("/");

                    if(request.requestMethod() != "OPTIONS" || request.headers("Access-Control-Request-Method")==null || request.headers("Origin") ==null ) {
                    	if (!acao[acao.length - 1].equals("login") && !acao[acao.length - 2].equals("gerar") && !acao[acao.length - 1].equals("teste") && !acao[acao.length - 1].equals("errojson") && !acao[acao.length - 1].equals("erro") && !acao[acao.length - 1].equals("alterarSenha")) {
       					 if (request.headers("Authorization") == null) {
       							response.redirect("/erro");
       						}
       						TokenVerify validar = new TokenVerify(request.headers("Authorization"));
       						App.logger.info("validar token " + validar);
       						if (!validar.validateSignature(App.FIREBASE_SUPER_SECRET_KEY))
       						{
       							if (acao[acao.length-1].equals("login"))
       							{
       								App.logger.info("aqui 1");
       								validar = null;
       								response.redirect("/erro");
       							}
       							if (!validar.validateSignature(App.AMBIENTE+App.FIREBASE_SUPER_SECRET_KEY))
       							{
       								App.logger.info("aqui 2 ");
       								validar = null;
       								response.redirect("/erro");
       							}
       						}
       						
       						JWTDecoder decode = new JWTDecoder(request.headers("Authorization"));
                               if (!acao[acao.length - 1].equals("upload") && !acao[acao.length - 1].equals("testarinsert")&& !acao[acao.length - 1].equals("testarconsultar")) {
//                                   empresaboleto = omfilter.readValue(request.body(), EmpresaBoleto.class);
//                                   if (empresaboleto.getNomeEmpresaBoleto().trim().length() == 0) {
//                                       validar = null;
//                                       response.redirect("/errojson");
//       
//                                   }
                                   validar = null;
                               }
                           }

                    }
                }

        )
        ;

    }


}