package com.asus.intellegent.understandjson;

import java.util.List;

/**
 * Created by ASUS on 2017/7/24.
 */

public class TextJson {

    /**
     * semantic : {"slots":{"name":"爱奇艺","code":"1800272919","url":"http://www.iqiyi.com"}}
     * rc : 0
     * operation : LAUNCH
     * service : app
     * moreResults : [{"semantic":{"slots":{"name":"爱奇艺","url":"http://www.iqiyi.com"}},"rc":0,"operation":"OPEN","service":"website","text":"打开爱奇艺"}]
     * text : 打开爱奇艺
     */

    private SemanticBean semantic;
    private int rc;
    private String operation;
    private String service;
    private String text;
    private List<MoreResultsBean> moreResults;

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

    public static class SemanticBean {
        /**
         * "slots":{"name":"爱奇艺","code":"1800272919","url":"http://www.iqiyi.com"}
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
             * name : 爱奇艺
             * code : 1800272929
             * url : http://www.iqiyi.com
             */

            private String name;
            private String code;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class MoreResultsBean {
        /**
         * semantic : {"slots":{"name":"爱奇艺","url":"http://www.iqiyi.com"}}
         * rc : 0
         * operation : OPEN
         * service : website
         * text : 打开爱奇艺
         */

        private SemanticBeanX semantic;
        private int rc;
        private String operation;
        private String service;
        private String text;

        public SemanticBeanX getSemantic() {
            return semantic;
        }

        public void setSemantic(SemanticBeanX semantic) {
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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static class SemanticBeanX {
            /**
             * slots : {"name":"爱奇艺","url":"http://www.iqiyi.com"}
             */

            private SlotsBeanX slots;

            public SlotsBeanX getSlots() {
                return slots;
            }

            public void setSlots(SlotsBeanX slots) {
                this.slots = slots;
            }

            public static class SlotsBeanX {
                /**
                 * name : 爱奇艺
                 * url : http://www.iqiyi.com
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
