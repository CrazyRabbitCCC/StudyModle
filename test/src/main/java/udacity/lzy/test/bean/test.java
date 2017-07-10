package udacity.lzy.test.bean;
// @author: lzy  time: 2016/11/09.


import java.util.List;

/**
 * sn : 1
 * ls : false
 * bg : 0
 * ed : 0
 * ws : [{"bg":0,"cw":[{"sc":0,"w":"jilu"}]}]
 */
public class test {

    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    private List<Ws> ws;

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public List<Ws> getWs() {
        return ws;
    }

    public void setWs(List<Ws> ws) {
        this.ws = ws;
    }
    /**
     * bg : 0
     * cw : [{"sc":0,"w":"jilu"}]
     */
    public static class Ws {
        private int bg;
        private List<Cw> cw;

        public int getBg() {
            return bg;
        }

        public void setBg(int bg) {
            this.bg = bg;
        }

        public List<Cw> getCw() {
            return cw;
        }

        public void setCw(List<Cw> cw) {
            this.cw = cw;
        }

        /**
         * sc : 0
         * w : jilu
         */
        public static class Cw {
            private double sc;
            private String w;

            public double getSc() {
                return sc;
            }

            public void setSc(double sc) {
                this.sc = sc;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }
        }
    }


//    int sn;
//    boolean ls;
//    int bg;
//    int ed;
//    List<Ws> ws;
//
//    public static class Ws {
//       int bg;
//        List<Cw> cw;
//
//        public static class Cw {
//            double bg;
//            String w;
//        }
//    }

}
