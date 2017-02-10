package cn.zhangxd.platform.system.api.entity;


import cn.zhangxd.platform.common.api.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 用户
 *
 * @author zhangxd
 */
public class TripUser extends DataEntity {
    /**
     * 图片存储地址
     */
    public static final String IMAGE_FOLDER = "user";
    /**
     * 未知
     */
    public static final String GENDER_UNKNOWN = "0";

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private String age;
    /**
     * 电话
     */
    private String photo;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 备注
     */
    private String remarks;

    public TripUser() {
        super();
    }

    public TripUser(String id) {
        super(id);
    }

    @Override
    public void preInsert() {
        super.preInsert();
        this.gender = GENDER_UNKNOWN; //未知
        this.enabled = true; //可用
    }

    @Length(min = 1, max = 64)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 0, max = 255)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
