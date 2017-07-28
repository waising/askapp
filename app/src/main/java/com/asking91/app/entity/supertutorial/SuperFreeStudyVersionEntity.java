package com.asking91.app.entity.supertutorial;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxiao on 2016/12/2.
 */

public class SuperFreeStudyVersionEntity {

    private String id;
    private Object pid;
    private String text;
    private Object parentId;
    private Object qtip;
    private int type;
    private boolean leaf;
    private Object state;
    @SerializedName("showType")
    private boolean showType;
    private Object level;
    private Object attributes;
    private Object iconCls;
    private List<ChildrenBeanX> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getQtip() {
        return qtip;
    }

    public void setQtip(Object qtip) {
        this.qtip = qtip;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public Object getLevel() {
        return level;
    }

    public void setLevel(Object level) {
        this.level = level;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public Object getIconCls() {
        return iconCls;
    }

    public void setIconCls(Object iconCls) {
        this.iconCls = iconCls;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {

        private String id;
        private Object pid;
        private String text;
        private String parentId;
        private Object qtip;
        private int type;
        private boolean leaf;
        private Object state;
        @SerializedName("showType")
        private boolean showType;
        private Object level;
        private Object attributes;
        private Object iconCls;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public Object getQtip() {
            return qtip;
        }

        public void setQtip(Object qtip) {
            this.qtip = qtip;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public boolean isShowType() {
            return showType;
        }

        public void setShowType(boolean showType) {
            this.showType = showType;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public Object getAttributes() {
            return attributes;
        }

        public void setAttributes(Object attributes) {
            this.attributes = attributes;
        }

        public Object getIconCls() {
            return iconCls;
        }

        public void setIconCls(Object iconCls) {
            this.iconCls = iconCls;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {

            private String id;
            private Object pid;
            private String text;
            private String parentId;
            private Object qtip;
            private int type;
            private boolean leaf;
            private Object state;
            @SerializedName("show_type")
            private boolean showType;
            private Object level;
            private AttributesBean attributes;
            private Object children;
            private Object iconCls;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getPid() {
                return pid;
            }

            public void setPid(Object pid) {
                this.pid = pid;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public Object getQtip() {
                return qtip;
            }

            public void setQtip(Object qtip) {
                this.qtip = qtip;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public boolean isLeaf() {
                return leaf;
            }

            public void setLeaf(boolean leaf) {
                this.leaf = leaf;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public boolean isShowType() {
                return showType;
            }

            public void setShowType(boolean showType) {
                this.showType = showType;
            }

            public Object getLevel() {
                return level;
            }

            public void setLevel(Object level) {
                this.level = level;
            }

            public AttributesBean getAttributes() {
                return attributes;
            }

            public void setAttributes(AttributesBean attributes) {
                this.attributes = attributes;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }

            public Object getIconCls() {
                return iconCls;
            }

            public void setIconCls(Object iconCls) {
                this.iconCls = iconCls;
            }

            public static class AttributesBean {

                private String tip_id;

                public String getTip_id() {
                    return tip_id;
                }

                public void setTip_id(String tip_id) {
                    this.tip_id = tip_id;
                }
            }
        }
    }
}
