package com.asking91.app.entity.juniorTohigh;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linbin on 2017/7/5.
 */

public class GoodsCoupon implements Serializable{


    /**
     * id : 595c8574e335e10006a5f486
     * userId : 595c8561b6c3c40007d07735
     * event : {"id":"595c8304e335e100072c247a","name":"APP注册送初升高优惠券","createDate":1499235538763,"startTime":1499184000000,"endTime":1502726400000,"creater":"谢思勇","remark":"限购买初升高衔接课使用","rule":{"id":"595c4f66642553000706a77c","name":"1元优惠券","code":"YHJ03","purchase":200,"off":199,"closingDate":1502812800000,"creater":"管理员","eventType":{"id":"3","name":"优惠券","createDate":1499052981286},"sendMode":{"id":"1","name":"注册赠送"},"getMode":{"id":"1","name":"自动领取"}},"plateforms":[{"id":"1","name":"APP"}]}
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
         * id : 595c8304e335e100072c247a
         * name : APP注册送初升高优惠券
         * createDate : 1499235538763
         * startTime : 1499184000000
         * endTime : 1502726400000
         * creater : 谢思勇
         * remark : 限购买初升高衔接课使用
         * rule : {"id":"595c4f66642553000706a77c","name":"1元优惠券","code":"YHJ03","purchase":200,"off":199,"closingDate":1502812800000,"creater":"管理员","eventType":{"id":"3","name":"优惠券","createDate":1499052981286},"sendMode":{"id":"1","name":"注册赠送"},"getMode":{"id":"1","name":"自动领取"}}
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
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
             * id : 595c4f66642553000706a77c
             * name : 1元优惠券
             * code : YHJ03
             * purchase : 200
             * off : 199
             * closingDate : 1502812800000
             * creater : 管理员
             * eventType : {"id":"3","name":"优惠券","createDate":1499052981286}
             * sendMode : {"id":"1","name":"注册赠送"}
             * getMode : {"id":"1","name":"自动领取"}
             */

            private String id;
            private String name;
            private String code;
            private int purchase;
            private int off;
            private long closingDate;
            private String creater;
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

            public long getClosingDate() {
                return closingDate;
            }

            public void setClosingDate(long closingDate) {
                this.closingDate = closingDate;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
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
                 * createDate : 1499052981286
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
