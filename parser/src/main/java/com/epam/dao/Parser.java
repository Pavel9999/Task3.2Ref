package com.epam.dao;

import com.epam.entities.Product;

import java.util.List;

public interface Parser {

    public List<Product> parseXml(String path);

    public ParserType getParserType();

    public String getParserName();
}
