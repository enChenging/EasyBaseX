package com.release.simplex.mvp.model;

import java.util.List;

/**
 * @author Mr.release
 * @create 2020/5/6
 * @Describe
 */

public class MenuBean {


    private List<FirstBean> first;

    public List<FirstBean> getFirst() {
        return first;
    }

    public void setFirst(List<FirstBean> first) {
        this.first = first;
    }

    public static class FirstBean {
        /**
         * title : first1-1
         * second : [{"title":"first2-1","third":[{"title":"first3-1"}]},{"title":"first2-2","third":[{"title":"first3-1"}]},{"title":"first2-3"}]
         */

        private String title;
        private List<SecondBean> second;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SecondBean> getSecond() {
            return second;
        }

        public void setSecond(List<SecondBean> second) {
            this.second = second;
        }

        public static class SecondBean {
            /**
             * title : first2-1
             * third : [{"title":"first3-1"}]
             */

            private String title;
            private List<ThirdBean> third;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ThirdBean> getThird() {
                return third;
            }

            public void setThird(List<ThirdBean> third) {
                this.third = third;
            }

            public static class ThirdBean {
                /**
                 * title : first3-1
                 */

                private String title;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
