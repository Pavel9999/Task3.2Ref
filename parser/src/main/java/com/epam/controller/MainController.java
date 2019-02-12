package com.epam.controller;

import com.epam.dao.Parser;
import com.epam.logger.MyLogger;
import org.xml.sax.SAXException;
import com.epam.dao.DOM_Parser;
import com.epam.dao.SAX_Parser;
import com.epam.dao.StAX_Parser;
import com.epam.entities.Product;
import com.epam.entities.ProductWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


@WebServlet(urlPatterns = {"/", "/index", "/menu"})
public class MainController extends HttpServlet {

    static final String xml_name = "mainmenu.xml";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MyLogger.logger.debug("URI request: " + req.getRequestURI());


        String reqUri = req.getRequestURI();

        try{
            if (reqUri.contains("/menu"))
            {
                getMenuPage(req,resp);
                return;
            }
            else
            {
                getIndex(req,resp);
                return;
            }

        }
        catch (Exception e)
        {
            MyLogger.logger.error("exception" ,e);
        }
    }


    protected void getIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("WEB-INF/pages/index.jsp").include(req, resp);
    }


    protected HttpServletRequest getMenuPage(HttpServletRequest req, HttpServletResponse resp) throws ParserConfigurationException, SAXException, ServletException, IOException {
        MyLogger.logger.debug("URI request: " + req.getRequestURI());

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(xml_name).getFile());
        String path = file.getAbsolutePath();

        Parser parser;
        String parser_type = "";
        ProductWrapper productWrapper = new ProductWrapper();
        String pr_type = "";
        String page_number = "";

        if (req.getParameterMap().containsKey("parser_type"))
        {
            parser_type = req.getParameter("parser_type");
        }
        switch (parser_type)
        {
            case "sax":{
                parser = new SAX_Parser();
            }break;
            case "dom":{
                parser = new DOM_Parser();
            }break;
            case "stax":{

                parser = new StAX_Parser();
            }break;
            default:{
                parser = new SAX_Parser();
            }break;
        }
        try {
            productWrapper.setProducts(parser.parseXml(path));
        } catch (Exception e) {
            MyLogger.logger.error("exception" ,e);
        }

        if (req.getParameterMap().containsKey("pr_type"))
        {
            pr_type = req.getParameter("pr_type");
        }

        if (req.getParameterMap().containsKey("page_num"))
        {
            page_number = req.getParameter("page_num");
        }

        List<Product> products = productWrapper.getProducts(pr_type, page_number, 5);
        int pagesCount = productWrapper.getPagesCount(pr_type, 5);



        req.setAttribute("products", products);
        req.setAttribute("parser_name",parser.getParserName());
        req.setAttribute("parser_type",parser.getParserName());
        req.setAttribute("pr_type",pr_type);
        req.setAttribute("pages_count",pagesCount);
        req.setAttribute("page_num",page_number);


        String language = "";
        if (req.getParameterMap().containsKey("lang"))
        {
            language = req.getParameter("lang");
        }
        req = packLocale(req, language);

        req.getRequestDispatcher("WEB-INF/pages/products.jsp").forward(req, resp);

        return req;
    }



    protected HttpServletRequest packLocale(HttpServletRequest req)
    {
        req = packLocale(req, "");
        return req;
    }

    protected HttpServletRequest packLocale(HttpServletRequest req, String language)
    {
        ResourceBundle rb = ResourceBundle.getBundle("locale", req.getLocale());

        switch (language)
        {
            case "ru":
            {
                rb = ResourceBundle.getBundle("locale", new Locale("ru","RU"));
            }break;
            case "en":
            {
                rb = ResourceBundle.getBundle("locale", new Locale("en","US"));
            }break;
            default: break;
        }

        req.setAttribute("lang", rb.getString("lang"));
        req.setAttribute("home_text", rb.getString("home"));
        req.setAttribute("cold_snack_text", rb.getString("cold_snack"));
        req.setAttribute("hot_snack_text", rb.getString("hot_snack"));
        req.setAttribute("breakfast_text", rb.getString("breakfast"));
        req.setAttribute("photo_text", rb.getString("photo"));
        req.setAttribute("title_text", rb.getString("title"));
        req.setAttribute("portion_text", rb.getString("portion"));
        req.setAttribute("description_text", rb.getString("description"));
        req.setAttribute("price_text", rb.getString("price"));

        return req;
    }

}
