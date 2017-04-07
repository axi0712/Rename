package com.example.administrator.opensourceinchina.parsing;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class MyHandler extends DefaultHandler {
      private String mNameStr;
    private ArrayList<Details> mList = new ArrayList<Details>();
    private Details mDetails;
    public ArrayList<Details> getmList(){
        return  mList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
       mNameStr = qName;
      if(mNameStr.equals("news")){
          if(mDetails == null){
              mDetails = new Details();
          }
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
       if(qName.equals("news")){
            mList.add(mDetails);
           mDetails = null;
       }
        mNameStr = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String str = new String(ch,start,length);
        if(mNameStr.equals("title")){
            mDetails.setTitle(str);
        }else if(mNameStr.equals("body")){
            mDetails.setBody(str);
        }else if(mNameStr.equals("url")){
            mDetails.setUrl(str);
        }else if (mNameStr.equals("id")){
            mDetails.setId(str);
        }
    }
}
