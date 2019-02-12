package com.epam.dao;

import com.epam.logger.MyLogger;
import com.epam.entities.Product;
import com.epam.entities.ProductComposition;
import com.epam.entities.ProductType;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StAX_Parser implements Parser
{
    static final String COLD_SNACK_TAG = "cold_snack";
    static final String HOT_SNACK_TAG = "hot_snack";
    static final String BREAKFASTS_TAG = "breakfasts";

    static final String FOOD_TAG = "food";

    static final String IMG_TAG = "img";
    static final String TITLE_TAG = "title";
    static final String PORTION_TAG = "portion";

    static final String COMLEX_DESCRIPTION_TAG = "complexDescriptionContainer";
    static final String INNER_DESCRIPTION_TAG = "innerDescription";

    static final String ID_ATTRIBUTE = "id";

    static final String INNER_DESC_ATTRIBUTE = "descript";
    static final String PRICE_ATTRIBUTE = "price";
    static final String NUMBER_ATTRIBUTE = "number";
    static final String COMPLEX_DESC_ATTRIBUTE = "description";

    ParserType parserType;

    XMLInputFactory factory = XMLInputFactory.newInstance();


    private List<Product> products;
    private Product currentProduct;
    private ProductComposition currentComposition;
    private ProductType productType;

    private String charString;


    public StAX_Parser()
    {
        parserType = ParserType.STAX;
    }

    public String getParserName()
    {
        return "stax";
    }

    public ParserType getParserType()
    {
        return parserType;
    }

    public List<Product> parseXml(String path)  {

        MyLogger.logger.debug("StAX XML parsing is started.");
        products = new LinkedList<>();

        try{


            InputStreamReader fr = new InputStreamReader(new FileInputStream(path), "UTF-8");
            XMLEventReader eventReader = factory.createXMLEventReader(fr);

            while (eventReader.hasNext()) {

                XMLEvent xmlEvent = eventReader.nextEvent();

                switch (xmlEvent.getEventType()) {
                    case XMLEvent.START_ELEMENT: {
                        start_Element(xmlEvent);
                    }
                    break;
                    case XMLEvent.CHARACTERS: {
                        characters(xmlEvent);
                    }
                    break;
                    case XMLEvent.END_ELEMENT: {
                        end_Element(xmlEvent);
                    }
                    break;
                    case XMLEvent.END_DOCUMENT: {
                        end_Document(xmlEvent);
                    }
                    break;
                }
            }
        }
        catch(Exception e)
        {
            MyLogger.logger.error("exception", e);
            return products;
        }


        MyLogger.logger.debug("StAX XML parsing is complete.");

        return products;
    }

    void start_Element(XMLEvent xmlEvent)
    {
        StartElement startElement = xmlEvent.asStartElement();
        String tag = startElement.getName().getLocalPart();


        switch (tag)
        {
            case COLD_SNACK_TAG:{
                productType = ProductType.COLD_SNACK;

            }break;
            case HOT_SNACK_TAG:{
                productType = ProductType.HOT_SNACK;

            }break;
            case BREAKFASTS_TAG:{
                productType = ProductType.BREAKFAST;
            }break;

            case FOOD_TAG:{
                currentProduct = new Product();
                currentProduct.setType(productType);

                Iterator<Attribute> attributes = startElement.getAttributes();
                while (attributes.hasNext())
                {
                    Attribute attribute = attributes.next();
                    String attrName = attribute.getName().getLocalPart();
                    switch (attrName)
                    {
                        case ID_ATTRIBUTE:{
                            currentProduct.setId(Integer.valueOf(attribute.getValue()));
                        } break;
                        default: break;
                    }
                }

            } break;

            case IMG_TAG:{
            } break;

            case TITLE_TAG:{
            } break;

            case COMLEX_DESCRIPTION_TAG:{
                Iterator<Attribute> attributes = startElement.getAttributes();
                while (attributes.hasNext())
                {
                    Attribute attribute = attributes.next();
                    String attrName = attribute.getName().getLocalPart();
                    switch (attrName)
                    {
                        case COMPLEX_DESC_ATTRIBUTE:{
                            currentProduct.setDescription(attribute.getValue());
                        } break;
                        default: break;
                    }
                }
            } break;

            case INNER_DESCRIPTION_TAG:{
                currentComposition = new ProductComposition();

                Iterator<Attribute> attributes = startElement.getAttributes();
                while (attributes.hasNext())
                {
                    Attribute attribute = attributes.next();
                    String attrName = attribute.getName().getLocalPart();
                    switch (attrName)
                    {
                        case INNER_DESC_ATTRIBUTE:{
                            currentComposition.setDescription(attribute.getValue());
                        } break;
                        case NUMBER_ATTRIBUTE:{
                            currentComposition.setNumber(attribute.getValue());
                        } break;
                        case PRICE_ATTRIBUTE:{
                            currentComposition.setPrice(attribute.getValue());
                        } break;
                        default: break;
                    }
                }

                currentProduct.addComposition(currentComposition);
            } break;

            case PORTION_TAG:{
            } break;

            default:break;
        }

    }

    void characters(XMLEvent xmlEvent)
    {
        Characters characters = (Characters)xmlEvent;

        charString = characters.getData();

    }

    void end_Element(XMLEvent xmlEvent)
    {
        EndElement endElement = xmlEvent.asEndElement();
        String tag = endElement.getName().getLocalPart();

        switch (tag)
        {

            case FOOD_TAG:{
                products.add(currentProduct);
            }break;


            case IMG_TAG:{
                currentProduct.setImagePath(charString);
            }break;

            case TITLE_TAG:{
                currentProduct.setTitle(charString);
            }break;
            case PORTION_TAG:{
                currentProduct.setPortion(charString);
            }break;

            default: break;
        }
    }


    void end_Document(XMLEvent xmlEvent)
    {
        System.out.println("StAX XML parsing is complete"); //завершение обработки документа
    }


}
