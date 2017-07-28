package com.asking91.app.entity.coupon;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linbin on 2017/7/2.
 */

public class CouponRegister implements Serializable{


    /**
     * id : 595618d75905eef380ca72b6
     * userId : 59561789f6ef57000592792d
     * event : {"id":"5955f6595905eee550d10950","name":"注册活动","createDate":1498805849877,"startTime":1498805849877,"endTime":1530341849877,"creater":"神","remake":"备注","rule":{"id":"2","name":"注册送卷","code":"YHJ02","purchase":5,"off":2,"persentOff":100,"closingDate":1498987479676,"eventType":{"id":"3","name":"优惠券","createDate":1498805849749},"sendMode":{"id":"1","name":"注册赠送"},"getMode":{"id":"1","name":"自动领取"}},"plateforms":[{"id":"1","name":"APP"}]}
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
         * id : 5955f6595905eee550d10950
         * name : 注册活动
         * createDate : 1498805849877
         * startTime : 1498805849877
         * endTime : 1530341849877
         * creater : 神
         * remake : 备注
         * rule : {"id":"2","name":"注册送卷","code":"YHJ02","purchase":5,"off":2,"persentOff":100,"closingDate":1498987479676,"eventType":{"id":"3","name":"优惠券","createDate":1498805849749},"sendMode":{"id":"1","name":"注册赠送"},"getMode":{"id":"1","name":"自动领取"}}
         * plateforms : [{"id":"1","name":"APP"}]
         */

        private String id;
        private String name;
        private long createDate;
        private long startTime;
        private long endTime;
        private String creater;
        private String remark;
        private RuleBean rule;


        private List<PlateformsBean> plateforms;

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

        public String getRemake() {
            return remark;
        }

        public void setRemake(String remark) {
            this.remark = remark;
        }

        public RuleBean getRule() {
            return rule;
        }

        public void setRule(RuleBean rule) {
            this.rule = rule;
        }

        public List<PlateformsBean> getPlateforms() {
            return plateforms;
        }

        public void setPlateforms(List<PlateformsBean> plateforms) {
            this.plateforms = plateforms;
        }

        public static class RuleBean implements Serializable{
            /**
             * id : 2
             * name : 注册送卷
             * code : YHJ02
             * purchase : 5
             * off : 2
             * persentOff : 100
             * closingDate : 1498987479676
             * eventType : {"id":"3","name":"优惠券","createDate":1498805849749}
             * sendMode : {"id":"1","name":"注册赠送"}
             * getMode : {"id":"1","name":"自动领取"}
             */

            private String id;
            private String name;
            private String code;
            private int purchase;
            private int off;
            private int persentOff;
            private long closingDate;
            private EventTypeBean eventType;
            private SendModeBean sendMode;
            private GetModeBean getMode;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

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

            public long getClosingDate() {
                return closingDate;
            }

            public void setClosingDate(long closingDate) {
                this.closingDate = closingDate;
            }

            public EventTypeBean getEventType() {
                return eventType;
            }

            public void setEventType(EventTypeBean eventType) {
                this.eventType = eventType;
            }

            public SendModeBean getSendMode() {
                return sendMode;
            }

            public void setSendMode(SendModeBean sendMode) {
                this.sendMode = sendMode;
            }

            public GetModeBean getGetMode() {
                return getMode;
            }

            public void setGetMode(GetModeBean getMode) {
                this.getMode = getMode;
            }

            public static class EventTypeBean implements Serializable{
                /**
                 * id : 3
                 * name : 优惠券
                 * createDate : 1498805849749
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

            public static class SendModeBean implements Serializable{
                /**
                 * id : 1
                 * name : 注册赠送
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

            public static class GetModeBean implements Serializable{
                /**
                 * id : 1
                 * name : 自动领取
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
