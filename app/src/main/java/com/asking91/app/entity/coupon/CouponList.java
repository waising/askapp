package com.asking91.app.entity.coupon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linbin on 2017/6/19.
 */

public class CouponList {


    /**
     * flag : 0
     * msg : success
     * content : {"content":[{"id":"59477a0e5905ee77d83143f1","userId":"5681dc026be6adfd05759b36","event":{"id":"59376db15905ee6f083c7f41","eventType":{"id":"3","name":"优惠券","createDate":1496217952441},"name":"大概如此","createDate":1496804784907,"startTime":1496804784966,"endTime":1498791984907,"creater":"神","rule":{"purchase":5,"off":2,"persentOff":7,"product":{"id":"1","name":"初升高衔接课"},"activeTime":2,"closingDate":1496804784966},"plateforms":[{"id":"1","name":"APP"}],"sendMode":{"id":"3","name":"普通赠送"}},"used":0}],"last":true,"totalElements":1,"totalPages":1,"first":true,"sort":[{"direction":"DESC","property":"$natural","ignoreCase":false,"nullHandling":"NATIVE","descending":true,"ascending":false}],"numberOfElements":1,"size":10,"number":0}
     */

    private int flag;
    private String msg;
    private ContentBeanX content;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContentBeanX getContent() {
        return content;
    }

    public void setContent(ContentBeanX content) {
        this.content = content;
    }

    public static class ContentBeanX implements Serializable{
        /**
         * content : [{"id":"59477a0e5905ee77d83143f1","userId":"5681dc026be6adfd05759b36","event":{"id":"59376db15905ee6f083c7f41","eventType":{"id":"3","name":"优惠券","createDate":1496217952441},"name":"大概如此","createDate":1496804784907,"startTime":1496804784966,"endTime":1498791984907,"creater":"神","rule":{"purchase":5,"off":2,"persentOff":7,"product":{"id":"1","name":"初升高衔接课"},"activeTime":2,"closingDate":1496804784966},"plateforms":[{"id":"1","name":"APP"}],"sendMode":{"id":"3","name":"普通赠送"}},"used":0}]
         * last : true
         * totalElements : 1
         * totalPages : 1
         * first : true
         * sort : [{"direction":"DESC","property":"$natural","ignoreCase":false,"nullHandling":"NATIVE","descending":true,"ascending":false}]
         * numberOfElements : 1
         * size : 10
         * number : 0
         */

        private boolean last;
        private int totalElements;
        private int totalPages;
        private boolean first;
        private int numberOfElements;
        private int size;
        private int number;
        private ArrayList<ContentBean> content;
        private ArrayList<SortBean> sort;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public ArrayList<ContentBean> getContent() {
            return content;
        }

        public void setContent(ArrayList<ContentBean> content) {
            this.content = content;
        }

        public ArrayList<SortBean> getSort() {
            return sort;
        }

        public void setSort(ArrayList<SortBean> sort) {
            this.sort = sort;
        }

        public static class ContentBean implements Serializable{
            /**
             * id : 59477a0e5905ee77d83143f1
             * userId : 5681dc026be6adfd05759b36
             * event : {"id":"59376db15905ee6f083c7f41","eventType":{"id":"3","name":"优惠券","createDate":1496217952441},"name":"大概如此","createDate":1496804784907,"startTime":1496804784966,"endTime":1498791984907,"creater":"神","rule":{"purchase":5,"off":2,"persentOff":7,"product":{"id":"1","name":"初升高衔接课"},"activeTime":2,"closingDate":1496804784966},"plateforms":[{"id":"1","name":"APP"}],"sendMode":{"id":"3","name":"普通赠送"}}
             * used : 0
             */

            private String id;
            private String userId;
            private EventBean event;
            private int used;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public EventBean getEvent() {
                return event;
            }

            public void setEvent(EventBean event) {
                this.event = event;
            }

            public int getUsed() {
                return used;
            }

            public void setUsed(int used) {
                this.used = used;
            }

            public static class EventBean implements Serializable{
                /**
                 * id : 59376db15905ee6f083c7f41
                 * eventType : {"id":"3","name":"优惠券","createDate":1496217952441}
                 * name : 大概如此
                 * createDate : 1496804784907
                 * startTime : 1496804784966
                 * endTime : 1498791984907
                 * creater : 神
                 * rule : {"purchase":5,"off":2,"persentOff":7,"product":{"id":"1","name":"初升高衔接课"},"activeTime":2,"closingDate":1496804784966}
                 * plateforms : [{"id":"1","name":"APP"}]
                 * sendMode : {"id":"3","name":"普通赠送"}
                 */

