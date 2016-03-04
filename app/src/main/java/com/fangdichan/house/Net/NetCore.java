package com.fangdichan.house.Net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class NetCore
{

    private final String ServerAddr = "http://211.87.239.112:8080/FangDiChan/";
//    public static String PicAddr="http://211.87.239.34:8080/FangDiChan/pics/";
    public static String PicAddr="http://211.87.239.112:8080/FangDiChan/pics/houselist/";
    public static String HeadPicAddr="http://211.87.239.112:8080/FangDiChan/pics/head/";

    private final String LoginAddr = ServerAddr + "login.action";
    private final String RegisterAddr = ServerAddr + "register.action";
    private final String GetUserInfoAddr=ServerAddr+"userInfo.action";
    private final String GetHouseListAddr=ServerAddr+"houseList.action";
    private final String ScanCodeAddr=ServerAddr+"scanCode.action";
    private final String GetChildListAddr=ServerAddr+"getChildList.action";
    private final String GetMoneyListAddr=ServerAddr+"getMoneyList.action";
    private final String GetHouseInfoAddr=ServerAddr+"houseDetail.action";
    private final String ScanAddr=ServerAddr+"scan.action";
    private final String BonusRankAddr=ServerAddr+"bonusRank.action";
    private final String GetMyBonusAddr=ServerAddr+"myBonus.action";
    private final String GetSpreadAddr=ServerAddr+"findChild.action";
    private final String GetNumberAddr=ServerAddr+"getNumber";

    private static final int REQUEST_TIMEOUT = 15000;//设置请求超时10秒钟
    private static final int SO_TIMEOUT = 15000;  //设置等待数据超时时间10秒钟

    public static String SESSID = null;
    private DefaultHttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private HttpResponse httpResponse;



    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    public String GetBonusRank()
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String result = "";
        result = GetResultFromNet(BonusRankAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }


    public String Login(String user_phone, String user_pwd)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        System.out.println(user_phone+"    "+user_pwd);
        params.add(new BasicNameValuePair("user.phone", user_phone));
        params.add(new BasicNameValuePair("user.pwd", user_pwd));
        String result = "";
            result = GetResultFromNet(LoginAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String Scan(String user_id,String code)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", user_id));
        params.add(new BasicNameValuePair("code", code));
        String result = "";
        result = GetResultFromNet(ScanAddr, params);
        if(result.equals(""))
            return null;
        Log.e("result",result);
        System.out.println(result);
        return result;
    }

    public String Register(String user_name, String user_pwd,String user_phone,String user_city)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user.name", user_name));
        params.add(new BasicNameValuePair("user.pwd", user_pwd));
        params.add(new BasicNameValuePair("user.phone", user_phone));
        params.add(new BasicNameValuePair("user.city", user_city));
        String result = "";
        result = GetResultFromNet(RegisterAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetUserInfoByPhone(String phone)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("choice", ""+1));
        String result = "";
            result = GetResultFromNet(GetUserInfoAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetUserInfoByEmail(String email)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("choice", ""+0));
        String result = "";
        result = GetResultFromNet(GetUserInfoAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetHouseList(int currentPage,int pageSize)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("currentPage", ""+currentPage));
        params.add(new BasicNameValuePair("pageSize", ""+pageSize));
        String result = "";
        result = GetResultFromNet(GetHouseListAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetMyBonus(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetMyBonusAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetNumber(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetNumberAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetSpreads(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetSpreadAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetHouseInfo(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetHouseInfoAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String ScanCode(String code)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("code", code));
        String result = "";
        result = GetResultFromNet(ScanCodeAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }

    public String GetChildList(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetChildListAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }


    public String GetMoneyList(String id)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        String result = "";
        result = GetResultFromNet(GetMoneyListAddr, params);
        if(result.equals(""))
            return null;
        System.out.println(result);
        return result;
    }


    /**
     *
     * @param url
     *            地址
     * @param params
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @throws Exception
     */
    private String GetResultFromNet(String url, List<NameValuePair> params)
    {
        String jsonData = "";
        try {
        HttpPost httpRequest = new HttpPost(url);
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == 200)
        {
            InputStream is = httpResponse.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                jsonData += line + "\r\n";
            }
        }
        Log.e("http result ", jsonData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonData.equals(""))
        {
            jsonData="{\"status\":\"0\"}";
        }
        return jsonData;
    }

//    public String GetResultFromNet(String path, List<NameValuePair> params) {
//        String ret = "{\"status\":\"0\"}";
//        try {
//            this.httpPost = new HttpPost(path);
//            httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//            httpPost.setEntity(httpEntity);
//            //第一次一般是还未被赋值，若有值则将SessionId发给服务器
//            if(null != SESSID){
//                httpPost.setHeader("Cookie", "JSESSIONID=" + SESSID);
//            }
//            BasicHttpParams httpParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
//            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
//            httpClient = new DefaultHttpClient(httpParams);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        try {
//            httpResponse = httpClient.execute(httpPost);
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                HttpEntity entity = httpResponse.getEntity();
//                ret = EntityUtils.toString(entity,"UTF_8");
//                CookieStore mCookieStore = httpClient.getCookieStore();
//                List<Cookie> cookies = mCookieStore.getCookies();
//                for (int i = 0; i < cookies.size(); i++) {
//                    if ("JSESSIONID".equals(cookies.get(i).getName())) {
//                        SESSID = cookies.get(i).getValue();
//                        break;
//                    }
//                }
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }

//    private String GetResultFromNet(String url, List<NameValuePair> params) throws ClientProtocolException, IOException
//    {
//        HttpPost httpRequest = new HttpPost(url);
//        httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
//        String jsonData = "";
//        if (httpResponse.getStatusLine().getStatusCode() == 200)
//        {
//            InputStream is = httpResponse.getEntity().getContent();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String line = "";
//            while ((line = br.readLine()) != null)
//            {
//                jsonData += line + "\r\n";
//            }
//        }
//        // jsonData=new String(jsonData.getBytes(),"gb2312");
//        return jsonData;
//    }

//    private void sendHttpPost(String url, List<NameValuePair> params){
//        HttpPost httpRequest = new HttpPost(url);
//        try {
//            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        try {
//            new DefaultHttpClient().execute(httpRequest);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

//    private String UploadAvata(String url,String sessionId, String sessionToken, String parentId, String pupilId, String file) throws ClientProtocolException, IOException
//    {
//        HttpPost httpRequest = new HttpPost(url);
//        FileBody fileBody = new FileBody(new File(file));
//        StringBody session_Id = new StringBody(sessionId);
//        StringBody session_Token = new StringBody(sessionToken);
//        StringBody parent_Id = new StringBody(parentId);
//        StringBody pupil_Id = new StringBody(pupilId);
//        MultipartEntity entity = new MultipartEntity();
//        entity.addPart("file", fileBody);
//        entity.addPart("sessionId", session_Id);
//        entity.addPart("sessionToken", session_Token);
//        entity.addPart("parentId", parent_Id);
//        entity.addPart("pupilId", pupil_Id);
//
//        httpRequest.setEntity(entity);
//        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
//        String jsonData = "";
//        if (httpResponse.getStatusLine().getStatusCode() == 200)
//        {
//            InputStream is = httpResponse.getEntity().getContent();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String line = "";
//            while ((line = br.readLine()) != null)
//            {
//                jsonData += line + "\r\n";
//            }
//        }
//        return jsonData;
//    }

}
