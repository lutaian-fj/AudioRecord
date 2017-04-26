package lta.com.audioRecord.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * @author: LuTaiAn
 * @className: BaseModel
 * @description: Model积累
 * @date: 2017/4/26
 */
public class BaseModel {
    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String DELETED = "deleted";
    public static final String IS_EDIT = "is_edit";

    @DatabaseField(id = true, useGetSet = true)
    @SerializedName("identifier")
    @Expose
    private String id;  // uuid 全局唯一id

    @DatabaseField(useGetSet = true)
    @Expose
    private String serverId;  // uuid 全局唯一id

    private String serverRelationId;// 预定生成的ID

    @DatabaseField(columnName = CREATE_TIME, useGetSet = true)
    @SerializedName("create_time")
    @Expose
    private long createTime;

    @DatabaseField(columnName = UPDATE_TIME, useGetSet = true)
    @SerializedName("update_time")
    @Expose
    private long updateTime;

    @DatabaseField(useGetSet = true)
    private long serverUpdateTime; //用于记录服务器的时间戳

    @DatabaseField(columnName = DELETED, useGetSet = true)
    @SerializedName("deleted")
    @Expose
    private int deleted ;

    @DatabaseField(columnName = IS_EDIT, useGetSet = true)
    @SerializedName("is_edit")
    @Expose
    private int isEdit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public static String getID() {
        return ID;
    }

    public static String getDELETED() {
        return DELETED;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public long getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(long serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public String getServerRelationId() {
        return serverRelationId;
    }

    public void setServerRelationId(String serverRelationId) {
        this.serverRelationId = serverRelationId;
    }
}
