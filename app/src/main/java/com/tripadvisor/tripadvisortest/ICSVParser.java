package com.tripadvisor.tripadvisortest;

import java.util.List;

public interface ICSVParser {
    List<City> parse(String content);
}
