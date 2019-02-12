package com.epam.dao;

import com.epam.logger.MyLogger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import com.epam.entities.Product;
import com.epam.entities.ProductComposition;
import com.epam.entities.ProductType;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.LinkedList;
import java.util.List;

public class SAX_Parser  implements Parser
{
    ParserType parserType;

    MyHandler handler;
    XMLReader xmlReader;

    public SAX_Parser() throws ParserConfigurationException, SAXException
    {
        parserType = ParserType.SAX;

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        xmlReader = saxParser.getXMLReader();
        handler = new MyHandler();
        xmlReader.setContentHandler(handler);
    }

    public ParserType getParserType()
    {
        return parserType;
    }

    public String getParserName()
    {
        return "sax";
    }

    public List<Product> parseXml(String path) {
        MyLogger.logger.debug("SAX XML parsing is started.");
        List<Product> products = new LinkedList<>();
        try {
            xmlReader.parse(path);
            products = handler.getReport();
        }
        catch (Exception e)
        {
            MyLogger.logger.error("exception", e);
            return products;
        }

        MyLogger.logger.debug("SAX XML parsing is complete.");
        return products;
    }

    private static class MyHandler extends DefaultHandler
    {
        static final String COLD_SNACK_TAG = "m:cold_snack";
        static final String HOT_SNACK_TAG = "m:hot_snack";
        static final String BREAKFASTS_TAG = "m:breakfasts";

        static final String FOOD_TAG = "m:food";

        static final String IMG_TAG = "m:img";
        static final String TITLE_TAG = "m:title";
        static final String PORTION_TAG = "m:portion";

        static final String COMPLEX_DESCRIPTION_TAG = "m:complexDescriptionContainer";
        static final String INNER_DESCRIPTION_TAG = "m:innerDescription";

        static final String ID_ATTRIBUTE = "m:id";

        static final String INNER_DESC_ATTRIBUTE = "m:descript";
        static final String PRICE_ATTRIBUTE = "m:price";
        static final String NUMBER_ATTRIBUTE = "m:number";
        static final String COMPLEX_DESC_ATTRIBUTE = "m:description";

        private List<Product> products;
        private Product currentProduct;
        private ProductComposition currentComposition;
        private ProductType productType;
        private String currentElement;

        public List<Product> getReport()
        {
            return products;
        }

        public void startDocument() {
            products = new LinkedList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
            currentElement = qName;

            switch (currentElement) {
                case COLD_SNACK_TAG:{
                    productType = ProductType.COLD_SNACK;
                } break;
                case HOT_SNACK_TAG:{
                    productType = ProductType.HOT_SNACK;
                } break;
                case BREAKFASTS_TAG:{
                    productType = ProductType.BREAKFAST;
                } break;

                case FOOD_TAG:{
                    currentProduct = new Product();
                    currentProduct.setType(productType);
                    currentProduct.setId(Integer.valueOf(attributes.getValue(ID_ATTRIBUTE)));
                } break;

                case IMG_TAG:{
                } break;

                case TITLE_TAG:{
                } break;

                case COMPLEX_DESCRIPTION_TAG:{
                    currentProduct.setDescription(attributes.getValue(COMPLEX_DESC_ATTRIBUTE));
                } break;

                case INNER_DESCRIPTION_TAG:{
                    currentComposition = new ProductComposition();
                    currentComposition.setDescription(attributes.getValue(INNER_DESC_ATTRIBUTE));
                    currentComposition.setNumber(attributes.getValue(NUMBER_ATTRIBUTE));
                    currentComposition.setPrice(attributes.getValue(PRICE_ATTRIBUTE));
                    currentProduct.addComposition(currentComposition);
                } break;

                case PORTION_TAG:{
                } break;

                default: break;
            }
        }

        //Событие, которые вызывается при каждом новом элементе и выдаёт строку, содержащуюся внутри элемента
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException{
            String text = new String (ch, start, length);


            if (text.contains("<") || currentElement == null || text.length() < 1){
                return;
            }


            switch (currentElement) {
                case IMG_TAG:{
                    currentProduct.setImagePath(text);
                } break;

                case TITLE_TAG:{
                    currentProduct.setTitle(text);
                } break;

                case PORTION_TAG:{
                    currentProduct.setPortion(text);
                } break;

                default: break;
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException{

            currentElement = null;

            switch (qName)
            {
                case FOOD_TAG:{


                    products.add(currentProduct);
                } break;

                default: break;
            }
        }

        public void endDocument() throws SAXException
        {
            System.out.println("SAX XML parsing is complete"); //завершение обработки документа
        }
    }
}


