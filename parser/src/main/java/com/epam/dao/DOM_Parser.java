package com.epam.dao;

import com.epam.logger.MyLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.epam.entities.Product;
import com.epam.entities.ProductComposition;
import com.epam.entities.ProductType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.LinkedList;
import java.util.List;

public class DOM_Parser implements Parser {

    static final String COLD_SNACK_TAG = "m:cold_snack";
    static final String HOT_SNACK_TAG = "m:hot_snack";
    static final String BREAKFASTS_TAG = "m:breakfasts";

    static final String FOOD_TAG = "m:food";

    static final String IMG_TAG = "m:img";
    static final String TITLE_TAG = "m:title";
    static final String PORTION_TAG = "m:portion";

    static final String COMLEX_DESCRIPTION_TAG = "m:complexDescriptionContainer";
    static final String INNER_DESCRIPTION_TAG = "m:innerDescription";

    static final String ID_ATTRIBUTE = "m:id";

    static final String INNER_DESC_ATTRIBUTE = "m:descript";
    static final String PRICE_ATTRIBUTE = "m:price";
    static final String NUMBER_ATTRIBUTE = "m:number";
    static final String COMPLEX_DESC_ATTRIBUTE = "m:description";

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    ParserType parserType;

    public DOM_Parser() throws ParserConfigurationException {

        parserType = ParserType.DOM;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

    }

    public ParserType getParserType()
    {
        return parserType;
    }

    public String getParserName()
    {
        return "dom";
    }


    public List<Product> parseXml(String path) {

        MyLogger.logger.debug("DOM XML parsing is started.");
        Document document;
        List<Product> products = new LinkedList<>();

        try{
            document = builder.parse(path);
        }
        catch (Exception e)
        {
            MyLogger.logger.error("exception", e);
            return products;
        }


        NodeList coldSnackNodes = document.getElementsByTagName(COLD_SNACK_TAG);
        NodeList hotSnackNodes = document.getElementsByTagName(HOT_SNACK_TAG);
        NodeList breakfastNodes = document.getElementsByTagName(BREAKFASTS_TAG);

        for (int i = 0 ; i < coldSnackNodes.getLength(); i++)
        {
            Element node = (Element)coldSnackNodes.item(i);
            List<Product> coldSnack = parseMenu(node, ProductType.COLD_SNACK);
            products.addAll(coldSnack);
        }
        for (int i = 0 ; i < hotSnackNodes.getLength(); i++)
        {
            Element node = (Element)hotSnackNodes.item(i);
            List<Product> hotSnack = parseMenu(node, ProductType.HOT_SNACK);
            products.addAll(hotSnack);
        }
        for (int i = 0 ; i < breakfastNodes.getLength(); i++)
        {
            Element node = (Element)breakfastNodes.item(i);
            List<Product> breakfast = parseMenu(node, ProductType.BREAKFAST);
            products.addAll(breakfast);
        }

        MyLogger.logger.debug("DOM XML parsing is complete.");


        return products;
    }

    private List<Product> parseMenu(Element menuNode, ProductType productType)
    {
        List<Product> products = new LinkedList<>();
        NodeList foodList = menuNode.getChildNodes();

        for (int i = 0 ; i < foodList.getLength(); i++)
        {
            if (foodList.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                Element food = (Element) foodList.item(i);
                if (food.getTagName() == FOOD_TAG) {
                    Product newProduct = parseFood(food);
                    newProduct.setType(productType);
                    products.add(newProduct);
                }
            }
        }

        return products;
    }

    private Product parseFood(Element food)
    {
        Product newProduct = new Product();

        newProduct.setId(Integer.valueOf(food.getAttribute(ID_ATTRIBUTE)));

        NodeList childNodes = food.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++)
        {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                Element node = (Element) childNodes.item(i);
                switch (node.getTagName())
                {
                    case IMG_TAG:{
                        newProduct.setImagePath(childNodes.item(i).getTextContent());
                    }break;
                    case TITLE_TAG:{
                        newProduct.setTitle(childNodes.item(i).getTextContent());
                    }break;
                    case COMLEX_DESCRIPTION_TAG:{
                        newProduct.setDescription(node.getAttribute(COMPLEX_DESC_ATTRIBUTE));
                        List<ProductComposition> pc = parseComplexDesc(node);
                        newProduct.setComposition(pc);
                    }break;
                    case PORTION_TAG:{
                        newProduct.setPortion(childNodes.item(i).getTextContent());
                    }break;


                    default: break;
                }
            }
        }

        return newProduct;
    }

    private List<ProductComposition> parseComplexDesc(Element complex_desc)
    {
        List<ProductComposition> compositions = new LinkedList<>();

        NodeList childNodes = complex_desc.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element node = (Element)childNodes.item(i);
                if (node.getTagName() == INNER_DESCRIPTION_TAG)
                {
                    ProductComposition newComposition = new ProductComposition();

                    newComposition.setDescription(node.getAttribute(INNER_DESC_ATTRIBUTE));
                    newComposition.setNumber(node.getAttribute(NUMBER_ATTRIBUTE));
                    newComposition.setPrice(node.getAttribute(PRICE_ATTRIBUTE));

                    compositions.add(newComposition);
                }
            }
        }
        return compositions;
    }

}
