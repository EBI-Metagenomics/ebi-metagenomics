package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class connects to the template service to get the ebi mast head and footer
 *
 * @author Gift Nuka
 *
 */
public class TemplateService {
    String globalMastHead;
    String localMastHead;
    String globalFooter;

    public String getGlobalMastHead() {
        return globalMastHead;
    }

    public String getLocalMastHead() {
        return localMastHead;
    }

    public String getGlobalFooter() {
        return globalFooter;
    }

    /**
     * se the global masthead using the webtemplate service
     */
    public void setGlobalMastHead() {
        StringBuffer globalMastHeadInputLine = new StringBuffer();
        try{
            URL oracle = new URL("http://wwwint.ebi.ac.uk/web/template-service/prod/templates/compliance/masthead/global/services");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                globalMastHeadInputLine.append(inputLine);
            in.close();
        }catch(Exception ex){
            globalMastHeadInputLine.append(getGlobalMastHeadStatic());
        }finally {
            if(globalMastHeadInputLine.equals("")){
                globalMastHeadInputLine.append(getGlobalMastHeadStatic());
            }
        }
        this.globalMastHead = globalMastHeadInputLine.toString();
    }

    /**
     * set the global masthead statically, to be used when an error is received from the template service
     *
     * @return
     */
    public String getGlobalMastHeadStatic() {
       return "<p>getGlobalMastHeadStatic</p>";
    }

    /**
     * set the local masthead from the template service:
     *   read json data from file
     *   connect to the template service
     *   send the data to the template service
     *   read the response from the template service     *
     *
     */
    public void setLocalMastHead() {
        StringBuffer localMastHeadInputLine = new StringBuffer();
        HttpURLConnection httpconnection = null;
        String mastHeadJson = getJsonInputLocally();
        String tracker = "";
        try{
            URL url = new URL("http://wwwint.ebi.ac.uk/web/template-service/dev/templates/compliance/masthead/local");
            URL urlFull = new URL("http://wwwint.ebi.ac.uk/web/template-service/prod/templates/compliance/service/full");
            httpconnection = (HttpURLConnection) (urlFull.openConnection());
            httpconnection.setDoOutput(true);
            httpconnection.setRequestProperty("Content-Type", "application/json");
//            httpconnection.setRequestProperty("Accept", "application/json");
            httpconnection.setRequestProperty("Accept", "text/*");
            httpconnection.setRequestMethod("POST");

            httpconnection.setDoInput(true);
            httpconnection.setDoOutput(true);
            httpconnection.connect();
            InputStream inputStream = null;

            System.out.println(mastHeadJson);

            if(! mastHeadJson.equals("")){
                //send the mastHeadJson
                byte[] outputBytes = mastHeadJson.getBytes("UTF-8");
                OutputStream os = httpconnection.getOutputStream();
                os.write(outputBytes);
                os.close();

                //Get Response
                String httpResponseMessage = httpconnection.getResponseMessage();
                System.out.println("httpResponseMessage: " + httpResponseMessage);

                //Get Response 2
                InputStream is = httpconnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer httpResponse = new StringBuffer();
                tracker = "readline";
                while ((line = rd.readLine()) != null) {
                    httpResponse.append(line);
//                    System.out.println("Output:" + line);
//                    httpResponse.append('\r');
                }
                rd.close();
                is.close();
                System.out.println("Output from server: (length) " + httpResponse.toString().trim().length());

                localMastHead = httpResponse.toString();
                localMastHeadInputLine.append(localMastHead);
            } else {
                localMastHead = "<p>mastHeadJson is empty</p>";
            }
            httpconnection.disconnect();
        }catch(Exception ex){
            //System.out.println("");
            localMastHeadInputLine.append("exception in set local masthead: " + tracker + " "  + ex);
            localMastHead  = getLocalMastHeadStatic();
        }finally {
            if(this.localMastHead == null){
                localMastHeadInputLine.append("<p>finally in set local masthead, but localMastHead is null : </p>" + mastHeadJson);
                this.localMastHead = localMastHeadInputLine.toString();
            }else if(this.localMastHead == null || this.localMastHead.equals("") || localMastHeadInputLine.equals("")){
                localMastHeadInputLine.append("finally in set local masthead" + getLocalMastHeadStatic());
                this.localMastHead = localMastHeadInputLine.toString();
            }
        }


    }

    public String getLocalMastHeadStatic() {
        return "<p> getJsonFileInput for LocalMastHead (Static) </p>";
    }

    /**
     * get the json data from a local file (localmasthead only)
     *
     * @return
     */
    public String getJsonInputLocally() {
        InputStream inputStream = null;
        StringBuffer mastHeadJson = new StringBuffer();
        try{
            final Resource fileResource = new ClassPathResource("compliance/interpro-localmasthead.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fileResource.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null)
                mastHeadJson.append(inputLine);
            bufferedReader.close();

        }catch(Exception ex){
            throw new IllegalStateException("Failed to get the local masthead  json file : " + ex);
        }
        return mastHeadJson.toString();

    }

    /**
     * get the json data from a local file (full template)
     *
     * @return
     */
    public String getJsonInputLocallyFullTemplate(){
        InputStream inputStream = null;
        StringBuffer mastHeadJson = new StringBuffer();
        try{
            final Resource fileResource = new ClassPathResource( "compliance/interpro-masthead.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(fileResource.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null)
                mastHeadJson.append(inputLine);
            bufferedReader.close();
        }catch(Exception ex){
            throw new IllegalStateException("Failed to get the full template masthead  json file : " + ex);
        }
        return mastHeadJson.toString();
    }

    public String getGlobalFooterStatic() {
        return "<p> getGlobalFooterStatic </p>";
    }

    /**
     * set the global footer from the template service
     *
     */
    public void setGlobalFooter() {
        StringBuffer globalFooterInputLine = new StringBuffer();
        try{
            URL oracle = new URL("http://wwwint.ebi.ac.uk/web/template-service/prod/templates/compliance/footer/global");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                globalFooterInputLine.append(inputLine);
            in.close();
        }catch(Exception ex){
            //System.out.println("");
            globalFooterInputLine.append(getGlobalFooterStatic());
        }finally {
            if(globalFooterInputLine.equals("")){
                globalFooterInputLine.append(getGlobalFooterStatic());
            }
        }
        this.globalFooter = globalFooterInputLine.toString();
    }


    public TemplateService(){
        setGlobalMastHead();
        setLocalMastHead();
        setGlobalFooter();
    }
}