                private String id;
                private EventTypeBean eventType;
                private String name;
                private long createDate;
                private long startTime;
                private long endTime;
                private String creater;
                private RuleBean rule;
                private SendModeBean sendMode;
                private List<PlateformsBean> plateforms;


                private String remark;

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public EventTypeBean getEventType() {
                    return eventType;
                }

                public void setEventType(EventTypeBean eventType) {
                    this.eventType = eventType;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public long getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(long createDate) {
                    this.createDate = createDate;
                }

                public long getStartTime() {
                    return startTime;
                }

                public void setStartTime(long startTime) {
                    this.startTime = startTime;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }

                public RuleBean getRule() {
                    return rule;
                }

                public void setRule(RuleBean rule) {
                    this.rule = rule;
                }

                public SendModeBean getSendMode() {
                    return sendMode;
                }

                public void setSendMode(SendModeBean sendMode) {
                    this.sendMode = sendMode;
                }

                public List<PlateformsBean> getPlateforms() {
                    return plateforms;
                }

                public void setPlateforms(List<PlateformsBean> plateforms) {
                    this.plateforms = plateforms;
                }

                public static class EventTypeBean implements Serializable{
                    /**
                     * id : 3
                     * name : 优惠券
                     * createDate : 1496217952441
                     */

                    private String id;
                    private String name;
                    private long createDate;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public long getCreateDate() {
                        return createDate;
                    }

                    public void setCreateDate(long createDate) {
                        this.createDate = createDate;
                    }
                }

                public static class RuleBean implements Serializable{
                    /**
                     * purchase : 5
                     * off : 2
                     * persentOff : 7
                     * product : {"id":"1","name":"初升高衔接课"}
                     * activeTime : 2
                     * closingDate : 1496804784966
                     */

                    private int purchase;
                    private int off;
                    private int persentOff;
                    private ProductBean product;
                    private int activeTime;
                    private long closingDate;

                    public int getPurchase() {
                        return purchase;
                    }

                    public void setPurchase(int purchase) {
                        this.purchase = purchase;
                    }

                    public int getOff() {
                        return off;
                    }

                    public void setOff(int off) {
                        this.off = off;
                    }

                    public int getPersentOff() {
                        return persentOff;
                    }

                    public void setPersentOff(int persentOff) {
                        this.persentOff = persentOff;
                    }

                    public ProductBean getProduct() {
                        return product;
                    }

                    public void setProduct(ProductBean product) {
                        this.product = product;
                    }

                    public int getActiveTime() {
                        return activeTime;
                    }

                    public void setActiveTime(int activeTime) {
                        this.activeTime = activeTime;
                    }

                    public long getClosingDate() {
                        return closingDate;
                    }

                    public void setClosingDate(long closingDate) {
                        this.closingDate = closingDate;
                    }

                    public static class ProductBean implements Serializable{
                        /**
                         * id : 1
                         * name : 初升高衔接课
                         */

                        private String id;
                        private String name;

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }
                    }
                }

                public static class SendModeBean implements Serializable{
                    /**
                     * id : 3
                     * name : 普通赠送
                     */

                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class PlateformsBean implements Serializable{
                    /**
                     * id : 1
                     * name : APP
                     */

                    private String id;
                    private String name;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }

        public static class SortBean implements Serializable{
            /**
             * direction : DESC
             * property : $natural
             * ignoreCase : false
             * nullHandling : NATIVE
             * descending : true
             * ascending : false
             */

            private String direction;
            private String property;
            private boolean ignoreCase;
            private String nullHandling;
            private boolean descending;
            private boolean ascending;

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public boolean isIgnoreCase() {
                return ignoreCase;
            }

            public void setIgnoreCase(boolean ignoreCase) {
                this.ignoreCase = ignoreCase;
            }

            public String getNullHandling() {
                return nullHandling;
            }

            public void setNullHandling(String nullHandling) {
                this.nullHandling = nullHandling;
            }

            public boolean isDescending() {
                return descending;
            }

            public void setDescending(boolean descending) {
                this.descending = descending;
            }

            public boolean isAscending() {
                return ascending;
            }

            public void setAscending(boolean ascending) {
                this.ascending = ascending;
            }
        }
    }
}
