package com.asus.intellegent.understandjson;

import java.util.List;

/**
 * Created by ASUS on 2017/7/30.
 */

public class AnswerJson {

    /**
     * moreResults : [{"rc":0,"answer":{"type":"T","text":" 词语解释你好是对别人一种尊敬，遇到认识的人或不认识的人都可以说的。你好主要用于打招呼请教别人问题前的时候。"},"service":"baike","text":"你好。","operation":"ANSWER"}]
     * rc : 0
     * operation : ANSWER
     * service : openQA
     * answer : {"type":"T","text":"你好，来个拥抱吧！"}
     * text : 你好。
     */

    private int rc;
    private String operation;
    private String service;
    private AnswerBean answer;
    private String text;
    private List<MoreResultsBean> moreResults;

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

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
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

    public static class AnswerBean {
        /**
         * type : T
         * text : 你好，来个拥抱吧！
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

    public static class MoreResultsBean {
        /**
         * rc : 0
         * answer : {"type":"T","text":" 词语解释你好是对别人一种尊敬，遇到认识的人或不认识的人都可以说的。你好主要用于打招呼请教别人问题前的时候。"}
         * service : baike
         * text : 你好。
         * operation : ANSWER
         */

        private int rc;
        private AnswerBeanX answer;
        private String service;
        private String text;
        private String operation;

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public AnswerBeanX getAnswer() {
            return answer;
        }

        public void setAnswer(AnswerBeanX answer) {
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

        public static class AnswerBeanX {
            /**
             * type : T
             * text :  词语解释你好是对别人一种尊敬，遇到认识的人或不认识的人都可以说的。你好主要用于打招呼请教别人问题前的时候。
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
