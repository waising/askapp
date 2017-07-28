package com.asking91.app.entity.oto;

import java.util.List;

/**
 * Created by wxiao on 2016/11/19.
 */

public class OtoHwyResultEntity {
    /**
     * code : 0
     * result : \documentstyle[12pt]{article}
     \begin{document}
     \begin{displaymath}
     |_{,{\frac {1;1^{?}'''--\cdot }{\div }}}^{j}\backslash |\dot {\| {\dot {\| }}}\end{displaymath}
     \end{document}
     * blocks : [{"rect":{"left":"0","top":"0","right":"783","bottom":"626"},"type":"1","formulaResult":"\\documentstyle[12pt]{article} \n \\begin{document} \n \\begin{displaymath} \n |_{,{\\frac {1;1^{?}'''--\\cdot }{\\div }}}^{j}\\backslash |\\dot {\\| {\\dot {\\| }}}\\end{displaymath} \n \\end{document}"}]
     */

    private String code;
    private String result;
    private List<BlocksBean> blocks;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BlocksBean> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlocksBean> blocks) {
        this.blocks = blocks;
    }

    public static class BlocksBean {
        /**
         * rect : {"left":"0","top":"0","right":"783","bottom":"626"}
         * type : 1
         * formulaResult : \documentstyle[12pt]{article}
         \begin{document}
         \begin{displaymath}
         |_{,{\frac {1;1^{?}'''--\cdot }{\div }}}^{j}\backslash |\dot {\| {\dot {\| }}}\end{displaymath}
         \end{document}
         */

        private RectBean rect;
        private String type;
        private String formulaResult;

        public RectBean getRect() {
            return rect;
        }

        public void setRect(RectBean rect) {
            this.rect = rect;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormulaResult() {
            return formulaResult;
        }

        public void setFormulaResult(String formulaResult) {
            this.formulaResult = formulaResult;
        }

        public static class RectBean {
            /**
             * left : 0
             * top : 0
             * right : 783
             * bottom : 626
             */

            private String left;
            private String top;
            private String right;
            private String bottom;

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getRight() {
                return right;
            }

            public void setRight(String right) {
                this.right = right;
            }

            public String getBottom() {
                return bottom;
            }

            public void setBottom(String bottom) {
                this.bottom = bottom;
            }
        }
    }
}
