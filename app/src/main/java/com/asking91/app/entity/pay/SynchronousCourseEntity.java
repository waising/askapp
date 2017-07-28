package com.asking91.app.entity.pay;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 同步课程的entity
 * Created by linbin on 2017/6/28.
 */

public class SynchronousCourseEntity implements Serializable {


    private ValueBean value;
    private String name;
    private ArrayList<NodelistBeanX> nodelist;

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<NodelistBeanX> getNodelist() {
        return nodelist;
    }

    public void setNodelist(ArrayList<NodelistBeanX> nodelist) {
        this.nodelist = nodelist;
    }

    public static class ValueBean implements Serializable {
        /**
         * _id : 59487ef769bfa60335f18bee
         * sequence : 1
         * path : ,KC03,
         * depth : 1
         * productId : KC03
         * createTime : 2017-06-20 09:48:39
         * productName : 同步课堂
         */

        private String _id;
        private String sequence;
        private String path;
        private String depth;
        private String productId;
        private String createTime;
        private String productName;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }

    public static class NodelistBeanX implements Serializable {


        private ValueBeanX value;
        private String name;
        private ArrayList<NodelistBean> nodelist;

        public ValueBeanX getValue() {
            return value;
        }

        public void setValue(ValueBeanX value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<NodelistBean> getNodelist() {
            return nodelist;
        }

        public void setNodelist(ArrayList<NodelistBean> nodelist) {
            this.nodelist = nodelist;
        }

        public static class ValueBeanX implements Serializable {
            /**
             * _id : 59487f1669bfa60335f18bef
             * productId : XK01
             * sequence : 1
             * productName : 初中数学
             * depth : 3
             * path : ,KC03,XK01,
             * createTime : 2017-06-20 09:49:10
             */

            private String _id;
            private String productId;
            private String sequence;
            private String productName;
            private String depth;
            private String path;
            private String createTime;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getSequence() {
                return sequence;
            }

            public void setSequence(String sequence) {
                this.sequence = sequence;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getDepth() {
                return depth;
            }

            public void setDepth(String depth) {
                this.depth = depth;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class NodelistBean implements Serializable {
            /**
             * value : {"_id":"59487f4c69bfa60335f18bf0","productId":"BB01","sequence":"1","productName":"人教版","depth":"4","path":",KC03,XK01,BB01,","createTime":"2017-06-20 09:50:04","courseList":[{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC03-XK01-BB01-BNJS01","courseName":"八年级上","coursePrice":1,"purchaseState":0},{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC03-XK01-BB01-BNJX02","courseName":"八年级下","coursePrice":1,"purchaseState":0},{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC-KC03-XK01-BB01-QNJS01","courseName":"七年级上","coursePrice":1,"purchaseState":0},{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC03-XK01-BB01-QNJX02","courseName":"七年级下","coursePrice":1,"purchaseState":0},{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC03-XK01-BB01-GNJS01","courseName":"九年级上","coursePrice":1,"purchaseState":0},{"courseTypeId":"BB01","isPresent":0,"commodityId":"KC03-XK01-BB01-GNJX02","courseName":"九年级下","coursePrice":1,"purchaseState":0}]}
             * name : BB01
             * nodelist : []
             */

            private ValueBeanXX value;
            private String name;
            private ArrayList<?> nodelist;

            public ValueBeanXX getValue() {
                return value;
            }

            public void setValue(ValueBeanXX value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<?> getNodelist() {
                return nodelist;
            }

            public void setNodelist(ArrayList<?> nodelist) {
                this.nodelist = nodelist;
            }

            public static class ValueBeanXX implements Serializable {


                private String _id;
                private String productId;
                private String sequence;
                private String productName;
                private String depth;
                private String path;
                private String createTime;
                private ArrayList<CourseListBean> courseList;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getSequence() {
                    return sequence;
                }

                public void setSequence(String sequence) {
                    this.sequence = sequence;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getDepth() {
                    return depth;
                }

                public void setDepth(String depth) {
                    this.depth = depth;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public ArrayList<CourseListBean> getCourseList() {
                    return courseList;
                }

                public void setCourseList(ArrayList<CourseListBean> courseList) {
                    this.courseList = courseList;
                }

                public static class CourseListBean implements Serializable {
                    /**
                     * courseTypeId : BB01
                     * isPresent : 0
                     * commodityId : KC03-XK01-BB01-BNJS01
                     * courseName : 八年级上
                     * coursePrice : 1
                     * purchaseState : 0
                     */

                    private String courseTypeId;
                    private int isPresent;
                    private String commodityId;
                    private String courseName;
                    private int coursePrice;
                    private int purchaseState;

                    private String courseTypeName;
                    private String courseImgUrl;
                    private String timeLimit;

                    private String favorableEndStr;

                    private String favorableStartStr;

                    private String favorableEnd;

                    private String favorableStart;

                    public String getFavorableEnd() {
                        return favorableEnd;
                    }

                    public void setFavorableEnd(String favorableEnd) {
                        this.favorableEnd = favorableEnd;
                    }

                    public String getFavorableStart() {
                        return favorableStart;
                    }

                    public void setFavorableStart(String favorableStart) {
                        this.favorableStart = favorableStart;
                    }

                    public String getFavorableEndStr() {
                        return favorableEndStr;
                    }

                    public void setFavorableEndStr(String favorableEndStr) {
                        this.favorableEndStr = favorableEndStr;
                    }

                    public String getFavorableStartStr() {
                        return favorableStartStr;
                    }

                    public void setFavorableStartStr(String favorableStartStr) {
                        this.favorableStartStr = favorableStartStr;
                    }

                    public String getTimeLimit() {
                        return timeLimit;
                    }

                    public void setTimeLimit(String timeLimit) {
                        this.timeLimit = timeLimit;
                    }

                    public String getCourseImgUrl() {
                        return courseImgUrl;
                    }

                    public void setCourseImgUrl(String courseImgUrl) {
                        this.courseImgUrl = courseImgUrl;
                    }

                    public String getCourseTypeName() {
                        return courseTypeName;
                    }

                    public void setCourseTypeName(String courseTypeName) {
                        this.courseTypeName = courseTypeName;
                    }

                    public String getCourseTypeId() {
                        return courseTypeId;
                    }

                    public void setCourseTypeId(String courseTypeId) {
                        this.courseTypeId = courseTypeId;
                    }

                    public int getIsPresent() {
                        return isPresent;
                    }

                    public void setIsPresent(int isPresent) {
                        this.isPresent = isPresent;
                    }

                    public String getCommodityId() {
                        return commodityId;
                    }

                    public void setCommodityId(String commodityId) {
                        this.commodityId = commodityId;
                    }

                    public String getCourseName() {
                        return courseName;
                    }

                    public void setCourseName(String courseName) {
                        this.courseName = courseName;
                    }

                    public int getCoursePrice() {
                        return coursePrice;
                    }

                    public void setCoursePrice(int coursePrice) {
                        this.coursePrice = coursePrice;
                    }

                    public int getPurchaseState() {
                        return purchaseState;
                    }

                    public void setPurchaseState(int purchaseState) {
                        this.purchaseState = purchaseState;
                    }
                }
            }
        }
    }
}
