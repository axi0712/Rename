package com.example.administrator.opensourceinchina.parsing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class DefaultTwo extends DefaultHandler {
    private String url;
    private String mNameStr;
      public  String  getString(){
          return  url;
      }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        mNameStr=qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      mNameStr="";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String str=new String(ch,start,length);
        if(mNameStr.equals("url")){
            this.url=str;
        }
    }
}
