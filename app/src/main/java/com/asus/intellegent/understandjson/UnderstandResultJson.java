package com.asus.intellegent.understandjson;

import java.util.List;

/**
 * Created by ASUS on 2017/7/15.
 */

public class UnderstandResultJson{
    /**
     * moreResults : [{"rc":0,"answer":{"type":"T","text":"今天是2017年07月15日 丁酉年六月二十二 星期六"},"service":"datetime","text":"现在多少号？今天的天气怎么样？","operation":"ANSWER"}]
     * webPage : {"header":"","url":"http://kcgz.openspeech.cn/service/iss?wuuid=eaae8a9b448eb75c284e2d5b9f6e6c55&ver=2.0&method=webPage&uuid=37818d89f745a40ec86618f2d80bc74dquery"}
     * semantic : {"slots":{"location":{"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"},"datetime":{"date":"2017-07-15","type":"DT_BASIC","dateOrig":"今天"}}}
     * rc : 0
     * operation : QUERY
     * service : weather
     * data : {"result":[{"airQuality":"优","sourceName":"中国天气网","date":"2017-07-14","lastUpdateTime":"2017-07-14 15:12:36","dateLong":1499961600,"pm25":"21","city":"东莞","humidity":"59%","windLevel":0,"weather":"雷阵雨转多云","tempRange":"26℃~34℃","wind":"无持续风向微风"},{"dateLong":1500134400,"sourceName":"中国天气网","date":"2017-07-16","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"中雨转雷阵雨","tempRange":"25℃~30℃","wind":"无持续风向微风"},{"dateLong":1500220800,"sourceName":"中国天气网","date":"2017-07-17","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"中雨转雷阵雨","tempRange":"25℃~30℃","wind":"无持续风向微风"},{"dateLong":1500307200,"sourceName":"中国天气网","date":"2017-07-18","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"中雨转阴","tempRange":"25℃~29℃","wind":"无持续风向微风"},{"dateLong":1500393600,"sourceName":"中国天气网","date":"2017-07-19","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"阵雨转多云","tempRange":"26℃~31℃","wind":"无持续风向微风"},{"dateLong":1500480000,"sourceName":"中国天气网","date":"2017-07-20","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"多云","tempRange":"26℃~32℃","wind":"无持续风向微风"},{"dateLong":1500566400,"sourceName":"中国天气网","date":"2017-07-21","lastUpdateTime":"2017-07-15 10:38:00","city":"东莞","windLevel":0,"weather":"多云转中雨","tempRange":"25℃~32℃","wind":"无持续风向微风"}]}
     * text : 现在多少号?今天的天气怎么样?
     */

    private WebPageBean webPage;
    private SemanticBean semantic;
    private int rc;
    private String operation;
    private String service;
    private DataBean data;
    private String text;
    private List<MoreResultsBean> moreResults;

    public WebPageBean getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPageBean webPage) {
        this.webPage = webPage;
    }

    public SemanticBean getSemantic() {
        return semantic;
    }

    public void setSemantic(SemanticBean semantic) {
        this.semantic = semantic;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MoreResultsBean> getMoreResults() {
        return moreResults;
    }

    public void setMoreResults(List<MoreResultsBean> moreResults) {
        this.moreResults = moreResults;
    }

    public static class WebPageBean {
        /**
         * header :
         * url : http://kcgz.openspeech.cn/service/iss?wuuid=eaae8a9b448eb75c284e2d5b9f6e6c55&ver=2.0&method=webPage&uuid=37818d89f745a40ec86618f2d80bc74dquery
         */

        private String header;
        private String url;

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class SemanticBean {
        /**
         * slots : {"location":{"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"},"datetime":{"date":"2017-07-15","type":"DT_BASIC","dateOrig":"今天"}}
         */

        private SlotsBean slots;

        public SlotsBean getSlots() {
            return slots;
        }

        public void setSlots(SlotsBean slots) {
            this.slots = slots;
        }

        public static class SlotsBean {
            /**
             * location : {"type":"LOC_POI","city":"CURRENT_CITY","poi":"CURRENT_POI"}
             * datetime : {"date":"2017-07-15","type":"DT_BASIC","dateOrig":"今天"}
             */

            private LocationBean location;
            private DatetimeBean datetime;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public DatetimeBean getDatetime() {
                return datetime;
            }

            public void setDatetime(DatetimeBean datetime) {
                this.datetime = datetime;
            }

            public static class LocationBean {
                /**
                 * type : LOC_POI
                 * city : CURRENT_CITY
                 * poi : CURRENT_POI
                 */

                private String type;
                private String city;
                private String poi;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getPoi() {
                    return poi;
                }

                public void setPoi(String poi) {
                    this.poi = poi;
                }
            }

            public static class DatetimeBean {
                /**
                 * date : 2017-07-15
                 * type : DT_BASIC
                 * dateOrig : 今天
                 */

                private String date;
                private String type;
                private String dateOrig;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getDateOrig() {
                    return dateOrig;
                }

                public void setDateOrig(String dateOrig) {
                    this.dateOrig = dateOrig;
                }
            }
        }
    }

    public static class DataBean {
        private List<ResultBean> result;

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * airQuality : 优
             * sourceName : 中国天气网
             * date : 2017-07-14
             * lastUpdateTime : 2017-07-14 15:12:36
             * dateLong : 1499961600
             * pm25 : 21
             * city : 东莞
             * humidity : 59%
             * windLevel : 0
             * weather : 雷阵雨转多云
             * tempRange : 26℃~34℃
             * wind : 无持续风向微风
             */

            private String airQuality;
            private String sourceName;
            private String date;
            private String lastUpdateTime;
            private int dateLong;
            private String pm25;
            private String city;
            private String humidity;
            private int windLevel;
            private String weather;
            private String tempRange;
            private String wind;

            public String getAirQuality() {
                return airQuality;
            }

            public void setAirQuality(String airQuality) {
                this.airQuality = airQuality;
            }

            public String getSourceName() {
                return sourceName;
            }

            public void setSourceName(String sourceName) {
                this.sourceName = sourceName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            public int getDateLong() {
                return dateLong;
            }

            public void setDateLong(int dateLong) {
                this.dateLong = dateLong;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public int getWindLevel() {
                return windLevel;
            }

            public void setWindLevel(int windLevel) {
                this.windLevel = windLevel;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTempRange() {
                return tempRange;
            }

            public void setTempRange(String tempRange) {
                this.tempRange = tempRange;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }

    public static class MoreResultsBean {
        /**
         * rc : 0
         * answer : {"type":"T","text":"今天是2017年07月15日 丁酉年六月二十二 星期六"}
         * service : datetime
         * text : 现在多少号？今天的天气怎么样？
         * operation : ANSWER
         */

        private int rc;
        private AnswerBean answer;
        private String service;
        private String text;
        private String operation;

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public AnswerBean getAnswer() {
            return answer;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public static class AnswerBean {
            /**
             * type : T
             * text : 今天是2017年07月15日 丁酉年六月二十二 星期六
             */

            private String type;
            private String text;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
