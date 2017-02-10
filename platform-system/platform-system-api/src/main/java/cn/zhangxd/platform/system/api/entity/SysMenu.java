package cn.zhangxd.platform.system.api.entity;

import cn.zhangxd.platform.common.api.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 *
 * @author zhangxd
 */
public class SysMenu extends DataEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 父级编号
     */
    private String parentId;
    /**
     * 所有父级编号
     */
    private String parentIds;
    /**
     * 名称
     */
    private String name;
    /**
     * 链接
     */
    private String href;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否在菜单中显示（1：显示；0：不显示）
     */
    private Boolean isShow;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否是叶子节点
     */
    private Boolean leaf = true;
    /**
     * 子节点
     */
    private List<SysMenu> children = new ArrayList<>();

    public SysMenu() {
        super();
    }

    public SysMenu(String id) {
        super(id);
    }

    @Override
    public void preInsert() {
        super.preInsert();
        this.sort = 30;
        this.isShow = true;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 2000)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min = 0, max = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Length(min = 0, max = 200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    /**
     * 添加子节点
     *
     * @param node 菜单节点
     */
    public void addChild(SysMenu node) {
        this.children.add(node);
    }

}