package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 区域表
* @TableName region
*/
public class Region implements Serializable {

    /**
    * 区域id
    */
    @NotNull(message="[区域id]不能为空")
    @ApiModelProperty("区域id")
    private Long id;
    /**
    * 城市编码
    */
    @NotBlank(message="[城市编码]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("城市编码")
    @Length(max= 255,message="编码长度不能超过255")
    private String cityCode;
    /**
    * 区域名称
    */
    @NotBlank(message="[区域名称]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("区域名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String name;
    /**
    * 负责人名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("负责人名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String managerName;
    /**
    * 负责人电话
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("负责人电话")
    @Length(max= 255,message="编码长度不能超过255")
    private String managerPhone;
    /**
    * 是否启用，0草稿,1禁用，2启用
    */
    @NotNull(message="[是否启用，0草稿,1禁用，2启用]不能为空")
    @ApiModelProperty("是否启用，0草稿,1禁用，2启用")
    private Integer activeStatus;
    /**
    * 排序字段
    */
    @NotNull(message="[排序字段]不能为空")
    @ApiModelProperty("排序字段")
    private Integer sortNum;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @NotNull(message="[更新时间]不能为空")
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
    * 创建者
    */
    @ApiModelProperty("创建者")
    private Long createBy;
    /**
    * 更新者
    */
    @ApiModelProperty("更新者")
    private Long updateBy;

    /**
    * 区域id
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 城市编码
    */
    private void setCityCode(String cityCode){
    this.cityCode = cityCode;
    }

    /**
    * 区域名称
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 负责人名称
    */
    private void setManagerName(String managerName){
    this.managerName = managerName;
    }

    /**
    * 负责人电话
    */
    private void setManagerPhone(String managerPhone){
    this.managerPhone = managerPhone;
    }

    /**
    * 是否启用，0草稿,1禁用，2启用
    */
    private void setActiveStatus(Integer activeStatus){
    this.activeStatus = activeStatus;
    }

    /**
    * 排序字段
    */
    private void setSortNum(Integer sortNum){
    this.sortNum = sortNum;
    }

    /**
    * 创建时间
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }

    /**
    * 更新时间
    */
    private void setUpdateTime(Date updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 创建者
    */
    private void setCreateBy(Long createBy){
    this.createBy = createBy;
    }

    /**
    * 更新者
    */
    private void setUpdateBy(Long updateBy){
    this.updateBy = updateBy;
    }


    /**
    * 区域id
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 城市编码
    */
    private String getCityCode(){
    return this.cityCode;
    }

    /**
    * 区域名称
    */
    private String getName(){
    return this.name;
    }

    /**
    * 负责人名称
    */
    private String getManagerName(){
    return this.managerName;
    }

    /**
    * 负责人电话
    */
    private String getManagerPhone(){
    return this.managerPhone;
    }

    /**
    * 是否启用，0草稿,1禁用，2启用
    */
    private Integer getActiveStatus(){
    return this.activeStatus;
    }

    /**
    * 排序字段
    */
    private Integer getSortNum(){
    return this.sortNum;
    }

    /**
    * 创建时间
    */
    private Date getCreateTime(){
    return this.createTime;
    }

    /**
    * 更新时间
    */
    private Date getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 创建者
    */
    private Long getCreateBy(){
    return this.createBy;
    }

    /**
    * 更新者
    */
    private Long getUpdateBy(){
    return this.updateBy;
    }

}
