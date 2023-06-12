package br.angulosistemas;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Comunica {


        private static final String TAG = Comunica.class.getName();

        public Comunica() {
        }

        public String callHttpGenerico(
                URL url,
                HttpVerbo requestMethod,
                boolean hasOutput,
                boolean hasInput,
                HttpHeader contentType,
                HttpHeader accept,
                HashMap<String, String> requestProperties,
                String conteudoBody,
                int readTimeout,
                int connectionTimeout
        ) throws Exception {
            HttpURLConnection connection;
            //String retorno = "";
            StringBuilder responseBuilder = new StringBuilder();

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod.toString());
            connection.setDoOutput(hasOutput);
            connection.setDoInput(hasInput);


            if (contentType != null)
                connection.setRequestProperty("Content-Type", contentType.toString());

            if (accept != null)
                connection.setRequestProperty("Accept", accept.toString());

            if (conteudoBody != null && !conteudoBody.equals(""))
                connection.setRequestProperty("Content-Length", String.valueOf(conteudoBody.getBytes().length));

            if (requestProperties != null) {
                Iterator it = requestProperties.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> par = (Map.Entry) it.next();
                    connection.addRequestProperty(par.getKey(), par.getValue());
                }
            }

            //Opcoes default
            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);


            if (hasOutput) {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
                bufferedWriter.write(conteudoBody);
                bufferedWriter.flush();
                bufferedWriter.close();
            }

            if (hasInput) {
                int codStatus = connection.getResponseCode();
                System.out.println("Codigo de Resposta do Web Service: " + codStatus);
              //  Util.info("Codigo de Resposta do Web Service: " + codStatus);

                BufferedReader bufferedReader;

                if (codStatus > 204) {
                    if (connection.getErrorStream() == null)
                        throw new Exception("Codigo do Web Service (" + codStatus + "), não há resposta no erro do servidor");

                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        //retorno += line;
                        responseBuilder.append(line);
                    }

                    bufferedReader.close();

                    if (responseBuilder.toString().contains("message"))
                        throw new Exception(responseBuilder.toString());
                    else
                        throw new Exception("Codigo do Web Service (" + codStatus + "), verifique o link do Servidor");
                }

                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    //retorno += line;
                    responseBuilder.append(line);
                }

                bufferedReader.close();
            }

            connection.disconnect();
            return responseBuilder.toString() ;
        }

