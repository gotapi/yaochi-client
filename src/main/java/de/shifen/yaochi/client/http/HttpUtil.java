package de.shifen.yaochi.client.http;


import de.shifen.yaochi.client.config.YaochiConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
@Component
@Slf4j
public class HttpUtil {

    @Autowired(required = false)
    private YaochiConfig yaochiConfig;


    public synchronized String sendLog(String jsonString,CredentialsProvider provider) {
        try {
            List<NameValuePair> paramsList = new ArrayList<>();
            paramsList.add(new BasicNameValuePair("logJsonString", jsonString));
            return sendPost(yaochiConfig.getServerAddress() + "/yaochi/log/add", paramsList,provider);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("sendLog error!", ex);
            return "";
        }
    }

    public synchronized String sendLog(String jsonString) {
        try {
            List<NameValuePair> paramsList = new ArrayList<>();
            paramsList.add(new BasicNameValuePair("logJsonString", jsonString));
            return sendPost(yaochiConfig.getServerAddress() + "/yaochi/log/add", paramsList,null);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("sendLog error!", ex);
            return "";
        }
    }

    private synchronized String sendPost(String url, List<NameValuePair> nameValuePairList,CredentialsProvider provider) throws Exception {
        CloseableHttpResponse response = null;
        CloseableHttpClient client;
        if(provider!=null){
            client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();
        }else{
            client = HttpClients.createDefault();
        }
        try  {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("send post error", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}
