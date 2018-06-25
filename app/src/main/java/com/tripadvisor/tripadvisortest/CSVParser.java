package com.tripadvisor.tripadvisortest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVParser implements ICSVParser {

    public static final String REGEX = "\"(.+?)\"";

    @Override
    public List<City> parse(String content) {
        final Pattern pattern = Pattern.compile(REGEX);
        final Matcher matcher = pattern.matcher(content);
        return getCitiesList(matcher);
    }

    private List<City> getCitiesList(Matcher matcher) {
        List<City> resultList = new ArrayList<>();
        int index = 0;
        City city = null;
        while (matcher.find()) {
            switch (index % 4) {
                case 0:
                    city = new City();
                    city.setName(matcher.group(1));
                    break;
                case 1:
                    city.setCountry(matcher.group(1));
                    break;
                case 2:
                    city.setUrl(matcher.group(1));
                    break;
                case 3:
                    city.setDescription(matcher.group(1));
                    resultList.add(city);
                    break;
            }
            index++;
        }
        return resultList;
    }
}