//        public InputStreamReader callHttpGerenericoGet(URL url, HttpVerbo requestMethod, boolean hasOutput, boolean hasInput, HttpHeader contentType, HttpHeader accept, HashMap<String, String> requestProperties, String conteudoBody, int readTimeout, int connectionTimeout) throws Exception {
//            HttpURLConnection connection;
//            BufferedInputStream retorno = null;
//
//            InputStreamReader inputStreamReader = null;
//
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod(requestMethod.toString());
//            connection.setDoOutput(hasOutput);
//            connection.setDoInput(hasInput);
//
//            if (contentType != null)
//                connection.setRequestProperty("Content-Type", contentType.toString());
//
//            if (accept != null)
//                connection.setRequestProperty("Accept", accept.toString());
//
//            if (conteudoBody != null && !conteudoBody.equals(""))
//                connection.setRequestProperty("Content-Length", String.valueOf(conteudoBody.getBytes().length));
//
//            if (requestProperties != null) {
//                Iterator it = requestProperties.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry<String, String> par = (Map.Entry) it.next();
//                    connection.addRequestProperty(par.getKey(), par.getValue());
//                }
//            }
//
//            //Opcoes default
//            connection.setInstanceFollowRedirects(false);
//            connection.setUseCaches(false);
//            connection.setConnectTimeout(connectionTimeout);
//            connection.setReadTimeout(readTimeout);
//
//
//            if (hasOutput) {
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
//                bufferedWriter.write(conteudoBody);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//            }
//
//            if (hasInput) {
//                int codStatus = connection.getResponseCode();
//                Util.info("Codigo de Resposta do Web Service: " + codStatus);
//
//                if (codStatus != 200){
//                    throw new Exception("Codigo do Web Service  diferente de OK (200)");
//                }
//
//
//                BufferedInputStream reader = new BufferedInputStream(connection.getInputStream());
//                inputStreamReader = new InputStreamReader(reader, Charset.forName("UTF-8"));
//                //reader.close();
//
//            }
//
//            //connection.disconnect();
//            return inputStreamReader;
//        }
//
//        //Metodos GET para pegar quaisquer objeto do projeto ou listas
//        public <T> Object getObjetcFromWebServices( Class<T> classRecebida, Object objectRetorno , String url ){
//
//            try {
//                JsonReader jsonReader = new JsonReader(callHttpGerenericoGet(new URL(url), HttpVerbo.METODO_GET, false, true, HttpHeader.APPLICATION_JSON, HttpHeader.TEXT_PLAIN, null, null, 1000 * 60 * 60, 1000 * 60 * 60));
//
//                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//
//                jsonReader.beginArray();
//                while (jsonReader.hasNext()) {
//                    objectRetorno = gson.fromJson(jsonReader, classRecebida);
//                }
//                jsonReader.endArray();
//                jsonReader.close();
//
//                return objectRetorno;
//            } catch (Exception e){
//                e.printStackTrace();
//                //Log.criarLogErro(e);
//                //Main.showNotificacao("OPS..", "Erro ao receber dados do webservice.", TrayIcon.MessageType.ERROR);
//                return objectRetorno;
//            } finally {
//                //Main.atualizarToolTip("Transmissor Angulo Sistemas - ConsWebDroid");
//            }
//        }
//
//        public <T> ArrayList<T> getListFromWebServices (Class<T> classRecebida, Object objectRetorno , String url ){
//
//            ArrayList<T> arrayList = new ArrayList<>();
//            try {
//                JsonReader jsonReader = new JsonReader(callHttpGerenericoGet(new URL(url), HttpVerbo.METODO_GET, false, true, HttpHeader.APPLICATION_JSON, HttpHeader.TEXT_PLAIN, null, null, 1000 * 60 * 60, 1000 * 60 * 60));
//
//                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//
//                jsonReader.beginArray();
//                while (jsonReader.hasNext()) {
//                    objectRetorno = gson.fromJson(jsonReader, classRecebida);
//                    arrayList.add((T) objectRetorno);
//                }
//                jsonReader.endArray();
//                jsonReader.close();
//
//                return arrayList;
//            } catch (Exception e){
//                e.printStackTrace();
//                //Log.criarLogErro(e);
//                //Main.showNotificacao("OPS..", "Erro ao receber dados do webservice.", TrayIcon.MessageType.ERROR);
//                return null;
//            } finally {
//                //Main.atualizarToolTip("Transmissor Angulo Sistemas - ConsWebDroid");
//            }
//        }
//
//        //Meotodos POST e PUT para enviar objetos do projeto
//        public boolean sendToWebServiceObjeto(Object objetoRecebido, String url){
//
//            Gson gson = new Gson();
//
//            String json;
//
//            try {
//                //Enviando produtos restantes da temporaryList que não entraram no limite
//                json = gson.toJson(objetoRecebido);
//
//                String retorno = callHttpGenerico(new URL(url), HttpVerbo.METODO_POST, true, true, HttpHeader.APPLICATION_JSON, HttpHeader.TEXT_PLAIN, null, json, 1000 * 60 * 60, 1000 * 60 * 60);
//                //if (!retorno.equals("1")) return false;
//
//                //Log.criarLogUpdate("Item enviado, tabela " + tableName);
//            } catch (Exception e){
//                e.printStackTrace();
//                //Log.criarLogErro(e);
//                //Main.showNotificacao("OPS..", "Erro ao enviar dados para o webservice.", TrayIcon.MessageType.ERROR);
//                return false;
//            } finally {
//                //Main.atualizarToolTip("Transmissor Angulo Sistemas - ConsWebDroid");
//            }
//            return true;
//        }
//
//        public boolean updateToWebServiceObjeto(Object objetoRecebido, String url){
//
//            Gson gson = new Gson();
//
//            String json;
//
//            try {
//                //Enviando produtos restantes da temporaryList que não entraram no limite
//                json = gson.toJson(objetoRecebido);
//
//                String retorno = callHttpGenerico(new URL(url), HttpVerbo.METODO_PUT, true, true, HttpHeader.APPLICATION_JSON, HttpHeader.TEXT_PLAIN, null, json, 1000 * 60 * 60, 1000 * 60 * 60);
//                //if (!retorno.equals("1")) return false;
//
//                //Log.criarLogUpdate("Item enviado, tabela " + tableName);
//            } catch (Exception e){
//                e.printStackTrace();
//                //Log.criarLogErro(e);
//                //Main.showNotificacao("OPS..", "Erro ao enviar dados para o webservice.", TrayIcon.MessageType.ERROR);
//                return false;
//            } finally {
//                //Main.atualizarToolTip("Transmissor Angulo Sistemas - ConsWebDroid");
//            }
//            return true;
//        }
    }

